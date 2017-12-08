package com.Manage.entity;


/**
 * VIPCardInfo实体类
 * @String 2015-11-24
 * @author jiang
 *
 */
public class VIPCardInfo {
	
	  public String cardID;//vip卡卡号(唯一标识10位数字)
	  
	  public String password; //密码（长度为10位）
	  
	  public String  preferentialType; // 优惠方式（身分、充值、设备）
	  
	  public String  batchNumber; //批次号
	  
	  public String startTime; //优惠卡  有效期开始时间
	  
	  public String endTime; //优惠卡有效期结束时间
	  
	  public String isExchange ; //是否兑换
	  
	  public String exchangeTime; // 兑换时间
	  
	  public String exchangeIphone; // 对兑手机号
	  
	  public String cardStatus; //  卡状态（可用、禁用）
	  
	  public String  isMakeCard ; // 是否已制卡
	  
	  public String creatorUserID ; //  创建人ID
	  
      public String creatorDate ; // 创建时间
	  
	  public String modifyUserID ; // 修改人ID
	  
	  public String  modifyDate ; // 修改时间
	  
	  public boolean  sysStatus; // 系统状态
	  
	  public String creatorUserName;
	  
	  public int vipcardCount;
	  
	  public String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getVipcardCount() {
		return vipcardCount;
	}

	public void setVipcardCount(int vipcardCount) {
		this.vipcardCount = vipcardCount;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPreferentialType() {
		return preferentialType;
	}

	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}

	public String getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(String exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public String getExchangeIphone() {
		return exchangeIphone;
	}

	public void setExchangeIphone(String exchangeIphone) {
		this.exchangeIphone = exchangeIphone;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getIsMakeCard() {
		return isMakeCard;
	}

	public void setIsMakeCard(String isMakeCard) {
		this.isMakeCard = isMakeCard;
	}

	public String getCreatorUserID() {
		return creatorUserID;
	}

	public void setCreatorUserID(String creatorUserID) {
		this.creatorUserID = creatorUserID;
	}

	public String getCreatorDate() {
		return creatorDate;
	}

	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}

	public String getModifyUserID() {
		return modifyUserID;
	}

	public void setModifyUserID(String modifyUserID) {
		this.modifyUserID = modifyUserID;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public boolean isSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(boolean sysStatus) {
		this.sysStatus = sysStatus;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	public VIPCardInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VIPCardInfo(String cardID, String password, String preferentialType,
			String batchNumber, String startTime, String endTime,
			String isExchange, String exchangeTime, String exchangeIphone,
			String cardStatus, String isMakeCard, String creatorUserID,
			String creatorDate, String modifyUserID, String modifyDate,
			boolean sysStatus, String creatorUserName) {
		super();
		this.cardID = cardID;
		this.password = password;
		this.preferentialType = preferentialType;
		this.batchNumber = batchNumber;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isExchange = isExchange;
		this.exchangeTime = exchangeTime;
		this.exchangeIphone = exchangeIphone;
		this.cardStatus = cardStatus;
		this.isMakeCard = isMakeCard;
		this.creatorUserID = creatorUserID;
		this.creatorDate = creatorDate;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
		this.sysStatus = sysStatus;
		this.creatorUserName = creatorUserName;
	}

	  
	
	  
}
