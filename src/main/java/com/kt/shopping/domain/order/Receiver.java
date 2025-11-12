package com.kt.shopping.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Receiver {
	// order 테이블의 각 field로 매핑
	@Column(name = "receiver_name")
	private String name;
	@Column(name = "receiver_address")
	private String address;
	@Column(name = "receiver_mobile")
	private String mobile;
}
