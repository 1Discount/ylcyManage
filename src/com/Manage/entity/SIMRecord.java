package com.Manage.entity;

/**
 * SIM卡记录
 * @author 沈超
 *
 */
public class SIMRecord {

	/**
	 * SIM卡唯一标识
	 */
	private String IMSI;
	
	/**
	 * 创建时间
	 */
	private String creatorTime;
	
	/**
	 * 创建日期
	 */
	private String creatorDate;
	
	/**
	 * 当天分配过的SN
	 */
	private String assignedSN;
	
	/**
	 * 国家
	 */
	private String country;
	
	/**
	 * SIM卡代号
	 */
	private String code;
	
	/**
	 * 当天累计使用流量
	 */
	private int useFlow;
	
	/**
	 * 可用卡总数
	 */
	private int SIMCount;
	
	
	//自定义字段
	private String dateBegin;
	private String dateEnd;
	private int useCount;

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}

	public String getCreatorTime() {
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	public String getCreatorDate() {
		return creatorDate;
	}

	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}

	public String getAssignedSN() {
		return assignedSN;
	}

	public void setAssignedSN(String assignedSN) {
		this.assignedSN = assignedSN;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getUseFlow() {
		return useFlow;
	}

	public void setUseFlow(int useFlow) {
		this.useFlow = useFlow;
	}

	public int getSIMCount() {
		return SIMCount;
	}

	public void setSIMCount(int sIMCount) {
		SIMCount = sIMCount;
	}

	public String getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
}
