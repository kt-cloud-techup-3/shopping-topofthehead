package com.kt.shopping.domain.payment;

import com.kt.shopping.common.BaseEntity;
import com.kt.shopping.domain.order.OrderEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Payment extends BaseEntity {
	private Long totalPrice;
	private Long deliveryFee;
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@OneToOne
	private OrderEntity order;
}
