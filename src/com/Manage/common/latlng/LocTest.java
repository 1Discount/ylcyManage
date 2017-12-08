package com.Manage.common.latlng;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;

import com.Manage.common.util.StringUtils;
import com.Manage.entity.JizhanInfo;
import com.Manage.entity.common.ApplicationContext2;
import com.Manage.service.JizhanInfoSer;



/** * @author  wangbo: * @date ����ʱ�䣺2015-10-22 ����5:37:12 * @version 1.0 * @parameter  * @since  * @return  */
public class LocTest {

	/**460.1.42303.24172859
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("-----------------我是分割线1------------------------");
		jizhantojw("310.410.11031.62521","50");
		System.out.println("-----------------我是分割线2------------------------");
		jizhantojw("222.99.50302.8866739","50");
		System.out.println("-----------------我是分割线3------------------------");
		jizhantojw("262.3.40093.137204151","50");
		System.out.println("-----------------我是分割线4------------------------");
		jizhantojw("310.410.56964.4446166","50");
		System.out.println("-----------------我是底线------------------------");
		String[] arr=new String[]{"310.410.56964.4446166"};
		System.out.println("-----------------我是分割线5------------------------");
		jizhantojwArray(arr);
	}*/

	
	public static JSONObject jizhantojw(String jizhan,String gs){
		String[]arr=jizhan.split("\\.");
		int mcc=Integer.parseInt(arr[0]);
		int mnc=Integer.parseInt(arr[1]);
		int lac=Integer.parseInt(arr[2]);
		int cid=Integer.parseInt(arr[3]);
		int igs=Integer.parseInt(gs);
		return JSONObject.fromObject(getJwByJizhan(cid,mnc,mcc,lac,0));
	}
	public static JSONObject jizhantojwArray(String[]jizhan){
		if(jizhan==null ||jizhan.length==0){
			return null;
		}

		String result="";
		for(int i=0;i<jizhan.length;i++){
			String[]arr=jizhan[i].split("\\.");
			int mcc=Integer.parseInt(arr[0]);
			int mnc=Integer.parseInt(arr[1]);
			int lac=Integer.parseInt(arr[2]);
			int cid=Integer.parseInt(arr[3]);
			int igs=50;
			if(arr.length>4){
				igs=Integer.parseInt(arr[4])+110;
			}
			result+=JSONObject.fromObject(getJwByJizhan(cid,mnc,mcc,lac,0))+(i==jizhan.length-1 ? "" : ",");
		}
		if(result.indexOf("OK")>-1){
			Map<String,Object> causeMap=new HashMap<String,Object>();
			causeMap.put("cause", "OK");
			result+=","+JSONObject.fromObject(causeMap);
		}
		System.out.println(result);
		return JSONObject.fromObject(result);
	}
	
