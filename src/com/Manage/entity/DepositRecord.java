package com.Manage.entity;

/**
 * DepositRecord.java
 * @author tangming@easy2go.cn 2015-8-4
 *
 */
public class DepositRecord {
	/** 退押金申请ID **/
	public String recordID;
	/** 订单ID **/
	public String orderID;
	/** 设备订单中的SN **/
	public String SN;
	/** 客户ID **/
	public String customerID;
	/** 客户姓名 **/
	public String customerName;
	/** 客户电话 **/
	public String phone;
	/** 押金金额 **/
	public double dealAmount;
	/** 状态(等待处理，处理中，退款成功) **/
	public String status;
	/** 快递名称 **/
	public String expressName;
	/** 快递单号 **/
	public String expressNum;
	/** 使用评价 **/
	public String comment;
	/** 买家秀图片地址1 **/
	public String show1ImgSrc;
	/** 买家秀图片地址2 **/
	public String show2ImgSrc;
	/** 买家秀图片地址3 **/
	public String show3ImgSrc;
	/** 买家秀图片地址4 **/
	public String show4ImgSrc;
	/** 买家秀图片地址5 **/
	public String show5ImgSrc;

	/**备注**/
	public String remark;
	/**创建人ID**/
	public String creatorUserID;
	/**创建时间**/
	public String creatorDate;
	/**创建人姓名**/
	public String creatorUserName;
	/**修改人ID**/
	public String modifyUserID;
	/**修改时间 **/
	public String modifyDate;
	/**系统状态**/
	public int sysStatus;

	/**以便传递请求参数**/
	public int limit;
	public int offset;

	/** join查询结果等 **/
	boolean useJoint;

	/** 下面系 join 结果所用到 **/
	/**设备ID **/
	public String deviceID;
	/**交易类型（购买或租用） **/
	public String deallType;
	/**是否归还 **/
	public String ifReturn;
	/**订单是否完成 **/
	public String IfFinish;
	
	/** 支付宝交易号**/
	public String aliPayTradeNo;
	/**实退金额**/
	public String refundMoney;
	/** 退款批次号 **/
	public String WIDbatch_no;
	

	public String getWIDbatch_no() {
		return WIDbatch_no;
	}
	public void setWIDbatch_no(String wIDbatch_no) {
		WIDbatch_no = wIDbatch_no;
	}
	public String getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}
	public String getAliPayTradeNo() {
		return aliPayTradeNo;
	}
	public void setAliPayTradeNo(String aliPayTradeNo) {
		this.aliPayTradeNo = aliPayTradeNo;
	}
	public String getRecordID() {
		return recordID;
	}
	public void setRecordID(String recordID) {
		this.recordID = recordID;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(double dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressNum() {
		return expressNum;
	}
	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getShow1ImgSrc() {
		return show1ImgSrc;
	}
	public void setShow1ImgSrc(String show1ImgSrc) {
		this.show1ImgSrc = show1ImgSrc;
	}
	public String getShow2ImgSrc() {
		return show2ImgSrc;
	}
	public void setShow2ImgSrc(String show2ImgSrc) {
		this.show2ImgSrc = show2ImgSrc;
	}
	public String getShow3ImgSrc() {
		return show3ImgSrc;
	}
	public void setShow3ImgSrc(String show3ImgSrc) {
		this.show3ImgSrc = show3ImgSrc;
	}
	public String getShow4ImgSrc() {
		return show4ImgSrc;
	}
	public void setShow4ImgSrc(String show4ImgSrc) {
		this.show4ImgSrc = show4ImgSrc;
	}
	public String getShow5ImgSrc() {
		return show5ImgSrc;
	}
	public void setShow5ImgSrc(String show5ImgSrc) {
		this.show5ImgSrc = show5ImgSrc;
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
	public String getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getCreatorUserName() {
		return creatorUserName;
	}
	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
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
	public int getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(int sysStatus) {
		this.sysStatus = sysStatus;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public boolean isUseJoint() {
		return useJoint;
	}
	public void setUseJoint(boolean useJoint) {
		this.useJoint = useJoint;
	}

	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getDeallType() {
		return deallType;
	}
	public void setDeallType(String deallType) {
		this.deallType = deallType;
	}
	public String getIfReturn() {
		return ifReturn;
	}
	public void setIfReturn(String ifReturn) {
		this.ifReturn = ifReturn;
	}
	public String getIfFinish() {
		return IfFinish;
	}
	public void setIfFinish(String ifFinish) {
		IfFinish = ifFinish;
	}

	public DepositRecord() {}

	public DepositRecord(String recordID, String orderID, String customerID, String customerName, String phone,
			double dealAmount, String status, String expressName, String expressNum, String comment,
			String show1ImgSrc, String show2ImgSrc, String show3ImgSrc, String show4ImgSrc, String show5ImgSrc,
			String remark, String creatorUserID, String creatorDate, String creatorUserName, String modifyUserID,
			String modifyDate, int sysStatus) {
		super();
		this.recordID = recordID;
		this.orderID = orderID;
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.dealAmount = dealAmount;
		this.status = status;
		this.expressName = expressName;
		this.expressNum = expressNum;
		this.comment = comment;
		this.show1ImgSrc = show1ImgSrc;
		this.show2ImgSrc = show2ImgSrc;
		this.show3ImgSrc = show3ImgSrc;
		this.show4ImgSrc = show4ImgSrc;
		this.show5ImgSrc = show5ImgSrc;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.creatorDate = creatorDate;
		this.creatorUserName = creatorUserName;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
		this.sysStatus = sysStatus;
	}

}
