package com.Manage.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.ExcelUtils.SIMInfoImportConstants;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.common.util.ByteUtils.StringValueConversionException;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.SIMAndSN;
import com.Manage.entity.SIMCardUsageRecord;
import com.Manage.entity.SIMCost;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMRecord;
import com.Manage.entity.SIMServer;
import com.Manage.entity.SIMcostStatistics;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.SimStatusStatByCountry;
import com.Manage.entity.report.SimStatusStatByOperator;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
@RequestMapping("/sim/siminfo")
public class SIMInfoControl extends BaseController {
	private final Logger logger = LogUtil.getInstance(SIMInfoControl.class);

	/**
	 * 分页查询SIM卡列表
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/index")
	public String index(String countryName, String cardZT, String MCC,
			HttpServletResponse response, HttpServletRequest request,
			Model model) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		if (countryName != null) {
			countryName = new String(java.net.URLDecoder.decode(countryName,
					"utf-8").getBytes("ISO-8859-1"), "utf-8");
		}
		if (cardZT != null) {
			cardZT = new String(java.net.URLDecoder.decode(cardZT, "utf-8")
					.getBytes("ISO-8859-1"), "utf-8");
		}
		/*
		 * if(MCC!=null){
		 * 
		 * }
		 */
		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");
		//渠道商
		List<Distributor> distributors=distributorSer.getAll("");
		model.addAttribute("Countries", countries);
		model.addAttribute("cardZT", cardZT);
		model.addAttribute("SIMServers", simServers);
		model.addAttribute("distributors", distributors);
		List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
		model.addAttribute("CardStatusDict", cardStatus);
		model.addAttribute("countryName", countryName);
		model.addAttribute("MCC", MCC);
		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/sim/siminfo_index";

	}

	/**
	 * 分页查询流量或余额预警SIM卡列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index/alert")
	public String indexOfAlert(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");

		model.addAttribute("Countries", countries);
		model.addAttribute("SIMServers", simServers);
		List<Dictionary> cardStatus = dictionarySer
				.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
		model.addAttribute("CardStatusDict", cardStatus);

		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/sim/siminfo_index_alert";

	}

	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, SIMInfo info,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		// 可通过参数获取IP地址过滤
		String serverIP = request.getParameter("serverIP");
		if (!StringUtils.isBlank(serverIP)) {
			info.setServerIP(serverIP);
		}

		// 可通过参数进行流量或余额预警过滤
		// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB 修改为预警剩余流量小于50M的 (07-24)
		String op = request.getParameter("op");
		if ("getAlert".equals(op)) {
			info.setCardBalance(Constants.simInfo_MinCardBalanceWarning);
			info.setPlanRemainData(Constants.simInfo_MinPlanRemainDataWarning); // 单位KB
		}

		// // 可通过参数国家ID过滤 待添加
		// String MCC = request.getParameter("mcc");
		// if (!StringUtils.isBlank(MCC)) {
		// info.setMCC(MCC);
		// }

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 全部已删除记录入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/trash")
	public String trash(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");

		model.addAttribute("Countries", countries);
		model.addAttribute("SIMServers", simServers);
		List<Dictionary> cardStatus = dictionarySer
				.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
		model.addAttribute("CardStatusDict", cardStatus);

		model.addAttribute("IsTrashView", true);
		return "WEB-INF/views/sim/siminfo_index";

	}

	/**
	 * 分页查询 已删除
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/trashdatapage")
	public void dataPageDeleted(SearchDTO searchDTO, SIMInfo info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getPageDeletedString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * SIM卡详情 by ID
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		SIMInfo info = simInfoSer.getById(id);

		if (StringUtils.isNotBlank(info.getSlotNumber())) {
			String[] strings = info.getSlotNumber().split("-");
			info.setSlotNumber(strings[0] + "-"
					+ (Integer.parseInt(strings[1]) + 1));
		}

		if (info != null && info.getSIMinfoID() != null) {
			if (StringUtils.isBlank(info.getMCC())) {
				model.addAttribute("Model_MCCCountryName", "");
			} else {
				CountryInfo country = countryInfoSer.getByMCC(info.getMCC());
				model.addAttribute("Model_MCCCountryName",
						country.getCountryName());
			}
			if (StringUtils.isBlank(info.getServerIP())) {
				model.addAttribute("Model_serverCode", "");
			} else {
				model.addAttribute("Model_serverCode",
						StringUtils.right(info.getServerIP(), 3));
			}

			List<CountryInfo> countries = countryInfoSer.getAll("");
			List<SIMServer> simServers = simServerSer.getAll("");

			if (!StringUtils.isBlank(info.getCountryList())) {
				info.setCountryList(CountryUtils
						.getCountryNamesFromSiminfoCountryList(
								info.getCountryList(), countries));
			}

			model.addAttribute("Model", info);
			model.addAttribute("Countries", countries);
			model.addAttribute("SIMServers", simServers);
		} else {
			model.addAttribute("info", "此ID的SIM卡不存在或已无效!");
		}
		return "WEB-INF/views/sim/siminfo_view";
	}

	/**
	 * 根据查询条件显示某张SIM卡. 这系比 /view/{id} 接口更通用的接口. 目前支持的有通过 IMSI 和 SIMInfoID -->
	 * 目前简化, 因为有一个现成的 getByImsi接口, 先使用它 后期有需要再通用实现
	 * 
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping("/view")
	public String viewInfo(SIMInfo info, Model model) {

		SIMInfo result;
		if (info != null && StringUtils.isNotBlank(info.getIMSI())) {
			result = simInfoSer.getByImsi(info.getIMSI());

			if (StringUtils.isNotBlank(info.getSlotNumber())) {
				String[] strings = result.getSlotNumber().split("-");
				result.setSlotNumber(strings[0] + "-"
						+ (Integer.parseInt(strings[1]) + 1));
			}

			model.addAttribute("Model", result);
		} else {
			model.addAttribute("info", "此IMSI的SIM卡不存在或已无效!");
		}
		return "WEB-INF/views/sim/siminfo_view";
	}

	/**
	 * 更新入口
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		SIMInfo info = simInfoSer.getById(id);
		if (info != null && info.getSIMinfoID() != null) {
			if (StringUtils.isNotBlank(info.getSlotNumber())) {
				String[] strings = info.getSlotNumber().split("-");
				info.setSlotNumber(strings[0] + "-"
						+ (Integer.parseInt(strings[1]) + 1));
			}
			List<CountryInfo> countries = countryInfoSer.getAll("");
			// 标记选中 selected 的项
			String countryString = info.getCountryList();
			if (!StringUtils.isBlank(countryString)) {
				for (CountryInfo country : countries) {
					// MCC 都系3位, 所以不需要前后补',' --> 因为带有价格数字, 所以必须前后补','来判断
					// --> 因为 SIMInfo 的值形如 455|456 , 所以不必要去前后补','
					// String matchString = "," +
					// String.valueOf(country.getCountryCode()) + ",";
					String matchString = String.valueOf(country
							.getCountryCode());
					if (StringUtils.contains(countryString, matchString)) {
						country.setSelected(true);
					}
				}
			}
			model.addAttribute("Countries", countries);

			List<SIMServer> simServers = simServerSer.getAll("");
			model.addAttribute("SIMServers", simServers);

			List<Dictionary> cardStatus = dictionarySer
					.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
			model.addAttribute("CardStatusDict", cardStatus);
			List<Dictionary> simHandleTypeDict = dictionarySer
					.getAllList(Constants.SIMINFO_HANDLE_TYPE);
			model.addAttribute("SimHandleTypeDict", simHandleTypeDict);

			List<Dictionary> simBillMethods = dictionarySer
					.getAllList(Constants.DICT_SIMINFO_SIM_BILL_METHOD);
			model.addAttribute("SimBillMethodDict", simBillMethods);

			List<Distributor> distributors = distributorSer.getAll(null);
			model.addAttribute("distributors", distributors);

			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info", "此SIM卡不存在或已无效!");
		}
		return "WEB-INF/views/sim/siminfo_edit";
	}

	// --> 目前回收站与正常全部列表里的编辑已统一, 所以不再区分. 对应编辑完毕后的返回页面, 使用 response:Referer 去跳转
	//
	// /**
	// * 回收站中更新记录
	// *
	// * 鉴于正常记录和回收站中的记录编辑有所不同， 所以区分两个接口
	// *
	// * @param id
	// * @param model
	// * @return
	// */
	// @RequestMapping("/trash/edit/{id}")
	// public String editTrash(@PathVariable String id, Model model) {
	// SIMInfo info = simInfoSer.getById(id);
	// if (info != null && info.getSIMinfoID() != null) {
	// List<CountryInfo> countries = countryInfoSer.getAll("");
	// List<SIMServer> simServers = simServerSer.getAll("");
	// // 标记选中 selected 的项
	// String countryString = info.getCountryList();
	// if (!StringUtils.isBlank(countryString)) {
	// for (CountryInfo country : countries) {
	// // MCC 都系3位, 所以不需要前后补',' --> 因为带有价格数字, 所以必须前后补','来判断
	// // --> 因为 SIMInfo 的值形如 455|456 , 所以不必要去前后补','
	// // String matchString = "," + String.valueOf(country.getCountryCode()) +
	// ",";
	// String matchString = String.valueOf(country.getCountryCode());
	// if (StringUtils.contains(countryString, matchString)) {
	// country.setSelected(true);
	// }
	// }
	// }
	//
	// model.addAttribute("Countries", countries);
	// model.addAttribute("SIMServers", simServers);
	// List<Dictionary> cardStatus =
	// dictionarySer.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
	// model.addAttribute("CardStatusDict", cardStatus);
	// List<Dictionary> simTypes =
	// dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_TYPE);
	// model.addAttribute("SIMTypeDict", simTypes);
	// List<Dictionary> simBillMethods =
	// dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_BILL_METHOD);
	// model.addAttribute("SimBillMethodDict", simBillMethods);
	//
	// model.addAttribute("Model", info);
	// model.addAttribute("IsTrashView", true);
	// } else {
	// model.addAttribute("info","此记录不存在或已无效!");
	// }
	// return "WEB-INF/views/sim/siminfo_edit";
	// }

	/**
	 * 新增记录入口
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/new")
	public String newEntity(HttpServletRequest request,
			HttpServletResponse response, Model model)
			throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		List<Dictionary> cardStatus = dictionarySer
				.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
		model.addAttribute("CardStatusDict", cardStatus);
		// List<Dictionary> simTypes =
		// dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_TYPE);
		// model.addAttribute("SIMTypeDict", simTypes);
		List<Dictionary> simBillMethods = dictionarySer
				.getAllList(Constants.DICT_SIMINFO_SIM_BILL_METHOD);
		model.addAttribute("SimBillMethodDict", simBillMethods);
		List<Dictionary> simHandleTypeDict = dictionarySer
				.getAllList(Constants.SIMINFO_HANDLE_TYPE);
		model.addAttribute("SimHandleTypeDict", simHandleTypeDict);

		// 可设定缺省值, 但同时要在前端做好处理. 目前仅完成了按服务器IP
		SIMInfo info = new SIMInfo();
		String serverIP = request.getParameter("serverIP");
		String simServerID = request.getParameter("SIMServerID");
		if (!StringUtils.isBlank(serverIP) && !StringUtils.isBlank(simServerID)) {
			info.setServerIP(serverIP);
			info.setSIMServerID(simServerID);
		}
		info.setSIMCategory("本地卡");
		info.setCardStatus(cardStatus.get(0).getLabel()); // 0 - 调试不可用
		info.setSimBillMethod(simBillMethods.get(0).getLabel()); // 0 - 预付费
		// try {
		// info.setPlanData((int)Bytes.valueOf("1G").kilobytes());
		// } catch (StringValueConversionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		info.setPlanData(1024 * 1024); // 1G = 1024 * 1024 KB = 1048576 KB 单位 KB
		// }
		// info.setPlanPrice(1.0); // 为了避免创建套餐金额为零, 使用一个更适合的默认值 或者前端使用空白
		info.setSIMIfActivated("否");
		info.setPlanIfActivated("否");

		info.setSpeedType(1);
		info.setSubscribeTag(0);
		info.setSelnet("");

		model.addAttribute("Model", info);

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");
		model.addAttribute("Countries", countries);
		model.addAttribute("SIMServers", simServers);

		// return "WEB-INF/views/sim/siminfo_new";
		return "WEB-INF/views/sim/siminfo_edit"; // 去掉siminfo_new,
													// 统一使用siminfo_edit
	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口 通过 boolean isInsert 来相应处理
	 * 
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(SIMInfo info, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// TODO: 服务器端数据验证

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		boolean isInsert = false;
		if (StringUtils.isBlank(info.getSIMinfoID())) {
			isInsert = true;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo != null) {
			if (isInsert) {
				info.setCreatorUserID(adminUserInfo.getUserID());
				info.setCreatorUserName(adminUserInfo.getUserName());
			} else {
				info.setModifyUserID(adminUserInfo.getUserID());
				// info.setModifyUserName(adminUserInfo.getUserName());
			}
		} else {
			try {
				// response.getWriter().println("请重新登录!");
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		String imsiString = info.getIMSI();
		if (null == imsiString) {
			info.setIMSI("");
			info.setMNC("");
		} else {
			// 截取 MNC 等
			/*
			 * if (imsiString.length() == 18) { // 验证长度
			 * info.setMNC(StringUtils.substring(imsiString, 6, 8)); }
			 */
		}
		if (null == info.getLastDeviceSN()) {
			info.setLastDeviceSN("");
		}
		if (StringUtils.isBlank(info.getPlanRemainData())) {
			info.setPlanRemainData(info.getPlanData());
		}
		// 从表单传过来的 MCC 列表值, 结合存在的国家列表 输出需要的字段值
		List<CountryInfo> countries = countryInfoSer.getAll("");
		info.setCountryList(CountryUtils.getCountryStringFromMCCListForSiminfo(
				info.getCountryList(), countries));
		if (StringUtils.isBlank(info.getPlanActivateDate())) {
			info.setPlanActivateDate(null);
		}
		if (StringUtils.isBlank(info.getPlanEndDate())) {
			info.setPlanEndDate(null);
		}
		if (StringUtils.isBlank(info.getSIMActivateDate())) {
			info.setSIMActivateDate(null);
		}
		if (StringUtils.isBlank(info.getSIMEndDate())) {
			info.setSIMEndDate(null);
		}

		boolean result;

		try {
			if (isInsert) {
				info.setSIMinfoID(getUUID());
				info.setSysStatus(1);
				result = simInfoSer.insertInfo(info);
			} else {
				if (info.getPlanRemainData() < 1024 * 50
						&& !"下架".equals(info.getCardStatus())) {
					info.setCardStatus("调试不可用");
				}
				if (info.getCardStatus().equals("调试不可用")
						|| info.getCardStatus().equals("下架")
						|| info.getCardStatus().equals("不可用")) {
					SIMInfo simInfo = simInfoSer.getById(info.getSIMinfoID());
					if (simInfo.getCardStatus().equals("使用中")) {
						commandSer.changeCard(simInfo.getLastDeviceSN());
					}
				}
				result = simInfoSer.updateInfo(info);
				// 判断如果是将使用中改为停用，那么就发一个换卡命令
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}

		if (result) {
			logger.debug("SIM卡信息保存成功");
			try {
				// response.getWriter().println("成功保存SIM卡信息!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存SIM卡信息!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			// 应该使用统计功能去同步这个数据, 否则各种添加入口去考虑则难以处理 --> 已添加服务器SIM卡数量统计和同步更新
			// //尝试更新SIMSever的SIM卡数量 目前不处理若更新失败的情形 后期可能提供SIM卡数量同步/更新的机制或方法
			// if (isInsert && StringUtils.isNotBlank(info.getSIMServerID())) {
			// SIMServer server = new SIMServer();
			// server.setSIMServerID(info.getSIMServerID());
			// server.setSIMCount(1);
			// if ("可用".equals(info.getCardStatus())) { // 1-可用
			// server.setAvailableSIMCount(1);
			// }
			// simServerSer.updateSimCount(server);
			// }

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				if (isInsert) {
					admin.setOperateContent("添加了本地SIM卡, 记录ID为: "
							+ info.getSIMinfoID() + " IMSI: " + info.getIMSI()
							+ " 卡状态: " + info.getCardStatus() + " 可用国家: "
							+ info.getCountryList()); // 操作内容
					admin.setOperateMenu("本地SIM卡管理>添加SIM卡"); // 操作菜单
					admin.setOperateType("添加");// 操作类型
				} else {
					admin.setOperateContent("修改了本地SIM卡, 记录ID为: "
							+ info.getSIMinfoID() + " IMSI: " + info.getIMSI()
							+ " 卡状态: " + info.getCardStatus() + " 可用国家: "
							+ info.getCountryList() + " lastDeviceSN: "
							+ info.getLastDeviceSN());
					admin.setOperateMenu("本地SIM卡管理>修改SIM卡");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			logger.debug("SIM卡信息保存失败");
			try {
				// response.getWriter().println("保存SIM卡信息出错, 请重试!");
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存SIM卡信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除记录
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable String id, HttpServletResponse response,
			Model model) {
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		SIMInfo detailInfo = simInfoSer.getById(id);
		if (detailInfo == null) {
			try {
				response.getWriter().println("查无此SIM卡!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		SIMInfo info = new SIMInfo();
		info.setSIMinfoID(id);
		info.setSysStatus(0);
		info.setModifyDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		info.setModifyUserID(adminUserInfo.getUserID());

		if (simInfoSer.updateInfoSysStatus(info)) {
			try {
				response.getWriter().println("SIM卡删除成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				admin.setOperateContent("删除了本地SIM卡, 记录ID为: "
						+ info.getSIMinfoID() + " IMSI: "
						+ detailInfo.getIMSI() + " 卡状态: "
						+ detailInfo.getCardStatus() + " 可用国家: "
						+ detailInfo.getCountryList() + " lastDeviceSN: "
						+ detailInfo.getLastDeviceSN());
				admin.setOperateMenu("本地SIM卡管理>删除SIM卡");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().println("SIM卡删除出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 恢复记录
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/restore/{id}")
	public void restorePlan(@PathVariable String id,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		SIMInfo detailInfo = simInfoSer.getById(id);
		if (detailInfo == null) {
			try {
				response.getWriter().println("查无此SIM卡!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		SIMInfo info = new SIMInfo();
		info.setSIMinfoID(id);
		info.setSysStatus(1);

		if (simInfoSer.updateInfoSysStatus(info)) {
			try {
				response.getWriter().println("SIM卡恢复成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				admin.setOperateContent("恢复了本地SIM卡, 记录ID为: "
						+ info.getSIMinfoID() + " IMSI: "
						+ detailInfo.getIMSI() + " 卡状态: "
						+ detailInfo.getCardStatus() + " 可用国家: "
						+ detailInfo.getCountryList());
				admin.setOperateMenu("本地SIM卡管理>恢复SIM卡");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().println("SIM卡恢复出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * SIM卡状态统计图表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/status/chart")
	public String statusChart(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<SimStatusStatByCountry> result;
		boolean checkResultFailed = false;
		// 统计SIM卡总量
		SIMInfo info = new SIMInfo();
		result = simInfoSer.getSimStatusStatByCountry(info);

		if (null != result) {
			// 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
			List<SimStatusStatByCountry> temp;

			// "可用"卡数量
			info.setCardStatus("可用");
			temp = simInfoSer.getSimStatusStatByCountry(info);
			if (null != temp && temp.size() == result.size()) { // 检查总数系为了前后统计的数据保持一致
																// 可重新考虑
				for (int i = 0; i < temp.size(); i++) {
					result.get(i).setAvalableSimsCount(
							temp.get(i).getSimsTotalCount());
				}
			} else {
				checkResultFailed = true;
			}

			// "使用中"卡数量
			if (!checkResultFailed) {
				info.setCardStatus("使用中");
				temp = simInfoSer.getSimStatusStatByCountry(info);
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUsingSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "不可用"卡数量
			if (!checkResultFailed) {
				info.setCardStatus("不可用");
				temp = simInfoSer.getSimStatusStatByCountry(info);
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUnavalableSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// // "停用"卡数量 --> 不使用停用状态
			// if (!checkResultFailed) {
			// info.setCardStatus("停用");
			// temp = simInfoSer.getSimStatusStatByCountry(info);
			// if (null != temp && temp.size() == result.size()) {
			// for (int i = 0; i < temp.size(); i++) {
			// result.get(i).setDeadSimsCount(temp.get(i).getSimsTotalCount());
			// }
			// } else {
			// checkResultFailed = true;
			// }
			// }

			// 流量/余额预警卡数量
			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			if (!checkResultFailed) {
				info.setCardStatus("");
				info.setCardBalance(50.0);
				info.setPlanRemainData(200 * 1024); // 单位KB
				temp = simInfoSer.getSimStatusStatByCountry(info);
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setAlertSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

		} else { // 统计总数已出错
			checkResultFailed = true;
		}

		if (checkResultFailed) {
			model.addAttribute("info", "统计出错, 请重试!");
		} else {
			// List<CountryInfo> countries = countryInfoSer.getAll("");
			// model.addAttribute("Countries", countries);
			// List<SIMServer> simServers = simServerSer.getAll("");
			// model.addAttribute("SIMServers", simServers);

			for (SimStatusStatByCountry country : result) {
				country.caculatePercents(true);
			}
			model.addAttribute("SimStatusStatByCountry", result);
		}

		return "WEB-INF/views/sim/siminfo_status";

	}

	/**
	 * SIM卡统计报表入口页 --> 改为 AJAX
	 * 
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statisticsIndex(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		return "WEB-INF/views/sim/siminfo_statistics";

		// 以下为非 ajax 情形, 可暂保留
		// List<SimStatusStatByCountry> result;
		// boolean checkResultFailed = false;
		// // 统计SIM卡总量
		// SIMInfo info = new SIMInfo();
		// result = simInfoSer.getSimStatusStatByCountry(info);
		//
		// if (null != result) {
		// // 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
		// List<SimStatusStatByCountry> temp;
		//
		// // "可用"卡数量
		// info.setCardStatus("可用");
		// temp = simInfoSer.getSimStatusStatByCountry(info);
		// if (null != temp && temp.size() == result.size()) { //
		// 检查总数系为了前后统计的数据保持一致 可重新考虑
		// for (int i = 0; i < temp.size(); i++) {
		// result.get(i).setAvalableSimsCount(temp.get(i).getSimsTotalCount());
		// }
		// } else {
		// checkResultFailed = true;
		// }
		//
		// // "使用中"卡数量
		// if (!checkResultFailed) {
		// info.setCardStatus("使用中");
		// temp = simInfoSer.getSimStatusStatByCountry(info);
		// if (null != temp && temp.size() == result.size()) {
		// for (int i = 0; i < temp.size(); i++) {
		// result.get(i).setUsingSimsCount(temp.get(i).getSimsTotalCount());
		// }
		// } else {
		// checkResultFailed = true;
		// }
		// }
		//
		// // "不可用"卡数量
		// if (!checkResultFailed) {
		// info.setCardStatus("不可用");
		// temp = simInfoSer.getSimStatusStatByCountry(info);
		// if (null != temp && temp.size() == result.size()) {
		// for (int i = 0; i < temp.size(); i++) {
		// result.get(i).setUnavalableSimsCount(temp.get(i).getSimsTotalCount());
		// }
		// } else {
		// checkResultFailed = true;
		// }
		// }
		//
		// // // "停用"卡数量 --> 不使用停用状态
		// // if (!checkResultFailed) {
		// // info.setCardStatus("停用");
		// // temp = simInfoSer.getSimStatusStatByCountry(info);
		// // if (null != temp && temp.size() == result.size()) {
		// // for (int i = 0; i < temp.size(); i++) {
		// // result.get(i).setDeadSimsCount(temp.get(i).getSimsTotalCount());
		// // }
		// // } else {
		// // checkResultFailed = true;
		// // }
		// // }
		//
		// // 流量/余额预警卡数量
		// // 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
		// if (!checkResultFailed) {
		// info.setCardStatus("");
		// info.setCardBalance(50.0);
		// info.setPlanRemainData(200 * 1024); // 单位KB
		// temp = simInfoSer.getSimStatusStatByCountry(info);
		// if (null != temp && temp.size() == result.size()) {
		// for (int i = 0; i < temp.size(); i++) {
		// result.get(i).setAlertSimsCount(temp.get(i).getSimsTotalCount());
		// }
		// } else {
		// checkResultFailed = true;
		// }
		// }
		//
		// } else { // 统计总数已出错
		// checkResultFailed = true;
		// }
		//
		// if (checkResultFailed) {
		// model.addAttribute("info","统计出错, 请重试!");
		// } else {
		// //List<CountryInfo> countries = countryInfoSer.getAll("");
		// //model.addAttribute("Countries", countries);
		// //List<SIMServer> simServers = simServerSer.getAll("");
		// //model.addAttribute("SIMServers", simServers);
		//
		// for (SimStatusStatByCountry country : result) {
		// country.caculatePercents(true);
		// }
		//
		//
		// model.addAttribute("SimStatusStatByCountry", result);
		// }
		//
		// return "WEB-INF/views/sim/siminfo_statistics";
	}

	/**
	 * 全部国家各种状态SIM卡数 AJAX 接口
	 * 
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/count")
	public void statisticsAllCount(SearchDTO searchDTO, SIMInfo info,
			HttpServletResponse response) {
		try {

			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			info.setCardBalance(50.0);
			info.setPlanRemainData(200 * 1024); // 单位KB

			Map<String, String> map = simInfoSer.allCount(info);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			if (map != null) {
				obj.put("count", map.get("count")); // SIM卡总数
				obj.put("alert", map.get("alert")); // 充值预警
				obj.put("status_available", map.get("status_available")); // 可用
				obj.put("status_using", map.get("status_using")); // 使用中
				obj.put("status_unavailable", map.get("status_unavailable")); // 不可用
				obj.put("status_stop", map.get("status_stop")); // 停用
			}
			jsonArray.add(obj);
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", 1);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 按国家统计各种状态SIM卡数 AJAX 接口
	 * 
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/statistics/getpagebycountry")
	public void statisticsGetPageByCountry(SearchDTO searchDTO, SIMInfo info,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		List<SimStatusStatByCountry> result = null;
		boolean checkResultFailed = false;

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);

		// 统计SIM卡总量
		Page page = simInfoSer.getPageOfSimStatusStatByCountry(seDto);
		if (null != page) {
			result = (List<SimStatusStatByCountry>) page.getRows();
		}

		if (null != result) {
			// 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
			List<SimStatusStatByCountry> temp;

			// "可用"卡数量
			info.setCardStatus("可用");
			seDto.setObj(info);
			temp = (List<SimStatusStatByCountry>) simInfoSer
					.getPageOfSimStatusStatByCountry(seDto).getRows();
			if (null != temp && temp.size() == result.size()) { // 检查总数系为了前后统计的数据保持一致
																// 可重新考虑
				for (int i = 0; i < temp.size(); i++) {
					result.get(i).setAvalableSimsCount(
							temp.get(i).getSimsTotalCount());
				}
			} else {
				checkResultFailed = true;
			}

			// "使用中"卡数量
			if (!checkResultFailed) {
				info.setCardStatus("使用中");
				seDto.setObj(info);
				temp = (List<SimStatusStatByCountry>) simInfoSer
						.getPageOfSimStatusStatByCountry(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUsingSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "不可用"卡数量
			if (!checkResultFailed) {
				info.setCardStatus("不可用");
				seDto.setObj(info);
				temp = (List<SimStatusStatByCountry>) simInfoSer
						.getPageOfSimStatusStatByCountry(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUnavalableSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "停用"卡数量 --> 不使用停用状态
			if (!checkResultFailed) {
				info.setCardStatus("停用");
				seDto.setObj(info);
				temp = (List<SimStatusStatByCountry>) simInfoSer
						.getPageOfSimStatusStatByCountry(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setDeadSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// 流量/余额预警卡数量
			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			if (!checkResultFailed) {
				info.setCardStatus("");
				info.setCardBalance(50.0);
				info.setPlanRemainData(200 * 1024); // 单位KB
				seDto.setObj(info);
				temp = (List<SimStatusStatByCountry>) simInfoSer
						.getPageOfSimStatusStatByCountry(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setAlertSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}
		} else { // 统计总数已出错
			checkResultFailed = true;
		}

		try {
			if (checkResultFailed) {
				// model.addAttribute("info","统计出错, 请重试!");
				response.getWriter().println("");
			} else {
				// List<CountryInfo> countries = countryInfoSer.getAll("");
				// model.addAttribute("Countries", countries);
				// List<SIMServer> simServers = simServerSer.getAll("");
				// model.addAttribute("SIMServers", simServers);

				for (SimStatusStatByCountry country : result) {
					country.caculatePercents(false); // true
				}

				JSONObject object = new JSONObject();
				object.put("success", true);
				object.put("totalRows", page.getTotal());
				object.put("curPage", page.getCurrentPage());
				JSONArray ja = new JSONArray();
				for (SimStatusStatByCountry a : result) {
					JSONObject obj = JSONObject.fromObject(a);
					// obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做
					// left join, 手动设置国家名称, 或者设法交由前端去处理
					ja.add(obj);
				}
				object.put("data", ja);

				// model.addAttribute("SimStatusStatByCountry", result);
				response.getWriter().println(object.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 按运营商SIM卡数统计 AJAX 接口
	 * 
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/statistics/getpagebyoperator")
	public void statisticsGetPageByOperator(SearchDTO searchDTO, SIMInfo info,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		List<SimStatusStatByOperator> result = null;
		boolean checkResultFailed = false;

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);

		// 统计SIM卡总量
		Page page = simInfoSer.getPageOfSimStatusStatByOperator(seDto);
		if (null != page) {
			result = (List<SimStatusStatByOperator>) page.getRows();
		}

		if (null != result) {
			// 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
			List<SimStatusStatByOperator> temp;

			// "可用"卡数量
			info.setCardStatus("可用");
			seDto.setObj(info);
			temp = (List<SimStatusStatByOperator>) simInfoSer
					.getPageOfSimStatusStatByOperator(seDto).getRows();
			if (null != temp && temp.size() == result.size()) { // 检查总数系为了前后统计的数据保持一致
																// 可重新考虑
				for (int i = 0; i < temp.size(); i++) {
					result.get(i).setAvalableSimsCount(
							temp.get(i).getSimsTotalCount());
				}
			} else {
				checkResultFailed = true;
			}

			// "使用中"卡数量
			if (!checkResultFailed) {
				info.setCardStatus("使用中");
				seDto.setObj(info);
				temp = (List<SimStatusStatByOperator>) simInfoSer
						.getPageOfSimStatusStatByOperator(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUsingSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "不可用"卡数量
			if (!checkResultFailed) {
				info.setCardStatus("不可用");
				seDto.setObj(info);
				temp = (List<SimStatusStatByOperator>) simInfoSer
						.getPageOfSimStatusStatByOperator(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUnavalableSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "停用"卡数量 --> 不使用停用状态
			if (!checkResultFailed) {
				info.setCardStatus("停用");
				seDto.setObj(info);
				temp = (List<SimStatusStatByOperator>) simInfoSer
						.getPageOfSimStatusStatByOperator(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setDeadSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// 流量/余额预警卡数量
			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			if (!checkResultFailed) {
				info.setCardStatus("");
				info.setCardBalance(50.0);
				info.setPlanRemainData(200 * 1024); // 单位KB
				seDto.setObj(info);
				temp = (List<SimStatusStatByOperator>) simInfoSer
						.getPageOfSimStatusStatByOperator(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setAlertSimsCount(
								temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}
		} else { // 统计总数已出错
			checkResultFailed = true;
		}

		try {
			if (checkResultFailed) {
				// model.addAttribute("info","统计出错, 请重试!");
				response.getWriter().println("");
			} else {
				// List<CountryInfo> countries = countryInfoSer.getAll("");
				// model.addAttribute("Countries", countries);
				// List<SIMServer> simServers = simServerSer.getAll("");
				// model.addAttribute("SIMServers", simServers);

				for (SimStatusStatByOperator country : result) {
					country.caculatePercents(false); // true
				}

				JSONObject object = new JSONObject();
				object.put("success", true);
				object.put("totalRows", page.getTotal());
				object.put("curPage", page.getCurrentPage());
				JSONArray ja = new JSONArray();
				for (SimStatusStatByOperator a : result) {
					JSONObject obj = JSONObject.fromObject(a);
					// obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做
					// left join, 手动设置国家名称, 或者设法交由前端去处理
					ja.add(obj);
				}
				object.put("data", ja);

				// model.addAttribute("SimStatusStatByCountry", result);
				response.getWriter().println(object.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 批量导入SIM卡信息入口 -> 本地SIM卡
	 * 
	 * importEntry 本地卡 importEntryRoamingSim 漫游卡
	 */
	@RequestMapping("/import")
	public String importEntry(HttpServletRequest req, HttpSession session,
			Model model) {
		model.addAttribute("LogMessage",
				req.getSession().getAttribute("excelmessage"));
		req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面
		return "WEB-INF/views/sim/siminfo_batch_import";
	}

	/*
	 * @RequestMapping("/siminfoviews") public String
	 * siminfoviews(HttpServletRequest req, HttpSession session, Model model){
	 * model.addAttribute("Model", req.getSession().getAttribute("Model"));
	 * req.getSession().setAttribute("Model", ""); // 及时清空, 避免污染其他页面 //return
	 * "WEB-INF/views/sim/siminfo_batch_import"; return
	 * "WEB-INF/views/sim/siminfo_view_server"; }
	 */

	@SuppressWarnings("unused")
	/**
	 * 批量导入SIM卡 AJAX 接口 -> 本地SIM卡
	 *
	 * startImport 本地卡
	 * startImportRoamingSim 漫游卡
	 *
	 * @param file
	 * @param req
	 * @param resp
	 * @param session
	 * @param model
	 * @return
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/startImport", method = RequestMethod.POST)
	public String startImport(@RequestParam("file") MultipartFile file,
			HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, Model model) throws SQLException,
			FileNotFoundException, IOException, InterruptedException {
		System.out.println("得到上传excel请求");

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (adminUserInfo == null) {
			req.getSession().setAttribute("excelmessage", "请重新登录！");
			return "redirect:import";
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		String logoPathDir = "/files" + dateformat.format(new Date());
		// 得到excel保存目录的真实路径
		String temp = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		// 创建文件保存路径文件夹
		File tempFile = new File(temp);

		System.out.println("这个是上传文件的真实路径temp：" + temp);
		// 这个是上传文件的真实路径temp：E:\.......\tomcat\webapps\ylcyManage\files2015\09\10\15

		MultipartFile multipartFile = multipartRequest.getFile("file");

		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		// 获取文件后缀名
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		// 构建文件名称
		String logExcelName = multipartFile.getOriginalFilename();

		/** 拼成完整的文件保存路径加文件 **/
		String fileName = temp + File.separator + logExcelName;
		File files = new File(fileName);
		try {
			multipartFile.transferTo(files);
		} catch (IllegalStateException e) {
			e.printStackTrace();

			req.getSession().setAttribute("excelmessage", "上传出错！请重试或联系管理员");
			return "redirect:import";
		} catch (IOException e) {
			e.printStackTrace();

			req.getSession().setAttribute("excelmessage", "上传出错！请重试或联系管理员");
			return "redirect:import";
		}
		System.out.println("上传完毕.");

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);

		// 开始时间 // <del>应该提前, 包括上传时间</del>
		Long startTime = System.currentTimeMillis();

		String[][] result = ExcelUtils.getData(files, 2); // 此模板开始两行均为标题
		int allDatacount = result.length; // 原始数据的记录总数
		// 大概判定这个模板不是所需要的模板 或者可以把这个差值再加大一点, 例如差值大于3, 等
		// 另外, POI库读出的列最后总有一个空白列, 所以下面至少需要 +1 . 目前允许差值扩大到 2
		if (allDatacount == 0) {
			req.getSession().setAttribute("excelmessage",
					"输入的表格除标题外无数据, 或错误去掉了标题. 请按要求只填充数据.");
			return "redirect:import";
		}
		/*
		 * else if (result[0].length <
		 * SIMInfoImportConstants.TEMPLAGE_FILE_CHECK_COL_NUMBERS-2) {
		 * req.getSession().setAttribute("excelmessage",
		 * "目前读出数据表的列数量与目标模板的列数量差距大, 请确认是否输错模板?"); return "redirect:import"; }
		 */

		// 统计条数
		// 原则上, 下面出错的全部记录, 需要记录到日志信息, 反馈给前端用户, 参考记录号系模板中的"序号"
		// 目前, 只作插入新记录, 其他情况如SIM卡ICCID/设备的SN已存在, 不会更新该记录 -->
		//
		// ---> 20150828 根据ICCID不唯一的要求修改：
		/**
		 * <p>
		 * 导入要求、规则更改历史： (2015-08-27) ICCID 可能不唯一，所以改用 IMSI
		 * 号作必要字段。并且：（1）一般情况是这样，先插入一系列SIM卡后服务器 会读取出 IMSI 和 ICCID
		 * 号，然后该系列卡需要从这里批量导入必要的 SIM 卡信息之后，才能正常使用。（2） 少数情况，可到SIM卡编辑手动添加SIM卡 或者，插入
		 * SIM 卡后，从全部SIM卡列表查询到该卡，再进行手动编辑。（3）这样，批量导入模板需要以 IMSI 号作必要字段，即必须提供 IMSI
		 * 才能正常 导入。
		 * </p>
		 */

		int keyEmptyCount = 0; // 关键的字段空白, 这种不能作为记录添加. 这即系"无效数据", 如设备SN缺失
		int requiredInvalidCount = 0; // 必需的字段缺少(没有默认值的)或格式不准确,
										// 这种可根据设定的策略先添加到数据库,
										// 但必须设备一些状态使其暂不可用, 如"SIM卡状态"设置为不可用, 等等
		int insertFailedCount = 0; // 数据库插入出现错误, 最好能够按各种出错原因细分, 如ICCID/设备SN重复,
									// 则原始那条数据不插入, 及其他各种情况
		int insertOKCount = 0; // 成功插入条数
		int insertRecordCount = 0; // 原始记录中需要插入的记录总数: 应该等于 insertFailedCount +
									// insertOKCount

		int updateFailedCount = 0; // 更新失败数量
		int updateOKCount = 0; // 更新成功数量
		// int updateRecordCount; // 原始记录中相同IMSI的记录已存在, 需要更新的记录总数: 应该等于
		// updateFailedCount + updateOKCount,
		// 也应该等于 allDatacount - keyEmptyCount - insertRecordCount

		// 下列的值对应导入模板中的首列: 序号列
		List<Integer> keyEmptyIDs = new ArrayList<Integer>();
		List<Integer> requiredInvalidIDs = new ArrayList<Integer>();
		List<Integer> insertFailedIDs = new ArrayList<Integer>(); // 插入失败的序号ID
		List<Integer> updateFailedIDs = new ArrayList<Integer>(); // 更新失败的序号ID

		// ------连接数据库-------
		Connection conn = null;
		PreparedStatement pst = null;

		// ahming marks: 因为认为 update 时应该较好支持那种若填入值空白时则不更新该字段的需求, 这样的话, 参数不固定,
		// 所以不适宜使用 PreparedStatement , 所以使用 Statement
		Statement stmtUpdate = null;
		String sqlUpdateString, sqlUpdateFields;

		try {
			conn = getDBConn();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			req.getSession().setAttribute("excelmessage", "无法连接数据库！请重试或联系管理员");
			return "redirect:import";
		}
		// conn.setAutoCommit(false); // !IMPORTANT 若改为逐条提交后, 这个必须去掉

		String category = request.getParameter("simcategory");
		String tableString;
		boolean isSIMInfoNotRoamingSIMInfo;
		if (StringUtils.isBlank(category) || "0".equals(category)) {
			// category = "0";
			tableString = " SIMInfo ";
			isSIMInfoNotRoamingSIMInfo = true;
		} else if ("1".equals(category)) {
			tableString = " RoamingSIMInfo ";
			isSIMInfoNotRoamingSIMInfo = false;
		} else {
			tableString = " SIMInfo ";
			isSIMInfoNotRoamingSIMInfo = true;
		}

		// TODO: 在插入或更新之前，先判断好记录的 IMSI 号是否已在表中
		List<Boolean> isIMSIexists = new ArrayList<Boolean>();
		for (int i = 0; i < allDatacount; i++) {
			// 是否适合检查IMSI的长度必须为18？
			if (result[i][SIMInfoImportConstants.COL_IMSI] == ""
					|| result[i][SIMInfoImportConstants.COL_IMSI].length() != 18) {
				keyEmptyCount++;
				keyEmptyIDs.add(Integer.valueOf(result[i][0]));

				isIMSIexists.add(i, null); // 这是空记录
			} else {
				// 可以优化吗？
				if (simInfoSer
						.isExistImsi(result[i][SIMInfoImportConstants.COL_IMSI])) {
					isIMSIexists.add(i, true);
				} else {
					isIMSIexists.add(i, false);
					insertRecordCount++;
				}
			}

		}

		try {
			// 此处插入语句的拼合次序与Excel导入模板的列次序基本一致, 其他则需要手动加入的放后部
			// --> 要保证这里的顺序与 ExcelUtils > SIMInfoImportConstants 类中的顺序"严格一致"
			// 因为下面引用时系按那里的值填充 pst
			// log: 20160113 增加 IMEI 列
			pst = (PreparedStatement) conn
					.prepareStatement("insert into "
							+ tableString
							+ " (IMSI,ICCID,speedType,simAlias,ifRoam,MCC,trademark,countryList,"
							+ // 7+1(7+1)
							"PIN,PUK,APN,IMEI,planType,planData,planRemainData,simBillMethod,"
							+ // 7(14)
							"planPrice,cardInitialBalance,cardBalance,SIMIfActivated,simActivateCode,SIMActivateDate,SIMEndDate,"
							+ // 7(21+1)
							"queryMethod,rechargeMethod,remark,"
							+ // 4(24+1)
							"SIMInfoID,SIMServerID,serverIP,MNC,SIMCategory,cardStatus,creatorUserID,creatorUserName,creatorDate,cardSource,sysStatus) "
							+ // 10
							" values (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,? ,? ,?,1)"); // 41
																															// ->
																															// 34+1

			// String[][] result = ExcelUtils.getData(files, 2); // 此模板开始两行均为标题
			// allDatacount = result.length; // 原始数据的记录总数

			SimpleDateFormat sdfdate = new SimpleDateFormat(); // 格式化时间
			sdfdate.applyPattern("yyyy-MM-dd HH:mm:ss"); // a为AM/PM的标记

			// ahming notes: 不需要提前分配. 且在我的ubuntu上开发测试发现会引起读取数组超出范围的错误, 直接赋值给 pst
			// 即可
			// for (int i = 0; i < allDatacount; i++) {
			// // 此处是后台代码添加的对应的属性值加到result二维数组当中
			// result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX] =
			// UUID.randomUUID().toString(); // SIMInfoID
			// result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX + 1] =
			// ""; // SIMServerID
			// result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX + 2] =
			// ""; // serverIP
			// // result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX +
			// 3] = ""; // MNC // 后面根据导入的字段设置
			// // result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX +
			// 4] = ""; // planIfActivated
			// // result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX +
			// 5] = ""; // SIMIfActivated
			// result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX + 6] =
			// adminUserInfo.userID; // 创建人ID
			// result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX + 7] =
			// adminUserInfo.userName; // 创建人名称
			// result[i][SIMInfoImportConstants.COL_GENERATED_START_INDEX + 8] =
			// sdfdate.format(new Date()).toString(); // 创建时间
			// }

			String MNCSegment; // 从IMSI中截取
			String MCCSegment;
			Boolean cardStatusAvailable = true;
			long parseBytesValue;
			double parseDoubleValue;
			int parseIntValue;
			long parseLongValue;
			Boolean requiredInvalidFlag = false;

			// TODO:目前未检查某些字段的值是否在可允许的值之路, 例如 SIMCategory 只能为"本地卡"或"漫游卡" .
			// 后面应该加好.

			// 注意: pst.setString 的索引从 1 开始
			for (int i = 0; i < allDatacount; i++) {
				if (null == isIMSIexists.get(i)) {
					continue;
				} else if (isIMSIexists.get(i)) {
					// 更新的记录

					// sqlUpdateString = "UPDATE "+ tableString + "SET ";
					sqlUpdateFields = "";

					// ? 其他有效性检查? 登录ICCID长度? 格式?
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_ICCID])) {
						sqlUpdateFields += " ICCID='"
								+ result[i][SIMInfoImportConstants.COL_ICCID]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_speedType])) {
						try {
							parseIntValue = Integer
									.parseInt(result[i][SIMInfoImportConstants.COL_speedType]);
							// 其他数值被认为无效,默认为0高速 TODO: 待与相关人员确认
							if (0 != parseIntValue && 1 != parseIntValue) {
								parseIntValue = 0;
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							parseIntValue = 0;
						}

						sqlUpdateFields += " speedType=" + parseIntValue + ", ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_simAlias])) {
						sqlUpdateFields += " simAlias='"
								+ result[i][SIMInfoImportConstants.COL_simAlias]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_ifRoam])) {
						try {
							parseIntValue = Integer
									.parseInt(result[i][SIMInfoImportConstants.COL_ifRoam]);
							// 其他数值被认为无效,默认为否,0,不支持漫游
							if (0 != parseIntValue && 1 != parseIntValue) {
								parseIntValue = 0;
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							parseIntValue = 0;
						}

						sqlUpdateFields += " ifRoam=" + parseIntValue + ", ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_MCC])) {
						sqlUpdateFields += " MCC='"
								+ result[i][SIMInfoImportConstants.COL_MCC]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_trademark])) {
						sqlUpdateFields += " trademark='"
								+ result[i][SIMInfoImportConstants.COL_trademark]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_countryList])) {
						sqlUpdateFields += " countryList='"
								+ result[i][SIMInfoImportConstants.COL_countryList]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_PIN])) {
						sqlUpdateFields += " PIN='"
								+ result[i][SIMInfoImportConstants.COL_PIN]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_PUK])) {
						sqlUpdateFields += " PUK='"
								+ result[i][SIMInfoImportConstants.COL_PUK]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_APN])) {
						sqlUpdateFields += " APN='"
								+ result[i][SIMInfoImportConstants.COL_APN]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_IMEI])) {
						sqlUpdateFields += " IMEI='"
								+ result[i][SIMInfoImportConstants.COL_IMEI]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_planType])) {
						sqlUpdateFields += " planType='"
								+ result[i][SIMInfoImportConstants.COL_planType]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_planData])) {
						try {
							parseBytesValue = (long) Bytes
									.valueOf(
											result[i][SIMInfoImportConstants.COL_planData])
									.kilobytes();
							sqlUpdateFields += " planData=" + parseBytesValue
									+ ", ";
						} catch (StringValueConversionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							// TODO: 与相关人员协定处理
							// continue; // 不适宜! --> 目前若格式不对, 不处理这个字段,
							// 但后期应该记录并反馈给用户
						}
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_planRemainData])) {
						try {
							parseBytesValue = (long) Bytes
									.valueOf(
											result[i][SIMInfoImportConstants.COL_planRemainData])
									.kilobytes();
							sqlUpdateFields += " planRemainData="
									+ parseBytesValue + ", ";
						} catch (StringValueConversionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							// TODO: 与相关人员协定处理
							// continue; // 不适宜! --> 目前若格式不对, 不处理这个字段,
							// 但后期应该记录并反馈给用户
						}
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_simBillMethod])) {
						sqlUpdateFields += " simBillMethod='"
								+ result[i][SIMInfoImportConstants.COL_simBillMethod]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_planPrice])) {
						try {
							parseDoubleValue = Double
									.valueOf(result[i][SIMInfoImportConstants.COL_planPrice]);
							sqlUpdateFields += " planPrice=" + parseDoubleValue
									+ ", ";
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							// TODO: 与相关人员协定处理
						}
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_cardInitialBalance])) {
						try {
							parseDoubleValue = Double
									.valueOf(result[i][SIMInfoImportConstants.COL_cardInitialBalance]);
							sqlUpdateFields += " cardInitialBalance="
									+ parseDoubleValue + ", ";
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							// TODO: 与相关人员协定处理
						}
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_cardBalance])) {
						try {
							parseDoubleValue = Double
									.valueOf(result[i][SIMInfoImportConstants.COL_cardBalance]);
							sqlUpdateFields += " cardBalance="
									+ parseDoubleValue + ", ";
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							// TODO: 与相关人员协定处理
						}
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.SIMIfActivated])) {
						if ("是".equals(result[i][SIMInfoImportConstants.SIMIfActivated])) {
							sqlUpdateFields += " SIMIfActivated='是', ";
						} else { // 其他一切都'否', 不作 requiredInvalidFlag 处理
							sqlUpdateFields += " SIMIfActivated='否', ";
						}
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_simActivateCode])) {
						sqlUpdateFields += " simActivateCode='"
								+ result[i][SIMInfoImportConstants.COL_simActivateCode]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_SIMActivateDate])) {
						sqlUpdateFields += " SIMActivateDate='"
								+ result[i][SIMInfoImportConstants.COL_SIMActivateDate]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_SIMEndDate])) {
						sqlUpdateFields += " SIMEndDate='"
								+ result[i][SIMInfoImportConstants.COL_SIMEndDate]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_queryMethod])) {
						sqlUpdateFields += " queryMethod='"
								+ result[i][SIMInfoImportConstants.COL_queryMethod]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_rechargeMethod])) {
						sqlUpdateFields += " rechargeMethod='"
								+ result[i][SIMInfoImportConstants.COL_rechargeMethod]
								+ "', ";
					}
					if (StringUtils
							.isNotBlank(result[i][SIMInfoImportConstants.COL_remark])) {
						sqlUpdateFields += " remark='"
								+ result[i][SIMInfoImportConstants.COL_remark]
								+ "', ";
					}
					if (StringUtils.isNotBlank(result[i][27])) {
						sqlUpdateFields += " cardStatus='" + result[i][27]
								+ "', ";
					}
					if (StringUtils.isNotBlank(result[i][28])) {
						sqlUpdateFields += " cardSource='" + result[i][28]
								+ "', ";
					}

					if (StringUtils.isBlank(sqlUpdateFields)) {
						continue; // 全部空白的行
					}

					sqlUpdateFields += " modifyUserID='"
							+ adminUserInfo.getUserID() + "', modifyDate='"
							+ sdfdate.format(new Date()).toString() + "'";

					sqlUpdateString = "UPDATE " + tableString + "SET "
							+ sqlUpdateFields + " WHERE IMSI='"
							+ result[i][SIMInfoImportConstants.COL_IMSI]
							+ "' LIMIT 1";

					stmtUpdate = conn.createStatement();

					try {
						stmtUpdate.executeUpdate(sqlUpdateString);
						updateOKCount++;
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
						updateFailedCount++;
						updateFailedIDs.add(Integer.valueOf(result[i][0]));
					}

					// 每行记录需要关闭?
					if (null != stmtUpdate) {
						try {
							stmtUpdate.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else {
					// 插入的记录

					// 根据数据库表字段的不同可以做修改
					// ahming notes: ExcelUtils.getData() 中所有需要trim的值已trim,
					// 所以这里不需要重复trim

					// SIMCategory 因为目前限定不能混合导入本地卡和漫游卡, 所以这个当由表单直接提供即可
					// 但目前未实现表单输入, 要先测试导入的情况 --> 已可通过下拉选择“本地”或“漫游卡”
					// 若模板的值为空，则相应填入类型
					// if (isSIMInfoNotRoamingSIMInfo) {
					// result[i][SIMInfoImportConstants.COL_SIMCategory] =
					// "本地卡";
					// } else {
					// result[i][SIMInfoImportConstants.COL_SIMCategory] =
					// "漫游卡";
					// }
					// pst.setString(1,
					// result[i][SIMInfoImportConstants.COL_SIMCategory]); //
					// SIMCategory

					// 2 可用状态放到后面, 因为若一些关键字段未提供, 强制设置此卡为"不可用"

					// if (result[i][SIMInfoImportConstants.COL_SIMType] == "")
					// {
					// result[i][SIMInfoImportConstants.COL_SIMType] = "SIM"; //
					// TODO: 这个需要最终确认默认值!
					// }
					// pst.setString(3,
					// result[i][SIMInfoImportConstants.COL_SIMType]); //
					// SIMType

					// 在 MCC 前先处理 IMSI 和 ICCID
					// --> IMSI 号现在必需，若丢失则作为无效数据
					if (result[i][SIMInfoImportConstants.COL_IMSI] == ""
							|| result[i][SIMInfoImportConstants.COL_IMSI]
									.length() != 18) {
						MNCSegment = MCCSegment = "";
						// keyEmptyCount ++; // 上面查询是否存在IMSI时已可获取
						// keyEmptyIDs.add(result[i][0]);
					} else {
						MCCSegment = StringUtils.substring(
								result[i][SIMInfoImportConstants.COL_IMSI], 3,
								6);
						MNCSegment = StringUtils.substring(
								result[i][SIMInfoImportConstants.COL_IMSI], 6,
								8);
					}
					pst.setString(SIMInfoImportConstants.COL_IMSI,
							result[i][SIMInfoImportConstants.COL_IMSI]); // IMSI

					// ICCID 若 // 不再关键
					// if (result[i][SIMInfoImportConstants.COL_ICCID] == "") {
					// keyEmptyCount ++;
					// keyEmptyIDs.add(result[i][0]);
					// continue; // ICCID 系关键数据, 若空则此记录无效, 跳过
					// }
					pst.setString(SIMInfoImportConstants.COL_ICCID,
							result[i][SIMInfoImportConstants.COL_ICCID]); // ICCID

					if (result[i][SIMInfoImportConstants.COL_speedType] == "") {
						result[i][SIMInfoImportConstants.COL_speedType] = "0"; // 插入时若空则设为高速
					}
					try {
						parseIntValue = Integer
								.parseInt(result[i][SIMInfoImportConstants.COL_speedType]);
						if (0 != parseIntValue && 1 != parseIntValue) { // 其他数值被认为无效,默认为0高速
							parseIntValue = 0;
						}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						parseIntValue = 0;
					}
					pst.setString(SIMInfoImportConstants.COL_speedType,
							String.valueOf(parseIntValue));

					pst.setString(SIMInfoImportConstants.COL_simAlias,
							result[i][SIMInfoImportConstants.COL_simAlias]); // simAlias

					if (result[i][SIMInfoImportConstants.COL_ifRoam] == "") {
						result[i][SIMInfoImportConstants.COL_ifRoam] = "0"; // 插入时若空则设为高速
					} else if (!"1"
							.equals(result[i][SIMInfoImportConstants.COL_ifRoam])
							&& !"0".equals(result[i][SIMInfoImportConstants.COL_ifRoam])) {
						result[i][SIMInfoImportConstants.COL_ifRoam] = "0";
					}
					pst.setString(SIMInfoImportConstants.COL_ifRoam,
							result[i][SIMInfoImportConstants.COL_ifRoam]);

					if (result[i][SIMInfoImportConstants.COL_MCC] == "") {
						result[i][SIMInfoImportConstants.COL_MCC] = (null == MCCSegment) ? ""
								: MCCSegment;
					} else {
						// if (StringUtils.isNotBlank(MCCSegment)) {
						// // 0911经讨论, 有可能不同的情形, 不需要使用截取, 两者不同时应使用输入的值.
						// 或有必要时提醒一下
						// if
						// (!MCCSegment.equals(result[i][SIMInfoImportConstants.COL_MCC]))
						// {
						// // DONE: 以下可向相关的人确认一下
						// result[i][5] = MCCSegment; // 若截取的MCC与原始记录中提供的MCC不同,
						// 则使用截取的MCC.
						// }
						// }
					}
					pst.setString(SIMInfoImportConstants.COL_MCC,
							result[i][SIMInfoImportConstants.COL_MCC]); // MCC

					pst.setString(SIMInfoImportConstants.COL_trademark,
							result[i][SIMInfoImportConstants.COL_trademark]); // trademark

					if (result[i][SIMInfoImportConstants.COL_countryList] == "") {
						// --> 0911 经讨论确认若插入时可使用国家留空时, 默认值为111
						// result[i][SIMInfoImportConstants.COL_countryList] =
						// result[i][SIMInfoImportConstants.COL_MCC]; //
						// countryList为MCC的组合值, 若原始数据为空则使用MCC即可
						result[i][SIMInfoImportConstants.COL_countryList] = "111";
					}
					pst.setString(SIMInfoImportConstants.COL_countryList,
							result[i][SIMInfoImportConstants.COL_countryList]); // countryList

					// pst.setString(7,
					// result[i][SIMInfoImportConstants.COL_trademark]); //
					// trademark
					// // 8, 9置前处理
					// pst.setString(10,
					// result[i][SIMInfoImportConstants.COL_phone]); // phone
					// pst.setString(11,
					// result[i][SIMInfoImportConstants.COL_registerInfo]); //
					// registerInfo

					pst.setString(SIMInfoImportConstants.COL_PIN,
							result[i][SIMInfoImportConstants.COL_PIN]); // PIN
					pst.setString(SIMInfoImportConstants.COL_PUK,
							result[i][SIMInfoImportConstants.COL_PUK]); // PUK
					pst.setString(SIMInfoImportConstants.COL_APN,
							result[i][SIMInfoImportConstants.COL_APN]); // APN

					// IMEI在表中为 bigint
					try {
						parseLongValue = Long
								.parseLong(result[i][SIMInfoImportConstants.COL_IMEI]);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						parseLongValue = 0;
					}
					pst.setString(SIMInfoImportConstants.COL_IMEI,
							parseLongValue + ""); // IMEI

					// 这个"套餐名称"若为空, 可不可按照其他字段拼合?
					pst.setString(SIMInfoImportConstants.COL_planType,
							result[i][SIMInfoImportConstants.COL_planType]); // planType

					if (result[i][SIMInfoImportConstants.COL_planData] == "") {
						parseBytesValue = 0L;
						requiredInvalidFlag = true;
						cardStatusAvailable = false;
					} else {
						try {
							parseBytesValue = (long) Bytes
									.valueOf(
											result[i][SIMInfoImportConstants.COL_planData])
									.kilobytes();
						} catch (StringValueConversionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							parseBytesValue = 0L;
							requiredInvalidFlag = true;
							cardStatusAvailable = false;
						}
					}
					pst.setString(SIMInfoImportConstants.COL_planData,
							String.valueOf(parseBytesValue)); // planData

					if (result[i][SIMInfoImportConstants.COL_planRemainData] == "") {
						requiredInvalidFlag = true;
						cardStatusAvailable = false;
						parseBytesValue = 0L;
					} else {
						try {
							parseBytesValue = (long) Bytes
									.valueOf(
											result[i][SIMInfoImportConstants.COL_planRemainData])
									.kilobytes();
						} catch (StringValueConversionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							parseBytesValue = 0L;
							requiredInvalidFlag = true;
							cardStatusAvailable = false;
						}
					}
					pst.setString(SIMInfoImportConstants.COL_planRemainData,
							String.valueOf(parseBytesValue)); // planRemainData

					// 模板中提示此字段为必需, 但这里不作检查 --> 设默认值若为空则系后付费
					if (result[i][SIMInfoImportConstants.COL_simBillMethod] == "") {
						result[i][SIMInfoImportConstants.COL_simBillMethod] = "后付费";
					}
					pst.setString(SIMInfoImportConstants.COL_simBillMethod,
							result[i][SIMInfoImportConstants.COL_simBillMethod]); // simBillMethod

					// 24和23对调, 系为了方便设置 cardBalance 可用余额
					if (result[i][SIMInfoImportConstants.COL_planPrice] == "") {
						requiredInvalidFlag = true;
						cardStatusAvailable = false;
						parseDoubleValue = 0.0;
					} else {
						try {
							parseDoubleValue = Double
									.valueOf(result[i][SIMInfoImportConstants.COL_planPrice]);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							parseDoubleValue = 0.0;
							requiredInvalidFlag = true;
							cardStatusAvailable = false;
						}
					}
					pst.setString(SIMInfoImportConstants.COL_planPrice,
							String.valueOf(parseDoubleValue)); // planPrice

					// 若这个为空, 后面直接使用刚刚的"卡初始余额"的值 -> 按新的要求,若为空则与套餐金额相同,
					// 所以位置调到planPrice之后
					if (result[i][SIMInfoImportConstants.COL_cardBalance] != "") {
						try {
							parseDoubleValue = Double
									.valueOf(result[i][SIMInfoImportConstants.COL_cardBalance]);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							// TODO: 若这个转换失败, 后面直接使用刚刚的"卡初始余额"的值, 但可能这需要向相关
							// 人员确认这个默认行为是否恰当, 因为涉及金额等计算
							parseDoubleValue = 0.0; // 现在若转换失败, 则当作零, 以提早发现问题.
													// 否则若实际余额很少了仍然搞足额则可能引起问题
							requiredInvalidFlag = true;
							cardStatusAvailable = false;
						}
					}
					pst.setString(SIMInfoImportConstants.COL_cardBalance,
							String.valueOf(parseDoubleValue)); // cardBalance

					if (result[i][SIMInfoImportConstants.COL_cardInitialBalance] == "") {
						// requiredInvalidFlag = true; // 卡初始余额不作要求,可为空,为空则录入零
						// cardStatusAvailable = false;
						parseDoubleValue = 0.0;
					} else {
						try {
							parseDoubleValue = Double
									.valueOf(result[i][SIMInfoImportConstants.COL_cardInitialBalance]);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							parseDoubleValue = 0.0;
							// requiredInvalidFlag = true;
							// cardStatusAvailable = false;
						}
					}
					pst.setString(
							SIMInfoImportConstants.COL_cardInitialBalance,
							String.valueOf(parseDoubleValue)); // cardInitialBalance

					if (result[i][SIMInfoImportConstants.SIMIfActivated] == "") {
						pst.setString(SIMInfoImportConstants.SIMIfActivated,
								"否"); // SIMIfActivated
					} else {
						if ("是".equals(result[i][SIMInfoImportConstants.SIMIfActivated])) {
							pst.setString(
									SIMInfoImportConstants.SIMIfActivated, "是");
						} else { // 其他一切都'否', 不作 requiredInvalidFlag 处理
							pst.setString(
									SIMInfoImportConstants.SIMIfActivated, "否");
						}
					}

					pst.setString(
							SIMInfoImportConstants.COL_simActivateCode,
							result[i][SIMInfoImportConstants.COL_simActivateCode]); // simActivateCode
					if (StringUtils
							.isBlank(result[i][SIMInfoImportConstants.COL_SIMActivateDate])) {
						pst.setString(
								SIMInfoImportConstants.COL_SIMActivateDate,
								null); // SIMActivateDate
					} else {
						pst.setString(
								SIMInfoImportConstants.COL_SIMActivateDate,
								result[i][SIMInfoImportConstants.COL_SIMActivateDate]); // SIMActivateDate
					}
					if (StringUtils
							.isBlank(result[i][SIMInfoImportConstants.COL_SIMEndDate])) {
						pst.setString(SIMInfoImportConstants.COL_SIMEndDate,
								null); // SIMEndDate
					} else {
						pst.setString(
								SIMInfoImportConstants.COL_SIMEndDate,
								result[i][SIMInfoImportConstants.COL_SIMEndDate]); // SIMEndDate
					}

					// pst.setString(27,
					// result[i][SIMInfoImportConstants.COL_planActivateCode]);
					// // planActivateCode
					// 日期类, 并设置 planIfActivated,SIMIfActivated
					// pst.setString(19,
					// result[i][SIMInfoImportConstants.COL_planActivateDate]);
					// // planActivateDate
					// pst.setString(20,
					// result[i][SIMInfoImportConstants.COL_planEndDate]); //
					// planEndDate

					pst.setString(SIMInfoImportConstants.COL_queryMethod,
							result[i][SIMInfoImportConstants.COL_queryMethod]); // queryMethod
					pst.setString(
							SIMInfoImportConstants.COL_rechargeMethod,
							result[i][SIMInfoImportConstants.COL_rechargeMethod]); // rechargeMethod
					pst.setString(SIMInfoImportConstants.COL_remark,
							result[i][SIMInfoImportConstants.COL_remark]); // remark

					if (requiredInvalidFlag) {
						requiredInvalidCount++; // 这种情况仍然尝试数据库插入
						requiredInvalidIDs.add(Integer.valueOf(result[i][0])); // 这个最后还要过滤,
																				// 期望它只包含统计已成功添加的记录
					}

					pst.setString(SIMInfoImportConstants.COL_SIMInfoID, UUID
							.randomUUID().toString());
					pst.setString(SIMInfoImportConstants.COL_SIMServerID, "");
					pst.setString(SIMInfoImportConstants.COL_serverIP, "");
					pst.setString(SIMInfoImportConstants.COL_MNC, MNCSegment);
					// // if
					// (result[i][SIMInfoImportConstants.COL_planActivateDate]
					// == "") {
					// // pst.setString(35, "否"); // planIfActivated
					// // } else {
					// // pst.setString(35, "是"); // planIfActivated
					// // }
					// if (result[i][SIMInfoImportConstants.COL_SIMActivateDate]
					// == "") {
					// pst.setString(36, "否"); // SIMIfActivated
					// } else {
					// pst.setString(36, "是"); // SIMIfActivated
					// }

					// SIMCategory 因为目前限定不能混合导入本地卡和漫游卡, 所以这个当由表单直接提供即可
					// 但目前未实现表单输入, 要先测试导入的情况 --> 已可通过下拉选择“本地”或“漫游卡”
					// 若模板的值为空，则相应填入类型
					if (isSIMInfoNotRoamingSIMInfo) {
						pst.setString(SIMInfoImportConstants.COL_SIMCategory,
								"本地卡"); // SIMCategory
					} else {
						pst.setString(SIMInfoImportConstants.COL_SIMCategory,
								"漫游卡"); // SIMCategory
					}

					// if (cardStatusAvailable) {
					// if (result[i][SIMInfoImportConstants.COL_cardStatus] ==
					// "") {
					// result[i][SIMInfoImportConstants.COL_cardStatus] = "可用";
					// // 约定的默认值
					// }
					// } else {
					// result[i][SIMInfoImportConstants.COL_cardStatus] = "不可用";
					// }
					pst.setString(SIMInfoImportConstants.COL_cardStatus,
							result[i][27]); // cardStatus
					// //
					// TODO:
					// 待确认新插入卡的默认状态

					pst.setString(SIMInfoImportConstants.COL_creatorUserID,
							adminUserInfo.userID);
					pst.setString(SIMInfoImportConstants.COL_creatorUserName,
							adminUserInfo.userName);
					pst.setString(SIMInfoImportConstants.COL_creatorDate,
							sdfdate.format(new Date()).toString());
					pst.setString(SIMInfoImportConstants.COL_cardSource,
							result[i][28]);

					// pst.addBatch(); //事务整体添加 -->
					// 开始数据库插入. 若用 addBatch + executeBatch 则发现若有任何一条失败, 则全部不会添加,
					// 故不能使用
					try {
						pst.execute();
						insertOKCount++;
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
						insertFailedCount++;
						insertFailedIDs.add(Integer.valueOf(result[i][0]));
					}

				} // if (isIMSIexists.get(i))
			}

			// // 批量整体提交
			// int[] sqlResult = null;
			// try {
			// sqlResult = pst.executeBatch();
			// conn.commit();
			// } catch (BatchUpdateException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// sqlResult = e.getUpdateCounts();
			// } catch (SQLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// if (null != sqlResult) {
			// for (int j = 0; j < sqlResult.length; j++) {
			// if (sqlResult[j] > 0) {
			// insertOKCount ++;
			// } else {
			// insertFailedCount ++;
			// }
			// }
			// }
			// //allDatacount = rowLength;

			// 过滤因缺失关键字段的记录统计, 期望它只包含统计已成功添加的记录
			// for (String id : requiredInvalidIDs) { //
			// java.util.ConcurrentModificationException
			// if (keyEmptyIDs.contains(id) || insertFailedIDs.contains(id) ) {
			// requiredInvalidIDs.remove(id);
			// requiredInvalidCount--;
			// }
			// }
			for (Iterator<Integer> iterator = requiredInvalidIDs.iterator(); iterator
					.hasNext();) {
				Integer id = (Integer) iterator.next();
				if (keyEmptyIDs.contains(id) || insertFailedIDs.contains(id)) {
					iterator.remove();
					requiredInvalidCount--;
				}
			}

			AdminOperate admin = new AdminOperate();
			admin.setOperateID(UUID.randomUUID().toString()); // id
			admin.setCreatorUserID(adminUserInfo.userID); // 创建人ID
			admin.setCreatorUserName(adminUserInfo.userName); // 创建人姓名

			admin.setOperateContent(multipartFile.getOriginalFilename()
					+ ", 批量导入" + ((isSIMInfoNotRoamingSIMInfo) ? "本地" : "漫游")
					+ "SIM卡, 成功新增插入 " + insertOKCount + " 条, 插入失败 "
					+ insertFailedCount + " 条, 并成功更新" + updateOKCount
					+ " 条, 无效数据 " + keyEmptyCount + " 条, 原始记录总计 "
					+ allDatacount + " 条."); // 操作内容

			admin.setOperateMenu("SIM卡管理>批量导入本地SIM卡"); // 操作菜单
			admin.setOperateType("新增"); // 操作类型
			// admin.setSysStatus(1);
			// SimpleDateFormat sdfdate = new SimpleDateFormat();// 格式化时间
			// sdfdate.applyPattern("yyyy-MM-dd HH:mm:ss");// a为AM/PM的标记
			// String createtime = sdfdate.format(new Date()).toString();// 创建时间
			// admin.setCreatorDate(createtime);// 创建时间
			// admin.setOperateDate(createtime);// 操作时间
			adminOperateSer.insertdata(admin);

			System.out.println("批量导入数据库写入结束");

		} catch (SQLException e) {
			e.printStackTrace();
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			return "redirect:import";
		} finally {
			// 关闭PreparedStatement
			if (pst != null) {
				pst.close();
				pst = null;
			}
			// 关闭Connection
			if (conn != null) {
				conn.close();
				pst = null;
			}
		}

		// -----结束时间------
		Long endTime = System.currentTimeMillis();
		String usetime = sdf.format(new Date(endTime - startTime));

		String requiredInvalidIDsString = "";
		String insertFailedIDsString = "";
		String keyEmptyIDsString = "";
		String keyUpdateFailedIDsString = "";
		if (requiredInvalidCount > 0) {
			Collections.sort(requiredInvalidIDs);
			requiredInvalidIDsString = "<br />关键字段缺失的记录序号: ";
			for (Integer id : requiredInvalidIDs) {
				requiredInvalidIDsString += id + ", ";
			}
		}
		if (insertFailedCount > 0) {
			Collections.sort(insertFailedIDs);
			insertFailedIDsString = "<br />插入失败的记录序号: ";
			for (Integer id : insertFailedIDs) {
				insertFailedIDsString += id + ", ";
			}
		}
		if (keyEmptyCount > 0) {
			Collections.sort(keyEmptyIDs);
			keyEmptyIDsString = "<br />无效数据的记录序号(缺少IMSI或格式不对): ";
			for (Integer id : keyEmptyIDs) {
				keyEmptyIDsString += id + ", ";
			}
		}
		// updateFailedIDs
		if (updateFailedCount > 0) {
			Collections.sort(updateFailedIDs);
			keyUpdateFailedIDsString = "<br />更新失败的记录序号: ";
			for (Integer id : updateFailedIDs) {
				keyUpdateFailedIDsString += id + ", ";
			}
		}
		requiredInvalidIDsString += keyEmptyIDsString + insertFailedIDsString
				+ keyUpdateFailedIDsString;
		if (StringUtils.isNotBlank(requiredInvalidIDsString)) {
			requiredInvalidIDsString = "<br />" + requiredInvalidIDsString;
		}

		req.getSession().setAttribute(
				"excelmessage",
				"《" + multipartFile.getOriginalFilename()
						+ "》<br /><br />成功新插入 <b>" + insertOKCount
						+ "</b> 条, 并更新 <b>" + updateOKCount + "</b> 条"
						+ ((isSIMInfoNotRoamingSIMInfo) ? "本地" : "漫游")
						+ "SIM卡数据, 共用时：" + usetime + "<br />其中, 有 <b>"
						+ requiredInvalidCount
						+ "</b> 条数据, 因关键字段缺失被强制设置'SIM卡状态'为'不可用'"
						+ "<br /><br />原始记录总数：<b>" + allDatacount
						+ "</b><br />无效数据条数：<b>" + keyEmptyCount
						+ "</b><br /><br />插入记录总条数：<b>" + insertRecordCount
						+ "</b><br />插入失败条数：<b>" + insertFailedCount
						+ "</b><br /><br />更新记录总条数：<b>"
						+ (allDatacount - keyEmptyCount - insertRecordCount)
						+ "</b><br />更新失败条数：<b>" + updateFailedCount + "</b>"
						+ requiredInvalidIDsString);

		// System.out.println("用时：" + usetime);
		// System.out.println("共导入" + allDatacount + "条数据！");

		return "redirect:import";
	}

	@SuppressWarnings("unused")
	private Connection getDBConn() throws ClassNotFoundException, SQLException,
			IOException {
		// 读取链接数据库的内容
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"jdbc.properties");
		Properties myProperty = new Properties();
		myProperty.load(in);

		String driverClass = myProperty.getProperty("jdbc.driverClassName");
		String url = myProperty.getProperty("jdbc.url");
		String user = myProperty.getProperty("jdbc.username");
		String password = myProperty.getProperty("jdbc.password");

		// System.out.println(in + " driverClass:" + driverClass + " url:" + url
		// + " user:" + user + " password:" + password);

		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("数据库连接成功");
		return (Connection) java.sql.DriverManager.getConnection(url, user,
				password);
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/siminfoviewserver")
	public String siminfoviewserver(String imsi, Model model) {
		SIMInfo simInfo = new SIMInfo();
		simInfo.setIMSI(imsi);
		SIMInfo sim = simInfoSer.getSIMInfoBysimAlias(simInfo);
		if (StringUtils.isNotBlank(sim.getSlotNumber())) {
			String[] strings = sim.getSlotNumber().split("-");
			sim.setSlotNumber(strings[0] + "-"
					+ (Integer.parseInt(strings[1]) + 1));
		}
		model.addAttribute("Model", sim);
		return "WEB-INF/views/sim/siminfo_view";

	}

	@RequestMapping(value = "/updatenotes", method = RequestMethod.POST)
	public void updateNotes(SIMInfo info, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		if (StringUtils.isBlank(info.getSIMinfoID()) || null == info.getNotes()) {
			try {
				jsonResult.put("code", -1);
				jsonResult.put("msg", "请指定sim卡ID和标记内容!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		SIMInfo detailInfo = simInfoSer.getById(info.getSIMinfoID());
		if (detailInfo == null) {
			try {
				jsonResult.put("code", -2);
				jsonResult.put("msg", "查无此SIM卡!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		if (simInfoSer.updateSimNotes(info)) {
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "标记成功");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		} else {
			try {
				jsonResult.put("code", -3);
				jsonResult.put("msg", "标记失败, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 批量修改卡状态
	 * 
	 * @param IMSI
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateCardStatus")
	public void updateCardStatus(String IMSI, int cardStatus,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(IMSI) || StringUtils.isBlank(cardStatus)) {
			try {
				response.getWriter().print("-1");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String iMSIString = "'" + IMSI.replaceAll(",", "','") + "'";
		SIMInfo simInfo = new SIMInfo();
		simInfo.setIMSI(iMSIString);
		if (cardStatus == 0)
			simInfo.setCardStatus("使用中");
		if (cardStatus == 1)
			simInfo.setCardStatus("可用");
		if (cardStatus == 2)
			simInfo.setCardStatus("调试不可用");
		if (cardStatus == 3)
			simInfo.setCardStatus("停用");
		if (cardStatus == 4)
			simInfo.setCardStatus("下架");
		if (simInfoSer.updataStatusByIMSI(simInfo)) {
			try {
				response.getWriter().print("1");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().print("0");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 进入SIMAndSN列表页
	 * 
	 * @param searchDTO
	 * @param simandsn
	 * @param req
	 */
	@RequestMapping("/simandsn")
	public String simandsn(HttpServletRequest req, Model model, String imsi) {

		model.addAttribute("IMSI", imsi);
		return "WEB-INF/views/sim/simandsn";
	}

	/**
	 * 加载SIMAndSN列表
	 * 
	 * @param searchDTO
	 * @param simandsn
	 * @param req
	 */
	@RequestMapping("/simandsnlist")
	public void simandsnlist(SearchDTO searchDTO, SIMAndSN simandsn,
			HttpServletResponse response) {

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), simandsn);
		String jsonString = simInfoSer.getPageSIMAndSN(seDto);
		System.out.println(jsonString);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * SIM卡详情 by ID
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view1/{IMSI}")
	public String view1(@PathVariable String IMSI, Model model) {
		SIMInfo info = simInfoSer.getByImsi(IMSI);

		if (StringUtils.isNotBlank(info.getSlotNumber())) {
			String[] strings = info.getSlotNumber().split("-");
			info.setSlotNumber(strings[0] + "-"
					+ (Integer.parseInt(strings[1]) + 1));
		}

		if (info != null && info.getSIMinfoID() != null) {
			if (StringUtils.isBlank(info.getMCC())) {
				model.addAttribute("Model_MCCCountryName", "");
			} else {
				CountryInfo country = countryInfoSer.getByMCC(info.getMCC());
				model.addAttribute("Model_MCCCountryName",
						country.getCountryName());
			}
			if (StringUtils.isBlank(info.getServerIP())) {
				model.addAttribute("Model_serverCode", "");
			} else {
				model.addAttribute("Model_serverCode",
						StringUtils.right(info.getServerIP(), 3));
			}

			List<CountryInfo> countries = countryInfoSer.getAll("");
			List<SIMServer> simServers = simServerSer.getAll("");

			if (!StringUtils.isBlank(info.getCountryList())) {
				info.setCountryList(CountryUtils
						.getCountryNamesFromSiminfoCountryList(
								info.getCountryList(), countries));
			}

			model.addAttribute("Model", info);
			model.addAttribute("Countries", countries);
			model.addAttribute("SIMServers", simServers);
		} else {
			model.addAttribute("info", "此ID的SIM卡不存在或已无效!");
		}
		return "WEB-INF/views/sim/siminfo_view";
	}

	@RequestMapping("/excelimportSIM")
	public void excelimportSIM(SIMInfo info, HttpServletRequest request,
			HttpServletResponse response) {
		String IMSI = info.getIMSI();
		IMSI = IMSI.replace(",", "\",\"");
		info.setIMSI(IMSI);

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setColumnWidth((short) 0, (short) 4000);
		sheet.setColumnWidth((short) 1, (short) 6000);
		sheet.setColumnWidth((short) 2, (short) 6000);
		sheet.setColumnWidth((short) 3, (short) 3000);
		sheet.setColumnWidth((short) 4, (short) 3000);
		sheet.setColumnWidth((short) 5, (short) 3000);
		sheet.setColumnWidth((short) 6, (short) 4000);
		sheet.setColumnWidth((short) 7, (short) 4500);
		sheet.setColumnWidth((short) 8, (short) 4000);
		sheet.setColumnWidth((short) 9, (short) 4000);
		sheet.setColumnWidth((short) 10, (short) 4000);
		sheet.setColumnWidth((short) 11, (short) 4000);
		sheet.setColumnWidth((short) 12, (short) 5000);
		sheet.setColumnWidth((short) 13, (short) 6000);
		sheet.setColumnWidth((short) 14, (short) 6000);
		sheet.setColumnWidth((short) 15, (short) 6000);
		sheet.setColumnWidth((short) 16, (short) 6000);
		sheet.setColumnWidth((short) 17, (short) 6000);
		sheet.setColumnWidth((short) 18, (short) 6000);
		sheet.setColumnWidth((short) 19, (short) 6000);
		sheet.setColumnWidth((short) 20, (short) 3000);
		sheet.setColumnWidth((short) 21, (short) 6000);
		sheet.setColumnWidth((short) 22, (short) 6000);
		sheet.setColumnWidth((short) 23, (short) 6000);
		sheet.setColumnWidth((short) 24, (short) 6000);
		sheet.setColumnWidth((short) 25, (short) 6000);
		sheet.setColumnWidth((short) 26, (short) 6000);
		sheet.setColumnWidth((short) 27, (short) 6000);
		// sheet.setDefaultRowHeightInPoints(5000);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 750);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		// 设置字体
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 11);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(headfont);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style.setWrapText(true);

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("IMSI");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("ICCID");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("高/低速");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("卡编号(SIM代号)");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("是否支持漫游");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellValue("发行国家(MCC)");
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellValue("运营商");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellValue("可使用国家列表(MCC)");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellValue("PIN");
		cell.setCellStyle(style);

		cell = row.createCell((short) 10);
		cell.setCellValue("PUK");
		cell.setCellStyle(style);

		cell = row.createCell((short) 11);
		cell.setCellValue("APN");
		cell.setCellStyle(style);

		cell = row.createCell((short) 12);
		cell.setCellValue("INEI");
		cell.setCellStyle(style);

		cell = row.createCell((short) 13);
		cell.setCellValue("当前套餐类型(套餐名称)");
		cell.setCellStyle(style);

		cell = row.createCell((short) 14);
		cell.setCellValue("套餐流量大小");
		cell.setCellStyle(style);

		cell = row.createCell((short) 15);
		cell.setCellValue("剩余流量大小");
		cell.setCellStyle(style);

		cell = row.createCell((short) 16);
		cell.setCellValue("SIM卡结算方式");
		cell.setCellStyle(style);

		cell = row.createCell((short) 17);
		cell.setCellValue("套餐金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 18);
		cell.setCellValue("卡内初始金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 19);
		cell.setCellValue("可用余额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 20);
		cell.setCellValue("卡是否激活");
		cell.setCellStyle(style);

		cell = row.createCell((short) 21);
		cell.setCellValue("卡激活号码");
		cell.setCellStyle(style);

		cell = row.createCell((short) 22);
		cell.setCellValue("卡激活日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 23);
		cell.setCellValue("卡到期日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 24);
		cell.setCellValue("查询方法说明");
		cell.setCellStyle(style);

		cell = row.createCell((short) 25);
		cell.setCellValue("充值方法说明");
		cell.setCellStyle(style);

		cell = row.createCell((short) 26);
		cell.setCellValue("备注(包括不限于:等)");
		cell.setCellStyle(style);

		cell = row.createCell((short) 27);
		cell.setCellValue("卡状态");
		cell.setCellStyle(style);

		cell = row.createCell((short) 28);
		cell.setCellValue("卡来源");
		cell.setCellStyle(style);

		HSSFFont headfont1 = wb.createFont();
		headfont1.setFontName("宋体");
		headfont1.setFontHeightInPoints((short) 8);// 字体大小

		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setFont(headfont1);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style2.setWrapText(true);

		row = sheet.createRow((int) 1);
		row.setHeight((short) 1200);

		HSSFCell cellremark = row.createCell((short) 0);
		cellremark.setCellValue("字段要求:序号必需! 很重要,并保证序号不重复");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 1);
		cellremark.setCellValue("必需, 18位数字. 若不提供，则该行是无效数据。若IMSI不存在，则新增，否则更新");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 2);
		cellremark.setCellValue("可选，一般留空。服务器会自行读出");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 3);
		cellremark.setCellValue("必需，0为高速，1为低速");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 4);
		cellremark.setCellValue("可选,最大长度10");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 5);
		cellremark.setCellValue("必需，是或否.请使用1或0别对应是或否来输入");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 6);
		cellremark
				.setCellValue("必需,可留空.填国家编号.程序上会从IMSI截取去作一个判断。若为空或者不一致，则使用截取的");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 7);
		cellremark.setCellValue("可选.若非空,则录入数据库,至于与运营商管理的关系,后续再处理.例 VDF或中国联通");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 8);
		cellremark
				.setCellValue("必需,最大长度100.多个国家时以|分开,例如: 454|455|460 若为新记录插入时, 留空默认值为111");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 9);
		cellremark.setCellValue("可选,最大长度20");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 10);
		cellremark.setCellValue("可选,最大长度20");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 11);
		cellremark.setCellValue("可选,最大长度20");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 12);
		cellremark.setCellValue("0");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 13);
		cellremark.setCellValue("可选,最大长度50，可以为空");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 14);
		cellremark.setCellValue("必需. 严格按照带G或M或K分别对应的单位数值,导入时自动识别");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 15);
		cellremark.setCellValue("必需. 严格按照带G或M或K分别对应的单位数值,导入时自动识别");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 16);
		cellremark.setCellValue("必需,下列值之一:后付费,预付费,或者考虑设定默认值，若留空则为后付费");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 17);
		cellremark.setCellValue("必需,数字");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 18);
		cellremark.setCellValue("可选,数字.留空则为零");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 19);
		cellremark.setCellValue("不再必需，可选,数字。留空则与套餐金额同");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 20);
		cellremark.setCellValue("必需，很重要。填是或否");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 21);
		cellremark
				.setCellValue("很重要，若未激活的卡必需提供。可选, 严格按号码格式,若要其他文字说明,补充到备注,最大长度32");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 22);
		cellremark.setCellValue("可选,严格按照下列格式,用斜杆 2015/9/2");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 23);
		cellremark.setCellValue("可选,严格按照下列格式,用斜杆 2015/9/2");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 24);
		cellremark.setCellValue("可选,最大长度50");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 25);
		cellremark.setCellValue("可选,最大长度50");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 26);
		cellremark.setCellValue("可选,最大长度100");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 27);
		cellremark.setCellValue("可用、使用中、备用、下架、库存");
		cellremark.setCellStyle(style2);

		cellremark = row.createCell((short) 28);
		cellremark.setCellValue("卡来源");
		cellremark.setCellStyle(style2);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		List<SIMInfo> sList = simInfoSer.excelimportSIM(info);

		for (int i = 0; i < sList.size(); i++) {
			// 第四步，创建单元格，并设置值
			row = sheet.createRow((int) i + 2);
			row.setHeight((short) 450);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(i + 1);// 序号
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(sList.get(i).getIMSI());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(sList.get(i).getICCID());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(sList.get(i).getSpeedType());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(sList.get(i).getSimAlias());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(sList.get(i).getIfRoam());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(sList.get(i).getMCC());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 7);
			cell1.setCellValue(sList.get(i).getTrademark());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 8);
			cell1.setCellValue(sList.get(i).getCountryList());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 9);
			cell1.setCellValue(sList.get(i).getPIN());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 10);
			cell1.setCellValue(sList.get(i).getPUK());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 11);
			cell1.setCellValue(sList.get(i).getAPN());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 12);
			cell1.setCellValue(sList.get(i).getIMEI());// INEI
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 13);
			cell1.setCellValue(sList.get(i).getPlanType());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 14);
			cell1.setCellValue(sList.get(i).getPlanData());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 15);
			cell1.setCellValue(sList.get(i).getPlanRemainData());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 16);
			cell1.setCellValue(sList.get(i).getSimBillMethod());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 17);
			cell1.setCellValue(sList.get(i).getPlanPrice());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 18);
			cell1.setCellValue(sList.get(i).getCardInitialBalance());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 19);
			cell1.setCellValue(sList.get(i).getCardBalance());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 20);
			cell1.setCellValue(sList.get(i).getSIMIfActivated());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 21);
			cell1.setCellValue(sList.get(i).getSimActivateCode());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 22);
			cell1.setCellValue(sList.get(i).getSIMActivateDate());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 23);
			cell1.setCellValue(sList.get(i).getSIMEndDate());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 24);
			cell1.setCellValue(sList.get(i).getQueryMethod());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 25);
			cell1.setCellValue(sList.get(i).getRechargeMethod());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 26);
			cell1.setCellValue(sList.get(i).getRemark());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 27);
			cell1.setCellValue(sList.get(i).getCardStatus());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 28);
			cell1.setCellValue(sList.get(i).getCardSource());
			cell1.setCellStyle(style1);

		}
		try {
			DownLoadUtil.execlExpoprtDownload("SIM卡信息（excel导出）.xls", wb,
					request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/simrecordcurve")
	public String simRecordCurve(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		return "WEB-INF/views/sim/simrecord_curve";
	}

	@RequestMapping("/getSIMRecordCount")
	public void getSIMRecordCount(HttpServletRequest request,
			HttpServletResponse response, SIMRecord simRecord) {

		response.setContentType("application/json;charset=UTF-8");

		List<SIMRecord> list = simInfoSer.getSIMRecordByDate(simRecord);

		JSONObject resultJSON = new JSONObject();
		JSONArray useRecordCount = new JSONArray();
		JSONArray recordCount = new JSONArray();

		for (SIMRecord simRecord2 : list) {

			JSONObject jo1 = new JSONObject();
			jo1.put("date", simRecord2.getCreatorDate());
			jo1.put("count", simRecord2.getUseCount());
			useRecordCount.add(jo1);

			JSONObject jo2 = new JSONObject();
			jo2.put("date", simRecord2.getCreatorDate());
			jo2.put("count", simRecord2.getSIMCount());
			recordCount.add(jo2);
		}

		resultJSON.put("useRecordCount", useRecordCount);
		resultJSON.put("recordCount", recordCount);

		try {

			response.getWriter().write(resultJSON.toString());

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@RequestMapping("/simrecordlist")
	public String simRecordList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "WEB-INF/views/sim/simrecord_list";
	}

	@RequestMapping("/getSIMRecordCountByDate")
	public void getSIMRecordCountByDate(SearchDTO searchDTO,
			SIMRecord simRecord, HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("application/json;charset=UTF-8");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), simRecord);

		String jsonResult = simInfoSer.getSIMRecordPage(seDto);

		try {
			response.getWriter().write(jsonResult);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/simrecorddetails")
	public String simRecordDetails(HttpServletRequest request,
			HttpServletResponse response, SIMRecord simRecord, Model model)
			throws UnsupportedEncodingException {

		List<CountryInfo> countries = countryInfoSer.getAll("");
		if (simRecord == null) {
			simRecord = new SIMRecord();
		}
		if (simRecord != null && StringUtils.isNotBlank(simRecord.getCountry())) {
			String countryName = new String(simRecord.getCountry().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			for (CountryInfo countryInfo : countries) {
				if (countryInfo.getCountryName().equals(countryName)) {
					countryInfo.setSelected(true);
				}
			}
		}
		model.addAttribute("Countries", countries);
		model.addAttribute("creatorDate", StringUtils.isNotBlank(simRecord
				.getCreatorDate()) ? simRecord.getCreatorDate() : "");

		return "WEB-INF/views/sim/simrecord_details";
	}

	@RequestMapping("/getSIMRecordCountByCountry")
	public void getSIMRecordCountByCountry(SearchDTO searchDTO,
			SIMRecord simRecord, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		response.setContentType("application/json;charset=UTF-8");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), simRecord);

		String jsonResult = simInfoSer.getSIMRecordPageByCountry(seDto);

		try {
			response.getWriter().write(jsonResult);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/simCardUsageRecordPageList")
	public String simCardUsageRecordPageList(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "WEB-INF/views/sim/simcardusagerecord_list";
	}

	/**
	 * SIM卡成本统计记录
	 * 
	 * @param searchDTO
	 * @param simCardUsageRecord
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getSIMCardUsageRecordPage")
	public void getSIMCardUsageRecordPage(SearchDTO searchDTO,
			SIMCardUsageRecord simCardUsageRecord, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		response.setContentType("application/json;charset=UTF-8");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), simCardUsageRecord);

		String jsonResult = simInfoSer.getSIMCardUsageRecordPage(seDto);

		try {
			response.getWriter().write(jsonResult);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/searchMoneySum")
	public void searchMoneySum(SIMCardUsageRecord simCardUsageRecord,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");

		String result = simInfoSer.searchMoneySum(simCardUsageRecord);

		DecimalFormat format = new DecimalFormat("0.00");

		String a = format.format(new BigDecimal(result));
		try {
			response.getWriter().write(StringUtils.isBlank(a) ? "0" : a);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ********************sim成本统计begin***********************

	// /**
	// * sim卡成本统计
	// *
	// * @author jxc
	// * @param request
	// * @param response
	// * @throws ParseException
	// */
	// @RequestMapping("simPirmeCost")
	// public void simPirmeCost(String creatorDate, HttpServletRequest request,
	// HttpServletResponse response) throws ParseException
	// {
	// List<CountryInfo> countries = countryInfoSer.getAll("");
	// HashMap<String, String> mccNameMap = new HashMap<String, String>();
	// for (CountryInfo item : countries)
	// {
	// // ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
	// // 中为 String
	// mccNameMap.put(String.valueOf(item.getCountryCode()),
	// item.getCountryName());
	// }
	// // 查询当天有效sim卡
	// SIMInfo simInfo = new SIMInfo();
	// List<SIMInfo> simInfos = simInfoSer.getAll(simInfo);
	//
	// JSONObject object = new JSONObject();
	// object.put("success", true);
	// object.put("totalRows", simInfos.size());
	// object.put("curPage", 1);
	// JSONArray ja = new JSONArray();
	//
	// // int price = 0;
	// double totalPrimeCost = 0; // sim卡总成本
	// for (SIMInfo sInfo : simInfos)
	// {
	// double planPrice = sInfo.getPlanPrice(); // 1
	// int planData = sInfo.getPlanData(); // 2
	// // int planDay = Integer.parseInt(sInfo.getPlanDay()); // 3
	//
	// int planDay = 0;
	// String endDate = sInfo.getSIMEndDate();
	// String activateDate = sInfo.getSIMActivateDate();
	// if (StringUtils.isNotBlank(endDate) &&
	// StringUtils.isNotBlank(activateDate))
	// {
	// planDay = (int) DateUtils.getDateSUM(activateDate, endDate,
	// "yyyy-MM-dd HH:mm:ss");
	// }
	// else
	// {
	// planDay = 0;
	// }
	//
	// double singlePrimeCost = 0; // 单个sim卡成本
	// SIMRecord sRecord = null;
	// if (planData != 0 && planDay != 0)
	// { // 按天 按流量
	// double flowPrice = planPrice / planData * 1024; // 按流量单价 （M）
	// double dayPirce = planPrice / planDay; // 按天单价
	// // 算出用了多少的流量
	// SIMRecord simRecord = new SIMRecord();
	// simRecord.setIMSI(sInfo.getIMSI());
	// simRecord.setCreatorDate(creatorDate);
	// sRecord = simInfoSer.getSIMRecordByIMSI(simRecord);
	//
	// if (sRecord == null)
	// {
	// // 进来说明此卡当天没使用
	// singlePrimeCost = planPrice / planDay;
	//
	// }
	// else
	// {
	// int useFlow = sRecord.getUseFlow();
	// if (dayPirce > useFlow / 1024 * flowPrice)
	// {
	// singlePrimeCost = dayPirce;
	// }
	// else
	// {
	// singlePrimeCost = useFlow / 1024 * flowPrice;
	// }
	// }
	// }
	// else if (planData == 1073741824 && planDay != 0)
	// { // 按天
	// singlePrimeCost = planPrice / planDay;
	//
	// }
	// else if (planDay == 0 && planData != 1073741824)
	// { // 按流量
	// singlePrimeCost = planPrice / planData * 1024;
	// }
	//
	// totalPrimeCost += singlePrimeCost;
	//
	// JSONObject obj = new JSONObject();
	// obj.put("IMSI", sInfo.getIMSI());
	// obj.put("primeCost", singlePrimeCost);
	// if (sRecord == null)
	// {
	// obj.put("useFlow",0);
	// obj.put("assignedSN","");
	// obj.put("creatorDate","");
	// }
	// else
	// {
	// obj.put("useFlow", sRecord.getUseFlow());
	// obj.put("assignedSN", sRecord.getAssignedSN());
	// obj.put("creatorDate", sRecord.getCreatorDate());
	// obj.put("country", sRecord.getCountry());
	// }
	//
	// String codelistString = sInfo.getCountryList();
	// String cnameString = "";
	// if (codelistString.indexOf("|") >= 0)
	// {
	// String[] codearr = codelistString.split("\\|");
	//
	// for (int i = 0; i < codearr.length; i++)
	// {
	// if (i == codearr.length - 1)
	// {
	// cnameString += mccNameMap.get(codearr[i]);
	// }
	// else
	// {
	// cnameString += mccNameMap.get(codearr[i]) + ",";
	// }
	// }
	// }
	// else
	// {
	// cnameString += mccNameMap.get(codelistString);
	// }
	//
	// obj.put("country", cnameString);
	//
	// ja.add(obj);
	// }
	//
	// object.put("data", ja);
	//
	// try
	// {
	// response.getWriter().print(object.toString());
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	//
	// }

	/**
	 * sim卡成本统计首页
	 * 
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("tosimPirmeCost")
	public String tosimPirmeCost(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		return "WEB-INF/views/sim/siminfo_pirmecost";
	}

	/**
	 * sim卡使用成本首页分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getPageSIMcostStatistics")
	public void getPageSIMcostStatistics(SearchDTO searchDTO, SIMInfo info,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getPageSIMcostStatistics(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sim卡成本统计二级页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */

	@RequestMapping("tosimPirmeCostInfo")
	public String tosimPirmeCostInfo(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("enddate", request.getParameter("enddate"));
		model.addAttribute("begindate", request.getParameter("begindate"));
		model.addAttribute("MCC", request.getParameter("MCC"));

		return "WEB-INF/views/sim/siminfo_pirmecostinfo";
	}

	/**
	 * 成本SIM卡二级页面 分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getPageSIMcostStatisticsInfo")
	public void getPageSIMcostStatisticsInfo(SearchDTO searchDTO, SIMInfo info,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getPageSIMcostStatisticsInfo(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * news 一级页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tosimPirmeCosttwo")
	public String tosimPirmeCosttwo(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("enddate", request.getParameter("enddate"));
		model.addAttribute("begindate", request.getParameter("begindate"));
		model.addAttribute("mcc", request.getParameter("mcc"));

		return "WEB-INF/views/sim/siminfo_pirmecosttwo";
	}

	/**
	 * news 一级页面分面查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getPageSIMcostStatistics1")
	public void getPageSIMcostStatistics1(SearchDTO searchDTO,
			SIMcostStatistics info, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		if (StringUtils.isNotBlank(info.getCountry())) {
			CountryInfo countryInfo = new CountryInfo();
			countryInfo.setCountryName(info.getCountry());
			CountryInfo countryInfo2 = countryInfoSer
					.selectCountryInfoBycname(countryInfo);
			info.setCountry(String.valueOf(countryInfo2.getCountryCode()));
		} else {

		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getPageSIMcostStatistics1(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * news 二级页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tosiminfo_pirmecostinfobyimsi")
	public String tosiminfo_pirmecostinfobyimsi(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("IMSI", request.getParameter("IMSI"));
		model.addAttribute("begindate", request.getParameter("begindate"));
		model.addAttribute("enddate", request.getParameter("enddate"));

		return "WEB-INF/views/sim/siminfo_pirmecostinfobyimsi";
	}

	/**
	 * news 二级页面分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getPageSIMcostStatisticsByDay")
	public void getPageSIMcostStatisticsByDay(SearchDTO searchDTO,
			SIMcostStatistics info, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getPageSIMcostStatisticsByDay(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * news SIMcostStatistics 表 新增数据
	 * 
	 * @param creatorDate
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping("simPirmeCosttwo")
	public void simPirmeCosttwo(String creatorDate, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		// 查询当天有效sim卡
		SIMInfo simInfo = new SIMInfo();
		simInfo.setModifyDate(creatorDate);
		List<SIMInfo> simInfos = simInfoSer.getDayUserAll(simInfo);

		double totalPrimeCost = 0; // sim卡总成本
		for (SIMInfo sInfo : simInfos) {
			double planPrice = sInfo.getPlanPrice(); // 1
			int planData = sInfo.getPlanData(); // 2
			int planDay = 0;
			String endDate = sInfo.getSIMEndDate();
			String activateDate = sInfo.getSIMActivateDate();
			if (StringUtils.isNotBlank(endDate)
					&& StringUtils.isNotBlank(activateDate)) {
				planDay = (int) DateUtils.getDateSUM(activateDate, endDate,
						"yyyy-MM-dd HH:mm:ss");
			} else {
				planDay = 0;
			}

			double singlePrimeCost = 0; // 单个sim卡成本
			SIMRecord sRecord = null;
			if (planData != 0 && planDay != 0) { // 按天 按流量
				double flowPrice = planPrice / planData * 1024; // 按流量单价 （M）
				double dayPirce = planPrice / planDay; // 按天单价
				// 算出用了多少的流量
				SIMRecord simRecord = new SIMRecord();
				simRecord.setIMSI(sInfo.getIMSI());
				simRecord.setCreatorDate(creatorDate);
				sRecord = simInfoSer.getSIMRecordByIMSI(simRecord);

				if (sRecord == null) {
					// 进来说明此卡当天没使用
					singlePrimeCost = planPrice / planDay;
				} else {
					int useFlow = sRecord.getUseFlow();
					if (dayPirce > useFlow / 1024 * flowPrice) {
						singlePrimeCost = dayPirce;
					} else {
						singlePrimeCost = useFlow / 1024 * flowPrice;
					}
				}
			} else if (planData == 1073741824 && planDay != 0) { // 按天
				singlePrimeCost = planPrice / planDay;
			} else if (planDay == 0 && planData != 1073741824) { // 按流量
				singlePrimeCost = planPrice / planData * 1024;
			}
			totalPrimeCost += singlePrimeCost;
			SIMcostStatistics siMcostStatistics = null;
			if (sRecord == null) {
				siMcostStatistics = new SIMcostStatistics(sInfo.getIMSI(), "",
						"", "", singlePrimeCost, 0, sInfo.getCountryList());
			} else {
				siMcostStatistics = new SIMcostStatistics(sInfo.getIMSI(),
						creatorDate, sRecord.getAssignedSN(),
						sRecord.getCountry(), singlePrimeCost,
						sRecord.getUseFlow(), sInfo.getCountryList());
			}
			simInfoSer.insertSIMcostStatistics(siMcostStatistics);
		}
	}

	/**
	 * sim卡成本统计三级页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("tosiminfo_pirmecostinfothree")
	public String tosiminfo_pirmecostinfothree(HttpServletResponse response,
			HttpServletRequest request, Model model)
			throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("enddate", request.getParameter("enddate"));
		model.addAttribute("begindate", request.getParameter("begindate"));
		model.addAttribute("MCC", request.getParameter("MCC"));
		// model.addAttribute("planTypePar", new
		// String(request.getParameter("planType").getBytes("iso-8859-1"),"utf-8"));

		model.addAttribute("planTypePar", request.getParameter("planType"));

		return "WEB-INF/views/sim/siminfo_pirmecostinfothree";
	}

	/**
	 * sim卡成本统计三级页面 分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("getPageSiminfo_pirmeCostInfoThree")
	public void getPageSiminfo_pirmeCostInfoThree(SearchDTO searchDTO,
			String planTypePar, SIMInfo info, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		if (StringUtils.isNotBlank(planTypePar)) {
			planTypePar = new String(planTypePar.getBytes("iso-8859-1"),
					"utf-8");
			info.setPlanType(planTypePar);
		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getPageSiminfopirmeCostInfoThree(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 四级页面
	 */
	@RequestMapping("tosiminfo_pirmecostbyimsiuserdays")
	public String tosiminfo_pirmecostbyimsiuserdays(
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("enddate", request.getParameter("enddate"));
		model.addAttribute("begindate", request.getParameter("begindate"));
		model.addAttribute("MCC", request.getParameter("MCC"));
		model.addAttribute("IMSI", request.getParameter("IMSI"));
		return "WEB-INF/views/sim/siminfo_pirmecostbyimsiuserdays";
	}

	/**
	 * 四级页面分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("getSIMRecordPageByIMSI")
	public void getSIMRecordPageByIMSI(SearchDTO searchDTO, SIMInfo info,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getSIMRecordPageByIMSI(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * IM卡成本统计（按卡类型）首页
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tosiminfo_pirmecostinfobyplantype")
	public String tosiminfo_pirmecostinfobyplantype(
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("enddate", request.getParameter("enddate"));
		model.addAttribute("begindate", request.getParameter("begindate"));
		model.addAttribute("MCC", request.getParameter("MCC"));
		model.addAttribute("IMSI", request.getParameter("IMSI"));
		return "WEB-INF/views/sim/siminfo_pirmecostinfobyplantype";
	}

	/**
	 * SIM卡成本统计（按卡类型）首页分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("getpageSIMUserInfoByplanType")
	public void getpageSIMUserInfoByplanType(SearchDTO searchDTO, SIMInfo info,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getpageSIMUserInfoByplanType(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按套餐类型统计二级页面
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("tosiminfo_pirmecostinfobyplantypetwo")
	public String tosiminfo_pirmecostinfobyplantypetwo(
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("enddate", request.getParameter("enddate"));
		model.addAttribute("begindate", request.getParameter("begindate"));
		model.addAttribute("planType", request.getParameter("planType"));
		return "WEB-INF/views/sim/siminfo_pirmecostinfobyplantypetwo";
	}

	@RequestMapping("getpagesimbyplanType")
	public void getpagesimbyplanType(SearchDTO searchDTO, SIMInfo info,
			String planTypePar, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		if (StringUtils.isNotBlank(planTypePar)) {
			planTypePar = new String(planTypePar.getBytes("iso-8859-1"),
					"utf-8");
		}
		info.setPlanType(planTypePar);
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simInfoSer.getpagesimbyplanType(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/simcostexportbycountry")
	public void simcostexportbycountry(SIMInfo info,
			HttpServletResponse response) throws UnsupportedEncodingException {

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultRowHeightInPoints(5000);

		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 6000);
		sheet.setColumnWidth((short) 2, (short) 5500);
		sheet.setColumnWidth((short) 3, (short) 5500);
		sheet.setColumnWidth((short) 4, (short) 5500);
		sheet.setColumnWidth((short) 5, (short) 5500);
		sheet.setColumnWidth((short) 6, (short) 5500);
		sheet.setColumnWidth((short) 7, (short) 5500);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// short c = 500;
		// row.setHeight(c);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("IMSI");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("套餐类型");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("使用国家");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("使用SN");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("总流量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("使用流量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("价格");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("成本(元)");
		cell.setCellStyle(style);

		List<SIMCost> simCosts = simInfoSer.simcostexportbycountry(info);

		for (int i = 0; i < simCosts.size(); i++) {
			SIMCost simCost = simCosts.get(i);

			row = sheet.createRow(i + 1);
			row.setHeight((short) 450);

			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(simCost.getIMSI());
			row.createCell((short) 1).setCellValue(simCost.getPlanType());
			row.createCell((short) 2).setCellValue(simCost.getCountryName());
			row.createCell((short) 3).setCellValue(simCost.getAssignedSN());
			row.createCell((short) 4).setCellValue(simCost.getTotalData());
			row.createCell((short) 5).setCellValue(simCost.getTotalFlow());
			row.createCell((short) 6).setCellValue(simCost.getTotalPrice());
			double primeCost = 0;

			double totalPrice = simCost.getTotalPrice();
			Long totaldata = simCost.getTotalData();
			Long totalFlow = simCost.getTotalFlow();

			if (simCost.getTotalData() != 0) {
				primeCost = (double) (totalPrice / (totaldata / 1024) * (totalFlow / 1024));
			}

			row.createCell((short) 7).setCellValue(primeCost);
		}
		DownLoadUtil.execlExpoprtDownload(
				info.getBegindate() + "到" + info.getEnddate()
						+ "SIM卡成本统计（按国家）.xls", wb, request, response);
	}

	// ********************sim成本统计end***********************
}
