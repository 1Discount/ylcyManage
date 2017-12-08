package com.Manage.c3p0;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

@Component
public class SqlSession {
	@Resource
	DefaultSqlSessionFactory sqlSessionFactory1;
	
    private static Map<String, Object> cache=new HashMap<>();

	public synchronized List<Map<String,String>> getBeika(String namespace,Map<Object, Object> map) {
		org.apache.ibatis.session.SqlSession session=null;
		List<Map<String,String>> list=null;
		try {
			session=sqlSessionFactory1.openSession();
			list=session.selectList(namespace, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}
	
	public synchronized List<Map> getVailable(String namespace,Map<Object, Object> map) {
		org.apache.ibatis.session.SqlSession session=null;
		List<Map> list=null;
		try {
			session=sqlSessionFactory1.openSession();
			list=session.selectList(namespace, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}
	
	public synchronized	List<Map<String,String>> getsimTotalandorderTotal(String namespace,Map<String, String> map) {
		org.apache.ibatis.session.SqlSession session=null;
		List<Map<String,String>> list=null;
		try {
			session=sqlSessionFactory1.openSession();
			list=session.selectList(namespace, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}

	public  synchronized List<Map<String, String>> queryOrderCountByMonth(String namespace,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		org.apache.ibatis.session.SqlSession session=null;
		List<Map<String,String>> list=null;
		list=(List<Map<String, String>>) cache.get("queryOrderCountByMonth");
		if(list==null){
			try {
				session=sqlSessionFactory1.openSession();
				list=session.selectList(namespace, map);
				cache.put("queryOrderCountByMonth", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(session!=null){
					session.close();
				}
			}
		}else{
			cache.remove("queryOrderCountByMonth");
		}
		return list;
	}

	public synchronized List<Map<String, Object>> queryOrderCountByMonth1(String namespace,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		org.apache.ibatis.session.SqlSession session=null;
		List<Map<String,Object>> list=null;
		list=(List<Map<String, Object>>) cache.get("queryOrderCountByMonth1");
		if(list==null){
			try {
				session=sqlSessionFactory1.openSession();
				list=session.selectList(namespace, map);
				cache.put("queryOrderCountByMonth1", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(session!=null){
					session.close();
				}
			}
		}else{
			cache.remove("queryOrderCountByMonth1");
		}
		return list;
	}
	
	
}
