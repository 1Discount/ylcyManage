package com.kdt.api.entity;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api/struct?name=TradeDetail
 * 交易数据结构
 *
 * reference: {@link TradeOrder} {@link TradeFetch} {@link UmpTradeCoupon} {@link TradePromotion}
 *
 *  * TODO: 下面这些文档没列出, 但实际返回结果有的字段待处理. 暂时使用了类的 @JsonIgnoreProperties 注解
 * Unrecognized field "status_str" (Class com.kdt.api.entity.TradeDetail), not marked as ignorable
 * "status_str": "已发货",
 *
 * TradeDetail.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeDetail implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1952176285887459106L;

	String tid; // 交易编号
	Integer num; // 商品购买数量。当一个trade对应多个order的时候，值为所有商品购买数量之和

	Integer goods_kind; // 在文档中未列出 如 1 等

	Integer num_iid; // 商品数字编号。当一个trade对应多个order的时候，值为第一个交易明细中的商品的编号
	Double price; // 商品价格。精确到2位小数；单位：元。当一个trade对应多个order的时候，值为第一个交易明细中的商品的价格
	String pic_path; // 商品主图片地址。当一个trade对应多个order的时候，值为第一个交易明细中的商品的图片地址
	String pic_thumb_path; // 商品主图片缩略图地址
	String title; // 交易标题，以首个商品标题作为此标题的值
	String type; // 交易类型。取值范围：FIXED （一口价）GIFT （送礼）BULK_PURCHASE（来自分销商的采购）PRESENT （赠品领取）COD （货到付款）QRCODE（扫码商家二维码直接支付的交易）
	Integer weixin_user_id; // 微信粉丝ID
	Integer buyer_type; // 买家类型，取值范围：0 为未知，1 为微信粉丝，2 为微博粉丝
	Integer buyer_id; // 买家ID，当 buyer_type 为 1 时，buyer_id 的值等于 weixin_user_id 的值
	String buyer_nick; // 买家昵称
	String buyer_message; // 买家购买附言
	Short seller_flag; // 卖家备注星标，取值范围 1、2、3、4、5；如果为0，表示没有备注星标
	String trade_memo; // 卖家对该交易的备注
	String receiver_city; // 收货人的所在城市。PS：如果订单类型是送礼订单，收货地址在sub_trades字段中；如果物流方式是到店自提，收货地址在fetch_detail字段中
	String receiver_district; // 收货人的所在地区
	String receiver_name; // 姓名
	String receiver_state; // 省份
	String receiver_address; // 详细地址
	String receiver_zip; // 邮编
	String receiver_mobile; // 收货人的手机号码
	Integer feedback; // 交易维权状态。0 无维权，1 顾客发起维权，2 顾客拒绝商家的处理结果，3 顾客接受商家的处理结果，9 商家正在处理， 101 等待卖家同意退款申请，102 等待卖家同意退款申请（维权失败过），103 卖家不同意退款申请，104 已经申请有赞客满介入， 105 卖家已同意退款，106 已退货，等待卖家确认收货，107 维权已经关闭，110 退款成功。备注：1到10的状态码是微信那边的维权状态码，100以上的状态码是有赞这边的维权状态码
	String refund_state; // 退款状态。取值范围：NO_REFUND（无退款）PARTIAL_REFUNDING（部分退款中）PARTIAL_REFUNDED（已部分退款）PARTIAL_REFUND_FAILED（部分退款失败）FULL_REFUNDING（全额退款中）FULL_REFUNDED（已全额退款）FULL_REFUND_FAILED（全额退款失败）
	String outer_tid; // 外部交易编号。比如，如果支付方式是微信支付，就是财付通的交易单号
	String status; // 交易状态。取值范围：
