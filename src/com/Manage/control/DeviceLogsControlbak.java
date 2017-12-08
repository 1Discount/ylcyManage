package com.Manage.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.tools.jar.resources.jar;

import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.SMSUltis;
import com.Manage.common.util.SMSUltisNews;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.common.util.ByteUtils.StringValueConversionException;
import com.Manage.dao.DictionaryDao;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.DeviceLogsTest;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.MSMRecord;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.SIMServer;
import com.Manage.entity.VIPCardInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.alibaba.druid.sql.visitor.functions.Left;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.Manage.service.DeviceLogsTempSer;
import com.Manage.service.DictionarySer;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/devicelogsbak")
public class DeviceLogsControlbak extends BaseController {

	@RequestMapping("/todaylistlogs")
	// 当天在线日志信息
	public String today(Model model) {
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/devicelogs_history";
	}

	@RequestMapping("/newDatelogs")
	// 当前分钟在线
	public String history(Model model) {
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/devicelogs_newDate";
	}

	/*
	 * @RequestMapping("/listlogs") public void datapage(SearchDTO
	 * searchDTO,DeviceLogs devicelogs,HttpServletResponse response){
	 * response.setContentType("application/json;charset=UTF-8");
	 * 
	 * String time = devicelogs.getLastTime(); System.out.println(time);
	 * if(time==null){ SimpleDateFormat sdf2 = new
	 * SimpleDateFormat("yyyy-MM-dd"); devicelogs.setLastTime(sdf2.format(new
	 * Date())); }
	 * System.out.println("---"+searchDTO.getCurPage()+"------"+searchDTO
	 * .getPageSize()); SearchDTO seDto=new
	 * SearchDTO(searchDTO.getCurPage(),searchDTO
	 * .getPageSize(),searchDTO.getSortName
	 * (),searchDTO.getSortOrder(),devicelogs); String
	 * jsonString=deviceLogsSer.getpageString(seDto); try {
	 * response.getWriter().println(jsonString); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 * @RequestMapping("/nowlistlogs") public void datapage2(SearchDTO
	 * searchDTO,DeviceLogs devicelogs,HttpServletResponse response){
	 * response.setContentType("application/json;charset=UTF-8");
	 * 
	 * SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 * devicelogs.setLastTime(sdf2.format(new Date()));
	 * 
	 * System.out.println("---"+searchDTO.getCurPage()+"------"+searchDTO.
	 * getPageSize()); SearchDTO seDto=new
	 * SearchDTO(searchDTO.getCurPage(),searchDTO
	 * .getPageSize(),searchDTO.getSortName
	 * (),searchDTO.getSortOrder(),devicelogs); String
	 * jsonString=deviceLogsSer.getpageString(seDto); try {
	 * response.getWriter().println(jsonString); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */
	/**
	 * see also {@link #deveceonlinepost(Model, String)} 另一个可接受post方法
	 * 之前试图使用本方法传递 tableName 在首页折线图中跳转, 但跳转后不好处理 重新设定日期后 url 中所带 tableName 不一致.
	 * 为简单处理, 在首页转为 post 去跳转
	 * 
	 * @param model
	 * @param tableName
	 * @return
	 */
	@RequestMapping("/deveceonline")
	public String deveceonline(Model model, String tableName) {
		if (StringUtils.isNotBlank(tableName)) {
			model.addAttribute("tableName", tableName);
		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("countries", countries);
		return "WEB-INF/views/service/deviceonline";
	}

	/**
	 * see also {@link #deveceonline(Model, String)}
	 * 
	 * @param model
	 * @param tableName
	 * @return
	 */
	@RequestMapping(value = "/deveceonlinepost", method = RequestMethod.POST)
	public String deveceonlinepost(Model model, String tableName) {
		if (StringUtils.isNotBlank(tableName)) {
			model.addAttribute("tableName", tableName);
		}
		return "WEB-INF/views/service/deviceonline";
	}

	@RequestMapping("/getonline")
	public void getonline(SearchDTO searchDTO, String tableName,
			String countryName, HttpServletResponse response, Model model)
			throws IOException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf1 = new SimpleDateFormat("HH:mm:ss");
		if (tableName != null) {
			String tableName1 = tableName.replace("-", "");
			String date = sf.format(new Date()).replace("-", "");
			if (Integer.parseInt(tableName1) > Integer.parseInt(date)) {
				response.getWriter().println("不能大于当于日期");
			}
		}
		Map<String, String> map = new HashMap<String, String>();

		map.put("tableName", tableName);
		map.put("countryName", countryName);
		List<DeviceLogs> deviceLogs = deviceLogsSer.getcountryonline1(map);

		JSONObject object = new JSONObject();
		object.put("success", true);

		object.put("curPage", 1);
		JSONArray ja = new JSONArray();

		for (int i = 0; i < deviceLogs.size(); i++) {

			if (i == 0) {
				JSONObject object2 = new JSONObject();
				object2.put("MCC", deviceLogs.get(i).getMcc());
				deviceLogs.get(i).getSN().substring(11);
				object2.put("SN", deviceLogs.get(i).getSN().substring(11));
				object2.put("SNCount", 1);
				ja.add(object2);
				// 判断是否和上一个mcc一样
			} else if (deviceLogs.get(i).getMcc()
					.equals(deviceLogs.get(i - 1).getMcc())) {
				// 如果一样,将上一个对象时的SN在追加一人SN sn,sn,sn,sn
				ja.getJSONObject(ja.size() - 1).put(
						"SN",
						ja.getJSONObject(ja.size() - 1).getString("SN") + "/"
								+ deviceLogs.get(i).getSN().substring(11));
				// 将上一个对象sn的个数加1
				ja.getJSONObject(ja.size() - 1).put("SNCount",
						ja.getJSONObject(ja.size() - 1).getInt("SNCount") + 1);
				JSONObject jj = ja.getJSONObject(ja.size() - 1);
				ja.remove(ja.size() - 1);
				ja.add(jj);
			} else {
				// 如果不一样，创建 一个新的对象
				JSONObject object2 = new JSONObject();
				object2.put("MCC", deviceLogs.get(i).getMcc());
				object2.put("SN", deviceLogs.get(i).getSN().substring(11));
				object2.put("SNCount", 1);
				ja.add(object2);
			}
		}
		object.put("totalRows", ja.size());
		object.put("data", ja);
		try {

			response.getWriter().print(object.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@RequestMapping("/getDeviceInCount")
	public void getDeviceInCount(HttpServletRequest request,
			HttpServletResponse response, String status)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String dateBegin = request.getParameter("dateBegin");
		String dateEnd = request.getParameter("dateEnd");
		String zxdateBegin = request.getParameter("zxdateBegin");
		String zxdateEnd = request.getParameter("zxdateEnd");
		if ("zhexiantu".equals(status)) {
			dateBegin = "";
			dateEnd = "";
		} else if ("bingtu".equals(status)) {
			zxdateBegin = "";
			zxdateEnd = "";
		}

		// 若改用map去处理会不会算优化, 减轻登录时的内存占用?
		DeviceLogs queryInfo = new DeviceLogs();
		queryInfo.setBeginTime(zxdateBegin);
		queryInfo.setEndTime(zxdateEnd);
		List<DeviceLogs> deviceInCountyByDay = deviceLogsSer
				.getDeviceInCountByDay(queryInfo);
		JSONObject resultJsonObject = new JSONObject();
		JSONArray countByDay = new JSONArray();
		for (DeviceLogs deviceLogs : deviceInCountyByDay) {
			JSONObject item = new JSONObject();
			// item.put(deviceLogs.getLogsDate(), deviceLogs.getCountByDay());
			item.put("date", deviceLogs.getLogsDate());
			item.put("count", deviceLogs.getCountByDay());
			countByDay.add(item);
		}
		resultJsonObject.put("countByDay", countByDay);
		queryInfo.setBeginTime(zxdateBegin);
		queryInfo.setEndTime(zxdateEnd);
		List<DeviceLogs> deviceInCountyByDayAll = deviceLogsSer
				.getDeviceInCountByDayAll(queryInfo);
		JSONArray countByDayAll = new JSONArray();
		for (DeviceLogs deviceLogs : deviceInCountyByDayAll) {
			JSONObject item = new JSONObject();
			item.put("date", deviceLogs.getLogsDate());
			item.put("count", deviceLogs.getCountByDay());
			countByDayAll.add(item);
		}
		resultJsonObject.put("countByDayAll", countByDayAll);
		queryInfo.setBeginTime(dateBegin);
		queryInfo.setEndTime(dateEnd);
		List<DeviceLogs> deviceInCountyByMCC = deviceLogsSer
				.getDeviceInCountByMCC(queryInfo);
		JSONArray countByMCC = new JSONArray();
		for (DeviceLogs deviceLogs : deviceInCountyByMCC) {
			JSONObject item = new JSONObject();
			// item.put(deviceLogs.getCountryName(),
			// deviceLogs.getCountByMCC());
			item.put("name", deviceLogs.getCountryName());
			item.put("count", deviceLogs.getCountByMCC());
			countByMCC.add(item);
		}
		resultJsonObject.put("countByMCC", countByMCC);

		try {
			response.getWriter().print(resultJsonObject.toString());
			//System.out.println(resultJsonObject.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@RequestMapping("/getDeviceInCount1")
	public void getDeviceInCount1(HttpServletRequest request,
			HttpServletResponse response, String tableName, String countryName)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 若改用map去处理会不会算优化, 减轻登录时的内存占用?
		DeviceLogs queryInfo = new DeviceLogs();
		queryInfo.setTableName(tableName);
		List<DeviceLogs> deviceInCountyByDay = deviceLogsSer
				.getDeviceInCountByDay1(queryInfo);
		JSONObject resultJsonObject = new JSONObject();
		JSONArray countByDay = new JSONArray();
		for (DeviceLogs deviceLogs : deviceInCountyByDay) {
			JSONObject item = new JSONObject();
			// item.put(deviceLogs.getLogsDate(), deviceLogs.getCountByDay());
			item.put("date", deviceLogs.getLogsDate());
			item.put("count", deviceLogs.getCountByDay());
			countByDay.add(item);
		}
		resultJsonObject.put("countByDay", countByDay);

		List<DeviceLogs> deviceInCountyByDayAll = deviceLogsSer
				.getDeviceInCountByDayAll1(queryInfo);
		JSONArray countByDayAll = new JSONArray();
		for (DeviceLogs deviceLogs : deviceInCountyByDayAll) {
			JSONObject item = new JSONObject();
			item.put("date", deviceLogs.getLogsDate());
			item.put("count", deviceLogs.getCountByDay());
			countByDayAll.add(item);
		}
		resultJsonObject.put("countByDayAll", countByDayAll);

		List<DeviceLogs> deviceInCountyByMCC = deviceLogsSer
				.getDeviceInCountByMCC1(queryInfo);
		JSONArray countByMCC = new JSONArray();
		for (DeviceLogs deviceLogs : deviceInCountyByMCC) {
			JSONObject item = new JSONObject();
			// item.put(deviceLogs.getCountryName(),
			// deviceLogs.getCountByMCC());
			item.put("name", deviceLogs.getCountryName());
			item.put("count", deviceLogs.getCountByMCC());
			countByMCC.add(item);
		}
		resultJsonObject.put("countByMCC", countByMCC);

		try {
			response.getWriter().print(resultJsonObject.toString());
			System.out.println(resultJsonObject.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 设备按时间段查看每天接入情况和统计流量
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/flowBySnAndDate")
	public String flowBySnAndDate(DeviceLogs info, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		if (info != null) {
			model.addAttribute("Model", info);
		}

		return "WEB-INF/views/service/devicelogs_flowBySnDate";
	}

	@RequestMapping("/flowBySnAndDateTwo")
	public String flowBySnAndDateTwo(DeviceFlow info,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		response.setContentType("text/html;charset=utf-8");
		if (info != null) {
			model.addAttribute("Model", info);
		}

		return "WEB-INF/views/service/devicelogs_flowBySnDateTwo";
	}
	
	@RequestMapping("/getFlowCount")
	public void getFlowCount(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String ddtime=request.getParameter("date");
		String mcc=request.getParameter("mcc");
		String sn=request.getParameter("sn");
		String sc=request.getParameter("sc");
		long flowCount=deviceLogsSer.getFlowCount(ddtime,mcc,sn,sc);
		try {
			response.getWriter().println(flowCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	@RequestMapping("/flowBySnAndDateExportexecl")
	public void flowBySnAndDateExportexecl(String sn, String begindate,
			String enddate, String all, String sta, String flowDistinction,
			String end, String cur, String pagesize, String total,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		if (StringUtils.isBlank(sn) || StringUtils.isBlank(begindate)
				|| StringUtils.isBlank(enddate)) {
			try {
				response.getWriter().println("请提供SN, 开始时间和结束时间");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("流量订单表一");
		sheet.setDefaultRowHeightInPoints(5000);

		sheet.setColumnWidth((short) 0, (short) 4500); // 9000
		sheet.setColumnWidth((short) 1, (short) 4500);
		sheet.setColumnWidth((short) 2, (short) 4500);
		sheet.setColumnWidth((short) 3, (short) 4500); // 5500
		sheet.setColumnWidth((short) 4, (short) 4500);
		sheet.setColumnWidth((short) 5, (short) 1000);

		sheet.setColumnWidth((short) 6, (short) 4500);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		// 第一行列明 SN 开始时间 结束时间
		HSSFRow row = sheet.createRow((int) 0);
		// short c = 500;
		// row.setHeight(c);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("设备序列号SN：");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue(sn);
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("时间段："); // 开始时间：
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue(begindate);
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("到");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue(enddate);
		cell.setCellStyle(style);

		// 第二行系表头
		row = sheet.createRow((int) 1);
		cell = row.createCell((short) 0);
		cell.setCellValue("日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("使用国家");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("是否接入过");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("是否接入成功");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("当日使用流量");
		cell.setCellStyle(style);

		// 转换mcc
		List<CountryInfo> countries = countryInfoSer.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries) {
			mccNameMap.put(String.valueOf(item.getCountryCode()),
					item.getCountryName());
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到

		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 创建一个居中格式

		DeviceLogs info = new DeviceLogs();
		info.setSN(sn);
		info.setBeginTime(begindate);
		info.setEndTime(enddate);
		info.setFlowDistinction(Double.parseDouble(flowDistinction) * 1024 + "");
		List<DeviceLogs> resultDeviceLogs = deviceLogsSer
				.getDeviceInBySnAndDate(info);
		for (int i = 0; i < resultDeviceLogs.size(); i++) {
			// 导出国家
			String mcc = resultDeviceLogs.get(i).getMcc();
			if (StringUtils.isNotBlank(mcc)) {
				mcc = mccNameMap.get(mcc);
			}

			row = sheet.createRow((int) i + 2); // 1 // marks:! 这是标题行的行数, 当前有两行

			DeviceLogs itemDeviceLogs = resultDeviceLogs.get(i);

			// 第四步，创建单元格，并设置值
			row.createCell((short) 0)
					.setCellValue(itemDeviceLogs.getLogsDate());

			row.createCell((short) 1).setCellValue(mcc);

			if (itemDeviceLogs.isIfIn()) {
				row.createCell((short) 2).setCellValue("是");
			} else {
				row.createCell((short) 2).setCellValue("否");
			}

			if (itemDeviceLogs.isIfInAndHasFlow()) {
				row.createCell((short) 3).setCellValue("是");
			} else {
				row.createCell((short) 3).setCellValue("否");
			}

			cell = row.createCell((short) 4);

			try {
				cell.setCellValue(Bytes.valueOf(
						itemDeviceLogs.getFlowDistinction() + "K").toString());
			} catch (StringValueConversionException e) {
				e.printStackTrace();
				cell.setCellValue(itemDeviceLogs.getFlowDistinction()); // 转换失败显示原值
			}
			cell.setCellStyle(style2);
		}

		DownLoadUtil.execlExpoprtDownload("flow-" + sn + "-" + begindate + "-"
				+ enddate + ".xls", wb, request, response);
	}

	/**
	 * 短信用户提醒
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/SMSsend")
	public String SMSsend(Model model) {
		String description = "短信提醒";
		List<Dictionary> alertType = dictionarySer.getalertType(description);
		model.addAttribute("alertType", alertType);
		return "WEB-INF/views/service/SMSsend";

	}

	/**
	 * 发短信调用
	 * 
	 * @param phone电话
	 * @param SMStext
	 *            设备号
	 * @param conectionCount
	 *            设备连接数
	 * @param dl
	 *            电量
	 * @param type
	 *            模板类型
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/startsendmsm")
	public void startsendmsm(String conectionCount, String templateId,
			String phone, String SMStext, String dl, String type,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException{
		SMSUltisNews smsNews = new SMSUltisNews();
		boolean success = smsNews.sendTemplateSMSCloopen(phone, templateId,
				conectionCount, dl, SMStext, "", "");// 云之讯
		//phone=18682200743&SMStext=2222222&dl=20&type=batterylow&templateId=25021&batterylow=20
		MSMRecord msg=new MSMRecord();
		msg.setAdminid(((AdminUserInfo)getSession().getAttribute("User")).getUserID());
		msg.setPhone(phone);
		msg.setSn(SMStext);
		msg.setTemplateId(templateId);
		msg.setStatus(success?1:0);
		deviceLogsSer.saveSendMsgRecord(msg);
		response.getWriter().print(success?"1":"0");
	}

	@RequestMapping("/exportexeclone")
	public void execlone(String beginTime, String endTime,
			HttpServletResponse response, String cName,
			HttpServletRequest request) throws UnsupportedEncodingException,
			ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		request.setCharacterEncoding("utf-8");
		if (StringUtils.isNotBlank(cName)) {
			cName = new String(cName.getBytes("ISO-8859-1"), "utf-8");
		}
		List<Date> dates = DateUtils.getDates(beginTime, endTime);
		dates.add(dateFormat.parse(beginTime));
		dates.add(dateFormat.parse(endTime));

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// sheet.setDefaultRowHeight((short)100);
		sheet.setDefaultColumnWidth((short) 20);
		sheet.setColumnWidth((short) 3, (short) 60000);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 600);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		// 设置字体
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 11);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		// 设轩样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style.setFont(headfont);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("国家");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("设备接入数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("SN");
		cell.setCellStyle(style);
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFCellStyle style2 = wb.createCellStyle();
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		CountryInfo info = new CountryInfo();
		info.setCountryName(cName);
		int i = 0;
		for (Date date2 : dates) {
			// 第四步，创建单元格，并设置值
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 450);

			HSSFCell cell1 = row.createCell((short) 0);

			cell1.setCellStyle(style1);
			info.setTableName(dateFormat.format(date2));
			List<DeviceLogs> deviceLogs = deviceLogsSer.getcountryonline(info);

			if (deviceLogs.size() == 0) {
				cell1.setCellValue(dateFormat.format(date2));
				cell1.setCellStyle(style1);

				cell1 = row.createCell((short) 1);
				cell1.setCellValue(cName);
				cell1.setCellStyle(style1);

				cell1 = row.createCell((short) 2);
				cell1.setCellValue("\'0");
				cell1.setCellStyle(style1);

				cell1 = row.createCell((short) 2);
				cell1.setCellValue("");
				cell1.setCellStyle(style2);

			} else {
				String SN = "";
				int sNCount = 0;
				for (DeviceLogs deviceLogs2 : deviceLogs) {
					SN = SN
							+ deviceLogs2.getSN().substring(11,
									deviceLogs2.getSN().length()) + "，";
					sNCount++;
				}
				SN = SN.substring(0, SN.length() - 1);

				cell1.setCellValue(dateFormat.format(date2));
				cell1.setCellStyle(style1);

				cell1 = row.createCell((short) 1);
				cell1.setCellValue(deviceLogs.get(0).getMcc());
				cell1.setCellStyle(style1);

				cell1 = row.createCell((short) 2);
				cell1.setCellValue(sNCount);
				cell1.setCellStyle(style1);

				cell1 = row.createCell((short) 3);
				cell1.setCellValue(SN);
				cell1.setCellStyle(style2);

			}
			i++;

		}
		DownLoadUtil.execlExpoprtDownload(beginTime + "-" + endTime
				+ "设备接入统计.xls", wb, request, response);
	}

	@RequestMapping("/exportexecl")
	public void execl(String beginTime, String endTime,
			HttpServletResponse response, String cName,
			HttpServletRequest request) throws UnsupportedEncodingException,
			ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		request.setCharacterEncoding("utf-8");
		if (StringUtils.isNotBlank(cName)) {
			cName = new String(cName.getBytes("ISO-8859-1"), "utf-8");
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		List<Date> dates = DateUtils.getDates(beginTime, endTime);
		dates.add(dateFormat.parse(beginTime));
		dates.add(dateFormat.parse(endTime));
		for (Date date2 : dates) {
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet(dateFormat.format(date2));
			sheet.setColumnWidth((short) 0, (short) 2500);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			row.setHeight((short) 500);
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style1 = wb.createCellStyle();
			style1.setFont(font);
			style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCellStyle style = wb.createCellStyle();
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("国家");
			cell.setCellStyle(style1);
			cell = row.createCell((short) 1);
			cell.setCellValue("设备接入数");
			cell.setCellStyle(style1);
			cell = row.createCell((short) 2);
			cell.setCellValue("SN");
			cell.setCellStyle(style1);
			// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
			List<FlowDealOrders> flowDealOrders = null;
			FlowDealOrders flowDealOrders3 = new FlowDealOrders();
			SearchDTO searchDTO = new SearchDTO();
			String tableName = dateFormat.format(date2);
			CountryInfo info = new CountryInfo();
			info.setCountryName(cName);
			info.setTableName(tableName);

			List<DeviceLogs> deviceLogs = deviceLogsSer.getcountryonline(info);
			List<DeviceLogs> deviceLogs3 = new ArrayList<DeviceLogs>();
			int jrs = 0;
			String SN = "";
			for (int i = 0; i < deviceLogs.size(); i++) {
				DeviceLogs deviceLogs2 = deviceLogs.get(i);
				DeviceLogs d = new DeviceLogs();
				;
				String countryName = "";
				if (i == 0) {
					countryName = deviceLogs2.getMcc();
					SN = deviceLogs.get(i).getSN()
							.substring(11, deviceLogs.get(i).getSN().length())
							+ "/";
					jrs++;
					// 判断是否和上一个mcc一样
				} else if (deviceLogs.get(i).getMcc()
						.equals(deviceLogs.get(i - 1).getMcc())) {
					countryName = deviceLogs2.getMcc();
					jrs++;
					SN = SN
							+ (deviceLogs.get(i).getSN().substring(11,
									deviceLogs.get(i).getSN().length())) + "/";
				} else {
					if (SN.length() >= 15) {
						SN = SN.substring(11, SN.length() - 1);
					}
					countryName = deviceLogs.get(i - 1).getMcc();
					d.setCountryName(countryName);
					d.setFirmWareVer(jrs);
					d.setSN(SN);
					deviceLogs3.add(d);
					SN = SN
							+ (deviceLogs.get(i).getSN().substring(11,
									deviceLogs.get(i).getSN().length()));
					countryName = deviceLogs2.getMcc();
					jrs = 1;
					if (i != deviceLogs.size() - 1) {
						SN = "";
					} else {
						SN = deviceLogs
								.get(i)
								.getSN()
								.substring(11,
										deviceLogs.get(i).getSN().length())
								+ "/";
					}
					SN = deviceLogs.get(i).getSN() + "/";
					d = new DeviceLogs();
					d.setCountryName(countryName);
					d.setFirmWareVer(jrs);
					d.setSN(SN);
				}
				if (i == deviceLogs.size() - 1) {
					if (SN.length() >= 15) {
						d.setSN(SN.substring(11, SN.length() - 1));
					} else {
						d.setSN(SN.substring(0, SN.length() - 1));
					}
					d.setCountryName(countryName);
					d.setFirmWareVer(jrs);
					deviceLogs3.add(d);
				}
			}

			for (int i = 0; i < deviceLogs3.size(); i++) {

				row = sheet.createRow((int) i + 1);
				row.setHeight((short) 500);
				DeviceLogs dd = deviceLogs3.get(i);
				// 第四步，创建单元格，并设置值
				HSSFCell cell1 = row.createCell((short) 0);
				cell1.setCellValue(dd.getCountryName());
				cell1.setCellStyle(style);

				HSSFCell cell2 = row.createCell((short) 1);
				cell2.setCellValue(dd.getFirmWareVer());
				cell2.setCellStyle(style);

				HSSFCell cell3 = row.createCell((short) 2);
				cell3.setCellValue(dd.getSN());
				cell3.setCellStyle(style);
				HSSFCellStyle style3 = wb.createCellStyle();
				style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cell3.setCellStyle(style3);
				sheet.autoSizeColumn((short) 2); // 调整第四列宽度

			}

		}
		DownLoadUtil.execlExpoprtDownload(beginTime + "-" + endTime
				+ "设备接入统计.xls", wb, request, response);
	}

	/**
	 * @deprecated折首页图表数据导出
	 * @author jiang
	 * @date 2015-11-13
	 */
	@RequestMapping("/tubiaoexecleexp")
	public void zhexianExeclexp(HttpServletRequest request, String begindate,
			String enddate,// export
			HttpServletResponse response) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setColumnWidth((short) 0, (short) 4000);

		sheet.setColumnWidth((short) 1, (short) 8500);
		sheet.setColumnWidth((short) 2, (short) 8000);
		// sheet.setDefaultRowHeightInPoints(5000);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 600);
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

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("接入成功并且有流量上传数量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("接入成功数量");
		cell.setCellStyle(style);

		DeviceLogs queryInfo = new DeviceLogs();
		queryInfo.setBeginTime(begindate);
		queryInfo.setEndTime(enddate);
		List<DeviceLogs> deviceInCountyByDay = deviceLogsSer
				.getDeviceInCountByDay(queryInfo);
		List<DeviceLogs> deviceInCountyByAll = deviceLogsSer
				.getDeviceInCountByDayAll(queryInfo);
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		for (int i = 0; i < deviceInCountyByDay.size(); i++) {
			// 第四步，创建单元格，并设置值
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 450);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(deviceInCountyByDay.get(i).getLogsDate());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(deviceInCountyByDay.get(i).getCountByDay());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(deviceInCountyByAll.get(i).getCountByDay());
			cell1.setCellStyle(style1);

		}
		try {
			DownLoadUtil.execlExpoprtDownload(begindate + "到" + enddate
					+ "设备接入数量.xls", wb, request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/bingtuexeclexport")
	public void bingtuexeclexport(HttpServletRequest request, String begindate,
			String enddate, String status,// export
			HttpServletResponse response) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// sheet.setDefaultRowHeight((short)100);
		sheet.setDefaultColumnWidth((short) 18);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 600);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		// 设置字体
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 11);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		// 设轩样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style.setFont(headfont);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("国家");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("接入数量");
		cell.setCellStyle(style);
		DeviceLogs queryInfo = new DeviceLogs();
		queryInfo.setBeginTime(begindate);
		queryInfo.setEndTime(enddate);
		List<DeviceLogs> deviceInCountyByDay = deviceLogsSer
				.getDeviceInCountByMCC(queryInfo);
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		for (int i = 0; i < deviceInCountyByDay.size(); i++) {
			// 第四步，创建单元格，并设置值
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 450);
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(deviceInCountyByDay.get(i).getCountryName());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(deviceInCountyByDay.get(i).getCountByMCC());
			cell1.setCellStyle(style1);

		}
		try {
			DownLoadUtil.execlExpoprtDownload(begindate + "到" + enddate
					+ "各个国家接入成功设备统计.xls", wb, request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getflowlogpage")
	public String getflowlogpage(DeviceLogs info, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		if (info != null) {
			model.addAttribute("Model", info);
		}
		return "WEB-INF/views/service/devicelogs_flowBySnDatePhone";
	}

	@RequestMapping("flowbydevicelogsTest")
	public String flowbythree(SearchDTO searchDTO, DeviceLogs info,
			HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("application/json;charset=UTF-8");
		String beginyear = info.getBeginTime().substring(0, 4);
		String beginmonth = info.getBeginTime().substring(5, 7);
		String endyear = info.getEndTime().substring(0, 4);
		String endmonth = info.getEndTime().substring(5, 7);

		System.out.println("开始年：" + beginyear + "结束年：" + endyear);

		List<DeviceLogsTest> resultDeviceLogsall = new ArrayList<DeviceLogsTest>();
		String end = "";

		if (Integer.parseInt(endyear) - Integer.parseInt(beginyear) >= 2) {
			System.out.println("一年以外！");
			return "WEB-INF/views/service/devicelogs_flowBySnDatePhone";
		} else {
			System.out.println("一年以内！");
			if (beginyear.equals(endyear)) {// 年相同 判断月份

				// 判断月份是否 相等
				if (beginmonth.equals(endmonth)) {
					System.out.println("同年同月！");
					// 遍历一次 得出结果

					// if(beginmonth.equals(endmonth)){
					try {
						DeviceLogsTest logs = new DeviceLogsTest();// new add
																	// bean
						info.setLogsDate(info.getBeginTime());
						logs = deviceLogsSer.getflowBySnAndDateAPI(info);
						logs.setLogsDate(info.getBeginTime().replace("-", "年")
								+ "月");// 时间
						logs.setSN(info.getSN());// SN
						if (logs.getMonthUsedFlow() == null) {
							logs.setIfLogin("否");
							logs.setIfLoginSuccess("否");
							logs.setMonthUsedFlow("0");
							resultDeviceLogsall.add(logs);
						} else {
							logs.setIfLogin("是");
							logs.setIfLoginSuccess("是");
							float a = Float.valueOf(logs.getMonthUsedFlow()) / 1024;
							float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
							logs.setMonthUsedFlow(flow + "MB");
							resultDeviceLogsall.add(logs);
						}
					} catch (Exception e) {
						end = "{code:" + "'01'" + ",msg:" + "'数据操作错误，请稍后重试！"
								+ ",Data:" + null + "}";
						e.printStackTrace();
					}
					System.out.println("同年同月,，，，遍历结束！");
				} else {
					// 结束月 减 开始月
					System.out.println("同年不同月！");

					int month = Integer.parseInt(endmonth)
							- Integer.parseInt(beginmonth);

					int years = Integer.parseInt(info.getBeginTime().substring(
							0, 4));
					System.out.println("年" + years);
					DeviceLogsTest resultDeviceLogs = new DeviceLogsTest();

					for (int i = 0; i <= month; i++) {

						List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
						String yue = (Integer.parseInt(beginmonth) + i) + "";
						if (yue.length() == 1)
							yue = "0" + yue;
						String endpar = years + "-" + yue;
						System.out.println(endpar);
						info.setLogsDate(endpar);
						try {
							resultDeviceLogs = deviceLogsSer
									.getflowBySnAndDateAPI(info);
							DeviceLogsTest logs = new DeviceLogsTest();// new
																		// add
																		// bean
							logs.setLogsDate(endpar.replace("-", "年") + "月");// 时间
							logs.setSN(info.getSN());// SN
							if (resultDeviceLogs == null) {
								logs.setIfLogin("否");
								logs.setIfLoginSuccess("否");
								logs.setMonthUsedFlow("0");
								resultDeviceLogsone.add(logs);
							} else {
								logs.setIfLogin("是");
								logs.setIfLoginSuccess("是");
								float a = Float.valueOf(resultDeviceLogs
										.getMonthUsedFlow()) / 1024;
								float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
								logs.setMonthUsedFlow(flow + "MB");
								resultDeviceLogsone.add(logs);
							}
							resultDeviceLogsall.addAll(resultDeviceLogsone);
						} catch (Exception e) {
							end = "{code:" + "'01'" + ",msg:"
									+ "'数据操作错误，请稍后重试！" + ",Data:" + null + "}";
							e.printStackTrace();
						}
					}
				}
				end = "";
			} else {// 年份不同

				// 一次只能查询只能是两年内的数据

				System.out.println("不同年！");
				// 开始年月 到 本年结束 遍历完成 再遍历 结束年一月份到结束年月
				String beginyearmonth = "";
				DeviceLogsTest resultDeviceLogs = new DeviceLogsTest();
				for (int i = Integer.parseInt(beginmonth); i <= 12; i++) {
					if (i < 10)
						beginmonth = "0" + i;
					else
						beginmonth = i + "";
					beginyearmonth = beginyear + "-" + beginmonth;
					System.out.println(beginyearmonth);
					info.setLogsDate(beginyearmonth);
					try {

						List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
						resultDeviceLogs = deviceLogsSer
								.getflowBySnAndDateAPI(info);
						DeviceLogsTest logs = new DeviceLogsTest();// new add
																	// bean
						logs.setLogsDate(beginyearmonth.replace("-", "年") + "月");// 时间
						logs.setSN(info.getSN());// SN
						if (resultDeviceLogs == null) {
							logs.setIfLogin("否");
							logs.setIfLoginSuccess("否");
							logs.setMonthUsedFlow("0");
							resultDeviceLogsone.add(logs);
						} else {
							logs.setIfLogin("是");
							logs.setIfLoginSuccess("是");
							float a = Float.valueOf(resultDeviceLogs
									.getMonthUsedFlow()) / 1024;
							float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
							logs.setMonthUsedFlow(flow + "MB");
							resultDeviceLogsone.add(logs);
						}
						resultDeviceLogsall.addAll(resultDeviceLogsone);
					} catch (Exception e) {
						end = "{code:" + "'01'" + ",msg:" + "'数据操作错误，请稍后重试！"
								+ ",Data:" + null + "}";
						e.printStackTrace();
					}
				}
				System.out.println("end开始！");
				String endmonths = "";
				String endyearmonth = "";
				for (int j = 1; j <= Integer.parseInt(endmonth); j++) {
					if (j < 10)
						endmonths = "0" + j;
					else
						endmonths = j + "";
					endyearmonth = endyear + "-" + endmonths;
					System.out.println(endyearmonth);
					info.setLogsDate(endyearmonth);
					try {

						List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
						resultDeviceLogs = deviceLogsSer
								.getflowBySnAndDateAPI(info);
						DeviceLogsTest logs = new DeviceLogsTest();// new add
																	// bean
						logs.setLogsDate(endyearmonth.replace("-", "年") + "月");// 时间
						logs.setSN(info.getSN());// SN
						if (resultDeviceLogs == null) {
							logs.setIfLogin("否");
							logs.setIfLoginSuccess("否");
							logs.setMonthUsedFlow("0");
							resultDeviceLogsone.add(logs);
						} else {
							logs.setIfLogin("是");
							logs.setIfLoginSuccess("是");
							float a = Float.valueOf(resultDeviceLogs
									.getMonthUsedFlow()) / 1024;
							float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
							logs.setMonthUsedFlow(flow + "MB");
							resultDeviceLogsone.add(logs);
						}
						resultDeviceLogsall.addAll(resultDeviceLogsone);
					} catch (Exception e) {
						end = "{code:" + "'01'" + ",msg:" + "'数据操作错误，请稍后重试！"
								+ ",Data:" + null + "}";
						e.printStackTrace();
					}
				}
			}
			request.setAttribute("resultDeviceLogsall", resultDeviceLogsall);
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
					.fromObject(resultDeviceLogsall);
			System.out.println(jsonArray.toString());
			// end =
			// "{code:"+"'00'"+",msg:"+"'成功！'"+",Data:"+jsonArray.toString()+"}";

			// return end;
			return "WEB-INF/views/service/devicelogs_flowBySnDatePhone";
		}
		// 方法结束

	}

	@RequestMapping("getFlowLogDataEndV5")
	public String getFlowLogDataEndV5(DeviceLogs info,
			HttpServletRequest request, HttpServletResponse response) {

		String beginyear = info.getBeginTime().substring(0, 4);
		String beginmonth = info.getBeginTime().substring(5, 7);
		String endyear = info.getEndTime().substring(0, 4);
		String endmonth = info.getEndTime().substring(5, 7);

		System.out.println("开始年：" + beginyear + "结束年：" + endyear);

		List<DeviceLogsTest> resultDeviceLogsall = new ArrayList<DeviceLogsTest>();
		String end = "";

		if (Integer.parseInt(endyear) - Integer.parseInt(beginyear) >= 2) {
			System.out.println("一年以外！");
			return "WEB-INF/views/service/devicelogs_flowBySnDatePhone";
		} else {
			System.out.println("一年以内！");
			if (beginyear.equals(endyear)) {// 年相同 判断月份

				// 判断月份是否 相等
				if (beginmonth.equals(endmonth)) {
					System.out.println("同年同月！");
					// 遍历一次 得出结果

					// if(beginmonth.equals(endmonth)){
					try {
						DeviceLogsTest logs = new DeviceLogsTest();// new add
																	// bean
						info.setLogsDate(info.getBeginTime());
						logs = deviceLogsSer.getflowBySnAndDateAPI(info);
						logs.setLogsDate(info.getBeginTime().replace("-", "年")
								+ "月");// 时间
						logs.setSN(info.getSN());// SN
						if (logs.getMonthUsedFlow() == null) {
							logs.setIfLogin("否");
							logs.setIfLoginSuccess("否");
							logs.setMonthUsedFlow("0");
							resultDeviceLogsall.add(logs);
						} else {
							logs.setIfLogin("是");
							logs.setIfLoginSuccess("是");
							float a = Float.valueOf(logs.getMonthUsedFlow()) / 1024;
							float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
							logs.setMonthUsedFlow(flow + "MB");
							resultDeviceLogsall.add(logs);
						}
					} catch (Exception e) {
						end = "{code:" + "'01'" + ",msg:" + "'数据操作错误，请稍后重试！"
								+ ",Data:" + null + "}";
						e.printStackTrace();
					}
					System.out.println("同年同月,，，，遍历结束！");
				} else {
					// 结束月 减 开始月
					System.out.println("同年不同月！");

					int month = Integer.parseInt(endmonth)
							- Integer.parseInt(beginmonth);

					int years = Integer.parseInt(info.getBeginTime().substring(
							0, 4));
					System.out.println("年" + years);
					DeviceLogsTest resultDeviceLogs = new DeviceLogsTest();

					for (int i = 0; i <= month; i++) {

						List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
						String yue = (Integer.parseInt(beginmonth) + i) + "";
						if (yue.length() == 1)
							yue = "0" + yue;
						String endpar = years + "-" + yue;
						System.out.println(endpar);
						info.setLogsDate(endpar);
						try {
							resultDeviceLogs = deviceLogsSer
									.getflowBySnAndDateAPI(info);
							DeviceLogsTest logs = new DeviceLogsTest();// new
																		// add
																		// bean
							logs.setLogsDate(endpar.replace("-", "年") + "月");// 时间
							logs.setSN(info.getSN());// SN
							if (resultDeviceLogs == null) {
								logs.setIfLogin("否");
								logs.setIfLoginSuccess("否");
								logs.setMonthUsedFlow("0");
								resultDeviceLogsone.add(logs);
							} else {
								logs.setIfLogin("是");
								logs.setIfLoginSuccess("是");
								float a = Float.valueOf(resultDeviceLogs
										.getMonthUsedFlow()) / 1024;
								float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
								logs.setMonthUsedFlow(flow + "MB");
								resultDeviceLogsone.add(logs);
							}
							resultDeviceLogsall.addAll(resultDeviceLogsone);
						} catch (Exception e) {
							end = "{code:" + "'01'" + ",msg:"
									+ "'数据操作错误，请稍后重试！" + ",Data:" + null + "}";
							e.printStackTrace();
						}
					}
				}
				end = "";
			} else {// 年份不同

				// 一次只能查询只能是两年内的数据

				System.out.println("不同年！");
				// 开始年月 到 本年结束 遍历完成 再遍历 结束年一月份到结束年月
				String beginyearmonth = "";
				DeviceLogsTest resultDeviceLogs = new DeviceLogsTest();
				for (int i = Integer.parseInt(beginmonth); i <= 12; i++) {
					if (i < 10)
						beginmonth = "0" + i;
					else
						beginmonth = i + "";
					beginyearmonth = beginyear + "-" + beginmonth;
					System.out.println(beginyearmonth);
					info.setLogsDate(beginyearmonth);
					try {

						List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
						resultDeviceLogs = deviceLogsSer
								.getflowBySnAndDateAPI(info);
						DeviceLogsTest logs = new DeviceLogsTest();// new add
																	// bean
						logs.setLogsDate(beginyearmonth.replace("-", "年") + "月");// 时间
						logs.setSN(info.getSN());// SN
						if (resultDeviceLogs == null) {
							logs.setIfLogin("否");
							logs.setIfLoginSuccess("否");
							logs.setMonthUsedFlow("0");
							resultDeviceLogsone.add(logs);
						} else {
							logs.setIfLogin("是");
							logs.setIfLoginSuccess("是");
							float a = Float.valueOf(resultDeviceLogs
									.getMonthUsedFlow()) / 1024;
							float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
							logs.setMonthUsedFlow(flow + "MB");
							resultDeviceLogsone.add(logs);
						}
						resultDeviceLogsall.addAll(resultDeviceLogsone);
					} catch (Exception e) {
						end = "{code:" + "'01'" + ",msg:" + "'数据操作错误，请稍后重试！"
								+ ",Data:" + null + "}";
						e.printStackTrace();
					}
				}
				System.out.println("end开始！");
				String endmonths = "";
				String endyearmonth = "";
				for (int j = 1; j <= Integer.parseInt(endmonth); j++) {
					if (j < 10)
						endmonths = "0" + j;
					else
						endmonths = j + "";
					endyearmonth = endyear + "-" + endmonths;
					System.out.println(endyearmonth);
					info.setLogsDate(endyearmonth);
					try {

						List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
						resultDeviceLogs = deviceLogsSer
								.getflowBySnAndDateAPI(info);
						DeviceLogsTest logs = new DeviceLogsTest();// new add
																	// bean
						logs.setLogsDate(endyearmonth.replace("-", "年") + "月");// 时间
						logs.setSN(info.getSN());// SN
						if (resultDeviceLogs == null) {
							logs.setIfLogin("否");
							logs.setIfLoginSuccess("否");
							logs.setMonthUsedFlow("0");
							resultDeviceLogsone.add(logs);
						} else {
							logs.setIfLogin("是");
							logs.setIfLoginSuccess("是");
							float a = Float.valueOf(resultDeviceLogs
									.getMonthUsedFlow()) / 1024;
							float flow = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
							logs.setMonthUsedFlow(flow + "MB");
							resultDeviceLogsone.add(logs);
						}
						resultDeviceLogsall.addAll(resultDeviceLogsone);
					} catch (Exception e) {
						end = "{code:" + "'01'" + ",msg:" + "'数据操作错误，请稍后重试！"
								+ ",Data:" + null + "}";
						e.printStackTrace();
					}
				}
			}
			request.setAttribute("resultDeviceLogsall", resultDeviceLogsall);
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
					.fromObject(resultDeviceLogsall);
			System.out.println(jsonArray.toString());
			// end =
			// "{code:"+"'00'"+",msg:"+"'成功！'"+",Data:"+jsonArray.toString()+"}";

			// return end;
			return "WEB-INF/views/service/devicelogs_flowBySnDatePhone";
		}
		// 方法结束

	}

	/**
	 * ahming notes: 若需要像首页折线图那样可使用日期跳转, 可参考
	 * {@link #deveceonlinepost(Model, String)} 使用 post 去处理较好.
	 * 
	 * @param searchDTO
	 * @param info
	 * @param request
	 * @param response
	 */
	@RequestMapping("/flowBySnAndDateGetResult")
	public void flowBySnAndDateGetResult(SearchDTO searchDTO, DeviceLogs info,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");

		if (StringUtils.isBlank(info.getSN())
				|| StringUtils.isBlank(info.getBeginTime())
				|| StringUtils.isBlank(info.getEndTime())) {
			try {
				response.getWriter().println("");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String jsonString;
		info.setFlowDistinction(null);
		List<DeviceLogs> resultDeviceLogs = deviceLogsSer
				.getDeviceInBySnAndDate(info);
		// 转换mcc
		List<CountryInfo> countries = countryInfoSer.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries) {
			mccNameMap.put(String.valueOf(item.getCountryCode()),
					item.getCountryName());
		}

		if (resultDeviceLogs == null || resultDeviceLogs.size() == 0) {
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", 0);
			object.put("curPage", 1);
			JSONArray ja = new JSONArray();
			object.put("data", ja);
			jsonString = object.toString();
		} else {
			JSONObject object = new JSONObject();

			int totalTow = 0;
			long sumDayUsedFlow = 0;
			JSONArray ja = new JSONArray();
			for (DeviceLogs item : resultDeviceLogs) {
				String mcc = item.getMcc();
				if (StringUtils.isNotBlank(mcc)) {
					mcc = mccNameMap.get(mcc);
				}

				item.setMcc(mcc);

				Boolean ifInAndHasFlow = item.isIfInAndHasFlow();
				if (ifInAndHasFlow) {
					totalTow++;
					int flow = Integer.parseInt(item.getFlowDistinction());
					sumDayUsedFlow += flow;
				}

				JSONObject obj = JSONObject.fromObject(item);

				ja.add(obj);
			}
			object.put("logsDate", "合计:");
			object.put("flowDistinction", sumDayUsedFlow);
			ja.add(object);

			object.put("success", true);
			object.put("totalRows", resultDeviceLogs.size() + 1);
			object.put("curPage", 1);
			object.put("data", ja);
			jsonString = object.toString();
		}

		try {
			System.out.println(jsonString);
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("gitJieruInfoPage")
	public void gitJieruInfo(SearchDTO searchDTO, DeviceLogs deviceLogs,
			HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), deviceLogs);
		String pagesString = deviceLogsSer.gitJieruInfo(seDto);
		try {
			response.getWriter().println(pagesString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}