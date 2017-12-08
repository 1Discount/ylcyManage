package com.Manage.entity;

import java.util.Date;

/** * @author  wangbo: * @date 创建时间：2015-5-25 下午7:31:31 * @version 1.0 * @parameter  * @since  * @return  */
public class RoleInfo {
	/** **/
	public String roleID;
	/**角色名称 **/
	public String roleName;
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
	
	/**菜单ID列表 **/
	public String[] menulist;
	
	public String[] getMenulist() {
		return menulist;
	}
	public void setMenulist(String[] menulist) {
		this.menulist = menulist;
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
