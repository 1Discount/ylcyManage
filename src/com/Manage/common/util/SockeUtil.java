package com.Manage.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SockeUtil {

	public static boolean send(String ip,int port,String content){
		Socket socket=null;
		try {
			socket = new Socket(ip, port);  
			//发送内容
	        PrintWriter out = new PrintWriter(socket.getOutputStream());  
            out.println(content);  
            out.flush(); 
            //获取响应
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg=in.readLine();
            if("ok".equals(msg)){
               return true;
            }  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		 return false;
	}
}
