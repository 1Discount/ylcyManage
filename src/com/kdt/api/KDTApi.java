package com.kdt.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.kdt.api.KdtApiClient;
import com.kdt.api.entity.LogisticsTrace;
import com.kdt.api.entity.TradeDetail;
import com.kdt.api.entity.TradeOrder;
import com.kdt.api.entity.LogisticsTrace.trace_list_item;
import com.kdt.api.result.BaseResultWrapper;
import com.kdt.api.result.IsSuccessResponse;
import com.kdt.api.result.LogisticsTraceWrapper;
import com.kdt.api.result.ShippingResultWrapper;
import com.kdt.api.result.ShopBasicWrapper;
import com.kdt.api.result.TradeGetResult;
import com.kdt.api.result.TradeMemoUpdateResult;
import com.kdt.api.result.TradesSoldListWrapper;

/**
 * 各方法的参数, 请参考文档:
 * https://open.koudaitong.com/doc
 * 有赞 API 文档
 *
 * KDTApi.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
public class KDTApi {
	private static Logger logger = LogUtil.getInstance(KDTApi.class);

	/*
	 * 获取店铺信息
	 */
	public static ShopBasicWrapper getShopBasic(){
		String method = "kdt.shop.basic.get";
		HashMap<String, String> params = new HashMap<String, String>();
		//params.put("num_iid", "2651514");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient();
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(result.toString());
			logger.info("getShopBasic: Response Code : " + response.getStatusLine().getStatusCode());
			logger.info(result.toString());

			// 转换结果
			ObjectMapper mapper = new ObjectMapper();
			ShopBasicWrapper wrapper = mapper.readValue(result.toString(), ShopBasicWrapper.class);
			logger.info("getShopBasic: mapper read OK");
			return wrapper;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("getShopBasic: " + e.getMessage());
		}
		return null;
	}


	/*
	 * 获取全部订单信息 params null-safe
	 * https://open.koudaitong.com/doc/api?method=kdt.trades.sold.get
	 *
	 * 参数列表:
	 * fields 需要返回的交易对象字段
	 * status 交易状态
	 * start_created 交易创建开始时间。查询在该时间之后（包含该时间）创建的交易，为空则不限制
	 * end_created 交易创建结束时间。查询在该时间之前创建的交易，为空则不限制
	 * start_update 交易状态更新的开始时间。查询在该时间之后（包含该时间）交易状态更新过的交易，为空则不限制
	 * end_update 交易状态更新的结束时间。查询在该时间之前交易状态更新过的交易，为空则不限制
	 * weixin_user_id 微信粉丝ID(有赞内部)
	 * buyer_nick 买家昵称
	 * page_no 页码。取值范围：大于零的整数；默认值：1
	 * page_size 每页条数。取值范围：大于零的整数；默认值：40；最大值：100
	 * use_has_next 是否启用has_next的分页方式，是的话返回的结果中不包含总记录数，但是会新增一个是否存在下一页的的字段
	 */
	public static TradesSoldListWrapper getTradesSoldList(HashMap<String, String> params){
		String method = "kdt.trades.sold.get";
//		HashMap<String, String> params = new HashMap<String, String>();
//		//params.put("num_iid", "2651514");
		if (null == params) { // must
			params = new HashMap<String, String>();
		}

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient();
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(result.toString());
			logger.info("getTradesSoldList: Response Code : " + response.getStatusLine().getStatusCode());
			logger.info(result.toString());

			// 转换结果
			ObjectMapper mapper = new ObjectMapper();
			//不行,改类注解@JsonIgnoreProperties //mapper.getDeserializationConfig().without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			TradesSoldListWrapper wrapper = mapper.readValue(result.toString(), TradesSoldListWrapper.class);
			logger.info("getTradesSoldList: mapper read OK");
			return wrapper;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("getTradesSoldList: " + e.getMessage());
		}
		return null;
	}

	/**
	 * https://open.koudaitong.com/doc/api?method=kdt.trade.get
	 * kdt.trade.get 获取单笔交易的信息
	 *
	 * @param params
	 * @return
	 */
	public static BaseResultWrapper<TradeGetResult> tradeGet(HashMap<String, String> params){
		String method = "kdt.trade.get";
//		HashMap<String, String> params = new HashMap<String, String>();
//		//params.put("num_iid", "2651514");
		if (null == params || params.size() == 0) { // must
			return null; // 有必须字段 tid
		}
		// TODO: 可检索是否有 tid

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient();
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			logger.info("logisticsOnlineMarksign: Response Code : " + response.getStatusLine().getStatusCode());
			logger.info(result.toString());

			// 转换结果
			ObjectMapper mapper = new ObjectMapper();
			BaseResultWrapper<TradeGetResult> wrapper;
			try {
				wrapper = mapper.readValue(result.toString(), new TypeReference<BaseResultWrapper<TradeGetResult>>(){});
				logger.info("logisticsOnlineMarksign: mapper read OK");
				return wrapper;
			} catch (Exception e) { // 这段可能没必要,若经过了测试之后的话
				e.printStackTrace();
				logger.info("logisticsOnlineMarksign 1: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logisticsOnlineMarksign: " + e.getMessage());
		}
		return null;
	}

	/**
	 * kdt.logistics.online.confirm 卖家确认发货
	 * 确认发货的目的是让交易流程继续走下去，确认发货后交易状态会由【买家已付款】变为【卖家已发货】
	 * https://open.koudaitong.com/doc/api?method=kdt.logistics.online.confirm
	 *
	 * @param params
	 * @return
	 */
	public static ShippingResultWrapper logisticsOnlineConfirm(HashMap<String, String> params){
		String method = "kdt.logistics.online.confirm";
//		HashMap<String, String> params = new HashMap<String, String>();
//		//params.put("num_iid", "2651514");
		if (null == params) { // must
			params = new HashMap<String, String>();
		}

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient();
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(result.toString());
			logger.info("logisticsOnlineConfirm: Response Code : " + response.getStatusLine().getStatusCode());
			logger.info(result.toString());

			// 转换结果
			ObjectMapper mapper = new ObjectMapper();
			ShippingResultWrapper wrapper;
			try {
				wrapper = mapper.readValue(result.toString(), ShippingResultWrapper.class);
				logger.info("logisticsOnlineConfirm: mapper read OK");
				return wrapper;
			} catch (Exception e) { // 这段可能没必要,若经过了测试之后的话
				e.printStackTrace();
				logger.info("logisticsOnlineConfirm 1: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logisticsOnlineConfirm: " + e.getMessage());
		}
		return null;
	}

	/**
	 * kdt.logistics.trace.search 物流流转信息查询
	 * 用户根据交易编号查询物流流转信息，如：2010-8-10 15:23:00 到达杭州集散地。
	 * https://open.koudaitong.com/doc/api?method=kdt.logistics.trace.search
	 *
	 * @param params
	 * @return
	 */
	public static LogisticsTraceWrapper logisticsTraceSearch(HashMap<String, String> params){
		String method = "kdt.logistics.trace.search";
//		HashMap<String, String> params = new HashMap<String, String>();
//		//params.put("num_iid", "2651514");
		if (null == params) { // must
			params = new HashMap<String, String>();
		}

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient();
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(result.toString());
			logger.info("logisticsTraceSearch: Response Code : " + response.getStatusLine().getStatusCode());
			logger.info(result.toString());

			// 转换结果
			ObjectMapper mapper = new ObjectMapper();
			LogisticsTraceWrapper wrapper;
			try {
				wrapper = mapper.readValue(result.toString(), LogisticsTraceWrapper.class);
				logger.info("logisticsTraceSearch: mapper read OK");
				return wrapper;
			} catch (Exception e) { // 这段可能没必要,若经过了测试之后的话
				e.printStackTrace();
				logger.info("logisticsTraceSearch 1: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logisticsTraceSearch: " + e.getMessage());
		}
		return null;
	}

	/**
	 * kdt.logistics.online.marksign 卖家标记签收
	 * 标记签收的目的是让交易流程继续走下去，标记签收后交易状态会由【卖家已发货】变为【买家已签收】，
	 * 通常到店自提的订单需要卖家做标记签收操作
	 *
	 * 使用泛型 BaseResultWrapper<IsSuccessResponse> 就不需要IsSuccessResponseWrapper了!
	 *
	 * 结果表明, 若重复调用同一物流信息, 将返回相同的成功结果; 若换物流, 则10分钟内只能更改一次
	 *
	 * @param params
	 * @return
	 */
	public static BaseResultWrapper<IsSuccessResponse> logisticsOnlineMarksign(HashMap<String, String> params){
		String method = "kdt.logistics.online.marksign";
//		HashMap<String, String> params = new HashMap<String, String>();
//		//params.put("num_iid", "2651514");
		if (null == params) { // must
			params = new HashMap<String, String>();
		}

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient();
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(result.toString());
			logger.info("logisticsOnlineMarksign: Response Code : " + response.getStatusLine().getStatusCode());
			logger.info(result.toString());

			// 转换结果
			ObjectMapper mapper = new ObjectMapper();
			BaseResultWrapper<IsSuccessResponse> wrapper;
			try {
				wrapper = mapper.readValue(result.toString(), new TypeReference<BaseResultWrapper<IsSuccessResponse>>(){});
				logger.info("logisticsOnlineMarksign: mapper read OK");
				return wrapper;
			} catch (Exception e) { // 这段可能没必要,若经过了测试之后的话
				e.printStackTrace();
				logger.info("logisticsOnlineMarksign 1: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logisticsOnlineMarksign: " + e.getMessage());
		}
		return null;
	}

	/**
	 * 测试修改交易备注
	 * https://open.koudaitong.com/doc/api?method=kdt.trade.memo.update
	 *
	 * 注意，因为备注有个上限，好像系500字符，所以建议把新的备注添加到前面！
	 *
	 * @param params
	 * @return
	 */
	public static BaseResultWrapper<TradeMemoUpdateResult> tradeMemoUpdate(HashMap<String, String> params){
		String method = "kdt.trade.memo.update";
//		HashMap<String, String> params = new HashMap<String, String>();
//		//params.put("num_iid", "2651514");
		if (null == params || params.size() == 0 || StringUtils.isBlank(params.get("tid"))) { // must
			//params = new HashMap<String, String>();
			return null; // 无必需参数
		}

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient();
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			//System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			//System.out.println(result.toString());
			logger.info("tradeMemoUpdate: Response Code : " + response.getStatusLine().getStatusCode());
			logger.info(result.toString());

			// 转换结果
			ObjectMapper mapper = new ObjectMapper();
			BaseResultWrapper<TradeMemoUpdateResult> wrapper;
			try {
				wrapper = mapper.readValue(result.toString(), new TypeReference<BaseResultWrapper<TradeMemoUpdateResult>>(){});
				logger.info("mapper read OK");
				return wrapper;
			} catch (Exception e) { // 这段可能没必要,若经过了测试之后的话
				e.printStackTrace();
				logger.info("1: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return null;
	}

	/**
	 * 针对有时修改备注系增加的，有时系覆盖的，所以封装起来多一个选项。若系增加则先查询该订单获取原备注。
	 * 注意，因为备注有个上限，好像系500字符，所以建议把新的备注添加到前面！
	 *
	 * {@link #tradeMemoUpdate(HashMap)}
	 *
	 * @param appendOrOverride true时为添加，false为覆盖
	 * @param tid
	 * @param memo 添加或覆盖的备注内容
	 * @return
	 */
	public static BaseResultWrapper<TradeMemoUpdateResult> tradeMemoUpdateWrapper(Boolean appendOrOverride,
			String tid, String memo){

		TradeDetail trade = null;
		if (appendOrOverride) { // 先查询原订单信息
			HashMap<String, String> paramsTradeGet = new HashMap<String, String>();
			paramsTradeGet.put("tid", tid);
			paramsTradeGet.put("fields", "tid,trade_memo,title");

			BaseResultWrapper<TradeGetResult> wrapper = tradeGet(paramsTradeGet);
			if (null != wrapper && null != wrapper.getResponse()) {
				trade = ((TradeGetResult)wrapper.getResponse()).getTrade();
				if (null == trade) {
					return null;
				}
			} else {
				// 获取订单信息出错
				return null;
			}
		}

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tid", tid);
		if (appendOrOverride) {
			params.put("memo", memo + " " + trade.getTrade_memo()); // 注意要保留原来的留言
		} else {
			params.put("memo", memo);
		}

		return KDTApi.tradeMemoUpdate(params);
	}

	/*
	 * 测试获取单个商品信息
	 */
	private static void sendGet(){
		String method = "kdt.items.onsale.get"; //"kdt.item.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("num_iid", "2651514");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(); // APP_ID, APP_SECRET
			response = kdtApiClient.get(method, params);
//			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

//			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取添加商品
	 */
	private static void sendPost(){
		String method = "kdt.item.add";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("price", "999.01");
		params.put("title", "测试商品");
		params.put("desc", "这是一个号商铺");
		params.put("is_virtual", "0");
		params.put("post_fee", "10.01");
		params.put("sku_properties", "");
		params.put("sku_quantities", "");
		params.put("sku_prices", "");
		params.put("sku_outer_ids", "");
		String fileKey = "images[]";
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("/Users/xuexiaozhe/Desktop/1.png");
		filePaths.add("/Users/xuexiaozhe/Desktop/2.png");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(); // APP_ID, APP_SECRET
			response = kdtApiClient.post(method, params, filePaths, fileKey);
//			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

//			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * test
	 *
	 * @param args
	 */
	public static void main(String[] args){
		//sendGetShopBasic();
		//sendGet();
		//sendPost();

		// GET 测试获取店铺基本信息开始
//		ShopBasicWrapper wrapper1 = null;
//		try {
//			wrapper1 = getShopBasic();
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("json conversion error: " + e.getMessage());
//		}
//		if (null != wrapper1) {
//			if (wrapper1.isSuccess()) {
//				System.out.println("sid=" + wrapper1.getResponse().getSid() + " name=" + wrapper1.getResponse().getName()
//						+ " logo=" + wrapper1.getResponse().getLogo());
//			} else {
////				System.out.println("error_response code=" + wrapper1.getError_response().getCode()
////						+ " msg=" + wrapper1.getError_response().getMsg());
//				logger.info("error_response code=" + wrapper1.getError_response().getCode()
//						+ " msg=" + wrapper1.getError_response().getMsg()	);
//			}
//		}

		// GET 测试获取全部订单列表开始
//		TradesSoldListWrapper wrapper = null;
//		try {
//			HashMap<String, String> queryMap = new HashMap<String, String>();
//			//queryMap.put("status", "TRADE_BUYER_SIGNED");
//			queryMap.put("start_created", "2015-11-16 10:09:54"); // 2015-11-16 10:00:00
//			queryMap.put("end_created", "2015-11-16 12:00:00");
//			//queryMap.put("start_update", "2015-11-11 10:42:19");
//			queryMap.put("status", "WAIT_SELLER_SEND_GOODS");
//			wrapper = getTradesSoldList(queryMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("json conversion error: " + e.getMessage());
//		}
//
//		if (null != wrapper) {
//			if (wrapper.isSuccess()) {
//				List<TradeDetail> trades = wrapper.getResponse().getTrades();
//				if (null != trades && trades.size() > 0) {
//					System.out.println("total_results=" + wrapper.getResponse().getTotal_results()
//							+ " tid=" + trades.get(0).getTid()
//							+ " title=" + trades.get(0).getTitle());
//					List<TradeOrder> orders = trades.get(0).getOrders();
//					if (null != orders && orders.size() > 0) {
//						System.out.println("TradeOrder oid=" + orders.get(0).getOid()
//								+ " num_iid=" + orders.get(0).getNum_iid()
//								+ " title=" + orders.get(0).getTitle());
//					}
//				}
//			} else {
////				System.out.println("error_response code=" + wrapper.getError_response().getCode()
////						+ " msg=" + wrapper.getError_response().getMsg());
//				logger.info("error_response code=" + wrapper.getError_response().getCode()
//						+ " msg=" + wrapper.getError_response().getMsg()	);
//			}
//		}

		// 测试物流发货接口
//		// 快递订单信息来源: https://koudaitong.com/v2/trade/order/detail.html?order_no=E20151113172630068592656
//		// 在这个订单上发货 测试店铺 E20151116165023068520702
//		// 结果表明, 若重复调用同一信息, 将返回相同的成功结果;
//		// 3中通 768405773847
//		// 2圆通 560079064174
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("is_no_express", "0");
//		params.put("tid", "E20151116165023068520702"); // 有赞同步后的设备订单ID格式为tid-oid
//		params.put("out_stype","2");
//		params.put("out_sid", "560079064174");
//		ShippingResultWrapper wrapper = KDTApi.logisticsOnlineConfirm(params);
//		if (null != wrapper && wrapper.isSuccess()
//				&& wrapper.getResponse().getShipping().getIs_success()) {
//			logger.info("OK");
//		} else {
//			logger.info("error_response code=" + wrapper.getError_response().getCode()
//			+ " msg=" + wrapper.getError_response().getMsg()	);
//		}

		// 测试物流发货流转信息
//		// 快递订单信息来源: https://koudaitong.com/v2/trade/order/detail.html?order_no=E20151113172630068592656
//		// 在这个订单上发货 测试店铺 E20151116165023068520702
//		// 结果表明, 若重复调用同一信息, 将返回相同的成功结果; 若换物流, 则10分钟内只能更改一次
//		// 3中通 768405773847
//		// 2圆通 560079064174
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("tid", "E20151116165023068520702"); // 有赞同步后的设备订单ID格式为tid-oid
//		LogisticsTraceWrapper wrapper = KDTApi.logisticsTraceSearch(params);
//		if (null != wrapper && wrapper.isSuccess()) {
//			logger.info(wrapper.getResponse().getCompany_name());
//			List<trace_list_item> info = wrapper.getResponse().getTrace_list();
//			String infoString = "";
//			for (trace_list_item item : info) {
//				infoString += item.getStatus_time() + " " + item.getStatus_desc();
//			}
//			logger.info(infoString);
//		} else {
//			logger.info("error_response code=" + wrapper.getError_response().getCode()
//			+ " msg=" + wrapper.getError_response().getMsg()	);
//		}

		// 测试物流标记签收
//		// 快递订单信息来源: https://koudaitong.com/v2/trade/order/detail.html?order_no=E20151113172630068592656
//		// 在这个订单上发货 测试店铺 E20151116165023068520702
//		// 结果表明, 若重复调用同一信息, 将返回相同的成功结果; 若换物流, 则10分钟内只能更改一次
//		// 3中通 768405773847
//		// 2圆通 560079064174
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("tid", "E20151116165023068520702"); // 有赞同步后的设备订单ID格式为tid-oid
//		BaseResultWrapper<IsSuccessResponse> wrapper = KDTApi.logisticsOnlineMarksign(params);
//		if (null != wrapper && wrapper.isSuccess() && ((IsSuccessResponse)wrapper.getResponse()).getIs_success()) {
//			logger.info("OK");
//		} else {
//			logger.info("error_response code=" + wrapper.getError_response().getCode()
//			+ " msg=" + wrapper.getError_response().getMsg()	);
//		}

		// 测试修改订单备注
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("tid", "E20151116165023068520702"); // 有赞同步后的设备订单ID格式为tid-oid
//		params.put("memo", "修改的卖家备注!");
//		BaseResultWrapper<TradeMemoUpdateResult> wrapper = KDTApi.tradeMemoUpdate(params);
//		if (null != wrapper && wrapper.isSuccess()) {
//			logger.info("OK " + ((TradeMemoUpdateResult)wrapper.getResponse()).getTrade().getTrade_memo());
//		} else {
//			logger.info("error_response code=" + wrapper.getError_response().getCode()
//			+ " msg=" + wrapper.getError_response().getMsg());
//		}

		HashMap<String, String> paramsTradeGet = new HashMap<String, String>();
		paramsTradeGet.put("tid", "E20151202153703068590016");
		paramsTradeGet.put("fields", "tid,trade_memo");
		BaseResultWrapper<TradeGetResult> wrapper = tradeGet(paramsTradeGet);
		if (null != wrapper && null != wrapper.getResponse()) {
			TradeDetail trade = ((TradeGetResult)wrapper.getResponse()).getTrade();
			if (null == trade) {
//				logger.info("error_response code=" + wrapper.getError_response().getCode()
//				+ " msg=" + wrapper.getError_response().getMsg());
				logger.info("TradeDetail trade 空！");
			} else {
				logger.info("tradeGet OK！");
			}
		} else {
			logger.info("error_response code=" + wrapper.getError_response().getCode()
			+ " msg=" + wrapper.getError_response().getMsg());
		}

	}
}
