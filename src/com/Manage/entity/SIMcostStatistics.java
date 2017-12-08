package com.Manage.entity;

public class SIMcostStatistics
{
	private String IMSI;
	private String creatorDate;
	private String assignedSN;
	private String country;
	private double primeCost;
	private long useFlow;
	private String countryList;
	private String begindate;
	private String enddate;
	private String isMoreCountry;
	
	
	
	public String getIsMoreCountry()
	{
		return isMoreCountry;
	}
	public void setIsMoreCountry(String isMoreCountry)
	{
		this.isMoreCountry = isMoreCountry;
	}
	public String getCountryList()
	{
		return countryList;
	}
	public void setCountryList(String countryList)
	{
		this.countryList = countryList;
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
	public String getIMSI()
	{
		return IMSI;
	}
	public void setIMSI(String iMSI)
	{
		IMSI = iMSI;
	}
	public String getCreatorDate()
	{
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate)
	{
		this.creatorDate = creatorDate;
	}
	public String getAssignedSN()
	{
		return assignedSN;
	}
	public void setAssignedSN(String assignedSN)
	{
		this.assignedSN = assignedSN;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public double getPrimeCost()
	{
		return primeCost;
	}
	public void setPrimeCost(double primeCost)
	{
		this.primeCost = primeCost;
	}
	public long getUseFlow()
	{
		return useFlow;
	}
	public void setUseFlow(long useFlow)
	{
		this.useFlow = useFlow;
	}
	public SIMcostStatistics(String iMSI, String creatorDate, String assignedSN, String country, double primeCost, long useFlow,String countryList)
	{
		super();
		IMSI = iMSI;
		this.creatorDate = creatorDate;
		this.assignedSN = assignedSN;
		this.country = country;
		this.primeCost = primeCost;
		this.useFlow = useFlow;
		this.countryList = countryList;
	}
	
	public SIMcostStatistics()
	{
		super();
	}
	
}
