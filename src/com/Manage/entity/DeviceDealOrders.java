package com.Manage.entity;

import java.util.Date;

/** * @author  wangbo: * @date 创建时间：2015-5-28 下午5:40:17 * @version 1.0 * @parameter  * @since  * @return  */
public class DeviceDealOrders {
	/**ID **/
	public String deviceDealID;
	/**关联订单ID **/
	public String orderID;
	/**序列号 **/
	public String SN;
	/**设备ID **/
	public String deviceID;
	/** 客户ID**/
	public String customerID;
	/** 客户名称**/
	public String customerName;
	/**交易类型（购买或租用） **/
	public String deallType;
	/** 归还日期**/
	public String returnDate;
	/** 交易总金额**/
	public double dealAmount;
	/**是否归还 **/
	public String ifReturn;
	/**订单是否完成 **/
	public String IfFinish;
	/** 订单状态**/
	public String orderStatus;
	/**备注 **/
	public String remark;
	/**创建人ID **/
	public String creatorUserID;
	/**创建人姓名**/
	public String creatorUserName;
	/**创建时间**/
	public Date  creatorDate;
	/**创建时间String**/
	public String creatorDateString;
	/**修改人ID **/
	public String modifyUserID;
	/**修改时间 **/
	public Date modifyDate;
	/**修改时间String**/
	public String modifyDateString;
	/**系统状态 **/
	public boolean sysStatus;
	/**用来做查询条件**/
	public String begindate;
	public String enddate;
	/****/
	public String deviceStatus;
	
	public String oldsn;
	
	public String getOldsn()
	{
		return oldsn;
	}

	public void setOldsn(String oldsn)
	{
		this.oldsn = oldsn;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public DeviceDealOrders(){};
	
	public DeviceDealOrders(String sN, String ifReturn, String modifyUserID,
			Date modifyDate) {
		super();
		SN = sN;
		this.ifReturn = ifReturn;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
	}
	
	

	public String getModifyDateString() {
		return modifyDateString;
	}

	public void setModifyDateString(String modifyDateString) {
		this.modifyDateString = modifyDateString;
	}

	public String getCreatorDateString() {
		return creatorDateString;
	}

	public void setCreatorDateString(String creatorDateString) {
		this.creatorDateString = creatorDateString;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	

	public String getIfFinish() {
		return IfFinish;
	}



	public void setIfFinish(String ifFinish) {
		IfFinish = ifFinish;
	}



	public String getOrderStatus() {
		return orderStatus;
	}



	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}



	public String getDeviceDealID() {
		return deviceDealID;
	}
	public void setDeviceDealID(String deviceDealID) {
		this.deviceDealID = deviceDealID;
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
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDeallType() {
		return deallType;
	}
	public void setDeallType(String deallType) {
		this.deallType = deallType;
	}
	
	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public double getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(double dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getIfReturn() {
		return ifReturn;
	}
	public void setIfReturn(String ifReturn) {
		this.ifReturn = ifReturn;
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
	public Date getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(Date creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getModifyUserID() {
		return modifyUserID;
	}
	public void setModifyUserID(String modifyUserID) {
		this.modifyUserID = modifyUserID;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public boolean isSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(boolean sysStatus) {
		this.sysStatus = sysStatus;
	}
	
}
