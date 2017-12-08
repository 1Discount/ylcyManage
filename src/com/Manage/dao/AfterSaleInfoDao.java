package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.AfterSaleInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@SuppressWarnings("rawtypes")
@Repository
public class AfterSaleInfoDao extends BaseDao{
	
	private static final String NAMESPACE = AfterSaleInfoDao.class.getName() + ".";
	
	/**
	 * 新增售后服务记录
	 * @param afterSaleInfo
	 * @return
	 */
	public int insert(AfterSaleInfo afterSaleInfo){
		try{
			return getSqlSession().insert(NAMESPACE + "insertSelective", afterSaleInfo);
		}catch (Exception e) {
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 根据ID查询
	 * @param asiID
	 * @return
	 */
	public AfterSaleInfo getById(String asiID){
		try{
			return getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", asiID);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 根据流量订单ID查询
	 * @param flowDealID
	 * @return
	 */
	public AfterSaleInfo getByFlowDealID(String flowDealID){
		try{
			return getSqlSession().selectOne(NAMESPACE + "selectByFlowDealID", flowDealID);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getPage(SearchDTO searchDTO){
		try{
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			
			List<AfterSaleInfo> list = (List<AfterSaleInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for (AfterSaleInfo asi : list) {
				JSONObject obj = JSONObject.fromObject(asi);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public int deleteById(String asiID){
		try{
			return getSqlSession().update(NAMESPACE + "deleteById", asiID);
		}catch (Exception e) {
			throw new BmException(Constants.common_errors_1002, e);
		}
	}
	
	/**
	 * 根据流量订单ID查询退款售后记录
	 * @param flowDealID
	 * @return
	 */
	public AfterSaleInfo getRefundInfo(String flowDealID){
		try{
			return getSqlSession().selectOne(NAMESPACE + "getByFlowDealID", flowDealID);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public List<AfterSaleInfo> getafterExprotExcel(AfterSaleInfo afterSaleInfo){
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getafterExprotExcel", afterSaleInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
