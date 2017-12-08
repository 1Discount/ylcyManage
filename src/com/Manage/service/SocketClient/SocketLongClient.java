package com.Manage.service.SocketClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.StringUtils;

/** * @author  wangbo: * @date 创建时间：2016-4-13 下午3:29:47 * @version 1.0 * @parameter  * @since  * @return  */
public final class SocketLongClient {
public static Socket socket=null;

	public static Socket getSocket(String ip,String port){
		if(StringUtils.isBlank(ip)){
			ip=com.Manage.common.constants.Constants.getConfig("JKsocket.ip");
		}
		if(StringUtils.isBlank(port)){
			port=com.Manage.common.constants.Constants.getConfig("JKsocket.port");
		}
		if(socket==null || !socket.isConnected()){
			System.out.println("新连接");
			try {
				socket =new Socket(ip,Integer.parseInt(port));
			} catch (Exception e) {
				// TODO Auto-generated catch block

				System.out.println("socket连接建立失败");
				e.printStackTrace();
			}
		}
		return socket;
	}


	/**
	 * 发消息
	 * @param mes
	 * @return
	 * @throws IOException
	 */
	public static String send(String mes,String ip,String port) throws IOException{
			PrintWriter out = new PrintWriter(getSocket(ip,port).getOutputStream());
			out.print(mes);
			out.flush();
			System.out.println("发送消息:"+mes);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			char[]c=new char[1024];
			int len= in.read(c);
			String resultString=String.valueOf(c,0,len);
			System.out.println("返回消息:"+resultString);
			return resultString;
	}

	/**
	 * 发消息
	 * @param mes
	 * @return
	 * @throws IOException
	 */
	public static String send(String mes) throws IOException{
		String ip=Constants.getConfig("JKsocket.ip");
		String port=Constants.getConfig("JKsocket.port");
		PrintWriter out = new PrintWriter(getSocket(ip,port).getOutputStream());
		out.print(mes);
		out.flush();
		//System.out.println("发送消息:"+mes);
		String resultString=null;
		DataInputStream dis= new DataInputStream(socket.getInputStream());
		byte[]c=new byte[1024];
		int len= dis.read(c);
		if(len!=-1){
			 resultString=new String(c,"utf-8");
		}else{
			//System.out.println("无消息返回!");
			resultString="";
		}
		
		//System.out.println("返回消息:"+resultString);
		return resultString;
}
}
