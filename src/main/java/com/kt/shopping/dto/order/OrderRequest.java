package com.kt.shopping.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface OrderRequest {
	@Schema(name="OrderRequest.Create")
	record Create(
		@NotNull
		Long userId,
		@NotNull
		Long prodId,
		@NotNull
		@Min(1)
		Long quantity,
		@NotBlank
		String receiverName,
		@NotBlank
		String receiverAddress,
		@NotBlank
		String receiverMobile
	){}
}
