//package com.boozeandice.entity;
//
//import java.util.Date;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name="stock_tansfer_trace")
//public class StockTransferTrace {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id", nullable = false, unique = true)
//	private Long id;
//	
//	@ManyToOne
//	@JoinColumn(name="stock_destination_id")
//	private Product stockDestinationId;
//	
//	@ManyToOne
//	@JoinColumn(name="stock_source_id")
//	private Product stockSourceId;
//	
//	@Column(name="quantity_transferred")
//	private Long quantityTransferred;
//	
//	@Column(name="notes")
//	private String notes;
//	
//	@Column(name="created_date")
//	private Date createdDate;
//	
//	@ManyToOne
//	@JoinColumn(name="created_by")
//	private User createdBy;
//	
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
//	public Product getStockDestinationId() {
//		return stockDestinationId;
//	}
//	public void setStockDestinationId(Product stockDestinationId) {
//		this.stockDestinationId = stockDestinationId;
//	}
//	public Product getStockSourceId() {
//		return stockSourceId;
//	}
//	public void setStockSourceId(Product stockSourceId) {
//		this.stockSourceId = stockSourceId;
//	}
//	public Long getQuantityTransferred() {
//		return quantityTransferred;
//	}
//	public void setQuantityTransferred(Long quantityTransferred) {
//		this.quantityTransferred = quantityTransferred;
//	}
//	public String getNotes() {
//		return notes;
//	}
//	public void setNotes(String notes) {
//		this.notes = notes;
//	}
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//	public User getCreatedBy() {
//		return createdBy;
//	}
//	public void setCreatedBy(User createdBy) {
//		this.createdBy = createdBy;
//	}
//	
//	
//	
//	
//	
//
//}
