<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<section id="main-content">
			<section class="wrapper">
				<%-- 这里添加页面主要内容  block content --%>
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">请先选取范围</h3>
						</div>
						<div class="panel-body">
							<form class="form-inline" id="searchForm" role="form" method="get" action="#">
								<input name="flowOrderID" id="flowOrderID" type="hidden" value="${Model.flowOrderID }" />
								<div class="form-group">
									<label class="inline-label">设备机身码：</label>
									<input type="text" name="SN" id="SN" style="width: 173px;" value="${Model.SN }" placeholder="输入15位完整机身码" class="form-control" data-popover-offset="10,-105">
								</div>
								<div class="form-group">
									<label class="inline-label">选取时间段： 从</label>
									<input id="beginTime" class="form-control form_datetime" name="beginTime" id="beginTime" type="text" placeholder="开始时间" data-date-format="YYYY-MM-DD" data-popover-offset="10,-105">
								</div>

								<div class="form-group">
									<label class="inline-label">到</label>
									<input id="endTime" class="form-control form_datetime" id="endTime" name="endTime" type="text" placeholder="结束时间" data-date-format="YYYY-MM-DD" data-popover-offset="10,-105">
								</div>

								<DIV class="form-group">
									<LABEL class="inline-label">流量值：</LABEL>
									<INPUT class="form-control" id="flowCount" name="flowCount" type="text" placeholder="流量值以 M 为单位" value="10">
								</DIV>
								<div class="form-group">
									<button class="btn btn-primary" type="submit" onclick="search()">查看</button>
                                    <button class="btn btn-default" type="button" onclick="save()">保存</button>
								</div>
							</form>
						</div>
					</div>


					<div class="panel">
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable">
									<tr>
										<th w_index="DDTime" width="15%">当地日期</th>
										<th w_index="countryName" width="15%">使用国家</th>
										<th w_render="render_ifInAndHasFlow" width="15%">是否接入成功</th>
										<th w_render="render_ifUsedNormal" width="15%">是否正常使用</th>
										<th w_render="flowCount" width="15%">当日使用流量</th>
                                        <th w_index="descr" w_edit="text" width="20%">备注</th>
									</tr>
								</table>
							</div>
							<DIV class="panel-body">
								<button id="b" style="border: none; background: none;">
									<a href="#" onclick="improtexcel()">
										<img src="<%=basePath%>static/images/excel.jpg" width="30" height="30" />导出EXCEL请点我
									</a>
								</button>
							</DIV>

						</div>
						<div id="special-note" style="background: #eee; padding: 15px 0px;">
							<p style="margin-left: 20px;">备注:</p>
							<p style="margin-left: 30px;">
								1. &nbsp;&nbsp;&nbsp;
								<a class="btn btn-warning btn-xs">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
								&nbsp;&nbsp;&nbsp;表示使用流量小于输入的流量值（单位：M）,&nbsp;&nbsp;&nbsp;
								<a class="btn btn-success btn-xs">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
								&nbsp;&nbsp;&nbsp;表示使用流量大于、等于输入的流量值（单位：M）,
							</p>

							<p style="margin-left: 30px;"></p>
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
	    var totalFlowCount=0;
	    var gridObj;
	    $(function(){
			$("#beginTime").datetimepicker({
				pickDate : true,
				pickTime : false,
				showToday : true,
				language : 'zh-CN',
				//defaultDate : moment().add(-30, 'days'),
				defaultDate : '${Model.beginTime}',
			});
			$("#endTime").datetimepicker({
				pickDate : true,
				pickTime : false,
				showToday : true,
				language : 'zh-CN',
				//defaultDate : moment().add(0, 'days'), 
				defaultDate : '${Model.endTime}',
			});
			var temp;
	    	var SN = '${Model.SN}';
	    	if(SN==''){
	    		temp= false;
	    	}else{
	    		temp=true;
	    	}
	         gridObj = $.fn.bsgrid.init('searchTable',{
	             	url:'<%=basePath%>deviceflow/flowBySnAndDateGetResult',
					autoLoad : temp,
					pageAll : true,
					otherParames : $('#searchForm').serializeArray(),
					additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
						var total = $("#searchTable_no_pagination").text();
		            	 if(parseSuccess){
		            		$("#searchTable_no_pagination").text(total-1);
		            	 }
		            },
		            extend : {
		            	settings: {
		                    supportGridEdit: true, // default false, if support extend grid edit
		                    supportGridEditTriggerEvent: 'cellClick' // default 'rowClick', values: ''(means no need Trigger), 'rowClick', 'rowDoubleClick', 'cellClick', 'cellDoubleClick'
		                }
		            }
			});
	        
		});

		function search(){
			totalFlowCount=0;
			$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});
			
		}
		function render_ifIn(record, rowIndex, colIndex, options) {
			if (record.logsDate == '合计:') {
				return '';
			} else {
				if (record.ifIn == 0) {
					return '否';
				} else {
					return '是';
				}
			}
		}
		
		function improtexcel(){
			window.location.href="<%=basePath%>deviceflow/flowBySnAndDateExportexecl?"+$('#searchForm').serialize();
		}
		function render_ifInAndHasFlow(record, rowIndex, colIndex, options) {
			if (record.DDTime == '合计:') {
				return "----";
			}else {
				var result="";
				$.ajax({
		               type:"GET",
		               url:"<%=basePath%>devicelogs/gethavelogs?date="+record.DDTime+"&sn="+$("#SN").val(),
		               async: false,
		               success : function(data) {
		            	   if(data=="0")
		            		   result="否";
		            	   if(data=="1")
		            		   result="是";
		                }
		            });
				return result;
			}
		}
		function render_ifUsedNormal (record, rowIndex, colIndex, options) {
			if (record.DDTime == '合计:') {
				return "----";
			}else{
				record.flowCount=getFlowCount(record.DDTime,record.MCC,$("#SN").val(),record.SC);
				if (record.flowCount == null || record.flowCount == 0 ) {
					return "否";
				}else {
					return "是";
				}	
			}
		}
		function flowCount(record, rowIndex, colIndex, options) {
			//alert("flowCount");
			if (record.flowCount == null || record.flowCount.length == 0) {
				return "";
			}
			else {
				if (record.DDTime == '合计:') {
					return '<a class="btn btn-info btn-xs" onclick="javascript:void(0);"' + ' title="' + totalFlowCount + '"><span>' + prettyByteUnitSize(totalFlowCount, 2, 1, true) + '</span></a>';
				}else {
					record.flowCount=getFlowCount(record.DDTime,record.MCC,$("#SN").val(),record.SC);
					totalFlowCount+=Number(record.flowCount);
					var textValue = parseFloat($("#flowCount").val()) * 1024;//250880
					var flowCount = parseInt(record.flowCount);//250876
					if (flowCount < textValue) {
						return '<a class="btn btn-warning btn-xs" onclick="javascript:void(0);"' + ' title="' + record.flowCount + '"><span>' + prettyByteUnitSize(record.flowCount, 2, 1, true) + '</span></a>';
					}
					else {
						return '<a class="btn btn-success btn-xs" onclick="javascript:void(0);"' + ' title="' + record.flowCount + '"><span>' + prettyByteUnitSize(record.flowCount, 2, 1, true) + '</span></a>';
					}
				}
			}
		}
		//从日志流水账记录里获取当天的总流量
		function getFlowCount(date,mcc,sn,sc){
			  var res=0;
			  $.ajax({
	               type:"GET",
	               url:"<%=basePath%>devicelogs/getFlowCount?date="+date+"&mcc="+mcc+"&sn="+sn+"&sc="+sc,
	               async: false,
	               success : function(data) {
	                    res=data;
	                }
	            });
			  return res;
		}
		
		
		
		$("#searchForm").validate_popover({
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
				$("#flowOrderID").val("");
				gridObj.options.otherParames = $('#searchForm').serializeArray();
				gridObj.refreshPage();
				$("#b a").attr("href", "javascript:void(0);"); // 暂这样处理，没结果时不需要导出
			}
		});
		function save(){
			var records = gridObj.getChangedRowsOldRecords();
	        var ids = '';
	        for(var i = 0; i < records.length; i++) {
	            ids += ',' + gridObj.getRecordIndexValue(records[i], 'ID');
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
               url:"<%=basePath%>deviceflow/updateRemark?ID=" + (ids.length > 0 ? ids.substring(1) : '') +"&descr=" + (valsStr.length > 0 ? valsStr.substring(1) : ''),
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
	</script>

	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>