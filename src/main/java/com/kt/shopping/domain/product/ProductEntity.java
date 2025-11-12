package com.kt.shopping.domain.product;

import java.util.ArrayList;
import java.util.List;

import com.kt.shopping.common.BaseEntity;
import com.kt.shopping.common.PreValidCondition;
import com.kt.shopping.domain.orderproduct.OrderProductEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name= "Product")
public class ProductEntity extends BaseEntity {
	private String name;
	private Long price;
	private Long stock;
	@Enumerated(EnumType.STRING)
	// 상품 초기설정
	private ProductStatus status = ProductStatus.ACTIVATIVED;
	@OneToMany(mappedBy="product")
	private List<OrderProductEntity> orderProductEntities = new ArrayList<>();
	public ProductEntity(String name, Long price, Long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	// Entity 정보 수정하는 setter -> 추후 DirtyChecking에 의해 변경사항 감지 후
	// DB Table에 반영
	public void update(String name, Long price, Long stock){
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	// 상태변경 : 품절
	public void soldOut(){
		this.status = ProductStatus.SOLD_OUT;
	}
	// 상태변경 : 판매중지
	public void inActivate(){
		this.status = ProductStatus.IN_ACTIVATED;
	}
	// 상태변경 : 판매중
	public void Acivate(){
		this.status = ProductStatus.ACTIVATIVED;
	}
	// 상태변경 : 삭제
	// 논리적으로 삭제된 상태로 지정하며 실제 DB Entity는 삭제되지 않음.
	public void delete(){
		this.status = ProductStatus.DELETED;
	}
	// 재고수량 변경
	public void decreaseStock(Long quantity){
		this.stock -= quantity;
	}
	public void increaseStock(Long quantity){
		this.stock += quantity;
	}
	public boolean canProvide(Long quantity){
		return stock - quantity > 0;
	}
	// OrderProduct 생성 시 수작업으로 양방향 관계에 있는 Entity의
	// @OneToMany가 선언된 List 필드에 추가해야한다.
	public void mapToOrderProduct(OrderProductEntity orderProductEntity){
		orderProductEntities.add(orderProductEntity);
	}
}
