package com.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.models.Category;
import com.blog.models.Post;
import com.blog.models.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	
	List<Post> findByUser(User user);
	
	List<Post>findByCategory(Category category);
	
	@Query("Select p from Post p where p.postTitle like:key")
	List<Post>searchbytitle(@Param("key") String title);
	

}
