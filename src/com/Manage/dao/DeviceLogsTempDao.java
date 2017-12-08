package com.Manage.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.DeviceLogsTest;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;


/** * @author  wangbo: * @date 创建时间：2015-9-6 下午2:11:26 * @version 1.0 * @parameter  * @since  * @return  */
@Repository
public class DeviceLogsTempDao extends BaseDao<DeviceLogsTempDao>{
	private static final String NAMESPACE = DeviceLogsTempDao.class.getName() + ".";
	private static final String NAMESPACESIM = SIMInfoDao.class.getName() + ".";
	private final Logger logger = LogUtil.getInstance(DeviceLogsTempDao.class);
	/**
	 * 插入记录
	 * @param info
	 * @return
	 */
	public int saveInfo(DeviceLogs info) {
		try {
			return getSqlSession().insert(NAMESPACE + "insertInfo", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

	public int updateSIM(Map map){
		try {
			return getSqlSession().update(NAMESPACE + "updateSIM", map);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	public int updateFlow(Map map){
		try {
			return getSqlSession().update(NAMESPACE + "updateFlow", map);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	public int updateLogin(DeviceLogs map){
		try {
			return getSqlSession().update(NAMESPACE + "updateLogin", map);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	public int updateOtherLogs(Map map){
		try {
			return getSqlSession().update(NAMESPACE + "updateOtherLogs", map);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 如果没有记录则插入一条记录.
	 * @param deviceLogs
	 * @return
	 */
	public boolean selectlogs(DeviceLogs deviceLogs){
		try {
			DeviceLogs d= getSqlSession().selectOne(NAMESPACE + "selectlogs", deviceLogs);
			if(d==null){
				int temp=saveInfo(deviceLogs);
				if(temp>0){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 查询设备是否有记录
	 * @param deviceLogs
	 * @return
	 */
	public DeviceLogs selecthavelogs(DeviceLogs deviceLogs){
		return getSqlSession().selectOne(NAMESPACE + "selectlogs", deviceLogs);
	}
	
	/**
	 * 查询实时在线设备
	 * @param searchDTO
	 * @return
	 */
	public String getnowString(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"getNowPage","getNowCount",searchDTO);
			//DeviceLogs dd=(DeviceLogs)searchDTO.getObj();
			List<DeviceLogs> arr=(List<DeviceLogs>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			//int count=0;
			
			
			String cusIDs="";
			for(DeviceLogs a :arr){
				String cusID = a.getCustomerID();
				cusIDs=cusIDs+"'"+cusID+"',";
			}
			if(!"".equals(cusIDs)){
				cusIDs = cusIDs.substring(0,cusIDs.length()-1);
			}else{
				cusIDs="'000'";
			}
			CustomerInfo info = new CustomerInfo();
			info.setCustomerID(cusIDs);
		 	List<CustomerInfo> cus =getSqlSession().selectList("com.Manage.dao.CustomerInfoDao.getCusByID",info);
		 	HashMap<String, String> cusMap = new HashMap<String, String>();
			for (CustomerInfo item : cus) {
				cusMap.put(String.valueOf(item.getCustomerID()), item.getCustomerType());
			}
			for(DeviceLogs a :arr){
				String t1=a.getLastTime01().substring(0,(a.getLastTime01().length()-2));
				SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if((new Date()).getTime()-sdf.parse(t1).getTime()>=2*60*1000 || a.getSIMAllot()!=0){
					a.setIfRed(1);
				}else{
					a.setIfRed(0);
				}
				String tag=gettagString(a.getSN());
				a.setTag(tag);
				SIMInfo simInfo=new SIMInfo();
				if(StringUtils.isBlank(a.getSimAlias()) && StringUtils.isNotBlank(a.getIMSI())){
					simInfo=(SIMInfo)getSqlSession().selectOne(NAMESPACESIM + "queryByImsi",a.getIMSI());
					if(simInfo!=null){
						a.setSimAlias(simInfo.getSimAlias());
					}
				}
				JSONObject obj=JSONObject.fromObject(a);
				
				String cusType = cusMap.get(a.getCustomerID());
				if(null==cusType){
					cusType="普通客户";
				}
				obj.put("customerType",cusType);
				ja.add(obj);
			}
			object.put("data",ja);
			System.out.println(object.toString());
			return object.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}


	}

	/**
	 * 查询历史
	 * @param searchDTO
	 * @return
	 */
	public String getHisString(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"getHisPage","getHisCount",searchDTO);

			List<DeviceLogs> arr=(List<DeviceLogs>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			
			String cusIDs="";
			for(DeviceLogs a :arr){
				String cusID = a.getCustomerID();
				cusIDs=cusIDs+"'"+cusID+"',";
			}
			if(!"".equals(cusIDs)){
				cusIDs = cusIDs.substring(0,cusIDs.length()-1);
			}else{
				cusIDs="'000'";
			}
			CustomerInfo info = new CustomerInfo();
			info.setCustomerID(cusIDs);
		 	List<CustomerInfo> cus =getSqlSession().selectList("com.Manage.dao.CustomerInfoDao.getCusByID",info);
		 	HashMap<String, String> cusMap = new HashMap<String, String>();
			for (CustomerInfo item : cus) {
				cusMap.put(String.valueOf(item.getCustomerID()), item.getCustomerType());
			}
			for(DeviceLogs a :arr){
				//判断上下线
				if(StringUtils.isBlank(a.getLastTime02())){
					a.setUpdownline("下线");
				}else{
					String t1=a.getLastTime02().substring(0,(a.getLastTime02().length()-2));
					SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if((new Date()).getTime()-sdf.parse(t1).getTime()>=10*60*1000 || ((new Date()).getTime()-sdf.parse(t1).getTime()>=10*60*1000 && "06".equals(a.getType()))){
						a.setUpdownline("下线");
					}else{
						a.setUpdownline("在线");
					}
				}
				//try {
					String tag=gettagString(a.getSN());
					a.setTag(tag);
					
				//} catch (Exception e) {
				//	a.setTag("");
				//}
				SIMInfo simInfo=new SIMInfo();
				if((StringUtils.isBlank(a.getSimAlias()) || a.getSimAlias().equals("null")) && StringUtils.isNotBlank(a.getIMSI())){
					simInfo=(SIMInfo)getSqlSession().selectOne(NAMESPACESIM + "queryByImsi",a.getIMSI());
					if(simInfo!=null){
						a.setSimAlias(simInfo.getSimAlias());
					}
				}
				JSONObject obj=JSONObject.fromObject(a);
				String cusType = cusMap.get(a.getCustomerID());
				if(null==cusType){
					cusType="普通客户";
				}
				obj.put("customerType",cusType);
				ja.add(obj);
			}
			object.put("data",ja);
			return object.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}


	}

	/**
	 * <!-- 按日期最近一个月的日设备接入数 仅包含有上传流量 -->
	 *
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInCountByDay(DeviceLogs info) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getDeviceInCountByDay", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	public List<DeviceLogs> getDeviceInCountByDay1(DeviceLogs info) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getDeviceInCountByDay1", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	public DeviceLogs getDeviceInCountByDay2(DeviceLogs info){
	    try {
		return getSqlSession().selectOne(NAMESPACE+"getDeviceInCountByDay2",info);
	    } catch (Exception e) {
		e.printStackTrace();
		throw new BmException(Constants.common_errors_1004, e);
	    }
	}

	/**
	 * <!-- 按日期最近一个月的日设备接入数 包含有上传流量或没有上传流量的全部 -->
	 *
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInCountByDayAll(DeviceLogs info) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getDeviceInCountByDayAll", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public List<DeviceLogs> getDeviceInCountByDayAll1(DeviceLogs info) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getDeviceInCountByDayAll1", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * <!-- 按国家最近一个月的设备接入数 -->
	 *
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInCountByMCC(DeviceLogs info) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getDeviceInCountByMCC", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public List<DeviceLogs> getDeviceInCountByMCC1(DeviceLogs info) {
		try {
			return getSqlSession().selectList("com.Manage.dao.DeviceLogsDao.getDeviceInCountByMCC1", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 读取设备标记
	 * @param sn
	 * @return
	 */
	public String gettagString(String sn){//319
		/*String locationString = Constants.getConfig("SNtaglocation");
		File fileName = new File(locationString);
		try {
			if (!fileName.exists()) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			String read = null;
			while ((read = bufferedReader.readLine()) != null) {
				result = result + read;
			}
			
			logger.info("输出字符串:"+result);
			
			result =new String(result.getBytes("utf-8"),"gbk");
			
			logger.info("utf-8转gbk后:"+result);
			result =new String(result.getBytes("iso-8859-1"),"gbk");
			
			logger.info("iso转gbk后:"+result);

			if(fileReader!=null){
				fileReader.close();
			}
			if(bufferedReader!=null){
				bufferedReader.close();
			}
		JSONObject json=null;
		if(StringUtils.isBlank(result)){
			return "";
		}else{
			//开始解析读取的文件
			json=JSONObject.fromObject(result);
			if(json.containsKey(sn)){
				return json.getString(sn);
			}else{
				return "";
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		Object object=CacheUtils.get("fileCache","SNTAG");
		JSONObject json=null;
		if(object==null || "".equals(object.toString())){
			return "";
		}else{
			json=JSONObject.fromObject(object.toString());
			if(json.containsKey(sn)){
				return json.getString(sn);
			}else{
				return "";
			}
		}
	}

	/**
	 * 设备按时间段查看每天接入情况和统计流量
	 *
	 * @author tangming@easy2go.cn
	 * @param info
	 * @return
	 */
	public List<DeviceLogs> getDeviceInBySnAndDate(DeviceLogs info) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getDeviceInBySnAndDate", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public List<DeviceLogs> getflowBySnAndDate(DeviceLogs info) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getflowBySnAndDate", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public DeviceLogsTest getflowBySnAndDateAPI(DeviceLogs info) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getflowBySnAndDateAPI", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public List<DeviceLogs> getdevicelogTempbysn(DeviceLogs info) {
		try
		{
			return getSqlSession().selectList("com.Manage.dao.DeviceLogsTempDao.getdevicelogTempbysn",info);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 查询抢卡
	 * @return
	 */
	public List<DeviceLogs> searchGrabCard()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String times = format.format(date);
		String tname = "DeviceLogs_" + times;
		try
		{
			return getSqlSession().selectList(NAMESPACE+"searchGrabCard",tname);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 查询实时在线设备(远程服务操作界面)
	 * @param searchDTO
	 * @return
	 */
	public String getnowString1(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"getNowPage1","getNowCount1",searchDTO);
			DeviceLogs dd=(DeviceLogs)searchDTO.getObj();
			List<DeviceLogs> arr=(List<DeviceLogs>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			int count=0;
			
			
			String cusIDs="";
			for(DeviceLogs a :arr){
				String cusID = a.getCustomerID();
				cusIDs=cusIDs+"'"+cusID+"',";
			}
			if(!"".equals(cusIDs)){
				cusIDs = cusIDs.substring(0,cusIDs.length()-1);
			}else{
				cusIDs="'000'";
			}
			CustomerInfo info = new CustomerInfo();
			info.setCustomerID(cusIDs);
		 	List<CustomerInfo> cus =getSqlSession().selectList("com.Manage.dao.CustomerInfoDao.getCusByID",info);
		 	HashMap<String, String> cusMap = new HashMap<String, String>();
			for (CustomerInfo item : cus) {
				cusMap.put(String.valueOf(item.getCustomerID()), item.getCustomerType());
			}
			for(DeviceLogs a :arr){
				String t1=a.getLastTime01().substring(0,(a.getLastTime01().length()-2));
				SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if((new Date()).getTime()-sdf.parse(t1).getTime()>=2*60*1000 || a.getSIMAllot()!=0){
					a.setIfRed(1);
				}else{
					a.setIfRed(0);
				}
				String tag=gettagString(a.getSN());
				a.setTag(tag);
				SIMInfo simInfo=new SIMInfo();
				if(StringUtils.isBlank(a.getSimAlias()) && StringUtils.isNotBlank(a.getIMSI())){
					simInfo=(SIMInfo)getSqlSession().selectOne(NAMESPACESIM + "queryByImsi",a.getIMSI());
					if(simInfo!=null){
						a.setSimAlias(simInfo.getSimAlias());
					}
				}
				JSONObject obj=JSONObject.fromObject(a);
				
				String cusType = cusMap.get(a.getCustomerID());
				if(null==cusType){
					cusType="普通客户";
				}
				obj.put("customerType",cusType);
				ja.add(obj);
			}
			object.put("data",ja);
			System.out.println(object.toString());
			return object.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}


	}
	
	public String gitJieruInfo(SearchDTO searchDTO,HashMap<String, String> mccNameMap){
		try
		{
			Page page = queryPage(NAMESPACE, "gitJieruInfoPage", "gitJieruInfoCount", searchDTO);
			@SuppressWarnings("unchecked")
			List<DeviceLogs> arr = (List<DeviceLogs>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			int inCount=0;
			for (DeviceLogs  a : arr)
			{
				inCount+=Integer.parseInt( a.getInCount());
				a.setMcc(mccNameMap.get(a.getMcc()));
				JSONObject obj = JSONObject.fromObject(a);
				ja.add(obj);
			}
			
			JSONObject obj = new JSONObject();
			obj.put("logsDate", "<a>合计：</a>");
			obj.put("inCount", "<a>"+inCount+"</a>");
			obj.put("mcc", "<a>-----------</a>");
			ja.add(obj);
			
			object.put("data", ja);
			object.put("totalRows", page.getTotal()+1);
			return object.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
}
