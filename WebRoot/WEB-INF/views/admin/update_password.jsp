<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>重设密码-流量运营中心</TITLE>
<META charset="utf-8">
<META content="IE=edge" http-equiv="X-UA-Compatible">
<META name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<LINK rel="stylesheet" href="<%=basePath%>static/css/app.min.css">
<META name="csrf_token" content="k6tnIukf-ZC7_OvGS7ZZhdYYbCdOCUTDL49U">
<META name="GENERATOR" content="MSHTML 9.00.8112.16457">
</HEAD>
<BODY class="login-body">
	<DIV class="container">
		<FORM class="form-signin" role="form" method="post" action="<%=basePath%>admin/adminuserinfo/restPassword">
			    <INPUT name="_csrf" value="k6tnIukf-ZC7_OvGS7ZZhdYYbCdOCUTDL49U" type="hidden">
				<INPUT name="t" value="vZTc5SUXt214Bwvwwangbo@easy2go.cn" type="hidden">
				<INPUT name="_method" value="put" type="hidden">
				<H2 class="form-signin-heading">重设密码</H2>
				<DIV class="login-wrap">
					<DIV class="user-login-info">
						<INPUT class="form-control" name="email" readOnly="true" 	value="${email}" type="text"> 
						<span style="color: red;">包含字母,数字,和特殊字符(@,&,*,^,!),8位以上</span>
						<INPUT class="form-control" name="password" type="password" placeholder="请输入新密码">
					</DIV>
					<INPUT class="btn btn-lg btn-block btn-login" value="提交" type="submit">
				</DIV>
		</FORM>
	</DIV>
	<SCRIPT src="<%=basePath%>static/js/app.min.js"></SCRIPT>
	<SCRIPT>
		easy2go.responseMsg.info = "${codestatus}".trim();
		
		easy2go.responseMsg.warn = "${rest_password_log}".trim();
		easy2go.responseMsg.error = "".trim();
		setTimeout(function() {
			//alert(easy2go.showResponseMSG());
			easy2go.showResponseMSG();
		}, 350);
	</SCRIPT>

 	<SCRIPT>
		(function(i, s, o, g, r, a, m) {
			i['GoogleAnalyticsObject'] = r;
			i[r] = i[r] || function() {
				(i[r].q = i[r].q || []).push(arguments)
			}, i[r].l = 1 * new Date();
			a = s.createElement(o), m = s.getElementsByTagName(o)[0];
			a.async = 1;
			a.src = g;
			m.parentNode.insertBefore(a, m)
		})(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
		ga('create', 'UA-58096909-2', 'auto');
		ga('send', 'pageview');
	</SCRIPT> 
</BODY>
</HTML>
