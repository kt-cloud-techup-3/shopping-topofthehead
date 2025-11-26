package com.kt.shopping.integrantion.eventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kt.shopping.common.MessageEvent;
import com.kt.shopping.integrantion.SlackApi;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {
	private final SlackApi slackApi;
	// ApplicationEventPublisher에 의해 MessageEvent 클래스로 이벤트 발행 시 수신하여 해당 메서드 실행
	// 매개변수로 수신한 클래스객체를 전달
	@EventListener(MessageEvent.class)
	public void onMessage(MessageEvent messageEvent){
		slackApi.notify(messageEvent.message());
	}
}
