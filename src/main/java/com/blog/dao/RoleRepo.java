package com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	
	
	

}
