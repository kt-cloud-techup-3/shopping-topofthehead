package com.kt.shopping.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kt.shopping.domain.history.HistoryType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLogger {
	HistoryType type();
	String content() default "";
}
