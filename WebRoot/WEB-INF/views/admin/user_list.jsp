<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>所有用户-用户管理-流量运营中心</title>
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
<DIV class="panel-body">
<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
<DIV class="form-group"><LABEL class="inline-label">姓名：</LABEL><INPUT 
class="form-control" id="userName" name="userName" type="text" 
placeholder="姓名"></DIV>
<DIV class="form-group"><BUTTON class="btn btn-primary" 
type="button" onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON></DIV></FORM></DIV></DIV>
<DIV class="panel">
<DIV class="panel-heading">
<H3 class="panel-title">所有用户</H3></DIV>
<DIV class="panel-body">
<table id="searchTable">
        <tr>
            <th w_index="userName"  width="10%;">用户名</th>
            <th w_index="email" width="10%;">email</th>
            <th w_index="phone"  width="10%;">手机号</th>
            <th w_index="roleName"  width="10%;">角色</th>
            <th w_render="operate" width="30%;">操作</th>
        </tr>
</table>
</DIV></DIV></DIV></SECTION></SECTION></section>

<div id="myModal" class="modal fade">
<div class="modal-dialog">
<div class="modal-content">
<!-- dialog body -->
<div class="modal-body">
<button type="button" class="close" data-dismiss="modal">&times;</button>
Hello world!
</div>
<!-- dialog buttons -->
<div class="modal-footer"><button type="button" class="btn btn-primary">保存</button><button type="button" class="btn btn-primary">取消</button></div>
</div>
</div>
</div>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>admin/adminuserinfo/datapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true,
                 displayPagingToolbarOnlyMultiPages:true
                 
             });
             
             
         });
         
         function operate(record, rowIndex, colIndex, options) {
             //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
             return '<A class="btn btn-primary  btn-xs" href="<%=basePath %>admin/adminuserinfo/usertorole?userID='+record.userID+'&userName='+record.userName+'&email='+record.email+'"><SPAN class="glyphicon glyphicon-certificate">分配角色</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;<A class="btn btn-primary btn-xs" onclick="deletebyid(\''+record.userID+'\')" href="javascript:;"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
         }
         
         //编辑用户的功能去掉了
      <%-- <A class="btn btn-primary btn-xs"   href="<%=basePath %>admin/adminuserinfo/touseredit?userID='+record.userID+'&userName='+record.userName+'&phone='+record.phone+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp; --%>
         
         function deletebyid(userID){
        	 bootbox.confirm("确定删除吗?", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"POST",
                		 url:"<%=basePath %>admin/adminuserinfo/datetebyid",
                		 data:{userID:userID},
                		 dataType:'html',
                		 success:function(data){
            				if(data=="删除成功"){
            					easy2go.toast('info',data);
                				gridObj.refreshPage();
            				}else{
            					easy2go.toast('warn',data);
            				}
                		 }
                		 
                	 });
        		 }  
        	 });
         }
         
         
         //分配角色.
         function usertorole(index){
        	 var record= gridObj.getRecord(index);
        	 window.location="<%=basePath %>admin/adminuserinfo/usertorole?userID="+record.userID+"&userName="+record.userName+"&email="+record.email;
         }
         
        
         
</SCRIPT>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>