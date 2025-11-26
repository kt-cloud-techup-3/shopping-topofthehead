package com.kt.shopping.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.kt.shopping.domain.history.HistoryType;
import com.kt.shopping.security.CurrentUser;
import com.kt.shopping.service.history.HistoryService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggerAspect {
	private final HistoryService historyService;
	@Around("@annotation(com.kt.shopping.aspect.ServiceLogger) && @annotation(serviceLogger)")
	public Object serviceLogger(ProceedingJoinPoint joinPoint, ServiceLogger serviceLogger) throws Throwable {
		// 로그인 시 UserDetails 객체 가져오기
		CurrentUser principal = (CurrentUser)Arrays.stream(joinPoint.getArgs())
			.filter(arg -> arg instanceof CurrentUser)
			.findFirst().orElse(null);
		Long userId = principal != null ? principal.getId() : null;
		HistoryType type = serviceLogger.type();
		String content = serviceLogger.content();
		historyService.create(type, content, userId);
		return joinPoint.proceed();
	}
}
