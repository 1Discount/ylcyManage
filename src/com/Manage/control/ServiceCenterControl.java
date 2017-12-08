package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.constants.Constants;
import com.Manage.common.latlng.RemoteUtil;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.MSMRecord;
import com.Manage.entity.common.SearchDTO;
import com.google.gson.JsonArray;

/**
 * ServiceCenterControl.java
 * 
 * 客服中心包含4个界面： 1.实时在线设备：根据设备日志查询上次更新时间在当前分钟内的记录，根据sn过滤，然后联连设备订单表，显示客户等信息。
 * 2.历史在线设备：根据日期查询当天的所以在线过的设备信息
 * 3.当日可用流量交易：查询订单开始时间是当天的或着订单状态是使用中和暂停的信息，然后根据订单绑定的SN联接设备订单
 * 表查询相应信息，如果订单状态是未激活，sn列和相应设备订单列显示为空。如果订单未激活，后面操作按钮有 查看，激活。
 * 如果订单状态为可用或者使用中，后面操作按钮有限速，暂停，查询。如果订单状态为暂停的，后面操作按钮有限速，启用，查看 4.当日使用中的流量订单
 * 根据订单状态为使用中的，而且上次更新时间 是当天的 查询流量订单信息
 * 
 */

@Controller
@RequestMapping("/service/center")
public class ServiceCenterControl extends BaseController {
	private final Logger logger = LogUtil
			.getInstance(ServiceCenterControl.class);

	private String getLogUrl = "http://112.74.141.241:8080/SpringNetty/admin/devicelogs/";

	/**
	 * 首页 // 待用
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		List<Dictionary> continents = dictionarySer
				.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
		model.addAttribute("Continents", continents);
		model.addAttribute("IsIndexView", true);

		return "WEB-INF/views/service/service_index";

	}

	@RequestMapping("/devicelogs")
	public String devicelogs(HttpServletRequest req, DeviceLogs delogs) {

		DeviceLogs devlogs = new DeviceLogs();
		String sn = req.getParameter("sbid");
		String sj = req.getParameter("sj");
		if (sj == null || "".equals(sj)) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			devlogs.setLastTime(sdf2.format(new Date()));

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String times = format.format(date);
			String tname = "DeviceLogs_" + times;
			devlogs.setTableName(tname);
		} else if (sj != null && !"".equals(sj)) {
			sj = sj.replace("-", "");
			String tname = "DeviceLogs_" + sj;
			System.out.println(sj + "/time");
			System.out.println(tname + "/tname");
			devlogs.setTableName(tname);
		}

		devlogs.setSN(sn);
		/*
		 * sj = sj.replace("-", "");
		 * 
		 * String tablename="DeviceLogs_"+sj; devlogs.setTableName(tablename);
		 */

		/* System.out.println("sn:"+sn+"==========="+"tablename:"+tablename); */

		DeviceLogs version = deviceLogsSer.getversion(devlogs);
		if (version != null) {
			req.setAttribute("version", version.getVersion());
			req.setAttribute("versionAPK", version.getVersionAPK());
			req.setAttribute("ICCID", version.getTTContext());
		} else {
			req.setAttribute("version", "暂无数据");
		}
		DeviceLogs res = deviceLogsSer.getfirmWareVer(devlogs);
		if (res != null) {
			req.setAttribute("res", res.getFirmWareVer());
			req.setAttribute("resAPK", res.getFirmWareAPKVer());
		} else {
			req.setAttribute("res", "暂无数据");
		}
		req.setAttribute("sj", sj);
		req.setAttribute("sj", sj);

