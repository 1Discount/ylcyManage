package com.Manage.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.DeviceLogsTest;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.service.DeviceInfoSer;
import com.Manage.service.DistributorSer;
import com.Manage.service.FlowDealOrdersSer;
import com.google.gson.JsonArray;

/**
 * 对外接口
 *
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api")
public class AllAPIControl extends BaseController
{
	private Logger logger = LogUtil.getInstance(CountryInfoControl.class);

	@Autowired
	DeviceInfoSer deviceInfoSer;

	@Autowired
	FlowDealOrdersSer flowDealOrdersSer;

	@Autowired
	DistributorSer distributorSer;

	@Resource(name = "transactionManager")
	private PlatformTransactionManager pm;

	private TransactionStatus ts = null;



	/**
	 *
	 * @param isAll
	 *            是否暂停所有使用中的 订单（1 是 0暂停最近使用的流量单）
	 * @param password
	 *            渠道商密码
	 * @param SN
	 *            设备SN
	 * @param distributorID
	 *            渠道商ＩＤ
	 * @param request
	 * @param response
	 * @return　０１密钥认证失败　、０２SN不属于本渠道商、０３无使用中流量订单、０４暂停出错、０５暂停成功
	 * @throws Exception
	 */
	@RequestMapping("/getflowBySnAndDate")
	public void getflowBySnAndDate(DeviceLogs info, String type, Distributor distributor, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
	{

		response.setContentType("application/json;charset=UTF-8");
		// 参数校验
		if (StringUtils.isBlank(type))
		{
			response.getWriter().print("{\"Code\":\"01\",\"Msg\":\"参数校验失败\"}");
			return;
		}
		if ("1".equals(type))
		{
			JSONObject object = new JSONObject();
			// 按天
			DeviceLogsTest logs = new DeviceLogsTest();
			List<DeviceLogs> resultDeviceLogs = deviceLogsSer.getDeviceInBySnAndDate(info);
			JSONArray ja = new JSONArray();
			for (DeviceLogs item : resultDeviceLogs)
			{
				JSONObject obj = JSONObject.fromObject(item);
				logs.setFlowDistinction(item.getFlowDistinction());
				logs.setIfLogin("是");
				logs.setIfLoginSuccess("是");
				logs.setLogsDate(item.getLogsDate());
				logs.setSN(item.getSN());
				logs.setMonthUsedFlow("");
				logs.setSN(info.getSN());
				if (!item.isIfInAndHasFlow())
				{
					logs.setIfLoginSuccess("否");
				}
				ja.add(logs);
			}
			object.put("Code", "00");
			object.put("Msg", "成功");
			object.put("data", ja);
			System.out.println(object.toString());
			response.getWriter().print(object.toString());
		}
		else
		{
			// 按月
			String beginyear = info.getBeginTime().substring(0, 4);
			String beginmonth = info.getBeginTime().substring(5, 7);
			String endyear = info.getEndTime().substring(0, 4);
			String endmonth = info.getEndTime().substring(5, 7);
			System.out.println("开始年：" + beginyear + "结束年：" + endyear);
			List<DeviceLogsTest> resultDeviceLogsall = new ArrayList<DeviceLogsTest>();
			String end = "";

			if (Integer.parseInt(endyear) - Integer.parseInt(beginyear) >= 2)
			{
				end = "{\"Code\":" + "\"01\"" + ",\"Msg\":" + "\"一次只能查询两年之内的流量数据！\"" + ",\"Data\":" + null + "}";
				System.out.println(end);
				// return end;
				response.getWriter().print(end);
			}
			else
			{
				System.out.println("一年以内！");
				if (beginyear.equals(endyear))
				{// 年相同 判断月份
					// 判断月份是否 相等
					if (beginmonth.equals(endmonth))
					{
						System.out.println("同年同月！");
						// 遍历一次 得出结果
						// if(beginmonth.equals(endmonth)){
						try
						{
							DeviceLogsTest logs = new DeviceLogsTest();// new
																		// add
																		// bean
							info.setLogsDate(info.getBeginTime());
							logs = deviceLogsSer.getflowBySnAndDateAPI(info);
							logs.setLogsDate(info.getBeginTime().replace("-", "年") + "月");// 时间
							logs.setSN(info.getSN());// SN
							if (logs == null)
							{
								// logs.setIfLogin("否");
								// logs.setIfLoginSuccess("否");
								// logs.setFlowDistinction("0");
								// resultDeviceLogsall.add(logs);
							}
							else
							{
								logs.setIfLogin("是");
								logs.setIfLoginSuccess("是");
								logs.setFlowDistinction(logs.getFlowDistinction());
								resultDeviceLogsall.add(logs);
							}
						}
						catch (Exception e)
						{
							end = "{\"Code\":" + "\"01\"" + ",\"Msg\":" + "\"数据操作错误，请稍后重试！\"" + ",\"Data\":" + null + "}";
							e.printStackTrace();
						}
						System.out.println("同年同月,，，，遍历结束！");
					}
					else
					{
						// 结束月 减 开始月
						System.out.println("同年不同月！");

						int month = Integer.parseInt(endmonth) - Integer.parseInt(beginmonth);

						int years = Integer.parseInt(info.getBeginTime().substring(0, 4));
						System.out.println("年" + years);
						DeviceLogsTest resultDeviceLogs = new DeviceLogsTest();

						for (int i = 0; i <= month; i++)
						{

							List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
							String yue = (Integer.parseInt(beginmonth) + i) + "";
							if (yue.length() == 1) yue = "0" + yue;
							String endpar = years + "-" + yue;
							System.out.println(endpar);
							info.setLogsDate(endpar);
							try
							{
								resultDeviceLogs = deviceLogsSer.getflowBySnAndDateAPI(info);
								DeviceLogsTest logs = new DeviceLogsTest();// new
																			// add
																			// bean
								logs.setLogsDate(endpar.replace("-", "年") + "月");// 时间
								logs.setSN(info.getSN());// SN
								if (resultDeviceLogs == null)
								{
									// logs.setIfLogin("否");
									// logs.setIfLoginSuccess("否");
									// logs.setFlowDistinction("0");
									// resultDeviceLogsone.add(logs);
								}
								else
								{
									logs.setIfLogin("是");
									logs.setIfLoginSuccess("是");
									logs.setFlowDistinction(resultDeviceLogs.getFlowDistinction());
									resultDeviceLogsone.add(logs);
								}
								resultDeviceLogsall.addAll(resultDeviceLogsone);
							}
							catch (Exception e)
							{
								end = "{\"Code\":" + "\"01\"" + ",\"Msg\":" + "\"数据操作错误，请稍后重试！\"" + ",\"Data\":" + null + "}";
								e.printStackTrace();
							}
						}
					}
				}
				else
				{// 年份不同
					// 一次只能查询只能是两年内的数据
					System.out.println("不同年！");
					// 开始年月 到 本年结束 遍历完成 再遍历 结束年一月份到结束年月
					String beginyearmonth = "";
					DeviceLogsTest resultDeviceLogs = new DeviceLogsTest();
					for (int i = Integer.parseInt(beginmonth); i <= 12; i++)
					{
						if (i < 10) beginmonth = "0" + i;
						else beginmonth = i + "";
						beginyearmonth = beginyear + "-" + beginmonth;
						System.out.println(beginyearmonth);
						info.setLogsDate(beginyearmonth);
						try
						{

							List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
							resultDeviceLogs = deviceLogsSer.getflowBySnAndDateAPI(info);
							DeviceLogsTest logs = new DeviceLogsTest();// new
																		// add
																		// bean
							logs.setLogsDate(beginyearmonth.replace("-", "年") + "月");// 时间
							logs.setSN(info.getSN());// SN
							if (resultDeviceLogs == null)
							{
								// logs.setIfLogin("否");
								// logs.setIfLoginSuccess("否");
								// logs.setFlowDistinction("0");
								// resultDeviceLogsone.add(logs);
							}
							else
							{
								logs.setIfLogin("是");
								logs.setIfLoginSuccess("是");
								logs.setFlowDistinction(resultDeviceLogs.getFlowDistinction());
								resultDeviceLogsone.add(logs);
							}
							resultDeviceLogsall.addAll(resultDeviceLogsone);
						}
						catch (Exception e)
						{
							end = "{Code:" + "\"01\"" + ",Msg:" + "\"数据操作错误，请稍后重试！\"" + ",Data:" + null + "}";
							e.printStackTrace();
						}
					}
					System.out.println("end开始！");
					String endmonths = "";
					String endyearmonth = "";
					for (int j = 1; j <= Integer.parseInt(endmonth); j++)
					{
						if (j < 10) endmonths = "0" + j;
						else endmonths = j + "";
						endyearmonth = endyear + "-" + endmonths;
						System.out.println(endyearmonth);
						info.setLogsDate(endyearmonth);
						try
						{

							List<DeviceLogsTest> resultDeviceLogsone = new ArrayList<DeviceLogsTest>();
							resultDeviceLogs = deviceLogsSer.getflowBySnAndDateAPI(info);
							DeviceLogsTest logs = new DeviceLogsTest();// new
																		// add
																		// bean
							logs.setLogsDate(endyearmonth.replace("-", "年") + "月");// 时间
							logs.setSN(info.getSN());// SN
							if (resultDeviceLogs == null)
							{
								// logs.setIfLogin("否");
								// logs.setIfLoginSuccess("否");
								// logs.setFlowDistinction("0");

								// resultDeviceLogsone.add(logs);
							}
							else
							{
								logs.setIfLogin("是");
								logs.setIfLoginSuccess("是");
								logs.setFlowDistinction(resultDeviceLogs.getFlowDistinction());
								resultDeviceLogsone.add(logs);
							}
							resultDeviceLogsall.addAll(resultDeviceLogsone);
						}
						catch (Exception e)
						{
							end = "{\"Code\":" + "\"01\"" + ",\"Msg\":" + "\"数据操作错误，请稍后重试！\"" + ",\"Data\":" + null + "}";
							e.printStackTrace();
						}
					}
				}
				request.setAttribute("resultDeviceLogsall", resultDeviceLogsall);
				net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(resultDeviceLogsall);
				end = "{\"Code\":" + "\"00\"" + ",\"Msg\":" + "\"成功！\"" + ",\"data\":" + jsonArray.toString() + "}";
				System.out.println(end);
				// end = end+end+jsonArray.toString();
				response.getWriter().print(end);
				// return end;
			}
		}

	}



	/**
	 * 暂停流量订单接
	 *
	 * @param isAll
	 * @param password
	 * @param SN
	 * @param distributorID
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/pauseOrders")
	public void pauseOrders(String isAll, String password, String SN, String distributorID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		PrintWriter out = response.getWriter();
		if (SN.length() != 15 && distributorID.length() != 3 && password.equals("") && isAll.equals(""))
		{
			out.print("{\"error\":\"01\",\"isSuccess\":FALSE,\"msg\":\"参数验证失败，请检查参数重试！\"}");
			return;
		}
		DeviceInfo device = new DeviceInfo();
		device.setSN(SN);
		device.setDistributorID(distributorID);
		device.setModifyUserID(distributorID);

		Distributor distributor = new Distributor();
		distributor.setPassword(DES.toHexString(DES.encrypt(password, Constants.DES_KEY)));
		distributor.setDistributorID(distributorID);
		int checkqds = 0;
		try
		{
			checkqds = deviceInfoSer.ckqdsforsn(device);// 验证设备是否归此渠道商所有
			if (checkqds > 0)
			{
				System.out.println("渠道商设备验证成功！");
				int checkpwd = 0;
				try
				{
					checkpwd = distributorSer.checkdispwd(distributor);// 验证 渠道商
																		// 密码
					if (checkpwd > 0)
					{
						System.out.println("渠道商密码验证成功......");
						// 进入验证订单....
						int flowcheck = 0;
						try
						{
							FlowDealOrders flow = new FlowDealOrders();
							flow.setSN(SN);
							flow.setCreatorUserID(distributorID);
							flowcheck = flowDealOrdersSer.getIsFlowUseOrderForSnqds(flow);// 验证是否有正在使用的流量订单
																							// 且该订单是此渠道商创建
							if (flowcheck > 0)
							{
								System.out.println("渠道商订单验证成功...");
								int flowpause = 0;
								try
								{
									if (isAll.equals("1"))
									{
										flowpause = flowDealOrdersSer.pauseOrdersOne(device);// 暂停最近的一个订单
										if (flowpause > 0)
										{
											logger.info("暂停订单成功！");
											out.print("{\"error\":\"05\",\"isSuccess\":TRUE,\"msg\":\"暂停订单成功！\"}");
											return;
										}
										else
										{
											logger.info("暂停订单失败！");
											out.print("{\"error\":\"04\",\"isSuccess\":FALSE,\"msg\":\"暂停订单失败！\"}");
											return;
										}
									}
									else
									{
										flowpause = flowDealOrdersSer.pauseOrders(device);// 暂停
																							// 满足条件全部
																							// 使用中的订单
										if (flowpause > 0)
										{
											logger.info("暂停订单成功！");
											out.print("{\"error\":\"05\",\"isSuccess\":TRUE,\"msg\":\"暂停订单成功！\"}");
											return;
										}
										else
										{
											logger.info("暂停订单失败！");
											out.print("{\"error\":\"04\",\"isSuccess\":FALSE,\"msg\":\"暂停订单失败！\"}");
											return;
										}
									}
								}
								catch (Exception e)
								{
									e.printStackTrace();
									logger.info("暂停订单时出错，暂停失败！");
									out.print("{\"error\":\"04\",\"isSuccess\":FALSE,\"msg\":\"暂停订单时出错,暂停订单失败！\"}");
									return;
								}
							}
							else
							{
								logger.info("没有正在使用中的订单，不存在暂停操作！");
								out.print("{\"error\":\"03\",\"isSuccess\":FALSE,\"msg\":\"没有正在使用的订单，不存在暂停操作！\"}");
								return;
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
							logger.info("\"验证订单时出错，请稍后重试！" + e.getMessage());
							out.print("{\"error\":\"03\",\"isSuccess\":FALSE,\"msg\":\"验证订单时出错，请稍后重试！\"}");
							return;
						}
					}
					else
					{
						logger.info("渠道商密匙验证失败,修改后请稍后重试！");
						out.print("{\"error\":\"01\",\"isSuccess\":FALSE,\"msg\":\"渠道商密匙验证失败,修改后请稍后重试！\"}");
						return;
					}
				}
				catch (Exception e1)
				{
					logger.info("渠道商设备SN验证失败,修改后请稍后重试!" + e1.getMessage());
					e1.printStackTrace();
					out.print("{\"error\":\"01\",\"isSuccess\":FALSE,\"msg\":\"渠道商密匙验证时出错,修改后请稍后重试！\"}");
					return;
				}
			}
			else
			{
				logger.info("渠道商设备SN验证失败,修改后请稍后重试!");
				out.print("{\"error\":\"02\",\"isSuccess\":FALSE,\"msg\":\"渠道商设备SN验证失败,修改后请稍后重试！\"}");
				return;
			}
		}
		catch (Exception e)
		{
			logger.info("验证渠道商设备时出错！");
			e.printStackTrace();
			out.print("{\"error\":\"02\",\"isSuccess\":FALSE,\"msg\":\"验证渠道商设备时出错！\"}");
			return;
		}
	}



	/**
	 * 渠道商新增流量订单接口
	 *
	 * @param flowDealOrders
	 * @param distributor
	 * @param phone
	 * @param customerName
	 * @param address
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addFlowDealOrder")
	public void addFlowDealOrder(FlowDealOrders flowDealOrders, Distributor distributor, String phone, String customerName, String address, HttpServletResponse response) throws Exception
	{

		request.setCharacterEncoding("utf-8");

		response.setContentType("application/json;charset=UTF-8");

		String method = request.getMethod();

		if ("GET".equals(method))
		{

			if (StringUtils.isNotBlank(flowDealOrders.getCustomerName()))
			{
				customerName = new String(flowDealOrders.getCustomerName().getBytes("ISO-8859-1"), "UTF-8");
			}
			if (StringUtils.isNotBlank(flowDealOrders.getUserCountry()))
			{
				flowDealOrders.setUserCountry(new String(flowDealOrders.getUserCountry().getBytes("ISO-8859-1"), "UTF-8"));
			}
			if (StringUtils.isNotBlank(address))
			{
				address = new String(address.getBytes("ISO-8859-1"), "utf-8");
			}
			if (StringUtils.isNotBlank(flowDealOrders.getJourney()))
			{
				flowDealOrders.setJourney(new String(flowDealOrders.getJourney().getBytes("ISO-8859-1"), "UTF-8"));
			}
			if (StringUtils.isNotBlank(flowDealOrders.getRemark()))
			{
				flowDealOrders.setRemark(new String(flowDealOrders.getRemark().getBytes("ISO-8859-1"), "UTF-8"));
			}
		}

		PrintWriter out = response.getWriter();
		// 校验参数
		if (distributor.getDistributorID().length() != 3 || StringUtils.isBlank(distributor.getPassword()) || StringUtils.isBlank(flowDealOrders.getUserCountry()) || StringUtils.isBlank(phone) || StringUtils.isBlank(customerName) || StringUtils.isBlank(flowDealOrders.getOrderType()) || StringUtils.isBlank(flowDealOrders.getPanlUserDate()) || (StringUtils.isBlank(flowDealOrders.getSN()) || flowDealOrders.getSN().length() != 15) || StringUtils.isBlank(flowDealOrders.getFlowDays()))
		{
			out.print("{\"Code\":\"01\",\"Msg\":\"参数校验失败，请检查参数重试！\"}");
			return;
		}
		// 校验此渠道商密码
		distributor.setPassword(DES.toHexString(DES.encrypt(distributor.getPassword(), Constants.DES_KEY)));
		Distributor user = distributorSer.checkDistributor(distributor);
		if (user != null)
		{
			logger.info("渠道商验证成功...");
			try
			{
				ts = pm.getTransaction(null);
				// 客户
				List<CustomerInfo> customerinfo = customerInfoSer.selectOneCustomerinfoPhonetwo(phone);
				String customerID = StringUtils.getDistributorID(user.getDistributorID());
				if (customerinfo == null || customerinfo.size() <= 0)
				{
					// 没有查到手机号，插入客户数据后再下单
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setCustomerID(customerID);
					customerInfo.setCustomerName(customerName);
					customerInfo.setPhone(phone);
					customerInfo.setPassword(DES.toHexString(DES.encrypt("88888888", Constants.DES_KEYWEB)));
					customerInfo.setCreatorUserID(user.getDistributorID());
					customerInfo.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
					customerInfo.setSysStatus(1);// 默认就是 1
					// 插入客户信息
					customerInfoSer.insertCustomerInfo(customerInfo);
				}
				else if (!customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					out.print("{\"Code\":\"07\",\"Msg\":\"该手机号码重复\"}");
					return;
				}
				else if (customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					customerID = customerinfo.get(0).getCustomerID();
				}
				// 设备信息
				String deviceID = StringUtils.getDistributorID(user.getDistributorID());// 设备id
				DeviceInfo deviceInfo = new DeviceInfo();
				deviceInfo.setSN(flowDealOrders.getSN());
				deviceInfo.setDistributorID(user.getDistributorID());
				DeviceInfo deviceInfo1 = deviceInfoSer.selectDeviceInfoByDis(deviceInfo);

				if (deviceInfo1 != null)
				{
					if ("不可用".equals(deviceInfo1.getDeviceStatus()))
					{
						out.print("{\"Code\":\"04\",\"Msg\":\"设备状态为不可用\"}");
						return;
					}
					// -1表示SN不存在。-2表示SN使用中,0表示SN或漫游卡不支持国家，1表示可以被下单
					int status = deviceInfoSer.verifySN(flowDealOrders.getSN(), flowDealOrders.getUserCountry());
					if (status == 0)
					{
						// 表示SN或漫游卡不支持国家
						out.print("{\"Code\":\"11\",\"Msg\":\"表示SN或漫游卡不支持国家\"}");
						return;
					}

					// 设备为使用中判断是否可以再次下单
					// 国家相同，时单不同可以下单，时间相同，国家不同，可以被 下单
					int result = deviceInfoSer.affirmIScreatorOrder(flowDealOrders);

					if (result == -1)
					{
						// 不可以被 下单
						out.print("{\"Code\":\"10\",\"Msg\":\"请确认同一国家订单，使用时间是否有重复\"}");
						return;
					}
				}
				else
				{
					// 没有这个设备 ---返回错误信息
					out.print("{\"Code\":\"09\",\"Msg\":\"此设备不存在\"}");
					return;
				}
				// 国家
				String[] country = flowDealOrders.getUserCountry().split(",");
				int length = country.length;
				String UserCountry = "";// 拼接使用国家信息
				double flowPrice = 0;
				for (int j = 0; j < country.length; j++)
				{
					// 查询国家是否存在，如何不存在提示国家名称不存在
					String c_Name = country[j];
					CountryInfo countryInfo = new CountryInfo();
					countryInfo.setCountryName(c_Name.trim());
					List<CountryInfo> contryInfos = countryInfoSer.getcountryinfoBycountryname(countryInfo);
					int size = contryInfos.size();
					if (contryInfos == null || contryInfos.size() <= 0)
					{
						// 国家名称不存在
						out.print("{\"Code\":\"05\",\"Msg\":\"国家名称填写错误\"}");
						return;
					}
					else
					{
						if (flowPrice < contryInfos.get(0).getFlowPrice())
						{
							flowPrice = contryInfos.get(0).getFlowPrice();
						}
						if (j == country.length - 1)
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice();
						}
						else
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice() + "|";
						}
					}
				}
				flowPrice = flowPrice * flowDealOrders.getFlowDays();

				// 总订单 插入一条记录
				OrdersInfo orders = new OrdersInfo();
				String orderID = StringUtils.getDistributorID(user.getDistributorID());
				orders.setOrderID(orderID);
				orders.setCustomerID(customerID);
				orders.setCustomerName(customerName);
				orders.setDeviceDealCount(1);
				orders.setIfFinish("是");
				orders.setOrderStatus("已付款");
				orders.setOrderSource("渠道商");
				orders.setShipmentsStatus("已发货");
				orders.setFlowDealCount(1);
				orders.setOrderAmount(flowPrice + 500);
				orders.setAddress(address);
				orders.setCreatorUserID(user.getDistributorID());
				orders.setCreatorUserName(user.getCompany());
				orders.setSysStatus(true);
				Boolean count = ordersInfoSer.insertinfo(orders);
				logger.info("总订单增加成功");
				// 设备订单
				String deviceDealID = StringUtils.getDistributorID(user.getDistributorID());
				DeviceDealOrders deviceDeal = new DeviceDealOrders();
				deviceDeal.setDeviceDealID(deviceDealID);
				deviceDeal.setOrderID(orderID);
				deviceDeal.setSN(flowDealOrders.getSN());
				deviceDeal.setCustomerName(customerName);
				deviceDeal.setDeviceID(deviceID);
				deviceDeal.setCustomerID(customerID);
				deviceDeal.setDeallType("租用");
				deviceDeal.setDealAmount(500);
				deviceDeal.setIfFinish("是");
				deviceDeal.setIfReturn("否");
				deviceDeal.setOrderStatus("使用中");
				deviceDeal.setCreatorUserID(user.getDistributorID());
				deviceDeal.setCreatorUserName(user.getCompany());
				deviceDeal.setSysStatus(true);
				count = deviceDealOrdersSer.insertinfo(deviceDeal);
				// 将设备信息表中设备状态改为使用中
				count = deviceInfoSer.updateDeviceOrder(flowDealOrders.getSN());
				logger.info("设备订单增加成功");
				// 流量订单
				String flowDealOrderID = StringUtils.getDistributorID(user.getDistributorID());
				FlowDealOrders flowDeal = new FlowDealOrders();
				flowDeal.setFlowDealID(flowDealOrderID);
				flowDeal.setOrderID(orderID);
				flowDeal.setCustomerID(customerID);
				flowDeal.setCustomerName(customerName);
				flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.DateToStr(new Date())));
				flowDeal.setUserCountry(UserCountry);
				flowDeal.setPanlUserDate(flowDealOrders.getPanlUserDate()+" 00:00:00"); // 预约时间
				flowDeal.setOrderStatus("可使用");
				flowDeal.setIfFinish("是");
				flowDeal.setRemark(flowDealOrders.getRemark());
				flowDeal.setFlowDays(flowDealOrders.getFlowDays());// 流量天数
				flowDeal.setDaysRemaining(flowDealOrders.getFlowDays());// 剩余天数
				flowDeal.setOrderAmount(flowPrice);// 订单金额
				String expireDate = DateUtils.getcurDate(DateUtils.beforeNDateToString(DateUtils.parseDate(flowDealOrders.getPanlUserDate()), flowDealOrders.getFlowDays(), "yyyy-MM-dd HH:mm:ss"), 1);
				flowDeal.setFlowExpireDate(expireDate);// 流量到期时间
				flowDeal.setSN(flowDealOrders.getSN());
				flowDeal.setIfActivate("是");// 是否激活
				flowDeal.setActivateDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));// 激活时间为当前时间
				flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
				flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
				flowDeal.setFlowUsed(0);
				flowDeal.setOrderType(flowDealOrders.getOrderType());
				flowDeal.setFlowTotal(flowDealOrders.getFlowTotal());
				flowDeal.setFactoryFlag(0);// 工厂标识
				flowDeal.setCreatorUserID(user.getDistributorID());
				flowDeal.setCreatorUserName(user.getCompany());
				flowDeal.setSysStatus(true);
				flowDeal.setSpeedStr(flowDealOrders.getSpeedStr());
				flowDeal.setJourney(flowDealOrdersSer.getJourney(flowDealOrders.getJourney()));
				count = flowDealOrdersSer.insertinfo(flowDeal);
				LOGGER.info("流量订单增加成功");
				// 订单创建成功返回流量订单信息
				JSONArray array = new JSONArray();
				JSONObject obj = JSONObject.fromObject(flowDeal);
				array.add(obj);
				JSONObject object = new JSONObject();
				object.put("Code", "00");
				object.put("Msg", "创建订单成功");
				object.put("data", array);
				out.print(object.toString());
				pm.commit(ts);
				return;
			}
			catch (Exception e)
			{
				pm.rollback(ts);
				e.printStackTrace();
				out.print("{\"Code\":\"06\",\"Msg\":\"创建订单失败\"}");
			}
		}
		else
		{
			logger.info("渠道商密匙验证失败，请重新输入参数校验");
			out.print("{\"Code\":\"02\",\"Msg\":\"渠道商密匙验证失败，请重新输入参数校验\"}");
			return;
		}
	}



	/**
	 * 编辑流量订单接口
	 *
	 * @param flowDealOrders
	 * @param distributor
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping("/editFlowDealOrder")
	public void editFlowDealOrder(FlowDealOrders flowDealOrders, Distributor distributor, HttpServletResponse response) throws Exception
	{
		String method = request.getMethod();

		PrintWriter out = response.getWriter();
		// 校验参数
		if (StringUtils.isBlank(distributor.getDistributorID()) || StringUtils.isBlank(distributor.getPassword()) || StringUtils.isBlank(flowDealOrders.getFlowDealID()) || StringUtils.isBlank(flowDealOrders.getUserCountry()) || StringUtils.isBlank(flowDealOrders.getFlowDays()))
		{
			out.print("{\"Code\":\"02\",\"Msg\":\"参数效验失败！\"}");
			return;
		}
		// 校验渠道商身份
		distributor.setPassword(DES.toHexString(DES.encrypt(distributor.getPassword(), Constants.DES_KEY)));
		Distributor user = distributorSer.checkDistributor(distributor);
		if (user != null)
		{
			logger.info("渠道商身份验证通过...");
			if ("GET".equals(method))
			{
				if (StringUtils.isNotBlank(flowDealOrders.getUserCountry()))
				{
					flowDealOrders.setUserCountry(new String(flowDealOrders.getUserCountry().getBytes("ISO-8859-1"), "UTF-8"));
				}
				if (StringUtils.isNotBlank(flowDealOrders.getJourney()))
				{
					flowDealOrders.setJourney(new String(flowDealOrders.getJourney().getBytes("ISO-8859-1"), "UTF-8"));
				}
				if (StringUtils.isNotBlank(flowDealOrders.getRemark()))
				{
					flowDealOrders.setRemark(new String(flowDealOrders.getRemark().getBytes("ISO-8859-1"), "UTF-8"));
				}
			}
			String[] country = flowDealOrders.getUserCountry().split(",");
			String UserCountry = "";
			for (int j = 0; j < country.length; j++)
			{
				String c_Name = country[j];
				CountryInfo countryInfo = new CountryInfo();
				countryInfo.setCountryName(c_Name);
				List<CountryInfo> contryInfos = countryInfoSer.getcountryinfoBycountryname(countryInfo);
				if (contryInfos == null || contryInfos.size() <= 0)
				{
					out.print("{\"Code\":\"04\",\"Msg\":\"国家名称填写错误\"}");
					return;
				}
				else
				{
					if (j == country.length - 1)
					{
						UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice();
					}
					else
					{
						UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice() + "|";
					}
				}
			}
			flowDealOrders.setUserCountry(UserCountry);

			//flowDealOrders.setMinsRemaining(null);
			boolean isSuccess = flowDealOrdersSer.editsaveauto(flowDealOrders);



			if (isSuccess)
			{
				logger.info("订单信息编辑成功...");
				out.print("{\"Code\":\"00\",\"Msg\":\"订单编辑成功...\"}");
				return;

			}
			else
			{
				out.print("{\"Code\":\"01\",\"Msg\":\"编辑订单失败...\"}");
				return;
			}
		}
		else
		{
			out.print("{\"Code\":\"03\",\"Msg\":\"渠道商密码验证失败,请检查参数后重试！\"}");
			return;
		}
	}



	/**
	 * 删除流量订单接口
	 *
	 * @param flowDealOrders
	 * @param distributor
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delFlowDealOrder")
	public void delFlowDealOrder(FlowDealOrders flowDealOrders, Distributor distributor, HttpServletResponse response) throws Exception
	{

		PrintWriter out = response.getWriter();
		// 参数校验
		if (StringUtils.isBlank(flowDealOrders.getFlowDealID()) || StringUtils.isBlank(distributor.getDistributorID()) || StringUtils.isBlank(distributor.getPassword()))
		{
			out.print("{\"Code\":\"02\",\"Msg\":\"参数校验失败\"}");
			return;
		}
		// 渠道商身份验证
		distributor.setPassword(DES.toHexString(DES.encrypt(distributor.getPassword(), Constants.DES_KEY)));
		Distributor user = distributorSer.checkDistributor(distributor);
		if (user != null)
		{
			logger.info("渠道商身份验证通过");
			// 通过订单ID判断此设备是否为此渠道商
			if (flowDealOrders.getFlowDealID().contains(user.getDistributorID()))
			{
				// 进来说 明设备是此渠道商，并进行删除
				boolean isDel = flowDealOrdersSer.delfloworder(flowDealOrders.getFlowDealID());
				if (isDel)
				{
					logger.info("id为" + user.getDistributorID() + "的渠道商删除流量订单成功...");
					out.print("{\"Code\":\"00\",\"Msg\":\"删除订单成功...\"}");
					return;
				}
				else
				{
					logger.info("id为" + user.getDistributorID() + "的渠道商删除流量订单失败...");
					out.print("{\"Code\":\"01\",\"Msg\":\"删除订单失败...\"}");
					return;
				}
			}
			else
			{
				out.print("{\"Code\":\"04\",\"Msg\":\"设备不存在...\"}");
				return;
			}

		}
		else
		{
			out.print("{\"Code\":\"03\",\"Msg\":\"渠道商密码验证失败,请检查参数后重试！\"}");
			return;
		}

	}

}
