package com.blog.payloads;

import com.blog.models.Post;
import com.blog.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {
	
	
	private int commentId;
	
	private String content;
	
	


}
