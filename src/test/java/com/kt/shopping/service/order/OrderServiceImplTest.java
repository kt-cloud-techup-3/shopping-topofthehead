package com.kt.shopping.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Member;
import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.domain.member.Gender;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.domain.order.Receiver;
import com.kt.shopping.domain.product.ProductEntity;
import com.kt.shopping.repository.member.MemberRepository;
import com.kt.shopping.repository.order.OrderRepository;
import com.kt.shopping.repository.orderproduct.OrderProductRepository;
import com.kt.shopping.repository.product.ProductRepository;
import com.kt.shopping.domain.order.OrderEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Transactional : 비동기 테스트에서 @Transactional 이 충돌되므로 주석처리
@ActiveProfiles("test")
class OrderServiceImplTest {
	@Autowired
	OrderService orderService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ProductRepository productRepository;
	MemberEntity member;
	ProductEntity product;
	@BeforeEach
	void setUp() throws Exception {
		// given
		product = ProductEntity.of(
			"테스트프로덕트1",
			2000L,
			100L
		);
		productRepository.save(product);
	}
	@Test
	void 동시에_100명이_주문수행() throws InterruptedException {
		List<MemberEntity> memberList = new ArrayList<MemberEntity>();
		// 100개의 MemberEntity 객체 생성
		for(int i = 0 ; i < 100 ; i++) {
			memberList.add(
				MemberEntity.normalMember(
					"로그인아이디" + i,
					"a1234",
					"테스터" + i,
					"wjdtn747@naver.com" + i,
					"010-1234-567" + i,
					Gender.MALE,
					LocalDate.of(1998, 3, 4)
				)
			);
		}
		memberRepository.saveAll(memberList);

		// 스레드 100개 생성
		var executorService = Executors.newFixedThreadPool(100);
		// 100개의 스레드가 모두 작업을 마치기 전 테스트 종료를 방지
		var countDownLatch = new CountDownLatch(100);
		// 비동기에서는 Integer이 아닌 AtomicInteger을 사용
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failureCount = new AtomicInteger(0);

		// 비동기 테스트 수행
		for(int i = 0 ; i < 100 ; i++){
			final int finalI = i;
			executorService.execute(() -> {
				try{
					// 람다식이므로 불변변수의 인덱스를 전달
					var targetMember = memberList.get(finalI);
					orderService.create(
						targetMember.getId(),
						product.getId(),
						targetMember.getName(),
						"수신자주소" + finalI,
						"010-1234-567" + finalI,
						1L
					);
					successCount.incrementAndGet(); // 성공시 1 증가
				} catch(RuntimeException e) {
					failureCount.incrementAndGet(); // 실패시 1 증가
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();
		executorService.shutdown();
		ProductEntity foundedProduct = productRepository
			.findById(product.getId())
			.orElse(null);
		System.out.println(successCount.get());
		System.out.println(failureCount.get());
		assertThat(successCount.get()).isEqualTo(100);
		assertThat(failureCount.get()).isEqualTo(0);
	}
}