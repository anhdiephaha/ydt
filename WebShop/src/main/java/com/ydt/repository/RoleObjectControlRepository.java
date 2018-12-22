package com.ydt.repository;

import com.ydt.entity.RoleObjectControl;
import com.ydt.entity.RoleObjectControlId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleObjectControlRepository extends JpaRepository<RoleObjectControl,RoleObjectControlId> {
    @Query("select count(*) from RoleObjectControl o where o.id.roleId = :roleId and o.id.objectControlId = :objectControlId")
    Integer existsObjectControlIdAndRoleId(@Param("roleId")int roleId, @Param("objectControlId")int objectControlId);
}
