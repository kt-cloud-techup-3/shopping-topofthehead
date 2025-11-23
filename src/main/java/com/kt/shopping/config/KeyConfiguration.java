package com.kt.shopping.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.kt.shopping.security.PojoJwtProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KeyConfiguration {
	private final PojoJwtProperties pojoJwtProperties;
	@Bean
	public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		// RSA 알고리즘을 사용하는 KeyPairGenerator객체 생성 및 Key 크기 : 2048bit 설정
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		return  keyPairGenerator.generateKeyPair();
	}
	@Bean
	// Spring Bean에 등록된 KeyPair을 매개변수로 의존성주입
	public RSAKey generateRSAKey(KeyPair keyPair){
		String keyId = pojoJwtProperties.getJwtProperties().keyId();
		return new RSAKey
			// Public Key객체를 RSAPublicKey로 casting하여 설정.
			.Builder((RSAPublicKey)keyPair.getPublic())
			// Private Key 객체를 RSAPrivateKey로 casting하여 설정.
			.privateKey((RSAPrivateKey)keyPair.getPrivate())
			// application.yml에 설정한 Key ID를 가져오기
			.keyID(keyId)
			.build();
	}
	@Bean
	// Spring Bean에 등록된 RSAKey를 매개변수로 의존성주입
	public JWKSource generateJWKSource(RSAKey rsaKey){
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (((jwkSelector, securityContext) -> jwkSelector.select(jwkSet)));
	}
	@Bean
	// Spring Bean에 등록된 RSAKey를 매개변수로 의존성주입
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
	}
	@Bean
	// Spring Bean에 등록된 JWKSource<SecurityContext>를 매개변수로 의존성주입
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) throws JOSEException {
		return new NimbusJwtEncoder(jwkSource);
	}
}
