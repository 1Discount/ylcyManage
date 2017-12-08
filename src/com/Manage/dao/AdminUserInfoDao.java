package com.Manage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class AdminUserInfoDao extends BaseDao<AdminUserInfoDao> {
	private static final String NAMESPACE = AdminUserInfoDao.class.getName() + ".";
	/**
	 * 用户登录验证查询（根据用户名查询密码）
	 */
	public AdminUserInfo login(AdminUserInfo auinfo) 
	{
		try {
			return (AdminUserInfo)getSqlSession().selectOne(NAMESPACE + "queryLogin",auinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	/**
	 * 添加用户信息
	 * @param auinfo
	 * @return
	 */
	public int insertinfo(AdminUserInfo auinfo){
		try {
			return getSqlSession().insert(NAMESPACE +"register",auinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 分页查询返回json
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			
			List<AdminUserInfo> arr=(List<AdminUserInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(AdminUserInfo a :arr){
				JSONObject obj=JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data",ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 修改密码
	 * @param adminUserInfo
	 * @return
	 */
	public int restpassword(AdminUserInfo adminUserInfo){
		try {
			return  getSqlSession().update(NAMESPACE+"restpassword", adminUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
			// TODO: handle exception
		}
	}
	
	/**
	 * 删除用户
	 * @param userID
	 * @return
	 */
	public int deletebyid(String userID){
		try {
			return  getSqlSession().update(NAMESPACE+"deletebyid",userID);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1002, e);
			// TODO: handle exception
		}
	}
	
	/**
	 * 用户信息编辑
	 * @param adminUserInfo
	 * @return
	 */
	public int useredit(AdminUserInfo adminUserInfo){
		try {
			return  getSqlSession().update(NAMESPACE+"useredit",adminUserInfo);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
			// TODO: handle exception
		}
		
	}
	
	/**
	 * 用户分配角色
	 * @param map
	 * @return
	 */
	public int usertorole(AdminUserInfo adminUserInfo){
		try {
			return  getSqlSession().update(NAMESPACE+"userrole",adminUserInfo);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
			// TODO: handle exception
		}
	}
	
	/**
	 *查询邮箱是否存在 
	 * @param email
	 * @return
	 */
	public AdminUserInfo getemail(String email){
		try {
			return  getSqlSession().selectOne(NAMESPACE+"getemail",email);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
		
	}
	
	/**
	 * 获取指派人列表
	 * @return
	 */
	public List<AdminUserInfo> getDesignee(){
		
		try {
			return  getSqlSession().selectList(NAMESPACE+"getDesignee");
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
}
