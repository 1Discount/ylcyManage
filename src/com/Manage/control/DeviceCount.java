package com.Manage.control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Distributor;
import com.Manage.entity.Shipment;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/devicecount")
public class DeviceCount extends BaseController {
	private Logger logger = LogUtil.getInstance(DeviceCount.class);


	@RequestMapping("/list")
	public String devicelist(HttpServletRequest req){
		Distributor dis = new Distributor();
		dis.setType("设备");
		List<Distributor> distributor = distributorSer.getbytype(dis);
		req.setAttribute("distributor", distributor);
		return "WEB-INF/views/orders/deviceCount_list";
	}

	/**
	 * 获取渠道商数据
	 * @param searchDTO
	 * @param distributor
	 * @param deviceinfo
	 * @param response
	 */
	@RequestMapping("/getdislist")
	public void getdislist(SearchDTO searchDTO,Distributor distributor, DeviceInfo deviceinfo,HttpServletResponse response){

		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), distributor);
		String jsonString = distributorSer.getpageStringDistributor(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * 获取渠道商设备数据
	 */
	@RequestMapping("/getdistributordata")
	public void getdistributordata(){

//	    distributorSer.getAll(arg)

	}

	/**
	 * 库存设备信息
	 * @param deviceStatus 设备状态
	 * @param deallType  设备交易类型
	 * @param ifReturn  是否已归还
	 * @param SN 设备号
	 * @param OrderID 订单id
	 */
	@RequestMapping("/kucunDeviceInfo")
	public void kucunDeviceInfo(SearchDTO searchDTO,Distributor distributor, DeviceInfo deviceinfo,HttpServletResponse response){
		System.out.println("库存设备："+deviceinfo.toString());
		if(deviceinfo.getIfReturn()!=null){
			  if(deviceinfo.getIfReturn().equals("是"))
				  deviceinfo.setDeviceStatus("可使用");
			  else if(deviceinfo.getIfReturn().equals("否"))
				  deviceinfo.setDeviceStatus("使用中");
			}
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), deviceinfo);
		String jsonString = deviceInfoSer.getpageStringkucun(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@RequestMapping("/getygqSN")
	public void getygqSN(SearchDTO searchDTO, DeviceInfo deviceinfo,HttpServletResponse response){

		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),searchDTO.getPageSize(), searchDTO.getSortName(),searchDTO.getSortOrder(), deviceinfo);
		String jsonString = deviceInfoSer.getygqSN(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/exportedgetygqSN")
	public void exportedgetygqSN(DeviceInfo device,HttpServletRequest req, HttpServletResponse resp) throws IOException{

		// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("订单已过期未归还设备记录");
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
				cell.setCellValue("订单号");
				cell.setCellStyle(style);
				cell = row.createCell((short) 3);
				cell.setCellValue("领用人");
				cell.setCellStyle(style);
				cell = row.createCell((short) 4);
				cell.setCellValue("备注");
				cell.setCellStyle(style);


				// 第四步，写入实体数据 实际应用中这些数据从数据库得到，

				List<DeviceInfo> resDevice = null;
				try {
					if(device.getSN()=="")
						device.setSN(null);
					   resDevice = deviceInfoSer.getygqSNdevice(device);
				} catch (Exception e1) {
					e1.printStackTrace();
					resp.getWriter().print("0");//导出失败
				}
				for(int i=0;i<resDevice.size();i++){
					// 第五步，创建单元格，并设置值
					row = sheet.createRow((int) i + 1);//创建行并从第二行开始写入导出的数据，第一行为标题

					row.createCell((short) 0).setCellValue(i+1);
					row.createCell((short) 1).setCellValue(resDevice.get(i).getSN());
					row.createCell((short) 2).setCellValue(resDevice.get(i).getOrderID());
					row.createCell((short) 3).setCellValue(resDevice.get(i).getRecipientName());
					row.createCell((short) 4).setCellValue(resDevice.get(i).getRemark());
				}
				// 第六步，将文件存到指定位置
				try
				{
					SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
					sdf.applyPattern("yyyyMMddHHmmss");// a为am/pm的标记
					String date = sdf.format(new Date());

					FileOutputStream fout = new FileOutputStream("D:/DeviceInfoygq"+date+".xls");
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
