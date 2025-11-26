package com.kt.shopping.service.product;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.common.MessageEvent;
import com.kt.shopping.domain.product.ProductEntity;
import com.kt.shopping.integrantion.SlackApi;
import com.kt.shopping.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final ApplicationEventPublisher applicationEventPublisher;
	@Override
	public void create(String name, Long price, Long quantity) {
		productRepository.save(
			ProductEntity.of(name, price, quantity)
		);
		applicationEventPublisher.publishEvent(
			new MessageEvent("명칭 : %s의 상품이 생성".formatted(name))
		);
	}
	@Override
	public void update(Long id, String name, Long price, Long quantity){
		var product = productRepository.findByIdOrThrow(id);
		product.update(name, price, quantity);
	}
	// 상태변경
	@Override
	public void soldOut(Long id){
		var product = productRepository.findByIdOrThrow(id);
		product.soldOut();
	}
	@Override
	public void inActivate(Long id){
		var product = productRepository.findByIdOrThrow(id);
		product.inActivate();
	}
	@Override
	public void Activate(Long id) {
		var product = productRepository.findByIdOrThrow(id);
		product.Acivate();
	}
	@Override
	public void delete(Long id) {
		var product = productRepository.findByIdOrThrow(id);
		product.delete();
	}
	// 재고변경
	@Override
	public void decreaseStock(Long id, Long quantity) {
		var product = productRepository.findByIdOrThrow(id);
		product.decreaseStock(quantity);
	}
	@Override
	public void increaseStock(Long id, Long quantity) {
		var product = productRepository.findByIdOrThrow(id);
		product.increaseStock(quantity);
	}
}
