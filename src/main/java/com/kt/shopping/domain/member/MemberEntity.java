package com.kt.shopping.domain.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Enumerated;

import com.kt.shopping.common.BaseEntity;
import com.kt.shopping.domain.order.OrderEntity;
import com.kt.shopping.dto.MemberUpdateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

	@Getter
	@NoArgsConstructor
	@Entity
	@Table(name="Member")
	// 도메인 객체와 DB Entity 역할을 동시에 수행
	public class MemberEntity extends BaseEntity {
		@Column(name = "LOGINID")
		private String loginId;
		private String password;
		private String name;
		private String email;
		private String mobile;
		@Enumerated(EnumType.STRING)
		private Gender gender;
		private LocalDate birthday;

		@OneToMany(mappedBy="member")
		private List<OrderEntity> orders;

	// Entity의 @Id Field는 @GeneratedValue에 의해 자동할당되므로 생성자로 할당하지않고 Null로 설정
	public MemberEntity(String loginId, String password, String name, String email, String mobile, Gender gender,
		LocalDate birthday, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.birthday = birthday;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public void updatePassword(String password){
		this.password = password;
		updatedAt = LocalDateTime.now();
	}
	public void updateContent(MemberUpdateRequest member){
			this.name = member.name();
			this.email = member.email();
			this.mobile = member.mobile();
			this.gender =  member.gender();
			this.birthday = member.birthday();
	}
}
