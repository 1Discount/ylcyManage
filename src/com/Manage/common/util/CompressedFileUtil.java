package com.Manage.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.apache.log4j.Logger;
import org.apache.tools.zip.*;
import org.apache.tools.zip.ZipOutputStream;

;

/**
 * 将文件夹下面的文件 打包成zip压缩文件
 * 
 * @author admin
 * 
 */
public class CompressedFileUtil
{

	public Logger l = LogUtil.getInstance(CompressedFileUtil.class);



	/**
	 * 
	 * @param srcPathName
	 *            待压缩的文件或文件夹路径
	 * @param zipFileName
	 *            压缩扣的文件路径 c:/temp/aaa.zip
	 */
	public static void zip(String srcPathName, String zipFileName)
	{
		File file = new File(srcPathName);
		File zipFile = new File(zipFileName);
		if (!file.exists()) throw new RuntimeException(srcPathName + "不存在！");
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			Properties pro = System.getProperties();
			String osName = pro.getProperty("os.name");
			if ("Linux".equals(osName) || "linux".equals(osName))
			{
				out.setEncoding("GBK");// 设置文件名编码方式
			}
			else
			{
				out.setEncoding(System.getProperty("sun.jnu.encoding"));// 设置文件名编码方式
			}

			String basedir = "";
			compress(file, out, basedir);
			out.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}



	/**
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	public static void compress(File file, ZipOutputStream out, String basedir)
	{
		/* 判断是目录还是文件 */
		if (file.isDirectory())
		{
			// System.out.println("压缩：" + basedir + file.getName());
			compressDirectory(file, out, basedir);
		}
		else
		{
			// System.out.println("压缩：" + basedir + file.getName());
			compressFile(file, out, basedir);
		}
	}



	/**
	 * 压缩一个目录
	 * 
	 * @param dir
	 * @param out
	 * @param basedir
	 */
	private static void compressDirectory(File dir, ZipOutputStream out, String basedir)
	{
		if (!dir.exists()) return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}



	/**
	 * 压缩一个文件
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private static void compressFile(File file, ZipOutputStream out, String basedir)
	{
		if (!file.exists())
		{
			return;
		}
		try
		{
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			Properties pro = System.getProperties();
			String osName = pro.getProperty("os.name");
			if ("Linux".equals(osName) || "linux".equals(osName))
			{
				entry.setUnixMode(644);
			}
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[8192];
			while ((count = bis.read(data, 0, 8192)) != -1)
			{
				out.write(data, 0, count);
			}
			bis.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}



	/**
	 * 删除文件和目录
	 * 
	 * @param workspaceRootPath
	 */
	public static void clearFiles(String workspaceRootPath)
	{
		File file = new File(workspaceRootPath);
		if (file.exists())
		{
			deleteFile(file);
		}
	}



	public static void deleteFile(File file)
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				deleteFile(files[i]);
			}
		}
		file.delete();
	}

}