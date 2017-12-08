package com.kdt.api.result;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.shop.basic.get
 * kdt.shop.basic.get 获取店铺基本信息
 *
 * ShopBasicWrapper.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
public class TradesSoldListWrapper extends BaseResult {
	/**
	 *
	 */
	private static final long serialVersionUID = -2945319781733982439L;

	TradesSoldList response;

	public TradesSoldList getResponse() {
		return response;
	}

	public void setResponse(TradesSoldList response) {
		this.response = response;
	}
}
