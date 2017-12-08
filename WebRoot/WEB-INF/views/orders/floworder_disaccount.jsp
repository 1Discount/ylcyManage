<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>全部流量订单-订单管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
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
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
								<div class="form-group">
									<label class="inline-label"> 渠道商：</label>
									<select name="company" class="form-control" id="company">
										<option value="">全部</option>
										<c:forEach items="${dis}" var="item" varStatus="status">
											<option value="${item.company}">${item.company}</option>
										</c:forEach>
									</select>
								</div>
								<DIV class="form-group">
									<LABEL class="inline-label">SN：</LABEL>
									<INPUT class="form-control" id="SN" name="SN" type="text" placeholder="设备SN">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL>
									<INPUT id="order_creatorDatebegin" class="form-control form_datetime" name="begindateForQuery" type="text" data-date-format="YYYY-MM-DD  HH:mm:ss">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL>
									<INPUT id="order_creatorDateend" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">流量值：</LABEL>
									<INPUT class="form-control" id="flowDistinction" name="flowDistinction" type="text" placeholder="流量值以 M 为单位" value="10">
								</DIV>
								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button" onclick="search()">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel">
							<header class="panel-heading tab-bg-dark-navy-blue">
								<ul class="nav nav-tabs nav-justified ">
									<li class="main-menu-tab active" id="youzanlistMenu" onclick="optionCardOne()"><a data-toggle="tab" href="#usered">已使用订单</a></li>
									<li class="main-menu-tab" id="youzansyncMenu" onclick="optionCardTwo()"><a data-toggle="tab" href="#notuser">未使用订单</a></li>
								</ul>
							</header>
						</DIV>
						<!-- 已使用订单选项卡 -->
						<div class="tab-content">
							<div id="usered" class="tab-pane active">
								<!-- <DIV class="panel-heading">
									<H3 class="panel-title">所有订单</H3>
								</DIV> -->
								<DIV class="table-responsive">
									<table id="searchTable">
										<tr>
											<!-- <th w_render="orderID" width="10%;">交易ID</th> -->
											<th w_render="opsn" width="10%;">设备SN</th>
											<th w_index="userCountry" width="10%;">已使用国家</th>
											<th w_index="userDays" width="10%;">使用天数</th>
											<th w_render="totalFlow" width="10%;">总流量</th>
											<!-- 	<th w_render="operate" width="10%;">操作</th> -->
										</tr>
									</table>
								</DIV>
								<DIV class="panel-body">
									<a onclick="excelExport()" id="exprot" href="#">
										<img src="<%=basePath%>static/images/excel.jpg" width="30" height="30" />导出EXCEL请点我<span style="color:red;">
										(导出的数据表示在输入的时间段内创建的订单在各个国家所使用的天数)</span>
									</a>
								</DIV>
								<div id="special-note" style="background: #eee; padding: 15px 0px;">
								<p style="margin-left: 20px;">备注:</p>
								<p style="margin-left: 30px;">1.使用流量小于输入的流量值时，不会统计天数</p>
								
								
						</div>
							</DIV>
							<!-- 未使用订单选项卡 -->
							<div id="notuser" class="tab-pane">
								<DIV class="table-responsive">
									<table id="searchTable1">
										<tr>
											<th w_render="opsn" width="10%;">设备SN</th>
											<th w_index="customerName" width="10%;">客户</th>
											<th w_index="userCountry" width="10%;">国家</th>
											<th w_index="orderSource" width="10%;">订单来源</th>
											<th w_index="orderAmount" width="10%;">总金额</th>
											<th w_index="flowDays" width="10%;">天数</th>
											<th w_index="ifActivate" width="10%;">是否激活</th>
											<th w_index="panlUserDate" width="10%;">预约开始时间</th>
											<th w_index="flowExpireDate" width="10%;">到期时间</th>
											<th w_index="lastUpdateDate" width="10%;">上次接入时间</th>
											<th w_index="orderStatus" width="10%;">订单状态</th>
											<th w_index="creatorUserName" width="10%;">创建人</th>
											<th w_index="creatorDate" width="10%;">创建时间</th>
										</tr>
									</table>
								</DIV>
							</DIV>
						</div>
					</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
    	  var gridObj1;
          $(function(){
             $("#order_creatorDatebegin").datetimepicker({
         		pickDate: true,                 //en/disables the date picker
         		pickTime: true,                 //en/disables the time picker
         		showToday: true,                 //shows the today indicator
         		language:'zh-CN',                  //sets language locale
         		defaultDate: moment().add(-3, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
         	});
             
             $("#order_creatorDateend").datetimepicker({
          		pickDate: true,                 //en/disables the date picker
          		pickTime: true,                 //en/disables the time picker
          		showToday: true,                 //shows the today indicator
          		language:'zh-CN',                  //sets language locale
          		defaultDate: moment().add(0, 'days'),                 
          	});
             //已使用订单
             gridObj = $.fn.bsgrid.init('searchTable',{
                url:'<%=basePath%>orders/flowdealorders/getfloworderbydis?userStatus=usered',
                pageSizeSelect: true,
				autoLoad : true,
				pageSize : 15,
				otherParames : $('#searchForm').serializeArray(),
				pageSizeForGrid : [ 15, 30, 50, 100 ],
			});
             
             //未使用订单
             gridObj1 = $.fn.bsgrid.init('searchTable1',{
                url:'<%=basePath%>orders/flowdealorders/getfloworderbydis?userStatus=notuser',
				autoLoad : true,
				pageSizeSelect: true,
				pageSize : 15,
				otherParames : $('#searchForm').serializeArray(),
				pageSizeForGrid : [ 15, 30, 50, 100 ],
			});
		});
		function totalFlow(record, rowIndex, colIndex, options) {
			var totalFlow = record.totalFlow;
			if (totalFlow >= 1024) {
				totalFlow = totalFlow / 1024 + "";
				totalFlow = totalFlow.substring(0, totalFlow.indexOf(".") + 3) + "M";
			}
			else {
				totalFlow = totalFlow + "kb";
			}
			var totalFlowTemp = $("#totalFlow").val();
			totalFlowTemp = parseInt(totalFlowTemp * 1024);
			if (record.totalFlow < totalFlowTemp) {
				return '<a class="btn btn-warning btn-xs" onclick="javascript:void(0);"' + ' title=""><span>' + totalFlow + '</span></a>';
			}
			else {
				return '<a class="btn btn-success btn-xs" onclick="javascript:void(0);"' + ' title=""><span>' + totalFlow + '</span></a>';
			}
		}
		function opsn(record, rowIndex, colIndex, options) {
			return '<a onclick="userinfo(\'' + record.SN + '\')">' + record.SN + '</a>';
		}
		function userinfo(SN) {
			var beginTime = $("#order_creatorDatebegin").val();
			var endTime = $("#order_creatorDateend").val();
			var flowDistinction = $("#flowDistinction").val();
			window.location.href = '<%=basePath%>devicelogs/flowBySnAndDate?SN='+SN+'&beginTime='+beginTime+'&endTime='+endTime+'&flowDistinction='+flowDistinction;
        }
        function search(){
        		gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);
        		gridObj1.options.otherParames =$('#searchForm').serializeArray();gridObj1.page(1);
        	
        }
        function excelExport(){
        	var totalFlowTemp=$("#flowDistinction").val();
         	totalFlowTemp=parseInt(totalFlowTemp*1024); 
        	var distributorName=$("#company  option:selected").val();;
        	var SN =$("#SN").val();
        	var lastUpdateDate="是";
        	var beginDateForQuery=$("#order_creatorDatebegin").val();
        	var endDate=$("#order_creatorDateend").val();
         	$("#exprot").attr('href','<%=basePath%>orders/flowdealorders/floworderbydisexcel?company=' + distributorName + '&lastUpdateDate=' + lastUpdateDate + '&SN=' + SN + '&beginDateForQuery=' + beginDateForQuery + '&endDate=' + endDate + '&totalFlowTemp=' + totalFlowTemp);
		}
		//刷新
		function re() {
			$("#order_ID").val('');
			$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
		}
	</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>