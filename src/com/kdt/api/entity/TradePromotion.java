package com.kdt.api.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api/struct?name=TradePromotion
 * 订单中使用到的优惠活动的数据结构
 *
 * TradePromotion.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradePromotion implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -7597939359796162349L;

	Integer promotion_id;
	String promotion_name;
	String promotion_type;
	String promotion_condition;
	String used_at; // Date 2014-09-30 12:23:00
	Double discount_fee; // Price

	public Integer getPromotion_id() {
		return promotion_id;
	}
	public void setPromotion_id(Integer promotion_id) {
		this.promotion_id = promotion_id;
	}
	public String getPromotion_name() {
		return promotion_name;
	}
	public void setPromotion_name(String promotion_name) {
		this.promotion_name = promotion_name;
	}
	public String getPromotion_type() {
		return promotion_type;
	}
	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}
	public String getPromotion_condition() {
		return promotion_condition;
	}
	public void setPromotion_condition(String promotion_condition) {
		this.promotion_condition = promotion_condition;
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
