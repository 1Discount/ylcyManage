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
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" id="searchForm" role="form"
								method="get" action="#">
									<input type="hidden" value="1" id="pagenum" />
									<input type="hidden" id="pagesize" value="10" />

								
								<!--<div class="form-group">
									<label class="inline-label">国 家：</label><input
										class="form-control" name="userCountry"  id="userCountry" type="text"/>
								</div> -->
								
								<DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL> <INPUT
										id="order_creatorDatebegin" class="form-control form_datetime"
										name="beginDate" type="text"
										data-date-format="YYYY-MM-DD">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL> <INPUT
										id="order_creatorDateend" class="form-control form_datetime"
										name="enddate" type="text"
										data-date-format="YYYY-MM-DD">
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
							<H3 class="panel-title">各国订单数量</H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									<th w_index="continent" width="10%;"> 大洲</th>
									<th w_index="countryName" width="10%;">国家</th>
									<th w_index="mcc" width="10%;" w_hidden="true">mcc</th>
									<th w_index="totalOrderCount" width="10%;">订单总数</th>
									<th w_render="totalInCountOP" width="10%;">总接入设备数</th>
									<th w_index="minCount" width="10%;">最低值</th>
									<th w_index="maxCount" width="10%;">最高值</th>
									<th w_index="averageCount" width="10%;">平均值</th>
								</tr>
							</table>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
	
    function exprotexcel(){	
   	 	window.location.href='<%=basePath %>orders/flowdealorders/QueryOrderCountByMonthexportexecl?'+$('#searchForm').serialize();
    }
    
    bootbox.setDefaults("locale","zh_CN");
	var gridObj;
    $(function(){
       $("#order_creatorDatebegin").datetimepicker({
	   		pickDate: true,                 
	   		pickTime: true,                 
	   		showToday: true,                 
	   		language:'zh-CN',                 
	   		defaultDate: moment().add(-1, 'months'),                
   	   });
       
       $("#order_creatorDateend").datetimepicker({
    		pickDate: true,                 
    		pickTime: true,               
    		showToday: true,                
    		language:'zh-CN',                 
    		defaultDate: moment().add(0, 'days'),                 
    	});

       var pagesize=parseInt($("#pagesize").val());
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>orders/flowdealorders/QueryOrderCountByMonth',
           pageSizeSelect: true,
           pageSize:pagesize,
           //autoLoad:false,
           pageAll:true,

           otherParames:$('#searchForm').serializeArray(),
           pageSizeForGrid:[1,30,50,100],
           additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
          	 if(parseSuccess){
          		 $("#pagenum").val(options.curPage);
          		 var a = $("#searchTable_pt_pageSize").val();
          		 $("#pagesize").val(a);
          	 }
           } 
       });
      if($("#pagenum").val()!=""){
   	   	gridObj.page($("#pagenum").val());
      }else{
   	   	gridObj.page(1);
      };
      var buttonHtml = '<td style="text-align: left;">';
      buttonHtml += '<table><tr>';
      buttonHtml += '<td><input type="button" value="导出数据" onclick="exprotexcel()" /></td>';
      buttonHtml += '</tr></table>';
      buttonHtml += '</td>';
      $('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);

   });
 
     function orderidOP(record, rowIndex, colIndex, options) {
    	 return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID='+record.flowDealID+'">详情</a>';
     }
      
  
     function totalInCountOP(record, rowIndex, colIndex, options){
    	 var countryName=record.countryName;
    	 var beginDate  = $("#order_creatorDatebegin").val();
    	 var endDate = $("#order_creatorDateend").val();
    	 var mcc = record.mcc;
    	 return '<a title="'+record.totalInCount+'" href="<%=basePath%>orders/flowdealorders/toflowOrderJieruInfo?mcc='+mcc+'&countryName='+countryName+'&beginDate='+beginDate+'&endDate='+endDate+'">'+record.totalInCount+'</a>';
     }
     
     
     //刷新
    function re(){
    	$("#order_ID").val('');
    	$("#order_customerName").val('');
    	gridObj.options.otherParames =$('#searchForm').serializeArray();
    	gridObj.refreshPage();
    }
 
</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="level1" />
	</jsp:include>
</body>
</html>