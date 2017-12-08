package com.Manage.dao;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.VIPCardInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
@Repository
public class VIPCardInfoDao  extends BaseDao {
	private static final String NAMESPACE = VIPCardInfoDao.class.getName() + ".";
	/**
	 * @deprecated 新增数据
	 * @author jiang
	 * @date 2015-11-24
	 */
	public int insertInfo(VIPCardInfo info){
		try {
			return getSqlSession().insert(NAMESPACE + "insertInfo", info);
		} catch (Exception e) {
			// TODO: handle exception
			/*if (StringUtils.indexOf(e.getMessage(), "Duplicate entry") >= 0) {
				throw new BmException(Constants.common_errors_1005, e);
			} else {
				throw new BmException(Constants.common_errors_1001, e);
			}*/
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	  /**
	   * 分页查询
	   * @author jiangxuecheng
	   * @param searchDTO
	   * @return
	   */
	  public String getpageString(SearchDTO searchDTO){
			try {
				Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
				List<VIPCardInfo> arr=(List<VIPCardInfo>)page.getRows();
				JSONObject object=new JSONObject();
				object.put("success",true);
				object.put("totalRows",page.getTotal());
				object.put("curPage",page.getCurrentPage());
				JSONArray ja=new JSONArray();
				for(VIPCardInfo a :arr){
					String preferentialType1 = a.getPreferentialType();
					String preferentialType = "";
					if(StringUtils.isNotBlank(preferentialType1)){
						String[] preArr = preferentialType1.split(",");
						if(preArr.length>=3){
							if("1".equals(preArr[0])){
								preferentialType=preferentialType+"	VIP身份";
							}
							if("1".equals(preArr[1])){
								preferentialType=preferentialType+",赠送设备";
							}
							if(!"0".equals(preArr[2])){
								preferentialType=preferentialType+",流量用户"+preArr[2];
							}	
							if(!"0".equals(preArr[3])){
								preferentialType=preferentialType+",话费"+preArr[3];
							}
							if(!"0".equals(preArr[4])){
								preferentialType=preferentialType+",基本"+preArr[4];
							}
							
						}
					}
					if(preferentialType.indexOf(",")==0){
						preferentialType=preferentialType.substring(1,preferentialType.length());
					}
					a.setPreferentialType(preferentialType);
					JSONObject obj=JSONObject.fromObject(a);
					ja.add(obj);
				}
				object.put("data",ja);
				return object.toString();
			}catch (Exception e) {
				e.printStackTrace();
				throw new BmException(Constants.common_errors_1004, e);
			}
		}
	  /***
	   * @deprecated根据cardID获取vip卡信息
	   * @param vipCardInfo
	   * @return
	   * @author jiangxuecheng
	   * @date 2015-11-25
	   */
	  public VIPCardInfo getvipcardinfobycardID(VIPCardInfo vipCardInfo){
		  try {
			return getSqlSession().selectOne(NAMESPACE+"getvipcardinfobycardID",vipCardInfo);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	  }
	  
	  /**
	   * @deprecated编辑修改
	   * @author jiangxuecheng
	   * @date 2015-11-25
	   * @param vipCardInfo
	   * @return
	   */
	  public int editupdate(VIPCardInfo vipCardInfo){
		  try {
			  return getSqlSession().update(NAMESPACE+"editupdate",vipCardInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	  }
	  
	  /***
	   * @deprecated根据cardID删除VIP卡
	   * @author jiangxuecheng
	   * @date 2015-11-25
	   * @param cardID
	   * @return
	   */
	  public int del(String cardID){
		  try {
			  return getSqlSession().update(NAMESPACE+"delBycardID",cardID);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	  }
	  
	  /**
	   * @deprecated  获取excel导出数据
	   * @author jiangxuecheng
	   * @date 2015-11-27
	   * @param vipCardInfo
	   * @return
	   */
	 public List<VIPCardInfo> getexecldata(SearchDTO searchDTO){
		 try {
			return getSqlSession().selectList(NAMESPACE+"getexecldata", searchDTO);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	 } 
	  
	 
}
