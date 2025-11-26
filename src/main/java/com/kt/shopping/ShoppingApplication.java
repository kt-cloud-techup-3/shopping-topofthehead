package com.kt.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

import com.kt.shopping.common.MessageEvent;
import com.kt.shopping.integrantion.SlackApi;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
@EnableAsync
public class ShoppingApplication {
	private final ApplicationEventPublisher publisher;
	@EventListener(ApplicationReadyEvent.class)
	public void started(){
		publisher.publishEvent(new MessageEvent("Shopping Application Started"));
	}
	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}
}
