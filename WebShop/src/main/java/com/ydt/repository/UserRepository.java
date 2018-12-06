package com.ydt.repository;

import com.ydt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	@Query("select o from Users o where o.userId = :idUser")
	Users getUserById(@Param("idUser")int id);
	Users findByUserName(String userName);
	Boolean existsByUserName(String username);
}
