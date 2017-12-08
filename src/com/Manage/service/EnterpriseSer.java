package com.Manage.service;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.randomUtil;
import com.Manage.dao.EnterpriseDao;
import com.Manage.entity.Distributor;
import com.Manage.entity.Enterprise;
import com.Manage.entity.common.SearchDTO;

@Service
public class EnterpriseSer extends BaseService{

	private Logger logger = LogUtil.getInstance(EnterpriseSer.class);

	
	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		try {
			String jsonString = enterpriseDao.getPage(searchDTO);
			return jsonString;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return "";
	}
	
	/**
	 * 根据ID修改
	 * @param enterprise
	 * @return
	 */
	public int updatebyid(Enterprise enterprise){
		try
		{
			return enterpriseDao.updatebyid(enterprise);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取信息
	 * @param enterprise
	 * @return
	 */
	public Enterprise getenterprise(Enterprise enterprise){
		try
		{
			return enterpriseDao.getenterprise(enterprise);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取全部信息
	 * @param enterprise
	 * @return
	 */
	public List<Enterprise> getAll(){
		try
		{
			return enterpriseDao.getAll();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 新增
	 * @return
	 */
	public int insertInfo(Enterprise enterprise){
		try
		{
			return enterpriseDao.insertInfo(enterprise);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getID(String enterpriseID)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("QY");
		sb.append(enterpriseID);
		sb.append("-");
		sb.append(DateUtils.getDate("yyyyMMddHHmm"));
		sb.append(randomUtil.getrandom(999, 100));
		return sb.toString();
	}

}
