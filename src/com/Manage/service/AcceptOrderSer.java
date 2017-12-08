package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.common.SearchDTO;

@Service
public class AcceptOrderSer extends BaseService{

	private Logger logger = LogUtil.getInstance(AcceptOrderSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO) {
		logger.debug("接单分页开始");
		try {
			String jsonString = acceptOrderDao.getPage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public boolean insert(AcceptOrder acceptOrder){
		logger.debug("开始执行插入接单信息");
		try {
			if (acceptOrderDao.insert(acceptOrder) > 0) {
				logger.debug("插入接单信息成功");
				return true;
			} else {
				logger.debug("插入接单信息失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据ID查询接单信息
	 * @param aoID
	 * @return
	 */
	public AcceptOrder getById(String aoID){
		return acceptOrderDao.getById(aoID);
	}

	/**
	 * 根据ID修改接单信息
	 * @param acceptOrder
	 * @return
	 */
	public boolean update(AcceptOrder acceptOrder){
		logger.debug("开始执行修改接单信息");
		try {
			if (acceptOrderDao.update(acceptOrder) > 0) {
				logger.debug("修改接单信息成功");
				return true;
			} else {
				logger.debug("修改接单信息失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteById(String aoID){
		logger.debug("开始执行删除接单信息");
		try {
			if (acceptOrderDao.deleteById(aoID) > 0) {
				logger.debug("删除接单信息成功");
				return true;
			} else {
				logger.debug("删除接单信息失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 修改接单状态
	 * @param aoID
	 * @return
	 */
	public boolean updateStatus(AcceptOrder acceptOrder){
		try {
			if (acceptOrderDao.update(acceptOrder) > 0) {
				logger.debug("修改接单信息成功");
				return true;
			} else {
				logger.debug("修改接单信息失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}


	public List<AcceptOrder> exprotexcel(AcceptOrder acceptOrder){
		try
		{
			return acceptOrderDao.exprotexcel(acceptOrder);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public int updateAccepOrderBySn(String aco){
		return acceptOrderDao.updateAccepOrderBySn(aco);
	}

}
