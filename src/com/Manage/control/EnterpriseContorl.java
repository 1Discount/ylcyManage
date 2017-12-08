package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.Enterprise;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.FlowPlanInfo;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.WorkFlow;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseContorl extends BaseController
{

	private Logger logger = LogUtil.getInstance(EnterpriseContorl.class);



	@RequestMapping("/list")
	public String toList(String Msg, Model model) throws UnsupportedEncodingException
	{
		if (StringUtils.isNotBlank(Msg))
		{
			Msg = new String(Msg.getBytes("iso-8859-1"), "utf-8");
		}
		model.addAttribute("Msg", Msg);
		return "WEB-INF/views/customerinfo/enterprise_list";

	}



	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @param enterprise
	 * @param response
	 */
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, Enterprise enterprise, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), enterprise);
		String jsonString = enterpriseSer.getPageString(seDto);
		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/delenterprise")
	public void del(Enterprise enterprise, HttpServletResponse response) throws IOException
	{
		enterprise.setSysStatus("0");
		int hang = enterpriseSer.updatebyid(enterprise);

		if (hang > 0)
		{
			response.getWriter().print("00");
		}
		else
		{
			response.getWriter().print("01");
		}

	}



	/**
	 * 编辑入口（）
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(Enterprise enterprise, Model model)
	{
		Enterprise info = enterpriseSer.getenterprise(enterprise);

		model.addAttribute("Model", info);

		return "WEB-INF/views/customerinfo/enterprise_edit";
	}



	/**
	 * 新增入口
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model)
	{
		return "WEB-INF/views/customerinfo/enterprise_edit";
	}



	/**
	 * 编辑、新增
	 * 
	 * @param enterprise
	 * @param response
	 */
	@RequestMapping("/save")
	public void save(Enterprise enterprise, HttpServletResponse response)
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

		boolean isInsert = false;
		if (StringUtils.isBlank(enterprise.getEnterpriseID()))
		{
			isInsert = true;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo != null)
		{
			if (isInsert)
			{
				enterprise.setCreatorUserID(adminUserInfo.getUserID());
				enterprise.setCreatorUserName(adminUserInfo.getUserName());
			}
			else
			{
				enterprise.setModifyUserID(adminUserInfo.getUserID());
				enterprise.setModifyUserName(adminUserInfo.getUserName());
			}
		}

		int hang;
		if (isInsert)
		{
			enterprise.setSysStatus("1");
			hang = enterpriseSer.insertInfo(enterprise);

		}
		else
		{
			hang = enterpriseSer.updatebyid(enterprise);
		}

		if (hang > 0)
		{
			logger.debug("企业用户信息保存成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存企业用户信息!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			logger.debug("企业用户保存失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存企业用户信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}



	@RequestMapping("/tofenfasebei")
	public String tofenfasebei(Enterprise enterprise, Model model)
	{
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("CountryList", countries);
		List<Dictionary> planTypes = dictionarySer.getAllList(Constants.DICT_FLOWPLANINFO_PLAN_TYPE);
		model.addAttribute("PlanTypes", planTypes);
		model.addAttribute("enterprise", enterprise);
		return "WEB-INF/views/customerinfo/enterprise_fenfasebei";
	}



	@RequestMapping("fenfaDevice")
	public void fenfaDevice(FlowPlanInfo flowPlanInfo,String speedLimit, String countryList1,String ifVPN, String snList, String enterpriseID, String enterpriseName, HttpServletResponse response, HttpServletRequest request) throws IOException
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		
		if(StringUtils.isNotBlank(enterpriseName)){
			
			enterpriseName = new String(enterpriseName.getBytes("iso-8859-1"),"utf-8");
			
		}

		IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();

		String[] snarry = snList.trim().split("/");
		int insertOKCount = 0;
		for (int i = 0; i < snarry.length; i++)
		{
			String SN = snarry[i];

			int result = deviceInfoSer.verifySN(Constants.SNformat(SN), countryList1);

			switch (result)
			{

				case -1:
					map.put(new String(SN), "设备不存在");
					continue;

				case -2:
					map.put(new String(SN), "设备 使用中");
					continue;

				case 0:
					map.put(new String(SN), "设备或漫游卡不支持国家");
					continue;

				case 1:
					logger.info(SN + "可以被下单");
					break;
			}

			// 总订单
			String orderID = enterpriseSer.getID(enterpriseID);

			OrdersInfo orders = new OrdersInfo();
			orders.setOrderID(orderID);
			orders.setCustomerID(enterpriseID);
			orders.setCustomerName(enterpriseName);
			orders.setDeviceDealCount(1);
			orders.setFlowDealCount(1);
			orders.setIfFinish("是");
			orders.setOrderStatus("已付款");
			orders.setOrderSource("后台");
			orders.setShipmentsStatus("已发货");
			orders.setOrderAmount(0);
			orders.setAddress(null);
			orders.setCreatorUserID(adminUserInfo.getUserID());
			orders.setCreatorUserName(adminUserInfo.getUserName());
			orders.setSysStatus(true);

			orders.setRemark("企业用户订单");

			ordersInfoSer.insertinfo(orders);

			// 设备订单

			String deviceDealID = enterpriseSer.getID(enterpriseID);

			DeviceDealOrders deviceDeal = new DeviceDealOrders();
			deviceDeal.setDeviceDealID(deviceDealID);
			deviceDeal.setOrderID(orderID);
			deviceDeal.setCustomerID(enterpriseID);
			deviceDeal.setCustomerName(enterpriseName);
			deviceDeal.setSN(Constants.SNformat(SN));
			DeviceInfo info = deviceInfoSer.getdevicetwo(Constants.SNformat(SN));
			deviceDeal.setDeviceID(info.getDeviceID());
			deviceDeal.setIfFinish("是");
			deviceDeal.setIfReturn("否");
			deviceDeal.setOrderStatus("使用中");
			deviceDeal.setDeallType("购买");
			deviceDeal.setDealAmount(0);
			deviceDeal.setCreatorUserID(adminUserInfo.getUserID());
			deviceDeal.setCreatorUserName(adminUserInfo.getUserName());
			deviceDeal.setSysStatus(true);
			deviceDeal.setRemark("企业用户订单");

			deviceDealOrdersSer.insertinfo(deviceDeal);

			// 设备状态改为使用中
			deviceInfoSer.updateDeviceOrder(Constants.SNformat(SN));

			// 流量订单
			FlowDealOrders flowDeal = new FlowDealOrders();
			flowDeal.setFlowDealID(enterpriseSer.getID(enterpriseID));
			flowDeal.setOrderID(orderID);
			flowDeal.setCustomerID(enterpriseID);
			flowDeal.setCustomerName(enterpriseName);
			flowDeal.setUserCountry(countryList1);
			flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss")));
			flowDeal.setPanlUserDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss")); // 预约时间
			flowDeal.setOrderStatus("可使用");
			flowDeal.setIfFinish("是");
			flowDeal.setSN(Constants.SNformat(SN));
			flowDeal.setOrderAmount(0);// 流量订单金额
			flowDeal.setIfActivate("是");// 是否激活
			flowDeal.setActivateDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));// 激活时间为当前时间
			flowDeal.setFlowUsed(0);
			flowDeal.setOrderType(flowPlanInfo.getOrderType());
			flowDeal.setFlowDays(flowPlanInfo.getValidPeriod());// 流量天数
			flowDeal.setDaysRemaining(flowPlanInfo.getValidPeriod());// 剩余天数
			flowDeal.setFlowPlanID(null);
			flowDeal.setFlowPlanName(null);
			flowDeal.setSpeedStr(speedLimit);


			//过期时间为预约开始时间+有效期
			flowDeal.setFlowExpireDate(DateUtils.beforeNDateToString( DateUtils.StrToDate(flowDeal.getPanlUserDate()), flowPlanInfo.getValidPeriod(), "yyyy-MM-dd HH:mm:ss"));

			
			if ("2".equals(flowPlanInfo.getOrderType()))
			{
				flowDeal.setFlowTotal(flowPlanInfo.getFlowSum() + "");
			}
			else
			{
				flowDeal.setFlowTotal(0 + "");
			}

			flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
			flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
			flowDeal.setIfVPN(ifVPN);
			flowDeal.setFactoryFlag(0);// 工厂标识
			flowDeal.setJourney("");
			flowDeal.setCreatorUserID(adminUserInfo.getUserID());
			flowDeal.setCreatorUserName(adminUserInfo.getUserName());
			flowDeal.setSysStatus(true);
			flowDeal.setRemark("企业用户订单");

			flowDealOrdersSer.insertinfo(flowDeal);
			insertOKCount++;
		}
		String message = "需要分发" + snarry.length + "条，分发成功" + insertOKCount + "条，分发失败" + map.size() + "条，以下为分发失败信息<br/>";
		
		LOGGER.info(message);
		for (String key : map.keySet())
		{
			message = message + key + ":　" + map.get(key) + "<br/>";
		}
		response.getWriter().print(message);

	}
	
	@RequestMapping("/toaccount")
	public String account(Model model){
		
		List<Enterprise> enterprises = enterpriseSer.getAll();
		
		model.addAttribute("enterprises",enterprises);
		
		return "WEB-INF/views/orders/flowOrder_Userinfo_enterprise";
	}
	
	@RequestMapping("/toenterpriseorderview")
	public String toenterprise_order_view(String enterpriseID,Model model){
		model.addAttribute("distributorName", enterpriseID);
		
		return "WEB-INF/views/customerinfo/enterprise_order_view";
	}
}
