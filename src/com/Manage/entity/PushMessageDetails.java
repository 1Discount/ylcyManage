package com.Manage.entity;

/**
 * 消息推送详细
 * @author Yann
 *
 */
public class PushMessageDetails {
	
	private String id;
	private String pushMessageInfoId;
	private String pushStartTime;
	private String pushArriveTime;
	private int pushStatus;
	private String isRead;
	private String readTime;
	private String deviceType;
	private String pushMsgid;
	private String pushMsgNo;
	private String pushResult;
	private String revoke;
	private String revokeTime;
	private int sysStatus;
	private String sn;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPushMessageInfoId()
	{
		return pushMessageInfoId;
	}
	public void setPushMessageInfoId(String pushMessageInfoId)
	{
		this.pushMessageInfoId = pushMessageInfoId;
	}
	public String getPushStartTime()
	{
		return pushStartTime;
	}
	public void setPushStartTime(String pushStartTime)
	{
		this.pushStartTime = pushStartTime;
	}
	public String getPushArriveTime()
	{
		return pushArriveTime;
	}
	public void setPushArriveTime(String pushArriveTime)
	{
		this.pushArriveTime = pushArriveTime;
	}
	public String getPushResult()
	{
		return pushResult;
	}
	public void setPushResult(String pushResult)
	{
		this.pushResult = pushResult;
	}
	public String getIsRead()
	{
		return isRead;
	}
	public void setIsRead(String isRead)
	{
		this.isRead = isRead;
	}
	public String getReadTime()
	{
		return readTime;
	}
	public void setReadTime(String readTime)
	{
		this.readTime = readTime;
	}
	public String getDeviceType()
	{
		return deviceType;
	}
	public void setDeviceType(String deviceType)
	{
		this.deviceType = deviceType;
	}
	
	public int getPushStatus()
	{
		return pushStatus;
	}
	public void setPushStatus(int pushStatus)
	{
		this.pushStatus = pushStatus;
	}
	public String getPushMsgid()
	{
		return pushMsgid;
	}
	public void setPushMsgid(String pushMsgid)
	{
		this.pushMsgid = pushMsgid;
	}
	public String getPushMsgNo()
	{
		return pushMsgNo;
	}
	public void setPushMsgNo(String pushMsgNo)
	{
		this.pushMsgNo = pushMsgNo;
	}
	public String getRevoke()
	{
		return revoke;
	}
	public void setRevoke(String revoke)
	{
		this.revoke = revoke;
	}
	public String getRevokeTime()
	{
		return revokeTime;
	}
	public void setRevokeTime(String revokeTime)
	{
		this.revokeTime = revokeTime;
	}
	public int getSysStatus()
	{
		return sysStatus;
	}
	public void setSysStatus(int sysStatus)
	{
		this.sysStatus = sysStatus;
	}
	public String getSn()
	{
		return sn;
	}
	public void setSn(String sn)
	{
		this.sn = sn;
	}
	
	
}
