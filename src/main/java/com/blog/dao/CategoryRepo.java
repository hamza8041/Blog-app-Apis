package com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
