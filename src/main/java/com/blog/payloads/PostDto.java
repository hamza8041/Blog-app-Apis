package com.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.models.Category;
import com.blog.models.Comment;
import com.blog.models.User;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PostDto {

	
	private int postId;
	private String postTitle;
	
	
	
	private String postContent;
	
	
	private String imageName;
	
	
	private Date addedDate;
	
	
	private CategoryDto category;
	

	private UserDto user;

	
	private List<CommentDto>comments=new ArrayList<>();
	
	
	
}
