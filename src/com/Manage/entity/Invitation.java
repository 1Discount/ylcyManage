package com.Manage.entity;

import java.util.Date;

/**
 * 邀请注册表实体
 * @author wangbo
 *
 */
public class Invitation {
	/**ID**/
	public String inviteID;
	/**邀请码**/
	public String inviteCode;
	/**有效日期**/
	public Date validDate;
	/**是否被用**/
	public String ifUserd;
	/**创建人ID**/
	public String creatorUserID;
	/**创建时间**/
	public Date creatorDate;
	
	
	public String getInviteID() {
		return inviteID;
	}
	public void setInviteID(String inviteID) {
		this.inviteID = inviteID;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public String getIfUserd() {
		return ifUserd;
	}
	public void setIfUserd(String ifUserd) {
		this.ifUserd = ifUserd;
	}
	public String getCreatorUserID() {
		return creatorUserID;
	}
	public void setCreatorUserID(String creatorUserID) {
		this.creatorUserID = creatorUserID;
	}
	public Date getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(Date creatorDate) {
		this.creatorDate = creatorDate;
	}
	
	
	
	
}
