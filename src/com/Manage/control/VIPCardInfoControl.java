package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.PasswordView;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Int;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.security.krb5.internal.crypto.crc32;
import com.Manage.common.constants.Constants;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.randomUtil;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.VIPCardInfo;
import com.Manage.entity.common.SearchDTO;
import com.Manage.service.VIPCardInfoSer;
import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/VIPCardInfo")
public class VIPCardInfoControl extends BaseController {
	private final static String RETURNROOT_STRING = "WEB-INF/views/vip/";
	private Logger logger = LogUtil.getInstance(VIPCardInfoControl.class);
	/**
	 * 获取分页数据
	 *
	 * @author jiangxuecheng
	 * @date 2015-11-25
	 * @param searchDTO
	 * @param vipCardInfo
	 * @param response
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, VIPCardInfo vipCardInfo,
			HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		System.out.println(" 执行中");
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), vipCardInfo);
		String pagesString = vipCardInfoSer.getpageString(seDto);
		System.out.println(pagesString);
		try {
			response.getWriter().println(pagesString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/allVIPCard")
	public String allvipcard(Model model) {
		return RETURNROOT_STRING + "vip_list";

	}

	/**
	 * 返回编辑页面
	 *
	 * @author jiangxuecheng
	 * @param model
	 */
	@RequestMapping("/edit")
	public String edit(VIPCardInfo vipCardInfo, Model model) {
		VIPCardInfo vip = vipCardInfoSer.getvipcardinfobycardID(vipCardInfo);
		String preferentialType = vip.getPreferentialType();

		model.addAttribute("Model", vip);
		return RETURNROOT_STRING + "vip_edit";
	}

	@RequestMapping("/add")
	public String add(VIPCardInfo vipCardInfo, Model model) {
		VIPCardInfo vip = vipCardInfoSer.getvipcardinfobycardID(vipCardInfo);
		model.addAttribute("Model", vip);
		return RETURNROOT_STRING + "vip_add";
	}

