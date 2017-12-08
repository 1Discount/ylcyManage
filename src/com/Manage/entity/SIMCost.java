package com.Manage.entity;


public class SIMCost
{

	private String country;
	private int totalSim;
	private double totalPrice;
	private long totalFlow;
	private long totalData;
	private String countryName;
	private String planType;
	private String simNum;
	private String assignedSN;
	private String IMSI;
	private String userDays;
	private String begindate;
	private String enddate;
	private String MCC;
	private String simAlias;
	
	public String getSimAlias()
	{
		return simAlias;
	}
	public void setSimAlias(String simAlias)
	{
		this.simAlias = simAlias;
	}
	public String getMCC()
	{
		return MCC;
	}
	public void setMCC(String mCC)
	{
		MCC = mCC;
	}
	public String getBegindate()
	{
		return begindate;
	}
	public void setBegindate(String begindate)
	{
		this.begindate = begindate;
	}
	public String getEnddate()
	{
		return enddate;
	}
	public void setEnddate(String enddate)
	{
		this.enddate = enddate;
	}
	public String getUserDays()
	{
		return userDays;
	}
	public void setUserDays(String userDays)
	{
		this.userDays = userDays;
	}
	public String getIMSI()
	{
		return IMSI;
	}
	public void setIMSI(String iMSI)
	{
		IMSI = iMSI;
	}
	public String getAssignedSN()
	{
		return assignedSN;
	}
	public void setAssignedSN(String assignedSN)
	{
		this.assignedSN = assignedSN;
	}
	public String getSimNum()
	{
		return simNum;
	}
	public void setSimNum(String simNum)
	{
		this.simNum = simNum;
	}
	public String getPlanType()
	{
		return planType;
	}
	public void setPlanType(String planType)
	{
		this.planType = planType;
	}
	public String getCountryName()
	{
		return countryName;
	}
	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}
	public long getTotalData()
	{
		return totalData;
	}
	public void setTotalData(long totalData)
	{
		this.totalData = totalData;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public int getTotalSim()
	{
		return totalSim;
	}
	public void setTotalSim(int totalSim)
	{
		this.totalSim = totalSim;
	}
	public double getTotalPrice()
	{
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	
	public long getTotalFlow()
	{
		return totalFlow;
	}
	public void setTotalFlow(long totalFlow)
	{
		this.totalFlow = totalFlow;
	}
	
	
}
