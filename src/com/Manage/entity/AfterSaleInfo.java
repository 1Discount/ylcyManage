package com.Manage.entity;

/**
 * 售后服务记录表
 * @author 沈超
 *
 */
public class AfterSaleInfo {

	/**
	 * ID
	 */
	private String asiID;

	/**
	 * 相关联流量订单ID
	 */
	private String flowDealID;

	/**
	 * 问题类型
	 */
	private String problemType;

	/**
	 * 问题级别
	 */
	private String problemLevel;

	/**
	 * 处理描述
	 */
	private String handleDescription;

	/**
	 * 处理结果(已解决,未解决,退款，退货)
	 */
	private String handleResult;

	/**
	 * 退款金额
	 */
	private Double refundAmount=null;
	
	/**
	 * 问题发生时间
	 */
	private String problemTime;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建人ID
	 */
	private String creatorUserID;
	
	/**
	 * 创建人姓名
	 */
	private String creatorUserName;
	
	/**
	 * 创建时间
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
	private Boolean sysStatus=null;
	
	private String beginDate;
	 
	private String endDate;

	public String getBeginDate()
	{
		return beginDate;
	}

	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getAsiID() {
		return asiID;
	}

	public void setAsiID(String asiID) {
		this.asiID = asiID;
	}

	public String getFlowDealID() {
		return flowDealID;
	}

	public void setFlowDealID(String flowDealID) {
		this.flowDealID = flowDealID;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getProblemLevel() {
		return problemLevel;
	}

	public void setProblemLevel(String problemLevel) {
		this.problemLevel = problemLevel;
	}

	public String getHandleDescription() {
		return handleDescription;
	}

	public void setHandleDescription(String handleDescription) {
		this.handleDescription = handleDescription;
	}

	public String getHandleResult() {
		return handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getProblemTime() {
		return problemTime;
	}

	public void setProblemTime(String problemTime) {
		this.problemTime = problemTime;
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

	public Boolean getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(Boolean sysStatus) {
		this.sysStatus = sysStatus;
	}
}