		return "WEB-INF/views/service/devicelogs";
	}

	@RequestMapping("/deviceLoginLog")
	public String deviceLoginLog(Model model) {
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/devicelogs_loginToday";

	}

	/**
	 * 设备异常登录记录
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("loginlogsEXC")
	public void dengluEXC(SearchDTO searchDTO, DeviceLogs devicelogs,
			String sj, String sn, String tag, HttpServletResponse response,
			HttpServletRequest req) {

		if (req.getParameter("sbid") != null) {
			devicelogs.setSN(req.getParameter("sbid"));
		}
		if (StringUtils.isBlank(sj)) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			devicelogs.setLastTime(sdf2.format(new Date()));

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String times = format.format(date);
			String tname = "DeviceLogs_" + times;
			devicelogs.setTableName(tname);
		} else if (StringUtils.isNotBlank(sj)) {
			sj = sj.replace("-", "");
			String tname = "DeviceLogs_" + sj;

			devicelogs.setTableName(tname);
		}
		devicelogs.setMcc(devicelogs.getMcc());
		devicelogs.setSIMAllot(devicelogs.getSIMAllot());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		String jsonString = deviceLogsSer.getpageloginLogsEXC(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设备登录记录
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("loginlogs")
	public void denglu(SearchDTO searchDTO, DeviceLogs devicelogs, String sj,
			HttpServletResponse response, HttpServletRequest req) {

		String time = req.getParameter("sj");
		if (req.getParameter("sbid") != null) {
			devicelogs.setSN(req.getParameter("sbid"));
		}
		if (StringUtils.isBlank(sj)) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			devicelogs.setLastTime(sdf2.format(new Date()));

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String times = format.format(date);
			String tname = "DeviceLogs_" + times;
			devicelogs.setTableName(tname);
		} else if (StringUtils.isNotBlank(sj)) {
			sj = sj.replace("-", "");
			String tname = "DeviceLogs_" + sj;
			System.out.println(sj + "/time");
			System.out.println(tname + "/tname");
			devicelogs.setTableName(tname);
		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		try {
			String jsonString = getLogService("login", searchDTO, devicelogs.getSN(), sj);
			if(jsonString==null){
				jsonString = deviceLogsSer.getpageloginsLogs(seDto);
			}
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 本地日志
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("bdcardlogs")
	public void bendicard(SearchDTO searchDTO, DeviceLogs devicelogs,
			HttpServletResponse response, HttpServletRequest req) {
		devicelogs.setSN(req.getParameter("sbid"));
		devicelogs.setType("01");
		String time = req.getParameter("sj");
		String sj = req.getParameter("sj");
		if (StringUtils.isBlank(time)) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			devicelogs.setLastTime(sdf2.format(new Date()));

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String times = format.format(date);
			String tname = "DeviceLogs_" + times;
			devicelogs.setTableName(tname);
		} else if (StringUtils.isNotBlank(time)) {
			time = time.replace("-", "");
			String tname = "DeviceLogs_" + time;
			System.out.println(time + "/time");
			System.out.println(tname + "/tname");
			devicelogs.setTableName(tname);
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		try {
			String jsonString = getLogService("local", searchDTO, devicelogs.getSN(), sj);
			if(jsonString==null){
				jsonString = deviceLogsSer.getpagebdcardLogs(seDto);
			}
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 漫游卡流量记录
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("manyoulogs")
	public void manyoucard(SearchDTO searchDTO, DeviceLogs devicelogs,
			HttpServletResponse response, HttpServletRequest req) {
		devicelogs.setSN(req.getParameter("sbid"));
		String time = req.getParameter("sj");
		String sj = req.getParameter("sj");
		if (StringUtils.isBlank(sj)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String times = format.format(date);
			String tname = "DeviceLogs_" + times;
			devicelogs.setTableName(tname);
		} else if (StringUtils.isNotBlank(sj)) {
			sj = sj.replace("-", "");
			String tablename = "DeviceLogs_" + sj;
			devicelogs.setTableName(tablename);
		}
		devicelogs.setType("02");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		try {
			String jsonString = getLogService("heart", searchDTO, devicelogs.getSN(), sj);
			if(jsonString==null){
				jsonString = deviceLogsSer.getpagemanyouLogs(seDto);
			}
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 透传流量记录
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("touchuanlogs")
	public void touchuancard(SearchDTO searchDTO, DeviceLogs devicelogs,
			HttpServletResponse response, HttpServletRequest req) {
		devicelogs.setSN(req.getParameter("sbid"));
		devicelogs.setType("05");
		String sj = req.getParameter("sj");
		String time = req.getParameter("sj");
		if (StringUtils.isBlank(sj)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String times = format.format(date);
			String tname = "DeviceLogs_" + times;
			devicelogs.setTableName(tname);
		} else if (StringUtils.isNotBlank(sj)) {
			sj = sj.replace("-", "");
			String tablename = "DeviceLogs_" + sj;
			devicelogs.setTableName(tablename);
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		try {
			String jsonString = getLogService("pt", searchDTO, devicelogs.getSN(), sj);
			if(jsonString==null){
				jsonString = deviceLogsSer.getpagetouchuanLogs(seDto);
			}
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * VPN分配记录
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("VPNlogs")
	public void VPNlogs(SearchDTO searchDTO, DeviceLogs devicelogs,
			HttpServletResponse response, HttpServletRequest req) {
		devicelogs.setSN(req.getParameter("sbid"));
		devicelogs.setType("07");
		String sj = req.getParameter("sj");
		String time = req.getParameter("sj");
		if (StringUtils.isBlank(sj)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String times = format.format(date);
			String tname = "DeviceLogs_" + times;
			devicelogs.setTableName(tname);
		} else if (StringUtils.isNotBlank(sj)) {
			sj = sj.replace("-", "");
			String tablename = "DeviceLogs_" + sj;
			devicelogs.setTableName(tablename);
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		try {
			String jsonString = getLogService("vpn", searchDTO, devicelogs.getSN(), sj);
			if(jsonString==null){
				jsonString = deviceLogsSer.getpagetouchuanLogs(seDto);
			}
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当天历史日志记录(客服监控中心-->历史在线设备)
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("/listlogs")
	public void datapage(SearchDTO searchDTO, DeviceLogs devicelogs,
			String temp, HttpServletResponse response, String ifOnlyString) {
		response.setContentType("application/json;charset=UTF-8");

		String time = devicelogs.getLastTime();
		//System.out.println(time);
		if (StringUtils.isBlank(time)) {
			devicelogs.setLogsDate(DateUtils.getDate());
		} else if (StringUtils.isNotBlank(time)) {
			devicelogs.setLogsDate(time);
		}
		//devicelogs.setLogsDate("2016-10-03");
		if ("1".equals(ifOnlyString)) {
			devicelogs.setIfOnlyString(ifOnlyString);
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		String jsonString = deviceLogsSer.getpageString(seDto);
		//System.out.println("----->"+jsonString);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当前10分钟在线设备(客服监控中心-->实时在线设备)
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("/nowlistlogs")
	public void datapage2(SearchDTO searchDTO, DeviceLogs devicelogs,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		getSession().setAttribute("nowpagesize", searchDTO.getPageSize());
		devicelogs.setLogsDate(DateUtils.getDate());
		//devicelogs.setLogsDate("2016-10-03");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		String jsonString = deviceLogsSer.getpageStringnews(seDto);
		try {
			response.getWriter().println(jsonString);
			logger.info("实时在线设备：" + jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/listimsi")
	public String dataimsi() {
		return "WEB-INF/views/service/device_FlowDealOrders_history";
	}

	/**
	 * 根据设备的imsi查询logs
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 * @param req
	 */
	@RequestMapping("/listimsilogs")
	public void datapageimsi(SearchDTO searchDTO, DeviceLogs devicelogs,
			HttpServletResponse response, HttpServletRequest req) {
		response.setContentType("application/json;charset=UTF-8");
		devicelogs.setIMSI(req.getParameter("imsi"));
		System.out.println("---" + searchDTO.getCurPage() + "------"
				+ searchDTO.getPageSize());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		String jsonString = flowDealOrdersSer.getpageimsi(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * 当前10分钟在线设备(远程服务操作界面)
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("/nowlistlogs1")
	public void datapage3(SearchDTO searchDTO, DeviceLogs devicelogs,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		getSession().setAttribute("nowpagesize", searchDTO.getPageSize());
		devicelogs.setLogsDate(DateUtils.getDate());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), devicelogs);
		String jsonString = deviceLogsSer.getNowString1(seDto);
		try {
			response.getWriter().println(jsonString);
			logger.info("实时在线设备：" + jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 今日在线设备
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/device/today")
	public String deviceToday(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		model.addAttribute("SN", request.getParameter("SN"));

		// 短信
		String description = "短信提醒";
		List<Dictionary> alertType = dictionarySer.getalertType(description);
		model.addAttribute("alertType", alertType);

		return "WEB-INF/views/service/devicelogs_newDate";

	}

	/**
	 * 历史在线设备
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/device/history")
	public String deviceHistory(String SN, String lastUpdateDate,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		if (StringUtils.isNotBlank(SN)
				&& StringUtils.isNotBlank(lastUpdateDate)) {
			model.addAttribute("SN", SN);
			model.addAttribute("lastUpdateDate",
					lastUpdateDate.substring(0, lastUpdateDate.length() - 11));
		}

		return "WEB-INF/views/service/device_history";

	}

	/**
	 * 分页查询 今天或历史在线设备
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/pageDeviceOnline")
	public void pageDeviceOnline(SearchDTO searchDTO, DeviceDealOrders info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		getSession().setAttribute("historypagesize", searchDTO.getPageSize());
		// 处理参数
		String range = request.getParameter("range");
		if (StringUtils.isBlank(range)) {
			range = "today";
		}

		if ("today".equals(range)) {
			String status = request.getParameter("status");
			if (StringUtils.isBlank(status)) {
				status = "online";
			}
			if ("online".equals(status)) {

			} else if ("offline".equals(status)) {

			} else if ("available".equals(status)) {

			}
		} else if ("history".equals(range)) {

		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = deviceDealOrdersSer
				.getpageDeviceStatusString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 今日流量交易
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/floworder/today")
	public String floworderToday(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		return "WEB-INF/views/service/floworder_today";

	}

	/**
	 * 历史流量交易
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/floworder/history")
	public String floworderHistory(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/floworder_history";

	}

	/**
	 * 最近的交易
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/floworder/incoming")
	public String floworderIncoming(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		return "WEB-INF/views/service/floworder_incoming";

	}

	/**
	 * 分页查询 今天或历史在线流量订单
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/pageFlowOrder")
	public void pageFlowOrder(String example, String enddate,
			SearchDTO searchDTO, FlowDealOrders info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (enddate == null) {
			enddate = sdf.format(new Date());
		}
		if (example == null) {
			example = "";
		}

		// 处理参数,isBlank把空格看成是空
		String range = request.getParameter("range");
		if (StringUtils.isBlank(range)) {
			range = "today";
		}

		String jsonString = "";

		if ("today".equals(range)) {
			String status = request.getParameter("status");
			if (StringUtils.isBlank(status)) {
				status = "online";
			}

			// !!! 下面仅处理按天数的流量订单
			if ("available".equals(status)) { // 当日可用的
				SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
						searchDTO.getPageSize(), searchDTO.getSortName(),
						searchDTO.getSortOrder(), info);
				jsonString = flowDealOrdersSer
						.getpageAvailableOrderString(seDto);

			} else if ("notuser".equals(status)) {
				info.setPanlUserDate(enddate);
				info.setUserCountry(example);
				List<String> countryList = new ArrayList<String>();
				for (int i = 0; i < example.split(",").length; i++) {
					countryList.add(example.split(",")[i]);
				}
				info.setCountryArray(countryList);
				SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
						searchDTO.getPageSize(), searchDTO.getSortName(),
						searchDTO.getSortOrder(), info);
				jsonString = flowDealOrdersSer.selectnotuserString(seDto);
			} else if ("online".equals(status)) {
				info.setSysStatus(true);
				info.setEndDateForLastUpdateDate(null);
				info.setBeginDateForLastUpdateDate(DateUtils.getDate());
				SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
						searchDTO.getPageSize(), searchDTO.getSortName(),
						searchDTO.getSortOrder(), info);
				jsonString = flowDealOrdersSer.getpageString(seDto);
			}
		} else if ("history".equals(range)) {
			// 历史订单, lastUpdateDate今天之前七日内用过的订单, (--SN非空--), 已激活,
			info.setSysStatus(true);
			info.setIfActivate("是");
			info.setEndDateForLastUpdateDate(DateUtils.getDate());
			info.setBeginDateForLastUpdateDate(DateUtils.beforeNDateToString(
					null, -7, "yyyy-MM-dd HH:mm:ss"));
			SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
					searchDTO.getPageSize(), searchDTO.getSortName(),
					searchDTO.getSortOrder(), info);
			jsonString = flowDealOrdersSer.getpageString(seDto);
		} else if ("incoming".equals(range)) {
			// 明日订单, panlUserDate 明天开始七日之内的订单
			// ->改为未来三日,现在按运营那边的要求改为查询预约使用时间是明天的订单。
			info.setSysStatus(true);
			info.setBeginDateForPanlUserDate(DateUtils.beforeNDateToString(
					DateUtils.parseDate(DateUtils.getDate()), 1, "yyyy-MM-dd"));
			info.setEndDateForPanlUserDate(DateUtils.beforeNDateToString(
					DateUtils.parseDate(DateUtils.getDate()), 1, "yyyy-MM-dd")); // 8
			SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
					searchDTO.getPageSize(), searchDTO.getSortName(),
					searchDTO.getSortOrder(), info);
			seDto.setSortName("panlUserDate");
			seDto.setSortOrder("desc");
			jsonString = flowDealOrdersSer.getpageString(seDto);
		}
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 标记在线设备
	 */
	@RequestMapping("/SNtag")
	public void SNtag(String sn, String tag, HttpServletResponse response) {
		AdminUserInfo use = (AdminUserInfo) getSession().getAttribute("User");
		logger.info("标记在线设备:" + sn + "---" + tag);
		Object object = CacheUtils.get("fileCache", "SNTAG");
		JSONObject json = null;
		if (object == null || "".equals(object.toString())) {
			json = new JSONObject();
		} else {
			json = JSONObject.fromObject(object.toString());
			CacheUtils.remove("fileCache", "SNTAG");
		}
		if (StringUtils.isBlank(tag.trim())) {// 移除标记
			json.remove(sn);
			json.put(sn, "");
		} else {// 添加标记
			json.remove(sn);
			json.put(
					sn,
					tag + " |" + use.getUserName() + "_"
							+ DateUtils.getDateTime());
		}
		CacheUtils.put("fileCache", "SNTAG", json.toString());
		logger.info("保存缓存完毕");
		try {
			response.getWriter().print("1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 标记登录异常设备
	 */
	@RequestMapping("/SNtagIMSI")
	public void SNtagIMSI(String sn, String tag, HttpServletResponse response) {
		AdminUserInfo use = (AdminUserInfo) getSession().getAttribute("User");
		logger.info("标记在线设备:" + sn + "---" + tag);
		Object object = CacheUtils.get("fileCache", "SNTAGIMSI");
		JSONObject json = null;
		if (object == null || "".equals(object.toString())) {
			json = new JSONObject();
		} else {
			json = JSONObject.fromObject(object.toString());
			CacheUtils.remove("fileCache", "SNTAGIMSI");
		}
		if (StringUtils.isBlank(tag.trim())) {// 移除标记
			json.remove(sn);
			json.put(sn, "");
		} else {// 添加标记
			if (json.containsKey(sn)) {
				json.remove(sn);
			}

			json.put(
					sn,
					tag + " |" + use.getUserName() + "_"
							+ DateUtils.getDateTime());
		}
		CacheUtils.put("fileCache", "SNTAGIMSI", json.toString());
		try {
			response.getWriter().print("1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到本地服务监控页面
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/localServerMonitor")
	public String localServerMonitor(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "WEB-INF/views/service/local_server_monitor";
	}
	
	/**
	 * 发送短信弹窗页面
	 * zwh
	 * @param model
	 * @return
	 */
	@RequestMapping("/device/sendmsg")
	public String sendmsg(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("SN", request.getParameter("SN"));
		model.addAttribute("phone", request.getParameter("phone"));
		// 短信
		String description = "短信提醒";
		List<Dictionary> alertType = dictionarySer.getalertType(description);
		model.addAttribute("alertType", alertType);

		return "WEB-INF/views/service/sendmsg";

	}
	
	/**
	 * 当天发送短信的记录
	 * 
	 * @param searchDTO
	 * @param devicelogs
	 * @param response
	 */
	@RequestMapping("/device/sendmsglist")
	public void sendmsgrecord(SearchDTO searchDTO, MSMRecord msm,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		getSession().setAttribute("nowpagesize1", searchDTO.getPageSize());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), msm);
		String jsonString = deviceLogsSer.getsendmsgrecord(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询抢卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/searchGrabCard")
	public void searchGrabCard(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		List<DeviceLogs> list = deviceLogsSer.searchGrabCard();

		Map<String, String> map = new HashMap<String, String>();

		for (DeviceLogs deviceLogs : list) {
			if (map.isEmpty()) {
				map.put(deviceLogs.getIMSI(), deviceLogs.getSN());
			} else {
				if (map.containsKey(deviceLogs.getIMSI())) {
					map.put(deviceLogs.getIMSI(), map.get(deviceLogs.getIMSI())
							+ "|" + deviceLogs.getSN());
				} else {
					map.put(deviceLogs.getIMSI(), deviceLogs.getSN());
				}
			}
		}

		String str = "";

		Object s[] = map.keySet().toArray();
		for (int i = 0; i < map.size(); i++) {

			if (map.get(s[i]).contains("|")) {
				if (StringUtils.isBlank(str)) {
					str += "疑似抢卡设备号/对应IMSI：" + map.get(s[i]) + "/"
							+ s[i].toString();
				} else {
					str += "," + map.get(s[i]) + "/" + s[i].toString();
				}

			}
		}

		JSONObject result = new JSONObject();

		result.put("data", str);

		try {
			response.getWriter().print(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//获取阿里云日志服务的数据
	private String getLogService(String logstore,SearchDTO searchDTO,String sn,String time){
//		try {//先查找阿里云日志服务的数据
//			if (StringUtils.isBlank(time)) {
//				time = DateUtils.getDate("yyyy-MM-dd");
//			}
//			String heartUrl = getLogUrl +logstore+ "?page=" + searchDTO.getCurPage() + "&pageSize="+ searchDTO.getPageSize() + "&sbid=" + sn + "&sj=" + time;
//			String resutString = RemoteUtil.sendHttpRequest(heartUrl);
//			
//			if(resutString==null||"".equals(resutString.trim())){
//				return null;
//			}
//			JSONObject obj = JSONObject.fromObject(resutString);
//			JSONArray ja = obj.getJSONArray("data");
//			if (!ja.isEmpty()) {
//				if(logstore.equals("pt")){
//					resutString=resutString.replace("ContextLen", "contextLen");
//				}
//				return resutString;
//			} 
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		return null;
	}
}
