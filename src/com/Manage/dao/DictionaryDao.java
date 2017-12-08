package com.Manage.dao;
 
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
 

@Repository
public class DictionaryDao extends BaseDao{
	
	private static final String NAMESPACE = DictionaryDao.class.getName() + ".";

 
	/**
	 * 分页查询返回json
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			
			List<Dictionary> arr=(List<Dictionary>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(Dictionary a :arr){
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
	
	public Dictionary getDicDelete(String uid)
	{
		try {
			return getSqlSession().selectOne(NAMESPACE+"getDeleteDic", uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1002,e);	
		}
	}
 
	/**
	 * 插入
	 * @param dictionary
	 * @return
	 */
	public int getSaveDic(Dictionary dictionary)
	{
		try {
			return getSqlSession().insert(NAMESPACE+"insertDic",dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001,e);
		}
	}
	/**
	 * 获取需修改的数据
	 * @param uid
	 * @return
	 */
	public Dictionary getDicUdata(String dictID)
	{
		try {
			return getSqlSession().selectOne(NAMESPACE+"getOneDicdata", dictID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003,e);	
		}
	}
	/**
	 * 修改
	 * @param dictionary
	 * @return
	 */
	public int getUpdateDic(Dictionary dictionary){
		try {
			return getSqlSession().update(NAMESPACE+"updateDicEnd",dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003,e);
		}
	}
	
	/**
	 * 查看所有类别
	 * @return
	 */
	public List<Dictionary> getAlldescription(){
		try {
			return getSqlSession().selectList(NAMESPACE+"getAllDescription");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);
		}
	}
	/**
	 * 通过类别名称获取属性值
	 * @return
	 */
	public List<Dictionary> getAlllaelListdao(String description){
		try {
			return getSqlSession().selectList(NAMESPACE+"getAlllabelList",description);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);		
		}
	}

	public List<Dictionary> getalertType(String description){
		try {
			return getSqlSession().selectList(NAMESPACE+"getalertType",description);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);		
		}
	}
	public Dictionary getDictByValue(Dictionary dictionary){
		try {
			return getSqlSession().selectOne(NAMESPACE+"getDictByValue",dictionary);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004,e);		
		}
	}
	
}
