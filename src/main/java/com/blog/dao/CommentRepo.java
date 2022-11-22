package com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
