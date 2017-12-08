package com.Manage.entity;

/**
 * SIMServer.java
 * @author tangming@easy2go.cn 2015-5-27
 *
 */
public class SIMServer {	
	/** SIM服务器ID **/
	public String SIMServerID;
	/** 编号（服务器IP的后3位） **/
	public String serverCode;
	/** IP地址 **/
	public String IP;
	/** SIM卡总数量 **/
	public int SIMCount;
	/** 可用SIM数量 **/
	public int availableSIMCount;
	/** 服务器状态 **/
	public String serverStatus;
	
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
	
	public String getSIMServerID() {
		return SIMServerID;
	}
	public void setSIMServerID(String sIMServerID) {
		SIMServerID = sIMServerID;
	}
	public String getServerCode() {
		return serverCode;
	}
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public int getSIMCount() {
		return SIMCount;
	}
	public void setSIMCount(int sIMCount) {
		SIMCount = sIMCount;
	}
	public int getAvailableSIMCount() {
		return availableSIMCount;
	}
	public void setAvailableSIMCount(int availableSIMCount) {
		this.availableSIMCount = availableSIMCount;
	}
	public String getServerStatus() {
		return serverStatus;
	}
	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
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

}
