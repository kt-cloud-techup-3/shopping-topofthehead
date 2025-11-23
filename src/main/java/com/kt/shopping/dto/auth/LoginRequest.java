package com.kt.shopping.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {
	public record Login(
		@NotBlank String loginId,
		@NotBlank String password
	){
	}
}
