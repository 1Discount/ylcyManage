package com.Manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DateUtil;
import com.Manage.common.util.FileWrite;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DevMessage;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.DeviceLogsTest;
import com.Manage.entity.LocTemp;
import com.Manage.entity.MSMRecord;
import com.Manage.entity.common.SearchDTO;

/**
 * 设备日志
 * 
 * @author lipeng
 */
@Service
public class DeviceLogsSer extends BaseService {

	private final Logger logger = LogUtil.getInstance(DeviceLogsSer.class);

	private static String timeZone = "{\"334\":-14,\"310\":-13,\"302\":-13,\"724\":-11,\"272\":-8,\"620\":-8,\"274\":-8,\"604\":-8,\"268\":-8,\"234\":-8,\"208\":-7,\"222\":-7,\"295\":-7,\"214\":-7,\"213\":-7,\"240\":-7,\"225\":-7,\"216\":-7,\"278\":-7,\"260\":-7,\"270\":-7,\"230\":-7,\"206\":-7,\"238\":-7,\"292\":-7,\"284\":-7,\"219\":-7,\"247\":-7,\"231\":-7,\"280\":-7,\"226\":-7,\"276\":-7,\"204\":-7,\"293\":-7,\"242\":-7,\"228\":-7,\"262\":-7,\"232\":-7,\"655\":-6,\"246\":-6,\"248\":-6,\"202\":-6,\"602\":-6,\"244\":-6,\"286\":-6,\"427\":-5,\"250\":-5,\"424\":-4,\"617\":-4,\"472\":-3,\"404\":-2.5,\"414\":-1.5,\"457\":-1,\"520\":-1,\"456\":-1,\"452\":-1,\"460\":0,\"454\":0,\"455\":0,\"466\":0,\"502\":0,\"515\":0,\"525\":0,\"450\":1,\"440\":1,\"510\":1,\"505\":2,\"530\":4,\"542\":4,\"212\":-7,\"288\":-8,\"266\":-8,\"218\":-7,\"401\":-2,\"535\":2,\"425\":-6,\"413\":-3,\"400\":-4,\"282\":-4}";
	/**
	 * 当天设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString;
			DeviceLogs deviceLogs = (DeviceLogs) searchDTO.getObj();
			String t1 = deviceLogs.getLogsDate().replace("-", "");
			if (Integer.parseInt(t1) <= 20150906) {
				deviceLogs.setTableName(Constants.DEVTABLEROOT_STRING + t1);
				jsonString = deviceLogsDao.getpage(searchDTO);
			} else {
				jsonString = deviceLogsTempDao.getHisString(searchDTO);
			}

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
	 * 实时设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageStringnews(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsTempDao.getnowString(searchDTO);
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
	 * 登录设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageloginLogs(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsDao.getpageloginLogs(searchDTO);
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
	 * 异常登录设备日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageloginLogsEXC(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsDao.getpageloginsLogsEXC(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public String getpageloginsLogs(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsDao.getpageloginsLogs(searchDTO);
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
	 * 本地卡流量日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpagebdcardLogs(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsDao.getpagebdcardLogs(searchDTO);
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
	 * 漫游卡流量日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpagemanyouLogs(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsDao.getpagemanyouLogs(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 透传日志
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpagetouchuanLogs(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsDao.getpagetouchuanLogs(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public DeviceLogs getfirmWareVer(DeviceLogs de) {
		try {
			return deviceLogsDao.getfirmWareVer(de);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public DeviceLogs getversion(DeviceLogs de) {
		try {
			return deviceLogsDao.getversion(de);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	// ==========================================
	/**
	 * 获取指定国家当天的在线流量订单
	 * 
	 * @return
	 */
	public List<DeviceLogs> getcountryonline(CountryInfo countryInfo) {
		logger.info("查询开始");
		try {
			return deviceLogsDao.getcountryonline(countryInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public List<DeviceLogs> getcountryonline1(Map tableName) {
		logger.info("查询开始");
		try {
			return deviceLogsDao.getcountryonline1(tableName);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <!-- 按日期最近一个月的日设备接入数 仅包含有上传流量-->
	 * 
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInCountByDay(DeviceLogs info) {
		logger.info("查询日设备接入数开始");
		try {
			return deviceLogsTempDao.getDeviceInCountByDay(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public List<DeviceLogs> getDeviceInCountByDay1(DeviceLogs info) {
		logger.info("查询日设备接入数开始");
		try {
			return deviceLogsTempDao.getDeviceInCountByDay1(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public int getDeviceInCountByDay2(String logTableName,String type){
	    DeviceLogs info=new DeviceLogs();
	    info.setLogsDate(logTableName);
	    info.setType(type);
	    logger.info("查询日设备接入数开始");
		try {
		    info=deviceLogsTempDao.getDeviceInCountByDay2(info);
		    if(info!=null){
			return info.getUploadFlowCount();
		    }
		    return 0;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * <!-- 按日期最近一个月的日设备接入数 包含有上传流量或没有上传流量的全部 -->
	 * 
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInCountByDayAll(DeviceLogs info) {
		logger.info("查询日设备接入数开始");
		try {
			return deviceLogsTempDao.getDeviceInCountByDayAll(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public List<DeviceLogs> getDeviceInCountByDayAll1(DeviceLogs info) {
		logger.info("查询日设备接入数开始");
		try {
			return deviceLogsTempDao.getDeviceInCountByDayAll1(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <!-- 按国家最近一个月的设备接入数 -->
	 * 
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInCountByMCC(DeviceLogs info) {
		logger.info("查询日设备接入数开始");
		try {
			return deviceLogsTempDao.getDeviceInCountByMCC(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public List<DeviceLogs> getDeviceInCountByMCC1(DeviceLogs info) {
		logger.info("查询日设备接入数开始");
		try {
			return deviceLogsTempDao.getDeviceInCountByMCC1(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设备按时间段查看每天接入情况和统计流量
	 * 
	 * @author tangming@easy2go.cn
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInBySnAndDate(DeviceLogs info) {
		logger.info("查询设备日接入情况和流量开始");
		try {
			return deviceLogsTempDao.getDeviceInBySnAndDate(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public List<DeviceLogs> getJZ(DeviceLogs info) {
		logger.info("根据SN查询最近10个基站");
		try {
			return deviceLogsDao.getJZ(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public List<DeviceLogs> getflowBySnAndDate(DeviceLogs info) {
		logger.info("查询设备日接入情况和流量开始");
		try {
			return deviceLogsTempDao.getflowBySnAndDate(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public DeviceLogsTest getflowBySnAndDateAPI(DeviceLogs info) {
		logger.info("查询设备日接入情况和流量开始");
		try {
			return deviceLogsTempDao.getflowBySnAndDateAPI(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询POS机基站信息
	 * 
	 * @param de
	 * @return
	 */
	public List<DeviceLogs> getPOSjz(DeviceLogs de) {
		logger.info("查询POS机基站信息");
		try {
			return deviceLogsDao.getPOSjz(de);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public List<DeviceLogs> getjzlist(DeviceLogs de) {
		logger.info("查询POS机基站信息");
		try {
			return deviceLogsDao.getjzlist(de);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	public int batchAddJZTemp(List<LocTemp> de) {
		logger.info("查询POS机基站信息");
		try {
			return deviceLogsDao.batchAddJZTemp(de);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return 0;
	}

	public List<LocTemp> getJWbyLike(LocTemp de) {
		logger.info("查询是否有存在基站组");
		try {
			return deviceLogsDao.getJWbyLike(de);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据SN查询设备信息
	 * 
	 * @param de
	 * @return
	 */
	public DeviceLogs getNewOne(DeviceLogs de) {
		return deviceLogsDao.getNewOne(de);
	}

	public List<DeviceLogs> getdevicelogTempbysn(DeviceLogs info) {
		try {
			return deviceLogsTempDao.getdevicelogTempbysn(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 插入设备短信记录
	 * 
	 * @param de
	 * @return
	 */
	public int insertDevMessage(DevMessage de) {
		try {
			return deviceLogsDao.insertDevMessage(de);
		} catch (Exception e) {
			FileWrite.printlog(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 查询累计流量
	 * 
	 * @param deviceLog
	 * @return
	 */
	public DeviceLogs selectUserFlowLast(DeviceLogs deviceLog) {
		try {
			return deviceLogsDao.selectUserFlowLast(deviceLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询最后一条01记录
	 * 
	 * @return
	 */
	public DeviceLogs getlastOne(DeviceLogs deviceLogs) {
		try {
			return deviceLogsDao.getlastOne(deviceLogs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询抢卡
	 * 
	 * @return
	 */
	public List<DeviceLogs> searchGrabCard() {
		return deviceLogsTempDao.searchGrabCard();
	}

	public String getNowString1(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = deviceLogsTempDao.getnowString1(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public String gitJieruInfo(SearchDTO searchDTO) {
		try {

			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
				// 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}

			return deviceLogsTempDao.gitJieruInfo(searchDTO, mccNameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据当地日期获取北京时间对应的时间段
	 * 
	 * @author zwh
	 */
	private static String[] getTimeSqr(int sc, String date) {
		long currTime = DateUtil.parseDateYYYY_MM_DD(date).getTime();
		long timeRel = -sc * 60 * 60 * 1000;
		String[] array = new String[4];
		array[0] = DateUtil.formatDateToString(currTime + timeRel, true);
		array[1] = DateUtil.formatDateToString(currTime + timeRel + 24 * 60
				* 60 * 1000 + 10 * 60 * 1000, true);
		array[2] = DateUtil.formatDateToString(currTime + timeRel, "yyyyMMdd");
		array[3] = DateUtil.formatDateToString(currTime + timeRel + 24 * 60
				* 60 * 1000 - 1000, "yyyyMMdd");
		return array;
	}

	// 根据日志流水账统计当天用的流量
	public long getFlowCount(String ddtime, String mcc, String sn, String sc) {
		// TODO Auto-generated method stub
		if(sc==null||"".equals(sc.trim())){
			try {
				JSONObject json=JSONObject.fromObject(timeZone);
				sc=json.getString(mcc);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if(sc==null||"".equals(sc.trim())){
				sc="0";
			}
			
		}
		// 根据mcc换算成北京时间的范围
		String[] time = getTimeSqr(Integer.parseInt(sc), ddtime);
		Map<String, String> map = new HashMap<>();
		map.put("startTime", time[0]);
		map.put("endTime", time[1]);
		map.put("mcc", mcc);
		map.put("sn", sn);
		map.put("table1", Constants.DEVTABLEROOT_STRING + time[2]);
		map.put("table2", Constants.DEVTABLEROOT_STRING + time[3]);
		boolean isSameDay = false;
		if (time[2].equals(time[3])) {
			isSameDay = true;
		}
		return deviceLogsDao.getFlowCount(map, isSameDay);
	}

	public static void main(String[] args) {
		String s[] = getTimeSqr(-3, "2016-10-22");
		System.out.println("开始时间：" + s[0] + "   " + s[1]);
	}

	public int saveSendMsgRecord(MSMRecord msg) {
		// TODO Auto-generated method stub
		return deviceLogsDao.saveSendMsgRecord(msg);
	}

	public String getsendmsgrecord(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		logger.debug("分页server开始");
		try {
			String jsonString;
			//MSMRecord deviceLogs = (MSMRecord) searchDTO.getObj();
			jsonString = deviceLogsDao.getsendmsgrecord(searchDTO);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 查询设备是否有记录
	 * @param deviceLogs
	 * @return
	 */
	public int selecthavelogs(DeviceLogs deviceLogs){
		if(deviceLogsTempDao.selecthavelogs(deviceLogs)==null){
			return 0;
		}else{
			return 1;
		}
	}
}
