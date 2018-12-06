package com.ydt.repository;

import com.ydt.entity.role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<role, Integer> {
}
