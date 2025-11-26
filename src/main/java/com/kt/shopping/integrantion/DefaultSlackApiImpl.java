package com.kt.shopping.integrantion;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kt.shopping.common.profile.AppProfile;
import com.slack.api.methods.MethodsClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 남기기 용도
@Component
@RequiredArgsConstructor
@AppProfile
public class DefaultSlackApiImpl implements SlackApi {
	private final MethodsClient methodsClient;
	private final SlackPropertiesPojo slackPropertiesPojo;
	private final Environment environment;
	@Override
	public void notify(String message){
		try{
			// 슬랙으로 전송
			methodsClient.chatPostMessage(request->{
				request.username("spring-bot")
					.channel(slackPropertiesPojo.getSlackProperties().logChannel())
					// Slack에서 ```문자열```은 코드블럭으로 설정됨
					.text(String.format("```%s - shopping-%s```", message,getActiveProfile()))
					.build();
				return request;
			});
		} catch (Exception e){
			log.error(e.getMessage());
		}
	}
	private String getActiveProfile(){
		// 현재 Active Profile을 가져오고 없으면 default로 "local"을 가져옴
		return Arrays.stream(environment.getActiveProfiles()).findFirst().orElse("local");
	}
}
