package com.kt.shopping.integrantion;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kt.shopping.common.profile.LocalProfile;
import com.slack.api.methods.MethodsClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 남기기 용도
@Component
@RequiredArgsConstructor
@LocalProfile
public class LocalSlackApiImpl implements SlackApi {
	private final MethodsClient methodsClient;
	private final SlackPropertiesPojo slackPropertiesPojo;
	private final Environment environment;
	@Override
	public void notify(String message){
		log.info(message);
	}
}
