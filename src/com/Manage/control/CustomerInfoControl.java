package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/customer/customerInfolist")
public class CustomerInfoControl extends BaseController
{
	private Logger logger = LogUtil.getInstance(CustomerInfoControl.class);



	@RequestMapping("/customerinfodatapage")
	public void datapage(SearchDTO searchDTO, CustomerInfo customerinfo, String distributorCompany, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		String distributorID = "";
		if (StringUtils.isNotBlank(distributorCompany))
		{
			Distributor distributor = new Distributor();
			distributor.setCompany(distributorCompany);
			distributorID = distributorSer.getdisInofbycompany(distributor).getDistributorID();
			customerinfo.setDistributorID(distributorID);
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), customerinfo);
		String jsonString = customerInfoSer.getpageString(seDto);
		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 条件查询
	 *
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("getSearchCustomerinfolist")
	public String getSearchCustomerInfoList(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException
	{

		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		// email = "%"+email+"%";
		// phone = "%"+phone+"%";
		CustomerInfo customerinfo = new CustomerInfo(email, phone);
		List<CustomerInfo> customers = customerInfoSer.getSearchCustomerInfoList(customerinfo);
		req.setAttribute("customers", customers);
		System.out.println("");
		System.out.println("[" + new Date() + "]Running >>>>>>>>>>>>>> [getSearchCustomerInfoList()]:条件查询...");
		System.out.println("");
		return "WEB-INF/views/customerinfo/CustomerInfoList";
	}



	/**
	 * 获取所有客户信息数据
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/all")
	public String getAllCustomerinfolist(HttpServletRequest req)
	{
		List<CustomerInfo> customers = customerInfoSer.getAllCustomerInfo(null, null);
		req.setAttribute("customers", customers);
		List<Distributor> disAll = distributorSer.getAll("");
		req.setAttribute("disAll", disAll);
		return "WEB-INF/views/customerinfo/CustomerInfoList";
	}



	/**
	 * 跳转到新增客户信息页面
	 *
	 * @return
	 */
	@RequestMapping("/insert")
	public String insertcus(CustomerInfo customerinfo, HttpServletResponse response, Model model, HttpServletRequest req)
	{
		response.setContentType("text/html;charset=utf-8");

		// 增加 CustomerInfo 参数接收可预先设定的字段去新建客户, 例如目前使用的"添加渠道商下客户"中的渠道商ID和渠道商名称
		// 注意在 JSP 端需要仔细处理
		// String name = customerinfo.getDistributorName();
		// if (!StringUtils.isBlank(name)) {
		// try {
		// name = new String(name.getBytes("ISO-8859-1"), "utf-8");
		// customerinfo.setDistributorName(name);

		// 获取渠道商数据
		// List<Distributor> distributor = distributorSer.getAll(null);
		Distributor dis = new Distributor();
		dis.setType("客户");
		List<Distributor> distributor = distributorSer.getbytype(dis);
		req.setAttribute("distributor", distributor);

		List<Dictionary> dictionary = dictionarySer.getAllList("客户来源");
		req.setAttribute("dictionary", dictionary);
		// } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		// model.addAttribute("cus", customerinfo);

		return "WEB-INF/views/customerinfo/CustomerInfo_add";
	}



	/**
	 * 新增 客户信息数据
	 *
	 * @throws IOException
	 */
	@RequestMapping("/insertinto")
	public void insertCustomerinfo(CustomerInfo custo, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException
	{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			try
			{
				// response.getWriter().println("请重新登录!");
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				resp.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		CustomerInfo cus = new CustomerInfo();
		cus.setCustomerID(UUID.randomUUID().toString());// 客户ID
		String cjyid = adminUserInfo.userID;
		String cjrname = adminUserInfo.userName;
		cus.setCreatorUserID(cjyid);// 创建人id

		SimpleDateFormat sdf2 = new SimpleDateFormat();// 格式化时间
		sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		cus.setCreatorDate(sdf2.format(new Date()));// 创建时间

		cus.setCustomerName(custo.getCustomerName().trim());// 客户名称

		String pwd = "88888888";// 这个是默认的密码，前台页面传过来的也是8个8，这里就不用前端传过来的默认密码，在这里从新定义然后加密
		try
		{
			pwd = DES.toHexString(DES.encrypt(pwd, Constants.DES_KEYWEB));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cus.setPassword(pwd);// 加密密码

		// cus.setPassword(custo.getPassword().trim());//未加密密码

		cus.setPhone(custo.getPhone().trim());// 电话
		cus.setEmail(custo.getEmail().trim());// email
		cus.setAddress(custo.getAddress().trim());// 地址
		cus.setRemark(custo.getRemark().trim());// 备注信息
		cus.setCustomerSource(custo.getCustomerSource());// 客户来源
		cus.setCustomerType(custo.getCustomerType());
		String disid = req.getParameter("distributorId");
		String disname = req.getParameter("distributorName");
		cus.setDistributorID(disid);// 渠道商id
		// if (!StringUtils.isBlank(custo.getDistributorID())) {
		cus.setDistributorName(disname);// 渠道商
		System.out.println(disid + " : " + disname);
		// }

		req.getSession().setAttribute("cus", cus);
		System.out.println("");
		System.out.println("[" + new Date() + "]Running >>>>>>>>>>>>>> [insertCustomerinfo() 插入客户信息数据 ]");
		System.out.println("");

		int result = customerInfoSer.insertCustomerInfo(cus);// 添加
		// resp.getWriter().print(result);

		if (result > 0)
		{
			logger.debug("客户信息保存成功");
			try
			{
				// response.getWriter().println("成功保存客户信息!");

				resp.getWriter().println("1");

				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				admin.setCreatorDate(sdf2.format(new Date()));// 创建时间
				admin.setCreatorUserID(cjyid);// 创建人ID
				admin.setCreatorUserName(cjrname);// 创建人姓名
				admin.setOperateContent("新增客户, 客户ID：" + cus.getCustomerID());// 操作内容
				admin.setOperateDate(sdf2.format(new Date()));// 操作时间
				admin.setOperateMenu("客户管理>新增客户");// 操作菜单
				admin.setOperateType("新增");// 操作类型
				admin.setSysStatus(1);
				adminOperateSer.insertdata(admin);

			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				session.removeAttribute("cus");
			}
		}
		else
		{
			logger.debug("客户信息保存失败");
			try
			{
				// response.getWriter().println("保存客户信息出错, 请重试!");

				resp.getWriter().println("0");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				session.removeAttribute("cus");
			}
		}

		// return "redirect:all";
	}



	/**
	 * 查看客户信息详情
	 *
	 * @param cusid
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "customerInfoDetail", method = RequestMethod.GET)
	public String searchCustomerInfo(@RequestParam String cusid, HttpServletRequest req)
	{
		CustomerInfo cus = customerInfoSer.getOneCustomerDetail(cusid);

		String creatorid = customerInfoSer.selectAdmin(cus.getCreatorUserID());// 根据创建者ID获取创建者名称
		System.out.println("=============================data:" + creatorid);
		if (creatorid == null)
		{
			cus.setCreatorUserID("");// 把ID替换成名称
		}
		else
		{
			cus.setCreatorUserID(creatorid);// 把ID替换成名称
		}

		req.setAttribute("cus", cus);
		System.out.println("");
		System.out.println("[" + new Date() + "]Running >>>>>>>>>>>>>> [getOneCustomerDetail() 查看客户信息详情]: select customerinfo data...");
		System.out.println("");
		return "WEB-INF/views/customerinfo/CustomerInfoDetail";
	}



	/**
	 * 根据电话查询
	 *
	 * @param cusid
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "customerInfockPhone", method = RequestMethod.POST)
	public void searchCustomerInfoPhone(CustomerInfo customerinfo, HttpServletRequest req, HttpServletResponse resp)
	{
		System.out.println("验证号码：" + customerinfo.getPhone());
		int cus = customerInfoSer.getOneCustomerPhone(customerinfo.getPhone());
		if (cus >= 1)
		{
			req.setAttribute("messagephone", " 此号码已经被注册过!");
		}
		try
		{
			resp.getWriter().print(cus);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return "WEB-INF/views/customerinfo/CustomerInfo_add";
	}



	/**
	 * 根据客户ID获取需要修改客户信息
	 */
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String UpdateCustomerinfo(@RequestParam String uid, HttpServletRequest req)
	{
		CustomerInfo cus = customerInfoSer.getOneCustomerUpdate(uid);
		req.setAttribute("cus", cus);
		System.out.println("");
		System.out.println("[" + new Date() + "]Running >>>>>>>>>>>>>> [UpdateCustomerinfo() 获取需修改的数据]:strat select to update customerinfo data...");
		System.out.println("");
		return "WEB-INF/views/customerinfo/CustomerInfo_update";
	}



	/**
	 * 修改客户信息
	 */
	@RequestMapping(value = "updateToCustomerInfo", method = RequestMethod.POST)
	public void toUpdateCustomer(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws UnsupportedEncodingException
	{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			try
			{
				// response.getWriter().println("请重新登录!");
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				resp.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		String id = req.getParameter("customerID").trim();
		String name = req.getParameter("customerName").trim();
		String phone = req.getParameter("phone").trim();
		String email = req.getParameter("email").trim();
		String address = req.getParameter("address").trim();
		String remark = req.getParameter("remark").trim();
		String customerType = req.getParameter("customerType").trim();
		String isVIP = req.getParameter("isVIP").trim();

		String xgr = adminUserInfo.userName;
		String updateid = adminUserInfo.userID;// 修改人ID
		// System.out.println("=======================修改人："+xgr+"==========修改人ID："+updateid);

		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		Date date1 = new Date();// 获取当前时间
		// System.out.println("现在时间：" + sdf.format(date1));//
		// 输出已经格式化的现在时间（24小时制）
		String date = sdf.format(date1).toString();

		// System.out.println("id:"+id+"——name:"+name+"——phone:"+phone+"——address:"+address+"——remark:"+remark+"——："+date);
		CustomerInfo cus = new CustomerInfo(id, name, phone, email, address, remark, updateid, date, isVIP);
		cus.setCustomerType(customerType);

		// System.out.println("");
		// System.out.println("["+new
		// Date()+"]Running >>>>>>>>>>>>>> [toUpdateCustomerinfo() 修改数据]:run select to update customerinfo data...");
		// System.out.println("");

		int result = customerInfoSer.updateCustomerInfo(cus);
		// req.setAttribute("cus", cus);

		if (result > 0)
		{
			logger.debug("客户信息修改成功");
			try
			{
				// response.getWriter().println("成功修改客户信息!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功修改客户信息!");
				resp.getWriter().println(jsonResult.toString());

				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				admin.setCreatorDate(date);// 创建时间
				admin.setCreatorUserID(updateid);// 创建人ID
				admin.setCreatorUserName(xgr);// 创建人姓名
				admin.setOperateContent("修改客户信息，客户ID：" + id);// 操作内容
				admin.setOperateDate(date);// 操作时间
				admin.setOperateMenu("客户管理>修改客户");// 操作菜单
				admin.setOperateType("修改");// 操作类型
				admin.setSysStatus(1);
				adminOperateSer.insertdata(admin);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			logger.debug("客户信息修改失败");
			try
			{
				// response.getWriter().println("修改客户信息出错, 请重试!");
				jsonResult.put("code", 1);
				jsonResult.put("msg", "修改客户信息出错, 请重试!");
				resp.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		// return "redirect:all";
	}



	/**
	 * 删除客户信息
	 *
	 */
	@RequestMapping("deleteCustomerInfo")
	public String deleteCustomerInfo(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
	{
		logger.info("进入删除客户数据。。。id:" + req.getParameter("sysStatus_id"));
		String id = req.getParameter("sysStatus_id");
		System.out.println("");
		int delecount = 0;
		try
		{
			delecount = customerInfoSer.deleteCustomerInfo(id);
			if (delecount > 0)
			{
				logger.info("删除成功！id:" + req.getParameter("sysStatus_id"));
				// 删除ip电话的client数据，sysStatus=0
				int deleip = 0;
				try
				{
					deleip = ipPhoneClientSer.deleteIpClient(id);
					if (deleip > 0)
					{
						logger.info("删除ip电话数据成功！");
					}
					else
					{
						logger.info("删除ip电话数据失败！");
					}
				}
				catch (Exception e)
				{
					logger.info("删除ip电弧数据时出错！");
					e.printStackTrace();
				}
			}
			else
			{
				logger.info("删除失败！");
			}
		}
		catch (Exception e1)
		{
			logger.info("删除客户信息时出错！");
			e1.printStackTrace();
		}

		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		Date date1 = new Date();// 获取当前时间
		String date = sdf.format(date1).toString();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		String userid = adminUserInfo.userID;
		String username = adminUserInfo.userName;

		try
		{
			AdminOperate admin = new AdminOperate();
			admin.setOperateID(UUID.randomUUID().toString());// id
			admin.setCreatorDate(date);// 创建时间
			admin.setCreatorUserID(userid);// 创建人ID
			admin.setCreatorUserName(username);// 创建人姓名
			admin.setOperateContent("删除客户信息，客户ID：" + id);// 操作内容
			admin.setOperateDate(date);// 操作时间
			admin.setOperateMenu("客户管理>删除客户");// 操作菜单
			admin.setOperateType("删除");// 操作类型
			admin.setSysStatus(1);
			adminOperateSer.insertdata(admin);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		return "redirect:all";
	}



	/**
	 * 客户全部订单
	 *
	 * @param uid
	 * @param req
	 * @return
	 */
	@RequestMapping("/OrdersInfo")
	public String OrdersInfo(@RequestParam String uid, OrdersInfo ordersInfo, Model model, HttpServletRequest req, HttpServletResponse resp)
	{
		resp.setContentType("text/html;charset=utf-8");

		OrdersInfo queryInfo = new OrdersInfo();
		if (StringUtils.isNotBlank(ordersInfo.getOrderStatus()))
		{
			queryInfo.setOrderStatus(ordersInfo.getOrderStatus());
			model.addAttribute("CurrentStatus", ordersInfo.getOrderStatus());
		}
		queryInfo.setCustomerID(uid);
		model.addAttribute("UID", uid);

		List<OrdersInfo> orders = ordersInfoSer.getOrdersInfoforCustomer(queryInfo);
		req.setAttribute("orders", orders);

		List<Dictionary> orderStatus = dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSTATUS);
		model.addAttribute("OrderStatusDict", orderStatus);

		System.out.println("");
		System.out.println("[" + new Date() + "]Running >>>>>>查看客户全部订单>>>>>>>> OrdersInfo ");
		System.out.println("");
		return "WEB-INF/views/customerinfo/CustomerInfo_orders";
	}



	/**
	 * 根据客户ID查询【客户使用的设备】信息
	 *
	 * @param uid
	 * @param req
	 * @return
	 */
	@RequestMapping("/DeviceInfo")
	public String CustomerInfoDevice(@RequestParam String uid, HttpServletRequest req)
	{
		List<DeviceDealOrders> devicedealorders = deviceDealOrdersSer.getCustomerIdDevice(uid);
		req.setAttribute("devicedealorders", devicedealorders);
		System.out.println("");
		System.out.println("[" + new Date() + "]Running >>>>>>>>>>>>>> 根据客户ID查询客户的设备...");
		System.out.println("");
		return "WEB-INF/views/customerinfo/CustomerInfo_device";
	}



	/**
	 * 根据客户ID查询【数据交易】信息
	 *
	 * @param uid
	 * @param req
	 * @return
	 */
	@RequestMapping("ordersfloworder")
	public String customer_ordersfloworder(@RequestParam String uid, HttpServletRequest req)
	{
		List<FlowDealOrders> flowdealorders = flowDealOrdersSer.getbyCustomerid(uid);
		req.setAttribute("flowdealorders", flowdealorders);
		System.out.println("[" + new Date() + "]Running >>>>>>>>>>>>>> 根据客户ID查询【数据交易】信息...");
		return "WEB-INF/views/customerinfo/CustomerInfo_ordersfloworder";
	}

	// 查看客户所有订单
	// @RequestMapping("/OrdersInfo")
	// public String customerOrderSinfo(@RequestParam String uid,
	// HttpServletRequest req){
	// List<OrdersInfo> ordersinfo = customerInfoSer.getOrdersInfo(uid);
	// req.setAttribute("ordersinfo", ordersinfo);
	// System.out.println("");
	// System.out.println("["+new
	// Date()+"]Running >>>>>>>>>>>>>> [customerOrderSinfo() 查看客户订单]:strat select OrdersInfo data...");
	// System.out.println("");
	// return "WEB-INF/views/customerinfo/orderinfo_list";
	// }
	String id = "";



	@RequestMapping("/SearchCustomerInfo")
	public String SearchCustomerInfo(HttpServletResponse response, HttpServletRequest req)
	{

		String data = req.getParameter("cusdata");
		try
		{
			data = java.net.URLDecoder.decode(data, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CustomerInfo cs = new CustomerInfo();
		cs.setCustomerName(data);
		CustomerInfo cus = customerInfoSer.searchOnePersonDataSer(cs);
		if (cus == null)
		{
			req.getSession().setAttribute("searchPerson", "没有此客户的信息！");

			if (id.length() == 0)
			{
				id = req.getHeader("Referer").substring(req.getHeader("Referer").indexOf("=") + 1, req.getHeader("Referer").length());
				cus = customerInfoSer.getOneCustomerDetail(id);
				req.setAttribute("cus", cus);
			}
			else if (id.length() > 0)
			{
				cus = customerInfoSer.getOneCustomerDetail(id);// 根据创建者ID获取创建者名称
				req.setAttribute("cus", cus);
			}
			return "WEB-INF/views/customerinfo/CustomerInfoDetail";
		}
		else if (cus != null)
		{
			req.setAttribute("cus", cus);
			req.getSession().removeAttribute("searchPerson");
			return "WEB-INF/views/customerinfo/CustomerInfoDetail";
		}
		return "WEB-INF/views/customerinfo/CustomerInfoDetail";

		// return "WEB-INF/views/customerinfo/CustomerinfoSearchList";
	}



	@RequestMapping("/SearchResult")
	public void SearchCusResult(SearchDTO searchDTO, CustomerInfo customerinfo, HttpServletResponse response, HttpServletRequest req) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");
		req.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 通过URL传递过来的值是通过两次转码，在这边需要先或取到值再转码，然后给对应的字段赋值就可以了。（解决搜索中文乱码问题）
		customerinfo.setCustomerName(java.net.URLDecoder.decode(customerinfo.getCustomerName(), "UTF-8"));
		System.out.println("---" + searchDTO.getCurPage() + "------" + searchDTO.getPageSize());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), customerinfo);
		String jsonString = customerInfoSer.getpageString(seDto);
		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/customerinfoCount")
	public String CustomerInfoCount()
	{
		return "WEB-INF/views/customerinfo/customerInfo_count";
	}



	/**
	 * 客户统计
	 *
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 */
	@RequestMapping("/customerinfoCountResult")
	public void CustomerInfoCountend(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
	{

		try
		{
			resp.setContentType("application/json;charset=UTF-8");

			CustomerInfo cusall = new CustomerInfo();
			cusall.setBegindate(req.getParameter("begindate"));
			cusall.setEnddate(req.getParameter("enddate"));
			int allCount = customerInfoSer.statisticscountser(cusall);

			CustomerInfo deleall = new CustomerInfo();
			deleall.setBegindate(req.getParameter("begindate"));
			deleall.setEnddate(req.getParameter("enddate"));
			deleall.setSysStatus2("0");
			int deleteCount = customerInfoSer.statisticscountser(deleall);

			CustomerInfo weball = new CustomerInfo();
			weball.setBegindate(req.getParameter("begindate"));
			weball.setEnddate(req.getParameter("enddate"));
			weball.setCustomerSource("官网");
			weball.setSysStatus2("1");
			int resourceWeb = customerInfoSer.statisticscountser(weball);

			CustomerInfo appall = new CustomerInfo();
			appall.setBegindate(req.getParameter("begindate"));
			appall.setEnddate(req.getParameter("enddate"));
			appall.setCustomerSource("APP");
			appall.setSysStatus2("1");
			int resourceApp = customerInfoSer.statisticscountser(appall);

			CustomerInfo taobaoall = new CustomerInfo();
			taobaoall.setBegindate(req.getParameter("begindate"));
			taobaoall.setEnddate(req.getParameter("enddate"));
			taobaoall.setCustomerSource("淘宝");
			taobaoall.setSysStatus2("1");
			int resourceTaobao = customerInfoSer.statisticscountser(taobaoall);

			CustomerInfo othersall = new CustomerInfo();
			othersall.setBegindate(req.getParameter("begindate"));
			othersall.setEnddate(req.getParameter("enddate"));
			othersall.setCustomerSource("其他");
			othersall.setSysStatus2("1");
			int resourceOthers = customerInfoSer.statisticscountser(othersall);

			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("allCount", allCount);
			obj.put("deleteCount", deleteCount);
			obj.put("resourceWeb", resourceWeb);
			obj.put("resourceApp", resourceApp);
			obj.put("resourceTaobao", resourceTaobao);
			obj.put("resourceOthers", resourceOthers);
			jsonArray.add(obj);
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", 1);
			System.out.println(allCount + " / " + deleteCount + " / " + resourceWeb + " / " + resourceApp + "  /  " + resourceTaobao);
			resp.getWriter().print(object.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	// ---
	@RequestMapping("/returnselectwifi")
	public String returnselectwifi(Model model)
	{
		return "WEB-INF/views/customerinfo/selectwifi";
	}



	@RequestMapping("/selectwifipsw")
	public void selectwifipsw(String SN, HttpServletResponse response)
	{
		JSONObject object = new JSONObject();
		String result = "";
		String mSN = Constants.SNformat(SN);
		if (mSN == null)
		{
			result = "设备不存在";
		}
		else if (mSN.length() == 15)
		{
			String sId = mSN.substring(mSN.length()-4, mSN.length());
			int iId = Integer.parseInt(sId);
			int mulId = iId * iId;
			int addId = mulId + 12345678;
			int reMain = addId % 100000000;
			if (reMain < 10000000)
			{
				reMain =reMain +10000000;
			}
			result = String.valueOf(reMain);
		}
		object.put("result", result);
		try
		{
			response.getWriter().print(object.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		String mPassword = "";
		String mSN="860172008149748";
		if(mSN.length()>4){
			String sId = mSN.substring(mSN.length()-4, mSN.length());
			int iId = Integer.parseInt(sId);
			int mulId = iId * iId;
			int addId = mulId + 12345678;
			int reMain = addId % 100000000;
			if (reMain < 10000000)
			{
				reMain =reMain +10000000;
			}
			mPassword = String.valueOf(reMain);
			System.out.println(mPassword);
		} else {
			System.out.println("1");
		}

	}
}