package com.kt.shopping.security;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;
	// JWT 토큰 발급
	public String issue(Long memberId, Long expirationMinute){
		var claims = JwtClaimsSet.builder()
			.subject("kt-cloud-shopping")
			.issuer("Topofthehead")
			.issuedAt(Instant.now())
			.id(memberId.toString())
			// 현재 시점부터 입력된 분 이후 만료
			.expiresAt(Instant.now().plusSeconds(60  * expirationMinute))
			.build();
		return jwtEncoder
			.encode(JwtEncoderParameters.from(claims))
			.getTokenValue();
	}
	// JWT 토큰 검증
	public boolean validate(String token){
		try {
			// 여기서 Signature, exp, iat 등을 모두 검증
			jwtDecoder.decode(token);
			return true;
		} catch (JwtException e) {
			// 서명 불일치, 만료, 포맷 오류 등 모든 예외
			return false;
		}
	}
	// Jwt Token Id 가져오기
	public Long parseId(String token){
		return Long.valueOf((String)jwtDecoder
			.decode(token)
			.getClaims()
			.get("jti")); // Jwt Token Id
	}
}
