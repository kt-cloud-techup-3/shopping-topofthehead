package com.kt.shopping.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.domain.product.ProductEntity;
import com.kt.shopping.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	@Override
	public void create(String name, Long price, Long quantity) {
		productRepository.save(
			ProductEntity.of(name, price, quantity)
		);
	}
	@Override
	public void update(Long id, String name, Long price, Long quantity){
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.update(name, price, quantity);
	}
	// 상태변경
	@Override
	public void soldOut(Long id){
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.soldOut();
	}
	@Override
	public void inActivate(Long id){
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.inActivate();
	}
	@Override
	public void Activate(Long id) {
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.Acivate();
	}
	@Override
	public void delete(Long id) {
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.delete();
	}
	// 재고변경
	@Override
	public void decreaseStock(Long id, Long quantity) {
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.decreaseStock(quantity);
	}
	@Override
	public void increaseStock(Long id, Long quantity) {
		var product = productRepository.findByIdOrThrowNotFound(id);
		product.increaseStock(quantity);
	}
}
