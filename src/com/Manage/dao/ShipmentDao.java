package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Shipment;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class ShipmentDao extends BaseDao{

	private static final String NAMESPACE = ShipmentDao.class.getName() + ".";
	public int insertinto(Shipment  shipment){
		try {
			return getSqlSession().insert(NAMESPACE+"insert",shipment);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1001, e);
		}
	}




	public String getDeviceShipmentLogs(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"getDeviceShipmentLogsPage","getDeviceShipmentLogsCount",searchDTO);

			List<Shipment> arr=(List<Shipment>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(Shipment a :arr){
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

	public List<Shipment> exportedShipment(Shipment shipment){
		try {
			return getSqlSession().selectList(NAMESPACE+"exportedShipment",shipment);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

	public Shipment ShipmentObject(String ShipmentID){
		try {
			return getSqlSession().selectOne(NAMESPACE+"ShipmentObject",ShipmentID);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

	public int updateOutboundDevicelogs(Shipment shipment){
		try {
			return getSqlSession().update(NAMESPACE+"updateOutboundDevicelogs",shipment);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

}
