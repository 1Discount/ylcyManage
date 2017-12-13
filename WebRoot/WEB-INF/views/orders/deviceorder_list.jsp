<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>全部设备订单-订单管理-EASY2GO ADMIN</title>
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
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" id="searchForm" role="form"
								method="get" action="#">
								<DIV style="display: none;" class="form-group">
									<LABEL class="inline-label">交易ID：</LABEL><INPUT
										class="form-control" id="order_ID" name="deviceDealID"
										type="text" placeholder="交易ID">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">机身码：</LABEL><INPUT
										class="form-control" id="order_customerName" name="SN"
										type="text" placeholder="设备机身码">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">交易类型：</LABEL> <select
										name="deallType" id="order_deallType" class="form-control">
										<option value="">全部</option>
										<option value="租用">租用</option>
										<option value="购买">购买</option>
										<option></option>
									</select>
								</DIV>
								
								<DIV class="form-group">
									<LABEL class="inline-label">渠道商：</LABEL>
									<select id="company" name="company" class="form-control" >
									<option value="">全部</option>
									<c:forEach items="${disAll}" var="item" varStatus="status">
										<option value="${item.company}">${item.company}</option>
									</c:forEach>
									</select>
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL> <INPUT
										id="order_creatorDatebegin" class="form-control form_datetime"
										name="begindate" type="text"
										data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL> <INPUT
										id="order_creatorDateend" class="form-control form_datetime"
										name="enddate" type="text"
										data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>

								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button"
										onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">所有设备订单</H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									<th w_render="orderidOP" width="10%;">交易ID</th>
									<th w_index="SN" width="10%;">机身码</th>
									<th w_index="customerName" width="10%;">客户</th>
									<th w_index="deallType" width="10%;">交易类型</th>
									<th w_index="dealAmount" width="10%;">总金额</th>
									<th w_index="ifReturn" width="10%;">是否归还</th>
									<th w_index="returnDate" width="10%;">归还日期</th>
									<th w_index="creatorUserName" width="10%;">创建人</th>
									<th w_index="creatorDate" width="10%;">创建时间</th>
									<th w_index="modifyDate" width="10%;">最后更新</th>
									<th w_render="operate" width="30%;">操作</th>
								</tr>
							</table>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>

	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- dialog body -->
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Hello world!
				</div>
				<!-- dialog buttons -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-primary">取消</button>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>orders/devicedealorders/getpage',
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true,
                 //otherParames:$('#searchForm').serializeArray(),
                 displayPagingToolbarOnlyMultiPages:true
                 
             });
             
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
          		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
          	});
         });
         
          
          //自定义列
         function operate(record, rowIndex, colIndex, options) {
             //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
              return '<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/devicedealorders/edit?deviceDealID='+record.deviceDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;<A class="btn btn-danger  btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
         }
         
         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<A  href="<%=basePath%>orders/devicedealorders/info?deviceDealID='+record.deviceDealID+'">详细</A>';
         }
          
         //根据ID删除 
         function deletebyid(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确定删除吗?", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"POST",
                		 url:"<%=basePath%> orders/ordersinfo/deldevorder?deviceDealID="
														+ record.deviceDealID
														+ "&dealAmount="
														+ record.dealAmount,
												dataType : 'html',
												success : function(data) {
													if (data == "0") {
														easy2go.toast('warn',
																"删除失败");
													} else if (data == "-1") {
														easy2go.toast('warn',
																"参数为空");
													} else if (data == "-5") {
														easy2go.toast('err',
																"请重新登录!");
													} else { // 这种else为成功应该需要改善
														easy2go.toast('info',
																"删除成功");
														gridObj.refreshPage();
													}
												}
											});
								}
							});
		}

		function re() {
			$("#order_ID").val('');
			$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
		}
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>