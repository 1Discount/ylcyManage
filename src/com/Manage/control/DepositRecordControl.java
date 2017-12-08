package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DepositRecord;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/device/deposit")
public class DepositRecordControl extends BaseController {
	private Logger logger = LogUtil.getInstance(DepositRecordControl.class);

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/getPageRecord")
	public void getPageRecord(SearchDTO searchDTO, DepositRecord info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = depositRecordSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 退押金申请记录 详情及增加/修改反馈
	 *
	 * @param recordID
	 * @param model
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/edit")
	public String editDeposit(@RequestParam String recordID, Model model, HttpServletRequest req, HttpServletResponse resp){
		try {
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");

		DepositRecord record = depositRecordSer.getById(recordID);
		model.addAttribute("Model", record);
		return "WEB-INF/views/deviceinfo/deposit_record_edit";
	}

	/**
	 * 保存修改备注/反馈
	 *
	 * @param formInfo
	 * @param model
	 * @param req
	 * @param response
	 */
	@RequestMapping("/save")
	public void saveActionDeposit(DepositRecord formInfo, Model model, HttpServletRequest req, HttpServletResponse response){
		try {
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");

		JSONObject jsonResult = new JSONObject();
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		DepositRecord info = new DepositRecord();
		info.setModifyUserID(adminUserInfo.getUserID());
		info.setRecordID(formInfo.getRecordID());
		info.setRemark(formInfo.getRemark());

		if (depositRecordSer.updateInfo(info)) {
			try {
				// response.getWriter().println("成功保存!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存!");
				response.getWriter().println(jsonResult.toString());

				try {
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());//id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
					admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
					//admin.setOperateDate(date);//操作时间
					//admin.setSysStatus(1);

					admin.setOperateContent("修改了退押金申请反馈, 记录ID为: " + info.getRecordID() + " 客户名称: " + info.getCustomerName());
					admin.setOperateMenu("设备管理>归还设备");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		} else {
			jsonResult.put("code", -1);
			jsonResult.put("msg", "保存出错, 请重试!");
			try {
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 退押金页面
	 *
	 * @param recordID
	 * @param model
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/withdraw")
	public String withdraw(@RequestParam String recordID, Model model, HttpServletRequest req, HttpServletResponse resp){
		try {
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");

		DepositRecord record = depositRecordSer.getById(recordID);
		model.addAttribute("Model", record);

		model.addAttribute("WithdrawView", true);
		return "WEB-INF/views/deviceinfo/deposit_record_edit";
	}

//	@RequestMapping("/applyDeposit")
//	public void applyDeposit(DepositRecord formInfo, HttpServletResponse response) throws IOException {
//		response.setCharacterEncoding("utf-8");
//
//		JSONObject jsonResult = new JSONObject();
//		// 目前快递单号必须
//		if (StringUtils.isBlank(formInfo.getOrderID()) || StringUtils.isBlank(formInfo.getExpressNum())) {
//			jsonResult.put("code", -1);
//			jsonResult.put("msg", "请提供订单ID和快递单号!"); // 这个系给开发者的信息,对终端客户可以更友好一点
//			response.getWriter().println(jsonResult.toString());
//			return;
//		}
//
//		// 首先查询此订单包含的租用设备信息
//		DeviceDealOrders queryInfo = new DeviceDealOrders();
//		queryInfo.setOrderID(formInfo.getOrderID());
//		queryInfo.setDeallType("租用");
//		List<DeviceDealOrders> dorders = ordersSer.getDeviceOrdersByFilter(queryInfo);
//		/** 注意一个说明：当前，只要系租赁的订单，则只有一个设备。购买的订单中才有可能两个设备以上 **/
//		if (null == dorders || dorders.size() == 0) {
//			jsonResult.put("code", -2);
//			jsonResult.put("msg", "此订单无租用设备!");
//			response.getWriter().println(jsonResult.toString());
//			return;
//		}
//		double depositTotal = 0;
//		for (DeviceDealOrders item : dorders) {
//			depositTotal += Double.valueOf(item.getDealAmount());
//		}
//		// 这里可内部判断是否与传递过来的押金总额相同, 若不同则可尝试适当提醒终端客户或者只需要把
//		// 后面生成的实际押金总额给客户即可
//
//		// 开始生成押金记录
//		CustomerInfo customerinfo = (CustomerInfo) getSession().getAttribute("User");
//		DepositRecord info = new DepositRecord();
//		info.setRecordID(UUID.randomUUID().toString());
//		info.setOrderID(formInfo.getOrderID());
//
//		// MARKS: 这里按当前的情况，租赁订单只有一个设备
//		info.setSN(dorders.get(0).getSN());
//
//		info.setCustomerID(customerinfo.getCustomerID());
//		info.setCustomerName(customerinfo.getCustomerName());
//		info.setPhone(customerinfo.getPhone());
//		info.setDealAmount(depositTotal);
//		info.setStatus("等待处理");
//		info.setExpressName(formInfo.getExpressName());
//		info.setExpressNum(formInfo.getExpressNum());
//		info.setComment(formInfo.getComment());
//		info.setShow1ImgSrc(formInfo.getShow1ImgSrc());
//		info.setShow2ImgSrc(formInfo.getShow2ImgSrc());
//		info.setShow3ImgSrc(formInfo.getShow3ImgSrc());
//		info.setShow4ImgSrc(formInfo.getShow4ImgSrc());
//		info.setShow5ImgSrc(formInfo.getShow5ImgSrc());
//		info.setCreatorUserID(customerinfo.getCustomerID());
//		info.setCreatorDate(DateUtils.formatDateTime(new Date()));
//		info.setSysStatus(1);
//
//		// TODO: 押金记录表已添加唯一性约束在 orderID 上. 所以这里可以具体处理这一类错误 而
//		// 不仅仅系简单返回数据库错误
//		if (depositRecordSer.insertInfo(info)) {
//			jsonResult.put("code", 0);
//			jsonResult.put("msg", "退押金申请成功, 请等待处理");
//			response.getWriter().println(jsonResult.toString());
//		} else {
//			jsonResult.put("code", -3);
//			jsonResult.put("msg", "数据库操作失败, 请重试!");
//			response.getWriter().println(jsonResult.toString());
//		}
//
//	}
	
	/**
	 * 退款时添加退款备注
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	@RequestMapping("/updateRefundRemarks")	
	public void updateRefundRemarks(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		DepositRecord dep = new DepositRecord();
		dep.setRemark(java.net.URLDecoder.decode(req.getParameter("remarks"),"UTF-8"));
		dep.setAliPayTradeNo(req.getParameter("aliPayTradeNo"));
		dep.setWIDbatch_no(req.getParameter("WIDbatch_no"));
		int count = 0;
		try {
			count = depositRecordSer.updateRefundRemarks(dep);
			if(count>0){
				resp.getWriter().print("1");
			}else{
				resp.getWriter().print("写入备注信息时出错，退款失败稍后请重试！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().print("写入备注信息时出错，退款失败稍后请重试！");
		}
	}
	
	
	
	
	
	
	
}