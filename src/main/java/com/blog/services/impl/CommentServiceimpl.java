package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CommentRepo;
import com.blog.dao.PostRepo;
import com.blog.dao.UserRepo;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.Category;
import com.blog.models.Comment;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@Service
public class CommentServiceimpl implements CommentService {
	
	@Autowired
	private CommentRepo crpo;
	
	@Autowired
	private PostRepo pro;
	
	
	@Autowired
	private UserRepo uro;
	
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CommentDto createcomment(CommentDto cdto, Integer postId, Integer userId) {
		
		Post post=this.pro.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		
		User user=this.uro.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
		
		
		Comment comment=this.dtotoComment(cdto);
		
		comment.setContent(cdto.getContent());
		
		comment.setPost(post);
		comment.setUser(user);
		Comment savedcomment = this.crpo.save(comment);
		
		
		
		
		
		return this.commenttodto(savedcomment);
	}
	

	@Override
	public void deleteComment(Integer commentId) {
		
		this.crpo.deleteById(commentId);
		
		
	}
	
	

	private Comment dtotoComment(CommentDto cdto)
	{
		
		Comment comment=this.modelmapper.map(cdto, Comment.class);
		return comment;
	}
	
	
	
	private CommentDto commenttodto(Comment comment)
	{
		
		CommentDto cdto=this.modelmapper.map(comment, CommentDto.class);
		
		return cdto;
	}
	
	
	
	
	

}
