package com.blog.payloads;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class JWTAuthRequest {
	
	
	private String userName;
	
	private String password;

}
