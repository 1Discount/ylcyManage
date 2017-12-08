package com.Manage.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.restlet.data.CookieSetting;
import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.Manage.common.constants.RestConstants;
import com.Manage.common.util.BeanConvertor;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.service.AcceptOrderSer;
import com.Manage.service.AdminOperateSer;
import com.Manage.service.AdminUserInfoSer;
import com.Manage.service.AfterSaleInfoSer;
import com.Manage.service.BackgroundCheckSer;
import com.Manage.service.CountryInfoSer;
import com.Manage.service.CouponRelationshipSer;
import com.Manage.service.CouponSer;
import com.Manage.service.CustomerInfoSer;
import com.Manage.service.DepositRecordSer;
import com.Manage.service.DevMessageSer;
import com.Manage.service.DeviceDealOrdersSer;
import com.Manage.service.DeviceFlowSer;
import com.Manage.service.DeviceInfoSer;
import com.Manage.service.DeviceJumpInfoSer;
import com.Manage.service.DeviceLogsSer;
import com.Manage.service.DictionarySer;
import com.Manage.service.DistributorMenuGroupInfoSer;
import com.Manage.service.DistributorMenuInfoSer;
import com.Manage.service.DistributorRoleInfoSer;
import com.Manage.service.DistributorRoleToMenuSer;
import com.Manage.service.DistributorSer;
import com.Manage.service.EnterpriseSer;
import com.Manage.service.EquipLogsSer;
import com.Manage.service.ExchangeKeySer;
import com.Manage.service.ExchangeRecordSer;
import com.Manage.service.FlowDealOrdersSer;
import com.Manage.service.FlowPlanInfoSer;
import com.Manage.service.GStrenthDataSer;
import com.Manage.service.InvitationSer;
import com.Manage.service.IpPhoneClientSer;
import com.Manage.service.JizhanInfoSer;
import com.Manage.service.MenuGroupInfoSer;
import com.Manage.service.MenuInfoSer;
import com.Manage.service.OperatorInfoSer;
import com.Manage.service.OrdersInfoSer;
import com.Manage.service.PushMessageDetailsSer;
import com.Manage.service.PushMessageInfoSer;
import com.Manage.service.RefundSer;
import com.Manage.service.RoamingSIMInfoSer;
import com.Manage.service.RoleInfoSer;
import com.Manage.service.RoleToMenuSer;
import com.Manage.service.SIMInfoSer;
import com.Manage.service.SIMRechargeBillSer;
import com.Manage.service.SIMServerSer;
import com.Manage.service.ShipmentSer;
import com.Manage.service.SubmitOrdersSer;
import com.Manage.service.TDMInfoSer;
import com.Manage.service.UpgradeFileInfoSer;
import com.Manage.service.VIPCardInfoSer;
import com.Manage.service.VendorDeviceBindKeySer;
import com.Manage.service.VirtualSIMInfoSer;
import com.Manage.service.VpnInfoSer;
import com.Manage.service.WorkFlowSer;
import com.Manage.service.WxBindDeviceSer;
import com.Manage.service.SocketClient.CommandSer;
import com.Manage.service.SocketClient.CommandSer2;

@Controller
public class BaseController implements ControlCommon {

	protected static final Logger LOGGER = LogUtil.getDefaultInstance();
	@Autowired
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected static String separate;

	/*********************************** 用户和权限 ***************************************/
	@Autowired
	AdminUserInfoSer adminuserinfoser; // 用户表service
	@Autowired
	InvitationSer invitationSer;// 邀请注册表service
	@Autowired
	MenuGroupInfoSer menuGroupInfoSer; // 菜单组service
	@Autowired
	MenuInfoSer menuInfoSer; // 菜单service
	@Autowired
	RoleInfoSer roleInfoSer; // 角色service
	@Autowired
	RoleToMenuSer roleToMenuSer;//角色菜单映射service
	
	@Autowired
	DistributorMenuGroupInfoSer distributorMenuGroupInfoSer; // 菜单组service
	@Autowired
	DistributorMenuInfoSer distributorMenuInfoSer; // 菜单service
	@Autowired
	DistributorRoleInfoSer distributorRoleInfoSer; // 角色service
	@Autowired
	DistributorRoleToMenuSer distributorRoleToMenuSer;//角色菜单映射service
	
	@Autowired
	DeviceInfoSer deviceInfoSer;

	/*********************************** 流量套餐 ***************************************/
	@Autowired
	FlowPlanInfoSer flowPlanInfoSer; // 套餐信息service
	@Autowired
    CountryInfoSer countryInfoSer; // 国家信息service

