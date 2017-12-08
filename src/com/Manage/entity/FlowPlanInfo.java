package com.Manage.entity;

public class FlowPlanInfo {
	/** 套餐ID **/
	public String flowPlanID;
	/** 套餐名称 **/
	public String flowPlanName;
	/** 相关流量交易数 **/
	public String flowDealCount;
	/** 是否限制国家 **/
	public String ifCountryLimit;
	/** 是否限制时间 **/
	public String ifTimeLimit;
	/** 是否限制流量 **/
	public String ifFlowLimit;
	/** 国家列表 (目前设计为内嵌) **/
	public String countryList;
	/** 套餐类型(天数或流量或自定义套餐) **/
	public String planType;
	/** 天数 **/
	public int planDays;
	/** 数据流量 单位: KB 前端使用MB输入/显示 **/
	public int flowSum;
	/** 数据流量预警阀值 单位: KB 前端使用MB输入/显示 **/
	public int flowAlert;
	/** 默认限速大小 单位: KB/s **/
	public String speedLimit;
	/** 套餐购买后最长可激活使用的天数 **/
	public int validPeriod;
	/** 金额 单位: 元 **/
	public double planPrice;
	/** 套餐备注 **/
	public String note;
	/** 套餐图片相对地址 **/
	public String imageUrl;
	/**备注**/
	public String remark;
	/**创建人ID**/
	public String creatorUserID;
	/**创建人姓名**/
	public String creatorUserName;
	/**创建时间**/
	public String creatorDate;
	/**修改人ID**/
	public String modifyUserID;
	/**修改时间 **/
	public String modifyDate;
	/**系统状态**/
	public int sysStatus;
	
	public String orderType;
	
	public String DeviceDealCount;
	
	public String userDayList;
	
	public String getUserDayList() {
		return userDayList;
	}
	public void setUserDayList(String userDayList) {
		this.userDayList = userDayList;
	}
	public int getPlanDays() {
		return planDays;
	}
	public void setPlanDays(int planDays) {
		this.planDays = planDays;
	}
	public String getFlowPlanID() {
		return flowPlanID;
	}
	public void setFlowPlanID(String flowPlanID) {
		this.flowPlanID = flowPlanID;
	}
	public String getFlowPlanName() {
		return flowPlanName;
	}
	public void setFlowPlanName(String flowPlanName) {
		this.flowPlanName = flowPlanName;
	}
	public String getFlowDealCount() {
		return flowDealCount;
	}
	public void setFlowDealCount(String flowDealCount) {
		this.flowDealCount = flowDealCount;
	}
	public String getIfCountryLimit() {
		return ifCountryLimit;
	}
	public void setIfCountryLimit(String ifCountryLimit) {
		this.ifCountryLimit = ifCountryLimit;
	}
	public String getIfTimeLimit() {
		return ifTimeLimit;
	}
	public void setIfTimeLimit(String ifTimeLimit) {
		this.ifTimeLimit = ifTimeLimit;
	}
	public String getIfFlowLimit() {
		return ifFlowLimit;
	}
	public void setIfFlowLimit(String ifFlowLimit) {
		this.ifFlowLimit = ifFlowLimit;
	}
	public String getCountryList() {
		return countryList;
	}
	public void setCountryList(String countryList) {
		this.countryList = countryList;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public int getFlowSum() {
		return flowSum;
	}
	public void setFlowSum(int flowSum) {
		this.flowSum = flowSum;
	}
	public int getFlowAlert() {
		return flowAlert;
	}
	public void setFlowAlert(int flowAlert) {
		this.flowAlert = flowAlert;
	}
	public String getSpeedLimit() {
		return speedLimit;
	}
	public void setSpeedLimit(String speedLimit) {
		this.speedLimit = speedLimit;
	}
	public int getValidPeriod() {
		return validPeriod;
	}
	public void setValidPeriod(int validPeriod) {
		this.validPeriod = validPeriod;
	}
	public double getPlanPrice() {
		return planPrice;
	}
	public void setPlanPrice(double planPrice) {
		this.planPrice = planPrice;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	public int getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(int sysStatus) {
		this.sysStatus = sysStatus;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getDeviceDealCount() {
		return DeviceDealCount;
	}
	public void setDeviceDealCount(String deviceDealCount) {
		DeviceDealCount = deviceDealCount;
	}




	
	

}
