package com.mm.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    VALIDATION_FAILED("400/0001", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    ITEM_NOT_FOUND("404/0001", HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다");

    private final String errorCode;
    private final HttpStatus status;
    private final String message;

    ErrorCode(String errorCode, HttpStatus httpStatus, String message) {
        this.errorCode = errorCode;
        this.status = httpStatus;
        this.message = message;
    }
}
