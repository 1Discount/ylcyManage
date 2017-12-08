package com.Manage.entity;

/**
 * 接单表
 * @author 沈超
 *
 */
public class AcceptOrder {
	
	/**
	 * ID
	 */
	private String aoID;
	
	/**
	 * 客户姓名
	 */
	private String customerName;
	
	/**
	 * 电话
	 */
	private String customerPhone;

	/**
	 * 地址
	 */
	private String customerAddress;

	/**
	 * 取货方式
	 */
	private String pickUpWay;
	
	/**
	 * 旺旺号
	 */
	private String wangwangNo;
	
	/**
	 * 网店订单编号
	 */
	private String orderNumber;
	
	/**
	 * 来源(A，B，天猫)
	 */
	private String orderSource;
	
	/**
	 * 开始使用时间
	 */
	private String startTime;
	
	/**
	 * 天数
	 */
	private Integer days=null;
	
	/**
	 * 国家列表
	 */
	private String countryList;
	
	/**
	 * 行程
	 */
	private String trip;
	
	/**
	 * 数量
	 */
	private Integer total=null;
	
	/**
	 * 金额
	 */
	private Double price=null;
	
	/**
	 * 打回原因
	 */
	private String callBackReason;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 设备交易类型
	 */
	private String deviceTransactionType;
	
	/**
	 * 状态
	 */
	private String acceptOrderStatus;
	
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
	private Integer sysStatus=null;

	/**
	 * 待发货日期
	 */
	private String shippmentDate;
	
	/**
	 * 订单类型
	 */
	private String orderType;
	
	/**下单人**/
	private String belowOrderPer;
	
	/**下单时间**/
	private String belowOrderDate;
	
	public String begindate;

	public String enddate;
	
	public String SN;
	
	public String ifReturn;
	
	public String getIfReturn()
	{
		return ifReturn;
	}

	public void setIfReturn(String ifReturn)
	{
		this.ifReturn = ifReturn;
	}

	public String getSN()
	{
		return SN;
	}

	public void setSN(String sN)
	{
		SN = sN;
	}

	public String getBegindate()
	{
		return begindate;
	}

	public void setBegindate(String begindate)
	{
		this.begindate = begindate;
	}

	public String getEnddate()
	{
		return enddate;
	}

	public void setEnddate(String enddate)
	{
		this.enddate = enddate;
	}

	public String getBelowOrderPer()
	{
		return belowOrderPer;
	}

	public void setBelowOrderPer(String belowOrderPer)
	{
		this.belowOrderPer = belowOrderPer;
	}

	public String getBelowOrderDate()
	{
		return belowOrderDate;
	}

	public void setBelowOrderDate(String belowOrderDate)
	{
		this.belowOrderDate = belowOrderDate;
	}

	

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}



	//自定义字段
	private String countryName;

	public String getAoID() {
		return aoID;
	}

	public void setAoID(String aoID) {
		this.aoID = aoID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getPickUpWay() {
		return pickUpWay;
	}

	public void setPickUpWay(String pickUpWay) {
		this.pickUpWay = pickUpWay;
	}

	public String getWangwangNo() {
		return wangwangNo;
	}

	public void setWangwangNo(String wangwangNo) {
		this.wangwangNo = wangwangNo;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getCountryList() {
		return countryList;
	}

	public void setCountryList(String countryList) {
		this.countryList = countryList;
	}

	public String getTrip() {
		return trip;
	}

	public void setTrip(String trip) {
		this.trip = trip;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCallBackReason() {
		return callBackReason;
	}

	public void setCallBackReason(String callBackReason) {
		this.callBackReason = callBackReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeviceTransactionType() {
		return deviceTransactionType;
	}

	public void setDeviceTransactionType(String deviceTransactionType) {
		this.deviceTransactionType = deviceTransactionType;
	}

	public String getAcceptOrderStatus() {
		return acceptOrderStatus;
	}

	public void setAcceptOrderStatus(String acceptOrderStatus) {
		this.acceptOrderStatus = acceptOrderStatus;
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

	public Integer getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getShippmentDate()
	{
		return shippmentDate;
	}

	public void setShippmentDate(String shippmentDate)
	{
		this.shippmentDate = shippmentDate;
	}
}
