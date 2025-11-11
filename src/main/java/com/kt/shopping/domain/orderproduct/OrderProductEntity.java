package com.kt.shopping.domain.orderproduct;

import com.kt.shopping.common.BaseEntity;
import com.kt.shopping.domain.order.OrderEntity;
import com.kt.shopping.domain.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name="OrderProduct")
public class OrderProductEntity extends BaseEntity {
	private Long quantity;
	// Mapping Table이므로 양쪽 Table을 1:1로 참조하는 field를 갖는다.
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderEntity order;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;
}
