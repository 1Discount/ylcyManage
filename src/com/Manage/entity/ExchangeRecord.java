package com.Manage.entity;

public class ExchangeRecord {

	/**记录ID **/
	String recordId;
	/**兑换手机号 **/
	String phone;
	/**兑换用户ID **/
	String customerID;
	/**兑换用户名 **/
	String customerName;
	/**兑换码ID **/
	Integer keyId;
	/**兑换码 **/
	String key;
	/**相关充值记录ID **/
	String rechargeId;
	/**金额 **/
	Double amount;
	/**类型(基本，表示充入基本帐户/流量，表示充入流量帐户) **/
	String type;

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

	/** 附带返回给前端的客户流量帐户总额等信息 **/
	public Double totalFlowAmount;
	/** 基本账户余额 **/
	public Double totalBasicAmount;
	/** 话费余额 **/
	public Double totalPhoneMoneyAmount;

	/** 兑换过程中需要的结果标记，例如兑换已更新了流量余额，但插入兑换记录时失败
	 * 列表："EXCHANGE_RECORD_INSERT_FAILED"
	 */
	public String flag;

	/** 查询参数 **/
	/**开始时间 **/
	String startDate;
	/**结束时间 **/
	String endDate;

	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public Integer getKeyId() {
		return keyId;
	}
	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Double getTotalFlowAmount() {
		return totalFlowAmount;
	}
	public void setTotalFlowAmount(Double totalFlowAmount) {
		this.totalFlowAmount = totalFlowAmount;
	}
	public Double getTotalBasicAmount() {
		return totalBasicAmount;
	}
	public void setTotalBasicAmount(Double totalBasicAmount) {
		this.totalBasicAmount = totalBasicAmount;
	}
	public Double getTotalPhoneMoneyAmount() {
		return totalPhoneMoneyAmount;
	}
	public void setTotalPhoneMoneyAmount(Double totalPhoneMoneyAmount) {
		this.totalPhoneMoneyAmount = totalPhoneMoneyAmount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
