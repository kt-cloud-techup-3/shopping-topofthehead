package com.kt.shopping.controller.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.common.SwaggerSupporter;
import com.kt.shopping.service.order.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController extends SwaggerSupporter {
	private final OrderService orderService;
}
