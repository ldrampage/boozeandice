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
import jakarta.persistence.OneToOne;
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
	
	@Column(name="batch_number")
	private String batchNumber;
	
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

	public String getBatchNumber() {
		return batchNumber;
	} 

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	
	
	
	
	

}
