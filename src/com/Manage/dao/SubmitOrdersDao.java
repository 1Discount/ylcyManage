package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.SubmitOrders;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@SuppressWarnings("rawtypes")
@Repository
public class SubmitOrdersDao extends BaseDao {
	
	private static final String NAMESPACE = SubmitOrdersDao.class.getName() + ".";
	
	@SuppressWarnings("unchecked")
	public String getPage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);

			List<SubmitOrders> arr=(List<SubmitOrders>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(SubmitOrders a :arr){
				JSONObject obj = JSONObject.fromObject(a);
				/*String temp=obj.getString("creatorDate");
				if(!StringUtils.isBlank(temp)){
					temp=temp.substring(5,temp.length()-5);
					obj.put("creatorDate", temp);
				}
				
				String temp1=obj.getString("solveTime");
				if(!StringUtils.isBlank(temp1)){
					temp1=temp1.substring(5,temp1.length()-5);
					obj.put("solveTime", temp1);
				}*/
				
				ja.add(obj);
			}
			object.put("data",ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 新增
	 * @param acceptOrder
	 * @return
	 */
	public int insert(SubmitOrders submitOrders){
		try {
			
			return getSqlSession().insert(NAMESPACE+"insertSelective",submitOrders);
			
		} catch (Exception e) {
			
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 修改
	 * @param acceptOrder
	 * @return
	 */
	public int update(SubmitOrders submitOrders){
		try {
			
			return getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective",submitOrders);
			
		} catch (Exception e) {
			
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 根据Id删除
	 * @param submitOrdersID
	 * @return
	 */
	public int deleteById(String submitOrdersID){
		try {
			
			return getSqlSession().update(NAMESPACE+"deleteById",submitOrdersID);
			
		} catch (Exception e) {
			
			throw new BmException(Constants.common_errors_1002, e);
		}
	}
}
