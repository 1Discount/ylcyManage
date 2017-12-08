<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>分配角色-用户管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
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
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading">
<H4 class="panel-title">用户角色</H4></DIV>
<DIV class="panel-body">
<FORM id="userrole_form" class="form-horizontal" role="form" method="post" 
action="javascript:return;"><input type="hidden" id="user_id" value="${user.userID}" name="userID" />
<DIV class="form-group"><LABEL class="col-md-2 control-label">用户：</LABEL>
<DIV class="col-md-6">
<P class="form-control-static">${user.userName}</P>
<P class="form-control-static">${user.email}</P></DIV></DIV>
<DIV class="form-group"><LABEL class="col-md-2 control-label">角色：</LABEL>
<DIV class="col-md-6">
<SELECT class="form-control" id="role_id"  name="roleid" required="">

<OPTION value="">请选择角色</OPTION>
<c:forEach items="${rlist}"  var="r" varStatus="index">
<OPTION  value="${r.roleID}">${r.roleName}</OPTION>
</c:forEach>
</SELECT>
  </DIV>
<DIV class="col-md-3">
<P class="form-control-static"><SPAN class="red">*</SPAN></P></DIV></DIV>
<DIV class="form-group">
<DIV class="col-md-offset-2 col-md-6"><BUTTON class="btn btn-primary" onclick="subinfo();" type="submit">提交</BUTTON></DIV></DIV></FORM></DIV></DIV>
</DIV></SECTION></SECTION></section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <SCRIPT type="text/javascript">
    var menu={"roleMenu":true,"allPermissionsMenu":true};
    bootbox.setDefaults("locale","zh_CN");
   //表单提交.
   function subinfo(){
	   //var record= gridObj.getRecord(index);
	   if($("#role_id").val()==''){
		   return;
	   }
	   var rtext=$("#role_id").find("option:selected").text();
	   $.ajax({
			type:"POST",
			url:"<%=basePath %>admin/adminuserinfo/adduserrole",
			dataType:"html",
			data:{userID:$("#user_id").val(),roleID:$("#role_id").val(),roleName:rtext},
			success:function(data){
				if(data=="1"){
					bootbox.alert("角色分配成功!",function(){
						window.location="<%=basePath %>admin/adminuserinfo/userlist";
					});
					gridObj.refreshPage();
				}else if(data=="0"){
					easy2go.toast('info',"角色分配失败");
				}else if(data=="－1"){
                    easy2go.toast('info',"角色分配失败");
				}else{
					easy2go.toast('warn', data);
				}
			}
	   });
   }
 
</SCRIPT>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>