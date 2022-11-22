package com.blog.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.dao.UserRepo;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.User;

@Service
public class CustomUserDetailService implements UserDetailsService {

	
	@Autowired
	private UserRepo uro;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		
		User user = this.uro.findByEmail(username)
				.orElseThrow(()->new ResourceNotFoundException("user", "email" + username, 0));
		
		System.out.println(username);
		
		
		return user;
	}

}
