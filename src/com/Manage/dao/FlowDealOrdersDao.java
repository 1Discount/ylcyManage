package com.Manage.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.record.formula.functions.Int;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Manage.c3p0.SqlSession;
import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.common.util.ByteUtils.StringValueConversionException;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.PrepareCardTemp;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.service.DeviceFlowSer;
import com.Manage.service.FlowDealOrdersSer;

/**
 * * @author wangbo: * @date 创建时间：2015-5-29 上午9:12:46 * @version 1.0 * @parameter
 * * @since * @return
 */
@Repository
public class FlowDealOrdersDao extends BaseDao<FlowDealOrdersDao> {
	private static final String NAMESPACE = FlowDealOrdersDao.class.getName()
			+ ".";

	@Autowired
	private FlowDealOrdersSer flowDealOrdersSer;
	@Resource
	private SqlSession sqlSession;

	/**
	 * 插入流量订单
	 * 
	 * @param ordersInfo
	 * @return
	 */
	public int insertinfo(FlowDealOrders flowordersInfo) {
		try {
			return getSqlSession().insert(NAMESPACE + "insertinfo",
					flowordersInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

	/**
	 * 更新流量订单完成
	 * 
	 * @param orderID
	 * @return
	 */
	public int updateiffinish(FlowDealOrders flowordersInfo) {
		try {
			return getSqlSession().update(NAMESPACE + "updateiffinish",
					flowordersInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 删除
	 * 
	 * @param flowDealID
	 * @return
	 */
	public int delfloworder(String flowDealID) {
		try {
			return getSqlSession().update(NAMESPACE + "delfloworder",
					flowDealID);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 根据订单ID删除
	 * 
	 * @param orderID
	 * @return
	 */
	public int delfloworderbyoid(String orderID) {
		try {
			return getSqlSession().update(NAMESPACE + "delfloworderbyoid",
					orderID);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 查询列表
	 * 
	 * @param oid
	 * @return
	 */
	public List<FlowDealOrders> getlistbyoid(String oid) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getbyoid", oid);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO) {

		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<FlowDealOrders> arr = (List<FlowDealOrders>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(
						a.getUserCountry());
				String cnames = wrapper.getCountryNameStrings();
				obj.remove("userCountry");
				obj.put("userCountry", cnames);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);
				Date mdate = a.getModifyDate();
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}
				if (StringUtils.isNotBlank(a.getOrderSource())) {
					obj.put("orderSource", a.getOrderSource());
				} else {
					String orderID = a.getOrderID();
					if (orderID.contains("GW")) {
						obj.put("orderSource", "官网");
					} else if (orderID.contains("AP")) {
						obj.put("orderSource", "APP");
					} else if (orderID.contains("WX")) {
						obj.put("orderSource", "微信");
					} else if (orderID.contains("YZ")) {
						obj.put("orderSource", "有赞");
					} else if (orderID.contains("AP")) {
						obj.put("orderSource", "APP");
					} else {
						obj.put("orderSource", "后台");
					}
				}

				if ("是".equals(((FlowDealOrders) searchDTO.getObj())
						.getIsUserd())) {
					// 如果是查已使用才会进入到这里来
					DeviceFlow deviceFlow = new DeviceFlow();

					deviceFlow.setFlowOrderID(a.getFlowDealID());
					deviceFlow.setFlowValue(Integer
							.parseInt(((FlowDealOrders) searchDTO.getObj())
									.getTargerFlow())
							* 1024 + "");
					String userCountry = "";
					// 根据订单ID获取到该条订单的使用流量情况
					List<DeviceFlow> deviceFlows = getSqlSession().selectList(
							"com.Manage.dao.DeviceFlowDao.getUserDayGroupMCC",
							deviceFlow);
					int tatalUserDay = 0;
					String userDays = "";
					Double userPrice = 0.0;
					String mccString = "";
					for (DeviceFlow deviceFlow2 : deviceFlows) {
						tatalUserDay += Integer.parseInt(deviceFlow2
								.getUserDays());
						userDays += deviceFlow2.getUserDays() + "，";
						String mcc = deviceFlow2.getMCC();
						mccString += mcc + "，";

						String[] userCountryArray = deviceFlow2
								.getUserCountry().split("\\|");
						for (int i = 0; i < userCountryArray.length; i++) {
							String U_countryOne = userCountryArray[i];
							if (U_countryOne.contains(mcc)) {
								int index = U_countryOne.indexOf(",");
								deviceFlow2.setCountryName(U_countryOne
										.substring(0, index));
							}
						}
						userCountry += deviceFlow2.getCountryName() + "，";
					}

					if (StringUtils.isNotBlank(userCountry)) {
						userCountry = userCountry.substring(0,
								userCountry.length() - 1);
					}

					if (StringUtils.isNotBlank(userDays)) {
						userDays = userDays.substring(0, userDays.length() - 1);
					}

					if (StringUtils.isNotBlank(mccString)) {
						mccString = mccString.substring(0,
								mccString.length() - 1);
					}

					// **********对于实际使用天数大于订单可使用的情况进行处理begin*******************
					if (tatalUserDay > a.getFlowDays()) {
						String[] userDayArr = userDays.split("，");
						if (userDayArr.length == 1) {
							// 获取到最后一个使用天数
							int lastUserDay = Integer
									.parseInt(userDayArr[userDayArr.length - 1]) - 1;
							userDays = "<a class=\"btn btn-warning btn-xs\">"
									+ lastUserDay + "</a>";
						} else {
							// 获取到最后一个使用天数
							int lastUserDay = Integer
									.parseInt(userDayArr[userDayArr.length - 1]) - 1;
							userDays = userDays.substring(0,
									userDays.length() - 1) + (lastUserDay + "");
							if (userDays.endsWith("0")) {
								userDays = "<a class=\"btn btn-warning btn-xs\">"
										+ userDays.substring(0,
												userDays.length() - 2) + "</a>";
								userCountry = userCountry.substring(0,
										userCountry.lastIndexOf("，"));
							}
						}
					}
					// ***********对于实际使用天数大于订单可使用的情况进行处理end******************

					obj.put("userDays", userDays);

					obj.put("isuserCountry", userCountry);

					// *****************已使用金额Begin******************
					String company = ((FlowDealOrders) searchDTO.getObj())
							.getDistributorName();
					Distributor distributor = new Distributor();
					distributor.setCompany(company);

					if (StringUtils.isNotBlank(company)
							&& StringUtils.isNotBlank(userDays)) {
						// 获取渠道价格配置
						Distributor disInfo = null;
						try {
							disInfo = getSqlSession()
									.selectOne(
											"com.Manage.dao.DistributorDao.getdisInofbycompany",
											distributor);
						} catch (Exception e) {
							throw new BmException(Constants.common_errors_1004,
									e);
						}
						String[] priceConfigurationArray = null;
						boolean countryExits = true;
						int isconfiguration = 0;
						if (StringUtils.isNotBlank(disInfo
								.getPriceConfiguration())) {
							priceConfigurationArray = disInfo
									.getPriceConfiguration().split("\\|");
							for (int i = 0; i < userCountry.split("，").length; i++) {
								for (int j = 0; j < priceConfigurationArray.length; j++) {
									if (priceConfigurationArray[j].split("-")[1]
											.contains(mccString.split("，")[i])) {
										userPrice += Double
												.parseDouble(userDays
														.replace(
																"<a class=\"btn btn-warning btn-xs\">",
																"")
														.replace("</a>", "")
														.split("，")[i])
												* Double.parseDouble(priceConfigurationArray[j]
														.split("-")[2]);
										break;
									} else {
										if (j == priceConfigurationArray.length - 1) {
											countryExits = false;
										}
									}
								}
							}
						} else {
							countryExits = false;
							isconfiguration = 1;
						}
						if (countryExits) {
							obj.put("userPrice", userPrice);
						} else {
							if (isconfiguration == 1) {
								obj.put("userPrice", "");// 如果没有价格配置，则前端会显示空字符串
							} else {
								obj.put("userPrice",
										"<a class=\"btn btn-warning btn-xs\">"
												+ userPrice + "</a>"); // 如果价格配置中，某些国家没有配置价格，则前端显示标黄
							}

						}
					} else {
						obj.put("userPrice", "");// 使用天数为空，则前端显示空字符串
					}
					// *****************已使用金额end******************
				}
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 企业用户分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageenterprise(SearchDTO searchDTO) {

		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<FlowDealOrders> arr = (List<FlowDealOrders>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(
						a.getUserCountry());
				String cnames = wrapper.getCountryNameStrings();
				obj.remove("userCountry");
				obj.put("userCountry", cnames);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);
				Date mdate = a.getModifyDate();
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}
				String orderID = a.getOrderID();
				if (orderID.contains("GW")) {
					obj.put("orderSource", "官网");
				} else if (orderID.contains("AP")) {
					obj.put("orderSource", "APP");
				} else if (orderID.contains("WX")) {
					obj.put("orderSource", "微信");
				} else if (orderID.contains("YZ")) {
					obj.put("orderSource", "有赞");
				} else if (orderID.contains("AP")) {
					obj.put("orderSource", "APP");
				} else {
					obj.put("orderSource", "后台");
				}

				if ("是".equals(((FlowDealOrders) searchDTO.getObj())
						.getIsUserd())) {
					// 如果是查已使用才会进入到这里来
					DeviceFlow deviceFlow = new DeviceFlow();

					deviceFlow.setFlowOrderID(a.getFlowDealID());
					deviceFlow.setFlowValue(((FlowDealOrders) (searchDTO
							.getObj())).getTargerFlow());
					String userCountry = "";
					// 根据订单ID获取到该条订单的使用流量情况
					List<DeviceFlow> deviceFlows = getSqlSession().selectList(
							"com.Manage.dao.DeviceFlowDao.getUserDayGroupMCC",
							deviceFlow);

					String userDays = "";

					Double userPrice = 0.0;

					String priceConfiguration = ((FlowDealOrders) searchDTO
							.getObj()).getPriceConfiguration();
					JSONObject jb = JSONObject.fromObject(priceConfiguration);

					for (DeviceFlow deviceFlow2 : deviceFlows) {
						userDays += deviceFlow2.getUserDays() + "，";

						String mcc = deviceFlow2.getMCC();

						userPrice += Double.parseDouble(deviceFlow2
								.getUserDays())
								* Double.parseDouble(jb.get(mcc).toString());
						String[] userCountryArray = deviceFlow2
								.getUserCountry().split("\\|");

						for (int i = 0; i < userCountryArray.length; i++) {
							String U_countryOne = userCountryArray[i];
							if (U_countryOne.contains(mcc)) {
								int index = U_countryOne.indexOf(",");
								deviceFlow2.setCountryName(U_countryOne
										.substring(0, index));
							}
						}

						userCountry += deviceFlow2.getCountryName() + "，";

					}

					if (StringUtils.isNotBlank(userCountry)) {
						userCountry = userCountry.substring(0,
								userCountry.length() - 1);
					}

					if (StringUtils.isNotBlank(userDays)) {
						userDays = userDays.substring(0, userDays.length() - 1);
					}

					obj.put("userDays", userDays);

					obj.put("isuserCountry", userCountry);

					obj.put("userPrice", userPrice);

				}

				ja.add(obj);

			}

			object.put("data", ja);

			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询渠道商查询已使用/未使用订单 旧
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString1(SearchDTO searchDTO,
			HashMap<String, String> mccNameMap) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDis",
					"queryPageDisCount", searchDTO);
			List<FlowDealOrders> arr = (List<FlowDealOrders>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				String SN = a.getSN();
				String flowDealID = a.getFlowDealID();
				String dString = "";
				Date date = a.getCreatorDate();
				if (StringUtils.isNotBlank(date)) {
					dString = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				}

				String userCountry = "";
				String userDays = "";

				DeviceLogs log = new DeviceLogs();
				log.setSN(SN);
				log.setFlowOrderID(flowDealID);
				log.setBeginTime(((FlowDealOrders) (searchDTO.getObj()))
						.getBegindateForQuery());
				log.setEndTime(((FlowDealOrders) (searchDTO.getObj()))
						.getEnddate());
				log.setFlowDistinction(((FlowDealOrders) (searchDTO.getObj()))
						.getFlowDistinction());
				List<DeviceLogs> logList = getSqlSession()
						.selectList(
								"com.Manage.dao.DeviceLogsTempDao.getdevicelogTempbysn",
								log);

				int totalFlow = 0;
				for (DeviceLogs deviceLogs : logList) {
					String flowDistinction = deviceLogs.getFlowDistinction();

					totalFlow += Integer.parseInt(flowDistinction);

					String mcc = deviceLogs.getMcc();
					if (StringUtils.isNotBlank(mcc)) {

						mcc = mccNameMap.get(mcc);

						userDays += deviceLogs.getUserDays() + "，";

						if (userCountry.indexOf(mcc) == -1) {
							userCountry += "<a style=\"color:#333\" title=\""
									+ deviceLogs.getUserDays() + "\">" + mcc
									+ "</a>，";
						}

					}
				}

				if (StringUtils.isNotBlank(userCountry)) {
					userCountry = userCountry.substring(0,
							userCountry.length() - 1);
				}
				if (StringUtils.isNotBlank(userDays)) {
					userDays = userDays.substring(0, userDays.length() - 1);
				}

				obj.put("totalFlow", totalFlow);
				obj.put("userCountry", userCountry);
				obj.put("userDays", userDays);
				obj.put("creatorDate", dString);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 分页查询 今天可用订单, 需要JOIN 设备订单表
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageAvailableOrderString(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageAvailableOrder",
					"getcount_join", searchDTO);
			List<FlowDealOrders> arr = (List<FlowDealOrders>) page.getRows();
			// List<Map<String,Object>>
			// arr=(List<Map<String,Object>>)page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders a : arr) { // Map<String,Object>
				JSONObject obj = JSONObject.fromObject(a);
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(
						a.getUserCountry()); // (String)a.get("userCountry")
				String cnames = wrapper.getCountryNameStrings();
				obj.remove("userCountry");
				obj.put("userCountry", cnames);
				Date date = a.getCreatorDate(); // (Date)a.get("creatorDate");
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);
				Date mdate = a.getModifyDate(); // (Date)a.get("modifyDate");
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 根据ID查询
	 * 
	 * @param flowID
	 * @return
	 */
	public FlowDealOrders getbyid(String flowID) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getbyid", flowID);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 编辑保存
	 * 
	 * @param orderID
	 * @return
	 */
	public int editsave(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "editsave",
					flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 编辑保存
	 * 
	 * @param orderID
	 * @return
	 */
	public int editsaveauto(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "editsaveauto",
					flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 激活
	 * 
	 * @param floworderid
	 * @return
	 */
	public int activate(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "activate",
					flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 暂停服务
	 * 
	 * @param floworderid
	 * @return
	 */
	public int pause(String floworderid) {
		try {
			return getSqlSession().update(NAMESPACE + "pause", floworderid);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 启动服务
	 * 
	 * @param floworderid
	 * @return
	 */
	public int launch(String floworderid) {
		try {
			return getSqlSession().update(NAMESPACE + "launch", floworderid);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 限速
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public int limitSpeed(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "limitSpeed",
					flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 更新国家
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public int updateCountry(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "updateCountry",
					flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 根据客户ID查询数据交易 lipeng
	 * 
	 * @param customerid
	 * @return
	 */
	public List<FlowDealOrders> getFlowforCustomerid(String customerid) {
		try {
			return getSqlSession().selectList(
					NAMESPACE + "getFlowDealOrdersForCustomerID", customerid);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}

	}

	/**
	 * 统计查询
	 * 
	 * @param deviceDealOrders
	 * @return
	 */
	public Map<String, String> statistics(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "statistics",
					flowDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}

	public Map<String, String> statistics1(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "statistics1",
					flowDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 设备接入认证
	 * 
	 * @param dealOrders
	 * @return
	 */
	public List<FlowDealOrders> getOrdersbysn(FlowDealOrders dealOrders) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getorderbysn",
					dealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}

	/**
	 * 更新流量订单字段
	 * 
	 * @param dealOrders
	 * @return
	 */
	public boolean updatefloworder(FlowDealOrders dealOrders) {
		try {
			int temp = getSqlSession().update(NAMESPACE + "updateorder",
					dealOrders);
			if (temp > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
			// TODO: handle exception
		}
	}

	/**
	 * 根据imsi查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageimsi(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageimsi", "getcountimsi",
					searchDTO);

			List<FlowDealOrders> arr = (List<FlowDealOrders>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	public int updateOrderStatusForSnDao(FlowDealOrders fdo) {
		try {
			return getSqlSession().update(NAMESPACE + "updateOrderStatusForSn",
					fdo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 设备归还将对应的流量订单改为不可用
	 * 
	 * @param fdo
	 * @return
	 */
	public int updatebysn(FlowDealOrders fdo) {
		try {
			return getSqlSession().update(NAMESPACE + "updatebysn", fdo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 根据sn查询流量订单
	 * 
	 * @return
	 */
	public List<FlowDealOrders> selectFlowdeal(FlowDealOrders dealOrders) {
		try {
			return getSqlSession().selectList(NAMESPACE + "selectCountbysn",
					dealOrders);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
			// TODO: handle exception
		}
	}

	/**
	 * 修改flowDealOrders
	 * 
	 * @param flowDealOrders
	 */
	public int updateFlowdealByflowDealID(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(
					NAMESPACE + "updateFlowdealByflowDealID", flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * 修改SN
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public int updateSN(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "updateSN",
					flowDealOrders);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 自动清除所有测试单
	 * 
	 * @return
	 */
	public int updateTestOrder() {
		try {

			return getSqlSession().update(NAMESPACE + "updateTestOrder");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 获取测试订单
	 * 
	 * @return
	 */
	public FlowDealOrders getTestbysn(String SN) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getTestBySN", SN);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 根据国家编号查询流量订单
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public List<FlowDealOrders> getflowdealbycountry(
			FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().selectList(
					NAMESPACE + "getflowdealbycountry", flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<FlowDealOrders> getflowdealbycountry1(
			FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().selectList(
					NAMESPACE + "getflowdealbycountry1", flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 生成随机颜色
	 * 
	 * @return
	 */
	public String getRandColorCode() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return r + g + b;
	}

	public String beika(SearchDTO searchDTO, String parCountryName,
			String parDateTime) {
		Map<String, String> VFmap = new HashMap<>();
		String journeyCountryString = "国家";
		String journeyUserTimeString = "使用时间";
		String sNSUMS = ""; // 所有国家可用的设备
		parDateTime = parDateTime + ":00";

		// 查询sim卡countryList
		SIMInfo simInfo = getSqlSession().selectOne(
				"com.Manage.dao.SIMInfoDao.getVFsimAlias");
		String countryList = simInfo.getCountryList();
		// 获取多国sim卡
		List<SIMInfo> simInfos = getSqlSession().selectList(
				"com.Manage.dao.SIMInfoDao.getVFsimcardCount");

		String FMdatetime = DateUtils
				.DateToStrtwo(
						DateUtils.StrToDatetwo(parDateTime, "yyyy-MM-dd"),
						"yyyy-MM-dd");
		String FMdatetimeProc = DateUtils.DateToStrtwo(
				DateUtils.StrToDatetwo(parDateTime, "yyyy-MM-dd HH:mm"),
				"yyyy-MM-dd HH:mm");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("cName", parCountryName);
		map.put("datetime", FMdatetimeProc);
		map.put("short", searchDTO.getSortOrder());
		List<Map<String, String>> baikaResult = sqlSession.getBeika(NAMESPACE
				+ "beika", map);// getSqlSession().selectList(NAMESPACE +
								// "beika", map);

		JSONArray ja = new JSONArray();
		for (Map m : baikaResult) {
			int flowdealCount = 0;

			JSONObject obj = JSONObject.fromObject(m);

			String countryName = m.get("countrName").toString();
			String countryCode = m.get("countryCode").toString();

			FlowDealOrders flowDealOrders = new FlowDealOrders();
			flowDealOrders.setUserCountry(countryCode);
			flowDealOrders.setPanlUserDate(FMdatetime); // search日期

			// 按国家和日期（搜索日期）获取流量订单的个数 如果有行程就判断搜索当天可不可用，如果可用加入到该国家中，否则则不加
			List<FlowDealOrders> flowDealOrders2 = flowDealOrdersSer
					.getflowdealbycountry(flowDealOrders);

			String SN = "　"; // 单个国家可用设备
			try {
				for (int j = 0; j < flowDealOrders2.size(); j++) {

					FlowDealOrders floOrders = flowDealOrders2.get(j);
					if(floOrders==null||floOrders.getSN()==null||floOrders.getSN().length()<15){
						continue;
					}
					
					String snLastSix = floOrders.getSN().substring(9, 15);

					// System.out.println("订单SN后六位："+snLastSix);

					// 过滤 搜索时间之前过期的订单
					long day = Long.parseLong(new SimpleDateFormat(
							"yyyyMMddHHmmss").format(DateUtils
							.StrToDate(parDateTime)));// 搜索时间
					long intflow = Long.parseLong(new SimpleDateFormat(
							"yyyyMMddHHmmss").format(DateUtils
							.StrToDate(floOrders.getFlowExpireDate())));// 过期时间
					if (intflow < day) {
						continue;
					}

					// 统计订单时需将3模式的订单区分出来，如果行程为空则不统计
					if ("3".equals(floOrders.getOrderType())) {
						if (StringUtils.isBlank(floOrders.getJourney())) {
							continue;
						}
					}

					// 如果订单使用国家包含多国卡，保此订单保存到map集合里
					String[] simAliasArr = countryList.split("\\|");
					System.out.println("simAliasArr.length=="+simAliasArr.length);
					try {
						for (int a = 0; a < simAliasArr.length; a++) {
							System.out.println(simAliasArr[a]+"  ===000000");
							if (floOrders.getUserCountry().contains(simAliasArr[a])) {
								VFmap.put(floOrders.getSN(), "");
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					

					String journey = floOrders.getJourney();
					if (StringUtils.isNotBlank(journey)) {
						JSONArray json = JSONArray.fromObject(journey);

						for (int i = 0; i < json.size(); i++) {
							// [{"国家":"新加坡","mcc":"525","使用时间":"20151219_20151220，20151223"},{"国家":"马来西亚","mcc":"502","使用时间":"20151221_20151222"}]
							JSONObject job = json.getJSONObject(i);

							String journeyCountry = job.get(
									journeyCountryString).toString(); // 行程国家
																		// 例如：新加坡
							String journeyUserTime = job.get(
									journeyUserTimeString).toString();// 使用时间
																		// 例如：20151219_20151220，20151223

							String[] journeyUserTimeArr = journeyUserTime
									.split("，"); // journeyUserTime
													// 例如：[20151219_20151220，20151223]
							if (journeyCountry.equals(countryName)) {
								for (int n = 0; n < journeyUserTimeArr.length; n++) {
									try {
										int startime, endtime = 0;
										String[] userTime = journeyUserTimeArr[n]
												.split("_");
										if (userTime.length == 1) {
											startime = Integer
													.parseInt(userTime[0]);
											endtime = Integer
													.parseInt(userTime[0]);
										} else {
											startime = Integer
													.parseInt(journeyUserTimeArr[n]
															.split("_")[0]);
											endtime = Integer
													.parseInt(journeyUserTimeArr[n]
															.split("_")[1]);
										}
										String time = FMdatetime.replace("-","");// 将2015-10-12 替换成 20151012

										// 判断订单搜索当天是否可用
										if (startime <= Integer.parseInt(time)
												&& Integer.parseInt(time) <= endtime) {
											// 查找当前国要可用设备中是否存在此设备（snLastSix）
											if (sNSUMS.indexOf(snLastSix) != -1) {
												// 逐个查找前面的sn没有没加上span标签
												// ，如果没有加则加上
												for (int l = 0; l < ja.size(); l++) {
													JSONObject lJsonObject = (JSONObject) ja
															.get(l);
													if (lJsonObject.get("SN")
															.toString()
															.indexOf(snLastSix) != -1) {
														if (lJsonObject
																.get("SN")
																.toString()
																.indexOf(
																		snLastSix
																				+ "</span>") == -1) {
															lJsonObject
																	.put("SN",
																			lJsonObject
																					.get("SN")
																					.toString()
																					.replaceFirst(
																							snLastSix,
																							"<span id='span' style='color:#05b75d; '>"
																									+ snLastSix
																									+ "</span>"));
															snLastSix = "<span id='span' style='color:#05b75d;'>"
																	+ snLastSix
																	+ "</span>";
															break;
														}
													}
												}
											}
											SN = SN + snLastSix + " , ";
											flowdealCount++;
											break;
										}
									} catch (Exception e) {
										e.printStackTrace();
										logger.info("行程解析错误，国家为：" + countryName
												+ "设备号为：" + snLastSix);
										snLastSix = "<span id='span' style='color:#ff6633;'>"
												+ snLastSix + "</span>";
										SN = SN + snLastSix + " , ";
										flowdealCount++;

									}
								}
							}
						}
					} else {
						// 行程为空 || null
						if (floOrders.getUserCountry().split("\\|").length != 1) {
							snLastSix = "<span id='span' style='color:#05b75d; '>"
									+ snLastSix + "</span>";
						}
						SN = SN + snLastSix + " , ";
						flowdealCount++;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			obj.put("flowdealCount", flowdealCount);
			sNSUMS = sNSUMS + SN;
			if (SN.length() > 3) {
				obj.put("SN", SN.substring(0, SN.length() - 3));
			} else {
				obj.put("SN", SN);
			}

			ja.add(obj);
		}
		JSONObject obj = new JSONObject();

		String vfCard = "";
		try {
			for (String key : VFmap.keySet()) {
				key = key.substring(9, key.length());
				vfCard += key + " , ";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		String useing = "0";
		String available = "0";
		String notavailable = "0";
		String stock = "0";
		String disabled = "0";
		// String disabledTwo="0";
		String offtheshelf = "0";
		int vfCardCount = 0;
		for (SIMInfo sInfo : simInfos) {
			String cardStatus = sInfo.getCardStatus();
			String count = sInfo.getCardStatusCount();
			vfCardCount = vfCardCount + Integer.parseInt(count);

			switch (cardStatus) {
			case "使用中":
				useing = count;
				break;
			case "可用":
				available = count;
				break;
			case "不可用":
				notavailable = count;
				break;
			case "停用":
				disabled = count;
				break;
			case "下架":
				offtheshelf = count;
				break;
			case "库存":
				stock = count;
				break;
			}

		}
		obj.put("countrName", "欧洲多国");
		obj.put("stock", stock);
		obj.put("notavailable", notavailable);
		obj.put("countryCode", 401);
		obj.put("SIMCount", vfCardCount);
		obj.put("offtheshelf", offtheshelf);
		obj.put("flowdealCount", VFmap.size());
		obj.put("disabledTwo", "--");
		obj.put("available", available);
		obj.put("disabled", disabled);
		obj.put("useing", useing);

		if (vfCard.length() > 3) {
			obj.put("SN", vfCard.substring(0, vfCard.length() - 3));
		} else {
			obj.put("SN", vfCard);
		}

		ja.add(obj);

		JSONObject object = new JSONObject();
		object.put("success", true);
		object.put("totalRows", baikaResult.size() + 1);
		object.put("curPage", searchDTO.getCurPage());
		object.put("data", ja);
		System.out.println(object.toString());
		return object.toString();
	}

	public String getVailable(SearchDTO searchDTO, String cName,
			String endtime, String begintime) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("cName", cName);
		map.put("begintime", begintime);
		map.put("endtime", endtime);
		List<Map> list = sqlSession.getVailable(NAMESPACE + "getVailable", map);

		JSONObject object = new JSONObject();
		object.put("success", true);
		object.put("totalRows", list.size());
		object.put("curPage", searchDTO.getCurPage());

		JSONArray ja = new JSONArray();
		int temp = 0;
		for (Map m : list) {
			JSONObject obj = JSONObject.fromObject(m);
			String flowdealCount = m.get("flowdealCount").toString();
			String countryName = m.get("countrName").toString();
			String countryCode = m.get("countryCode").toString();

			FlowDealOrders flowDealOrders = new FlowDealOrders();
			flowDealOrders.setUserCountry(countryCode);
			flowDealOrders.setPanlUserDate(begintime);
			flowDealOrders.setFlowExpireDate(endtime);
			List<FlowDealOrders> flowDealOrders2 = flowDealOrdersSer
					.getflowdealbycountry1(flowDealOrders);
			String SN = "";

			for (int j = 0; j < flowDealOrders2.size(); j++) {
				if (j == flowDealOrders2.size() - 1) {
					SN = SN
							+ flowDealOrders2
									.get(j)
									.getSN()
									.substring(
											flowDealOrders2.get(j).getSN()
													.length() - 4,
											flowDealOrders2.get(j).getSN()
													.length());
				} else {
					SN = SN
							+ flowDealOrders2
									.get(j)
									.getSN()
									.substring(
											flowDealOrders2.get(j).getSN()
													.length() - 4,
											flowDealOrders2.get(j).getSN()
													.length()) + "/";
				}
			}
			obj.remove("SN");
			obj.put("SN", SN);
			ja.add(obj);
		}
		object.put("data", ja);
		return object.toString();
	}

	/* 查询可以使用今天没有使用的流量订单 */
	public String selectnotuserString(SearchDTO searchDTO) {
		try {// getpageAvailableOrderString
			Page page = queryPage(NAMESPACE, "selectnotuser",
					"selectnotuserCount", searchDTO);
			List<FlowDealOrders> arr = (List<FlowDealOrders>) page.getRows();
			// List<Map<String,Object>>
			// arr=(List<Map<String,Object>>)page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders a : arr) { // Map<String,Object>
				JSONObject obj = JSONObject.fromObject(a);
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(
						a.getUserCountry()); // (String)a.get("userCountry")
				String cnames = wrapper.getCountryNameStrings();
				obj.remove("userCountry");
				obj.put("userCountry", cnames);
				Date date = a.getCreatorDate(); // (Date)a.get("creatorDate");
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);
				Date mdate = a.getModifyDate(); // (Date)a.get("modifyDate");
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public String getpage(FlowDealOrders flowDealOrders, String sql1,
			String sql2, SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, sql1, sql2, searchDTO);
			List<FlowDealOrders> arr = (List<FlowDealOrders>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders a : arr) {
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(
						a.getUserCountry());
				String countryName = wrapper.getCountryNameStrings();
				a.setUserCountry(countryName);
				JSONObject obj = JSONObject.fromObject(a);
				Date mdate = a.getCreatorDate();
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.put("creatorDate", mString);
				}
				String userDay = "";
				DeviceLogs info = new DeviceLogs();
				info.setSN(a.getSN());
				info.setCustomerID(a.getCustomerID());
				try {
					DeviceLogs inDeviceLogs = (DeviceLogs) getSqlSession()
							.selectOne(
									"com.Manage.dao.DeviceLogsTempDao.getbeginDate",
									info);
					userDay = inDeviceLogs.getLogsDate();
				} catch (Exception e) {
					e.printStackTrace();
				}

				obj.put("userDay", userDay);

				// 实际使用天数
				String flowDealID = a.getFlowDealID();
				DeviceFlow deviceFlow = new DeviceFlow();
				deviceFlow.setFlowOrderID(flowDealID);
				deviceFlow.setEndTime(flowDealOrders.getEnddate());
				deviceFlow.setBeginTime(flowDealOrders.getBegindateForQuery());
				List<DeviceFlow> deviceFlows = this
						.getDeviceInBySnAndDate(deviceFlow);

				int sjUserDay = 0;

				if (deviceFlows.size() > a.getFlowDays()) {
					sjUserDay = a.getFlowDays();
				} else {
					sjUserDay = deviceFlows.size();
				}
				obj.put("realityUserDays", sjUserDay);

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 根据时间段查询订单使用详情（设备流量报表查询）desc
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public List<DeviceFlow> getDeviceInBySnAndDate(DeviceFlow deviceFlow) {
		try {
			return getSqlSession().selectList(
					"com.Manage.dao.DeviceFlowDao.getDeviceInBySnAndDate",
					deviceFlow);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<FlowDealOrders> getflow(String sqlID, SearchDTO searchDTO) {
		try {
			return getSqlSession().selectList(NAMESPACE + sqlID, searchDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 退订时,更改总订单其下的流量订单为不可用
	 * 
	 * @param orderID
	 * @return
	 */
	public int updateFlowOrderUnavailable(FlowDealOrders order) {
		try {
			return getSqlSession().update(
					NAMESPACE + "updateFlowOrderUnavailable", order);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 修改微信下的流量单为可用
	 * 
	 * @param id
	 * @return
	 */
	public int updatewxStatusFlow(FlowDealOrders flow) {
		try {
			return getSqlSession().update(NAMESPACE + "updatewxStatusFlow",
					flow);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	public List<FlowDealOrders> selectwxOrderSnList(String id) {
		try {
			return getSqlSession().selectList(
					NAMESPACE + "selectwxOrderSnList", id);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public int getCheckSNisLiuLiang(String info) {
		try {
			return getSqlSession().selectOne(
					NAMESPACE + "getCheckSNisLiuLiang", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 判断两个订单时间是否交叉
	 * 
	 * @param flow
	 * @return
	 */
	public int getcheckordertimeagain(FlowDealOrders flow) {
		return getSqlSession().selectOne(NAMESPACE + "getcheckordertimeagain",
				flow);
	}

	public String getPanlUserDateFororderid(String id) {
		return getSqlSession().selectOne(
				NAMESPACE + "getPanlUserDateFororderid", id);
	}

	/**
	 * 判断两个订单是否有国家交叉
	 * 
	 * @param flow
	 * @return
	 */
	public int getCountryisTwo(FlowDealOrders flow) {
		return getSqlSession().selectOne(NAMESPACE + "getCountryisTwo", flow);
	}

	/**
	 * 验证两次下单是否是同一个客户
	 * 
	 * @param customerid
	 * @return
	 */
	public int checkCustomerEqual(FlowDealOrders customerid) {
		return getSqlSession().selectOne(NAMESPACE + "checkCustomerEqual",
				customerid);
	}

	public FlowDealOrders getFlowData(String orderid) {
		return getSqlSession().selectOne(NAMESPACE + "getFlowData", orderid);
	}

	public String getuserCountry(String id) {
		return getSqlSession().selectOne(NAMESPACE + "getuserCountry", id);
	}

	/**
	 * 取消标3星且全部未发货的总订单, 和其他的流量订单/设备订单设置为不可用
	 * 
	 * @param ordersInfo
	 *            注意参数类型使用总订单
	 * @return
	 */
	public int cancelYouzanStar3FlowOrder(OrdersInfo ordersInfo) {
		try {
			return getSqlSession().update(
					NAMESPACE + "cancelYouzanStar3FlowOrder", ordersInfo);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 根据SN验证此设备是否有未结束的有效流量订单 2015-12-18 15:09:21
	 * 
	 * @param id
	 * @return
	 */
	public int getIsFlowUseOrderForSn(String sn) {
		try {
			return getSqlSession().selectOne(
					NAMESPACE + "getIsFlowUseOrderForSn", sn);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * 
	 * @return
	 */
	public int getIsFlowUseOrderForSnqds(FlowDealOrders flow) {
		try {
			return getSqlSession().selectOne(
					NAMESPACE + "getIsFlowUseOrderForSnqds", flow);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 数据交易列表页面表格导出使用
	 * 
	 * @author jiangxuecheng
	 * @date 20150-12-14
	 * @param flowDealOrders
	 * @return
	 */
	public List<FlowDealOrders> queryPageExcel(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().selectList(NAMESPACE + "queryPageExcel",flowDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	public List<FlowDealOrders> queryPageExcel2(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().selectList(NAMESPACE + "queryPageExcel2",flowDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public int pauseOrders(DeviceInfo device) {
		try {
			return getSqlSession().update(NAMESPACE + "pauseOrders", device);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public int pauseOrdersOne(DeviceInfo device) {
		try {
			return getSqlSession().update(NAMESPACE + "pauseOrdersOne", device);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<FlowDealOrders> getuseredflowdealbydis(
			FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().selectList(
					NAMESPACE + "getuseredflowdealbydis", flowDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 取消订单
	 * 
	 * @param orderID
	 * @return
	 */
	public int cancelOrder(String orderID) {
		try {
			return getSqlSession().update(NAMESPACE + "cancelOrder", orderID);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 更新退款信息
	 * 
	 * @param orderID
	 * @return
	 */
	public int updateRefundInfo(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "updateRefundInfo",
					flowDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 根据设备查询可使用，使用中的流量订单
	 * 
	 * @return
	 */
	public List<FlowDealOrders> getFlowOrderbysn(String SN) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getFlowOrderbysn",
					SN);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 分页查询测试单
	 * 
	 * @param searchDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTestOrderPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "getTestOrderPage",
					"getTestOrderPageCount", searchDTO);
			List<FlowDealOrders> list = (List<FlowDealOrders>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FlowDealOrders order : list) {
				Date d = order.getCreatorDate();
				JSONObject obj = JSONObject.fromObject(order);
				obj.put("creatorDate",
						DateUtils.formatDate(d, "yyyy-MM-dd HH:mm:ss"));
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 修改测试单状态信息(状态改为已删除)
	 * 
	 * @param ids
	 * @return
	 */
	public int updateTestOrderStatus(List<String> list) {
		try {
			return getSqlSession().update(NAMESPACE + "updateTestOrderStatus",
					list);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	public int updateTestOrderStatusBySearch(FlowDealOrders dealOrders) {
		try {
			return getSqlSession().update(
					NAMESPACE + "updateTestOrderStatusBySearch", dealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	public String getorderStatus(String id) {
		return getSqlSession().selectOne(NAMESPACE + "getorderStatus", id);
	}

	public String QueryOrderCountByMonth(SearchDTO searchDTO,
			FlowDealOrders flowDealOrders) {
		JSONObject object = new JSONObject();
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			int curPage = searchDTO.getCurPage();
			int pageSize = searchDTO.getPageSize();

			String beginDate = flowDealOrders.getBeginDate();
			String endDate = flowDealOrders.getEnddate();
			int startIndex = searchDTO.getStartIndex();
			int endIndex = searchDTO.getEndIndex();

			map.put("cName", flowDealOrders.getUserCountry());
			map.put("beginDate", beginDate);
			map.put("endDate", endDate);
			map.put("startIndex", 0);
			map.put("endIndex", 99999);

			JSONArray ja = new JSONArray();

			List<Map<String, String>> resultList = sqlSession
					.queryOrderCountByMonth(NAMESPACE
							+ "QueryOrderCountByMonth", map);// getSqlSession().selectList(NAMESPACE
																// +
																// "QueryOrderCountByMonth",
																// map);
			for (Map<String, String> map1 : resultList) {
				JSONObject obj = JSONObject.fromObject(map1);

				ja.add(obj);
			}

			int toTalRows = getSqlSession().selectOne(
					"com.Manage.dao.CountryInfoDao.getcountbaobiao",
					flowDealOrders);

			object.put("data", ja);
			object.put("success", true);
			object.put("totalRows", toTalRows);
			object.put("curPage", searchDTO.getCurPage());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return object.toString();
	}

	public List<Map<String, Object>> QueryOrderCountByMonthexportexecl(
			Map<String, Object> map) {
		try {
			return sqlSession.queryOrderCountByMonth1(NAMESPACE
					+ "QueryOrderCountByMonth", map);// getSqlSession().selectList(NAMESPACE
														// +
														// "QueryOrderCountByMonth",
														// map);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	public List<PrepareCardTemp> getPreparInfoByTime(
			PrepareCardTemp prepareCardTemp) {
		try {
			return getSqlSession().selectList(
					"com.Manage.dao.PrepareCardTempDao.getPreparInfoByTime",
					prepareCardTemp);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public String floworderalwayuserinfoString(SearchDTO searchDTO,
			List<CountryInfo> countryInfos, String searchDate) {

		try {
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", countryInfos.size());
			object.put("curPage", 1);
			JSONArray ja = new JSONArray();
			JSONObject obj = new JSONObject();

			int lastDay = 1;

			for (CountryInfo countryInfo : countryInfos) {

				String countryName = countryInfo.getCountryName();
				String countryCode = String.valueOf(countryInfo
						.getCountryCode());

				obj.put("countryName", countryName);

				// /////////////////////////////////////////////////////////////
				// int curYue = Integer.parseInt(DateUtils.getDate("MM"));
				// int curDay = Integer.parseInt(DateUtils.getDate("dd"));
				//
				// int year = Integer.parseInt(searchDate.split("-")[0]);
				// int month = Integer.parseInt(searchDate.split("-")[1]);
				//
				// if(curYue==Integer.parseInt(searchDate.split("-")[1]) ||
				// curYue<Integer.parseInt(searchDate.split("-")[1])){
				// //需要新增数据
				// // 查询当前月
				// Map<String, String> parameterMap = new HashMap<String,
				// String>();
				// parameterMap.put("startDate",searchDate+"-"+curDay);
				// parameterMap.put("endDate",searchDate+"-"+DateUtils.getDaysByYearMonth(year,
				// month));
				// List<Map<String, String>> resultMap=
				// getsimTotalandorderTotal(parameterMap);
				//
				//
				// }else if(curYue<Integer.parseInt(searchDate.split("-")[1])){
				// //需要新增数据 （查询是未来月）
				//
				// }else if(curYue>Integer.parseInt(searchDate.split("-")[1])){
				// //不需要新增数据
				//
				// }
				// //////////////////////////////////////
				PrepareCardTemp prepareCardTemp = new PrepareCardTemp();
				prepareCardTemp.setCountryName(countryName);
				prepareCardTemp.setTime(searchDate);
				List<PrepareCardTemp> prepareCardTemps = getPreparInfoByTime(prepareCardTemp); // 获取到一个国家一个月的数据
				for (PrepareCardTemp prepareCardTemp2 : prepareCardTemps) {
					String time = prepareCardTemp2.getTime();

					String orderTotalNum = prepareCardTemp2.getOrderTotalNum(); // 订单总数
					String cardCount = prepareCardTemp2.getCardCount(); // SIM卡总数
					String userFlow = Bytes
							.valueOf(prepareCardTemp2.getUserFlow() + "K")
							.toString().replace("0 bytes", "0"); // 使用流量
					String surplusFlow = Bytes
							.valueOf(prepareCardTemp2.getSurplusFlow() + "K")
							.toString().replace("0 bytes", "0"); // 剩余流量
					String info = "<input type=\"text\" style=\"width:60px;border:1px dashed #ccc;border-top-style:none;border-bottom-style:none; border-left-style:none; text-align:center;\" value=\""
							+ orderTotalNum
							+ "\"/>"
							+ "<input type=\"text\" style=\"width:60px;border:1px dashed #ccc;border-top-style:none;border-bottom-style:none; border-left-style:none; text-align:center;\" value=\""
							+ cardCount
							+ "\"/>"
							+ "<input type=\"text\" style=\" border:1px dashed #ccc;text-align:center;border-top-style:none;border-bottom-style:none; border-left-style:none;width:60px;\" value=\""
							+ userFlow
							+ "\"/>"
							+ "<input type=\"text\" style=\" text-align:center;border:none;width:60px;\" value=\""
							+ surplusFlow + "\"/>";

					String day = time.substring(8, 10);
					lastDay = Integer.parseInt(day) + 1;
					obj = jsonData(day, obj, info);
				}

				int year = Integer.parseInt(searchDate.split("-")[0]);
				int month = Integer.parseInt(searchDate.split("-")[1]);

				int days = DateUtils.getDaysByYearMonth(year, month);

				for (int i = lastDay; i <= days; i++) {
					String day = "";
					if (String.valueOf(i).length() == 1) {
						day = "0" + String.valueOf(i);
					} else {
						day = String.valueOf(i);
					}

					String curDate = searchDate + day;

					if (String.valueOf(i).length() == 1)
						curDate = searchDate + "-0" + i;
					else
						curDate = searchDate + "-" + i;

					FlowDealOrders flowDealOrders = new FlowDealOrders();
					flowDealOrders.setPanlUserDate(curDate);
					flowDealOrders.setUserCountry(countryName);
					List<FlowDealOrders> flowDealOrdList = flowDealOrdersSer
							.getflowdealbycountry(flowDealOrders);
					String orderTotalNum = String.valueOf(flowDealOrdList
							.size()); // 订单总数

					// 查询sim卡总数
					SIMInfo simInfo = new SIMInfo();
					String datetime = curDate + " 00:00";
					simInfo.setDatetime(datetime);
					simInfo.setCountryCode(countryCode);
					String cardCount = String.valueOf(getSIMCardTotal(simInfo)
							.get(0).getSIMCount());// SIM卡总数
					String info = "<input type=\"text\" style=\"width:60px;border:none; background:;text-align:center;\" value=\""
							+ orderTotalNum
							+ "\"/>"
							+ "<input type=\"text\" style=\"width:60px;border:1px dashed #ccc;border-top-style:none;border-bottom-style:none; border-right-style:none;text-align:center;\" value=\""
							+ cardCount + "\"/>";
					obj = jsonData(day, obj, info);
				}

				// ///////////////////////存储过程begin///////////////////////////
				// String day="";
				// if(String.valueOf(lastDay).length()==1){
				// day="0"+String.valueOf(lastDay);
				// }else{
				// day=String.valueOf(lastDay);
				// }
				//
				// String startDate =searchDate+"-"+day;
				//
				// int year = Integer.parseInt(searchDate.split("-")[0]);
				// int month = Integer.parseInt(searchDate.split("-")[1]);
				//
				// Map<String, String> parameterMap = new HashMap<String,
				// String>();
				// parameterMap.put("countryName", countryName);
				// parameterMap.put("countryCode",countryCode );
				// parameterMap.put("startDate",startDate);
				// parameterMap.put("endDate",searchDate+"-"+DateUtils.getDaysByYearMonth(year,
				// month));
				// List<Map<String, String>> resultMap=
				// getsimTotalandorderTotal(parameterMap);
				//
				// for (Map<String,String> m : resultMap)
				// {
				// String time = m.get("time").toString();
				// String orderTotalNum =
				// String.valueOf(m.get("orderTotalNum")); // 订单总数
				// String cardCount = String.valueOf(m.get("simCardTotalNum"));
				// //SIM卡总数
				// String
				// info="<input type=\"text\" style=\"width:60px;border:1px dashed #ccc;border-top-style:none;border-bottom-style:none; border-left-style:none;text-align:center;\" value=\""+cardCount+"\"/>"+"<input type=\"text\" style=\"width:60px;border:none; background:;text-align:center;\" value=\""+orderTotalNum+"\"/>";
				//
				// String day1 = time.substring(8, 10);
				//
				// obj = jsonData(day1, obj, info);
				// }
				// ///////////////////////存储过程end///////////////////////////
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 组装json数据
	 * 
	 * @param i
	 * @param obj
	 * @param info
	 * @return
	 */
	public JSONObject jsonData(String day, JSONObject obj, String info) {
		switch (day) {
		case "01":
			obj.put("first", info);
			break;

		case "02":
			obj.put("second", info);
			break;

		case "03":
			obj.put("third", info);
			break;

		case "04":
			obj.put("fourth", info);
			break;

		case "05":
			obj.put("fifth", info);
			break;

		case "06":
			obj.put("sixth", info);
			break;

		case "07":
			obj.put("seventh", info);
			break;

		case "08":
			obj.put("eighth", info);
			break;

		case "09":
			obj.put("ninth", info);
			break;

		case "10":
			obj.put("tenth", info);
			break;

		case "11":
			obj.put("eleventh", info);
			break;

		case "12":
			obj.put("twelfth", info);
			break;

		case "13":
			obj.put("thirteenth", info);
			break;

		case "14":
			obj.put("fourteenth", info);
			break;

		case "15":
			obj.put("fifteenth", info);
			break;

		case "16":
			obj.put("sixteenth", info);
			break;

		case "17":
			obj.put("seventeenth", info);
			break;

		case "18":
			obj.put("eighteenth", info);
			break;

		case "19":
			obj.put("nineteeneh", info);
			break;

		case "20":
			obj.put("twentieth", info);
			break;

		case "21":
			obj.put("twentyFirst", info);
			break;

		case "22":
			obj.put("twentySecond", info);
			break;

		case "23":
			obj.put("twentyThird", info);
			break;

		case "24":
			obj.put("twentyFourth", info);
			break;

		case "25":
			obj.put("twentyFifth", info);
			break;

		case "26":
			obj.put("twentySixth", info);
			break;

		case "27":
			obj.put("twentySeventh", info);
			break;

		case "28":
			obj.put("twentyEighth", info);
			break;

		case "29":
			obj.put("twentyNinth", info);
			break;

		case "30":
			obj.put("thirtieth", info);
			break;

		case "31":
			obj.put("thirtyFirst", info);
			break;

		default:
			break;
		}
		return obj;
	}

	public List<Map<String, String>> getsimTotalandorderTotal(
			Map<String, String> map) {
		try {
			return sqlSession.getsimTotalandorderTotal(NAMESPACE
					+ "getsimTotalandorderTotal", map);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询sim卡总数
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public List<SIMInfo> getSIMCardTotal(SIMInfo simInfo) {
		try {
			return getSqlSession().selectList(
					"com.Manage.dao.SIMInfoDao.getSIMCardTotal", simInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询PrepareCardTemp表
	 * 
	 * @param prepareCardTemp
	 * @return
	 */
	public PrepareCardTemp getPreparInfoBycountryNameandTime(
			PrepareCardTemp prepareCardTemp) {
		try {
			return getSqlSession()
					.selectOne(
							"com.Manage.dao.PrepareCardTempDao.getPreparInfoBycountryNameandTime",
							prepareCardTemp);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public int updatedrawBackDay(FlowDealOrders flowDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "updatedrawBackDay",
					flowDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	public List<FlowDealOrders> getFlowDealOrdersByUsingDate(FlowDealOrders f){
	    return getSqlSession().selectList(NAMESPACE+"getFlowDealOrdersByUsingDate", f);
	}
}
