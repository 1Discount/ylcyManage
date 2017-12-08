package com.Manage.entity;

/**
 * 设备信息
 * @author lipeng
 *
 */
public class DeviceInfo {

	private String deviceID;
	private String SN;
	private String CardID;
	private int deviceOrderCount;
	private String deviceColour;//设备颜色
	private String repertoryStatus;//出入库状态（入库，出货，维修）
	private String distributorID;//渠道商ID
	private String distributorName;//渠道商名称
	private String deviceStatus;
	private String remark;
	private String creatorUserID;
	private String creatorUserName;//创建人姓名
	private String creatorDate;
	private String modifyUserID;
	private String modifyDate;
	private String sysStatus;
	private String isreturn;
	private String serviceIP;

	private String zlgm;

	private int fd1;
	private int fd2;
	
	private String firmWareVer;
	private String firmWareAPKVer;
	private String version;
	private String versionAPK;

	public String getServiceIP() {
		return serviceIP;
	}
	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}
	public int getFd1() {
		return fd1;
	}
	public void setFd1(int fd1) {
		this.fd1 = fd1;
	}

	public int getFd2() {
		return fd2;
	}
	public void setFd2(int fd2) {
		this.fd2 = fd2;
	}
	public String getZlgm() {
		return zlgm;
	}
	public void setZlgm(String zlgm) {
		this.zlgm = zlgm;
	}
	public String getIsreturn() {
		return isreturn;
	}
	public void setIsreturn(String isreturn) {
		this.isreturn = isreturn;
	}
	public DeviceInfo(String sN) {
		super();
		SN = sN;
	}
	public DeviceInfo(){}

	public DeviceInfo(String sN,String deviceStatus,String repertoryStatus, String modifyUserID, String modifyDate) {
		super();
		SN = sN;
		this.deviceStatus=deviceStatus;
		this.repertoryStatus = repertoryStatus;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
	}
	public DeviceInfo(String CardID,String sN,String deviceStatus,String repertoryStatus,String remark, String modifyUserID, String modifyDate) {
		super();
		SN = sN;
		this.CardID=CardID;
		this.deviceStatus=deviceStatus;
		this.remark=remark;
		this.repertoryStatus=repertoryStatus;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
	}

