<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>客服中心-wifi密码查询-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
    <style type="text/css">
           #searchTable tr{height:40px;} 
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
<DIV class="panel-heading"><h4>wifi密码查询</h4></DIV>
<div class="form-group">
<div style="padding: 20px;">
	<label class="inline-label" style=" line-height: 34px;">设备序列号：</label>
	<input class="form-control" id="SN" name="SN" type="text" placeholder="" style="width:200px;display: inline-block;">&nbsp;
		<button class="btn btn-primary" id="serch" type="button" onclick="selectwifi()">查询</button>
	<input class="form-control" id="SNresult" name="SN" type="text" placeholder="" style="width:200px;border:none; display:inline-block;"><br /><br />
	

</div>

</DIV>
</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script type="text/javascript">
    $(function(){
    	$(document).keydown(function(event){
    		  if (event.keyCode == 13) {
    			  selectwifi();
    		  };
    	});
    })
 	function selectwifi(){
 		var SN = $("#SN").val();
 		if(SN=="" || SN.length!=6){
 			easy2go.toast('warn', "请输入6位数设备序列号");
 			return;
 		}
 		$.ajax({
			type : "POST",
			url : "<%=basePath%>customer/customerInfolist/selectwifipsw?SN="+SN,
			dataType : "JSON",
			success : function(data) {
				$("#SNresult").val("wifi密码是："+data.result	);
			} 
		});
 	}
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>