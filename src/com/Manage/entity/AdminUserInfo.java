package com.Manage.entity;

/**
 * 用户信息表实体
 * @author wangbo
 *
 */
public class AdminUserInfo {
	/**用户ID**/
	public String userID;
	/**用户名 **/
	public String userName;
	/**联系电话**/
	public String phone;
	/**email **/
	public String email;
	/**密码**/
	public String password;
	/**是否锁定**/
	public String ifLock;
	/**是否管理员**/
	public String ifAdmin;
	/**角色ID **/
	public String roleID;
	/**角色名称**/
	public String roleName;
	/**令牌**/
	public String keyCode;
	/**密码请求令牌 **/
	public String passwordKey;
	/**备注**/
	public String remark;
	/**创建人ID**/
	public String creatorUserID;
	/**创建时间**/
	public String creatorDate;
	/**修改人ID**/
	public String modifyUserID;
	/**修改时间 **/
	public String modifyDate;
	/**系统状态**/
	public boolean sysStatus;

	/**确认密码,用于修改密码时等等**/
	public String checkPassword;

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public String getIfLock() {
		return ifLock;
	}
	public void setIfLock(String ifLock) {
		this.ifLock = ifLock;
	}
	public String getIfAdmin() {
		return ifAdmin;
	}
	public void setIfAdmin(String ifAdmin) {
		this.ifAdmin = ifAdmin;
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
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getpasswordKey() {
		return passwordKey;
	}
	public void setpasswordKey(String passwordKey) {
		this.passwordKey = passwordKey;
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
	public boolean getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(boolean sysStatus) {
		this.sysStatus = sysStatus;
	}
	public String getCheckPassword() {
		return checkPassword;
	}
	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}


}
