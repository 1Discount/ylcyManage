package com.Manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.RoleToMenu;




/** * @author  wangbo: * @date 创建时间：2015-5-26 上午10:57:35 * @version 1.0 * @parameter  * @since  * @return  */
@Repository
public class RoleToMenuDao extends BaseDao<RoleToMenuDao> {
	private static final String NAMESPACE = RoleToMenuDao.class.getName() + ".";
	/**
	 * 批量添加角色菜单映射信息.
	 * @param 
	 * @return
	 */
	public int batchinsert(List<RoleToMenu> list){
		try {
			return getSqlSession().insert(NAMESPACE+"batchinsertinfo",list);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 *根据角色ID删除角色菜单映射.
	 */
	public int deletebyrid(String rid){
		try {
			return getSqlSession().update(NAMESPACE+"deleteinfo",rid);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
		
	}
	
	/**
	 * 根据角色ID查询
	 * @param rid
	 * @return
	 */
	public List<RoleToMenu> getListbyrid(String rid){
		try {
			//com.Manage.dao.RoleToMenuDao.
			return getSqlSession().selectList(NAMESPACE+"getmenubyrid",rid);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
