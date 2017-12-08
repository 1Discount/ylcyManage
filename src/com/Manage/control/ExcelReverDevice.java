package com.Manage.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.FlowDealOrders;

@Controller
@RequestMapping("/revertDevice")
public class ExcelReverDevice extends BaseController{

	private Logger logger = LogUtil.getInstance(ExcelReverDevice.class);

	@RequestMapping("/revertall")
	public String revertdevice(Model model, HttpServletRequest req, HttpServletResponse resp){
		return "WEB-INF/views/deviceinfo/deviceInfo_revertall";
	}


	@RequestMapping(value="/readSN",method=RequestMethod.POST)
    public String insert(@RequestParam("file") MultipartFile file,HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SQLException, FileNotFoundException, IOException, InterruptedException{
     System.out.println("得到上传excel请求");
     SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
     MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
     int allDatacount=0;
     String logoPathDir = "/files"+ dateformat.format(new Date());
     //得到excel保存目录的真实路径
  String temp = request.getSession().getServletContext().getRealPath(logoPathDir);
     //创建文件保存路径文件夹
  File tempFile = new File(temp);
  System.out.println("这个是temp："+temp);

  MultipartFile multipartFile = multipartRequest.getFile("file");

     if (!tempFile.exists()) {
       tempFile.mkdirs();
     }
     //获取文件后缀名
     String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
     //构建文件名称
     String logExcelName = multipartFile.getOriginalFilename();

     String fileName = temp + File.separator   + logExcelName;
     File files = new File(fileName);
     try {
        multipartFile.transferTo(files);
       } catch (IllegalStateException e) {
       e.printStackTrace();
      } catch (IOException e) {
       e.printStackTrace();
       }


   String[][] result = getData(files, 1);
   int rowLength = result.length;
   System.out.println(result.length);
//
            SimpleDateFormat sdfdate = new SimpleDateFormat();// 格式化时间
            sdfdate.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记

            AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
            String createid = adminUserInfo.userID;//创建人ID
            String creatname = adminUserInfo.userName;//创建人
   String sn="";

   for (int i = 0; i < rowLength; i++) {
	   sn=sn+(result[i][1])+"/";
	   System.out.println("是否需要入库："+result[i][2]);
	   DeviceInfo dev = new DeviceInfo();dev.setSN(result[i][1]);dev.setRepertoryStatus(result[i][2]);
	   try {
		 deviceInfoSer.updaterepertoryStatus(dev);
	   } catch (Exception e) {
		 e.printStackTrace();
	   }
   }
   sn=sn.substring(0,sn.length()-1);
   System.out.println("所有读到的SN:"+sn);

 ////////////////开始归还操作、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、//

   String resultmsg = "";
	try {
		req.setCharacterEncoding("utf-8");
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	resp.setContentType("text/html;charset=utf-8");
//	String sn = req.getParameter("device_sn");
	System.out.println("sn:"+sn);
//	AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
	String updateid = adminUserInfo.userID;//修改人ID
	String username = adminUserInfo.userName;
	int issuccesscount=0;

	//归还操作判断
	if(sn.indexOf("/")>0){
		System.out.println("进入多个设备归还...");
		String[] strArray=null;
		strArray = sn.split("/");
		String[] strresult = new String[strArray.length];
		for(int i=0;i<strArray.length;i++){
//			strresult[i]="17215021000"+strArray[i];
			strresult[i]=Constants.SNformat(strArray[i]);

		}
		int forresutcount=0;
		//多个设备号归还
		for(int i=0;i<strresult.length;i++){
			String sns=strresult[i];
			//输入单个设备号归还
			SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		    sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
			String date = sdf.format(new Date());

			int backresult = deviceInfoSer.getdevice(strresult[i]);
			System.out.println("查看设备信息表是否有此设备 的记录:"+backresult);
			int snCount = 0;
			try {
				snCount = flowDealOrdersSer.getIsFlowUseOrderForSn(strresult[i]);// 判断此SN是否有未完成的有效流量订单

			// 有则不允许使用归还操作
			if (snCount > 0)
			{

				System.out.println("有未结束的有效订单，不能进行归还操作！");
//				resp.getWriter().print("SN:" + strresult[i] + " 有未结束的有效流量订单！归还失败！");
				resultmsg=resultmsg+"设备:"+strresult[i]+" 有未结束的有效流量订单！归还失败！<br/>";
			}else{

			if(backresult>0)
			{
				System.out.println("查询此设备是否有交易记录！");

				DeviceDealOrders devicedeal = new DeviceDealOrders();
				devicedeal.setSN(strresult[i]);//sn
				devicedeal.setDeallType("租用");//设备状态为租用
				devicedeal.setIfReturn("否");//还没有归还此设备
//				int devicedealnum = deviceInfoSer.searchDevice(devicedeal);//通过sn查询此设备的交易记录（是否有设备记录）
				//2015-9-25 11:49:08 （汪博讨论结果） 修改为该设备状态是否为【可使用】 ，如果为【可使用】状态不处理，【不是可使用】状态进行下面的归还操作

				int devicedealnum = deviceInfoSer.searchDevicekeshiyong(devicedeal);
				if(devicedealnum >0)
				{
					System.out.println("有此设备的交易记录，修改交易状态及设备状态！");
					//1.修改设备信息表设备状态 及库存状态
					DeviceInfo deviceinfodata = new DeviceInfo();
					deviceinfodata.setSN(strresult[i]);
					deviceinfodata.setRepertoryStatus("入库");//库存状态
					deviceinfodata.setDeviceStatus("可使用");//设备状态
					deviceinfodata.setModifyUserID(updateid);//修改人
					deviceinfodata.setModifyDate(date);//修改时间
					int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
					if(num>0)
					{
				    	System.out.println("第一步：修改设备信息表状态成功！deviceInfo");

				    	DeviceDealOrders devicedeals = new DeviceDealOrders();//归还设备后修改设备订单表状态
						devicedeals.setSN(strresult[i]);
						devicedeals.setIfReturn("是");//是否归还
						devicedeals.setIfFinish("是");//订单是否完成
						devicedeals.setOrderStatus("已过期");//订单状态改为已过期
						devicedeals.setModifyUserID(updateid);//修改人ID
						SimpleDateFormat sdf2 = new SimpleDateFormat();// 格式化时间
					    sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记

					    devicedeals.setReturnDate(date);//归还日期
						devicedeal.setModifyDate(new DateUtils().parseDate(sdf2.format(new Date())));//修改日期

						int devicedealsnum = deviceInfoSer.updateDeviceDealOrders(devicedeals);//2.修改设备订单表 设备状态为 已归还
						if(devicedealsnum>0){
							System.out.println("第二步：修改设备订单表 设备状态为 已归还成功！devicedealorders");

							//3.修改流量订单表（数据服务）
							FlowDealOrders fdo=new FlowDealOrders();
							fdo.setOrderStatus("不可用");
							fdo.setSN(strresult[i]);
							fdo.setModifyDate(new DateUtils().parseDate(date));
							fdo.setModifyUserID(updateid);

//							int three =
									try {
										flowDealOrdersSer.updateOrderStatusForSn(fdo);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										logger.info("归还设备，操作流量订单失败！"+e1.getMessage());
										e1.printStackTrace();
									}
//							if(three>0){
								System.out.println("第三步:修改流量交易订单成功！flowdealorders");

								try{
									AdminOperate admin = new AdminOperate();
									admin.setOperateID(UUID.randomUUID().toString());//id
									admin.setCreatorDate(date);//创建时间
									admin.setCreatorUserID(updateid);//创建人ID
									admin.setCreatorUserName(username);//创建人姓名
									admin.setOperateContent("归还设备 ,设备序列号："+strresult[i]);//操作内容
									admin.setOperateDate(date);//操作时间
									admin.setOperateMenu("设备管理>归还设备");//操作菜单
									admin.setOperateType("归还");//操作类型
									admin.setSysStatus(1);
									adminOperateSer.insertdata(admin);
									}catch (Exception e) {
										logger.debug(e.getMessage());
										e.printStackTrace();
									}

								// 修改下单表  租赁设备 是否归还状态为 是
								try {
									acceptOrderSer.updateAccepOrderBySn(strresult[i]);
								} catch (Exception e) {
									logger.info("修改下单表状态为是");
									e.printStackTrace();
								}

									System.out.println("修改设备交易表状态成功，设备归还成功！");
									resultmsg=resultmsg+"设备"+strresult[i]+"归还<span style='color:green'>成功</span>！<br/>";
//								}
//							else
//							{
//								System.out.println("第三步:修改流量交易订单失败！flowdealorders");
//								resp.getWriter().print("归还设备:"+strresult[i]+"<span style='color:red'>失败</span>！03<br/>");
//							}
						}else{
							System.out.println("第二步：修改设备订单表 设备状态为 已归还失败！devicedealorders");
							resultmsg=resultmsg+"归还设备:"+strresult[i]+"<span style='color:red'>失败</span>！02<br/>";
						}
					}
					else
					{
				        System.out.println("第一步：修改设备信息表状态失败！deviceInfo");
				        resultmsg=resultmsg+"归还设备:"+strresult[i]+"<span style='color:red'>失败</span>！01<br/>";
					}
				}
				else
				{
					    String type = deviceInfoSer.searchDeviceInfoTypeforSn(sn);
						System.out.println("此设备当前还没有交易记录,不存在归还操作！");
						resultmsg=resultmsg+"设备<span style='color:red'>"+strresult[i]+"</span>还没有交易记录,不存在归还操作！<br/>";
						DeviceInfo deviceinfodata = new DeviceInfo();
						deviceinfodata.setSN(sns);
						deviceinfodata.setRepertoryStatus("入库");//库存状态
						deviceinfodata.setDeviceStatus("可使用");//设备状态
						deviceinfodata.setModifyUserID(updateid);//修改人
						deviceinfodata.setModifyDate(date);//修改时间
						int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
						if(num>0){
							System.out.println("此设备还没有交易记录，当前为待使用状态，已改为可使用状态！");
						}
				}
			}

			else
			{
				System.out.println("没有搜索到"+sns+"设备记录！");
				resultmsg=resultmsg+"没有搜索到<span style='color:red'>"+sns+"</span>设备记录！<br/>";
			}
		}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//
		}


	}else{//输入单个设备号归还
		System.out.println("进入单个设备归还...");

		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
	    sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		String date = sdf.format(new Date());
//	       sn = "17215021000" + sn;
	       sn = Constants.SNformat(sn);

		int backresult = deviceInfoSer.getdevice(sn);
		System.out.println("查看设备信息表是否有此设备 的记录:"+backresult);

		int snCount = 0;

		try {
			snCount = flowDealOrdersSer.getIsFlowUseOrderForSn(sn);// 判断此SN是否有未完成的有效流量订单

		// 有则不允许使用归还操作
        if (snCount > 0)
        {
         System.out.println("此SN有未结束的有效流量订单！不允许归还此设备！");
         resp.getWriter().print("SN:" + sn + " 有未结束的有效流量订单！归还失败！");
        }else{

		if(backresult>0)
		{
			System.out.println("查询此设备是否有交易记录！");

			DeviceDealOrders devicedeal = new DeviceDealOrders();
			devicedeal.setSN(sn);//sn
			devicedeal.setDeallType("租用");//设备状态为租用
//			int devicedealnum = deviceInfoSer.searchDevice(devicedeal);//通过sn查询此设备的交易记录（是否有设备记录）
			//2015-9-25 11:49:08 （汪博讨论结果） 修改为该设备状态是否为【可使用】 ，如果为【可使用】状态不处理，【不是可使用】状态进行下面的归还操作

			int devicedealnum = deviceInfoSer.searchDevicekeshiyong(devicedeal);
			if(devicedealnum >0)
			{
				System.out.println("有此设备的交易记录，修改交易状态及设备状态！");
				//1.修改设备信息表设备状态 及库存状态
				DeviceInfo deviceinfodata = new DeviceInfo();
				deviceinfodata.setSN(sn);
				deviceinfodata.setRepertoryStatus("入库");//库存状态
				deviceinfodata.setDeviceStatus("可使用");//设备状态
				deviceinfodata.setModifyUserID(updateid);//修改人
				deviceinfodata.setModifyDate(date);//修改时间
				int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
				if(num>0){
			    	System.out.println("第一步：修改设备信息表状态成功！deviceInfo");
			    	//2.修改设备交易表信息
					DeviceDealOrders devicedeals = new DeviceDealOrders();//归还设备后修改设备订单表状态
					devicedeals.setSN(sn);
					devicedeals.setIfReturn("是");//是否归还
					devicedeals.setIfFinish("是");//订单是否完成
					devicedeals.setOrderStatus("已过期");//订单状态改为已过期

					devicedeals.setModifyUserID(updateid);//修改人ID
					SimpleDateFormat sdf2 = new SimpleDateFormat();// 格式化时间
				    sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
					devicedeal.setModifyDate(new DateUtils().parseDate(sdf2.format(new Date())));//修改日期

					int devicedealsnum = deviceInfoSer.updateDeviceDealOrders(devicedeals);//2.修改设备订单表 设备状态为 已归还
					if(devicedealsnum>0){
						System.out.println("第二步：修改设备订单表 设备状态为 已归还成功！devicedealorders");
						//3.修改流量订单表（数据服务）
						FlowDealOrders fdo=new FlowDealOrders();
						fdo.setOrderStatus("不可用");
						fdo.setSN(sn);

//						int three =
								try {
									flowDealOrdersSer.updateOrderStatusForSn(fdo);
								} catch (Exception e1) {
									logger.info("归还设备，操作流量订单失败！"+e1.getMessage());
									e1.printStackTrace();
								}
//						if(three>0){
							System.out.println("第三步:修改流量交易订单成功！flowdealorders");

								try//操作日志部分
								{
								AdminOperate admin = new AdminOperate();
								admin.setOperateID(UUID.randomUUID().toString());//id
								admin.setCreatorDate(date);//创建时间
								admin.setCreatorUserID(updateid);//创建人ID
								admin.setCreatorUserName(username);//创建人姓名
								admin.setOperateContent("归还设备 ,设备序列号："+sn);//操作内容
								admin.setOperateDate(date);//操作时间
								admin.setOperateMenu("设备管理>归还设备");//操作菜单
								admin.setOperateType("归还");//操作类型
								admin.setSysStatus(1);
								adminOperateSer.insertdata(admin);
								}catch (Exception e) {
									logger.debug(e.getMessage());
									e.printStackTrace();
								}
								System.out.println("修改设备交易表状态成功，设备归还成功！");
								resultmsg=resultmsg+"归还设备:"+sn+"<span style='color:green'>成功</span>！";
//						}else{
//							System.out.println("第三步:修改流量交易订单失败！flowdealorders");
//							resp.getWriter().print("归还设备:"+sn+"<span style='color:red'>失败</span>！03<br/>");
//						}

					}else{
						System.out.println("第二步：修改设备订单表 设备状态为 已归还失败！devicedealorders");
						resultmsg=resultmsg+"归还设备"+sn+"<span style='color:red'>失败</span>!02<br/>";
					}
				}
				else{
			        System.out.println("第一步：修改设备信息表状态失败！deviceInfo");
			        resultmsg=resultmsg+"归还设备"+sn+"<span style='color:red'>失败</span>!01<br/>";
				}
			}
			else
			{
				String type = deviceInfoSer.searchDeviceInfoTypeforSn(sn);
				if(type.equals("可使用")){
					System.out.println("没有搜索到<span style='color:red'>"+sn+"</span>记录，此设备信息或已被他人删除！");
					resultmsg=resultmsg+"此设备当前还没有交易记录,不存在归还操作！";
				}else{
					DeviceInfo deviceinfodata = new DeviceInfo();
					deviceinfodata.setSN(sn);
					deviceinfodata.setRepertoryStatus("入库");//库存状态
					deviceinfodata.setDeviceStatus("可使用");//设备状态
					deviceinfodata.setModifyUserID(updateid);//修改人
					deviceinfodata.setModifyDate(date);//修改时间
					int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
					if(num>0){
						System.out.println("此设备还没有交易记录，当前为待使用状态，已改为可使用状态！");
					}
				}
			}
		}
		else
		{
			System.out.println("没有搜索到此设备记录，此设备信息或已被他人删除！");
			resultmsg=resultmsg+"没有搜索到设备：<span style='color:red'>"+sn+"</span>的记录！";
		}
	}
} catch (Exception e2) {

			e2.printStackTrace();
		}
	}
	req.getSession().setAttribute("excelreverdevmsg",resultmsg);
	return "redirect:revertall";
	}


 public static String[][] getData(File file, int ignoreRows)
   throws FileNotFoundException, IOException {
  List result = new ArrayList();
  int rowSize = 0;
  BufferedInputStream in = new BufferedInputStream(new FileInputStream(
    file));
  // 打开HSSFWorkbook
  POIFSFileSystem fs = new POIFSFileSystem(in);
  HSSFWorkbook wb = new HSSFWorkbook(fs);
  HSSFCell cell = null;
  for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
   HSSFSheet st = wb.getSheetAt(sheetIndex);
   // 第一行为标题，不取
   for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
    HSSFRow row = st.getRow(rowIndex);
    if (row == null) {
     continue;
    }
    int tempRowSize = row.getLastCellNum() + 1;
    if (tempRowSize > rowSize) {
     rowSize = tempRowSize;
    }
    String[] values = new String[rowSize];
    Arrays.fill(values, "");
    boolean hasValue = false;
    for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
     String value = "";
     cell = row.getCell(columnIndex);
     if (cell != null) {
      // 注意：一定要设成这个，否则可能会出现乱码
      cell.setEncoding(HSSFCell.ENCODING_UTF_16);
      switch (cell.getCellType()) {
      case HSSFCell.CELL_TYPE_STRING:
       value = cell.getStringCellValue();
       break;
      case HSSFCell.CELL_TYPE_NUMERIC:
       if (HSSFDateUtil.isCellDateFormatted(cell)) {
        Date date = cell.getDateCellValue();
        if (date != null) {
         value = new SimpleDateFormat("yyyy-MM-dd")
           .format(date);
        } else {
         value = "";
        }
       } else {
        value = new DecimalFormat("0").format(cell
          .getNumericCellValue());
       }
       break;
      case HSSFCell.CELL_TYPE_FORMULA:
       // 导入时如果为公式生成的数据则无值
       if (!cell.getStringCellValue().equals("")) {
        value = cell.getStringCellValue();
       } else {
        value = cell.getNumericCellValue() + "";
       }
       break;
      case HSSFCell.CELL_TYPE_BLANK:
       break;
      case HSSFCell.CELL_TYPE_ERROR:
       value = "";
       break;
      case HSSFCell.CELL_TYPE_BOOLEAN:
       value = (cell.getBooleanCellValue() == true ? "Y"
         : "N");
       break;
      default:
       value = "";
      }
     }
     if (columnIndex == 0 && value.trim().equals("")) {
      break;
     }
     values[columnIndex] = rightTrim(value);
     hasValue = true;
    }

    if (hasValue) {
     result.add(values);
    }
   }
  }
  in.close();
  String[][] returnArray = new String[result.size()][rowSize];
  for (int i = 0; i < returnArray.length; i++) {
   returnArray[i] = (String[]) result.get(i);
  }
  return returnArray;
 }


 public static String rightTrim(String str) {
  if (str == null) {
   return "";
  }
  int length = str.length();
  for (int i = length - 1; i >= 0; i--) {
   if (str.charAt(i) != 0x20) {
    break;
   }
   length--;
  }
  return str.substring(0, length);
 }


}
