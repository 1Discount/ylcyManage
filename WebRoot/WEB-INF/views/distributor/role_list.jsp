<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>角色列表-权限管理-流量运营中心</title>
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
<H3 class="panel-title">全部角色</H3></DIV>
<DIV class="panel-body">
<table id="searchTable">
        <tr>
        	<th w_index="roleID" w_hidden="true" width="10%;"></th>
        	<th w_index="roleName" width="10%;">角色名称</th>
            <th w_index="remark" width="10%;">备注</th>
            <th w_index="creatorDate" width="10%;">创建时间</th>
            <th w_render="operate" width="30%;">操作</th>
        </tr>
</table>
</DIV></DIV>
</DIV></SECTION></SECTION></section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <SCRIPT type="text/javascript">
    //var menu={"roleMenu":true,"allPermissionsMenu":true};
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>distributor/roleinfo/pagelist',
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30],
           multiSort:true
           //pageAll:true
       });
   });
    
   function operate(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       return '<A class="btn btn-primary btn-xs" href="<%=basePath %>distributor/roleinfo/eidt?roleID='+record.roleID+'&roleName='+record.roleName+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;<A class="btn btn-primary btn-xs" onclick="deleteinfo('+rowIndex+')" href="#"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>'
   }
   
   /* function operate1(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       if(record.remark==""){
    	   return "";
       }
       return '<A class="btn btn-primary btn-xs"  href="#">'+record.remark+'</A>'
   } */
   
   
   //删除.
   function deleteinfo(index){
       if(confirm("确认删除此角色吗?")) {
	   var record= gridObj.getRecord(index);
	   $.ajax({
			type:"POST",
			url:"<%=basePath %>distributor/roleinfo/deletebyid",
			dataType:"html",
			data:{rid:record.roleID},
			success:function(data){
				if(data=="1"){
					easy2go.toast('info',"删除成功");
					gridObj.refreshPage();
				}else if(data=="0"){
					easy2go.toast('info',"角色删除成功，角色下菜单删除失败");
					gridObj.refreshPage();
				}else if(data=="-1"){
					easy2go.toast('info',"删除失败");
				} else {
                     easy2go.toast('error', data);
                 }
			}
	   });
	   }
   }
 
</SCRIPT>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>