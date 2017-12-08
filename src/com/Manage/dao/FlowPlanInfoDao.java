package com.Manage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;


import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.FlowPlanInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class FlowPlanInfoDao extends BaseDao {
	
	private static final String NAMESPACE = FlowPlanInfoDao.class.getName() + ".";

	/**
	 * 分页查询有效套餐 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<FlowPlanInfo> arr = (List<FlowPlanInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			/**
			 * 注意: 因为前端使用 grid 自动装载数据, 需要严格对照字段名, 因为国家字符串不适宜在列表
			 * 中直接显示, 所以适当修改输出. 可行的做法系, 字段的值修改为要显示的值, 然后 json 中添
			 * 加新的字段保存原值, 以备其他接口或前端其他地方需要使用.
			 * ---> 既然确定只需要字符串且目前仅供 grid javascript , 所以暂只需改值即可
			 */
			CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper();
			for (FlowPlanInfo a : arr) {
				if(a.getCountryList().indexOf("-")==-1){
					wrapper.setCountryListString(a.getCountryList());
					a.setCountryList(wrapper.getCountryNameStrings());
				}else{
					String countryList="";
					for(int i=0;i<a.getCountryList().split("-").length;i++){
						wrapper.setCountryListString(a.getCountryList().split("-")[i]);
						countryList=countryList+wrapper.getCountryNameStrings()+"-";
						
					}
					a.setCountryList(countryList.substring(0,countryList.length()-1));
				}
				
				a.setFlowSum(a.getFlowSum() / 1024); // 前端使用MB显示
				a.setFlowAlert(a.getFlowAlert() / 1024); // 前端使用MB显示
				JSONObject obj = JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 分页查询已删除套餐 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeleted(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<FlowPlanInfo> arr = (List<FlowPlanInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			
			/**
			 * 注意: 因为前端使用 grid 自动装载数据, 需要严格对照字段名, 因为国家字符串不适宜在列表
			 * 中直接显示, 所以适当修改输出. 可行的做法系, 字段的值修改为要显示的值, 然后 json 中添
			 * 加新的字段保存原值, 以备其他接口或前端其他地方需要使用.
			 * ---> 既然确定只需要字符串且目前仅供 grid javascript , 所以暂只需改值即可
			 */
			CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper();
			for (FlowPlanInfo a : arr) {
				String cList= a.getCountryList();
				
				if(cList.split("-").length<1){
					wrapper.setCountryListString(a.getCountryList());
					a.setCountryList(wrapper.getCountryNameStrings());
				}else{
					String countryString="";
					for(int i=0;i<cList.split("-").length;i++ ){
						wrapper.setCountryListString(cList.split("-")[i]);
						countryString+=countryString+wrapper.getCountryNameStrings()+"-";
					}
					countryString=countryString.substring(0,countryString.length()-1);
					a.setCountryList(countryString);
				}
				a.setFlowSum(a.getFlowSum() / 1024); // 前端使用MB显示
				a.setFlowAlert(a.getFlowAlert() / 1024); // 前端使用MB显示
				
				JSONObject obj = JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * !目前未使用, 详情见controller端"/datapage/{MCC}", 保留待参考! 
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageByMCC(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageByMCC", "getcountByMCC", searchDTO);

			List<FlowPlanInfo> arr = (List<FlowPlanInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());			
			object.put("data", renderToJsonArray(arr));
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
	
	private JSONArray renderToJsonArray(List<FlowPlanInfo> arr) {
		JSONArray ja = new JSONArray();
		
		/**
		 * 注意: 因为前端使用 grid 自动装载数据, 需要严格对照字段名, 因为国家字符串不适宜在列表
		 * 中直接显示, 所以适当修改输出. 可行的做法系, 字段的值修改为要显示的值, 然后 json 中添
		 * 加新的字段保存原值, 以备其他接口或前端其他地方需要使用.
		 * ---> 既然确定只需要字符串且目前仅供 grid javascript , 所以暂只需改值即可
		 */
		CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper();
		for (FlowPlanInfo a : arr) {
			wrapper.setCountryListString(a.getCountryList());
			a.setCountryList(wrapper.getCountryNameStrings());
			
			a.setFlowSum(a.getFlowSum() / 1024); // 前端使用MB显示
			a.setFlowAlert(a.getFlowAlert() / 1024); // 前端使用MB显示
			
			JSONObject obj = JSONObject.fromObject(a);
			ja.add(obj);
		}
		
		return ja;
	}
	
	/**
	 * 套餐详情列表
	 */
	public List<FlowPlanInfo> getPlans(String arg) 
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryFlowPlan", arg);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 套餐详情查询（根据套餐id）
	 */
	public FlowPlanInfo getPlanById(String id) 
	{
		try {
			return (FlowPlanInfo)getSqlSession().selectOne(NAMESPACE + "queryFlowPlanById", id);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public int insertInfo(FlowPlanInfo info) {
		try {
			return getSqlSession().update(NAMESPACE + "insertInfo", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public int updateInfo(FlowPlanInfo info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfo", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public int updateInfoSysStatus(FlowPlanInfo info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 查询所有套餐,用于绑定下拉框
	 * @return
	 */
	public List<FlowPlanInfo> getall(){
		try {
			return getSqlSession().selectList(NAMESPACE + "getall");
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
		
	}
	
}
