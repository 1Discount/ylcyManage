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
<div id="D_display" style="display:none;">
		<div style="background: #666; width: 100%; height: 100%; position:fixed; z-index: 1; opacity: 0.2;">

		</div>
		<div style=" border: 1px solid #999; width: 240px; background: #FFF; color: #000; height: 35px; padding: 0px 15px; line-height: 35px; text-align: center; opacity: 1; position: absolute; left: 50%; top: 50%; z-index: 888;">
			<img src="<%=basePath %>static/images/spinner.gif" style="float: left; margin-top: 7px; width: 20px; height: 20px;" />正在导出数据，请稍后...
		</div>
	</div>
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
									<select name="distributorName" class="form-control" id="distributorName">
										<!-- <option value="">全部</option>	 -->	
										<c:forEach items="${dis}" var="item" varStatus="status">
											<option value="${item.distributorID}">${item.company}</option>
										</c:forEach>
									</select>
								</div>
								<DIV class="form-group">
									<LABEL class="inline-label">机身码：</LABEL>
									<INPUT class="form-control" id="SN" name="SN" type="text" placeholder="设备机身码">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL>
									<INPUT id="order_creatorDatebegin" class="form-control form_datetime" name="begindateForQuery" type="text" data-date-format="YYYY-MM-DD  HH:mm:ss" onchange="begindatechante();"> 
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL>
									<INPUT id="order_creatorDateend" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>
								
								<DIV class="form-group">
									<LABEL class="inline-label">流量值：</LABEL>
									<INPUT class="form-control" id="targerFlow" name="targerFlow" type="text" placeholder="流量值">
								</DIV>
								
								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button" onclick="search()">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
									<button class="btn btn-default" type="button" onclick="save()">保存</button>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel">
							<header class="panel-heading tab-bg-dark-navy-blue">
								<ul class="nav nav-tabs nav-justified ">
									<li class="main-menu-tab active" id="youzanlistMenu" onclick="optionCardOne()"><a data-toggle="tab" href="#usered">全部订单</a></li>
									<li class="main-menu-tab" id="youzansyncMenu" onclick="optionCardTwo()"><a data-toggle="tab" href="#notuser">未使用订单</a></li>
								</ul>
							</header>
						</DIV>
						<!-- 已使用订单选项卡 -->
						<div class="tab-content">
							<div id="usered" class="tab-pane active">
								<DIV class="table-responsive">
									<table id="searchTable">
										<tr>
											<th w_render="orderidOP" width="1%;">ID</th>
											<th w_index="SN" width="2%;">设备机身码</th>
											<th w_index="customerName" width="10%;">客户</th>
											<th w_index="userCountry" width="10%;">可使用国家</th>
											<th w_index="orderAmount" width="10%;">总金额</th>
											<th w_index="flowDays" width="10%;">天数</th>
											<th w_index="drawBackDay" w_edit="text" width="3%;">退款天数</th>
											<th w_index="ifActivate" width="10%;">是否激活</th>
											<th w_index="panlUserDate" width="10%;">预约开始时间</th>
											<th w_index="flowExpireDate" width="10%;">到期时间</th>
											<th w_index="lastUpdateDate" width="10%;">上次接入时间</th>
											<th w_index="orderStatus" width="10%;">订单状态</th>
											<th w_index="creatorUserName" width="10%;">创建人</th>
											<th w_index="creatorDate" width="10%;">创建时间</th>
											<th w_index="userDays" width="10%;">已使用天数</th> 
											<th w_index="isuserCountry" width="10%;">已使用国家</th> 
											<th w_index="userPrice" width="10%;">已使用金额</th> 
											<th w_render="operate" width="1%;">操作</th> 
										</tr>
									</table>
								</DIV>
								<DIV class="panel-body">
									<a onclick="excelExport()" id="exprot" href="#">
										<img src="<%=basePath%>static/images/excel.jpg" width="30" height="30" />导出EXCEL请点我<span style="color:red;">（导出数据表示订单在各个国家的使用详情）</span>
									</a>
								</DIV>
									<div id="special-note">备注：<br><br>
									<p>1.<A class="btn btn-warning btn-xs">&nbsp;&nbsp;&nbsp;&nbsp;</A>表示国家没有单价</p>
								</div>
								
								
					
							</DIV>
							<!-- 未使用订单选项卡 -->
							<div id="notuser" class="tab-pane">
								<DIV class="table-responsive">
									<table id="searchTable1">
										<tr>
											<th w_render="opsn" width="10%;">设备机身码</th>
											<th w_index="customerName" width="10%;">客户</th>
											<th w_index="userCountry" width="10%;">国家</th>
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
		</DIV></SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
    	  var gridObj1;
          $(function(){
             $("#order_creatorDatebegin").datetimepicker({
         		pickDate: true,                
         		pickTime: true,                
         		showToday: true,                
         		language:'zh-CN',                 
         		defaultDate: "2016-04-01 00:00:00",                 
         	});
             
             $("#order_creatorDateend").datetimepicker({
          		pickDate: true,                 
          		pickTime: true,                
          		showToday: true,                 
          		language:'zh-CN',               
          		defaultDate: moment().add(0, 'days'),                 
          	});
             //已使用订单
             gridObj = $.fn.bsgrid.init('searchTable',{
                url:'<%=basePath%>orders/flowdealorders/getpage',
                pageSizeSelect: true,
				autoLoad : true,
				pageSize : 15,
				otherParames : $('#searchForm').serializeArray(),
				pageSizeForGrid : [ 15, 30, 50, 100 ],
				extend : {
	            	settings: {
	                    supportGridEdit: true, // default false, if support extend grid edit
	                    supportGridEditTriggerEvent: 'cellClick' // default 'rowClick', values: ''(means no need Trigger), 'rowClick', 'rowDoubleClick', 'cellClick', 'cellDoubleClick'
	                }
	            }
			});
             
             //未使用订单
             gridObj1 = $.fn.bsgrid.init('searchTable1',{
                url:'<%=basePath%>orders/flowdealorders/getpage?isUserd=否',
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
		
		function begindatechante(){
			var beginDate = $("#order_creatorDatebegin").val();
			
			// 获取某个时间格式的时间戳
			var timestamp2 = Date.parse(new Date(beginDate))/1000/60/60;
			var timestamp1 = Date.parse(new Date("2016-04-01  00:00:00"))/1000/60/60;
			/* if(timestamp2 < timestamp1){
				easy2go.toast('warn','你不能查询2016-04-01之前的订单');
				$("#order_creatorDatebegin").val("2016-04-01  00:00:00");
				return;
			} */
		}
		function opsn(record, rowIndex, colIndex, options) {
			return '<a onclick="userinfo(\'' + record.SN + '\')">' + record.SN + '</a>';
		}
	   function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID='+record.flowDealID+'">详情</a>';
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
        function operate(record, rowIndex, colIndex, options) {
        	return  '<a class="btn btn-primary btn-xs" href="<%=basePath%>devicelogs/flowBySnAndDateTwo?flowOrderID='+record.flowDealID+'&SN='+record.SN+'"><span class="glyphicon glyphicon-info-sign">使用详情</span></a>';
        } 
        function excelExport(){
        	$("#D_display").css({'display':'block'});
        	var distributorName=$("#distributorName  option:selected").val();
        	var SN =$("#SN").val();
        	var beginDateForQuery=$("#order_creatorDatebegin").val();
        	var endDate=$("#order_creatorDateend").val();
        	var targerFlow=$("#targerFlow").val();
   
         	<%-- var url =  '<%=basePath%>orders/flowdealorders/flowexportexeclTwoUserd?distributorName=' + distributorName+'&SN='+SN+'&begindateForQuery='+beginDateForQuery+'&enddate='+endDate+'&isUserd=是&targerFlow='+targerFlow; --%>
         	var url =  '<%=basePath%>orders/flowdealorders/flowexportexeclTwoUserd2?distributorName=' + distributorName+'&SN='+SN+'&begindateForQuery='+beginDateForQuery+'&enddate='+endDate+'&isUserd=全部&targerFlow='+targerFlow;
        	$.ajax({
	       		 type:"POST",
	       		 url:url,
	       		 dataType:"text",
	       		 success:function(data){
	       			$("#D_display").css({'display':'none'});
	       			window.location.href="<%=basePath%>orders/flowdealorders/orderUserInfodownLoad?fileName=已使用订单详情.zip";
	       		 },
	       		 error:function(data){
	       			 //alert(data.status);
	       			$("#D_display").css({'display':'none'});
	       			alert("导出失败"); 
	       		 }
       		});
        }
		//刷新
		function re() {
			$("#order_ID").val('');
			$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
		}
	
		function save(){
			var records = gridObj.getChangedRowsOldRecords();
	        var ids = '';
	        for(var i = 0; i < records.length; i++) {
	            ids += ',' + gridObj.getRecordIndexValue(records[i], 'flowDealID');
	        }
	        
	   
	        var valsStr = '';
	        $.each(gridObj.getRowsChangedColumnsValue(), function (key, object) {
	        	valsStr += '|';
	        	$.each(object, function (ckey, cvalue) {
	            	valsStr += cvalue;
	            });
	        });
	        
	        if(ids.length == 0)
	        {
	        	easy2go.toast('warn', "请先修改一条数据");
	        	return;
	        }
	        $.ajax({
               type:"POST",
              url:"<%=basePath%>orders/flowdealorders/updatedrawBackDay?flowDealID=" + (ids.length > 0 ? ids.substring(1) : '') +"&drawBackDayString=" + (valsStr.length > 0 ? valsStr.substring(1) : ''),

               success : function(data) {
                    if (data.code == 0) { // 成功保存
                        easy2go.toast('info', data.msg);
                        gridObj.refreshPage();
                    } else {
                        easy2go.toast('error', data.msg);
                        gridObj.refreshPage();
                    }
                }
            });
		}
	</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>