package com.Manage.entity;

import java.util.Date;
/**
 * 客户信息类
 * @author lipeng
 * 
 */
public class CustomerInfo {
	
	/**
	 * 客户ID
	 */
	private String customerID;
	/**
	 * 内部用户名唯一,备用
	 */
	private String username;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 客户电话
	 */
	private String phone;
	/**
	 * 客户登陆官网密码
	 */
	private String password;
	/**
	 * 客户email
	 */
	private String email;
	/**
	 * 客户收货地址
	 */
	private String address;
	/**
	 * 客户来源
	 */
	private String customerSource;
	/**
	 * 流量订单总数
	 */	
	private int flowOrderCount;
	/**
	 * 设备订单总数
	 */
	private int deviceOrderCount;
	/**
	 * 设备订单总数
	 */
	private int orderCount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人ID
	 */
	private String creatorUserID;
	/**
	 *创建时间
	 */
	private String creatorDate;
	/**
	 * 修改人ID
	 */
	private String modifyUserID;
	/**
	 * 修改时间
	 */
	private String modifyDate;
	/**
	 * 系统状态
	 */
	private int sysStatus;
	/**
	 * 渠道商ID
	 */
	private String distributorID;
	/**
	 * 渠道商姓名
	 */
	private String distributorName;
	
	/**
	 * 客户类型
	 */
	private String customerType;
	
	/**是否是vip用户**/
	private String isVIP;
	
	public String getIsVIP() {
		return isVIP;
	}
	public void setIsVIP(String isVIP) {
		this.isVIP = isVIP;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDistributorID() {
		return distributorID;
	}
	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public CustomerInfo() {}
	public CustomerInfo(String customerID) {this.customerID = customerID;}
 
	public CustomerInfo(String phone, String email) {
		super();
		this.phone = phone;
		this.email = email;
	}
	/**
	 * 插入 对应方法 insertCustomerinfo
	 */
	public CustomerInfo(String customerID, String customerName, String phone,String password,
			 String email, String address, String customerSource,
			String remark, String creatorUserID, String creatorDate, int sysStatus) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.password = password;
		this.email = email;
		this.address = address;
		this.customerSource = customerSource;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.creatorDate = creatorDate;
		this.sysStatus = sysStatus;
	}
	
	public CustomerInfo(String customerID, String customerName, String phone,
			 String email, String address, String customerSource,
			String remark, String creatorUserID, String creatorDate, int sysStatus) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.customerSource = customerSource;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.creatorDate = creatorDate;
		this.sysStatus = sysStatus;
	}
	
	/**
	 * 修改客户信息
	 * toUpdateCustomer
	 */
	public CustomerInfo( String customerID, String customerName, String phone,String email, String address,String remark, String modifyUserID, String modifyDate,String isVIP) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.email = email;
		this.remark = remark;
		this.address = address;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
		this.isVIP = isVIP;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustomerSource() {
		return customerSource;
	}
	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	public int getFlowOrderCount() {
		return flowOrderCount;
	}
	public void setFlowOrderCount(int flowOrderCount) {
		this.flowOrderCount = flowOrderCount;
	}
	public int getDeviceOrderCount() {
		return deviceOrderCount;
	}
	public void setDeviceOrderCount(int deviceOrderCount) {
		this.deviceOrderCount = deviceOrderCount;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
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
	@Override
	public String toString() {
		return "CustomerInfo [customerID=" + customerID + ", customerName="
				+ customerName + ", phone=" + phone + ", password=" + password
				+ ", email=" + email + ", address=" + address
				+ ", customerSource=" + customerSource + ", flowOrderCount="
				+ flowOrderCount + ", deviceOrderCount=" + deviceOrderCount
				+ ", orderCount=" + orderCount + ", remark=" + remark
				+ ", creatorUserID=" + creatorUserID + ", creatorDate="
				+ creatorDate + ", modifyUserID=" + modifyUserID
				+ ", modifyDate=" + modifyDate + ", sysStatus=" + sysStatus
				+ ", distributorID=" + distributorID + ", distributorName="
				+ distributorName + "]";
	}
	
	public String allCount;//总记录数
	public String deleteCount;//已删除总数
	//客户来源
	public String resourceWeb;//官网
	public String resourceApp;//app
	public String resourceTaobao;//淘宝
	public String resourceDis;//渠道商
	public String resourceLuru;
	
	public String begindate;//开始时间（统计 查询条件）
	public String enddate;//结束时间
	public String sysStatus2;
	
	
	
	

	
	public String getSysStatus2() {
		return sysStatus2;
	}
	public void setSysStatus2(String sysStatus2) {
		this.sysStatus2 = sysStatus2;
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
	public String getResourceLuru() {
		return resourceLuru;
	}
	public void setResourceLuru(String resourceLuru) {
		this.resourceLuru = resourceLuru;
	}
	public String getResourceDis() {
		return resourceDis;
	}
	public void setResourceDis(String resourceDis) {
		this.resourceDis = resourceDis;
	}
	public String getResourceWeb() {
		return resourceWeb;
	}
	public void setResourceWeb(String resourceWeb) {
		this.resourceWeb = resourceWeb;
	}
	public String getResourceApp() {
		return resourceApp;
	}
	public void setResourceApp(String resourceApp) {
		this.resourceApp = resourceApp;
	}
	public String getResourceTaobao() {
		return resourceTaobao;
	}
	public void setResourceTaobao(String resourceTaobao) {
		this.resourceTaobao = resourceTaobao;
	}
	public String getAllCount() {
		return allCount;
	}
	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	public String getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(String deleteCount) {
		this.deleteCount = deleteCount;
	}
	

}
