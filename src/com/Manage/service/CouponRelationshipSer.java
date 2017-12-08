package com.Manage.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.Coupon;
import com.Manage.entity.CouponRelationship;

@Service
public class CouponRelationshipSer extends BaseService{
	private Logger logger = LogUtil.getInstance(CouponRelationshipSer.class);


	  public int checkphone(CouponRelationship cr){
		  return couponRelationshipDao.checkphone(cr);
	  }
	  
	  public int inertgetCustomer(CouponRelationship cr){
		  return couponRelationshipDao.inertgetCustomer(cr);
	  }
	  /**
	   * 撤销领取记录
	   * @param cr
	   * @return
	   */
	  public int delegetCustomer(String cr){
		  return couponRelationshipDao.delegetCustomer(cr);
	  }
	  
	  
}
