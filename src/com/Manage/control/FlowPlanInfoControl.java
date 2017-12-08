package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Datestring;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.FlowPlanInfo;
import com.Manage.entity.OrdersInfo;
//import com.Manage.entity.MockCountryInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/flowplan/flowplaninfo")
public class FlowPlanInfoControl extends BaseController
{
	private Logger logger = LogUtil.getInstance(FlowPlanInfoControl.class);



	/**
	 * 全部有效套餐列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/flowplan/flowplaninfo_index";

	}



	/**
	 * 分页查询 有效套餐
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, FlowPlanInfo info, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), info);
		String jsonString = flowPlanInfoSer.getPageString(seDto);
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
	 * 全部已删除套餐列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/trash")
	public String trash(HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		model.addAttribute("IsTrashView", true);
		return "WEB-INF/views/flowplan/flowplaninfo_index";

	}



	/**
	 * 分页查询 已删除套餐
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/trashdatapage")
	public void dataPageDeleted(SearchDTO searchDTO, FlowPlanInfo info, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), info);
		String jsonString = flowPlanInfoSer.getPageDeletedString(seDto);
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
	 * 分页查询某套餐套餐列表 ajax
	 * 
	 * 因为通过 like 匹配 countryList, 所以{MCC}传递值为套餐名称或者MCC都能得到期望的结果.
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage/mcc/{MCC}")
	public void datapageByMCC(@PathVariable String MCC, SearchDTO searchDTO, FlowPlanInfo info, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");

		info.setCountryList(MCC);

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), info);
		String jsonString = flowPlanInfoSer.getPageString(seDto); 
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
	 * 套餐详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String viewPlan(@PathVariable String id, Model model, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");

		FlowPlanInfo info = flowPlanInfoSer.getPlanById(id);
		if (info != null && info.getFlowPlanID() != null)
		{
			CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(info.getCountryList());
			String names = wrapper.getCountryNameStrings();
			info.setCountryList(names);

			info.setFlowSum(info.getFlowSum() / 1024); // 前端使用MB显示
			info.setFlowAlert(info.getFlowAlert() / 1024); // 前端使用MB显示

			model.addAttribute("Model", info);
		}
		else
		{
			model.addAttribute("info", "此套餐不存在或已无效!");
		}
		return "WEB-INF/views/flowplan/flowplaninfo_view";
	}



	/**
	 * 更新套餐
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String editPlan(@PathVariable String id, Model model)
	{
		FlowPlanInfo info = flowPlanInfoSer.getPlanById(id);
		if (info != null && info.getFlowPlanID() != null)
		{
			// TODO: under developing start
			// !! 约定全部套餐已按大洲的顺序排列
			// model.addAttribute("CountryList",
			// getAllCountries(info.getCountryList()));
			// under developing end

			List<CountryInfo> countries = countryInfoSer.getAll("");
			// 标记选中 selected 的项
			String countryString = info.getCountryList();
			if (!StringUtils.isBlank(countryString))
			{
				for (CountryInfo country : countries)
				{
					// MCC 都系3位, 所以不需要前后补',' --> 因为带有价格数字, 所以必须前后补','来判断
					String matchString = "," + String.valueOf(country.getCountryCode()) + ",";
					// String matchString =
					// String.valueOf(country.getCountryCode());
					if (StringUtils.contains(countryString, matchString))
					{
						country.setSelected(true);
					}
				}
			}

			model.addAttribute("CountryList", countries);

			List<Dictionary> planTypes = dictionarySer.getAllList(Constants.DICT_FLOWPLANINFO_PLAN_TYPE);
			model.addAttribute("PlanTypes", planTypes);

			info.setFlowSum(info.getFlowSum() / 1024); // 前端使用MB显示
			info.setFlowAlert(info.getFlowAlert() / 1024); // 前端使用MB显示

			model.addAttribute("Model", info);
		}
		else
		{
			model.addAttribute("info", "此套餐不存在或已无效!");
		}
		return "WEB-INF/views/flowplan/flowplaninfo_edit";
	}



	// --> 目前回收站与正常全部列表里的编辑已统一, 所以不再区分. 对应编辑完毕后的返回页面, 使用 response:Referer 去跳转
	// /**
	// * 套餐回收站 更新套餐
	// *
	// * 鉴于正常套餐和回收站中的套餐编辑有所不同， 所以区分两个接口
	// *
	// * @param id
	// * @param model
	// * @return
	// */
	// @RequestMapping("/trash/edit/{id}")
	// public String editTrashPlan(@PathVariable String id, Model model) {
	// FlowPlanInfo info = flowPlanInfoSer.getPlanById(id);
	// if (info != null && info.getFlowPlanID() != null) {
	// // TODO: under developing start
	// // !! 约定全部套餐已按大洲的顺序排列
	// // model.addAttribute("CountryList",
	// getAllCountries(info.getCountryList()));
	// // under developing end
	//
	// List<CountryInfo> countries = countryInfoSer.getAll("");
	// // 标记选中 selected 的项
	// String countryString = info.getCountryList();
	// if (!StringUtils.isBlank(countryString)) {
	// for (CountryInfo country : countries) {
	// // MCC 都系3位, 所以不需要前后补',' --> 因为带有价格数字, 所以必须前后补','来判断
	// String matchString = "," + String.valueOf(country.getCountryCode()) +
	// ",";
	// // String matchString = String.valueOf(country.getCountryCode());
	// if (StringUtils.contains(countryString, matchString)) {
	// country.setSelected(true);
	// }
	// }
	// }
	//
	// model.addAttribute("CountryList", countries);
	//
	// List<Dictionary> planTypes =
	// dictionarySer.getAllList(Constants.DICT_FLOWPLANINFO_PLAN_TYPE);
	// model.addAttribute("PlanTypes", planTypes);
	//
	// info.setFlowSum(info.getFlowSum() / 1024); // 前端使用MB显示
	// info.setFlowAlert(info.getFlowAlert() / 1024); // 前端使用MB显示
	//
	// model.addAttribute("Model", info);
	// model.addAttribute("IsTrashView", true);
	// } else {
	// model.addAttribute("info","此套餐不存在或已无效!");
	// }
	// return "WEB-INF/views/flowplan/flowplaninfo_edit";
	// }

