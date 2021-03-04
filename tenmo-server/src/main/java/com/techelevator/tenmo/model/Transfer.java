package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

	
	private int id;
	private int typeId;
	private int statusId;
	private int from;
	private int to;
	private BigDecimal account;
	
	
	public Transfer () {};
	
	public Transfer(int id, int typeId, int statusId, int from, int to, BigDecimal account) {
		this.id = id;
		this.typeId = typeId;
		this.statusId = statusId;
		this.from = from;
		this.to = to;
		this.account = account;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	
	
}
