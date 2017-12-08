package com.Manage.service;
import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.VirtualSIMInfo;
import com.Manage.entity.common.SearchDTO;
import com.sun.org.apache.xml.internal.utils.Trie;
@Service
public class VirtualSIMInfoSer extends BaseService{
	private Logger logger = LogUtil.getInstance(VirtualSIMInfoSer.class);
	/**
	 * 获取所有的虚拟卡
	 * @return
	 */
	public String getVirtualAll(SearchDTO searchDTO) {
		List<CountryInfo> countries = countryInfoDao.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries) {
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}
		String jsonString = virtualSIMInfoDao.getVirtualAll(searchDTO, mccNameMap);
		return jsonString;
	}
	
	public VirtualSIMInfo getvirtualbyid(String id){
		try {
			return virtualSIMInfoDao.getvirtualbyid(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int save(VirtualSIMInfo virtualSIMInfo){
		try {
			return virtualSIMInfoDao.save(virtualSIMInfo);
	
		} catch (Exception e) {
			logger.info("新增失败");
			e.printStackTrace();
		}
		return 0;
	}
	public int delete(String id){
		try {
			return virtualSIMInfoDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int insert(VirtualSIMInfo virtualSIMInfo){
		try {
			return virtualSIMInfoDao.insert(virtualSIMInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
