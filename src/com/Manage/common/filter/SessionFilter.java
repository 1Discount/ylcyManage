/*
 * 文件名     ：SessionFilter.java
 * 创建时间：2014年8月5日 上午10:12:03
 * 创建人     : Yangkeke
 */
package com.Manage.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Manage.control.AdminUserInfoControl;

/**
 * session超时跳转到登录界面
 * @author
 *
 */
public class SessionFilter extends HttpServlet implements Filter
{
	private static final long serialVersionUID = 1L;

	public void destroy()
	{
		System.out.println("session已经失效");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		servletResponse.setContentType("text/html;charset=UTF-8");
		HttpSession session = servletRequest.getSession();
		String uri = servletRequest.getRequestURI();
		if(uri.endsWith("login.jsp") ||uri.endsWith("verifyFactoryTestInfo") 
				|| uri.endsWith("gooflow.jsp")  
				||  uri.contains("conDis")|| uri.endsWith("login") 
				|| uri.endsWith("DManage") || uri.contains("register")  
				|| uri.contains("forcePassword") || uri.contains("static") 
				|| uri.contains("login_up.jsp") || uri.contains("test.jsp") 
				|| uri.contains("updatepassword") || uri.contains("tofindpassword") 
				|| uri.endsWith("sendemail") || uri.endsWith("restPassword") 
				|| uri.contains("upload") || uri.contains("batchaddorders") 
				|| uri.contains("backMoneyEnd") || uri.contains("backMoneyEndtuikuan") 
				|| uri.contains("getonecoupon") || uri.contains("getthisonecoupon")
				|| uri.contains("flowbydevicelogsTest")
				|| uri.contains("flowbydevicelogsTestPage")
				|| uri.contains("getflowlogpage")|| uri.contains("getflowBySnAndDate") 
				|| uri.contains("api") || uri.contains("upgradeBySN")
				|| uri.contains("upgradeFile") || uri.contains("updateUpgradeSuccess") 
				|| uri.contains("getUpgradeFileName") 
				|| uri.contains("saveFactoryTestInfo") 
				|| uri.contains("testUpgradeFileDirectory") 
				|| uri.contains("testLogin") || uri.contains("testIfNB") 
				|| uri.contains("testAppFile") 
				|| uri.contains("testAPPinterface")|| uri.endsWith("loginout"))
		{
			chain.doFilter(request,response);
			return;
		}
		//如果是ajax请求响应头会有 x-requested-with
		boolean isAjax;
		if(AdminUserInfoControl.set.contains("key")){session=null;}
        if (servletRequest.getHeader("x-requested-with") != null
        		&& servletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
        	//servletRequest.setHeader("sessionstatus", "timeout"); //在响应头设置session状态
        	isAjax = true;
        }else{
        	isAjax = false;
        }

		String rootUrlString = servletRequest.getScheme() + "://"
                + servletRequest.getServerName() + ":"
                + servletRequest.getServerPort() + servletRequest.getContextPath();
        String loginUrl = rootUrlString + "/login.jsp";
		if (null == session || null == session.getAttribute("User"))
		{
			if (isAjax) {
				PrintWriter out = servletResponse.getWriter();
				out.write(loginUrl); // 只把网址传递过去,方便前端跳转
			} else {
				PrintWriter out = servletResponse.getWriter();
				out.write("<script>window.location.href='" + loginUrl + "';</script>");
			}
			return;
		}

		/*// 判断是否弱密码
		if (null != session.getAttribute("isWeakPwd") && "1".equals(session.getAttribute("isWeakPwd"))
				&& !uri.contains("touseredit") && !uri.contains("pwdedit") && !uri.contains("loginout")) // /admin/adminuserinfo/touseredit
		{
			PrintWriter out = servletResponse.getWriter();
			// userID=1002&userName=唐明&phone=13691639673
			AdminUserInfo userInfo = (AdminUserInfo) session.getAttribute("User");
			out.write("<script>window.location.href='" + rootUrlString + "/admin/adminuserinfo/touseredit"
					+ "?IsWeakPwd=1&userID=" + userInfo.getUserID() + "&userName=" + userInfo.getUserName() + "&phone=" + userInfo.getPhone()
					+ "';</script>");
			return;
		}*/

        /*
         * 只比域名,不比端口及之后
         * 通过消息头Referer判断是否跨站访问 注:直接访问jsp时referer为null
         */
        String context = servletRequest.getContextPath();

        String referer = servletRequest.getHeader("Referer");

        if (null != referer && referer.indexOf(context) == -1) {
            PrintWriter out = servletResponse.getWriter();
            out.write("<script>window.location.href='" + loginUrl
                    + "';</script>");
            return;
        }
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException
	{

	}
}
