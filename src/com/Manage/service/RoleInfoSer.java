package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.RoleInfo;
import com.Manage.entity.RoleToMenu;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-26 上午10:55:47 * @version 1.0 * @parameter  * @since  * @return  */
@Service
public class RoleInfoSer extends BaseService {

	private Logger logger = LogUtil.getInstance(RoleInfoSer.class);
	
	/**
	 * 添加角色
	 * @param
	 * @return
	 */
	public boolean insertinfo(RoleInfo roleInfo){
		logger.debug("添加角色service");
		try {
			int temp=roleInfoDao.insertinfo(roleInfo);
			if(temp>0){
				logger.debug("添加角色成功");
				return true;
			}else{
				logger.debug("添加角色失败");
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
	 * 修改角色
	 * @param mid
	 * @return
	 */
	public boolean editinfo(RoleInfo roleInfo){
		logger.debug("修改角色service");
		try {
			int temp=roleInfoDao.editinfo(roleInfo);
			if(temp>0){
				logger.debug("修改角色成功");
				return true;
			}else{
				logger.debug("修改角色失败");
				return  false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除角色 
	 * @param mid
	 * @return
	 */
	public boolean deleteinfo(String mid){
		logger.debug("删除角色service");
		try {
			int temp=roleInfoDao.delteinfo(mid);
			if(temp>0){
				logger.debug("删除角色成功");
				return true;
			}else{
				logger.debug("删除角色失败");
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
			  String jsonString=roleInfoDao.getpageString(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<RoleInfo> getalList(){
		logger.debug("分页server开始");
		  try {
			  return roleInfoDao.getallrole();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