	/**
	 * 新增套餐入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String newPlan(HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");

		// TODO: under developing start
		// !! 约定全部套餐已按大洲的顺序排列
		// model.addAttribute("CountryList", getAllCountries(""));
		// // int continents[] = {0, 1};
		// // model.addAttribute("ContinentList", continents);
		// under developing end

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("CountryList", countries);
		List<Dictionary> planTypes = dictionarySer.getAllList(Constants.DICT_FLOWPLANINFO_PLAN_TYPE);
		model.addAttribute("PlanTypes", planTypes);

		return "WEB-INF/views/flowplan/flowplaninfo_new";

	}



	// TODO: under developing start
	/**
	 * 测试获取全部套餐列表 正式版本基于真实 CountryInfo 表
	 * 
	 * @param countryString
	 *            当前套餐的套餐字段值
	 * @return List<MockCountryInfo>
	 */
	/*
	 * private List<MockCountryInfo> getAllCountries(String countryString) {
	 * List<MockCountryInfo> countries = new ArrayList<MockCountryInfo>();
	 * countries.add(new MockCountryInfo("香港", 454, 19.0, "亚洲"));
	 * countries.add(new MockCountryInfo("澳门", 455, 19.9, "亚洲"));
	 * countries.add(new MockCountryInfo("台湾", 466, 25.0, "亚洲"));
	 * countries.add(new MockCountryInfo("日本", 440, 23.0, "亚洲"));
	 * countries.add(new MockCountryInfo("泰国", 520, 39.9, "亚洲"));
	 * countries.add(new MockCountryInfo("德国", 262, 29.0, "欧洲"));
	 * countries.add(new MockCountryInfo("意大利", 222, 19.9, "欧洲"));
	 * countries.add(new MockCountryInfo("澳大利亚", 505, 49.9, "大洋洲"));
	 * 
	 * // 标记选中 selected 的项 if (!StringUtils.isBlank(countryString)) { for
	 * (MockCountryInfo country : countries) { // MCC 都系3位, 所以不需要前后补',' -->
	 * 因为带有价格数字, 所以必须前后补','来判断 String matchString = "," +
	 * String.valueOf(country.getCountryCode()) + ","; if
	 * (StringUtils.contains(countryString, matchString)) {
	 * country.setSelected(true); } } }
	 * 
	 * return countries; }
	 */
	/*
	 * private List<MockCountryInfo> getCountriesByCountryCodeString(String
	 * codeString) { List<MockCountryInfo> countries = new
	 * ArrayList<MockCountryInfo>(); countries.add(new MockCountryInfo("香港",
	 * 454, 19.0, "亚洲")); countries.add(new MockCountryInfo("澳门", 455, 19.9,
	 * "亚洲")); countries.add(new MockCountryInfo("台湾", 466, 25.0, "亚洲"));
	 * countries.add(new MockCountryInfo("日本", 440, 23.0, "亚洲"));
	 * countries.add(new MockCountryInfo("泰国", 520, 39.9, "亚洲"));
	 * countries.add(new MockCountryInfo("德国", 262, 29.0, "欧洲"));
	 * countries.add(new MockCountryInfo("意大利", 222, 19.9, "欧洲"));
	 * countries.add(new MockCountryInfo("澳大利亚", 505, 49.9, "大洋洲"));
	 * 
	 * // 标记选中 selected 的项 List<MockCountryInfo> result = new
	 * ArrayList<MockCountryInfo>(); if (StringUtils.isBlank(codeString)) {
	 * 
	 * } else { // 按统一 ,code, 的判断方法作一个修正 codeString = "," + codeString + ",";
	 * for (MockCountryInfo country : countries) { // MCC 都系3位, 所以不需要前后补',' -->
	 * 因为带有价格数字, 所以必须前后补','来判断 String matchString = "," +
	 * String.valueOf(country.getCountryCode()) + ","; if
	 * (StringUtils.contains(codeString, matchString)) { result.add(country); }
	 * } } countries.clear();
	 * 
	 * return result; }
	 */
	// under developing end

