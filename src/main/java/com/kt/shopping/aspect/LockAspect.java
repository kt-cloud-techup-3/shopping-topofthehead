package com.kt.shopping.aspect;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.common.Lock;
import com.kt.shopping.common.PreValidCondition;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LockAspect {
	private final AopTransactionManager aopTransactionManager;
	private final RedissonClient redissonClient;
	// pointcut을 커스텀어노테이션을 사용하도록 설정
	// 해당 커스텀어노테이션이 선언된 메서드를 자동으로 인터셉트하여 AOP를 수행
	@Around("@annotation(com.kt.shopping.common.Lock) && @annotation(lock)")
	// 매개변수에 커스텀어노테이션을 선언하여 어노테이션 내부 필드값을 가져옴
	public Object lock(ProceedingJoinPoint joinPoint , Lock lock) throws Throwable {
		Long productId = (Long)joinPoint.getArgs()[1];
		// 커스텀어노테이션에서 설정된 필드값으로 동적으로 Lock의 키이름을 설정
		RLock rLock = redissonClient.getLock("%s:%d".formatted(lock.key(),productId));
		try{
			// 커스텀어노테이션에서 설정된 필드값으로 동적으로 설정하여 tryLock()을 통해 락을 획득
			Boolean available = rLock.tryLock(lock.waitTime(), lock.leaseTime(), lock.timeunit());
			if ( !available ) return false;
			return aopTransactionManager.proceed(joinPoint);
		} finally {
			if(rLock.isHeldByCurrentThread()) rLock.unlock();
		}
	}
}
