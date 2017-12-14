<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>SIM服务器SIM卡统计-流量运营中心</title>
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
      <section id="main-content">
        <section class="wrapper">
          <%-- 这里添加页面主要内容  block content --%>
		<div class="col-md-12">
        <div class="panel">
        <div class="panel-heading">
        <h3 class="panel-title">说明</h3></div>
        <div class="panel-body">
        <p>SIM卡服务器的SIM卡总数和可用SIM卡数量, 通过此接口去同步并更新.</p>
        <button class="btn btn-primary" type="button" onclick="query();">开始统计并同步</button>
        </div>
        </div>
        
		<div class="panel">
		<div class="panel-heading">		
		<h3 class="panel-title">SIM服务器SIM卡统计</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
		    <th w_index="SIMServerID" width="15%;">服务器ID</th>
            <th w_index="serverCode" width="15%;">SIM服务器编号</th>
            <th w_index="IP">IP</th>
            <th w_index="serverStatus">状态</th>
            <th w_index="SIMCount">SIM卡总数</th>
            <th w_index="availableSIMCount">可用SIM卡数量</th>
		        </tr>
		</table></div>
		</div></div></div>
          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    var gridObj;
     $(function(){
         gridObj = $.fn.bsgrid.init('searchTable',{
             url:'<%=basePath %>sim/simserver/count',
             autoLoad: false,
             pageAll: true
         });
     });

     function query(){
    	 gridObj.refreshPage();    	 
     }
	</script>
    
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
  </body>
</html>