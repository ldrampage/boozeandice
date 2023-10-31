package com.boozeandice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import com.boozeandice.enums.PaymentMethod;
import com.boozeandice.enums.TransactionStatus;
import com.boozeandice.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	
	@ManyToOne
	@JoinColumn(name="customer_id") 
	private Customer customer;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="shipment_id")
	private Shipment shipment; 
	
	@ManyToOne
	@JoinColumn(name="cashier_id")
	private User cashier;
	
	@ManyToOne
	@JoinColumn(name="cashdrawer_id")
	private CashDrawer cashdrawer;
	
	@OneToMany(mappedBy="transaction")
	private Set<TransactionItem> transactionItem;
	
	@Column(name="subTotal") // before packaging, deductions, discounts
	private Double subTotal;
	
	@Column(name="total") // total revenue per transaction
	private Double total;
	
	@Column(name="cost") // product cost + discounts per transaction
	private Double cost;
	
	@Column(name="profit") // total - cost per transaction
	private Double profit;
	
	@Column(name="shipping")
	private Double shipping;
	
	@Column(name="take_out")
	private Boolean takeOut = false;
	
	@Column(name="is_delivery")
	private Boolean isDelivery = false;
	
	@Column(name="packaging")
	private Double packaging;
	
	@OneToMany(mappedBy="transaction")
	private Set<Discount> discount;
	
	@Column(name="vatable_sales")
	private Double vatableSales;
	
	@Column(name="vat_amount")
	private Double vatAmount;
	
	@Column(name="transaction_date_time")
	private Date transactionDateTime;
	
	@Column(name="soldTo")
	private String soldTo;
	
	@Column(name="table_no")
	private String tableNo;
		
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
	
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="card_brand")
	private String cardBrand;
	
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
	
	
	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getProfit() {
		return profit;
	}
	
	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public String getCardBrand() {
		return cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
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

	
	


	public Set<Discount> getDiscount() {
		return discount;
	}


	public void setDiscount(Set<Discount> discount) {
		this.discount = discount;
	}


	public Boolean getTakeOut() {
		return takeOut;
	}

	public void setTakeOut(Boolean takeOut) {
		this.takeOut = takeOut;
	}
	
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public Boolean getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(Boolean isDelivery) {
		this.isDelivery = isDelivery;
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