	/*********************************** SIM卡管理 ***************************************/
	@Autowired
	VpnInfoSer vpnInfoSer;//VPV service
	@Autowired
	SIMServerSer simServerSer; //SIM卡服务器service
	@Autowired
	SIMInfoSer simInfoSer; //SIM卡service
	@Autowired
	RoamingSIMInfoSer roamingSimInfoSer; //漫游SIM卡service
	@Autowired
	SIMRechargeBillSer simRechargeBillSer; //SIM卡充值记录service
	@Autowired
	OperatorInfoSer operatorInfoSer; //运营商信息service

	/*********************************** 客户 ***************************************/
	@Autowired
	CustomerInfoSer customerInfoSer; // 客户
	@Autowired
	DistributorSer distributorSer; // 渠道商

	/*********************************** 订单 ***************************************/
	@Autowired
	protected OrdersInfoSer ordersInfoSer; // 订单表
	@Autowired
	protected FlowDealOrdersSer flowDealOrdersSer; // 流量交易表
	@Autowired
	protected DeviceDealOrdersSer deviceDealOrdersSer;// 设备交易表
	@Autowired
	protected AcceptOrderSer acceptOrderSer;//接单表

	//***************************************************
	@Autowired
	DictionarySer dictionarySer; //数据字典

	@Autowired
	AdminOperateSer adminOperateSer; //用户操作记录

	//*************设备日志************************
	@Autowired
	DeviceLogsSer deviceLogsSer;

	@Autowired
	CommandSer commandSer;

	@Autowired
	CommandSer2 commandSer2;

	@Autowired
	EquipLogsSer equipLogsSer;

	@Autowired
	DepositRecordSer depositRecordSer; // 押金申请记录

	@Autowired

	WxBindDeviceSer wxBindDeviceSer; // 微信设备SN绑定关系表

	@Autowired
	VirtualSIMInfoSer virtualSIMInfoSer; // 虚拟SIM卡

	@Autowired
	ExchangeKeySer exchangeKeySer;// 兑换码
	@Autowired
	ExchangeRecordSer exchangeRecordSer;// 兑换记录

	@Autowired
	RefundSer refundSer;//退款

	@Autowired
	CouponSer couponSer;//优惠劵

	@Autowired
	GStrenthDataSer gStrenthDataSer;//基站经纬度查询

	@Autowired
	CouponRelationshipSer couponRelationshipSer;//优惠券关系表

	@Autowired
	VIPCardInfoSer vipCardInfoSer;//vip优惠卡

	@Autowired
	ShipmentSer ShipmentSer;//vip优惠卡

	@Autowired
	WorkFlowSer workFlowSer;//vip优惠卡

	@Autowired
	AfterSaleInfoSer afterSaleInfoSer;//售后服务记录表


	@Autowired
	IpPhoneClientSer ipPhoneClientSer;

	@Autowired
	SubmitOrdersSer submitOrdersSer;//提工单表


	@Autowired
    VendorDeviceBindKeySer vendorDeviceBindKeySer;// 设备绑定码

	@Autowired
	DevMessageSer devMessageSer;//设备短信记录表

	@Autowired
	DeviceFlowSer deviceFlowSer;//新设备流量表

	@Autowired
	EnterpriseSer enterpriseSer;//企业用户表

	@Autowired
	BackgroundCheckSer backgroundCheckSer; //后台自检Service

	@Autowired
	JizhanInfoSer jizhanInfoSer; //基站信息Service
	
	@Autowired
	PushMessageInfoSer pushMessageInfoSer; //推送消息
	
	@Autowired
	PushMessageDetailsSer pushMessageDetailsSer;//推送信息详细
	
	@Autowired
	UpgradeFileInfoSer upgradeFileInfoSer;//升级文件
	
	@Autowired
	TDMInfoSer tdmInfoSer;//tdm
	
	@Autowired
	DeviceJumpInfoSer deviceJumpInfoSer;//
	/**
	 * 以下四个参数是由jquery分页传过来的,不能随意更改
	 */
	protected Integer page; // 当前页
	protected Integer rows; // 页大小
	protected String sort; // 排序字段
	protected String order; // 排序方式

	protected String filters; // 查询参数

	private static String CONTENT_TYPE_JSON = "text/json; charset=UTF-8";
	private static String CONTENT_TYPE_TEXT = "text/text; charset=UTF-8";

	protected static final String SEND_SUCCESS = "suc";
	protected static final String SEND_TRUE = "true";
	protected static final String SEND_FAIL = "fail";
	protected static final String SEND_EXCEPTION = "exception";

	public HttpSession getSession() {
		return request.getSession();
	}

