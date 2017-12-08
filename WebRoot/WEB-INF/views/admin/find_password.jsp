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
<TITLE>找回密码-EASY2GO ADMIN</TITLE>
<META charset="utf-8">
<META content="IE=edge" http-equiv="X-UA-Compatible">
<META name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<LINK rel="stylesheet" href="<%=basePath%>static/css/app.min.css">
<META name="csrf_token" content="vUFZ4hjB-msj3Q3x4cDKc5L-iD8dux-HaE8Q">
<META name="GENERATOR" content="MSHTML 9.00.8112.16457">
</HEAD>
<BODY class="login-body">
	<DIV class="container">
		<FORM class="form-signin" id="restpassword-email-form" role="form"
			method="post" action="#">
			<INPUT name="_csrf" value="vUFZ4hjB-msj3Q3x4cDKc5L-iD8dux-HaE8Q"
				type="hidden">
			<H2 class="form-signin-heading">找回密码</H2>
			<DIV class="login-wrap">
				<DIV class="user-login-info">
					<INPUT id="user_email" class="form-control" name="email"
						type="text" placeholder="请输入您注册时使用的email">
				</DIV>
				<INPUT class="btn btn-lg btn-block btn-login" value="好了，我填好了"
					type="submit">
			</DIV>
		</FORM>
	</DIV>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<SCRIPT type="text/javascript">
    $(function(){
		$("#restpassword-email-form").validate_popover({
			rules:{
				'email':{ required:true, email:true,minlength:7,maxlength:32},
			},
			messages:{
				'email':{
					email: "请输入正确格式的Email。",
					required:"请输入Email！",
					minlength:"至少输入7个字符！",
					maxlength:"最多不超过32个字符！"
				}
			}});
		//异步提交发邮件表单.
		$("#restpassword-email-form").submit(function(){
			if($("#user_email").val()==''){
				return false;
			}
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/invitation/sendemail",
					dataType : "html",
					data : $('#restpassword-email-form').serialize(),
					success : function(data) {
						easy2go.toast('info', data);
					}
				});
				return false;
			})
		});
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
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