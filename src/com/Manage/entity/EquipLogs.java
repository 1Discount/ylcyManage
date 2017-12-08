package com.Manage.entity;

/**
 * * @author wangbo: * @date 创建时间：2015-7-9 下午4:45:47 * @version 1.0 * @parameter
 * * @since * @return
 * 用来存储设备日志上传的表
 */
public class EquipLogs {

	public String ID;
	public String SN;
	public int NO;
	public String content;
	public int type;
	public int logSize;
	public String creatorUserID;
	public String creatorDate;
	public String modifyUserID;
	public String modifyDate;
	public String sysStatus;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public int getNO() {
		return NO;
	}
	public void setNO(int nO) {
		NO = nO;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLogSize() {
		return logSize;
	}
	public void setLogSize(int logSize) {
		this.logSize = logSize;
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

	
}
