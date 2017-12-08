package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.DepositRecord;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Refund;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class RefundDao  extends BaseDao {

	private static final String NAMESPACE = RefundDao.class.getName() + ".";

	 
	/**
	 * 分页查询返回json
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			
			List<Refund> arr=(List<Refund>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(Refund a :arr){
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
	
	
	public int updateRefundbackEnd(Refund id){
		try {
			return getSqlSession().update(NAMESPACE + "updateRefundbackEnd", id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	public int updateRefundRemarks(Refund info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateRefundRemarks", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	
	
	
}