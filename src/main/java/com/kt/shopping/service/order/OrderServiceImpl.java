package com.kt.shopping.service.order;

import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.aspect.ServiceLogger;
import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.common.Lock;
import com.kt.shopping.common.PreValidCondition;
import com.kt.shopping.domain.history.HistoryType;
import com.kt.shopping.domain.order.Receiver;
import com.kt.shopping.domain.orderproduct.OrderProductEntity;
import com.kt.shopping.dto.order.OrderResponse;
import com.kt.shopping.repository.member.MemberRepository;
import com.kt.shopping.repository.orderproduct.OrderProductRepository;
import com.kt.shopping.repository.order.OrderRepository;
import com.kt.shopping.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;
import com.kt.shopping.domain.order.OrderEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final OrderProductRepository orderProductRepository;
	@Override
	@Lock(key = Lock.Key.STOCK, waitTime = 500L, leaseTime = 700L, timeunit =  TimeUnit.MILLISECONDS)
	public void create(
		Long userId,
		Long prodId,
		String receiverName,
		String receiverAddress,
		String receiverMobile,
		Long quantity
	){
		// DB 상호작용
		var product = productRepository.findByIdOrThrow(prodId);
		// 재고 검증
		PreValidCondition.validate(
			product.canProvide(quantity)
			, ErrorCode.NOT_ENOUGH_STOCK
		);
		var member = memberRepository.findMemberByIdOrThrowNotFound(userId);
		var receiver = new Receiver(receiverName, receiverAddress, receiverMobile);
		var order = orderRepository.save(OrderEntity.of(receiver,member));
		orderProductRepository.save(OrderProductEntity.of(quantity,order,product));
		// 주문 완료 시 재고차감
		product.decreaseStock(quantity);
	}

	@Override
	public Page<OrderResponse.Search> search(Pageable pageable,String keyword){
		return orderRepository.search(keyword,pageable);
	}

}
