package com.Manage.entity.report;

import java.io.Serializable;

public class YouzanSyncOrderResult implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6393172908751102443L;

	/**
	 * 错误代码 原则: 负数为错误, 0为正常结束, 正数为其他可能情况的结果, 但不会影响整个流程
	 *
	 * 1	处理结束: 没有新增加的订单
	 * -1	抱歉, 系统出错, 请重试! 解释新创建订单错误/解释更新订单错误
	 * -2	抱歉, 访问有赞商城出错, 请重试, 或联系开发/管理员等!
	 * -3
	 * -4
	 * -5	数据库操作失败, 再通过msg描述情况
	 */
	Integer errorCode;
	/** 错误描述 */
	String msg;

	/** 有赞总订单数量 */
	Integer okQueryYouzanTradesDetailCount = 0; // 不是 newCreatedTrades.size() 这个带上所有未标星的订单
	/** 有赞总订单下的详细宝贝数量 建议只统计 流量/设备/押金 这几种宝贝, 其他的另外处理 */
	Integer okQueryYouzanTradesOrderCount = 0;
	/** (目前)成功处理 流量/设备/押金 的数量 */
	Integer okSyncCount = 0;
	/** (目前)失败处理 流量/设备/押金 的数量 */
	Integer failedSyncCount = 0;
	/** 已处理过的 流量/设备/押金 的数量 */
	Integer hasHandledCount = 0;
	/** 已标星的订单, 其他类型的宝贝 */
	Integer otherTypeCount = 0;
	/** 个别情况下, 新添加的宝贝, 可能管理人员未按要求编辑商品信息添加"联系手机"必填留言字段, 也统计一下, 并提醒
	 * 添加新手机客户时, 若添加出错, 也统计到这个
	 */
	Integer missingPhoneCount = 0;

	/** 已标3星的订单, 成功取消的有赞总订单数量 注意系有赞的总订单即整个购物车订单算作1 */
	Integer star3OkCancelledCount = 0;
	/** 已标3星的订单, 需要取消但处理失败的有赞总订单数量 注意系有赞的总订单即整个购物车订单算作1 */
	Integer star3FailedCancelledCount = 0;
	/** 已标3星的订单, 反馈不处理的有赞总订单数量 注意系有赞的总订单即整个购物车订单算作1  */
	Integer star3IgnoredCount = 0;

	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getOkQueryYouzanTradesDetailCount() {
		return okQueryYouzanTradesDetailCount;
	}
	public void setOkQueryYouzanTradesDetailCount(Integer okQueryYouzanTradesDetailCount) {
		this.okQueryYouzanTradesDetailCount = okQueryYouzanTradesDetailCount;
	}
	public Integer getOkQueryYouzanTradesOrderCount() {
		return okQueryYouzanTradesOrderCount;
	}
	public void setOkQueryYouzanTradesOrderCount(Integer okQueryYouzanTradesOrderCount) {
		this.okQueryYouzanTradesOrderCount = okQueryYouzanTradesOrderCount;
	}
	public Integer getOkSyncCount() {
		return okSyncCount;
	}
	public void setOkSyncCount(Integer okSyncCount) {
		this.okSyncCount = okSyncCount;
	}
	public Integer getFailedSyncCount() {
		return failedSyncCount;
	}
	public void setFailedSyncCount(Integer failedSyncCount) {
		this.failedSyncCount = failedSyncCount;
	}
	public Integer getHasHandledCount() {
		return hasHandledCount;
	}
	public void setHasHandledCount(Integer hasHandledCount) {
		this.hasHandledCount = hasHandledCount;
	}
	public Integer getOtherTypeCount() {
		return otherTypeCount;
	}
	public void setOtherTypeCount(Integer otherTypeCount) {
		this.otherTypeCount = otherTypeCount;
	}
	public Integer getMissingPhoneCount() {
		return missingPhoneCount;
	}
	public void setMissingPhoneCount(Integer missingPhoneCount) {
		this.missingPhoneCount = missingPhoneCount;
	}

	/**
	 * 拼合反馈给前端的结果 但目前未用, 在调用这个之前, 保存先 set 好各项值
	 *
	 * @return
	 */
	public String getAllCountFeedbackString() {
		// TODO: 待补充
		return "";
	}
	public Integer getStar3OkCancelledCount() {
		return star3OkCancelledCount;
	}
	public void setStar3OkCancelledCount(Integer star3OkCancelledCount) {
		this.star3OkCancelledCount = star3OkCancelledCount;
	}
	public Integer getStar3FailedCancelledCount() {
		return star3FailedCancelledCount;
	}
	public void setStar3FailedCancelledCount(Integer star3FailedCancelledCount) {
		this.star3FailedCancelledCount = star3FailedCancelledCount;
	}
	public Integer getStar3IgnoredCount() {
		return star3IgnoredCount;
	}
	public void setStar3IgnoredCount(Integer star3IgnoredCount) {
		this.star3IgnoredCount = star3IgnoredCount;
	}

}
