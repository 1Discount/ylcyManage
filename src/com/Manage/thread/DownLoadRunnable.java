package com.Manage.thread;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Socket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.exception.SCException;
import com.Manage.common.util.FileWrite;
import com.Manage.common.util.LogUtil;
import com.Manage.control.FlowDealOrdersControl;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.common.ApplicationContext2;
import com.Manage.entity.common.SocketMessage;
import com.Manage.service.SocketService.BusinessService;
import com.Manage.service.SocketService.SocketServer;

/**
 * * @author wangbo: * @date 创建时间：2015-6-11 上午9:49:02 * @version 1.0 * @parameter
 * * @since * @return
 */
public class DownLoadRunnable implements Runnable
{
	/**
	 * debug日志
	 */
	// private Logger logger = LogUtil.getInstance(DownLoadRunnable.class);



	private HttpServletResponse response;

	private HSSFWorkbook wb;


	public DownLoadRunnable( HttpServletResponse response, HSSFWorkbook wb)
	{
		super();
		this.response = response;
		this.wb = wb;
	}



	@Override
	public synchronized void  run()
	{
		
		try
		{
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");// inline attachment
			response.setHeader("Content-Disposition", "inline;fileName=" + java.net.URLEncoder.encode("aa.xls", "UTF-8"));
			try
			{
				OutputStream os = response.getOutputStream();

				wb.write(os);
				os.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
