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
<title>全部流量订单-订单管理-流量运营中心</title>
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
					<!-- <DIV class="panel">
						<DIV class="panel-body">

							<H3 class="panel-title"
								style="float: left; display: inline-block;">流量订单详情页</H3>
							<p style="float: left; margin-left: 100px;">*提示：设备使用天数表示该用户使用此设备
								( 机身码 ) 总天数</p>
						</DIV>
					</DIV> -->
					<DIV class="panel">
					<DIV class="panel-body" style="margin: 0px; padding: 0px;">
							<DIV class="panel-heading">
								<b>定时任务开关: <span style="color: red;" id="snCount">
								<input type="hidden" value="" id="snCountInput"></span></b>
							</DIV>
						</div>
					
						<DIV class="panel-body">
							<FORM class="form-inline" id="searchForm" role="form"
								method="get" action="#">
								<input type="hidden" value="1" id="pagenum" /><input
									type="hidden" id="pagesize" value="15" />
							</FORM>
							<p id="status" style="display: none;">${status}</p>
							<table id="searchTable">
								<tr>
									<th w_render="orderidOP" width="10%;">交易ID</th>
									<th w_index="SN" width="10%;">设备机身码</th>
									<th w_index="customerName" width="10%;">客户</th>
									<th w_index="creatorUserName" width="6%;">创建人</th>
									<th w_index="creatorDate" width="10%;">创建时间</th>
									<!-- 
								<th w_render="operate" width="30%;">操作</th> -->
								</tr>
							</table>

						</DIV>
						<br /> <label class="checkbox-items" style="padding: 0 15px;"><INPUT
							class="qb" name="all" value="1" type="checkbox">全部</label>
						<button id="b">
							<a href="javascript:void(0);">导出到 Excel</a>
						</button>
						<br />
						<br />
					</DIV>
					<%--					</DIV>--%>
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
	/*  //customerName=${customerName}&orderStatus=${orderStatus}&begindateForQuery=${begindateForQuery}&enddate=${enddate} */
	
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
          $(function(){
             var pagesize=parseInt($("#pagesize").val());
             var status = '${status}';
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>orders/devicedealorders/getdevicedealinfo',
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 autoLoad:true,
                 otherParames:{'status':status},
                 pageSizeForGrid:[15,30,50,100],
                 displayPagingToolbarOnlyMultiPages:true,
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 /* $("#pagenum").val(options.curPage);	
                		 var a = $("#searchTable_pt_pageSize").val();
                		 var sta= $("#searchTable_pt_startRow").html();
                		 var end= $("#searchTable_pt_endRow").html();
                		 var cur = $("#searchTable_pt_curPage").html();
                		 var total = $("#searchTable_pt_totalRows").html();
                		 $("#b a").attr("href","exportexecl?customerName=${customerName}&orderStatus=${orderStatus}&begindateForQuery=${begindateForQuery}&enddate=${enddate}&status=${status}&sta="+sta+"&end="+end+"&cur="+cur+"&pagesize="+pagesize+"&total="+total);
                		 $("#pagesize").val(a);
                		 $(".qb").click(function(){
          	        	   if($(this).is(':checked')){
          	        		   $("#b a").attr("href","exportexecl?customerName=${customerName}&orderStatus=${orderStatus}&begindateForQuery=${begindateForQuery}&enddate=${enddate}&status=${status}&all=1&sta="+sta+"&end="+end+"&cur="+cur+"&pagesize="+pagesize+"&total="+total);
          	        	   }
          	           }); */
          	          
                	 }
                 } 
             });
               if($("#pagenum").val()!=""){
	        	   gridObj.page($("#pagenum").val());
	           }else{
	        	   gridObj.page(1);
	           };
	           var href = $("#b a").attr("href");
	           //gridObj.options.otherParames =$('#searchForm').serializeArray();
	          
          });
         
       
         <%-- 
         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID='+record.flowDealID+'">详情</a>';
         } --%>
          
        <%--  //根据ID删除 
         function deletebyid(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确定删除吗?", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"POST",
                		 url:"<%=basePath%>orders/ordersinfo/delfloworder?flowDealID="+record.flowDealID+"&orderAmount="+record.orderAmount,
                		 dataType:'html',
                		 success:function(data){ 
                            if(data=="0"){
            					easy2go.toast('warn',"删除失败");
            				}else if(data=="-1"){
            					easy2go.toast('warn',"参数为空");
            				}else if(data=="-5"){
	                            easy2go.toast('err',"请重新登录!");
	                        } else { // 这种else为成功应该需要改善
                                easy2go.toast('info',"删除成功");
                                gridObj.refreshPage();
	                        }
                		 }
                	 });
        		 }  
        	 });
         } --%>
         
   /*       
         //刷新
        function re(){
        	$("#order_ID").val('');
        	$("#order_customerName").val('');
        	gridObj.options.otherParames =$('#searchForm').serializeArray();
        	gridObj.refreshPage();
        } */
        
       <%-- //激活，暂停,启动
       function  activate(index){
    	   var record= gridObj.getRecord(index);
    	   var urlpath="";
    	   var OP="";
      	 if(record.orderStatus=='可使用' || record.orderStatus=='使用中'){
      		OP='暂停服务';
      		urlpath='<%=basePath%>orders/flowdealorders/pause?flowOrderID='+record.flowDealID;
      	 }else if(record.orderStatus=='已暂停'){
      		OP='启动服务';
      		urlpath='<%=basePath%>orders/flowdealorders/launch?flowOrderID='+record.flowDealID;
      	 }
      	 bootbox.confirm("确定要"+OP+"吗?", function(result) {
      		 if(result){
      			 $.ajax({
              		 type:"POST",
              		 url:urlpath,
              		 dataType:'html',
              		 success:function(data){
                        if(data=="0"){
          					easy2go.toast('warn',OP+"失败");
          				}else if(data=="-1"){
          					easy2go.toast('warn',"参数为空");
          				}else if(data=="-5"){
	                        easy2go.toast('err',"请重新登录!");
	                    } else if(data=="1"){ // 这种else为成功应该需要改善
                            easy2go.toast('info',OP+"成功");                              
                            gridObj.refreshPage();
	                    }
              		 }
              	 });
      		 }  
      	 });
       }
        --%>
      <%--  //跳转到订单激活界面
       function  toactivate(index){
    	   var record= gridObj.getRecord(index);
    	   window.location="<%=basePath%>orders/ordersinfo/activate?orderID="+record.customerID+"&flowOrderID="+record.flowDealID;
       } 
       function updateSN(index){
    	   //拿到这一行的c
    	   	 var record= gridObj.getRecord(index);
    	   	 bootbox.confirm("确定更换吗?", function(result) {
	   	   		 if(result){
	   						 window.location.href="<%=basePath%>orders/flowdealorders/updateSN?flowDealID="+record.flowDealID+"&oldSN="+record.SN;
	   				}
	   		});
    	} --%>
</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>