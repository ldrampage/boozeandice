package com.boozeandice.enums;

public enum TransactionType {
	PURCHASE("PURCHASE"),
    SALE("SALE"),
    REFUND("REFUND"),
    CANCELLED("CANCELLED");
	
	private final String description;

	TransactionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
