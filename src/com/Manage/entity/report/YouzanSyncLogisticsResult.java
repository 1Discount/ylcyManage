package com.Manage.entity.report;

import java.io.Serializable;

public class YouzanSyncLogisticsResult implements Serializable {

	/**
	 * 错误代码 原则: 负数为错误, 0为正常结束, 正数为其他可能情况的结果, 但不会影响整个流程
	 *
	 * 10	有赞平台订单: 发货成功, 同时成功同步发货状态到有赞平台
	 * 11	有赞平台订单: 发货成功, 但此有赞购物车订单下仍有未发货的宝贝所以未同步物流状态到有赞
	 * 12	有赞平台订单: 发货成功, 同时租用的设备订单增加了物流备注到有赞
	 * 0 	未全部发货, 或者提示不是有赞订单, 提示跳过
	 * -2	有赞平台订单: 发货信息已保存, 但开发/管理人员未处理好此快递公司信息, 所以未能同步发货状态到有赞平台
	 * -3	有赞平台订单: 发货信息已保存, 但同步发货状态到有赞平台失败, 请联系有赞管理人员手动去有赞后台处理发货状态
	 * -10	有赞平台订单: 流量订单激活成功，但需要同步状态到有赞平台时查询总订单失败，请备忘并联系有赞后台订单管理员处理
	 *
	 * -11	出错了，未支持的特性：目前有赞平台同步过来的任意一个总订单要么只含一个流量订单要么只含一个设备订单，不会出现同时包含两个
	 * -12	开发未完善的特性：联系开发人员补上
	 * -13	有赞平台订单: 激活成功，但对流量订单开发未设定其对应的总订单发货状态为“已发货”。请沟通处理
	 * -14	有赞平台订单: 流量订单激活成功，但需要同步状态到有赞平台时发生未知错误，请备忘并联系有赞后台订单管理员处理
	 * -15	有赞平台订单: 发货成功, 但增加物流备注到有赞时失败，可通知相关人员留意一下
	 */
	Integer errorCode;
	/** 错误描述 */
	String msg;

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

}
