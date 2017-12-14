<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>邀请注册-流量运营中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
  </head>
  <body>
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
      <SECTION id="main-content">
<SECTION class="wrapper">
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">
<H2 class="panel-title">邀请注册</H2></DIV>
<DIV class="panel-body">
<FORM id="invite-form" class="form-horizontal" role="form" method="post" name="invite" 
action=""><INPUT name="_csrf" value="OPgdN0xt-6h9U5tfDXwoa_G-4MpxHXJ0jrmY" 
type="hidden">
<DIV class="form-group"><LABEL class="form-label col-md-3 control-label" for="email">Email:</LABEL>
<DIV class="col-md-5"><INPUT id="email" class="form-control" name="email" 
maxLength="32" type="email"></DIV></DIV>
<DIV class="form-group">
<DIV class="col-md-offset-3 col-md-5"><BUTTON class="btn btn-primary" type="submit">发送</BUTTON></DIV></DIV></FORM></DIV></DIV></DIV></SECTION></SECTION></SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <SCRIPT type="text/javascript">
    $(function(){
	$("#invite-form").validate_popover({
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
	$("#invite-form").submit(function(){
		$.ajax({
			type:"POST",
			url:"<%=basePath %>admin/invitation/insertinfo",
			dataType:"html",
			data:$('#invite-form').serialize(),
			success:function(data){
				easy2go.toast('info',data);
			}
		});
		return false;
	});
});</SCRIPT>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>