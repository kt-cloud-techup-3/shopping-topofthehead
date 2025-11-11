package com.kt.shopping.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResult<T> {
	@JsonProperty("statuscode")
	private String code;
	@JsonIgnore
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
	public static ApiResult<Void> ok(){
		return ApiResult.of("ok","성공",null);
	}
	public static <T> ApiResult<T> ok(T data){
		return ApiResult.of("ok","성공",data);
	}
	public static <T> ApiResult<T> of(String code, String message, T data){
		return new ApiResult<>(code,message,data);
	}
}
