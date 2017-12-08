package com.Manage.entity;

import java.io.Serializable;


/**
 * 设备日志
 * @author lipeng
 */
public class DeviceLogs implements Serializable{

	/** 漫游软件版本号 *
	 * */
	private String deviceLogID;

	/** 设备序列号 * */
	private String SN;

	/** 设备动作类型（1.漫游模块上传 0.设备登陆） * */
	private String type;

	/** 基站信息 * */
	private String location;

	/** 漫游软件版本号 * */
	private int firmWareVer;

	/** 本地软件版本号* */
	private int version;
	
	/** 漫游APK软件版本号 * */
	private int firmWareAPKVer;
	
	/** 本地APK软件版本号* */
	private int versionAPK;

	/** SIM卡分配结果 * */
	private int SIMAllot;

	/** 流量信息上传次数 * */
	private int UploadFlowCount;

	/** 接入本机wifi的设备数 * */
	private int wifiCount;

	/** 设备剩余电量单位 % * */
	private int battery;

	/** wifi状态（0.未开，1.已开 2.限速） * */
	private int wifiState;

	/** 本地模块3G网络状态（0.断开 1.接通） * */
	private int gSta;

	/** 3G信号强度 * */
	private int gStrenth;

	/** 本次开机上传总流量* */
	private String upFlowAll;

	/** 本次开机下载总流量 * */
	private String downFlowAll;

	/** 当前计费周期的总使用流量 * */
	private String dayUsedFlow;

	/** 3G信号强度(漫游卡)* */
	private int roamGStrenth;

	/** 本次开机上传总流量(漫游卡) * */
	private int roamUpFlowAll;

	/** 本次开机下载总流量(m漫游卡)* */
	private int roamDownFlowAll;

	/** 当前计费周期的总使用流量(漫游卡) * */
	private int roamDayUsedFlow;

	/** 上次更新时间 * */
	private String lastTime;

	/**imsi * */
	private String IMSI;

	/** 透传序号 * */
	private int TTCnt;

	/** 透传的数据* */
	private String TTContext;

	//--新增
	/** 国家码* */
	private String mcc;

	/** 网络码* */
	private String mnc;

	/** 基站编码* */
	private String cid;

	/** 分配的结果，正确分配为0，没有有效订单为3，没有可用卡位4.* */
	private String status;


	/** 客户姓名**/
	private String customerName;

	/** 客户ID**/
	private String customerID;

	/** 表名**/
	private String tableName;

	/**订单备注**/
	private String orderRemark;

	/**订单说明**/
	private String orderExplain;

	/**
	 * 最新的01记录时间 为了方便而已
	 */
	private String lastTime01;

	/**高低速**/
	private int speedType;

	/**SIM ID**/
	private String SIMID;

	/**流量差量**/
	public String FlowDistinction;

	/**是否只显示飘红**/
	public String ifOnlyString;

	/**SIM卡代号**/
	public String simAlias;

	public String monthUsedFlow;
	
	public String userDays;
	
	public String flowOrderID;
	
	public String beginDate;
	
	public String endDate;
	
	public String InCount;
	
	public String getInCount()
	{
		return InCount;
	}

	public void setInCount(String inCount)
	{
		InCount = inCount;
	}

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

	public String getFlowOrderID()
	{
		return flowOrderID;
	}

	public void setFlowOrderID(String flowOrderID)
	{
		this.flowOrderID = flowOrderID;
	}

	public String getUserDays()
	{
		return userDays;
	}

	public void setUserDays(String userDays)
	{
		this.userDays = userDays;
	}

	public String getMonthUsedFlow() {
		return monthUsedFlow;
	}

	public void setMonthUsedFlow(String monthUsedFlow) {
		this.monthUsedFlow = monthUsedFlow;
	}

	/**以下为临时表所用**/
	private String panlUserDate;
	private String userCountry;
	private String flowExpireDate;
	private String orderStatus;
	private String logsDate;
	
	/**订单ID**/
	private String orderID;

	private int minsRemaining;
	private int ContextLen;
	private int newStart;
	public String jizhan;

	/**接入数统计所用**/
	private int countByDay;
	private int countByMCC;
	private String countryName;

	/**是否标红**/
	private int ifRed=0;

	/**设备按时间段查看每天接入情况和统计流量**/
	private boolean ifIn;
	public boolean ifInAndHasFlow;

	public String beginTime;
	public String endTime;
	
	/**标记**/
	private String tag;
	
	/**是否异常退出,辅助查询用**/
	private String ifExcOut;
	/**是否纯历史**/
	private String ifChunString;
	
	/**辅助字段**/
	private String lastTime02;
	
	/**上下线 **/
	private String updownline;
	
	
	public int getFirmWareAPKVer() {
		return firmWareAPKVer;
	}

	public void setFirmWareAPKVer(int firmWareAPKVer) {
		this.firmWareAPKVer = firmWareAPKVer;
	}

	public int getVersionAPK() {
		return versionAPK;
	}

	public void setVersionAPK(int versionAPK) {
		this.versionAPK = versionAPK;
	}

	public String getUpdownline() {
		return updownline;
	}

	public void setUpdownline(String updownline) {
		this.updownline = updownline;
	}

	public String getLastTime02() {
		return lastTime02;
	}

	public void setLastTime02(String lastTime02) {
		this.lastTime02 = lastTime02;
	}

	public String getIfChunString() {
		return ifChunString;
	}

	public void setIfChunString(String ifChunString) {
		this.ifChunString = ifChunString;
	}

	public String getIfExcOut() {
		return ifExcOut;
	}

