package com.kt.shopping.config;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kt.shopping.security.JwtFilter;
import com.kt.shopping.security.PojoJwtProperties;
import com.nimbusds.jose.jwk.RSAKey;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	// 커스텀한 필터구현체를 의존성주입
	private final JwtFilter jwtFilter;
	// 메서드를 허용할 API 엔드포인트 지정
	private static final String[] GET_PERMIT_ALL = {"/api/health/**", "/swagger-ui/**", "/v3/api-docs/**"};
	private static final String[] POST_PERMIT_ALL = {"/members", "/auth/login"};
	private static final String[] PUT_PERMIT_ALL = {"/api/v1/public/**"};
	private static final String[] PATCH_PERMIT_ALL = {"/api/v1/public/**"};
	private static final String[] DELETE_PERMIT_ALL = {"/api/v1/public/**"};
	// BCrypt hashing 알고리즘을 사용하는 패스워드 인코더를
	// Spring Bean으로 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// AuthentificationManager를 Spring Bean으로 등록
	// 사용자가 전달한 인증방식에 맞는 검증방식의 AutheticationProvider를
	// 식별하여 검증을 수행
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
		throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	// @EnableWebSecurity를 통해 사용할 Spring Security Filter Chain 설정
	// JWT 사용 기준
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 사용하지 않도록 설정
			.authorizeHttpRequests(
				request -> { // 요청에서 허용할 메서드 당 API 엔드포인트 범위 지정
					// "/api/v1/public/**" URL로 요청하는 HTTP GET REQUEST를 모두 허용
					request.requestMatchers(HttpMethod.GET, GET_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.POST, POST_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.PATCH, PATCH_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.PUT, PUT_PERMIT_ALL).permitAll();
					request.requestMatchers(HttpMethod.DELETE, DELETE_PERMIT_ALL).permitAll();
					// 위 설정범위에 들어가지 않는 메서드에 대해서는 인증이 필요하도록 설정
					request.anyRequest().authenticated();
				}
			)
			.csrf(AbstractHttpConfigurer::disable)
			// 커스텀한 JWT 검증용도의 Filter를 Spring Security Filter Chain에 포함
			// UsernamePasswordAuthenticationFilter가 동작하기 전에 작동할 필터로 설정
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}