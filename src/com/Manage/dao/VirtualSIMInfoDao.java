package com.Manage.dao;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.VirtualSIMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class VirtualSIMInfoDao extends BaseDao {
	private static final String NAMESPACE = VirtualSIMInfoDao.class.getName()
			+ ".";
	/**
	 * 获取所有的虚拟卡
	 * @return
	 */
	public String getVirtualAll(SearchDTO searchDTO,HashMap<String, String> mccNameMap) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<VirtualSIMInfo> arr = (List<VirtualSIMInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (VirtualSIMInfo a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				  String codelistString=a.getCountryList();
				  String cnameString="";
				 if(codelistString.indexOf("|")>=0){
				    	String [] codearr=codelistString.split("\\|");
					    for(int i=0;i<codearr.length;i++){
					    	if(i==codearr.length-1){
					    		cnameString+=mccNameMap.get(codearr[i]);
					    	}else{
					    		cnameString+=mccNameMap.get(codearr[i])+",";
					    	}
					    }
				    }else{
				    	cnameString+=mccNameMap.get(codelistString);
				    }
				 obj.put("countryName",cnameString); 
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
	public VirtualSIMInfo getvirtualbyid(String id){
		try {
			return getSqlSession().selectOne(NAMESPACE+"getvirtualbyid",id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
			
		}
		
	}
	public int save(VirtualSIMInfo virtualSIMInfo){
		try {
			return getSqlSession().insert(NAMESPACE+"updatevirtual", virtualSIMInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1000, e);
		}
	}
	public int delete(String id){
		try {
			return  getSqlSession().update(NAMESPACE+"deletevirtual",id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1002, e);
		}
	}
	public int insert(VirtualSIMInfo virtualSIMInfo){
		try {
			return  getSqlSession().insert(NAMESPACE+"insertInfo",virtualSIMInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e); 
		}
	}
}
