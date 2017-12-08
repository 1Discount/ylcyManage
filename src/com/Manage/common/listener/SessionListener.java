package com.Manage.common.listener;


import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener extends HttpServlet implements HttpSessionListener
{
	public long t1=0;
	public void sessionCreated(HttpSessionEvent arg0)
	{
		// TODO Auto-generated method stub
		t1= new Date().getTime();
		//System.out.println("sessionCreated");
	}

	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		long t2=new Date().getTime();
		long t3= (t2-t1)/(1000*60);
		System.out.println("sessionDestroyed,本次session持续时间为:"+t3+"分钟!");
	}
	
}
