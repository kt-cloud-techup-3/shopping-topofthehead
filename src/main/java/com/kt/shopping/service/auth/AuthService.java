package com.kt.shopping.service.auth;

import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.common.CustomException;
import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.repository.member.MemberRepository;
import com.kt.shopping.security.JwtService;
import com.kt.shopping.security.PojoJwtProperties;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final PojoJwtProperties pojoJwtProperties;
	public Pair<String, String> login(String loginId, String password) {
		// Login ID로 기존 DB에 저장된 계정의 Hashin 된 PW를 조회
		// Null 체크는 해당 Repository 인터페이스의 default 메서드에서  검증
		MemberEntity member = memberRepository.findByLoginIdOrThrow(loginId);
		String foundedHashedMemberPassword = member.getPassword();
		// 입력된 raw password와 DB의 hash password를 비교검증
		if (!passwordEncoder.matches(password,foundedHashedMemberPassword))
			throw new CustomException(ErrorCode.FAIL_LOGIN);
		// 비교검증 성공 시 각각 만료시간을 전달하여 Access Token과 Refresh Token을 생성하여 발급 후 Pair로 반환
		String accessToken = jwtService.issue(member.getId(), pojoJwtProperties.getJwtProperties().accessTokenExpiration()); // 5분
		String refreshToken = jwtService.issue(member.getId(), pojoJwtProperties.getJwtProperties().refreshTokenExpiration()); // 12시간
		return Pair.of(accessToken,refreshToken);
	}
}
