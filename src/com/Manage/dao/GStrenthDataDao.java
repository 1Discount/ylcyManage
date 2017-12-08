package com.Manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.GStrenthData;

/** * @author  wangbo: * @date 创建时间：2015-11-11 上午9:52:34 * @version 1.0 * @parameter  * @since  * @return  */
@Repository
public class GStrenthDataDao extends BaseDao<GStrenthDataDao>{
	
	private static final String NAMESPACE = GStrenthDataDao.class.getName() + ".";
	
	/**
	 * 查询需要转换的基站
	 * @return
	 */
	public List<GStrenthData> getTop10(){
		try {
			// throw new Exception(); // test only
			return getSqlSession().selectList(NAMESPACE+"getTop10");
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 批量填充经纬度
	 * @param list
	 * @return
	 */
	public int update(List<GStrenthData> list){
		try {
			// throw new Exception(); // test only
			return getSqlSession().update(NAMESPACE+"batchUpdate",list);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
	/**
	 * 查询信号弱的经纬度
	 * @param gd
	 * @return
	 */
	public List<GStrenthData> getbyMCC(GStrenthData gd){
		try {
			// throw new Exception(); // test only
			return getSqlSession().selectList(NAMESPACE+"getbyMCC",gd);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
