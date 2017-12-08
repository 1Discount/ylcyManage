package com.Manage.control;

import net.sf.json.JSONObject;

import org.restlet.data.Form;

/** * @author  wangbo: * @date 创建时间：2015-6-18 下午6:07:53 * @version 1.0 * @parameter  * @since  * @return  */
public interface ControlCommon {
	/**
	 * 输出参数校验
	 * @param jString
	 * @return
	 */
	boolean resultValidate(String jString) throws Exception;

	/**
	 * post请求
	 * @param url
	 * @param entity
	 * @return
	 */
	String postRequestForm(String url,Form form)  throws Exception;
	/**
	 * post请求
	 * @param url
	 * @param o
	 * @return
	 */
	String postRequestForm(String url,Object o)  throws Exception;
	/**
	 * put请求
	 * @param url
	 * @param entity
	 * @return
	 */
	String putRequestForm(String url,Form form)  throws Exception;
	/**
	 * put请求
	 * @param url
	 * @param o
	 * @return
	 */
	String putRequestForm(String url,Object o)  throws Exception;
	/**
	 * get请求 /{param1}/{param2} 形式
	 * @param url
	 * @param par
	 * @return
	 */
	String getRequest(String url,Object[] par)  throws Exception;

	/**
	 * get请求 ?param1=value1&?param2=value2 形式
	 * @param url
	 * @param queryString 不需要带开头的?号. 例: "page=1&size=10"
	 * @return
	 * @throws Exception
	 */
	String getRequest(String url, String queryString) throws Exception;

	/**
	 * delete请求
	 * @param url
	 * @param par
	 * @return
	 */
	String deleteRequest(String url,Object[] par)  throws Exception;


	/**
	 * 获取rest结果的错误码
	 * @param reString
	 * @return
	 * @throws Exception
	 */
	String  getErrorCode(String reString) throws Exception;

	/**
	 * 获取一个结果对应的JSONObject以便获取结果元素
	 * @param reString
	 * @return
	 * @throws Exception
	 */
	JSONObject  getResultJsonObject(String reString) throws Exception;

}
