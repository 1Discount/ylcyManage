/*
 * 文件名    ：SocketListener.java
 * 创建时间：2014年5月9日 下午2:31:07
 * 创建人    : FuLei
 */
package com.Manage.common.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.Manage.common.util.LogUtil;
import com.Manage.thread.SocketThread;

/**
 * 服务端Socket监听
 * 
 * @author Administrator
 * 
 */

public class ServerSocketListener implements ServletContextListener
{
    
	private Logger logger = LogUtil.getInstance(ServerSocketListener.class);
    private SocketThread socketThread;

    
    @Override
    public void contextDestroyed(ServletContextEvent arg0)
    {
        logger.info("Begin destroyed context...");
        
        if (socketThread != null && socketThread.isInterrupted())
        {
            socketThread.closeServerSocket();
            socketThread.interrupt();
        }
        
        logger.info("End destroyed context...");
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0)
    {
        logger.info("Begin init server socket...");
        
        ServletContext servletContext = arg0.getServletContext();
        
        if (socketThread == null)
        {
            socketThread = new SocketThread(servletContext);
            socketThread.start();
        }
        
        logger.info("End init server socket...");
    }
    
}
