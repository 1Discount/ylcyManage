package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.Coupon;
import com.Manage.entity.Dictionary;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class CouponDao extends BaseDao{
	private static final String NAMESPACE = CouponDao.class.getName() + ".";

	 
		/**
		 * 优惠劵列表 分页查询返回json
		 * @param searchDTO
		 * @return
		 */
	public String getpage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);
			
			List<Coupon> arr=(List<Coupon>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(Coupon a :arr){
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
	
	
	
	public int saveCoupon(Coupon coupon){
		try {
			return getSqlSession().insert(NAMESPACE + "saveAll", coupon);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	public List<Coupon> getcouponVersion(){
		
		try {
		   return getSqlSession().selectList(NAMESPACE+"getcouponVersion");
	    } catch (Exception e) {
		   e.printStackTrace();
		   throw new BmException(Constants.common_errors_1001, e);
	   }
	}
	
	public int deleteCoupon(String coupon){
		try {
			return getSqlSession().update(NAMESPACE+"deleteCoupon",coupon);
		}catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1001, e);
		   }
	}
	
	public List<Coupon> getCouponData(){
		try {
			   return getSqlSession().selectList(NAMESPACE+"getOnecoupon");
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1001, e);
		   }
	}
	
	  /**
	   * 获取一条 优惠码
	   * @param couponVersion
	   * @return
	   */
	public Coupon getOneCouponData(String couponVersion){
		try {
			   return getSqlSession().selectOne(NAMESPACE+"getOneCouponData",couponVersion);
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1001, e);
		   }
	}
	
	public int updateOneCoupon(String couponVersion){
		try {
			   return getSqlSession().update(NAMESPACE+"updateOneCoupon",couponVersion);
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1001, e);
		   }
	}
	
	
		
}