	/**
	 * 保存增加
	 * @throws IOException
	 */
	@RequestMapping("/saveadd")
	public void saveadd(VIPCardInfo vipCardInfo,String liuliang,String huafei,String jiben,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		String batchNumber=DateUtils.getDate("yyyyMMddHHmm");//设置批次号
		String preferentialType1 = vipCardInfo.getPreferentialType();//优惠方式
		String biaoshima = randomUtil.getrandom(9, 0)+"";//设置标识码
		//标识码
		//身份，设备，充值 7
		//身份，设备           6
		//身份，          充值 5
		//身份，                  4
		//设备，	          充值 3
		//设备，                  2
		//充值      		  1
		/*if(preferentialType1.contains("身份") && preferentialType1.contains("设备") && preferentialType1.contains("充值")){
			biaoshima="7";
		}else if(preferentialType1.contains("身份") && preferentialType1.contains("设备")){
			biaoshima="6";
		}else if(preferentialType1.contains("身份") && preferentialType1.contains("充值")){
			biaoshima="5";
		}else if(preferentialType1.contains("身份")){
			biaoshima="4";
		}else if(preferentialType1.contains("设备") && preferentialType1.contains("充值")){
			biaoshima="3";
		}else if( preferentialType1.contains("设备")){
			biaoshima="2";
		}else if( preferentialType1.contains("充值")){
			biaoshima="1";
		}*/
		int hang=0;
		for (int k = 0; k < vipCardInfo.getVipcardCount(); k++) {
			String shijiancuo = new Date().getTime() + "";
			shijiancuo = shijiancuo.substring(6, shijiancuo.length());
			String random = randomUtil.getrandom(99, 11) + "";
			String cardID = biaoshima + shijiancuo + random;
			byte[] b = cardID.getBytes();
			CRC32 crc = new CRC32();
			crc.update(b);
			// 第一次加密的密码
			String password = crc.getValue() + "";
			if (password.length() < 10) {
				for (int t = 0; t < 10 - password.length(); t++) {
					password = "0" + password;
				}
			}
			// 使用控制符混合
			char[] contrlOperator = Constants.CONTROLOPERATOR.toCharArray();// QWERASDFG//1,
			char[] stringArr = password.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < stringArr.length; i++) {
				sb.append(stringArr[i]);
				if (i < contrlOperator.length) {
					sb.append(contrlOperator[i]);
				}
			}
			password = sb.toString();
			byte[] bb = password.getBytes();
			CRC32 c = new CRC32();
			c.update(bb);
			password = c.getValue() + "";
			vipCardInfo.setBatchNumber(batchNumber);//批次号
			vipCardInfo.setCardStatus("可用");// 卡状态
			vipCardInfo.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));// 创建时间
			vipCardInfo.setCreatorUserID(adminUserInfo.getUserID());// 创建用户ID
			vipCardInfo.setCreatorUserName(adminUserInfo.getUserName());
		   // vipCardInfo.setEndTime(DateUtils.getDatebyYear(1));//结束时间
		   // vipCardInfo.setStartTime(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));//开始时间
			vipCardInfo.setIsExchange("否");// 是否对换
			vipCardInfo.setIsMakeCard("否");// 是否制卡
			vipCardInfo.setSysStatus(true);
			vipCardInfo.setPassword(password);
			vipCardInfo.setCardID(cardID);
			if (vipCardInfo.getPassword().length() < 10) {
				for (int t = 0; t < 10 - vipCardInfo.getPassword().length(); t++) {
					password =password+"0";
				}
			}
			vipCardInfo.setPassword(password);
			String preferentialType = "";
			if(preferentialType1.contains("身份")){
				preferentialType=preferentialType+"1,";
			}else{
				preferentialType=preferentialType+"0,";
			}
			if(preferentialType1.contains("设备")){
				preferentialType=preferentialType+"1,";
			}else{
				preferentialType=preferentialType+"0,";
			}
			if(preferentialType1.contains("流量费用")){
				preferentialType=preferentialType+liuliang+",";
			} else{
				preferentialType=preferentialType+"0,";
			}
			if(preferentialType1.contains("话费")){
				preferentialType=preferentialType+huafei+",";
			}else{
				preferentialType=preferentialType+"0,";
			}
			if(preferentialType1.contains("基本")){
				preferentialType=preferentialType+jiben;
			} else{
				preferentialType=preferentialType+"0";
			}

			vipCardInfo.setPreferentialType(preferentialType);
			 hang = vipCardInfoSer.insertInfo(vipCardInfo);
		}
		JSONObject jsonResult = new JSONObject();
		if(hang>0){
			jsonResult.put("code", 1);
			jsonResult.put("msg", "添加成功");
			response.getWriter().println(jsonResult.toString());
		}else{
			jsonResult.put("code", -1);
			jsonResult.put("msg", "添加失败");
			response.getWriter().println(jsonResult.toString());
		}
	}
	/**
	 * 编辑保存
	 * @throws IOException
	 * @author jiangxuecheng
	 * @date 2015-11-24
	 */
	@RequestMapping("/editsave")
	public void editsave(VIPCardInfo vipCardInfo,String liuliang,String huafei,String jiben,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		String preferentialType1 = vipCardInfo.getPreferentialType();//优惠方式
		String preferentialType = "";
		if(preferentialType1.contains("身份")){
			preferentialType=preferentialType+"1,";
		}else{
			preferentialType=preferentialType+"0,";
		}
		if(preferentialType1.contains("设备")){
			preferentialType=preferentialType+"1,";
		}else{
			preferentialType=preferentialType+"0,";
		}
		if(preferentialType1.contains("流量费用")){
			preferentialType=preferentialType+liuliang+",";
		} else{
			preferentialType=preferentialType+"0,";
		}
		if(preferentialType1.contains("话费")){
			preferentialType=preferentialType+huafei+",";
		}else{
			preferentialType=preferentialType+"0,";
		}
		if(preferentialType1.contains("基本")){
			preferentialType=preferentialType+jiben;
		} else{
			preferentialType=preferentialType+"0";
		}
		vipCardInfo.setPreferentialType(preferentialType);
		vipCardInfo.setModifyUserID(adminUserInfo.getUserID());
		vipCardInfo.setModifyDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));

		int hang = vipCardInfoSer.editupdate(vipCardInfo);
		JSONObject jsonResult = new JSONObject();
		if(hang>0){
			jsonResult.put("code", 1);
			jsonResult.put("msg", "保存成功");
			response.getWriter().println(jsonResult.toString());
		}else{
			jsonResult.put("code", -1);
			jsonResult.put("msg", "保存失败");
			response.getWriter().println(jsonResult.toString());
		}
	}

	/**
	 * 根据cardID删除VIP卡
	 * @throws IOException
	 */
	@RequestMapping("/del")
	public void del(String cardID ,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		int hang = vipCardInfoSer.del(cardID);
		if(hang>0){
			response.getWriter().println("删除成功");
		}
	}

	/**
	 * @deprecated 批量编辑
	 * @author jiangxuecheng
	 * @throws IOException
	 * @throws
	 * @date 2015-11-24
	 *
	 */
	@RequestMapping("/batchedit")
	public void batchedit(String jine,VIPCardInfo vipCardInfo,String liuliang,String huafei,String jiben,
			HttpServletRequest request, HttpServletResponse response) throws IOException    {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String preferentialType1 = new String(vipCardInfo.getPreferentialType().getBytes("ISO-8859-1"),"utf-8");//优惠方式;
		String cardStatus = new String(vipCardInfo.getCardStatus().getBytes("ISO-8859-1"),"utf-8");
		String isMakeCard = new String(vipCardInfo.getIsMakeCard().getBytes("ISO-8859-1"),"utf-8");
		String remark = new String(vipCardInfo.getRemark().getBytes("ISO-8859-1"),"utf-8");
		vipCardInfo.setCardStatus(cardStatus);
		vipCardInfo.setIsMakeCard(isMakeCard);
		vipCardInfo.setRemark(remark);
		String[] cardIDs = vipCardInfo.getCardID().split(",");

		int hang=0;
		for(int i=0;i<cardIDs.length;i++){
			AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
					.getAttribute("User");

			String preferentialType = "";
			if(preferentialType1.contains("身份")){
				preferentialType=preferentialType+"1,";
			}else{
				preferentialType=preferentialType+"0,";
			}
			if(preferentialType1.contains("设备")){
				preferentialType=preferentialType+"1,";
			}else{
				preferentialType=preferentialType+"0,";
			}



			if(preferentialType1.contains("流量费用")){
				preferentialType=preferentialType+liuliang+",";
			} else{
				preferentialType=preferentialType+"0,";
			}
			if(preferentialType1.contains("话费")){
				preferentialType=preferentialType+huafei+",";
			}else{
				preferentialType=preferentialType+"0,";
			}
			if(preferentialType1.contains("基本")){
				preferentialType=preferentialType+jiben;
			} else{
				preferentialType=preferentialType+"0";
			}
			vipCardInfo.setPreferentialType(preferentialType);
			vipCardInfo.setModifyUserID(adminUserInfo.getUserID());
			vipCardInfo.setModifyDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			vipCardInfo.setCardID(cardIDs[i]);
		    hang = hang+vipCardInfoSer.editupdate(vipCardInfo);
		}
		JSONObject jsonResult = new JSONObject();
		if(hang==cardIDs.length){
			jsonResult.put("code", 1);
			jsonResult.put("msg", "编辑成功");
			response.getWriter().println(jsonResult.toString());
		}else{
			jsonResult.put("code", -1);
			jsonResult.put("msg", "编辑失败");
			response.getWriter().println(jsonResult.toString());
		}
	}


	/**
	 * @deprecated跳转到vip_view.jsp页面
	 * @author jiangxuecheng
	 * @date 2015-11-26
	 */
	@RequestMapping("skipVIPview")
	public String skipVIPview(	VIPCardInfo info , Model model,HttpServletRequest request,HttpServletResponse response){
		//查询数据库
		VIPCardInfo vipCardInfo = vipCardInfoSer.getvipcardinfobycardID(info);
		model.addAttribute("Model", vipCardInfo);
		return RETURNROOT_STRING+"vip_view";
	}

	/***
	 * @deprecated 导出数据到excel
	 * @author jiangxuecheng
	 * @throws UnsupportedEncodingException
	 * @date 2015-11-27
	 *
	 */
	@RequestMapping("/excelExport")
	public void excelExport(HttpServletRequest request,VIPCardInfo vipCardInfo,SearchDTO searchDTO,//export
            HttpServletResponse response) throws UnsupportedEncodingException{
		String cardStatus = new String(vipCardInfo.getCardStatus().getBytes("ISO-8859-1"),"utf-8");
		String isExchange = new String(vipCardInfo.getIsExchange().getBytes("ISO-8859-1"),"utf-8");
		vipCardInfo.setCardStatus(cardStatus);
		vipCardInfo.setIsExchange(isExchange);
		// 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("Sheet1");
       // sheet.setDefaultRowHeight((short)100);
        sheet.setDefaultColumnWidth((short)20);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        row.setHeight((short) 600);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        // 设置字体
        HSSFFont headfont = wb.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 11);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        //设轩样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
        style.setFont(headfont);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("卡号");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("优惠方式");
        cell.setCellStyle(style);

        cell = row.createCell((short) 2);
        cell.setCellValue("批次号");
        cell.setCellStyle(style);

        cell = row.createCell((short) 3);
        cell.setCellValue("有效期开始时间");
        cell.setCellStyle(style);

        cell = row.createCell((short) 4);
        cell.setCellValue("有效期结束时间");
        cell.setCellStyle(style);

        cell = row.createCell((short) 5);
        cell.setCellValue("是否兑换");
        cell.setCellStyle(style);

        cell = row.createCell((short) 6);
        cell.setCellValue("兑换时间");
        cell.setCellStyle(style);

        cell = row.createCell((short) 7);
        cell.setCellValue("兑换手机号");
        cell.setCellStyle(style);

        cell = row.createCell((short) 8);
        cell.setCellValue("卡状态");
        cell.setCellStyle(style);

        cell = row.createCell((short) 9);
        cell.setCellValue("是否已制卡");
        cell.setCellStyle(style);

        cell = row.createCell((short) 10);
        cell.setCellValue("创建人");
        cell.setCellStyle(style);

        cell = row.createCell((short) 11);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);

        searchDTO.setObj(vipCardInfo);
        List<VIPCardInfo> vipCardInfos = vipCardInfoSer.getexecldata(searchDTO);
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        for (int i = 0; i < vipCardInfos.size(); i++) {
        	 // 第四步，创建单元格，并设置值
	        row = sheet.createRow((int) i+1);
	        row.setHeight((short) 450);
	        HSSFCell cell1 = row.createCell((short) 0);
            cell1.setCellValue(vipCardInfos.get(i).getCardID());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 1);
	        String preferentialType1 = vipCardInfos.get(i).getPreferentialType();
			String preferentialType = "";
			if(StringUtils.isNotBlank(preferentialType1)){
				String[] preArr = preferentialType1.split(",");
				if(preArr.length>=3){
					if("1".equals(preArr[0])){
						preferentialType=preferentialType+"	VIP身份";
					}
					if("1".equals(preArr[1])){
						preferentialType=preferentialType+",赠送设备";
					}
					if(!"0".equals(preArr[2])){
						preferentialType=preferentialType+",充值"+preArr[2];
					}
				}
			}
			if(preferentialType.indexOf(",")==0){
				preferentialType=preferentialType.substring(1,preferentialType.length());
			}
            cell1.setCellValue(preferentialType);
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 2);
            cell1.setCellValue(vipCardInfos.get(i).getBatchNumber());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 3);
            cell1.setCellValue(vipCardInfos.get(i).getStartTime());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 4);
            cell1.setCellValue(vipCardInfos.get(i).getEndTime());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 5);
            cell1.setCellValue(vipCardInfos.get(i).getIsExchange());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 6);
            cell1.setCellValue(vipCardInfos.get(i).getExchangeTime());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 7);
            cell1.setCellValue(vipCardInfos.get(i).getExchangeIphone());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 8);
            cell1.setCellValue(vipCardInfos.get(i).getCardStatus());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 9);
            cell1.setCellValue(vipCardInfos.get(i).getIsMakeCard());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 10);
            cell1.setCellValue(vipCardInfos.get(i).getCreatorUserName());
	        cell1.setCellStyle(style1);

	        cell1 = row.createCell((short) 11);
            cell1.setCellValue(vipCardInfos.get(i).getCreatorDate());
	        cell1.setCellStyle(style1);

		}
      try {
			//DownLoadUtil.execlExpoprtDownload(begindate+"到"+enddate+"各个国家接入成功设备统计.xls",wb, request, response);
			DownLoadUtil.execlExpoprtDownload("VIP卡信息.xls",wb, request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
