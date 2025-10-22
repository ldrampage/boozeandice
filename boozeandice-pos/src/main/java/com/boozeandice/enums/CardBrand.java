package com.boozeandice.enums;

public enum CardBrand {
	
	VISA("VISA"), 
	MASTERCARD("MASTERCARD"), 
	AMEX("AMERICAN_EXPRESS");
	
	private final String description;

	CardBrand(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
