package com.boozeandice.enums;

public enum TransactionStatus {
	PENDING("PENDING"),
    PAID("PAID"),
    REFUND("REFUND"),
    CANCELLED("CANCELLED");
	
	private final String description;

	TransactionStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	
}
