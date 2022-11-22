package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService CS;
	
	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto cdto, @PathVariable Integer userId,@PathVariable Integer postId)
	{
		
		CommentDto createcomment = this.CS.createcomment(cdto, postId, userId);
		
		return new ResponseEntity<CommentDto>(createcomment,HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/delete/{deleteId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer deleteId)
	{
		this.CS.deleteComment(deleteId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully",true),HttpStatus.OK);
	}
	
	

}
