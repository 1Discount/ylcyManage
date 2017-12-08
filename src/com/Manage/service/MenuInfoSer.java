package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.dao.MenuInfoDao;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-25 下午4:46:27 * @version 1.0 * @parameter  * @since  * @return  */
@Service
public class MenuInfoSer extends BaseService {
	private Logger logger = LogUtil.getInstance(MenuInfoSer.class);
	/**
	 * 添加菜单
	 * @param
	 * @return
	 */
	public boolean insertinfo(MenuInfo menuInfo){
		logger.debug("添加菜单service");
		try {
			int temp=menuInfoDao.insertinfo(menuInfo);
			if(temp>0){
				logger.debug("添加菜单成功");
				return true;
			}else{
				logger.debug("添加菜单失败");
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
	 * 修改菜单
	 * @param mid
	 * @return
	 */
	public boolean editinfo(MenuInfo menuInfo){
		logger.debug("修改菜单service");
		try {
			int temp=menuInfoDao.editinfo(menuInfo);
			if(temp>0){
				logger.debug("修改菜单成功");
				return true;
			}else{
				logger.debug("修改菜单失败");
				return  false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 删除菜单 
	 * @param mid
	 * @return
	 */
	public boolean deleteinfo(String mid){
		logger.debug("删除菜单service");
		try {
			int temp=menuInfoDao.delteinfo(mid);
			if(temp>0){
				logger.debug("删除菜单成功");
				return true;
			}else{
				logger.debug("删除菜单失败");
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
			  String jsonString=menuInfoDao.getpageString(searchDTO);
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
	  * 查询所有菜单组
	  * @return
	  */
	 public List<MenuGroupInfo> selectgroup(){
		 logger.debug("查询所有菜单组service");
		 try {
			return menuGroupInfoDao.selectgroup();
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		 return null;
	 }
	 
	 /**
	  * 查询所有菜单组和菜单
	  * @return
	  */
	 public List<MenuGroupInfo> getGroupAndMenu(){
		 List<MenuGroupInfo> mgList=menuGroupInfoDao.selectgroup();
		 for(MenuGroupInfo mg:mgList){
			 List<MenuInfo> mlist=menuInfoDao.getmenubygroup(mg.getMenuGroupID());
			 mg.setMenuInfos(mlist);
		 }
		 return mgList;
	 }
	 
}
