package com.blog.controllers;


import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.APIException;
import com.blog.payloads.JWTAuthRequest;
import com.blog.payloads.JWTAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JWTTokenHelper;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	
	@Autowired
	private JWTTokenHelper helper;
	
	

	@Autowired
	private UserDetailsService uds;
	
	
	@Autowired
	private UserService us;
	
	
	@Autowired
	private AuthenticationManager authManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception 
	{
		this.authenticate(request.getUserName(),request.getPassword());

		UserDetails userdetails = this.uds.loadUserByUsername(request.getUserName());
		
		String token=this.helper.generateToken(userdetails);
		
		JWTAuthResponse response=new JWTAuthResponse();
		
		response.setToken(token);
		
		
		return new ResponseEntity<JWTAuthResponse>(response,HttpStatus.OK);
		
		
	}


	private void authenticate(String userName, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(userName, password);
		
			
		
		
		try
		{
			
		
		this.authManager.authenticate(upat);
		}
		catch(BadCredentialsException e)
		{
			System.out.println("Invalid details");
			
			throw new APIException("Invalid Username or Password");
		}
		
		
		
		
		
	}
	
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto>registernewUser(@RequestBody UserDto udto)
	{
		UserDto registernewUser = this.us.registernewUser(udto);
		
		return new ResponseEntity<UserDto>(registernewUser,HttpStatus.CREATED);
		
		
	}
	
	

}
