package com.kt.shopping.controller.order;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.common.response.ApiResult;
import com.kt.shopping.common.Paging;
import com.kt.shopping.common.SwaggerSupporter;
import com.kt.shopping.dto.order.OrderResponse;
import com.kt.shopping.service.order.OrderService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자용 주문", description = "관리자 권한으로 주문을 관리합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController extends SwaggerSupporter {
	private final OrderService orderService;

	@GetMapping
	public ApiResult<Page<OrderResponse.Search>> search(
		// Controller로 전달되는 page, size를 해당 객체로 Mapping
		@Parameter(hidden = false) Paging paging,
		@RequestParam(required = false) String keyword
	){
		Page<OrderResponse.Search> page = orderService.search(paging.toPageable(),keyword);
		return ApiResult.ok(page);
	}
}
