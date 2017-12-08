package com.Manage.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.restlet.data.Form;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.Manage.common.constants.Constants;
import com.Manage.common.constants.RestConstants;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DES;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.FlowPlanInfo;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.WorkFlow;
import com.Manage.entity.common.SearchDTO;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午6:28:58 * @version 1.0 * @parameter
 * * @since * @return
 */
@Controller
@RequestMapping("/orders/ordersinfo")
public class OrdersInfoControl extends BaseController
{
	private final static String RETURNROOT_STRING = "WEB-INF/views/orders/";

	private Logger logger = LogUtil.getInstance(OrdersInfoControl.class);



	/**
	 * 新建订单页面跳转
	 *
	 * @return
	 */
	@RequestMapping("/goone")
	public String goone(Model model)
	{

		// 查询客户来源绑定下拉框.
		List<Dictionary> dictionaries = dictionarySer.getAllList(com.Manage.common.constants.Constants.DICT_CUSTOMER_SOURCE);
		model.addAttribute("diclist", dictionaries);
		Distributor dis = new Distributor();
		dis.setType("客户");
		List<Distributor> list = distributorSer.getbytype(dis);
		model.addAttribute("distributor", list);
		return RETURNROOT_STRING + "order_add_one";
	}



	/**
	 * 跳转到订单查询页面 全部有效订单 目前有效订单系指"已付款" orderStatus 的订单, 不管其发货状态
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model)
	{

		List<Dictionary> orderStatus = dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSTATUS);
		List<Dictionary> orderSources = dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSOURCE);
		model.addAttribute("OrderStatusDict", orderStatus);
		model.addAttribute("OrderSourceDict", orderSources);

		List<Distributor> distributors = distributorSer.getAll("");
		model.addAttribute("dis", distributors);

		return RETURNROOT_STRING + "order_list";
	}



	/**
	 * 跳转到订单查询页面 全部其他订单 指除了已付款外的其他状态订单 与 前一个 /list 共用同一个模板, 使用参数区别 // ahming
	 * notes: 使用"其他"系在提交: // commit dd4424a81344993fcea234a205d29d2491203989 //
	 * Author: tangming <tangming@easy2go.cn> // Date: Fri Sep 11 08:53:21 2015
	 * +0800 // >> "全部订单"分开为两个页面"全部有效订单"和"全部其他订单", 并默认显示 // >> 订单列表行为30,
	 * 优化显示金额格式. // 作出的处理, 后面已取消. 所以现在把不需要的也清理一下. 只使用"全部订单"页面, 添加按"订单状态"和 //
	 * "订单来源"作过滤, 然后相关人员可跟进"退订"操作.
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/listotherstatus")
	public String listOtherStatus(Model model)
	{

		List<Dictionary> orderStatus = dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSTATUS);
		model.addAttribute("OrderStatusDict", orderStatus);
		model.addAttribute("IsOtherOrderStatus", true);
		return RETURNROOT_STRING + "order_list";
	}



	/**
	 * 跳转到订单编辑页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(String orderID, Model model, HttpServletRequest request)
	{

		OrdersInfo info = ordersInfoSer.getdetail(orderID);
		if (info != null)
		{
			model.addAttribute("order", info);

			String from = request.getParameter("from");
			if (StringUtils.isNotBlank(from) && "youzan".equals(from))
			{
				model.addAttribute("From", from);
			}

			return RETURNROOT_STRING + "order_edit";
		}
		else
		{
			return RETURNROOT_STRING + "order_list";
		}
	}



	/**
	 * 跳转到订单统计界面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/count")
	public String count(Model model)
	{

		// 订单来源.
		List<Dictionary> dictionarie = dictionarySer.getAllList(Constants.DICT_CUSTOMER_SOURCE);
		// 渠道商.
		List<Distributor> distributors = distributorSer.getAll("");
		model.addAttribute("diclist", dictionarie);
		model.addAttribute("distributors", distributors);
		return RETURNROOT_STRING + "order_statistics";
	}



	/**
	 * 跳转到订单激活界面
	 *
	 * @param model
	 *            这里的orderID传的是客户ID
	 * @return
	 */
	@RequestMapping("/activate")
	public String activate(String flowOrderID, String orderID, Model model)
	{

		if (orderID == null || "".equals(orderID))
		{
			return "WEB-INF/views/orders/floworder_list";
		}

		List<DeviceDealOrders> list = deviceDealOrdersSer.getsnbyoid(orderID);
		model.addAttribute("snlist", list);
		model.addAttribute("fid", flowOrderID);
		return RETURNROOT_STRING + "floworder_activate";
	}



