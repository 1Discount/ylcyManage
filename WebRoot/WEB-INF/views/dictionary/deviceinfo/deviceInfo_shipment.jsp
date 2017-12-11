<%@page import="com.Manage.service.OrdersInfoSer"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

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
<title>未发货订单详情</title>
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
</head>
<body>
	<section id="container"> <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" /> <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" /> <SECTION id="main-content"> <SECTION class="wrapper">
	<DIV class="col-md-12">
		<div>
			<!-- 输入机身码搜索表单 -->
			<FORM class="form-inline" id="searchForm3" role="form" method="post" enctype="multipart/form-data" action="<%=basePath%>orders/ordersinfo/excelsearch" style="display: inline-block;">
				<DIV class="form-group">
					<lable class="inline-label" style="display: inline-block;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机身码：</lable>
					<input class="form-control" value="" name="SN" id="SN" style="display: inline-block; width: 800px;">
				</DIV>
				<div class="form-group">
					<label class=" control-label" style="float: left; line-height: 40px;">表格搜索：</label>
					<div class="col-sm-9">
						<input type="file" id="file" name="file" id="file" data-popover-offset="0,8" onchange="searchNone()" class="form-control">
					</div>
				</div>
				<DIV class="form-group">
					<BUTTON class="btn btn-primary" type="button" onclick="serach()" id="search">搜索</BUTTON>
				</DIV>
			</FORM>
			<br />
			<!-- 发货表单 -->
			<FORM class="form-inline" id="searchForm2" role="form" method="get" action="#" style="display: inline-block;">
				<DIV class="form-group">
					<LABEL class="inline-label">物流公司：</LABEL>
					<select class="form-control" id="KDGS" name="logisticsName">
						<option value="">--请选择物流公司--</option>
						<option value="shunfeng">顺丰快递(顺丰速运)</option>
						<option value="yuantong">圆通快递</option>
						<option value="shentong">申通快递</option>
						<option value="zhongtong">中通</option>
						<option value="pingyou">中国邮政快递</option>
						<option value="ems">EMS</option>
						<option value="gnxb">邮政小包</option>
						<option value="guotong">国通快递</option>
						<option value="tiantian">天天快递</option>
						<option value="zitiziqu">自提自取</option>
					</select>
				</DIV>
				<DIV class="form-group">
					<lable class="inline-label" style="display: inline-block;">快递单号：</lable>
					<input class="form-control" name="expressNO" id="KDNO" style="display: inline-block;">
				</DIV>
				<DIV class="form-group">
					<lable class="inline-label" style="display: inline-block;">物流价格：</lable>
					<input class="form-control" name="logisticsCost" id="logisticsCost" style="display: inline-block;">
				</DIV>
				<DIV class="form-group">
					<lable class="inline-label" style="display: inline-block;">&nbsp;&nbsp;&nbsp;&nbsp;收货地址：</lable>
					<input class="form-control" name="address" id="address" style="display: inline-block; width: 500px;">
				</DIV>
				<button type="button" class="btn btn-primary" onclick="batchShipment()">发货</button>

			</form>
		</div>
		<!-- 第一个表格 -->
		<DIV class="panel">
			<DIV class="panel-heading">
				<H3 class="panel-title">待发货订单</H3>
			</DIV>
			<span style="color: green;"></span>
			<DIV class="panel-body">
				<table id="searchTable2">
					<tr>
						<th w_check="true" width="3%;"></th>
						<th w_render="orderidOP" width="10%;">订单ID</th>
						<th w_index="customerName" width="10%;">客户</th>
						<th w_index="SN" width="10%;">机身码</th>
						<th w_index="deviceDealCount" width="10%;">设备交易数</th>
						<th w_index="flowDealCount" width="10%;">流量交易数</th>
						<th w_render="render_orderAmount" width="10%;">总金额</th>
						<th w_index="yajin" width="10%;">押金</th>
						<th w_index="orderSource" width="10%;">来源</th>
						<th w_index="orderStatus" width="10%;">订单状态</th>
						<th w_index="pickUpWay" width="10%;">取货方式</th>
						<th w_index="shipmentsStatus" width="10%;">发货状态</th>
						<th w_index="creatorUserName" width="10%;">创建人</th>
						<th w_index="creatorDate" width="10%;">创建时间</th>
					</tr>
				</table>
			</DIV>

		</DIV>

		<DIV class="panel">
			<DIV class="panel-heading">
				<H3 class="panel-title">已发货订单</H3>
			</DIV>
			<span style="color: green;"></span>
			<DIV class="panel-body">
				<table id="searchTable">
					<tr>
						<th w_render="orderidOP" width="10%;">订单ID</th>
						<th w_index="customerName" width="10%;">客户</th>
						<th w_index="logisticsInfo" width="10%;">物流信息</th>
						<th w_index="address" width="10%;">快递地址</th>
						<th w_index="orderSource" width="10%;">来源</th>
						<th w_index="orderStatus" width="10%;">订单状态</th>
						<th w_index="shipmentsStatus" width="10%;">发货状态</th>
					</tr>
				</table>
			</DIV>
			<span style="border: 0px solid pink; font-size: 14px; color: red;" id="logmessage">${LogMessage}</span>
		</DIV>
	</DIV>
	</SECTION> </SECTION> </section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<SCRIPT type="text/javascript">
		var excelSN='${SN}';

		$(function(){

			if(excelSN!=''){

				$("#SN").val(excelSN);

			}

		});
		bootbox.setDefaults("locale","zh_CN");

		//excel表格批量发货时，提示用户是否发货成功！！！

		$(function(){

			var status= '${Status}';

			if(status=="00"){

				easy2go.toast('info','发货成功！！！');

			}else if(status=="01"){

				easy2go.toast('error','发货失败！！！');

			}else if(status=="02"){

				easy2go.toast('warn','没有搜索到要发货的订单');
			}
		});

	      var gridObj2;
    	  var gridObj;
          $(function(){
        	  //未发货
        	  gridObj2 = $.fn.bsgrid.init('searchTable2',{
		          url:'<%=basePath%>/orders/ordersinfo/ISbindSN',
		          pageSizeSelect: true,
		          pageSize: 10,
		          otherParames:$('#searchForm3').serializeArray(),
		          pageSizeForGrid:[10,20,30,50,100],
		          displayPagingToolbarOnlyMultiPages:true
		      });
        	  //已发货
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>/orders/ordersinfo/Shipped',
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 displayPagingToolbarOnlyMultiPages:true
             });

             $("#order_creatorDatebegin").datetimepicker({
         		pickDate: true,
         		pickTime: true,
         		showToday: true,
         		language:'zh-CN',
         		defaultDate: moment().add(-3, 'months'),
         	});

             $("#order_creatorDateend").datetimepicker({
          		pickDate: true,
          		pickTime: true,
          		showToday: true,
          		language:'zh-CN',
          		defaultDate: moment().add(0, 'days'),
          	});
         });
          //自定义列
         function operate(record, rowIndex, colIndex, options) {

        	  return '<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/ordersinfo/order?ordersID='+record.orderID+'"><SPAN class="glyphicon glyphicon-edit">发货</SPAN></A>';

          }
         function orderidOP(record, rowIndex, colIndex, options) {

        	 return '<A   href="<%=basePath%>orders/ordersinfo/order?ordersID='+record.orderID+'"><SPAN class="" style="color: green;">详细</SPAN></A>';

         }
         function render_orderAmount(record, rowIndex, colIndex, options) {

             return record.orderAmount;

         }
         function serach(){

        	 var file = $("#file").val();

        	 if(file!=''){

        		 $("#searchForm3").submit();

        	 }else{

        		 gridObj2.options.otherParames =$('#searchForm3').serializeArray();gridObj2.refreshPage();
        	 }
         }

         function batchShipment(){

           var KDGS = $("#KDGS").val();

      	   var KDNO = $("#KDNO").val();

      	   var logisticsCost=$("#logisticsCost").val();

    	   var selectRowCount=gridObj2.getCheckedRowsRecords().length;

    	   var orderID="";

      	   var SN="";

      	   if(selectRowCount==0){

      		  easy2go.toast('warn',"请选择你要发货的订单");

      		  return;

      	   }if(selectRowCount==1){

      		 var sendType=gridObj2.getCheckedRowsRecords()[0].pickUpWay;

      		 if(sendType=="自取"){

      			 if(KDGS!="zitiziqu"){

      				easy2go.toast('warn',"你选择的订单为自取");

      				return;

      			 }
      			 if(KDNO!='' || logisticsCost!=''){

      				easy2go.toast('warn',"自提自取请不要输入物流信息");

      				return;
      			 }

      		 }else{

      			 if(KDGS=="zitiziqu"){

      				easy2go.toast('warn',"你选择的订单发货为快递");

      				return;
      			 }
      			 if(KDGS==''){

       				easy2go.toast('warn',"请输入快递公司");

       				return;
       			 }
      			 if(KDNO==''){

      				easy2go.toast('warn',"请输入快递单号");

      				return;
      			 }
      		 }

      		 orderID=gridObj2.getCheckedRowsRecords()[0].orderID;

  	    	 SN=gridObj2.getCheckedRowsRecords()[0].SN;

      	   }else if(selectRowCount>1){

          	 var sendType=gridObj2.getCheckedRowsRecords()[0].pickUpWay;

      		 for(var i=0;i<selectRowCount;i++){

      			if(sendType!=gridObj2.getCheckedRowsRecords()[i].pickUpWay){

              		easy2go.toast('warn',"发货方式不一样，不能同进发货");

      				return;
              	 }
          		 if(i==0){

          	    	 orderID+=gridObj2.getCheckedRowsRecords()[i].orderID;

          	    	 SN+=gridObj2.getCheckedRowsRecords()[i].SN;

          	     }else{

          	    	 orderID+=","+gridObj2.getCheckedRowsRecords()[i].orderID;

          	    	 SN+="|"+gridObj2.getCheckedRowsRecords()[i].SN;
          	     }
          	 }
      		 if(sendType=="自取"){

     			 if(KDGS!="zitiziqu"){

     				easy2go.toast('warn',"你选择的订单为自取");

     				return;
     			 }
     			 if(KDNO!='' || logisticsCost!=''){

     				easy2go.toast('warn',"自提自取请不要输入物流信息");

     				return;
     			 }
     		 }else{
     			 if(KDGS=="zitiziqu"){

     				easy2go.toast('warn',"你选择的订单发货为快递");

     				return;
     			 }
     			 if(KDGS==''){

      				easy2go.toast('warn',"请输入快递公司");

      				return;
      			 }
     			 if(KDNO==''){

     				easy2go.toast('warn',"请输入快递单号");

     				return;
     			 }
     		 }
      	   }
    	    bootbox.dialog({
    	             title: "发货",
    	             message: '<p>你确定要发货吗？？？</p>',
    	             buttons: {
    	                 cancel: {
    	                     label: "取消",
    	                     className: "btn-default",
    	                     callback: function () { }
    	                 },
    	                 success: {
    	                     label: "确认发货",
    	                     className: "btn-success edit-button-ok",
    	                     callback: function () {
    	                     		$.ajax({
    	                     			type:"POST",
    	                     			url:"<%=basePath%>orders/ordersinfo/consignment?orderID=" + orderID + "&SN=" + SN,
										data : $("#searchForm2").serialize(),
										dataType : "html",
										success : function(data) {
											if (data == '00') {
												easy2go.toast('info', "发货成功，正在刷新页面", false, 2000, {
													onClose : function() {
														history.go(0);
													}
												});
											}
											else if (data = '01') {
												easy2go.toast('error', '发货失败！！！');
											}
											else if (data == "10") {
												easy2go.toast('info', "有赞平台订单: 发货成功, 同时成功同步到有赞平台, 正在跳转", false, 4000, {
													onClose : function() {
														history.go(0);
													}
												});
											}
											else if (data == "11") {
												easy2go.toast('info', "有赞平台订单: 发货成功, 但此有赞购物车订单下仍有未发货的宝贝所以未同步物流状态到有赞, 正在跳转", false, 4000, {
													onClose : function() {
														history.go(0);
													}
												});
											}
											else if (data == "12") {
												easy2go.toast('info', "有赞平台订单: 发货成功, 同时租用的设备订单增加了物流备注到有赞, 正在跳转", false, 4000, {
													onClose : function() {
														history.go(0);
													}
												});
											}
											else if (data.startWith("以下设备状态")) {
												$('#logmessage').empty().append("反馈信息:<br /><br />" + data);
											}
											else {
												$('#logmessage').empty().append("反馈信息:<br /><br />" + data);
												bootbox.alert("反馈信息:<br /><br />" + data, function() {
													history.go(0);
													;
												});
											}
										}
									});
								}
						 }
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
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>