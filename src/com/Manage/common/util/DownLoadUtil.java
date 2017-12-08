package com.Manage.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DownLoadUtil {
	/**
	 *  用这个工具类下载源文件要入在/ylcyManage/WebRoot/upload/下
	 * @param fileName
	 * @param filelocaltion
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	 public static String download(String fileName,String filelocaltion, HttpServletRequest request,
	            HttpServletResponse response) throws UnsupportedEncodingException {
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("multipart/form-data");//inline  attachment//.encode(fileName, "UTF-8")
	        response.setHeader("Content-Disposition", "inline;fileName="+ java.net.URLEncoder.encode(fileName, "UTF-8"));//这里可以支持中文 
	        try {           
	        	 String path=request.getSession().getServletContext().getRealPath("upload");
	        	 File file = new File(path + File.separator + filelocaltion);
	            InputStream inputStream = new FileInputStream(file);
	            OutputStream os = response.getOutputStream();
	            byte[] b = new byte[2048];
	            int length;
	            while ((length = inputStream.read(b)) > 0) {
	                os.write(b, 0, length);
	            }
	            os.close();
	            inputStream.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 /**
	  *  用这个工具类下载源文件要入在传入的路径
	  * @param request
	  * @param response
	  * @param fileName
	  * @param path
	  * @return
	  * @throws UnsupportedEncodingException
	  */
	 public static String download(HttpServletRequest request, HttpServletResponse response,String fileName,String path) throws UnsupportedEncodingException {
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("multipart/form-data");//inline  attachment
	        response.setHeader("Content-Disposition", "inline;fileName="+ java.net.URLEncoder.encode(fileName, "UTF-8"));//这里可以支持中文 
	        try { 
	        	File file = new File(path + File.separator + fileName);
	            InputStream inputStream = new FileInputStream(file);
		        OutputStream os = response.getOutputStream();
	            byte[] b = new byte[2048];
	            int length;
	            while ((length = inputStream.read(b)) > 0) {
	                os.write(b, 0, length);
	            }
	            os.flush();
	            os.close();
	            inputStream.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 /**
	  * execl导出时，使用这个下载
	  * @param fileName
	  * @param wb
	  * @param request
	  * @param response
	  * @return
	  * @throws UnsupportedEncodingException
	  * @author jiang
	  * @date 2015-11-23
	  * @
	  */
	  public static String execlExpoprtDownload(String fileName,HSSFWorkbook wb, HttpServletRequest request,
	            HttpServletResponse response) throws UnsupportedEncodingException {
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("multipart/form-data");//inline  attachment
	        response.setHeader("Content-Disposition", "inline;fileName="+ java.net.URLEncoder.encode(fileName, "UTF-8") );
	        try {
	            OutputStream os = response.getOutputStream();
	            wb.write(os);
	            os.flush();
	            os.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
