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
<meta http-equiv= "Expires " content= "0 "> 
<meta http-equiv= "kiben " content= "no-cache "> 
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
							<input type="hidden"  value="1" id="pagenum" />
							<input type="hidden" id="pagesize" value="15" />
							
							<input type="hidden"  name="status" value="${status }"/>
							<input type="hidden"  name="customerName" value="${customerName }"/>
							<input type="hidden"  name="orderSource" value="${orderSource }"/>
							<input type="hidden"  name="begindateForQuery" value="${begindateForQuery }"/>
							<input type="hidden"  name="enddate" value="${enddate }"/>
							<input type="hidden"  name="distributorName" value="${distributorName }"/>
							
						 	<div class="form-group">
                                   <label class="inline-label">国 家：</label><input class="form-control" name="userCountry" value="${userCountry }" id="userCountry" type="text"/>
                            </div>

							<DIV class="form-group">
								<BUTTON class="btn btn-primary" type="button"
									onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
								<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
							</DIV>
						</FORM>
					</DIV>
				</DIV>
						
					<DIV class="panel">
					<DIV class="panel-heading"><b class="">${begindateForQuery }</b>&nbsp;&nbsp;至&nbsp;&nbsp;<b>${enddate}</b>&nbsp;&nbsp;&nbsp;可用订单总数</DIV>
					<DIV class="panel-body">
						<p id="status" style="display: none;">${status}</p>
						<table id="searchTable">
							<tr>
								<th w_render="orderidOP" width="10%;">交易ID</th>
								<th w_index="SN" width="10%;">设备机身码</th>
								<th w_index="customerName" width="10%;">客户</th>
								<th w_index="userCountry" width="10%;">国家</th>
								<th w_index="orderAmount" width="5%;">总金额</th>
								<th w_index="flowDays" width="3%;">天数</th>
								<th w_index="realityUserDays" width="3%;">实际使用天数</th>
								<th w_index="ifActivate" width="3%;">是否激活</th>
								<th w_index="panlUserDate" width="10%;">预约开始时间</th>
								<th w_index="flowExpireDate" width="10%;">到期时间</th>
								<th w_index="lastUpdateDate" width="10%;">上次接入时间</th>
								<th w_index="orderStatus" width="5%;">订单状态</th>
								<!-- <th w_index="userDay" width="5%;">设备使用天数</th> -->
								<th w_index="creatorUserName" width="6%;">创建人</th>
								<th w_index="creatorDate" width="10%;">创建时间</th>
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
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Hello world!
				</div>
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
             var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>orders/flowdealorders/flowdealall',
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 autoLoad:false,
                 otherParames:$('#searchForm').serializeArray(),
                 pageSizeForGrid:[15,30,50,100],
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
          });
      	
         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID='+record.flowDealID+'">详情</a>';
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
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>