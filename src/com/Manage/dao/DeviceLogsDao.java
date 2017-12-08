package com.Manage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DevMessage;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.LocTemp;
import com.Manage.entity.MSMRecord;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.google.gson.Gson;

@Repository
public class DeviceLogsDao extends BaseDao {

	private static final String NAMESPACE = DeviceLogsDao.class.getName() + ".";
	private static final String NAMESPACESIM = SIMInfoDao.class.getName() + ".";

	/**
	 * 当天设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();

			String cusIDs = "";
			for (DeviceLogs a : arr) {
				String cusID = a.getCustomerID();
				cusIDs = cusIDs + "'" + cusID + "',";
			}
			if (!"".equals(cusIDs)) {
				cusIDs = cusIDs.substring(0, cusIDs.length() - 1);
			} else {
				cusIDs = "'000'";
			}
			CustomerInfo info = new CustomerInfo();
			info.setCustomerID(cusIDs);
			List<CustomerInfo> cus = getSqlSession().selectList(
					"com.Manage.dao.CustomerInfoDao.getCusByID", info);
			HashMap<String, String> cusMap = new HashMap<String, String>();
			for (CustomerInfo item : cus) {
				cusMap.put(String.valueOf(item.getCustomerID()),
						item.getCustomerType());
			}

			for (DeviceLogs a : arr) {
				// 查询客户
				FlowDealOrders dd = getFlowBysn(a.getSN());
				if (dd == null) {
					a.setCustomerName("");
					a.setCustomerID("");
					a.setOrderExplain("");
					a.setOrderRemark("");
				} else {
					a.setCustomerName(dd.getCustomerName());
					a.setCustomerID(dd.getCustomerID());
					a.setOrderRemark(dd.getRemark());
					CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(
							dd.getUserCountry());
					String cnames = wrapper.getCountryNameStrings();
					a.setOrderExplain(cnames + "/" + dd.getFlowDays()
							+ "天/到期时间:" + dd.getFlowExpireDate());
				}
				DeviceLogs d06 = new DeviceLogs();
				d06.setSN(a.getSN());
				d06.setTableName(((DeviceLogs) searchDTO.getObj())
						.getTableName());
				// 先查是否是登陆.
				DeviceLogs dn = getNewOne(d06);
				if (dn.getType().equals("00")) {
					a.setType("00");
					a.setSIMAllot(dn.getSIMAllot());
					a.setSpeedType(-1);
					a.setLastTime(dn.getLastTime());
					a.setJizhan(dn.getJizhan());
				} else {
					a.setIMSI(dn.getIMSI());
				}
				// 查询最近的01
				DeviceLogs dl = new DeviceLogs();
				dl.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
				dl.setSN(a.getSN());
				dl.setTableName(((DeviceLogs) searchDTO.getObj())
						.getTableName());
				DeviceLogs d2 = getone01(dl);
				if (d2 == null) {
					a.setUpFlowAll("暂无数据");
					a.setDayUsedFlow("暂无数据");
					a.setDownFlowAll("暂无数据");
					a.setLastTime01(a.getLastTime());
				} else {
					a.setUpFlowAll(d2.getUpFlowAll());
					a.setDownFlowAll(d2.getDownFlowAll());
					a.setDayUsedFlow(d2.getDayUsedFlow());
					a.setLastTime01(d2.getLastTime());
					// a.setIMSI(d2.getIMSI());
				}
				// 查询高低速
				if (StringUtils.isNotBlank(a.getIMSI())
						&& a.getIMSI().length() == 18) {

					SIMInfo simInfo = getByImsi(a.getIMSI());
					if (StringUtils.isNotBlank(simInfo)) {
						a.setSIMID(simInfo.getSIMinfoID());
						a.setSpeedType(simInfo.getSpeedType());
					} else {
						a.setSpeedType(-1);
					}
				}
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
				String tag = gettagString1(a.getSN());
				a.setTag(tag);
				JSONObject obj = JSONObject.fromObject(a);

				String cusType = cusMap.get(a.getCustomerID());
				if (null == cusType) {
					cusType = "普通客户";
				}
				obj.put("customerType", cusType);

				ja.add(obj);
			}
			object.put("data", ja);
			System.out.println(object.toString());
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * 实时设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpagenews(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPagenew", "getcountnew",
					searchDTO);

			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceLogs a : arr) {
				FlowDealOrders dd = getFlowBysn(a.getSN());
				if (dd == null) {
					a.setCustomerName("");
					a.setCustomerID("");
					a.setOrderExplain("");
					a.setOrderRemark("");
				} else {
					a.setCustomerName(dd.getCustomerName());
					a.setCustomerID(dd.getCustomerID());
					a.setOrderRemark(dd.getRemark());
					CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(
							dd.getUserCountry());
					String cnames = wrapper.getCountryNameStrings();
					a.setOrderExplain(cnames + "/" + dd.getFlowDays()
							+ "天/到期时间:" + dd.getFlowExpireDate());
				}
				DeviceLogs d06 = new DeviceLogs();
				d06.setLastTime(a.getLastTime());
				d06.setSN(a.getSN());
				d06.setTableName(((DeviceLogs) searchDTO.getObj())
						.getTableName());
				// 先最近3秒的数据.
				List<DeviceLogs> dnlist = getNew3s(d06);
				boolean ifout = false; // 是否有退出的标识
				if (dnlist.size() > 0) {
					for (DeviceLogs ds : dnlist) {
						if (ds.getType().equals("06")) {
							ifout = true;
						}
					}
					if (ifout) {
						continue;
					} else {
						if (dnlist.get(0).getType().equals("00")) {
							a.setType("00");
							a.setSIMAllot(dnlist.get(0).getSIMAllot());
							a.setSpeedType(-1);
							a.setJizhan(dnlist.get(0).getJizhan());
						}
					}
				}

				// 查询最近的01
				DeviceLogs dl = new DeviceLogs();
				dl.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
				dl.setSN(a.getSN());
				dl.setTableName(((DeviceLogs) searchDTO.getObj())
						.getTableName());
				DeviceLogs d2 = getone01(dl);
				if (d2 == null) {
					a.setUpFlowAll("暂无数据");
					a.setDayUsedFlow("暂无数据");
					a.setDownFlowAll("暂无数据");
					a.setLastTime01("1970-01-01:00:00.0");
				} else {
					a.setUpFlowAll(d2.getUpFlowAll());
					a.setDownFlowAll(d2.getDownFlowAll());
					a.setDayUsedFlow(d2.getDayUsedFlow());
					a.setLastTime01(d2.getLastTime());
					a.setJizhan(d2.getJizhan());
				}
				// 查询高低速
				if (StringUtils.isNotBlank(a.getIMSI())) {
					SIMInfo simInfo = getByImsi(a.getIMSI());
					if (StringUtils.isNotBlank(simInfo)) {
						a.setSIMID(simInfo.getSIMinfoID());
						a.setSpeedType(simInfo.getSpeedType());
					} else {
						a.setSpeedType(-1);
					}
				}
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
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

	/**
	 * 设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageloginLogs(SearchDTO searchDTO) {
		try {
			// Page
			// page=queryPage(NAMESPACE,"queryPageloginlogs","getcountloginlogs",searchDTO);
			Page page = queryPage(NAMESPACE, "queryPagefourlogs",
					"getcountfourlogs", searchDTO);
			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceLogs a : arr) {
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
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

	/**
	 * 登录设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageloginsLogs(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageloginslogs",
					"getcountloginslogs", searchDTO);
			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceLogs a : arr) {
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
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

	/**
	 * 异常登录设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageloginsLogsEXC(SearchDTO searchDTO) {
		try {
			// Page
			// page=queryPage(NAMESPACE,"queryPageloginlogs","getcountloginlogs",searchDTO);
			Page page = queryPage(NAMESPACE, "queryPageloginslogsEXC",
					"getcountloginslogsEXC", searchDTO);
			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceLogs a : arr) {
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));

				String tag = gettagString(a.getSN());

				a.setTag(tag);
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

	public String gettagString1(String sn) {
		/*
		 * String locationString = Constants.getConfig("SNtaglocation"); File
		 * fileName = new File(locationString); try { if (!fileName.exists()) {
		 * return ""; } } catch (Exception e) { e.printStackTrace(); } String
		 * result = ""; FileReader fileReader = null; BufferedReader
		 * bufferedReader = null; try { fileReader = new FileReader(fileName);
		 * bufferedReader = new BufferedReader(fileReader); String read = null;
		 * while ((read = bufferedReader.readLine()) != null) { result = result
		 * + read; }
		 * 
		 * logger.info("输出字符串:"+result);
		 * 
		 * result =new String(result.getBytes("utf-8"),"gbk");
		 * 
		 * logger.info("utf-8转gbk后:"+result); result =new
		 * String(result.getBytes("iso-8859-1"),"gbk");
		 * 
		 * logger.info("iso转gbk后:"+result);
		 * 
		 * if(fileReader!=null){ fileReader.close(); } if(bufferedReader!=null){
		 * bufferedReader.close(); } JSONObject json=null;
		 * if(StringUtils.isBlank(result)){ return ""; }else{ //开始解析读取的文件
		 * json=JSONObject.fromObject(result); if(json.containsKey(sn)){ return
		 * json.getString(sn); }else{ return ""; } } } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		Object object = CacheUtils.get("fileCache", "SNTAG");
		JSONObject json = null;
		if (object == null || "".equals(object.toString())) {
			return "";
		} else {
			json = JSONObject.fromObject(object.toString());
			if (json.containsKey(sn)) {
				return json.getString(sn);
			} else {
				return "";
			}
		}
	}

	/**
	 * 读取登录异常设备标记
	 * 
	 * @param sn
	 * @return
	 */
	public String gettagString(String sn) {
		/*
		 * String locationString = Constants.getConfig("SNtaglocation"); File
		 * fileName = new File(locationString); try { if (!fileName.exists()) {
		 * return ""; } } catch (Exception e) { e.printStackTrace(); } String
		 * result = ""; FileReader fileReader = null; BufferedReader
		 * bufferedReader = null; try { fileReader = new FileReader(fileName);
		 * bufferedReader = new BufferedReader(fileReader); String read = null;
		 * while ((read = bufferedReader.readLine()) != null) { result = result
		 * + read; }
		 * 
		 * logger.info("输出字符串:"+result);
		 * 
		 * result =new String(result.getBytes("utf-8"),"gbk");
		 * 
		 * logger.info("utf-8转gbk后:"+result); result =new
		 * String(result.getBytes("iso-8859-1"),"gbk");
		 * 
		 * logger.info("iso转gbk后:"+result);
		 * 
		 * if(fileReader!=null){ fileReader.close(); } if(bufferedReader!=null){
		 * bufferedReader.close(); } JSONObject json=null;
		 * if(StringUtils.isBlank(result)){ return ""; }else{ //开始解析读取的文件
		 * json=JSONObject.fromObject(result); if(json.containsKey(sn)){ return
		 * json.getString(sn); }else{ return ""; } } } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		Object object = CacheUtils.get("fileCache", "SNTAGIMSI");
		JSONObject json = null;
		if (object == null || "".equals(object.toString())) {
			return "";
		} else {
			json = JSONObject.fromObject(object.toString());
			if (json.containsKey(sn)) {
				String temp = json.getString(sn);
				if (temp == null) {
					temp = "";
				}
				if (temp.indexOf(DateUtils.getDate("yyyy-MM-dd")) != -1) {
					return json.getString(sn);
				} else {
					return "";
				}
			} else {
				return "";
			}
		}
	}

	/**
	 * 本地卡日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpagebdcardLogs(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPagefourlogs",
					"getcountfourlogs", searchDTO);
			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceLogs a : arr) {
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
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

	/**
	 * 漫游卡流量日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpagemanyouLogs(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPagefourlogs",
					"getcountfourlogs", searchDTO);
			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceLogs a : arr) {
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
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

	/**
	 * 透传
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpagetouchuanLogs(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPagefourlogs",
					"getcountfourlogs", searchDTO);
			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceLogs a : arr) {
				a.setLastTime(a.getLastTime().substring(0,
						a.getLastTime().length() - 2));
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

	/**
	 * 根据sn获取漫游软件版本
	 * 
	 * @param sn
	 * @return
	 */
	public DeviceLogs getfirmWareVer(DeviceLogs de) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getfirmWareVer", de);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public DeviceLogs getversion(DeviceLogs de) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getversion", de);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public DeviceLogs getone01(DeviceLogs de) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getone01", de);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public DeviceLogs getNewOne(DeviceLogs de) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getNewOne", de);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据SIMInfo IMSI）
	 */
	public SIMInfo getByImsi(String imsi) {
		try {
			return (SIMInfo) getSqlSession().selectOne(
					NAMESPACESIM + "queryByImsi", imsi);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 根据SN查询最近3秒的设备日志
	 * 
	 * @param de
	 * @return
	 */
	public List<DeviceLogs> getNew3s(DeviceLogs de) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getNew3s", de);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public DeviceDealOrders getbysn(String sn) {
		try {
			return getSqlSession().selectOne(
					DeviceDealOrdersDao.class.getName() + "." + "getbysn", sn);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}

	}

	public FlowDealOrders getFlowBysn(String sn) {
		try {
			return getSqlSession().selectOne(
					FlowDealOrdersDao.class.getName() + "." + "getbysn", sn);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}

	// =============================
	/**
	 * 根据状态查询sim卡
	 * 
	 * @param status
	 * @return
	 */
	public List<SIMInfo> getbystatus(String status) {
		try {
			return getSqlSession().selectList(NAMESPACESIM + "getbystatus",
					status);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 获取指定国家当天的在线流量订单
	 * 
	 * @return
	 */
	public List<DeviceLogs> getcountryonline(CountryInfo countryInfo) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getcountryonline",
					countryInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<DeviceLogs> getcountryonline1(Map tableName) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getcountryonline1",
					tableName);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<DeviceLogs> getJZ(DeviceLogs de) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getLastJZ", de);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询POS基站信息
	 * 
	 * @param de
	 * @return
	 */
	public List<DeviceLogs> getPOSjz(DeviceLogs de) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getPOSjz", de);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<DeviceLogs> getjzlist(DeviceLogs de) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getjzlist", de);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 批量插入基站组数据
	 * 
	 * @param de
	 * @return
	 */
	public int batchAddJZTemp(List<LocTemp> de) {
		try {
			return getSqlSession().insert(NAMESPACE + "batchAddJZTemp", de);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<LocTemp> getJWbyLike(LocTemp de) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getJWbyLike", de);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 插入短信记录
	 * 
	 * @param de
	 * @return
	 */
	public int insertDevMessage(DevMessage de) {
		try {
			return getSqlSession().insert(NAMESPACE + "insertDevMessage", de);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询累计流量
	 * 
	 * @param deviceLog
	 * @return
	 */
	public DeviceLogs selectUserFlowLast(DeviceLogs deviceLog) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "selectUserFlowLast",
					deviceLog);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询最后一套设备日志记录.
	 */
	public String getLast() {
		String tablename = Constants.DEVTABLEROOT_STRING + DateUtils.getYear()
				+ DateUtils.getMonth() + DateUtils.getDay();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("tablename", tablename);
			String s = getSqlSession().selectOne(NAMESPACE + "getlast", map);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询最后一条01记录
	 * 
	 * @return
	 */
	public DeviceLogs getlastOne(DeviceLogs deviceLogs) {
		try {
			return getSqlSession().selectOne(
					"com.Manage.dao.DeviceLogsTempDao.getLast01", deviceLogs);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 获取某一个设备当地时间所使用的总流量
	 * 
	 * @param map
	 */
	public long getFlowCount(Map<String, String> map,boolean isSameDay) {
		// TODO Auto-generated method stub
		try {
			long count =0;
//			if(isSameDay){
//				count=getSqlSession().selectOne(NAMESPACE + "getFlowCountSameDay",map);
//			}else{
				count = getSqlSession().selectOne(NAMESPACE + "getFlowCount",map);
//			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public int saveSendMsgRecord(MSMRecord msg) {
		// TODO Auto-generated method stub
		try {
			return getSqlSession().insert(NAMESPACE + "saveSendMsgRecord", msg);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public String getsendmsgrecord(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		try {
			Page page = queryPage(NAMESPACE, "querySendMsgRecord","getCountSendMsgRecord", searchDTO);
			List<MSMRecord> arr = (List<MSMRecord>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (MSMRecord a : arr) {
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

}
