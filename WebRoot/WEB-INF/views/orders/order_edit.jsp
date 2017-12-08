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
<title>编辑订单-订单管理-EASY2GO ADMINEASY2GO ADMIN</title>
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
				<div class="col-md-9">
					<div class="panel">
						<div class="panel-heading">编辑订单</div>
						<div class="panel-body">
							<form id="order_form" role="form"
								action="" method="post"
								autocomplete="off" class="form-horizontal">
								<input type="hidden" name="_method" value="put"><input
									type="hidden" name="orderID" value="${order.orderID}">
								<div class="form-group">
									<label class="col-md-3 control-label">客户:</label>
									<div class="col-md-6">
										<p class="form-control-static">
											<a href="#">${order.customerName}</a>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label for="order_amount" class="col-md-3 control-label">总金额:</label>
									<div class="col-md-6">
										<input id="order_amount" type="number" name="orderAmount"
											value="${order.orderAmount}" data-popover-offset="0,8" required
											class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label for="order_amount" class="col-md-3 control-label">备注：</label>
									<div class="col-md-6">
										<textarea id="order_remark" rows="3" name="remark"
											data-popover-offset="0,8" class="form-control">${order.remark}</textarea>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-9 col-md-offset-3">
										<div class="btn-toolbar">
											<button type="button" onclick="addsub();" class="btn btn-primary">保存</button>
											<button type="button" onclick="javascript:history.go(-1);"
												class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</SECTION>
		</SECTION>
	</section>

	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
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
	   
	   if($("#menu_name").val()==""||  $("#menu_path").val()=="" || $("#slect_mg").val()==""){
		   return false;
	   }
			$.ajax({
				type : "POST",
				url : "<%=basePath %>orders/ordersinfo/update",
				dataType : "html",
				data : $('#order_form').serialize(),
				success : function(data) {
                    msg = data;
					data = parseInt(data);
					if (data == 1) {
						bootbox.alert("订单编辑成功",function(){
							if('${From}' == 'youzan'){
								window.location="<%=basePath %>orders/youzan/index";
							} else {
                                window.location="<%=basePath %>orders/ordersinfo/list";
							}
						});
					} else if (data == 0) {
						easy2go.toast('warn', "添加失败");
					} else if(data==-1){
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