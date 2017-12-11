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
<title>出入库记录查询-设备管理-EASY2GO ADMIN</title>
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
<div id="messdele">${messagedevicedele}</div>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<!-- ============================================================ -->
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
<%-- 							<FORM class="form-inline" role="form" id="searchForm" method="get" action="<%=path%>/device/deviceinfolikePage" > --%>
							<FORM class="form-inline" role="form" id="searchForm" method="get" action="" >
								<DIV class="form-group">
									<LABEL class="inline-label">设备出入库状态：</LABEL>
									<select class="form-control" name="deviceStatus" id="deviceStatus">
									     <option value="">--所有状态--</option>
                                         <option value="出库">出库</option>
                                         <option value="入库">入库</option>
                                    </select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">机身码：</LABEL>
									<INPUT class="form-control" name="SN" id="SN" type="text" placeholder="机身码">
								</DIV>

							   <DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL>
									<INPUT name="begainTime" id="begainTime" type="text" placeholder="机身码" data-popover-offset="0,8" class="form_datetime form-control">
									 ——
									<INPUT  name="endTime" id="endTime" type="text" placeholder="机身码" data-popover-offset="0,8" class="form_datetime form-control">
								</DIV>
                                <div class="form-group">
									<label class="inline-label">&nbsp;&nbsp;&nbsp;备注：</label>
									<input class="form-control" name="remark" id="remark" type="text" placeholder="备注">
								</div>
								<DIV class="form-group">
									<BUTTON class="btn btn-primary" id="devFind"
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
										<TH w_index="SN"  width="10%"><b>机身码</b></TH>
										<TH w_index="deviceStatus"  width="10%"><b>状态</b></TH>
										<th w_index="creatorUserName"  width="10%"><b>创建人</b></th>
										<th w_index="recipientName" width="10%"><b>领用人</b></th>
										<TH w_index="shipmentDate" width="10%"><b>操作时间</b></TH>
										<TH w_index="address" width="10%"><b>地址</b></TH>
<!-- 										<TH w_index="logisticsName" width="10%"><b>快递名称</b></TH> -->
<!-- 										<TH w_index="expressNO" width="10%"><b>快递单号</b></TH> -->
										<TH w_index="chuchu" width="10%"><b>出处</b></TH>
										<TH w_index="deviceCardType" width="10%"><b>设备卡类型</b></TH>
										<TH w_index="remark" width="10%"><b>备注信息</b></TH>
<!-- 										<th w_index="creatorDate" width="10%"><b>创建时间</b></th> -->
										<TH w_render="operate" width="10%"><b>操作</b></TH>

									</TR>
								</TABLE>
							</DIV>
						</DIV>
						<a href="javascript:void(0);"  onclick="exportedShipment();"><img src="http://localhost:8090/ylcyManage/static/images/excel.jpg" width="30" height="30"><b>导出excel请点击我</b></a>
						<span>&nbsp;&nbsp;（  &nbsp;导出的excel保存地址为（D盘）：D:/ShipmentLogs20160...xls）</span>
					</DIV>
				</DIV>
				<!-- ============================================================ -->
			</SECTION>
		</SECTION>
	</section>
	<form id="testform"  method="get"></form>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script>

    function operate(record, rowIndex, colIndex, options) {
        	 return '<A class="btn btn-primary btn-xs" href="<%=path %>/device/getoutDeviceLogsEdit?shipmentID='+record.shipmentID+'">'+
             '<SPAN class="glyphicon glyphicon-edit">修改</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;';
		}

function exportedShipment(){
    var deviceStatus = document.getElementById('deviceStatus').value;
    var SN = document.getElementById('SN').value;
    var begainTime = document.getElementById('begainTime').value;
    var endTime = document.getElementById('endTime').value;
    var remark = document.getElementById('remark').value;
    remark = encodeURI(encodeURI(remark));
 	  $.ajax({
			type : "POST",
			url : "<%=basePath%>shipment/exportedShipment?deviceStatus="+deviceStatus+"&SN="+SN+"&begainTime="+begainTime+"&endTime="+endTime+"&remark="+remark,
			dataType : "html",
			success : function(data) {
			    msg = data;
				      if (data=="0")
				       {
				    	easy2go.toast('warn',"导出失败！");
					   } else{
						    easy2go.toast('info', "导出成功！");
// 							gridObj.refreshPage();
					   }
					}
				});

}
    $(".form_datetime").datetimepicker({
        format: 'YYYY-MM-DD',
        pickDate: true,     //en/disables the date picker
        pickTime: true,     //en/disables the time picker
        showToday: true,    //shows the today indicator
        language:'zh-CN',   //sets language locale
        defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
    });


function messageload(){
	var mess = document.getElementById('messdele').innerHTML;
	if(mess!=""){
		mess=mess+"";
		if(mess.length>35){
			 easy2go.toast('error', mess);
	    	 document.getElementById('messdele').innerHTML="";
		}else{
	     easy2go.toast('info', mess);
    	 document.getElementById('messdele').innerHTML="";
		}
	 }
	}
window.onload=messageload;
    var gridObj;
      $(function(){
       gridObj = $.fn.bsgrid.init('searchTable2',{
           url:'<%=basePath%>device/deviceShipmentLogs',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    $(document).keydown(function(event){
    	if(event.keyCode == 13){//绑定回车
    		$("#SN").blur();
    		gridObj.options.otherParames =$('#searchForm').serializeArray();
    		gridObj.page(1);
    		event.returnValue = false;
    	}
    	});



    function rebackDevice(index){
    	 var record= gridObj.getRecord(index);
    	 record = record.SN;
    	  $.ajax({
  			type : "POST",
  			url : "<%=basePath%>device/revertTo2?device_sn="+record,
  			dataType : "html",
  			success : function(data) {
  			    msg = data;
  				      if (data=="0")
  				       {
  				    	easy2go.toast('warn',"归还失败！");
  					   } else{
  						    easy2go.toast('info', data);
  							gridObj.refreshPage();
  					   }
  					}
  				});
    }
	</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>