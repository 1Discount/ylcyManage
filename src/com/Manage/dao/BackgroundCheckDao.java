package com.Manage.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;

@SuppressWarnings("rawtypes")
@Repository
public class BackgroundCheckDao extends BaseDao
{
	
	private static final String NAMESPACE = BackgroundCheckDao.class.getName() + ".";
	
	/**
	 * 检测数据库连接是否正常以及延时
	 * @return
	 */
	public String connectionDelayed()
	{
		
		JSONObject result = new JSONObject();
		
		try
		{
			long t1 = (new Date()).getTime();
			
			Map<String, String> map = getSqlSession().selectOne(NAMESPACE+"connectionDelayed");
			
			long t2 = (new Date()).getTime();
			
			if(map!=null)
			{
				result.put("success", "连接正常");
				result.put("data", (t2-t1)+"");
			}
			return result.toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result.put("result", "连接失败");
			return result.toString();
		}
		
	}
	
	/**
	 * 查询当前数据库连接数
	 * @return
	 */
	public String connectionNum()
	{
		JSONObject result = new JSONObject();
		
		try
		{
			int count = getSqlSession().selectOne(NAMESPACE+"connectionNum");
			
			result.put("data", count);
			
			return result.toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 数据库定时任务是否打开正常运行
	 * @return
	 */
	public String timingTaskOpen()
	{
		JSONObject result = new JSONObject();
		
		try
		{
			Map<String,String> map = getSqlSession().selectOne(NAMESPACE+"timingTaskOpen");
			
			if(map!=null&&map.get("Value").equals("ON"))
			{
				result.put("success", "是");
			}
			else
			{
				result.put("success", "否");
			}
			
			return result.toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 查询是否有锁表超时现象
	 * @return
	 */
	public String lockTable()
	{
		JSONObject result = new JSONObject();
		
		try
		{
			List<Map<String,Object>> listMap = getSqlSession().selectList(NAMESPACE+"lockTable");
			
			if(listMap!=null && listMap.size()>0)
			{
				String str = "";
				for (Map<String, Object> map : listMap) 
				{
					if(str.equals(""))
					{
						str = map.get("trx_mysql_thread_id").toString();
					}
					else
					{
						str+=","+map.get("trx_mysql_thread_id").toString();
					}
					
				}
				result.put("success", "是");
				result.put("data", str);
			}
			else
			{
				result.put("success", "否");
			}
			
			return result.toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 数据同步（备份）是否正常运行
	 * @return
	 */
	public String dataSynchronization()
	{
		JSONObject result = new JSONObject();
		
		try
		{
			Map<String, String> map = getSqlSession().selectOne(NAMESPACE+"dataSynchronization");
			
			Thread.sleep(5000);
			
			Map<String, String> map2 = getSqlSession().selectOne(NAMESPACE+"dataSynchronization");

			if(map==null || map2==null)
			{
				result.put("success", "否");
			}
			//zwh 屏蔽(20161027  数据库已经转移到rds不需要自己做数据备份 并且还报错可以屏蔽)
//			else if(map.get("Position").equals(map2.get("Position")))
//			{
//				result.put("success", "否");
//			}
			else
			{
				result.put("success", "是");
			}
			return result.toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 表结构是否完整，设备日志表是否正常
	 * @return
	 */
	public String tableIntegrity()
	{
		JSONObject result = new JSONObject();
		
		try
		{
			int count = getSqlSession().selectOne(NAMESPACE+"tableIntegrity");
			
			if(count>0)
			{
				result.put("success", "是");
			}
			else
			{
				result.put("success", "否");
			}
			
			return result.toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 检测是否有异常设备日志记录影响逻辑
	 * @return
	 */
	public String testDeviceLogs()
	{
		JSONObject result = new JSONObject();
		
		try
		{
			int count = getSqlSession().selectOne(NAMESPACE+"testDeviceLogs");
			
			if(count>0)
			{
				result.put("success", "异常");
				result.put("data", count);
			}
			else
			{
				result.put("success", "正常");
			}
			
			return result.toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
