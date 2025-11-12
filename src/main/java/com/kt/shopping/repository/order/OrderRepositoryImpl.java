package com.kt.shopping.repository.order;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.kt.shopping.common.Paging;
import com.kt.shopping.domain.order.QOrderEntity;
import com.kt.shopping.domain.orderproduct.QOrderProductEntity;
import com.kt.shopping.domain.product.QProductEntity;
import com.kt.shopping.dto.order.OrderResponse;
import com.kt.shopping.dto.order.QOrderResponse_Search;
import com.kt.shopping.dto.order.QOrderResponse_Search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.kt.shopping.domain.order.OrderEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{
	// QueryDslConfiguration 클래스에서 등록한 JPAQueryFactory의 스프링빈을 주입
	private final JPAQueryFactory jpaQueryFactory;
	// 빌드된 QEntity 를 import해서 Query 작성 시 사용
	private final QOrderEntity qOrder = QOrderEntity.orderEntity;
	private final QOrderProductEntity qOrderProduct = QOrderProductEntity.orderProductEntity;
	private final QProductEntity qProduct = QProductEntity.productEntity;
	@Override // 메서드 오버라이딩
	public Page<OrderResponse.Search> search(
		String keyword,
		Pageable pageable
	){
		var booleanBuilder = new BooleanBuilder()
			.and(containsProductName(keyword));
		// 검색결과
		var result = jpaQueryFactory
			.select(new QOrderResponse_Search(
				qOrder.id,
				qOrder.receiver.name,
				qProduct.name,
				qOrderProduct.quantity,
				Expressions.constant(0L),
				qOrder.orderStatus,
				qOrder.createdAt
			))
			.from(qOrder)
			.join(qOrderProduct).on(qOrderProduct.order.id.eq(qOrder.id))
			.join(qProduct).on(qOrderProduct.product.id.eq(qProduct.id))
			.where(booleanBuilder)
			.orderBy(qOrder.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		// 총 갯수
		//  이미 Entity간 연관관계가 정의되있으므로 on을 생략해도된다
		var total = jpaQueryFactory
			.select(qOrder.id)
			.from(qOrder)
			.join(qOrderProduct)
			.join(qProduct)
			.where(booleanBuilder)
			.fetch().size();
		// PageImpl : Page 구현체
		Page<OrderResponse.Search> page = new PageImpl<OrderResponse.Search>(result, pageable, total);
		return page;
	}
	public BooleanExpression containsProductName(String keyword){
		// keyword가 null이거나 ""인 경우 BooleanExpression = null 반환
		return (Strings.isNotBlank(keyword))? qProduct.name.containsIgnoreCase(keyword) : null;
	}
}
