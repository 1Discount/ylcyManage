package com.Manage.entity;

/**
 * 企业用户表
 * @author jiang
 *
 */
public class Enterprise {
	private String enterpriseID;
	private String enterpriseName;
	private String enterprisePhone;
	private String enterpriseAddress;
	private Double score;
	private Double balance;
	private String priceConfiguration;
	private String creatorDate;
	private String creatorUserID;
	private String creatorUserName;
	private String modifyDate;
	private String modifyUserID;
	private String modifyUserName;
	private String sysStatus;
	private String remark;
	private String begindateForQuery;
	private String enddate;
	
	

	public String getBegindateForQuery()
	{
		return begindateForQuery;
	}


	public void setBegindateForQuery(String begindateForQuery)
	{
		this.begindateForQuery = begindateForQuery;
	}


	public String getEnddate()
	{
		return enddate;
	}


	public void setEnddate(String enddate)
	{
		this.enddate = enddate;
	}


	public Enterprise()
	{
		super();
	}


	public Enterprise(String enterpriseID, String enterpriseName, String enterprisePhone, String enterpriseAddress, Double score, Double balance, String priceConfiguration, String creatorDate, String creatorUserID, String creatorUserName, String modifyDate, String modifyUserID, String modifyUserName, String sysStatus)
	{
		super();
		this.enterpriseID = enterpriseID;
		this.enterpriseName = enterpriseName;
		this.enterprisePhone = enterprisePhone;
		this.enterpriseAddress = enterpriseAddress;
		this.score = score;
		this.balance = balance;
		this.priceConfiguration = priceConfiguration;
		this.creatorDate = creatorDate;
		this.creatorUserID = creatorUserID;
		this.creatorUserName = creatorUserName;
		this.modifyDate = modifyDate;
		this.modifyUserID = modifyUserID;
		this.modifyUserName = modifyUserName;
		this.sysStatus = sysStatus;
	}


	public String getRemark()
	{
		return remark;
	}


	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	public String getEnterpriseID()
	{
		return enterpriseID;
	}


	public void setEnterpriseID(String enterpriseID)
	{
		this.enterpriseID = enterpriseID;
	}


	public String getEnterpriseName()
	{
		return enterpriseName;
	}


	public void setEnterpriseName(String enterpriseName)
	{
		this.enterpriseName = enterpriseName;
	}


	public String getEnterprisePhone()
	{
		return enterprisePhone;
	}


	public void setEnterprisePhone(String enterprisePhone)
	{
		this.enterprisePhone = enterprisePhone;
	}


	public String getEnterpriseAddress()
	{
		return enterpriseAddress;
	}


	public void setEnterpriseAddress(String enterpriseAddress)
	{
		this.enterpriseAddress = enterpriseAddress;
	}


	public Double getScore()
	{
		return score;
	}


	public void setScore(Double score)
	{
		this.score = score;
	}


	public Double getBalance()
	{
		return balance;
	}


	public void setBalance(Double balance)
	{
		this.balance = balance;
	}


	public String getPriceConfiguration()
	{
		return priceConfiguration;
	}


	public void setPriceConfiguration(String priceConfiguration)
	{
		this.priceConfiguration = priceConfiguration;
	}


	public String getCreatorDate()
	{
		return creatorDate;
	}


	public void setCreatorDate(String creatorDate)
	{
		this.creatorDate = creatorDate;
	}


	public String getCreatorUserID()
	{
		return creatorUserID;
	}


	public void setCreatorUserID(String creatorUserID)
	{
		this.creatorUserID = creatorUserID;
	}


	public String getCreatorUserName()
	{
		return creatorUserName;
	}


	public void setCreatorUserName(String creatorUserName)
	{
		this.creatorUserName = creatorUserName;
	}


	public String getModifyDate()
	{
		return modifyDate;
	}


	public void setModifyDate(String modifyDate)
	{
		this.modifyDate = modifyDate;
	}


	public String getModifyUserID()
	{
		return modifyUserID;
	}


	public void setModifyUserID(String modifyUserID)
	{
		this.modifyUserID = modifyUserID;
	}


	public String getModifyUserName()
	{
		return modifyUserName;
	}


	public void setModifyUserName(String modifyUserName)
	{
		this.modifyUserName = modifyUserName;
	}


	public String getSysStatus()
	{
		return sysStatus;
	}


	public void setSysStatus(String sysStatus)
	{
		this.sysStatus = sysStatus;
	}
	
	
}
