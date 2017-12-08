package com.kdt.api;

public final class YouzanConfig {
	private YouzanConfig(){}

	// 导入的有赞订单的设备订单ID直接使用TradeDetail的tid + "-" + TradeOrder的oid, 流量订单ID的与设备订单ID相同,
	// 而总订单OrdersInfo的ID则在前面添加前缀"YZ": "YZ"+tid+"-"+oid
	public static final String YOUZAN_ORDER_ID_PREFIX = "YZ";
	// 用于标记已处理过的有效订单
	public static final String HANDELED_ORDER = "handled_order"; // 或 duplicative

	// 宝贝的"商品编码" 除下列之外, 大多数为流量宝贝, 则不作要求. 保证有限个别少数的使用好编码
	// 注意!: 区分好 TradeOrder 的 outer_sku_id 和 outer_item_id . 有赞的文档会造成误解...
	// outer_sku_id: 商家编码（商家为Sku设置的外部编号）
	// outer_item_id: 商品货号（商家为商品设置的外部编号） 事实在有赞的"商品"编辑里"商品编码"的值系对应这个!
	//
	/** 押金链接宝贝 */
	public static final String OUTER_ITEM_ID_DEPOSITE = "押金";
	/** 途狗设备宝贝 */
	public static final String OUTER_ITEM_ID_DEVICE = "设备";
	/** 流量宝贝 --> updated 现在流量宝贝要带上国家代码, 格式形如: 流量-202-204-206-208-214-222-228-232-238
	 * 一个国家一个代码, 使用-连接. 待确认可以支持的最大数字, 初步确认这样10个系OK的
	 * 注意前台判断时使用 startWith 而不要 equals
	 */
	public static final String OUTER_ITEM_ID_WIFI = "流量-"; // 加上了"-"
	/** 深圳自取 大陆使用 大陆的人工操作 */
	public static final String OUTER_ITEM_ID_SELF = "自取";
	/** 其他类型宝贝 sample 假设这个不需要留言手机号码 */
	public static final String OUTER_ITEM_ID_OTHER_SAMPLE = "其他Sample";

	// 留意标题
	public static final String BUY_MESSAGE_TITLE_PHONE = "联系手机";
	public static final String BUY_MESSAGE_TITLE_NAME = "使用人姓名";
	public static final String BUY_MESSAGE_TITLE_DATE = "出发日期";
	public static final String BUY_MESSAGE_TITLE_DAYS = "使用天数";

}
