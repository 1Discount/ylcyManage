<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>渠道商详情-渠道商管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<style type="text/css">
	.disinfo p label{
		width:100px;
	}
</style>
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
				<div class="col-md-3">
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">
								<b>渠道商信息</b>
							</h3>
						</div>
						<div class="panel-body disinfo">
							<p>
								<label>单&nbsp;位&nbsp;名&nbsp;称:&nbsp;</label><span class="label label-success label-xs">${Model.company}</span>
							</p>
							<p>
								<label>sim服务器编号:&nbsp;</label><span>${Model.serverCode}</span>
							</p>
							<p>
								<label>联&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;人:&nbsp;</label><span>${Model.operatorName}</span>
							</p>
							<p>
								<label>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:&nbsp;</label><span>${Model.operatorTel}</span>
							</p>
							<p>
								<label>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:&nbsp;</label><span>${Model.operatorEmail}</span>
							</p>
							<p>
								<label>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label><span>${Model.address}</span>
							</p>
							<p>
								<label>渠道商类型:&nbsp;</label><span>${Model.type}</span>
							</p>
							<p>
								<label>结&nbsp;算&nbsp;方&nbsp;式:&nbsp;</label><span>${Model.paymentMethod}</span>
							</p>
							<p>
								<label>积&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分:&nbsp;</label><span>${Model.score}</span>
							</p>
							<p>
								<label>创&nbsp;建&nbsp;时&nbsp;间:&nbsp;</label><span>${Model.creatorDate}</span>
							</p>
							<p>
								<label>更&nbsp;新&nbsp;时&nbsp;间:&nbsp;</label><span>${Model.modifyDate}</span>
							</p>
							<p>
								<label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;</label><span>${Model.remark}<!-- TODO --></span>
							</p>
							<div class="btn-toolbar">
								<button type="button" onclick="javascript:window.location.href='<%=basePath %>customer/distributor/edit/${Model.distributorID}';"
									class="btn btn-primary">编辑</button>
								<button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-9">
					<div class="panel">
						<div class="panel-body">
							<form id="searchForm" role="form" action="#" method="get"
								class="form-inline">
								<div class="form-group">
									<label class="inline-label">机身码：</label>
										<input class="form-control" name="SN" id="SN" type="text" placeholder="SN">
										<input name="distributorID"  type="hidden" value="${Model.distributorID }">
								</div>

								<div class="form-group">
									<button class="btn btn-primary" type="button"
										onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button>
									<button type="reset" class="btn btn-default">重置</button>
								</div>
							</form>
						</div>
					</div>
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">所有设备（<span style="color:red;">直属一级渠道不提供删除设备功能，如需删除请做入库</span>）</h3>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable">
									<tr>
										<th w_index="deviceID" width="10%"><b>ID</b></th>
										<th w_index="SN" width="10%"><b>机身码</b></th>
										<th w_index="deviceColour" width="10%"><b>设备颜色</b></th>
										<th w_index="repertoryStatus" width="10%"><b>出入库状态</b></th>
										<th w_index="creatorUserName" width="10%"><b>创建人</b></th>
										<th w_index="creatorDate" width="10%"><b>创建日期</b></th>
										<th w_index="creatorDate" width="10%"><b>导入用户</b></th>
									</tr>
								</table>
							</div>
						</div>
						<DIV class="panel-body">
							<a onclick="excelExport()" id="exprot" href="#">
								<img src="<%=basePath%>static/images/excel.jpg" width="30" height="30" />导出EXCEL请点我<span style="color: red;"> </span>
							</a>
						</DIV>
					</div>
				</div>
			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script type="text/javascript">
        var gridObj;
        $(function(){
           gridObj = $.fn.bsgrid.init('searchTable',{
              url:'<%=basePath%>device/getpage',
              pageSizeSelect: true,
              pageSize: 20,
              otherParames:$('#searchForm').serializeArray(),
              pageSizeForGrid:[10,20,30,50,100],
              multiSort:true
           });
         });
        
		function persondetail(record, rowIndex, colIndex, options){
			return '<a style="color:#1FB5AD;" href="<%=basePath%>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</a>';
		}
		
		function op_del(deviceID){
        	 $.ajax({
       			type:"POST",
       			url:"<%=basePath%>device/deldistributorID?deviceID="+deviceID,
       			dataType:"html",
       			success:function(data){
       				if(data==1){
       					 easy2go.toast('info', "删除成功");
       					 gridObj.refreshPage();
       				}else{
       					 easy2go.toast('error', "删除失败");
       				}
       			}
        	});
        }
		//导出excel表格
		function excelExport(){
			var l=gridObj.getCheckedRowsRecords().length;
      	  	var deviceIDstr='';
        	  for(var i=0;i<l;i++){
        	    if(i==0){
        	    	deviceIDstr+=gridObj.getCheckedRowsRecords()[i].deviceID;
        	    }else{
        	    	deviceIDstr+=","+gridObj.getCheckedRowsRecords()[i].deviceID;
        	    }
        	  }
           $("#exprot").attr('href','<%=basePath%>device/excelExportDevice?deviceIDstr='+deviceIDstr+'&distributorID=${Model.distributorID}');
		}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>