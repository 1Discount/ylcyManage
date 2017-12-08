package com.Manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.SIMCardUsageRecord;
import com.Manage.entity.SIMCost;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMRecord;
import com.Manage.entity.SIMcostStatistics;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.SimStatusStatByCountry;

@Service
public class SIMInfoSer extends BaseService {
	private Logger logger = LogUtil.getInstance(SIMInfoSer.class);

	/**
	 * 分页，排序，条件查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("SIMInfo分页开始");
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
				// 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			String jsonString = simInfoDao.getPage(searchDTO, mccNameMap);
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
	 * 分页，排序，条件查询 已删除记录
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeletedString(SearchDTO searchDTO) {
		logger.debug("SIMInfo删除记录分页开始");
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
				// 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			String jsonString = simInfoDao
					.getPageDeleted(searchDTO, mccNameMap);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	// /**
	// * 查询全部记录列表
	// * @param arg
	// * @return
	// */
	// public List<SIMInfo> getAll(String arg){
	// logger.debug("查询全部SIMInfo开始");
	// return simInfoDao.getAll(arg);
	// }

	/**
	 * 查询SIMInfo详情 by ID
	 * 
	 * @param id
	 * @return
	 */
	public SIMInfo getById(String id) {
		logger.debug("根据ID查询SIMInfo详情开始");
		return simInfoDao.getById(id);
	}

	/**
	 * 查询SIMInfo详情 by IMSI
	 * 
	 * @param id
	 * @return
	 */
	public SIMInfo getByImsi(String imsi) {
		logger.debug("根据ID查询SIMInfo详情开始");
		return simInfoDao.getByImsi(imsi);
	}

