package com.Manage.entity;

/**
 * 退款记录
 * @author Administrator
 *
 */
public class Refund {

	/**id**/
	public String refundID; 
	/**客户id**/
	public String customerID;
	/**订单id**/
	public String orderID;
	/**支付宝账号**/
	public String aliPayID;
	/** 支付宝交易号**/
	public String aliPayTradeNo;
	/**退款金额**/
	public String refundMoney;
	/**备注**/
	public String remark;
	/**退款责任人**/
	public String refundMoneyPeople;
	/**退款时间**/
	public String refundMoneyTime;
	/**实退金额**/
	public String refundMoneyTrue;
	public String sysStatus;
	/**退款状态（等待退款、退款成功）**/
	public String refundStatus;
	/**退款批次号**/
	public String WIDbatch_no;
	/**客户姓名**/
	public String customerName;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getWIDbatch_no() {
		return WIDbatch_no;
	}
	public void setWIDbatch_no(String wIDbatch_no) {
		WIDbatch_no = wIDbatch_no;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getAliPayTradeNo() {
		return aliPayTradeNo;
	}
	public void setAliPayTradeNo(String aliPayTradeNo) {
		this.aliPayTradeNo = aliPayTradeNo;
	}
	public String getRefundMoneyTrue() {
		return refundMoneyTrue;
	}
	public void setRefundMoneyTrue(String refundMoneyTrue) {
		this.refundMoneyTrue = refundMoneyTrue;
	}
	public String getRefundID() {
		return refundID;
	}
	public void setRefundID(String refundID) {
		this.refundID = refundID;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getAliPayID() {
		return aliPayID;
	}
	public void setAliPayID(String aliPayID) {
		this.aliPayID = aliPayID;
	}
	public String getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRefundMoneyPeople() {
		return refundMoneyPeople;
	}
	public void setRefundMoneyPeople(String refundMoneyPeople) {
		this.refundMoneyPeople = refundMoneyPeople;
	}
	public String getRefundMoneyTime() {
		return refundMoneyTime;
	}
	public void setRefundMoneyTime(String refundMoneyTime) {
		this.refundMoneyTime = refundMoneyTime;
	}
	public String getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}
	
	
}
