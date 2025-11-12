package com.kt.shopping.domain.orderproduct;

import com.kt.shopping.common.BaseEntity;
import com.kt.shopping.domain.order.OrderEntity;
import com.kt.shopping.domain.product.ProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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
	private OrderProductEntity(Long quantity, OrderEntity order, ProductEntity product) {
		this.quantity = quantity;
		this.order = order;
		this.product = product;
	}
	public static OrderProductEntity of(Long quantity, OrderEntity order, ProductEntity product){
		OrderProductEntity orderProduct = new OrderProductEntity(quantity,order,product);
		// OrderProduct 생성 시 각각 부모Entity인 ProductEntity , OrderEntity에 반영
		order.mapToOrderProduct(orderProduct);
		product.mapToOrderProduct(orderProduct);
		return orderProduct;
	}
}
