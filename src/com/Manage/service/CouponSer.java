package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.Coupon;
import com.Manage.entity.common.SearchDTO;

@Service
public class CouponSer extends BaseService{
	private Logger logger = LogUtil.getInstance(CouponSer.class);

	
	/**
	 * 查询优惠劵列表
	 * @param searchDTO
	 * @return
	 */
	  public String getpageString(SearchDTO searchDTO){
		  logger.debug("查询优惠劵列表，分页server开始");
		  try {
			  String jsonString = couponDao.getpage(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	  }
	  
	  public int saveCoupon(Coupon coupon){
		  return couponDao.saveCoupon(coupon);
	  }
	  
	  public List<Coupon> getcouponVersion(){
		  return couponDao.getcouponVersion();
	  }
	  
	  public int deleteCoupon(String coupon){
		  return couponDao.deleteCoupon(coupon);
	  }
	  
	  public List<Coupon> getCouponData(){
		  return couponDao.getCouponData();
	  }
	  /**
	   * 获取一条 优惠码
	   * @param couponVersion
	   * @return
	   */
	  public Coupon getOneCouponData(String couponVersion){
		  return couponDao.getOneCouponData(couponVersion);
	  }
	  
	  public int updateOneCoupon(String coupon){
		  return couponDao.updateOneCoupon(coupon);
	  }

}
