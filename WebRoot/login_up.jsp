<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- saved from url=(0027)http://m.easy2go.cn/sign_up -->
<!DOCTYPE html>
<HTML>
<HEAD>
<TITLE>流量运营中心</TITLE>
<META charset="utf-8">
<META content="IE=edge" http-equiv="X-UA-Compatible">
<META name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<LINK rel="stylesheet" href="<%=basePath%>static/css/app.min.css">
<META name="csrf_token" content="khHeUELF-WL7xdBpxFK5VYYd44Lg0nw6LuTk">
<META name="GENERATOR" content="MSHTML 9.00.8112.16457">
</HEAD>
<BODY class="login-body">
	<DIV class="container">
		<FORM id="register-form" class="form-signin" role="form" method="post"
			name="user_create"
			action="<%=basePath%>admin/adminuserinfo/register">
			<INPUT name="_csrf" value="khHeUELF-WL7xdBpxFK5VYYd44Lg0nw6LuTk"
				type="hidden">
			<H2 class="form-signin-heading">注&nbsp;&nbsp;册</H2>
			<DIV class="login-wrap">
				<DIV class="user-login-info"></DIV>
				<LABEL for="user_code"> 邀请码：</LABEL><INPUT id="user_code"
					class="form-control" value="${code}" name="inviteCode" type="text"><LABEL
					for="user_name">姓名：</LABEL><INPUT id="user_name"
					class="form-control" name="userName" type="text"><LABEL
					for="user_email">Email:</LABEL><INPUT id="user_email"
					class="form-control" name="email" type="email"><LABEL
					for="user_password">密码：</LABEL><INPUT id="user_password"
					class="form-control" name="password" type="password" required=""
					data-validation-required-message="请输入密码"><SPAN
					style="color: red;"><I class="fa fa-warning"></I>&nbsp;请勿使用简单密码。</SPAN>
				<BUTTON class="btn btn-lg btn-login btn-block" id="subtn"
					type="submit">提交</BUTTON>
				<DIV class="registration">
					<SPAN class="pull-right">如已有账户，<A
						href="<%=basePath%>login.jsp">请点此登录。</A></SPAN>
				</DIV>
			</DIV>
		</FORM>
	</DIV>
	<SCRIPT src="<%=basePath%>static/js/app.min.js"></SCRIPT>
	
	
	<SCRIPT>
		easy2go.responseMsg.info = "${codestatus}".trim();
		easy2go.responseMsg.warn = "${rest_password_log}".trim();
		easy2go.responseMsg.error = "".trim();
		setTimeout(function() {
			easy2go.showResponseMSG();
		}, 350);
		//邀请码如果不可用，将按钮置为灰色.
		if ('${codestatus}' != '邀请码可用' && '${codestatus}' != '') {
			$("#subtn").attr("disabled", "disabled");
		}
	</SCRIPT>


	<SCRIPT type="text/javascript">
		$(function() {
			$("#register-form").validate_popover({
				rules : {
					'invite_code' : {
						required : true,
						minlength : 10,
						maxlength : 32
					},
					'userName' : {
						required : true,
						minlength : 2,
						maxlength : 16
					},
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
					'invite_code' : {
						required : "请输入邀请码",
						minlength : "至少输入10个字符！",
						maxlength : "最多不超过32个字符！"
					},
					'userName' : {
						required : "请输入昵称！",
						minlength : "至少输入3个字符！",
						maxlength : "最多不超过16个字符！"
					},
					'email' : {
						email : "请输入正确格式的Email。",
						required : "请输入Email！",
						minlength : "至少输入7个字符！",
						maxlength : "最多不超过32个字符！"
					},
					'password' : {
						required : "请输入密码！",
						minlength : "至少输入5个字符！",
						maxlength : "最多不超过32个字符！"
					},
				}
			});
		});
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
		})(window, document, 'script',
				'//www.google-analytics.com/analytics.js', 'ga');
		ga('create', 'UA-58096909-2', 'auto');
		ga('send', 'pageview');
	</SCRIPT>
</BODY>
</HTML>