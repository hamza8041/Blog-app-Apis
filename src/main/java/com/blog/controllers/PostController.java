package com.blog.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.payloads.UserDto;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService ps;
	
	@Autowired
	private FileService fs;
	
	@Value("${project.image}")
	private String path;
	
	//create
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto>createPost(@RequestBody PostDto postdto,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		
		PostDto createPost = this.ps.createPost(postdto, categoryId, userId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>>getByUser(@PathVariable Integer userId)
	{
		
		List<PostDto> postbyUser = this.ps.getPostbyUser(userId);
		
		return new ResponseEntity<List<PostDto>>(postbyUser,HttpStatus.OK);
	}
	
	
	//get by category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getByCategory(@PathVariable Integer categoryId)
	{
		
		List<PostDto> postbyCategory = this.ps.getPostbyCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(postbyCategory,HttpStatus.OK);
	}
	
	
	
	//get all posts
	
	@GetMapping("/posts")
	
	public ResponseEntity <PostResponse> getallposts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber
	,@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize, @RequestParam(value = "sortBy",defaultValue = AppConstants.SORTBY,required = false)
	String sortBy)
	{
		PostResponse pr = this.ps.getallPosts(pageNumber, pageSize,sortBy);
		return new ResponseEntity<PostResponse>(pr,HttpStatus.OK);
		
	}
	
	
	//get post by Id
	
	@GetMapping("/posts/{postId}")
	
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer postId)
	{
		return ResponseEntity.ok(this.ps.getPostbyId(postId));
	}
	
	
	
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost( @PathVariable Integer postId)
	{
		this.ps.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("post Deleted Successfullly", true),HttpStatus.OK);
	}
	
	
	
	
	//update post
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto pdto,@PathVariable Integer postId)
	{
		
		PostDto updatedpdto=this.ps.updatePost(pdto, postId);
		return new ResponseEntity<PostDto>(updatedpdto,HttpStatus.OK);
	}
	
	
	
	
	//searching 
	@GetMapping("/posts/search")
	public ResponseEntity<List<PostDto>>searchPostByTitle(@RequestParam(value = "keyword") String keywords)
	{
		List<PostDto> searchPost = this.ps.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
	}
	
	
	
	//Post Image Upload
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> UploadImage(@PathVariable Integer postId, @RequestParam("image") MultipartFile image) throws IOException
	{
		PostDto pdto = this.ps.getPostbyId(postId);
		
		String uploadImageName = this.fs.uploadImage(path, image);
		
		
		pdto.setImageName(uploadImageName);
		
		PostDto updatePost = this.ps.updatePost(pdto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
		
		
	}
	
	
	
	
	
	

	
	
}
