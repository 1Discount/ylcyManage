package com.Manage.entity;

/**
 * IP电话clent表，记录手机号申请的clent记录
 * @author lipeng
 *
 */
public class IpPhoneClient {

	private String id;
	private String customerID;
	private String customerPhone;
	private String clientName;
	private String clientNumber;
	private String clientPwd;
	private String balance;
	private String charge;
	private String createDate;
	private String remark;
	private String sysStatus;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getClientPwd() {
		return clientPwd;
	}
	public void setClientPwd(String clientPwd) {
		this.clientPwd = clientPwd;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	@Override
	public String toString() {
		return "IpPhoneClient [id=" + id + ", customerID=" + customerID
				+ ", customerPhone=" + customerPhone + ", clientName="
				+ clientName + ", clientNumber=" + clientNumber
				+ ", clientPwd=" + clientPwd + ", balance=" + balance
				+ ", charge=" + charge + ", createDate=" + createDate
				+ ", remark=" + remark + ", sysStatus=" + sysStatus
				+ ", getRemark()=" + getRemark() + ", getId()=" + getId()
				+ ", getCustomerID()=" + getCustomerID()
				+ ", getCustomerPhone()=" + getCustomerPhone()
				+ ", getClientName()=" + getClientName()
				+ ", getClientNumber()=" + getClientNumber()
				+ ", getClientPwd()=" + getClientPwd() + ", getBalance()="
				+ getBalance() + ", getCreateDate()=" + getCreateDate()
				+ ", getSysStatus()=" + getSysStatus() + ", getCharge()="
				+ getCharge() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
