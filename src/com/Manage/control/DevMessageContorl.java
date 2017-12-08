package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.DevMessage;
import com.Manage.entity.Distributor;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/device/devmessage")
public class DevMessageContorl extends BaseController
{
	private Logger logger = LogUtil.getInstance(DevMessageContorl.class);



	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @param ordersInfo
	 * @param responses
	 */
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, DevMessage devMessage, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), devMessage);
		String pagesString = devMessageSer.getpageString(seDto);
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
	 * excel数据导出
	 * @param searchDTO
	 * @param devMessage
	 * @param response
	 */
	@RequestMapping("/exportexcel")
	public void exportexcel(DevMessage devMessage, HttpServletResponse response)
	{
		// 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("Sheet1");
        sheet.setColumnWidth((short)0, (short)4000);

        sheet.setColumnWidth((short)1, (short)2500);
        sheet.setColumnWidth((short)2, (short)5000);
        sheet.setColumnWidth((short)3, (short)50000);
        sheet.setColumnWidth((short)4, (short)5000);
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
        cell.setCellValue("SN");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 1);
        cell.setCellValue("类型");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 2);
        cell.setCellValue("IMSI");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 3);
        cell.setCellValue("短信内容");
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 4);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
        
        List<DevMessage> devMessages = devMessageSer.exprotexcel(devMessage);
        
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
        for (int i = 0; i < devMessages.size(); i++) {
        	 // 第四步，创建单元格，并设置值
	        row = sheet.createRow((int) i+1);
	        row.setHeight((short) 450);
	        
	        
	        HSSFCell cell1 = row.createCell((short) 0);
            cell1.setCellValue(devMessages.get(i).getSN	()); 
	        cell1.setCellStyle(style1); 
	        
	        cell1 = row.createCell((short) 1);
            cell1.setCellValue(devMessages.get(i).getType()); 
	        cell1.setCellStyle(style1);
	        
	        
	        cell1 = row.createCell((short) 2);
            cell1.setCellValue(devMessages.get(i).getIMSI()); 
	        cell1.setCellStyle(style1);
	        

	        cell1 = row.createCell((short) 3);
            cell1.setCellValue(devMessages.get(i).getContent()); 
	        cell1.setCellStyle(style1);
	        
	        cell1 = row.createCell((short) 4);
            cell1.setCellValue(devMessages.get(i).getCreatorDate()); 
	        cell1.setCellStyle(style1);
	        
		}
        try {
			DownLoadUtil.execlExpoprtDownload("短信详情.xls",wb, request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/skipsmsnotes")
	public String skipsmsnotes(Model model)
	{
		return "WEB-INF/views/deviceinfo/deviceInfo_smsnotes";
	}
	
	
}
