package com.ydt.repository;

import com.ydt.entity.PositionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionUser,Integer> {
}
