package com.zinkworks.springboot.atm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "atms")
public class Atms {

	@Id
	@Column(name = "atm_id")
	@JsonIgnore
	private int id;

	private int fifty;

	private int twenty;

	private int ten;

	private int five;
	
	@Transient
	private int amountDispersed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFifty() {
		return fifty;
	}

	public void setFifty(int fifty) {
		this.fifty = fifty;
	}

	public int getTwenty() {
		return twenty;
	}

	public void setTwenty(int twenty) {
		this.twenty = twenty;
	}

	public int getTen() {
		return ten;
	}

	public void setTen(int ten) {
		this.ten = ten;
	}

	public int getFive() {
		return five;
	}

	public void setFive(int five) {
		this.five = five;
	}

	public int getAmountDispersed() {
		return amountDispersed;
	}

	public void setAmountDispersed(int amountDispersed) {
		this.amountDispersed = amountDispersed;
	}
	

}
