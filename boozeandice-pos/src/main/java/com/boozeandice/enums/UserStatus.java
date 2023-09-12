package com.boozeandice.enums;

public enum UserStatus {
	
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE");
	
	private final String description;

	UserStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	
}
