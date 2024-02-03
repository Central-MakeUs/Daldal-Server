package com.mm.api.common.swaggerAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mm.api.exception.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Operation()
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "성공"),
	@ApiResponse(responseCode = "400/0001", description = "잘못된 요청입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	@ApiResponse(responseCode = "404/0002", description = "존재하지 않는 회원입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	@ApiResponse(responseCode = "401/0001", description = "리퀘스트 토큰이 만료되었으니 다시 로그인 해주세요.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	@ApiResponse(responseCode = "401/0002", description = "어세스 토큰이 만료되었으니 재발급 해주세요.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
	@ApiResponse(responseCode = "500", description = "서버 에러 발생", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
})
public @interface SwaggerResponseAuth {
}
