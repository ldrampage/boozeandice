package com.boozeandice.enums;

public enum PaymentMethod {
	PAYMAYA("PAYMAYA"), 
	CASH("CASH"), 
	GCASH("GCASH");
	
	private final String description;

	PaymentMethod(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	
}
