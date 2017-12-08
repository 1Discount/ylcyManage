package com.Manage.entity;

/**
 * 推送消息表
 * @author Yann
 *
 */
public class PushMessageInfo {
	
	private String id;
	private String title;
	private String content;
	private String deviceTypes;
	private String alias;
	private String sn;
	private int pushMessagecount;
	private String source;
	private String type;
	private String developmentId;
	private String pushCompanyId;
	private String pushCompanyName;
	private String remark;
	private String creatorName;
	private String creatorUserID;
	private String creatorDate;
	private String modifyUserID;
	private String modifyDate;
	private int sysStatus;
	private int pushStatus;
	
	private int arriveCount;
	private int readCount;
	
	public int getArriveCount()
	{
		return arriveCount;
	}
	public void setArriveCount(int arriveCount)
	{
		this.arriveCount = arriveCount;
	}
	public int getReadCount()
	{
		return readCount;
	}
	public void setReadCount(int readCount)
	{
		this.readCount = readCount;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getDeviceTypes()
	{
		return deviceTypes;
	}
	public void setDeviceTypes(String deviceTypes)
	{
		this.deviceTypes = deviceTypes;
	}
	public String getAlias()
	{
		return alias;
	}
	public void setAlias(String alias)
	{
		this.alias = alias;
	}
	public String getSn()
	{
		return sn;
	}
	public void setSn(String sn)
	{
		this.sn = sn;
	}
	public int getPushMessagecount()
	{
		return pushMessagecount;
	}
	public void setPushMessagecount(int pushMessagecount)
	{
		this.pushMessagecount = pushMessagecount;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getDevelopmentId()
	{
		return developmentId;
	}
	public void setDevelopmentId(String developmentId)
	{
		this.developmentId = developmentId;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public String getCreatorName()
	{
		return creatorName;
	}
	public void setCreatorName(String creatorName)
	{
		this.creatorName = creatorName;
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
	public String getPushCompanyId()
	{
		return pushCompanyId;
	}
	public void setPushCompanyId(String pushCompanyId)
	{
		this.pushCompanyId = pushCompanyId;
	}
	public String getPushCompanyName()
	{
		return pushCompanyName;
	}
	public void setPushCompanyName(String pushCompanyName)
	{
		this.pushCompanyName = pushCompanyName;
	}
	public int getPushStatus()
	{
		return pushStatus;
	}
	public void setPushStatus(int pushStatus)
	{
		this.pushStatus = pushStatus;
	}
}
