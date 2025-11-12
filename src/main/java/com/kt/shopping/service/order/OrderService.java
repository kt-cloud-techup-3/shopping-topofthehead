package com.kt.shopping.service.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kt.shopping.domain.order.OrderEntity;
import com.kt.shopping.common.Paging;
import com.kt.shopping.dto.order.OrderResponse;

public interface OrderService {
	void create(
		Long userId,
		Long prodId,
		String receiverName,
		String receiverAddress,
		String receiverMobile,
		Long quantity
	);
	Page<OrderResponse.Search> search(
		Pageable pageable,
		String keyword
	);
}
