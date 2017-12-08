package com.Manage.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午5:48:47 * @version 1.0 * @parameter
 * * @since * @return
 */
public class FlowDealOrders
{

	/** ID **/
	public String flowDealID;

	/** 关联订单ID **/
	public String orderID;

	/** 客户ID **/
	public String customerID;

	/** 客户名称 **/
	public String customerName;

	/** 套餐ID（如果是自定的流量订单，此处为空） **/
	public String flowPlanID;

	/** 套餐名称（如果是自定的流量订单，此处为空） **/
	public String flowPlanName;

	/** 订单生成时间 **/
	public Date orderCreateDate;

	/** 使用国家(国家名称，国家编号，价格|国家名称，国家编号，价格) **/
	public String userCountry;

	/** 预约使用时间 **/
	public String panlUserDate;

	/** 预约使用时间更改次数, 目前可能限定最多只能修改1次 **/
	public int planUseDateCCount;

	/** 订单状态(0.不可用，1，2.可使用 3.使用中 4.已过期.) **/
	public String orderStatus;

	/** 订单类型（按时间，按流量） **/
	public String orderType;

	/** 订单是否完成 **/
	public String IfFinish;

	/** 流量天数 **/
	public int flowDays;

	/** 剩余分钟数 **/
	public int minsRemaining;

	/** 剩余天数 **/
	public int daysRemaining;

	/** 订单金额 **/
	public double orderAmount;

	/** 流量到期时间 **/
	public String flowExpireDate;

	/** 设备SN号码 **/
	public String SN;

	/** 是否激活 **/
	public String ifActivate;

	/** 激活时间 **/
	public String activateDate;

	/**
	 * 开始使用时间 ahming adds:对于那些特殊订单,如10087北京银行的,开始时这个记录"开始可使用的时间" 到了真正开始使用时,
	 * 再更新为实际开始使用时间.
	 */
	public String beginDate;

	/** 当前计费周期第一次使用时间 **/
	public String intradayDate;

	/** 是否限速 **/
	public String ifLimitSpeed;

	/** 限速阀值 **/
	public int limitValve;

	/** 限速多少 **/
	public int limitSpeed;

	/** 已经使用流量(kb) **/
	public int flowUsed;

	/** 分配到的IMSI **/
	public String IMSI;

	/** 上次更新时间(sim server那边用) **/
	public String lastUpdateDate;

	/** 备注 **/
	public String remark;

	/** 创建人ID **/
	public String creatorUserID;

	/** 创建人 姓名 **/
	public String creatorUserName;

	/** 创建时间 **/
	public Date creatorDate;

	/** 创建时间String **/
	public String creatorDateString;

	/** 修改人ID **/
	public String modifyUserID;

	/** 修改时间 **/
	public Date modifyDate;

	/** 修改时间String **/
	public String modifyDateString;

	/** 系统状态 **/
	public boolean sysStatus;

	/** 流量订单所绑定设备与SIM服务器通讯的服务器的IP和端口组合 **/
	public String serverInfo;

	/** 工厂测试标识 **/
	public int factoryFlag;

	/** 限速策略 **/
	public String speedStr;

	/**
	 * 退款状态
	 */
	public String refundStatus;

	/**
	 * 退款金额
	 */
	public double refundAmount;

	/** 渠道商名称 **/
	public String distributorName;

	/** 订单总流量 **/
	public String flowTotal;

	public String ifVPN;

	public String flowDistinction;

	/** 订单是否使用 **/
	public String isUserd;

	public String enterpriseID;

	public String priceConfiguration;

	public String targerFlow;

	public String available;
	
	public int drawBackDay;
	
	//已使用天数
	public String usingDate;
	//已使用国家
	public String usingCountry;
	//服务器编号
	public String serverCode;
	
	//订单使用信息
	public String userInfo;
	

	/** 渠道商标识(自定义属性) **/
	public String disId;
	
	public int getDrawBackDay()
	{
		return drawBackDay;
	}



	public void setDrawBackDay(int drawBackDay)
	{
		this.drawBackDay = drawBackDay;
	}



	public String getAvailable()
	{
		return available;
	}



	public void setAvailable(String available)
	{
		this.available = available;
	}



	public String getTargerFlow()
	{
		return targerFlow;
	}



	public void setTargerFlow(String targerFlow)
	{
		this.targerFlow = targerFlow;
	}



	public String getEnterpriseID()
	{
		return enterpriseID;
	}



	public void setEnterpriseID(String enterpriseID)
	{
		this.enterpriseID = enterpriseID;
	}



	public String getPriceConfiguration()
	{
		return priceConfiguration;
	}



	public void setPriceConfiguration(String priceConfiguration)
	{
		this.priceConfiguration = priceConfiguration;
	}



	public String getIsUserd()
	{
		return isUserd;
	}



	public void setIsUserd(String isUserd)
	{
		this.isUserd = isUserd;
	}



	public String getFlowDistinction()
	{
		return flowDistinction;
	}



	public void setFlowDistinction(String flowDistinction)
	{
		this.flowDistinction = flowDistinction;
	}



