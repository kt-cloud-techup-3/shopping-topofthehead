package com.kt.shopping.service.product;

import java.util.UUID;


public interface ProductService {
	void create(String name, Long price, Long quantity);
	void update(Long id, String name, Long price, Long quantity);
	void soldOut(Long id);
	void inActivate(Long id);
	void Activate(Long id);
	void delete(Long id);
	void decreaseStock(Long id, Long quantity);
	void increaseStock(Long id, Long quantity);
}
