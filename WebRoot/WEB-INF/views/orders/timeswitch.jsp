<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>定时任务开关-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<%--  <link href="<%=basePath %>static/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=basePath %>static/css/bootstrap-switch.css" rel="stylesheet"> --%>
<link rel="stylesheet" href="<%=basePath%>static/css/lc_switch.css">


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
				<div class="col-md-12">
					<div class="panel">
						<DIV class="panel-body" style="margin: 0px; padding: 0px;">
							<DIV class="panel-heading">
								<b>定时任务开关: <span style="color: red;" id="snCount">
								<input type="hidden" value="" id="snCountInput"></span></b>
							</DIV>
						</div>
						<DIV class="panel-body">
							<div style="display: inline-block; font-weight: bolder;">
								<span>定时开关SIM卡</span>
								<p style="display: inline-block;">
									<input id="simswitch" type="checkbox" name="check-1" value="4" class="sim" autocomplete="off"/>
								</p>
							</div>
							<!-- <div style="display: inline-block;margin-left: 100px;font-weight: bolder;">
								<span>有赞订单同步</span>
								<p style="display: inline-block;">
									<input id="youzhanswitch" type="checkbox" name="check-1" value="4" class="youzhan" autocomplete="off"  />
								</p>
							</div> -->
							<div style="display: inline-block;margin-left: 100px; font-weight: bolder;">
								<span>基站转换</span>	
								<p style="display: inline-block;">
									<input id="jizhanswitch" type="checkbox" name="check-1" value="4" class="jizhan" autocomplete="off" />
								</p>
							</div>
						</div>
					</div>
				</div>
			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/timeswitch/lc_switch.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script type="text/javascript">
	$(function(){
		//初始化开关状态
		var simswitch =  ${simswitch};
		//var youzhanswitch =  ${youzhanswitch};
		var jizhanswitch =  ${jizhanswitch};
		if(simswitch){
			$("#simswitch").attr({'checked':'checked'});
		}
		/* if(youzhanswitch){
			$("#youzhanswitch").attr({'checked':'checked'});
		} */
		if(jizhanswitch){
			$("#jizhanswitch").attr({'checked':'checked'});
		}
	});
	//sim卡开关
	$(document).ready(function(e) {
		$('#simswitch').lc_switch();
		//每次触发字段更改状态
	 	 $('body').delegate('.sim', 'lcs-statuschange', function() {
			var status = ($(this).is(':checked')) ? 'checked' : 'unchecked';
		}); 
		$('body').delegate('.sim', 'lcs-on', function() {
			$.ajax({
	            type:"POST",
	            url:"<%=basePath %>timeswitch/simwitch?status=on",
	            dataType:"html",
	            success:function(data){
	            },
	            error:function(){
	            }
	        });
		});

		// triggered each time a is unchecked
		$('body').delegate('.sim', 'lcs-off', function() {
			$.ajax({
	            type:"POST",
	            url:"<%=basePath %>timeswitch/simwitch?status=off",
	            dataType:"html",
	            success:function(data){
	            },
	            error:function(){
	            }
	        });
		});
	});
	//有赞开关
	<%-- $(document).ready(function(e) {
		$('#youzhanswitch').lc_switch();
		//每次触发一个检查
		$('body').delegate('.youzhan', 'lcs-on', function() {
			$.ajax({
	            type:"POST",
	            url:"<%=basePath %>timeswitch/youzhan?status=on",
	            dataType:"html",
	            success:function(data){
	            	
	            },
	            error:function(){
	            }
	        });
		});

		// 每次触发一个不检查
		$('body').delegate('.youzhan', 'lcs-off', function() {
			$.ajax({
	            type:"POST",
	            url:"<%=basePath %>timeswitch/youzhan?status=off",
	            dataType:"html",
	            success:function(data){
	            },
	            error:function(){
	            }
	        });
		});
	}); --%>
	//基站开关
	$(document).ready(function(e) {
		$('#jizhanswitch').lc_switch();
		$('body').delegate('.jizhan', 'lcs-on', function() {
			$.ajax({
	            type:"POST",
	            url:"<%=basePath %>timeswitch/jizhan?status=on",
	            dataType:"html",
	            success:function(data){
	            },
	            error:function(){
	            }
	        });
		});

		// triggered each time a is unchecked
		$('body').delegate('.jizhan', 'lcs-off', function() {
			$.ajax({
	            type:"POST",
	            url:"<%=basePath %>timeswitch/jizhan?status=off",
	            dataType:"html",
	            success:function(data){
	            },
	            error:function(){
	            }
	        });
		});
	});
	</script>

	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>