package com.kdt.api.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.Manage.common.util.StringUtils;
import com.kdt.api.YouzanConfig;

/**
 * https://open.koudaitong.com/doc/api/struct?name=TradeOrder
 * 交易明细数据结构
 *
 * TODO: 下面这些文档没列出, 但实际返回结果有的字段待处理. 暂时使用了类的 @JsonIgnoreProperties 注解
 * Unrecognized field "state_str" (Class com.kdt.api.entity.TradeOrder), not marked as ignorable
 * "state_str": "已发货",
 *
 * TradeOrder.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeOrder implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -2913001911036381961L;

	Integer oid; // 交易明细编号。该编号并不唯一，只用于区分交易内的多条明细记录
	Integer num_iid; // 商品数字编号
	Integer sku_id; // Sku的ID，sku_id 在系统里并不是唯一的，结合商品ID一起使用才是唯一的。
	String sku_unique_code; // Sku在系统中的唯一编号，可以在开发者的系统中用作 Sku 的唯一ID，但不能用于调用接口
	Integer num; // 商品购买数量
	String outer_sku_id; // 商家编码（商家为Sku设置的外部编号）
	String outer_item_id; // 商品货号（商家为商品设置的外部编号）
	String title; // 商品标题
	String seller_nick; // 卖家昵称
	Double fenxiao_price; // 商品在分销商那边的出售价格。精确到2位小数；单位：元。如果是采购单才有值，否则值为 0
	Double fenxiao_payment; // 商品在分销商那边的实付金额。精确到2位小数；单位：元。如果是采购单才有值，否则值为 0
	Double price; // 商品价格。精确到2位小数；单位：元
	Double total_fee; // 应付金额（商品价格乘以数量的总金额）
	Double discount_fee; // 交易明细内的优惠金额。精确到2位小数，单位：元
	Double payment; // 实付金额。精确到2位小数，单位：元
	String sku_properties_name; // SKU的值，即：商品的规格。如：机身颜色:黑色;手机套餐:官方标配
	String pic_path; // 商品主图片地址
	String pic_thumb_path; // 商品主图片缩略图地址
	Integer item_type; // 商品类型。0：普通商品；10：分销商品;

	List<TradeBuyerMessage> buyer_messages; // 交易明细中的买家留言列表
	List<TradeOrderPromotion> order_promotion_details; // 交易明细中的优惠信息列表

	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public Integer getNum_iid() {
		return num_iid;
	}
	public void setNum_iid(Integer num_iid) {
		this.num_iid = num_iid;
	}
	public Integer getSku_id() {
		return sku_id;
	}
	public void setSku_id(Integer sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_unique_code() {
		return sku_unique_code;
	}
	public void setSku_unique_code(String sku_unique_code) {
		this.sku_unique_code = sku_unique_code;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getOuter_sku_id() {
		return outer_sku_id;
	}
	public void setOuter_sku_id(String outer_sku_id) {
		this.outer_sku_id = outer_sku_id;
	}
	public String getOuter_item_id() {
		return outer_item_id;
	}
	public void setOuter_item_id(String outer_item_id) {
		this.outer_item_id = outer_item_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSeller_nick() {
		return seller_nick;
	}
	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}
	public Double getFenxiao_price() {
		return fenxiao_price;
	}
	public void setFenxiao_price(Double fenxiao_price) {
		this.fenxiao_price = fenxiao_price;
	}
	public Double getFenxiao_payment() {
		return fenxiao_payment;
	}
	public void setFenxiao_payment(Double fenxiao_payment) {
		this.fenxiao_payment = fenxiao_payment;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public Double getDiscount_fee() {
		return discount_fee;
	}
	public void setDiscount_fee(Double discount_fee) {
		this.discount_fee = discount_fee;
	}
	public Double getPayment() {
		return payment;
	}
	public void setPayment(Double payment) {
		this.payment = payment;
	}
	public String getSku_properties_name() {
		return sku_properties_name;
	}
	public void setSku_properties_name(String sku_properties_name) {
		this.sku_properties_name = sku_properties_name;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	public String getPic_thumb_path() {
		return pic_thumb_path;
	}
	public void setPic_thumb_path(String pic_thumb_path) {
		this.pic_thumb_path = pic_thumb_path;
	}
	public Integer getItem_type() {
		return item_type;
	}
	public void setItem_type(Integer item_type) {
		this.item_type = item_type;
	}
	public List<TradeBuyerMessage> getBuyer_messages() {
		return buyer_messages;
	}
	public void setBuyer_messages(List<TradeBuyerMessage> buyer_messages) {
		this.buyer_messages = buyer_messages;
	}
	public List<TradeOrderPromotion> getOrder_promotion_details() {
		return order_promotion_details;
	}
	public void setOrder_promotion_details(List<TradeOrderPromotion> order_promotion_details) {
		this.order_promotion_details = order_promotion_details;
	}

	/**
	 * 返回此订单下的电话号码
	 * @return 若无号码, 返回null
	 */
	public String getPhone() {
		if (null == buyer_messages || buyer_messages.size() == 0) {
			return null;
		}

		String foundPhone = null;
		for (TradeBuyerMessage message : buyer_messages) {
			// 要不要作联系手机格式校验? 考虑情形，因为已系标星订单. 说明此订单已人工处理,
			// 但若通过沟通, 客户发现输错了手机, 因客户留言无法更改, 所以一般可以在卖家备注
			// 里补充正确的手机, 可能目前也只有这一个途径, 因为客户似乎无法增加/修改订单留言
			// TODO: 待确认
			if (YouzanConfig.BUY_MESSAGE_TITLE_PHONE.equals(message.getTitle()) &&
					StringUtils.isNotBlank(message.getContent())) {
				foundPhone = message.getContent();
				break;
			}
		}
		return foundPhone;
	}

	/**
	 * 返回此订单下的客户留言Map
	 * @return 返回此订单下的客户留言Map
	 */
	public Map<String, String> getBuyerMessageMap () {
		if (null == buyer_messages || buyer_messages.size() == 0) {
			return null;
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		for (TradeBuyerMessage message : buyer_messages) {
			resultMap.put(message.getTitle(), message.getContent());
		}
		return resultMap;
	}

	/**
	 * 返回买家留言的拼合字符串
	 *
	 * @return
	 */
	public String getBuyerMessageString() {
		String result = "";
		for (TradeBuyerMessage message : buyer_messages) {
			result += message.getTitle() + ":" + message.getContent() + " ";
		}
		return result;
	}

	/**
	 * 返回实付金额对应的宝贝单价. 在一个购物车订单中, 实付款由运费, 优惠金额, 单价, 数量等几个决定
	 * 为了真实表示一个例如实付的设备订单的单价, 最大目的系达到确定设备押金的金额, 所以必须计算准确
	 * 目前觉得较好的系此 TradeOrder 的实付金额除以数量
	 *
	 * 顺便提出, 流量订单退订则与它的实付金额为准.
	 *
	 * @return
	 */
	public Double getDevicePaymentPrice() {
		return (double) (Math.round((payment / num) * 100) / 100.0);
	}
}
