package com.Manage.entity;


public class PrepareCardTemp {

	private String ID;
	private String time;
	private String countryName;
	private String cardCount;
	private String orderTotalNum;
	private String surplusFlow;
	private String userFlow;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCardCount() {
		return cardCount;
	}
	public void setCardCount(String cardCount) {
		this.cardCount = cardCount;
	}
	public String getOrderTotalNum() {
		return orderTotalNum;
	}
	public void setOrderTotalNum(String orderTotalNum) {
		this.orderTotalNum = orderTotalNum;
	}
	public String getSurplusFlow() {
		return surplusFlow;
	}
	public void setSurplusFlow(String surplusFlow) {
		this.surplusFlow = surplusFlow;
	}
	public String getUserFlow() {
		return userFlow;
	}
	public void setUserFlow(String userFlow) {
		this.userFlow = userFlow;
	}
	
	public PrepareCardTemp()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public PrepareCardTemp(String iD, String time, String countryName, String cardCount, String orderTotalNum, String surplusFlow, String userFlow)
	{
		super();
		ID = iD;
		this.time = time;
		this.countryName = countryName;
		this.cardCount = cardCount;
		this.orderTotalNum = orderTotalNum;
		this.surplusFlow = surplusFlow;
		this.userFlow = userFlow;
	}
	
}
