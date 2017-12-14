<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>TDM列表-TDM管理-流量运营中心</title>
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
<H3 class="panel-title">全部TDM</H3></DIV>
<DIV class="panel-body">
<table id="searchTable">
        <tr>
        	<th w_index="TDMId" w_hidden="true" width="10%;"></th>
        	<th w_index="tdmName" width="10%;">TDM名称</th>
        	<th w_index="tdmAddress" width="10%;">地址</th>
        	<th w_index="tdmUrl" width="10%;">外网URL</th>
        	<th w_index="intranetUrl" width="10%;">内网URL</th>
        	<th w_index="serverCode" width="10%;">编号</th>
        	<th w_index="sort" width="10%;">排序</th>
        	<th w_index="tdmStatus" width="10%;">状态</th>
        	<th w_index="ownerName" width="10%;">渠道商</th>
        	<th w_index="softwareVersion" width="10%;">版本</th>
        	<th w_index="creatorUserName" width="10%;">创建者</th>
        	<th w_index="creatorDate" width="10%;">创建时间</th>
        	<th w_index="modifyUserName" width="10%;">修改者</th>
        	<th w_index="modifyDate" width="10%;">修改时间</th>
            <th w_index="remarks" width="10%;">备注</th>
            <th w_render="operate" width="10%;">操作</th>
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
           url:'<%=basePath%>tdm/list?sysStatus=0',
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30],
           multiSort:true
           //pageAll:true
       });
   });
    function operate(record, rowIndex, colIndex, options) {
        return '<A class="btn btn-primary btn-xs" onclick="deleteinfo('+rowIndex+')" href="#"><SPAN class="glyphicon glyphicon-remove">恢复</SPAN></A>';
    }
    //删除.
    function deleteinfo(index){
        if(confirm("确认恢复这个TDM吗?")) {
 	   var record= gridObj.getRecord(index);
 	   $.ajax({
 			type:"POST",
 			url:"<%=basePath %>tdm/deletebyid",
 			dataType:"html",
 			data:{TDMId:record.TDMId,sysStatus:1},
 			success:function(data){
 				if(data=="1"){
 					easy2go.toast('info',"恢复成功");
 					gridObj.refreshPage();
 				}else if(data=="2"){
 					easy2go.toast('info',"未获取到当前用户信息，请刷新后重新登录");
 				}else if(data=="3"){
 					easy2go.toast('info',"ID为空");
 				}else if(data=="0"){
 					easy2go.toast('info',"恢复失败");
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