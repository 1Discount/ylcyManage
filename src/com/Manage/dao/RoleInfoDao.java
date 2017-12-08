package com.Manage.dao;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.RoleInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-26 上午10:55:18 * @version 1.0 * @parameter  * @since  * @return  */
@Repository
public class RoleInfoDao extends BaseDao<RoleInfoDao> {
	private static final String NAMESPACE = RoleInfoDao.class.getName() + ".";
	/**
	 * 添加角色信息.
	 * @param 
	 * @return
	 */
	public int insertinfo(RoleInfo roleinfo){
		try {
			return getSqlSession().insert(NAMESPACE+"insertinfo",roleinfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 修改角色信息
	 * @param mid
	 * @return
	 */
	public int editinfo(RoleInfo roleinfo){
		try {
			return getSqlSession().update(NAMESPACE+"editinfo",roleinfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	
	/**
	 * 删除角色信息
	 * @param mid
	 * @return
	 */
	public int delteinfo(String rid){
		try {
			return getSqlSession().update(NAMESPACE+"deleteinfo",rid);
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
			List<RoleInfo> arr=(List<RoleInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(RoleInfo a :arr){
				Date date=a.getCreatorDate();
				String dString=DateUtils.formatDate(date,"yyyy-MM-dd hh:mm:ss");
				JSONObject obj=JSONObject.fromObject(a);
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);
				ja.add(obj);
			}
			object.put("data",ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<RoleInfo> getallrole(){
		try {
			return getSqlSession().selectList(NAMESPACE+"getallrole");
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1002, e);
		}
	}
	
}
