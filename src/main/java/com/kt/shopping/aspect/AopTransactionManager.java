package com.kt.shopping.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

public interface AopTransactionManager {
	Object proceed(ProceedingJoinPoint joinPoint) throws Throwable;
}
