package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	@Autowired
	private UserService userservice;

	
	//create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto)
	{
		
		UserDto createuserdto=this.userservice.createuser(userdto);
		return new ResponseEntity<UserDto>(createuserdto,HttpStatus.CREATED);
	}
	
	
	//update user
	
	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto,@PathVariable Integer userid)
	{
		UserDto updateduserdto=this.userservice.updateuser(userdto, userid);
		return new ResponseEntity<UserDto>(updateduserdto,HttpStatus.OK);
	}
	
	
	
	//Delete user
	//only for ADMIN
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<ApiResponse> deleteuser( @PathVariable Integer userid)
	{
		this.userservice.deleteuser(userid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfullly", true),HttpStatus.OK);
	}
	
	
	
	// get all users
	
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getallusers()
	{
		
		return ResponseEntity.ok(this.userservice.getall());
		
	}
	
	
	
	@GetMapping("/{userid}")
	
	
	public ResponseEntity<UserDto> getuserbyid( @PathVariable Integer userid)
	{
		
		return ResponseEntity.ok(this.userservice.getbyID(userid));
	}
	
	
	
	
}
