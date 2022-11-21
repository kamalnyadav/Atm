package com.zinkworks.springboot.atm.entity;

public class BalanceCheck {
	private long balance;
	private long maximumWithdrawal;

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getMaximumWithdrawal() {
		return maximumWithdrawal;
	}

	public BalanceCheck(long balance, long maximumWithdrawal) {
		super();
		this.balance = balance;
		this.maximumWithdrawal = maximumWithdrawal;
	}

	public void setMaximumWithdrawal(long maximumWithdrawal) {
		this.maximumWithdrawal = maximumWithdrawal;
	}
}
