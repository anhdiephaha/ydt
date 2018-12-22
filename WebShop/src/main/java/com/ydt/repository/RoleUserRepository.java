package com.ydt.repository;

import com.ydt.entity.RoleUser;
import com.ydt.entity.RoleUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser , RoleUserId> {
    @Query("select count(*) from RoleUser o where  o.id.userId = :userId and o.id.roleId = :roleId")
    Integer existsUserIdAndRoleId(@Param("userId")int userId,@Param("roleId")int roleId);
}
