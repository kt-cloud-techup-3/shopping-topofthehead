package com.kt.shopping.controller;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.dto.UserCreateRequest;
import com.kt.shopping.service.UserService;

@RestController
public class UserController {
	private final UserService userservice;
	public UserController(UserService userservice){
		this.userservice = userservice;
	}
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody UserCreateRequest request) {
		// 코드
		userservice.create(request);
	}
}
