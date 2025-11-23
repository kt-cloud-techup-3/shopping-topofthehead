package com.kt.shopping.security;

import java.time.Instant;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String keyId, Long accessTokenExpiration, Long refreshTokenExpiration) {
}
