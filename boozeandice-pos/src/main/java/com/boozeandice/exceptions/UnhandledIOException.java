package com.boozeandice.exceptions;

public class UnhandledIOException extends RuntimeException {

	private static final long serialVersionUID = -2936795258192673458L;
	
	public UnhandledIOException(String message, Throwable cause) {
		super(message, cause);
	}

}
