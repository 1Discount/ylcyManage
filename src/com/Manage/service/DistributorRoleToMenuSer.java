package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.dao.RoleInfoDao;
import com.Manage.entity.RoleToMenu;

/** * @author  wangbo: * @date 创建时间：2015-5-26 上午10:59:53 * @version 1.0 * @parameter  * @since  * @return  */
@Service
public class DistributorRoleToMenuSer extends BaseService {
	
	private Logger logger = LogUtil.getInstance(DistributorRoleToMenuSer.class);
	
	/**
	 * 批量添加角色菜单
	 * @param list
	 * @return
	 */
	public boolean batchinsert(List<RoleToMenu> list){
		logger.debug("批量添加角色菜单service");
		try {
			int temp= distributorRoleToMenuDao.batchinsert(list);
			logger.debug("批量添加角色菜单数据操作结果:"+temp);
			if(temp==list.size()){
				logger.debug("批量添加角色菜单全部成功");
				return true;
			}else if(temp==0){
				logger.debug("批量添加角色菜单全部失败");
				return false;
			}else{
				logger.debug("批量添加角色菜单部分成功");
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
	 * 根据角色ID删除角色菜单映射
	 * @param rid
	 * @return
	 */
	public boolean deletebyid(String rid){
		logger.debug("根据角色ID删除角色菜单映射service");
		try {
			int temp=distributorRoleToMenuDao.deletebyrid(rid);
			logger.debug("根据角色ID删除角色菜单数据操作结果:"+temp);
			if(temp>0){
				logger.debug("根据角色ID删除角色菜单成功");
				return true;
			}else{
				logger.debug("根据角色ID删除角色菜单成功");
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
	 * 根据ID查询角色映射菜单
	 * @param rid
	 * @return
	 */
	public List<RoleToMenu> getbyrid(String rid){
		logger.debug("根据角色ID查询角色菜单映射service");
		try {
			return distributorRoleToMenuDao.getListbyrid(rid);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
