package com.ydt.repository;

import com.ydt.entity.RoleObject;
import com.ydt.entity.RoleObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleScreenRepository extends JpaRepository<RoleObject,RoleObjectId> {
    @Query("select count(*) from RoleObject o where o.id.roleId = :roleId and o.id.objectId = :objectId")
    Integer existsObjectIdAndRoleId(@Param("roleId")int roleId,@Param("objectId")int objectId);
}
