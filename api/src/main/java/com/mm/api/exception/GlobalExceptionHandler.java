package com.mm.api.exception;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		log.info(">>>>> CustomException occurred!! {}", e);

		ErrorResponse errorResponse = new ErrorResponse(e.getCode().getErrorCode(), e.getMessage());
		return ResponseEntity.status(e.getCode().getStatus()).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException e) {
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
	public ResponseEntity<?> handleException(Exception e) {
		log.info(">>>>> Internal Server error occurred!! {}", e);

		ErrorResponse errorResponse = new ErrorResponse("500/0001", e.getMessage());
		return ResponseEntity.internalServerError().body(errorResponse);
	}
}
