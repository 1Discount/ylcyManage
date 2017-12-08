package com.Manage.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.Manage.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.FlowView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CompressedFileUtil;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.randomUtil;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.common.util.ByteUtils.StringValueConversionException;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.AfterSaleInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.Enterprise;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.PrepareCardTemp;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.YouzanSyncLogisticsResult;
import com.Manage.service.DeviceInfoSer;
import com.Manage.service.DeviceLogsTempSer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import com.Manage.dao.BaseDao;
import com.kdt.api.KDTApi;
import com.kdt.api.LogisticsUtils;
import com.kdt.api.YouzanConfig;
import com.kdt.api.result.BaseResultWrapper;
import com.kdt.api.result.IsSuccessResponse;
import com.kdt.api.result.ShippingResultWrapper;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import com.sun.org.apache.xpath.internal.operations.And;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.poi.hssf.record.formula.functions.Datestring;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * * @author wangbo: * @date 创建时间：2015-5-29 上午9:15:58 * @version 1.0 * @parameter
 * * @since * @return
 */
@Controller
@RequestMapping("/orders/flowdealorders")
public class FlowDealOrdersControl extends BaseController
{
	private final static String RETURNROOT_STRING = "WEB-INF/views/orders/";

	private Logger logger = LogUtil.getInstance(FlowDealOrdersControl.class);



