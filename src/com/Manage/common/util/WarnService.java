package com.Manage.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.util.LogUtil;


/** * @author  wangbo: * @date 创建时间：2016-4-6 下午4:13:42 * @version 1.0 * @parameter  * @since  * @return  */
public class WarnService {
	private static Logger logger = LogUtil.getInstance(WarnService.class);
	
	/**
	 * 获取CPU使用情况
	 * @return
	 * @throws IOException
	 */
	public static String checkCPU(){
		double cpu=0;
		double javacpu=0;
		Runtime rt=Runtime.getRuntime();
		Process process=null;
		try {
			process = rt.exec("top -b -n 1");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader in =null;
		try {
			in =new BufferedReader(new InputStreamReader(process.getInputStream()));
			String string=null;
			String arr[]=null;
			int n=0;
			while((string=in.readLine())!=null){
				int m=0;
				
				//logger.info("string:"+string);
				//logger.info("arr:"+arr);
				if(n==2){
					logger.info("CPU:"+string);
				}
				if(n>7){
					arr=string.split(" ");
					for(String tmp:arr){
						if(tmp.trim().length()==0){
							continue;
						}
						if(++m==9){
							cpu+=Double.parseDouble(tmp.trim());
							if(string.contains("java")){
								javacpu+=Double.parseDouble(tmp);
							}
						}
					}
				}
				n++;
			}
			System.out.println("没读到东西了");
			return "192.168.0.110"+"|"+cpu+"|"+javacpu;
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.info("获取CPU情况异常:"+e.getMessage());
			rt.exit(1);
			return null;
		}
	}
	
	
	/**
	 * 查看8090端口是否打开
	 * @return
	 * @throws IOException
	 */
	public static String checkPort(){
		Runtime rt=Runtime.getRuntime();
		Process process=null;
		try {
			process = rt.exec("netstat -ntpl");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader in =null;
		try {
			in =new BufferedReader(new InputStreamReader(process.getInputStream()));
			String string=null;
			String meString="";
			String arr[]=null;
			while((string=in.readLine())!=null){
				int m=0;
				/*arr=string.split(" ");
				for(String tmp:arr){
					if(tmp.trim().length()==0){
						continue;
					}
					if(++m==9){
						
					}
				}*/
				meString+=string;
			}
			if(meString.contains(":::8080") && meString.contains(":::8090")){
				return "true|true";
			}else if(meString.contains(":::8080") && !meString.contains(":::8090")){
				return "true|false";
			}else if(!meString.contains(":::8080") && !meString.contains(":::8090")){
				return "false|false";
			}else
				return "false|true";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("获取端口情况异常:"+e.getMessage());
			rt.exit(1);
			return null;
		}
	}
	
	
	/**
	 * 检测socket线程数
	 * @return
	 * @throws IOException
	 */
	public static String checkSocketNum(){
		Runtime rt=Runtime.getRuntime();
		Process process=null;
		try {
			process = rt.exec("netstat -n | grep tcp | grep 8090  | wc -l");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader in =null;
		try {
			in =new BufferedReader(new InputStreamReader(process.getInputStream()));
			String string=null;
			String meString="";
			String arr[]=null;
			while((string=in.readLine())!=null){
				int m=0;
				/*arr=string.split(" ");
				for(String tmp:arr){
					if(tmp.trim().length()==0){
						continue;
					}
					if(++m==9){
						
					}
				}*/
				meString+=string;
			}
			return meString;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("获取端口情况异常:"+e.getMessage());
			rt.exit(1);
			return null;
		}
	}
	
	/**
	 * 检测是否有黏包
	 * @return
	 */
	public static String checkifNB(){
		if(CacheUtils.get("ifNB")!=null){
			return CacheUtils.get("ifNB").toString();
		}else{
			return null;
		}
	}
	
}