	public String getIfVPN()
	{
		return ifVPN;
	}



	public void setIfVPN(String ifVPN)
	{
		this.ifVPN = ifVPN;
	}



	public String getFlowTotal()
	{
		return flowTotal;
	}



	public void setFlowTotal(String flowTotal)
	{
		this.flowTotal = flowTotal;
	}



	public String getDistributorName()
	{
		return distributorName;
	}



	public void setDistributorName(String distributorName)
	{
		this.distributorName = distributorName;
	}

	/** 在更新有赞订单物流状态时标记系来自有赞的订单 **/
	public String orderSource;



	public String getSpeedStr()
	{
		return speedStr;
	}



	public void setSpeedStr(String speedStr)
	{
		this.speedStr = speedStr;
	}



	public List<String> getCountryArray()
	{
		return countryArray;
	}



	public void setCountryArray(List<String> countryArray)
	{
		this.countryArray = countryArray;
	}

	public String journey;

	public List<String> countryArray;



	public String getJourney()
	{
		return journey;
	}



	public void setJourney(String journey)
	{
		this.journey = journey;
	}



	public int getFactoryFlag()
	{
		return factoryFlag;
	}



	public void setFactoryFlag(int factoryFlag)
	{
		this.factoryFlag = factoryFlag;
	}

	/** 用来做查询条件 **/
	public String begindateForQuery;

	public String enddate;

	public String beginDateForLastUpdateDate;

	public String endDateForLastUpdateDate;

	public String beginDateForPanlUserDate;

	public String endDateForPanlUserDate;

	// 为了在 FlowDealOrders 统一使用同一个查询条件片段, 用此变量来声明较复杂的条件
	// 1 : 查询当日可用订单: 查询订单开始时间(panlUserDate)是当天的或着订单状态是使用中和暂停的信息
	public int queryFlag;

	// 临时包装查询结果, 未熟悉使用 map/resultmap 返回
	public String deviceDealID;

	public String deviceID;

	public String deallType;

	public String deviceOrderStatus;



	public int getLimitValve()
	{
		return limitValve;
	}



	public void setLimitValve(int limitValve)
	{
		this.limitValve = limitValve;
	}



	public String getBegindateForQuery()
	{
		return begindateForQuery;
	}



	public void setBegindateForQuery(String begindateForQuery)
	{
		this.begindateForQuery = begindateForQuery;
	}



	public String getEnddate()
	{
		return enddate;
	}



	public void setEnddate(String enddate)
	{
		this.enddate = enddate;
	}



	public String getBeginDateForLastUpdateDate()
	{
		return beginDateForLastUpdateDate;
	}



	public void setBeginDateForLastUpdateDate(String beginDateForLastUpdateDate)
	{
		this.beginDateForLastUpdateDate = beginDateForLastUpdateDate;
	}



	public String getEndDateForLastUpdateDate()
	{
		return endDateForLastUpdateDate;
	}



	public void setEndDateForLastUpdateDate(String endDateForLastUpdateDate)
	{
		this.endDateForLastUpdateDate = endDateForLastUpdateDate;
	}



	public String getBeginDateForPanlUserDate()
	{
		return beginDateForPanlUserDate;
	}



	public void setBeginDateForPanlUserDate(String beginDateForPanlUserDate)
	{
		this.beginDateForPanlUserDate = beginDateForPanlUserDate;
	}



	public String getEndDateForPanlUserDate()
	{
		return endDateForPanlUserDate;
	}



	public void setEndDateForPanlUserDate(String endDateForPanlUserDate)
	{
		this.endDateForPanlUserDate = endDateForPanlUserDate;
	}



	public int getQueryFlag()
	{
		return queryFlag;
	}



	public void setQueryFlag(int queryFlag)
	{
		this.queryFlag = queryFlag;
	}



	public String getIfFinish()
	{
		return IfFinish;
	}



	public void setIfFinish(String ifFinish)
	{
		IfFinish = ifFinish;
	}



	public String getFlowDealID()
	{
		return flowDealID;
	}



	public void setFlowDealID(String flowDealID)
	{
		this.flowDealID = flowDealID;
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



	public String getFlowPlanID()
	{
		return flowPlanID;
	}



	public void setFlowPlanID(String flowPlanID)
	{
		this.flowPlanID = flowPlanID;
	}



	public String getUserCountry()
	{
		return userCountry;
	}



	public void setUserCountry(String userCountry)
	{
		this.userCountry = userCountry;
	}



	public String getOrderStatus()
	{
		return orderStatus;
	}



	public void setOrderStatus(String orderStatus)
	{
		this.orderStatus = orderStatus;
	}



	public String getOrderType()
	{
		return orderType;
	}



	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}



	public String getSN()
	{
		return SN;
	}



	public void setSN(String sN)
	{
		SN = sN;
	}



	public String getIfActivate()
	{
		return ifActivate;
	}



	public void setIfActivate(String ifActivate)
	{
		this.ifActivate = ifActivate;
	}



	public String getIfLimitSpeed()
	{
		return ifLimitSpeed;
	}



