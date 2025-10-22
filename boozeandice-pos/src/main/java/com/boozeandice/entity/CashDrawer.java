package com.boozeandice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
@Table(name = "cash_drawer")
public class CashDrawer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "starting_cash")
	private Double startingCash;

	@OneToMany(mappedBy = "cashdrawer")
	private Set<CashAdded> cashAdded;

	@OneToMany(mappedBy = "cashdrawer")
	private Set<Expense> expenses;

	@Column(name = "created_date")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@OneToMany(mappedBy = "cashdrawer")
	private Set<Transaction> transactions;

	@Transient
	private Double totalCashAdded;

	@Transient
	private Double totalExpenses;

	@Transient
	private Double totalCashSales;
	
	@Transient
	private Double totalCashInDrawer;
	
	@Transient 
	private Double totalGCashPayments;
	
	@Transient 
	private Double totalCreditCardPayments;
	
	public Double getTotalGCashPayments() {
		return totalGCashPayments;
	}

	public void setTotalGCashPayments(Double totalGCashPayments) {
		this.totalGCashPayments = totalGCashPayments;
	}

	public Double getTotalCreditCardPayments() {
		return totalCreditCardPayments;
	}

	public void setTotalCreditCardPayments(Double totalCreditCardPayments) {
		this.totalCreditCardPayments = totalCreditCardPayments;
	}

	public Double getTotalCashInDrawer() {
		return totalCashInDrawer;
	}

	public void setTotalCashInDrawer(Double totalCashInDrawer) {
		this.totalCashInDrawer = totalCashInDrawer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getStartingCash() {
		return startingCash;
	}

	public void setStartingCash(Double startingCash) {
		this.startingCash = startingCash;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<CashAdded> getCashAdded() {
		return cashAdded;
	}

	public void setCashAdded(Set<CashAdded> cashAdded) {
		this.cashAdded = cashAdded;
	}

	public Set<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(Set<Expense> expenses) {
		this.expenses = expenses;
	}

	public Double getTotalCashAdded() {
		return totalCashAdded;
	}

	public void setTotalCashAdded(Double totalCashAdded) {
		this.totalCashAdded = totalCashAdded;
	}

	public Double getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(Double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public Double getTotalCashSales() {
		return totalCashSales;
	}

	public void setTotalCashSales(Double totalCashSales) {
		if(this.totalCashSales == null) {
			this.totalCashSales = 0.0;
		}
		this.totalCashSales = totalCashSales;
	}

}
