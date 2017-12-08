package com.kdt.api.result;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.kdt.api.entity.TradeDetail;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.trade.get
 * kdt.trade.get 获取单笔交易的信息
 *
 * TradeGetResult.java
 * @author tangming@easy2go.cn 2015-12-2
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeGetResult implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -472739020231004385L;

	private TradeDetail trade;

	public TradeDetail getTrade() {
		return trade;
	}

	public void setTrade(TradeDetail trade) {
		this.trade = trade;
	}
}
