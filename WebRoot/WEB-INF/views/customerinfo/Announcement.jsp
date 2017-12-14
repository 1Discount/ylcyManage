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
<title>添加客户-客户管理-流量运营中心</title>
<meta charset="utf-8">

<meta http-equiv= "Expires " content= "0 "> 
<meta http-equiv= "kiben " content= "no-cache "> 

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
.col-md-6 {
	width: 30%;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-heading">修改公告信息</DIV>
						<DIV class="panel-body"></DIV>
					</DIV>
				</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script type="text/javascript">
		
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>