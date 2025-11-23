package com.kt.shopping.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

// OncePerRequestFilter를 상속하여 어플리케이션 내 한번만 검증을 수행
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	// JWT 토큰 접두사
	private final String TOKEN_PREFIX = "Bearer ";
	private final JwtService jwtService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		// Request의 Authorization Header 가져오기
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		// Request에 Authorization Header가 미설정된 경우 JWT 검증을 수행하지않고 다음 필터로 전달
		if (authorizationHeader == null) {
			filterChain.doFilter(request, response);
			return; // 종료
		}
		// JWT 토큰 가져오기 ( Bearer 접두사 제거 )
		String token = authorizationHeader.substring(TOKEN_PREFIX.length());
		// JwtDecoder객체.decode(토큰)를 통해 JWT 검증 수행
		// 해당 서버의 Public Key로 발급한 JWT토큰이 아닌 경우 다음 필터로 전달
		if(!jwtService.validate(token)){
			filterChain.doFilter(request, response);
			return; // 종료
		}
		// 검증이 완료된 경우 해당 JWT ID를 가져온 후 인증된 UserDetails 객체를 생성 및
		// AuthenticationToken을 전달하여 생성 후 SecurityContextHolder에 등록
		Long jwtTokenId = jwtService.parseId(token);
		AuthenticationToken authToken = new AuthenticationToken(
			new DefaultCurrentUser(jwtTokenId),
			List.of(new SimpleGrantedAuthority("ROLE_USER"))
		);
		SecurityContextHolder.getContext().setAuthentication(authToken);
		// 모든 작업이 끝난 후 다음 필터로 전달
		filterChain.doFilter(request, response);
		}
}
