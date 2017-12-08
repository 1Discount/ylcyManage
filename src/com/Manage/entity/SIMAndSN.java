package com.Manage.entity;
/** * @author  wangbo: * @date 创建时间：2016-1-8 上午11:40:09 * @version 1.0 * @parameter  * @since  * @return  */
public class SIMAndSN {
	private String ID;
	private String IMSI;
	private String SIMID;
	private String SN;
	private String allotTime;
	private String MCC;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getSIMID() {
		return SIMID;
	}
	public void setSIMID(String sIMID) {
		SIMID = sIMID;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getAllotTime() {
		return allotTime;
	}
	public void setAllotTime(String allotTime) {
		this.allotTime = allotTime;
	}
	public String getMCC() {
		return MCC;
	}
	public void setMCC(String mCC) {
		MCC = mCC;
	}
	
	

}
