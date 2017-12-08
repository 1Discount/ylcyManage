package com.Manage.entity;
/** * @author  wangbo: * @date 创建时间：2015-11-10 下午5:43:02 * @version 1.0 * @parameter  * @since  * @return  */
public class GStrenthData {
	/**ID**/
	private String ID;
	/**基站信息**/
	private String Jizhan;
	/** 经度**/
	private String Lat;
	/** 纬度**/
	private String Lon;
	/** 国家码**/
	private String MCC;
	/**频次**/
	private String Frequency;
	/**本地信号**/
	private String gStrenth;
	/**漫游信号**/
	private String roamGStrenth;
	/**创建时间**/
	private String creatorDate;
	/**上次更新时间**/
	private String lastTime;
	/**SN**/
	private String SN;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getJizhan() {
		return Jizhan;
	}
	public void setJizhan(String jizhan) {
		Jizhan = jizhan;
	}
	public String getLat() {
		return Lat;
	}
	public void setLat(String lat) {
		Lat = lat;
	}
	public String getLon() {
		return Lon;
	}
	public void setLon(String lon) {
		Lon = lon;
	}
	public String getMCC() {
		return MCC;
	}
	public void setMCC(String mCC) {
		MCC = mCC;
	}
	public String getFrequency() {
		return Frequency;
	}
	public void setFrequency(String frequency) {
		Frequency = frequency;
	}
	public String getgStrenth() {
		return gStrenth;
	}
	public void setgStrenth(String gStrenth) {
		this.gStrenth = gStrenth;
	}
	public String getRoamGStrenth() {
		return roamGStrenth;
	}
	public void setRoamGStrenth(String roamGStrenth) {
		this.roamGStrenth = roamGStrenth;
	}
	public String getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
}
