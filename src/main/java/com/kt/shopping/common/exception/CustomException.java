package com.kt.shopping.common.exception;

import org.springframework.http.HttpStatus;

import com.kt.shopping.common.ErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private HttpStatus status;
	private String message;
	public CustomException(String message) {
		super(message);
	}
	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getStatus();
		this.message = errorCode.toString();
	}
}
