package com.mm.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private final String errorCode;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public void addValidation(String field, String message) {
        validation.put(field, message);
    }
}