	/**
	 * 根据手机号或者姓名搜索客户
	 *
	 * @param p
	 * @param response
	 */
	@RequestMapping("/getcustomer")
	public void getcustomer(String p, HttpServletResponse response)
	{

		logger.debug("新建订单搜索客户得到请求");
		response.setContentType("application/json;charset=UTF-8");
		try
		{
			if (p == null)
			{
				response.getWriter().print("[]");
				return;
			}
			List<CustomerInfo> customerInfos = customerInfoSer.getbynameorphone(p);
			if (customerInfos == null)
			{
				response.getWriter().print("[]");
				return;
			}
			JSONArray jsonArray = new JSONArray();
			for (CustomerInfo c : customerInfos)
			{
				jsonArray.add(JSONObject.fromObject(c));
			}
			response.getWriter().print(jsonArray.toString());
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

	}



	/**
	 * 订单点击下一步
	 *
	 * 下一步进入后总会创建订单 OrderInfo 记录, 若中途未完成, ifFinish为'否', 则为无效订单, 可重新编辑完成订单
	 * 20150907更新: 根据官网的需求, 补上 orderStatus orderSource shipmentsStatus 等字段值
	 *
	 * @param customerInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/gotwo")
	public String gotwo(CustomerInfo customerInfo, Model model, HttpServletRequest request)
	{

		if (customerInfo == null)
		{
			model.addAttribute("mes", "参数为空");
			return RETURNROOT_STRING + "order_add_one";
		}

		// 判断是添加的还是搜索的.
		if (customerInfo.getCustomerID() != null && !"".equals(customerInfo.getCustomerID()))
		{
			CustomerInfo customer = customerInfoSer.getOneCustomerDetail(customerInfo.getCustomerID());
			// 创建新订单.
			OrdersInfo order = new OrdersInfo();
			String uid = getUUID();
			order.setOrderID(uid);
			order.setFlowDealCount(0);
			order.setDeviceDealCount(0);
			order.setRemark("");
			order.setIfFinish("否");
			order.setOrderAmount(0.00);

			order.setOrderSource("后台");
			order.setOrderStatus("已付款");
			order.setShipmentsStatus("已发货");

			order.setCustomerID(customer.getCustomerID());
			order.setCustomerName(customer.getCustomerName());
			AdminUserInfo user = (AdminUserInfo) getSession().getAttribute("User");
			if (user != null)
			{
				order.setCreatorUserID(user.getUserID());
				order.setCreatorUserName(user.getUserName());
			}
			if (ordersInfoSer.insertinfo(order))
			{
				// 查询所有国家
				List<CountryInfo> countryInfos = countryInfoSer.getAll("");
				// 查询所有套餐
				List<FlowPlanInfo> flowPlanInfos = flowPlanInfoSer.getall();
				model.addAttribute("order", order);
				model.addAttribute("countrys", countryInfos);
				model.addAttribute("flows", flowPlanInfos);
				model.addAttribute("cus", customer);
				return RETURNROOT_STRING + "order_add_two";
			}
			else
			{
				model.addAttribute("mes", "生成订单失败");
				return RETURNROOT_STRING + "order_add_one";
			}
		}
		else if (customerInfo.getCustomerName() != null && !"".equals(customerInfo.getCustomerName()))
		{
			CustomerInfo info = new CustomerInfo();
			info.setCustomerID(getUUID());
			info.setCustomerName(customerInfo.getCustomerName());
			info.setAddress(customerInfo.getAddress());
			info.setPhone(customerInfo.getPhone());
			try
			{
				info.setPassword(DES.toHexString(DES.encrypt(Constants.DEFAULT_CUSTOMER_PWD, Constants.DES_KEYWEB)));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			info.setCustomerType("普通客户");
			info.setCustomerSource("后台");
			info.setRemark(customerInfo.getRemark());
			info.setEmail(customerInfo.getEmail());
			String dString = customerInfo.getDistributorID();
			if (!"".equals(dString))
			{
				customerInfo.setDistributorID(dString.split(",")[0]);
				customerInfo.setDistributorName(dString.split(",")[1]);
			}
			else
			{
				customerInfo.setDistributorID("");
				customerInfo.setDistributorName("");
			}

			AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
			if (adminUserInfo != null)
			{
				info.setCreatorUserID(adminUserInfo.getUserID());
			}
			info.setCreatorDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			info.setSysStatus(1);
			int temp = customerInfoSer.insertCustomerInfo(info);
			if (temp > 0)
			{
				// 创建新订单.
				OrdersInfo order = new OrdersInfo();
				String uid = getUUID();
				order.setOrderID(uid);
				order.setFlowDealCount(0);
				order.setDeviceDealCount(0);
				order.setRemark("");
				order.setIfFinish("否");
				order.setOrderAmount(0.00);
				order.setOrderSource("后台");
				order.setOrderStatus("已付款"); // 后台添加的订单此判断总设备为已付款, 目前评估影响不大,
												// 因为前端主要根据 orderSource 处理
				order.setShipmentsStatus("已发货"); // 类似同上

				order.setCustomerID(info.getCustomerID());
				order.setCustomerName(info.getCustomerName());
				AdminUserInfo user = (AdminUserInfo) getSession().getAttribute("User");
				if (user != null)
				{
					order.setCreatorUserID(user.getUserID());
					order.setCreatorUserName(user.getUserName());
				}
				if (ordersInfoSer.insertinfo(order))
				{
					// 查询所有国家
					List<CountryInfo> countryInfos = countryInfoSer.getAll("");
					// 查询所有套餐
					List<FlowPlanInfo> flowPlanInfos = flowPlanInfoSer.getall();
					model.addAttribute("order", order);
					model.addAttribute("countrys", countryInfos);
					model.addAttribute("flows", flowPlanInfos);
					model.addAttribute("cus", info);
					return RETURNROOT_STRING + "order_add_two";
				}
				else
				{
					model.addAttribute("mes", "添加订单失败");
					return RETURNROOT_STRING + "order_add_one";
				}
			}
			else
			{
				model.addAttribute("mes", "添加客户失败");
				return RETURNROOT_STRING + "order_add_one";
			}
		}
		else
		{
			return RETURNROOT_STRING + "order_add_one";
		}
	}



	/**
	 * 查询可使用设备信息
	 *
	 * @param searchDTO
	 * @param deviceInfo
	 * @param response
	 */
	@RequestMapping("/getdev")
	public void getdevpage(SearchDTO searchDTO, DeviceInfo deviceInfo, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		if (deviceInfo == null)
		{
			deviceInfo = new DeviceInfo();
		}
		deviceInfo.setSysStatus("1");
		deviceInfo.setDeviceStatus("可使用");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceInfo);
		String jsonString = deviceInfoSer.getdevpage(seDto);
		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * 根据SN查询
	 *
	 * @param SN
	 * @param response
	 */
	@RequestMapping("/getbysn")
	public void getbysn(String SN, HttpServletResponse response)
	{

		try
		{
			JSONArray jArray = new JSONArray();
			if (SN == null || "".equals(SN))
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -1);
				jArray.add(obj);
				response.getWriter().print(jArray.toString());
			}
			DeviceInfo deviceInfo = deviceInfoSer.getbysn(SN);
			if (deviceInfo == null)
			{
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				jArray.add(obj);
				response.getWriter().print(jArray.toString());
			}
			else if (!"可使用".equals(deviceInfo.getDeviceStatus()))
			{
				JSONObject obj = new JSONObject();
				obj.put("error", 2);
				jArray.add(obj);
				response.getWriter().print(jArray.toString());
			}
			else
			{
				JSONObject object = JSONObject.fromObject(deviceInfo);
				jArray.add(object);
				response.getWriter().print(jArray.toString());
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	/**
	 * 添加设备订单
	 *
	 * @param deviceDealOrders
	 * @param response
	 */
	@RequestMapping("/devorder")
	public void devorder(DeviceDealOrders deviceDealOrders, HttpServletResponse response)
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			try
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -5); // 提示重新登录
				response.getWriter().print(obj.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}

		try
		{
			if (!"".equals(deviceDealOrders.getCustomerID()) && !"".equals(deviceDealOrders.getDeviceID()) && !"".equals(deviceDealOrders.getSN()) && !"".equals(deviceDealOrders.getOrderID()))
			{

				deviceDealOrders.setCustomerName(java.net.URLDecoder.decode(deviceDealOrders.getCustomerName(), "UTF-8"));
				String uid = getUUID();
				deviceDealOrders.setDeviceDealID(uid);
				deviceDealOrders.setIfFinish("否");
				deviceDealOrders.setIfReturn("否");
				deviceDealOrders.setOrderStatus("不可用");
				deviceDealOrders.setReturnDate(null);
				deviceDealOrders.setCreatorUserID(adminUserInfo.getUserID());
				deviceDealOrders.setCreatorUserName(adminUserInfo.getUserName());
				if (deviceDealOrdersSer.insertinfo(deviceDealOrders))
				{
					JSONObject obj = JSONObject.fromObject(deviceDealOrders);
					response.getWriter().print(obj.toString());

					try
					{
						AdminOperate admin = new AdminOperate();
						admin.setOperateID(UUID.randomUUID().toString());
						admin.setCreatorUserID(adminUserInfo.getUserID());
						admin.setCreatorUserName(adminUserInfo.getUserName());

						admin.setOperateContent("已添加设备交易订单, 记录ID为: " + deviceDealOrders.getDeviceDealID() + " 设备SN: " + deviceDealOrders.getSN() + " 客户: " + deviceDealOrders.getCustomerName());
						admin.setOperateMenu("订单管理>新增订单");
						admin.setOperateType("新增");

						adminOperateSer.insertdata(admin);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					response.getWriter().print(obj.toString());
				}
			}
			else
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -1);
				response.getWriter().print(obj.toString());
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * 添加流量订单
	 *
	 * @param deviceDealOrders
	 * @param response
	 */
	@RequestMapping("/floworder")
	public void floworder(FlowDealOrders flowDealOrders, HttpServletResponse response)
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			try
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -5);
				response.getWriter().print(obj.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}
		try
		{
			String uclist2 = "";
			if (flowDealOrders.getUserCountry() == null || "".equals(flowDealOrders.getUserCountry()))
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -2);
				response.getWriter().print(obj.toString());
			}
			else
			{
				String uclist1 = "";
				String[] uc = flowDealOrders.getUserCountry().split(",");
				for (int i = 0; i < uc.length; i++)
				{
					String[] temp = uc[i].split("-");
					if (i != uc.length - 1)
					{
						uclist1 += temp[0] + "," + temp[1] + "," + temp[2] + "|";
						uclist2 += temp[0] + ",";
					}
					else
					{
						uclist1 += temp[0] + "," + temp[1] + "," + temp[2];
						uclist2 += temp[0];
					}
				}
				flowDealOrders.setUserCountry(uclist1);

			}
			if (flowDealOrders.getCustomerID() != null && flowDealOrders.getOrderID() != null)
			{
				flowDealOrders.setCustomerName(java.net.URLDecoder.decode(flowDealOrders.getCustomerName(), "UTF-8"));
				String uid = getUUID();
				flowDealOrders.setFlowDealID(uid);
				flowDealOrders.setIfFinish("否");
				flowDealOrders.setIfActivate("否");
				flowDealOrders.setOrderStatus("不可用");
				flowDealOrders.setLimitSpeed(Constants.LIMITSPEED);
				flowDealOrders.setLimitValve(Constants.LIMITVALUE);
				flowDealOrders.setFlowExpireDate(DateUtils.getcurDate(DateUtils.beforeNDateToString(DateUtils.parseDate(flowDealOrders.getPanlUserDate()), flowDealOrders.getFlowDays(), "yyyy-MM-dd HH:mm:ss"), 1));// 流量到期时间
				if (!flowDealOrders.getOrderType().equals("3"))
				{
					flowDealOrders.setFlowExpireDate(DateUtils.beforeNDateToString(DateUtils.parseDate(flowDealOrders.getPanlUserDate()), flowDealOrders.getFlowDays(), "yyyy-MM-dd HH:mm:ss"));
				}

				flowDealOrders.setDaysRemaining(flowDealOrders.getFlowDays());
				flowDealOrders.getFlowDays();
				flowDealOrders.setOrderCreateDate(new Date());
				flowDealOrders.setCreatorUserID(adminUserInfo.getUserID());
				flowDealOrders.setCreatorUserName(adminUserInfo.getUserName());
				flowDealOrders.setJourney(flowDealOrders.getJourney().replace(",", "，"));

				String journey = flowDealOrdersSer.getJourney(flowDealOrders.getJourney());

				flowDealOrders.setJourney(journey);

				if (flowDealOrders.getOrderType().equals("2"))
				{
					flowDealOrders.setSpeedStr("0-2000|" + flowDealOrders.getFlowTotal() + "-2");
				}

				if (flowDealOrdersSer.insertinfo(flowDealOrders))
				{
					flowDealOrders.setUserCountry(uclist2);
					JSONObject obj = JSONObject.fromObject(flowDealOrders);
					response.getWriter().print(obj.toString());

					try
					{
						AdminOperate admin = new AdminOperate();
						admin.setOperateID(UUID.randomUUID().toString());
						admin.setCreatorUserID(adminUserInfo.getUserID());
						admin.setCreatorUserName(adminUserInfo.getUserName());
						admin.setOperateContent("已新增流量交易订单, 记录ID为: " + flowDealOrders.getFlowDealID() + " 客户: " + flowDealOrders.getCustomerName());
						admin.setOperateMenu("订单管理>新增订单");
						admin.setOperateType("新增");
						adminOperateSer.insertdata(admin);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					response.getWriter().print(obj.toString());
				}
			}
			else
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -1);
				response.getWriter().print(obj.toString());
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * 添加套餐订单
	 *
	 * @param flowDealOrders
	 * @param response
	 */
	@RequestMapping("/flowplanorder")
	public void flowplanorder(FlowDealOrders flowDealOrders, HttpServletResponse response)
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			try
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -5);
				response.getWriter().print(obj.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}
		try
		{
			if (flowDealOrders.getFlowPlanID() == null || "".equals(flowDealOrders.getFlowPlanID()))
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -2);
				response.getWriter().print(obj.toString());
				return;
			}
			if ("".equals(flowDealOrders.getCustomerID()) || "".equals(flowDealOrders.getOrderID()))
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -1);
				response.getWriter().print(obj.toString());
				return;
			}
			List<FlowDealOrders> resultList = new  ArrayList<FlowDealOrders>();
			IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
			// 获取到套餐信息
			FlowPlanInfo flowPlanInfo = flowPlanInfoSer.getPlanById(flowDealOrders.getFlowPlanID());
			// 相关流量订单数量
			String flowdealCount = flowPlanInfo.getFlowDealCount();
			// 相关设备数量
			String deviceDealCount = flowPlanInfo.getDeviceDealCount();
			// 使用国家
			String countryList = flowPlanInfo.getCountryList();
			if (countryList == null)
			{
				countryList = "";
			}
			String[] countryListArray = countryList.split("-");
		
			String[] SNArray = flowDealOrders.getSN().split("/");
			boolean flowdealSuccess = false;
			boolean orderSuccess = false;
			boolean deviceSuccess = false;
			for (int i = 0; i < SNArray.length; i++)
			{
				// 检查设备状态是否为使用中
				if(Constants.SNformat(SNArray[i])==null){
					map.put(SNArray[i], "请输入SN后六位");
					continue;
				}
				DeviceInfo info = deviceInfoSer.getbysn(Constants.SNformat(SNArray[i]));
				if (info == null)
				{
					map.put(SNArray[i], "没有此设备");
					continue;
				}
				else
				{
					if ("使用中".equals(info.getDeviceStatus()))
					{
						map.put(SNArray[i], "设备状态使用中");
						continue;
					}
				}
				String orderID = UUID.randomUUID().toString();
				// 总订单
				OrdersInfo orders = new OrdersInfo();
				orders.setOrderID(orderID);
				orders.setCustomerID("10088");
				orders.setCustomerName("套餐用户");
				orders.setDeviceDealCount(Integer.parseInt(flowPlanInfo.getDeviceDealCount()));
				orders.setIfFinish("是");
				orders.setOrderStatus("已付款");
				orders.setOrderSource("后台");
				orders.setShipmentsStatus("已发货");
				orders.setFlowDealCount(Integer.parseInt(countryListArray.length + ""));
				orders.setOrderAmount(flowPlanInfo.getPlanPrice());
				orders.setOrderStatus("");
				orders.setAddress("");
				orders.setCreatorUserID(adminUserInfo.getUserID());
				orders.setCreatorUserName(adminUserInfo.getUserName());
				orders.setSysStatus(true);
				orderSuccess = ordersInfoSer.insertinfo(orders);
				// 暂时先按一个个套餐只关联一个设备（具体情况后续开会讨论决定）
				// for(int j=0;j<Integer.parseInt(deviceDealCount);j++){
				// 设备交易订单
				DeviceDealOrders deviceDeal = new DeviceDealOrders();
				String deviceDealID = UUID.randomUUID().toString();
				deviceDeal.setDeviceDealID(deviceDealID);
				deviceDeal.setOrderID(orderID);
				deviceDeal.setSN(Constants.SNformat(SNArray[i]));
				deviceDeal.setCustomerName("套餐用户");
				deviceDeal.setCustomerID("10088");
				deviceDeal.setDeviceID(info.getDeviceID());
				deviceDeal.setDeallType("购买");
				deviceDeal.setDealAmount(500);
				deviceDeal.setIfFinish("是");
				deviceDeal.setIfReturn("否");
				deviceDeal.setOrderStatus("可使用");
				deviceDeal.setCreatorUserID(adminUserInfo.getUserID());
				deviceDeal.setCreatorUserName(adminUserInfo.getUserName());
				deviceDeal.setSysStatus(true);
				deviceSuccess = deviceDealOrdersSer.insertinfo(deviceDeal);
				// 将设备状态改为使用中
				deviceInfoSer.updatedevicestatus(info);
				// }
				for (int j = 0; j < Integer.parseInt(flowdealCount); j++)
				{
					// 流量订单
					flowDealOrders.setFlowDealID(UUID.randomUUID().toString());
					flowDealOrders.setOrderID(orderID);
					flowDealOrders.setCustomerID("10088");
					flowDealOrders.setCustomerName("套餐用户");
					flowDealOrders.setOrderCreateDate(DateUtils.StrToDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss")));
					flowDealOrders.setUserCountry(countryListArray[j]);
					flowDealOrders.setPanlUserDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss")); // 预约时间
					flowDealOrders.setOrderStatus("可使用");
					flowDealOrders.setIfFinish("是");
					flowDealOrders.setRemark("");
					flowDealOrders.setUserCountry(flowPlanInfo.getCountryList());
					flowDealOrders.setFlowPlanID(flowDealOrders.getFlowPlanID());
					flowDealOrders.setFlowPlanName(flowPlanInfo.getFlowPlanName());
					flowDealOrders.setFlowDays(Integer.parseInt(flowPlanInfo.getUserDayList().split("-")[j]));// 流量天数
					flowDealOrders.setDaysRemaining(Integer.parseInt(flowPlanInfo.getUserDayList().split("-")[j]));// 剩余天数
					
					
					flowDealOrders.setOrderAmount(flowPlanInfo.getPlanPrice()/Double.parseDouble(flowdealCount));// 流量订单金额
					
					
					String orderType = flowPlanInfo.getOrderType();
					if ("1".equals(orderType) || "2".equals(orderType))
					{
						flowDealOrders.setFlowExpireDate(DateUtils.beforeNDateToString(new Date(), Integer.parseInt(flowPlanInfo.getUserDayList().split("-")[j]), "yyyy-MM-dd HH:mm:ss"));
					}
					else if ("3".equals(orderType) || "4".equals(orderType))
					{
						int validPeriod = flowPlanInfo.getValidPeriod();
						
						Date panlUserDate = DateUtils.StrToDate(flowDealOrders.getPanlUserDate());
						
						String flowExpireDate = DateUtils.beforeNDateToString(panlUserDate, validPeriod, "yyyy-MM-dd HH:mm:ss");
						
						flowDealOrders.setFlowExpireDate(flowExpireDate);

					}

					flowDealOrders.setSN(Constants.SNformat(SNArray[i]));
					flowDealOrders.setIfActivate("是");// 是否激活
					flowDealOrders.setActivateDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));// 激活时间为当前时间
					flowDealOrders.setLimitValve(Constants.LIMITVALUE);// 限速阀值
					flowDealOrders.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
					flowDealOrders.setFlowUsed(0);
					flowDealOrders.setOrderType(flowPlanInfo.getOrderType());
					if ("2".equals(flowPlanInfo.getOrderType())) flowDealOrders.setFlowTotal(flowPlanInfo.getFlowSum() + "");
					else flowDealOrders.setFlowTotal(0 + "");
					flowDealOrders.setFactoryFlag(0);// 工厂标识
					flowDealOrders.setCreatorUserID(adminUserInfo.getUserID());
					flowDealOrders.setCreatorUserName(adminUserInfo.getUserName());
					flowDealOrders.setSysStatus(true);
					flowDealOrders.setJourney("");
					flowDealOrders.setIfVPN("0");
					flowdealSuccess = flowDealOrdersSer.insertinfo(flowDealOrders);
					
					CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(flowDealOrders.getUserCountry());
					String names = wrapper.getCountryNameStrings();
					flowDealOrders.setUserCountry(names);
					flowDealOrders.setDeviceDealID("true");
					
					resultList.add(flowDealOrders);
				}
			}
			if (orderSuccess == true && deviceSuccess == true && flowdealSuccess == true)
			{
				/*CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(flowDealOrders.getUserCountry());
				String names = wrapper.getCountryNameStrings();
				flowDealOrders.setUserCountry(names);
				flowDealOrders.setDeviceDealID("true");*/
				
				JSONObject object = new JSONObject();
				JSONArray ja = new JSONArray();
				
				
				String resultString="";
				for (FlowDealOrders flowDealOrders2 : resultList)
				{
					JSONObject obj = JSONObject.fromObject(flowDealOrders2);
					ja.add(obj);
				}
				//JSONObject obj = JSONObject.fromObject(flowDealOrders);
				//response.getWriter().print(obj.toString());
				
				//String json  ="{\"data\":[{},{}]}";
				
				
				object.put("data", ja);
				object.put("error", "6");
				response.getWriter().print(object.toString());
			}
			else
			{
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				String msg = "<p>错误信息如下：</p>";
				for (String key : map.keySet())
				{
					msg = msg + "<p>" + key + ":" + map.get(key) + "</p>";
				}
				obj.put("Msg", msg);
				response.getWriter().print(obj.toString());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 删除设备订单
	 *
	 * @param deviceDealID
	 * @param response
	 */
	@RequestMapping("/deldevorder")
	public void deldevorder(String deviceDealID, String dealAmount, HttpServletResponse response)
	{

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}

		try
		{
			if (deviceDealID == null || "".equals(deviceDealID))
			{
				response.getWriter().print("-1");
				return;
			}
			if (deviceDealOrdersSer.deldevorder(deviceDealID))
			{
				response.getWriter().print(deviceDealID + "|" + dealAmount);

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());

					admin.setOperateContent("已删除设备交易订单, 记录ID为: " + deviceDealID + " 金额为: " + dealAmount);
					admin.setOperateMenu("订单管理>设备交易订单删除");
					admin.setOperateType("删除");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				response.getWriter().print("0");
				return;
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

	}



	/**
	 * 删除流量订单
	 *
	 * @param flowDealID
	 * @param dealAmount
	 * @param response
	 */
	@RequestMapping("/delfloworder")
	public void delfloworder(String flowDealID, String orderAmount, HttpServletResponse response)
	{

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}

		try
		{
			if (flowDealID == null || "".equals(flowDealID))
			{
				response.getWriter().print("-1");
				return;
			}
			if (flowDealOrdersSer.delfloworder(flowDealID))
			{
				response.getWriter().print(flowDealID + "|" + orderAmount);

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());

					admin.setOperateContent("已删除流量交易订单, 记录ID为: " + flowDealID + " 金额为: " + orderAmount);
					admin.setOperateMenu("订单管理>流量交易订单删除");
					admin.setOperateType("删除");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				response.getWriter().print("0");
				return;
			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

	}



	/**
	 * 保存订单
	 *
	 * @param info
	 * @param model
	 *            1.更新总订单的流量交易数，设备交易数，是否完成，总金额 2.更新设备订单是否完成为是
	 *            3.如果一个设备交易，默认更新流量订单为已激活，流量订单是否完成更新为是 4.将设备订单对应的设备的设备状态改为使用中
	 * @return
	 */
	@RequestMapping("/saveorder")
	public void saveorder(OrdersInfo info, String floworderid, String devorderid, String SN, HttpServletResponse response, Model model)
	{

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}

		try
		{
			if (info.getOrderID() == null || "".equals(info.getOrderID()))
			{
				response.getWriter().print("-1");
				return;
			}
			if (info.getDeviceDealCount() == 0 && info.getFlowDealCount() == 0)
			{
				response.getWriter().print("-2");
				return;
			}
			// 更新订单信息
			if (ordersInfoSer.updateiffinish(info))
			{
				DeviceDealOrders dealOrders = new DeviceDealOrders();

				if (info.getDeviceDealCount() == 1)
				{
					dealOrders.setDeviceDealID(devorderid);
					if (info.getFlowDealCount() == 1)
					{
						// 绑定设备前检测SN是否支持国家
						dealOrders.setOrderStatus("使用中");
						deviceDealOrdersSer.updateiffinish(dealOrders);
						FlowDealOrders flowDealOrders = new FlowDealOrders();
						flowDealOrders.setFlowDealID(floworderid);
						flowDealOrders.setIfActivate("是");
						flowDealOrders.setSN(SN);
						flowDealOrders.setIfFinish("是");
						flowDealOrders.setActivateDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						flowDealOrders.setOrderStatus("可使用");
						flowDealOrdersSer.updateiffinish(flowDealOrders);
					}
					else
					{
						dealOrders.setOrderStatus("可使用");
						deviceDealOrdersSer.updateiffinish(dealOrders);
						String[] forderid = floworderid.split(",");
						for (int i = 0; i < forderid.length; i++)
						{
							FlowDealOrders flowDealOrders = new FlowDealOrders();
							flowDealOrders.setFlowDealID(forderid[i]);
							flowDealOrders.setIfActivate("否");
							flowDealOrders.setIfFinish("是");
							flowDealOrders.setOrderStatus("不可用");
							flowDealOrdersSer.updateiffinish(flowDealOrders);
						}
					}

					response.getWriter().print("1");
				}
				else
				{
					String[] dorderid = devorderid.split(",");
					for (int i = 0; i < dorderid.length; i++)
					{
						dealOrders.setDeviceDealID(dorderid[i]);
						dealOrders.setOrderStatus("可使用");
						deviceDealOrdersSer.updateiffinish(dealOrders);
					}
					if (info.getFlowDealCount() == 1)
					{
						FlowDealOrders flowDealOrders = new FlowDealOrders();
						flowDealOrders.setFlowDealID(floworderid);
						flowDealOrders.setIfActivate("否");
						flowDealOrders.setIfFinish("是");
						flowDealOrders.setOrderStatus("不可用");
						flowDealOrdersSer.updateiffinish(flowDealOrders);
					}
					else
					{
						String[] forderid = floworderid.split(",");
						for (int i = 0; i < forderid.length; i++)
						{
							FlowDealOrders flowDealOrders = new FlowDealOrders();
							flowDealOrders.setFlowDealID(forderid[i]);
							flowDealOrders.setIfActivate("否");
							flowDealOrders.setIfFinish("是");
							flowDealOrders.setOrderStatus("不可用");
							flowDealOrdersSer.updateiffinish(flowDealOrders);
						}
					}
					response.getWriter().print("1");
				}

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());

					admin.setOperateContent("已新增订单, 记录ID为: " + info.getOrderID());
					admin.setOperateMenu("订单管理>新增订单完毕");
					admin.setOperateType("新增");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				response.getWriter().print("0");
				return;
			}

		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * 分页查询
	 *
	 * @param searchDTO
	 * @param ordersInfo
	 * @param responses
	 */
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, OrdersInfo ordersInfo, String company, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		ordersInfo.setSysStatus(true);
		ordersInfo.setIfFinish("是");

		if (StringUtils.isNotBlank(company))
		{
			Distributor distributor = new Distributor();
			distributor.setCompany(company);
			Distributor distributor2 = distributorSer.getdisInofbycompany(distributor);
			String disID = "";
			if (distributor2 != null)
			{
				disID = distributor2.getDistributorID();
			}
			ordersInfo.setOrderID("QD" + disID);
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), ordersInfo);
		String pagesString = ordersInfoSer.getpageString(seDto);
		try
		{
			response.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 删除
	 *
	 * @param id
	 * @param response
	 */
	@RequestMapping("/deleteby/{id}")
	public void deleteby(@PathVariable String id, HttpServletResponse response)
	{

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}

		try
		{
			if (id == null || "".equals(id))
			{
				response.getWriter().print("-1");
				return;
			}
			if (ordersInfoSer.delete(id))
			{
				response.getWriter().print("1");

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());
					admin.setOperateContent("已删除订单, 记录ID为: " + id);
					admin.setOperateMenu("订单管理>删除订单");
					admin.setOperateType("删除");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return;
			}
			else
			{
				response.getWriter().print("0");
				return;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}



	/**
	 * 编辑
	 *
	 * @param ordersInfo
	 * @param response
	 * @return
	 */
	@RequestMapping("/update")
	public void update(OrdersInfo ordersInfo, HttpServletResponse response)
	{

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("请重新登录!"); // 此处留意前端这个处理
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}
		try
		{
			if (ordersInfo == null || "".equals(ordersInfo.getOrderID()))
			{
				response.getWriter().print("-1");
				return;
			}
			if (ordersInfoSer.edit(ordersInfo))
			{
				response.getWriter().print("1");

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());

					admin.setOperateContent("已修改订单, 记录ID为: " + ordersInfo.getOrderID());
					admin.setOperateMenu("订单管理>修改订单");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return;
			}
			else
			{
				response.getWriter().print("0");
				return;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 订单详细信息
	 *
	 * @param ordersID
	 * @param model
	 * @return
	 */
	@RequestMapping("/orderinfo")
	public String orderinfo(String ordersID, Model model)
	{

		if (ordersID == null || "".equals(ordersID))
		{
			model.addAttribute("mes", "订单信息加载失败");
			return RETURNROOT_STRING + "order_view";
		}
		OrdersInfo ordersInfo = ordersInfoSer.getdetail(ordersID);

		if (ordersInfo == null)
		{
			model.addAttribute("mes", "订单信息加载失败");
			return RETURNROOT_STRING + "order_view";
		}
		else
		{
			ordersInfo.setCreatorDateString(DateUtils.formatDate(ordersInfo.getCreatorDate(), "yyyy-MM-dd HH:mm:ss"));
			if (ordersInfo.getModifyDate() != null) ordersInfo.setModifyDateString(DateUtils.formatDate(ordersInfo.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
			CustomerInfo customerInfo = customerInfoSer.getOneCustomerDetail(ordersInfo.getCustomerID());
			ordersInfo.setCustomerInfo(customerInfo);

			if (StringUtils.isNotBlank(ordersInfo.getLogisticsInfo()))
			{
				String[] stringList = ordersInfo.getLogisticsInfo().split(",");
				if (stringList.length >= 1)
				{ // 2 特别要处理自提自取的情况
					String kdgs = stringList[0];

					List<Dictionary> dictionaries = dictionarySer.getalertType("快递公司");
					for (Dictionary dictionary : dictionaries)
					{
						if (dictionary.getValue().equals(kdgs))
						{
							kdgs = dictionary.getLabel();
						}
					}

					if (stringList.length >= 2)
					{
						String kdNO = stringList[1];
						ordersInfo.setLogisticsInfo(kdgs + ", 单号" + kdNO);
						model.addAttribute("ExpressNum", stringList[1]);
					}
					else
					{ // == 1
						ordersInfo.setLogisticsInfo(kdgs + ", 无单号");
					}
					model.addAttribute("DictLogistics", dictionaries);
					model.addAttribute("ExpressName", stringList[0]);
				}

			}

			model.addAttribute("order", ordersInfo);
			return RETURNROOT_STRING + "order_view";
		}

	}



	/**
	 * 根据订单ID查询设备交易
	 *
	 * @param ordersID
	 * @param model
	 * @param response
	 */
	@RequestMapping("/getdevbyoid")
	public void getdevbyoid(String ordersID, Model model, HttpServletResponse response)
	{

		try
		{
			response.setContentType("application/json;charset=UTF-8");
			List<DeviceDealOrders> deviceDealOrders = deviceDealOrdersSer.getbyoid(ordersID);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (DeviceDealOrders dos : deviceDealOrders)
			{
				JSONObject jObject = JSONObject.fromObject(dos);
				Date date = dos.getCreatorDate();
				String dString = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				jObject.remove("creatorDate");
				jObject.put("creatorDate", dString);
				jsonArray.add(jObject);
			}
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", deviceDealOrders.size());
			response.getWriter().print(object.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 根据订单ID查询流量交易
	 *
	 * @param ordersID
	 * @param model
	 * @param response
	 */
	@RequestMapping("/getflowbyoid")
	public void getflowbyoid(String ordersID, Model model, HttpServletResponse response)
	{

		try
		{
			response.setContentType("application/json;charset=UTF-8");
			List<FlowDealOrders> flowDealOrders = flowDealOrdersSer.getbyoid(ordersID);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			for (FlowDealOrders dos : flowDealOrders)
			{
				JSONObject jObject = JSONObject.fromObject(dos);
				Date date = dos.getCreatorDate();
				String dString = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				jObject.remove("creatorDate");
				jObject.put("creatorDate", dString);
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(dos.getUserCountry());
				String names = wrapper.getCountryNameStrings();
				jObject.remove("userCountry");
				jObject.put("userCountry", names);

				jsonArray.add(jObject);
			}
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", flowDealOrders.size());
			response.getWriter().print(object.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 订单统计
	 *
	 * @param ordersInfo
	 * @param response
	 */
	@RequestMapping("/ordercount")
	public void ordercount(OrdersInfo ordersInfo, String company, HttpServletResponse response)
	{

		try
		{
			response.setContentType("application/json;charset=UTF-8");

			if (StringUtils.isNotBlank(company))
			{
				Distributor distributor = new Distributor();
				distributor.setCompany(company);
				Distributor distributor2 = distributorSer.getdisInofbycompany(distributor);
				String disID = "";
				if (distributor2 != null)
				{
					disID = distributor2.getDistributorID();
				}
				ordersInfo.setOrderID("QD" + disID);
			}
			ordersInfo.setSysStatus(true);
			// 未删除的订单数
			int nodel = ordersInfoSer.statistics(ordersInfo);

			ordersInfo.setSysStatus(true);
			// 正常订单数
			int deviceDealCount = ordersInfoSer.deviceDealCount(ordersInfo);
			// 正常订单总额
			int sumnormal = ordersInfoSer.sumorder(ordersInfo);
			ordersInfo.setSysStatus(true);
			// 未完成订单数
			int flowDealCount = ordersInfoSer.flowDealCount(ordersInfo);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("sum", nodel);
			obj.put("deviceDealCount", deviceDealCount);
			obj.put("flowDealCount", flowDealCount);
			obj.put("sumnormal", sumnormal);
			jsonArray.add(obj);
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", 1);

			response.getWriter().print(object.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}



	/**
	 * 返回到deviceInfo_shipment页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/deviceshipment")
	public String returnshipment(Model model)
	{

		return "WEB-INF/views/deviceinfo/deviceInfo_shipment";
	}



	/**
	 * 出货界面查询未绑定SN的订单
	 */
	@RequestMapping("/shipment")
	public void deviceshipment(HttpServletResponse resp, SearchDTO searchDTO, OrdersInfo ordersInfo)
	{

		resp.setContentType("application/json;charset=UTF-8");
		System.out.println("---" + searchDTO.getCurPage() + "------" + searchDTO.getPageSize());
		ordersInfo.setSysStatus(true);
		ordersInfo.setIfFinish("是 ");
		ordersInfo.setShipmentsStatus("未发货");
		ordersInfo.setOrderStatus("已付款");
		ordersInfo.setBindSN(false);
		// 有赞订单同步后, 过滤出设备订单数大于零的
		ordersInfo.setDeviceDealCount(1); // 在 xml 中大于等于1
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), ordersInfo);
		String pagesString = ordersInfoSer.getpageString(seDto);
		try
		{
			resp.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 返回发货确认页面（orderinfo.jsp）这个路径命名可优化得更贴切点
	 *
	 * @param ordersID
	 * @param model
	 * @return
	 */
	@RequestMapping("/order")
	public String order(String ordersID, Model model)
	{

		if (ordersID == null || "".equals(ordersID))
		{
			model.addAttribute("mes", "订单信息加载失败");
			return RETURNROOT_STRING + "order_view";
		}
		OrdersInfo ordersInfo = ordersInfoSer.getdetail(ordersID);
		// 获取到快递公司名称
		List<Dictionary> dictionaries = ordersInfoSer.getExpress();
		if (ordersInfo == null)
		{
			model.addAttribute("mes", "订单信息加载失败");
			return RETURNROOT_STRING + "order_view";
		}
		else
		{
			ordersInfo.setCreatorDateString(DateUtils.formatDate(ordersInfo.getCreatorDate(), "yyyy-MM-dd HH:mm:ss"));
			if (ordersInfo.getModifyDate() != null) ordersInfo.setModifyDateString(DateUtils.formatDate(ordersInfo.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
			CustomerInfo customerInfo = customerInfoSer.getOneCustomerDetail(ordersInfo.getCustomerID());
			ordersInfo.setCustomerInfo(customerInfo);
			model.addAttribute("order", ordersInfo);
			model.addAttribute("dictionaries", dictionaries);
			//

			// ahming notes: 现在修复把这个页面展开的左侧菜单对应"设备批量出货"
			// http://127.0.0.1:8070/ylcyManage/orders/ordersinfo/deviceshipment
			model.addAttribute("MenuPath", "/orders/ordersinfo/deviceshipment");

			model.addAttribute("DeviceSnPrefix", Constants.DICT_DEVICE_SN); // 增强体验,
																			// 支持省略输入4位SN

			// 友好显示物流信息
			if (StringUtils.isNotBlank(ordersInfo.getLogisticsInfo()))
			{
				String[] stringList = ordersInfo.getLogisticsInfo().split(",");
				if (stringList.length >= 1)
				{ // 2 特别要处理自提自取的情况
					String kdgs = stringList[0];

					// List<Dictionary> dictionaries =
					// dictionarySer.getalertType("快递公司");
					for (Dictionary dictionary : dictionaries)
					{
						if (dictionary.getValue().equals(kdgs))
						{
							kdgs = dictionary.getLabel();
						}
					}

					if (stringList.length >= 2)
					{
						String kdNO = stringList[1];
						ordersInfo.setLogisticsInfo(kdgs + ", 单号" + kdNO);
						model.addAttribute("ExpressNum", stringList[1]);
					}
					else
					{ // == 1
						ordersInfo.setLogisticsInfo(kdgs + ", 无单号");
					}
					model.addAttribute("DictLogistics", dictionaries);
					model.addAttribute("ExpressName", stringList[0]);
				}

			}

			return RETURNROOT_STRING + "orderinfo";
		}

	}



	@RequestMapping("/okshipment")
	public void okshipment(OrdersInfo info, String floworderid, String devorderid, String SN, HttpServletResponse resp, Model model) throws IOException
	{

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				resp.getWriter().print("-5"); // .println("请重新登录!");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}
		try
		{
			// OrdersInfo ordersInfo = ordersInfoSer.getdetail(ordersID);
			resp.getWriter().print("1");
		}
		catch (Exception e)
		{
			resp.getWriter().print("0");
		}
	}



	/**
	 * 返回订单下设备信息
	 *
	 * ahming note: 暴力的命名, 这个应该系设备订单的信息.
	 *
	 * @param ordersID
	 * @param resp
	 * @param searchDTO
	 */
	@RequestMapping("/orderdetails")
	public void orderdetails(String ordersID, HttpServletResponse resp, SearchDTO searchDTO)
	{

		resp.setContentType("application/json;charset=UTF-8");
		System.out.println("---" + searchDTO.getCurPage() + "------" + searchDTO.getPageSize());
		DeviceDealOrders dealOrders = new DeviceDealOrders();
		dealOrders.setSysStatus(true);
		dealOrders.setOrderID(ordersID);
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), dealOrders);
		String pagesString = deviceDealOrdersSer.getpageString(seDto);
		try
		{
			resp.getWriter().println(pagesString);
			System.out.println("111" + pagesString);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * 官网，微信，有赞，单个进行发货（批量发货在execlInfoDatacontrl里面）
	 *
	 * @param SN
	 * @param logistics
	 * @param expresssNO
	 * @param orderID
	 * @param deviceDealCount
	 * @param resp
	 * @throws IOException
	 */
	/*
	 * @RequestMapping("/saveSN") public void saveSN(Shipment shipment, String
	 * SN, String logistics, String expresssNO, String logisticsCost, String
	 * orderID, HttpServletResponse resp) throws IOException {
	 *
	 * resp.setContentType("application/html;charset=UTF-8");
	 * resp.setCharacterEncoding("utf-8"); DeviceDealOrders devicedeal = new
	 * DeviceDealOrders(); devicedeal.setSysStatus(true);
	 * devicedeal.setOrderID(orderID); int deviceDealCount = 0; String[] SNarr =
	 * new String[] {}; if (StringUtils.isNotBlank(SN)) { SNarr = SN.split(",");
	 * String result = ""; for (int i = 0; i < SNarr.length; i++) { //
	 * 这里检查设备有没有在使用中 DeviceInfo deviceInfo = deviceInfoSer.getbysn(SNarr[i]); if
	 * (null == deviceInfo) { result += SNarr[i] + " (查无此设备) "; } else if (
	 * deviceInfo!=null && "使用中".equals(deviceInfo.getDeviceStatus())) { result
	 * += SNarr[i] + " (设备使用中) "; // ("设备使用中"); } } if (!"".equals(result)) {
	 * resp.getWriter().print("以下设备状态有问题: " + result); // 前端应该做好直接显示这种提示 return;
	 * } } try { deviceDealCount = deviceDealOrdersSer.getdealcount(devicedeal);
	 * } catch (Exception e) { e.printStackTrace(); return; } String[] sn =
	 * SN.split(","); for (int i = 0; i < sn.length; i++) { if (sn[i].length()
	 * != 15) { resp.getWriter().print("-1"); return; } } if (SN.equals("")) {
	 * sn = new String[0]; } if (sn.length > deviceDealCount) {
	 * resp.getWriter().print("0");// ("sn个数大于设备个数"); return; } if (sn.length <
	 * deviceDealCount) { resp.getWriter().print("4");// ("sn个数不能小于设备个数");
	 * return; }
	 *
	 * if (sn[0].equals("")) { resp.getWriter().print("3");// 没有填写sn return; }
	 * // 获取当前订单下的所有设备交易ID List<DeviceDealOrders> deviceDealOrders =
	 * ordersInfoSer.getDeviceDealOrdersIDByorderID(orderID); int count =
	 * ordersInfoSer.saveSN(sn, orderID, deviceDealOrders);
	 *
	 * // TODO: 更好的错误处理: 若下面的条件不成立时系什么情况? 若单个SN, 若不成立, 则前面表示失败, 所以也要 //
	 * 返回正确的结果给前端---现在没返回(现已添加); 若多个SN, 若条件不成功, 也有可能系部分成功, 部分失败, 或者全部 // 失败,
	 * 那也要考虑怎样处理 // ------------------------------------- // 优化考虑: 上面 saveSN
	 * 实现中见到也更新了总订单的发货状态为"已发货", 可考虑把下面更改物流字段的操作 // 放到改发货时间时一步完成, 即考虑把物流信息参数传递到
	 * saveSN 中. 这样使用模板和直接SN列表都能统一 // --> 但考虑到错误信息处理的情况, 所以保留这里处理,
	 * 在使用模板那边同样添加好有赞的订单判断
	 *
	 * // 更新快递信息 if (count == deviceDealOrders.size()) { int hang =
	 * ordersInfoSer.updateLogisticsInfo(expresssNO, logistics, orderID, null,
	 * deviceDealOrders); if (hang != 0 || count == sn.length) { // 一般来说,
	 * 有赞对应的总订单下总是只有一条流量或设备订单, 所以在这里处理是适合的, 不需要在前面 // ordersInfoSer.saveSN 里处理
	 * // // 如果系有赞的订单, 则通过接口同步状态到有赞 : 若它所在的有赞购物车订单宝贝已全部完毕 // --> 应该制作成一个 service
	 * 多处去调用! YouzanSyncLogisticsResult result =
	 * ordersInfoSer.syncYouzanShipmentStatus(orderID, deviceDealOrders.get(0),
	 * null, logistics, expresssNO, sn[0]); if (0 == result.getErrorCode()) {
	 * resp.getWriter().print("1"); // 成功 } else if (result.getErrorCode() ==
	 * 10) { // 正面的返回结果，前端直接跳转页面就可以 resp.getWriter().print("10"); } else if
	 * (result.getErrorCode() == 11) { // 正面的返回结果，前端直接跳转页面就可以
	 * resp.getWriter().print("11"); } else if (result.getErrorCode() == 12) {
	 * // 正面的返回结果，前端直接跳转页面就可以 resp.getWriter().print("12"); } else {
	 * resp.getWriter().print(result.getMsg()); // 系有赞的订单 } } } else {
	 * resp.getWriter().print("更新发货状态失败,请重试"); // 前端显示直接复杂信息 } // 更新工作流记录表物流信息
	 * WorkFlow workFlow = new WorkFlow(); workFlow.setOrderID(orderID); if
	 * (workFlowSer.getInfoByOrderID(workFlow) != null) {
	 * workFlow.setLogisticsCompany(logistics);// 物流公司
	 * workFlow.setExpressNO(expresssNO);// 快递单号
	 * workFlow.setWorkFlowStatus(Constants.workFlowStatus_shipment); if
	 * (workFlowSer.updateLogisticsInfo(workFlow)) {
	 * logger.info("更新工作流记录表物流信息成功！！！"); } else { logger.info("更新工作流记录表物流信息失败");
	 * } } else { // 没有找到数据不用更新物流信息 logger.info("工作流记录表中没有找到订单ID为：" + orderID +
	 * "的订单记录"); }
	 *
	 * // 到这里说明发送成功，同时将发货的信息增加到出库记录表里 int success = 0; String batchNO =
	 * DateUtils.getDate("yyyyMMddHHmmss"); for (int i = 0; i < SNarr.length;
	 * i++) { AdminUserInfo user = (AdminUserInfo)
	 * getSession().getAttribute("User");
	 * shipment.setLogisticsCost(logisticsCost); Dictionary dict = new
	 * Dictionary(); dict.setDescription("快递公司"); dict.setValue(logistics);
	 * Dictionary dictionary = dictionarySer.getDictByValue(dict);
	 * shipment.setLogisticsName(dictionary.getLabel());
	 * shipment.setShipmentDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
	 * shipment.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
	 * shipment.setCreatorUserID(user.getUserID());
	 * shipment.setCreatorUserName(user.getUserName());
	 * shipment.setExpressNO(expresssNO); shipment.setLogisticsJC(logistics);
	 * shipment.setModifyDate(null); shipment.setModifyUserID(null);
	 * shipment.setOrderID(orderID); shipment.setRemark("");
	 * shipment.setShipmentID(UUID.randomUUID().toString());
	 * shipment.setSN(SNarr[i].trim()); shipment.setSysStatus("1");
	 * shipment.setBatchNO(batchNO); if (ShipmentSer.insert(shipment)) {
	 * success++; } } if (success == SNarr.length) {
	 * logger.info("出货记录表数据插入成功！！！"); } else { logger.info("出货记录表数据插入成功！！！"); }
	 * }
	 */

	/**
	 * 返回到流量订单服务端口跳转页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/floworderServer")
	public String floworderServer(Model model)
	{

		List<Dictionary> dictionaries = dictionarySer.getAllList("SIMServer");
		model.addAttribute("dictionaries", dictionaries);
		return RETURNROOT_STRING + "floworder_server";
	}



	@RequestMapping("/returnbatchorders")
	public String returnbatchorders(Model model)
	{

		return RETURNROOT_STRING + "batchorders";
	}



	/**
	 * 确认退订此订单
	 *
	 * 退订后则订单状态由'退订中'更改为'已退订', 款项将按来源相应处理, 返还给客户. 同时, 其下的流量订单状态将设为不可用, 若带有设备的订单,
	 * 设备订单状态不会改变? 则此订单也不可再使用
	 *
	 * @param id
	 * @param response
	 */
	@RequestMapping("/cancelPaidOrder/{id}")
	public void cancelPaidOrder(@PathVariable String id, HttpServletResponse response)
	{

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5"); // "请重新登录!"
													// 前端这个处理较其他也略为不同,所以注意处理
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				// logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		try
		{
			if (id == null || "".equals(id))
			{
				response.getWriter().print("-1");
				return;
			}
			OrdersInfo orders = ordersInfoSer.getOrdersInfo(id);
			if (orders == null)
			{
				response.getWriter().print("-2"); // 查无此订单或已删除
				return;
			}
			// 只有官网或APP的订单才可退订
			if (!"退订中".equals(orders.getOrderStatus()) || !("官网".equals(orders.getOrderSource()) || "APP".equals(orders.getOrderSource())))
			{
				response.getWriter().print("-3"); // 此订单未可退订
				return;
			}

			orders.setModifyDate(new Date());
			orders.setModifyUserID(loginAdminUserInfo.getUserID());
			if (ordersInfoSer.cancelPaidOrder(orders))
			{

				// 根据订单来源相应处理款项. 在线帐号直接返回账户, 其他由客服线下处理
				// TODO: 让支付宝等支持退款, 并提供给客户一个选项退回在线账户或者退回支付宝等
				boolean returnMoneyOK = true; // 若不需要返还到在线账户, 直接跳过下面
				if ("在线账户".equals(orders.getPaymentType()))
				{
					Form formacc = new Form();
					formacc.add("modifyID", orders.getCustomerID());
					formacc.add("money", String.valueOf(orders.getOrderAmount())); // 增加
					formacc.add("customerID", orders.getCustomerID());
					// formacc.add("beforeRechargeTime",date);
					// formacc.add("beforeRechargeID",czid);
					try
					{
						String resStr = postRequestForm(getRestUrlRoot() + RestConstants.URL_ADDMONEY_UPDATECOUNT, formacc);
						if ("1".equals(resStr))
						{
							logger.debug("退订返回客户在线账户金额成功！");

							returnMoneyOK = true;

							// TODO：是否应该增加退订记录表？若没有，难以知道退款的情况，退订的情况等 在官网个人中心收支
							// 明细里也不好判断

						}
						else
						{
							logger.debug("退订返回客户在线账户金额失败！");

							returnMoneyOK = false;
						}
					}
					catch (Exception e)
					{
						logger.debug("退订返回客户在线账户金额失败！");
						logger.debug(e.getMessage());
						e.printStackTrace();

						// 作适当处理, 务必! 未成功返还到客户处
						returnMoneyOK = false;
					}

				}
				// else {
				// // 其他支付途径的
				// }

				if (returnMoneyOK)
				{ // 若不需要返回, 直接就OK
					try
					{
						response.getWriter().print("1");
					}
					catch (Exception e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try
					{
						AdminOperate admin = new AdminOperate();
						admin.setOperateID(UUID.randomUUID().toString());// id
						// admin.setCreatorDate(date);//创建时间
						admin.setCreatorUserID(loginAdminUserInfo.getUserID());// 创建人ID
						admin.setCreatorUserName(loginAdminUserInfo.getUserName());// 创建人姓名
						// admin.setOperateDate(date);//操作时间
						// admin.setSysStatus(1);

						admin.setOperateContent("已退订订单, 订单ID为: " + id);
						admin.setOperateMenu("订单管理>退订订单");
						admin.setOperateType("修改");

						adminOperateSer.insertdata(admin);
					}
					catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				else
				{ // 作一条失败操作记录!以备参考!
					try
					{
						response.getWriter().print("-4"); // 前端提示操作人
					}
					catch (Exception e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try
					{
						AdminOperate admin = new AdminOperate();
						admin.setOperateID(UUID.randomUUID().toString());// id
						// admin.setCreatorDate(date);//创建时间
						admin.setCreatorUserID(loginAdminUserInfo.getUserID());// 创建人ID
						admin.setCreatorUserName(loginAdminUserInfo.getUserName());// 创建人姓名
						// admin.setOperateDate(date);//操作时间
						// admin.setSysStatus(1);

						admin.setOperateContent("已退订订单, 但返还客户在线账户金额时出错, 订单ID为: " + id);
						admin.setOperateMenu("订单管理>退订订单");
						admin.setOperateType("修改");

						adminOperateSer.insertdata(admin);
					}
					catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				return;
			}
			else
			{
				response.getWriter().print("0"); // 失败
				return;
			}

		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			try
			{
				response.getWriter().print("0"); // 失败
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}



	@RequestMapping("/updatewxStatus/{id}")
	public void updatewxStatus(@PathVariable String id, HttpServletResponse response, HttpServletRequest request) throws IOException
	{

		int countflow = 0;
		String noendsn = "";
		// 确认sn有没有有效订单
		List<FlowDealOrders> flowdeals = new ArrayList<FlowDealOrders>();
		try
		{
			flowdeals = flowDealOrdersSer.selectwxOrderSnList(id);
			for (int i = 0; i < flowdeals.size(); i++)
			{
				System.out.println(flowdeals.get(i).SN);
				countflow = flowDealOrdersSer.getCheckSNisLiuLiang(flowdeals.get(i).SN);
				System.out.println("查询结果：" + countflow);
				if (countflow > 0)
				{
					noendsn = noendsn + flowdeals.get(i).SN + ",";
					logger.info("设备" + flowdeals.get(i).SN + "有未结束的有效流量订单！");
				}
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

		System.out.println("noendsn:" + noendsn);
		if (noendsn.length() > 15)
		{
			noendsn = noendsn.substring(0, noendsn.length() - 1);
			String[] arr = noendsn.split(",");
			List<String> listsn = Arrays.asList(arr);
			System.out.println("listsn的个数:" + listsn.size());
			response.getWriter().print(noendsn);// 通知页面 XX设备存在未结束的有效订单
		}
		else
		{

			AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
			// 1.修改流量表(修改人id，订单id)
			FlowDealOrders flow = new FlowDealOrders();
			flow.setModifyUserID(loginAdminUserInfo.getUserID());
			flow.setOrderID(id);
			int flowcount = 0;
			try
			{
				flowcount = flowDealOrdersSer.updatewxStatusFlow(flow);
				if (flowcount > 0)
				{
					// 2.修改订单表
					OrdersInfo order = new OrdersInfo();
					order.setOrderID(id);
					order.setModifyUserID(loginAdminUserInfo.getUserID());
					int countorder = 0;
					try
					{
						countorder = ordersInfoSer.updatewxOrders(order);
						if (countorder > 0)
						{
							System.out.println("微信订单确认成功！");
							response.getWriter().print("1");
						}
						else
						{
							response.getWriter().print("0");
						}
					}
					catch (Exception e)
					{
						response.getWriter().print("0");
						e.printStackTrace();
					}
				}
				else
				{
					response.getWriter().print("0");
				}
			}
			catch (Exception e)
			{
				response.getWriter().print("0");
				e.printStackTrace();
			}

		}//

	}// =========确认微信订单结束=================



	@RequestMapping("/checkwxordersecond/{id}")
	public void checkwxordersecond(@PathVariable String id, HttpServletResponse response, HttpServletRequest request) throws IOException
	{

		String orderid = id.substring(0, id.indexOf("|"));
		System.out.println("订单id是：" + orderid);
		String allsn = id.substring(id.indexOf("|") + 1, id.length());
		if (allsn.indexOf(",") > 0)
		{
			System.out.println("多个设备号：" + allsn);
		}
		else
		{

			String[] arr = allsn.split(",");
			List<String> listsn = Arrays.asList(arr);
			System.out.println("listsn的个数:" + listsn.size());
			String checkend = "";
			int flowcou = 0;
			for (int i = 0; i < listsn.size(); i++)
			{
				System.out.println("sn的遍历：" + listsn.get(i));
				// 判断两次下单是否是同一客户所为，(参数 此次订单的客户id 需通过订单orderID来查询)
				// 1.不同则不可确认订单，流程到此结束
				// 2.有相同，弹框提示是否继续（覆盖）上一个订单

				FlowDealOrders getflow = flowDealOrdersSer.getFlowData(orderid);// 获取此次确认订单的创建人id
				FlowDealOrders flowcus = new FlowDealOrders();
				flowcus.setCreatorUserID(getflow.getCreatorUserID());
				flowcus.setSN(listsn.get(i));
				int cCustomerEqual = flowDealOrdersSer.checkCustomerEqual(flowcus);// 验证两个订单是否是同一人所为
																					// 返回
																					// 1
																					// 是，返回2不是
				if (cCustomerEqual == 1)
				{
					System.out.println("是同一人所为！继续验证订单是否可确认！");// =========================必须满足两个订单是同一人的才可继续操作
					// 判断时间是否交叉 参数SN 时间
					FlowDealOrders flow = new FlowDealOrders();
					flow.setSN(listsn.get(i));// sn
					String panlUserDate = flowDealOrdersSer.getPanlUserDateFororderid(orderid);
					flow.setPanlUserDate(panlUserDate);// 计划开始时间 使用订单号查询
					int flowc = 0;
					try
					{
						flowc = flowDealOrdersSer.getcheckordertimeagain(flow);// ===============时间冲突
						if (flowc > 0)
						{
							System.out.println(listsn.get(i) + "时间冲突不可重复下单");

							// 验证国家 是否交叉 国家交叉则不可下单 （两个订单都是多个国家的时候，此处还不好验证）
							flow.setUserCountry(getflow.getUserCountry());// 使用国家
							flowcou = flowDealOrdersSer.getCountryisTwo(flow);// 参数
																				// :
																				// SN设备号、
																				// userCountry使用国家
							if (flowcou > 0)
							{
								System.out.println("时间交叉，国家也有交叉，不能下单");
								checkend = checkend + listsn.get(i) + ",";
							}
							// 此处是时间交叉，国家没交叉，可以下单（此处有 两个有效流量订单 可能会有问题）
							System.out.println(listsn.get(i) + "此处是时间交叉，国家没交叉，可以下单（此处有 两个有效流量订单 可能会有问题");
						}
						// 此处是时间不冲突直接下单
						System.out.println(listsn.get(i) + "此处是时间不冲突直接下单");
					}
					catch (Exception e)
					{
						e.printStackTrace();
						logger.info("验证重复订单时间时出错，" + e.getMessage());
					}
				}
				else
				{
					System.out.println("返回操作到此结束！弹框 另外一位客户正在使用，不可确认此订单！");
					checkend = checkend + listsn.get(i) + "|";
					// response.getWriter().print(listsn.get(i)+"另外一位客户正在使用，不可确认此订单！");
				}

			}// 循环验证结束

			// 大于15表示 有SN违反规则 不可下单 ，小于则可下单
			if (checkend.length() > 15)
			{
				if (checkend.indexOf(",") > 0)
				{
					checkend = checkend.substring(0, checkend.length() - 1);
					response.getWriter().print("设备" + checkend + "和已存在的有效订单有冲突，确认失败！");// 通知页面
																						// XX设备存在未结束的有效订单
				}
				else
				{
					checkend = checkend.substring(0, checkend.length() - 1);
					response.getWriter().print("设备" + checkend + "另外一位客户正在使用，不可确认此订单！！");// 通知页面
																							// XX设备存在未结束的有效订单
				}
			}
			else
			{

				// 继续确认订单的操作
				AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
				// 1.修改流量表(修改人id，订单id)
				FlowDealOrders flow = new FlowDealOrders();
				flow.setModifyUserID(loginAdminUserInfo.getUserID());
				flow.setOrderID(orderid);
				int flowcount = 0;
				try
				{
					flowcount = flowDealOrdersSer.updatewxStatusFlow(flow);
					if (flowcount > 0)
					{
						// 2.修改订单表
						OrdersInfo order = new OrdersInfo();
						order.setOrderID(orderid);
						order.setModifyUserID(loginAdminUserInfo.getUserID());
						int countorder = 0;
						try
						{
							countorder = ordersInfoSer.updatewxOrders(order);
							if (countorder > 0)
							{
								System.out.println("微信订单确认成功！");
								response.getWriter().print("1");
							}
							else
							{
								response.getWriter().print("0");
							}
						}
						catch (Exception e)
						{
							response.getWriter().print("0");
							e.printStackTrace();
						}
					}
					else
					{
						response.getWriter().print("0");
					}
				}
				catch (Exception e)
				{
					response.getWriter().print("0");
					e.printStackTrace();
				}
			}// 确认订单结束
		}
	}



	@RequestMapping("timeswitch")
	public String timeswitch(Model model)
	{

		model.addAttribute("simswitch", Constants.TIMING_SIMMANAGEJOB);
		model.addAttribute("jizhanswitch", Constants.TIMING_LATLONJOB);
		return RETURNROOT_STRING + "timeswitch";
	}



	/***
	 * 出货界面查询绑定过SN的订单
	 *
	 * @param resp
	 * @param searchDTO
	 * @param ordersInfo
	 * @param SN
	 */
	@RequestMapping(value = "/ISbindSN")
	public void ISbindSN(HttpServletResponse resp, SearchDTO searchDTO, OrdersInfo ordersInfo, String SN)
	{
		resp.setContentType("application/json;charset=UTF-8");

		String orderID = "";
		if (StringUtils.isNotBlank(SN))
		{
			String[] snArray = SN.split("/");
			String newsn = "";
			for (int i = 0; i < snArray.length; i++)
			{
				newsn = newsn + "'" + Constants.SNformat(snArray[i]) + "',";
			}
			SN = newsn.substring(0, newsn.length() - 1);

			DeviceDealOrders Dorders = new DeviceDealOrders();
			Dorders.setSN(SN);
			List<DeviceDealOrders> deviceDealList = ordersInfoSer.getdeviceOrderBysn(Dorders);
			for (DeviceDealOrders deviceDealOrders : deviceDealList)
			{
				orderID += orderID + "'" + deviceDealOrders.getOrderID() + "',";
			}
			if (!StringUtils.isBlank(orderID))
			{
				orderID = orderID.substring(0, orderID.length() - 1);
			}
			else
			{
				orderID = "''";
			}
		}

		ordersInfo.setOrderID(orderID);
		ordersInfo.setSysStatus(true);
		ordersInfo.setIfFinish("是 ");
		ordersInfo.setShipmentsStatus("未发货");
		ordersInfo.setOrderStatus("已付款");
		ordersInfo.setIfBindSN("是");
		ordersInfo.setDeviceDealCount(1);
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), ordersInfo);
		String pagesString = ordersInfoSer.getpageString1(seDto);
		try
		{
			resp.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	// 新下单页面部分-------------------------------------------------------
	/**
	 * 进入新下单界面
	 *
	 * @param acceptOrderID
	 * @param resp
	 */
	@RequestMapping("toNewOrder")
	public String toNewOrder(AcceptOrder acceptOrder, Model model)
	{

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<Dictionary> dictionaries = dictionarySer.getAllList(com.Manage.common.constants.Constants.DICT_CUSTOMER_SOURCE);
		model.addAttribute("Countries", countries);
		model.addAttribute("diclist", dictionaries);
		return RETURNROOT_STRING + "order_add_new";
	}



	/**
	 * 打回接单记录
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("BackAcceptOrder")
	public void BackAcceptOrder(String acceptOrderID, HttpServletResponse resp)
	{

		if (StringUtils.isBlank(acceptOrderID))
		{
			ResponPrint("-1", resp);
			return;
		}
		AcceptOrder aOrder = acceptOrderSer.getById(acceptOrderID);
		aOrder.setAcceptOrderStatus("已打回");
		if (acceptOrderSer.update(aOrder))
		{
			ResponPrint("1", resp);
		}
		else
		{
			ResponPrint("0", resp);
		}
	}



	/**
	 * 添加订单
	 *
	 * @param acceptOrderID
	 * @param resp
	 */
	@RequestMapping("addOrder")
	public void addOrder(FlowDealOrders flowDealOrders, String aoID, String hfexample, String pickUpWay, String yajin, String pullcountry, String phone, String address, String deallType, String flowNum, HttpServletResponse resp)
	{

		try
		{
			logger.info("订单工作流创建订单得到请求");
			List<CustomerInfo> customerinfo = customerInfoSer.selectOneCustomerinfoPhonetwo(phone);
			AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
			JSONObject object = new JSONObject(); // {code:-1,msg:"s",sn:"1234,2345,3242"}
			// 使用国家转化
			if (StringUtils.isNotBlank(hfexample))
			{
				hfexample = hfexample.replaceAll(",", "|");
				hfexample = hfexample.replaceAll("-", ",");
				flowDealOrders.setUserCountry(hfexample);
			}
			else
			{
				flowDealOrders.setUserCountry(pullcountry);
			}
			// 检验设备有效性
			String sNlistString = flowDealOrders.getSN();
			if (Integer.parseInt(flowNum) > 1)
			{

				if (flowDealOrders.getSN().indexOf("/") == -1)
				{
					object.put("code", -3);
					object.put("msg", "SN数目与订单个数不匹配,请检查");
					ResponPrint(object.toString(), resp);
					return;
				}
				else
				{
					String[] snarrString = sNlistString.split("/");
					if (snarrString.length != Integer.parseInt(flowNum))
					{
						object.put("code", -3);
						object.put("msg", "SN数目与订单个数不匹配,请检查");
						ResponPrint(object.toString(), resp);
					}
					else
					{
						String snstrString1 = "";
						String snstrString2 = "";
						String snstrString3 = "";
						boolean flag = true;
						// 循环校验
						logger.info("开始检测设备的有效性");
						for (int i = 0; i < snarrString.length; i++)
						{
							// 校验SN有效性
							int rcode = deviceInfoSer.verifySN(Constants.SNformat(snarrString[i]), flowDealOrders.getUserCountry());
							
							if (rcode == -1)
							{
								flag = false;
								if ("".equals(snstrString1))
								{
									snstrString1 += snarrString[i];
								}
								else
								{
									snstrString1 += "," + snarrString[i];
								}
							}
							if (rcode == -2)
							{
								flag = false;
								if ("".equals(snstrString2))
								{
									snstrString2 += snarrString[i];
								}
								else
								{
									snstrString2 += "," + snarrString[i];
								}
							}
							if (rcode == 0)
							{
								flag = false;
								if ("".equals(snstrString3))
								{
									snstrString3 += snarrString[i];
								}
								else
								{
									snstrString3 += "," + snarrString[i];
								}
							}

						}
						if (!flag)
						{
							logger.info("其中有sn不能被下单"+snstrString1 + "|" + snstrString2 + "|" + snstrString3);
							object.put("code", -4);
							object.put("msg", "其中有SN不可被下单");
							object.put("sn", snstrString1 + "|" + snstrString2 + "|" + snstrString3);
							ResponPrint(object.toString(), resp);
							return;
						}
						else
						{
							logger.info("SN校验成功！！！");
						}
					}
				}
			}
			else
			{
				int temp = deviceInfoSer.verifySN(Constants.SNformat(flowDealOrders.getSN()), flowDealOrders.getUserCountry());
				if (temp == -2)
				{
					object.put("code", -5);
					object.put("msg", "此SN是使用中");
					ResponPrint(object.toString(), resp);
					return;
				}
				if (temp == -1)
				{
					object.put("code", -5);
					object.put("msg", "此设备SN不存在,请检查是否需要录入设备!");
					ResponPrint(object.toString(), resp);
					return;
				}
				if (temp == 0)
				{
					object.put("code", -5);
					object.put("msg", "此SN或漫游卡不支持此国家");
					ResponPrint(object.toString(), resp);
					return;
				}

			}

			// 判断客户是否存在
			String customerID = UUID.randomUUID().toString();
			if (customerinfo == null || customerinfo.size() <= 0)
			{
				logger.info("没有此客户，插入客户信息");
				// 没有查到手机号，插入客户数据后再下单.
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setCustomerID(customerID);
				customerInfo.setCustomerName(flowDealOrders.getCustomerName());
				customerInfo.setPhone(phone);
				customerInfo.setAddress(address);
				try
				{
					customerInfo.setPassword(DES.toHexString(DES.encrypt("88888888", Constants.DES_KEYWEB)));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				customerInfo.setEmail("");
				customerInfo.setUsername("");
				customerInfo.setCreatorUserID(loginAdminUserInfo.getUserID());
				customerInfo.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				customerInfo.setRemark("");
				customerInfo.setSysStatus(1);// 默认就是 1
				// 插入客户信息
				int hang = customerInfoSer.insertCustomerInfo(customerInfo);
				if(hang>0){
					logger.info("插入客户信息成功");
				}else{
					logger.info("插入客户信息失败");
				}

			}
			else
			{
				customerID = customerinfo.get(0).getCustomerID();
			}

			// 创建总订单
			// 总订单 插入一条记录
			logger.info("创建总订单开始");
			OrdersInfo orders = new OrdersInfo();
			String orderID = UUID.randomUUID().toString();
			orders.setOrderID(orderID);
			orders.setAoID(aoID);
			orders.setCustomerID(customerID);
			orders.setCustomerName(flowDealOrders.getCustomerName());
			orders.setCustomerPhone(phone);
			orders.setDeviceDealCount(Integer.parseInt(flowNum));
			orders.setIfFinish("是");
			orders.setOrderStatus("已付款");
			orders.setIfBindSN("是");
			orders.setPickUpWay(pickUpWay);
			String orderSource = flowDealOrders.getOrderSource();
			if ("天猫".equals(orderSource) || "淘宝A".equals(orderSource) || "淘宝B".equals(orderSource))
			{
				orders.setOrderSource("线上网店");
			}
			else if ("其他".equals(orderID) || "线下".equals(orderSource))
			{
				orders.setOrderSource("后台");
			}
			else
			{
				orders.setOrderSource(orderSource);
			}
			orders.setShipmentsStatus("未发货");
			orders.setFlowDealCount(Integer.parseInt(flowNum));
			if ("购买".equals(deallType))
			{
				orders.setOrderAmount((flowDealOrders.getOrderAmount())  * (Integer.parseInt(flowNum))+500);
			}else{
				orders.setOrderAmount((flowDealOrders.getOrderAmount())  * (Integer.parseInt(flowNum))+Double.parseDouble(yajin));
			}
			
			orders.setOrderStatus("已付款");
			orders.setAddress(address);
			orders.setCreatorUserID(loginAdminUserInfo.getUserID());
			orders.setCreatorUserName(loginAdminUserInfo.getUserName());
			orders.setSysStatus(true);
			Boolean count = ordersInfoSer.insertinfo(orders);
			if(count){
				logger.info("总订单创建成功");
			}else{
				logger.info("总订单创建失败");
			}
			String flowDealOrderID = "";
			if (count)
			{
				// 创建设备单和流量单.
				if (Integer.parseInt(flowNum) > 1)
				{

					for (int i = 0; i < Integer.parseInt(flowNum); i++)
					{

						// 创建设备单.
						DeviceDealOrders deviceDeal = new DeviceDealOrders();
						String deviceDealID = UUID.randomUUID().toString();
						deviceDeal.setDeviceDealID(deviceDealID);
						deviceDeal.setOrderID(orderID);
						deviceDeal.setSN(Constants.SNformat(flowDealOrders.getSN().split("/")[i]));
						deviceDeal.setCustomerName(flowDealOrders.getCustomerName());
						deviceDeal.setDeviceID(""); // 此处字段没用到，留为空.
						deviceDeal.setCustomerID(customerID);
						deviceDeal.setDeallType(deallType);
						if ("购买".equals(deallType))
						{
							deviceDeal.setDealAmount(500);
						}
						else
						{
							deviceDeal.setDealAmount(Double.parseDouble(yajin));
						}
						deviceDeal.setIfFinish("是");
						deviceDeal.setIfReturn("否");

						deviceDeal.setOrderStatus("使用中");
						deviceDeal.setCreatorUserID(loginAdminUserInfo.getUserID());
						deviceDeal.setCreatorUserName(loginAdminUserInfo.getUserName());
						deviceDeal.setSysStatus(true);
						count = deviceDealOrdersSer.insertinfo(deviceDeal);
						if(count){
							logger.info("创建设备交易订单成功");
						}else{
							logger.info("创建设备交易订单失败");
						}

						// 修改设备状态为使用中
						count = deviceInfoSer.updateDeviceOrder(Constants.SNformat(flowDealOrders.getSN().split("/")[i]));
						if(count){
							logger.info("修改设备状态成功");
						}else{
							logger.info("修改设备状态失败");
						}

						// 创建流量单.
						flowDealOrderID = UUID.randomUUID().toString();
						FlowDealOrders flowDeal = new FlowDealOrders();
						flowDeal.setFlowDealID(flowDealOrderID);
						flowDeal.setOrderID(orderID);
						flowDeal.setCustomerID(customerID);
						flowDeal.setCustomerName(flowDealOrders.getCustomerName());
						flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.DateToStr(new Date())));
						flowDeal.setUserCountry(flowDealOrders.getUserCountry());
						flowDeal.setPanlUserDate(flowDealOrders.getPanlUserDate()); // 预约时间
						flowDeal.setOrderStatus("可使用");
						flowDeal.setIfFinish("是");
						flowDeal.setRemark(flowDealOrders.getRemark());
						flowDeal.setFlowDays(flowDealOrders.getFlowDays());// 流量天数
						flowDeal.setDaysRemaining(flowDealOrders.getFlowDays());// 剩余天数
						flowDeal.setOrderAmount(Double.parseDouble(flowDealOrders.getOrderAmount() + ""));// 订单金额
						if (flowDealOrders.getOrderType().equals("1") || flowDealOrders.getOrderType().equals("2"))
						{
							flowDeal.setFlowExpireDate(DateUtils.getcurDate(DateUtils.beforeNDateToString(DateUtils.parseDate(flowDealOrders.getPanlUserDate()), Integer.parseInt(flowDealOrders.getFlowDays() + ""), "yyyy-MM-dd HH:mm:ss"), 1));
						}
						else
						{
							flowDeal.setFlowExpireDate(flowDealOrders.getFlowExpireDate()); // 流量到期时间
						}
						flowDeal.setFlowTotal(flowDealOrders.getFlowTotal());
						flowDeal.setSN(Constants.SNformat(flowDealOrders.getSN().split("/")[i]));
						flowDeal.setIfActivate("是");// 是否激活
						flowDeal.setActivateDate(DateUtils.DateToStr(new Date()));// 激活时间为当前时间
						flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
						flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
						flowDeal.setFlowUsed(0);
						flowDeal.setFactoryFlag(0);// 工厂标识
						flowDeal.setOrderType(flowDealOrders.getOrderType());
						flowDeal.setCreatorUserID(loginAdminUserInfo.getUserID());
						flowDeal.setCreatorUserName(loginAdminUserInfo.getUserName());
						flowDeal.setSysStatus(true);
						flowDeal.setJourney(flowDealOrdersSer.getJourney(flowDealOrders.getJourney()));
						flowDeal.setIfVPN(flowDealOrders.getIfVPN());
						flowDeal.setOrderSource(orderSource);
						boolean success = flowDealOrdersSer.insertinfo(flowDeal);
						if(success){
							logger.info("创建流量订单成功");
						}else{
							logger.info("创建流量订单失败");
						}

					}
				}
				else
				{
					// 创建设备单

					DeviceDealOrders deviceDeal = new DeviceDealOrders();
					String deviceDealID = UUID.randomUUID().toString();
					deviceDeal.setDeviceDealID(deviceDealID);
					deviceDeal.setOrderID(orderID);
					deviceDeal.setSN(Constants.SNformat(flowDealOrders.getSN()));
					deviceDeal.setCustomerName(flowDealOrders.getCustomerName());
					deviceDeal.setDeviceID("");
					deviceDeal.setCustomerID(customerID);
					deviceDeal.setDeallType(deallType);
					if ("购买".equals(deallType))
					{
						deviceDeal.setDealAmount(500);
					}
					else
					{
						deviceDeal.setDealAmount(Double.parseDouble(yajin));
					}
					deviceDeal.setIfFinish("是");
					deviceDeal.setIfReturn("否");
					deviceDeal.setOrderStatus("使用中");
					deviceDeal.setCreatorUserID(loginAdminUserInfo.getUserID());
					deviceDeal.setCreatorUserName(loginAdminUserInfo.getUserName());
					deviceDeal.setSysStatus(true);
					count = deviceDealOrdersSer.insertinfo(deviceDeal);
					if(count){
						logger.info("创建设备交易订单成功");
					}else{
						logger.info("创建设备交易订单失败");
					}
					// 修改设备状态为使用中
					count = deviceInfoSer.updateDeviceOrder(Constants.SNformat(flowDealOrders.getSN()));
					if(count){
						logger.info("修改设备状态成功");
					}else{
						logger.info("修改设备状态失败");
					}
					// 创建流量单
					flowDealOrderID = UUID.randomUUID().toString();
					FlowDealOrders flowDeal = new FlowDealOrders();
					flowDeal.setFlowDealID(flowDealOrderID);
					flowDeal.setOrderID(orderID);
					flowDeal.setCustomerID(customerID);
					flowDeal.setCustomerName(flowDealOrders.getCustomerName());
					flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.DateToStr(new Date())));
					flowDeal.setUserCountry(flowDealOrders.getUserCountry());
					flowDeal.setPanlUserDate(flowDealOrders.getPanlUserDate()); // 预约时间
					flowDeal.setOrderStatus("可使用");
					flowDeal.setIfFinish("是");
					flowDeal.setRemark(flowDealOrders.getRemark());
					flowDeal.setFlowDays(flowDealOrders.getFlowDays());// 流量天数
					flowDeal.setDaysRemaining(flowDealOrders.getFlowDays());// 剩余天数
					flowDeal.setOrderAmount(Double.parseDouble(flowDealOrders.getOrderAmount() + ""));// 订单金额
					if (flowDealOrders.getOrderType().equals("1") || flowDealOrders.getOrderType().equals("2"))
					{
						flowDeal.setFlowExpireDate(DateUtils.getcurDate(DateUtils.beforeNDateToString(DateUtils.parseDate(flowDealOrders.getPanlUserDate()), Integer.parseInt(flowDealOrders.getFlowDays() + ""), "yyyy-MM-dd HH:mm:ss"), 1));
					}
					else
					{
						flowDeal.setFlowExpireDate(flowDealOrders.getFlowExpireDate());// 流量到期时间
					}
					flowDeal.setFlowTotal(flowDealOrders.getFlowTotal());
					flowDeal.setSN(Constants.SNformat(flowDealOrders.getSN()));
					flowDeal.setIfActivate("是");// 是否激活
					flowDeal.setActivateDate(DateUtils.DateToStr(new Date()));// 激活时间为当前时间
					flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
					flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
					flowDeal.setFlowUsed(0);
					flowDeal.setFactoryFlag(0); // 工厂标识
					flowDeal.setOrderType(flowDealOrders.getOrderType());
					flowDeal.setCreatorUserID(loginAdminUserInfo.getUserID());
					flowDeal.setCreatorUserName(loginAdminUserInfo.getUserName());
					flowDeal.setSysStatus(true);
					flowDeal.setJourney(flowDealOrdersSer.getJourney(flowDealOrders.getJourney()));
					flowDeal.setIfVPN(flowDealOrders.getIfVPN());
					flowDeal.setOrderSource(orderSource);
					boolean success = flowDealOrdersSer.insertinfo(flowDeal);
					if(success){
						logger.info("创建流量订单成功");
					}else{
						logger.info("创建流量订单失败");
					}
				}
			}

			AcceptOrder aOrder = new AcceptOrder();
			aOrder.setAcceptOrderStatus("已下单");
			aOrder.setBelowOrderDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			aOrder.setPrice(flowDealOrders.getOrderAmount());
			aOrder.setBelowOrderPer(loginAdminUserInfo.getUserName());
			String[] snarray = flowDealOrders.getSN().split("/");
			String sn = "";
			for(int i=0;i<snarray.length;i++){
				sn=sn+ Constants.SNformat(snarray[i])+",";
			}
			sn=sn.substring(0,sn.length()-1);
			aOrder.setSN(sn);
			aOrder.setIfReturn("否");

			aOrder.setAoID(aoID);
			if (acceptOrderSer.update(aOrder))
			{
				logger.info("修改接单表状态成功");
				object.put("code", "3");
				object.put("msg", "下单成功");
				resp.getWriter().print(object.toString());
			}
			else
			{
				logger.info("修改接单表状态失败");
				object.put("code", 4);
				object.put("msg", "下单成功,更新状态失败!");
				ResponPrint(object.toString(), resp);
			}

			try
			{

				logger.info("工作流记录表插入数据开始");
				WorkFlow workFlow = new WorkFlow();
				workFlow.setWorkFlowID(UUID.randomUUID().toString());
				workFlow.setAoID(aoID);
				workFlow.setReceiveOrderPer(loginAdminUserInfo.getUserName());
				workFlow.setReceiveOrderDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				workFlow.setCustomerName(flowDealOrders.getCustomerName());
				workFlow.setOrderID(orderID);
				workFlow.setCreatorOrderPer(loginAdminUserInfo.getUserName());
				workFlow.setCreatorOrderDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				workFlow.setFlowDealOrderID(flowDealOrderID);
				workFlow.setShipmentID("");
				workFlow.setShipmentPer("");
				workFlow.setShipmentDate(null);
				workFlow.setExpressNO(null);
				workFlow.setCustomerServiceID("");
				workFlow.setCustomerServicePer("");
				workFlow.setCustomerServiceDate(null);
				workFlow.setHandleFruit("");
				workFlow.setWorkFlowStatus("下单");
				workFlow.setRemark(flowDealOrders.getRemark());
				workFlow.setCreatorUserID(loginAdminUserInfo.getUserID());
				workFlow.setCreatorUserName(loginAdminUserInfo.getUserName());
				workFlow.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				workFlow.setModifyUserID(null);
				workFlow.setModifyDate(null);

				if (workFlowSer.insert(workFlow))
				{
					logger.info("工作流记录表插入数据成功！！！");

				}
				else
				{
					logger.info("工作流记录表插入数据失败！！！");
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		catch (Exception e)
		{
			JSONObject object = new JSONObject();
			object.put("code", 2);
			object.put("msg", "下单失败");
			ResponPrint(object.toString(), resp);
			logger.info("下单失败"+e.getMessage());
		}
	}


	/**
	 * 返回订单流程
	 *
	 * @return
	 */
	@RequestMapping("/gooflow")
	public String gooflow()
	{

		return "WEB-INF/views/orders/gooflow";
	}



	@RequestMapping("/queryPage")
	public void queryPage(SearchDTO searchDTO, OrdersInfo ordersInfo, String company, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		ordersInfo.setIfFinish("是 ");
		ordersInfo.setShipmentsStatus("未发货");
		ordersInfo.setOrderStatus("已付款");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), ordersInfo);
		String pagesString = ordersInfoSer.queryPage(seDto);
		try
		{
			response.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * <<订单工作流>>对来自官网 app  微信订单分配设备
	 * @param userCountry
	 * @param countryName
	 * @param SN
	 * @param orderID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/bindSNupdate")
	public void bindSNupdate(String userCountry, String countryName, String SN, String orderID, HttpServletRequest request, HttpServletResponse response) throws IOException
	{

		JSONObject object = new JSONObject();

		SN = SN.trim();

		String[] snarray = SN.split(",");

		for (int i = 0; i < snarray.length; i++)
		{
			int status = deviceInfoSer.verifySN(Constants.SNformat(snarray[i]), countryName);

			if (status != 1)
			{
				object.put("code", status);
				object.put("Msg", snarray[i]);
				response.getWriter().print(object.toString());
				return;

			}
		}

		boolean success = ordersInfoSer.bindsnupdate(SN, orderID);

		if (success)
		{
			object.put("code", "00");
			object.put("Msg", "分配设备成功");
			response.getWriter().print(object.toString());
		}
		else
		{
			object.put("code", "01");
			object.put("Msg", "分配设备失败");
			response.getWriter().print(object.toString());

		}

	}



	@RequestMapping("/Shipped")
	public void Shipped(SearchDTO searchDTO, OrdersInfo ordersInfo, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		ordersInfo.setShipmentsStatus("已发货");
		ordersInfo.setSysStatus(true);
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), "modifyDate", searchDTO.getSortOrder(), ordersInfo);
		String pagesString = ordersInfoSer.getpageString(seDto);
		try
		{
			response.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/shiping")
	public void shiping(String logisticsName, String expressNO, String SN, String orderID, String logisticsCost, String address, HttpServletResponse response, HttpServletRequest request) throws IOException
	{

		address = new String(address.getBytes("ISO-8859-1"), "UTF-8");

		String[] orderIDarray = orderID.split(",");
		int success = 0;
		for (int i = 0; i < orderIDarray.length; i++)
		{
			success += ordersInfoSer.updateLogisticsInfo(expressNO + "," + logisticsCost, logisticsName, orderID, address, null);
		}

		if (success == orderIDarray.length)
		{
			response.getWriter().print("00");
		}
		else
		{
			response.getWriter().print("01");
		}

	}



	/**
	 * 发货
	 *
	 * @param logisticsName
	 * @param expressNO
	 * @param SN
	 * @param orderID
	 * @param logisticsCost
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/consignment")
	public void consignment(String logisticsName, String expressNO, String SN, String orderID, String logisticsCost, String address, HttpServletResponse response, HttpServletRequest request) throws IOException
	{

		//address = new String(address.getBytes("ISO-8859-1"), "UTF-8");

		boolean success = ordersInfoSer.consignment(logisticsName, expressNO, SN, orderID, logisticsCost, address, request);

		if (success)
		{
			// 进来说明发货成功

			// 更新工作流记录表物流信息

			for (int i = 0; i < orderID.split(",").length; i++)
			{

				WorkFlow workFlow = new WorkFlow();

				workFlow.setOrderID(orderID.split(",")[i]);

				if (workFlowSer.getInfoByOrderID(workFlow) != null)
				{

					workFlow.setLogisticsCompany(logisticsName);// 物流公司

					workFlow.setExpressNO(expressNO);// 快递单号

					workFlow.setWorkFlowStatus(Constants.workFlowStatus_shipment);

					if (workFlowSer.updateLogisticsInfo(workFlow))
					{

						logger.info("更新工作流记录表物流信息成功！！！");

					}
					else
					{

						logger.info("更新工作流记录表物流信息失败");

					}
				}
				else
				{

					// 没有找到数据不用更新物流信息

					logger.info("工作流记录表中没有找到订单ID为：" + orderID + "的订单记录");

				}
			}

			response.getWriter().print("00");

		}
		else
		{

			// 发货失败
			response.getWriter().print("01");

		}
	}



	/**
	 * 订单工作流发货界面导入表格搜索SN
	 *
	 * @param file
	 * @param session
	 * @param req
	 * @param resp
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value = "/excelsearch", method = RequestMethod.POST)
	public String excelsearch(@RequestParam("file") MultipartFile file, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException, IOException
	{

		resp.setContentType("application/json;charset=UTF-8");
		logger.info("..导入订单得到请求..");
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File files = fi.getStoreLocation();
		String[][] result = ExcelUtils.getData(files, 1);
		logger.info("成功获取到execl");
		String SN = "";
		String newsn = "";
		int allRow = result.length;
		for (int i = 0; i < result.length; i++)
		{
			System.out.println(result[i][0]);
			if (result[i][0].equals(""))
			{
				allRow = i;
				break;
			}
		}
		for (int i = 0; i < allRow; i++)
		{
			newsn = newsn + result[i][0] + "/";
		}
		SN = newsn.substring(0, newsn.length() - 1);
		req.getSession().setAttribute("SN", SN);
		return "redirect:returnshipment";
	}



	@RequestMapping("/returnshipment")
	public String returnbatchorder(HttpServletRequest req, HttpSession session, Model model)
	{

		model.addAttribute("SN", req.getSession().getAttribute("SN"));
		req.getSession().setAttribute("SN", "");
		return "WEB-INF/views/deviceinfo/deviceInfo_shipment";
	}

}
