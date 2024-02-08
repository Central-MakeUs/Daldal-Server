package com.mm.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	VALIDATION_FAILED("400/0001", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	DIB_ALREADY_EXIST("400/0002", HttpStatus.BAD_REQUEST, "이미 찜하기를 했습니다."),
	POINTS_WITHDRAW_MIN("400/0003", HttpStatus.BAD_REQUEST, "출금 최소 금액은 1000 P 입니다"),
	POINTS_WITHDRAW_MAX("400/0004", HttpStatus.BAD_REQUEST, "출금 최소 금액은 5000 P 입니다"),
	POINTS_WITHDRAW_NOT_ENOUGH("400/0005", HttpStatus.BAD_REQUEST, "출금 포인트보다 보유 포인트가 더 적습니다"),
	ITEM_NOT_FOUND("404/0001", HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다"),
	MEMBER_NOT_FOUND("404/0002", HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다"),
	BUY_NOT_FOUND("404/0003", HttpStatus.NOT_FOUND, "존재하지 않는 구매인증입니다"),
	DIB_NOT_FOUND("404/0004", HttpStatus.NOT_FOUND, "존재하지 않는 찜하기입니다"),
	REFRESH_TOKEN_EXPIRED("401/0001", HttpStatus.UNAUTHORIZED, "리퀘스트 토큰이 만료되었으니 다시 로그인 해주세요"),
	ACCESS_TOKEN_EXPIRED("401/0002", HttpStatus.UNAUTHORIZED, "어세스 토큰이 만료되었으니 재발급 해주세요"),
	ACCESS_TOKEN_MALFORMED("401/0003", HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다.");

	private final String errorCode;
	private final HttpStatus status;
	private final String message;

	ErrorCode(String errorCode, HttpStatus httpStatus, String message) {
		this.errorCode = errorCode;
		this.status = httpStatus;
		this.message = message;
	}
}
