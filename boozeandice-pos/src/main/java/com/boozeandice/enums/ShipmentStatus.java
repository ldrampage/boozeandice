package com.boozeandice.enums;

public enum ShipmentStatus {
	
	PENDING("PENDING"),
	OUT_FOR_DELIVERY("OUT_FOR_DELIVERY"),
	DEVLIVERED("DELIVERED");
	
	private final String description;

	ShipmentStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
