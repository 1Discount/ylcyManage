package com.Manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.Coupon;
import com.Manage.entity.CouponRelationship;

@Repository
public class CouponRelationshipDao extends BaseDao{
	private static final String NAMESPACE = CouponRelationshipDao.class.getName() + ".";

	
	public int checkphone(CouponRelationship cou){
		try {
			   return getSqlSession().selectOne(NAMESPACE+"checkphone",cou);
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1004, e);
		   }
	}
	
	public int inertgetCustomer(CouponRelationship cou){
		try {
			   return getSqlSession().insert(NAMESPACE+"inertgetCustomer",cou);
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1001, e);
		   }
	}
	
	/**
	 * 撤销领取记录
	 * @param cou
	 * @return
	 */
	public int delegetCustomer(String cou){
		try {
			   return getSqlSession().delete(NAMESPACE+"delegetCustomer",cou);
		    } catch (Exception e) {
			   e.printStackTrace();
			   throw new BmException(Constants.common_errors_1002, e);
		   }
	}
	
	
	
	
}
