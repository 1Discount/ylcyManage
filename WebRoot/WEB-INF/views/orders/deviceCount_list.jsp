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
<title>设备统计-流量运营中心 </title>
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
 <script>
 function distributor(){
 	document.getElementById("kucun").style.display="none";
 	document.getElementById("distributor").style.display="block";
 	document.getElementById("kucunbtn").style.backgroundColor="rgb(31, 181, 173)";
 	document.getElementById("distributorbtn").style.backgroundColor="";

 }
 function kucun(){
 	document.getElementById("distributor").style.display="none";
 	document.getElementById("kucun").style.display="block";
 	document.getElementById("distributorbtn").style.backgroundColor="rgb(31, 181, 173)";
 	document.getElementById("kucunbtn").style.backgroundColor="";
 }
 </script>
 <style>
 </style>

</head>
<body>
	<SECTION id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">

						      <div id="div1">
                                  <input type="button" id="distributorbtn" onclick="distributor();" value="渠道商" style="margin-left:0px;border:1px solid #e2e2e4;border-bottom:0px;width:49%;height:50px;"/>
                                  <input type="button" id="kucunbtn" onclick="kucun();" value="库存" style="border:1px solid #e2e2e4;width:49%;height:50px;border-bottom:0px;"/>
                                  <!--===================== 渠道商部分========================= -->
                                  <div id="distributor"　style="height:100%;border:1px solid red;">
					<div class="panel">
						<div class="panel-body">
							<form class="form-inline" id="searchForm" role="form"
								method="get" action="#">
								<div class="form-group">
									<label class="inline-label">渠道商单位名称：</label>
									<select class="form-control" name="company">
									     <option></option>
									  <c:forEach items="${distributor}" var="dis">
									     <option>${dis.company}</option>
									  </c:forEach>
									</select>
								</div>
							   <div class="form-group">
									<label class="inline-label">设备状态：</label>
									<select class="form-control" name="deviceStatus">
									     <option></option>
									     <option>使用中</option>
									     <option>不可用</option>
									     <option>可使用</option>
									</select>
								</div>
								<div class="form-group">
									<label class="inline-label">渠道商姓名：</label>
									<input class="form-control" name=operatorName type="text" placeholder="姓名">
								</div>
								<div class="form-group">
									<label class="inline-label">联系电话：</label>
									<input class="form-control" name="operatorTel" type="text" placeholder="电话">
								</div>
							    <div class="form-group">
									<label class="inline-label">设备号：</label>
									<input class="form-control" name=SN type="text" placeholder="SN">
								</div>
								<div class="form-group">
									<button class="btn btn-primary" type="button"
										onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button>
								</div>
							</form>
						</div>
					</div>

                  <div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable" class="bsgrid">
									<tr>
										<th w_index="company" width="10%;">渠道商名称</th>
										<th w_index="SN" width="10%;">机身码</th>
										<th w_render="deviceStatus1" width="10%;">设备状态</th>
										<th w_index="creatorDate" width="10%;">设备创建时间</th>
										<th w_index="creatorUserName" width="10%;">创建人</th>
										<th w_render="qdsoperate" width="10%;">操作</th>
									</tr>
								</table>
							</div>
					</div>

                                  </div><!--================ 渠道商部分    结束==== -->
                                  <!--================================== 库存部分============= -->
                                  <div id="kucun" style="display:none;height:100%;">

                                       <div class="panel">
						<div class="panel-body">
							<form class="form-inline" id="searchForm2" role="form"
								method="get" action="#">
								<div class="form-group">
									<label class="inline-label">设备状态：</label>
									<select class="form-control" name="deviceStatus">
									     <option></option>
									     <option>使用中</option>
									     <option>不可用</option>
									     <option>可使用</option>
									</select>
								</div>
							    <div class="form-group">
									<label class="inline-label">交易状态：</label>
									<select class="form-control" name="deallType">
									     <option></option>
									     <option>租用</option>
									     <option>购买</option>
									</select>
								</div>
							   <div class="form-group">
									<label class="inline-label">设备是否归还：</label>
									<select class="form-control" name="ifReturn">
									     <option></option>
