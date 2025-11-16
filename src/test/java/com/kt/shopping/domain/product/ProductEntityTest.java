package com.kt.shopping.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kt.shopping.common.CustomException;

class ProductEntityTest {

	@Test
	void 객체생성_성공(){
		ProductEntity productEntity = ProductEntity.of(
			"테스트케이스1",
			2000L,
			3L
		);
		assertThat(productEntity.getName()).isEqualTo("테스트케이스1");
		assertThat(productEntity.getPrice()).isEqualTo(2000L);
		assertThat(productEntity.getStock()).isEqualTo(3L);
	}
	@ParameterizedTest
	@NullAndEmptySource
	void 객체생성_실패__name_Null_이거나_Empty(String name){
		assertThrowsExactly(
			CustomException.class,
			() -> ProductEntity.of(
				name,
				2000L,
				3L)
		);
	}

	@ParameterizedTest
	@ValueSource(longs = { -1L })
	void 객체생성_실패__price_음수값입력(Long price){
		assertThrowsExactly(
			CustomException.class,
			() -> ProductEntity.of(
				"테스트케이스",
				price,
				3L
			)
		);
	}


	@Test
	void 업데이트_성공() {

	}

	@Test
	void soldOut() {
	}

	@Test
	void inActivate() {
	}

	@Test
	void acivate() {
	}

	@Test
	void delete() {
	}

	@Test
	void decreaseStock() {
	}

	@Test
	void increaseStock() {
	}

	@Test
	void canProvide() {
	}
}