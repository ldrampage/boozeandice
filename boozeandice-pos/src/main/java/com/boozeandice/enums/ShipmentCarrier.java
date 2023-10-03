package com.boozeandice.enums;

public enum ShipmentCarrier {
	OWN("OWN");
	
	private final String description;

	ShipmentCarrier(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
