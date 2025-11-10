package com.kt.shopping.domain.orderproduct;

import com.kt.shopping.common.BaseEntity;
import com.kt.shopping.domain.order.OrderEntity;
import com.kt.shopping.domain.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name="OrderProduct")
public class OrderProductEntity extends BaseEntity {
	private Long quantity;
	// Mapping Table이므로 양쪽 Table을 1:1로 참조하는 field를 갖는다.
	@OneToOne
	private OrderEntity order;
	@OneToOne
	private ProductEntity product;
}
