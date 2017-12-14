<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 格式化时间 -->

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>设备详情-设备管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html" %>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
#searchTable2 tr {
	height: 40px;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<!-- ============================================================ -->
<SECTION id="main-content">
  <SECTION class="wrapper">
    <DIV class="col-md-12">
    
<DIV class="panel">
<DIV class="panel-heading">设备（${deviceinfo.deviceID}）</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE class="table table-bordered table-hover">
  <TBODY>
  <TR>
    <TD><LABEL>机身码：</LABEL></TD>
    <TD>${deviceinfo.SN}</TD>
    <TD><LABEL>状态：</LABEL></TD>
    <TD>
      <SPAN class="label label-primary label-xs">
         ${deviceinfo.deviceStatus}
      </SPAN>
    </TD>
   </TR>
  <TR>
    <TD><LABEL>备注：</LABEL></TD><%--设备颜色： --%>
    <TD>${deviceinfo.remark}
    </TD>
    <TD><LABEL>创建者：</LABEL></TD>
    <TD>${deviceinfo.creatorUserName}
    </TD>
    </TR>
  <TR>
    <TD><LABEL>创建时间：</LABEL></TD>
    <TD>${deviceinfo.creatorDate}</TD>
    <TD><LABEL>更新时间：</LABEL></TD>
    <TD>${deviceinfo.modifyDate}</TD>
  </TR>
  </TBODY>
</TABLE>
</DIV>
<DIV class="btn-toolbar">
<A class="btn btn-primary" href="<%=path %>/device/edit?xlid=${deviceinfo.SN}"> 
    <SPAN class="glyphicon glyphicon-edit"></SPAN>编辑
</A>
</DIV>
</DIV></DIV>



<DIV class="panel">
<DIV class="panel-heading">
<H3 class="panel-title">相关设备交易单</H3></DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>交易ID</TH>
    <TH>金额</TH>
    <TH>类型</TH>
    <TH>订单</TH>
    <TH>客户</TH>
    <TH>状态</TH>
    <TH>是否归还</TH>
    <TH>归还日期</TH>
    <TH>创建者</TH>
    <TH>创建时间</TH>
    <TH>操作</TH>
   </TR>
   <c:forEach var="deviceorders" items="${device}" varStatus="count">
   <tr>
     <td><a href="<%=basePath%>orders/devicedealorders/info?deviceDealID=${deviceorders.deviceDealID}">${deviceorders.deviceDealID}</a></td>
     <td>${deviceorders.dealAmount}</td>
     <td>${deviceorders.deallType}</td>
     <td><a href="<%=basePath%>orders/ordersinfo/orderinfo?ordersID=${deviceorders.orderID}" target="_bland">查看订单</a></td>
     <td><a href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid=${deviceorders.customerID}">${deviceorders.customerName}</a></td>
     <td>${deviceorders.orderStatus}</td>
     <td>${deviceorders.ifReturn}</td>
     <td>${deviceorders.returnDate}</td>
     <td>${deviceorders.creatorUserName}</td>
     <td><fmt:formatDate value="${deviceorders.creatorDate}" type="both" timeStyle="medium" dateStyle="medium" /></td>
     <td><!-- 操作 --></td>
   </tr>
   </c:forEach>
  </THEAD>
  <TBODY>
  </TBODY>
</TABLE>
  
  </DIV></DIV></DIV></DIV></SECTION></SECTION>

		<!-- ============================================================ -->
		 
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="level1" />
</jsp:include>
</body>
</html>