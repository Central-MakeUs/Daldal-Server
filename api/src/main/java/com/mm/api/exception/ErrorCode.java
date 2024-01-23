package com.mm.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	VALIDATION_FAILED("400/0001", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	ITEM_NOT_FOUND("404/0001", HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다"),
	MEMBER_NOT_FOUND("404/0002", HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다"),
	REFRESH_TOKEN_EXPIRED("401/0001", HttpStatus.UNAUTHORIZED, "토큰이 만료되었으니 다시 로그인 해주세요");

	private final String errorCode;
	private final HttpStatus status;
	private final String message;

	ErrorCode(String errorCode, HttpStatus httpStatus, String message) {
		this.errorCode = errorCode;
		this.status = httpStatus;
		this.message = message;
	}
}
