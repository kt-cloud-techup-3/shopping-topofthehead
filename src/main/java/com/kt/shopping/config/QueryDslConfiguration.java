package com.kt.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class QueryDslConfiguration {
	//。내부에 `JPA`의 `EntityManager 객체`을 `field`로 선언 및
	// `@PersistenceContext`를 선언하여 `Spring Context`에서 주입
	@PersistenceContext
	private EntityManager em;
	//주입된 `EntityManager`을 기반으로 `@Bean Method`를 통해 `JPAQueryFactory 객체`로 생성하여
	// `Spring Bean`으로서 `Spring Context`에 등록
	@Bean
	public JPAQueryFactory jpaQueryFactory(){
		return new JPAQueryFactory(em);
	}
}
