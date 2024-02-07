package com.mm.api.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.List;
import java.util.Objects;

import static com.mm.api.exception.ErrorCode.ACCESS_TOKEN_EXPIRED;
import static com.mm.api.exception.ErrorCode.ACCESS_TOKEN_MALFORMED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException() {
        ErrorResponse errorResponse = new ErrorResponse(ACCESS_TOKEN_MALFORMED.getErrorCode(), ACCESS_TOKEN_MALFORMED.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException() {
        ErrorResponse errorResponse = new ErrorResponse(ACCESS_TOKEN_MALFORMED.getErrorCode(), ACCESS_TOKEN_MALFORMED.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException() {
        ErrorResponse errorResponse = new ErrorResponse(ACCESS_TOKEN_EXPIRED.getErrorCode(), ACCESS_TOKEN_EXPIRED.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.info(">>>>> CustomException occurred!! {}", e);

        ErrorResponse errorResponse = new ErrorResponse(e.getCode().getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentException(MethodArgumentNotValidException e) {
        log.info(">>>>> MethodArgumentNotValidException occurred!! {}", e);

        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = Objects.requireNonNull(bindingResult.getFieldError())
                .getDefaultMessage();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_FAILED.getErrorCode(), errorMessage);
        fieldErrors.forEach(error -> errorResponse.addValidation(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.info(">>>>> Internal Server error occurred!! {}", e);

        ErrorResponse errorResponse = new ErrorResponse("500/0001", e.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