//	new DeviceInfo(uuid,sn,deviceOrderCount,deviceStatus,remark,createid,createuname,date,sysstauts);
//	DeviceInfo device = new DeviceInfo(uuid,sn,remark,createid,createuname,date);

	public DeviceInfo(String deviceID, String CardID,String SN, String deviceColour,
			String distributorID,String distributorName,String remark,String creatorUserID,String creatorUserName, String creatorDate) {
		super();
		this.deviceID = deviceID;
		this.CardID=CardID;
		this.SN = SN;
		this.deviceColour=deviceColour;
//		this.repertoryStatus=repertoryStatus;
		this.distributorID=distributorID;
		this.distributorName=distributorName;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.creatorUserName = creatorUserName;
		this.creatorDate = creatorDate;
 	}

	public DeviceInfo(String deviceID, String CardID,String SN, String deviceColour,
			String distributorID,String distributorName,String remark,String deviceStatus,String creatorUserID,String creatorUserName, String creatorDate) {
		super();
		this.deviceID = deviceID;
		this.CardID=CardID;
		this.SN = SN;
		this.deviceColour=deviceColour;
		this.deviceStatus=deviceStatus;
		this.distributorID=distributorID;
		this.distributorName=distributorName;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.creatorUserName = creatorUserName;
		this.creatorDate = creatorDate;

 	}

	public String getDeviceColour() {
		return deviceColour;
	}
	public void setDeviceColour(String deviceColour) {
		this.deviceColour = deviceColour;
	}
	public String getRepertoryStatus() {
		return repertoryStatus;
	}
	public void setRepertoryStatus(String repertoryStatus) {
		this.repertoryStatus = repertoryStatus;
	}
	public String getDistributorID() {
		return distributorID;
	}
	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getCreatorUserName() {
		return creatorUserName;
	}
	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getCardID() {
		return CardID;
	}
	public void setCardID(String cardID) {
		CardID = cardID;
	}
	public int getDeviceOrderCount() {
		return deviceOrderCount;
	}
	public void setDeviceOrderCount(int deviceOrderCount) {
		this.deviceOrderCount = deviceOrderCount;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
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
	@Override
	public String toString() {
		return "DeviceInfo [deviceID=" + deviceID + ", SN=" + SN + ", CardID="
				+ CardID + ", deviceOrderCount=" + deviceOrderCount
				+ ", deviceColour=" + deviceColour + ", repertoryStatus="
				+ repertoryStatus + ", distributorID=" + distributorID
				+ ", distributorName=" + distributorName + ", deviceStatus="
				+ deviceStatus + ", remark=" + remark + ", creatorUserID="
				+ creatorUserID + ", creatorUserName=" + creatorUserName
				+ ", creatorDate=" + creatorDate + ", modifyUserID="
				+ modifyUserID + ", modifyDate=" + modifyDate + ", sysStatus="
				+ sysStatus + ", isreturn=" + isreturn + ", zlgm=" + zlgm
				+ ", fd1=" + fd1 + ", fd2=" + fd2 + ", allDevice=" + allDevice
				+ ", kyDevice=" + kyDevice + ", chDevice=" + chDevice
				+ ", wxDevice=" + wxDevice + ", deleDevice=" + deleDevice
				+ ", starttime=" + starttime + ", endtime=" + endtime
				+ ", rkDevice=" + rkDevice + ", bkyDevice=" + bkyDevice
				+ ", obj=" + obj + ", begindate=" + begindate + ", enddate="
				+ enddate + ", deallType=" + deallType + ", ifReturn="
				+ ifReturn + ", orderID=" + orderID + ", customerName="
				+ customerName + ", orderStatus=" + orderStatus
				+ ", supportCountry=" + supportCountry + ", modelNumber="
				+ modelNumber + ", frequencyRange=" + frequencyRange + "]";
	}

	public String allDevice;//设备总数
	public String kyDevice;//可以设备总数
	public String chDevice;//出库设备总数
	public String wxDevice;//维修设备总数
	public String deleDevice;//删除设备总数

	public String starttime;//开始时间
	public String endtime;//结束时间

	public String rkDevice;//入库设备总数
	public String bkyDevice;//不可用设备总数

	public DeviceInfo(String starttime, String endtime) {
		super();
		this.starttime = starttime;
		this.endtime = endtime;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getAllDevice() {
		return allDevice;
	}
	public void setAllDevice(String allDevice) {
		this.allDevice = allDevice;
	}
	public String getKyDevice() {
		return kyDevice;
	}
	public void setKyDevice(String kyDevice) {
		this.kyDevice = kyDevice;
	}
	public String getChDevice() {
		return chDevice;
	}
	public void setChDevice(String chDevice) {
		this.chDevice = chDevice;
	}
	public String getWxDevice() {
		return wxDevice;
	}
	public void setWxDevice(String wxDevice) {
		this.wxDevice = wxDevice;
	}
	public String getDeleDevice() {
		return deleDevice;
	}
	public void setDeleDevice(String deleDevice) {
		this.deleDevice = deleDevice;
	}

	private String obj;

	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}

	public String begindate;
	public String enddate;

	public String getRkDevice() {
		return rkDevice;
	}
	public void setRkDevice(String rkDevice) {
		this.rkDevice = rkDevice;
	}
	public String getBkyDevice() {
		return bkyDevice;
	}
	public void setBkyDevice(String bkyDevice) {
		this.bkyDevice = bkyDevice;
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

	private String deallType;
	private String ifReturn;
	private String orderID;
	private String customerName;
	private String orderStatus;

	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDeallType() {
		return deallType;
	}
	public void setDeallType(String deallType) {
		this.deallType = deallType;
	}
	public String getIfReturn() {
		return ifReturn;
	}
	public void setIfReturn(String ifReturn) {
		this.ifReturn = ifReturn;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	private String supportCountry;//支持的国家

	public String getSupportCountry() {
		return supportCountry;
	}
	public void setSupportCountry(String supportCountry) {
		this.supportCountry = supportCountry;
	}

	/** 型号**/
	private String modelNumber;
	/** 频段 **/
	private String frequencyRange;

	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getFrequencyRange() {
		return frequencyRange;
	}
	public void setFrequencyRange(String frequencyRange) {
		this.frequencyRange = frequencyRange;
	}


	public String recipientName;


	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String remarkcrk;


	public String getRemarkcrk() {
		return remarkcrk;
	}
	public void setRemarkcrk(String remarkcrk) {
		this.remarkcrk = remarkcrk;
	}

	public String shipmentDate;


	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public String getFirmWareVer() {
	    return firmWareVer;
	}
	public void setFirmWareVer(String firmWareVer) {
	    this.firmWareVer = firmWareVer;
	}
	public String getFirmWareAPKVer() {
	    return firmWareAPKVer;
	}
	public void setFirmWareAPKVer(String firmWareAPKVer) {
	    this.firmWareAPKVer = firmWareAPKVer;
	}
	public String getVersion() {
	    return version;
	}
	public void setVersion(String version) {
	    this.version = version;
	}
	public String getVersionAPK() {
	    return versionAPK;
	}
	public void setVersionAPK(String versionAPK) {
	    this.versionAPK = versionAPK;
	}







}
