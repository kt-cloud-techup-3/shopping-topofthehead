package com.kt.shopping.domain.order;

import java.time.LocalDateTime;

import com.kt.shopping.common.BaseEntity;
import com.kt.shopping.domain.member.MemberEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Orders")
public class OrderEntity extends BaseEntity {
	private String receiverName;
	private String receiverAddress;
	private String receiverMobile;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	private LocalDateTime deliveredAt;

	// 자식 Entity 이므로 외래키 Field에 @ManyToOne을 선언
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="memberId") // DB Table의 외래키 field명 설정
	private MemberEntity member;
}