	/**
	 * 输出流,用户异步请求.
	 *
	 * @param outString
	 * @return
	 */
	public boolean outprintln(String outString) {
		try {
			response.getWriter().println(outString);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 发送对象数据
	 *
	 * @param obj
	 */
	protected void sendObjectData(Object obj) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(obj);
			sendJsonData(jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送集合数据
	 *
	 * @param list
	 */
	protected void sendListData(List<?> list) {
		try {
			JSONArray jsonArray = JSONArray.fromObject(list);
			sendJsonData(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送JSON数据
	 *
	 * @param str
	 */
	protected void sendJsonData(String str) {
		try {
			response.setContentType(CONTENT_TYPE_JSON);
			PrintWriter out = response.getWriter();
			System.out.println(str);
			out.print(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送文本数据
	 *
	 * @param str
	 */
	protected void sendTextData(String str) {
		try {
			response.setContentType(CONTENT_TYPE_TEXT);
			PrintWriter out = response.getWriter();
			System.out.println(str);
			out.print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取查询参数集合
	 *
	 * @param filters
	 * @return
	 */
	protected Map<String, Object> getFilterMap(String filters) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (filters == null || "".equals(filters)) {
			return map;
		}
		JSONObject obj = JSONObject.fromObject(filters);
		for (Object key : obj.keySet()) {
			if (!"".equals(obj.getString(key.toString()))) {
				try {
					String value = String
							.valueOf(obj.getString(key.toString()));
					map.put(key.toString(), value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 获取浏览器IP端口地址
	 * @return String
	 */
	public String getRemoteUrl() {
		return request.getLocalAddr() + ":" + request.getLocalPort();
	}



	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}


	/**
	 * 整合 website 项目的 restlet 接口相关
	 *
	 */
	protected static String resturlRoot="";
	/**
	 *
	 * @return 形如: http://127.0.0.1:8080/easy2go
	 */
	protected String getUrlRoot() {
		return request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath();
	}
	/**
	 * 有别于官网
	 * @return
	 */
	protected String getRestUrlRoot() {
//		return request.getScheme() + "://" + request.getServerName() + ":"
//				+ request.getServerPort() + request.getContextPath() + Constants.getConfig("web.restEndpoint");
		return RestConstants.URL_BASE;
	}

	@Override
	public boolean resultValidate(String jString) throws Exception{
		// TODO Auto-generated method stub
		JSONObject object = JSONObject.fromObject(jString);
		if (object.getString("error") == null
				|| object.getString("msg") == null) {
			return false;
		}
		if (object.containsKey("data")) {
			if (object.getString("data").indexOf("{") == 0) {
				try {
					JSONObject obj = JSONObject.fromObject(object.getString("data"));
				} catch (Exception e) {
					// TODO: handle exception
					return false;
				}
			} else if (object.getString("data").indexOf("[") == 0) {
				try {
					JSONArray arrobj = JSONArray.fromObject(object
							.getString("data"));
					if (arrobj.size() == 0) {
						return false;
					}
				} catch (Exception e) {
					// TODO: handle exception
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 从内部发出的REST请求, 带上一个简单的cookie标识, 以使REST端能区分从APP或内部发出的请求, 可能作相应处理
	 *
	 * TO DO: 这里应该有别于官网! 已完成修改, 只需要添加一个有效\有别于正常customerID的值就可
	 *
	 * @return
	 */
	private CookieSetting generateCookieForRest() {
//		CustomerInfo customerInfo = (CustomerInfo) getSession().getAttribute("User");
//		if (null != customerInfo && null != customerInfo.getCustomerID()) {
//			CookieSetting cookie1 = new CookieSetting(RestConstants.Common.REST_FROM_SERVLET_COOKIE_NAME,
//					customerInfo.getCustomerID());
//	        cookie1.setDomain(request.getServerName());
//	        cookie1.setPath("/");
//	        cookie1.setMaxAge(-1);
//	        //client.getRequest().getCookies().add(cookie1);
//	        return cookie1;
//		}

		// FIXED: 之前一旦前面判断没session User时, 就不添加这个cookie, 这不合实际. 一些接口
		// 并不需要用户登录, 但也要标识系来自内部的调用. 但必须同时处理好当需要用户登录的接口而
		// 未登录前就请求了的情形. 例如 CustomerInfoControl 中的一些接口, 适当更改
		// [1103] ahming notes: 使用 api auth 认证后 这里也必须有. 注意, 已确认, 有效的cookie不能设置
		// 为空的值"". CustomerInfoControl相关接口处引用这个cookie已作了长度判断, 所以那里也不需要改动了
		CookieSetting cookie1 = new CookieSetting(RestConstants.Common.REST_FROM_SERVLET_COOKIE_NAME,
				"inner");
        cookie1.setDomain(request.getServerName());
        cookie1.setPath("/");
        cookie1.setMaxAge(-1);
        //client.getRequest().getCookies().add(cookie1);
        return cookie1;

	}

	@Override
	public String postRequestForm(String url, Form form) throws Exception {
		ClientResource client =new ClientResource(url);

		CookieSetting cookie = generateCookieForRest();
		if (null != cookie) {
			client.getRequest().getCookies().add(cookie);
		}

		String info=client.post(form.getWebRepresentation()).getText();

		// 测试这里是否能从REST端获取/传递 cookie. 目前的结果系不能, 后续再作处理
		//
		// 例如登录login, 成功登录后, 这里并得不到期望的cookie 因而这样也无法把 cookie 再返回到官网客户端
		// 即使不需要返回官网客户端(因为有session标识用户会话), 但后续controller再执行 REST 调用时, 必须
		// 提供 cookie, 否则需要登录才能执行的接口无法正常调用
		// 考虑 REST 端的请求或回应添加处理器(拦截器?filter?)去持久化?或正确设置使用 ClientResource 能接收?
		//
		//Series<org.restlet.data.Cookie> clientCookies = client.getCookies();
		//javax.servlet.http.Cookie[] requestCookies = request.getCookies();

		return info;
	}

	@Override
	public String postRequestForm(String url, Object o) throws Exception{
		// TODO Auto-generated method stub
		ClientResource client =new ClientResource(url);

		CookieSetting cookie = generateCookieForRest();
		if (null != cookie) {
			client.getRequest().getCookies().add(cookie);
		}

		Form form =BeanConvertor.beanObjectToForm(o);
		String info=client.post(form.getWebRepresentation()).getText();
		return info;
	}

	@Override
	public String putRequestForm(String url, Form form)throws Exception{
		// TODO Auto-generated method stub
		ClientResource client =new ClientResource(url);

		CookieSetting cookie = generateCookieForRest();
		if (null != cookie) {
			client.getRequest().getCookies().add(cookie);
		}

		String info=client.put(form.getWebRepresentation()).getText();
		return info;
	}

	@Override
	public String putRequestForm(String url, Object o) throws Exception{
		// TODO Auto-generated method stub
		ClientResource client =new ClientResource(url);

		CookieSetting cookie = generateCookieForRest();
		if (null != cookie) {
			client.getRequest().getCookies().add(cookie);
		}

		Form form =BeanConvertor.beanObjectToForm(o);
		String info=client.put(form.getWebRepresentation()).getText();
		return info;
	}

	/**
	 * GET 请求 /{param1}/{param2} 形式aa
	 */
	@Override
	public String getRequest(String url, Object[] par) throws Exception{
		// TODO Auto-generated method stub

		if(par!=null && par.length>0){
			for(int i=0;i<par.length;i++){
				url+="/"+par[i];
			}
		}
		// ahming notes: 请评估这个fix. 个别接口没有参数/参数个数为零, 应该继续调用REST接口
//		else{
//			return null;
//		}
		ClientResource client =new ClientResource(url);

		CookieSetting cookie = generateCookieForRest();
		if (null != cookie) {
			client.getRequest().getCookies().add(cookie);
		}

		String info=client.get().getText();

		// 测试这里是否能从REST端获取/传递 cookie. 目前的结果系不能, 后续再作处理
		//Series<org.restlet.data.Cookie> cookies = client.getCookies();

		return info;
	}

	/**
	 * GET 请求, ?param1=value1&?param2=value2 形式
	 *
	 * @param url
	 * @param queryString 注意这个不需要带开头?号. 例: "page=1&size=10"
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getRequest(String url, String queryString) throws Exception{
		if(StringUtils.isNotBlank(queryString)){
				url += "?" + queryString;
		}

		ClientResource client =new ClientResource(url);

		CookieSetting cookie = generateCookieForRest();
		if (null != cookie) {
			client.getRequest().getCookies().add(cookie);
		}

		String info=client.get().getText();

		return info;
	}

	@Override
	public String deleteRequest(String url, Object[] par) throws Exception{
		// TODO Auto-generated method stub
		if(par!=null && par.length>0){
			for(int i=0;i<par.length;i++){
				url+="/"+par[i];
			}
		}else{
			return null;
		}
		ClientResource client =new ClientResource(url);

		CookieSetting cookie = generateCookieForRest();
		if (null != cookie) {
			client.getRequest().getCookies().add(cookie);
		}

		String info=client.delete().getText();
		return info;
	}

	@Override
	public String getErrorCode(String reString) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonObject=JSONObject.fromObject(reString);
		return jsonObject.getString("error");
	}

	@Override
	public JSONObject getResultJsonObject(String reString) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonObject=JSONObject.fromObject(reString);
		return jsonObject;
	}

	/**
	 * 异步返回
	 * @param content
	 * @param req
	 */
	public void ResponPrint(String content,HttpServletResponse req){
		try {
			req.getWriter().print(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