	/**
	 * 新增套餐动作
	 * 
	 * @param info
	 * @param model
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/save", method = RequestMethod.POST) public void
	 * saveAction(FlowPlanInfo info, HttpServletRequest request,
	 * HttpServletResponse response, Model model) { // TODO: 数据验证 try {
	 * request.setCharacterEncoding("utf-8"); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); }
	 * response.setContentType("text/html;charset=utf-8"); JSONObject jsonResult
	 * = new JSONObject();
	 * 
	 * boolean isInsert = false; if (StringUtils.isBlank(info.getFlowPlanID()))
	 * { isInsert = true; info.setFlowPlanID(getUUID()); info.setSysStatus(1); }
	 * 
	 * AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
	 * .getAttribute("User");
	 * 
	 * if (adminUserInfo != null) { if (isInsert) {
	 * info.setCreatorUserID(adminUserInfo.getUserID());
	 * info.setCreatorUserName(adminUserInfo.getUserName()); } else {
	 * info.setModifyUserID(adminUserInfo.getUserID()); //
	 * info.setModifyUserName(adminUserInfo.getUserName()); } } else { try {
	 * jsonResult.put("code", 2); jsonResult.put("msg", "请重新登录!");
	 * response.getWriter().println(jsonResult.toString()); //
	 * response.getWriter().println("请重新登录!"); } catch (IOException e) { // TODO
	 * Auto-generated catch block logger.debug(e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * return; }
	 * 
	 * // 处理限制套餐/流量/天数情况. 以下三个字段 ifCountryLimit ifTimeLimit ifFlowLimit //
	 * 目前仅作为保留字段, 但这里仍然按实际意义设置值 if ("天数".equals(info.getPlanType())) {
	 * info.setFlowSum(500 * 1024 * 1024); // 天数套餐, 不限流量时流量值设置为极大值 999G -> //
	 * 500G (524288000) MySQL // int(11)最大值约存 990G // 还是设为零?
	 * info.setIfTimeLimit("否"); info.setIfFlowLimit("是"); } else if
	 * ("流量".equals(info.getPlanType())) { info.setPlanDays(999); // 流量套餐,
	 * 不限天数时天数设置为极大值 999 // 还是设为零? info.setFlowSum(info.getFlowSum() * 1024); //
	 * 前端使用MB显示 info.setIfTimeLimit("是"); info.setIfFlowLimit("否"); } //
	 * 如果有既限流量又限天数的套餐, 在这里加多一种类型, 等等 info.setIfCountryLimit("否");
	 * info.setFlowAlert(info.getFlowAlert() * 1024); // 前端使用MB显示
	 * 
	 * // TODO: 处理 countryList string // List<MockCountryInfo> countries = //
	 * getCountriesByCountryCodeString(info.getCountryList()); // List<String>
	 * stringList = new ArrayList<String>(); // for (MockCountryInfo item :
	 * countries) { // stringList.add(item.getCountryName() + "," + //
	 * String.valueOf(item.getCountryCode()) + "," + //
	 * String.valueOf(item.getFlowPrice())); // } //
	 * info.setCountryList(StringUtils.join(stringList, "|"));
	 * 
	 * List<CountryInfo> countries = countryInfoSer.getAll("");
	 * info.setCountryList(CountryUtils.getCountryStringFromMCCList(
	 * info.getCountryList(), countries));
	 * 
	 * if (StringUtils.isBlank(info.getImageUrl())) {
	 * info.setImageUrl("http://m.easy2go.cn/upload/gat3tian.png"); // 目前的默认图片 }
	 * 
	 * boolean result;
	 * 
	 * // try { if (isInsert) { result = flowPlanInfoSer.insertInfo(info); }
	 * else { result = flowPlanInfoSer.updateInfo(info); } // } catch (Exception
	 * e) { // result = false; // e.printStackTrace(); // }
	 * 
	 * if (result) { logger.debug("套餐信息保存成功"); try { jsonResult.put("code", 0);
	 * jsonResult.put("msg", "成功保存套餐信息!");
	 * response.getWriter().println(jsonResult.toString()); //
	 * response.getWriter().println("成功保存套餐信息!"); } catch (IOException e) { //
	 * TODO Auto-generated catch block logger.debug(e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * try { AdminOperate admin = new AdminOperate();
	 * admin.setOperateID(UUID.randomUUID().toString());// id //
	 * admin.setCreatorDate(date);//创建时间
	 * admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
	 * admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名 //
	 * admin.setOperateDate(date);//操作时间 // admin.setSysStatus(1);
	 * 
	 * if (isInsert) { admin.setOperateContent("添加了套餐, 记录ID为: " +
	 * info.getFlowPlanID() + " 名称: " + info.getFlowPlanName()); // 操作内容
	 * admin.setOperateMenu("套餐管理>添加套餐"); // 操作菜单 admin.setOperateType("添加");//
	 * 操作类型 } else { admin.setOperateContent("修改了套餐, 记录ID为: " +
	 * info.getFlowPlanID() + " 名称: " + info.getFlowPlanName());
	 * admin.setOperateMenu("套餐管理>修改套餐"); admin.setOperateType("修改"); }
	 * 
	 * adminOperateSer.insertdata(admin); } catch (Exception e) { // TODO:
	 * handle exception e.printStackTrace(); } } else {
	 * logger.debug("套餐信息保存失败"); try { jsonResult.put("code", 1);
	 * jsonResult.put("msg", "保存套餐信息出错, 请重试!");
	 * response.getWriter().println(jsonResult.toString()); //
	 * response.getWriter().println("保存套餐信息出错, 请重试!"); } catch (IOException e) {
	 * // TODO Auto-generated catch block logger.debug(e.getMessage());
	 * e.printStackTrace(); } }
	 * 
	 * }
	 */

