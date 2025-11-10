package com.kt.shopping.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.shopping.domain.product.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
