/**
 * RemoteUtil.java
 *
 * @date: 2012-11-30
 * @author: Xiaoyu Guo
 */
package com.Manage.common.latlng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.Remote;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @author Xiaoyu Guo
 *
 */
public class RemoteUtil
{
	// private static final Logger logger = Logger.getLogger(RemoteUtil.class);
	public static final String METHOD_POST = "POST";

	public static final String METHOD_GET = "GET";

	static HttpURLConnection conn = null;



	/**
	 * 发起 HTTP 请求
	 *
	 * @param address
	 *            服务器地址
	 * @param method
	 *            请求类型，取值为 {@value #METHOD_GET} 或 {@value #METHOD_POST}
	 * @param contentType
	 *            Content Type
	 * @param data
	 * @return
	 */
	public static String request(String address, String method, String contentType, String data)
	{

		String responseText = "";
		// if (Strings.isEmpty(method) ){
		// method = METHOD_POST;
		// }
		// if (Strings.isEmpty(contentType) ) {
		// contentType = "application/x-www-form-urlencoded";
		// }

		try
		{
			URL url = new URL(address);

			conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);
			if (data != null)
			{
				conn.setDoInput(true);

			}
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestProperty("User-Agent", "");
			// conn.setRequestProperty("Connection", "Keep-Alive");
			// conn.setRequestProperty("Accept", "application/json");
			// conn.setRequestProperty("Accept-Encoding", "gzip");
			if (data != null)
			{
				conn.setRequestProperty("Content-Length", data.getBytes().length + "");
			}
			conn.setUseCaches(false);
			OutputStreamWriter wr = null;
			if (data != null)
			{
				wr = new OutputStreamWriter(conn.getOutputStream());
				// logger.trace("发送数据：");
				// logger.trace(data);
				wr.write(data);
				wr.flush();
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String currentLine = "";
			StringBuffer sb = new StringBuffer();
			while ((currentLine = rd.readLine()) != null)
			{
				sb.append(currentLine);
				sb.append("\n");
			}
			responseText = sb.toString();

			// logger.trace("接收数据：");
			// logger.warn(responseText);
			if (data != null)
			{
				wr.close();
			}
			rd.close();

		}
		catch (Exception ex)
		{
			if (conn != null)
			{
				// logger.warn("close:" + address);
				conn.disconnect();
			}
			// logger.error(ex);
			ex.printStackTrace();
		}
		// finally
		// {
		// conn.disconnect();
		// }
		return responseText;
	}

	   /**
	    * post请求 传递的参数为json
	    * @param baseUrl
	    * @param json
	    * @return
	    * @throws JSONException
	    */
	    public static String sendHttpRequest(String baseUrl,JSONObject json) throws JSONException {
	    	 StringBuffer sb=null;
	        try {
	            //创建连接
	            URL url = new URL(baseUrl);
	            HttpURLConnection connection = (HttpURLConnection) url
	                    .openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setRequestMethod("POST");
	            connection.setUseCaches(false);
	            connection.setInstanceFollowRedirects(true);
	            connection.setRequestProperty("Content-Type",
	                    "text/html;charset=UTF8");
	            connection.connect();

	            //POST请求
	            OutputStreamWriter bw=new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	            bw.write(json.toString());
	            bw.flush();
	            bw.close();

	            //读取响应
	            InputStream is=connection.getInputStream();
	           // System.out.println(is.available());
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	            String lines;
	            sb = new StringBuffer();
	            while ((lines = reader.readLine()) != null) {
	                lines = new String(lines.getBytes(), "UTF-8");
	                sb.append(lines);
	            }
	            // 断开连接
	            connection.disconnect();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } 
	        return sb.toString();
     }
	    
	    
	    public static String sendHttpRequest(String baseUrl) {
	    	 StringBuffer sb=null;
	        try {
	            //创建连接
	            URL url = new URL(baseUrl);
	            HttpURLConnection connection = (HttpURLConnection) url
	                    .openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setRequestMethod("GET");
	            connection.setUseCaches(false);
	            connection.setInstanceFollowRedirects(true);
	            connection.setRequestProperty("Content-Type","json;charset=UTF8");
	            connection.connect();
//	            connection.setConnectTimeout(10);
//	            connection.setReadTimeout(10);

	            //读取响应
	            InputStream is=connection.getInputStream();
	           // System.out.println(is.available());
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	            String lines;
	            sb = new StringBuffer();
	            while ((lines = reader.readLine()) != null) {	          	     
	                sb.append(lines);
	            }
//	            System.out.println("sb:"+sb.toString());
	            // 断开连接
	            connection.disconnect();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        } 
	        return sb.toString();
    }
}

