package com.kdt.api.result;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.logistics.online.marksign
 * kdt.logistics.online.marksign 卖家标记签收
 * 标记签收的目的是让交易流程继续走下去，标记签收后交易状态会由【卖家已发货】变为【买家已签收】，通常到店自提的订单需要卖家做标记签收操作
 *
 * ShippingResult.java
 * @author tangming@easy2go.cn 2015-11-17
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingResult implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1890811383655412457L;

	IsSuccessResponse shipping;

	public IsSuccessResponse getShipping() {
		return shipping;
	}

	public void setShipping(IsSuccessResponse shipping) {
		this.shipping = shipping;
	}
}
