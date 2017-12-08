package com.Manage.entity;

/**
 * SimRechargeBill.java
 * @author tangming@easy2go.cn 2015-5-26
 *
 */
public class SIMRechargeBill {
	/** SIM卡充值记录ID **/
	public String rechargeBillID;
	/** sim卡使用类型(本地卡/漫游卡) **/
	public String SIMCategory;
	/** 充值SIM卡ID **/
	public String SIMinfoID;
	/** IMSI (唯一标识) 可允许空白, 在 imsi 未设置前**/
	public String IMSI;
	/** 充值金额 **/
	public double rechargeAmount;
	/** 充值后有效日期 **/
	public String rechargedValidDate;

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
	
	/** query helper 用来做查询条件 join查询结果等 **/
	public String begindate;
	public String enddate;
	/** 国家编号 MCC, 对应 SIMInfo MCC 卡的发行国家 **/
	public String MCC;
	/** 国家名称 **/
	public String countryName;
	/** 运营商/品牌, 对应 SIMInfo trademark, 对应 OperatorInfo operatorName **/
	public String operatorName;
	
	public String getRechargeBillID() {
		return rechargeBillID;
	}
	public void setRechargeBillID(String rechargeBillID) {
		this.rechargeBillID = rechargeBillID;
	}
	public String getSIMCategory() {
		return SIMCategory;
	}
	public void setSIMCategory(String sIMCategory) {
		SIMCategory = sIMCategory;
	}
	public String getSIMinfoID() {
		return SIMinfoID;
	}
	public void setSIMinfoID(String sIMinfoID) {
		SIMinfoID = sIMinfoID;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getRechargedValidDate() {
		return rechargedValidDate;
	}
	public void setRechargedValidDate(String rechargedValidDate) {
		this.rechargedValidDate = rechargedValidDate;
	}
	public double getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
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
	public String getMCC() {
		return MCC;
	}
	public void setMCC(String mCC) {
		MCC = mCC;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
