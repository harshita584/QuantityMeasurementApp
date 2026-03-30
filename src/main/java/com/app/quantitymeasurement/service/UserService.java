package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.LoginDto;
import com.app.quantitymeasurement.dto.SignupDto;
import com.app.quantitymeasurement.dto.UserDto;

public interface UserService {
	public UserDto createUser(SignupDto signupDto);
	
	public UserDto getUserById(Long id);

	String login(LoginDto loginDto);
}
