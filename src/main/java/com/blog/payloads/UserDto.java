package com.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import com.blog.models.Role;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
private int userId;	

@NotEmpty
@Size(min = 3,message = "Name must be more than 3 characters")
private String name;


@Email(message = "Email address is not valid")
private String email;


@NotEmpty
@Size(min = 3,max = 10,message = "Password must be between 3 to 10 characters")
private String password;


@NotEmpty
private String about;

private List<RoleDTO>roles=new ArrayList<>();


}
