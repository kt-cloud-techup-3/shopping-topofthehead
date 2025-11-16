package com.kt.shopping.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME) // 런타임에서 어노테이션을 실행
@Target(ElementType.METHOD) // 메서드에 적용
public @interface Lock {
	Key key();
	long waitTime() default 2000L;
	long leaseTime() default 5000L;
	TimeUnit timeunit() default TimeUnit.MILLISECONDS;
	// key 이름을 상수로하여 규격화
	enum Key {
		STOCK
	}
}
