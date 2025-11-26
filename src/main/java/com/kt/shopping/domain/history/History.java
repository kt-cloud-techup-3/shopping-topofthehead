package com.kt.shopping.domain.history;

import com.kt.shopping.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class History extends BaseEntity {
	@Enumerated(value = EnumType.STRING)
	private HistoryType type;
	private String content;
	private Long userId;
	public History(HistoryType type, String content, Long userId) {
		this.type = type;
		this.content = content;
		this.userId = userId;
	}
}
