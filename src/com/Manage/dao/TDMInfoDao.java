package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.TDMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class TDMInfoDao extends BaseDao
{

	private static final String NAMESPACE = TDMInfoDao.class.getName() + ".";


	public int addTDM(TDMInfo tdm){
	    try {
		return getSqlSession().insert(NAMESPACE+"addTDM",tdm);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1001, e);
	    }
	}
	
	public List<TDMInfo> getList(TDMInfo tdm){
	    try {
		return getSqlSession().selectList(NAMESPACE+"getList", tdm);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1004, e);
	    }
	}
	
	public List<TDMInfo> getListNoItSelf(TDMInfo tdm){
	    try {
		return getSqlSession().selectList(NAMESPACE+"getListNoItSelf", tdm);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1004, e);
	    }
	}
	
	/**
	 * 检测tdm是否重复
	 * @param tdm
	 * @return
	 */
	public List<TDMInfo> checkTDM(TDMInfo tdm){
	    try {
		return getSqlSession().selectList(NAMESPACE+"checkTDM", tdm);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1004, e);
	    }
	}
	public String getpage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			List<TDMInfo> arr=(List<TDMInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(TDMInfo a :arr){
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
	
	public int updateTDMByID(TDMInfo tdm){
	    try {
		return getSqlSession().update(NAMESPACE+"updateTDMByID",tdm);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1001, e);
	    }
	}
	public int deleteOrBack(TDMInfo tdm){
	    try {
		return getSqlSession().update(NAMESPACE+"deleteOrBack",tdm);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1001, e);
	    }
	}
	
	public TDMInfo getTDMInfoBySN(String sn){
	    try {
		return getSqlSession().selectOne(NAMESPACE+"getBySN",sn);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1004, e);
	    }
	    
	}
	public TDMInfo getTDMInfo(String tdmUrl){
	    try {
		return getSqlSession().selectOne(NAMESPACE+"getTDMInfo",tdmUrl);
	    } catch (Exception e) {
		throw new BmException(Constants.common_errors_1004, e);
	    }
	}
	
}
