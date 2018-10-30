package com.ydt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydt.entity.Role;
import com.ydt.entity.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(RoleName roleName);
}
