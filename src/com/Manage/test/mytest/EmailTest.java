package com.Manage.test.mytest;

import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.*;
import javax.servlet.ServletContext;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.Manage.common.cache.CacheUtils;
import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;
import com.Manage.common.util.FileWrite;
import com.Manage.common.util.PropertiesLoader;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.dao.CustomerInfoDao;
import com.Manage.service.SocketClient.CommandSer;
import com.Manage.service.SocketClient.SocketLongClient;

public class EmailTest
{
	@Autowired
	protected static CustomerInfoDao customerInfoDao;



	public static void main(String[] arg1)
	{
		/*
		 * try{ HtmlEmail email = new HtmlEmail();
		 * //这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
		 * email.setHostName(Constants.JAVAMAIL_HOSTNAME);
		 * email.setSSL(Constants.JAVAMAIL_IFSSL);
		 * email.setSmtpPort(Constants.JAVAMAIL_SMTPPORT); //字符编码集的设置
		 * email.setCharset("UTF-8"); //收件人的邮箱 email.addTo("1424687997@qq.com");
		 * //发送人的邮箱
		 * email.setFrom(Constants.JAVAMAIL_SENDNAME,Constants.JAVAMAIL_SENDNAME
		 * ); //如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
		 * email.setAuthentication
		 * (Constants.JAVAMAIL_SENDNAME,Constants.JAVAMAIL_SENDPASSWORD());
		 * //要发送的邮件主题 email.setSubject("试试呗"); //
		 * 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签 String obj ="测试邮件看看.";
		 * email.setMsg(obj); // 发送 email.send(); }catch(Exception e){
		 * e.printStackTrace(); }
		 */
		// gbEncoding("wb07284843695wb");
		// System.out.println(TokenProcessor.getInstance().generateTokeCode());
		/*
		 * try {
		 * //System.out.print(DES.toHexString(DES.encrypt("13802283407&abc",
		 * Constants.DES_KEY))); //System.out.print(DES.decrypt(
		 * "d972813d89a8fb5cc9aea804de93db08b138fccda9192c3a",
		 * Constants.DES_KEY));
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		/*
		 * CustomerInfoDao cDao=new CustomerInfoDao(); CustomerInfo
		 * customerInfo=new CustomerInfo();
		 * customerInfo.setPhone("13632525790");
		 * customerInfo.setPassword("123456"); List<CustomerInfo>
		 * ls=customerInfoDao.getSearchCustomerInfoList(customerInfo);
		 * System.out.print("sdf");
		 */
		// Constants.setConfig("we.we","123");
		// AdminUserInfoDao adminUserInfoDao=
		// ApplicationContext2.getBean(AdminUserInfoDao.class);

		// String pathroot=ApplicationContext2.getRootpath();
		// String path=pathroot+"500.png";
		// java.io.File file=new File(path);
		/*
		 * if(file.exists()){ long temp=file.length(); }
		 */
		/*
		 * Date d=new Date(1453175530000L);
		 * System.out.println(d.toLocaleString());
		 */
		/*
		 * String tString="sdfsfsfsdfsfsfds"; try {
		 * System.out.println(tString.getBytes("").length); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * CommandSer commandSer=new CommandSer();
		 * commandSer.remoteShutdown("172150210000204");
		 */
		/*
		 * String string=CacheUtils.get("CacheUtils", "SNTAG").toString();
		 * >>>>>>> Stashed changes System.out.println(string);
		 */
		/* FileWrite.printlog("知道呀"); */

		
		  try { 
			System.out.println( Bytes.valueOf("1024").toString());
		  
		  }catch (Exception e) {
			e.printStackTrace();
		  }
		  
		 
		/*try
		{
			URL url = new URL("http://localhost/ylcyManage/login.jsp");
			InputStreamReader isr = new InputStreamReader(url.openStream());
			BufferedReader br = new BufferedReader(isr);

			String str;
			while ((str = br.readLine()) != null)
			{
				System.out.println(str);
			}

			br.close();
			isr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/

	}



	public Properties getProperties()
	{
		Properties p = new Properties();
		p.put("mail.smtp.host", "exmail.qq.com");
		p.put("mail.smtp.port", "25");
		p.put("mail.smtp.auth", true);
		return p;
	}



	public static String gbEncoding(String gbString)
	{
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++)
		{
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2)
			{
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		System.out.println("unicodeBytes is: " + unicodeBytes);
		return unicodeBytes;
	}

}
