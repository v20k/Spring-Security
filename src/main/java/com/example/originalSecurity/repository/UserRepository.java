package com.example.originalSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.originalSecurity.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select s from User s where s.userName=?1")
	User findByUserName(String username);

}
