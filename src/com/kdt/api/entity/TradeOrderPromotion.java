package com.kdt.api.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api/struct?name=TradeOrderPromotion
 * 交易明细中的优惠信息的数据结构
 *
 * TradeOrderPromotion.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeOrderPromotion implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1623999637643949139L;

	String promotion_name;
	String promotion_type;
	String apply_at; // Date 2014-09-30 12:23:00
	Double discount_fee; // Price 10.00

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
	public String getApply_at() {
		return apply_at;
	}
	public void setApply_at(String apply_at) {
		this.apply_at = apply_at;
	}
	public Double getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(Double discount_fee) {
		this.discount_fee = discount_fee;
	}
}
