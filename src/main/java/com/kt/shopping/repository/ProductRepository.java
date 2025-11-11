package com.kt.shopping.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.shopping.common.CustomException;
import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.domain.product.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	default ProductEntity findByIdOrThrowNotFound(Long id) {
		return findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
	}
}
