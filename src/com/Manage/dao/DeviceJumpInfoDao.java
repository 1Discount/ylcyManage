package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.DeviceJumpInfo;
import com.Manage.entity.TDMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class DeviceJumpInfoDao extends BaseDao
{

	private static final String NAMESPACE = DeviceJumpInfoDao.class.getName() + ".";

	public int addJump(DeviceJumpInfo jump){
	    try {
		return getSqlSession().insert(NAMESPACE+"addJump",jump);
	    } catch (Exception e) {
		
		throw new BmException(Constants.common_errors_1001, e);
	    }
	}
	public String getpage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			List<DeviceJumpInfo> arr=(List<DeviceJumpInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(DeviceJumpInfo a :arr){
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
