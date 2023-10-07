package com.boozeandice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@OneToOne(mappedBy = "originAddress")
	private Shipment originAddress;
	
	@OneToOne(mappedBy = "destinationAddress")
	private Shipment destinationAddress;

	@Column(name = "street")
	private String street;
	
	@Column(name="region")
	private String region;
	
	@Column(name="province")
	private String province;
	
	@Column(name = "city")
	private String city;

	@Column(name = "postal_zip_code")
	private String postalZipCode;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "additional_address_details")
	private String additionalAddressDetails;
	
	@Column(name = "landmark")
	private String landmark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalZipCode() {
		return postalZipCode;
	}

	public void setPostalZipCode(String postalZipCode) {
		this.postalZipCode = postalZipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAdditionalAddressDetails() {
		return additionalAddressDetails;
	}

	public void setAdditionalAddressDetails(String additionalAddressDetails) {
		this.additionalAddressDetails = additionalAddressDetails;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public Shipment getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(Shipment originAddress) {
		this.originAddress = originAddress;
	}

	public Shipment getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(Shipment destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	
	
	

}
