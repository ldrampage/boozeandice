package com.boozeandice.exceptions;

public class InvalidDateFormatException extends RuntimeException {

	private static final long serialVersionUID = 8697833564712767305L;
	
	public InvalidDateFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
