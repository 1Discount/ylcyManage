package com.Manage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * 设备日志
 * @author lipeng
 */
public class DeviceLogsTest{

	private String SN;
	public String ifLogin;
	public String ifLoginSuccess;
	private String monthUsedFlow;
	private String logsDate;
	private String flowDistinction;
	public String getFlowDistinction() {
		return flowDistinction;
	}
	public void setFlowDistinction(String flowDistinction) {
		this.flowDistinction = flowDistinction;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getMonthUsedFlow() {
		return monthUsedFlow;
	}
	public void setMonthUsedFlow(String monthUsedFlow) {
		this.monthUsedFlow = monthUsedFlow;
	}
	public String getLogsDate() {
		return logsDate;
	}
	public void setLogsDate(String logsDate) {
		this.logsDate = logsDate;
	}
	public String getIfLogin() {
		return ifLogin;
	}
	public void setIfLogin(String ifLogin) {
		this.ifLogin = ifLogin;
	}
	public String getIfLoginSuccess() {
		return ifLoginSuccess;
	}
	public void setIfLoginSuccess(String ifLoginSuccess) {
		this.ifLoginSuccess = ifLoginSuccess;
	}
	
	
	
	 

}
