package com.Manage.dao;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.Shipment;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.kdt.api.YouzanConfig;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午6:26:26 * @version 1.0 * @parameter
 * * @since * @return
 */
@Repository
public class OrdersInfoDao extends BaseDao<OrdersInfoDao>
{
	private static final String NAMESPACE = OrdersInfoDao.class.getName() + ".";



	/**
	 * 插入订单
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int insertinfo(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "insertinfo", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 插入订单, 像有赞订单同步使用更多的字段
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int insertinfoWithExtra(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "insertinfoWithExtra", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 更新订单完成
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int updateiffinish(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "updateiffinish", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 一个更通用的完成订单更新, 但前端要确保各字段输入值的有效性!
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int updateiffinishMore(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "updateiffinishMore", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 总订单表分页查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO)
	{

		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<OrdersInfo> arr = (List<OrdersInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (OrdersInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				Date mdate = a.getModifyDate();
				if (mdate != null)
				{
					String mString = DateUtils.formatDate(mdate, "yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 出货界面查询绑定过SN的订单
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpageString1(SearchDTO searchDTO)
	{

		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<OrdersInfo> arr = (List<OrdersInfo>) page.getRows();

			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (OrdersInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				String orderID = a.getOrderID();
				String SN = "";

				Double yajin = 0.0;

				DeviceDealOrders dorders = new DeviceDealOrders();
				dorders.setOrderID(orderID);

				List<DeviceDealOrders> deviceDealList = getSqlSession().selectList("com.Manage.dao.DeviceDealOrdersDao.getCustomerIdDevice", dorders);
				for (DeviceDealOrders deviceDealOrders : deviceDealList)
				{
					SN += deviceDealOrders.getSN().substring(9) + "，";

					yajin += deviceDealOrders.getDealAmount();
				}

				obj.put("yajin", yajin);
				if(StringUtils.isNotBlank(SN)){
					obj.put("SN", SN.substring(0, SN.length() - 1));
				}else{
					obj.put("SN","");
				}
				Date mdate = a.getModifyDate();
				if (mdate != null)
				{
					String mString = DateUtils.formatDate(mdate, "yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 *
	 *
	 * @param searchDTO
	 * @return
	 */

	/*
	 * public String getpageString2(SearchDTO searchDTO) { try { Page page =
	 * queryPage(NAMESPACE, "queryPage2", "getcount2", searchDTO);
	 * List<OrdersInfo> arr = (List<OrdersInfo>) page.getRows(); JSONObject
	 * object = new JSONObject(); object.put("success", true);
	 * object.put("totalRows", page.getTotal()); object.put("curPage",
	 * page.getCurrentPage()); JSONArray ja = new JSONArray(); for (OrdersInfo a
	 * : arr) { JSONObject obj = JSONObject.fromObject(a); Date date =
	 * a.getCreatorDate(); String dString = DateUtils.formatDate(date,
	 * "yyyy-MM-dd HH:mm:ss"); obj.remove("creatorDate"); obj.put("creatorDate",
	 * dString);
	 *
	 * Date mdate = a.getModifyDate(); if (mdate != null) { String mString =
	 * DateUtils.formatDate(mdate, "yyyy-MM-dd HH:mm:ss");
	 * obj.remove("modifyDate"); obj.put("modifyDate", mString); }
	 *
	 * ja.add(obj); } object.put("data", ja); return object.toString(); } catch
	 * (Exception e) { throw new BmException(Constants.common_errors_1004, e); }
	 * }
	 */

