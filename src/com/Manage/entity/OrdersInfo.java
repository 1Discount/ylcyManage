package com.Manage.entity;

import java.util.Date;
import java.util.List;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午2:30:15 * @version 1.0 * @parameter
 * * @since * @return
 */
public class OrdersInfo
{
	/** 接单ID **/
	public String aoID;

	/** ID **/
	public String orderID;

	/** 客户ID **/
	public String customerID;

	/** 客户名称 **/
	public String customerName;

	/** 客户手机号 **/
	public String customerPhone;

	/** 设备交易数 **/
	public int deviceDealCount;

	/** 流量交易数 **/
	public int flowDealCount;

	/** 总额 **/
	public double orderAmount;

	/** 订单状态 **/
	public String orderStatus;

	/** 支付类型(在线账户,支付宝,微信) **/
	public String paymentType;

	/** 订单是否完成 **/
	public String ifFinish;

	/** 备注 **/
	public String remark;

	/** 创建人ID **/
	public String creatorUserID;

	/** 创建人姓名 **/
	public String creatorUserName;

	/** 创建时间 **/
	public Date creatorDate;

	/** 创建时间String **/
	public String creatorDateString;

	/** 修改人ID **/
	public String modifyUserID;

	/** 修改时间 **/
	public Date modifyDate;

	/** 修改时间 String **/
	public String modifyDateString;

	/** 系统状态 **/
	public boolean sysStatus;

	/** 用来做查询条件 **/
	public String begindate;

	public String enddate;

	/** 设备交易订单列表 **/
	public List<DeviceDealOrders> deviceDealOrders;

	/** 流量交易订单列表 **/
	public List<FlowDealOrders> flowDealOrders;

	/** 订单来源 **/
	public String orderSource;

	/** 物流信息:物流单号，物流公司拼音简称 **/
	public String logisticsInfo;

	/** 发货状态 **/
	public String shipmentsStatus;

	/** 支付宝帐户 **/
	public String aliPayID;

	/** 订单客户信息 **/
	public CustomerInfo customerInfo;

	/** 收货地址 **/
	public String Address;

	/** 用作有赞的查询条件 目前的值有: all 查询同一购物车订单下所有流量和设备宝贝对应的全部总订单 */
	public String youzanFilter;

	/**
	 * ahming notes:目前业务使用时查询大多数使用了 sysStatus=1 的条件, 某些情况下希望得到全部的包括已删除的 订单,
	 * 所以增加这一个标志, 目的系避免对原来条件的影响, 在缺省时即等同原来的不变
	 */
	public Boolean sysStausAdmin;

	public String SN;

	public String countryName;

	public String startTime;

	public String customerAddress;

	public String price;

	public String trip;

	public String total;

	public String flowPrice;

	public String pickUpWay;

	public String ifBindSN;// 是否分配SN

	public String dealAmount;

	public String deallType;

	public String flowDays;



	public String getFlowDays()
	{
		return flowDays;
	}



	public void setFlowDays(String flowDays)
	{
		this.flowDays = flowDays;
	}



	public String getDeallType()
	{
		return deallType;
	}



	public void setDeallType(String deallType)
	{
		this.deallType = deallType;
	}



	public String getDealAmount()
	{
		return dealAmount;
	}



	public void setDealAmount(String dealAmount)
	{
		this.dealAmount = dealAmount;
	}



	public String getIfBindSN()
	{
		return ifBindSN;
	}



	public void setIfBindSN(String ifBindSN)
	{
		this.ifBindSN = ifBindSN;
	}



	public String getPickUpWay()
	{

		return pickUpWay;
	}



	public void setPickUpWay(String pickUpWay)
	{

		this.pickUpWay = pickUpWay;
	}



	public String getFlowPrice()
	{

		return flowPrice;
	}



	public void setFlowPrice(String flowPrice)
	{

		this.flowPrice = flowPrice;
	}



	public String getCustomerPhone()
	{

		return customerPhone;
	}



	public String getCountryName()
	{

		return countryName;
	}



	public void setCountryName(String countryName)
	{

		this.countryName = countryName;
	}



	public String getStartTime()
	{

		return startTime;
	}



	public void setStartTime(String startTime)
	{

		this.startTime = startTime;
	}



	public String getCustomerAddress()
	{

		return customerAddress;
	}



	public void setCustomerAddress(String customerAddress)
	{

		this.customerAddress = customerAddress;
	}



	public String getPrice()
	{

		return price;
	}



	public void setPrice(String price)
	{

		this.price = price;
	}



	public String getTrip()
	{

		return trip;
	}



	public void setTrip(String trip)
	{

		this.trip = trip;
	}



	public String getTotal()
	{

		return total;
	}



	public void setTotal(String total)
	{

		this.total = total;
	}



	public void setCustomerPhone(String customerPhone)
	{

		this.customerPhone = customerPhone;
	}



	public String getAoID()
	{

		return aoID;
	}



	public void setAoID(String aoID)
	{

		this.aoID = aoID;
	}



	public String getSN()
	{

		return SN;
	}



	public void setSN(String sN)
	{

		SN = sN;
	}

	public boolean isBindSN;



	public boolean isBindSN()
	{

		return isBindSN;
	}



