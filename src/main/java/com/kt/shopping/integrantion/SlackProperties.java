package com.kt.shopping.integrantion;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("slack")
public record SlackProperties(
	String botToken,
	String logChannel
) {
}
