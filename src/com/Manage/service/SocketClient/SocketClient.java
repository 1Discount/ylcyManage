package com.Manage.service.SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.Manage.common.util.StringUtils;

/** * @author  wangbo: * @date 创建时间：2015-7-8 下午2:18:16 * @version 1.0 * @parameter  * @since  * @return  */
public final class SocketClient {
	public static Socket getSocket(String ip,String port){
		Socket socket=null;
		if(StringUtils.isBlank(ip)){
			ip=com.Manage.common.constants.Constants.getConfig("socket.ip");
		}
		if(StringUtils.isBlank(port)){
			port=com.Manage.common.constants.Constants.getConfig("socket.port");
		}
		try {
			socket =new Socket(ip,Integer.parseInt(port));
		} catch (Exception e) {
			System.out.println("socket连接建立失败");
			e.printStackTrace();
		}
		return socket;
	}

	/**
	 * 发消息
	 * @param mes
	 * @return
	 * @throws IOException
	 */
	public static boolean send(String mes,String ip,String port) throws IOException{
			PrintWriter out = new PrintWriter(getSocket(ip,port).getOutputStream());
			out.print(mes);
			out.flush();
			System.out.println("发送消息:"+mes);
			return true;
	}

	/**
	 * 收消息
	 * @return
	 * @throws IOException
	 */
	public static String read(String ip,String port) throws IOException{
			BufferedReader in = new BufferedReader(new InputStreamReader(getSocket(ip,port).getInputStream()));
			char[]c=new char[1024];
			int len= in.read(c);
			String resultString=String.valueOf(c,0,len);
			System.out.println("返回消息:"+resultString);
			return resultString;
	}
	

	/**
	 * 短连接发送消息  
	 * @author zwh
	 */
	public static boolean send(Socket socket,String msg){
		try {
			//发送消息
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.print(msg);
			out.flush();
			//读取消息
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	String res=in.readLine();
	    	if("ok".equals(res)){
	    		return true;
	    	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(socket!=null&&!socket.isClosed()){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return  false;
	}

}
