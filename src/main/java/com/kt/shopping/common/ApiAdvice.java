package com.kt.shopping.common;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 어플리케이션 전역적으로 API에서 발생하는 예외를 가져온다.
// 각 @ExceptionHandler에서 지정된 예외를 처리
@Hidden
@RestControllerAdvice
public class ApiAdvice {
	// 가장 후순위로 예외를 처리하는 메서드
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorData> internalServerError(Exception e){
		e.printStackTrace();
		return ApiAdvice.of(
			HttpStatus.INTERNAL_SERVER_ERROR,
			"에러입니다."
		);
	}
	// 사용자 정의 예외를 처리하는 메서드
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorData> customError(CustomException e){
		return ApiAdvice.of(
			e.getStatus(),
			e.getMessage()
		);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorData> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		// 예외 처리 로직 작성
		// 예외 발생 시 전달할 에러메시지 가공
		var details = Arrays.toString(e.getDetailMessageArguments());
		var message = details.split(",", 2)[1]
			.replace("[","")
			.replace("]","")
			.trim();
		// 정적팩토리메서드를 통해 ResponseEntity객체를 생성해서 반환
		return ApiAdvice.of(HttpStatus.BAD_REQUEST, message);
	}

	// ResponseEntity를 생성하는 정적 팩토리 메서드
	public static ResponseEntity<ErrorData> of(HttpStatus status, String message) {
		String statusSeriesName = status.series().name();
		ErrorData errorData = ErrorData.of(statusSeriesName,message);
		return ResponseEntity
			.status(status)
			.body(errorData);
	}
	// DTO 역할의 중첩 static Class
	// 해당 클래스객체로 JSON 변환되어 클라이언트에게 반환
	@Getter
	@AllArgsConstructor
	public static class ErrorData{
		private String code;
		private String message;
		// 정적팩토리메서드 패턴
		public static ErrorData of(String code, String message) {
			return new ErrorData(code,message);
		}
	}
}
