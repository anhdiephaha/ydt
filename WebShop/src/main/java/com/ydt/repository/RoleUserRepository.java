package com.ydt.repository;

import com.ydt.entity.RoleUser;
import com.ydt.entity.RoleUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser , RoleUserId> {
}
