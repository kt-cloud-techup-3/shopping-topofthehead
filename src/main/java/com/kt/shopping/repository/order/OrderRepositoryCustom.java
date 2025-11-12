package com.kt.shopping.repository.order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kt.shopping.domain.order.OrderEntity;
import com.kt.shopping.dto.order.OrderResponse;

public interface OrderRepositoryCustom {
	public Page<OrderResponse.Search> search(String keyword, Pageable pageable);
}