	/**
	 * 流量订单查询入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(String SN, Model model)
	{
		// 绑定流量订单状态.
		List<Dictionary> dictionarie = dictionarySer.getAllList(Constants.DICT_FLOWORDER_STATUS);
		List<Distributor> distributors = distributorSer.getAll("");
		model.addAttribute("SN", SN);
		model.addAttribute("diclist", dictionarie);
		model.addAttribute("distributors", distributors);
		return RETURNROOT_STRING + "floworder_list";
	}



	/**
	 * 统计界面入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model)
	{
		List<CountryInfo> countries = countryInfoSer.getAll("");
		// 获取订单来源
		List<Dictionary> dictionarie = dictionarySer.getAllList(Constants.DICT_CUSTOMER_SOURCE);
		List<Distributor> distributors = distributorSer.getAll("");
		model.addAttribute("diclist", dictionarie);
		model.addAttribute("distributors", distributors);
		model.addAttribute("Countries", countries);
		return RETURNROOT_STRING + "floworder_statistics";
	}



	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @param dealOrders
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, FlowDealOrders flowDealOrders, String limitdays, String isUserd, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{

		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		if (StringUtils.isNotBlank(isUserd))
		{
			isUserd = new String(isUserd.getBytes("iso-8859-1"), "utf-8");
			flowDealOrders.setIsUserd(isUserd);
		}

		if (StringUtils.isBlank(flowDealOrders.getTargerFlow()))
		{
			flowDealOrders.setTargerFlow("0");
		}

		flowDealOrders.setSysStatus(true);
		if (limitdays != null && !"".equals(limitdays))
		{
			flowDealOrders.setEnddate(DateUtils.getDateTime());
			flowDealOrders.setBegindateForQuery(DateUtils.beforeNDateToString(null, Integer.parseInt(limitdays), "yyyy-MM-dd HH:mm:ss"));
		}
		// 可通过参数获取流量套餐ID过滤
		String flowPlanID = request.getParameter("flowPlanID");
		if (!StringUtils.isBlank(flowPlanID))
		{
			flowDealOrders.setFlowPlanID(flowPlanID);
		}
		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{
			Distributor distributor = new Distributor();
			distributor.setCompany(flowDealOrders.getDistributorName());
			Distributor dis = distributorSer.getdisInofbycompany(distributor);
			//flowDealOrders.setFlowDealID("QD" + dis.getDistributorID());
			flowDealOrders.setFlowDealID("QD" + flowDealOrders.getDistributorName());
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), flowDealOrders);
		String pagesString = flowDealOrdersSer.getpageString(seDto);
		try
		{
			response.getWriter().println(pagesString);
			System.out.println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 企业用户分页查询
	 * 
	 * @param searchDTO
	 * @param dealOrders
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getpageenterprise")
	public void getpageenterprise(SearchDTO searchDTO, FlowDealOrders flowDealOrders, String limitdays, String isUserd, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		if (StringUtils.isNotBlank(isUserd))
		{
			isUserd = new String(isUserd.getBytes("iso-8859-1"), "utf-8");
			flowDealOrders.setIsUserd(isUserd);
		}

		if (StringUtils.isBlank(isUserd))
		{
			flowDealOrders.setTargerFlow("0");
		}

		flowDealOrders.setSysStatus(true);
		if (limitdays != null && !"".equals(limitdays))
		{
			flowDealOrders.setEnddate(DateUtils.getDateTime());
			flowDealOrders.setBegindateForQuery(DateUtils.beforeNDateToString(null, Integer.parseInt(limitdays), "yyyy-MM-dd HH:mm:ss"));
		}

		// 可通过参数获取流量套餐ID过滤
		String flowPlanID = request.getParameter("flowPlanID");
		if (!StringUtils.isBlank(flowPlanID))
		{
			flowDealOrders.setFlowPlanID(flowPlanID);
		}

		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{
			Enterprise enterprise = new Enterprise();
			enterprise.setEnterpriseID(flowDealOrders.getDistributorName());
			Enterprise enterprise2 = enterpriseSer.getenterprise(enterprise);

			flowDealOrders.setPriceConfiguration(enterprise2.getPriceConfiguration());

			flowDealOrders.setEnterpriseID(enterprise2.getEnterpriseID());

			flowDealOrders.setFlowDealID("QY" + flowDealOrders.getDistributorName());
		}
		else
		{
			flowDealOrders.setFlowDealID("QY");
		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), flowDealOrders);

		String pagesString = flowDealOrdersSer.getpageenterprise(seDto);

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
	 * 分页查询 根据国家编号通过 like 匹配 userCountry
	 * 
	 * @param searchDTO
	 * @param dealOrders
	 * @param response
	 */
	@RequestMapping("/getpage/mcc/{code}")
	public void getpageByMCC(@PathVariable String code, String orderSource, SearchDTO searchDTO, FlowDealOrders flowDealOrders, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("---" + searchDTO.getCurPage() + "------" + searchDTO.getPageSize());
		flowDealOrders.setSysStatus(true);

		flowDealOrders.setUserCountry(code);
		flowDealOrders.setOrderSource(orderSource);
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), flowDealOrders);
		String pagesString = flowDealOrdersSer.getpageString(seDto);
		try
		{
			response.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * 编辑
	 * 
	 * @param flowDealID
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String eidt(String flowDealID, Model model)
	{
		FlowDealOrders dealOrders = flowDealOrdersSer.getbyid(flowDealID);
		if (dealOrders == null)
		{
			return RETURNROOT_STRING + "floworder_list";
		}
		// 查询所有国家
		List<CountryInfo> countryInfos = countryInfoSer.getAll("");
		model.addAttribute("countrys", countryInfos);
		model.addAttribute("floworder", dealOrders);

		// 展开菜单设定为全部"数据交易订单"
		model.addAttribute("MenuPath", "/orders/flowdealorders/list");

		return RETURNROOT_STRING + "floworder_edit";
	}



	/**
	 * 详细信息
	 * 
	 * @param flowDealID
	 * @param model
	 * @return
	 */
	@RequestMapping("/info")
	public String info(String flowDealID, Model model)
	{
		FlowDealOrders dealOrders = flowDealOrdersSer.getbyid(flowDealID);
		if (dealOrders == null)
		{
			return RETURNROOT_STRING + "floworder_list";
		}
		dealOrders.setCreatorDateString(DateUtils.formatDate(dealOrders.getCreatorDate(), "yyyy-MM-dd HH:mm:ss"));
		if (dealOrders.getModifyDate() != null) dealOrders.setModifyDateString(DateUtils.formatDate(dealOrders.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("Model", dealOrders);

		CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(dealOrders.getUserCountry());
		dealOrders.setUserCountry(wrapper.getCountryNameStrings());
		model.addAttribute("Countries", wrapper.getmCountryList());

		model.addAttribute("AllCountryList", countryInfoSer.getAll(""));

		// 展开菜单设定为全部"数据交易订单"
		model.addAttribute("MenuPath", "/orders/flowdealorders/list");

		return RETURNROOT_STRING + "floworder_view";
	}



	/**
	 * 编辑保存
	 * 
	 * @param flowDealOrders
	 * @param response
	 */
	@RequestMapping("/editsave")
	public void editsave(FlowDealOrders flowDealOrders, String redays, String days, HttpServletResponse response)
	{
		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().println("请重新登录!");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;
		}
		try
		{
			if (flowDealOrders == null || flowDealOrders.getFlowDealID() == null || "".equals(flowDealOrders.getFlowDealID()))
			{
				response.getWriter().print("-1");
				return;
			}
			flowDealOrders.setModifyUserID(loginAdminUserInfo.getUserID());
			String uclist1 = "";
			String[] uc = flowDealOrders.getUserCountry().split(",");
			for (int i = 0; i < uc.length; i++)
			{
				String[] temp = uc[i].split("-");
				if (i != uc.length - 1)
				{
					uclist1 += temp[0] + "," + temp[1] + "," + temp[2] + "|";
				}
				else
				{
					uclist1 += temp[0] + "," + temp[1] + "," + temp[2];
				}
			}
			flowDealOrders.setUserCountry(uclist1);
			int dd = flowDealOrders.getFlowDays() - Integer.parseInt(days);
			flowDealOrders.setDaysRemaining(Integer.parseInt(redays) + dd);
			flowDealOrders.setJourney(flowDealOrders.getJourney().replace(",", "，"));
			if (StringUtils.isBlank(flowDealOrders.getJourney()))
			{
				flowDealOrders.setJourney("");
			}
			else
			{
				String journey = flowDealOrders.getJourney();
				String journey1 = "";
				String time = "";
				String countrynam = "";
				int mcc = 0;
				try
				{
					if (!"".equals(journey))
					{
						if (journey.split("\\|").length == 1)
						{
							countrynam = journey.split("\\（")[0];
							CountryInfo countryInfo = new CountryInfo();
							countryInfo.setCountryName(countrynam);
							mcc = countryInfoSer.getcountryinfoBycountryname(countryInfo).get(0).getCountryCode();
							String xcTime = journey.split("\\（")[1].replace("）", "");
							String[] xctimearray = xcTime.split("，");
							for (int n = 0; n < xctimearray.length; n++)
							{
								if (xctimearray[n].split("-").length == 2)
								{
									String beginTime = "20" + xctimearray[n].split("-")[0].replace(" ", "");
									String endTime = "20" + xctimearray[n].split("-")[1];
									time = time + "，" + beginTime + "_" + endTime;
								}
								else
								{
									time = time + "，20" + xctimearray[n];
								}
							}
							journey1 = "[{\"国家\":\"" + countrynam + "\",\"mcc\":\"" + mcc + "\",\"使用时间\":\"" + time + "\"}]";
							journey1 = journey1.replace("，\"}]", "\"}]");
							journey1 = journey1.replace("\":\"，", "\":\"");
						}
						else
						{
							journey1 = "[{";
							for (int j = 0; j < journey.split("\\|").length; j++)
							{
								countrynam = journey.split("\\|")[j].split("\\（")[0];
								CountryInfo countryInfo = new CountryInfo();
								countryInfo.setCountryName(countrynam);
								mcc = countryInfoSer.getcountryinfoBycountryname(countryInfo).get(0).getCountryCode();							
								String[] temparray = journey.split("\\|")[j].split("\\（")[1].replace("）", "").split("，");
								if (temparray.length >= 2)
								{
									for (int s = 0; s < temparray.length; s++)
									{
										if (temparray[s].split("-").length == 2)
										{
											String beginTime = "20" + temparray[s].split("-")[0].trim();
											String endTime = "20" + temparray[s].split("-")[1];
											time = time + "，" + beginTime + "_" + endTime;
											if (s == temparray.length - 1)
											{
												time = time + "\"},{";
											}											
										}
										else
										{
											time = time + "，20" + temparray[s].trim();
											if (s == temparray.length - 1)
											{
												time = time + "\"},{";
											}
										}
									}
									if (j == journey.split("\\|").length - 1)
									{
										time = time.substring(0, time.length() - 4);
									}
								}
								else
								{
									if (temparray[0].split("-").length == 2)
									{
										String beginTime = "20" + temparray[0].split("-")[0].trim();
										String endTime = "20" + temparray[0].split("-")[1];
										time = time + "，" + beginTime + "_" + endTime;
										if (j != journey.split("\\|").length - 1)
										{
											time = time + "\"},{";
										}
									}
									else
									{
										time = time + "，20" + temparray[0].trim();
										if (j != journey.split("\\|").length - 1)
										{
											time = time + "\"},{";
										}
									}
								}
								journey1 = journey1 + "\"国家\":\"" + countrynam + "\",\"mcc\":\"" + mcc + "\",\"使用时间\":\"" + time;
								time = "";
							}
							journey1 = journey1 + "\"}]";
							journey1 = journey1.replace("\":\"，", "\":\"");
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					response.getWriter().print("-2");
					return;
				}
				flowDealOrders.setJourney(journey1);
			}
			if (flowDealOrders.getOrderType().equals("2"))
			{
				flowDealOrders.setSpeedStr("0-2000|" + flowDealOrders.getFlowTotal() + "-1");
			}
			if (flowDealOrdersSer.editsave(flowDealOrders))
			{
				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());
					admin.setOperateContent("已修改流量交易订单, 记录ID为: " + flowDealOrders.getFlowDealID() + " 客户: " + flowDealOrders.getCustomerName());
					admin.setOperateMenu("订单管理>修改流量交易订单");
					admin.setOperateType("修改");
					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				response.getWriter().print("1");
			}
			else
			{
				response.getWriter().print("0");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 激活
	 * 
	 * @param flowOrderID
	 * @param response
	 */
	@RequestMapping("/activate")
	public void activate(String flowOrderID, String SN, String deviceDealID, HttpServletResponse response)
	{
		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		JSONObject result = new JSONObject();

		if (loginAdminUserInfo == null)
		{
			try
			{
				// response.getWriter().print("-5"); //.println("请重新登录!");
				result.put("code", -5);
				result.put("msg", "请重新登录!");
				response.getWriter().print(result.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				// logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		try
		{
			if (flowOrderID == null || "".equals(flowOrderID))
			{
				// response.getWriter().print("-1");
				result.put("code", -1);
				result.put("msg", "参数为空");
				response.getWriter().print(result.toString());
				return;
			}

			FlowDealOrders flowDealOrders = new FlowDealOrders();
			flowDealOrders.setFlowDealID(flowOrderID);
			flowDealOrders.setSN(SN);

			if (flowDealOrdersSer.activate(flowDealOrders))
			{
				// 将对应的设备订单更新状态
				DeviceDealOrders deviceDealOrders = new DeviceDealOrders();
				deviceDealOrders.setDeviceDealID(deviceDealID);
				deviceDealOrders.setOrderStatus("使用中");
				deviceDealOrdersSer.updateiffinish(deviceDealOrders);

				// 如果系有赞的订单, 则通过接口同步状态到有赞 : 若它所在的有赞购物车订单宝贝已全部完毕
				// --> 应该制作成一个 service 多处去调用!
				// 注意在更新下面前先把这流量订单设为已发货
				// 查询此订单
				FlowDealOrders dealOrders = flowDealOrdersSer.getbyid(flowOrderID);
				if (dealOrders != null)
				{
					if (StringUtils.startsWith(dealOrders.getOrderID(), YouzanConfig.YOUZAN_ORDER_ID_PREFIX))
					{
						int count = ordersInfoSer.updateLogisticsInfo("", "", dealOrders.getOrderID(), null, null);
						if (count > 0)
						{
							YouzanSyncLogisticsResult resultYouzan = ordersInfoSer.syncYouzanShipmentStatus(dealOrders.getOrderID(), null, dealOrders, null, null, "");
							if (0 != resultYouzan.getErrorCode() && 11 != resultYouzan.getErrorCode())
							{ // 11的情况这里不提示
								// response.getWriter().print(result.getMsg());
								result.put("code", 4);
								result.put("msg", resultYouzan.getMsg());
								response.getWriter().print(result.toString());
							}
							else if (10 == resultYouzan.getErrorCode())
							{
								result.put("code", 1);
								result.put("msg", resultYouzan.getMsg());
								response.getWriter().print(result.toString());
							}
							else
							{
								// response.getWriter().print("1");
								result.put("code", 1);
								result.put("msg", "流量订单激活成功");
								response.getWriter().print(result.toString());
							}
						}
						else
						{
							// 更新它对应的总订单发货状态出错, 则应该给出前端提示
							// response.getWriter().print("1"); // TODO: ...
							result.put("code", 3);
							result.put("msg", "有赞平台订单: 流量订单激活成功，但需要同步状态到有赞平台时更新总订单发货状态失败，请备忘并联系有赞后台订单管理员处理");
							response.getWriter().print(result.toString());
						}
					}
				}
				else
				{
					// 仍然可以通过Eid以E为开头去判断是否为有赞平台订单 但仅作为一个补救措施，严格还是通过YZ去判断为好
					if (StringUtils.startsWith(flowOrderID, "E"))
					{
						// response.getWriter().print("2");
						result.put("code", 2);
						result.put("msg", "有赞平台订单: 流量订单激活成功，但需要同步状态到有赞平台时查询总订单失败，请备忘并联系有赞后台订单管理员处理");
						response.getWriter().print(result.toString());
					}
					else
					{
						// response.getWriter().print("1");
						result.put("code", 1);
						result.put("msg", "流量订单激活成功");
						response.getWriter().print(result.toString());
					}
				}

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());// 创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());// 创建人姓名
					// admin.setOperateDate(date);//操作时间
					// admin.setSysStatus(1);

					admin.setOperateContent("已激活流量交易订单, 记录ID为: " + flowOrderID);
					admin.setOperateMenu("订单管理>激活流量交易订单");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				return;
			}
			else
			{
				// response.getWriter().print("0");
				result.put("code", 0);
				result.put("msg", "保存失败");
				response.getWriter().print(result.toString());
				return;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	/**
	 * 暂停
	 * 
	 * @param flowOrderID
	 * @param response
	 */
	@RequestMapping("/pause")
	public void pause(String flowOrderID, HttpServletResponse response)
	{
		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5"); // .println("请重新登录!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				// logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		try
		{
			if (flowOrderID == null || "".equals(flowOrderID))
			{
				response.getWriter().print("-1");
				return;
			}

			if (flowDealOrdersSer.pause(flowOrderID))
			{
				response.getWriter().print("1");

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());// 创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());// 创建人姓名
					// admin.setOperateDate(date);//操作时间
					// admin.setSysStatus(1);
					admin.setOperateContent("已暂停流量交易订单, 记录ID为: " + flowOrderID);
					admin.setOperateMenu("订单管理>暂停流量交易订单");
					admin.setOperateType("修改");
					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				return;
			}
			else
			{
				response.getWriter().print("0");
				return;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	/**
	 * 启动
	 * 
	 * @param flowOrderID
	 * @param response
	 */
	@RequestMapping("/launch")
	public void launch(String flowOrderID, HttpServletResponse response)
	{
		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5"); // .println("请重新登录!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				// logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		try
		{
			if (flowOrderID == null || "".equals(flowOrderID))
			{
				response.getWriter().print("-1");
				return;
			}
			if (flowDealOrdersSer.launch(flowOrderID))
			{
				response.getWriter().print("1");

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());// 创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());// 创建人姓名
					// admin.setOperateDate(date);//操作时间
					// admin.setSysStatus(1);

					admin.setOperateContent("已启用流量交易订单, 记录ID为: " + flowOrderID);
					admin.setOperateMenu("订单管理>启用流量交易订单");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				return;
			}
			else
			{
				response.getWriter().print("0");
				return;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	/**
	 * 限速
	 * 
	 * @param flowDealOrders
	 * @param response
	 */
	@RequestMapping("/limitspeed")
	public void limitSpeed(FlowDealOrders flowDealOrders, HttpServletResponse response)
	{

		// TODO: under construction!!!
		// 本项目的 dao 部分也已经加了, 但未测试
		// flowDealOrdersSer.limitSpeed(flowDealOrders);

	}



	/**
	 * 更新国家
	 * 
	 * @param flowDealOrders
	 * @param response
	 */
	@RequestMapping("/updatecountry")
	public void updateCountry(FlowDealOrders info, HttpServletResponse response)
	{
		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (loginAdminUserInfo == null)
		{
			try
			{
				response.getWriter().print("-5"); // .println("请重新登录!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				// logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		try
		{
			String op = request.getParameter("op");
			String mcc = request.getParameter("mcc");
			if (StringUtils.isBlank(op) || StringUtils.isBlank(mcc) || StringUtils.isBlank(info.getFlowDealID()))
			{
				response.getWriter().print("-1"); // 缺少参数
				return;
			}

			if ("delete".equals(op))
			{ // 删除
				FlowDealOrders order = flowDealOrdersSer.getbyid(info.getFlowDealID());
				if (null == order || StringUtils.isBlank(order.getFlowDealID()))
				{
					response.getWriter().print("-2"); // 此订单已失效
					return;
				}

				List<CountryInfo> countries = countryInfoSer.getAll("");
				info.setUserCountry(CountryUtils.getUpdateCountryStringFromMCCList(order.getUserCountry(), countries, mcc, 0));

			}
			else if ("add".equals(op))
			{ // 增加，可使用多个，形如 454,455
				FlowDealOrders order = flowDealOrdersSer.getbyid(info.getFlowDealID());
				if (null == order || StringUtils.isBlank(order.getFlowDealID()))
				{
					response.getWriter().print("-2"); // 此订单已失效
					return;
				}

				List<CountryInfo> countries = countryInfoSer.getAll("");
				info.setUserCountry(CountryUtils.getUpdateCountryStringFromMCCList(order.getUserCountry(), countries, mcc, 1));
			}
			else
			{
				response.getWriter().print("-1"); // 缺少参数
				return;
			}

			if (flowDealOrdersSer.updateCountry(info))
			{
				response.getWriter().print("1"); // 成功

				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());// 创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());// 创建人姓名
					// admin.setOperateDate(date);//操作时间
					// admin.setSysStatus(1);

					if ("delete".equals(op))
					{
						admin.setOperateContent("已删除流量交易订单可用国家, 记录ID为: " + info.getFlowDealID() + "删除国家: " + mcc);
					}
					else if ("add".equals(op))
					{
						admin.setOperateContent("已增加流量交易订单可用国家, 记录ID为: " + info.getFlowDealID() + "增加国家: " + mcc);
					}
					admin.setOperateMenu("订单管理>流量交易订单>修改国家");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else
			{
				response.getWriter().print("-3"); // 操作数据库出错
				return;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

	}



	/**
	 * 统计查询
	 * 
	 * @param dealOrders
	 * @param response
	 */
	@RequestMapping("/getstatistics")
	public void getstatistics(FlowDealOrders flowDealOrders, HttpServletResponse response)
	{
		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{
			Distributor distributor = new Distributor();

			distributor.setCompany(flowDealOrders.getDistributorName());
			Distributor dis = distributorSer.getdisInofbycompany(distributor);
			flowDealOrders.setFlowDealID(dis.getDistributorID());
		}

		try
		{
			Map<String, String> map = flowDealOrdersSer.statistics1(flowDealOrders);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			if (map != null)
			{
				obj.put("notdelete", map.get("notdelete"));
				obj.put("usered", map.get("usered"));
				obj.put("notuser", map.get("notuser"));
				obj.put("amount", map.get("amount"));
				obj.put("available", map.get("available"));
			}
			jsonArray.add(obj);
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", 1);
			response.getWriter().print(object.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}



	/**
	 * 判断该过期订单是否可编辑
	 * 
	 * @param flowDealOrders
	 * @param response
	 *            如果一个过期的流量订单，所绑定的SN设备未归还，并且该设备订单状态为可使用，那么就可以编辑，否则不能编辑
	 */
	public void ifedit(FlowDealOrders flowDealOrders, HttpServletResponse response)
	{

	}



	/**
	 * 根据sn查询流量订单
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/selectFlowdeal")
	public void selectFlowdeal(String SN, String value, HttpServletResponse response) throws IOException
	{
		if (value.equals(""))
		{
			response.getWriter().print("-1");
			return;
		}
		if (SN.equals(""))
		{
			response.getWriter().print("-2");
			return;
		}
		FlowDealOrders dealOrders = new FlowDealOrders();
		dealOrders.setIfFinish("是");
		dealOrders.setSysStatus(true);
		dealOrders.setSN(SN);
		List<FlowDealOrders> flowDealOrders = flowDealOrdersSer.selectFlowdeal(dealOrders);
		if (flowDealOrders == null)
		{
			response.getWriter().print("0");
			return;
		}
		else
		{
			String FlowDealID = flowDealOrders.get(0).getFlowDealID();
			// 流量订单信息的factoryFlag改为1，将所选的平台的value更新到serverInfo字段
			FlowDealOrders flowDeal = new FlowDealOrders();
			flowDeal.setFlowDealID(FlowDealID);
			flowDeal.setServerInfo(value);
			// 将factoryFlag改为1 //将value更新到serverInfo字段
			int count = flowDealOrdersSer.updateFlowdealByflowDealID(flowDeal);
			if (count > 0)
			{
				response.getWriter().print("1");
			}
		}
	}



	@RequestMapping("/updateSN")
	public String updateSN(String flowDealID, Model model)
	{
		FlowDealOrders dealOrders = flowDealOrdersSer.getbyid(flowDealID);
		if (dealOrders == null)
		{
			return RETURNROOT_STRING + "floworder_list";
		}
		dealOrders.setCreatorDateString(DateUtils.formatDate(dealOrders.getCreatorDate(), "yyyy-MM-dd HH:mm:ss"));
		if (dealOrders.getModifyDate() != null) dealOrders.setModifyDateString(DateUtils.formatDate(dealOrders.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("Model", dealOrders);

		CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(dealOrders.getUserCountry());
		dealOrders.setUserCountry(wrapper.getCountryNameStrings());
		model.addAttribute("Countries", wrapper.getmCountryList());

		model.addAttribute("AllCountryList", countryInfoSer.getAll(""));

		return "WEB-INF/views/deviceinfo/update_devicedealinfo";
	}



	/**
	 * 自动生成测试单
	 * 
	 * @param SNlist
	 * @param response
	 */
	@RequestMapping("/addTestOrder")
	public void addTestOrder(String SNlist, String ifLimitSpeed, String days, String ifAddDevcie, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isBlank(SNlist))
			{
				response.getWriter().print("-1");
			}
			if (StringUtils.isBlank(days))
			{
				days = "1";
			}
			int count = 0;
			int dcount = 0;
			AdminUserInfo userInfo = (AdminUserInfo) getSession().getAttribute("User");
			if (SNlist.indexOf("/") >= 0 && SNlist.indexOf("-") == -1)
			{
				String[] snarr = SNlist.split("/");
				for (int i = 0; i < snarr.length; i++)
				{
					if (flowDealOrdersSer.getTestBySN(Constants.SNformat(snarr[i])) != null)
					{
						// 说明已有测试订单
					}
					else
					{
						FlowDealOrders flowDealOrders = new FlowDealOrders();
						flowDealOrders.setFlowDealID(getUUID());
						flowDealOrders.setOrderID("10086");
						flowDealOrders.setCustomerID("10086");
						flowDealOrders.setCustomerName("测试订单");
						flowDealOrders.setOrderCreateDate(new Date());
						flowDealOrders.setUserCountry("中国,460,30.0");
						flowDealOrders.setPanlUserDate(DateUtils.getDateTime());
						flowDealOrders.setOrderStatus("可使用");
						flowDealOrders.setOrderType("1");
						flowDealOrders.setIfVPN("0");
						flowDealOrders.setIfFinish("是");
						flowDealOrders.setDaysRemaining(Integer.parseInt(days));
						flowDealOrders.setFlowDays(Integer.parseInt(days));
						flowDealOrders.setOrderAmount(0.00);
						flowDealOrders.setFlowExpireDate(DateUtils.beforeNDateToString(new Date(), Integer.parseInt(days), "yyyy-MM-dd HH:mm:ss"));
						flowDealOrders.setSN(Constants.SNformat(snarr[i]));
						flowDealOrders.setIfActivate("是");
						flowDealOrders.setActivateDate(DateUtils.getDateTime());
						flowDealOrders.setLimitValve(Constants.LIMITVALUE);
						flowDealOrders.setLimitSpeed(Constants.LIMITSPEED);
						flowDealOrders.setFlowUsed(0);
						flowDealOrders.setRemark("设备出厂测试订单，自动生成.");
						flowDealOrders.setCreatorDate(new Date());
						flowDealOrders.setCreatorUserID("10086");
						flowDealOrders.setCreatorUserName("自动");
						flowDealOrders.setIfLimitSpeed(ifLimitSpeed);
						if (flowDealOrdersSer.insertinfo(flowDealOrders))
						{
							count++;
						}
					}
					if (ifAddDevcie.equals("是"))
					{
						if (deviceInfoSer.getonebysn(Constants.SNformat(snarr[i])) == null)
						{
							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setDeviceID(getUUID());
							deviceInfo.setSN(Constants.SNformat(snarr[i]));
							deviceInfo.setRepertoryStatus("入库");
							deviceInfo.setDeviceStatus("可使用");
							deviceInfo.setCreatorUserID(userInfo.getUserID());
							deviceInfo.setCreatorUserName(userInfo.getUserName());
							deviceInfo.setCreatorDate(DateUtils.getDateTime());
							if (deviceInfoSer.insertDeviceInfo(deviceInfo) > 0)
							{
								dcount++;
							}
						}

					}

				}
			}
			else if (SNlist.indexOf("/") == -1 && SNlist.indexOf("-") >= 0)
			{
				String[] snarr = SNlist.split("-");
				long begin = Long.parseLong(snarr[0]);
				long end = Long.parseLong(snarr[1]);
				for (long i = begin; i <= end; i++)
				{
					if (flowDealOrdersSer.getTestBySN(i + "") != null)
					{

					}
					else
					{
						FlowDealOrders flowDealOrders = new FlowDealOrders();
						flowDealOrders.setFlowDealID(getUUID());
						flowDealOrders.setOrderID("10086");
						flowDealOrders.setCustomerID("10086");
						flowDealOrders.setCustomerName("测试订单");
						flowDealOrders.setOrderCreateDate(new Date());
						flowDealOrders.setUserCountry("中国,460,30.0");
						flowDealOrders.setPanlUserDate(DateUtils.getDateTime());
						flowDealOrders.setOrderStatus("可使用");
						flowDealOrders.setOrderType("1");
						flowDealOrders.setIfVPN("0");
						flowDealOrders.setIfFinish("是");
						flowDealOrders.setDaysRemaining(Integer.parseInt(days));
						flowDealOrders.setFlowDays(Integer.parseInt(days));
						flowDealOrders.setOrderAmount(0.00);
						flowDealOrders.setFlowExpireDate(DateUtils.beforeNDateToString(new Date(), Integer.parseInt(days), "yyyy-MM-dd HH:mm:ss"));
						flowDealOrders.setSN(i + "");
						flowDealOrders.setIfActivate("是");
						flowDealOrders.setActivateDate(DateUtils.getDateTime());
						flowDealOrders.setLimitValve(Constants.LIMITVALUE);
						flowDealOrders.setLimitSpeed(Constants.LIMITSPEED);
						flowDealOrders.setFlowUsed(0);
						flowDealOrders.setRemark("设备出厂测试订单，自动生成.");
						flowDealOrders.setCreatorDate(new Date());
						flowDealOrders.setCreatorUserID("10086");
						flowDealOrders.setCreatorUserName("自动");
						flowDealOrders.setIfLimitSpeed(ifLimitSpeed);
						if (flowDealOrdersSer.insertinfo(flowDealOrders))
						{
							count++;
						}
					}
					if (ifAddDevcie.equals("是"))
					{
						if (deviceInfoSer.getonebysn(i + "") == null)
						{
							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setDeviceID(getUUID());
							deviceInfo.setSN(i + "");
							deviceInfo.setRepertoryStatus("入库");
							deviceInfo.setDeviceStatus("可使用");
							deviceInfo.setCreatorUserID(userInfo.getUserID());
							deviceInfo.setCreatorUserName(userInfo.getUserName());
							deviceInfo.setCreatorDate(DateUtils.getDateTime());
							if (deviceInfoSer.insertDeviceInfo(deviceInfo) > 0)
							{
								dcount++;
							}
						}
					}
				}
			}
			else
			{
				if (flowDealOrdersSer.getTestBySN(Constants.SNformat(SNlist)) != null)
				{

				}
				else
				{
					FlowDealOrders flowDealOrders = new FlowDealOrders();
					flowDealOrders.setFlowDealID(getUUID());
					flowDealOrders.setOrderID("10086");
					flowDealOrders.setCustomerID("10086");
					flowDealOrders.setCustomerName("测试订单");
					flowDealOrders.setOrderCreateDate(new Date());
					flowDealOrders.setUserCountry("中国,460,30.0");
					flowDealOrders.setPanlUserDate(DateUtils.getDateTime());
					flowDealOrders.setOrderStatus("可使用");
					flowDealOrders.setOrderType("1");
					flowDealOrders.setIfVPN("0");
					flowDealOrders.setIfFinish("是");
					flowDealOrders.setDaysRemaining(Integer.parseInt(days));
					flowDealOrders.setFlowDays(Integer.parseInt(days));
					flowDealOrders.setOrderAmount(0.00);
					flowDealOrders.setFlowExpireDate(DateUtils.beforeNDateToString(new Date(), Integer.parseInt(days), "yyyy-MM-dd HH:mm:ss"));
					flowDealOrders.setSN(Constants.SNformat(SNlist));
					flowDealOrders.setIfActivate("是");
					flowDealOrders.setActivateDate(DateUtils.getDateTime());
					flowDealOrders.setLimitValve(Constants.LIMITVALUE);
					flowDealOrders.setLimitSpeed(Constants.LIMITSPEED);
					flowDealOrders.setFlowUsed(0);
					flowDealOrders.setRemark("设备出厂测试订单，自动生成.");
					flowDealOrders.setCreatorDate(new Date());
					flowDealOrders.setCreatorUserID("10086");
					flowDealOrders.setCreatorUserName("自动");
					flowDealOrders.setIfLimitSpeed(ifLimitSpeed);
					if (flowDealOrdersSer.insertinfo(flowDealOrders))
					{
						count++;
					}
				}

				if (ifAddDevcie.equals("是"))
				{
					if (deviceInfoSer.getonebysn(Constants.SNformat(SNlist)) == null)
					{
						DeviceInfo deviceInfo = new DeviceInfo();
						deviceInfo.setDeviceID(getUUID());
						deviceInfo.setSN(Constants.SNformat(SNlist));
						deviceInfo.setRepertoryStatus("入库");
						deviceInfo.setDeviceStatus("可使用");
						deviceInfo.setCreatorUserID(userInfo.getUserID());
						deviceInfo.setCreatorUserName(userInfo.getUserName());
						deviceInfo.setCreatorDate(DateUtils.getDateTime());
						if (deviceInfoSer.insertDeviceInfo(deviceInfo) > 0)
						{
							dcount++;
						}
					}
				}
			}
			response.getWriter().print(count + "");
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}

	}



	/**
	 * 自动生成测试单导入excel生成
	 * 
	 * @param SNlist
	 * @param response
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/addTestOrderexcel", method = RequestMethod.POST)
	public String addTestOrderexcel(@RequestParam("file") MultipartFile file, String ifAddDevcie, String ifLimitSpeed, String days, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
	{
		logger.info("..导入订单得到请求..");
		if (StringUtils.isBlank(days))
		{
			days = "1";
		}
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File files = fi.getStoreLocation();
		String[][] result = ExcelUtils.getData(files, 1); // 此模板开始一行为标题
		logger.info("成功获取到execl");
		int allColomn = result[0].length;
		int allRow = result.length;
		logger.info("成功获取到execl，行：" + allRow + "列：" + allColomn + "");
		for (int i = 0; i < result.length; i++)
		{
			for (int j = 0; j < result[i].length - 1; j++)
			{
				if (result[i][j].equals(""))
				{
					if (j == 0)
					{
						allRow = i;
						i = result.length - 1;
						break;
					}
				}
			}
		}
		logger.info("excel解析完毕.实际数据行数:" + allRow);
		String SNlist = "";
		for (int i = 0; i < allRow; i++)
		{
			SNlist += result[i][0] + "/";
		}
		SNlist = SNlist.substring(0, SNlist.length() - 1);
		logger.info("导入的sn为：" + SNlist);
		try
		{
			if (StringUtils.isBlank(SNlist))
			{
				response.getWriter().print("-1");
			}
			int count = 0;
			int dcount = 0;
			AdminUserInfo userInfo = (AdminUserInfo) getSession().getAttribute("User");
			if (SNlist.indexOf("/") >= 0 && SNlist.indexOf("-") == -1)
			{
				String[] snarr = SNlist.split("/");
				for (int i = 0; i < snarr.length; i++)
				{
					if (flowDealOrdersSer.getTestBySN(snarr[i]) != null)
					{
						// 说明已有测试订单
					}
					else
					{
						FlowDealOrders flowDealOrders = new FlowDealOrders();
						flowDealOrders.setFlowDealID(getUUID());
						flowDealOrders.setOrderID("10086");
						flowDealOrders.setCustomerID("10086");
						flowDealOrders.setCustomerName("测试订单");
						flowDealOrders.setOrderCreateDate(new Date());
						flowDealOrders.setUserCountry("中国,460,30.0");
						flowDealOrders.setPanlUserDate(DateUtils.getDateTime());
						flowDealOrders.setOrderStatus("可使用");
						flowDealOrders.setOrderType("1");
						flowDealOrders.setIfVPN("0");
						flowDealOrders.setIfFinish("是");
						flowDealOrders.setDaysRemaining(Integer.parseInt(days));
						flowDealOrders.setFlowDays(Integer.parseInt(days));
						flowDealOrders.setOrderAmount(0.00);
						flowDealOrders.setFlowExpireDate(DateUtils.beforeNDateToString(new Date(), Integer.parseInt(days), "yyyy-MM-dd HH:mm:ss"));
						flowDealOrders.setSN(Constants.SNformat(snarr[i]));
						flowDealOrders.setIfActivate("是");
						flowDealOrders.setActivateDate(DateUtils.getDateTime());
						flowDealOrders.setLimitValve(Constants.LIMITVALUE);
						flowDealOrders.setLimitSpeed(Constants.LIMITSPEED);
						flowDealOrders.setFlowUsed(0);
						flowDealOrders.setRemark("设备出厂测试订单，自动生成.");
						flowDealOrders.setCreatorDate(new Date());
						flowDealOrders.setCreatorUserID("10086");
						flowDealOrders.setCreatorUserName("自动");
						flowDealOrders.setIfLimitSpeed(ifLimitSpeed);
						if (flowDealOrdersSer.insertinfo(flowDealOrders))
						{
							count++;
						}
					}
					if (ifAddDevcie.equals("是"))
					{
						if (deviceInfoSer.getonebysn(snarr[i]) == null)
						{
							DeviceInfo deviceInfo = new DeviceInfo();
							deviceInfo.setDeviceID(getUUID());
							deviceInfo.setSN(snarr[i]);
							deviceInfo.setRepertoryStatus("入库");
							deviceInfo.setDeviceStatus("可使用");
							deviceInfo.setCreatorUserID(userInfo.getUserID());
							deviceInfo.setCreatorUserName(userInfo.getUserName());
							deviceInfo.setCreatorDate(DateUtils.getDateTime());
							if (deviceInfoSer.insertDeviceInfo(deviceInfo) > 0)
							{
								dcount++;
							}
						}

					}

				}
			}
			else
			{
				if (flowDealOrdersSer.getTestBySN(Constants.SNformat(SNlist)) != null)
				{

				}
				else
				{
					FlowDealOrders flowDealOrders = new FlowDealOrders();
					flowDealOrders.setFlowDealID(getUUID());
					flowDealOrders.setOrderID("10086");
					flowDealOrders.setCustomerID("10086");
					flowDealOrders.setCustomerName("测试订单");
					flowDealOrders.setOrderCreateDate(new Date());
					flowDealOrders.setUserCountry("中国,460,30.0");
					flowDealOrders.setPanlUserDate(DateUtils.getDateTime());
					flowDealOrders.setOrderStatus("可使用");
					flowDealOrders.setOrderType("1");
					flowDealOrders.setIfVPN("0");
					flowDealOrders.setIfFinish("是");
					flowDealOrders.setDaysRemaining(Integer.parseInt(days));
					flowDealOrders.setFlowDays(Integer.parseInt(days));
					flowDealOrders.setOrderAmount(0.00);
					flowDealOrders.setFlowExpireDate(DateUtils.beforeNDateToString(new Date(), Integer.parseInt(days), "yyyy-MM-dd HH:mm:ss"));
					flowDealOrders.setSN(Constants.SNformat(SNlist));
					flowDealOrders.setIfActivate("是");
					flowDealOrders.setActivateDate(DateUtils.getDateTime());
					flowDealOrders.setLimitValve(Constants.LIMITVALUE);
					flowDealOrders.setLimitSpeed(Constants.LIMITSPEED);
					flowDealOrders.setFlowUsed(0);
					flowDealOrders.setRemark("设备出厂测试订单，自动生成.");
					flowDealOrders.setCreatorDate(new Date());
					flowDealOrders.setCreatorUserID("10086");
					flowDealOrders.setCreatorUserName("自动");
					flowDealOrders.setIfLimitSpeed(ifLimitSpeed);
					if (flowDealOrdersSer.insertinfo(flowDealOrders))
					{
						count++;
					}
				}

				if (ifAddDevcie.equals("是"))
				{
					if (deviceInfoSer.getonebysn(Constants.SNformat(SNlist)) == null)
					{
						DeviceInfo deviceInfo = new DeviceInfo();
						deviceInfo.setDeviceID(getUUID());
						deviceInfo.setSN(SNlist);
						deviceInfo.setRepertoryStatus("入库");
						deviceInfo.setDeviceStatus("可使用");
						deviceInfo.setCreatorUserID(userInfo.getUserID());
						deviceInfo.setCreatorUserName(userInfo.getUserName());
						deviceInfo.setCreatorDate(DateUtils.getDateTime());
						if (deviceInfoSer.insertDeviceInfo(deviceInfo) > 0)
						{
							dcount++;
						}
					}
				}
			}
			logger.info("成功生成测试订单的个数：" + count);
			model.addAttribute("count", count + "");
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		return "WEB-INF/views/orders/floworder_server";
	}



	/**
	 * 清楚测试订单数据
	 * 
	 * @param response
	 */
	@RequestMapping("/claerTestOrder")
	public void claerTestOrder(HttpServletResponse response)
	{
		try
		{
			int count = flowDealOrdersSer.updateTestOrder();
			response.getWriter().print(count);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}

	}



	/**
	 * 跳转到floworder_controy.jsp页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/returnflowordercontroy")
	public String returnflowordercontroy(Model model)
	{
	    //渠道商
	    List<Distributor> distributors=distributorSer.getAll("");
	    model.addAttribute("distributors", distributors);
	    return "WEB-INF/views/orders/floworder_CountryNew";
	}



	@RequestMapping("/returnAvailableOrder")
	public String returnAvailableOrder()
	{
		return "WEB-INF/views/orders/floworder_vailableordersum";
	}



	@RequestMapping("/flowdealinfo")
	public String flowdealinfo(String status, String customerName, String userCountry, String orderSource, String begindateForQuery, String distributorName, String enddate, Model model, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");

		if (customerName != null) customerName = new String(customerName.trim().getBytes("ISO-8859-1"), "utf-8");
		if (orderSource != null) orderSource = new String(orderSource.trim().getBytes("ISO-8859-1"), "utf-8");
		if (distributorName != null) distributorName = new String(distributorName.trim().getBytes("ISO-8859-1"), "utf-8");
		if (userCountry != null) userCountry = new String(userCountry.trim().getBytes("ISO-8859-1"), "utf-8");

		request.setCharacterEncoding("utf-8");
		model.addAttribute("status", status);
		model.addAttribute("customerName", customerName);
		model.addAttribute("orderSource", orderSource);
		model.addAttribute("begindateForQuery", begindateForQuery);
		model.addAttribute("enddate", enddate);
		model.addAttribute("distributorName", distributorName);
		model.addAttribute("userCountry", userCountry);
		return "WEB-INF/views/orders/flowdealinfo";
	}



	@RequestMapping("/flowdealinfotwo")
	public String flowdealinfotwo(String status, String customerName, String orderSource, String userCountry, String begindateForQuery, String distributorName, String enddate, Model model, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");

		if (customerName != null) customerName = new String(customerName.trim().getBytes("ISO-8859-1"), "utf-8");
		if (orderSource != null) orderSource = new String(orderSource.trim().getBytes("ISO-8859-1"), "utf-8");
		if (distributorName != null) distributorName = new String(distributorName.trim().getBytes("ISO-8859-1"), "utf-8");
		if (userCountry != null) userCountry = new String(userCountry.trim().getBytes("ISO-8859-1"), "utf-8");

		request.setCharacterEncoding("utf-8");
		model.addAttribute("status", status);
		model.addAttribute("customerName", customerName);
		model.addAttribute("orderSource", orderSource);
		model.addAttribute("begindateForQuery", begindateForQuery);
		model.addAttribute("enddate", enddate);
		model.addAttribute("distributorName", distributorName);
		model.addAttribute("userCountry", userCountry);
		return "WEB-INF/views/orders/flowdealinfotwo";
	}



	@RequestMapping("/flowdealall")
	public void flowdealall(SearchDTO searchDTO, String status, String distributorName, String customerName, 
			String userCountry, String orderSource, String begindateForQuery, String enddate, HttpServletResponse response, 
			HttpServletRequest request) throws IOException
	{
		FlowDealOrders flowDealOrders = new FlowDealOrders();

		if (StringUtils.isNotBlank(distributorName))
		{
			Distributor dis = new Distributor();
			dis.setCompany(distributorName);
			Distributor distributor = distributorSer.getdisInofbycompany(dis);
			flowDealOrders.setFlowDealID(distributor.getDistributorID());
		}

		flowDealOrders.setCustomerName(customerName);
		flowDealOrders.setOrderSource(orderSource);
		flowDealOrders.setBegindateForQuery(begindateForQuery);
		flowDealOrders.setEnddate(enddate);
		flowDealOrders.setUserCountry(userCountry);

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), flowDealOrders);
		String pagesString = "";

		switch (status)
		{
			case "notdelete":
				pagesString = flowDealOrdersSer.getpage(flowDealOrders, "getnotdelete", "getnotdeleteCount", seDto);
				break;
			case "usered":
				pagesString = flowDealOrdersSer.getpage(flowDealOrders, "getusered", "getuseredCount", seDto);
				break;
			case "notuser":
				pagesString = flowDealOrdersSer.getpage(flowDealOrders, "getnotuser", "getnotuserCount", seDto);
				break;
			case "amount":
				pagesString = flowDealOrdersSer.getpage(flowDealOrders, "getamount", "getamountCount", seDto);
				break;
			case "available":
				pagesString = flowDealOrdersSer.getpage(flowDealOrders, "getavailable", "getavailableCount", seDto);
				break;
		}
		response.getWriter().println(pagesString);

	}



	public String download(String fileName, HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "inline;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
		try
		{
			OutputStream os = response.getOutputStream();
			wb.write(os);

			os.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 流量报表信息导出（按总数，使用过，未使用）
	 * 
	 * @param status
	 * @param all
	 * @param sta
	 * @param end
	 * @param cur
	 * @param distributorName
	 * @param pagesize
	 * @param total
	 * @param request
	 * @param customerName
	 * @param orderStatus
	 * @param begindateForQuery
	 * @param enddate
	 * @param response
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("/exportexecl")
	public void execl(String status, String all, String sta, String end, String cur, String distributorName, String pagesize, String total, HttpServletRequest request, String customerName, String orderStatus, String begindateForQuery, String enddate, HttpServletResponse response) throws UnsupportedEncodingException
	{
		FlowDealOrders flowDealOrders2 = null;
		if (customerName != null) customerName = new String(customerName.trim().getBytes("ISO-8859-1"), "utf-8");
		if (orderStatus != null) orderStatus = new String(orderStatus.trim().getBytes("ISO-8859-1"), "utf-8");
		if (StringUtils.isNotBlank(distributorName)) distributorName = new String(distributorName.trim().getBytes("ISO-8859-1"), "utf-8");
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setDefaultRowHeightInPoints(5000);

		sheet.setColumnWidth((short) 0, (short) 9000);
		sheet.setColumnWidth((short) 1, (short) 4500);
		sheet.setColumnWidth((short) 3, (short) 5500);
		sheet.setColumnWidth((short) 7, (short) 5500);
		sheet.setColumnWidth((short) 8, (short) 5500);
		sheet.setColumnWidth((short) 9, (short) 5500);
		sheet.setColumnWidth((short) 12, (short) 5500);
		sheet.setColumnWidth((short) 13, (short) 5500);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// short c = 500;
		// row.setHeight(c);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("交易ID");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("设备SN");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("客户");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("国家");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("总金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("天数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("是否激活");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("预约开始时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("到期时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("上次接入时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("订单状态");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("创建人");
		cell.setCellStyle(style);

		cell = row.createCell((short) 12);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 13);
		cell.setCellValue("实际使用天数");
		cell.setCellStyle(style);
		
		
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		// List list = CreateSimpleExcelToDisk.getStudent();
		List<FlowDealOrders> flowDealOrders = null;
		FlowDealOrders flowDealOrders3 = new FlowDealOrders();
		if ("所有".equals(orderStatus))
		{
			orderStatus = "";
		}
		flowDealOrders3.setCustomerName(customerName);
		flowDealOrders3.setEnddate(enddate);
		flowDealOrders3.setBegindateForQuery(begindateForQuery);
		flowDealOrders3.setOrderStatus(orderStatus);
		flowDealOrders3.setCustomerName(customerName);
		Distributor dis = new Distributor();
		dis.setCompany(distributorName);
		Distributor distributor = distributorSer.getdisInofbycompany(dis);
		if (distributor != null)
		{
			flowDealOrders3.setFlowDealID(distributor.getDistributorID());
		}
		else
		{
			flowDealOrders3.setFlowDealID("");
		}

		SearchDTO searchDTO = new SearchDTO();
		if (all == null)
		{
			// 导出当前页数据
			searchDTO.setEndIndex(Integer.parseInt(end));
			searchDTO.setPageSize(Integer.parseInt(pagesize));
			searchDTO.setCurPage(Integer.parseInt(cur));
			searchDTO.setTotal(Integer.parseInt(total));
			searchDTO.setStartIndex(Integer.parseInt(sta));
			searchDTO.setObj(flowDealOrders3);
		}
		else
		{
			searchDTO.setEndIndex(Integer.parseInt(total));
			searchDTO.setPageSize(Integer.parseInt(total));
			searchDTO.setCurPage(Integer.parseInt("1"));
			searchDTO.setTotal(Integer.parseInt(total));
			searchDTO.setStartIndex(Integer.parseInt("1"));
			searchDTO.setObj(flowDealOrders3);
		}
		if ("notdelete".equals(status))
		{
			flowDealOrders = flowDealOrdersSer.getflow("getnotdelete", searchDTO);
		}
		else if ("usered".equals(status))
		{
			flowDealOrders = flowDealOrdersSer.getflow("getusered", searchDTO);

		}
		else if ("notuser".equals(status))
		{
			flowDealOrders = flowDealOrdersSer.getflow("getnotuser", searchDTO);

		}
		else if ("amount".equals(status))
		{
			flowDealOrders = flowDealOrdersSer.getflow("getamount", searchDTO);
		}

		for (int i = 0; i < flowDealOrders.size(); i++)
		{
			row = sheet.createRow((int) i + 1);
			flowDealOrders2 = flowDealOrders.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(flowDealOrders2.getFlowDealID());
			row.createCell((short) 1).setCellValue(flowDealOrders2.getSN());
			row.createCell((short) 2).setCellValue(flowDealOrders2.getCustomerName());

			// 转换国家（中国，日本）
			CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(flowDealOrders.get(i).getUserCountry());
			String countryName = wrapper.getCountryNameStrings();

			row.createCell((short) 3).setCellValue(countryName);
			row.createCell((short) 4).setCellValue(flowDealOrders2.getOrderAmount());
			row.createCell((short) 5).setCellValue(flowDealOrders2.getFlowDays());
			row.createCell((short) 6).setCellValue(flowDealOrders2.getIfActivate());
			row.createCell((short) 7).setCellValue(flowDealOrders2.getPanlUserDate());
			row.createCell((short) 8).setCellValue(flowDealOrders2.getFlowExpireDate());
			row.createCell((short) 9).setCellValue(flowDealOrders2.getLastUpdateDate());
			row.createCell((short) 10).setCellValue(flowDealOrders2.getOrderStatus());
			row.createCell((short) 11).setCellValue(flowDealOrders2.getCreatorUserName());
			String creatorDate = DateUtils.formatDate(flowDealOrders2.getCreatorDate(), "yyyy-MM-dd HH:mm:ss");
			row.createCell((short) 12).setCellValue(creatorDate);
			
			
			//实际使用天数
			DeviceFlow deviceFlow = new DeviceFlow();
			deviceFlow.setFlowOrderID(flowDealOrders2.getFlowDealID());
			deviceFlow.setEndTime(enddate);
			deviceFlow.setBeginTime(begindateForQuery);
			List<DeviceFlow> deviceFlows = deviceFlowSer.getDeviceInBySnAndDate(deviceFlow);
			int sjUserDay = 0;
			if(deviceFlows.size()>flowDealOrders2.getFlowDays()){
				sjUserDay=flowDealOrders2.getFlowDays();
			}else{
				sjUserDay=deviceFlows.size();
			}
			row.createCell((short) 13).setCellValue(sjUserDay);
		}
		download(begindateForQuery + "到" + enddate + "订单统计.xls", wb, request, response);
	}



	/**
	 * 数据交易列表页面导出
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param response
	 * @param cName
	 * @param request
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/flowexportexecl")
	public void execl(FlowDealOrders flowDealOrders, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException
	{
		request.setCharacterEncoding("utf-8");
		if (flowDealOrders.getUserCountry() != null)
		{
			flowDealOrders.setUserCountry(new String(flowDealOrders.getUserCountry().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getIsUserd() != null)
		{
			flowDealOrders.setIsUserd(new String(flowDealOrders.getIsUserd().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderSource() != null)
		{
			flowDealOrders.setOrderSource(new String(flowDealOrders.getOrderSource().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderStatus() != null)
		{
			flowDealOrders.setOrderStatus(new String(flowDealOrders.getOrderStatus().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getCustomerName() != null)
		{
			flowDealOrders.setCustomerName(new String(flowDealOrders.getCustomerName().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getSN() != null)
		{
			flowDealOrders.setSN(new String(flowDealOrders.getSN().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getBeginDate() != null)
		{
			flowDealOrders.setBeginDate(new String(flowDealOrders.getBeginDate().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getEnddate() != null)
		{
			flowDealOrders.setEnddate(new String(flowDealOrders.getEnddate().trim().getBytes("ISO-8859-1"), "utf-8"));

		}
		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{
			flowDealOrders.setDistributorName(new String(flowDealOrders.getDistributorName().trim().getBytes("ISO-8859-1"), "utf-8"));
			Distributor distributor = new Distributor();
			distributor.setCompany(flowDealOrders.getDistributorName());
			Distributor dis = distributorSer.getdisInofbycompany(distributor);// null
			if (dis != null)
			{
				flowDealOrders.setFlowDealID("QD" + dis.getDistributorID());
			}
			else
			{
				flowDealOrders.setFlowDealID("");
			}

		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		sheet.setColumnWidth((short) 9, (short) 5000);
		sheet.setColumnWidth((short) 10, (short) 5000);
		sheet.setColumnWidth((short) 11, (short) 5000);
		sheet.setColumnWidth((short) 12, (short) 5000);

		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("设备SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("客户");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("国家");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("订单来源");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue("总金额");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("是否激活");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("预约开始时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 8);
		cell.setCellValue("到期时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 9);
		cell.setCellValue("上次接入时间 ");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 10);
		cell.setCellValue("订单状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 11);
		cell.setCellValue("创建人");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 12);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style1);

		List<FlowDealOrders> flowDealOrders2 = flowDealOrdersSer.queryPageExcel(flowDealOrders);

		for (int i = 0; i < flowDealOrders2.size(); i++)
		{
			FlowDealOrders f = flowDealOrders2.get(i);
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(f.getSN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(f.getCustomerName());
			cell1.setCellStyle(style);

			CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(f.getUserCountry());
			String cnames = wrapper.getCountryNameStrings();
			cell1 = row.createCell((short) 2);
			cell1.setCellValue(cnames);
			cell1.setCellStyle(style);
			String orderID = f.getOrderID();
			String orderSource = "";
			if (orderID.contains("GW"))
			{
				orderSource = "官网";
			}
			else if (orderID.contains("AP"))
			{
				orderSource = "APP";
			}
			else if (orderID.contains("WX"))
			{
				orderSource = "微信";
			}
			else if (orderID.contains("YZ"))
			{
				orderSource = "有赞";
			}
			else if (orderID.contains("AP"))
			{
				orderSource = "APP";
			}
			else
			{
				orderSource = "后台";
			}
			cell1 = row.createCell((short) 3);
			cell1.setCellValue(orderSource);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(f.getOrderAmount());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(f.getFlowDays());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(f.getIfActivate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 7);
			cell1.setCellValue(f.getPanlUserDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 8);
			cell1.setCellValue(f.getFlowExpireDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 9);
			cell1.setCellValue(f.getLastUpdateDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 10);
			cell1.setCellValue(f.getOrderStatus());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 11);
			cell1.setCellValue(f.getCreatorUserName());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 12);
			cell1.setCellValue(f.getCreatorDate());
			cell1.setCellStyle(style);

		}

		DownLoadUtil.execlExpoprtDownload(flowDealOrders.getUserCountry() + "订单详情.xls", wb, request, response);
	}



	/**
	 * 未使用订单详情导出
	 * 
	 * @param flowDealOrders
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	@RequestMapping("/flowexportexeclTwonotuser")
	public void flowexportexeclTwonotuser(FlowDealOrders flowDealOrders, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException
	{
		request.setCharacterEncoding("utf-8");
		if (flowDealOrders.getUserCountry() != null)
		{
			flowDealOrders.setUserCountry(new String(flowDealOrders.getUserCountry().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getIsUserd() != null)
		{
			flowDealOrders.setIsUserd(new String(flowDealOrders.getIsUserd().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderSource() != null)
		{
			flowDealOrders.setOrderSource(new String(flowDealOrders.getOrderSource().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderStatus() != null)
		{
			flowDealOrders.setOrderStatus(new String(flowDealOrders.getOrderStatus().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getCustomerName() != null)
		{
			flowDealOrders.setCustomerName(new String(flowDealOrders.getCustomerName().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getSN() != null)
		{
			flowDealOrders.setSN(new String(flowDealOrders.getSN().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getBeginDate() != null)
		{
			flowDealOrders.setBeginDate(new String(flowDealOrders.getBeginDate().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getEnddate() != null)
		{
			flowDealOrders.setEnddate(new String(flowDealOrders.getEnddate().trim().getBytes("ISO-8859-1"), "utf-8"));

		}
		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{
			flowDealOrders.setDistributorName(new String(flowDealOrders.getDistributorName().trim().getBytes("ISO-8859-1"), "utf-8"));
			Distributor distributor = new Distributor();
			distributor.setCompany(flowDealOrders.getDistributorName());
			Distributor dis = distributorSer.getdisInofbycompany(distributor);// null
			if (dis != null)
			{
				flowDealOrders.setFlowDealID("QD" + dis.getDistributorID());
			}
			else
			{
				flowDealOrders.setFlowDealID("");
			}

		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		sheet.setColumnWidth((short) 9, (short) 5000);
		sheet.setColumnWidth((short) 10, (short) 5000);
		sheet.setColumnWidth((short) 11, (short) 5000);
		sheet.setColumnWidth((short) 12, (short) 5000);

		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("设备SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("客户");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("国家");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("订单来源");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue("总金额");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("是否激活");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("预约开始时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 8);
		cell.setCellValue("到期时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 9);
		cell.setCellValue("上次接入时间 ");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 10);
		cell.setCellValue("订单状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 11);
		cell.setCellValue("创建人");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 12);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style1);

		List<FlowDealOrders> flowDealOrders2 = flowDealOrdersSer.queryPageExcel(flowDealOrders);

		for (int i = 0; i < flowDealOrders2.size(); i++)
		{
			FlowDealOrders f = flowDealOrders2.get(i);
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(f.getSN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(f.getCustomerName());
			cell1.setCellStyle(style);

			CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(f.getUserCountry());
			String cnames = wrapper.getCountryNameStrings();
			cell1 = row.createCell((short) 2);
			cell1.setCellValue(cnames);
			cell1.setCellStyle(style);
			String orderID = f.getOrderID();
			String orderSource = "";
			if (orderID.contains("GW"))
			{
				orderSource = "官网";
			}
			else if (orderID.contains("AP"))
			{
				orderSource = "APP";
			}
			else if (orderID.contains("WX"))
			{
				orderSource = "微信";
			}
			else if (orderID.contains("YZ"))
			{
				orderSource = "有赞";
			}
			else if (orderID.contains("AP"))
			{
				orderSource = "APP";
			}
			else
			{
				orderSource = "后台";
			}
			cell1 = row.createCell((short) 3);
			cell1.setCellValue(orderSource);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(f.getOrderAmount());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(f.getFlowDays());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(f.getIfActivate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 7);
			cell1.setCellValue(f.getPanlUserDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 8);
			cell1.setCellValue(f.getFlowExpireDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 9);
			cell1.setCellValue(f.getLastUpdateDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 10);
			cell1.setCellValue(f.getOrderStatus());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 11);
			cell1.setCellValue(f.getCreatorUserName());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 12);
			cell1.setCellValue(f.getCreatorDate());
			cell1.setCellStyle(style);
		}
		DownLoadUtil.execlExpoprtDownload("未使用订单详情.xls", wb, request, response);
	}



	/**
	 * 企业用户已使用订单详情导出
	 * 
	 * @param flowDealOrders
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/enterpriseflowexportexeclTwoUserd")
	public void enterpriseflowexportexeclTwoUserd(FlowDealOrders flowDealOrders, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
	{
		logger.info("导出表格进入请求");
		request.setCharacterEncoding("utf-8");
		String path = request.getSession().getServletContext().getRealPath("upload") + "/temp/" + new Date().getTime() + "/";

		File file = new File(request.getSession().getServletContext().getRealPath("upload") + "/temp");
		logger.info("路径：" + file);
		if (file.exists())
		{
			logger.info("文夹存在开始删除");
			CompressedFileUtil.clearFiles(request.getSession().getServletContext().getRealPath("upload") + "/temp");
			logger.info("文件删除成功,并开始重新创建");
			file.mkdirs();
			logger.info("文件创建成功");
		}
		else
		{
			logger.info("文件不存在开始创建");
			file.mkdirs();
		}

		logger.info("创建文件夹成功");
		File file1 = new File(path);
		if (!file1.exists())
		{
			file1.mkdirs();
		}
		OutputStream out = new FileOutputStream(path + "已使用订单详情.xls");// 文件本地存储地址

		int hang = 0;
		if (flowDealOrders.getUserCountry() != null)
		{
			flowDealOrders.setUserCountry(new String(flowDealOrders.getUserCountry().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getIsUserd() != null)
		{
			flowDealOrders.setIsUserd(new String(flowDealOrders.getIsUserd().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderSource() != null)
		{
			flowDealOrders.setOrderSource(new String(flowDealOrders.getOrderSource().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderStatus() != null)
		{
			flowDealOrders.setOrderStatus(new String(flowDealOrders.getOrderStatus().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getCustomerName() != null)
		{
			flowDealOrders.setCustomerName(new String(flowDealOrders.getCustomerName().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getSN() != null)
		{
			flowDealOrders.setSN(new String(flowDealOrders.getSN().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getBeginDate() != null)
		{
			flowDealOrders.setBeginDate(new String(flowDealOrders.getBeginDate().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getEnddate() != null)
		{
			flowDealOrders.setEnddate(new String(flowDealOrders.getEnddate().trim().getBytes("ISO-8859-1"), "utf-8"));

		}
		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{

			flowDealOrders.setFlowDealID("QY" + flowDealOrders.getDistributorName());

		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 10000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		sheet.setColumnWidth((short) 9, (short) 5000);
		sheet.setColumnWidth((short) 10, (short) 5000);
		sheet.setColumnWidth((short) 11, (short) 5000);
		sheet.setColumnWidth((short) 12, (short) 5000);
		sheet.setColumnWidth((short) 13, (short) 5000);
		sheet.setColumnWidth((short) 14, (short) 5000);

		HSSFRow row = sheet.createRow((int) 0);
		hang++;
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("ID");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("设备SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("客户");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("总金额");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue("天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("是否激活");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("预约开始时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("到期时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 8);
		cell.setCellValue("上次接入时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 9);
		cell.setCellValue("订单状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 10);
		cell.setCellValue("创建人");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 11);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 12);
		cell.setCellValue("已使用天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 13);
		cell.setCellValue("已使用国家");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 14);
		cell.setCellValue("已使金额");
		cell.setCellStyle(style1);

		// 获取到已使用的订单
		List<FlowDealOrders> flowDealOrders2 = flowDealOrdersSer.queryPageExcel(flowDealOrders);

		List<FlowDealOrders> tempList = new ArrayList<>();

		DeviceFlow deviceFlow = new DeviceFlow();

		// ------获取价格配置begin--------
		Enterprise enterprise = new Enterprise();
		enterprise.setEnterpriseID(flowDealOrders.getDistributorName());
		Enterprise enterprise2 = enterpriseSer.getenterprise(enterprise);
		String priceConfiguration = enterprise2.getPriceConfiguration();
		JSONObject jb = JSONObject.fromObject(priceConfiguration);

		// ------获取价格配置end--------

		int size = flowDealOrders2.size();
		// 记录使用天数为0的数据条数
		int remove = 0;
		for (int i = 0; i < size; i++)
		{

			deviceFlow.setFlowOrderID(flowDealOrders2.get(i).getFlowDealID());
			// 根据订单ID获取该订单流量使用情况
			List<DeviceFlow> deviceFlows = deviceFlowSer.getUserDayGroupMCC(deviceFlow);
			String userDays = "";
			String userCountry = "";
			Double userPrice = 0.0;
			for (DeviceFlow deviceFlow2 : deviceFlows)
			{
				String mcc = deviceFlow2.getMCC();
				userDays += deviceFlow2.getUserDays() + "，";
				userPrice = Double.parseDouble(deviceFlow2.getUserDays()) * Double.parseDouble(jb.get(mcc).toString());
				String[] userCountryArray = deviceFlow2.getUserCountry().split("\\|");
				for (int k = 0; k < userCountryArray.length; k++)
				{
					String U_countryOne = userCountryArray[k];
					if (U_countryOne.contains(mcc))
					{
						int index = U_countryOne.indexOf(",");
						deviceFlow2.setCountryName(U_countryOne.substring(0, index));
					}
				}

				if (userCountry.indexOf(deviceFlow2.getCountryName()) == -1)
				{
					userCountry += deviceFlow2.getCountryName() + "，";
				}

			}
			if (StringUtils.isNotBlank(userCountry))
			{
				userCountry = userCountry.substring(0, userCountry.length() - 1);
			}

			if (StringUtils.isNotBlank(userDays))
			{
				userDays = userDays.substring(0, userDays.length() - 1);
			}
			FlowDealOrders f = flowDealOrders2.get(i);

			// 过滤掉使用天数为0的

			if (StringUtils.isBlank(userDays))
			{
				remove++;
				continue;
			}
			else
			{
				tempList.add(f);
			}

			row = sheet.createRow((int) i - remove + 1);
			hang++;
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(f.getFlowDealID());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(f.getSN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(f.getCustomerName());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(f.getOrderAmount());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(f.getFlowDays());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(f.getIfActivate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(f.getPanlUserDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 7);
			cell1.setCellValue(f.getFlowExpireDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 8);
			cell1.setCellValue(f.getLastUpdateDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 9);
			cell1.setCellValue(f.getOrderStatus());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 10);
			cell1.setCellValue(f.getCreatorUserName());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 11);
			cell1.setCellValue(f.getCreatorDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 12);
			cell1.setCellValue(userDays);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 13);
			cell1.setCellValue(userCountry);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 14);
			cell1.setCellValue(userPrice);
			cell1.setCellStyle(style);

		}
		try
		{
			wb.write(out);
			logger.info("写入文件成功");
			if (out != null) out.flush();
			if (out != null) out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.flowUserInfo("0", tempList, response, request, path);

	}



	/**
	 * 渠道商已使用订单详情导出
	 * 
	 * @param flowDealOrders
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/flowexportexeclTwoUserd")
	public void flowexportexeclTwoUserd(String targerFlow, FlowDealOrders flowDealOrders, String distributorName, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
	{
		logger.info("导出表格进入请求");
		request.setCharacterEncoding("utf-8");
		//创建文件
		String path = request.getSession().getServletContext().getRealPath("upload") + "/temp/" + new Date().getTime() + "/";

		File file = new File(request.getSession().getServletContext().getRealPath("upload") + "/temp");
		logger.info("路径：" + file);
		if (file.exists())
		{
			logger.info("文夹存在开始删除");
			CompressedFileUtil.clearFiles(request.getSession().getServletContext().getRealPath("upload") + "/temp");
			logger.info("文件删除成功,并开始重新创建");
			file.mkdirs();
			logger.info("文件创建成功");
		}
		else
		{
			logger.info("文件不存在开始创建");
			file.mkdirs();
		}

		logger.info("创建文件夹成功");
		File file1 = new File(path);
		if (!file1.exists())
		{
			file1.mkdirs();
		}
		// 文件本地存储地址
		OutputStream out = new FileOutputStream(path +"已使用订单详情.xls");
		//防止乱码，转换查询参数
		int hang = 0;
		if (StringUtils.isBlank(targerFlow))
		{
			targerFlow = "0";
		}
		if (distributorName != null)
		{
			distributorName = new String(distributorName.trim().getBytes("ISO-8859-1"), "utf-8");
		}
		if (flowDealOrders.getUserCountry() != null)
		{
			flowDealOrders.setUserCountry(new String(flowDealOrders.getUserCountry().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getIsUserd() != null)
		{
			flowDealOrders.setIsUserd(new String(flowDealOrders.getIsUserd().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderSource() != null)
		{
			flowDealOrders.setOrderSource(new String(flowDealOrders.getOrderSource().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderStatus() != null)
		{
			flowDealOrders.setOrderStatus(new String(flowDealOrders.getOrderStatus().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getCustomerName() != null)
		{
			flowDealOrders.setCustomerName(new String(flowDealOrders.getCustomerName().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getSN() != null)
		{
			flowDealOrders.setSN(new String(flowDealOrders.getSN().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getBeginDate() != null)
		{
			flowDealOrders.setBeginDate(new String(flowDealOrders.getBeginDate().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getEnddate() != null)
		{
			flowDealOrders.setEnddate(new String(flowDealOrders.getEnddate().trim().getBytes("ISO-8859-1"), "utf-8"));

		}
		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{
			flowDealOrders.setDistributorName(new String(flowDealOrders.getDistributorName().trim().getBytes("ISO-8859-1"), "utf-8"));
			Distributor distributor = new Distributor();
			distributor.setCompany(flowDealOrders.getDistributorName());
			Distributor dis = distributorSer.getdisInofbycompany(distributor);// null
			if (dis != null)
			{
				flowDealOrders.setFlowDealID("QD" + dis.getDistributorID());
			}
			else
			{
				flowDealOrders.setFlowDealID("");
			}

		}
		
		
		//創建已使用订单表格
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		sheet.setColumnWidth((short) 9, (short) 5000);
		sheet.setColumnWidth((short) 10, (short) 5000);
		sheet.setColumnWidth((short) 11, (short) 5000);

		HSSFRow row = sheet.createRow((int) 0);
		hang++;
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("地区");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("设备SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue(" 客户名称");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("可使用国家");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("已使用国家");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("预约开始时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 8);
		cell.setCellValue("到期时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 9);
		cell.setCellValue("订单天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 10);
		cell.setCellValue("已使用天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 11);
		cell.setCellValue("退款天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 12);
		cell.setCellValue("已使用金额");
		cell.setCellStyle(style1);

		// 获取到已使用的订单
		List<FlowDealOrders> flowDealOrders2 = flowDealOrdersSer.queryPageExcel(flowDealOrders);

		List<FlowDealOrders> tempList = new ArrayList<>();
		//获取国家列表
		List<CountryInfo> getAllCountryInfo=countryInfoSer.getAll(null);
		Map<String,String> CountryInfoMap= new HashMap<>();
		for(CountryInfo c:getAllCountryInfo){
		    CountryInfoMap.put(c.getCountryCode()+"", c.getCountryName());
		}
		DeviceFlow deviceFlow = new DeviceFlow();

		int size = flowDealOrders2.size();
		// 记录使用天数为0的数据条数
		int remove = 0;
		for (int i = 0; i < size; i++)
		{
			FlowDealOrders f = flowDealOrders2.get(i);
			deviceFlow.setFlowOrderID(flowDealOrders2.get(i).getFlowDealID());
			deviceFlow.setFlowValue(Integer.parseInt(targerFlow) * 1024 + "");
			// 根据订单ID获取该订单流量使用情况
			List<DeviceFlow> deviceFlows = deviceFlowSer.getUserDayGroupMCC(deviceFlow);
			String userDays = "";
			String userCountry = "";
			Double userPriceTemp = 0.0;
			String mccString = "";
			String userPrice = "";
			int tatalUserDay = 0;
			
			// 获取订单使用天数和使用国家
			for (DeviceFlow deviceFlow2 : deviceFlows)
			{
				String mcc = deviceFlow2.getMCC();
				userDays += deviceFlow2.getUserDays() + "，";
				mccString += mcc + "，";
				tatalUserDay += Integer.parseInt(deviceFlow2.getUserDays());

				String[] userCountryArray = deviceFlow2.getUserCountry().split("\\|");
				for (int k = 0; k < userCountryArray.length; k++)
				{
					String U_countryOne = userCountryArray[k];
					if (U_countryOne.contains(mcc))
					{
						int index = U_countryOne.indexOf(",");
						deviceFlow2.setCountryName(U_countryOne.substring(0, index));
					}
				}

				if (userCountry.indexOf(deviceFlow2.getCountryName()) == -1)
				{
					userCountry += deviceFlow2.getCountryName() + "，";
				}

			}
			if (StringUtils.isNotBlank(userCountry))
			{
				userCountry = userCountry.substring(0, userCountry.length() - 1);
			}

			if (StringUtils.isNotBlank(userDays))
			{
				userDays = userDays.substring(0, userDays.length() - 1);
			}

			if (StringUtils.isNotBlank(mccString))
			{
				mccString = mccString.substring(0, mccString.length() - 1);
			}

			// **********对于实际使用天数大于订单可使用的情况进行处理begin*******************
			if (f.getFlowDays() < tatalUserDay)
			{

				String[] userDayArr = userDays.split("，");
				if (userDayArr.length == 1)
				{
					// 获取到最后一个使用天数
					int lastUserDay = Integer.parseInt(userDayArr[userDayArr.length - 1]) - 1;
					userDays = lastUserDay + "";
				}
				else
				{
					// 获取到最后一个使用天数
					int lastUserDay = Integer.parseInt(userDayArr[userDayArr.length - 1]) - 1;
					userDays = userDays.substring(0, userDays.length() - 1) + (lastUserDay + "");
					if (userDays.endsWith("0"))
					{
						userDays = userDays.substring(0, userDays.length() - 2);
						userCountry = userCountry.split("，")[0];
					}
				}
			}
			// ***********对于实际使用天数大于订单可使用的情况进行处理end******************
			//通过开始时间、结束时间以及设备序列号查询设备使用天数
			DeviceLogs devicelogs=new DeviceLogs();
			devicelogs.setSN(f.getSN());
			devicelogs.setBeginTime(f.getPanlUserDate());
			devicelogs.setEndTime(f.getFlowExpireDate());
			devicelogs.setFlowDistinction(null);
			List<DeviceLogs> resultDeviceLogs = deviceLogsSer.getDeviceInBySnAndDate(devicelogs);
			if(userDays==null || userDays.equals("")){
			   /* userDays=resultDeviceLogs==null ? "":resultDeviceLogs.size()+"";
			    if(resultDeviceLogs!=null && resultDeviceLogs.size()>0 && StringUtils.isBlank(userCountry)){
				String userCountryTemp="";
				for(int i2=0;i2<resultDeviceLogs.size();i2++){
				    userCountryTemp=resultDeviceLogs.get(i2).getMcc();
				    userCountryTemp=userCountryTemp.substring(0,userCountryTemp.indexOf(","));
				    if(userCountry.indexOf(userCountryTemp)<0){
					userCountry+=userCountryTemp+(i2==resultDeviceLogs.size()-1 ? "":",");
				    }
				}
			    }*/
			    if(resultDeviceLogs!=null && resultDeviceLogs.size()>0 ){
				String mccTemp="";
				for(int i2=0;i2<resultDeviceLogs.size();i2++){
				    mccTemp=resultDeviceLogs.get(i2).getMcc();
				    userCountry+=CountryInfoMap.get(StringUtils.isBlank(mccTemp)? "":mccTemp)+(i2==resultDeviceLogs.size()-1 ? "":"，");
				    userDays+=resultDeviceLogs.get(i2).getInCount()+(i2==resultDeviceLogs.size()-1 ? "":"，");
				}
			    }
			    
			}
			/*// 过滤掉使用天数为0的
			if (StringUtils.isBlank(userDays))
			{
				remove++;
				continue;
			}
			else
			{
				tempList.add(f);
			}*/
			//20170104修改，不过滤掉使用天数为0的
			tempList.add(f);
			// *****************已使用金额Begin******************
			String company = distributorName;
			Distributor distributor = new Distributor();
			distributor.setCompany(company);

			if (StringUtils.isNotBlank(company) && StringUtils.isNotBlank(userDays))
			{
				// 获取渠道价格配置
				Distributor disInfo = distributorSer.getdisInofbycompany(distributor);
				String[] priceConfigurationArray = null;
				boolean countryExits = true;
				int isconfiguration = 0;
				if (StringUtils.isNotBlank(disInfo.getPriceConfiguration()))
				{
					priceConfigurationArray = disInfo.getPriceConfiguration().split("\\|");
					for (int k = 0; k < userCountry.split("，").length; k++)
					{
						for (int j = 0; j < priceConfigurationArray.length; j++)
						{
							if (priceConfigurationArray[j].split("-")[1].contains(mccString.split("，")[k]))
							{
								userPriceTemp += Double.parseDouble(userDays.split("，")[k]) * Double.parseDouble(priceConfigurationArray[j].split("-")[2]);
								break;
							}
							else
							{
								if (j == priceConfigurationArray.length - 1)
								{
									countryExits = false;
								}
							}
						}
					}
				}
				else
				{
					countryExits = false;
					isconfiguration = 1;
				}
				if (countryExits)
				{
					userPrice = userPriceTemp + "";
				}
				else
				{
					if (isconfiguration == 1)
					{
						userPrice = "配置价格为空";
					}
					else
					{
						userPrice = userPriceTemp + "配置价格为空";
					}
				}
			}
			else
			{
				userPrice = "未选择渠道商";
			}
			// *****************已使用金额end******************

			HSSFCellStyle styleYellow = wb.createCellStyle();
			styleYellow.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleYellow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			//
			// if(userPrice.contains("配置价格为空") || userPrice.contains("未选择渠道商")){
			// styleYellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //
			// 填充单元格
			// styleYellow.setFillForegroundColor(HSSFColor.YELLOW.index);
			// }

			if (f.getFlowDays() < tatalUserDay)
			{
				styleYellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
				styleYellow.setFillForegroundColor(HSSFColor.YELLOW.index);
			}

			row = sheet.createRow((int) i - remove + 1);
			hang++;
			row.setHeight((short) 500);
			boolean isbreak = false;

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(i - remove);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(distributorName);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(DateUtils.DateToStr(f.getCreatorDate()));
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(f.getSN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(f.getCustomerName());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(new CountryUtils.CountryListWrapper(f.getUserCountry()).getCountryNameStrings());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(userCountry);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 7);
			cell1.setCellValue(f.getPanlUserDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 8);
			cell1.setCellValue(f.getFlowExpireDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 9);
			cell1.setCellValue(f.getFlowDays());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 10);
			cell1.setCellValue(userDays);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 11);
			cell1.setCellValue(f.getDrawBackDay());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 12);
			cell1.setCellValue(userPrice);
			cell1.setCellStyle(styleYellow);
		}
		try
		{
			wb.write(out);
			logger.info("写入文件成功");
			if (out != null) out.flush();
			if (out != null) out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.flowUserInfo(targerFlow, tempList, response, request, path);

	}



	public void flowUserInfo(String targerFlow, List<FlowDealOrders> flowDealOrders2, HttpServletResponse response, HttpServletRequest request, String path) throws FileNotFoundException, UnsupportedEncodingException
	{
		OutputStream out = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		int k = 0;
		DeviceLogs devicelogs=new DeviceLogs();
		// 转换mcc
		List<CountryInfo> countries = countryInfoSer.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}
		for (FlowDealOrders flowDealOrders3 : flowDealOrders2)
		{
			out = new FileOutputStream(path + "流量详情.xls");// 文件本地存储地址
			HSSFSheet sheet = wb.createSheet(k + "-" + flowDealOrders3.getSN().substring(9, 15));
			k++;

			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			HSSFCellStyle style1 = wb.createCellStyle();
			style1.setFont(font);
			style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCellStyle style = wb.createCellStyle();
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			sheet.setColumnWidth((short) 0, (short) 5000);
			sheet.setColumnWidth((short) 1, (short) 5000);
			sheet.setColumnWidth((short) 2, (short) 5000);
			sheet.setColumnWidth((short) 3, (short) 10000);
			sheet.setColumnWidth((short) 4, (short) 5000);
			sheet.setColumnWidth((short) 5, (short) 5000);
			sheet.setColumnWidth((short) 6, (short) 5000);

			HSSFRow row = sheet.createRow((int) 0);
			row.setHeight((short) 500);

			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("设备序列号SN：");
			cell.setCellStyle(style1);

			cell = row.createCell((short) 1);
			cell.setCellValue(flowDealOrders3.getSN());
			cell.setCellStyle(style1);

			cell = row.createCell((short) 2);
			cell.setCellValue("订单ID");
			cell.setCellStyle(style1);

			cell = row.createCell((short) 3);
			cell.setCellValue(flowDealOrders3.getFlowDealID());
			cell.setCellStyle(style1);

			row = sheet.createRow((int) 1);
			row.setHeight((short) 500);

			cell = row.createCell((short) 0);
			cell.setCellValue("日期");
			cell.setCellStyle(style);

			cell = row.createCell((short) 1);
			cell.setCellValue("使用国家");
			cell.setCellStyle(style);

			cell = row.createCell((short) 2);
			cell.setCellValue("是否接入成功");
			cell.setCellStyle(style);

			cell = row.createCell((short) 3);
			cell.setCellValue("当日使用流量");
			cell.setCellStyle(style);

			cell = row.createCell((short) 4);
			cell.setCellValue("备注");
			cell.setCellStyle(style);

			cell = row.createCell((short) 5);
			cell.setCellValue("退费标注");
			cell.setCellStyle(style);

			HSSFCellStyle style2 = wb.createCellStyle();
			style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 创建一个居中格式
			//根据订单计划使用时间 、结束时间、SN查询该订单下设备的使用详情
			devicelogs.setSN(flowDealOrders3.getSN());
			devicelogs.setBeginTime(flowDealOrders3.getPanlUserDate());
			devicelogs.setEndTime(flowDealOrders3.getFlowExpireDate());
			devicelogs.setFlowDistinction(null);
			List<DeviceLogs> resultDeviceLogs = deviceLogsSer.getDeviceInBySnAndDate(devicelogs);
			// 创建一个居中格式
			HSSFCellStyle styleYellow = wb.createCellStyle();
			styleYellow.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleYellow.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
			//循环创建每一行数据
			for (int i=0;i<resultDeviceLogs.size();i++) {
			    row = sheet.createRow((int) i + 2);
			    row.setHeight((short) 500);
			  //使用时间
			    cell = row.createCell((short) 0);
			    cell.setCellValue(resultDeviceLogs.get(i).getLogsDate());
			    cell.setCellStyle(styleYellow);
			    //使用国家
			    cell = row.createCell((short) 1);
			    String mcc =resultDeviceLogs.get(i).getMcc();
			    if (StringUtils.isNotBlank(mcc)) {
				mcc = mccNameMap.get(mcc);
			    }
			    cell.setCellValue(mcc);
			    cell.setCellStyle(styleYellow);
			    //是否接入成功
			    cell = row.createCell((short) 2);
			    cell.setCellValue(resultDeviceLogs.get(i).isIfIn()? "是":"否");
			    cell.setCellStyle(styleYellow);				
			    //当日使用流量
			    cell = row.createCell((short) 3);
			    try {
				cell.setCellValue(Bytes.valueOf(resultDeviceLogs.get(i).getFlowDistinction() + "K").toString());
			    } catch (StringValueConversionException e) {
				e.printStackTrace();
				cell.setCellValue(resultDeviceLogs.get(i).getFlowDistinction()); // 转换失败显示原值
			    }
			    cell.setCellStyle(styleYellow);
			    //备注
			    cell = row.createCell((short) 4);
			    cell.setCellValue("");
			    cell.setCellStyle(styleYellow);
			    //退费标注
			    cell = row.createCell((short) 5);
			    cell.setCellValue("");
			    cell.setCellStyle(styleYellow);
			}
			
			try
			{
				wb.write(out);
				logger.info("写入文件成功");
				if (out != null) out.flush();
				if (out != null) out.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			String zipFilePath = request.getSession().getServletContext().getRealPath("upload") + "/temp/";
			/*
			 * boolean flag = CompressedFileUtil.fileToZip(path, zipFilePath,
			 * "已使用订单详情");
			 * 
			 * if (flag) { logger.info("文件打包成功!"); } else { logger.info("文件打包失败!");
			 * }
			 */
			try
			{
				CompressedFileUtil.zip(path, zipFilePath + "已使用订单详情.zip");
			}
			catch (Exception e)
			{
				logger.info(e.getMessage());
				logger.info(e.getCause());
			}
			
			/*if (flowDealOrders3.getFlowDays() < resultDeviceLogs.size())
			{
				// 进来需要合并流量
				boolean isbreak = false;
				for (int i = 0; i < resultDeviceLogs.size(); i++)
				{
					DeviceFlow itemDeviceLogs = resultDeviceLogs.get(i);

					if (i == resultDeviceLogs.size() - 2)
					{
						isbreak = true;
						Long temp1 = Long.parseLong(resultDeviceLogs.get(resultDeviceLogs.size() - 1).getFlowCount());
						Long temp2 = Long.parseLong(resultDeviceLogs.get(resultDeviceLogs.size() - 2).getFlowCount());

						itemDeviceLogs.setFlowCount((temp2 + temp1) + "");
					}
					// 导出国家
					String mcc = resultDeviceLogs.get(i).getMCC();
					if (StringUtils.isNotBlank(mcc))
					{
						mcc = mccNameMap.get(mcc);
					}

					row = sheet.createRow((int) i + 2);

					row.setHeight((short) 500);

					HSSFCellStyle styleYellow = wb.createCellStyle();
					styleYellow.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
					styleYellow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
					
					if(itemDeviceLogs.getFlowCount()==null){
					    DeviceLogs dev = new DeviceLogs();
					    String sn1 = itemDeviceLogs.getSN();
					    dev.setSN(sn1);
					    DeviceLogs deviceLogs = deviceLogsSer.getlastOne(dev);
					    itemDeviceLogs.setFlowCount(deviceLogs.getDayUsedFlow());
					}else if(itemDeviceLogs.getFlowCount().equals("0")){
					    DeviceLogs dev = new DeviceLogs();
					    String sn1 = itemDeviceLogs.getSN();
					    dev.setSN(sn1);
					    DeviceLogs deviceLogs = deviceLogsSer.getlastOne(dev);
					    itemDeviceLogs.setFlowCount(deviceLogs.getDayUsedFlow());
					}

					if (Integer.parseInt(itemDeviceLogs.getFlowCount()) < Integer.parseInt(targerFlow) * 1024)
					{
						// 标黄
						// style.setFillForegroundColor(HSSFColor.YELLOW.index);//
						// 设置背影颜色

						styleYellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
						styleYellow.setFillForegroundColor(HSSFColor.YELLOW.index);

						cell = row.createCell((short) 4);
						cell.setCellValue(new HSSFRichTextString("未统计计费"));
						cell.setCellStyle(styleYellow);
					}
					cell = row.createCell((short) 0);
					cell.setCellValue(itemDeviceLogs.getDDTime());
					cell.setCellStyle(styleYellow);

					cell = row.createCell((short) 1);
					cell.setCellValue(mcc);
					cell.setCellStyle(styleYellow);

					cell = row.createCell((short) 2);
					cell.setCellValue("是");
					cell.setCellStyle(styleYellow);
					

					cell = row.createCell((short) 5);
					cell.setCellValue(itemDeviceLogs.getDescr());
					cell.setCellStyle(styleYellow);

					cell = row.createCell((short) 3);

					if (!"0".equals(itemDeviceLogs.getFlowCount()))
					{
						try
						{
							if (StringUtils.isBlank(itemDeviceLogs.getFlowCount()))
							{
								cell.setCellValue(Bytes.valueOf(0 + "K").toString());
							}
							else
							{
								cell.setCellValue(Bytes.valueOf(itemDeviceLogs.getFlowCount() + "K").toString());
							}

						}
						catch (StringValueConversionException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						String sn1 = itemDeviceLogs.getSN();
						String[] snarray = new String[] { sn1 };
						String flowValueJson = "";
						DeviceLogs deviceLogs = new DeviceLogs();
						try
						{
							deviceLogs.setSN(sn1);
							deviceLogs = deviceLogsSer.getlastOne(deviceLogs);

							// 判断周期结束刷新到数据库 刷新到数据库

							String BJTime = itemDeviceLogs.getBJTime() + " 00:00:00";

							Long temp1 = Long.parseLong(DateUtils.dateToTimeStamp(BJTime, "yyyy-MM-dd HH:mm:ss")) / 60 / 60;// hh

							Long temp2 = new Date().getTime() / 1000 / 60 / 60;

							// 判断是否已过周期时间
							if (temp2 - temp1 > 25)
							{
								DeviceFlow deviceFlow2 = new DeviceFlow();
								deviceFlow2.setID(itemDeviceLogs.getID());
								deviceFlow2.setFlowCount(deviceLogs.getDayUsedFlow());
								deviceFlowSer.updateInfo(deviceFlow2);
							}
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}

						itemDeviceLogs.setFlowCount(deviceLogs.getDayUsedFlow());

						try
						{
							cell.setCellValue(Bytes.valueOf(itemDeviceLogs.getFlowCount() + "K").toString());
						}
						catch (StringValueConversionException e)
						{
							e.printStackTrace();
						}
					}
					cell.setCellStyle(styleYellow);
					if (isbreak)
					{
						break;
					}
				}

			}
			else
			{
				for (int i = 0; i < resultDeviceLogs.size(); i++)
				{
					DeviceFlow itemDeviceLogs = resultDeviceLogs.get(i);

					// 导出国家
					String mcc = resultDeviceLogs.get(i).getMCC();
					if (StringUtils.isNotBlank(mcc))
					{
						mcc = mccNameMap.get(mcc);
					}

					row = sheet.createRow((int) i + 2);

					row.setHeight((short) 500);

					HSSFCellStyle styleYellow = wb.createCellStyle();
					styleYellow.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
					styleYellow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

					if (Integer.parseInt(itemDeviceLogs.getFlowCount()) < Integer.parseInt(targerFlow) * 1024)
					{
						// 标黄
						// style.setFillForegroundColor(HSSFColor.YELLOW.index);//
						// 设置背影颜色

						styleYellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
						styleYellow.setFillForegroundColor(HSSFColor.YELLOW.index);

						cell = row.createCell((short) 4);
						cell.setCellValue(new HSSFRichTextString("未统计计费"));
						cell.setCellStyle(styleYellow);

					}
					else
					{
						// styleYellow.setFillForegroundColor(HSSFColor.WHITE.index);
						// styleYellow.setFillPattern(HSSFCellStyle.ALIGN_FILL);

					}
					cell = row.createCell((short) 0);
					cell.setCellValue(itemDeviceLogs.getDDTime());
					cell.setCellStyle(styleYellow);

					cell = row.createCell((short) 1);
					cell.setCellValue(mcc);
					cell.setCellStyle(styleYellow);

					cell = row.createCell((short) 2);
					cell.setCellValue("是");
					cell.setCellStyle(styleYellow);
					
					cell = row.createCell((short) 5);
					cell.setCellValue(itemDeviceLogs.getDescr());
					cell.setCellStyle(styleYellow);

					cell = row.createCell((short) 3);

					if (!"0".equals(itemDeviceLogs.getFlowCount()))
					{
						try
						{
							if (StringUtils.isBlank(itemDeviceLogs.getFlowCount()))
							{
								cell.setCellValue(Bytes.valueOf(0 + "K").toString());
							}
							else
							{
								cell.setCellValue(Bytes.valueOf(itemDeviceLogs.getFlowCount() + "K").toString());
							}

						}
						catch (StringValueConversionException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						String sn1 = itemDeviceLogs.getSN();
						String[] snarray = new String[] { sn1 };
						String flowValueJson = "";
						DeviceLogs deviceLogs = new DeviceLogs();
						try
						{
							// flowValueJson =
							// this.getRequest(Constants.GET_FLOWCOUNT_API,
							// snarray);
							deviceLogs.setSN(sn1);
							deviceLogs = deviceLogsSer.getlastOne(deviceLogs);

							// 判断周期结束刷新到数据库 刷新到数据库

							String BJTime = itemDeviceLogs.getBJTime() + " 00:00:00";

							Long temp1 = Long.parseLong(DateUtils.dateToTimeStamp(BJTime, "yyyy-MM-dd HH:mm:ss")) / 60 / 60;// hh

							Long temp2 = new Date().getTime() / 1000 / 60 / 60;

							// 判断是否已过周期时间
							if (temp2 - temp1 > 25)
							{
								DeviceFlow deviceFlow2 = new DeviceFlow();
								deviceFlow2.setID(itemDeviceLogs.getID());
								deviceFlow2.setFlowCount(deviceLogs.getDayUsedFlow());
								deviceFlowSer.updateInfo(deviceFlow2);

							}
							// ///////////
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}

						itemDeviceLogs.setFlowCount(deviceLogs.getDayUsedFlow());

						try
						{
							cell.setCellValue(Bytes.valueOf(itemDeviceLogs.getFlowCount() + "K").toString());
						}
						catch (StringValueConversionException e)
						{
							e.printStackTrace();
						}
					}
					cell.setCellStyle(styleYellow);
				}
			}*/
		}

		

	}



	// public void flowUserInfo(List<FlowDealOrders> flowDealOrders2,
	// HttpServletResponse response, HttpServletRequest request, String path)
	// throws FileNotFoundException, UnsupportedEncodingException
	// {
	// for (FlowDealOrders flowDealOrders3 : flowDealOrders2)
	// {
	// String fileName =
	// flowDealOrders3.getSN()+"__"+flowDealOrders3.getCustomerName().replace("/",
	// ",")+"_"+randomUtil.getrandom(100, 999);
	// OutputStream out = new FileOutputStream(path + fileName+ ".xls");//
	// 文件本地存储地址
	//
	// HSSFWorkbook wb = new HSSFWorkbook();
	// HSSFSheet sheet = wb.createSheet("sheet1");
	//
	// HSSFFont font = wb.createFont();
	// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	//
	// HSSFCellStyle style1 = wb.createCellStyle();
	// style1.setFont(font);
	// style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	// style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	//
	// HSSFCellStyle style = wb.createCellStyle();
	// style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	// style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	//
	// sheet.setColumnWidth((short) 0, (short) 5000);
	// sheet.setColumnWidth((short) 1, (short) 5000);
	// sheet.setColumnWidth((short) 2, (short) 5000);
	// sheet.setColumnWidth((short) 3, (short) 10000);
	// sheet.setColumnWidth((short) 4, (short) 5000);
	// sheet.setColumnWidth((short) 5, (short) 5000);
	// sheet.setColumnWidth((short) 6, (short) 5000);
	//
	// HSSFRow row = sheet.createRow((int) 0);
	// row.setHeight((short) 500);
	//
	// HSSFCell cell = row.createCell((short) 0);
	// cell.setCellValue("设备序列号SN：");
	// cell.setCellStyle(style1);
	//
	// cell = row.createCell((short) 1);
	// cell.setCellValue(flowDealOrders3.getSN());
	// cell.setCellStyle(style1);
	//
	// cell = row.createCell((short) 2);
	// cell.setCellValue("订单ID");
	// cell.setCellStyle(style1);
	//
	// cell = row.createCell((short) 3);
	// cell.setCellValue(flowDealOrders3.getFlowDealID());
	// cell.setCellStyle(style1);
	//
	// row = sheet.createRow((int) 1);
	// row.setHeight((short) 500);
	//
	// cell = row.createCell((short) 0);
	// cell.setCellValue("日期");
	// cell.setCellStyle(style);
	//
	// cell = row.createCell((short) 1);
	// cell.setCellValue("使用国家");
	// cell.setCellStyle(style);
	//
	// cell = row.createCell((short) 2);
	// cell.setCellValue("是否接入成功");
	// cell.setCellStyle(style);
	//
	// cell = row.createCell((short) 3);
	// cell.setCellValue("当日使用流量");
	// cell.setCellStyle(style);
	//
	// List<CountryInfo> countries = countryInfoSer.getAll("");
	// HashMap<String, String> mccNameMap = new HashMap<String, String>();
	// for (CountryInfo item : countries)
	// {
	// mccNameMap.put(String.valueOf(item.getCountryCode()),
	// item.getCountryName());
	// }
	//
	// HSSFCellStyle style2 = wb.createCellStyle();
	// style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 创建一个居中格式
	//
	// DeviceFlow deviceFlow1 = new DeviceFlow();
	// deviceFlow1.setFlowOrderID(flowDealOrders3.getFlowDealID());
	//
	// List<DeviceFlow> resultDeviceLogs =
	// deviceFlowSer.getDeviceInBySnAndDate(deviceFlow1);
	// for (int i = 0; i < resultDeviceLogs.size(); i++)
	// {
	// // 导出国家
	// String mcc = resultDeviceLogs.get(i).getMCC();
	// if (StringUtils.isNotBlank(mcc))
	// {
	// mcc = mccNameMap.get(mcc);
	// }
	//
	// row = sheet.createRow((int) i + 2);
	//
	// row.setHeight((short) 500);
	//
	// DeviceFlow itemDeviceLogs = resultDeviceLogs.get(i);
	//
	// cell = row.createCell((short) 0);
	// cell.setCellValue(itemDeviceLogs.getDDTime());
	// cell.setCellStyle(style);
	//
	// cell = row.createCell((short) 1);
	// cell.setCellValue(mcc);
	// cell.setCellStyle(style);
	//
	// cell = row.createCell((short) 2);
	// cell.setCellValue("是");
	// cell.setCellStyle(style);
	//
	// cell = row.createCell((short) 3);
	//
	// if (!"0".equals(itemDeviceLogs.getFlowCount()))
	// {
	// try
	// {
	// if(StringUtils.isBlank(itemDeviceLogs.getFlowCount())){
	// cell.setCellValue(Bytes.valueOf(0+ "K").toString());
	// }else{
	// cell.setCellValue(Bytes.valueOf(itemDeviceLogs.getFlowCount() +
	// "K").toString());
	// }
	//
	// }
	// catch (StringValueConversionException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// else
	// {
	//
	// String sn1 = itemDeviceLogs.getSN();
	// String[] snarray = new String[] { sn1 };
	// String flowValueJson = "";
	// DeviceLogs deviceLogs = new DeviceLogs();
	// try
	// {
	// //flowValueJson = this.getRequest(Constants.GET_FLOWCOUNT_API, snarray);
	// deviceLogs.setSN(sn1);
	// deviceLogs = deviceLogsSer.getlastOne(deviceLogs);
	//
	// //判断周期结束刷新到数据库 刷新到数据库
	//
	// String BJTime = itemDeviceLogs.getBJTime()+" 00:00:00";
	//
	// Long temp1 = Long.parseLong(DateUtils.dateToTimeStamp(BJTime,
	// "yyyy-MM-dd HH:mm:ss"))/60/60;//hh
	//
	// Long temp2 = new Date().getTime()/1000/60/60;
	//
	// //判断是否已过周期时间
	// if(temp2-temp1>25){
	// DeviceFlow deviceFlow2 = new DeviceFlow();
	// deviceFlow2.setID(itemDeviceLogs.getID());
	// deviceFlow2.setFlowCount(deviceLogs.getDayUsedFlow());
	// deviceFlowSer.updateInfo(deviceFlow2);
	//
	// }
	// /////////////
	// }
	// catch (Exception e1)
	// {
	// e1.printStackTrace();
	// }
	// //JSONObject object1 = JSONObject.fromObject(flowValueJson);
	// //String flowCount =
	// JSONObject.fromObject(object1.getString("data")).getString("flowValue");
	// itemDeviceLogs.setFlowCount(deviceLogs.getDayUsedFlow());
	//
	// try
	// {
	// cell.setCellValue(Bytes.valueOf(itemDeviceLogs.getFlowCount()).toString());
	// }
	// catch (StringValueConversionException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// cell.setCellStyle(style2);
	// }
	// try
	// {
	// wb.write(out);
	// logger.info("写入文件成功");
	// if (out != null) out.flush();
	// if (out != null) out.close();
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// }
	//
	// String zipFilePath =
	// request.getSession().getServletContext().getRealPath("upload") +
	// "/temp/";
	// /* boolean flag = CompressedFileUtil.fileToZip(path, zipFilePath,
	// "已使用订单详情");
	//
	// if (flag)
	// {
	// logger.info("文件打包成功!");
	// }
	// else
	// {
	// logger.info("文件打包失败!");
	// }*/
	// try
	// {
	// CompressedFileUtil.zip(path, zipFilePath+"已使用订单详情.zip");
	// }
	// catch (Exception e)
	// {
	// logger.info(e.getMessage());
	// logger.info(e.getCause());
	// }
	//
	// }

	/**
	 * 下载打包的文件(订单使用详情.zip)
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/orderUserInfodownLoad")
	public void orderUserInfodownLoad(HttpServletRequest request, String fileName, HttpServletResponse response) throws UnsupportedEncodingException
	{
		fileName = new String(fileName.getBytes("ISO-8859-1"), "utf-8");
		String path = request.getSession().getServletContext().getRealPath("upload") + "/temp";
		DownLoadUtil.download(request, response, fileName, path);
	}



	@RequestMapping("/skipFeiPeiSN")
	public String skipFeiPeiSN(Model mode)
	{
		return RETURNROOT_STRING + "floworder_feipeisn";
	}



	/**
	 * 返回渠道对帐页面
	 * 
	 * @return
	 */
	@RequestMapping("/disaccount")
	public String disaccount(Model model)
	{
		List<Distributor> distributors = distributorSer.getAll("");
		model.addAttribute("dis", distributors);
		return RETURNROOT_STRING + "floworder_disaccount";
	}



	/**
	 * 按渠道商获取未使用订单 旧 分页查询
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getfloworderbydis")
	public void getfloworderbydis(SearchDTO searchDTO, FlowDealOrders flowDealOrders, Distributor distributor, String userStatus, HttpServletResponse response, String FlowDistinction, HttpServletRequest request) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		if ("usered".equals(userStatus))
		{
			// 已使用订单
			flowDealOrders.setLastUpdateDate("是");// "是" 只是何证了lastUpdateDate
													// 不为null 或 "" 别无他用
		}
		else if ("notuser".equals(userStatus))
		{
			// 未使用订单
			flowDealOrders.setLastUpdateDate(null);
		}

		if (StringUtils.isNotBlank(distributor.getCompany()))
		{
			Distributor dis = distributorSer.getdisInofbycompany(distributor);
			flowDealOrders.setFlowDealID("QD" + dis.getDistributorID());
		}
		else
		{
			flowDealOrders.setFlowDealID("QD");
		}

		flowDealOrders.setFlowDistinction(Double.parseDouble(flowDealOrders.getFlowDistinction()) * 1024 + "");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), flowDealOrders);

		String pagesString = flowDealOrdersSer.getpageString1(seDto);

		try
		{
			response.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/floworderbydisexcel")
	public void floworderbydisexcel(String lastUpdateDate, Distributor distributor, String SN, String beginDateForQuery, String endDate, String totalFlowTemp, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Map<String, String> userflow = new HashMap<>();
		distributor.setCompany(new String(distributor.getCompany().getBytes("ISO-8859-1"), "UTF-8"));
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setColumnWidth((short) 0, (short) 6000);
		sheet.setColumnWidth((short) 1, (short) 2400);
		sheet.setColumnWidth((short) 2, (short) 2400);
		sheet.setColumnWidth((short) 3, (short) 2400);
		sheet.setColumnWidth((short) 4, (short) 2400);
		sheet.setColumnWidth((short) 5, (short) 2400);
		sheet.setColumnWidth((short) 6, (short) 2400);
		sheet.setColumnWidth((short) 7, (short) 2400);
		sheet.setColumnWidth((short) 8, (short) 2400);
		sheet.setColumnWidth((short) 9, (short) 2400);
		sheet.setColumnWidth((short) 10, (short) 2400);
		sheet.setColumnWidth((short) 11, (short) 2400);
		sheet.setColumnWidth((short) 12, (short) 2400);
		sheet.setColumnWidth((short) 13, (short) 2400);
		sheet.setColumnWidth((short) 14, (short) 2400);
		sheet.setColumnWidth((short) 15, (short) 2400);
		sheet.setColumnWidth((short) 16, (short) 2400);
		sheet.setColumnWidth((short) 17, (short) 2400);
		sheet.setColumnWidth((short) 18, (short) 2400);
		sheet.setColumnWidth((short) 19, (short) 2400);
		sheet.setColumnWidth((short) 20, (short) 2400);
		sheet.setColumnWidth((short) 21, (short) 2400);
		sheet.setColumnWidth((short) 22, (short) 2400);
		sheet.setColumnWidth((short) 23, (short) 2400);
		sheet.setColumnWidth((short) 24, (short) 2400);
		sheet.setColumnWidth((short) 25, (short) 2400);
		sheet.setColumnWidth((short) 26, (short) 2400);
		sheet.setColumnWidth((short) 27, (short) 2400);
		sheet.setColumnWidth((short) 28, (short) 2400);
		sheet.setColumnWidth((short) 29, (short) 2400);
		sheet.setColumnWidth((short) 30, (short) 2400);
		sheet.setColumnWidth((short) 31, (short) 2400);
		sheet.setColumnWidth((short) 32, (short) 2400);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 600);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		// 设置字体
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 9);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(headfont);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式

		FlowDealOrders flowDealOrders = new FlowDealOrders();
		flowDealOrders.setLastUpdateDate(lastUpdateDate);

		if (StringUtils.isNotBlank(distributor.getCompany()))
		{
			distributor = distributorSer.getdisInofbycompany(distributor);
			flowDealOrders.setFlowDealID("QD" + distributor.getDistributorID());
		}
		else
		{
			flowDealOrders.setFlowDealID("QD");
			// flowDealOrders.setFlowDealID("");
		}

		flowDealOrders.setSN(SN);
		flowDealOrders.setBegindateForQuery(beginDateForQuery);
		flowDealOrders.setEnddate(endDate);

		// 获取所有使用过的订单
		List<FlowDealOrders> flowDealList = flowDealOrdersSer.getuseredflowdealbydis(flowDealOrders);

		// 转换mcc
		List<CountryInfo> countries = countryInfoSer.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("SN");
		cell.setCellStyle(style);

		String countryString = "";
		for (FlowDealOrders flowDealOrders2 : flowDealList)
		{
			String snString = flowDealOrders2.getSN();

			String flowOrderID = flowDealOrders2.getFlowDealID();

			if (StringUtils.isBlank(snString))
			{
				continue;
			}

			DeviceLogs log = new DeviceLogs();
			log.setSN(snString);
			log.setBeginTime(beginDateForQuery);
			log.setEndTime(endDate);
			log.setFlowDistinction(totalFlowTemp);
			log.setFlowOrderID(flowOrderID);
			List<DeviceLogs> logList = deviceLogsSer.getdevicelogTempbysn(log);

			int totalFlow = 0;

			for (DeviceLogs deviceLogs : logList)
			{
				String mcc = deviceLogs.getMcc();
				if (StringUtils.isNotBlank(mcc))
				{
					mcc = mccNameMap.get(mcc);
					if (countryString.indexOf(mcc) == -1)
					{
						countryString += mcc + ",";
					}
				}
			}

		}
		if (StringUtils.isNotBlank(countryString))
		{
			countryString = countryString.substring(0, countryString.length() - 1);
		}
		for (int i = 0; i < countryString.split(",").length; i++)
		{
			cell = row.createCell((short) (1 + i));
			cell.setCellValue(countryString.split(",")[i]);
			cell.setCellStyle(style);
		}

		cell = row.createCell((short) (1 + countryString.split(",").length));
		cell.setCellValue("流量合计");
		cell.setCellStyle(style);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		String userDays = "";
		int temp = 0;
		for (int i = 0; i < flowDealList.size(); i++)
		{

			String snString = flowDealList.get(i).getSN();

			String flowOrderID = flowDealList.get(i).getFlowDealID();

			// 第四步，创建单元格，并设置值
			row = sheet.createRow((int) i + 1 - temp);
			row.setHeight((short) 450);
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(snString);
			cell1.setCellStyle(style1);

			DeviceLogs log = new DeviceLogs();
			log.setSN(snString);
			log.setFlowOrderID(flowOrderID);
			log.setBeginTime(beginDateForQuery);
			log.setEndTime(endDate);
			log.setFlowDistinction(totalFlowTemp);
			// 根据sn获取到使用国家
			List<DeviceLogs> logList = deviceLogsSer.getdevicelogTempbysn(log);

			int totalFlow = 0;

			for (DeviceLogs deviceLogs : logList)
			{
				String mcc = deviceLogs.getMcc();

				String flowDistinction = deviceLogs.getFlowDistinction();
				totalFlow += Integer.parseInt(flowDistinction);

				if (StringUtils.isNotBlank(mcc))
				{

					// 获取到第i个使用国家
					mcc = mccNameMap.get(mcc);
					// 获取到第i个国家使用的天数
					userDays = deviceLogs.getUserDays();

					// 找出国家在国家数组中的位置
					int index = -1;
					for (int k = 0; k < countryString.split(",").length; k++)
					{
						String oneCountry = countryString.split(",")[k];
						if (oneCountry.equals(mcc))
						{
							index = k;
							break;
						}
					}

					if (index != -1)
					{
						cell1 = row.createCell((short) (1 + index));
						cell1.setCellValue(Integer.parseInt(userDays));
						cell1.setCellStyle(style1);

					}

				}
			}

			cell1 = row.createCell((short) (1 + countryString.split(",").length));
			cell1.setCellValue(userDays);
			cell1.setCellStyle(style1);
			if (totalFlow >= 1024)
			{
				cell1.setCellValue(totalFlow / 1024 + "M");
			}
			else
			{
				cell1.setCellValue(totalFlow + "KB");
			}

		}
		try
		{
			// 2月2号-2月24日环球猫订单使用详情
			DownLoadUtil.execlExpoprtDownload(beginDateForQuery + "到" + endDate + distributor.getCompany() + "订单使用详情.xls", wb, request, response);

		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/refundView")
	public String refundView(String flowDealID, HttpServletRequest request, HttpServletResponse response, Model model)
	{

		AfterSaleInfo afterSaleInfo = afterSaleInfoSer.getRefundInfo(flowDealID);

		model.addAttribute("Model", afterSaleInfo);

		return RETURNROOT_STRING + "floworder_refundview";
	}



	/**
	 * 分页显示测试单
	 * 
	 * @param request
	 * @param response
	 * @param orders
	 * @param seDto
	 */
	@RequestMapping("/pageTestOrder")
	public void pageTestOrder(HttpServletRequest request, HttpServletResponse response, FlowDealOrders orders, SearchDTO seDto)
	{
		SearchDTO sd = new SearchDTO(seDto.getCurPage(), seDto.getPageSize(), seDto.getSortName(), seDto.getSortOrder(), orders);

		String resultString = flowDealOrdersSer.getTestOrderPage(sd);

		try
		{
			response.getWriter().write(resultString);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 删除选中测试单
	 * 
	 * @param request
	 * @param response
	 * @param flowDealID
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/deleteCheckTestOrder")
	public void deleteCheckTestOrder(HttpServletRequest request, HttpServletResponse response, String flowDealID) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		String[] ids = flowDealID.split(",");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < ids.length; i++)
		{
			list.add(ids[i]);
		}

		int count = flowDealOrdersSer.updateTestOrderStatus(list);

		if (count > 0)
		{
			logger.debug("测试单删除成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", count + "条测试单删除成功!");
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
			logger.debug("测试单删除失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "测试单删除失败!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}



	@RequestMapping("/deleteBySearchTestOrder")
	public void deleteBySearchTestOrder(HttpServletRequest request, HttpServletResponse response, FlowDealOrders flowDealOrders) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		int count = flowDealOrdersSer.updateTestOrderStatusBySearch(flowDealOrders);

		if (count > 0)
		{
			logger.debug("测试单删除成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", count + "条测试单删除成功!");
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
			logger.debug("测试单删除失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "测试单删除失败!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}



	/**
	 * @param request
	 * @param response
	 */
	@RequestMapping("QueryOrderCountByMonth")
	public void QueryOrderCountByMonth(HttpServletRequest request, HttpServletResponse response, SearchDTO searchDTO, FlowDealOrders flowDealOrders)
	{
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), flowDealOrders);
		String pagesString = flowDealOrdersSer.QueryOrderCountByMonth(searchDTO, flowDealOrders);
		try
		{
			response.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("toQueryOrderCountByMonth")
	public String toQueryOrderCountByMonth()
	{
		return RETURNROOT_STRING + "QueryOrderCountByMonth";
	}



	@RequestMapping("QueryOrderCountByMonthexportexecl")
	public void QueryOrderCountByMonthexportexecl(HttpServletRequest request, String beginDate, String enddate,// export
			HttpServletResponse response)
	{
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setColumnWidth((short) 0, (short) 4000);

		sheet.setColumnWidth((short) 1, (short) 4000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);
		sheet.setColumnWidth((short) 5, (short) 4000);
		sheet.setColumnWidth((short) 6, (short) 4000);
		// sheet.setDefaultRowHeightInPoints(5000);
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
		cell.setCellValue("大洲");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("国家");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("总订单数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("总接入设备数");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("最低值");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("最高值");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellValue("平均值");
		cell.setCellStyle(style);

		// List<DeviceLogs> deviceInCountyByAll =
		// deviceLogsSer.getDeviceInCountByDayAll(queryInfo);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("beginDate", beginDate);
		map.put("endDate", enddate);
		map.put("startIndex", 0);
		map.put("endIndex", 999999);

		JSONArray ja = new JSONArray();

		List<Map<String, Object>> resultList = flowDealOrdersSer.QueryOrderCountByMonthexportexecl(map);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		int i = 0;
		for (Map<String, Object> map2 : resultList)
		{

			i++;

			String continent = map2.get("continent").toString();
			String countryName = map2.get("countryName").toString();
			String totalInCount = map2.get("totalInCount").toString();
			String totalOrderCount = map2.get("totalOrderCount").toString();
			String minCount = map2.get("minCount").toString();
			String maxCount = map2.get("maxCount").toString();
			String averageCount = map2.get("averageCount").toString();

			// 第四步，创建单元格，并设置值
			row = sheet.createRow((int) i);
			row.setHeight((short) 450);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(continent);
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(countryName);
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(totalOrderCount);
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(totalInCount);
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(minCount);
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(maxCount);
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(averageCount);
			cell1.setCellStyle(style1);

		}
		try
		{
			DownLoadUtil.execlExpoprtDownload(beginDate + "到" + enddate + "各国订单接入数量.xls", wb, request, response);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/toFloworderalwayuserinfo")
	public String toFloworderalwayuserinfo(HttpServletRequest request, HttpServletResponse response)
	{
		return RETURNROOT_STRING + "floworder_alwaysuserinfo";
	}



	@RequestMapping("/floworderalwayuserinfo")
	public void floworderalwayuserinfo(String countryName, PrepareCardTemp prepareCardTemp, String searchDate, SearchDTO searchDTO, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), prepareCardTemp);
		String pagesString = flowDealOrdersSer.floworderalwayuserinfoString(seDto, searchDate, countryName);
		try
		{
			response.getWriter().println(pagesString);
			System.out.println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("toflowOrderJieruInfo")
	public String floworderjieruinfo(Model model) throws UnsupportedEncodingException
	{
		String mcc = request.getParameter("mcc");
		String countryName = request.getParameter("countryName");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		model.addAttribute("mcc", mcc);
		model.addAttribute("countryName", new String(countryName.getBytes("iso-8859-1"), "utf-8"));
		model.addAttribute("endDate", endDate);
		model.addAttribute("beginDate", beginDate);
		return RETURNROOT_STRING + "floworder_jieruinfo";
	}
	
	@RequestMapping("updatedrawBackDay")
	public void updateRemark(FlowDealOrders flowDealOrders, String drawBackDayString, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		try 
		{
			request.setCharacterEncoding("utf-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		String ID = new String(flowDealOrders.getFlowDealID().getBytes("ISO-8859-1"),"UTF-8");
		
		String desc = new String(drawBackDayString.getBytes("ISO-8859-1"),"UTF-8");
		
		int count = 0;
		
		String[] ids = ID.split(",");
		
		String[] remarks = desc.split("\\|");
		
		JSONObject jo = new JSONObject();
		
		for (int i = 0; i < ids.length; i++) 
		{
			
			FlowDealOrders  fOrders = new FlowDealOrders();
			fOrders.setFlowDealID(ids[i]);
			fOrders.setDrawBackDay(Integer.parseInt(remarks[i]));

		
			
			int result = flowDealOrdersSer.updatedrawBackDay(fOrders);
			
			if(result>0)
			{
				count++;
			}
		}
		
		if(count>0)
		{
			jo.put("code", "0");
			jo.put("msg", count+"条数据保存成功！");
		}
		else
		{
			jo.put("code", "1");
			jo.put("msg", "保存失败！");
		}
		
		try 
		{
			response.getWriter().write(jo.toString());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 渠道商已使用订单详情导出
	 * 
	 * @param flowDealOrders
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/flowexportexeclTwoUserd2")
	public void flowexportexeclTwoUserd2(String targerFlow, FlowDealOrders flowDealOrders, String distributorName, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException
	{
		logger.info("导出表格进入请求");
		request.setCharacterEncoding("utf-8");
		//创建文件
		String path = request.getSession().getServletContext().getRealPath("upload") + "/temp/" + new Date().getTime() + "/";

		File file = new File(request.getSession().getServletContext().getRealPath("upload") + "/temp");
		logger.info("路径：" + file);
		if (file.exists())
		{
			logger.info("文夹存在开始删除");
			CompressedFileUtil.clearFiles(request.getSession().getServletContext().getRealPath("upload") + "/temp");
			logger.info("文件删除成功,并开始重新创建");
			file.mkdirs();
			logger.info("文件创建成功");
		}
		else
		{
			logger.info("文件不存在开始创建");
			file.mkdirs();
		}

		logger.info("创建文件夹成功");
		File file1 = new File(path);
		if (!file1.exists())
		{
			file1.mkdirs();
		}
		
		// 文件本地存储地址
		OutputStream out = new FileOutputStream(path +"已使用订单详情.xls");
		
		//防止乱码，转换查询参数
		
		if (StringUtils.isBlank(targerFlow))
		{
			targerFlow = "0";
		}
		if (distributorName != null)
		{
			distributorName = new String(distributorName.trim().getBytes("ISO-8859-1"), "utf-8");
		}
		if (flowDealOrders.getUserCountry() != null)
		{
			flowDealOrders.setUserCountry(new String(flowDealOrders.getUserCountry().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getIsUserd() != null)
		{
			flowDealOrders.setIsUserd(new String(flowDealOrders.getIsUserd().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderSource() != null)
		{
			flowDealOrders.setOrderSource(new String(flowDealOrders.getOrderSource().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getOrderStatus() != null)
		{
			flowDealOrders.setOrderStatus(new String(flowDealOrders.getOrderStatus().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getCustomerName() != null)
		{
			flowDealOrders.setCustomerName(new String(flowDealOrders.getCustomerName().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getSN() != null)
		{
			flowDealOrders.setSN(new String(flowDealOrders.getSN().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getBeginDate() != null)
		{
			flowDealOrders.setBeginDate(new String(flowDealOrders.getBeginDate().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (flowDealOrders.getEnddate() != null)
		{
			flowDealOrders.setEnddate(new String(flowDealOrders.getEnddate().trim().getBytes("ISO-8859-1"), "utf-8"));

		}
		if (StringUtils.isNotBlank(flowDealOrders.getDistributorName()))
		{
			flowDealOrders.setDistributorName(new String(flowDealOrders.getDistributorName().trim().getBytes("ISO-8859-1"), "utf-8"));
			Distributor distributor = new Distributor();
			/*distributor.setCompany(flowDealOrders.getDistributorName());*/
			distributor.setDistributorID(flowDealOrders.getDistributorName());
			Distributor dis = distributorSer.getDistributorbydistributorID(distributor);// null
			if (dis != null)
			{
				flowDealOrders.setFlowDealID("QD" + dis.getDistributorID());
				flowDealOrders.setDisId(dis.getDistributorID());
				distributorName=dis.getCompany();
			}
			else
			{
				flowDealOrders.setFlowDealID("");
			}

		}
		
		//創建已使用订单表格
		int hang = 0;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		sheet.setColumnWidth((short) 9, (short) 5000);
		sheet.setColumnWidth((short) 10, (short) 5000);
		sheet.setColumnWidth((short) 11, (short) 5000);

		HSSFRow row = sheet.createRow((int) 0);
		hang++;
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("地区");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("设备SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue(" 客户名称");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("可使用国家");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("已使用国家");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("预约开始时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 8);
		cell.setCellValue("到期时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 9);
		cell.setCellValue("订单天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 10);
		cell.setCellValue("已使用天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 11);
		cell.setCellValue("退款天数");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 12);
		cell.setCellValue("已使用金额");
		cell.setCellStyle(style1);

		// 获取到已使用的订单
		List<FlowDealOrders> tempList = flowDealOrdersSer.queryPageExcel2(flowDealOrders);
		int size = tempList.size();
		// 记录使用天数为0的数据条数
		int remove = 0;
		for (int i = 0; i < size; i++)
		{
		    	FlowDealOrders f=tempList.get(i);
			// *****************已使用金额end******************
			HSSFCellStyle styleYellow = wb.createCellStyle();
			styleYellow.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleYellow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			row = sheet.createRow((int) i - remove + 1);
			hang++;
			row.setHeight((short) 500);
			boolean isbreak = false;

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(i - remove);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(distributorName);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(DateUtils.DateToStr(f.getCreatorDate()));
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(f.getSN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(f.getCustomerName());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(f.getUserCountry());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(f.getUsingCountry());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 7);
			cell1.setCellValue(f.getPanlUserDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 8);
			cell1.setCellValue(f.getFlowExpireDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 9);
			cell1.setCellValue(f.getFlowDays());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 10);
			cell1.setCellValue(f.getUsingDate());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 11);
			cell1.setCellValue(f.getDrawBackDay());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 12);
			cell1.setCellValue(f.getOrderAmount());
			cell1.setCellStyle(styleYellow);
		}
		try
		{
			wb.write(out);
			logger.info("写入文件成功");
			if (out != null) out.flush();
			if (out != null) out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.flowUserInfo(targerFlow, tempList, response, request, path);

	}
}
