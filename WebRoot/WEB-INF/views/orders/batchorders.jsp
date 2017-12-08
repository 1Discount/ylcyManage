<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>批量导入订单</title>
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
	<div id="D_display" style="display:none;">
		<div
			style="background: #666; width: 100%; height: 100%; position: absolute; z-index: 1; opacity: 0.2;">

		</div>
		<div
			style="border: 1px solid #999; width: 240px; background: #FFF; color: #000; height: 35px; padding: 0px 15px; line-height: 35px; text-align: center; opacity: 1; position: absolute; left: 50%; top: 50%; z-index: 888;">
			<img src="../../static/images/spinner.gif"
				style="float: left; margin-top: 7px; width: 20px; height: 20px;" />正在导入数据,请不要关闭程序...
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
	<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
	<SECTION id="main-content"> <SECTION class="wrapper">
	<DIV class="col-md-12">
		<DIV class="panel">
			<DIV class="panel-body">
				<DIV class="panel-heading">批量导入订单</DIV>
				<DIV class="panel-body">
					<DIV class="panel">
						<DIV class="panel-heading">
							<SMALL>使用Excel文档批量导入订单</SMALL>
						</DIV>
						<DIV class="panel-body">
							<%--              <span style="border:0px solid pink;font-size:14px;color:red;padding-left:50%;">${excelmessage}</span>--%>
							<form id="import_form" class="form-horizontal"
								action="" method="post"
								enctype="multipart/form-data">
								<div class="panel-heading">
									<label for="subscribeTag0" class="radio-inline">
					                  <input type="radio" name="orderType" id="subscribeTag0" value="0" checked>按天数不限流量</label>
					                <label for="subscribeTag1" class="radio-inline">
					                  <input type="radio" name="orderType" id="subscribeTag1" value="1">按天数限流量</label>
					                <label for="subscribeTag2" class="radio-inline">
					                  <input type="radio" name="orderType" id="subscribeTag2" value="2">按时间段使用天数不限流量</label>
					                  <span style="color:red; margin-left: 50px;">每次导入时只能选择一种模式进行导入，一次导入的订单不能包含多种模式混合的订单</span>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">批量导入：</label>
									<div class="col-sm-9">
										<input type="file" id="file" name="file" id="file"
											data-popover-offset="0,8" required class="form-control">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-6 col-sm-offset-3">
										<div class="btn-toolbar">
											<button type="button" id="batchsubmit"
												onclick="unlockScreen();" class="btn btn-primary">提交</button>
											<button type="button" onclick="javascript:history.go(-1);"
												class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
							<div class="row">
								<div class="well">
									<span id="LogMessage"
										style="border: 0px solid pink; font-size: 14px; color: red;">${LogMessage}</span>
								</div>
							</div>


							<div>
							     <p>表格模板下载地址:<a href="http://58.250.57.153:81/redmine/projects/easy2go-backend/files" target="_bank">http://58.250.57.153:81/redmine/projects/easy2go-backend/files</a></p>
								注意：<br>
								<br>
								<p>1.批量导入数据只支持 excel 2003版本</p>
								<p>2.请不要修改excel的格式，在对应的列加上需要导入的数据即可， excel档的文件名可以使用中文</p>
								<p>3.关键字段不能为空，否则可能引起该条数据导入失败 </p>
								<p>4.SN只需输入最后四位即可，并确保输入的SN状态为可使用</p>
								<p>5.预约开始时间的填写格式：例如 “2015年1月1日 13点30分”对应的输入格式为：“ 1501011330 ” </p>
								<p>6.导入时系统会查询当前的客户资料，如何没有该客户，则导入相应的客户资料</p>
								<p>7.导入时系统会查询当前输入的国家名称列表是否存在，如果不存在则会导入失败并给出相应的提示</p>
								<p>8.当数据导入失败时，系统会自动列出导入失败的手机号，以及导入失败的原因</p>
								
							</div>




						</DIV>
					</DIV>
	</SECTION></SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript">
		function unlockScreen(){
			var orderType = $("input[name='orderType']:checked").val();
			if(orderType=="0"){
				orderType="按天数不限流量";
				$("#import_form").attr('action','<%=basePath%>device/data/batchaddorders1');
			}else if(orderType=="1"){
				orderType="按天数限流量";
				$("#import_form").attr('action','<%=basePath%>device/data/batchaddorders2');
			}else if(orderType=="2"){
				orderType="按时单段使用天数不限流量";
				$("#import_form").attr('action','<%=basePath%>device/data/batchaddorders3');
			}
			bootbox.confirm("你选择的是<span style='color:red;'>"+orderType+"</span>,确认要导入吗？", function(result) {
				if(result){
					$("#D_display").css("display", "block");
				  $("#import_form").submit();
				}
			});
		}
	</script>
	    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="level2" />
</jsp:include>
</body>
</html>