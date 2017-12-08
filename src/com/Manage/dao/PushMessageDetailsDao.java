package com.Manage.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.PushMessageDetails;
import com.Manage.entity.PushMessageInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;


@Repository
public class PushMessageDetailsDao extends BaseDao
{

	private static final String NAMESPACE = PushMessageDetailsDao.class.getName() + ".";
	
	public int insertAll(PushMessageDetails p){
		try {
			   return getSqlSession().insert(NAMESPACE+"insertAll",p);
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1004, e);
		   }
	}
	public int delByPushMessageInfoId(String pushMessageInfoId){
		try {
			   return getSqlSession().update(NAMESPACE+"delByPushMessageInfoId",pushMessageInfoId);
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1002, e);
		   }
	}
	
	public List<PushMessageDetails> selectByParams(Map<String,Object> params){
		try {
			 return getSqlSession().selectList(NAMESPACE+"selectByParams",params);
		} catch (Exception e) {
			 e.printStackTrace();
			throw new BmException(Constants.common_errors_1002, e);
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

			List<PushMessageDetails> arr=(List<PushMessageDetails>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(PushMessageDetails a :arr){
				JSONObject obj=JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data",ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
}
