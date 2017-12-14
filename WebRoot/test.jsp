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
<title>全部客户-设备日志-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>

</head>
<body>

<form id="form" action="" method="get" >
	<p><span>password:</span><input type="text"/></p>
	<input type="button"  onclick="aa()"/>
</form>
</body>
<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
<script>

/* function aa(){
	alert(1);
	$.ajax({
		type : "POST",
		url : "http://localhost/ylcyManage/api/editFlowDealOrder?password=123456",
		dataType:"json",
		success : function(data) {
			alert(data);
		}
	});
} */
var waitForRfreshProgressTimer = setInterval(
		function(){
			alert("startcheck");
			//startCheckData1AndWaitForRefreshDownloadProgressEnd();
		}, 2000);
		clearTimeout(waitForRfreshProgressTimer);
		alert("end");
</script>
</html>