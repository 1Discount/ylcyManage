package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.AfterSaleInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.WorkFlow;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/orders/aftersale")
public class AfterSaleInfoControl extends BaseController {

	private Logger logger = LogUtil.getInstance(AfterSaleInfoControl.class);
	
	@RequestMapping("/list")
	public String index(String SN,Model model){
		//绑定流量订单状态.
		List<Dictionary> dictionarie=dictionarySer.getAllList(Constants.DICT_FLOWORDER_STATUS);
		List<Dictionary> dicAfterSale=dictionarySer.getAllList(Constants.DICT_AFTER_SALE);
		JSONArray ja = new JSONArray();
		for (Dictionary dictionary : dicAfterSale) {
			JSONObject jo = new JSONObject();
			jo.put("value", dictionary.getValue());
			jo.put("label", dictionary.getLabel());
			ja.add(jo);
		}
		List<Distributor> distributors = distributorSer.getAll("");
		model.addAttribute("SN", SN);
		model.addAttribute("diclist",dictionarie);
		model.addAttribute("dicAfterSale",ja.toString());
		model.addAttribute("distributors", distributors);
		
		return "WEB-INF/views/orders/aftersale_list";
	}
	
	@RequestMapping("/getpage")
	public void getpage(String fdID,SearchDTO searchDTO,HttpServletResponse response){
		System.out.println("getpage.............."+fdID);
		AfterSaleInfo afterSaleInfo = new AfterSaleInfo();
		afterSaleInfo.setFlowDealID(fdID);
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), afterSaleInfo);
		String jsonString = afterSaleInfoSer.getPage(seDto);
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增售后记录
	 * @param afterSaleInfo
	 * @param response
	 * @param request
	 */
	@RequestMapping("/save")                 
	public void save(AfterSaleInfo afterSaleInfo,HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		
		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		if (adminUserInfo != null) {
			afterSaleInfo.setCreatorDate(format.format(new Date()));
			afterSaleInfo.setCreatorUserID(adminUserInfo.getUserID());
			afterSaleInfo.setCreatorUserName(adminUserInfo.getUserName());
		} else {
			try {
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		
		boolean result=false;
		
		afterSaleInfo.setAsiID(getUUID());
		afterSaleInfo.setSysStatus(true);
		afterSaleInfo.setCreatorUserID(adminUserInfo.getUserID());
		afterSaleInfo.setCreatorUserName(adminUserInfo.getUserName());
		afterSaleInfo.setCreatorDate(format.format(new Date()));
		if(afterSaleInfo.getRefundAmount()==null)
		{
			afterSaleInfo.setRefundAmount(0.0);
		}
		
		result = afterSaleInfoSer.insert(afterSaleInfo);
		
		if(afterSaleInfo.getHandleResult().equals("退款")){
			//更新流量订单退款信息
			FlowDealOrders flowDealOrders = new FlowDealOrders();
			flowDealOrders.setFlowDealID(afterSaleInfo.getFlowDealID());
			flowDealOrders.setRefundStatus("已退款");
			flowDealOrders.setRefundAmount(afterSaleInfo.getRefundAmount());
			flowDealOrdersSer.updateRefundInfo(flowDealOrders);
		}
		//修改工作流售后信息
		WorkFlow workFlow = new WorkFlow();
		workFlow.setFlowDealOrderID(afterSaleInfo.getFlowDealID());
		workFlow.setCustomerServiceID(afterSaleInfo.getAsiID());
		workFlow.setCustomerServicePer(adminUserInfo.getUserName());
		workFlow.setCustomerServiceDate(format.format(new Date()));
		workFlow.setHandleFruit(afterSaleInfo.getHandleResult());
		workFlow.setWorkFlowStatus("售后");
		workFlowSer.updatAfterSaleInfo(workFlow);
		
		if (result) {
			logger.debug("售后记录保存成功");
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存售后记录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			
			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				admin.setCreatorDate(format.format(new Date()));//创建时间
				admin.setOperateDate(format.format(new Date()));//操作时间
				admin.setSysStatus(1);
	
				admin.setOperateContent("添加了售后记录, 记录ID为: " + afterSaleInfo.getAsiID()); //操作内容
				admin.setOperateMenu("订单管理>添加售后记录"); //操作菜单
				admin.setOperateType("添加");//操作类型

				adminOperateSer.insertdata(admin);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			
			logger.debug("售后记录保存失败");
			try {
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存售后记录出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查看售后记录
	 * @param flowDealID
	 */
	@RequestMapping("/view")
	public void view(String flowDealID,String customerName,HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
			customerName = URLDecoder.decode(customerName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		AfterSaleInfo afterSaleInfo = afterSaleInfoSer.getByFlowDealID(flowDealID);
		StringBuffer sb = new StringBuffer();
		if(afterSaleInfo!=null){
			sb.append("<table class=\"table table-bordered\">");
	        sb.append("<tr><td>流量订单ID：</td>");
		    sb.append("<td><b>"+flowDealID+"</b></td>");
		    sb.append("<td>客户姓名：</td>");
		    sb.append("<td><b>"+customerName+"</b></td></tr>");
	        sb.append("<tr><td>问题类型：</td>");
		    sb.append("<td><b>"+(afterSaleInfo.getProblemType()==null?"":afterSaleInfo.getProblemType())+"</b></td>");
		    sb.append("<td>问题级别：</td>");
		    sb.append("<td><b>"+(afterSaleInfo.getProblemLevel()==null?"":afterSaleInfo.getProblemLevel())+"</b></td></tr>");
	        sb.append("<tr><td>处理描述：</td>");
		    sb.append("<td><b>"+(afterSaleInfo.getHandleDescription()==null?"":afterSaleInfo.getHandleDescription())+"</b></td>");
		    sb.append("<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>");
		    sb.append("<td><b>"+(afterSaleInfo.getRemark()==null?"":afterSaleInfo.getRemark())+"</b></td></tr>");
	        sb.append("<tr><td>处理结果：</td>");
		    sb.append("<td><b>"+(afterSaleInfo.getHandleResult()==null?"":afterSaleInfo.getHandleResult())+"</b></td>");
		    sb.append("<td>退款金额：</td>");
		    sb.append("<td><b>"+(afterSaleInfo.getRefundAmount()==0?"无":afterSaleInfo.getRefundAmount())+"</b></td>");
	        sb.append("</tr></table>");
		}else{
			sb.append("<table>");
			sb.append("<tr><td style=\"text-align:center;\" colspan=\"4\">暂无售后记录</td>");
			sb.append("</tr></table>");
		}
        try{
			response.getWriter().println(sb.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/*	public void check(String flowDealID,HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();
		AfterSaleInfo afterSaleInfo = afterSaleInfoSer.getByFlowDealID(flowDealID);
		if(afterSaleInfo!=null){
			
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "已有记录，请选择改行查看");
				response.getWriter().write(jsonResult.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			
			try {
				jsonResult.put("code", 1);
				response.getWriter().write(jsonResult.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * 删除售后记录
	 * @param asiID
	 * @param response
	 * @param request
	 */
	@RequestMapping("/deleteinfo")
	public void deleteinfo(String asiID,HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		if (adminUserInfo == null) {
			try {
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		
		boolean result=false;
		
		result = afterSaleInfoSer.deleteById(asiID);
		
		if(result){
			logger.debug("售后记录删除成功");
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功删除售后记录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			
			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				admin.setCreatorDate(format.format(new Date()));//创建时间
				admin.setOperateDate(format.format(new Date()));//操作时间
				admin.setSysStatus(1);
	
				admin.setOperateContent("删除了售后记录, 记录ID为: " + asiID); //操作内容
				admin.setOperateMenu("订单管理>删除售后记录"); //操作菜单
				admin.setOperateType("删除");//操作类型

				adminOperateSer.insertdata(admin);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{

			logger.debug("售后记录删除失败");
			try {
				jsonResult.put("code", 1);
				jsonResult.put("msg", "删除售后记录出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/exproexcel")
	public void exproexcel(HttpServletRequest request,AfterSaleInfo afterSaleInfo,
            HttpServletResponse response){
		// 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("Sheet1");
        sheet.setColumnWidth((short)0, (short)4000);
        sheet.setColumnWidth((short)1, (short)8500);
        sheet.setColumnWidth((short)2, (short)8000);
        sheet.setColumnWidth((short)3, (short)8000);
        sheet.setColumnWidth((short)4, (short)8000);
        sheet.setColumnWidth((short)5, (short)8000);
        sheet.setColumnWidth((short)6, (short)8000);
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
        cell.setCellValue("问题类型");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 1);
        cell.setCellValue("问题级别");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 2);
        cell.setCellValue("处理结果");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 3);
        cell.setCellValue("退款金额");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 4);
        cell.setCellValue("问题出现时间");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 5);
        cell.setCellValue("处理描述");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 6);
        cell.setCellValue("备注");
        cell.setCellStyle(style);
        

        afterSaleInfo.setEndDate(afterSaleInfo.getEndDate()+" 23:59:59");
        List<AfterSaleInfo> afterSaleInfos = afterSaleInfoSer.getafterExprotExcel(afterSaleInfo);
        
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
        
        for (int i = 0; i < afterSaleInfos.size(); i++) {
        	 // 第四步，创建单元格，并设置值
	        row = sheet.createRow((int) i+1);
	        row.setHeight((short) 450);
	        
	        
	        HSSFCell cell1 = row.createCell((short) 0);
            cell1.setCellValue(afterSaleInfos.get(i).getProblemType()); 
	        cell1.setCellStyle(style1); 
	        
	        cell1 = row.createCell((short) 1);
            cell1.setCellValue(afterSaleInfos.get(i).getProblemLevel()); 
	        cell1.setCellStyle(style1);
	        
	        
	        cell1 = row.createCell((short) 2);
            cell1.setCellValue(afterSaleInfos.get(i).getHandleResult()); 
	        cell1.setCellStyle(style1);
	        
	        
	        cell1 = row.createCell((short) 3);
            cell1.setCellValue(afterSaleInfos.get(i).getRefundAmount()); 
	        cell1.setCellStyle(style1);
	        
	        
	        cell1 = row.createCell((short) 4);
            cell1.setCellValue(afterSaleInfos.get(i).getProblemTime()); 
	        cell1.setCellStyle(style1);
	        
	        
	        cell1 = row.createCell((short) 5);
            cell1.setCellValue(afterSaleInfos.get(i).getHandleDescription()); 
	        cell1.setCellStyle(style1);
	        
	        
	        cell1 = row.createCell((short) 6);
            cell1.setCellValue(afterSaleInfos.get(i).getRemark()); 
	        cell1.setCellStyle(style1);
	        
		}
        try {
			DownLoadUtil.execlExpoprtDownload(afterSaleInfo.getBeginDate()+"到"+afterSaleInfo.getEndDate()+"售后服务记录.xls",wb, request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
