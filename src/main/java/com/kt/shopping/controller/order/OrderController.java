package com.kt.shopping.controller.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.common.ApiResult;
import com.kt.shopping.common.SwaggerSupporter;
import com.kt.shopping.dto.order.OrderRequest;
import com.kt.shopping.security.DefaultCurrentUser;
import com.kt.shopping.service.order.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "주문" , description = "주문을 관리합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController extends SwaggerSupporter {
	private final OrderService orderService;

	@PostMapping
	public ApiResult<Void> create(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser, // Spring Security 를 통해 획득
		@RequestBody @Valid OrderRequest.Create request
	){
		orderService.create(
			defaultCurrentUser.getId(), //
			request.prodId(),
			request.receiverName(),
			request.receiverAddress(),
			request.receiverMobile(),
			request.quantity()
			);
		return ApiResult.ok();
	}
}
