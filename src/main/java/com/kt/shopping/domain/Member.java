package com.kt.shopping.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private Long id;
	// 유저로 부터 받는 부분
	private String loginId;
	private String password;
	private String name;
	private String email;
	private String mobile;
	private Gender gender;
	private LocalDate birthday;
	//
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
