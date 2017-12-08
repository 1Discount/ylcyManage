package com.kdt.api.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api/struct?name=UmpTradeCoupon
 * 订单中使用到的卡券的数据结构
 *
 * UmpTradeCoupon.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UmpTradeCoupon implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4603730149567051183L;

	Integer coupon_id;
	String coupon_name;
	String coupon_type;
	String coupon_content;
	String coupon_description;
	String coupon_condition;
	String used_at; // Date 2014-09-30 12:23:00
	Double discount_fee; // Price

	public Integer getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(Integer coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}
	public String getCoupon_content() {
		return coupon_content;
	}
	public void setCoupon_content(String coupon_content) {
		this.coupon_content = coupon_content;
	}
	public String getCoupon_description() {
		return coupon_description;
	}
	public void setCoupon_description(String coupon_description) {
		this.coupon_description = coupon_description;
	}
	public String getCoupon_condition() {
		return coupon_condition;
	}
	public void setCoupon_condition(String coupon_condition) {
		this.coupon_condition = coupon_condition;
	}
	public String getUsed_at() {
		return used_at;
	}
	public void setUsed_at(String used_at) {
		this.used_at = used_at;
	}
	public Double getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(Double discount_fee) {
		this.discount_fee = discount_fee;
	}

}
