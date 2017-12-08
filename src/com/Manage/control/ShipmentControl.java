package com.Manage.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.Shipment;
@Controller
@RequestMapping("/shipment")
public class ShipmentControl extends BaseController {
	private Logger logger = LogUtil.getInstance(ShipmentControl.class);
	@RequestMapping("/insert")
	public void insert(Shipment shipment,HttpServletRequest req){
		boolean success = ShipmentSer.insert(shipment);
		if(success){
			logger.info("出货记录表数据插入成功！！！");
		}else{
			logger.info("出货记录表数据插入失败！！！");
		}
	}


	@RequestMapping("/exportedShipment")
	public void exportedShipment(Shipment shipm,HttpServletRequest req, HttpServletResponse resp) throws IOException{

		// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("出入库设备记录");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow row = sheet.createRow((int) 0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				HSSFCell cell = row.createCell((short) 0);
				cell.setCellValue("序号");
				cell.setCellStyle(style);
				cell = row.createCell((short) 1);
				cell.setCellValue("SN号");
				cell.setCellStyle(style);
				cell = row.createCell((short) 2);
				cell.setCellValue("状态");
				cell.setCellStyle(style);
				cell = row.createCell((short) 3);
				cell.setCellValue("创建人");
				cell.setCellStyle(style);
				cell = row.createCell((short) 4);
				cell.setCellValue("领用人");
				cell.setCellStyle(style);
				cell = row.createCell((short) 5);
				cell.setCellValue("操作时间");
				cell.setCellStyle(style);
				cell = row.createCell((short) 6);
				cell.setCellValue("备注");
				cell.setCellStyle(style);
				cell = row.createCell((short) 7);
				cell.setCellValue("收货地址");
				cell.setCellStyle(style);

				// 第四步，写入实体数据 实际应用中这些数据从数据库得到，

				List<Shipment> getShipm = null;
				try {
					if(shipm.getSN()=="")
						shipm.setSN("");
					if(shipm.getRemark()=="")
						shipm.setRemark("");
					if(shipm.getDeviceStatus()=="")
						shipm.setDeviceStatus("");
					if(shipm.getBegainTime()==""){
						shipm.setBegainTime("2014-01-01 00:00:00");
						SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
						sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
						String date = sdf.format(new Date());
						shipm.setEndTime(date);
						if(shipm.getRemark()!=""){
							shipm.setRemark(java.net.URLDecoder.decode(shipm.getRemark(),"UTF-8"));
						}
					}
					getShipm = ShipmentSer.exportedShipment(shipm);
				} catch (Exception e1) {
					e1.printStackTrace();
					resp.getWriter().print("0");//导出失败
				}
				for(int i=0;i<getShipm.size();i++){
					// 第五步，创建单元格，并设置值
					row = sheet.createRow((int) i + 1);//创建行并从第二行开始写入导出的数据，第一行为标题

					row.createCell((short) 0).setCellValue(i+1);
					row.createCell((short) 1).setCellValue(getShipm.get(i).getSN());
					row.createCell((short) 2).setCellValue(getShipm.get(i).getDeviceStatus());
					row.createCell((short) 3).setCellValue(getShipm.get(i).getCreatorUserName());
					row.createCell((short) 4).setCellValue(getShipm.get(i).getRecipientName());
					row.createCell((short) 5).setCellValue(getShipm.get(i).getCreatorDate());
					row.createCell((short) 6).setCellValue(getShipm.get(i).getRemark());
					row.createCell((short) 7).setCellValue(getShipm.get(i).getAddress());
				}
				// 第六步，将文件存到指定位置
				try
				{
					SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
					sdf.applyPattern("yyyyMMddHHmmss");// a为am/pm的标记
					String date = sdf.format(new Date());

					FileOutputStream fout = new FileOutputStream("D:/ShipmentLogs"+date+".xls");
					wb.write(fout);
					fout.close();
					resp.getWriter().print("1");
				}
				catch (Exception e)
				{
					e.printStackTrace();
					resp.getWriter().print("0");
				}

	}





}