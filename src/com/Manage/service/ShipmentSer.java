package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.Shipment;
import com.Manage.entity.common.SearchDTO;

@Service
public class ShipmentSer extends BaseService {
	private Logger logger = LogUtil.getInstance(ShipmentSer.class);
	public boolean  insert(Shipment shipment) {
		try {
			if(ShipmentDao.insertinto(shipment)>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 出入库记录查询
	 * @param searchDTO
	 * @return
	 */
	public String getDeviceShipmentLogs(SearchDTO searchDTO){
		  logger.debug("分页server开始");
		  try {
			  String jsonString = ShipmentDao.getDeviceShipmentLogs(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }

	public List<Shipment> exportedShipment(Shipment shipment){
		return ShipmentDao.exportedShipment(shipment);
	}

	/**
	 * 根据出入库ID获取记录
	 * @param shipmentID
	 * @return
	 */
	public Shipment ShipmentObject(String shipmentID){
		return ShipmentDao.ShipmentObject(shipmentID);
	}

	/**
	 * 修改出入库记录
	 * @param shipment
	 * @return
	 */
	public int updateOutboundDevicelogs(Shipment shipment){
		return ShipmentDao.updateOutboundDevicelogs(shipment);
	}

}
