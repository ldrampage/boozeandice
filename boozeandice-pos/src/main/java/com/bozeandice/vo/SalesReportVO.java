package com.bozeandice.vo;

import java.util.Date;

public class SalesReportVO {
	
	private Date date;
	private String dateString;
	private String total;
	private String cashTransaction;
	private String gcashTransaction;
	private String cardTransaction;
	private String cost;
	private String profit;
	private String taxes;
	private String expenses;
	private Long noOfTransactions = Long.valueOf(0);
	private Long noOfItems = Long.valueOf(0);
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public String getTotal() {
		if(total != null)
			total = String.format("%,.2f", Double.valueOf(total));
		return total;
	}
		
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCashTransaction() {
		if(cashTransaction != null)
			cashTransaction = String.format("%,.2f", Double.valueOf(cashTransaction));
		return cashTransaction;
	}
	public void setCashTransaction(String cashTransaction) {
		this.cashTransaction = cashTransaction;
	}
	public String getGcashTransaction() {
		if(gcashTransaction != null)
			gcashTransaction = String.format("%,.2f", Double.valueOf(gcashTransaction));
		return gcashTransaction;
	}
	public void setGcashTransaction(String gcashTransaction) {
		this.gcashTransaction = gcashTransaction;
	}
	public String getCardTransaction() {
		if(cardTransaction != null)
			cardTransaction = String.format("%,.2f", Double.valueOf(cardTransaction));
		return cardTransaction;
	}
	public void setCardTransaction(String cardTransaction) {
		this.cardTransaction = cardTransaction;
	}
	public String getCost() {
		if(cost != null)
			cost = String.format("%,.2f", Double.valueOf(cost));
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getProfit() {
		if(profit != null)
			profit = String.format("%,.2f", Double.valueOf(profit));
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getExpenses() {
//		if(expenses != null) {
//			expenses.replace(",", "");
//			expenses = String.format("%,.2f", Double.valueOf(expenses));
//		}
		return expenses;
	}
	public void setExpenses(String expenses) {
		this.expenses = expenses;
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
	
	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}
	
	public String getTaxes() {
		if(taxes != null)
			taxes = String.format("%,.2f", Double.valueOf(taxes));
		return taxes;
	}
	
	
	
	
	
	

}
