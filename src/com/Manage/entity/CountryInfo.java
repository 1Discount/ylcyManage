package com.Manage.entity;

/**
 * CountryInfo.java
 * @author tangming@easy2go.cn 2015-5-26
 *
 */
public class CountryInfo {
	/** 国家ID **/
	public String countryID;
	/** 国家名称 **/
	public String countryName;
	/** 国家编号 MCC **/
	public int countryCode;
	/** 大洲 **/
	public String continent;
	/** 流量交易总数 **/
	public String flowCount;
	/** 订单总数 **/
	public String orderCount;
	/** 数据服务价格 元/天 **/
	public double flowPrice;
	/**备注**/
	public String remark;
	/**创建人ID**/
	public String creatorUserID;
	/**创建人姓名**/
	public String creatorUserName;
	/**创建时间**/
	public String creatorDate;
	/**修改人ID**/
	public String modifyUserID;
	/**修改时间 **/
	public String modifyDate;
	/**系统状态**/
	public int sysStatus;

	/**图片路径**/
	public String imgsrc;

	/**限速策略**/
	public String limitSpeedStr;

	/** Helper fields **/
	public boolean selected;

	/** 20160115需求新增 本地卡代号说明 */
	public String localCodeExplain;
	/** 20160115需求新增 按流量价格 */
	public Double pressFlowPrice;

	/**排序序号**/
	public int sortCode;

	public String tableName;
	
	/**
	 * 时差
	 */
	public String timeDifference;
	
	/**
	 * 英文简称
	 * @return
	 */
	public String countryNameEnShort;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getSortCode() {
		return sortCode;
	}
	public void setSortCode(int sortCode) {
		this.sortCode = sortCode;
	}
	public String getLimitSpeedStr() {
		return limitSpeedStr;
	}
	public void setLimitSpeedStr(String limitSpeedStr) {
		this.limitSpeedStr = limitSpeedStr;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getCountryID() {
		return countryID;
	}
	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public String getFlowCount() {
		return flowCount;
	}
	public void setFlowCount(String flowCount) {
		this.flowCount = flowCount;
	}
	public String getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}
	public double getFlowPrice() {
		return flowPrice;
	}
	public void setFlowPrice(double flowPrice) {
		this.flowPrice = flowPrice;
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
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
    public String getLocalCodeExplain() {
        return localCodeExplain;
    }
    public void setLocalCodeExplain(String localCodeExplain) {
        this.localCodeExplain = localCodeExplain;
    }
    public Double getPressFlowPrice() {
        return pressFlowPrice;
    }
    public void setPressFlowPrice(Double pressFlowPrice) {
        this.pressFlowPrice = pressFlowPrice;
    }
    public String getTimeDifference() {
        return timeDifference;
    }
    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }
    public String getCountryNameEnShort() {
        return countryNameEnShort;
    }
    public void setCountryNameEnShort(String countryNameEnShort) {
        this.countryNameEnShort = countryNameEnShort;
    }
    
}
