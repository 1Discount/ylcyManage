<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>编辑客户-客户管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-9">
					<DIV class="panel">
						<div class="panel-heading">数据服务交易详细</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<tr>
										<td style="width: 15%;">流量交易ID:</td>
										<td style="width: 35%;">${Model.flowDealID}</td>
										<td style="width: 15%;">订单：</td>
										<td style="width: 35%;"><a id="orderID" href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID=${Model.orderID}">${Model.orderID}</a></td>
									</tr>
									<tr>
										<td>客户名称：</td>
										<td><span class="label label-success label-xs">${Model.customerName}</span></td>
										<td>订单状态：</td>
										<td><c:if test="${Model.orderStatus eq '已暂停' or Model.orderStatus eq '已过期'}">
												<span class="label label-danger label-xs">${Model.orderStatus}</span>
											</c:if> <c:if test="${Model.orderStatus ne '已暂停' and Model.orderStatus ne '已过期'}">
												<span class="label label-success label-xs">${Model.orderStatus}</span>
											</c:if></td>
									</tr>
									<tr>
										<td>金额（￥）：</td>
										<td><span class="num">${Model.orderAmount}</span></td>
										<td>创建者：</td>
										<td><a href="#">${Model.creatorUserName}</a></td>
									</tr>
									<tr>
										<td>天数：</td>
										<td>${Model.flowDays}</td>
										<td>已使用天数：</td>
										<td>${Model.flowDays - Model.daysRemaining}</td>
									</tr>
									<tr>
										<td>剩余可用天数：</td>
										<td>${Model.daysRemaining}</td>
										<td>当前周期剩余分钟数：</td>
										<td>${Model.minsRemaining}</td>
									</tr>
									<tr>
										<td>预约使用日期：</td>
										<td>${Model.panlUserDate}</td>
										<td>到期时间：</td>
										<td>${Model.flowExpireDate}</td>
									</tr>
									<tr>
										<td>创建时间：</td>
										<td>${Model.creatorDateString}</td>
										<td>更新时间：</td>
										<td>${Model.modifyDateString}</td>
									</tr>
									<tr>
										<td colspan="4"><b><em>设备情况:</em></b></td>
									</tr>
									<tr>
										<td>启用的设备：</td>
										<td><a href="<%=basePath %>device/deviceInfodetail?deviceid=${Model.SN}">${Model.SN}</a></td>
										<td>当前计费周期开始时间：</td>
										<td>${Model.intradayDate}</td>
									</tr>
									<tr>
										<td>IMSI：</td>
										<td>${Model.IMSI} <a href="<%=basePath %>sim/siminfo/view?IMSI=${Model.IMSI}">
												<span class="label label-info label-xs">查看该SIM卡信息</span>
											</a></td>
										<td colspan="2"></td>
									</tr>
								</table>
							</div>
							
							<FORM class="form-inline" role="form" method="get" action="#" >
								<DIV class="form-group">
									<LABEL class="inline-label">机身码：</LABEL>
									<INPUT class="form-control" name="SN" id="SN" type="text" placeholder="请输入机身码后六位">
									<button type="button" class="btn btn-primary" onclick="updateSN();">更换设备</button>
								</DIV>
							</FORM>
						</div>
					</div>
				</DIV>
			</SECTION>
		</SECTION>
	</SECTION>
	
	
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	
	
	<script type="text/javascript">
		function updateSN(){
			var SN = $("#SN").val();
			 $.ajax({
           		 type:"POST",
           		 url:"<%=basePath%>device/updateSN?SN=" + SN + "&orderID=${Model.orderID}&flowDealID=${Model.flowDealID}&oldsn=${Model.SN}",
				 dataType : 'html',
				 success : function(data) {
					if (data == "0") {
						easy2go.toast('warn', "更新失败");
					}
					else if (data == "1") {
						easy2go.toast('info', "更新成功");
					}
					else if (data == "-1") {
						easy2go.toast('warn', "该机身码对应的设备已有订单，或不可用");
					}
				 }
			});
		}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>