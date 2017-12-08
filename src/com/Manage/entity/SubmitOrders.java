package com.Manage.entity;

/**
 * 提工单表
 * @author 沈超
 *
 */
public class SubmitOrders {

	/**
	 * ID
	 */
	private String submitOrdersID;
	
	/**
	 * 问题描述
	 */
	private String description;
	
	/**
	 * 紧急程度
	 */
	private String urgency;
	
	/**
	 * 类型
	 */
	private String problemType;
	
	/**
	 * 提出人ID
	 */
	private String introducerID;
	
	/**
	 * 提出人姓名
	 */
	private String introducerName;
	
	/**
	 * 要求解决截止时间
	 */
	private String deadline;
	
	/**
	 * 设备SN
	 */
	private String SN;
	
	/**
	 * 客户名
	 */
	private String customerName;
	
	/**
	 * IMSI(唯一标识)
	 */
	private String IMSI;
	
	/**
	 * 基站信息
	 */
	private String baseStation;
	
	/**
	 * 工单备注
	 */
	private String ordersRemark;
	
	/**
	 * 指派人姓名
	 */
	private String designeeName;
	
	/**
	 * 状态(待解决/已解决/已打回)
	 */
	private String orderStatus;
	
	/**
	 * 处理人ID
	 */
	private String handleID;
	
	/**
	 * 处理人姓名
	 */
	private String handleName;
	
	/**
	 * 打回原因
	 */
	private String callBackReason;
	
	/**
	 * 解决时间
	 */
	private String solveTime;
	
	/**
	 * 纠正结果
	 */
	private String solveResultDesc;
	
	/**
	 * 纠正措施
	 */
	private String correctiveMeasure;
	
	/**
	 * 预防措施
	 */
	private String preventiveMeasure;
	
	/**
	 * 点评
	 */
	private String solveRemark;
	
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
	private boolean sysStatus;
	
	//自定义属性，作为查询条件
	private String userName;
	private String begainTime;
	private String endTime;

	public String getSubmitOrdersID() {
		return submitOrdersID;
	}

	public void setSubmitOrdersID(String submitOrdersID) {
		this.submitOrdersID = submitOrdersID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getIntroducerID() {
		return introducerID;
	}

	public void setIntroducerID(String introducerID) {
		this.introducerID = introducerID;
	}

	public String getIntroducerName() {
		return introducerName;
	}

	public void setIntroducerName(String introducerName) {
		this.introducerName = introducerName;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}

	public String getBaseStation() {
		return baseStation;
	}

	public void setBaseStation(String baseStation) {
		this.baseStation = baseStation;
	}

	public String getOrdersRemark() {
		return ordersRemark;
	}

	public void setOrdersRemark(String ordersRemark) {
		this.ordersRemark = ordersRemark;
	}

	public String getDesigneeName() {
		return designeeName;
	}

	public void setDesigneeName(String designeeName) {
		this.designeeName = designeeName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getHandleID() {
		return handleID;
	}

	public void setHandleID(String handleID) {
		this.handleID = handleID;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public String getCallBackReason() {
		return callBackReason;
	}

	public void setCallBackReason(String callBackReason) {
		this.callBackReason = callBackReason;
	}

	public String getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(String solveTime) {
		this.solveTime = solveTime;
	}

	public String getSolveResultDesc() {
		return solveResultDesc;
	}

	public void setSolveResultDesc(String solveResultDesc) {
		this.solveResultDesc = solveResultDesc;
	}

	public String getCorrectiveMeasure() {
		return correctiveMeasure;
	}

	public void setCorrectiveMeasure(String correctiveMeasure) {
		this.correctiveMeasure = correctiveMeasure;
	}

	public String getPreventiveMeasure() {
		return preventiveMeasure;
	}

	public void setPreventiveMeasure(String preventiveMeasure) {
		this.preventiveMeasure = preventiveMeasure;
	}

	public String getSolveRemark() {
		return solveRemark;
	}

	public void setSolveRemark(String solveRemark) {
		this.solveRemark = solveRemark;
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

	public boolean getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(boolean sysStatus) {
		this.sysStatus = sysStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBegainTime() {
		return begainTime;
	}

	public void setBegainTime(String begainTime) {
		this.begainTime = begainTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
