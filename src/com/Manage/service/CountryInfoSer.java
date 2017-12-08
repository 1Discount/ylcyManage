package com.Manage.service;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.dao.CountryInfoDao;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.common.SearchDTO;

@Service
public class CountryInfoSer extends BaseService{
	private Logger logger = LogUtil.getInstance(CountryInfoSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("国家分页开始");
		try {
			String jsonString = countryInfoDao.getPage(searchDTO);
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
		logger.debug("国家分页开始");
		try {
			String jsonString = countryInfoDao.getPageDeleted(searchDTO);
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
	 * 查询全部记录列表
	 * @param arg
	 * @return
	 */
	public List<CountryInfo> getAll(String arg){
		logger.debug("查询全部国家开始");
		try {
			return countryInfoDao.getAll(arg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询国家详情 by ID
	 * @param id
	 * @return
	 */
	public CountryInfo getById(String id){
		logger.debug("根据ID查询国家详情开始");
		try {
			return countryInfoDao.getById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查询国家详情 by MCC 国家编号
	 * @param id
	 * @return
	 */
	public CountryInfo getByMCC(String id){
		logger.debug("根据MCC查询国家详情开始");
		try {
			return countryInfoDao.getByMCC(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 插入
	 *
	 * ahming notes: 针对区分各种错误, 返回 BmException 的错误码
	 * @param info
	 * @return String // old: boolean
	 */
	public String insertInfo(CountryInfo info) {
		logger.debug("开始执行插入国家信息");
		String result;
		try {
			if (countryInfoDao.insertInfo(info) > 0) {
				logger.debug("插入国家成功");
				result = Constants.common_errors_general_ok_100;
			} else {
				logger.debug("插入国家失败");
				result = Constants.common_errors_general_failed_101;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			result = e.getMessageCode();
			e.printStackTrace();
		}
		return result;

	}
	/**
	 * 插入temp
	 *
	 * ahming notes: 针对区分各种错误, 返回 BmException 的错误码
	 * @param info
	 * @return String // old: boolean
	 */
	public String insertInfoTemp(String insertStr) {
		logger.debug("开始执行插入国家信息");
		String result;
		try {
			if (countryInfoDao.insertInfoTemp(insertStr) > 0) {
				logger.debug("插入国家成功");
				result = Constants.common_errors_general_ok_100;
			} else {
				logger.debug("插入国家失败");
				result = Constants.common_errors_general_failed_101;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			result = e.getMessageCode();
			e.printStackTrace();
		}
		return result;

	}
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public String updateInfo(CountryInfo info) {
		logger.debug("开始执行更新国家信息");
		String result;
		try {
			if (countryInfoDao.updateInfo(info) > 0) {
				logger.debug("更新国家成功");
				result = Constants.common_errors_general_ok_100;
			} else {
				logger.debug("更新国家失败");
				result = Constants.common_errors_general_failed_101;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			result = e.getMessageCode();
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(CountryInfo info) {
		logger.debug("开始执行更新国家信息sysStatus");
		try {
			if (countryInfoDao.updateInfoSysStatus(info) > 0) {
				logger.debug("更新国家sysStatus成功");
				return true;
			} else {
				logger.debug("更新国家sysStatus失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	/*<!-- 根据国家名称查询国家信息表 -->*/
	public List<CountryInfo> getcountryinfoBycountryname(CountryInfo CountryInfo){
		try {
			return countryInfoDao.getcountryinfoBycountryname(CountryInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据国家名称分组
	 * @return
	 */
	public List<CountryInfo> groupBycountryName(){
		try {
			return countryInfoDao.groupBycountryName();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询国家的数量
	 * @return
	 */
	public int groupCount(){
		try {
			return countryInfoDao.groupCount();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 * 将国家编码转换为国家名称
	 */
	public CountryInfo convertcountry(CountryInfo countryiInfo){
		try {
			return countryInfoDao.convertcountry(countryiInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public CountryInfo selectCountryInfoBycname(CountryInfo info){
		try {
			return countryInfoDao.selectCountryInfoBycname(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int updateCountryByName(CountryInfo countryInfo){
		try {
			return countryInfoDao.updateCountryByName(countryInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<CountryInfo> exportexcel(CountryInfo countryInfo){
		try
		{
			return countryInfoDao.exportexcel(countryInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 修改
	 * @param countryInfo
	 * @return
	 */
	public boolean updateinfotwo(CountryInfo countryInfo){
		try
		{
			logger.info("批量修改国家得到请求");
			if(countryInfoDao.updateinfotwo(countryInfo)>0){
				logger.info("批量修改国家成功");
				return true;
			}else{
				logger.info("批量修改国家失败");
				return false;
			}
			
		}
		catch (Exception e)
		{
			logger.info("批量修改国家失败");
			e.printStackTrace();
			logger.info(e.getCause());
		}
		return false;
	}
	
	public List<CountryInfo> selectCountryByContinent(){
		try
		{
			return countryInfoDao.selectCountryByContinent();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info("根据大洲获取国家信息失败");
		}
		return null;
	}
}
