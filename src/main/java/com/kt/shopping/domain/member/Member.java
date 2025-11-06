package com.kt.shopping.domain.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

// 1. domain과 JPA의 entity를 분리
// 2. 같이 공유


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
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
