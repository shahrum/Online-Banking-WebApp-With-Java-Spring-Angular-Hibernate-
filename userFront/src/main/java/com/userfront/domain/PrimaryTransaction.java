package com.userfront.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PrimaryTransaction {
	private long id;
	private Date date;
	private  String description;
	private  String type;
	private  String status;
	private double amount;
	private BigDecimal availabelBalance;
	private PrimaryAccount primaryAccount;
	
	public PrimaryTransaction(){
		
	}

	public PrimaryTransaction(Date date, String description, String type, String status, double amount,
			BigDecimal availabelBalance, PrimaryAccount primaryAccount) {
		this.date = date;
		this.description = description;
		this.type = type;
		this.status = status;
		this.amount = amount;
		this.availabelBalance = availabelBalance;
		this.primaryAccount = primaryAccount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public BigDecimal getAvailabelBalance() {
		return availabelBalance;
	}

	public void setAvailabelBalance(BigDecimal availabelBalance) {
		this.availabelBalance = availabelBalance;
	}

	public PrimaryAccount getPrimaryAccount() {
		return primaryAccount;
	}

	public void setPrimaryAccount(PrimaryAccount primaryAccount) {
		this.primaryAccount = primaryAccount;
	}

	@Override
	public String toString() {
		return "PrimaryTransaction [id=" + id + ", date=" + date + ", description=" + description + ", type=" + type
				+ ", status=" + status + ", amount=" + amount + ", availabelBalance=" + availabelBalance
				+ ", primaryAccount=" + primaryAccount + "]";
	}
	
	
	
}
