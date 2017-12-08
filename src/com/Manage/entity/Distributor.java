package com.Manage.entity;

/**
 * Distributor.java
 * 
 * @author tangming@easy2go.cn 2015-6-24
 * 
 */
public class Distributor
{
	/** 渠道商ID **/
	public String distributorID;

	/** 经营单位, 如公司名等称呼 **/
	public String company;

	/** 联系人姓名 **/
	public String operatorName;

	/** 联系电话 **/
	public String operatorTel;

	/** 联系email **/
	public String operatorEmail;

	/** 地址 **/
	public String address;

	/** 渠道商类型 **/
	public String type;

	/** 结算方式 **/
	public String paymentMethod;

	/** 积分 **/
	public int score;

	/** 备注 **/
	public String remark;

	/** 创建人ID **/
	public String creatorUserID;

	/** 创建时间 **/
	public String creatorDate;

	/** 创建人姓名 **/
	public String creatorUserName;

	/** 修改人ID **/
	public String modifyUserID;

	/** 修改时间 **/
	public String modifyDate;

	/** 系统状态 **/
	public int sysStatus;

	/**登录用户名**/
	public String standbyOperator;

	/** 渠道商公告 **/
	public String announcement;

	/** 渠道层级 **/
	public String grade;

	/** 归属渠道ID **/
	public String parentsDisID;

	/** 归属渠道名称 **/
	public String parentsDisName;
	
	/**余额**/
	public String balance;
	
	/**角色ID **/
	public String roleID;
	/**角色名称**/
	public String roleName;
	
	/**渠道商价格配置**/
	public String priceConfiguration;
	
	public String serverCode;

	public String getPriceConfiguration()
	{
		return priceConfiguration;
	}



	public void setPriceConfiguration(String priceConfiguration)
	{
		this.priceConfiguration = priceConfiguration;
	}



	public String getBalance()
	{
		return balance;
	}



	public void setBalance(String balance)
	{
		this.balance = balance;
	}



	public String getGrade()
	{
		return grade;
	}



	public void setGrade(String grade)
	{
		this.grade = grade;
	}



	public String getParentsDisID()
	{
		return parentsDisID;
	}



	public void setParentsDisID(String parentsDisID)
	{
		this.parentsDisID = parentsDisID;
	}



	public String getParentsDisName()
	{
		return parentsDisName;
	}



	public void setParentsDisName(String parentsDisName)
	{
		this.parentsDisName = parentsDisName;
	}



	public String getAnnouncement()
	{
		return announcement;
	}



	public void setAnnouncement(String announcement)
	{
		this.announcement = announcement;
	}



	public String getStandbyOperator()
	{
		return standbyOperator;
	}



	public void setStandbyOperator(String standbyOperator)
	{
		this.standbyOperator = standbyOperator;
	}

	public String userName;

	private String password;// 用户登录密码



	public String getPassword()
	{
		return password;
	}



	public void setPassword(String password)
	{
		this.password = password;
	}



	public String getUserName()
	{
		return userName;
	}



	public void setUserName(String userName)
	{
		this.userName = userName;
	}



	public String getDistributorID()
	{
		return distributorID;
	}



	public void setDistributorID(String distributorID)
	{
		this.distributorID = distributorID;
	}



	public String getCompany()
	{
		return company;
	}



	public void setCompany(String company)
	{
		this.company = company;
	}



	public String getOperatorName()
	{
		return operatorName;
	}



	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}



	public String getOperatorTel()
	{
		return operatorTel;
	}



	public void setOperatorTel(String operatorTel)
	{
		this.operatorTel = operatorTel;
	}



	public String getOperatorEmail()
	{
		return operatorEmail;
	}



	public void setOperatorEmail(String operatorEmail)
	{
		this.operatorEmail = operatorEmail;
	}



	public String getAddress()
	{
		return address;
	}



	public void setAddress(String address)
	{
		this.address = address;
	}



	public String getType()
	{
		return type;
	}



	public void setType(String type)
	{
		this.type = type;
	}



	public String getPaymentMethod()
	{
		return paymentMethod;
	}



	public void setPaymentMethod(String paymentMethod)
	{
		this.paymentMethod = paymentMethod;
	}



	public int getScore()
	{
		return score;
	}



	public void setScore(int score)
	{
		this.score = score;
	}



	public String getRemark()
	{
		return remark;
	}



	public void setRemark(String remark)
	{
		this.remark = remark;
	}



	public String getCreatorUserID()
	{
		return creatorUserID;
	}



	public void setCreatorUserID(String creatorUserID)
	{
		this.creatorUserID = creatorUserID;
	}



	public String getCreatorDate()
	{
		return creatorDate;
	}



	public void setCreatorDate(String creatorDate)
	{
		this.creatorDate = creatorDate;
	}



	public String getCreatorUserName()
	{
		return creatorUserName;
	}



	public void setCreatorUserName(String creatorUserName)
	{
		this.creatorUserName = creatorUserName;
	}



	public String getModifyUserID()
	{
		return modifyUserID;
	}



	public void setModifyUserID(String modifyUserID)
	{
		this.modifyUserID = modifyUserID;
	}



	public String getModifyDate()
	{
		return modifyDate;
	}



	public void setModifyDate(String modifyDate)
	{
		this.modifyDate = modifyDate;
	}



	public int getSysStatus()
	{
		return sysStatus;
	}



	public void setSysStatus(int sysStatus)
	{
		this.sysStatus = sysStatus;
	}

	private String sncount;



	public String getSncount()
	{
		return sncount;
	}



	public void setSncount(String sncount)
	{
		this.sncount = sncount;
	}

	private String deviceStatus;

	private String SN;



	public String getDeviceStatus()
	{
		return deviceStatus;
	}



	public void setDeviceStatus(String deviceStatus)
	{
		this.deviceStatus = deviceStatus;
	}



	public String getSN()
	{
		return SN;
	}



	public void setSN(String sN)
	{
		SN = sN;
	}



	public String getRoleID() {
	    return roleID;
	}



	public void setRoleID(String roleID) {
	    this.roleID = roleID;
	}



	public String getRoleName() {
	    return roleName;
	}



	public void setRoleName(String roleName) {
	    this.roleName = roleName;
	}



	public String getServerCode() {
		return serverCode;
	}



	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	
}
