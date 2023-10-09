package com.boozeandice.entity;

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
@Table(name="transaction_item")
public class TransactionItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique=true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="product_id" , nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="transaction_id", nullable = false)
	private Transaction transaction;
	
	@Column(name="product_real_id")
	private Long productId;
	
	@Column(name="product_price")
	private Double productPriceAtTimeSold;
	
	@Column(name="produc_cost")
	private Double productCostAtTimeSold;
	
	@Column(name="packaging_fee")
	private Double packagingFee;
	
	@Column(name="barcode_digits")
	private String barcodeDigits;
	
	@Column(name="quantity")
	private Long quantity;
	
	@Column(name="created_date")
	private Date createdDate;

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

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getBarcodeDigits() {
		return barcodeDigits;
	}

	public void setBarcodeDigits(String barcodeDigits) {
		this.barcodeDigits = barcodeDigits;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Double getProductPriceAtTimeSold() {
		return productPriceAtTimeSold;
	}

	public void setProductPriceAtTimeSold(Double productPriceAtTimeSold) {
		this.productPriceAtTimeSold = productPriceAtTimeSold;
	}

	public Double getProductCostAtTimeSold() {
		return productCostAtTimeSold;
	}

	public void setProductCostAtTimeSold(Double productCostAtTimeSold) {
		this.productCostAtTimeSold = productCostAtTimeSold;
	}
	
	

	

	
	
	
	
	
	
	

}
