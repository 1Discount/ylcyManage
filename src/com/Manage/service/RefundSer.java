package com.Manage.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.DepositRecord;
import com.Manage.entity.Refund;
import com.Manage.entity.common.SearchDTO;

@Service
public class RefundSer extends BaseService{
	
	
	private Logger logger = LogUtil.getInstance(RefundSer.class);

	/**
	 * 分页，排序，条件查询.
	 * @param searchDTO
	 * @return
	 */
	  public String getpageString(SearchDTO searchDTO){
		  logger.debug("分页server开始");
		  try {
			  String jsonString=refundDao.getpage(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }
	  
		public int updateRefundbackEnd(Refund id){
			return refundDao.updateRefundbackEnd(id);
		}
	  
		public int updateRefundRemarks(Refund id){
			return refundDao.updateRefundRemarks(id);
		}
}