	@RequestMapping("/addFlowPlan")
	public void addFlowPlan(String flowPlanName, String orderType, String userDayList, String countryList1, String planPrice, String deviceDealCount, String flowSum, String speedLimit, String validPeriod, String note, HttpServletResponse response) throws IOException
	{
		AdminUserInfo user = (AdminUserInfo) getSession().getAttribute("User");
		FlowPlanInfo info = new FlowPlanInfo();
		info.setCountryList(countryList1);
		info.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		info.setCreatorUserID(user.getUserID());
		info.setCreatorUserName(user.getUserName());
		info.setFlowAlert(0);// 数据流量预警阀值(KB)
		info.setFlowDealCount(countryList1.split("-").length + "");// 相关流量交易数
		info.setFlowPlanID(UUID.randomUUID().toString());
		info.setFlowPlanName(flowPlanName);
		info.setFlowSum(0);// 数据流量(MB)
		info.setModifyDate(null);
		info.setModifyUserID(null);
		info.setNote(note);
		info.setDeviceDealCount(deviceDealCount);
		info.setPlanDays(0);
		info.setUserDayList(userDayList);
		info.setPlanPrice(Double.parseDouble(planPrice));// 金额
		info.setPlanType("天数");
		info.setRemark(null);
		if ("2".equals(orderType))
		{
			info.setSpeedLimit("");// 限速策略
		}
		else
		{
			info.setSpeedLimit(speedLimit);// 限速策略
		}
		info.setSysStatus(1);
		info.setOrderType(orderType);
		info.setValidPeriod(Integer.parseInt(validPeriod));
		JSONObject jsonResult = new JSONObject();
		if (flowPlanInfoSer.insertInfo(info))
		{
			logger.info("保存成功!!!");
			jsonResult.put("code", 0);
			jsonResult.put("msg", "保存套餐信息成功！！！");
			response.getWriter().println(jsonResult.toString());
		}
		else
		{
			logger.info("保存失败!!!");
			jsonResult.put("code", 1);
			jsonResult.put("msg", "保存套餐信息失败！！！");
			response.getWriter().println(jsonResult.toString());
		}
	}



