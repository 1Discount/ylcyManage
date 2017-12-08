package com.Manage.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * * @author wangbo: * @date 创建时间：2015-9-2 上午11:53:17 * @version 1.0 * @parameter
 * * @since * @return
 */
public class FileWrite
{

	public static void printlog(String content)
	{
		File fileName = new File("/logs/socket.log");
		try
		{
			if (!fileName.exists())
			{
				fileName.createNewFile();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			String ctxtString = DateUtils.getDateTime() + "----" + content + "/n";
			FileWriter fw = null;
			fw = new FileWriter(fileName, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(ctxtString);
			pw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		}
	}

}
