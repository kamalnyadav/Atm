package com.zinkworks.springboot.atm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "pin")
	private int pin;

	@Column(name = "balance")
	private int balance;

	@Column(name = "overdraft")
	private int overdraft;
	
	@Transient
	private int amount;
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(int overdraft) {
		this.overdraft = overdraft;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Users [accountNumber=" + accountNumber + ", pin=" + pin + ", balance=" + balance + ", overdraft="
				+ overdraft + "]";
	}
	
}
