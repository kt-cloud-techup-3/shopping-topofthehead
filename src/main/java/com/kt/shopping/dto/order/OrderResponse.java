package com.kt.shopping.dto.order;

import java.time.LocalDateTime;

import com.kt.shopping.domain.order.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface OrderResponse {
	@Schema(name = "OrderResponse.Search")
	record Search(
		Long id,
		String receiverName,
		String productName,
		Long quantity,
		Long totalPrice,
		OrderStatus status,
		LocalDateTime createdAt
	) {
		@QueryProjection
		public Search {
		}
	}
}
