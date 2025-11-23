package com.kt.shopping.dto.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class LoginResponse {
	public record Login(
		String accessToken,
		String refreshToken
	){}
}