	//如果这种方法从opencellid.org获取失败，则采用从minigps.net手动输入验证码获取。
	public static Map<String,Object> getJwByJizhan(int cid,int mnc,int mcc,int lac,int limit){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String result="";
		Map<String,Object> jizhanMap=new HashMap<String, Object>();
		String url="",lat="",lon="";
		try {
			jizhanMap.put("mcc", mcc);
			jizhanMap.put("mnc",mnc);
			jizhanMap.put("lac",lac);
			jizhanMap.put("cellId",cid);
			jizhanMap.put("limit",0);
		//先从数据库中获取基站信息,如果为空,则模拟浏览器请求,从其他网站获取
		JizhanInfo jizhan=ApplicationContext2.getBean(JizhanInfoSer.class).getJizhanInfoByParams(jizhanMap);
		if(jizhan!=null){
			System.out.println("-----------------本地数据库中已经存在，从本地获取数据-------------------");
			resultMap.put("cause","OK");
			resultMap.put("lat",jizhan.getLat());
			resultMap.put("lon",jizhan.getLon());
			resultMap.put("address",jizhan.getAddress());
			return resultMap;
		}
		jizhanMap.remove("jizhan");
		
		System.out.println("--------------从opencellid.org获取基站信息----------start-------");
		//新的基站转坐标，url示例  转义符 （ %22  = "）2016-11-16
		//http://opencellid.org/gsmCell/jsonpservice/query/measurementsForTower?callback=jQuery19002744496658427431_1479199827765&{%22cellId%22:%22157822445%22,%22mnc%22:%227%22,%22mcc%22:%22214%22,%22lac%22:%22864%22,%22limit%22:0}
		JSONObject jsonObj = JSONObject.fromObject(jizhanMap);		//System.out.println("请求参数："+jsonObj);
		url="http://opencellid.org/gsmCell/jsonpservice/query/measurementsForTower?callback=jQuery19005038570955926678_1479353059774&"+jsonObj;		//System.out.println("请求地址："+url);
		result = RemoteUtil.request(url, "GET", "application/json;charset=utf-8", null);//处理返回结果，使其变成一个正确的json字符串
		result=result.substring(result.indexOf("(")+1,result.lastIndexOf(")"));	//System.out.println("处理后的返回结果："+result);
		JSONObject resultJsonObj = JSONObject.fromObject(result);
		JSONObject calculatedCellJsonObj = JSONObject.fromObject(resultJsonObj.get("calculatedCell"));
		JSONObject coordinateJsonObj = JSONObject.fromObject(calculatedCellJsonObj.get("coordinate"));
		//获取当前获取坐标的结果
		Object cause=resultJsonObj.get("codeError");
		if(StringUtils.isBlank(cause) ||  !cause.toString().equals("0")){
			resultMap.put("cause","NO");
		}else{
			resultMap.put("cause","OK");
			lat=coordinateJsonObj.getString("latitude");
			lon=coordinateJsonObj.getString("longitude");
			resultMap.put("lat",lat);
			resultMap.put("lon",lon);
			//根据坐标获取地址
			resultMap.put("address",getAddressByJW(lat,lon));
			//将查询结果插入数据库保存
			try{
				ApplicationContext2.getBean(JizhanInfoSer.class).insert(mcc+"."+mnc+"."+lac+"."+cid,lat,lon,(resultMap.get("address")==null ? "" : resultMap.get("address").toString()),result);
			}catch(Exception e){
				System.out.println("基站信息添加失败");
				e.printStackTrace();
			}
		}
		} catch (Exception e) {
			System.out.println("--------------基站信息转换失败--------------");
			System.out.println("请求参数为："+JSONObject.fromObject(jizhanMap));
			System.out.println("请求地址为："+url);
			System.out.println("返回结果："+result);
			resultMap.put("cause","NO");
			System.out.println("--------------从opencellid.org获取基站信息----------end----状态：NO---");
			return resultMap;
		}
		System.out.println(resultMap);
		System.out.println("--------------从opencellid.org获取基站信息----------end----状态：OK---");
		return resultMap;
	}
	
public static String getAddressByJW(Object lat,Object lon){
	String address="";
	try {
		//http://ditu.google.cn/maps/api/geocode/json?latlng=22.5,114.5&sensor=false&language=zh-cn
		String url="http://ditu.google.cn/maps/api/geocode/json?latlng="+lat+","+lon+"&sensor=false&language=zh-cn";
		
		String result = RemoteUtil.request(url, "GET", "application/json;charset=utf-8", null);
		JSONObject resultJSONObject=JSONObject.fromObject(result);
		
		if(resultJSONObject.get("status")!=null
		   && StringUtils.isNotBlank(resultJSONObject.get("status").toString())
		   && resultJSONObject.get("status").toString().equals("OK")){
			JSONArray resultsJSONArray = JSONArray.fromObject(resultJSONObject.get("results"));
			JSONObject resultsJSONObject0 = JSONObject.fromObject(resultsJSONArray.get(0));
			Object formattedAddressObject = resultsJSONObject0.get("formatted_address");
			if(formattedAddressObject!=null  && StringUtils.isNotBlank(formattedAddressObject.toString())){
				address=formattedAddressObject.toString();
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}

	//从minigps.net手动输入验证码获取。
	public static int jizhantojwFromMinigps(String jizhan,String checkCode,HttpSession session){
		int status=0;
		System.out.println("-------------从minigps.net手动输入验证码获取--------------start------");
		try {
			String location[]=jizhan.split("\\.");
			HttpClient httpClient=(HttpClient)session.getAttribute("httpClient");
			
			String getlocationurl = "http://www.minigps.net/map/google/location";
	        HttpPost httpPost = new HttpPost(getlocationurl);
	        StringEntity requestEntity = new StringEntity("{\"version\":\"1.1.0\",\"host\":\"maps.google.com\","
	        		+ "\"cell_towers\":[{\"cell_id\":\""+location[3]+"\",\"location_area_code\":\""+location[2]+"\","
	        		+ "\"mobile_country_code\":\""+location[0]+"\",\"mobile_network_code\":\""+location[1]+"\",\"age\":0,\"signal_strength\":-65}],\"verifycode\":\""+checkCode+"\"}","utf-8");
	        httpPost.setEntity(requestEntity);
	        requestEntity.setContentEncoding("UTF-8");
	        httpPost.setHeader("Content-type", "application/json");
	        httpPost.setHeader("Referer", "http://www.minigps.net/cellsearch.html");
	        String result = httpClient.execute(httpPost,new BasicResponseHandler());
	        System.out.println(result);
	        //解析返回结果
	        JSONObject resultJSONObject=JSONObject.fromObject(result);
	        JSONObject locationJSONObject=resultJSONObject.getJSONObject("location");
	        JSONObject addressJSONObject=locationJSONObject.getJSONObject("address");
	        String city=addressJSONObject.getString("city");
	        if(city.indexOf("verify code error")>-1){
	        	status=2;
	        	System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态："+status+"---");
	        	return status;
	        }
	        if(city.indexOf("不存在")>-1 || city.indexOf("读取正确")>-1){
	        	status=3;
	        	System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态："+status+"---");
	        	return status;
	        }
	        String latitude=locationJSONObject.getString("latitude");
	        String longitude=locationJSONObject.getString("longitude");
	        String street=addressJSONObject.getString("street");
	        if(latitude.equals("0.0") || longitude.equals("0.0")){
	        	status=0;
	        	System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态："+status+"---");
	        	return status;
	        }else{
	        	if(street.equals("")){
	        		street=getAddressByJW(latitude,longitude);
	        	}
	        	//插入数据库
		        status=ApplicationContext2.getBean(JizhanInfoSer.class).insert(jizhan,latitude,longitude,street,result)==true ? 1:0;
	        }
		} catch (Exception e) {
			status=0;
			e.printStackTrace();
		}
		System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态："+status+"---");
		return status;
	}
	
	//从minigps.net手动输入验证码获取。
		public static Map<String,Object> jizhantojwFromMinigpsNew(String jizhan,String checkCode,HttpSession session){
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("status", 0);
			System.out.println("-------------从minigps.net手动输入验证码获取--------------start------");
			try {
				String location[]=jizhan.split("\\.");
				HttpClient httpClient=(HttpClient)session.getAttribute("httpClient");
				
				String getlocationurl = "http://www.minigps.net/map/google/location";
		        HttpPost httpPost = new HttpPost(getlocationurl);
		        StringEntity requestEntity = new StringEntity("{\"version\":\"1.1.0\",\"host\":\"maps.google.com\","
		        		+ "\"cell_towers\":[{\"cell_id\":\""+location[3]+"\",\"location_area_code\":\""+location[2]+"\","
		        		+ "\"mobile_country_code\":\""+location[0]+"\",\"mobile_network_code\":\""+location[1]+"\",\"age\":0,\"signal_strength\":-65}],\"verifycode\":\""+checkCode+"\"}","utf-8");
		        httpPost.setEntity(requestEntity);
		        requestEntity.setContentEncoding("UTF-8");
		        httpPost.setHeader("Content-type", "application/json");
		        httpPost.setHeader("Referer", "http://www.minigps.net/cellsearch.html");
		        String result = httpClient.execute(httpPost,new BasicResponseHandler());
		        System.out.println(result);
		        //解析返回结果
		        JSONObject resultJSONObject=JSONObject.fromObject(result);
		        JSONObject locationJSONObject=resultJSONObject.getJSONObject("location");
		        JSONObject addressJSONObject=locationJSONObject.getJSONObject("address");
		        String city=addressJSONObject.getString("city");
		        if(city.indexOf("verify code error")>-1){
		        	resultMap.put("status", 2);
		        	System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态：2---");
		        	return resultMap;
		        }
		        if(city.indexOf("不存在")>-1 || city.indexOf("读取正确")>-1){
		        	resultMap.put("status", 3);
		        	System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态：3---");
		        	return resultMap;
		        }
		        String latitude=locationJSONObject.getString("latitude");
		        String longitude=locationJSONObject.getString("longitude");
		        String street=addressJSONObject.getString("street");
		        if(latitude.equals("0.0") || longitude.equals("0.0")){
		        	resultMap.put("status", 0);
		        	System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态：0---");
		        	return resultMap;
		        }else{
		        	if(street.equals("")){
		        		street=getAddressByJW(latitude,longitude);
		        	}
		        	//插入数据库
		        	resultMap.put("status", ApplicationContext2.getBean(JizhanInfoSer.class).insert(jizhan,latitude,longitude,street,result)==true ? 1:0);
		        	resultMap.put("lat", latitude);
		        	resultMap.put("lon", longitude);
		        	resultMap.put("address", street);
		        }
			} catch (Exception e) {
				resultMap.put("status", 0);
				e.printStackTrace();
			}
			System.out.println("-------------从minigps.net手动输入验证码获取--------------end---状态："+resultMap.get("status")+"---");
			return resultMap;
		}
	
	private static String t1(int a){
	      String str = "";
	      int sun = a/16;
	      int yuShu = a%16;
	      str = ""+shuZhiToZhiMu(yuShu);
	      while(sun > 0 ){
	         yuShu = sun % 16;
	         sun = sun / 16;
	         str = shuZhiToZhiMu(yuShu) + str;
	      }
	      return str;
	   }

	private static String shuZhiToZhiMu(int a){
	      switch(a){
	         case 10 :
	            return "A";
	         case 11 :
	           return "B";
	         case 12 :
	           return "C";
	         case 13 :
	           return "D";
	         case 14 :
	           return "E";
	         case 15 :
	           return "F";
	      }
	      return ""+a;
	   }

	public static double Distance(double long1, double lat1, double long2,
	        double lat2) {
	    double a, b, R;
	    R = 6378137; // 地球半径
	    lat1 = lat1 * Math.PI / 180.0;
	    lat2 = lat2 * Math.PI / 180.0;
	    a = lat1 - lat2;
	    b = (long1 - long2) * Math.PI / 180.0;
	    double d;
	    double sa2, sb2;
	    sa2 = Math.sin(a / 2.0);
	    sb2 = Math.sin(b / 2.0);
	    d = 2
	            * R
	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
	                    * Math.cos(lat2) * sb2 * sb2));
	    return d;
	}

	public static double Distance(String jz1,String jz2){
		JSONObject object1=jizhantojwArray(new String[]{jz1});
		JSONObject object2=jizhantojwArray(new String[]{jz2});
		if("OK".equals(object1.getString("cause")) && "OK".equals(object2.getString("cause"))){
			double long1=Double.parseDouble(object1.getString("lon"));
			double lat1=Double.parseDouble(object1.getString("lat"));
			double long2=Double.parseDouble(object2.getString("lon"));
			double lat3=Double.parseDouble(object2.getString("lat"));
			return  Distance(long1,lat1,long2,lat3);
		}else{
			return -1;
		}
	}

}