	@RequestMapping("/editFlowPlan")
	public void editFlowPlan(String flowPlanName, String orderType, String userDayList, String flowPlanID, String countryList1, String planPrice, String deviceDealCount, String flowSum, String speedLimit, String validPeriod, String note, HttpServletResponse response) throws IOException
	{
		AdminUserInfo user = (AdminUserInfo) getSession().getAttribute("User");
		FlowPlanInfo info = new FlowPlanInfo();
		info.setFlowPlanName(flowPlanName);
		info.setOrderType(orderType);
		info.setUserDayList(userDayList);
		info.setCountryList(countryList1);
		info.setPlanPrice(Double.parseDouble(planPrice));
		info.setFlowDealCount(countryList1.split("-").length + "");// 相关流量交易数
		info.setDeviceDealCount(deviceDealCount);
		if ("2".equals(orderType))
		{
			info.setFlowSum(Integer.parseInt(flowSum));
		}
		else
		{
			info.setFlowSum(0);
		}
		info.setSpeedLimit(speedLimit);
		info.setValidPeriod(Integer.parseInt(validPeriod));
		info.setNote(note);
		info.setPlanType("天数");
		info.setModifyUserID(user.getUserID());
		info.setModifyDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		info.setFlowPlanID(flowPlanID);
		boolean success = flowPlanInfoSer.updateInfo(info);
		if (success)
		{
			logger.info("编辑成功！！！");
			response.getWriter().print("00");
		}
		else
		{
			logger.info("编辑失败！！！");
			response.getWriter().print("01");
		}
	}