	public void setBindSN(boolean isBindSN)
	{

		this.isBindSN = isBindSN;
	}



	public String getAliPayID()
	{

		return aliPayID;
	}



	public void setAliPayID(String aliPayID)
	{

		this.aliPayID = aliPayID;
	}



	public String getAddress()
	{

		return Address;
	}



	public void setAddress(String address)
	{

		Address = address;
	}



	public String getOrderSource()
	{

		return orderSource;
	}



	public void setOrderSource(String orderSource)
	{

		this.orderSource = orderSource;
	}



	public String getLogisticsInfo()
	{

		return logisticsInfo;
	}



	public void setLogisticsInfo(String logisticsInfo)
	{

		this.logisticsInfo = logisticsInfo;
	}



	public String getShipmentsStatus()
	{

		return shipmentsStatus;
	}



	public void setShipmentsStatus(String shipmentsStatus)
	{

		this.shipmentsStatus = shipmentsStatus;
	}



	public CustomerInfo getCustomerInfo()
	{

		return customerInfo;
	}



	public void setCustomerInfo(CustomerInfo customerInfo)
	{

		this.customerInfo = customerInfo;
	}



	public String getCreatorUserName()
	{

		return creatorUserName;
	}



	public void setCreatorUserName(String creatorUserName)
	{

		this.creatorUserName = creatorUserName;
	}



	public String getIfFinish()
	{

		return ifFinish;
	}



	public void setIfFinish(String ifFinish)
	{

		this.ifFinish = ifFinish;
	}



	public List<DeviceDealOrders> getDeviceDealOrders()
	{

		return deviceDealOrders;
	}



	public void setDeviceDealOrders(List<DeviceDealOrders> deviceDealOrders)
	{

		this.deviceDealOrders = deviceDealOrders;
	}



	public List<FlowDealOrders> getFlowDealOrders()
	{

		return flowDealOrders;
	}



	public void setFlowDealOrders(List<FlowDealOrders> flowDealOrders)
	{

		this.flowDealOrders = flowDealOrders;
	}



	public String getOrderID()
	{

		return orderID;
	}



	public void setOrderID(String orderID)
	{

		this.orderID = orderID;
	}



	public String getCustomerID()
	{

		return customerID;
	}



	public void setCustomerID(String customerID)
	{

		this.customerID = customerID;
	}



	public String getCustomerName()
	{

		return customerName;
	}



	public void setCustomerName(String customerName)
	{

		this.customerName = customerName;
	}



	public String getOrderStatus()
	{

		return orderStatus;
	}



	public int getDeviceDealCount()
	{

		return deviceDealCount;
	}



	public void setDeviceDealCount(int deviceDealCount)
	{

		this.deviceDealCount = deviceDealCount;
	}



	public int getFlowDealCount()
	{

		return flowDealCount;
	}



	public void setFlowDealCount(int flowDealCount)
	{

		this.flowDealCount = flowDealCount;
	}



	public double getOrderAmount()
	{

		return orderAmount;
	}



	public void setOrderAmount(double orderAmount)
	{

		this.orderAmount = orderAmount;
	}



	public void setOrderStatus(String orderStatus)
	{

		this.orderStatus = orderStatus;
	}



	public String getPaymentType()
	{

		return paymentType;
	}



	public void setPaymentType(String paymentType)
	{

		this.paymentType = paymentType;
	}



	public String getRemark()
	{

		return remark;
	}



	public void setRemark(String remark)
	{

		this.remark = remark;
	}



	public String getCreatorUserID()
	{

		return creatorUserID;
	}



	public void setCreatorUserID(String creatorUserID)
	{

		this.creatorUserID = creatorUserID;
	}



	public Date getCreatorDate()
	{

		return creatorDate;
	}



	public void setCreatorDate(Date creatorDate)
	{

		this.creatorDate = creatorDate;
	}



	public String getModifyUserID()
	{

		return modifyUserID;
	}



	public void setModifyUserID(String modifyUserID)
	{

		this.modifyUserID = modifyUserID;
	}



	public Date getModifyDate()
	{

		return modifyDate;
	}



	public void setModifyDate(Date modifyDate)
	{

		this.modifyDate = modifyDate;
	}



	public boolean isSysStatus()
	{

		return sysStatus;
	}



	public void setSysStatus(boolean sysStatus)
	{

		this.sysStatus = sysStatus;
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



	public String getCreatorDateString()
	{

		return creatorDateString;
	}



	public void setCreatorDateString(String creatorDateString)
	{

		this.creatorDateString = creatorDateString;
	}



	public String getModifyDateString()
	{

		return modifyDateString;
	}



	public void setModifyDateString(String modifyDateString)
	{

		this.modifyDateString = modifyDateString;
	}



	public String getYouzanFilter()
	{

		return youzanFilter;
	}



	public void setYouzanFilter(String youzanFilter)
	{

		this.youzanFilter = youzanFilter;
	}



	public Boolean getSysStausAdmin()
	{

		return sysStausAdmin;
	}



	public void setSysStausAdmin(Boolean sysStausAdmin)
	{

		this.sysStausAdmin = sysStausAdmin;
	}

	private String paymoney;



	public String getPaymoney() {
		return paymoney;
	}



	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}



}
