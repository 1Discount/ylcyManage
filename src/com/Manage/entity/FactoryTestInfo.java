package com.Manage.entity;

import java.util.List;

/**
 * 工厂测试记录表
 * @author 沈超
 *
 */
public class FactoryTestInfo {
	
	/**
	 * ID
	 */
	private int factoryTestInfoID;
	
	/**
	 * 设备号
	 */
	private String SN;
	
	/**
	 * 当前SSID
	 */
	private String SSID;
	
	/**
	 * 漫游-漫游-应用版本号(应用)
	 */
	private String roam_appVersionNum;
	
	/**
	 * 漫游-apk版本号(APK)
	 */
	private String roam_apkVersionNum;
	
	/**
	 * 漫游-IMEI(APK)
	 */
	private String roam_IMEI;
	
	/**
	 * 漫游-SN(APK)
	 */
	private String roam_SN;
	
	/**
	 * 漫游-ICCID(APK)
	 */
	private String roam_ICCID;
	
	/**
	 * 漫游-SIM卡状态(APK)
	 */
	private String roam_SIMStatus;
	
	/**
	 * 漫游-校准标志位(应用)
	 */
	private String roam_calibrationMark;
	
	/**
	 * 漫游-网络类型(APK)
	 */
	private String roam_networkType;
	
	/**
	 * 漫游-网络强度(APK)
	 */
	private String roam_networkStrength;
	
	/**
	 * 漫游-数据连接状态(APK)
	 */
	private String roam_dataConnectionStatus;
	
	/**
	 * 本地-WIFI PWD(APK)
	 */
	private String local_wifiPwd;
	
	/**
	 * 本地-应用版本号(应用)
	 */
	private String local_appVersionNum;
	
	/**
	 * 本地-APK版本号(APK)
	 */
	private String local_apkVersionNum;
	
	/**
	 * 本地-IMEI(APK)
	 */
	private String local_IMEI;
	
	/**
	 * 本地-SN(APK)
	 */
	private String local_SN;
	
	/**
	 * 本地-ICCID(APK)
	 */
	private String local_ICCID;
	
	/**
	 * 本地-SIM卡状态(APK)
	 */
	private String local_SIMStatus;
	
	/**
	 * 本地-校准标志位(应用)
	 */
	private String local_calibrationMark;
	
	/**
	 * 本地-网络类型(APK)
	 */
	private String local_networkType;
	
	/**
	 * 本地-网络强度(APK)
	 */
	private String local_networkStrength;
	
	/**
	 * 本地-数据连接状态(APK)
	 */
	private String local_dataConnectionStatus;
	
	/**
	 * 本地-串口通信测试(应用)
	 */
	private String local_serialComTest;
	
	/**
	 * 上传-联网测试结果(客户端APP)
	 */
	private String upload_networkedTestResult;
	
	/**
	 * 结果(开始测试/测试中/成功/失败)
	 */
	private String result;
	
	/**
	 * 创建人ID
	 */
	private String creatorUserID;
	
	/**
	 * 创建时间
	 */
	private String creatorDate;
	
	/**
	 * 创建人姓名
	 */
	private String creatorUserName;
	
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
	
	/**
	 * 是否插卡
	 */
	private String installCard;
	
	/**
	 * GPS
	 */
	private String gps;
	
	/**
	 * power
	 */
	private String power;
	
	
	private List<String> snList;
	
	public FactoryTestInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public FactoryTestInfo(int factoryTestInfoID, String sN, String sSID, String roam_appVersionNum, String roam_apkVersionNum, String roam_IMEI, String roam_SN, String roam_ICCID, String roam_SIMStatus, String roam_calibrationMark, String roam_networkType, String roam_networkStrength, String roam_dataConnectionStatus, String local_wifiPwd, String local_appVersionNum, String local_apkVersionNum, String local_IMEI, String local_SN, String local_ICCID, String local_SIMStatus, String local_calibrationMark, String local_networkType, String local_networkStrength, String local_dataConnectionStatus, String local_serialComTest, String upload_networkedTestResult, String result, String creatorUserID, String creatorDate, String creatorUserName, String modifyUserID, String modifyDate,
			boolean sysStatus)
	{
		super();
		this.factoryTestInfoID = factoryTestInfoID;
		SN = sN;
		SSID = sSID;
		this.roam_appVersionNum = roam_appVersionNum;
		this.roam_apkVersionNum = roam_apkVersionNum;
		this.roam_IMEI = roam_IMEI;
		this.roam_SN = roam_SN;
		this.roam_ICCID = roam_ICCID;
		this.roam_SIMStatus = roam_SIMStatus;
		this.roam_calibrationMark = roam_calibrationMark;
		this.roam_networkType = roam_networkType;
		this.roam_networkStrength = roam_networkStrength;
		this.roam_dataConnectionStatus = roam_dataConnectionStatus;
		this.local_wifiPwd = local_wifiPwd;
		this.local_appVersionNum = local_appVersionNum;
		this.local_apkVersionNum = local_apkVersionNum;
		this.local_IMEI = local_IMEI;
		this.local_SN = local_SN;
		this.local_ICCID = local_ICCID;
		this.local_SIMStatus = local_SIMStatus;
		this.local_calibrationMark = local_calibrationMark;
		this.local_networkType = local_networkType;
		this.local_networkStrength = local_networkStrength;
		this.local_dataConnectionStatus = local_dataConnectionStatus;
		this.local_serialComTest = local_serialComTest;
		this.upload_networkedTestResult = upload_networkedTestResult;
		this.result = result;
		this.creatorUserID = creatorUserID;
		this.creatorDate = creatorDate;
		this.creatorUserName = creatorUserName;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
		this.sysStatus = sysStatus;
	}