//		TRADE_NO_CREATE_PAY (没有创建支付交易)
//		WAIT_BUYER_PAY (等待买家付款)
//		WAIT_PAY_RETURN (等待支付确认)
//		WAIT_SELLER_SEND_GOODS (等待卖家发货，即：买家已付款)
//		WAIT_BUYER_CONFIRM_GOODS (等待买家确认收货，即：卖家已发货)
//		TRADE_BUYER_SIGNED (买家已签收)
//		TRADE_CLOSED (付款以后用户退款成功，交易自动关闭)
//		TRADE_CLOSED_BY_USER (付款以前，卖家或买家主动关闭交易)
	String shipping_type; // 创建交易时的物流方式。取值范围：express（快递），fetch（到店自提）
	Double post_fee; // 运费。单位：元，精确到分
	Double total_fee; // 商品总价（商品价格乘以数量的总金额）。单位：元，精确到分
	Double refunded_fee; // 交易完成后退款的金额。单位：元，精确到分
	Double discount_fee; // 交易优惠金额（不包含交易明细中的优惠金额）。单位：元，精确到分
	Double payment; // 实付金额。单位：元，精确到分
	String created; // Date 交易创建时间
	String update_time; // Date 交易更新时间。当交易的：状态改变、备注更改、星标更改 等情况下都会刷新更新时间
	String pay_time; // 买家付款时间
	String pay_type; // 支付类型。取值范围：
