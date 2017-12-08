package com.Manage.entity;



/**
 * SIMInfo.java
 * @author tangming@easy2go.cn 2015-5-27
 *
 */
public class VirtualSIMInfo {
	/** SIM卡ID **/
	public String SIMinfoID;

	/** 高速/低速（0高速，1低速）**/
	public int speedType;
	/** 预约标识(0没 预约 1预约) **/
	public int subscribeTag;
	/** 手动选网(空字符串表示不手动选网) **/
	public String selnet;

	/** SIM卡服务器ID **/
	public String SIMServerID;
	/** 服务器IP **/
	public String serverIP;
	/** 卡槽编号 **/
	public String slotNumber;

	/** sim卡使用类型(本地卡/漫游卡) **/
	public String SIMCategory;
	/** ICCID **/
	public String ICCID;
	/** IMSI(唯一标识) **/
	public String IMSI;
	/** 运营商编码(IMSI中截取[7:8]位) 索引起于1 **/
	public String MNC;
	/** IMSI截取[4:6]位 索引起于1 **/
	public String MCC;
	/** 国家列表 对应 目前后台可用国家 **/
	public String countryList;
	/** 运营商/品牌 **/
	public String trademark;
	/** 卡手机号码 **/
	public String phone;
	/** 注册信息 **/
	public String registerInfo;
	/** PIN*/
	public String PIN;
	/** PUK **/
	public String PUK;
	/** APN **/
	public String APN;
	/** SIM卡类型(sim, usim) **/
	public String SIMType;
	/** 套餐类型 **/
	public String planType;
	/** 套餐金额 **/
	public double planPrice;
	/** 套餐是否激活 **/
	public String planIfActivated;
	/** 套餐激活日期 **/
	public String planActivateDate;
	/** 套餐截止日期 **/
	public String planEndDate;
	/** 卡激活时间 **/
	public String SIMIfActivated;
	/** 卡激活时间 **/
	public String SIMActivateDate;
	/** 卡截止日期 **/
	public String SIMEndDate;
	/** 充值日期 **/
	public String rechargeDate;
	/** 充值后有效日期 **/
	public String rechargedValidDate;
	/** 卡内初始余额 **/
	public double cardInitialBalance;
	/** 卡内余额 **/
	public double cardBalance;
	/** 卡状态 **/
	public String cardStatus;
	/** 套餐激活代码 **/
	public String planActivateCode;
	/** 卡激活代码 **/
	public String simActivateCode;
	/** 查询方法说明 // 按issues #39 查询和充值方法说明合并为余额/流量操作方法说明 **/
	public String queryMethod;
	/** 充值方法说明 **/
	public String rechargeMethod;
	/** 套餐流量大小KB 可能不通过直接输入, 从套餐类型里确定数值 **/
	public int planData;
	/** 剩余流量大小KB **/
	public int planRemainData;
	/** 上一次分配到的设置SN **/
	public String lastDeviceSN;
	/** 限速大小(0则不限速) **/
	public int speedLimit;
	/** SIM卡结算方式(预付费/后付费) **/
	public String simBillMethod;
	/** SIM卡代号, 内部使用 **/
	public String simAlias;

	/**用来做查询条件**/
	public String begindate;
	public String enddate;

	/**标注**/
	public String notes;

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

	/**累计使用流量**/
	public long historyUsedFlow;
	
	public int tag;
	
	/**是否支持漫游**/
	public int ifRoam;
	
