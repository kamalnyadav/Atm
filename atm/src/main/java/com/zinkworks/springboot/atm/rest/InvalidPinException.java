package com.zinkworks.springboot.atm.rest;

public class InvalidPinException extends RuntimeException {

	public InvalidPinException() {
	}

	public InvalidPinException(String message) {
		super(message);
	}

	public InvalidPinException(Throwable cause) {
		super(cause);
	}

	public InvalidPinException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
