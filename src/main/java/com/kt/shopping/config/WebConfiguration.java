package com.kt.shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kt.shopping.common.VisitStatInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
	// Spring Bean으로 등록한 VisitStatInterceptor을 의존성주입
	private final VisitStatInterceptor visitStatInterceptor;
	// 인터셉터 등록
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(visitStatInterceptor);
	}
}
