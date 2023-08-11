package com.boozeandice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.boozeandice.enums.PaymentMethod;
import com.boozeandice.enums.TransactionStatus;
import com.boozeandice.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique=true)
	private Long id;
	
	@Column(name="subTotal")
	private Double subTotal;
	
	@Column(name="shipping")
	private Double shipping;
	
	@Column(name="tax")
	private Double tax;
	
	@Column(name="total")
	private Double total;
	
	@Column(name="transaction_date_time")
	private Date transactionDateTime;
	
	@Column(name="soldTo")
	private String soldTo;
	
	@ManyToOne
	@JoinColumn(name="customer_id") 
	private Customer customer;
	
	@OneToMany(mappedBy="transaction")
	private List<TransactionItem> transactionItem; 
	
	@Column(name="invoiceNumber")
	private String invoiceNumber;
	
	@Column(name="transaction_type")
	private TransactionType transactionType; // this also tells about the status of the transaction
	
	@Column(name="payment_method")
	private PaymentMethod paymentMethod; 
	
	@Column(name="transaction_status")
	private TransactionStatus transactionStatus;
	
	@Column(name="payment_reference")
	private String paymentReference; // A string field to store any reference or transaction ID provided by the payment gateway or processor. 
	//It can be used to track the payment externally if needed.
	
	@Column(name="transaction_notes")
	private String transactionNotes;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getShipping() {
		return shipping;
	}

	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getSoldTo() {
		return soldTo;
	}

	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<TransactionItem> getTransactionItem() {
		return transactionItem;
	}

	public void setTransactionItem(List<TransactionItem> transactionItem) {
		this.transactionItem = transactionItem;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getTransactionNotes() {
		return transactionNotes;
	}

	public void setTransactionNotes(String transactionNotes) {
		this.transactionNotes = transactionNotes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	


}
