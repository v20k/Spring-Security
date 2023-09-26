package com.example.originalSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.originalSecurity.entity.Role;
import com.example.originalSecurity.enums.Roles;

public interface RoleRepository extends JpaRepository<Role, Long>{

	@Query(value = "select s from Role s where s.roleName=?1")
	Role findByRoleName(Roles role);

}
