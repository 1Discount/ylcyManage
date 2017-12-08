package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.ExchangeKey;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;


@Service
public class ExchangeKeySer extends BaseService{
	private Logger logger = LogUtil.getInstance(ExchangeKeySer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("兑换码分页开始");
		try {
			String jsonString = exchangeKeyDao.getPage(searchDTO);
			return jsonString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			return "";
		}
	}

	/**
	 * 分页，排序，条件查询 返回列表信息
	 *
	 * @param searchDTO
	 * @return
	 */
	public Page getPageInfo(SearchDTO searchDTO) {
		logger.debug("兑换码分页信息开始");
		try {
			return exchangeKeyDao.getPageInfo(searchDTO);
			//return jsonString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			return null;
		}
	}

	/**
	 * 查询全部记录列表
	 * @param arg
	 * @return
	 */
	public List<ExchangeKey> getAll(ExchangeKey info){
		logger.debug("兑换码查询全部记录");
		try {
			return exchangeKeyDao.getAll(info);
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
	public ExchangeKey getById(String id){
		logger.debug("兑换码查询详情By ID");
		try {
			return exchangeKeyDao.getById(id);
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
	public boolean insertInfo(ExchangeKey info) {
		logger.debug("兑换码插入");
		try {
			if (exchangeKeyDao.insertInfo(info) > 0) {
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
	public boolean updateInfo(ExchangeKey info) {
		logger.debug("兑换码更新");
		try {
			if (exchangeKeyDao.updateInfo(info) > 0) {
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
	public boolean updateInfoSysStatus(ExchangeKey info) {
		logger.debug("兑换码更新sysStatus");
		try {
			if (exchangeKeyDao.updateInfoSysStatus(info) > 0) {
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
	 * 更新可用状态 禁止或正常
	 * @param info
	 * @return
	 */
	public boolean updateInfoStatus(ExchangeKey info) {
		logger.debug("兑换码更新可用状态：" + info.getStatus());
		try {
			if (exchangeKeyDao.updateInfoStatus(info) > 0) {
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
	 * 更新短信发送次数，注意使用的系增量更改, 若要减少，使用一个负数相加
	 *
	 * @param info
	 * @return
	 */
	public boolean updateSendSmsCount(ExchangeKey info) {
		logger.debug("兑换码更新短信发送次数：" + info.getStatus());
		try {
			if (exchangeKeyDao.updateSendSmsCount(info) > 0) {
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