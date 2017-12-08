 package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.WorkFlow;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/orders/acceptorder")
public class AcceptOrderContorl extends BaseController
{

	private Logger logger = LogUtil.getInstance(AcceptOrderContorl.class);



	@RequestMapping("/index")
	public String index(HttpServletResponse response, HttpServletRequest request, Model model)
	{
		// 查询客户来源绑定下拉框.
		List<Dictionary> dictionaries = dictionarySer.getAllList(com.Manage.common.constants.Constants.DICT_CUSTOMER_SOURCE);
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		model.addAttribute("diclist", dictionaries);
		return "WEB-INF/views/orders/acceptorder_index";
	}



	@RequestMapping("/getpage")
	public void AcceptOrderList(SearchDTO searchDTO, String ifAll, AcceptOrder acceptOrder, HttpServletResponse response)
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		acceptOrder.setCreatorUserID(adminUserInfo.getUserID());
		if (StringUtils.isNotBlank(ifAll))
		{
			acceptOrder.setCreatorUserID(null);
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), acceptOrder);
		String jsonString = acceptOrderSer.getpage(seDto);
		try
		{
			response.getWriter().write(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/save")
	public void save(AcceptOrder acceptOrder,HttpServletRequest request, HttpServletResponse response)
	{

		try
		{
			request.setCharacterEncoding("utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo != null)
		{
			acceptOrder.setCreatorUserID(adminUserInfo.getUserID());
			acceptOrder.setCreatorUserName(adminUserInfo.getUserName());
		}
		else
		{
			try
			{
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		boolean isInsert = StringUtils.isBlank(acceptOrder.getAoID());

		boolean result;
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try
		{
			if (acceptOrder.getCountryList() == null || "".equals(acceptOrder.getCountryList()))
			{
				JSONObject obj = new JSONObject();
				obj.put("error", -2);
				response.getWriter().print(obj.toString());
				return;
			}
			else
			{
				String uclist1 = "";
				String[] uc = acceptOrder.getCountryList().split(",");
				for (int i = 0; i < uc.length; i++)
				{
					String[] temp = uc[i].split("-");
					if (i != uc.length - 1)
					{
						uclist1 += temp[0] + "," + temp[1] + "," + temp[2] + "|";
					}
					else
					{
						uclist1 += temp[0] + "," + temp[1] + "," + temp[2];
					}
				}
				acceptOrder.setCountryList(uclist1);

			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		if (isInsert)
		{

			// 添加接单信息
			acceptOrder.setAoID(getUUID());
			acceptOrder.setSysStatus(1);
			acceptOrder.setCreatorUserID(adminUserInfo.getUserID());
			acceptOrder.setCreatorUserName(adminUserInfo.getUserName());
			acceptOrder.setCreatorDate(format.format(new Date()));
			acceptOrder.setAcceptOrderStatus("未下单");
			result = acceptOrderSer.insert(acceptOrder);

			// 添加工作流信息
			WorkFlow workFlow = new WorkFlow();
			workFlow.setWorkFlowID(getUUID());
			workFlow.setAoID(acceptOrder.getAoID());
			workFlow.setReceiveOrderPer(adminUserInfo.getUserName());
			workFlow.setReceiveOrderDate(format.format(new Date()));
			workFlow.setCustomerName(acceptOrder.getCustomerName());
			workFlow.setWorkFlowStatus("接单");
			workFlowSer.insert(workFlow);

		}
		else
		{
			// 修改接单信息
			acceptOrder.setModifyUserID(adminUserInfo.getUserID());
			acceptOrder.setModifyDate(format.format(new Date()));
			acceptOrder.setAcceptOrderStatus("未下单");
			result = acceptOrderSer.update(acceptOrder);

		}

		if (result)
		{
			logger.debug("接单信息保存成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存接单信息!");
				response.getWriter().println(jsonResult.toString());
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
				admin.setCreatorDate(format.format(new Date()));// 创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				admin.setOperateDate(format.format(new Date()));// 操作时间
				admin.setSysStatus(1);

				if (isInsert)
				{
					admin.setOperateContent("添加了接单信息, 记录ID为: " + acceptOrder.getAoID()); // 操作内容
					admin.setOperateMenu("订单管理>接单"); // 操作菜单
					admin.setOperateType("添加");// 操作类型
				}
				else
				{
					admin.setOperateContent("修改了接单信息, 记录ID为: " + acceptOrder.getAoID());
					admin.setOperateMenu("订单管理>修改接单");
					admin.setOperateType("修改");
				}
				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			logger.debug("接单信息保存失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存接单信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}



	/**
	 * 编辑入口
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(String id, Model model)
	{

		AcceptOrder acceptOrder = acceptOrderSer.getById(id);
		List<Dictionary> dictionaries = dictionarySer.getAllList(com.Manage.common.constants.Constants.DICT_CUSTOMER_SOURCE);
		List<CountryInfo> countries = countryInfoSer.getAll("");
		String countryString = acceptOrder.getCountryList();
		if (!StringUtils.isBlank(countryString))
		{
			for (CountryInfo country : countries)
			{
				String matchString = String.valueOf(country.getCountryCode());
				if (StringUtils.contains(countryString, matchString))
				{
					country.setSelected(true);
				}
			}
		}
		model.addAttribute("diclist", dictionaries);
		model.addAttribute("Countries", countries);
		model.addAttribute("acceptOrder", acceptOrder);

		return "WEB-INF/views/orders/acceptorder_index";
	}



	/**
	 * 根据Id删除
	 * 
	 * @return
	 */
	@RequestMapping("/deletebyid")
	public void deleteById(HttpServletResponse response, String id)
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

		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		AcceptOrder acceptOrder = new AcceptOrder();
		acceptOrder.setAoID(id);
		acceptOrder.setSysStatus(0);
		acceptOrder.setModifyUserID(adminUserInfo.getUserID());
		acceptOrder.setModifyDate(format.format(new Date()));

		if (acceptOrderSer.update(acceptOrder))
		{

			try
			{
				response.getWriter().println("接单信息删除成功!");
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
				admin.setCreatorDate(format.format(new Date()));// 创建时间
				admin.setOperateDate(format.format(new Date()));// 操作时间
				admin.setSysStatus(1);

				admin.setOperateContent("删除了接单信息, 记录ID为: " + acceptOrder.getAoID());
				admin.setOperateMenu("订单管理>删除接单");
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
				response.getWriter().println("接单信息删除出错!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	 @RequestMapping("/exportexcel")
	  public void exportexcel(HttpServletRequest request,AcceptOrder acceptOrder,String begindate,String enddate,
	            HttpServletResponse response) throws UnsupportedEncodingException{
		 	request.setCharacterEncoding("UTF-8"); 
		 	
		 	String pickUpWay=acceptOrder.getPickUpWay();
		 	String orderSource = acceptOrder.getOrderSource();
		 	String deviceTransactionType = acceptOrder.getDeviceTransactionType();
		 	String customerName = acceptOrder.getCustomerName();
		 	String countryList = acceptOrder.getCountryList();
		 	String acceptOrderStatus = acceptOrder.getAcceptOrderStatus();
		 	

		 	pickUpWay = new String(pickUpWay.getBytes("ISO-8859-1"),"UTF-8");
		 	orderSource = new String(orderSource.getBytes("ISO-8859-1"),"UTF-8");
		 	deviceTransactionType =new String(deviceTransactionType.getBytes("ISO-8859-1"),"UTF-8");
		 	customerName=new String(customerName.getBytes("ISO-8859-1"),"UTF-8");
		 	countryList=new String(countryList.getBytes("ISO-8859-1"),"UTF-8");
		 	acceptOrderStatus=new String(acceptOrderStatus.getBytes("ISO-8859-1"),"UTF-8");
		 	
		 	
		 	acceptOrder.setPickUpWay(pickUpWay);
		 	acceptOrder.setOrderSource(orderSource);
		 	acceptOrder.setDeviceTransactionType(deviceTransactionType);
		 	acceptOrder.setCustomerName(customerName);
		 	acceptOrder.setCountryList(countryList);
		 	acceptOrder.setAcceptOrderStatus(acceptOrderStatus);
		 
		 	
			// 第一步，创建一个webbook，对应一个Excel文件
	        HSSFWorkbook wb = new HSSFWorkbook();
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
	        HSSFSheet sheet = wb.createSheet("Sheet1");
	        sheet.setColumnWidth((short)0, (short)4000);
	        sheet.setColumnWidth((short)1, (short)4000);
	        sheet.setColumnWidth((short)2, (short)4000);
	        sheet.setColumnWidth((short)3, (short)4000);
	        sheet.setColumnWidth((short)4, (short)6000);
	        sheet.setColumnWidth((short)5, (short)4000);
	        sheet.setColumnWidth((short)6, (short)6000);
	        sheet.setColumnWidth((short)7, (short)4000);
	        sheet.setColumnWidth((short)8, (short)4000);
	        sheet.setColumnWidth((short)9, (short)4000);
	        sheet.setColumnWidth((short)10, (short)4000);
	        sheet.setColumnWidth((short)11, (short)4000);
	        sheet.setColumnWidth((short)12, (short)6000);
	        sheet.setColumnWidth((short)13, (short)4000);
	        sheet.setColumnWidth((short)14, (short)6000);
	        sheet.setColumnWidth((short)15, (short)6000);
	        sheet.setColumnWidth((short)16, (short)3000);
	        sheet.setColumnWidth((short)17, (short)6000);
	        sheet.setColumnWidth((short)18, (short)3000);
	        sheet.setColumnWidth((short)19, (short)3000);
	        sheet.setColumnWidth((short)20, (short)8000);
	        sheet.setColumnWidth((short)21, (short)8000);
	        sheet.setColumnWidth((short)22, (short)8000);
	      //  sheet.setDefaultRowHeightInPoints(5000);
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
	        cell.setCellValue("客户姓名");
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 1);
	        cell.setCellValue("手机号");
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 2);
	        cell.setCellValue("地址");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 3);
	        cell.setCellValue("旺旺号");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 4);
	        cell.setCellValue("网店订单编号");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 5);
	        cell.setCellValue("来源");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 6);
	        cell.setCellValue("使用开始时间");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 7);
	        cell.setCellValue("可用国家");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 8);
	        cell.setCellValue("行程");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 9);
	        cell.setCellValue("取货方式");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 10);
	        cell.setCellValue("数量");
	        cell.setCellStyle(style);

	        
	        cell = row.createCell((short) 11);
	        cell.setCellValue("金额");
	        cell.setCellStyle(style);


	        cell = row.createCell((short) 12);
	        cell.setCellValue("待发货日期");
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 13);
	        cell.setCellValue("下单人");
	        cell.setCellStyle(style);


	        cell = row.createCell((short) 14);
	        cell.setCellValue("下单时间");
	        cell.setCellStyle(style);


	        cell = row.createCell((short) 15);
	        cell.setCellValue("接单状态");
	        cell.setCellStyle(style);

	        cell = row.createCell((short) 16);
	        cell.setCellValue("交易类型");
	        cell.setCellStyle(style);

	        cell = row.createCell((short) 17);
	        cell.setCellValue("接单时间");
	        cell.setCellStyle(style);

	        cell = row.createCell((short) 18);
	        cell.setCellValue("SN");
	        cell.setCellStyle(style);

	        cell = row.createCell((short) 19);
	        cell.setCellValue("是否归还");
	        cell.setCellStyle(style);


	        cell = row.createCell((short) 20);
	        cell.setCellValue("备注");
	        cell.setCellStyle(style);


	        cell = row.createCell((short) 21);
	        cell.setCellValue("接单人");
	        cell.setCellStyle(style);


	        cell = row.createCell((short) 22);
	        cell.setCellValue("订单天数");
	        cell.setCellStyle(style);


	        
	        
	        
	        List<AcceptOrder> acceptOrders = acceptOrderSer.exprotexcel(acceptOrder);
	      
	        HSSFCellStyle style1 = wb.createCellStyle();
	        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
	        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
	        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
	        
	        
	        for (int i = 0; i < acceptOrders.size(); i++) {
	        	 // 第四步，创建单元格，并设置值
		        row = sheet.createRow((int) i+1);
		        row.setHeight((short) 450);
		        
		       
		        HSSFCell cell1 = row.createCell((short) 0);
	            cell1.setCellValue(acceptOrders.get(i).getCustomerName()); 
		        cell1.setCellStyle(style1); 
		        
		        cell1 = row.createCell((short) 1);
	            cell1.setCellValue(acceptOrders.get(i).getCustomerPhone()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 2);
	            cell1.setCellValue(acceptOrders.get(i).getCustomerAddress()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 3);
	            cell1.setCellValue(acceptOrders.get(i).getWangwangNo()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 4);
	            cell1.setCellValue(acceptOrders.get(i).getOrderNumber()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 5);
	            cell1.setCellValue(acceptOrders.get(i).getOrderSource()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 6);
	            cell1.setCellValue(acceptOrders.get(i).getStartTime()); 
		        cell1.setCellStyle(style1);
		        

				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(acceptOrders.get(i).getCountryList());
		        
		        
		        cell1 = row.createCell((short) 7);
	            cell1.setCellValue(wrapper.getCountryNameStrings()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 8);
	            cell1.setCellValue(acceptOrders.get(i).getTrip()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 9);
	            cell1.setCellValue(acceptOrders.get(i).getPickUpWay()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 10);
	            cell1.setCellValue(acceptOrders.get(i).getTotal()); 
		        cell1.setCellStyle(style1);
		        

		        cell1 = row.createCell((short) 11);
	            cell1.setCellValue(acceptOrders.get(i).getPrice()); 
		        cell1.setCellStyle(style1);


		        cell1 = row.createCell((short) 12);
	            cell1.setCellValue(acceptOrders.get(i).getShippmentDate()); 
		        cell1.setCellStyle(style1);

		        cell1 = row.createCell((short) 13);
	            cell1.setCellValue(acceptOrders.get(i).getBelowOrderPer()); 
		        cell1.setCellStyle(style1);

		        cell1 = row.createCell((short) 14);
	            cell1.setCellValue(acceptOrders.get(i).getBelowOrderDate()); 
		        cell1.setCellStyle(style1);

		        cell1 = row.createCell((short) 15);
	            cell1.setCellValue(acceptOrders.get(i).getAcceptOrderStatus()); 
		        cell1.setCellStyle(style1);

		        cell1 = row.createCell((short) 16);
	            cell1.setCellValue(acceptOrders.get(i).getDeviceTransactionType()); 
		        cell1.setCellStyle(style1);

		        cell1 = row.createCell((short) 17);
	            cell1.setCellValue(acceptOrders.get(i).getCreatorDate()); 
		        cell1.setCellStyle(style1);

		        cell1 = row.createCell((short) 18);
	            cell1.setCellValue(acceptOrders.get(i).getSN()); 
		        cell1.setCellStyle(style1);

		        cell1 = row.createCell((short) 19);
	            cell1.setCellValue(acceptOrders.get(i).getIfReturn()); 
		        cell1.setCellStyle(style1);


		        cell1 = row.createCell((short) 20);
	            cell1.setCellValue(acceptOrders.get(i).getRemark()); 
		        cell1.setCellStyle(style1);


		        cell1 = row.createCell((short) 21);
	            cell1.setCellValue(acceptOrders.get(i).getCreatorUserName()); 
		        cell1.setCellStyle(style1);


		        cell1 = row.createCell((short) 22);
	            cell1.setCellValue(acceptOrders.get(i).getDays()); 
		        cell1.setCellStyle(style1);
		        
		        
		        
			}
           try {
				DownLoadUtil.execlExpoprtDownload("客服接单数据详情.xls",wb, request, response);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	  }
	 
	 @RequestMapping("/biaojiyixiadan")
	 public void biaojiyixiadan(String id,String SN,HttpServletRequest request,HttpServletResponse response){
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
         JSONObject jsonResult = new JSONObject();
         
         try {
			 //设置接单记录状态为已取消
			 AcceptOrder acceptOrder = new AcceptOrder();
			 acceptOrder.setAoID(id);
			 acceptOrder.setAcceptOrderStatus("已下单");
			 String[] snarray = {};
			 if(StringUtils.isNotBlank(SN)){
				 snarray = SN.trim().split("/");
				 SN="";
				 for(int i=0;i<snarray.length;i++){
					 SN=SN+Constants.SNformat(snarray[i])+"，";
				 }
				 SN=SN.substring(0,SN.length()-1);
			 }
			 acceptOrder.setSN(SN);
			 
			 boolean success = acceptOrderSer.update(acceptOrder);
			 if(success){
				 jsonResult.put("code", 0);
				 jsonResult.put("msg", "标记成功！");
				 response.getWriter().println(jsonResult.toString());
			 }else{
				 jsonResult.put("code", 1);
				 jsonResult.put("msg", "标记成功！");
				 response.getWriter().println(jsonResult.toString());
			 }
			 
		 } catch (Exception e) {
			 
			 try{
				 jsonResult.put("code", 1);
				 jsonResult.put("msg", "标记失败！");
				 response.getWriter().println(jsonResult.toString());
			 }catch (Exception e1) {
				 e1.printStackTrace();
			 }
			 e.printStackTrace();
		 }
	 
	 }
	 
	 /**
	  * 取消订单
	  * @param id
	  * @param request
	  * @param response
	  */
	 @RequestMapping("/cancel")
	 public void cancelOrder(String id,HttpServletRequest request,HttpServletResponse response){
         
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
         JSONObject jsonResult = new JSONObject();
         
         try {
			 //设置接单记录状态为已取消
			 AcceptOrder acceptOrder = new AcceptOrder();
			 acceptOrder.setAoID(id);
			 acceptOrder.setAcceptOrderStatus("已取消");
			 acceptOrderSer.update(acceptOrder);
			 
			 //设置总订单记录状态为已取消
			 OrdersInfo ordersInfo = ordersInfoSer.getByAoID(id); //根据接单ID查询总订单记录
			if(ordersInfo!=null){
				ordersInfoSer.cancelOrder(ordersInfo.getOrderID());
				 
				 //设置流量订单记录状态为已取消
				 deviceDealOrdersSer.cancelOrder(ordersInfo.getOrderID());
				 
				 //设置设备交易订单记录状态为已取消
				 flowDealOrdersSer.cancelOrder(ordersInfo.getOrderID());
				 jsonResult.put("code", 0);
				 jsonResult.put("msg", "订单取消成功！");
				 response.getWriter().println(jsonResult.toString());
				 
			} else{
				jsonResult.put("code", 0);
				 jsonResult.put("msg", "无订单需取消！");
				 response.getWriter().println(jsonResult.toString());
			}
			 
			 
			 
		 } catch (Exception e) {
			 
			 try{
				 jsonResult.put("code", 1);
				 jsonResult.put("msg", "订单取消失败！");
				 response.getWriter().println(jsonResult.toString());
			 }catch (Exception e1) {
				 e1.printStackTrace();
			 }
			 e.printStackTrace();
		 }
	 }
}
