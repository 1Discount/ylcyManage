<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>设备短信查看-客服查询中心-流量运营中心</title>
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
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<section id="main-content">
			<section class="wrapper">
				<%-- 这里添加页面主要内容  block content --%>
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="form-title">编辑</h4>
						</div>
						<div class="panel-body">
							<form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
								<input type="hidden" name="enterpriseID" id="enterpriseID" value="${Model.enterpriseID}">
								<div class="form-group">
									<label for="company" class="col-md-3 control-label">企业名称:</label>
									<div class="col-md-6">
										<input id="enterpriseName" type="text" name="enterpriseName" value="${Model.enterpriseName}" data-popover-offset="0,8" required maxlength="50" class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">*</span>
										</p>
									</div>
								</div>
								
								
								<div class="form-group">
									<label for="operatorName" class="col-md-3 control-label">联系电话:</label>
									<div class="col-md-6">
										<input id="enterprisePhone" type="text" name="enterprisePhone" value="${Model.enterprisePhone}" data-popover-offset="0,8" required maxlength="50" class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">*</span>
										</p>
									</div>
								</div>
								
								
								<div class="form-group">
									<label for="operatorTel" class="col-md-3 control-label">联系地址:</label>
									<div class="col-md-6">
										<input id="enterpriseAddress" type="text" name="enterpriseAddress" value="${Model.enterpriseAddress}" data-popover-offset="0,8" required maxlength="50" class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">*</span>
										</p>
									</div>
								</div>


								<div class="form-group">
									<label for="operatorTel" class="col-md-3 control-label">帐户:</label>
									<div class="col-md-6">
										<input id="score" type="text" name="score" value="${Model.score}" data-popover-offset="0,8" required maxlength="50" class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">* </span>
										</p>
									</div>
								</div>
								
								
								<div class="form-group">
									<label for="operatorEmail" class="col-md-3 control-label">帐户余额:</label>
									<div class="col-md-6">
										<input id="balance" type="text" name="balance" value="${Model.balance}" data-popover-offset="0,8" maxlength="50" class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">* </span>
										</p>
									</div>
								</div>
								
								
								<div class="form-group">
									<label for="address" class="col-md-3 control-label">价格配置:</label>
									<div class="col-md-6">
<%-- 										<input id="priceConfiguration" type="text" name="priceConfiguration" value='${Model.priceConfiguration}' data-popover-offset="0,8" maxlength="1000" class="form-control">
 --%>									
 										<textarea rows="10" cols="auto"  id="priceConfiguration" name="priceConfiguration"class="form-control">
 											${Model.priceConfiguration==null?'{"510":30.0,"286":30.0,"427":30.0,"454":30.0,"535":30.0,"525":30.0,"456":30.0,"520":30.0,"502":30.0,"452":30.0,"466":30.0,"460":30.0,"111":30.0,"450":30.0,"515":30.0,"440":30.0,"455":30.0,"260":60.0,"278":60.0,"505":60.0,"292":60.0,"232":60.0,"231":60.0,"647":60.0,"240":60.0,"334":60.0,"262":60.0,"268":60.0,"242":60.0,"214":60.0,"272":60.0,"213":60.0,"302":60.0,"530":60.0,"284":60.0,"238":60.0,"266":60.0,"295":60.0,"219":60.0,"226":60.0,"310":60.0,"204":60.0,"274":60.0,"250":60.0,"230":60.0,"280":60.0,"247":60.0,"293":60.0,"234":60.0,"218":60.0,"542":60.0,"216":60.0,"724":60.0,"270":60.0,"228":60.0,"246":60.0,"208":60.0,"276":60.0,"288":60.0,"248":60.0,"202":60.0,"244":60.0,"206":60.0,"222":60.0,"225":60.0,"620":90.0,"655":90.0,"602":90.0,"617":90.0,"604":90.0,"472":90.0,"424":90.0,"404":90.0}				
 											':Model.priceConfiguration}
 										</textarea>
 									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">* 例如：{"460":"30.0","464":"30.0"}</span>
										</p>
									</div>
								</div>


								<div class="form-group">
									<label for="address" class="col-md-3 control-label">备注:</label>
									<div class="col-md-6">
										<input id="remark" type="text" name="remark" value="${Model.remark}" data-popover-offset="0,8" maxlength="100" class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red"> </span>
										</p>
									</div>
								</div>
							
								<div class="form-group">
									<div class="col-md-6 col-md-offset-3">
										<div class="btn-toolbar">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				
			</section>
		</section>
	</section>



	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<script type="text/javascript">
	  $("#edit_form").validate_popover({
	      rules: {
	        'enterpriseName': {
	          required: true,
	          maxlength: 50
	        },
	        'enterprisePhone': {
	          required: true,
	          maxlength: 50
	        },
	        'enterpriseAddress': {
	          required: true,
	          maxlength: 50
	        },
	        'score': {
	          required: true,
	          maxlength: 50
	        },
	        'balance': {
	          required: true,
	          maxlength: 100
	        },
	        'priceConfiguration': {
	            required: true,
	            maxlength: 1000
	          }
	      },
	      messages: {

	      },
	        submitHandler: function(form){
		        $.ajax({
		            type:"POST",
		            url:"<%=basePath%>enterprise/save",
		            dataType:"html",
		            data:$('#edit_form').serialize(),
		            success:function(data){
		                result = jQuery.parseJSON(data);
		                if (result.code == 0) { // 成功保存
		                    easy2go.toast('info',"保存成功, 正在跳转", false, null, {
			  					onClose: function() { 
			  						window.location.href="<%=basePath%>enterprise/list";
			  					}
			  				});
						}
						else {
							easy2go.toast('error', result.msg);
						}
					}
					});
				}
			});
	
	    //刷新
		function re() {
			$("#order_ID").val('');
			$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
		}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="level1" />
	</jsp:include>
</body>
</html>