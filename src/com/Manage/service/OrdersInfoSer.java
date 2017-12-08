package com.Manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DES;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.YouzanSyncLogisticsResult;
import com.Manage.entity.report.YouzanSyncOrderResult;
import com.kdt.api.KDTApi;
import com.kdt.api.LogisticsUtils;
import com.kdt.api.YouzanConfig;
import com.kdt.api.entity.TradeDetail;
import com.kdt.api.entity.TradeOrder;
import com.kdt.api.result.BaseResultWrapper;
import com.kdt.api.result.IsSuccessResponse;
import com.kdt.api.result.ShippingResultWrapper;
import com.kdt.api.result.TradeMemoUpdateResult;
import com.kdt.api.result.TradesSoldListWrapper;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午6:29:54 * @version 1.0 * @parameter
 * * @since * @return
 */
@Service
public class OrdersInfoSer extends BaseService
{
	private Logger logger = LogUtil.getInstance(OrdersInfoSer.class);


	@Autowired
	DeviceDealOrdersSer deviceDealOrdersSer;
	/**
	 * 添加订单
	 *
	 * @param menuInfo
	 * @return
	 */
	public boolean insertinfo(OrdersInfo orders)
	{

		logger.debug("添加订单service");
		try
		{
			int temp = ordersInfoDao.insertinfo(orders);
			if (temp > 0)
			{
				logger.debug("添加订单成功");
				return true;
			}
			else
			{
				logger.debug("添加订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 添加订单 像有赞订单同步使用更多的字段
	 *
	 * @param menuInfo
	 * @return
	 */
	public boolean insertinfoWithExtra(OrdersInfo orders)
	{

		logger.debug("添加订单service");
		try
		{
			int temp = ordersInfoDao.insertinfoWithExtra(orders);
			if (temp > 0)
			{
				logger.debug("添加订单成功");
				return true;
			}
			else
			{
				logger.debug("添加订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 更新未完成订单
	 *
	 * @param orders
	 * @return
	 */
	public boolean updateiffinish(OrdersInfo orders)
	{

		logger.debug("更新订单service");
		try
		{
			int temp = ordersInfoDao.updateiffinish(orders);
			if (temp > 0)
			{
				logger.debug("更新订单成功");
				return true;
			}
			else
			{
				logger.debug("更新订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 一个更通用的完成订单更新, 但前端要确保各字段输入值的有效性!
	 *
	 * @param orders
	 * @return
	 */
	public boolean updateiffinishMore(OrdersInfo orders)
	{

		logger.debug("更新订单service");
		try
		{
			int temp = ordersInfoDao.updateiffinishMore(orders);
			if (temp > 0)
			{
				logger.debug("更新订单成功");
				return true;
			}
			else
			{
				logger.debug("更新订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	public String getpageString(SearchDTO searchDTO)
	{

		logger.debug("分页server开始");
		try
		{
			String jsonString = ordersInfoDao.getpageString(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}



	/**
	 * 分页查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpageString1(SearchDTO searchDTO)
	{

		logger.debug("分页server开始");
		try
		{
			String jsonString = ordersInfoDao.getpageString1(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}


/*
	public String getpageString2(SearchDTO searchDTO)
	{

		logger.debug("分页server开始");
		try
		{
			String jsonString = ordersInfoDao.getpageString2(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}*/



	/**
	 * 删除
	 *
	 * @param ordersID
	 * @return
	 */
	public boolean delete(String ordersID)
	{

		logger.debug("删除订单service");
		try
		{
			int temp = ordersInfoDao.delete(ordersID);
			if (temp > 0)
			{
				logger.debug("删除订单成功");
				// 删除该订单下的流量订单
				int temp1 = flowDealOrdersDao.delfloworderbyoid(ordersID);
				logger.debug("删除流量订单返回:" + temp1);
				// 删除该订单下的设备订单
				int temp2 = deviceDealOrdersDao.deldevorderbyoid(ordersID);
				logger.debug("删除设备订单返回:" + temp2);
				// 将设备订单的订单状态改为可使用

				return true;
			}
			else
			{
				logger.debug("删除订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 编辑
	 */
	public boolean edit(OrdersInfo ordersInfo)
	{

		logger.debug("编辑订单service");
		try
		{
			int temp = ordersInfoDao.edit(ordersInfo);
			if (temp > 0)
			{
				logger.debug("编辑订单成功");
				return true;
			}
			else
			{
				logger.debug("编辑订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 查询详细包含 流量订单和设备订单
	 *
	 * @param ordersInfoID
	 * @return
	 */
	public OrdersInfo getOrdersInfo(String ordersInfoID)
	{

		logger.debug("查询订单详细service");
		try
		{
			OrdersInfo temp = ordersInfoDao.getInfo(ordersInfoID);
			if (temp != null)
			{
				logger.debug("查询订单详细成功");
				List<FlowDealOrders> flowDealOrders = flowDealOrdersDao.getlistbyoid(ordersInfoID);
				List<DeviceDealOrders> deviceDealOrders = deviceDealOrdersDao.getlistbyoid(ordersInfoID);
				temp.setFlowDealOrders(flowDealOrders);
				temp.setDeviceDealOrders(deviceDealOrders);
				return temp;
			}
			else
			{
				logger.debug("查询订单详细失败");
				return null;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 查询订单详细
	 *
	 * @param ordersInfoID
	 * @return
	 */
	public OrdersInfo getdetail(String ordersInfoID)
	{

		logger.debug("查询订单详细service");
		try
		{
			OrdersInfo temp = ordersInfoDao.getInfo(ordersInfoID);
			if (temp != null)
			{
				logger.debug("查询订单详细成功");
				return temp;
			}
			else
			{
				logger.debug("查询订单详细失败");
				return null;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}



	/**
	 * 统计
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int statistics(OrdersInfo ordersInfo)
	{

		try
		{
			return ordersInfoDao.statistics(ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	/**
	 * 合计
	 */
	public int sumorder(OrdersInfo ordersInfo)
	{

		try
		{
			return ordersInfoDao.sumorder(ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	public List<OrdersInfo> getOrdersInfoforCustomer(OrdersInfo ordersInfo)
	{

		try
		{
			return ordersInfoDao.getOrdersInfo(ordersInfo);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 同 {@link #getOrdersInfoforCustomer} 但改名以使表达更准确
	 *
	 * @param ordersInfo
	 * @return
	 */
	public List<OrdersInfo> getOrdersInfoByFilter(OrdersInfo ordersInfo)
	{

		try
		{
			return ordersInfoDao.getOrdersInfo(ordersInfo);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}






	public List<DeviceDealOrders> getDeviceDealOrdersIDByorderID(String orderID)
	{

		try
		{
			return ordersInfoDao.getDeviceDealOrdersIDByorderID(orderID);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 更新快递信息
	 *
	 * @param expresssNO
	 * @param logistics
	 */
	public int updateLogisticsInfo(String expresssNO, String logistics, String orderID, String address, List<DeviceDealOrders> deviceDealOrders)
	{

		try
		{
			return ordersInfoDao.updateLogisticsInfo(expresssNO, logistics, orderID, address, deviceDealOrders);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}



	/**
	 * 获取快递公司名称
	 *
	 * @return
	 */
	public List<Dictionary> getExpress()
	{

		try
		{
			return ordersInfoDao.getExpress();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 根据ID获流量订单
	 *
	 * @param orderID
	 * @return
	 */
	public List<FlowDealOrders> getflowDeal(String orderID)
	{

		try
		{
			return ordersInfoDao.getflowDeal(orderID);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 修改流量订单为激活状态
	 *
	 * @param orderID
	 * @param SN
	 * @return
	 */
	public int updateFlowDeal(String orderID, String SN)
	{

		try
		{
			return ordersInfoDao.updateFlowDeall(orderID, SN);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}



	/**
	 * 确认退订此订单 同时更新操作其下的相关订单
	 *
	 * @param ordersID
	 * @return
	 */
	public boolean cancelPaidOrder(OrdersInfo orders)
	{

		logger.debug("退订订单service");
		try
		{
			int temp = ordersInfoDao.cancelPaidOrder(orders);
			if (temp > 0)
			{
				logger.debug("退订订单成功");

				// 删除该订单下的流量订单
				// orders
				FlowDealOrders fOrders = new FlowDealOrders();
				fOrders.setOrderID(orders.getOrderID());
				fOrders.setModifyDate(orders.getModifyDate());
				fOrders.setModifyUserID(orders.getModifyUserID());

				// 应该值得标记/记录一下若更新总订单成功但意外未更新流量订单的情况
				try
				{
					int temp1 = flowDealOrdersDao.updateFlowOrderUnavailable(fOrders);
					logger.debug("退订后操作流量订单返回:" + temp1);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					logger.error("退订时更新总订单成功但更新流量订单失败");
					// TODO 再作适当处理, 有必要时, 目前后续当作退订成功
				}

				// 该订单下的设备订单 目前不需要操作

				return true;
			}
			else
			{
				logger.debug("退订订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	public int updatewxOrders(OrdersInfo order)
	{

		return ordersInfoDao.updatewxOrders(order);
	}



	/**
	 * 查询有赞相关订单
	 *
	 * 例如: 最后一条新创建的订单,将以该时间为开始时间, 查询从那里以来的订单
	 *
	 * @param ordersInfo
	 * @return
	 */
	public List<OrdersInfo> getOrdersInfoYouzan(OrdersInfo ordersInfo)
	{

		logger.debug("查询有赞订单列表");
		try
		{
			List<OrdersInfo> temp = ordersInfoDao.getOrdersInfoYouzan(ordersInfo);
			if (temp != null)
			{
				logger.debug("查询成功");
				return temp;
			}
			else
			{
				logger.debug("查询失败");
				return null;
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 把有赞订单同步放到 service 封装, 来实现更好的重用, 使用在 QuartzJOB 中调用, 等等
	 * -----------------------
	 *
	 * "有赞订单同步"系把有赞商城中的有效订单(已付款), 包括设备购买, WIFI流量购买, WIFI流量购买+WIFI设备租赁三种订单, 通过
	 * 网络请求配合有赞平台的API去获取, 然后在运营后台创建相应的业务订单.
	 *
	 * 同步分定时任务的周期同步和这里的手工同步. 同步的过程分两步: 先获取上次同步以来有赞平台新创建的有效订单,
	 * 这部分订单将全部作新创建业务订单处理; 再获取上次同步以来有更新的订单, 过滤出有效的订单, 并且之前未处理过的订单, 例如由"未付款"
	 * 更新为"已付款", 或其他情形分别相应处理.
	 *
	 * 为了实现的需要, 并且使业务/运营上在"有赞"和"运营后台"间的订单保持一种时间的关系, 设定后台新创建的业务订单的 创建时间,
	 * 对于上面第一步的新订单, 使用有赞订单中的创建时间, 对于上面第二步的新订单, 使用有赞订单中的更新时间.
	 *
	 * !! 与严婷讨论的做法: (1) 讨论决定使用标星的方式, 即先由管理人员人工地在"有赞"订单后台根据已付款的订单信息, 初步过滤有效的订单,
	 * 例如避免单独拍押金或单独拍流量但没有设备的, 等等, 则标星. 之后同步时只处理标星的. (2) 解析某些情形: (A) 例如,
	 * 假设某订单在上一次同步时因各种原因未标星, 后来经过确认标星了, 则再次同步时, 这种订单将在"新更新的订单"里面,
	 * 并不需要再扫描全部有赞订单才能重新获取到这种订单. 因为"标星"操作能触发订单 更新时间的更改. (3) 但之前未沟通好设备绑定的有关处理.
	 * 目前我们的订单途径大概分两种类型: 一, 系官网和APP的下单因为都可以与客户 自己的帐号挂勾, 所以较容易提供SN去下单. 二,
	 * 系淘宝和有赞的部分. 因为多数客户在下单时未与后台数据的客户挂勾, 故需要较多的人工处理. 但通过有赞API,
	 * 我觉得可以比淘宝更自动化一点处理某种常见的情形: 例如, (A)使用一个有赞订单 拍了押金或购买一个设备和若干个流量宝贝,
	 * 这种订单在同步订单时可考虑关联, 等等. (B) 或者在宝贝编辑里再增加一个 客户可留言的字段"设备SN", 设为可选, 若客户提供到,
	 * 也可考虑在同步时就创建关联, 且通过设备的状态和一定的规则 判断要不要即时激活等. !!! 但目前实现并未全部处理, 暂: (A)
	 * 尽量避免产生只拍了押金的情形, 管理人员以在有赞订单后台进行了标星处理, 所以出现有一个单独押金的宝贝订单,
	 * 应该至少还有一个订单系该客户拍下的流量宝贝. 在同一次同步中能都查询到. 所以这种可确定可作个关联.(一些特殊情况下,
	 * 该次同步未同时查询到这样的订单, 也可以通过相关API单独查该客户 的全部订单去判断, 或者甚至通过其他途径去购买了流量宝贝,
	 * 则仍可在后台创建一个押金的订单, 待后台运营处理)
	 *
	 * 参考: 有赞 API 文档 https://open.koudaitong.com/doc
	 *
	 * 有赞订单的订单状态:
	 *
	 * TRADE_NO_CREATE_PAY（没有创建支付交易） WAIT_BUYER_PAY（等待买家付款）
	 * WAIT_SELLER_SEND_GOODS（等待卖家发货，即：买家已付款） <--------将处理这种状态
	 * WAIT_BUYER_CONFIRM_GOODS（等待买家确认收货，即：卖家已发货） <--------将处理这种状态 虚拟宝贝直接这种状态
	 * TRADE_BUYER_SIGNED（买家已签收）
	 * TRADE_CLOSED（付款以后用户退款成功，交易自动关闭）<--------后期将配合考虑处理这种状态
	 * ALL_WAIT_PAY（包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY） ALL_CLOSED（所有关闭订单）
	 *
	 * 延伸要注意的地方: (1) 有赞申请退款等与后台订单的关系 保证双向互动 (2) 后期必须尽早处理好退款的情况 (3)
	 * 接口和前端增加可强制重新全部扫描的选项, 或者还可以考虑添加时间段选项
	 *
	 * @param adminUserInfo
	 *            从上层获取的管理员帐号, 若系 QuartzJOB 则使用 QuartzJOB 帐号
	 * @return 处理结果 {@link com.Manage.entity.repor.YouzanSyncOrderResult}
	 */
	public YouzanSyncOrderResult syncYouzanOrder(AdminUserInfo adminUserInfo)
	{

		YouzanSyncOrderResult result = new YouzanSyncOrderResult();
		logger.info("同步有赞订单开始");

		// (1) 获取最后一个有赞订单的创建时间, 可使用为上次同步的时间. 因为作为参数输入有赞API时系有效可行的
		String lastSyncTime;
		Date lastSyncDate; // 待用
		OrdersInfo queryOrder = new OrdersInfo();
		// 处理前面的查询系数据库查询失败, 应该不要继续后面处理
		List<OrdersInfo> lastOrders; // = getOrdersInfoYouzan(queryOrder);
		try
		{
			lastOrders = ordersInfoDao.getOrdersInfoYouzan(queryOrder);
			if (lastOrders != null)
			{
				logger.info("查询已有的有赞订单成功");
				// return temp;
			}
			else
			{
				// logger.info("查询已有的有赞订单失败");
				// return null;
			}
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
			logger.info("code: -5, msg:抱歉, 查询已有的有赞订单时数据库操作出错, 请重试!");
			result.setErrorCode(-5);
			result.setMsg("抱歉, 查询已有的有赞订单时数据库操作出错, 请重试!");
			return result;
		}
		if (null != lastOrders && lastOrders.size() == 1)
		{ // 之前有查询并同步过的订单
			lastSyncDate = lastOrders.get(0).getCreatorDate();
			// lastSyncTime = DateUtils.formatDateTime(lastSyncDate); // -->
			// 下面需要增加一秒
			lastSyncTime = DateUtils.beforeNSecondToString(lastSyncDate, 1, "yyyy-MM-dd HH:mm:ss");
		}
		else
		{ // 首次查询, 设置一个较早的值开始查询
			lastSyncTime = "2015-01-01 00:00:00";
		}

		// (2) 获取该时间以来新创建的订单, 放入列表
		TradesSoldListWrapper newCreatedTradesWrapper = null;
		String nowSyncTime = DateUtils.getDateTime(); // 务必保存已确保和后面更新时的一致
		logger.info("sync: start sync from " + lastSyncTime + " to " + nowSyncTime);
		try
		{
			HashMap<String, String> queryMap = new HashMap<String, String>();
			// !暂按都为实物宝贝!（等待卖家发货，即：买家已付款） 若系虚拟宝贝客户付款后状态直接为:
			// WAIT_SELLER_SEND_GOODS
			queryMap.put("status", "WAIT_SELLER_SEND_GOODS");
			queryMap.put("start_created", lastSyncTime);
			queryMap.put("end_created", nowSyncTime);
			newCreatedTradesWrapper = KDTApi.getTradesSoldList(queryMap);
			logger.info("get new created orders successffly");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("sync: json conversion error: " + e.getMessage());
			logger.info("code: -1, msg:抱歉, 系统出错, 请重试! 解释新创建订单错误");
			result.setErrorCode(-1);
			result.setMsg("抱歉, 系统出错, 请重试! 解释新创建订单错误");
			return result;
		}

		List<TradeDetail> newCreatedTrades = null;
		if (null != newCreatedTradesWrapper)
		{
			if (newCreatedTradesWrapper.isSuccess())
			{
				newCreatedTrades = newCreatedTradesWrapper.getResponse().getTrades();
				if (null != newCreatedTrades && newCreatedTrades.size() > 0)
				{
					// System.out.println("total_results=" +
					// newCreatedTradesWrapper.getResponse().getTotal_results()
					// + " tid=" + newCreatedTrades.get(0).getTid()
					// + " title=" + newCreatedTrades.get(0).getTitle());
					List<TradeOrder> orders = newCreatedTrades.get(0).getOrders();
					if (null != orders && orders.size() > 0)
					{
						// System.out.println("TradeOrder oid=" +
						// orders.get(0).getOid()
						// + " num_iid=" + orders.get(0).getNum_iid()
						// + " title=" + orders.get(0).getTitle());
					}
				}
			}
			else
			{
				logger.info("sync: error_response code=" + newCreatedTradesWrapper.getError_response().getCode() + " msg=" + newCreatedTradesWrapper.getError_response().getMsg());
				logger.info("code: -2, msg:抱歉, 访问有赞商城出错, 请重试, 或联系开发/管理员等!");
				result.setErrorCode(-2);
				result.setMsg("抱歉, 访问有赞商城出错, 请重试, 或联系开发/管理员等!");
				return result;
			}
		}

		// (3) 获取该时间以来更新的订单, 放入列表
		// 目前尝试的更新机制系分创建和更新两部分处理, 在当前的有赞定单未上量和定时任务的情况下, 再执行以下
		// 判断更新的接口调用并不会造成相关资源的过重压力. 按有赞的接口说明和实际观察, 这个调用的结果可能与
		// 前面获取新创建的订单会有很多重复, 那后面要过滤掉
		TradesSoldListWrapper newUpdatedTradesWrapper = null;
		try
		{
			HashMap<String, String> queryMap = new HashMap<String, String>();
			// !暂按都为实物宝贝!（等待卖家发货，即：买家已付款） 若系虚拟宝贝客户付款后状态直接为:
			// WAIT_SELLER_SEND_GOODS
			queryMap.put("status", "WAIT_SELLER_SEND_GOODS");
			queryMap.put("start_update", lastSyncTime);
			queryMap.put("end_update", nowSyncTime);
			newUpdatedTradesWrapper = KDTApi.getTradesSoldList(queryMap);
			logger.info("get new updated orders successffly");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("sync: json conversion error: " + e.getMessage());
			logger.info("code: -1, msg:抱歉, 系统出错, 请重试! 解释更新订单错误");
			result.setErrorCode(-1);
			result.setMsg("抱歉, 系统出错, 请重试! 解释更新订单错误");
			return result;
		}

		List<TradeDetail> newUpdatedTrades = null;
		if (null != newUpdatedTradesWrapper)
		{
			if (newUpdatedTradesWrapper.isSuccess())
			{
				newUpdatedTrades = newUpdatedTradesWrapper.getResponse().getTrades();
				if (null != newUpdatedTrades && newUpdatedTrades.size() > 0)
				{
					// List<TradeOrder> orders =
					// newUpdatedTrades.get(0).getOrders();
					// if (null != orders && orders.size() > 0) {
					// // TODO: ...
					// }

					// 过滤掉与前面获取的新创建重复的 trade : newCreatedTrades
					if (null != newCreatedTrades && newCreatedTrades.size() > 0)
					{
						// !下面一行要求务必实现 entity 的 hashCode和equals 方法 同时, 若不存在重复元素,
						// 也返回
						// false 所以不能因为 false 而结束处理
						// http://blog.csdn.net/tavor/article/details/2502350
						// http://sparkandshine.net/java-intersection-union-set-difference-override-equals-hashcode-when-necessary/
						Boolean debugVal = newUpdatedTrades.removeAll(newCreatedTrades);
						if (debugVal)
						{
							logger.info("newUpdatedTrades.removeAll true");
						}
						else
						{
							logger.info("newUpdatedTrades.removeAll false");
						}
						// if(!newUpdatedTrades.removeAll(newCreatedTrades)){
						// logger.info("code: -3, msg:抱歉, 系统出错, 请重试! 排除重复订单出错");
						// jsonResult.put("code", -3);
						// jsonResult.put("msg", "抱歉, 系统出错, 请重试! 排除重复订单出错");
						// response.getWriter().println(jsonResult.toString());
						// return;
						// }
					}
				}
			}
			else
			{
				logger.info("sync: error_response code=" + newUpdatedTradesWrapper.getError_response().getCode() + " msg=" + newUpdatedTradesWrapper.getError_response().getMsg());
				logger.info("code: -2, msg:抱歉, 访问有赞商城出错, 请重试, 或联系开发/管理员等!");
				result.setErrorCode(-2);
				result.setMsg("抱歉, 访问有赞商城出错, 请重试, 或联系开发/管理员等!");
				return result;
			}
		}

		// (4) 统一处理列表中的订单: 创建订单, 设定状态
		newCreatedTrades.addAll(newUpdatedTrades); // 把新创建和新更新的订单集合到一个列表统一处理

		// 先标出订单中的手机号码是否为新客户
		List<String> phoneStrings = new ArrayList<String>();
		/**
		 * 这个Map映射关系: phone -> ( tid -> [oid1, oid2] ) 以备后面处理时快速查询
		 *
		 * 注意! 处理电话这里, 要把需要处理的各种标星的都添加 例如原处理5星, 现在要添加处理3星, 则务必补上
		 *
		 * TODO: 可以考虑把下面部分放入 TradeDetail 的 entity 中的成员函数去返回
		 */
		// HashMap<String, HashMap<String, List<Integer>>> phoneOrderMap = new
		// HashMap<String, HashMap<String, List<Integer>>>(); // --> 目前这个Map根本未用
		for (TradeDetail tradeDetail : newCreatedTrades)
		{
			if (tradeDetail.getSeller_flag() == 5 || tradeDetail.getSeller_flag() == 3)
			{ // 只处理已标星的
				List<TradeOrder> orders = tradeDetail.getOrders();

				for (TradeOrder tradeOrder : orders)
				{
					if (!YouzanConfig.OUTER_ITEM_ID_OTHER_SAMPLE.equals(tradeOrder.getOuter_item_id()))
					{
						// 要不要作联系手机格式校验? 考虑情形，因为已系标星订单. 说明此订单已人工处理,
						// 但若通过沟通, 客户发现输错了手机, 因客户留言无法更改, 所以一般可以在卖家备注
						// 里补充正确的手机, 可能目前也只有这一个途径, 因为客户似乎无法增加/修改订单留言
						// TODO: 待确认
						String foundPhone = tradeOrder.getPhone();
						if (StringUtils.isNotBlank(foundPhone))
						{ // !"".equals(foundPhone)
							phoneStrings.add(foundPhone);

							// if (phoneOrderMap.containsKey(foundPhone)) {
							// // 此电话已在列表里 引用并加入新订单TradeDetail
							// if
							// (phoneOrderMap.get(foundPhone).containsKey(tradeDetail.getTid()))
							// {
							// // 此订单tid已在列表里, 引用并加入TradeOrder的ID
							// List<Integer> oidList =
							// phoneOrderMap.get(foundPhone).get(tradeDetail.getTid());
							// oidList.add(tradeOrder.getOid());
							// phoneOrderMap.get(foundPhone).put(tradeDetail.getTid(),
							// oidList);
							// } else {
							// // 此订单tid未在列表里, 新建一个list加入
							// List<Integer> oidList = new ArrayList<Integer>();
							// oidList.add(tradeOrder.getOid());
							// phoneOrderMap.get(foundPhone).put(tradeDetail.getTid(),
							// oidList);
							// }
							// } else {
							// // 此电话未在列表里, 全部新增
							// HashMap<String, List<Integer>> orderMap = new
							// HashMap<String, List<Integer>>();
							// List<Integer> oidList = new ArrayList<Integer>();
							// oidList.add(tradeOrder.getOid());
							// orderMap.put(tradeDetail.getTid(), oidList);
							// phoneOrderMap.put(foundPhone, orderMap);
							// }

							continue; // 此 TradeOrder 的电话检索已完毕, 跳到下一个继续处理
						}
						else
						{
							// T待确认, 是否应该认为没有电话则订单非法 --> 不需要，因为系必填留言, 且已系标星订单
							// --> TO DO: 为了增加一定的容错保证, 例如管理员的确在某时间新建宝贝没有添加留言必填字段
							// 那可考虑使用总订单的收货人姓名/地址/手机号码(要判断是否为手机号码)等
							// --> 应该放到下面其下的详细宝贝订单去判断后使用
						}

					}
					else
					{
						// TODO: 这种不需要留言手机的后面单独处理
					}
				}

			} // if (tradeDetail.getSeller_flag() == 5)
			else if (tradeDetail.getSeller_flag() == 3)
			{
				// TODO: 三星代表取消订单

			} // if (tradeDetail.getSeller_flag() == 3)
		}
		// TODO: 可能在数量较大的情况下根据返回结果大小做好分批处理
		List<CustomerInfo> existedCustomerInfos = null;
		if (phoneStrings.size() > 0)
		{
			CustomerInfo queryInfo = new CustomerInfo();
			queryInfo.setPhone(StringUtils.join(phoneStrings, ','));
			try
			{
				existedCustomerInfos = customerInfoDao.getSearchCustomerInfoListInPhoneList(queryInfo);
			}
			catch (BmException e)
			{
				logger.info(e.getMessage());
				e.printStackTrace();
				// existedCustomerInfos = null;

				// 若查询失败, 是否应该终止? 觉得应该终止, 否则后面当作全部为新客户, 新增时会失败
				logger.info("抱歉, 查询手机是否已注册时数据库操作出错, 请重试!");
				result.setErrorCode(-5);
				result.setMsg("抱歉, 查询手机是否已注册时数据库操作出错, 请重试!");
				return result;
			}
		}
		else
		{
			// 这并非一定系无效订单数据, 如没有新订单, 或全部不是流量/押金/设备等必须留言电话的订单列表
			// TODO: 处理不需要留言电话的订单

			logger.info("处理结束: 没有新增加的订单");
			result.setErrorCode(1);
			result.setMsg("同步有赞订单结束:同步时段: " + lastSyncTime + " - " + nowSyncTime + "<br /><br />处理结束: 没有新增加的订单");
			return result;
		}

		// 做好统计数据反馈给前端
		/** 有赞总订单数量 */
		Integer okQueryYouzanTradesDetailCount = 0; // 不是
													// newCreatedTrades.size()
													// 这个带上所有未标星的订单
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
		/**
		 * 个别情况下, 新添加的宝贝, 可能管理人员未按要求编辑商品信息添加"联系手机"必填留言字段, 也统计一下, 并提醒 添加新手机客户时,
		 * 若添加出错, 也统计到这个
		 */
		Integer missingPhoneCount = 0;
		/** 已标3星的订单, 成功取消的有赞总订单数量 注意系有赞的总订单即整个购物车订单算作1 */
		Integer star3OkCanceledCount = 0;
		/** 已标3星的订单, 需要取消但处理失败的有赞总订单数量 注意系有赞的总订单即整个购物车订单算作1 */
		Integer star3FailedCancelledCount = 0;
		/** 已标3星的订单, 反馈不处理的有赞总订单数量 注意系有赞的总订单即整个购物车订单算作1 */
		Integer star3IgnoredCount = 0;

		// 以实现 MCC 列表值 "454,455,456", 结合存在的国家列表 输出需要的字段值
		List<CountryInfo> countries;
		try
		{
			countries = countryInfoDao.getAll("");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("code: -5, msg:抱歉, 查询国家列表时数据库操作出错, 请重试!");
			result.setErrorCode(-5);
			result.setMsg("抱歉, 查询国家列表时数据库操作出错, 请重试!");
			return result;
		}

		for (TradeDetail tradeDetail : newCreatedTrades)
		{
			String tradeMemo = ""; // 卖家备注 --> 应该提前到此, 例如缺少留言字段时也可同步一下备注,
									// 标其他星的也可

			if (tradeDetail.getSeller_flag() == 5)
			{ // 只处理已标星的
				okQueryYouzanTradesDetailCount++;

				// (A)创建总订单

				// 检索订单内容 按与严婷的讨论确定, 优先处理/或必须处理已星标的订单. 若未星标, 待后面更新

				// 规则: (1) 已标星的全部订单都处理 (2) 流量订单直接创建总订单和流量订单 设备订单在延后在运营后台处理
				// 有赞订单的处理人员在留言中流明实际情况, 在后台处理时做好 例如流量订单的激活

				// 若为实物宝贝(目前全部WIFI和设备/押金)都设定为实物宝贝了, 含实物宝贝的订单都有地址,也带收货人
				// 的手机号码. 注意后面每个细单里的也有一个"联系手机"的记录. 原则手机号码应该使用后面那个. 但
				// 联系人的地址仍然统一使用这总订单的相关信息, 如receiver_city, receiver_district等.
				// 特殊的情形
				// 例如一个客户帮他人购买押金和流量, 或者为自己的另一个手机号码购买流量等, 按原则使用细单里的
				// 手机号码, 则可支持这些特殊的情形, 按每个细单去创建订单(这样创建后, 若发现搞错手机号码等情况,
				// 可继续在运营后台处理, 因为流量订单必须在后台激活等, 不会自动激活)

				List<TradeOrder> orders = tradeDetail.getOrders();
				if (null != orders && orders.size() > 0)
				{
					// 全部国家查询已前移

					// String tradeMemo = ""; // 卖家备注 --> 应该再提前, 例如缺少留言字段时也可同步一下
					for (TradeOrder tradeOrder : orders)
					{

						// 先判断是否为新客户, 若是, 先创建客户信息 注意以后若有订单类型无需处理客户信息的, 把这部分
						// 放下下面各种类型去 TODO: 可考虑细分为单独的功能函数
						Map<String, String> messageMap = tradeOrder.getBuyerMessageMap();
						String phone = null;
						CustomerInfo cus = null;

						if (!YouzanConfig.OUTER_ITEM_ID_OTHER_SAMPLE.equals(tradeOrder.getOuter_item_id()))
						{
							phone = messageMap.get(YouzanConfig.BUY_MESSAGE_TITLE_PHONE);

							if (null == phone)
							{ // 若phone为空,可能管理员未按格式要求编辑好商品的留言必填"联系手机"
								// 为了增加一定的容错保证, 例如管理员的确在某时间新建宝贝没有添加留言必填字段
								// 那可考虑使用总订单的收货人姓名/地址/手机号码(要判断是否为手机号码)等
								// --> 尝试使用总订单的收货地址里的手机号码, 这种情况事实很少
								if (StringUtils.isNotBlank(tradeDetail.getReceiver_mobile()))
								{
									phone = tradeDetail.getReceiver_mobile();
								}
								else
								{
									missingPhoneCount++;

									tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "）缺少联系手机 | ";
									continue; // 待处理 应该必须有手机, 否则无法正确创建后面的订单
								}
							}
							// 判断是否已存在系统中
							for (CustomerInfo customer : existedCustomerInfos)
							{
								if (customer.getPhone().equals(phone))
								{
									cus = customer;
									break;
								}
							}
							if (null == cus)
							{ // 新客户手机
								cus = createCustomerInfo(adminUserInfo, messageMap.get(YouzanConfig.BUY_MESSAGE_TITLE_NAME), phone, tradeDetail.getFullAddress());
								if (null == cus)
								{
									// 添加失败
									logger.info("code: XX, msg:添加新客户失败");

									// if
									// (YouzanConfig.OUTER_ITEM_ID_DEPOSITE.equals(tradeOrder.getOuter_item_id())
									// ||
									// YouzanConfig.OUTER_ITEM_ID_DEVICE.equals(tradeOrder.getOuter_item_id())
									// ||
									// tradeOrder.getOuter_item_id().startsWith(YouzanConfig.OUTER_ITEM_ID_WIFI))
									// {
									// // 流量/设备/押金宝贝
									okQueryYouzanTradesOrderCount++;
									failedSyncCount++;
									// } else {
									// otherTypeCount++; // 后期这些宝贝可以不需要处理电话,
									// 所以不应该在这里计算
									// }
									// //missingPhoneCount++; // 这情况也按这个统计 -->
									// 不应该重复

									tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "）添加客户手机失败 | ";
									continue; // TODO: 应该严格地作好持久化记录
												// 之后在适当时候做好重试处理
								}
							}
						}
						else
						{
							// 这是其他类型, 不需要处理电话号码, 其他流量/押金/设备/自取 都要处理电话
							// 同时继续下面处理
						}

						if (YouzanConfig.OUTER_ITEM_ID_DEPOSITE.equals(tradeOrder.getOuter_item_id()))
						{ // 押金宝贝
							// 注意要支持多个宝贝数量的情形 这样就要求订单ID还要加上一个序号
							Integer ordersNum = tradeOrder.getNum();
							for (int i = 0; i < ordersNum; i++)
							{

								okQueryYouzanTradesOrderCount++;

								// 只有押金的总订单, 假定标星已做好处理, 客户应该增加了必要的流量订单.
								// 避免只拍押金的情况
								// 下面要应该检索该手机号码得到相关的订单 --> 押金宝贝只需要新建一个设备订单
								// 注意下面的押金使用 tradeOrder 的 payment 细单的实付金额, 不是
								// total_fee , 也不是总订单 payment
								Boolean isFailed = false;
								String customerName = messageMap.get(YouzanConfig.BUY_MESSAGE_TITLE_NAME);
								if (StringUtils.isBlank(customerName))
								{ // 增加容错性, 若未有留言使用人姓名, 使用手机客户人的姓名
									if (StringUtils.isNotBlank(cus.getCustomerName()))
									{
										customerName = cus.getCustomerName();
									}
									else
									{
										customerName = "";
									}
								}
								OrdersInfo ordersInfo = createOrderInfo(adminUserInfo, tradeDetail.getTid(), tradeOrder.getOid(), customerName, cus.getCustomerID(), tradeDetail.getRemarkString() + tradeOrder.getBuyerMessageString(), tradeDetail.getCreated(), 0, i);
								if (null != ordersInfo && YouzanConfig.HANDELED_ORDER.equals(ordersInfo.getOrderID()))
								{
									// 重复处理的订单
									hasHandledCount++;
									continue;
								}
								else if (null != ordersInfo)
								{

									DeviceDealOrders dOrders = createDeviceDealOrders(adminUserInfo, tradeDetail.getTid(), tradeOrder.getOid(), customerName, cus.getCustomerID(), "租用", tradeOrder.getDevicePaymentPrice(), ordersInfo.getOrderID(), i);
									if (null != dOrders)
									{
										// 更新总订单相关状态
										if (null == updateOrdersInfo(adminUserInfo, ordersInfo.getOrderID(), 1, 0, dOrders.getDealAmount()))
										{
											// TODO: 更新总订单相关字段失败 应该严格地作好持久化记录
											// 之后在适当时候做好重试处理
											logger.info("code: XX, msg:更新总订单失败");
											isFailed = true;
										}
										else
										{
											okSyncCount++;
										}

									}
									else
									{
										// TODO: 创建设备订单失败 应该严格地作好持久化记录
										// 之后在适当时候做好重试处理
										// 待确定: 要不要清除刚刚新建的订单? --> 成功后更新订单信息,
										// 则失败则总订单系一个
										// 不可用订单, 所以可以忽略
										logger.info("code: XX, msg:创建设备订单失败");
										isFailed = true;
									}
								}
								else
								{
									// TODO: 创建总订单失败 应该严格地作好持久化记录 之后在适当时候做好重试处理
									logger.info("code: XX, msg:创建总订单失败");
									isFailed = true;
								}
								if (isFailed)
								{
									failedSyncCount++;
									tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "-" + i + "）添加设备订单失败 | ";
								}
								else
								{
									tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "-" + i + "）添加设备订单成功 | ";
								}

							} // for

						}
						else if (YouzanConfig.OUTER_ITEM_ID_DEVICE.equals(tradeOrder.getOuter_item_id()))
						{ // 购买设备
							// 注意要支持多个宝贝数量的情形 这样就要求订单ID还要加上一个序号
							Integer ordersNum = tradeOrder.getNum();
							for (int i = 0; i < ordersNum; i++)
							{

								okQueryYouzanTradesOrderCount++;

								// 购买的设备订单
								Boolean isFailed = false;
								String customerName = messageMap.get(YouzanConfig.BUY_MESSAGE_TITLE_NAME);
								if (StringUtils.isBlank(customerName))
								{ // 增加容错性, 若未有留言使用人姓名, 使用手机客户人的姓名
									if (StringUtils.isNotBlank(cus.getCustomerName()))
									{
										customerName = cus.getCustomerName();
									}
									else
									{
										customerName = "";
									}
								}
								OrdersInfo ordersInfo = createOrderInfo(adminUserInfo, tradeDetail.getTid(), tradeOrder.getOid(), customerName, cus.getCustomerID(), tradeDetail.getRemarkString() + tradeOrder.getBuyerMessageString(), tradeDetail.getCreated(), 0, i);
								if (null != ordersInfo && YouzanConfig.HANDELED_ORDER.equals(ordersInfo.getOrderID()))
								{
									// 重复处理的订单
									hasHandledCount++;
									continue;
								}
								else if (null != ordersInfo)
								{
									DeviceDealOrders dOrders = createDeviceDealOrders(adminUserInfo, tradeDetail.getTid(), tradeOrder.getOid(), customerName, cus.getCustomerID(), "购买", tradeOrder.getDevicePaymentPrice(), ordersInfo.getOrderID(), i);
									if (null != dOrders)
									{
										// 更新总订单相关状态
										if (null == updateOrdersInfo(adminUserInfo, ordersInfo.getOrderID(), 1, 0, dOrders.getDealAmount()))
										{
											// TODO: 更新总订单相关字段失败 应该严格地作好持久化记录
											// 之后在适当时候做好重试处理
											logger.info("code: XX, msg:更新总订单失败");
											isFailed = true;
										}
										else
										{
											okSyncCount++;
										}

									}
									else
									{
										// TODO: 创建设备订单失败 应该严格地作好持久化记录
										// 之后在适当时候做好重试处理
										// 待确定: 要不要清除刚刚新建的订单? --> 成功后更新订单信息,
										// 则失败则总订单系一个
										// 不可用订单, 所以可以忽略
										logger.info("code: XX, msg:创建设备订单失败");
										isFailed = true;
									}
								}
								else
								{
									// TODO: 创建总订单失败 应该严格地作好持久化记录 之后在适当时候做好重试处理
									logger.info("code: XX, msg:创建总订单失败");
									isFailed = true;
								}
								if (isFailed)
								{
									failedSyncCount++;
									tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "-" + i + "）添加设备订单失败 | ";
								}
								else
								{
									tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "-" + i + "）添加设备订单成功 | ";
								}

							} // for
						}
						else if (tradeOrder.getOuter_item_id().startsWith(YouzanConfig.OUTER_ITEM_ID_WIFI))
						{ // 流量宝贝

							okQueryYouzanTradesOrderCount++;

							// TODO: 考虑查询此客户是否已有的可用流量订单中是否与这个有时间的重叠/冲突, 若有,
							// 必须妥善处理
							// 能否确定一个系合并到有效天数增加这种原则?

							// YouzanConfig.OUTER_ITEM_ID_WIFI.equals(tradeOrder.getOuter_item_id())
							Boolean isFailed = false;
							String customerName = messageMap.get(YouzanConfig.BUY_MESSAGE_TITLE_NAME);
							if (StringUtils.isBlank(customerName))
							{ // 增加容错性, 若未有留言使用人姓名, 使用手机客户人的姓名
								if (StringUtils.isNotBlank(cus.getCustomerName()))
								{
									customerName = cus.getCustomerName();
								}
								else
								{
									customerName = "";
								}
							}
							OrdersInfo ordersInfo = createOrderInfo(adminUserInfo, tradeDetail.getTid(), tradeOrder.getOid(), customerName, cus.getCustomerID(), tradeDetail.getRemarkString() + tradeOrder.getBuyerMessageString(), tradeDetail.getCreated(), 0, 0); // forOrderType
																																																																		// 1
																																																																		// 按流量宝贝目前为实物的标准,
																																																																		// 也要管理发货状态
							if (null != ordersInfo && YouzanConfig.HANDELED_ORDER.equals(ordersInfo.getOrderID()))
							{
								// 重复处理的订单
								hasHandledCount++;
								continue;
							}
							else if (null != ordersInfo)
							{
								FlowDealOrders fOrders = createFlowDealOrders(adminUserInfo, tradeDetail.getTid(), tradeOrder.getOid(), cus.getCustomerID(), customerName, ordersInfo.getOrderID(), tradeOrder.getOuter_item_id(), tradeOrder.getPayment(), tradeOrder.getNum(), messageMap, countries, 0);
								if (null != fOrders)
								{
									// 更新总订单相关状态
									if (null == updateOrdersInfo(adminUserInfo, ordersInfo.getOrderID(), 0, 1, fOrders.getOrderAmount()))
									{
										// TODO: 更新总订单相关字段失败 应该严格地作好持久化记录
										// 之后在适当时候做好重试处理
										logger.info("code: XX, msg:更新总订单失败");
										isFailed = true;
									}
									else
									{
										okSyncCount++;
									}
								}
								else
								{
									// TODO: 创建流量订单失败 应该严格地作好持久化记录 之后在适当时候做好重试处理
									// 待确定: 要不要清除刚刚新建的订单? --> 成功后更新订单信息,
									// 若失败则总订单系一个
									// 不可用订单, 所以可以忽略
									logger.info("code: XX, msg:创建流量订单失败");
									isFailed = true;
								}
								//创建总订单成功；发邮件
								List<DeviceDealOrders> dev = null;
								try {
									dev = deviceDealOrdersSer.getGWOrdData(ordersInfo.getOrderID());
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String devicecount="0";String dealtype="无设备订单！";
								if(dev.size()>0){
									 devicecount=dev.size()+"";
									 dealtype=dev.get(0).getDeallType();
								}
								try {
									String content = "[官网已付款订单]<br/><p>订单号：<b>"+ordersInfo.getOrderID()+"</b> &nbsp;&nbsp;&nbsp;&nbsp;金额："+ordersInfo.getOrderAmount()+"(支付方式:["+ordersInfo.getPaymentType()+"]，实付金额："+ordersInfo.getPaymoney().substring(0,ordersInfo.getPaymoney().indexOf(".")+2)+")"
									+"</p>"+
									"<p>客户姓名："+ordersInfo.getCustomerName()+"&nbsp;&nbsp;&nbsp;&nbsp;收货地址："+ordersInfo.getAddress()+"</p>"+
									"<p><b>订单明细：</b></p>"+
									"<p>设备：设备交易数量【 "+devicecount+" 】；交易方式："+dealtype+"</p>"+
									"<p>流量：使用国家【"+fOrders.getUserCountry()+"】；使用天数【"+fOrders.getFlowDays()+"】;订单开始时间："+fOrders.getPanlUserDate()+"</p>";
									EmailControl.sendAndCc(content);
								} catch (Exception e) {
									e.printStackTrace();
								}
								//发邮件结束
							}
							else
							{
								// TODO: 创建总订单失败 应该严格地作好持久化记录 之后在适当时候做好重试处理
								logger.info("code: XX, msg:创建总订单失败");
								isFailed = true;
							}
							if (isFailed)
							{
								failedSyncCount++;
								tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "）添加流量订单失败 | ";
							}
							else
							{
								tradeMemo += "（" + tradeOrder.getTitle() + tradeOrder.getOid() + "）添加流量订单成功 | ";
							}

						}
						else if (YouzanConfig.OUTER_ITEM_ID_SELF.equals(tradeOrder.getOuter_item_id()))
						{ // 深圳自取 大陆使用 宝贝
							// 亦可能有赞管理人员不会标星
							// TODO: 务必完成自取的订单的情况, 自取用户一样要提供联系手机/姓名/出发日期/预约时间

							otherTypeCount++; // 待确认这个是否要其他类之中
							// tradeMemo += ??;
						}
						else if (YouzanConfig.OUTER_ITEM_ID_OTHER_SAMPLE.equals(tradeOrder.getOuter_item_id()))
						{ // 若有特殊宝贝上面要注明并处理
							// TODO: 其他类型宝贝也应该分好类, 设置好"商品编码"
							otherTypeCount++; // 暂时
							// tradeMemo += ??;
						}
						else
						{
							// TODO: 其他类型宝贝作好处理
							otherTypeCount++;
							// tradeMemo += ??;
						}
					}

				}
				// else {
				// // 应该肯定有内容
				// // jsonResult.put("code", -4);
				// // jsonResult.put("msg", "抱歉, 系统出错, 请重试! 无效有赞订单数据");
				// // response.getWriter().println(jsonResult.toString());
				// // return;
				// continue; // 留待下次手动更新 并考虑做好记录 或者重新根据SID再获取一次
				// }

			} // if (tradeDetail.getSeller_flag() == 5)
			else if (tradeDetail.getSeller_flag() == 3)
			{
				okQueryYouzanTradesDetailCount++;

				// TODO: 3星代表取消订单
				// 情况：因为设备订单和流量订单都对应一个总订单，实现拆单发货后，设备发货、流量激活都能够使对应总订单的
				// 发货状态设为“已发货”，所以，只检索总订单的发货状态基本可以确定整个有赞订单的处理。注意，拆单发货后，
				// 有赞订单管理时也能及时看到单个宝贝的发货情况，所以有赞订单管理人员在决定标3个星前应该清楚情况，一般
				// 只有全部设备未发货，流量未发货（对应运营后台未激活）的才直接标3星，其他情况要按情况实际处理。这样的话，
				// 这里的自动处理也简单化，决定，一旦被标三个星，就设置相应的订单已取消，并务必把订单的实际情况在有需要时
				// 再反馈（通过卖家备注）到有赞后台管理订单。
				// 设定：只有在都是“未发货”状态的总订单情况下，以下操作：流量订单状态设为”不可用“，
				// 设备订单设为”不可用“，总订单设为”已取消“。一旦有任一个设备或订单显示为“已发货”，反馈一条信息到
				// 有赞后台订单的卖家备注里（设星？暂不）

				// 查询全部此订单下的所以总订单
				OrdersInfo queryInfo = new OrdersInfo();
				// 注意只系匹配前面同一购物车订单ID的公共部分! 需要添加 youzanFilter
				String orderIdPrefix = YouzanConfig.YOUZAN_ORDER_ID_PREFIX + tradeDetail.getTid();
				queryInfo.setOrderID(orderIdPrefix);
				queryInfo.setYouzanFilter("all");
				queryInfo.setSysStausAdmin(true); // 把已删除的查询
				// ordersInfo.setOrderStatus("已发货"); //--> 查询出全部然后遍历更好控制更方便使用
				List<OrdersInfo> orders = getOrdersInfoByFilter(queryInfo);
				if (null == orders || orders.size() == 0)
				{
					// 这些订单都未入库 暂反馈一条信息给有赞，不再任何处理 除非双方协定作其他处理，如新增，拼可把标星状态
					// 同步回有赞
					// --> TODO:
					// 也有可能是删除了,因为getOrdersInfoByFilter过滤掉了sysStatus=0的订单, 建议前面

					star3IgnoredCount++;

					logger.info("标3星此订单未同步到后台,应该先标5星处理 tid=" + tradeDetail.getTid());
					tradeMemo += " 标3星此订单未同步到后台,应该先标5星处理！";
				}
				else
				{
					Boolean foundShippedOrder = false;
					// Boolean isAllDeleted = true; // --> 删除无关处理,继续处理
					Boolean isAllCancelled = true;
					for (OrdersInfo item : orders)
					{
						okQueryYouzanTradesOrderCount++;

						// if (item.isSysStatus()) { // --> 删除无关处理,继续处理
						// isAllDeleted = false;
						// }
						if (!"已取消".equals(item.getOrderStatus()))
						{
							isAllCancelled = false;
						}
						if ("已发货".equals(item.getShipmentsStatus()))
						{
							foundShippedOrder = true;
							break;
						}

					}
					if (isAllCancelled)
					{
						star3IgnoredCount++;
						// 这个不需要任何留言
					}
					else if (foundShippedOrder)
					{
						// 拒绝处理, 并反馈给有赞
						star3IgnoredCount++;
						tradeMemo += " 标3星此订单有已发货商品,需沟通处理！";
					}
					else
					{
						// 相应改状态
						// if (isAllDeleted) { // 若全部已删除, 直接 --> 删除无关处理,继续处理
						//
						// }
						OrdersInfo updateInfo = new OrdersInfo();
						updateInfo.setOrderID(orderIdPrefix);
						updateInfo.setYouzanFilter("all"); // 使用上好统一格式处理
						if (cancelYouzanStar3Order(updateInfo))
						{
							star3OkCanceledCount++;
							tradeMemo += " 标3星此订单已成功取消,请跟进处理！";
						}
						else
						{
							star3FailedCancelledCount++;
							tradeMemo += " 标3星此订单需要取消,但处理失败,请联系后台管理员跟进！";
						}
					}
				}

			} // if (tradeDetail.getSeller_flag() == 3)

			// 按情况修改订单备注
			logger.info("tid: " + tradeDetail.getTid() + " memo: " + tradeMemo);
			if (StringUtils.isNotBlank(tradeMemo))
			{
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("tid", tradeDetail.getTid());
				// 注意要保留原来的留言 ！因为字数的限制，所以把最新的放到前面
				params.put("memo", tradeMemo + " " + tradeDetail.getTrade_memo());
				KDTApi.tradeMemoUpdate(params); // BaseResultWrapper<TradeMemoUpdateResult>
												// wrapper =
				// 暂不处理成功/失败
			}
		}

		// (5) 反馈结果给前端
		String outputString = "成功同步有赞订单结束:同步时段: " + lastSyncTime + " - " + nowSyncTime + " <br /><br />查询到有赞新处理总订单数: " + okQueryYouzanTradesDetailCount + " <br />总订单其下的流量/设备/押金等订单数统计: " + okQueryYouzanTradesOrderCount + " <br /><br />其中标5星, 成功导入订单数: " + okSyncCount + " <br />导入失败订单数: " + failedSyncCount + " <br />缺少手机号码的订单数: " + missingPhoneCount // 缺少手机号码或创建新手机客户失败的订单数
				+ " <br />发现已处理过的重复订单数: " + hasHandledCount;
		if (star3OkCanceledCount > 0 || star3IgnoredCount > 0 || star3FailedCancelledCount > 0)
		{
			outputString += " <br /><br />其中有标3星, 成功取消订单数: " + star3OkCanceledCount + " <br />但取消操作失败的订单数: " + star3FailedCancelledCount + " <br />已取消故忽略或反馈不处理的订单数: " + star3IgnoredCount;
		}
		else
		{
			outputString += " <br /><br />其中没有标3星订单";
		}
		outputString += " <br /><br />总订单其下其他类型的宝贝订单数: " + otherTypeCount;

		if (okSyncCount > 0)
		{
			result.setErrorCode(0); // 前端应该刷新列表
		}
		else
		{
			result.setErrorCode(2); // 前端无需刷新列表
		}
		// TODO: 可考虑设置结果里的各数量 setXXXXXCount()...
		result.setMsg(outputString);
		logger.info(outputString);

		return result;
	}



	/**
	 * 添加新客户
	 *
	 * @param name
	 * @param phone
	 * @param Address
	 * @return
	 */
	private CustomerInfo createCustomerInfo(AdminUserInfo adminUserInfo, String name, String phone, String address)
	{

		// 新客户 参考: {@link OrdersInfoControl#gotwo()}
		CustomerInfo info = new CustomerInfo();
		info.setCustomerID(UUID.randomUUID().toString());
		info.setCustomerName(name);
		info.setAddress(address);
		info.setPhone(phone);

		try
		{
			String pwd = DES.toHexString(DES.encrypt(Constants.DEFAULT_CUSTOMER_PWD, Constants.DES_KEYWEB));
			info.setPassword(pwd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("login:DES处理错误");
			info.setPassword("22a9e954c05e440cd251b6858bb5411c"); // 暂硬编码
		}

		info.setRemark(""); // 待处理备注
		info.setEmail("");
		info.setDistributorID("");
		info.setDistributorName("");

		info.setCustomerSource("有赞");
		info.setUsername("User" + StringUtils.right(info.getCustomerID(), 10)); // 同官网一致

		// 若系定时任务, 这个应该为空
		// AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
		// .getAttribute("User");
		if (adminUserInfo != null)
		{
			info.setCreatorUserID(adminUserInfo.getUserID());
		}
		info.setCreatorDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		info.setSysStatus(1);
		int temp = 0;
		try
		{
			temp = customerInfoDao.insertCustomerInfo(info);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		if (temp > 0)
		{
			return info;
		}

		return null;
	}



	/**
	 * 创建新设备单
	 *
	 * 注意导入的有赞订单的设备订单ID直接使用TradeDetail的tid + "-" + TradeOrder的oid,
	 * 流量订单ID的与设备订单ID相同, 而总订单OrdersInfo的ID则在前面添加前缀"YZ": "YZ"+tid+"-"+oid
	 *
	 * @param tid
	 * @param oid
	 * @param customerName
	 * @param customerId
	 * @param dealType
	 * @param dealAmount
	 *            // 注意使用正确的金额. 目前觉得正确的应该系TradeOrder的实付金额除以数量,
	 *            可能比TradeOrder的单价号, 更不能错误使用 TradeOrder 的实付金额(它包含了全部)
	 * @param orderID
	 * @param index
	 *            同一个宝贝的数量按0开始到num-1, 这个值也要放到订单ID里, 每一个数量单独创建订单
	 * @return
	 */
	private DeviceDealOrders createDeviceDealOrders(AdminUserInfo adminUserInfo, String tid, Integer oid, String customerName, String customerId, String dealType, Double dealAmount, String orderID, Integer index)
	{

		DeviceDealOrders deviceDealOrders = new DeviceDealOrders();
		deviceDealOrders.setCustomerName(customerName);
		deviceDealOrders.setCustomerID(customerId);
		deviceDealOrders.setDeviceDealID(combineFlowOrDealOrderId(tid, oid, index)); // getUUID()
																						// //
																						// tid
																						// +
																						// "-"
																						// +
																						// oid
		deviceDealOrders.setOrderID(orderID);
		deviceDealOrders.setSN("");
		deviceDealOrders.setDeviceID("");
		deviceDealOrders.setDeallType(dealType);
		deviceDealOrders.setDealAmount(dealAmount);
		deviceDealOrders.setIfFinish("是");
		// if ("租用".equals(dealType)) { // 目的系在设备列表中购买的设置不显示归还状态 -->
		// 但可能会影响到后续的业务判断 现去掉
		deviceDealOrders.setIfReturn("否");
		// }
		deviceDealOrders.setOrderStatus("不可用");
		deviceDealOrders.setDeviceStatus("不可用"); // 可能这个根本没用过?
		deviceDealOrders.setReturnDate(null);
		deviceDealOrders.setSysStatus(true);
		// AdminUserInfo adminUserInfo = (AdminUserInfo)
		// getSession().getAttribute("User");
		if (adminUserInfo != null)
		{
			deviceDealOrders.setCreatorUserID(adminUserInfo.getUserID());
			deviceDealOrders.setCreatorUserName(adminUserInfo.getUserName());
		}

		try
		{
			int temp = deviceDealOrdersDao.insertinfo(deviceDealOrders);
			if (temp > 0)
			{
				logger.info("添加设备订单成功");
				return deviceDealOrders;
			}
			else
			{
				logger.info("添加设备订单失败");
			}
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}



	/**
	 * 创建流量订单
	 *
	 * 注意导入的有赞订单的设备订单ID直接使用TradeDetail的tid + "-" + TradeOrder的oid,
	 * 流量订单ID的与设备订单ID相同, 而总订单OrdersInfo的ID则在前面添加前缀"YZ": "YZ"+tid+"-"+oid
	 *
	 * TO DO: 对于不全格式的预约使用日期和可使用国家, 怎样处理? 暂不考虑, 确认商品编辑正确, 测试订单及时清理
	 *
	 * @param tid
	 * @param oid
	 * @param customerId
	 * @param orderID
	 * @param outer_item_id
	 *            流量宝贝中的商品编码, 形如: 流量-202-204-206-208-214-222-228-232-238 含可用国家信息
	 * @param orderAmount
	 * @param num
	 *            WIFI宝贝中的下单数量
	 * @param messageMap
	 * @param countries
	 *            全部国家列表值, 以方便获取 userCountryList... 优化系使用缓存的字典值
	 * @param index
	 *            流量订单总只系一个, 所以应该输入总为0即可
	 * @return
	 */
	private FlowDealOrders createFlowDealOrders(AdminUserInfo adminUserInfo, String tid, Integer oid, String customerId, String customerName, String orderID, String outer_item_id, Double orderAmount, Integer num, Map<String, String> messageMap, List<CountryInfo> countries, Integer index)
	{

		FlowDealOrders flow = new FlowDealOrders();
		flow.setFlowDealID(combineFlowOrDealOrderId(tid, oid, index)); // getUUID()
																		// //
																		// tid +
																		// "-" +
																		// oid
		flow.setOrderID(orderID);
		flow.setCustomerID(customerId);
		flow.setCustomerName(customerName);
		flow.setIfFinish("是");
		flow.setIfActivate("否");
		flow.setOrderStatus("不可用");
		flow.setOrderType("按时间");
		flow.setLimitSpeed(Constants.LIMITSPEED);
		flow.setLimitValve(Constants.LIMITVALUE);
		flow.setOrderAmount(orderAmount);
		// flow.setJourney();

		flow.setOrderCreateDate(new Date());
		// AdminUserInfo adminUserInfo = (AdminUserInfo)
		// getSession().getAttribute("User");
		if (adminUserInfo != null)
		{
			flow.setCreatorUserID(adminUserInfo.getUserID());
			flow.setCreatorUserName(adminUserInfo.getUserName());
		}

		// 应该使用单价数量而不是客户指定的天数, 但有赞运营人员标星前值得先处理好可能客户下单数量不够承担所希望的使用天数的情形
		// TODO: 与运营沟通解决
		// Integer days = 0;
		// try {
		// days =
		// Integer.valueOf(messageMap.get(YouzanConfig.BUY_MESSAGE_TITLE_DAYS));
		// } catch (NumberFormatException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		flow.setFlowDays(num);
		flow.setDaysRemaining(num);

		String remark = "";

		// 目前有赞只能支持日期类型, 若使用文本又难以控制格式
		String useDateString = messageMap.get(YouzanConfig.BUY_MESSAGE_TITLE_DATE);
		if (StringUtils.isBlank(useDateString))
		{
			// 日期不合法, 应该继续吗?
			remark += "有赞订单:预约使用时间格式不符, 请联系有赞管理人员, 可能此订单的商品编辑未符合要求.";
		}
		else
		{
			Date planUseDate = DateUtils.parseDate(useDateString);
			if (null == planUseDate)
			{
				// 日期不合法, 应该继续吗?
				remark += "有赞订单:预约使用时间格式不符, 请联系有赞管理人员, 可能此订单的商品编辑未符合要求 ";
			}
			else
			{
				flow.setPanlUserDate(DateUtils.DateToStr(planUseDate));
				flow.setFlowExpireDate(DateUtils.beforeNDateToString(planUseDate, num, "yyyy-MM-dd HH:mm:ss"));
			}
		}

		outer_item_id = StringUtils.substringAfter(outer_item_id, YouzanConfig.OUTER_ITEM_ID_WIFI);
		// String[] uc = StringUtils.split(outer_item_id, "-");
		String mcc = StringUtils.replace(outer_item_id, "-", ",");
		String useCountryList = CountryUtils.getCountryStringFromMCCList(mcc, countries);
		if (StringUtils.isNotBlank(useCountryList))
		{
			flow.setUserCountry(useCountryList);
		}
		else
		{
			// 可用国家不合法, 是否继续?
			remark += "有赞订单:此流量订单可使用国家列表为空, 请联系有赞管理人员, 可能此订单的商品编辑未符合要求 ";
		}

		flow.setRemark(remark);

		try
		{
			int temp = flowDealOrdersDao.insertinfo(flow);
			if (temp > 0)
			{
				logger.info("添加流量订单成功");
				return flow;
			}
			else
			{
				logger.info("添加流量订单失败");
			}
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}



	/**
	 * 创建总订单
	 *
	 * 注意输入备注, 使用有赞总订单的卖家备注(trade_memo),
	 * 买家购买附言(buyer_message)和细单中的买家的留言(buyer_messages)
	 * 注意这个字段的数据库表中的大小应该由原来的100改大一点例如512
	 *
	 * 注意导入的有赞订单的设备订单ID直接使用TradeDetail的tid + "-" + TradeOrder的oid,
	 * 流量订单ID的与设备订单ID相同, 而总订单OrdersInfo的ID则在前面添加前缀"YZ": "YZ"+tid+"-"+oid
	 *
	 * @param tid
	 * @param oid
	 * @param customerName
	 * @param customerId
	 * @param remark
	 * @param created
	 * @param forOrderType
	 *            值为0, 则为设备创建订单; 值为1, 则为流量创建订单. 原因系要区分发货状态字段使用未发货还是留空白,
	 *            从而避免流量订单发货状态引起误解 ---> 目前流量宝贝也作为实物宝贝, 所以选择在激活时作为流量类型的发货时刻,
	 *            所以现在流量也使用值0去设定发货状态了
	 * @param index
	 *            同一个宝贝的数量按0开始到num-1, 这个值也要放到订单ID里, 每一个数量单独创建订单
	 * @return
	 */
	private OrdersInfo createOrderInfo(AdminUserInfo adminUserInfo, String tid, Integer oid, String customerName, String customerId, String remark, String created, Integer forOrderType, Integer index)
	{

		// TODO: 应该先检查此订单号是否已存在, 应付的情形可能有: (1)上次同步时某些订单在创建了总订单之后发生了错误, 这次
		// 同步时检测到并开始处理时, 这种情形若判断存在此订单号, 并检查此订单的相关字段, 若的确不是有效订单, 则可以直接
		// 返回查询到的订单信息; 若发现系有效订单, 则之后已处理过此有赞订单, 那应该忽略返回, 并作出反馈, 后面不应该当作
		// 失败的处理
		// TODO: 同时这里也可以考虑在前端添加一个开关, 在前面设定同步时段时, 允许设定某个时间的订单重新处理, 这里判断
		// 若存在, 则跳过, 并可统计计数, 最后反馈给前端. 前面的开关名称可考虑: 增量同步 重新扫描全部订单同步

		String orderID = combineOrderId(tid, oid, index);

		// 查询是否已存在 若存在则再判断是否为无效订单
		OrdersInfo queryInfo = new OrdersInfo();
		try
		{
			queryInfo = ordersInfoDao.getInfo(orderID);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
			return null; // TODO: 待确认这种情况是否可以继续
		}
		if (null != queryInfo)
		{
			if ("否".equals(queryInfo.getIfFinish()))
			{
				// 若系无效订单, 则可返回该订单, 继续后面处理
				return queryInfo;
			}
			else
			{
				queryInfo.setOrderID(YouzanConfig.HANDELED_ORDER);
				return queryInfo; // 应该想办法返回能区别的错误, 例如考虑在 queryInfo中临时更改某个唯一的字段
			}
		}

		OrdersInfo order = new OrdersInfo();
		// String uid = getUUID();
		order.setOrderID(orderID); // uid
		order.setFlowDealCount(0); // 后面更新
		order.setDeviceDealCount(0); // 后面更新
		order.setRemark(remark);
		order.setIfFinish("否"); // 后面更新
		// 后面 order.setOrderStatus("0");
		order.setOrderAmount(0.00); // 后面更新

		order.setOrderSource("有赞");
		order.setOrderStatus("未付款"); // 后面更新
		if (0 == forOrderType)
		{ // 只为设备订单设定明显的发货状态
			order.setShipmentsStatus("未发货"); // 经过发货操作才为已发货
		}

		order.setCustomerID(customerId);
		order.setCustomerName(customerName);

		// 同步到后台的有赞新订单因为创建时间使用了有赞总订单的创建时间 所以额外使用 modifyUserId/modifyUserDate
		// 等保存一下当前时间点 以供后面使用!
		// AdminUserInfo adminUserInfo = (AdminUserInfo)
		// getSession().getAttribute("User");
		order.setCreatorDate(DateUtils.StrToDate(created));
		if (adminUserInfo != null)
		{
			order.setCreatorUserID(adminUserInfo.getUserID());
			order.setCreatorUserName(adminUserInfo.getUserName());
			order.setModifyUserID(adminUserInfo.getUserID());
		}

		try
		{
			int temp = ordersInfoDao.insertinfoWithExtra(order);
			if (temp > 0)
			{
				logger.info("添加订单成功");
				return order;
			}
			else
			{
				logger.info("添加订单失败");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 成功完成订单的创建时更新状态
	 *
	 * @param info
	 * @return
	 */
	private OrdersInfo updateOrdersInfo(AdminUserInfo adminUserInfo, String orderID, int dCount, int fCount, double amount)
	{

		if (StringUtils.isBlank(orderID))
		{
			return null;
		}
		OrdersInfo updateInfo = new OrdersInfo();
		updateInfo.setOrderID(orderID);
		updateInfo.setDeviceDealCount(dCount);
		updateInfo.setFlowDealCount(fCount);
		updateInfo.setOrderAmount(amount);
		updateInfo.setIfFinish("是");
		updateInfo.setOrderStatus("已付款");

		// AdminUserInfo adminUserInfo = (AdminUserInfo)
		// getSession().getAttribute("User");
		if (adminUserInfo != null)
		{
			updateInfo.setModifyUserID(adminUserInfo.getUserID());
		}

		try
		{
			int temp = ordersInfoDao.updateiffinishMore(updateInfo);
			if (temp > 0)
			{
				logger.info("更新订单成功");
				return updateInfo;
			}
			else
			{
				logger.info("更新订单失败");
			}
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}



	/**
	 * 统一拼合总订单ID
	 *
	 * @param tid
	 * @param oid
	 * @param index
	 * @return
	 */
	public static String combineOrderId(String tid, Integer oid, Integer index)
	{

		return YouzanConfig.YOUZAN_ORDER_ID_PREFIX + tid + "-" + oid + "-" + index; // 严格可考虑调用
																					// combineFlowOrDealOrderId
																					// 取后面部分
	}



	/**
	 * 统一拼合流量订单或设备订单的ID, 这ID应该与其所在的总订单ID除YZ的后面部分相同
	 *
	 * @param tid
	 * @param oid
	 * @param index
	 * @return
	 */
	public static String combineFlowOrDealOrderId(String tid, Integer oid, Integer index)
	{

		return tid + "-" + oid + "-" + index;
	}



	/**
	 * 同步有赞订单的物流状态
	 *
	 * 只有整个购物车订单下面全部的设备和流量都已发货后, 才开始通过有赞的物流接口向有赞同步物流状态. (1)若存在设备, 则设定的物流公司和快递单号,
	 * 以最后发货的为准(-->发现之前并没记录发货时间, 所以任意选择一个); (2)若只有流量, 则没有物流公司和快递单号, 直接"卖家标记签收";
	 * (3)若"全部"设备系自提自取的话, 也系"卖家标记签收", 而"部分"则按第(1)种情况
	 *
	 * 注意: 这个方法不会更新/处理运营后台的发货状态, 一般情况下, 总假设在调用前，运营后台已成功处理了发货,
	 * 不管设备(在批量发货页面)还是流量(对于流量, 只有有赞的订单在激活时会更改对应的总订单的发货状态为已发货， 但没有物流信息)
	 *
	 * [updated 20151127] 改为拆单发货，即购买物车订单其下的宝贝单独发货，基本目的系为了押金的情况。押金宝贝不能
	 * 同步已发货状态到有赞订单那边，即，只能在运营后台设置为发货状态，在有赞那边系未发货状态。 参考：
	 * https://open.koudaitong.com/doc/api?method=kdt.logistics.online.confirm
	 *
	 * @param orderID
	 * @param dOrder
	 *            （目前未用）若系设备订单且已知设备订单则传入，否则传null，会使用orderID去查询获取；若为流量订单传入null
	 * @param fOrder
	 *            （目前未用）若系流量订单且已知流量订单则传入，否则传null，会使用orderID去查询获取；若为设备订单传入null
	 * @param logistics
	 *            调用时, 若系流量订单输入null
	 * @param expresssNO
	 *            调用时, 若系流量订单输入null
	 * @param sn
	 *            设备号 为有赞的订单时，前面的SN总只系一个
	 * @return
	 */
	public YouzanSyncLogisticsResult syncYouzanShipmentStatus(String orderID, DeviceDealOrders dOrder, FlowDealOrders fOrder, String logistics, String expresssNO, String sn)
	{

		logger.info("syncYouzanShipmentStatus: 同步物流状态到有赞开始, orderID=" + orderID + " logistics=" + logistics + " expresssNO=" + expresssNO);

		YouzanSyncLogisticsResult result = new YouzanSyncLogisticsResult();

		// 如果系有赞的订单, 则通过接口同步状态到有赞
		if (StringUtils.startsWith(orderID, YouzanConfig.YOUZAN_ORDER_ID_PREFIX))
		{
			String[] tid_oid;
			tid_oid = StringUtils.split(orderID.substring(2), "-"); // 去掉YZ
																	// tid-oid-number

			OrdersInfo ordersInfo = new OrdersInfo();
			// 注意只系匹配前面同一购物车订单ID的公共部分! 需要配合添加下面的 setYouzanFilter("all")
			// 现在只需要为照顾多个数量的设备或押金，带上oid，不需要最后的number序号
			ordersInfo.setOrderID(YouzanConfig.YOUZAN_ORDER_ID_PREFIX + tid_oid[0] + "-" + tid_oid[1]);
			ordersInfo.setYouzanFilter("all");
			List<OrdersInfo> orders = getOrdersInfoByFilter(ordersInfo);

			if (null == orders || orders.size() == 0)
			{
				result.setErrorCode(-10);
				result.setMsg("有赞平台订单: 流量订单激活成功，但需要同步状态到有赞平台时查询总订单失败，请备忘并联系有赞后台订单管理员处理");
				return result;
			}
			else
			{
				OrdersInfo firstOrder = orders.get(0);
				if (firstOrder.getDeviceDealCount() > 0)
				{
					// 设备的情形：押金宝贝则只在运营后台记录物流，不同步到有赞，只同步一个备注到有赞；
					// 若一个购物车一个购买的设备宝贝的数量超出两件，则在运营后台分为多个总订单和设备订单，
					// 那么，这种情况要全部已发货后才能同步到有赞设定为“已发货”

					// 如果系租赁的设备，不同步物流状态！改为增加一个备注到有赞
					if (null != dOrder && ("租用".equals(dOrder.getDeallType()) || "租赁".equals(dOrder.getDeallType())))
					{

						// if (!"已发货".equals(firstOrder.getShipmentsStatus())) {
						// // 请保证使用此条件判断, 因为考虑到发货状态有可能空
						// // 一般不会出现，因为外面已判断系发货成功再能执行本方法
						// result.setErrorCode(-13);
						// result.setMsg("有赞平台订单: 激活成功，但对流量订单开发未设定其对应的总订单发货状态为“已发货”。请沟通处理");
						// return result;
						// }

						String memo = "租用设备" + sn + "已发货:";
						if (StringUtils.isNotBlank(firstOrder.getLogisticsInfo()))
						{
							String[] stringList = firstOrder.getLogisticsInfo().split(",");
							if (stringList.length >= 1)
							{ // 2 特别要处理自提自取的情况
								String kdgs = stringList[0];
								List<Dictionary> dictionaries;
								try
								{
									dictionaries = dictonaryDao.getalertType("快递公司");
									for (Dictionary dictionary : dictionaries)
									{
										if (dictionary.getValue().equals(kdgs))
										{
											kdgs = dictionary.getLabel();
										}
									}
								}
								catch (Exception e)
								{
									logger.info("syncYouzanShipmentStatus: 获取快递公司出错");
									e.printStackTrace();
									dictionaries = null;
								}
								memo += kdgs + " ";
								if (stringList.length >= 2)
								{
									memo += "单号" + stringList[1] + " ";
								}
							}
						}

						BaseResultWrapper<TradeMemoUpdateResult> wrapper = KDTApi.tradeMemoUpdateWrapper(true, tid_oid[0], memo);
						if (null != wrapper && null != wrapper.getResponse())
						{
							result.setErrorCode(12);
							result.setMsg("有赞平台订单: 发货成功, 同时租用的设备订单增加了物流备注到有赞");
							return result;
						}
						else
						{
							result.setErrorCode(-15);
							result.setMsg("有赞平台订单: 发货成功, 但增加物流备注到有赞时失败，可通知相关人员留意一下");
							return result;
						}

					}

					Boolean needSync = true;
					Boolean isAllZitiziqu = true;
					String foundLogistics = "", foundExpressNum = "";
					for (OrdersInfo order : orders)
					{
						if (!"已发货".equals(order.getShipmentsStatus()))
						{ // 请保证使用此条件判断, 因为考虑到发货状态有可能空
							needSync = false; // 还有设备未发货，所以还不需要同步发货状态到有赞
						}
						else
						{
							if (StringUtils.isNotBlank(order.getLogisticsInfo()))
							{
								String[] name_no = order.getLogisticsInfo().split(",");
								if (name_no.length >= 2)
								{
									if (!"zitiziqu".equals(name_no[0]))
									{
										foundLogistics = name_no[0];
										foundExpressNum = name_no[1];
										isAllZitiziqu = false;
									}
								}
							}
						}
					}

					if (needSync)
					{ // 全部已发货，需要同步发货状态到有赞
						// 设定最终的物流
						Integer out_stype;
						if (isAllZitiziqu)
						{
							logistics = "zitiziqu";
						}
						else if (StringUtils.isNotBlank(logistics) && StringUtils.isNotBlank(expresssNO) && !"zitiziqu".equals(logistics))
						{
							// 若传进来的值有效的话就使用传进来的值
							// 此处不变
						}
						else if (StringUtils.isNotBlank(foundLogistics) && StringUtils.isNotBlank(foundExpressNum))
						{
							logistics = foundLogistics;
							expresssNO = foundExpressNum;
						}
						else
						{
							// 出错了, 设定出错了, !不应该直接使用卖家标记签收, 而应该提醒前端,
							// 去由有赞管理人员去手工处理
							logistics = "unknowLogistics";
						}

						out_stype = LogisticsUtils.PY2YZ_EXPRESS.get(logistics);

						if (null == out_stype)
						{
							// 开发/管理人员未处理好快递公司信息 请参考此处去处理:
							// LogisticsUtils.PY2YZ_EXPRESS
							// 这里返回具体的错误代码但要知道物流信息已录入订单, 但未同步到有赞
							result.setErrorCode(-2);
							result.setMsg("有赞平台订单: 发货信息已保存, 但开发/管理人员未处理好此快递公司信息, 所以未能同步到有赞平台");
							return result;
						}

						if (out_stype == 0)
						{ // "zitiziqu".equals(logistics) // 全部为自提自取，不需要物流
							// （注意之前“应该执行卖家标记签收”系错误的）
							HashMap<String, String> params = new HashMap<String, String>();
							params.put("is_no_express", "0");
							params.put("tid", tid_oid[0]); // 有赞同步后的设备订单ID格式为tid-oid
							params.put("oids", tid_oid[1]);
							params.put("is_no_express", 1 + ""); // 流量无需物流
							// params.put("out_stype","" + out_stype);
							// params.put("out_sid", expresssNO);
							ShippingResultWrapper wrapper = KDTApi.logisticsOnlineConfirm(params);
							if (null != wrapper && wrapper.isSuccess() && wrapper.getResponse().getShipping().getIs_success())
							{
								result.setErrorCode(10); // 有赞订单物流同步成功
								result.setMsg("有赞平台订单: 拆单发货成功, 同时成功同步发货状态到有赞平台");
								return result;
							}
							else
							{
								String outputString = "有赞平台订单: 发货信息已保存, 但同步发货状态到有赞平台失败, 请联系有赞管理人员手动去有赞后台处理发货状态" + "<br />error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg(); // -3
																																																											// //
																																																											// 有赞订单物流失败
								logger.info("error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg());

								result.setErrorCode(-3);
								result.setMsg(outputString);
								return result;
							}

						}
						else
						{ // 以前面找到的其中一个物流去同步发货物流到有赞
							HashMap<String, String> params = new HashMap<String, String>();
							params.put("is_no_express", "0");
							params.put("tid", tid_oid[0]); // 有赞同步后的设备订单ID格式为tid-oid
							params.put("oids", tid_oid[1]);
							params.put("out_stype", "" + out_stype);
							params.put("out_sid", expresssNO);
							ShippingResultWrapper wrapper = KDTApi.logisticsOnlineConfirm(params);
							if (null != wrapper && wrapper.isSuccess() && wrapper.getResponse().getShipping().getIs_success())
							{
								result.setErrorCode(10); // 有赞订单物流同步成功
								result.setMsg("有赞平台订单: 发货成功, 同时成功同步发货状态到有赞平台");
								return result;
							}
							else
							{
								String outputString = "有赞平台订单: 发货信息已保存, 但同步发货状态到有赞平台失败, 请联系有赞管理人员手动去有赞后台处理发货状态" + "<br />error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg(); // -3
																																																											// //
																																																											// 有赞订单物流失败
								logger.info("error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg());

								result.setErrorCode(-3); // 有赞订单物流同步成功
								result.setMsg(outputString);
								return result;
							}
						}

					}
					else
					{
						// 表示系有赞订单, 但这个订单所在的购物车的流量或设备订单未全部发货完毕. 只有发货完毕才去同步有赞
						// 路过即可
						result.setErrorCode(11);
						result.setMsg("有赞平台订单: 发货成功, 但此有赞购物车订单下仍有未发货的宝贝所以未同步物流状态到有赞");
						return result;
					}

				}
				else if (firstOrder.getFlowDealCount() > 0)
				{
					// 流量的情形：一个宝贝无论多少个数量都系一个流量订单，所以直接可以确定是否同步到有赞那边
					if (!"已发货".equals(firstOrder.getShipmentsStatus()))
					{ // 请保证使用此条件判断, 因为考虑到发货状态有可能空
						// 一般不会出现，因为外面已判断系发货成功再能执行本方法

						result.setErrorCode(-13);
						result.setMsg("有赞平台订单: 激活成功，但对流量订单开发未设定其对应的总订单发货状态为“已发货”。请沟通处理");
						return result;
					}
					else
					{
						// 流量订单拆单发货，不需要物流

						HashMap<String, String> params = new HashMap<String, String>();
						params.put("is_no_express", "0");
						params.put("tid", tid_oid[0]); // 有赞同步后的设备订单ID格式为tid-oid
						params.put("oids", tid_oid[1]);
						params.put("is_no_express", 1 + ""); // 流量无需物流
						// params.put("out_stype","" + out_stype);
						// params.put("out_sid", expresssNO);
						ShippingResultWrapper wrapper = KDTApi.logisticsOnlineConfirm(params);
						if (null != wrapper && wrapper.isSuccess() && wrapper.getResponse().getShipping().getIs_success())
						{
							result.setErrorCode(10); // 有赞订单物流同步成功
							result.setMsg("有赞平台订单: 拆单发货成功, 同时成功同步发货状态到有赞平台");
							return result;
						}
						else
						{
							String outputString = "有赞平台订单: 发货信息已保存, 但同步发货状态到有赞平台失败, 请联系有赞管理人员手动去有赞后台处理发货状态" + "<br />error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg(); // -3
																																																										// //
																																																										// 有赞订单物流失败
							logger.info("error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg());

							result.setErrorCode(-3);
							result.setMsg(outputString);
							return result;
						}
					}
				}
			}

			result.setErrorCode(-14);
			result.setMsg("有赞平台订单: 流量订单激活成功，但需要同步状态到有赞平台时发生未知错误，请备忘并联系有赞后台订单管理员处理");
			return result;

		}
		else
		{

			// 表示不是有赞订单, 提示跳过
			result.setErrorCode(0);
			// result.setMsg("");
			return result;
		}

	}



	/**
	 * 保留一份之前非拆单发货情况的处理
	 *
	 * @param orderID
	 * @param subID
	 * @param logistics
	 * @param expresssNO
	 * @return
	 */
	public YouzanSyncLogisticsResult syncYouzanShipmentStatusTotal(String orderID, String subID, String logistics, String expresssNO)
	{

		YouzanSyncLogisticsResult result = new YouzanSyncLogisticsResult();

		// 如果系有赞的订单, 则通过接口同步状态到有赞
		if (StringUtils.startsWith(orderID, YouzanConfig.YOUZAN_ORDER_ID_PREFIX))
		{
			String[] tid_oid;
			if (StringUtils.isBlank(subID))
			{
				subID = orderID.substring(2); // 去掉YZ
			}
			tid_oid = StringUtils.split(subID, "-");

			// TODO: 先判断总订单下是否全部已发货 注意,
			// 所有有赞订单的流量和设备对应的总订单一定填"发货状态"shippmentStatus
			// 首先要判断这个订单所在的购物车订单的全部设备和全部流量是否都已发货!
			// 以下为查询同一有赞购物车订单对应的全部总订单, 包含全部流量/设备宝贝对应的
			OrdersInfo ordersInfo = new OrdersInfo();
			// 注意只系匹配前面同一购物车订单ID的公共部分! 需要添加 youzanFilter
			ordersInfo.setOrderID(YouzanConfig.YOUZAN_ORDER_ID_PREFIX + tid_oid[0]);
			ordersInfo.setYouzanFilter("all");
			// ordersInfo.setOrderStatus("已发货"); //--> 查询出全部然后遍历更好控制更方便使用
			List<OrdersInfo> orders = getOrdersInfoByFilter(ordersInfo);

			Boolean needSync = true;
			Boolean isAllZitiziqu = true;
			String foundLogistics = "", foundExpressNum = "";
			int fOrderCount = 0;
			int dOrderCount = 0;
			if (null == orders || orders.size() == 0)
			{
				// TODO: 出错了返回结果
				// needSyncBoolean = false;
				// isAllZitiziqu = false;

				result.setErrorCode(-10);
				result.setMsg("出错了, 查询总订单失败");
				return result;
			}
			else
			{
				for (OrdersInfo order : orders)
				{
					if (!"已发货".equals(order.getShipmentsStatus()))
					{ // 请保证使用此条件判断, 因为考虑到发货状态有可能空
						needSync = false;
						// 提醒这里不需要统计设备和流量的数量, 因为一旦有未发货, 后面不会引用这两个数量
					}
					else
					{
						if (order.getDeviceDealCount() > 0)
						{
							if (StringUtils.isNotBlank(order.getLogisticsInfo()))
							{
								String[] name_no = order.getLogisticsInfo().split(",");
								if (name_no.length >= 2)
								{
									if (!"zitiziqu".equals(name_no[0]))
									{
										foundLogistics = name_no[0];
										foundExpressNum = name_no[1];
										isAllZitiziqu = false;
									}
								}
							}
							else
							{
								isAllZitiziqu = false;
							}
							dOrderCount++;
						}
						else
						{
							fOrderCount++;
						}

					}
				}
			}

			if (needSync)
			{
				// 设定最终的物流
				Integer out_stype;
				if (dOrderCount == 0)
				{ // 只有流量订单
					out_stype = 0;
				}
				else
				{
					if (isAllZitiziqu)
					{
						logistics = "zitiziqu";
					}
					else if (StringUtils.isNotBlank(logistics) && StringUtils.isNotBlank(expresssNO) && !"zitiziqu".equals(logistics))
					{
						// 若传进来的值有效的话就使用传进来的值
						// 此处不变
					}
					else if (StringUtils.isNotBlank(foundLogistics) && StringUtils.isNotBlank(foundExpressNum))
					{
						logistics = foundLogistics;
						expresssNO = foundExpressNum;
					}
					else
					{
						// 出错了, 设定出错了, !不应该直接使用卖家标记签收, 而应该提醒前端, 去由有赞管理人员去手工处理
						logistics = "unknowLogistics";
					}

					out_stype = LogisticsUtils.PY2YZ_EXPRESS.get(logistics);

					if (null == out_stype)
					{
						// 开发/管理人员未处理好快递公司信息 请参考此处去处理:
						// LogisticsUtils.PY2YZ_EXPRESS
						// 这里返回具体的错误代码但要知道物流信息已录入订单, 但未同步到有赞
						result.setErrorCode(-2);
						result.setMsg("有赞平台订单: 发货信息已保存, 但开发/管理人员未处理好此快递公司信息, 所以未能同步到有赞平台");
						return result;
					}
				}

				if (out_stype == 0)
				{ // "zitiziqu".equals(logistics) // 自提自取应该执行卖家标记签收
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("tid", tid_oid[0]); // 有赞同步后的设备订单ID格式为tid-oid
					BaseResultWrapper<IsSuccessResponse> wrapper = KDTApi.logisticsOnlineMarksign(params);
					if (null != wrapper && wrapper.isSuccess() && ((IsSuccessResponse) wrapper.getResponse()).getIs_success())
					{
						result.setErrorCode(10); // 有赞订单物流同步成功
						result.setMsg("有赞平台订单: 发货成功, 同时成功同步到有赞平台");
						return result;
					}
					else
					{
						String outputString = "有赞平台订单: 发货信息已保存, 但同步到有赞平台失败, 请联系有赞管理人员手动去有赞后台处理发货状态" + "<br />error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg(); // -3
																																																								// //
																																																								// 有赞订单物流失败
						logger.info("error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg());

						result.setErrorCode(-3); // 有赞订单物流同步成功
						result.setMsg(outputString);
						return result;
					}
				}
				else
				{
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("is_no_express", "0");
					params.put("tid", tid_oid[0]); // 有赞同步后的设备订单ID格式为tid-oid
					params.put("out_stype", "" + out_stype);
					params.put("out_sid", expresssNO);
					ShippingResultWrapper wrapper = KDTApi.logisticsOnlineConfirm(params);
					if (null != wrapper && wrapper.isSuccess() && wrapper.getResponse().getShipping().getIs_success())
					{
						result.setErrorCode(10); // 有赞订单物流同步成功
						result.setMsg("有赞平台订单: 发货成功, 同时成功同步到有赞平台");
						return result;
					}
					else
					{
						String outputString = "有赞平台订单: 发货信息已保存, 但同步到有赞平台失败, 请联系有赞管理人员手动去有赞后台处理发货状态" + "<br />error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg(); // -3
																																																								// //
																																																								// 有赞订单物流失败
						logger.info("error_response code=" + wrapper.getError_response().getCode() + " msg=" + wrapper.getError_response().getMsg());

						result.setErrorCode(-3); // 有赞订单物流同步成功
						result.setMsg(outputString);
						return result;
					}
				}
			}

			// 表示系有赞订单, 但这个订单所在的购物车的流量或设备订单未全部发货完毕. 只有发货完毕才去同步有赞
			// 路过即可
			result.setErrorCode(11);
			result.setMsg("有赞平台订单: 发货成功, 但此有赞购物车订单下仍有未发货的宝贝所以未同步物流状态到有赞");
			return result;
		}

		// 未全部发货, 或者提示不是有赞订单, 提示跳过
		result.setErrorCode(0);
		// result.setMsg("");
		return result;
	}



	/**
	 * 取消标3星且全部未发货的总订单, 和其他的流量订单/设备订单设置为不可用
	 *
	 * @param order
	 * @return
	 */
	public boolean cancelYouzanStar3Order(OrdersInfo order)
	{

		logger.debug("取消有赞标3星订单");
		try
		{
			int temp = ordersInfoDao.cancelYouzanStar3Order(order);
			if (temp > 0)
			{
				logger.debug("更新订单成功");

				flowDealOrdersDao.cancelYouzanStar3FlowOrder(order);
				deviceDealOrdersDao.cancelYouzanStar3DeviceOrder(order);

				return true;
			}
			else
			{
				logger.debug("更新订单失败");
				return false;
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 设备交易统计
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int deviceDealCount(OrdersInfo ordersInfo)
	{

		try
		{
			return ordersInfoDao.deviceDealCount(ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	/**
	 * 流量交易统计
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int flowDealCount(OrdersInfo ordersInfo)
	{

		try
		{
			return ordersInfoDao.flowDealCount(ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	public boolean consignment(String logisticsName, String expressNO, String SN, String orderID, String logisticsCost, String address, HttpServletRequest request)
	{

		try
		{
			return ordersInfoDao.consignment(logisticsName, logisticsCost, expressNO, SN, orderID, address, request);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}



	public List<OrdersInfo> getShipmentOrder(OrdersInfo ordersInfo)
	{

		try
		{
			return ordersInfoDao.getShipmentOrder(ordersInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 官网、app、微信、有赞示分配sn订单查询
	 *
	 * @return
	 */
	public String queryPage(SearchDTO searchDTO)
	{

		try
		{
			return ordersInfoDao.queryPage(searchDTO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}



	/**
	 *
	 * 给订官网、APP、微信、有赞订单分配SN
	 *
	 * @param SN  1254/2562/2546
	 * @param orderID
	 * @return boolean
	 */
	public boolean bindsnupdate(String SN, String orderID)
	{

		try
		{
			return ordersInfoDao.bindsnupdate(SN, orderID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}


	public List<DeviceDealOrders> getdeviceOrderBysn(DeviceDealOrders deviceDealOrders)
	{
		try
		{
			return ordersInfoDao.getdeviceOrderBysn(deviceDealOrders);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据接单ID查询订单表
	 * @param aoID 接单ID
	 * @return
	 */
	public OrdersInfo getByAoID(String aoID){
		try{

			return ordersInfoDao.getByAoID(aoID);

		}catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取消订单
	 * @param orderID
	 * @return
	 */
	public boolean cancelOrder(String orderID){
		try{

			if(ordersInfoDao.cancelOrder(orderID)>0){

				return true;
			}

		}catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}
}
