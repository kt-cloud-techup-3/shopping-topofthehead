package com.kt.shopping.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@CreatedDate
	@Column(name="CREATEDAT")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Instant createdAt;
	@LastModifiedDate
	@Column(name="UPDATEDAT")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Instant updatedAt;
}
