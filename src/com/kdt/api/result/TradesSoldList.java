package com.kdt.api.result;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.kdt.api.entity.TradeDetail;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.trades.sold.get
 * kdt.trades.sold.get 查询卖家已卖出的交易列表
 * 查询卖家已卖出的交易列表，按创建时间的倒序排序
 *
 * TradesSoldListWrapper.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradesSoldList implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2922746736591805349L;

	Integer total_results; // 搜索到的交易总数
	List<TradeDetail> trades; // 搜索到的交易列表
	Boolean has_next; // 是否存在下一页

	public Integer getTotal_results() {
		return total_results;
	}
	public void setTotal_results(Integer total_results) {
		this.total_results = total_results;
	}
	public List<TradeDetail> getTrades() {
		return trades;
	}
	public void setTrades(List<TradeDetail> trades) {
		this.trades = trades;
	}
	public Boolean getHas_next() {
		return has_next;
	}
	public void setHas_next(Boolean has_next) {
		this.has_next = has_next;
	}
}
