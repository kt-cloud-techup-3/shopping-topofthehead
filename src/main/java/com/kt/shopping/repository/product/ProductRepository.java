package com.kt.shopping.repository.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kt.shopping.common.exception.CustomException;
import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.domain.product.ProductEntity;

import jakarta.persistence.LockModeType;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	default ProductEntity findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
	}

	// select * from product where name = ?
	Optional<ProductEntity> findByName(String name);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT p FROM ProductEntity p WHERE p.id = :id")
	Optional<ProductEntity> findByIdPessimistic(Long id);

}
