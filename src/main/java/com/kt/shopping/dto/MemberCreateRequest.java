package com.kt.shopping.dto;

import java.time.LocalDate;

import com.kt.shopping.domain.Gender;

public record MemberCreateRequest(
	String loginId,
	String password,
	String name,
	String email,
	String mobile,
	Gender gender,
	LocalDate birthday
) { }
