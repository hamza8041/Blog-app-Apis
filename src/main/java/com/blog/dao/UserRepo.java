package com.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {



Optional<User>  findByEmail(String email);

}
