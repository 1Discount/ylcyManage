package com.Manage.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-23 下午2:43:49 * @version 1.0 * @parameter  * @since  * @return  */
@Service
public class DistributorMenuGroupInfoSer extends BaseService {
	private Logger logger = LogUtil.getInstance(DistributorMenuGroupInfoSer.class);
	/**
	 * 添加角色组
	 * @param menuGroupInfo
	 * @return
	 */
	public boolean insertinfo(MenuGroupInfo menuGroupInfo){
		logger.debug("添加角色组service");
		try {
			int temp=distributorMenuGroupInfoDao.insertinfo(menuGroupInfo);
			if(temp>0){
				logger.debug("添加角色组成功");
				return true;
			}else{
				logger.debug("添加角色组失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 修改角色组 
	 * @param mid
	 * @return
	 */
	public boolean editinfo(MenuGroupInfo menuGroupInfo){
		logger.debug("修改角色组service");
		try {
			int temp=distributorMenuGroupInfoDao.editinfo(menuGroupInfo);
			if(temp>0){
				logger.debug("修改角色组成功");
				return true;
			}else{
				logger.debug("修改角色组失败");
				return  false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 修改角色组 
	 * @param mid
	 * @return
	 */
	public boolean deleteinfo(String mid){
		logger.debug("删除角色组service");
		try {
			int temp=distributorMenuGroupInfoDao.delteinfo(mid);
			if(temp>0){
				logger.debug("删除角色组成功");
				return true;
			}else{
				logger.debug("删除角色组失败");
				return  false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 分页查询
	 * @param searchDTO
	 * @return
	 */
	 public String getpageString(SearchDTO searchDTO){
		  logger.debug("分页server开始");
		  try {
			  String jsonString=distributorMenuGroupInfoDao.getpageString(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }
}