<!-- 									     <option>待归还</option> -->
									     <option>是</option>
									     <option>否</option>
									</select>
								</div>
							   <div class="form-group">
									<label class="inline-label">是否出库：</label>
									<select class="form-control" name="repertoryStatus">
									     <option></option>
									     <option>出库</option>
									     <option>入库</option>
									</select>
								</div>
							<!-- 	<DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL> <INPUT
										id="order_creatorDatebegin" class="form-control form_datetime"
										name="begindateForQuery" type="text"
										data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL> <INPUT
										id="order_creatorDateend" class="form-control form_datetime"
										name="enddate" type="text"
										data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>		 -->

								<div class="form-group">
									<label class="inline-label">设备号：</label>
									<input class="form-control" name=SN type="text" placeholder="机身码">
								</div>
<!-- 								<div class="form-group"> -->
<!-- 									<label class="inline-label">订单号：</label> -->
<!-- 									<input class="form-control" name="orderID" type="text" placeholder="订单ID"> -->
<!-- 								</div> -->
								<div class="form-group">
									<button class="btn btn-primary" type="button"
										onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj2.options.otherParames = $('#searchForm2').serializeArray();gridObj2.refreshPage();">搜索</button>
								</div>
							</form>
						</div>
					</div>



                  <div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable2" class="bsgrid">
									<tr>
										<th w_index="SN" width="10%;">设备号</th>
										<th w_render="dis_deviceStatus" width="10%;">设备状态</th>
										<th w_render="zlgm" width="10%">设备交易类型</th>
										<th w_render="repertoryStatus2" width="10%">是否出库</th>
										<th w_render="ifReturn" width="10%;">是否已归还</th>
										<th w_index="remark" width="10%;">备注</th>
	                                    <th w_index="recipientName" width="10%">领用人</th>
										<th w_index="shipmentDate" width="10%;">出入库记录时间</th>
										<th w_index="remarkcrk" width="10%;">出入库备注</th>
										<th w_render="operate" width="10%;">操作</th>
									</tr>
								</table>
							</div>
					</div>


                       <span><b>订单已过期设备未归还(库存设备与渠道商无关)：</b></span>
                       <div class="panel">
                       <div class="panel-body">
							<form class="form-inline" id="searchForm3" role="form"method="get" action="#">
	                       <div class="form-group">
							  <label class="inline-label">设备号：</label>
							  <input class="form-control" id="ygqSN" name=SN type="text" placeholder="机身码">
						   </div>
						   <div class="form-group">
									<button class="btn btn-primary" type="button"
										onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj3.options.otherParames = $('#searchForm3').serializeArray();gridObj3.refreshPage();">搜索</button>
						  </div>
						  </form>
					   </div>
                       </div>
                       <div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable3" class="bsgrid">
									<tr>
										<th w_index="SN" width="10%;">设备号</th>
										<th w_index="orderID" width="10%;">订单ID</th>
										<th w_index="orderStatus" width="10%">流量订单状态</th>
										<th w_index="ifReturn" width="10%;">是否已归还</th>
										<th w_index="recipientName" width="10%">领用人</th>
										<th w_index="deviceStatus" width="10%;">设备库存状态</th>
										<th w_index="shipmentDate" width="10%;">出入库记录时间</th>
										<th w_index="remark" width="10%;">备注</th>
										<th w_render="operateygq" width="10%;">操作</th>
									</tr>
								</table>
							</div>
					    </div>
					    <a href="javascript:void(0);"  onclick="exportedgetygqSN();"><img src="http://localhost:8090/ylcyManage/static/images/excel.jpg" width="30" height="30"><b>导出excel请点击我</b></a>
						<span>&nbsp;&nbsp;（  &nbsp;导出的excel保存地址为（D盘）：D:/DeviceInfoygq2016...xls）</span>

                                  </div><!--================ 库存部分  结束===== -->
                              </div>

						</DIV>
					</DIV>

				</DIV>
			</SECTION>
		</SECTION>
	</SECTION>


	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>

	<SCRIPT type="text/javascript">
	function exportedgetygqSN(){
	    var SN = document.getElementById('ygqSN').value;
	 	  $.ajax({
				type : "POST",
				url : "<%=basePath%>devicecount/exportedgetygqSN?SN="+SN,
				dataType : "html",
				success : function(data) {
				    msg = data;
					      if (data=="0")
					       {
					    	easy2go.toast('warn',"导出失败！");
						   } else{
							    easy2go.toast('info', "导出成功！");
//	 							gridObj.refreshPage();
						   }
						}
					});

	}

	function repertoryStatus(record, rowIndex, colIndex, options){
		if(record.repertoryStatus=="出库"){
	 		   return '<span class="btn btn-danger btn-xs">'+record.repertoryStatus+'</span>';
	 	   }else{
	     	return '<span class="label label-primary label-xs">'+record.repertoryStatus+'</span>';
	 	   }
	}
	   function deviceStatus1(record, rowIndex, colIndex, options){
	 	   if(record.deviceStatus=="使用中"){
	 		   return '<span class="btn btn-danger btn-xs">'+record.deviceStatus+'</span>';
	 	   }else{
	     	return '<span class="label label-primary label-xs">'+record.deviceStatus+'</span>';
	 	   }
	     }


    function dis_deviceStatus(record, rowIndex, colIndex, options){
 	   if(record.deviceStatus=="使用中"){
 		   return '<span class="btn btn-danger btn-xs">'+record.deviceStatus+'</span>';
 	   }else{
     	return '<span class="label label-primary label-xs">'+record.deviceStatus+'</span>';
 	   }
     }

    function zlgm(record, rowIndex, colIndex, options){
    	if(record.zlgm=="购买"){
    	   return '<span class="btn btn-danger btn-xs">'+record.deallType+'</span>';
    	}else{
    	   return '<span class="label label-primary label-xs">'+record.deallType+'</span>';
    	}
    }

    function ifReturn(record, rowIndex, colIndex, options){
    	if(record.ifReturn=="是"){
     	   return '<span class="label label-primary label-xs">'+record.ifReturn+'</span>';
     	}else{
     	   return '<span class="btn btn-danger btn-xs">'+record.ifReturn+'</span>';
     	}
    }

    function repertoryStatus2(record, rowIndex, colIndex, options){
    	if(record.repertoryStatus=="入库"){
      	   return '<span class="label label-primary label-xs">'+record.repertoryStatus+'</span>';
      	}else{
      	   return '<span class="btn btn-danger btn-xs">'+record.repertoryStatus+'</span>';
      	}
    }
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>devicecount/getdislist',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    var gridObj2;
    $(function(){

        $("#order_creatorDatebegin").datetimepicker({
     		pickDate: true,                 //en/disables the date picker
     		pickTime: true,                 //en/disables the time picker
     		showToday: true,                 //shows the today indicator
     		language:'zh-CN',                  //sets language locale
     		defaultDate: moment().add(-1, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
     	});

         $("#order_creatorDateend").datetimepicker({
      		pickDate: true,                 //en/disables the date picker
      		pickTime: true,                 //en/disables the time picker
      		showToday: true,                 //shows the today indicator
      		language:'zh-CN',                  //sets language locale
      		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
      	});


       gridObj2 = $.fn.bsgrid.init('searchTable2',{
           url:'<%=basePath%>devicecount/kucunDeviceInfo',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    var gridObj3;
    $(function(){
       gridObj3 = $.fn.bsgrid.init('searchTable3',{
           url:'<%=basePath%>devicecount/getygqSN',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
    function operate(record, rowIndex, colIndex, options){
  	   return '<A class="btn btn-primary btn-xs" href="<%=basePath %>device/deviceInfodetail?deviceid='+record.SN+'">'+
        '<SPAN class="glyphicon glyphicon-edit">查看订单</SPAN></A>';
     }

    function operateygq(record, rowIndex, colIndex, options){

    			return '<A class="btn btn-primary btn-xs" href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID='+record.orderID+'">'+
<%--   	   return '<A class="btn btn-primary btn-xs" href="<%=basePath %>device/deviceInfodetail?deviceid='+record.SN+'">'+ --%>
        '<SPAN class="glyphicon glyphicon-edit">查看订单</SPAN></A>';
     }

    function qdsoperate(record, rowIndex, colIndex, options){
    	 return '<A class="btn btn-primary btn-xs" href="<%=basePath %>device/deviceInfodetail?deviceid='+record.SN+'">'+
         '<SPAN class="glyphicon glyphicon-edit">查看订单</SPAN></A>';
    }

	 document.getElementById("kucunbtn").style.backgroundColor="rgb(31, 181, 173)";

	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>