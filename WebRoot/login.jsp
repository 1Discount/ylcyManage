<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<meta name="csrf_token">
</head>
<body class="login-body">
	<div class="container">
		<form id="signin-form" name="session_create"
			action="<%=basePath%>admin/adminuserinfo/login" method="post"
			role="form" class="form-signin">
			<input type="hidden" name="_csrf">
			<h2 class="form-signin-heading">用 户 登 录</h2>
			<div class="login-wrap">
				<div class="user-login-info">
					<input type="text" id="user_email" name="email" placeholder="Email"
						value="${email}" autofocus="" class="form-control"> <input
						type="password" id="user_pwd" name="password"
						placeholder="Password" class="form-control">
				</div>
				<label class="checkbox"> <input style="display: none;"
					type="checkbox" id="rememberme_pwd" name="rememberme" value="true"><span
					class="pull-right"><a data-toggle="modal"
						href="<%=basePath%>admin/adminuserinfo/tofindpassword">我忘记密码了?</a></span>
				</label>
				<button type="submit" class="btn btn-lg btn-login btn-block">登
					入</button>
				<div class="registration">
					没有帐号?<a href="<%=basePath%>login_up.jsp">前往注册</a>
				</div>
			</div>
		</form>
		
		<!-- <form action="http://localhost/ylcyManage/api/editFlowDealOrder" method="get">
			<p><input type="text" name="flowDealID" value="000080a4-73ea-4222-a215-a795be17919b"/></p>
			<p><input type="text" name="distributorID" value="113"/></p>
			<p><input type="text" name="password" value="@easy2go"/></p>
			<p><input type="text" name="orderType" value="1"/></p>
			<p><input type="text" name="userCountry" value="美国"/></p>
			<p><input type="text" name="flowDays" value="3"/></p>
			<p><input type="submit" name="" value="提交"/></p>
		</form> -->
		
	
	</div>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/jquery.cookies.2.2.0.min.js"></script>
	<script>
      easy2go.responseMsg.info = "${registerlog}".trim();
      easy2go.responseMsg.warn ="${loginmes}";
      easy2go.responseMsg.error = "".trim();
      easy2go.showResponseMSG();
    </script>
	<script type="text/javascript">
      $(function(){
    	  if($.cookie('upwd')!=null && $.cookie('upwd')!=''){
    		  $("#rememberme_pwd").attr("checked","checked");
    		  var uname=$.cookie('uname');
    		  var upwd=$.cookie('upwd');
    		  window.location="<%=basePath%>admin/adminuserinfo/login?email="
						+ uname + "&password=" + upwd;
			}
			$("#rememberme_pwd").click(
					function() {

						if (this.checked) {
							if ($("#user_email").val() != ''
									&& $("#user_pwd").val() != '') {
								document.cookie = 'uname='
										+ $("#user_email").val()
										+ ';expires=7 * 24 * 60 * 60 * 1000';
								document.cookie = 'upwd='
										+ $("#user_pwd").val()
										+ ';expires=7 * 24 * 60 * 60 * 1000';
							} else {
								easy2go.toast('info', "输入不完整,无法自动登录");
							}
						} else {
							alert(2);
							$.cookie("uname", null, {
								path : '/'
							});
							$.cookie("upwd", null, {
								path : '/'
							});
						}

					});

			$("#signin-form").validate_popover({
				rules : {
					'email' : {
						required : true,
						email : true,
						minlength : 7,
						maxlength : 32
					},
					'password' : {
						required : true,
						minlength : 5,
						maxlength : 32
					},
				},
				messages : {
					'email' : {
						email : "请输入正确的Email。",
						required : "请输入Email！",
						minlength : "至少输入7个字符！",
						maxlength : "最多不超过70个字符！"
					},
					'password' : {
						required : "请输入密码！",
						minlength : "至少输入5个字符！",
						maxlength : "最多不超过32个字符！"
					},
				}
			});
		});
	</script>
	<script>
		(function(i, s, o, g, r, a, m) {
			i['GoogleAnalyticsObject'] = r;
			i[r] = i[r] || function() {
				(i[r].q = i[r].q || []).push(arguments)
			}, i[r].l = 1 * new Date();
			a = s.createElement(o), m = s.getElementsByTagName(o)[0];
			a.async = 1;
			a.src = g;
			m.parentNode.insertBefore(a, m)
		})(window, document, 'script',
				'//www.google-analytics.com/analytics.js', 'ga');
		ga('create', 'UA-58096909-2', 'auto');
		ga('send', 'pageview');
	</script>
</body>
</html>