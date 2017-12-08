package com.Manage.entity;

import java.util.List;

/** * @author  wangbo: * @date 创建时间：2015-5-23 下午2:33:42 * @version 1.0 * @parameter  * @since  * @return  */
public class MenuGroupInfo {
	/** ID**/
	public String menuGroupID;
	/** 菜单组名称**/
	public String menuGroupName;
	/** 层级序列**/
	public String sortCode;
	/** 图标路径**/
	public String menuIcon;
	/** 备注**/
	public String remark;
	/** 创建人ID**/
	public String creatorUserID;
	/** 创建时间**/
	public String creatorDate;
	/** 修改人ID**/
	public String modifyUserID;
	/** 修改时间**/
	public String modifyDate;
	/** 系统状态**/
	public String sysStatus;
	
	/**菜单列表 **/
	public List<MenuInfo> menuInfos;
	
	
	
	public String getMenuGroupID() {
		return menuGroupID;
	}
	public void setMenuGroupID(String menuGroupID) {
		this.menuGroupID = menuGroupID;
	}
	
	public String getMenuGroupName() {
		return menuGroupName;
	}
	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatorUserID() {
		return creatorUserID;
	}
	public void setCreatorUserID(String creatorUserID) {
		this.creatorUserID = creatorUserID;
	}
	public String getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getModifyUserID() {
		return modifyUserID;
	}
	public void setModifyUserID(String modifyUserID) {
		this.modifyUserID = modifyUserID;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}
	public List<MenuInfo> getMenuInfos() {
		return menuInfos;
	}
	public void setMenuInfos(List<MenuInfo> menuInfos) {
		this.menuInfos = menuInfos;
	}
	
	
}
