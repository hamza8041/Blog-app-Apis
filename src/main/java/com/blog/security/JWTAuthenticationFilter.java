package com.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService uds;
	
	
	@Autowired
	private JWTTokenHelper jwttoken;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		//1. get token
		
		String requesttoken=request.getHeader("Authorization");
		
		System.out.println(requesttoken);
		
		
		String username=null;
		String token=null;
		
		if(requesttoken!=null && requesttoken.startsWith("Bearer"))
		{
			
			 token = requesttoken.substring(7);
			 
			 try
			 {
				 
			
			 username=this.jwttoken.extractUsername(token);
			 }
			 
			 catch(IllegalArgumentException e)
			 {
				 System.out.println("Unable to get jwt token");
			 }
			 catch (ExpiredJwtException e) {
				System.out.println("JWT token has expired");
			}
			 
			catch (MalformedJwtException e) {
				System.out.println("Invaid JWT");
			}
			 
			 
			 
			 
		}
		
		else
			
		{
			System.out.println("JWT Token does not begin with bearer");
		}
		
		
		//2. Validate token once we got it
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			
			UserDetails userdetails = this.uds.loadUserByUsername(username);
			if(this.jwttoken.validateToken(token, userdetails))
				
			{
				//good so far
				
				UsernamePasswordAuthenticationToken upt=new UsernamePasswordAuthenticationToken(userdetails,null, userdetails.getAuthorities());
				upt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(upt);
				
			}
			
			else
			{
				System.out.println("Invalid jwt token");
			}
		}
		
		else
			
		{
		
			System.out.println("User name is null or Context is not null");
		}
		
		
		filterChain.doFilter(request, response);
		
		
		
	}

}
