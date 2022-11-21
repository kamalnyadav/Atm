package com.zinkworks.springboot.atm.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AtmRestExceptionHandler {

	ErrorResponse error;

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleInvalidPinException(InvalidPinException e) {
		if (e.getMessage().contains("type Integer")) {
			error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		} else {
			error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), System.currentTimeMillis());
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}

	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
		error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}
}
