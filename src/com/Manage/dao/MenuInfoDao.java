package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.RoleToMenu;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-25 下午4:45:35 * @version 1.0 * @parameter  * @since  * @return  */
@Repository
public class MenuInfoDao extends BaseDao<MenuInfoDao> {
	private static final String NAMESPACE = MenuInfoDao.class.getName() + ".";
	/**
	 * 添加菜单信息.
	 * @param 
	 * @return
	 */
	public int insertinfo(MenuInfo menuInfo){
		try {
			return getSqlSession().insert(NAMESPACE+"insertinfo",menuInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	/**
	 * 修改菜单信息
	 * @param mid
	 * @return
	 */
	public int editinfo(MenuInfo menuInfo){
		try {
			return getSqlSession().update(NAMESPACE+"editinfo",menuInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	/**
	 * 删除菜单信息
	 * @param mid
	 * @return
	 */
	public int delteinfo(String mid){
		try {
			return getSqlSession().update(NAMESPACE+"deleteinfo",mid);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1002, e);
		}
	}
	/**
	 * 分页查询
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			List<MenuInfo> arr=(List<MenuInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(MenuInfo a :arr){
				JSONObject obj=JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data",ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	/**
	 * 根据菜单组ID查询菜单
	 * @param mgid
	 * @return
	 */
	public List<MenuInfo> getmenubygroup(String mgid){
		try {	
		List<?> list=getSqlSession().selectList(NAMESPACE+"selectbygroupid",mgid);
		Page page=new Page();
		page.setRows(list);
		return (List<MenuInfo>)page.getRows();
	} catch (Exception e) {
		throw new BmException(Constants.common_errors_1004, e);
	}
	}
	
	/**
	 * 根据菜单ID列表查询得到用户的所有菜单
	 * @param toMenus
	 * @return
	 */
	public List<MenuInfo> getlistbyid(List<RoleToMenu> toMenus){
		try {	
			return getSqlSession().selectList(NAMESPACE+"batchmenubyid",toMenus);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
}
