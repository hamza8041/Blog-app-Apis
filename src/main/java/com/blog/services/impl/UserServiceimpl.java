package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.dao.RoleRepo;
import com.blog.dao.UserRepo;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.Role;
import com.blog.models.User;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;


@Service
public class UserServiceimpl implements UserService {

	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private RoleRepo rolerepo;
	
	
	@Override
	public UserDto createuser(UserDto userdto) {
		
		User user=this.dtotoUser(userdto);
		
		User saveduser=this.userrepo.save(user);
		
		return this.usertodto(saveduser);
		
		
	}

	@Override
	public UserDto updateuser(UserDto userdto, Integer userId) {
		
		
		User user=this.userrepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(user.getPassword());
		user.setAbout(userdto.getAbout());
		
		User updateduser=this.userrepo.save(user);
		
		UserDto upduserdto=this.usertodto(updateduser);
		
		
		return upduserdto;
	}

	@Override
	public UserDto getbyID(Integer userId) {
		
		User user=this.userrepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		UserDto userdto=this.usertodto(user);
		
		return userdto;
	}

	@Override
	public List<UserDto> getall() {
		
		List<User> users=this.userrepo.findAll();
		
		List<UserDto> userdtos = users.stream().map(user->this.usertodto(user)).collect(Collectors.toList());
		
		
		
		return userdtos;
	}

	@Override
	public void deleteuser(Integer userId) {
		
		
		this.userrepo.deleteById(userId);
		
	}
	
	
	
	@Override
	public UserDto registernewUser(UserDto udto) {
		User user = this.dtotoUser(udto);
	user.setPassword(udto.getPassword());
		
		
		///roles
		
		Role role = this.rolerepo.findById(AppConstants.NORMAL_USER).get();
		
		
		user.getRoles().add(role);
		
		User newuser = this.userrepo.save(user);
		return this.usertodto(newuser);
		
	}
	
	
	
	private User dtotoUser(UserDto userDto)
	{
		User user=this.modelmapper.map(userDto, User.class);
		
	
		
		
		return user;
		
		
		
	}
	
	
	
	private UserDto usertodto(User user)
	
	{
		UserDto userdto=this.modelmapper.map(user, UserDto.class);
		
		return userdto;
		
		
	}

	
	
	
	
	
	

}
