package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.alipay.config.AlipayConfig;
import com.Manage.common.alipay.util.AlipaySubmit;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DepositRecord;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Refund;
import com.Manage.entity.common.SearchDTO;

import java.util.*;
import com.Manage.common.alipay.util.*;
import com.Manage.common.alipay.config.*;

@Controller
@RequestMapping("/refund")
public class RefundControl extends BaseController{
	
	/**
	 * 退款
	 * @return
	 */
	@RequestMapping("/list")
	public String refundpage(){
		return "WEB-INF/views/orders/Refund_list";
	}
	
//	@RequestMapping("backMoneyEnd")
//	public String backMoneyEnd(){
//		return "WEB-INF/views/orders/backMoneyEnd";
//	}
	
	@RequestMapping("alipayapi")
	public String alipayapi(){
		return "WEB-INF/views/orders/alipayapi";
	}
	
	@RequestMapping("alipayapituikuan")
	public String alipayapituikuan(){
		return "WEB-INF/views/orders/alipayapi_tuikuan";
	}
	
	@RequestMapping("alipayindex")
	public String alipayindex(){
		return "WEB-INF/views/orders/alipay_index";
	}
	/**
	 * 退款
	 * @param searchDTO
	 * @param refund
	 * @param response
	 * @param req
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/listPage")
	public void datapage(SearchDTO searchDTO,Refund refund,HttpServletResponse response,HttpServletRequest req) throws UnsupportedEncodingException{
		response.setContentType("application/json;charset=UTF-8");
		req.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		System.out.println("---"+searchDTO.getCurPage()+"------"+searchDTO.getPageSize());
		SearchDTO seDto=new SearchDTO(searchDTO.getCurPage(),searchDTO.getPageSize(),searchDTO.getSortName(),searchDTO.getSortOrder(),refund);
		String jsonString=refundSer.getpageString(seDto);
		
		try {
			if(refund.getRefundStatus()==null){
				jsonString = refundSer.getpageString(seDto);
			}else if(refund.getRefundStatus()!=null){
				if(refund.getRefundStatus().equals("--所有状态--")){
					refund.setRefundStatus(null);
					jsonString = refundSer.getpageString(seDto);
				}else{
					jsonString = refundSer.getpageString(seDto);
				}
			}
				response.getWriter().println(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/** 退押金回调  
	 * @throws IOException **/
	@RequestMapping("/backMoneyEnd")
	public void comebackRefund(HttpServletResponse response,HttpServletRequest request) throws IOException{
		System.out.println("========================进入退押金回调方法========================");
		///////////////////////////////////////////////////
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//批次号
		String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");
		//批量退款数据中转账成功的笔数
		String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8");
		//批量退款数据中的详细信息
		String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"),"UTF-8");

		if(AlipayNotify.verify(params)){//验证成功
			AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
			DepositRecord dep = new DepositRecord();
			dep.setWIDbatch_no(batch_no);
			dep.setModifyUserID(adminUserInfo.userID);
			int count = 0;
			try {
				count = depositRecordSer.updateRefundbackEnd(dep);
				if(count>0){
					System.out.println("修改退款状态为：退款成功，结束！操作成功");
					response.getWriter().println("success");//请不要修改或删除
				}else{
					System.out.println("修改失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("修改退款状态为“退款成功”时出错了！");
			}
		}else{//验证失败
			response.getWriter().println("fail");
		}
	}  
	
	
	/** 退款回调  
	 * @throws IOException **/
	@RequestMapping("/backMoneyEndtuikuan")
	public void backMoneyEndtuikuan(HttpServletResponse response,HttpServletRequest request) throws IOException{
		System.out.println("========================进入退款回调方法========================");
		///////////////////////////////////////////////////
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//批次号
		String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");
		//批量退款数据中转账成功的笔数
		String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8");
		//批量退款数据中的详细信息
		String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"),"UTF-8");

		if(AlipayNotify.verify(params)){//验证成功
			AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
			Refund re = new Refund();
			re.setWIDbatch_no(batch_no);
			re.setRefundMoneyPeople(adminUserInfo.userID);
			int count = 0;
			try {
				count = refundSer.updateRefundbackEnd(re);
				if(count>0){
					System.out.println("修改退款状态为：退款成功，结束！操作成功");
					response.getWriter().println("success");//请不要修改或删除
				}else{
					System.out.println("修改失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("修改退款状态为“退款成功”时出错了！");
			}
		}else{//验证失败
			response.getWriter().println("fail");
		}
		
		
	}  
	
 
	/**
	 * 退款前写入备注和批次号
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/updateRefundRemarks")	
	public void updateRefundRemarks(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		System.out.println("=======================退款前写入备注和批次号====");
		Refund re = new Refund();
		re.setRemark(java.net.URLDecoder.decode(req.getParameter("remarks"),"UTF-8"));
		re.setAliPayTradeNo(req.getParameter("aliPayTradeNo"));
		re.setWIDbatch_no(req.getParameter("WIDbatch_no"));
		int count = 0;
		try {
			count = refundSer.updateRefundRemarks(re);
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