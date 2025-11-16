package com.kt.shopping.repository.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.domain.product.ProductEntity;

// QueryDSL은 @DataJpaTest에서 Spring Bean으로 주입되지않으므로
// QueryDSL을 포함하는 경우 @SpringBootTest 선언
@SpringBootTest
// 인메모리DB를 사용하지 않는 경우 선언하는 어노테이션
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProductRepositoryTest {
	// Spring Context로부터 JpaRepository에 관련한 Spring Bean을 주입
	@Autowired
	ProductRepository productRepository;
	// 각 테스트 메서드 실행 전
	@BeforeEach
	void setUp(){
		// 준비
		productRepository.save(
			ProductEntity.of(
				"테스트케이스",
				2000L,
				3L
			)
		);
	}
	@Test
	void 이름으로_검색(){
		// 실행 : 보통 DB에서 찾은 객체는 founded라는 키워드를 붙임
		Optional<ProductEntity> foundedProduct = productRepository.findByName("테스트케이스");
		// 검증
		assertThat(foundedProduct).isNotEmpty();
	}
}