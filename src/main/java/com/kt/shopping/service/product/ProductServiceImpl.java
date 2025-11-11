package com.kt.shopping.service.product;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.common.CustomException;
import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.domain.product.ProductEntity;
import com.kt.shopping.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	public void create(String name, Long price, Long quantity) {
		productRepository.save(
			new ProductEntity(name, price, quantity)
		);
	}
	public void update(Long id, String name, Long price, Long quantity){
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.update(name, price, quantity);
	}
	// 상태변경
	public void soldOut(Long id){
		var product = productRepository.findById(id)
			.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.soldOut();
	}

	public void inActivate(Long id){
		var product = productRepository.findById(id)
			.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.inActivate();
	}
	public void Activate(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.Acivate();
	}
	public void delete(Long id) {
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.delete();
	}
	// 재고변경
	public void decreaseStock(Long id, Long quantity) {
		var product = productRepository.findById(id)
			.orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
		product.decreaseStock(quantity);
	}
	public void increaseStock(Long id, Long quantity) {
		var product = productRepository.findById(id)
			.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.increaseStock(quantity);
	}
}