	//自定义属性
	private String begainTime;
	private String endTime;
	private String distinct; //是否去重复

	public int getFactoryTestInfoID() {
		return factoryTestInfoID;
	}

	public void setFactoryTestInfoID(int factoryTestInfoID) {
		this.factoryTestInfoID = factoryTestInfoID;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public String getRoam_appVersionNum() {
		return roam_appVersionNum;
	}

	public void setRoam_appVersionNum(String roam_appVersionNum) {
		this.roam_appVersionNum = roam_appVersionNum;
	}

	public String getRoam_apkVersionNum() {
		return roam_apkVersionNum;
	}

	public void setRoam_apkVersionNum(String roam_apkVersionNum) {
		this.roam_apkVersionNum = roam_apkVersionNum;
	}

	public String getRoam_IMEI() {
		return roam_IMEI;
	}

	public void setRoam_IMEI(String roam_IMEI) {
		this.roam_IMEI = roam_IMEI;
	}

	public String getRoam_SN() {
		return roam_SN;
	}

	public void setRoam_SN(String roam_SN) {
		this.roam_SN = roam_SN;
	}

	public String getRoam_ICCID() {
		return roam_ICCID;
	}

	public void setRoam_ICCID(String roam_ICCID) {
		this.roam_ICCID = roam_ICCID;
	}

	public String getRoam_SIMStatus() {
		return roam_SIMStatus;
	}

	public void setRoam_SIMStatus(String roam_SIMStatus) {
		this.roam_SIMStatus = roam_SIMStatus;
	}

	public String getRoam_calibrationMark() {
		return roam_calibrationMark;
	}

	public void setRoam_calibrationMark(String roam_calibrationMark) {
		this.roam_calibrationMark = roam_calibrationMark;
	}

	public String getRoam_networkType() {
		return roam_networkType;
	}

	public void setRoam_networkType(String roam_networkType) {
		this.roam_networkType = roam_networkType;
	}

	public String getRoam_networkStrength() {
		return roam_networkStrength;
	}

	public void setRoam_networkStrength(String roam_networkStrength) {
		this.roam_networkStrength = roam_networkStrength;
	}

	public String getRoam_dataConnectionStatus() {
		return roam_dataConnectionStatus;
	}

	public void setRoam_dataConnectionStatus(String roam_dataConnectionStatus) {
		this.roam_dataConnectionStatus = roam_dataConnectionStatus;
	}

	public String getLocal_wifiPwd() {
		return local_wifiPwd;
	}

	public void setLocal_wifiPwd(String local_wifiPwd) {
		this.local_wifiPwd = local_wifiPwd;
	}

	public String getLocal_appVersionNum() {
		return local_appVersionNum;
	}

	public void setLocal_appVersionNum(String local_appVersionNum) {
		this.local_appVersionNum = local_appVersionNum;
	}

	public String getLocal_apkVersionNum() {
		return local_apkVersionNum;
	}

	public void setLocal_apkVersionNum(String local_apkVersionNum) {
		this.local_apkVersionNum = local_apkVersionNum;
	}

	public String getLocal_IMEI() {
		return local_IMEI;
	}

	public void setLocal_IMEI(String local_IMEI) {
		this.local_IMEI = local_IMEI;
	}

	public String getLocal_SN() {
		return local_SN;
	}

	public void setLocal_SN(String local_SN) {
		this.local_SN = local_SN;
	}

	public String getLocal_ICCID() {
		return local_ICCID;
	}

	public void setLocal_ICCID(String local_ICCID) {
		this.local_ICCID = local_ICCID;
	}

	public String getLocal_SIMStatus() {
		return local_SIMStatus;
	}

	public void setLocal_SIMStatus(String local_SIMStatus) {
		this.local_SIMStatus = local_SIMStatus;
	}

	public String getLocal_calibrationMark() {
		return local_calibrationMark;
	}

	public void setLocal_calibrationMark(String local_calibrationMark) {
		this.local_calibrationMark = local_calibrationMark;
	}

	public String getLocal_networkType() {
		return local_networkType;
	}

	public void setLocal_networkType(String local_networkType) {
		this.local_networkType = local_networkType;
	}

	public String getLocal_networkStrength() {
		return local_networkStrength;
	}

	public void setLocal_networkStrength(String local_networkStrength) {
		this.local_networkStrength = local_networkStrength;
	}

	public String getLocal_dataConnectionStatus() {
		return local_dataConnectionStatus;
	}

	public void setLocal_dataConnectionStatus(String local_dataConnectionStatus) {
		this.local_dataConnectionStatus = local_dataConnectionStatus;
	}

	public String getLocal_serialComTest() {
		return local_serialComTest;
	}

	public void setLocal_serialComTest(String local_serialComTest) {
		this.local_serialComTest = local_serialComTest;
	}

	public String getUpload_networkedTestResult() {
		return upload_networkedTestResult;
	}

	public void setUpload_networkedTestResult(String upload_networkedTestResult) {
		this.upload_networkedTestResult = upload_networkedTestResult;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public boolean isSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(boolean sysStatus) {
		this.sysStatus = sysStatus;
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

	public String getDistinct() {
		return distinct;
	}

	public void setDistinct(String distinct) {
		this.distinct = distinct;
	}

	public String getInstallCard() {
	    return installCard;
	}

	public void setInstallCard(String installCard) {
	    this.installCard = installCard;
	}

	public String getGps() {
	    return gps;
	}

	public void setGps(String gps) {
	    this.gps = gps;
	}

	public String getPower() {
	    return power;
	}

	public void setPower(String power) {
	    this.power = power;
	}

	public List<String> getSnList() {
	    return snList;
	}

	public void setSnList(List<String> snList) {
	    this.snList = snList;
	}
	
	
}
