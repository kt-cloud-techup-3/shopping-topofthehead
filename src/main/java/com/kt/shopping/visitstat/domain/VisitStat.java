package com.kt.shopping.visitstat.domain;

import java.time.LocalDateTime;

import com.kt.shopping.common.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class VisitStat extends BaseEntity {
	private String ip;
	private String userAgent; // 접속한 사용자의 단말정보
	private Long userId = null;
	private LocalDateTime visitedAt = LocalDateTime.now();
	public VisitStat(Long userId, String userAgent, String ip) {
		this.userId = userId;
		this.userAgent = userAgent;
		this.ip = ip;
	}
}
