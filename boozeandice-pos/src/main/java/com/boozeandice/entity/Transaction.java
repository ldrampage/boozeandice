package com.boozeandice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import jakarta.persistence.Transient;

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
	
	@Column(name="packaging")
	private Double packaging;
	
	@Column(name="discount")
	private Double discount;
	
	@Column(name="vatable_sales")
	private Double vatableSales;
	
	@Column(name="vat_amount")
	private Double vatAmount;
	
	@Column(name="total")
	private Double total;
	
	@Column(name="transaction_date_time")
	private Date transactionDateTime;
	
	@Column(name="soldTo")
	private String soldTo;
	
	@Column(name="table_no")
	private String tableNo;
	
	@ManyToOne
	@JoinColumn(name="customer_id") 
	private Customer customer;
	
	@OneToMany(mappedBy="transaction")
	private Set<TransactionItem> transactionItem; 
	
	@Column(name="invoiceNumber")
	private String invoiceNumber;
	
	@Column(name="transaction_type")
	private String transactionType; 
	
	@Column(name="payment_method")
	private String paymentMethod; 
	
	@Column(name="transaction_status")
	private String transactionStatus;
	
	@Column(name="payment_reference")
	private String paymentReference; // A string field to store any reference or transaction ID provided by the payment gateway or processor. 
	//It can be used to track the payment externally if needed.
	
	@Column(name="transaction_notes")
	private String transactionNotes;
	
	@Column(name="cash_received")
	private Double cashReceived;
	
	@ManyToOne
	@JoinColumn(name="cashier_id")
	private User cashier;
	
	@ManyToOne
	@JoinColumn(name="cashdrawer_id")
	private CashDrawer cashdrawer;
	
	@Transient
	private Long totalItems = Long.valueOf(0);
	

	public Long getTotalItems() {
		if(this.transactionItem != null && this.transactionItem.size() > 0) {
			for(TransactionItem tI : this.transactionItem) {
				totalItems = totalItems + tI.getQuantity();
			}
			
		}
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

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

	
	public Double getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(Double vatAmount) {
		this.vatAmount = vatAmount;
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

	public Set<TransactionItem> getTransactionItem() {
		return transactionItem;
	}

	public void setTransactionItem(Set<TransactionItem> transactionItem) {
		this.transactionItem = transactionItem;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType.getDescription();
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod.getDescription();
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus.getDescription();
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

	public Double getVatableSales() {
		return vatableSales;
	}

	public void setVatableSales(Double vatableSales) {
		this.vatableSales = vatableSales;
	}

	public Double getCashReceived() {
		return cashReceived;
	}

	public void setCashReceived(Double cashReceived) {
		this.cashReceived = cashReceived;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public User getCashier() {
		return cashier;
	}

	public void setCashier(User cashier) {
		this.cashier = cashier;
	}

	public CashDrawer getCashdrawer() {
		return cashdrawer;
	}

	public void setCashdrawer(CashDrawer cashdrawer) {
		this.cashdrawer = cashdrawer;
	}
	
	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public Double getPackaging() {
		return packaging;
	}

	public void setPackaging(Double packaging) {
		this.packaging = packaging;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	
	
	


}
