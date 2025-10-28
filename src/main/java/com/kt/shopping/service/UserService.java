package com.kt.shopping.service;

import org.springframework.stereotype.Service;

import com.kt.shopping.dto.UserCreateRequest;
import com.kt.shopping.domain.User;
import com.kt.shopping.repository.UserCreateRepository;

@Service
public class UserService {
	private final UserCreateRepository userCreateRepository;
	public UserService(UserCreateRepository userCreateRepository) {
		this.userCreateRepository = userCreateRepository;
	}
	public void create(UserCreateRequest request){

		User u1 = new User(
			request.loginId(),
			request.password(),
			request.name(),
			request.birthday()
		);
		userCreateRepository.save(u1);
	}
}
