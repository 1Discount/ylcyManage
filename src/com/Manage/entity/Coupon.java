package com.Manage.entity;

/**
 * 优惠劵
 * **/
public class Coupon {
	 
	
	/**优惠券id**/
	public String couponID;
	/**批次号**/
	public String couponVesion;
	/**优惠劵个数**/
	public String couponCount;
	/** 优惠折扣**/
	public String couponDiscount;
	/** 序列号**/
	public String couponNumber;
	/** 活动标题**/
	public String couponTitle;
	/** 状态（已领取、已过期、未领取）**/
	public String couponStatus;
	/** 开始时间**/
	public String couponBeginTime;
	/** 过期时间**/
	public String couponEndTime;
	/** 折扣类型**/
	public String couponDiscountStatus;
	/** 折扣值**/
	public String couponValue;
	/**备注**/
	public String remark;
	/**创建时间**/
	public String createDate;
	/**创建人**/
	public String createUserID;
	/** 创建人姓名**/
	public String createUserName;
	/**修改时间**/
	public String modifyDate;
	/**修改人**/
	public String modifyUserID;
	/**系统状态**/
	public String sysStatus;
	
	public String ylqCoupon;//已领取数量
	/** 是否公开  **/
	public String ispublic;
	
	/** 图片地址 **/
	public String couponimg;
	
	/** 优惠类型 **/
	public String coupontype;
	
	public String getCoupontype() {
		return coupontype;
	}
	public void setCoupontype(String coupontype) {
		this.coupontype = coupontype;
	}
	public String getCouponimg() {
		return couponimg;
	}
	public void setCouponimg(String couponimg) {
		this.couponimg = couponimg;
	}
	public String getIspublic() {
		return ispublic;
	}
	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}
	public String getYlqCoupon() {
		return ylqCoupon;
	}
	public void setYlqCoupon(String ylqCoupon) {
		this.ylqCoupon = ylqCoupon;
	}
	public String getCouponVesion() {
		return couponVesion;
	}
	public void setCouponVesion(String couponVesion) {
		this.couponVesion = couponVesion;
	}
	public String getCouponID() {
		return couponID;
	}
	public void setCouponID(String couponID) {
		this.couponID = couponID;
	}
	public String getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(String couponCount) {
		this.couponCount = couponCount;
	}
	public String getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(String couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public String getCouponNumber() {
		return couponNumber;
	}
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}
	public String getCouponTitle() {
		return couponTitle;
	}
	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}
	public String getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
	public String getCouponBeginTime() {
		return couponBeginTime;
	}
	public void setCouponBeginTime(String couponBeginTime) {
		this.couponBeginTime = couponBeginTime;
	}
	public String getCouponEndTime() {
		return couponEndTime;
	}
	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
	public String getCouponDiscountStatus() {
		return couponDiscountStatus;
	}
	public void setCouponDiscountStatus(String couponDiscountStatus) {
		this.couponDiscountStatus = couponDiscountStatus;
	}
	public String getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
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
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
