package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.dao.CategoryRepo;
import com.blog.dao.PostRepo;
import com.blog.dao.UserRepo;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.Category;
import com.blog.models.Post;
import com.blog.models.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.PostService;


@Service
public class PostServiceimpl implements PostService {

	@Autowired
	private PostRepo pro;
	
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private CategoryRepo crpo;
	
	
	
	
	@Override
	public PostDto createPost(PostDto pdto,Integer categoryId,Integer userId) {
		
		//get user
		
		User user=this.userrepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
		
		//get Category
		
		
		Category category=this.crpo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
		
		
		
		Post post=this.dtotoPost(pdto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		
		Post savedpost = this.pro.save(post);
		
		
		
		
		return this.posttoDto(savedpost);
		
	}

	@Override
	public PostDto updatePost(PostDto pdto, Integer postId) {
		
		Post post = this.pro.findById(postId)
		.orElseThrow(()->new ResourceNotFoundException("posts", "postId", postId));
		
		post.setPostTitle(pdto.getPostTitle());
		post.setPostContent(pdto.getPostContent());
		post.setImageName(pdto.getImageName());
		
		Post savedpost = this.pro.save(post);
		return this.posttoDto(savedpost);
	}
	

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.pro.findById(postId)
		.orElseThrow(()->new ResourceNotFoundException("posts", "postId", postId));
		
		this.pro.delete(post);
		
		
		
	}

	@Override
	public PostResponse getallPosts(Integer pageNumber,Integer pageSize,String sortBy) {
		
		
		
		
		Pageable p=PageRequest.of(pageNumber, pageSize,org.springframework.data.domain.Sort.by(sortBy).descending());
		
		
		
		Page<Post> pagepost = this.pro.findAll(p);
		
		List<Post> posts = pagepost.getContent();
		
		List<PostDto> allposts = posts.stream().map(post->this.posttoDto(post)).collect(Collectors.toList());
		
		PostResponse pr=new PostResponse();
		pr.setContent(allposts);
		pr.setPageNumber(pagepost.getNumber());
		pr.setPageSize(pagepost.getSize());
		pr.setTotalElements(pagepost.getTotalElements());
		pr.setTotalPages(pagepost.getTotalPages());
		pr.setLastPage(pagepost.isLast());
		
		
		
			return pr;
	}

	@Override
	public PostDto getPostbyId(Integer postId) {
		
		Post post=this.pro.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Posts", "postId", postId));
		
		PostDto pdto=this.posttoDto(post);
		return pdto;
	}

	@Override
	public List<PostDto> getPostbyCategory(Integer categoryId) {
		
		//get category
		Category category=this.crpo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		
		List<Post> posts = this.pro.findByCategory(category);
		
		List<PostDto> postdtos = posts.stream().map(post->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postdtos;
		
	}
	
	

	@Override
	public List<PostDto> getPostbyUser(Integer userId) {
		
		//get user
		User user=this.userrepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		
		List<Post> posts = this.pro.findByUser(user);
		
		List<PostDto> postdtos = posts.stream().map(post->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		return postdtos;
	}

	
	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.pro.searchbytitle("%"+ keyword+ "%");
		List<PostDto> pdtos = posts.stream().map((post)->this.posttoDto(post)).collect(Collectors.toList());
		
		
		return pdtos;
	}
	
	
	
	
	
	
	
	
	
	private Post dtotoPost(PostDto pdto)
	{
		Post post=this.modelmapper.map(pdto, Post.class);
		
		return post;
	}
	
	
	
	private PostDto posttoDto(Post post)
	{
		PostDto pdto=this.modelmapper.map(post, PostDto.class);
		return pdto;
	}
	
	
	
	

}
