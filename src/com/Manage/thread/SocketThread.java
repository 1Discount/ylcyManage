package com.Manage.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.Manage.common.util.FileWrite;
import com.Manage.common.util.LogUtil;



/**
 * socket线程，容器加载时启动
 * @author wangbo
 *
 */
public class SocketThread extends Thread
{
    /**
     * debug日志
     */
	private Logger logger = LogUtil.getInstance(SocketThread.class);

    private ServletContext context;

    /**
     * 服务端socket对象
     */
    private ServerSocket serverSocket = null;

    // 从web.xml中context-param节点
    private static final String SOCKETPORT = "socketPort";

    private Socket socket;

    // 线程池
    private ExecutorService executorService;

    public SocketThread(ServletContext servletContext)
    {
        context = servletContext;

        // 初始化线程池
        executorService = Executors.newCachedThreadPool();

        // 从web.xml中context-param节点获取socket端口
        String port = context.getInitParameter(SOCKETPORT);

        if (null == serverSocket)
        {
        	System.out.println(SOCKETPORT+"||"+Integer.parseInt(port));
            try
            {
                serverSocket = new ServerSocket(Integer.parseInt(port));
            }
            catch (NumberFormatException e)
            {
                logger.error("socket端口错误。", e);

                e.printStackTrace();
            }
            catch (IOException e)
            {
                logger.error("启动服务端socket失败。", e);

                e.printStackTrace();
            }
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run()
    {
        while (!Thread.interrupted() && serverSocket!=null)
        {
            try
            {
                // 等待接收
            	socket = serverSocket.accept();
            	FileWrite.printlog("连接建立:"+socket.getInetAddress());
                System.out.println("------等待接收------");
                logger.info("---------Waiting to receive---------");
                // 线程池处理
                executorService.execute(new SocketRunnable(socket));

            }
            catch (IOException e)
            {
                logger.error("Socket处理异常。", e);
                e.printStackTrace();
            }
        }

        executorService.shutdownNow();
    }

    /**
     * 关闭服务端socket
     */
    public void closeServerSocket()
    {
        try
        {
            if (serverSocket != null && !serverSocket.isClosed())
            {
                serverSocket.close();
            }
        }
        catch (Exception ex)
        {
            logger.error("socket关闭异常", ex);

            ex.printStackTrace();
        }
    }

}
