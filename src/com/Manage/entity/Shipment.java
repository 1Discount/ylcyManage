package com.Manage.entity;

public class Shipment {
	/** 出库记录ID **/
	private String shipmentID;
	/** 总订单ID **/
	private String orderID;
	/** 设备序列号 **/
	private String SN;
	/** 出货时间 **/
	private String shipmentDate;
	/** 快递公司名称 **/
	private String logisticsName;
	/** 快递公司简称 **/
	private String logisticsJC;
	/** 运费 **/
	private String LogisticsCost;
	/** 快递单号 **/
	private String expressNO;
	/** 批次号 **/
	private String batchNO;
	private String remark;
	private String creatorUserID;
	private String creatorUserName;
	private String creatorDate;
	private String modifyUserID;
	private String modifyDate;
	private String sysStatus;

	/**出入库状态**/
	private String deviceStatus;
	/**
	 * 出货地址
	 */
	private String address;


	public String getDeviceStatus()
	{
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus)
	{
		this.deviceStatus = deviceStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShipmentID() {
		return shipmentID;
	}

	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getLogisticsJC() {
		return logisticsJC;
	}

	public void setLogisticsJC(String logisticsJC) {
		this.logisticsJC = logisticsJC;
	}

	public String getLogisticsCost() {
		return LogisticsCost;
	}

	public void setLogisticsCost(String logisticsCost) {
		LogisticsCost = logisticsCost;
	}

	public String getExpressNO() {
		return expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public String getBatchNO() {
		return batchNO;
	}

	public void setBatchNO(String batchNO) {
		this.batchNO = batchNO;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatorUserID() {
		return creatorUserID;
	}

	public void setCreatorUserID(String creatorUserID) {
		this.creatorUserID = creatorUserID;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
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

	public String getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}

	public Shipment(String shipmentID, String orderID, String sN,
			String shipmentDate, String logisticsName, String logisticsJC,
			String logisticsCost, String expressNO, String batchNO,
			String remark, String creatorUserID, String creatorUserName,
			String creatorDate, String modifyUserID, String modifyDate,
			String sysStatus) {
		super();
		this.shipmentID = shipmentID;
		this.orderID = orderID;
		SN = sN;
		this.shipmentDate = shipmentDate;
		this.logisticsName = logisticsName;
		this.logisticsJC = logisticsJC;
		LogisticsCost = logisticsCost;
		this.expressNO = expressNO;
		this.batchNO = batchNO;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.creatorUserName = creatorUserName;
		this.creatorDate = creatorDate;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
		this.sysStatus = sysStatus;
	}

	public Shipment() {
		super();
	}


	public String endTime;
	public String begainTime;

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBegainTime() {
		return begainTime;
	}

	public void setBegainTime(String begainTime) {
		this.begainTime = begainTime;
	}

	public String recipientName;

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String chuchu;
	public String deviceCardType;


	public String getChuchu() {
		return chuchu;
	}

	public void setChuchu(String chuchu) {
		this.chuchu = chuchu;
	}

	public String getDeviceCardType() {
		return deviceCardType;
	}

	public void setDeviceCardType(String deviceCardType) {
		this.deviceCardType = deviceCardType;
	}





}
