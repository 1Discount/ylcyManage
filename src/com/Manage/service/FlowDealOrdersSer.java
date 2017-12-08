package com.Manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMInfoCountryTemp;
import com.Manage.entity.common.SearchDTO;

/**
 * * @author wangbo: * @date 创建时间：2015-5-29 上午9:14:42 * @version 1.0 * @parameter
 * * @since * @return
 */
@Service
public class FlowDealOrdersSer extends BaseService {
	private Logger logger = LogUtil.getInstance(FlowDealOrdersSer.class);

	/**
	 * 添加流量订单
	 * 
	 * @param menuInfo
	 * @return
	 */
	public boolean insertinfo(FlowDealOrders orders) {
		logger.debug("添加流量订单service");
		try {
			int temp = flowDealOrdersDao.insertinfo(orders);
			if (temp > 0) {
				logger.debug("添加流量订单成功");
				return true;
			} else {
				logger.debug("添加流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更新未完成流量订单
	 * 
	 * @param ordersID
	 * @return
	 */
	public boolean updateiffinish(FlowDealOrders orders) {
		logger.debug("更新流量订单service");
		try {
			int temp = flowDealOrdersDao.updateiffinish(orders);
			if (temp > 0) {
				logger.debug("更新流量订单成功");
				return true;
			} else {
				logger.debug("更新流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除
	 * 
	 * @param ordersID
	 * @return
	 */
	public boolean delfloworder(String ordersID) {
		logger.debug("删除设备订单service");
		try {
			int temp = flowDealOrdersDao.delfloworder(ordersID);
			if (temp > 0) {
				logger.debug("删除设备订单成功");
				return true;
			} else {
				logger.debug("删除设备订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public List<FlowDealOrders> getbyoid(String oid) {
		return flowDealOrdersDao.getlistbyoid(oid);
	}

	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = flowDealOrdersDao.getpageString(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 企业用户分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageenterprise(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = flowDealOrdersDao.getpageenterprise(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 查询渠道商已使用/未使用订单 旧
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString1(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		List<CountryInfo> countries = countryInfoDao.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries) {
			mccNameMap.put(String.valueOf(item.getCountryCode()),
					item.getCountryName());
		}

		try {
			String jsonString = flowDealOrdersDao.getpageString1(searchDTO,
					mccNameMap);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 分页查询 今天可用订单, 需要JOIN 设备订单表
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageAvailableOrderString(SearchDTO searchDTO) {
		logger.debug("分页查询今天可用订单开始");
		try {
			String jsonString = flowDealOrdersDao
					.getpageAvailableOrderString(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 根据ID查询流量订单详细
	 * 
	 * @param flowID
	 * @return
	 */
	public FlowDealOrders getbyid(String flowID) {
		logger.debug("删除设备订单service");
		try {
			FlowDealOrders flowDealOrders = flowDealOrdersDao.getbyid(flowID);
			String journey = flowDealOrders.getJourney();// [{"国家":"澳门","mcc":455,"使用时间":"20151024"},{"国家":"香港","mcc":454,"使用时间":"20151025"}]
			try {

				if (StringUtils.isNotBlank(journey)) {
					logger.info("开始转换行程");// 香港（151022，151030）|意大利（151023，151024）|瑞士（151024-151027）|德国（151027-151029）
					// [
					// {"国家":"香港","mcc":454,"使用时间":"20151022，20151030"},
					// {"国家":"意大利","mcc":222,"使用时间":"20151023，20151024"},
					// {"国家":"瑞士","mcc":228,"使用时间":"20151024_20151027"},
					// {"国家":"德国","mcc":262,"使用时间":"20151027_20151029"
					// }]
					StringBuffer sb = new StringBuffer();
					// 获取到国家
					JSONArray json = JSONArray.fromObject(journey); // 首先把字符串转成
																	// JSONArray
																	// 对象
					for (int i = 0; i < json.size(); i++) {
						JSONObject job = json.getJSONObject(i);
						// System.out.println(job.get("mcc")) ;
						// 获取到行程国家
						String cName = job.get("国家").toString();
						// 获取到mcc
						String mccString = job.get("mcc").toString();
						// 获取到使用时间
						String userDate = job.get("使用时间").toString();// 20151023，20151024//20151027_20151029
						userDate = userDate.replace("2015", "15")
								.replace("_", "-").replace("2016", "16")
								.replace("2017", "17");
						sb.append(cName);
						sb.append("（");
						sb.append(userDate);
						sb.append("）");
						if (i != json.size() - 1) {
							sb.append("|");
						}

					}
					logger.info("转换行程 完毕");
					flowDealOrders.setJourney(sb.toString());

				}

			} catch (Exception e) {
				e.printStackTrace();
				flowDealOrders.setJourney("");
				logger.info("行程解析出错！");
			}
			return flowDealOrders;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public static void main(String[] args) { String journey =
	 * "[{\"国家\":\"香港\",\"mcc\":\"454\",\"使用时间\":\"20151217\"},{\"国家\":\"新西兰\",\"mcc\":\"530\",\"使用时间\":\"20151217_20160101\"}]"
	 * ;//[{"国家":"澳门","mcc":455,"使用时间":"20151024"},{"国家":"香港","mcc":454,"使用时间":
	 * "20151025"}]
	 * 
	 * if(StringUtils.isNotBlank(journey)){
	 * 
	 * StringBuffer sb = new StringBuffer(); //获取到国家 JSONArray json =
	 * JSONArray.fromObject(journey); // 首先把字符串转成 JSONArray 对象 for(int
	 * i=0;i<json.size();i++){ JSONObject job = json.getJSONObject(i);
	 * //System.out.println(job.get("mcc")) ; //获取到行程国家 String cName =
	 * job.get("国家").toString(); //获取到mcc String mccString =
	 * job.get("mcc").toString(); //获取到使用时间 String userDate =
	 * job.get("使用时间").toString();//20151023，20151024//20151027_20151029
	 * userDate = userDate.replace("2015", "15").replace("_",
	 * "-").replace("2016", "16").replace("2017", "17"); sb.append(cName);
	 * sb.append("（"); sb.append(userDate); sb.append("）");
	 * if(i!=json.size()-1){ sb.append("|"); }
	 * 
	 * } System.out.println(sb.toString()); }
	 * 
	 * }
	 */

	/**
	 * 编辑保存
	 * 
	 * @param ordersID
	 * @return
	 */
	public boolean editsave(FlowDealOrders flowDealOrders) {
		logger.debug("编辑流量订单service");
		try {
			int temp = flowDealOrdersDao.editsave(flowDealOrders);
			if (temp > 0) {
				logger.debug("编辑流量订单成功");
				return true;
			} else {
				logger.debug("编辑流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 编辑保存
	 * 
	 * @param ordersID
	 * @return
	 */
	public boolean editsaveauto(FlowDealOrders flowDealOrders) {
		logger.debug("编辑流量订单service");
		try {
			int temp = flowDealOrdersDao.editsaveauto(flowDealOrders);
			if (temp > 0) {
				logger.debug("编辑流量订单成功");
				return true;
			} else {
				logger.debug("编辑流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 激活
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public boolean activate(FlowDealOrders flowDealOrders) {
		logger.debug("激活流量订单service");
		try {
			int temp = flowDealOrdersDao.activate(flowDealOrders);
			if (temp > 0) {
				logger.debug("激活流量订单成功");
				return true;
			} else {
				logger.debug("激活流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据ID查询流量订单详细
	 * 
	 * @param flowID
	 * @return
	 */
	public List<FlowDealOrders> getbyCustomerid(String customerID) {
		logger.debug("删除设备订单service");
		try {
			return flowDealOrdersDao.getFlowforCustomerid(customerID);

		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 暂停服务
	 * 
	 * @param flowDealID
	 * @return
	 */
	public boolean pause(String flowDealID) {
		logger.debug("激活流量订单service");
		try {
			int temp = flowDealOrdersDao.pause(flowDealID);
			if (temp > 0) {
				logger.debug("暂停流量订单成功");
				return true;
			} else {
				logger.debug("暂停流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 启动服务
	 * 
	 * @param flowDealID
	 * @return
	 */
	public boolean launch(String flowDealID) {
		logger.debug("启动流量订单service");
		try {
			int temp = flowDealOrdersDao.launch(flowDealID);
			if (temp > 0) {
				logger.debug("启动流量订单成功");
				return true;
			} else {
				logger.debug("启动流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 限速
	 * 
	 * @param flowDealID
	 * @return
	 */
	public boolean limitSpeed(FlowDealOrders flowDealOrders) {
		logger.debug("限速流量订单service");
		try {
			int temp = flowDealOrdersDao.limitSpeed(flowDealOrders);
			if (temp > 0) {
				logger.debug("限速流量订单成功");
				return true;
			} else {
				logger.debug("限速流量订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更新国家
	 * 
	 * @param flowDealID
	 * @return
	 */
	public boolean updateCountry(FlowDealOrders flowDealOrders) {
		logger.debug("更新国家开始");
		try {
			int temp = flowDealOrdersDao.updateCountry(flowDealOrders);
			if (temp > 0) {
				logger.debug("更新国家成功");
				return true;
			} else {
				logger.debug("更新国家失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 统计查询
	 * 
	 * @param deviceDealOrders
	 * @return
	 */
	public Map<String, String> statistics(FlowDealOrders flowDealOrders) {
		try {
			logger.debug("流量订单统计查询");
			return flowDealOrdersDao.statistics(flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> statistics1(FlowDealOrders flowDealOrders) {
		try {
			logger.debug("流量订单统计查询");
			return flowDealOrdersDao.statistics1(flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据imsi查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageimsi(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = flowDealOrdersDao.getpageimsi(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 根据sn修改订单状态
	 * 
	 * @param fdo
	 * @return
	 */
	public int updateOrderStatusForSn(FlowDealOrders fdo) {
		try {
			return flowDealOrdersDao.updateOrderStatusForSnDao(fdo);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 设备归还将对应的流量订单改为不可用
	 * 
	 * @param fdo
	 * @return
	 */
	public boolean updatebysn(FlowDealOrders fdo) {
		try {
			int temp = flowDealOrdersDao.updatebysn(fdo);
			if (temp > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据sn查询流量订单
	 * 
	 * @return
	 */
	public List<FlowDealOrders> selectFlowdeal(FlowDealOrders dealOrders) {
		try {
			return flowDealOrdersDao.selectFlowdeal(dealOrders);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改flowDealOrders
	 * 
	 * @param flowDealOrders
	 */
	public int updateFlowdealByflowDealID(FlowDealOrders flowDealOrders) {
		try {
			return flowDealOrdersDao.updateFlowdealByflowDealID(flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	/**
	 * 修改SN
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public int updateSN(FlowDealOrders flowDealOrders) {
		try {
			int count = flowDealOrdersDao.updateSN(flowDealOrders);
			if (count > 0) {
				logger.info("更新SN成功");
				return count;
			} else {
				logger.info("更新SN失败");
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 清除测试订单
	 * 
	 * @return
	 */
	public int updateTestOrder() {
		return flowDealOrdersDao.updateTestOrder();
	}

	public FlowDealOrders getTestBySN(String SN) {
		return flowDealOrdersDao.getTestbysn(SN);
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
			// FlowDealOrdersDao flowdeal = new FlowDealOrdersDao();
			return flowDealOrdersDao.getflowdealbycountry(flowDealOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<FlowDealOrders> getflowdealbycountry1(
			FlowDealOrders flowDealOrders) {
		try {
			// FlowDealOrdersDao flowdeal = new FlowDealOrdersDao();
			return flowDealOrdersDao.getflowdealbycountry1(flowDealOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String beika(SearchDTO searchDTO, String cName, String datetime) {
		return flowDealOrdersDao.beika(searchDTO, cName, datetime);
	}

	public String getVailable(SearchDTO searchDTO, String cName,
			String begintime, String endtime) {
		return flowDealOrdersDao.getVailable(searchDTO, cName, endtime,
				begintime);
	}

	/* 查询可以使用今天没有使用的流量订单 */
	public String selectnotuserString(SearchDTO searchDTO) {
		try {
			return flowDealOrdersDao.selectnotuserString(searchDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getpage(FlowDealOrders flowDealOrders, String sql1,
			String sql2, SearchDTO searchDTO) {
		try {
			return flowDealOrdersDao.getpage(flowDealOrders, sql1, sql2,
					searchDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<FlowDealOrders> getflow(String sqlID, SearchDTO searchDTO) {
		try {
			return flowDealOrdersDao.getflow(sqlID, searchDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改微信下的流量单为可用
	 * 
	 * @param id
	 * @return
	 */
	public int updatewxStatusFlow(FlowDealOrders flow) {
		return flowDealOrdersDao.updatewxStatusFlow(flow);
	}

	/**
	 * 根据总订单ID查询所有此总订单ID的流量订单列表
	 * 
	 * @param orderID
	 *            总订单ID
	 * @return 符合的列表
	 */
	public List<FlowDealOrders> selectwxOrderSnList(String orderID) {
		return flowDealOrdersDao.selectwxOrderSnList(orderID);
	}

	public int getCheckSNisLiuLiang(String info) {
		return flowDealOrdersDao.getCheckSNisLiuLiang(info);
	}

	/**
	 * 判断两个订单时间是否交叉
	 * 
	 * @return
	 */
	public int getcheckordertimeagain(FlowDealOrders flow) {
		return flowDealOrdersDao.getcheckordertimeagain(flow);
	}

	/**
	 * 根据订单号查询计划使用时间
	 * 
	 * @return
	 */
	public String getPanlUserDateFororderid(String id) {
		return flowDealOrdersDao.getPanlUserDateFororderid(id);
	}

	/**
	 * 判断两个订单是否有国家交叉
	 * 
	 * @param flow
	 * @return
	 */
	public int getCountryisTwo(FlowDealOrders flow) {
		return flowDealOrdersDao.getCountryisTwo(flow);
	}

	/**
	 * 验证两个订单的客户是否是同一人
	 * 
	 * @return
	 */
	public int checkCustomerEqual(FlowDealOrders customerid) {
		return flowDealOrdersDao.checkCustomerEqual(customerid);
	}

	/**
	 * 根据订单号查询订单数据
	 * 
	 * @param orderid
	 * @return
	 */
	public FlowDealOrders getFlowData(String orderid) {
		return flowDealOrdersDao.getFlowData(orderid);
	}

	public String getuserCountry(String id) {
		return flowDealOrdersDao.getuserCountry(id);
	}

	/**
	 * 取消标3星且全部未发货的总订单, 和其他的流量订单/设备订单设置为不可用
	 * 
	 * @param orders
	 *            注意参数类型使用总订单
	 * @return
	 */
	public boolean cancelYouzanStar3FlowOrder(OrdersInfo orders) {
		logger.debug("设置有赞标3星流量订单为不可用");
		try {
			int temp = flowDealOrdersDao.cancelYouzanStar3FlowOrder(orders);
			if (temp > 0) {
				logger.debug("更新订单成功");
				return true;
			} else {
				logger.debug("更新订单失败");
				return false;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
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
			return flowDealOrdersDao.queryPageExcel(flowDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}
	public List<FlowDealOrders> queryPageExcel2(FlowDealOrders flowDealOrders) {
		try {
		    List<FlowDealOrders>  fos=flowDealOrdersDao.queryPageExcel2(flowDealOrders);
		    setFlowDealOrdersUserInfo(fos);
		    return fos;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	public void setFlowDealOrdersUserInfo(List<FlowDealOrders>  fos){
	    if(!fos.isEmpty()){
		List<CountryInfo> cs= countryInfoDao.getAll("");
		Map<String,String> CountryInfoMap= new HashMap<>();
		for(CountryInfo c:cs){
		    CountryInfoMap.put(c.getCountryCode()+"", c.getCountryName());
		}
		for(FlowDealOrders f:fos){
		    //设置可使用国家
		    String[] userCountryArr;
		    if(f.getUserCountry().indexOf("|")>-1){
			userCountryArr=f.getUserCountry().split("\\|");
		    }else{
			userCountryArr=new String[]{f.getUserCountry()};
		    }
		    
		    String userCountry="";
		    for(int i=0;i<userCountryArr.length;i++){
			System.out.println("============:"+userCountryArr[i]+":============");
			userCountry+=userCountryArr[i].substring(0,userCountryArr[i].indexOf(","))+",";
		    }
		    f.setUserCountry(userCountry);
		    //已使用国家
		    String[] usedCountryArr;
		    if(f.getUserInfo().indexOf(";")>-1){
			usedCountryArr=f.getUserInfo().split(";");
		    }else{
			usedCountryArr=new String[]{f.getUserInfo()};
		    }
		    String usedCountry="";
		    String usedDays="";
		    for(int j=0;j<usedCountryArr.length;j++){
			usedCountry+=CountryInfoMap.get(usedCountryArr[j].substring(0,usedCountryArr[j].indexOf(":")))+",";
			usedDays+=usedCountryArr[j].substring(usedCountryArr[j].indexOf(":")+1,usedCountryArr[j].length())+",";
		    }
		    f.setUsingDate(usedDays);
		    f.setUsingCountry(usedCountry);
		}
	    }
	}
	
	/**
	 * 根据SN验证此设备是否有未结束的有效流量订单 2015-12-18 15:09:33
	 * 
	 * @param id
	 * @return
	 */
	public int getIsFlowUseOrderForSn(String sn) {
		return flowDealOrdersDao.getIsFlowUseOrderForSn(sn);
	}

	/**
	 * 根据SN验证有没有使用中的订单 且是渠道商创建
	 * 
	 * @param flow
	 *            渠道商ID SN
	 * @return
	 */
	public int getIsFlowUseOrderForSnqds(FlowDealOrders flow) {
		return flowDealOrdersDao.getIsFlowUseOrderForSnqds(flow);
	}

	public int pauseOrders(DeviceInfo device) {
		return flowDealOrdersDao.pauseOrders(device);
	}

	public int pauseOrdersOne(DeviceInfo device) {
		return flowDealOrdersDao.pauseOrdersOne(device);
	}

	public String getJourney(String journey) {
		String journey1 = "";
		String time = "";
		String countrynam = "";
		int mcc = 0;
		try {
			if (StringUtils.isNotBlank(journey)) {
				journey = journey.replace(",", "，");
				if (journey.split("\\|").length == 1) {
					countrynam = journey.split("\\（")[0];
					CountryInfo countryInfo = new CountryInfo();
					countryInfo.setCountryName(countrynam);
					mcc = countryInfoDao
							.getcountryinfoBycountryname(countryInfo).get(0)
							.getCountryCode();
					String xcTime = journey.split("\\（")[1].replace("）", "");
					String[] xctimearray = xcTime.split("，");
					for (int n = 0; n < xctimearray.length; n++) {

						if (xctimearray[n].split("-").length == 2) {
							String beginTime = "20"
									+ xctimearray[n].split("-")[0].replace(" ",
											"");
							String endTime = "20"
									+ xctimearray[n].split("-")[1];
							time = time + "，" + beginTime + "_" + endTime;

						} else {
							time = time + "，20" + xctimearray[n];
						}
					}

					journey1 = "[{\"国家\":\"" + countrynam + "\",\"mcc\":\""
							+ mcc + "\",\"使用时间\":\"" + time + "\"}]";
					journey1 = journey1.replace("，\"}]", "\"}]");
					journey1 = journey1.replace("\":\"，", "\":\"");
				} else {
					journey1 = "[{";
					for (int j = 0; j < journey.split("\\|").length; j++) {
						countrynam = journey.split("\\|")[j].split("\\（")[0];
						CountryInfo countryInfo = new CountryInfo();
						countryInfo.setCountryName(countrynam);
						mcc = countryInfoDao
								.getcountryinfoBycountryname(countryInfo)
								.get(0).getCountryCode();
						String xcTimes = journey.split("\\|")[j].split("\\（")[1]
								.replace("）", "");
						String[] temparray = journey.split("\\|")[j]
								.split("\\（")[1].replace("）", "").split("，");
						if (temparray.length >= 2) {
							for (int s = 0; s < temparray.length; s++) {
								if (temparray[s].split("-").length == 2) {
									String beginTime = "20"
											+ temparray[s].split("-")[0].trim();
									String endTime = "20"
											+ temparray[s].split("-")[1];
									time = time + "，" + beginTime + "_"
											+ endTime;
									if (s == temparray.length - 1) {
										time = time + "\"},{";
									}

								} else {
									time = time + "，20" + temparray[s].trim();
									if (s == temparray.length - 1) {
										time = time + "\"},{";
									}

								}

							}
							if (j == journey.split("\\|").length - 1) {
								time = time.substring(0, time.length() - 4);
							}
						} else {
							if (temparray[0].split("-").length == 2) {
								String beginTime = "20"
										+ temparray[0].split("-")[0].trim();
								String endTime = "20"
										+ temparray[0].split("-")[1];
								time = time + "，" + beginTime + "_" + endTime;
								if (j != journey.split("\\|").length - 1) {
									time = time + "\"},{";
								}

							} else {
								time = time + "，20" + temparray[0].trim();
								if (j != journey.split("\\|").length - 1) {
									time = time + "\"},{";
								}
							}

						}

						journey1 = journey1 + "\"国家\":\"" + countrynam
								+ "\",\"mcc\":\"" + mcc + "\",\"使用时间\":\""
								+ time;

						time = "";
					}
					journey1 = journey1 + "\"}]";
					journey1 = journey1.replace("\":\"，", "\":\"");
				}

			}
			return journey1;
		} catch (Exception e) {
			e.printStackTrace();
			journey1 = "";
		}
		return journey1;
	}

	public List<FlowDealOrders> getuseredflowdealbydis(
			FlowDealOrders flowDealOrders) {
		try {
			return flowDealOrdersDao.getuseredflowdealbydis(flowDealOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取消订单
	 * 
	 * @param orderID
	 * @return
	 */
	public boolean cancelOrder(String orderID) {
		try {

			if (flowDealOrdersDao.cancelOrder(orderID) > 0) {

				return true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更新退款信息
	 * 
	 * @param orderID
	 * @return
	 */
	public boolean updateRefundInfo(FlowDealOrders flowDealOrders) {
		try {

			if (flowDealOrdersDao.updateRefundInfo(flowDealOrders) > 0) {

				return true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据设备查询可使用，使用中的流量订单
	 * 
	 * @return
	 */
	public List<FlowDealOrders> getFlowOrderbysn(String SN) {
		try {
			return flowDealOrdersDao.getFlowOrderbysn(SN);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 分页查询测试单
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getTestOrderPage(SearchDTO searchDTO) {
		logger.debug("测试单分页开始");
		try {
			String jsonString = flowDealOrdersDao.getTestOrderPage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 修改测试单状态信息(状态改为已删除)
	 * 
	 * @param ids
	 * @return
	 */
	public int updateTestOrderStatus(List<String> list) {
		logger.debug("开始执行更新测试单状态信息");
		try {
			return flowDealOrdersDao.updateTestOrderStatus(list);
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public int updateTestOrderStatusBySearch(FlowDealOrders dealOrders) {
		try {
			return flowDealOrdersDao.updateTestOrderStatusBySearch(dealOrders);
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public String getorderStatus(String id) {
		try {
			return flowDealOrdersDao.getorderStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @Cacheable(value = "SimplePageCachingFilter", key =
	 * "#flowDealOrders.getBeginDate()+#flowDealOrders.getEnddate()")
	 */
	public String QueryOrderCountByMonth(SearchDTO searchDTO,
			FlowDealOrders flowDealOrders) {
		try {
			return flowDealOrdersDao.QueryOrderCountByMonth(searchDTO,
					flowDealOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, Object>> QueryOrderCountByMonthexportexecl(
			Map<String, Object> map) {
		try {
			return flowDealOrdersDao.QueryOrderCountByMonthexportexecl(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Cacheable(value = "SimplePageCachingFilter", key = "#countryName+#searchDate")
	public String floworderalwayuserinfoString(SearchDTO searchDTO,
			String searchDate, String countryName) {
		try {
			List<CountryInfo> countryInfos = countryInfoDao.getAll(countryName);

			return flowDealOrdersDao.floworderalwayuserinfoString(searchDTO,
					countryInfos, searchDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询sim卡总数
	 * 
	 * @param flowDealOrders
	 * @return
	 */
	public List<SIMInfo> getSIMCardTotal(SIMInfo simInfo) {
		try {
			return flowDealOrdersDao.getSIMCardTotal(simInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int updatedrawBackDay(FlowDealOrders flowDealOrders) {
		try {
			return flowDealOrdersDao.updatedrawBackDay(flowDealOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**备卡查询**/
	public List<SIMInfoCountryTemp> beika(String cName, String datetime,String serverCode) {
	    	if(datetime==null || datetime.isEmpty()){datetime=DateUtil.getCurrentYYYYMMDDString();}
	    	SIMInfoCountryTemp s=new SIMInfoCountryTemp();
	    	s.setCountryName(cName);
	    	s.setSearchTime(datetime);
	    	s.setServerCode(serverCode);
	    	
	    	List<SIMInfoCountryTemp> sL=(serverCode.equals("0")) ? simInfoCountryTempDao.beika(s):simInfoCountryTempDao.QDbeika(s);
	    	if(!sL.isEmpty()){
	    	    FlowDealOrders f=new FlowDealOrders();
	    	    f.setUsingDate(datetime);
	    	    f.setServerCode(serverCode);
	    	    List<FlowDealOrders> fL=flowDealOrdersDao.getFlowDealOrdersByUsingDate(f);
	    	    for(SIMInfoCountryTemp sc:sL){
	    		setSIMStatusCount(sc);
	    		if(!fL.isEmpty()){
	    		   for(FlowDealOrders fdo:fL){
	    			if(fdo.getUserCountry().indexOf(sc.getCountryCode())>-1){
	    			    //设备号SN
	    			    if(fdo.getUserCountry().indexOf("|")>-1){
	    				sc.setSnArray(sc.getSnArray()+"<span style='color:green'>"+fdo.getSN().substring(9,15)+"</span>"+",");
	    			    }else{
	    				sc.setSnArray(sc.getSnArray()+fdo.getSN().substring(9,15)+",");
	    			    }
	    			    
	    			    sc.setSnArray(sc.getSnArray().replaceAll("null", ""));
	    			    sc.setOrderCount(Integer.parseInt((sc.getOrderCount()==null ||sc.getOrderCount().isEmpty()) ? "0":sc.getOrderCount())+1+"");
	    			}
	    		    }
	    		}else{
	    		    sc.setOrderCount("0");
	    		    sc.setSnArray("");
	    		}
	    	    }
	    	    
	    	}
		return sL;
	}
	//设置sim卡各种状态的数量值
		public void setSIMStatusCount(SIMInfoCountryTemp sc){
		    if(sc.getCardStatusCountTemp() != null && !sc.getCardStatusCountTemp().isEmpty()){
			String[] cardStatusCountStrArry =sc.getCardStatusCountTemp().split(";");
			for(int i=0;i<cardStatusCountStrArry.length;i++){
			    String cardStatusCountTemp=cardStatusCountStrArry[i].replaceAll("[1-9]\\d*", "").replaceAll(":","");
			    String cardCountTemp=cardStatusCountStrArry[i].replaceAll(":","").replaceAll("[\u4e00-\u9fa5]", "");
			    int cardCount=cardCountTemp.isEmpty() ? 0 : Integer.parseInt(cardCountTemp);
			    switch (cardStatusCountTemp) {
			    	case "可用":sc.setAvailable(cardCount);break;
			    	case "不可用":sc.setNotavailable(cardCount);break;
			    	case "使用中":sc.setUseing(cardCount);break;
			    	case "停用":sc.setDisabledTwo(cardCount);break;
			    	case "下架":sc.setOfftheshelf(cardCount);break;
			    	case "库存":sc.setStock(cardCount);break;
			    	default:
			    	    break;
			    }
			    sc.setSIMCount(sc.getAvailable()+sc.getNotavailable()+sc.getUseing()+sc.getDisabledTwo());
			}
		    }
		}
}
