package com.kt.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kt.shopping.integrantion.SlackPropertiesPojo;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SlackConfiguration {
	private final SlackPropertiesPojo slackProperties;
	@Bean
	public MethodsClient methodsClient() {
		return Slack.getInstance().methods(slackProperties.getSlackProperties().botToken());
	}
}
