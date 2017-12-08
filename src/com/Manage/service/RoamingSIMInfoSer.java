package com.Manage.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.FlowPlanInfo;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.SimStatusStatByCountry;

@Service
public class RoamingSIMInfoSer extends BaseService{
	private Logger logger = LogUtil.getInstance(RoamingSIMInfoSer.class);

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
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
			}
			String jsonString = roamingSimInfoDao.getPage(searchDTO, mccNameMap);
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
     * 分页查询 直接返回 Page 如果要显示国家名，在上层处理
     *
     * @param searchDTO
     * @return
     */
    public Page getPageInfo(SearchDTO searchDTO) {
        logger.debug("SIMInfo分页开始");
        try {
            return roamingSimInfoDao.getPageInfo(searchDTO);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.debug(e.getMessage());
            return null;
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
				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo 中为 String
				mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
			}
			String jsonString = roamingSimInfoDao.getPageDeleted(searchDTO, mccNameMap);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

//	/**
//	 * 查询全部记录列表
//	 * @param arg
//	 * @return
//	 */
//	public List<SIMInfo> getAll(String arg){
//		logger.debug("查询全部SIMInfo开始");
//		return roamingSimInfoDao.getAll(arg);
//	}

	/**
	 * 查询SIMInfo详情 by ID
	 * @param id
	 * @return
	 */
	public SIMInfo getById(String id){
		logger.debug("根据ID查询SIMInfo详情开始");
		return roamingSimInfoDao.getById(id);
	}

	/**
	 * 查询SIMInfo详情 by IMSI
	 * @param id
	 * @return
	 */
	public SIMInfo getByImsi(String imsi){
		logger.debug("根据ID查询SIMInfo详情开始");
		return roamingSimInfoDao.getByImsi(imsi);
	}

	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertInfo(SIMInfo info) {
		logger.debug("开始执行插入SIMInfo信息");
		try {
			if (roamingSimInfoDao.insertInfo(info) > 0) {
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
	 * @param info
	 * @return
	 */
	public boolean updateInfo(SIMInfo info) {
		logger.debug("开始执行更新SIMInfo信息");
		try {
			if (roamingSimInfoDao.updateInfo(info) > 0) {
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
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(SIMInfo info) {
		logger.debug("开始执行更新SIMInfo sysStatus");
		try {
			if (roamingSimInfoDao.updateInfoSysStatus(info) > 0) {
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
	 * @param SIMInfo info
	 * @return
	 */
	public Map<String,String> allCount(SIMInfo info){
		try {
			logger.debug("本地SIM卡状态统计查询");
			return roamingSimInfoDao.allCount(info);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过SIM卡状态查询各地区SIM卡数量
	 * @param info
	 * @return
	 */
	public List<SimStatusStatByCountry> getSimStatusStatByCountry(SIMInfo info) {
		logger.debug("开始按国家统计SIM卡状态");
		try {
			return roamingSimInfoDao.getSimStatusStatByCountry(info);
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 通过SIM卡状态查询各地区SIM卡数量
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByCountry(SearchDTO searchDTO) {
		logger.debug("开始按国家统计SIM卡状态 page");
		try {
//			List<CountryInfo> countries = countryInfoDao.getAll("");
//			HashMap<String, String> mccNameMap = new HashMap<String, String>();
//			for (CountryInfo item : countries) {
//				// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo 中为 String
//				mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
//			}
//			String jsonString = roamingSimInfoDao.getPageDeleted(searchDTO, mccNameMap);
//			logger.debug("分页查询结果:" + jsonString);
//			return jsonString;

			Page page = roamingSimInfoDao.getPageOfSimStatusStatByCountry(searchDTO);
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
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByOperator(SearchDTO searchDTO) {
		logger.debug("开始按运营商统计SIM卡状态 page");
		try {
			Page page = roamingSimInfoDao.getPageOfSimStatusStatByOperator(searchDTO);
			return page;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询是否存在此 ICCID 的SIM卡记录
	 *
	 * @param keyValue
	 * @return
	 */
	public boolean isExistPrimaryKey(String keyValue) {
		//logger.debug("");
		try {
			if (roamingSimInfoDao.getCountByPrimaryKey(keyValue) > 0) {
				//logger.debug("查询成功");
				return true;
			} else {
				//logger.debug("查询失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			//logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

    /**
     * ahming notes: 这个功能系何广超根据漫游卡管理实际情况提出的需求，在导入前手动执行将清除全部漫游卡信息！请只有在清楚问题情况下再继续清除
     *
     * @return
     */
    public int deleteAll() {
        logger.debug("开始执行删除全部漫游卡");
        try {
            return roamingSimInfoDao.deleteAll();
//            if (roamingSimInfoDao.deleteAll() > 0) {
//                logger.debug("删除全部漫游卡成功");
//                return true;
//            } else {
//                logger.debug("删除全部漫游卡失败"); // ... 也有可能是操作记录数为零，即原本没有任何记录,但不理会了
//                return false;
//            }
        } catch (BmException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

}
