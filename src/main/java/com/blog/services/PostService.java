package com.blog.services;

import java.util.List;

import com.blog.models.Post;
import com.blog.models.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	
	
	
	PostDto createPost(PostDto pdto,Integer categoryId,Integer userId);
	
	
	//update posts
	
	PostDto updatePost(PostDto pdto,Integer postId);
	
	
	//delete posts
	void deletePost(Integer postId);
	
	
	//get all posts
	PostResponse getallPosts(Integer pageNumber,Integer pageSize,String sortBy);
	
	
	PostDto getPostbyId(Integer postId);
	
	//get all posts by category
	
	List<PostDto> getPostbyCategory(Integer categoryId);
	
	
	//Get all posts by users
	
	List<PostDto> getPostbyUser(Integer userId);
	
	
	//search posts
	
	List<PostDto> searchPost(String keyword);
	

}
