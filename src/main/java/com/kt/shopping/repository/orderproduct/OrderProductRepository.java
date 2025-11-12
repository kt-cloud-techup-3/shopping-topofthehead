package com.kt.shopping.repository.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.shopping.domain.orderproduct.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
}
