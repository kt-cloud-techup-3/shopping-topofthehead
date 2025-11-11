package com.kt.shopping.common;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

// Swagger에 표시할 각 API의 HTTP Status를 API 전체에 일괄적으로 적용하기위해  클래스 레벨 선언
@ApiResponses(value = {
	@ApiResponse(responseCode="400", description = "유효성 검사 실패"),
	@ApiResponse(responseCode = "500", description = "서버 에러")
})
public abstract class SwaggerSupporter {
}
