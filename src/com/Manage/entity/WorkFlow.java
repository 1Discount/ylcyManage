package com.Manage.entity;

public class WorkFlow {
	/**工作流记录ID**/
	private String workFlowID;
	/**接单ID**/
	private String aoID;
	/**接单人**/
	private String receiveOrderPer;
	/**接单时间**/
	private String receiveOrderDate;
	/**客户名称**/
	private String customerName;
	/**总订单ID**/
	private String orderID;
	/**下单人**/
	private String creatorOrderPer;
	/**下单时间**/
	private String creatorOrderDate;
	/**流量订单ID**/
	private String flowDealOrderID;
	/**出货记录ID**/
	private String shipmentID;
	/**出货人**/
	private String shipmentPer;
	/**出货时间**/
	private String shipmentDate;
	private String logisticsCompany;
	/**快递号ID**/
	private String expressNO;
	/**最新售后记录ID**/
	private String customerServiceID;
	/**最新售后服务人**/
	private String customerServicePer;
	/**最新售后服务时间**/
	private String customerServiceDate;
	/**最新处理结果**/
	private String handleFruit;
	/**当前工作流程状态**/
	private String workFlowStatus;
	/**备注**/
	private String remark;
	
	private String creatorUserID;
	private String creatorUserName;
	private String creatorDate;
	private String modifyUserID;
	private String modifyDate;
	private String sysStatus;
	
	public String getLogisticsCompany() {
		return logisticsCompany;
	}
	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	public String getWorkFlowID() {
		return workFlowID;
	}
	public void setWorkFlowID(String workFlowID) {
		this.workFlowID = workFlowID;
	}
	public String getAoID() {
		return aoID;
	}
	public void setAoID(String aoID) {
		this.aoID = aoID;
	}
	public String getReceiveOrderPer() {
		return receiveOrderPer;
	}
	public void setReceiveOrderPer(String receiveOrderPer) {
		this.receiveOrderPer = receiveOrderPer;
	}
	public String getReceiveOrderDate() {
		return receiveOrderDate;
	}
	public void setReceiveOrderDate(String receiveOrderDate) {
		this.receiveOrderDate = receiveOrderDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getCreatorOrderPer() {
		return creatorOrderPer;
	}
	public void setCreatorOrderPer(String creatorOrderPer) {
		this.creatorOrderPer = creatorOrderPer;
	}
	public String getCreatorOrderDate() {
		return creatorOrderDate;
	}
	public void setCreatorOrderDate(String creatorOrderDate) {
		this.creatorOrderDate = creatorOrderDate;
	}
	public String getFlowDealOrderID() {
		return flowDealOrderID;
	}
	public void setFlowDealOrderID(String flowDealOrderID) {
		this.flowDealOrderID = flowDealOrderID;
	}
	public String getShipmentID() {
		return shipmentID;
	}
	public void setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
	}
	public String getShipmentPer() {
		return shipmentPer;
	}
	public void setShipmentPer(String shipmentPer) {
		this.shipmentPer = shipmentPer;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public String getExpressNO() {
		return expressNO;
	}
	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}
	public String getCustomerServiceID() {
		return customerServiceID;
	}
	public void setCustomerServiceID(String customerServiceID) {
		this.customerServiceID = customerServiceID;
	}
	public String getCustomerServicePer() {
		return customerServicePer;
	}
	public void setCustomerServicePer(String customerServicePer) {
		this.customerServicePer = customerServicePer;
	}
	public String getCustomerServiceDate() {
		return customerServiceDate;
	}
	public void setCustomerServiceDate(String customerServiceDate) {
		this.customerServiceDate = customerServiceDate;
	}
	public String getHandleFruit() {
		return handleFruit;
	}
	public void setHandleFruit(String handleFruit) {
		this.handleFruit = handleFruit;
	}
	public String getWorkFlowStatus() {
		return workFlowStatus;
	}
	public void setWorkFlowStatus(String workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
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
	public String getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}
	
	public WorkFlow(String workFlowID, String aoID, String receiveOrderPer,
			String receiveOrderDate, String customerName, String orderID,
			String creatorOrderPer, String creatorOrderDate,
			String flowDealOrderID, String shipmentID, String shipmentPer,
			String shipmentDate, String logisticsCompany, String expressNO,
			String customerServiceID, String customerServicePer,
			String customerServiceDate, String handleFruit,
			String workFlowStatus, String remark, String creatorUserID,
			String creatorUserName, String creatorDate, String modifyUserID,
			String modifyDate, String sysStatus) {
		super();
		this.workFlowID = workFlowID;
		this.aoID = aoID;
		this.receiveOrderPer = receiveOrderPer;
		this.receiveOrderDate = receiveOrderDate;
		this.customerName = customerName;
		this.orderID = orderID;
		this.creatorOrderPer = creatorOrderPer;
		this.creatorOrderDate = creatorOrderDate;
		this.flowDealOrderID = flowDealOrderID;
		this.shipmentID = shipmentID;
		this.shipmentPer = shipmentPer;
		this.shipmentDate = shipmentDate;
		this.logisticsCompany = logisticsCompany;
		this.expressNO = expressNO;
		this.customerServiceID = customerServiceID;
		this.customerServicePer = customerServicePer;
		this.customerServiceDate = customerServiceDate;
		this.handleFruit = handleFruit;
		this.workFlowStatus = workFlowStatus;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.creatorUserName = creatorUserName;
		this.creatorDate = creatorDate;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
		this.sysStatus = sysStatus;
	}
	public WorkFlow() {
		super();
	}
	
}