	public String IMEI="0";
	public String VPN;
	
	
	/****/
	public String IPAndPort;
	public String countryName;
	public int ifBoundSN;
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public int getIfBoundSN() {
		return ifBoundSN;
	}
	public void setIfBoundSN(int ifBoundSN) {
		this.ifBoundSN = ifBoundSN;
	}
	public String getVPN() {
		return VPN;
	}
	public void setVPN(String vPN) {
		VPN = vPN;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getIPAndPort() {
		return IPAndPort;
	}
	public void setIPAndPort(String iPAndPort) {
		IPAndPort = iPAndPort;
	}
	public int getIfRoam() {
		return ifRoam;
	}
	public void setIfRoam(int ifRoam) {
		this.ifRoam = ifRoam;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public long getHistoryUsedFlow() {
		return historyUsedFlow;
	}
	public void setHistoryUsedFlow(long historyUsedFlow) {
		this.historyUsedFlow = historyUsedFlow;
	}
	public String getSIMinfoID() {
		return SIMinfoID;
	}
	public void setSIMinfoID(String sIMinfoID) {
		SIMinfoID = sIMinfoID;
	}
	public String getSIMServerID() {
		return SIMServerID;
	}
	public void setSIMServerID(String sIMServerID) {
		SIMServerID = sIMServerID;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getSlotNumber() {
		return slotNumber;
	}
	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}
	public String getSIMCategory() {
		return SIMCategory;
	}
	public void setSIMCategory(String sIMCategory) {
		SIMCategory = sIMCategory;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getMNC() {
		return MNC;
	}
	public void setMNC(String mNC) {
		MNC = mNC;
	}
	public String getMCC() {
		return MCC;
	}
	public void setMCC(String mCC) {
		MCC = mCC;
	}
	public String getCountryList() {
		return countryList;
	}
	public void setCountryList(String countryList) {
		this.countryList = countryList;
	}
	public String getTrademark() {
		return trademark;
	}
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegisterInfo() {
		return registerInfo;
	}
	public void setRegisterInfo(String registerInfo) {
		this.registerInfo = registerInfo;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	public String getPUK() {
		return PUK;
	}
	public void setPUK(String pUK) {
		PUK = pUK;
	}
	public String getAPN() {
		return APN;
	}
	public void setAPN(String aPN) {
		APN = aPN;
	}
	public String getSIMType() {
		return SIMType;
	}
	public void setSIMType(String sIMType) {
		SIMType = sIMType;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public double getPlanPrice() {
		return planPrice;
	}
	public void setPlanPrice(double planPrice) {
		this.planPrice = planPrice;
	}
	public String getPlanIfActivated() {
		return planIfActivated;
	}
	public void setPlanIfActivated(String planIfActivated) {
		this.planIfActivated = planIfActivated;
	}
	public String getPlanActivateDate() {
		return planActivateDate;
	}
	public void setPlanActivateDate(String planActivateDate) {
		this.planActivateDate = planActivateDate;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getSIMIfActivated() {
		return SIMIfActivated;
	}
	public void setSIMIfActivated(String sIMIfActivated) {
		SIMIfActivated = sIMIfActivated;
	}
	public String getSIMActivateDate() {
		return SIMActivateDate;
	}
	public void setSIMActivateDate(String sIMActivateDate) {
		SIMActivateDate = sIMActivateDate;
	}
	public String getSIMEndDate() {
		return SIMEndDate;
	}
	public void setSIMEndDate(String sIMEndDate) {
		SIMEndDate = sIMEndDate;
	}
	public String getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public String getRechargedValidDate() {
		return rechargedValidDate;
	}
	public void setRechargedValidDate(String rechargedValidDate) {
		this.rechargedValidDate = rechargedValidDate;
	}
	public double getCardInitialBalance() {
		return cardInitialBalance;
	}
	public void setCardInitialBalance(double cardInitialBalance) {
		this.cardInitialBalance = cardInitialBalance;
	}
	public double getCardBalance() {
		return cardBalance;
	}
	public void setCardBalance(double cardBalance) {
		this.cardBalance = cardBalance;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getPlanActivateCode() {
		return planActivateCode;
	}
	public void setPlanActivateCode(String planActivateCode) {
		this.planActivateCode = planActivateCode;
	}
	public String getSimActivateCode() {
		return simActivateCode;
	}
	public void setSimActivateCode(String simActivateCode) {
		this.simActivateCode = simActivateCode;
	}
	public String getQueryMethod() {
		return queryMethod;
	}
	public void setQueryMethod(String queryMethod) {
		this.queryMethod = queryMethod;
	}
	public String getRechargeMethod() {
		return rechargeMethod;
	}
	public void setRechargeMethod(String rechargeMethod) {
		this.rechargeMethod = rechargeMethod;
	}
	public int getPlanData() {
		return planData;
	}
	public void setPlanData(int planData) {
		this.planData = planData;
	}
	public int getPlanRemainData() {
		return planRemainData;
	}
	public void setPlanRemainData(int planRemainData) {
		this.planRemainData = planRemainData;
	}
	public String getLastDeviceSN() {
		return lastDeviceSN;
	}
	public void setLastDeviceSN(String lastDeviceSN) {
		this.lastDeviceSN = lastDeviceSN;
	}
	public int getSpeedLimit() {
		return speedLimit;
	}
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}
	public String getSimBillMethod() {
		return simBillMethod;
	}
	public void setSimBillMethod(String simBillMethod) {
		this.simBillMethod = simBillMethod;
	}
	public String getSimAlias() {
		return simAlias;
	}
	public void setSimAlias(String simAlias) {
		this.simAlias = simAlias;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public int getSpeedType() {
		return speedType;
	}
	public void setSpeedType(int speedType) {
		this.speedType = speedType;
	}
	public int getSubscribeTag() {
		return subscribeTag;
	}
	public void setSubscribeTag(int subscribeTag) {
		this.subscribeTag = subscribeTag;
	}
	public String getSelnet() {
		return selnet;
	}
	public void setSelnet(String selnet) {
		this.selnet = selnet;
	}

}
