package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-23 下午2:40:36 * @version 1.0 * @parameter  * @since  * @return  */
@Repository
public class MenuGroupInfoDao extends BaseDao<MenuGroupInfoDao> {
	private static final String NAMESPACE = MenuGroupInfoDao.class.getName() + ".";
	/**
	 * 添加角色组信息.
	 * @param menuGroupInfo
	 * @return
	 */
	public int insertinfo(MenuGroupInfo menuGroupInfo){
		try {
			return getSqlSession().insert(NAMESPACE+"insertinfo",menuGroupInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	/**
	 * 修改角色组信息
	 * @param mid
	 * @return
	 */
	public int editinfo(MenuGroupInfo menuGroupInfo){
		try {
			return getSqlSession().update(NAMESPACE+"editinfo",menuGroupInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	
	/**
	 * 删除角色组信息
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
			List<MenuGroupInfo> arr=(List<MenuGroupInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(MenuGroupInfo a :arr){
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
	 * 查询所有菜单组
	 */
	public List<MenuGroupInfo> selectgroup(){
		try {
			List<?> lm=getSqlSession().selectList(NAMESPACE+"selectgroup");
			Page page=new Page();
			page.setRows(lm);
			return (List<MenuGroupInfo>)page.getRows();
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
		
	}
	
	/**
	 * 菜单表的菜单组ID查询菜单组
	 */
	public List<MenuGroupInfo> getinid(List<MenuInfo> menuInfos){
		try {
			return getSqlSession().selectList(NAMESPACE+"getmenugroupinid",menuInfos);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
}
