<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>设备订单详情-订单管理-EASY2GO ADMIN</title>
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
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">设备交易详细</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<tr>
										<td style="width: 160px">ID:</td>
										<td style="width: 400px">${devorder.deviceDealID }</td>
										<td>订单：</td>
										<td><a href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID=${devorder.orderID }">${devorder.orderID }</a></td>
									</tr>
									<tr>
										<td>金额（￥）：</td>
										<td><span class="num">${devorder.dealAmount }</span></td>
										<td>创建者：</td>
										<td><a href="#">${devorder.creatorUserName }</a></td>
									</tr>
									<tr>
										<td>设备：</td>
										<td><a href="<%=basePath %>device/deviceInfodetail?deviceid=${devorder.SN}">${devorder.SN }</a></td>
										<td>订单生成时间：</td>
										<td>${devorder.creatorDateString }</td>
									</tr>
									<tr>
										<td>客户：</td>
										<td><a href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid=${devorder.customerID}">${devorder.customerName }</a></td>
										<td>交易类型：</td>
										<td><span>${devorder.deallType }</span></td>
									</tr>
									<tr>
										<td>归还时间：</td>
										<td>${devorder.returnDate }</td>
										<td>是否已归还：</td>
										<td><span>${devorder.ifReturn}</span></td>
									</tr>
									<tr>
										<td colspan="4"><div class="btn-toolbar">
												<a href="<%=basePath %>orders/devicedealorders/edit?deviceDealID=${devorder.deviceDealID}"
													class="btn btn-primary"><span
													class="glyphicon glyphicon-edit"></span>编辑</a>
											</div></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</SECTION>
		</SECTION>
	</section>

	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
   
   /* function operate1(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       if(record.remark==""){
    	   return "";
       }
       return '<A class="btn btn-primary btn-xs"  href="#">'+record.remark+'</A>'
   } */
   
   function addsub(){
			$.ajax({
				type : "POST",
				url : "<%=basePath%>orders/devicedealorders/editsave",
				dataType : "html",
				data : $('#devicedeal_form').serialize(),
				success : function(data) {
                    msg = data;
					data = parseInt(data);
					if (data == 1) {
						bootbox.alert("订单编辑成功",function(){
							window.location="<%=basePath%>orders/devicedealorders/list";
												});
							} else if (data == 0) {
								easy2go.toast('warn', "保存失败");
							} else if (data == -1) {
								easy2go.toast('warn', "参数为空");
							} else {
                                easy2go.toast('error', msg);
                            }
						}
					});
		}
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>