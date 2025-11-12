package com.kt.shopping.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 요청의 기능별 DTO를 저장
public class ProductRequest {
	@Schema(name = "ProductRequest.Create")
	public record Create(
		@NotBlank String name,
		@NotNull Long price,
		@NotNull Long quantity
	){ }
	@Schema(name = "ProductRequest.Update")
	public record Update(
		@NotBlank String name,
		@NotNull Long price,
		@NotNull Long quantity
	){}
}
