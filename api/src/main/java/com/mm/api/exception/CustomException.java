package com.mm.api.exception;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode code;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode;
    }
}
