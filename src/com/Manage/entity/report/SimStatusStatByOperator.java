package com.Manage.entity.report;

/**
 * CountryInfo.java
 * @author tangming@easy2go.cn 2015-5-26
 *
 */
public class SimStatusStatByOperator {
	/** 运营商ID **/
	public String operatorID;
	/** 运营商名称 **/
	public String operatorName;
	/** 国家名称 **/
	public String countryName;
	/** 国家编号 MCC **/
	public String MCC;
	/** 大洲 **/
	public String continent;
	/** SIM卡的运营商 **/
	public String trademark;

	// status

	/** SIM卡总量 **/
	public int simsTotalCount;
	/** 可用 SIM卡量 **/
	public int avalableSimsCount;
	/** 使用中 SIM卡量 **/
	public int usingSimsCount;
	/** 不可用 SIM卡量 **/
	public int unavalableSimsCount;
	/** 停用 SIM卡量 **/
	public int deadSimsCount;
	/** 可用 SIM卡量百分比 **/
	public double avalableSimsCountPercent;
	/** 使用中 SIM卡量百分比 **/
	public double usingSimsCountPercent;
	/** 不可用 SIM卡量百分比 **/
	public double unavalableSimsCountPercent;
	/** 停用 SIM卡量百分比 **/
	public double deadSimsCountPercent;

	/** 流量预警 SIM卡量 **/
	public int alertSimsCount;

	/** Helper fields **/
	public boolean selected;

	/**
	 * 计算百分比
	 */
	public void caculatePercents(boolean ifMultifiedBy100) {
		double multifier = 1.0;
		if (ifMultifiedBy100) {
			multifier = 100.0;
		}
		if (simsTotalCount > 0) {
			avalableSimsCountPercent = multifier * (double) avalableSimsCount / (double) simsTotalCount;
			usingSimsCountPercent = multifier * (double) usingSimsCount / (double) simsTotalCount;
			unavalableSimsCountPercent = multifier * (double) unavalableSimsCount / (double) simsTotalCount;
			deadSimsCountPercent = multifier * (double) deadSimsCount / (double) simsTotalCount;
		} else  {
			avalableSimsCountPercent = 0;
			usingSimsCountPercent = 0;
			unavalableSimsCountPercent = 0;
			deadSimsCountPercent = 0;
		}
	}

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getMCC() {
		return MCC;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	public int getSimsTotalCount() {
		return simsTotalCount;
	}

	public void setSimsTotalCount(int simsTotalCount) {
		this.simsTotalCount = simsTotalCount;
	}

	public int getAvalableSimsCount() {
		return avalableSimsCount;
	}

	public void setAvalableSimsCount(int avalableSimsCount) {
		this.avalableSimsCount = avalableSimsCount;
	}

	public int getUsingSimsCount() {
		return usingSimsCount;
	}

	public void setUsingSimsCount(int usingSimsCount) {
		this.usingSimsCount = usingSimsCount;
	}

	public int getUnavalableSimsCount() {
		return unavalableSimsCount;
	}

	public void setUnavalableSimsCount(int unavalableSimsCount) {
		this.unavalableSimsCount = unavalableSimsCount;
	}

	public int getDeadSimsCount() {
		return deadSimsCount;
	}

	public void setDeadSimsCount(int deadSimsCount) {
		this.deadSimsCount = deadSimsCount;
	}

	public double getAvalableSimsCountPercent() {
		return avalableSimsCountPercent;
	}

	public void setAvalableSimsCountPercent(double avalableSimsCountPercent) {
		this.avalableSimsCountPercent = avalableSimsCountPercent;
	}

	public double getUsingSimsCountPercent() {
		return usingSimsCountPercent;
	}

	public void setUsingSimsCountPercent(double usingSimsCountPercent) {
		this.usingSimsCountPercent = usingSimsCountPercent;
	}

	public double getUnavalableSimsCountPercent() {
		return unavalableSimsCountPercent;
	}

	public void setUnavalableSimsCountPercent(double unavalableSimsCountPercent) {
		this.unavalableSimsCountPercent = unavalableSimsCountPercent;
	}

	public double getDeadSimsCountPercent() {
		return deadSimsCountPercent;
	}

	public void setDeadSimsCountPercent(double deadSimsCountPercent) {
		this.deadSimsCountPercent = deadSimsCountPercent;
	}

	public int getAlertSimsCount() {
		return alertSimsCount;
	}

	public void setAlertSimsCount(int alertSimsCount) {
		this.alertSimsCount = alertSimsCount;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

// 待用
//	public SimStatusStatByOperator(String operatorID, String operatorName,
//			String countryName, String mCC, String continent, String trademark,
//			int simsTotalCount, int avalableSimsCount, int usingSimsCount,
//			int unavalableSimsCount, int deadSimsCount,
//			double avalableSimsCountPercent, double usingSimsCountPercent,
//			double unavalableSimsCountPercent, double deadSimsCountPercent,
//			int alertSimsCount, boolean selected) {
//		super();
//		this.operatorID = operatorID;
//		this.operatorName = operatorName;
//		this.countryName = countryName;
//		MCC = mCC;
//		this.continent = continent;
//		this.trademark = trademark;
//		this.simsTotalCount = simsTotalCount;
//		this.avalableSimsCount = avalableSimsCount;
//		this.usingSimsCount = usingSimsCount;
//		this.unavalableSimsCount = unavalableSimsCount;
//		this.deadSimsCount = deadSimsCount;
//		this.avalableSimsCountPercent = avalableSimsCountPercent;
//		this.usingSimsCountPercent = usingSimsCountPercent;
//		this.unavalableSimsCountPercent = unavalableSimsCountPercent;
//		this.deadSimsCountPercent = deadSimsCountPercent;
//		this.alertSimsCount = alertSimsCount;
//		this.selected = selected;
//	}

}
