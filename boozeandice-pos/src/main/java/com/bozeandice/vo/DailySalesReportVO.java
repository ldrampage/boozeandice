package com.bozeandice.vo;

import java.util.Date;

public class DailySalesReportVO {
	
	private Date date;
	private Double total;
	private Double cashTransaction;
	private Double gcashTransaction;
	private Double cardTransaction;
	private Double cost;
	private Double profit;
	private Double taxes;
	private Long noOfTransactions;
	private Long noOfItems;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getCashTransaction() {
		return cashTransaction;
	}
	public void setCashTransaction(Double cashTransaction) {
		this.cashTransaction = cashTransaction;
	}
	public Double getGcashTransaction() {
		return gcashTransaction;
	}
	public void setGcashTransaction(Double gcashTransaction) {
		this.gcashTransaction = gcashTransaction;
	}
	public Double getCardTransaction() {
		return cardTransaction;
	}
	public void setCardTransaction(Double cardTransaction) {
		this.cardTransaction = cardTransaction;
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
	public Long getNoOfTransactions() {
		return noOfTransactions;
	}
	public void setNoOfTransactions(Long noOfTransactions) {
		this.noOfTransactions = noOfTransactions;
	}
	public Long getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(Long noOfItems) {
		this.noOfItems = noOfItems;
	}
	
	public Double getTaxes() {
		return taxes;
	}
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	
	
	
	

}
