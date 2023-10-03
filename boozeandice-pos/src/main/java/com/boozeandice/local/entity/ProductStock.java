package com.boozeandice.local.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="product_stock")
public class ProductStock implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique=true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;  
	
	@Column(name="quantity", nullable = false)
	private Long quantity;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="purchase_date", nullable=false)
	private Date purchaseDate;
	
	@Column(name="cost", nullable = false)
	private Double cost;
	
	@Column(name="barcodeDigits")
	private Long barcodeDigits;
	
	@Column(name="barcodeDigitsVII")
	private String barcodeDigitsv2;
	
	@Column(name="barcodeImageLocation")
	private String barcodeImageLocation;
	
	@Column(name="expenses")
	private Double expenses;
	
	@Column(name="createdDate", nullable = false)
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name="created_by")	
	private User createdBy;
	
	@ManyToOne
	@JoinColumn(name="last_modified_by") 
	private User lastModifiedBy;
	
	@Column(name="lastModifiedDate")
	private Date lastModifiedDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	public User getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public User getCreatedBy() {
		if(createdBy == null)
			createdBy = new User();
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Double getExpenses() {
		return expenses;
	}
	public void setExpenses(Double expenses) {
		this.expenses = expenses;
	}
	public String getBarcodeImageLocation() {
		return barcodeImageLocation;
	}
	public void setBarcodeImageLocation(String barcodeImageLocation) {
		this.barcodeImageLocation = barcodeImageLocation;
	}
	public Long getBarcodeDigits() {
		return barcodeDigits;
	}
	public void setBarcodeDigits(Long barcodeDigits) {
		this.barcodeDigits = barcodeDigits;
	}
	public String getBarcodeDigitsv2() {
		return barcodeDigitsv2;
	}
	public void setBarcodeDigitsv2(String barcodeDigitsv2) {
		this.barcodeDigitsv2 = barcodeDigitsv2;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
