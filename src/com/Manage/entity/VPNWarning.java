package com.Manage.entity;

public class VPNWarning {
	
	/**
	 * ID
	 */
	private String vpnWarningID;
	
	/**
	 * IP
	 */
	private String IP;
	
	/**
	 * 状态
	 */
	private String warningStatus;
	
	/**
	 * 是否短信提醒
	 */
	private boolean ifMsgAlter;
	
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
	private boolean sysStatus;

	public String getVpnWarningID() {
		return vpnWarningID;
	}

	public void setVpnWarningID(String vpnWarningID) {
		this.vpnWarningID = vpnWarningID;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getWarningStatus() {
		return warningStatus;
	}

	public boolean getIfMsgAlter() {
		return ifMsgAlter;
	}

	public void setIfMsgAlter(boolean ifMsgAlter) {
		this.ifMsgAlter = ifMsgAlter;
	}

	public void setWarningStatus(String warningStatus) {
		this.warningStatus = warningStatus;
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

	public boolean isSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(boolean sysStatus) {
		this.sysStatus = sysStatus;
	}
	
}
