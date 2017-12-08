<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>编辑角色-权限管理-EASY2GO ADMIN</title>
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

<DIV class="col-md-3">
<DIV class="panel">
<DIV class="panel-heading"> 角色</DIV>
<DIV class="panel-body">
<UL class="nav nav-pills nav-stacked" role="tablist">
  <LI class="active" role="presentation"><A 
  href="<%=basePath %>distributor/roleinfo/rolelist">全部角色</A></LI>
  <LI role="presentation"><A 
href="<%=basePath %>distributor/roleinfo/add">添加角色</A></LI></UL></DIV></DIV></DIV>
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">编辑角色</DIV>
<DIV class="panel-body">
<FORM id="role_form" class="form-horizontal" role="form" method="post" action=""><INPUT 
name="roleID" value="${roleInfo.roleID }" id="role_id" type="hidden">
<DIV class="form-group"><LABEL class="col-md-2 control-label" >名称:</LABEL>
<DIV class="col-md-6"><INPUT id="role_name" class="form-control" name="roleName" 
type="text" required="" value="${roleInfo.roleName }" data-popover-offset="0,8"></DIV>
<DIV class="col-md-4">
<P class="form-control-static"></P></DIV></DIV>
<DIV class="form-group"><LABEL class="col-md-2 control-label" for="permission_group_no">权限:</LABEL>
<DIV class="col-md-10">
<DIV class="checkbox">
<c:forEach items="${mglist}" var="mg" varStatus="status">

<DIV class="checkbox-group">
<STRONG>${mg.menuGroupName}</STRONG></DIV>
<c:forEach items="${mg.menuInfos}" var="m" varStatus="status">
<c:if test="${fn:contains(romid,m.menuInfoID)}">
<LABEL class="checkbox-items"><INPUT name="role_menu" value="${m.menuInfoID}" type="checkbox" checked="checked">${m.menuName}</LABEL>
</c:if>
<c:if test="${!fn:contains(romid,m.menuInfoID)}">
<LABEL class="checkbox-items"><INPUT name="role_menu" value="${m.menuInfoID}" type="checkbox">${m.menuName}</LABEL>
</c:if>
</c:forEach>
</c:forEach>
</DIV></DIV></DIV>
<DIV class="form-group">
<DIV class="col-md-7 col-md-offset-2">
<DIV class="btn-toolbar"><BUTTON class="btn btn-primary" 
type="submit">保存</BUTTON><BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" 
type="button">返回</BUTTON></DIV></DIV></DIV></FORM></DIV></DIV></DIV></SECTION></SECTION></section>
<script src="<%=basePath %>static/js/app.min.js?20150209"></script>
<SCRIPT type="text/javascript">
$(function(){
	//异步提交发邮件表单.
	$("#role_form").submit(function(){
		var chk_value =new Array();
		$('input[name="role_menu"]:checked').each(function(){    
			   chk_value.push($(this).val());    
		});
		
		$.ajax({
			type:"POST",
			url:"<%=basePath %>distributor/roleinfo/editsave",
			dataType:"html",
			data:{menulist:chk_value.toString(),roleName:$("#role_name").val(),roleID:$("#role_id").val()},
			success:function(data){
                msg = data;
				data=parseInt(data);
				if(data==1){
					window.location="<%=basePath %>distributor/roleinfo/rolelist";
				}else if(data==0){
					easy2go.toast('info',"角色修改成功，权限修改失败，请联系管理员");
				}else if(data==-1){
					easy2go.toast('info',"修改失败");
				} else {
                     easy2go.toast('error', msg);
                 }
			}
		});
		return false;
	});
});
	
</SCRIPT>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>