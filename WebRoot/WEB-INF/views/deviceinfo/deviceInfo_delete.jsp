<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 格式化时间 -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>已删设备-设备管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html" %>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
#searchTable2 tr {
	height: 36px;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<!-- ============================================================ -->
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" role="form" id="searchForm" method="get" action="<%=path%>/device/deviceinfolikePage" >
								<DIV class="form-group">
<!-- 									<LABEL class="inline-label">状态：</LABEL> -->
<!-- 									 <SELECT class="form-control" name="deviceStatus" id="deviceStatus_svalue" required="" onchange="send_deviceStatus()"> -->
<!-- 										<OPTION value="all">选择状态</OPTION> -->
<!-- 										<OPTION value="0">不可用</OPTION> -->
<!-- 										<OPTION value="1">可使用</OPTION> -->
<!-- 										<OPTION value="2">使用中</OPTION> -->
<!-- 									</SELECT> -->
									<INPUT class="form-control"   name="deviceStatus" id="deviceStatus" type="hidden" placeholder="状态">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">机身码：</LABEL> 
									<INPUT class="form-control" name="SN" id="SN" type="text" placeholder="机身码">
								</DIV>
								<DIV class="form-group">
									<BUTTON class="btn btn-primary"
									onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);" 
									type="button">搜索</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>

					<DIV class="panel">
						<DIV class="panel-heading">所有设备</DIV>
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE id="searchTable2">
									<TR>
										<TH w_render="device_num"  width="15%"><b>机身码</b></TH>
										<TH w_index="deviceDealID" w_hidden="true" width="15%"><b>机身码</b></TH>
<!-- 										<TH w_render="device_Status" w_index="deviceStatus"  width="5%"><b>状态</b></TH> -->
										<!-- <TH w_index="deviceColour"  width="5%"><b>设备颜色</b></TH> -->
<!-- 										<TH w_index="repertoryStatus" width="5%"><b>出入库状态</b></TH> -->
										<TH w_index=customerID w_hidden="true" width="7%"><b>客户ID</b></TH>
<!-- 										<TH w_render="device_customer" w_index="customerName" width="10%"><b>客户</b></TH> -->
<!-- 										<th w_render="device_type" w_index="deallType" width="5%"><b>类型</b></th> -->
										<th w_render="userName" w_index="userName" width="5%"><b>创建人</b></th>
										<th w_index="creatorDate" width="10%"><b>创建时间</b></th>
										<th w_index="modifyDate" width="10%"><b>更新时间</b></th>
<!-- 										<TH w_render="operate" width="10%"><b>操作</b></TH> -->
									</TR>
								</TABLE>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
				<!-- ============================================================ -->
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script>
 
	function send_deviceStatus(){
		var deviceStatus_index = document.getElementById("deviceStatus_svalue").selectedIndex;
        if(deviceStatus_index==1){
        	document.getElementById("deviceStatus").value="不可用";
        }else if(deviceStatus_index==2){
        	document.getElementById("deviceStatus").value="可使用";
        }else{
        	document.getElementById("deviceStatus").value="使用中";
        }
	}
	
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable2',{
           url:'<%=basePath%>device/deviceinfoPagedelete',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
 //设备sn
    function device_num(record, rowIndex, colIndex, options){
     	return '<A style="color:#1FB5AD;" href="<%=basePath %>device/deviceInfodetail?deviceid='+record.SN+'">'+record.SN+'</A>';
     } 
 //设备状态
    function device_Status(record, rowIndex, colIndex, options){
    	return '<span class="label label-primary label-xs">'+record.deviceStatus+'</span>';
    }
 
    function userName(record, rowIndex, colIndex, options){
<%--     	return '<A style="color:#1FB5AD;"href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.creatorUserID+'">'+record.creatorUserName+'</A>'; --%>
    	return '<A style="color:#1FB5AD;"">'+record.creatorUserName+'</A>';
    }

//     function device_type(record, rowIndex, colIndex, options){
//     	return '<span class="label label-primary label-xs">'+record.deallType+'</span>';
//     }

//     function device_customer(record, rowIndex, colIndex, options){
<%--     	return '<A style="color:#1FB5AD;"href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>'; --%>
//     }
    //操作
    function operate(record, rowIndex, colIndex, options) {
        return '<A class="btn btn-primary btn-xs" href="<%=path %>/device/edit?xlid='+record.SN+'">'+
            '<SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;'+
        '<A class="btn btn-primary btn-xs" href="<%=path %>/device/deletedevice?uid='+record.SN+'">'+
            '<SPAN class="glyphicon glyphicon-edit">删除</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;'
		}
	</script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>