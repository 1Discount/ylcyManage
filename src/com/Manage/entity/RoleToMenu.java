package com.Manage.entity;

import java.util.Date;

/** * @author  wangbo: * @date 创建时间：2015-5-26 上午10:02:49 * @version 1.0 * @parameter  * @since  * @return  */
/**
 * 角色权限映射实体 
 * @author Administrator
 *
 */
public class RoleToMenu {
	
	/** **/
	public String roleToMenuID;
	/** 角色ID**/
	public String roleID;
	/** 权限菜单ID**/
	public String menuID;
	/**角色实体**/
	public RoleInfo roleInfo;
	/**菜单实体**/
	public MenuInfo menuInfo;
	/**角色名称 **/
	public String roleName;
	/**权限菜单名称**/
	public String menuName;
	/**备注 **/
	public String remark;
	/**创建人ID **/
	public String creatorUserID;
	/**创建时间**/
	public Date   creatorDate;
	/**修改人ID **/
	public String modifyUserID;
	/**修改时间 **/
	public Date modifyDate;
	/**系统状态 **/
	public boolean sysStatus;
	public String getRoleToMenuID() {
		return roleToMenuID;
	}
	public void setRoleToMenuID(String roleToMenuID) {
		this.roleToMenuID = roleToMenuID;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getMenuID() {
		return menuID;
	}
	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}
	public RoleInfo getRoleInfo() {
		return roleInfo;
	}
	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}
	public MenuInfo getMenuInfo() {
		return menuInfo;
	}
	public void setMenuInfo(MenuInfo menuInfo) {
		this.menuInfo = menuInfo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
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
	public Date getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(Date creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getModifyUserID() {
		return modifyUserID;
	}
	public void setModifyUserID(String modifyUserID) {
		this.modifyUserID = modifyUserID;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public boolean isSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(boolean sysStatus) {
		this.sysStatus = sysStatus;
	}
	
	

}
