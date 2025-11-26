package com.kt.shopping.integrantion;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(SlackProperties.class)
public class SlackPropertiesPojo {
	private final SlackProperties slackProperties;
}
