package com.kt.shopping.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kt.shopping.domain.order.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> , OrderRepositoryCustom{
}
