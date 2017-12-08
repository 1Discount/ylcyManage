package com.Manage.entity;

/**
 * 设备升级配置表
 * @author Administrator
 *
 */
public class DeviceUpgrading {
	
	/**
	 * ID
	 */
	private String deviceUpgradingID;
	
	/**
	 * SN
	 */
	private String SN;
	
	/**
	 * 升级文件类型
	 */
	private String upgradeFileType;
	
	/**
	 * 是否强制升级
	 */
	private boolean ifForcedToUpgrade;
	
	/**
	 * 是否已升级
	 */
	private Boolean ifUpdated;
	
	/**
	 * 升级来源
	 */
	private int updateSource;
	
	/**
	 * 升级时间
	 */
	private String updateDate;
	
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
	
	/**查询辅助属性**/
	private String begainTime;
	private String endTime;

	public String getDeviceUpgradingID() {
		return deviceUpgradingID;
	}

	public void setDeviceUpgradingID(String deviceUpgradingID) {
		this.deviceUpgradingID = deviceUpgradingID;
	}

	public String getSN() {
		return SN;
	}

	public void setSN(String sN) {
		SN = sN;
	}

	public String getUpgradeFileType() {
		return upgradeFileType;
	}

	public void setUpgradeFileType(String upgradeFileType) {
		this.upgradeFileType = upgradeFileType;
	}

	public boolean getIfForcedToUpgrade() {
		return ifForcedToUpgrade;
	}

	public void setIfForcedToUpgrade(boolean ifForcedToUpgrade) {
		this.ifForcedToUpgrade = ifForcedToUpgrade;
	}

	public Boolean getIfUpdated() {
		return ifUpdated;
	}

	public void setIfUpdated(Boolean ifUpdated) {
		this.ifUpdated = ifUpdated;
	}

	public int getUpdateSource() {
	    return updateSource;
	}

	public void setUpdateSource(int updateSource) {
	    this.updateSource = updateSource;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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
}
