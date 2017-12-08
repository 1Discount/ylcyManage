package com.kdt.api.result;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.kdt.api.entity.ShopBasic;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.shop.basic.get
 * kdt.shop.basic.get 获取店铺基本信息
 *
 * ShopBasicWrapper.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
public class ShopBasicWrapper extends BaseResult {
	/**
	 *
	 */
	private static final long serialVersionUID = 8822175152359043564L;

	ShopBasic response;

	public ShopBasic getResponse() {
		return response;
	}

	public void setResponse(ShopBasic response) {
		this.response = response;
	}
}