	/**
	 * 删除
	 *
	 * @param ordersInfoID
	 * @return
	 */
	public int delete(String ordersInfoID)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "deletebyid", ordersInfoID);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1002, e);
		}
	}



	/**
	 * 编辑修改
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int edit(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "edit", ordersInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 查询详细
	 *
	 * @param ordersInfoID
	 * @return
	 */
	public OrdersInfo getInfo(String ordersInfoID)
	{

		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getbyid", ordersInfoID);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 订单统计
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int statistics(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().selectOne(NAMESPACE + "statistics", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 合计
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int sumorder(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().selectOne(NAMESPACE + "sumorder", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public List<OrdersInfo> getOrdersInfo(OrdersInfo ordersInfo)
	{

		try
		{

			return getSqlSession().selectList(NAMESPACE + "SearchOrdersInfo", ordersInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 保存设备的sn
	 */
	/*
	 * public int saveSN(String[] SN, String orderID, List<DeviceDealOrders>
	 * deviceDealOrders) {
	 *
	 * // 将sn插入到表中 int count = 0; try { Map<String, String> map = new
	 * HashMap<String, String>(); map.put("orderID", orderID); for (int i = 0; i
	 * < SN.length; i++) { map.remove("SN"); map.remove("deallType");
	 * map.remove("deviceDealID"); map.put("SN", SN[i]); map.put("deviceDealID",
	 * deviceDealOrders.get(i).getDeviceDealID()); map.put("deallType",
	 * deviceDealOrders.get(i).getDeallType()); int successRow = 0; int hang =
	 * 0; try { // 修改设备状态 DeviceInfo info = new DeviceInfo();
	 * info.setDeviceStatus("使用中"); info.setSN(SN[i]);
	 * getSqlSession().update("com.Manage.dao.DeviceInfoDao.updatedevicestatus",
	 * info); // 绑定设备订单SN,修孜设备订单状态（orderstatus） //
	 * 如果是购买设备订单：将SN更新到对应的设备订单，然后将设备订单状态改为 可使用 //
	 * 如果是租用设备订单：将SN更新到对应的设备订单，然后将设备订单状态改为 // 使用中，然后将这个总订单下的流量订单SN更新，将是否激活改为
	 * 是，流量订单状态改为可使用 successRow = getSqlSession().update(NAMESPACE + "updateSN",
	 * map);// 修改设备流量订单 // 如果是有赞，将设备订单状态改为可使用。 //
	 * 因为有赞平台的租用设备"押金"宝贝的订单在同步时只新建一个总订单和其下的设备订单, 而未有其下 // 关联的流量订单信息,
	 * 所以在此时更新流量订单应该适当要更改, 直到流量订单"激活"操作时, 才把设备关联 // 并更新流量订单状态. 所以下面若为有赞订单就先跳过 //
	 * 修改流量订单SN if (!deviceDealOrders.get(i).getDeallType().equals("购买") &&
	 * !StringUtils.startsWith(orderID, YouzanConfig.YOUZAN_ORDER_ID_PREFIX)) {
	 * Map<String, String> map1 = new HashMap<String, String>();
	 * map.put("orderID", orderID); map.put("SN", SN[0]); hang =
	 * getSqlSession().update("com.Manage.dao.FlowDealOrdersDao.updateflowdeal",
	 * map1); } // 修改成功的条数 count = count + successRow; } catch (Exception e) {
	 * e.printStackTrace(); } } return count; } catch (Exception e) {
	 * e.printStackTrace(); throw new BmException(Constants.common_errors_1003,
	 * e); } }
	 */

	/**
	 * 来自后台订单发货
	 *
	 * @param logisticsName
	 * @param logisticsCost
	 * @param expressNO
	 * @param SN
	 * @param orderID
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean consignment(String logisticsName, String logisticsCost, String expressNO, String SN, String orderID, String address, HttpServletRequest request) throws UnsupportedEncodingException
	{
		if (StringUtils.isBlank(logisticsCost))
		{
			logisticsCost = "0";
		}

		AdminUserInfo user = (AdminUserInfo) request.getSession().getAttribute("User");
		Dictionary dict = new Dictionary();
		dict.setDescription("快递公司");
		dict.setValue(logisticsName);
		Dictionary dictionary = getSqlSession().selectOne("com.Manage.dao.DictionaryDao.getDictByValue", dict);
		try
		{
			String[] SNarray = SN.split("\\|");
			String[] orderIDarray = orderID.split(",");

			OrdersInfo ordersInfo = new OrdersInfo();
			ordersInfo.setShipmentsStatus("已发货");
			ordersInfo.setAddress(address);

			String batchNO = DateUtils.getDate("yyyyMMddHHmmss");
			for (int i = 0; i < orderIDarray.length; i++)
			{

				ordersInfo.setOrderID(orderIDarray[i]);

				// 更新快递信息
				if ("zitiziqu".equals(logisticsName))
				{
					ordersInfo.setLogisticsInfo(dictionary.getLabel());
				}
				else
				{
					ordersInfo.setLogisticsInfo(dictionary.getLabel() + "," + expressNO + "," + logisticsCost);
				}
				
				//更新快递信息
				getSqlSession().update(NAMESPACE + "updatelogisticsInfo", ordersInfo);

				// 发货记录表插入一条数据
				Shipment shipment = new Shipment();
				shipment.setLogisticsCost(logisticsCost);
				shipment.setAddress(address);
				shipment.setLogisticsName(dictionary.getLabel());
				shipment.setShipmentDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				shipment.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				shipment.setCreatorUserID(user.getUserID());
				shipment.setCreatorUserName(user.getUserName());
				shipment.setExpressNO(expressNO);
				shipment.setLogisticsJC(logisticsName);
				shipment.setModifyDate(null);
				shipment.setModifyUserID(null);
				shipment.setOrderID(orderIDarray[i]);
				shipment.setRemark("");
				shipment.setShipmentID(UUID.randomUUID().toString());
				shipment.setDeviceStatus("出库");
				String newsn = "";
				for (int k = 0; k < SNarray.length; k++)
				{
					String formatsn = Constants.SNformat(SNarray[i]);
					if (k == 0)
					{
						newsn += formatsn;

					}
					else
					{
						newsn += "，" + formatsn;
					}
					
					//将设备状态改为出库
					try
					{
						DeviceInfo d = new DeviceInfo();
						d.setRepertoryStatus("出库");
						d.setSN(formatsn);
						getSqlSession().insert("com.Manage.dao.DeviceInfoDao.updaterepertoryStatus", d);
					}
					catch (Exception e)
					{
						e.printStackTrace();
						logger.info("发货更改设备状态为出库失败");
					}
					
				}
				shipment.setSN(newsn);
				shipment.setSysStatus("1");
				shipment.setBatchNO(batchNO);
				shipment.setAddress(address);
				//发货记录表插入一条数据
				getSqlSession().insert("com.Manage.dao.ShipmentDao.insert", shipment);
				
				
				

			}
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 修改流量订单为激活状态,并给流量订单绑定相应的SN
	 *
	 * @param orderID
	 * @param SN
	 * @return
	 */
	public int updateFlowDeall(String orderID, String SN)
	{

		Map<String, String> map = new HashMap<String, String>();
		map.put("orderID", orderID);
		map.put("SN", SN);
		try
		{
			return getSqlSession().update("com.Manage.dao.FlowDealOrdersDao.updateflowdeal", map);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 查订指定订单下的设备
	 *
	 * @return
	 */
	public List<DeviceDealOrders> getDeviceDealOrdersIDByorderID(String orderID)
	{

		return getSqlSession().selectList(NAMESPACE + "getDeviceDealOrdersIDByorderID", orderID);
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
			OrdersInfo ordersInfo = new OrdersInfo();
			ordersInfo.setOrderID(orderID);
			ordersInfo.setLogisticsInfo(logistics + "," + expresssNO);
			ordersInfo.setShipmentsStatus("已发货");
			ordersInfo.setAddress(address);
			return getSqlSession().update(NAMESPACE + "updatelogisticsInfo", ordersInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003);
		}
	}



	/**
	 * 获取快递公司名称
	 *
	 * @return
	 */
	public List<Dictionary> getExpress()
	{

		List<Dictionary> dictionaries = getSqlSession().selectList(NAMESPACE + "selectexpress");
		return dictionaries;
	}



	/**
	 * 根据ID获流量订单
	 *
	 * @param orderID
	 * @return
	 */
	public List<FlowDealOrders> getflowDeal(String orderID)
	{

		return getSqlSession().selectList("com.Manage.dao.FlowDealOrdersDao.getflowDeal", orderID);
	}



	/**
	 * 确认退订此订单 更新为"退订中"状态
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int cancelPaidOrder(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "cancelPaidOrder", ordersInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public int updatewxOrders(OrdersInfo order)
	{

		return getSqlSession().update("updatewxOrders", order);
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

		try
		{
			return getSqlSession().selectList(NAMESPACE + "SearchOrdersInfoLastYouzan", ordersInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 取消标3星且全部未发货的总订单, 和其他的流量订单/设备订单设置为不可用
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int cancelYouzanStar3Order(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "cancelYouzanStar3Order", ordersInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
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
			return getSqlSession().selectOne(NAMESPACE + "deviceDealCount", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
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
			return getSqlSession().selectOne(NAMESPACE + "flowDealCount", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public List<OrdersInfo> getShipmentOrder(OrdersInfo ordersInfo)
	{

		try
		{
			return getSqlSession().selectList(NAMESPACE + "getShipmentOrder", ordersInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
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
			Page page = queryPage(NAMESPACE, "queryPage3", "getcount3", searchDTO);
			List<OrdersInfo> arr = (List<OrdersInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (OrdersInfo a : arr)
			{
				String userCountry1 = a.getCountryName();
				String userCountry = a.getCountryName();
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(userCountry);
				userCountry = wrapper.getCountryNameStrings();
				a.setCountryName(userCountry);

				JSONObject obj = JSONObject.fromObject(a);
				obj.put("countryName1", userCountry1);

				obj.put("days", a.getFlowDays());

				obj.put("flowPrice", a.getFlowPrice());
				obj.put("yajingPrice", a.getDealAmount());

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 设备订单状态改为使用中，根据设备订单ID
	 *
	 * @param deviceDealOrders
	 * @return
	 */
	public int updateorderStatus(DeviceDealOrders deviceDealOrders)
	{
		try
		{
			return getSqlSession().update("com.Manage.dao.DeviceDealOrdersDao.updateorderStatus", deviceDealOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 然后将设备状态改为使用中
	 *
	 * @param deviceDealOrders
	 * @return
	 */
	public int updateDeviceOrder(String SN)
	{
		try
		{
			return getSqlSession().update("com.Manage.dao.DeviceInfoDao.updateDeviceOrder", SN);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 将SN更新到对应的设备订单，根据设备订单ID
	 *
	 * @param deviceDealOrders
	 * @return
	 */
	public int updatesn(DeviceDealOrders deviceDealOrders)
	{
		try
		{
			return getSqlSession().update("com.Manage.dao.DeviceDealOrdersDao.updatesn", deviceDealOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * getSqlSession
	 *
	 * @param flowOrders
	 * @return
	 */
	public int updateflowdeal(FlowDealOrders flowOrders)
	{
		try
		{
			return getSqlSession().update("com.Manage.dao.FlowDealOrdersDao.updateflowdeal3", flowOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 将总订单是否绑定SN更改为是
	 *
	 * @param info
	 * @return
	 */
	public int updateifBindSN(OrdersInfo info)
	{
		try
		{
			return getSqlSession().update("updateifBindSN", info);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 根据总订单搜索所有的设备订单
	 *
	 * @param deviceDealOrders
	 * @return
	 */
	public List<DeviceDealOrders> getCustomerIdDevice(DeviceDealOrders deviceDealOrders)
	{
		try
		{
			return getSqlSession().selectList("com.Manage.dao.DeviceDealOrdersDao.getCustomerIdDevice", deviceDealOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 根据总订单搜索所有的流量订单
	 *
	 * @param flowOrders
	 * @return
	 */
	public List<FlowDealOrders> selectwxOrderSnList(FlowDealOrders flowOrders)
	{
		try
		{
			return getSqlSession().selectList("com.Manage.dao.FlowDealOrdersDao.selectwxOrderSnList", flowOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 *
	 * 给订官网、APP、微信、有赞订单分配SN
	 *
	 * @param SN
	 *            1254/2562/2546
	 * @param orderID
	 * @return boolean
	 */
	public boolean bindsnupdate(String SN, String orderID)
	{
		String[] snarray = SN.split("/");

		DeviceDealOrders deviceDealOrders = new DeviceDealOrders();
		deviceDealOrders.setOrderID(orderID);
		// 根据总订单搜索所有的设备订单
		List<DeviceDealOrders> deviceDealList = this.getCustomerIdDevice(deviceDealOrders);

		FlowDealOrders flowOrders = new FlowDealOrders();
		flowOrders.setOrderID(orderID);
		// 搜索该总订单下的所在流量订单
		List<FlowDealOrders> flowDealOrders = this.selectwxOrderSnList(flowOrders);

		if (deviceDealList.size() != flowDealOrders.size())
		{
			logger.info("该订单下设备订单和流量订单的个数不一样");

			return false;
		}
		else if (deviceDealList.size() > 0 && flowDealOrders.size() > 0)
		{
			try
			{
				/*
				 * ---APP和官网---
				 * 如果是购买设备订单：将SN更新到对应的设备订单，然后将设备状态改为使用中，设备订单状态改为可使用
				 * 如果是租用设备订单：将SN更新到对应的设备订单 ，然后将设备状态改为使用中，设备订单状态改为使用中，
				 * 然后将这个总订单下的流量订单SN更新，将是否激活改为是，流量订单状态改为可使用。
				 *
				 * ---有 赞--- 将设备状态改为使用中，将设备订单状态改为可使用。
				 */

				for (int i = 0; i < deviceDealList.size(); i++)
				{
					String deallType = deviceDealList.get(i).getDeallType();

					SN = Constants.SNformat(snarray[i]);

					String deviceDealID = deviceDealList.get(i).getDeviceDealID();

					String flowDealID = flowDealOrders.get(i).getFlowDealID();

					deviceDealOrders.setSN(SN);

					deviceDealOrders.setDeviceDealID(deviceDealID);

					// 如果是购买订单 并且 不是有赞将设备状态改为可使用
					if ("购买".equals(deallType) && !StringUtils.startsWith(orderID, YouzanConfig.YOUZAN_ORDER_ID_PREFIX))
					{
						deviceDealOrders.setOrderStatus("可使用");
					}
					else if ("租用".equals(deallType))
					{
						// 如果是租用，将设备状态改为 使用中
						deviceDealOrders.setOrderStatus("使用中");
					}

					// 设备订单状态改为使用中，根据设备订单ID
					this.updateorderStatus(deviceDealOrders);

					// 然后将设备状态改为使用中
					this.updateDeviceOrder(SN);

					// 将SN更新到对应的设备订单，根据设备订单ID
					this.updatesn(deviceDealOrders);

					flowOrders.setSN(SN);

					flowOrders.setFlowDealID(flowDealID);

					// 将是否激活改为是，流量订单状态改为可使用 ,更新SN
					this.updateflowdeal(flowOrders);
				}

				// 将总订单是否绑定SN更改为是
				OrdersInfo info = new OrdersInfo();

				info.setOrderID(orderID);

				this.updateifBindSN(info);

				return true;
			}
			catch (Exception e)

			{
				e.printStackTrace();

				logger.info("分配设备失败...");

				return false;
			}
		}
		else
		{
			logger.info("该总订单下没有设备订单和流量订单");

			return true;
		}

	}



	/**
	 * 已发货订单查看
	 */
	public String queryPageShipped(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<OrdersInfo> arr = (List<OrdersInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (OrdersInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				Date mdate = a.getModifyDate();
				if (mdate != null)
				{
					String mString = DateUtils.formatDate(mdate, "yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public List<DeviceDealOrders> getdeviceOrderBysn(DeviceDealOrders deviceDealOrders)
	{
		try
		{
			return getSqlSession().selectList("com.Manage.dao.DeviceDealOrdersDao.getdeviceOrderBysn", deviceDealOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 根据接单ID查询订单表
	 *
	 * @param aoID
	 *            接单ID
	 * @return
	 */
	public OrdersInfo getByAoID(String aoID)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getByAoID", aoID);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 取消订单
	 *
	 * @param orderID
	 * @return
	 */
	public int cancelOrder(String orderID)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "cancelOrder", orderID);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
}
