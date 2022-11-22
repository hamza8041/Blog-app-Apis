
package com.blog.services;

import org.springframework.stereotype.Service;

import com.blog.payloads.CommentDto;

@Service
public interface CommentService {

	
	CommentDto createcomment(CommentDto comment,Integer postId,Integer userId);
	
	void deleteComment(Integer commentId);
	
}
