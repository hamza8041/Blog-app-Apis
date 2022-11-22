package com.blog.services;



import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {

	UserDto registernewUser(UserDto udto);
	
UserDto createuser(UserDto user);

UserDto updateuser(UserDto user, Integer userId);

UserDto getbyID(Integer userId);

List<UserDto> getall();

void deleteuser(Integer userId);



}