	@RequestMapping("/bindSN")
	public void bindSN(String flowPlanID, String SN, HttpServletResponse response, HttpServletRequest request, Model model) throws IOException
	{
		FlowPlanInfo flowPlanInfo = flowPlanInfoSer.getPlanById(flowPlanID);
		JSONObject object = new JSONObject();
		String countryList = flowPlanInfo.getCountryList();
		IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		String[] SNArray = SN.trim().split("/");
		int successCount = 0;
		if (StringUtils.isNotBlank(countryList))
		{
			String[] countryListArray = {};
			for (int j = 0; j < SNArray.length; j++)
			{
				// 检查设备状态是否为使用中
				DeviceInfo info = deviceInfoSer.getbysn(Constants.SNformat(SNArray[j]));
				if (info == null)
				{
					map.put(SNArray[j], "没有此设备");
					continue;
				}
				else
				{
					if ("使用中".equals(info.getDeviceStatus()))
					{
						map.put(SNArray[j], "设备状态使用中");
						continue;
					}
				}

				countryListArray = countryList.split("-");
				// 总订单
				OrdersInfo orders = new OrdersInfo();
				String orderID = UUID.randomUUID().toString();
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
				Boolean orderSuccess = ordersInfoSer.insertinfo(orders);
				// 设备交易订单
				DeviceDealOrders deviceDeal = new DeviceDealOrders();
				String deviceDealID = UUID.randomUUID().toString();
				deviceDeal.setDeviceDealID(deviceDealID);
				deviceDeal.setOrderID(orderID);
				deviceDeal.setSN(Constants.SNformat(SNArray[j]));
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
				boolean deviceSuccess = deviceDealOrdersSer.insertinfo(deviceDeal);
				// 将设备状态改为使用中
				info.setDeviceStatus("使用中");
				deviceInfoSer.updatedevicestatus(info);
				for (int i = 0; i < countryListArray.length; i++)
				{
					// 流量订单
					FlowDealOrders flowDeal = new FlowDealOrders();
					flowDeal.setFlowDealID(UUID.randomUUID().toString());
					flowDeal.setOrderID(orderID);
					flowDeal.setCustomerID("10088");
					flowDeal.setCustomerName("套餐用户");
					flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss")));
					flowDeal.setUserCountry(countryListArray[i]);
					flowDeal.setPanlUserDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss")); // 预约时间+
					flowDeal.setOrderStatus("可使用");
					flowDeal.setIfFinish("是");
					flowDeal.setRemark("");
					flowDeal.setFlowPlanID(flowPlanID);
					flowDeal.setFlowPlanName(flowPlanInfo.getFlowPlanName());
					flowDeal.setFlowDays(Integer.parseInt(flowPlanInfo.getUserDayList().split("-")[i]));// 流量天数
					flowDeal.setDaysRemaining(Integer.parseInt(flowPlanInfo.getUserDayList().split("-")[i]));// 剩余天数
					flowDeal.setOrderAmount(Double.parseDouble(flowPlanInfo.getPlanPrice() + ""));// 流量订单金额

					String orderType = flowPlanInfo.getOrderType();
					if ("1".equals(orderType) || "2".equals(orderType))
					{
						flowDeal.setFlowExpireDate(DateUtils.beforeNDateToString(new Date(), Integer.parseInt(flowPlanInfo.getUserDayList().split("-")[i]), "yyyy-MM-dd HH:mm:ss"));

					}
					else if ("3".equals(orderType) || "4".equals(orderType))
					{
						int validPeriod = flowPlanInfo.getValidPeriod();
						
						Date panlUserDate = DateUtils.StrToDate(flowDeal.getPanlUserDate());
						
						String flowExpireDate = DateUtils.beforeNDateToString(panlUserDate, validPeriod, "yyyy-MM-dd HH:mm:ss");
						
						flowDeal.setFlowExpireDate(flowExpireDate);

					}

					flowDeal.setSN(Constants.SNformat(SNArray[j]));
					flowDeal.setIfActivate("是");// 是否激活
					flowDeal.setActivateDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));// 激活时间为当前时间
					flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
					flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
					flowDeal.setFlowUsed(0);
					flowDeal.setOrderType(flowPlanInfo.getOrderType());

					if ("2".equals(flowPlanInfo.getOrderType())) flowDeal.setFlowTotal(flowPlanInfo.getFlowSum() + "");
					else flowDeal.setFlowTotal(0 + "");

					flowDeal.setFactoryFlag(0);// 工厂标识
					flowDeal.setCreatorUserID(adminUserInfo.getUserID());
					flowDeal.setCreatorUserName(adminUserInfo.getUserName());
					flowDeal.setSysStatus(true);
					flowDeal.setJourney("");
					flowDeal.setIfVPN("0");
					boolean flowdealSuccess = flowDealOrdersSer.insertinfo(flowDeal);
					if (orderSuccess == true && deviceSuccess == true && flowdealSuccess == true)
					{
						successCount++;
					}
				}
			}
			if (map.size() == 0)
			{
				logger.info("绑定成功...");
				object.put("Code", "00");
				object.put("Msg", "绑定成功");
				response.getWriter().print(object.toString());
				return;
			}
		}
		else
		{
			logger.info("使用国家信息为空，不能下单");
			map.put("Code", "02");
			map.put("Msg", "此套餐使用国家信息为空，不能下单");
			response.getWriter().print(object.toString());
			return;
		}
		String result = "<p>需要绑定" + SNArray.length + "台设备，绑定成功" + (SNArray.length - map.size()) + "台，绑定失败" + map.size() + "台,以下为绑定失败信息...</p>";
		for (String key : map.keySet())
		{
			System.out.println(key + ":::" + map.get(key));
			result = result + "<p>" + key + ":" + map.get(key) + "</p>";
		}
		object.put("Code", "01");
		object.put("Msg", result);
		response.getWriter().print(object.toString());
		return;
	}



	/**
	 * 删除套餐
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public void deletePlan(@PathVariable String id, HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");
		if (StringUtils.isBlank(id))
		{
			try
			{
				response.getWriter().println("请求参数无效!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			try
			{
				response.getWriter().println("请重新登录!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		FlowPlanInfo info = new FlowPlanInfo();
		info.setFlowPlanID(id);
		info.setSysStatus(0);
		if (flowPlanInfoSer.updateInfoSysStatus(info))
		{
			try
			{
				response.getWriter().println("套餐删除成功!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			try
			{
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				admin.setOperateContent("删除了套餐, 记录ID为: " + info.getFlowPlanID() + " 名称: " + info.getFlowPlanName());
				admin.setOperateMenu("套餐管理>删除套餐");
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
			try
			{
				response.getWriter().println("套餐删除出错!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}



	/**
	 * 恢复套餐
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/restore/{id}")
	public void restorePlan(@PathVariable String id, HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id))
		{
			try
			{
				response.getWriter().println("请求参数无效!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		if (adminUserInfo == null)
		{
			try
			{
				response.getWriter().println("请重新登录!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		FlowPlanInfo info = new FlowPlanInfo();
		info.setFlowPlanID(id);
		info.setSysStatus(1);

		if (flowPlanInfoSer.updateInfoSysStatus(info))
		{
			try
			{
				response.getWriter().println("套餐恢复成功!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try
			{
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				admin.setOperateContent("恢复了套餐, 记录ID为: " + info.getFlowPlanID() + " 名称: " + info.getFlowPlanName());
				admin.setOperateMenu("套餐管理>恢复套餐");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				// TODO: handle exception
				// logger.debug("记录操作日志失败, 恢复套餐");
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				response.getWriter().println("套餐恢复出错!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

}