	public void setIfLimitSpeed(String ifLimitSpeed)
	{
		this.ifLimitSpeed = ifLimitSpeed;
	}



	public String getIMSI()
	{
		return IMSI;
	}



	public void setIMSI(String iMSI)
	{
		IMSI = iMSI;
	}



	public Date getOrderCreateDate()
	{
		return orderCreateDate;
	}



	public void setOrderCreateDate(Date orderCreateDate)
	{
		this.orderCreateDate = orderCreateDate;
	}



	public String getPanlUserDate()
	{
		return panlUserDate;
	}



	public void setPanlUserDate(String panlUserDate)
	{
		this.panlUserDate = panlUserDate;
	}



	public int getFlowDays()
	{
		return flowDays;
	}



	public void setFlowDays(int flowDays)
	{
		this.flowDays = flowDays;
	}



	public int getMinsRemaining()
	{
		return minsRemaining;
	}



	public void setMinsRemaining(int minsRemaining)
	{
		this.minsRemaining = minsRemaining;
	}



	public int getDaysRemaining()
	{
		return daysRemaining;
	}



	public void setDaysRemaining(int daysRemaining)
	{
		this.daysRemaining = daysRemaining;
	}



	public double getOrderAmount()
	{
		return orderAmount;
	}



	public void setOrderAmount(double orderAmount)
	{
		this.orderAmount = orderAmount;
	}



	public int getLimitSpeed()
	{
		return limitSpeed;
	}



	public void setLimitSpeed(int limitSpeed)
	{
		this.limitSpeed = limitSpeed;
	}



	public int getFlowUsed()
	{
		return flowUsed;
	}



	public void setFlowUsed(int flowUsed)
	{
		this.flowUsed = flowUsed;
	}



	public String getFlowExpireDate()
	{
		return flowExpireDate;
	}



	public void setFlowExpireDate(String flowExpireDate)
	{
		this.flowExpireDate = flowExpireDate;
	}



	public String getActivateDate()
	{
		return activateDate;
	}



	public void setActivateDate(String activateDate)
	{
		this.activateDate = activateDate;
	}



	public String getBeginDate()
	{
		return beginDate;
	}



	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}



	public String getIntradayDate()
	{
		return intradayDate;
	}



	public void setIntradayDate(String intradayDate)
	{
		this.intradayDate = intradayDate;
	}



	public String getLastUpdateDate()
	{
		return lastUpdateDate;
	}



	public void setLastUpdateDate(String lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
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



	public String getCreatorUserName()
	{
		return creatorUserName;
	}



	public void setCreatorUserName(String creatorUserName)
	{
		this.creatorUserName = creatorUserName;
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



	public String getDeviceDealID()
	{
		return deviceDealID;
	}



	public void setDeviceDealID(String deviceDealID)
	{
		this.deviceDealID = deviceDealID;
	}



	public String getDeviceID()
	{
		return deviceID;
	}



	public void setDeviceID(String deviceID)
	{
		this.deviceID = deviceID;
	}



	public String getDeallType()
	{
		return deallType;
	}



	public void setDeallType(String deallType)
	{
		this.deallType = deallType;
	}



	public String getDeviceOrderStatus()
	{
		return deviceOrderStatus;
	}



	public void setDeviceOrderStatus(String deviceOrderStatus)
	{
		this.deviceOrderStatus = deviceOrderStatus;
	}



	public String getServerInfo()
	{
		return serverInfo;
	}



	public void setServerInfo(String serverInfo)
	{
		this.serverInfo = serverInfo;
	}



	public String getFlowPlanName()
	{
		return flowPlanName;
	}



	public void setFlowPlanName(String flowPlanName)
	{
		this.flowPlanName = flowPlanName;
	}



	public int getPlanUseDateCCount()
	{
		return planUseDateCCount;
	}



	public void setPlanUseDateCCount(int planUseDateCCount)
	{
		this.planUseDateCCount = planUseDateCCount;
	}



	public String getOrderSource()
	{
		return orderSource;
	}



	public void setOrderSource(String orderSource)
	{
		this.orderSource = orderSource;
	}



	public String getRefundStatus()
	{
		return refundStatus;
	}



	public void setRefundStatus(String refundStatus)
	{
		this.refundStatus = refundStatus;
	}



	public double getRefundAmount()
	{
		return refundAmount;
	}



	public void setRefundAmount(double refundAmount)
	{
		this.refundAmount = refundAmount;
	}



	public String getUsingDate() {
	    return usingDate;
	}



	public void setUsingDate(String usingDate) {
	    this.usingDate = usingDate;
	}



	public String getServerCode() {
	    return serverCode;
	}



	public void setServerCode(String serverCode) {
	    this.serverCode = serverCode;
	}



	public String getUserInfo() {
	    return userInfo;
	}



	public void setUserInfo(String userInfo) {
	    this.userInfo = userInfo;
	}



	public String getUsingCountry() {
	    return usingCountry;
	}



	public void setUsingCountry(String usingCountry) {
	    this.usingCountry = usingCountry;
	}



	public String getDisId() {
	    return disId;
	}



	public void setDisId(String disId) {
	    this.disId = disId;
	}
	
}