//		WEIXIN (微信支付)
//		ALIPAY (支付宝支付)
//		BANKCARDPAY (银行卡支付)
//		PEERPAY (代付)
//		CODPAY (货到付款)
//		BAIDUPAY (百度钱包支付)
//		PRESENTTAKE (直接领取赠品)
//		COUPONPAY（优惠券/码全额抵扣）
//		BULKPURCHASE（来自分销商的采购）
	String consign_time; // Date 卖家发货时间
	String sign_time; // Date 买家签收时间
	String buyer_area; // 买家下单的地区
	List<TradeOrder> orders; // 交易明细列表
	TradeFetch fetch_detail; // 如果是到店自提交易，返回自提详情，否则返回空
	List<UmpTradeCoupon> coupon_details; // 在交易中使用到的卡券的详情，包括：优惠券、优惠码
	List<TradePromotion> promotion_details; // 在交易中使用到优惠活动详情，包括：满减满送
	Double adjust_fee; // 卖家手工调整订单金额。精确到2位小数；单位：元。若卖家减少订单金额10元2分，则这里为10.02；若卖家增加订单金额10元2分，则这里为-10.02
	// Trade sub_trades; // 从文档查看类型未知! 交易中包含的子交易，目前：仅在送礼订单中会有子交易

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getGoods_kind() {
		return goods_kind;
	}
	public void setGoods_kind(Integer goods_kind) {
		this.goods_kind = goods_kind;
	}
	public Integer getNum_iid() {
		return num_iid;
	}
	public void setNum_iid(Integer num_iid) {
		this.num_iid = num_iid;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getWeixin_user_id() {
		return weixin_user_id;
	}
	public void setWeixin_user_id(Integer weixin_user_id) {
		this.weixin_user_id = weixin_user_id;
	}
	public Integer getBuyer_type() {
		return buyer_type;
	}
	public void setBuyer_type(Integer buyer_type) {
		this.buyer_type = buyer_type;
	}
	public Integer getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getBuyer_nick() {
		return buyer_nick;
	}
	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}
	public String getBuyer_message() {
		return buyer_message;
	}
	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}
	public Short getSeller_flag() {
		return seller_flag;
	}
	public void setSeller_flag(Short seller_flag) {
		this.seller_flag = seller_flag;
	}
	public String getTrade_memo() {
		return trade_memo;
	}
	public void setTrade_memo(String trade_memo) {
		this.trade_memo = trade_memo;
	}
	public String getReceiver_city() {
		return receiver_city;
	}
	public void setReceiver_city(String receiver_city) {
		this.receiver_city = receiver_city;
	}
	public String getReceiver_district() {
		return receiver_district;
	}
	public void setReceiver_district(String receiver_district) {
		this.receiver_district = receiver_district;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_state() {
		return receiver_state;
	}
	public void setReceiver_state(String receiver_state) {
		this.receiver_state = receiver_state;
	}
	public String getReceiver_address() {
		return receiver_address;
	}
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}
	public String getReceiver_zip() {
		return receiver_zip;
	}
	public void setReceiver_zip(String receiver_zip) {
		this.receiver_zip = receiver_zip;
	}
	public String getReceiver_mobile() {
		return receiver_mobile;
	}
	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}
	public Integer getFeedback() {
		return feedback;
	}
	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}
	public String getRefund_state() {
		return refund_state;
	}
	public void setRefund_state(String refund_state) {
		this.refund_state = refund_state;
	}
	public String getOuter_tid() {
		return outer_tid;
	}
	public void setOuter_tid(String outer_tid) {
		this.outer_tid = outer_tid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShipping_type() {
		return shipping_type;
	}
	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
	}
	public Double getPost_fee() {
		return post_fee;
	}
	public void setPost_fee(Double post_fee) {
		this.post_fee = post_fee;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public Double getRefunded_fee() {
		return refunded_fee;
	}
	public void setRefunded_fee(Double refunded_fee) {
		this.refunded_fee = refunded_fee;
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
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getConsign_time() {
		return consign_time;
	}
	public void setConsign_time(String consign_time) {
		this.consign_time = consign_time;
	}
	public String getSign_time() {
		return sign_time;
	}
	public void setSign_time(String sign_time) {
		this.sign_time = sign_time;
	}
	public String getBuyer_area() {
		return buyer_area;
	}
	public void setBuyer_area(String buyer_area) {
		this.buyer_area = buyer_area;
	}
	public List<TradeOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<TradeOrder> orders) {
		this.orders = orders;
	}
	public TradeFetch getFetch_detail() {
		return fetch_detail;
	}
	public void setFetch_detail(TradeFetch fetch_detail) {
		this.fetch_detail = fetch_detail;
	}
	public List<UmpTradeCoupon> getCoupon_details() {
		return coupon_details;
	}
	public void setCoupon_details(List<UmpTradeCoupon> coupon_details) {
		this.coupon_details = coupon_details;
	}
	public List<TradePromotion> getPromotion_details() {
		return promotion_details;
	}
	public void setPromotion_details(List<TradePromotion> promotion_details) {
		this.promotion_details = promotion_details;
	}
	public Double getAdjust_fee() {
		return adjust_fee;
	}
	public void setAdjust_fee(Double adjust_fee) {
		this.adjust_fee = adjust_fee;
	}

	/**
	 * 拼合订单的详细地址, 若为实物宝贝则非空. 注意: 因为有购物车功能, 一个总订单下会有可能多于1个的流量宝贝,
	 * 也有可能每个流量宝贝所留言的"联系手机"/"使用人姓名"之前不相同, 也有可能与这里收货地址的姓名或手机不同.
	 * 原则系按留言中的联系手机去确认是否为新客户
	 *
	 * @return
	 */
	public String getFullAddress() {
		// 按目前的结果, 不会为 null
		return receiver_state + receiver_city + receiver_district + receiver_address;
	}

	/**
	 * 获取拼合的卖家备注和买家购买附言
	 *
	 * @return
	 */
	public String getRemarkString() {
		String resultString = "";
		if (null != trade_memo && "" != trade_memo) {
			resultString += "卖家备注:" + trade_memo;
		}
		if (null != buyer_message && "" != buyer_message) {
			resultString += " 买家附言:" + buyer_message;
		}
		return resultString;
	}

	@Override
	public int hashCode() {
		String str = this.tid.replaceAll("\\D+","");
		return Integer.parseInt(str);
	}

	@Override
	public boolean equals(Object obj) {
		boolean retVal = false;
		if(obj != null && obj.getClass().equals(this.getClass())) {
			TradeDetail bean = (TradeDetail) obj;
			if(bean.getTid()==null && this.getTid()==null)
            {
                retVal = true;
            }
            else
            {
                if(bean.getTid()!=null && bean.getTid().equals(this.getTid()))
                {
                    retVal = true;
                }
            }
		}
		return retVal;
	}

}
