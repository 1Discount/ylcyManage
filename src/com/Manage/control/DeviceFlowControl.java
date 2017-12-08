package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.common.util.ByteUtils.StringValueConversionException;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.DeviceUpgrading;
import com.Manage.entity.Distributor;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/deviceflow")
public class DeviceFlowControl extends BaseController
{
	private Logger logger = LogUtil.getInstance(DeviceFlowControl.class);



	@RequestMapping("/toflowOrderUserinfo")
	public String toflowOrderUserinfo(Model model)
	{
		List<Distributor> distributors = distributorSer.getAll("");
		model.addAttribute("dis", distributors);
		return "WEB-INF/views/orders/flowOrder_Userinfo";
	}



	/***
	 * 新设备流量报表页面表格查询
	 * 
	 * @param searchDTO
	 * @param deviceFlow
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/flowBySnAndDateGetResult")
	public void flowBySnAndDateGetResult(SearchDTO searchDTO, DeviceFlow deviceFlow, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("application/json;charset=UTF-8");

		String jsonString;
		List<DeviceFlow> resultDeviceLogs = deviceFlowSer.getDeviceInBySnAndDate(deviceFlow);
		// 转换mcc
		List<CountryInfo> countries = countryInfoSer.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}

		if (resultDeviceLogs == null || resultDeviceLogs.size() == 0)
		{
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", 0);
			object.put("curPage", 1);
			JSONArray ja = new JSONArray();
			object.put("data", ja);
			jsonString = object.toString();
		}
		else
		{
			JSONObject object = new JSONObject();

			long sumDayUsedFlow = 0;
			JSONArray ja = new JSONArray();
			for (DeviceFlow item : resultDeviceLogs)
			{
				String mcc = item.getMCC();
				if (StringUtils.isNotBlank(mcc))
				{
					mcc = mccNameMap.get(mcc);
				}

				item.setCountryName(mcc);

				Long flow = 0L;
				DeviceLogs deviceLogs = new DeviceLogs();
				// 判断累计流量是否为空
				if ("0".equals(item.getFlowCount()))
				{
					String sn = item.getSN();
					deviceLogs.setSN(item.getSN());
					
					deviceLogs = deviceLogsSer.getlastOne(deviceLogs);
					
					item.setFlowCount(deviceLogs.getDayUsedFlow());

					flow = Long.parseLong(deviceLogs.getDayUsedFlow());//kb
					
					//判断周期结束刷新到数据库 刷新到数据库 
					
					String BJTime = item.getBJTime()+" 00:00:00";
					
					Long temp1 = Long.parseLong(DateUtils.dateToTimeStamp(BJTime, "yyyy-MM-dd HH:mm:ss"))/60/60;//hh
					
					Long temp2 = new Date().getTime()/1000/60/60;
					
					//判断是否已过周期时间
					if(temp2-temp1>25){
						
						DeviceFlow deviceFlow2 = new DeviceFlow();
						deviceFlow2.setID(item.getID());
						deviceFlow2.setFlowCount(flow+"");
						deviceFlowSer.updateInfo(deviceFlow2);
						
					}
				}
				else
				{
					flow = Long.parseLong(item.getFlowCount());

				}
 				sumDayUsedFlow += flow;

				JSONObject obj = JSONObject.fromObject(item);

				ja.add(obj);
			}
			object.put("DDTime", "合计:");
			object.put("countryName", "----");
			object.put("ifInAndHasFlow", "----");
			object.put("flowCount", sumDayUsedFlow);
			ja.add(object);

			object.put("success", true);
			object.put("totalRows", resultDeviceLogs.size() + 1);
			object.put("curPage", 1);
			object.put("data", ja);
			jsonString = object.toString();
		}

		try
		{
			System.out.println(jsonString);
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	/***
	 * 
	 * 新设备流量报表页面数据导出
	 * 
	 * @param sn
	 * @param begindate
	 * @param enddate
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/flowBySnAndDateExportexecl")
	public void flowBySnAndDateExportexecl(String SN, String beginTime, String endTime, String flowOrderID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

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
		sheet.setColumnWidth((short) 5, (short) 4500);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

		// 第一行列明 SN 开始时间 结束时间
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("设备序列号SN：");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue(SN);
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("时间段："); // 开始时间：
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue(beginTime);
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("到");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue(endTime);
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
		cell.setCellValue("是否接入成功");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("当日使用流量");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("备注");
		cell.setCellStyle(style);

		// 转换mcc
		List<CountryInfo> countries = countryInfoSer.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到

		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 创建一个居中格式

		DeviceFlow deviceFlow = new DeviceFlow();
		deviceFlow.setSN(SN);
		deviceFlow.setBeginTime(beginTime);
		deviceFlow.setEndTime(endTime);
		deviceFlow.setFlowOrderID(flowOrderID);

		List<DeviceFlow> resultDeviceLogs = deviceFlowSer.getDeviceInBySnAndDate(deviceFlow);
		for (int i = 0; i < resultDeviceLogs.size(); i++)
		{
			// 导出国家
			String mcc = resultDeviceLogs.get(i).getMCC();
			if (StringUtils.isNotBlank(mcc))
			{
				mcc = mccNameMap.get(mcc);
			}

			row = sheet.createRow((int) i + 2);

			DeviceFlow itemDeviceLogs = resultDeviceLogs.get(i);

			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(itemDeviceLogs.getDDTime());
			row.createCell((short) 1).setCellValue(mcc);
			row.createCell((short) 2).setCellValue("是");
			cell = row.createCell((short) 3);
			DeviceLogs deviceLogs  = new DeviceLogs();
			try
			{
				if (!"0".equals(itemDeviceLogs.getFlowCount()))
				{
					cell.setCellValue(Bytes.valueOf(itemDeviceLogs.getFlowCount() + "K").toString());
				}
				else
				{
					deviceLogs.setSN(itemDeviceLogs.getSN());
					
					deviceLogs = deviceLogsSer.getlastOne(deviceLogs);
					
					if(StringUtils.isBlank(deviceLogs.getDayUsedFlow())){
						cell.setCellValue(0);
					}
					else{
						cell.setCellValue(Bytes.valueOf(deviceLogs.getDayUsedFlow()).toString());
					}
				}
			}
			catch (StringValueConversionException e)
			{
				e.printStackTrace();
				cell.setCellValue(deviceLogs.getDayUsedFlow()); // 转换失败显示原值
			}
			row.createCell((short) 4).setCellValue(itemDeviceLogs.getDescr());
			cell.setCellStyle(style2);
		}
		DownLoadUtil.execlExpoprtDownload("flow-" + SN + "-" + beginTime + "-" + endTime + ".xls", wb, request, response);
	}
	
	@RequestMapping("/deviceflowstatistics")
	public String deviceflowstatistics(HttpServletRequest req, HttpServletResponse response, DeviceFlow deviceFlow, Model model)
	{
		response.setContentType("text/html;charset=utf-8");
		model.addAttribute("beginTime", deviceFlow.getBeginTime());
		model.addAttribute("endTime", deviceFlow.getEndTime());
		model.addAttribute("MCC", deviceFlow.getMCC());
		return "WEB-INF/views/deviceinfo/deviceflow_statistics";
	}
	
	/**
	 * 设备管理 >>查看全部设备信息
	 *
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/deviceflowstatisticspage")
	public void deviceflowstatisticspage(SearchDTO searchDTO, DeviceFlow deviceFlow, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		response.setContentType("text/html;charset=utf-8");
		if(StringUtils.isNotBlank(deviceFlow.getFlowCount()))
		{
			deviceFlow.setFlowCount(Integer.parseInt(deviceFlow.getFlowCount())*1024+"");
		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceFlow);

		String jsonString = deviceFlowSer.deviceflowstatisticsPage(seDto);

		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deviceflowstatistics1")
	public String deviceflowstatistics1(HttpServletRequest req,Model model)
	{
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/deviceinfo/deviceflow_statistics1";
	}
	
	/**
	 * 设备管理 >>查看全部设备信息
	 *
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/deviceflowstatisticspage1")
	public void deviceflowstatisticspage1(SearchDTO searchDTO, DeviceFlow deviceFlow, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		response.setContentType("text/html;charset=utf-8");
		if(StringUtils.isNotBlank(deviceFlow.getFlowCount()))
		{
			deviceFlow.setFlowCount(Integer.parseInt(deviceFlow.getFlowCount())*1024+"");
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceFlow);

		String jsonString = deviceFlowSer.deviceflowstatisticsPage1(seDto);

		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/exportdeviceflowstatistics")
	public void exportdeviceflowstatistics(DeviceFlow deviceFlow, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding("utf-8");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

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
		cell.setCellValue("SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("日流量均值");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("日流量峰值");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue("日流量谷值");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("日流量中位数");
		cell.setCellStyle(style1);
		
		List<DeviceFlow> list = deviceFlowSer.getDeviceflowstatisticsExport(deviceFlow);
		
		for (int i = 0; i < list.size(); i++)
		{

			DeviceFlow df = list.get(i);
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(df.getCountryName());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(df.getSN());
			cell1.setCellStyle(style);
			
			cell1 = row.createCell((short) 2);
			cell1.setCellValue(convertByte(df.getAvgFlow()));
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(convertByte(df.getMaxFlow()));
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(convertByte(df.getMinFlow()));
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(convertByte(df.getMiddleFlow()));
			cell1.setCellStyle(style);
		}
		if(StringUtils.isNotBlank(deviceFlow.getMCC()))
		{
			row = sheet.createRow((int) list.size() + 1);
			row.setHeight((short) 500);
			HSSFCell cell3 = row.createCell((short) 0);
			cell3.setCellValue("");
			cell3.setCellStyle(style1);
			
			row = sheet.createRow((int) list.size() + 2);
			row.setHeight((short) 500);
			HSSFCell cell4 = row.createCell((short) 0);
			cell4.setCellValue("国家");
			cell4.setCellStyle(style1);

			cell4 = row.createCell((short) 1);
			cell4.setCellValue("日流量均值");
			cell4.setCellStyle(style1);

			cell4 = row.createCell((short) 2);
			cell4.setCellValue("日流量峰值");
			cell4.setCellStyle(style1);

			cell4 = row.createCell((short) 3);
			cell4.setCellValue("日流量谷值");
			cell4.setCellStyle(style1);
			
			List<DeviceFlow> list1 = deviceFlowSer.getDeviceflowstatisticsExport1(deviceFlow);
			
			for (int i = 0; i < list1.size(); i++)
			{

				DeviceFlow df = list1.get(i);
				row = sheet.createRow((int) i + list.size() + 3);
				row.setHeight((short) 500);

				HSSFCell cell5 = row.createCell((short) 0);
				cell5.setCellValue(df.getCountryName());
				cell5.setCellStyle(style);
				
				cell5 = row.createCell((short) 1);
				cell5.setCellValue(convertByte(df.getAvgFlow()));
				cell5.setCellStyle(style);

				cell5 = row.createCell((short) 2);
				cell5.setCellValue(convertByte(df.getMaxFlow()));
				cell5.setCellStyle(style);

				cell5 = row.createCell((short) 3);
				cell5.setCellValue(convertByte(df.getMinFlow()));
				cell5.setCellStyle(style);
			}
		}
		DownLoadUtil.execlExpoprtDownload("设备流量使用情况.xls", wb, request, response);
	}
	
	@RequestMapping("/exportdeviceflowstatistics1")
	public void exportdeviceflowstatistics1(DeviceFlow deviceFlow, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding("utf-8");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

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
		cell.setCellValue("日流量均值");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("日流量峰值");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("日流量谷值");
		cell.setCellStyle(style1);
		
		List<DeviceFlow> list = deviceFlowSer.getDeviceflowstatisticsExport1(deviceFlow);
		
		for (int i = 0; i < list.size(); i++)
		{

			DeviceFlow df = list.get(i);
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(df.getCountryName());
			cell1.setCellStyle(style);
			
			cell1 = row.createCell((short) 1);
			cell1.setCellValue(convertByte(df.getAvgFlow()));
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(convertByte(df.getMaxFlow()));
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(convertByte(df.getMinFlow()));
			cell1.setCellStyle(style);
		}
		DownLoadUtil.execlExpoprtDownload("国家流量使用情况.xls", wb, request, response);
	}
	
	public String convertByte(String b)
	{
	    double kilobyte = 1;
	    double megabyte = kilobyte * 1024;
	    double gigabyte = megabyte * 1024;
	    double terabyte = gigabyte * 1024;
	    String result="";   

	    double i = Double.parseDouble(b);
	    
		if(i>=0 && i<megabyte)
		{
			result = i + "KB";
		}
		else if(i>=megabyte && i<gigabyte)
		{
			result = new java.text.DecimalFormat("#.00").format(i/megabyte)+"MB";
		}
		else if(i>=gigabyte && i<terabyte)
		{
			result = new java.text.DecimalFormat("#.00").format(i/gigabyte)+"GB";
		}
		else if(i>=terabyte && i<terabyte*1024)
		{
			result = new java.text.DecimalFormat("#.00").format(i/terabyte)+"TB";
		}
	    
		return result;
	}
	
	@RequestMapping("updateRemark")
	public void updateRemark(DeviceFlow deviceFlow, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		try 
		{
			request.setCharacterEncoding("utf-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		String ID = new String(deviceFlow.getID().getBytes("ISO-8859-1"),"UTF-8");
		
		String desc = new String(deviceFlow.getDescr().getBytes("ISO-8859-1"),"UTF-8");
		
		int count = 0;
		
		String[] ids = ID.split(",");
		
		String[] remarks = desc.split("\\|");
		
		JSONObject jo = new JSONObject();
		
		for (int i = 0; i < ids.length; i++) 
		{
			DeviceFlow deFlow = new DeviceFlow();

			deFlow.setID(ids[i]);
			
			deFlow.setDescr(remarks[i]);
			
			int result = deviceFlowSer.updateInfo(deFlow);
			
			if(result>0)
			{
				count++;
			}
		}
		
		if(count>0)
		{
			jo.put("code", "0");
			jo.put("msg", count+"条数据保存成功！");
		}
		else
		{
			jo.put("code", "1");
			jo.put("msg", "保存失败！");
		}
		
		try 
		{
			response.getWriter().write(jo.toString());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}
