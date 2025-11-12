package com.kt.shopping.controller.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.common.ApiResult;
import com.kt.shopping.common.SwaggerSupporter;
import com.kt.shopping.dto.product.ProductRequest;
import com.kt.shopping.service.product.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name="상품", description ="상품입니다.")
public class ProductContoller extends SwaggerSupporter {
	private final ProductService productService;
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<Void> create(@RequestBody @Valid ProductRequest.Create request){
		productService.create(
			request.name(),
			request.price(),
			request.quantity()
		);
		return ApiResult.ok();
	}
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> update(
		@RequestBody @Valid ProductRequest.Update request,
		@PathVariable Long id
	){
		productService.update(
			id,
			request.name(),
			request.price(),
			request.quantity()
		);
		return ApiResult.ok();
	}
	@PutMapping("/{id}/sold-out")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> soldOut(@PathVariable Long id){
		productService.soldOut(id);
		return ApiResult.ok();
	}
	@PutMapping("/{id}/activate")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> activate(@PathVariable Long id){
		productService.Activate(id);
		return ApiResult.ok();
	}
	@PutMapping("/{id}/in-activate")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> inActivate(@PathVariable Long id){
		productService.inActivate(id);
		return ApiResult.ok();
	}
	// 실제 삭제 수행
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> delete(@PathVariable Long id){
		productService.delete(id);
		return ApiResult.ok();
	}
}