	public void setIfExcOut(String ifExcOut) {
		this.ifExcOut = ifExcOut;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSimAlias() {
		return simAlias;
	}

	public void setSimAlias(String simAlias) {
		this.simAlias = simAlias;
	}

	public int getIfRed() {
		return ifRed;
	}

	public void setIfRed(int ifRed) {
		this.ifRed = ifRed;
	}

	public String getIfOnlyString() {
		return ifOnlyString;
	}

	public void setIfOnlyString(String ifOnlyString) {
		this.ifOnlyString = ifOnlyString;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getPanlUserDate() {
		return panlUserDate;
	}

	public void setPanlUserDate(String panlUserDate) {
		this.panlUserDate = panlUserDate;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getFlowExpireDate() {
		return flowExpireDate;
	}

	public void setFlowExpireDate(String flowExpireDate) {
		this.flowExpireDate = flowExpireDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getLogsDate() {
		return logsDate;
	}

	public void setLogsDate(String logsDate) {
		this.logsDate = logsDate;
	}

	public String getFlowDistinction() {
		return FlowDistinction;
	}

	public void setFlowDistinction(String flowDistinction) {
		FlowDistinction = flowDistinction;
	}

	public String getSIMID() {
		return SIMID;
	}

	public void setSIMID(String sIMID) {
		SIMID = sIMID;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getOrderExplain() {
		return orderExplain;
	}

	public void setOrderExplain(String orderExplain) {
		this.orderExplain = orderExplain;
	}

	public int getSpeedType() {
		return speedType;
	}

	public void setSpeedType(int speedType) {
		this.speedType = speedType;
	}

	public String getLastTime01() {
		return lastTime01;
	}

	public int getContextLen() {
		return ContextLen;
	}

	public void setContextLen(int contextLen) {
		ContextLen = contextLen;
	}

	public int getNewStart() {
		return newStart;
	}

	public void setNewStart(int newStart) {
		this.newStart = newStart;
	}

	public int getMinsRemaining() {
		return minsRemaining;
	}

	public void setMinsRemaining(int minsRemaining) {
		this.minsRemaining = minsRemaining;
	}

	public void setLastTime01(String lastTime01) {
		this.lastTime01 = lastTime01;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public String getDeviceLogID() {
		return deviceLogID;
	}

	public void setDeviceLogID(String deviceLogID) {
		this.deviceLogID = deviceLogID;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getFirmWareVer() {
		return firmWareVer;
	}

	public void setFirmWareVer(int firmWareVer) {
		this.firmWareVer = firmWareVer;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getSIMAllot() {
		return SIMAllot;
	}

	public void setSIMAllot(int sIMAllot) {
		SIMAllot = sIMAllot;
	}

	public int getUploadFlowCount() {
		return UploadFlowCount;
	}

	public void setUploadFlowCount(int uploadFlowCount) {
		UploadFlowCount = uploadFlowCount;
	}

	public int getWifiCount() {
		return wifiCount;
	}

	public void setWifiCount(int wifiCount) {
		this.wifiCount = wifiCount;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public int getWifiState() {
		return wifiState;
	}

	public void setWifiState(int wifiState) {
		this.wifiState = wifiState;
	}

	public int getgSta() {
		return gSta;
	}

	public void setgSta(int gSta) {
		this.gSta = gSta;
	}

	public int getgStrenth() {
		return gStrenth;
	}

	public void setgStrenth(int gStrenth) {
		this.gStrenth = gStrenth;
	}

	public String getUpFlowAll() {
		return upFlowAll;
	}

	public void setUpFlowAll(String object) {
		this.upFlowAll = object;
	}

	public String getDownFlowAll() {
		return downFlowAll;
	}

	public void setDownFlowAll(String object) {
		this.downFlowAll = object;
	}

	public String getDayUsedFlow() {
		return dayUsedFlow;
	}

	public void setDayUsedFlow(String object) {
		this.dayUsedFlow = object;
	}

	public int getRoamGStrenth() {
		return roamGStrenth;
	}

	public void setRoamGStrenth(int roamGStrenth) {
		this.roamGStrenth = roamGStrenth;
	}

	public int getRoamUpFlowAll() {
		return roamUpFlowAll;
	}

	public void setRoamUpFlowAll(int roamUpFlowAll) {
		this.roamUpFlowAll = roamUpFlowAll;
	}

	public int getRoamDownFlowAll() {
		return roamDownFlowAll;
	}

	public void setRoamDownFlowAll(int roamDownFlowAll) {
		this.roamDownFlowAll = roamDownFlowAll;
	}

	public int getRoamDayUsedFlow() {
		return roamDayUsedFlow;
	}

	public void setRoamDayUsedFlow(int roamDayUsedFlow) {
		this.roamDayUsedFlow = roamDayUsedFlow;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}

	public int getTTCnt() {
		return TTCnt;
	}

	public void setTTCnt(int tTCnt) {
		TTCnt = tTCnt;
	}

	public String getTTContext() {
		return TTContext;
	}

	public void setTTContext(String tTContext) {
		TTContext = tTContext;
	}

	//==
	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJizhan() {
		return jizhan;
	}

	public void setJizhan(String jizhan) {
		this.jizhan = jizhan;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getCountByDay() {
		return countByDay;
	}

	public void setCountByDay(int countByDay) {
		this.countByDay = countByDay;
	}

	public int getCountByMCC() {
		return countByMCC;
	}

	public void setCountByMCC(int countByMCC) {
		this.countByMCC = countByMCC;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public boolean isIfIn() {
		return ifIn;
	}

	public void setIfIn(boolean ifIn) {
		this.ifIn = ifIn;
	}

	public boolean isIfInAndHasFlow() {
		return ifInAndHasFlow;
	}

	public void setIfInAndHasFlow(boolean ifInAndHasFlow) {
		this.ifInAndHasFlow = ifInAndHasFlow;
	}
 
/*	public String tablename;


	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}*/
	
	private String phone;


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	



}
