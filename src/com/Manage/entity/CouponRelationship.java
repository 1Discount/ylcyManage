package com.Manage.entity;

public class CouponRelationship {
 
	/**ＩＤ**/
	public String id;
	/**手机号码**/
	public String phone;
	/** 序列号**/
	public String couponNumber;
	/** 优惠券状态 **/
	public String couponStatus;
	/** 使用时间 **/
	public String useTime;
	/** 使用订单id **/
	public String useORderID;
	/**备注**/
	public String remark;
	/**创建时间**/
	public String createDate;
	/**创建人**/
	public String createUserID;
	/**修改时间**/
	public String modifyDate;
	/**修改人**/
	public String modifyUserID;
	/**系统状态**/
	public String sysStatus;
	/**优惠券批次号**/
	public String couponVesion;
	
	/** 图片地址 **/
	public String couponimg;
	
	public String getCouponimg() {
		return couponimg;
	}
	public void setCouponimg(String couponimg) {
		this.couponimg = couponimg;
	}
	public String getCouponVesion() {
		return couponVesion;
	}
	public void setCouponVesion(String couponVesion) {
		this.couponVesion = couponVesion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCouponNumber() {
		return couponNumber;
	}
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}
	public String getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getUseORderID() {
		return useORderID;
	}
	public void setUseORderID(String useORderID) {
		this.useORderID = useORderID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateUserID() {
		return createUserID;
	}
	public void setCreateUserID(String createUserID) {
		this.createUserID = createUserID;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyUserID() {
		return modifyUserID;
	}
	public void setModifyUserID(String modifyUserID) {
		this.modifyUserID = modifyUserID;
	}
	public String getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}
	
 
	
	
}
