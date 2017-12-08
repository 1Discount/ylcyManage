package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AfterSaleInfo;
import com.Manage.entity.common.SearchDTO;

@Service
public class AfterSaleInfoSer extends BaseService
{

	private final Logger logger = LogUtil.getInstance(AfterSaleInfoSer.class);



	/**
	 * 新增售后记录
	 * 
	 * @param afterSaleInfo
	 * @return
	 */
	public boolean insert(AfterSaleInfo afterSaleInfo)
	{
		logger.debug("开始执行插入售后服务记录");
		try
		{
			if (afterSaleInfoDao.insert(afterSaleInfo) > 0)
			{
				logger.debug("插入售后服务记录成功");
				return true;
			}
			else
			{
				logger.debug("插入售后服务记录失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 根据ID查询
	 * 
	 * @param asiID
	 * @return
	 */
	public AfterSaleInfo getById(String asiID)
	{
		return afterSaleInfoDao.getById(asiID);
	}



	public AfterSaleInfo getByFlowDealID(String flowDealID)
	{
		return afterSaleInfoDao.getByFlowDealID(flowDealID);
	}



	/**
	 * 售后分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO)
	{
		logger.debug("售后分页开始");
		try
		{
			String result = afterSaleInfoDao.getPage(searchDTO);
			logger.debug("分页查询结果:" + result);
			return result;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}



	/**
	 * 删除售后记录
	 * 
	 * @param afterSaleInfo
	 * @return
	 */
	public boolean deleteById(String asiID)
	{
		logger.debug("开始执行删除售后服务记录");
		try
		{
			if (afterSaleInfoDao.deleteById(asiID) > 0)
			{
				logger.debug("删除售后服务记录成功");
				return true;
			}
			else
			{
				logger.debug("删除售后服务记录失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 根据流量订单ID查询退款售后记录
	 * 
	 * @param flowDealID
	 * @return
	 */
	public AfterSaleInfo getRefundInfo(String flowDealID)
	{
		return afterSaleInfoDao.getRefundInfo(flowDealID);
	}



	public List<AfterSaleInfo> getafterExprotExcel(AfterSaleInfo afterSaleInfo)
	{
		logger.info("开始导出售后记录数据Dao");
		try
		{
			return afterSaleInfoDao.getafterExprotExcel(afterSaleInfo);

		}
		catch (Exception e)
		{

			logger.info("导出售后记录数据失败Dao");
			e.printStackTrace();
		}
		return null;
	}
}
