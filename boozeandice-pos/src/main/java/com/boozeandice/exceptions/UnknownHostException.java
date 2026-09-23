package com.boozeandice.exceptions;

public class UnknownHostException extends RuntimeException {

	private static final long serialVersionUID = 4240816875428407108L;
	
	public UnknownHostException(String message, Throwable cause) {
		super(message,cause);
	}

}
