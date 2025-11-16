package com.kt.shopping.common;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected Long id;
	@Column(name="CREATEDAT")
	protected LocalDateTime createdAt;
	@Column(name="UPDATEDAT")
	protected LocalDateTime updatedAt;
}
