package com.Manage.entity;

import java.util.Date;

/** * @author  wangbo: * @date 创建时间：2015-5-25 下午4:21:51 * @version 1.0 * @parameter  * @since  * @return  */
// 权限菜单表实体
public class MenuInfo {
	/**ID **/
	public String menuInfoID;
	/**菜单名称 **/
	public String menuName;
	/**界面路径 **/
	public String menuPath;
	/**请求方式 **/
	public String requestWay;
	/**所属菜单组ID **/
	public String menuGroupID;
	/**显示层级序列 **/
	public String sortCode;
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
	
	
	public String getMenuInfoID() {
		return menuInfoID;
	}
	public void setMenuInfoID(String menuInfoID) {
		this.menuInfoID = menuInfoID;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuPath() {
		return menuPath;
	}
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}
	public String getRequestWay() {
		return requestWay;
	}
	public void setRequestWay(String requestWay) {
		this.requestWay = requestWay;
	}
	public String getMenuGroupID() {
		return menuGroupID;
	}
	public void setMenuGroupID(String menuGroupID) {
		this.menuGroupID = menuGroupID;
	}
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
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
