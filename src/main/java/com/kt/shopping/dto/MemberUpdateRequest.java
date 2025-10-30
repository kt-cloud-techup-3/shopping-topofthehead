package com.kt.shopping.dto;

import java.time.LocalDate;

import com.kt.shopping.domain.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MemberUpdateRequest(
	@NotBlank
	String name,
	@NotBlank
	@Pattern(regexp="^[0-9A-Za-z._%+-]+@[0-9A-Za-z.-]+\\.[A-Za-z]{2,6}$")
	String email,
	@NotBlank
	@Pattern(regexp="^(0\\d{1,2})-(\\d{3,4})-(\\d{4})$")
	String mobile,
	@NotNull
	Gender gender,
	@NotNull
	LocalDate birthday
) { }