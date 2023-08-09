package com.boozeandice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.boozeandice.enums.PaymentMethod;
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
	
	@Column(name="payment_reference")
	private String paymentReference; // A string field to store any reference or transaction ID provided by the payment gateway or processor. 
	//It can be used to track the payment externally if needed.
	
	@Column(name="transaction_notes")
	private String transactionNotes;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;


}