	/**
	 * 插入
	 * 
	 * @param info
	 * @return
	 */
	public boolean insertInfo(SIMInfo info) {
		logger.debug("开始执行插入SIMInfo信息");
		try {
			if (simInfoDao.insertInfo(info) > 0) {
				logger.debug("插入SIMInfo成功");
				return true;
			} else {
				logger.debug("插入SIMInfo失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 更新
	 * 
	 * @param info
	 * @return
	 */
	public boolean updateInfo(SIMInfo info) {
		logger.debug("开始执行更新SIMInfo信息");
		try {
			if (simInfoDao.updateInfo(info) > 0) {
				logger.debug("更新SIMInfo成功");
				return true;
			} else {
				logger.debug("更新SIMInfo失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 更新 sysStatus
	 * 
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(SIMInfo info) {
		logger.debug("开始执行更新SIMInfo sysStatus");
		try {
			if (simInfoDao.updateInfoSysStatus(info) > 0) {
				logger.debug("更新SIMInfo sysStatus成功");
				return true;
			} else {
				logger.debug("更新SIMInfo sysStatus失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 总数统计查询
	 * 
	 * @param SIMInfo
	 *            info
	 * @return
	 */
	public Map<String, String> allCount(SIMInfo info) {
		try {
			logger.debug("本地SIM卡状态统计查询");
			return simInfoDao.allCount(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过SIM卡状态查询各地区SIM卡数量
	 * 
	 * @param info
	 * @return
	 */
	public List<SimStatusStatByCountry> getSimStatusStatByCountry(SIMInfo info) {
		logger.debug("开始按国家统计SIM卡状态");
		try {
			return simInfoDao.getSimStatusStatByCountry(info);
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 通过SIM卡状态查询各地区SIM卡数量
	 * 
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByCountry(SearchDTO searchDTO) {
		logger.debug("开始按国家统计SIM卡状态 page");
		try {
			// List<CountryInfo> countries = countryInfoDao.getAll("");
			// HashMap<String, String> mccNameMap = new HashMap<String,
			// String>();
			// for (CountryInfo item : countries) {
			// // ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo 中为
			// String
			// mccNameMap.put(String.valueOf(item.getCountryCode()),
			// item.getCountryName());
			// }
			// String jsonString = simInfoDao.getPageDeleted(searchDTO,
			// mccNameMap);
			// logger.debug("分页查询结果:" + jsonString);
			// return jsonString;

			Page page = simInfoDao.getPageOfSimStatusStatByCountry(searchDTO);
			return page;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过SIM卡状态查询各运营商SIM卡数量
	 * 
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByOperator(SearchDTO searchDTO) {
		logger.debug("开始按运营商统计SIM卡状态 page");
		try {
			Page page = simInfoDao.getPageOfSimStatusStatByOperator(searchDTO);
			return page;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询SIM卡状态
	 */
	public List<SIMInfo> getcardStatus(SIMInfo simInfo) {
		try {
			return simInfoDao.getcardStatus(simInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询是否存在此IMSI的SIM卡记录
	 * 
	 * @param imsi
	 * @return
	 */
	public boolean isExistImsi(String imsi) {
		// logger.debug("");
		try {
			if (simInfoDao.getCountByIMSI(imsi) > 0) {
				// logger.debug("更新SIMInfo sysStatus成功");
				return true;
			} else {
				// logger.debug("更新SIMInfo sysStatus失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			// logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据服务器获得SIM卡
	 * 
	 * @return
	 */
	public List<SIMInfo> getSIMbyserverID(SIMInfo simInfo) {
		try {
			List<SIMInfo> simInfos = simInfoDao.getSIMbyserverID(simInfo);
			for (SIMInfo s : simInfos) {
				if (s.getSlotNumber() != null && !s.getSlotNumber().equals("")) {
					// System.out.println(s.getSlotNumber());
					String[] strings = s.getSlotNumber().split("-");
					s.setSlotNumber(strings[0] + "-"
							+ (Integer.parseInt(strings[1]) + 1));

				}

			}
			return simInfos;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据 simAlias查询SIM卡信息
	 * 
	 * @return
	 */
	public SIMInfo getSIMInfoBysimAlias(SIMInfo simInfo) {
		try {
			return simInfoDao.getSIMInfoBysimAlias(simInfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 标记SIM卡 notes 字段
	 * 
	 * @param info
	 * @return
	 */
	public boolean updateSimNotes(SIMInfo info) {
		logger.debug("开始执行更新SIM卡标记");
		try {
			if (simInfoDao.updateSimNotes(info) > 0) {
				logger.debug("标记成功");
				return true;
			} else {
				logger.debug("标记失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 批量修改SIM卡状态
	 * 
	 * @param info
	 * @return
	 */
	public boolean updataStatusByIMSI(SIMInfo info) {
		logger.debug("开始执行更新SIM卡标记");
		try {
			if (simInfoDao.updataStatusByIMSI(info) > 0) {
				logger.debug("批量修改SIM状态成功,IMSI:" + info.getIMSI());
				return true;
			} else {
				logger.debug("批量修改SIM状态失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 分页查询SIM历史绑定SN列表
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageSIMAndSN(SearchDTO searchDTO) {
		logger.debug("SIMAndSN分页开始");
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
				// 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			String jsonString = simInfoDao.getPageSIMAndSN(searchDTO,
					mccNameMap);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public List<SIMInfo> excelimportSIM(SIMInfo simInfo) {
		try {
			return simInfoDao.excelimportSIM(simInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SIMRecord> getSIMRecordByDate(SIMRecord simRecord) {

		try {
			return simInfoDao.getSIMRecordByDate(simRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页，排序，条件查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getSIMRecordPage(SearchDTO searchDTO) {
		logger.debug("SIMInfo分页开始");
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
				// 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			String jsonString = simInfoDao.getSIMRecordPage(searchDTO,
					mccNameMap);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 分页，排序，条件查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getSIMRecordPageByCountry(SearchDTO searchDTO) {
		logger.debug("SIMInfo分页开始");
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
				// 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			String jsonString = simInfoDao.getSIMRecordPageByCountry(searchDTO,
					mccNameMap);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public SIMRecord getSIMRecordByIMSI(SIMRecord simRecord) {
		try {
			return simInfoDao.getSIMRecordByIMSI(simRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页，排序，条件查询 SIM卡成本统计记录
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getSIMCardUsageRecordPage(SearchDTO searchDTO) {
		logger.debug("SIMCardUsageRecord分页开始");
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			String jsonString = simInfoDao.getSIMCardUsageRecordPage(searchDTO,
					mccNameMap);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 查询总消耗金额
	 * 
	 * @return
	 */
	public String searchMoneySum(SIMCardUsageRecord simCardUsageRecord) {
		try {
			return simInfoDao.searchMoneySum(simCardUsageRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有SIM卡信息
	 * 
	 * @return
	 */
	public List<SIMInfo> getAll(SIMInfo simInfo) {
		try {
			return simInfoDao.getAll(simInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SIMInfo> getDayUserAll(SIMInfo simInfo) {
		try {
			return simInfoDao.getDayUserAll(simInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertSIMcostStatistics(SIMcostStatistics siMcostStatistics) {
		try {
			return simInfoDao.insertSIMcostStatistics(siMcostStatistics);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getPageSIMcostStatistics1(SearchDTO searchDTO) {
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			return simInfoDao.getPageSIMcostStatistics1(searchDTO, mccNameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPageSIMcostStatistics(SearchDTO searchDTO) {
		try {
			return simInfoDao.getPageSIMcostStatistics(searchDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPageSIMcostStatisticsByDay(SearchDTO searchDTO) {
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			return simInfoDao.getPageSIMcostStatisticsByDay(searchDTO,
					mccNameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPageSIMcostStatisticsInfo(SearchDTO searchDTO) {
		try {
			return simInfoDao.getPageSIMcostStatisticsInfo(searchDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPageSiminfopirmeCostInfoThree(SearchDTO searchDTO) {
		try {
			return simInfoDao.getPageSiminfopirmeCostInfoThree(searchDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getSIMRecordPageByIMSI(SearchDTO searchDTO) {
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			return simInfoDao.getSIMRecordPageByIMSI(searchDTO, mccNameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getpageSIMUserInfoByplanType(SearchDTO searchDTO) {
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			return simInfoDao.getpageSIMUserInfoByplanType(searchDTO,
					mccNameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getpagesimbyplanType(SearchDTO searchDTO) {
		try {
			List<CountryInfo> countries = countryInfoDao.getAll("");
			HashMap<String, String> mccNameMap = new HashMap<String, String>();
			for (CountryInfo item : countries) {
				mccNameMap.put(String.valueOf(item.getCountryCode()),
						item.getCountryName());
			}
			return simInfoDao.getpagesimbyplanType(searchDTO, mccNameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<SIMCost> simcostexportbycountry(SIMInfo info) {
		try {
			return simInfoDao.simcostexportbycountry(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
