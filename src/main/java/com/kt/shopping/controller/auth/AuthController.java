package com.kt.shopping.controller.auth;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.aspect.ServiceLogger;
import com.kt.shopping.common.response.ApiResult;
import com.kt.shopping.domain.history.HistoryType;
import com.kt.shopping.dto.auth.LoginRequest;
import com.kt.shopping.dto.auth.LoginResponse;
import com.kt.shopping.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	@ServiceLogger(type = HistoryType.LOGIN, content = "사용자 로그인")
	@PostMapping("/login")
	public ApiResult<LoginResponse.Login> login(@Valid @RequestBody LoginRequest.Login request){
		Pair<String,String> tokenPair = authService.login(request.loginId(), request.password());
		// DTO에 AccessToken 과 RefreshToken을 포함
		LoginResponse.Login responseDto = new LoginResponse.Login(tokenPair.getFirst(), tokenPair.getSecond());
		return ApiResult.ok(responseDto);
	}
}
