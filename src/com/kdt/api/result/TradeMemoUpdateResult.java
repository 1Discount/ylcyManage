package com.kdt.api.result;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.kdt.api.entity.TradeDetail;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.trade.memo.update
 * kdt.trade.memo.update 修改一笔交易备注
 * 修改一笔交易备注
 *
 * TradeMemoUpdateResult.java
 * @author tangming@easy2go.cn 2015-11-24
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeMemoUpdateResult implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5642729749766007443L;

	TradeDetail trade; // 交易详情

	public TradeDetail getTrade() {
		return trade;
	}

	public void setTrade(TradeDetail trade) {
		this.trade = trade;
	}
}
