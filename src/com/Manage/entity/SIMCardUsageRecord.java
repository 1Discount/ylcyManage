package com.Manage.entity;

/**
 * 
 * @author 沈超 2016/05/27
 *
 */
public class SIMCardUsageRecord {
	
	private int id;
	/**
	 * 统计卡数量
	 */
	private String cardCount;
	/**
	 * 使用时间
	 */
	private String useTime;
	/**
	 * 消耗金额
	 */
	private String moneyCount;
	/**
	 * 累计使用流量
	 */
	private String flowCount;
	/**
	 * 国家
	 */
	private String country;
	
	//查询辅助字段
	private String begainTime;
	private String endTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getCardCount() {
		return cardCount;
	}
	public void setCardCount(String cardCount) {
		this.cardCount = cardCount;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getMoneyCount() {
		return moneyCount;
	}
	public void setMoneyCount(String moneyCount) {
		this.moneyCount = moneyCount;
	}
	public String getFlowCount() {
		return flowCount;
	}
	public void setFlowCount(String flowCount) {
		this.flowCount = flowCount;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBegainTime() {
		return begainTime;
	}
	public void setBegainTime(String begainTime) {
		this.begainTime = begainTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
