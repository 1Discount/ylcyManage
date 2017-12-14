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
<title>设备流量时间段统计 - 流量运营中心</title>
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
							<h3 class="panel-title">请先选取范围</h3>
						</div>
						<div class="panel-body">
							<form class="form-inline" id="searchForm" role="form"
								method="get" action="#">
								<input name="flowOrderID" type="hidden" value="${flowOrderID }"/>
								<div class="form-group">
									<label class="inline-label">设备机身码：</label> <input type="text"
										name="SN" id="SN" style="width: 173px;"
										placeholder="输入15位完整机身码" class="form-control"
										data-popover-offset="10,-105">
								</div>
								<div class="form-group">
									<label class="inline-label">选取时间段： 从</label>
									<input id="beginTime" class="form-control form_datetime"
										name="beginTime" id="beginTime" type="text" placeholder="开始时间"
										data-date-format="YYYY-MM-DD" data-popover-offset="10,-105">
								</div>

								<div class="form-group">
									<label class="inline-label">到</label> <input id="endTime"
										class="form-control form_datetime" id="endTime" name="endTime"
										type="text" placeholder="结束时间" data-date-format="YYYY-MM-DD"
										data-popover-offset="10,-105">
								</div>

								<DIV class="form-group">
									<LABEL class="inline-label">流量值：</LABEL><INPUT
										class="form-control" id="flowDistinction" name="flowDistinction" type="text"
										placeholder="流量值以 M 为单位" value="10">
								</DIV>
								<div class="form-group">
									<button class="btn btn-primary" type="submit"
										onclick="serch()">查看</button>
								</div>
							</form>
						</div>
					</div>


					<div class="panel">
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable">
									<tr>
										<th w_index="logsDate" width="20%">日期</th>
										<th w_index="mcc" width="20%">使用国家</th>
										<th w_render="render_ifIn" width="20%">是否接入过</th>
										<th w_render="render_ifInAndHasFlow" width="20%">是否接入成功</th>
										<th w_render="flowDistinction" width="20%">当日使用流量</th>
									</tr>
								</table>
							</div>
							<DIV class="panel-body">
								<button id="b" style="border: none; background: none;">
									<a href="javascript:void(0);"  title="标黄的数据不会被导出"> <img
										src="<%=basePath %>static/images/excel.jpg"
										width="30" height="30" />导出EXCEL请点我<span style="color:red; display:none;">（&nbsp;&nbsp;&nbsp;<span class="btn btn-warning btn-xs" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;数据不会被导出）</span>
									</a>
								</button>
							</DIV>
						
						</div>
						<div id="special-note" style="background: #eee; padding: 15px 0px;">
								<p style="margin-left: 20px;">备注:</p>
								<p style="margin-left: 30px;">1. &nbsp;&nbsp;&nbsp;<a class="btn btn-warning btn-xs" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp;&nbsp;表示使用流量小于输入的流量值（单位：M）,&nbsp;&nbsp;&nbsp;<a class="btn btn-success btn-xs" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp;&nbsp;表示使用流量大于、等于输入的流量值（单位：M）,</p>
								
								<p  style="margin-left: 30px;">
								</p>
						</div>
					</div>
				</div>
			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script type="text/javascript">
    var SN;
    $(function(){
        SN = '${Model.SN}';
        var beginTime='${Model.beginTime}';
        var endTime='${Model.endTime}';
        var flowDistinction='${Model.flowDistinction}';
        if(SN!=''){
        	$("#beginTime").val(beginTime);
        	$("#endTime").val(endTime);
        	$("#SN").val(SN);
        	$("#flowDistinction").val(flowDistinction);
        }
    });

    var notin=0;
    var gridObj;
    $(function(){
    	var temp;
    	if(SN==''){
    		temp= false;
    	}else{
    		temp=true;
    	}
    	
         gridObj = $.fn.bsgrid.init('searchTable',{
        	 	url:'<%=basePath%>devicelogs/flowBySnAndDateGetResult',
				autoLoad : temp,
				pageAll : true,
				otherParames : $('#searchForm').serializeArray(),
				additionalAfterRenderGrid : function(parseSuccess, gridData,
						options) {
					if (parseSuccess) {
						
						var pagesize = $("#searchTable_no_pagination").text();
						$("#searchTable_no_pagination").html("接入成功<span style='color:red; font-size:14px;'>"	+ (pagesize - notin) + "</span>条");
						notin=0;
						var a = $("#searchTable_pt_pageSize").val();
						var sta = $("#searchTable_pt_startRow").html();
						var end = $("#searchTable_pt_endRow").html();
						var cur = $("#searchTable_pt_curPage").html();
						var total = $("#searchTable_pt_totalRows").html();
						$("#b a").attr(
								"href",
								"flowBySnAndDateExportexecl?sn="
										+ $('input[name="SN"]').val()
										+ "&begindate="
										+ $('input[name="beginTime"]').val()
										+ "&enddate="
										+ $('input[name="endTime"]').val()
										+ "&flowDistinction=0");

					} else {
						$("#b a").attr("href", "javascript:void(0);");
					}
					
				}
			});

			var href = $("#b a").attr("href");

		});

    
		$.bsgrid.forcePushPropertyInObject(
				$.fn.bsgrid.defaults.extend.renderPerRowMethods,
				'extend_render_per_row', function(record, rowIndex, trObj,
						options) {
					
					if (!record.ifInAndHasFlow) {
						notin = notin + 1;
					}
				});

		function search(){
			$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});
			
		}
		function render_ifIn(record, rowIndex, colIndex, options) {
			if (record.logsDate == '合计:') {
				return '';
			} else {
				if (record.ifIn == 0) {
					return '否';
				} else {
	<%--目前查询直接返回全部接入的记录--%>
		return '是';
				}
			}
		}
		function render_ifInAndHasFlow(record, rowIndex, colIndex, options) {
			if (record.logsDate == '合计:') {
				return '';
			} else {
				if (record.ifInAndHasFlow == 0) {
					return '<a class="btn btn-danger btn-xs" onclick="javascript:void(0);"><span>否</span></a>';
				} else {
					return '是';
				}
			}
		}
		function flowDistinction(record, rowIndex, colIndex, options) {
			if (record.flowDistinction == null
					|| record.flowDistinction.length == 0) {
				return "";
			} else {
				if (record.logsDate == '合计:') {
					return '<a class="btn btn-info btn-xs" onclick="javascript:void(0);"'
							+ ' title="'
							+ record.flowDistinction
							+ '"><span>'
							+ prettyByteUnitSize(record.flowDistinction, 2, 1,
									true) + '</span></a>';
				} else {
					var textValue =parseFloat( $("#flowDistinction").val())*1024;//250880
					
					var flowDistinction = parseInt( record.flowDistinction);//250876
					if(flowDistinction<textValue){
						return '<a class="btn btn-warning btn-xs" onclick="javascript:void(0);"'
						+ ' title="'
						+ record.flowDistinction
						+ '"><span>'
						+ prettyByteUnitSize(record.flowDistinction, 2, 1,true) + '</span></a>';
					}else{
						return '<a class="btn btn-success btn-xs" onclick="javascript:void(0);"'
						+ ' title="'
						+ record.flowDistinction
						+ '"><span>'
						+ prettyByteUnitSize(record.flowDistinction, 2, 1,true) + '</span></a>';
					}
					
				}
			}
		}
		$("#beginTime").datetimepicker({
			pickDate : true, 
			pickTime : false, 
			showToday : true, 
			language : 'zh-CN', 
			defaultDate : moment().add(-30, 'days'),
		});

		$("#endTime").datetimepicker({
			pickDate : true,
			pickTime : false, 
			showToday : true, 
			language : 'zh-CN', 
			defaultDate : moment().add(0, 'days'), 
		});

		$("#searchForm").validate_popover(
				{
					rules : {
						'SN' : {
							required : true
						},
						'beginTime' : {
							required : true
						},
						'endTime' : {
							required : true
						},
					},
					messages : {
						'SN' : {
							required : "请输入机身码"
						},
						'beginTime' : {
							required : "请选择开始时间"
						},
						'endTime' : {
							required : "请选择结束时间"
						},
					},
					submitHandler : function(form) {
						gridObj.options.otherParames = $('#searchForm')
								.serializeArray();
						gridObj.refreshPage();
						$("#b a").attr("href", "javascript:void(0);"); // 暂这样处理，没结果时不需要导出
					}
				});
	</script>

	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>