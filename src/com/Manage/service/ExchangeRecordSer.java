package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.ExchangeRecord;
import com.Manage.entity.common.SearchDTO;


@Service
public class ExchangeRecordSer extends BaseService{
	private Logger logger = LogUtil.getInstance(ExchangeRecordSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("兑换码兑换记录分页开始");
		try {
			String jsonString = exchangeRecordDao.getPage(searchDTO);
			return jsonString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			return "";
		}
	}

	/**
	 * 查询全部记录列表
	 * @param arg
	 * @return
	 */
	public List<ExchangeRecord> getAll(ExchangeRecord info){
		logger.debug("兑换码兑换记录查询全部记录");
		try {
			return exchangeRecordDao.getAll(info);
		} catch (BmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 查询详情 by ID
	 * @param id
	 * @return
	 */
	public ExchangeRecord getById(String id){
		logger.debug("兑换码兑换记录查询详情By ID");
		try {
			return exchangeRecordDao.getById(id);
		} catch (BmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 插入
	 *
	 * @param info
	 * @return String // old: boolean
	 */
	public boolean insertInfo(ExchangeRecord info) {
		logger.debug("兑换码兑换记录插入");
		try {
			if (exchangeRecordDao.insertInfo(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public boolean updateInfo(ExchangeRecord info) {
		logger.debug("兑换码兑换记录更新");
		try {
			if (exchangeRecordDao.updateInfo(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(ExchangeRecord info) {
		logger.debug("兑换码兑换记录更新sysStatus");
		try {
			if (exchangeRecordDao.updateInfoSysStatus(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return false;
	}

}