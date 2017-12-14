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
<title>无订单设备出入库管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
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
							<span>注：出库只针对没有订单的设备 出库！</span>
							<div id="div1">
								<input type="button" id="distributorbtn" onclick="distributor();" value="出库" style="margin-left: 0px; border: 1px solid #e2e2e4; border-bottom: 0px; width: 49%; height: 50px;" />
								<input type="button" id="kucunbtn" onclick="kucun();" value="入库" style="border: 1px solid #e2e2e4; width: 49%; height: 50px; border-bottom: 0px;" />
								<!--===================== 渠道商部分========================= -->
								<div id="distributor" 　style="height: 100%; border: 1px solid red;">
									<div class="panel">
										<div class="panel-body">
											<form class="form-inline" id="searchForm" role="form" method="post" enctype="multipart/form-data" action="<%=basePath%>device/excelsearch" style="display: inline-block;">
												<div class="form-group">
													<label class="inline-label">机身码：</label>
													<input class="form-control" name="SN" type="text" placeholder="设备号" style="width: 600px;">
												</div>
												<div class="form-group">
													<label class="inline-label">&nbsp;&nbsp;&nbsp;excel导入：</label>
													<input class="form-control" type="file" name="file" id="file">
												</div>
												<div class="form-group">
													<button class="btn btn-primary" type="button" onclick="serach();">搜索/出库</button>
												</div>

												<div class="form-group">
													<label class="inline-label">物流公司：</label>
													<select class="form-control" id="KDGS" name="logisticsName">
														<option value="">--请选择物流公司--</option>
														<option value="shunfeng">顺丰快递(顺丰速运)</option>
														<option value="yuantong">圆通快递</option>
														<option value="shentong">申通快递</option>
														<option value="zhongtong">中通</option>
														<option value="pingyou">中国邮政快递</option>
														<option value="ems">EMS</option>
														<option value="gnxb">邮政小包</option>
														<option value="guotong">国通快递</option>
														<option value="tiantian">天天快递</option>
														<option value="zitiziqu">自提自取</option>
													</select>
												</div>
												<DIV class="form-group">
													<lable class="inline-label" style="display: inline-block;"> <b>快递单号：</b></lable>
													<input class="form-control" name="expressNO" id="KDNO" style="display: inline-block;">
												</DIV>
												<DIV class="form-group">
													<lable class="inline-label" style="display: inline-block;"> <b>物流价格：</b></lable>
													<input class="form-control" name="logisticsCost" id="expressPrice" style="display: inline-block;">
												</DIV>
												<DIV class="form-group">
													<lable class="inline-label" style="display: inline-block;color:red;"> <b>领用人姓名：</b></lable>
													<input class="form-control" name="recipientName" id="recipientName" style="display: inline-block;">
												</DIV>
												<div class="form-group">
									                <label for="ifRoam0" class="radio-inline"> <input type="radio" name="repertoryStatus" id="ifRoam0" value="1" checked>出库</label>
									                <label for="ifRoam1" class="radio-inline">  <input type="radio" name="repertoryStatus" id="ifRoam1" value="2">待出库	</label>
								                </div>

												<DIV class="form-group">
													<lable class="inline-label" style="display: inline-block;"> <b>收货地址：</b></lable>
													<input class="form-control" name="address" id="address" style="display: inline-block; width: 500px;">
												</DIV>
												<DIV class="form-group">
													<lable class="inline-label" style="display: inline-block;"> <b>备注：</b></lable>
													<input class="form-control" name="remark" id="remark" style="display: inline-block; width: 500px;">
												</DIV>
												<button class="btn btn-primary" type="button" onclick="return devicechuku();">出库</button>
											</form>
										</div>
										<c:if test="${not empty chukuexcel}">
													<div style="border: 1px solid #e2e2e4; color: red;">
														<span>${chukuexcel}</span>
													</div>
												</c:if>
									</div>
									<div class="panel-body">
										<div class="table-responsive">
											<table id="searchTable" class="bsgrid">
												<tr>
													<th w_check="true" width="3%;"></th>
													<th w_index="SN" width="15%;">机身码</th>
													<th w_index="deviceStatus" width="15%;">状态</th>
													<th w_index="repertoryStatus" width="15%;">出入库状态</th>
													<th w_index="creatorUserName" width="15%;">创建人</th>
													<th w_index="creatorDate" width="15%;">创建时间</th>
												</tr>
											</table>
										</div>
									</div>
									<div style="color:red;">
										1.批量出库excel模板下载地址：
										<a href="http://yun.baidu.com/s/1o8fByH0" target="_blank">http://yun.baidu.com/s/1o8fByH0</a>
										2016-5-27 11:29:52 出库新增【出处、设备卡类型】字段，以上链接为最新导入出库模板！
									</div>
									<div style="color:red;">2.选择一个excel后直接点击 “搜索/出库”即可完成设备出库操作</div>
									<div style="color:red;">3.出库不支持多个设备号搜索，多个可使用模板导入出库</div>
									<div style="color:red;">4.单个设备出库，【领用人姓名】为必填项，为保证数据的可用性 请填写【领用人】 和【 备注】</div>
								</div>
								<!--================ 渠道商部分    结束==== -->
								<!--================================== 库存部分============= -->
								<div id="kucun" style="display: none; height: 100%;">
									<div class="panel">
										<div class="panel-body">
											<form class="form-inline" id="searchForm2" role="form" method="get" action="#">
												<div class="form-group" style="width:70%">
													<label class="inline-label">机身码：</label>
													<input class="form-control" name=SN id="SN" type="text" placeholder="请输入机身码后六位，多个用'/'隔开，例如：123456/123456" style="width:95%;">
												</div>
												<div class="form-group" style="width:25%">
													<button class="btn btn-primary" type="button" onclick="return rukuDevice();">输入机身码入库</button>
													<button class="btn btn-primary" type="submit" onclick="excelDevice();">导入excel入库</button>
												</div>
											</form>
										</div>
									</div>
									<div class="panel-body">
										<div class="table-responsive">
											<table id="searchTable2" class="bsgrid">
												<tr>
													<th w_check="true" width="3%;"></th>
													<th w_index="SN" width="10%;">机身码</th>
													<th w_index="deviceStatus" width="10%;">状态</th>
													<!-- <th w_index="orderID" width="10%;">订单号</th>
													<th w_index="shipmentDate" width="10%;">出货时间</th>
													<th w_index="logisticsName" width="10%;">快递公司</th>
													<th w_index="LogisticsCost" width="10%;">运费</th>
													<th w_index="expressNO" width="10%;">快递单号</th> -->
													<th w_index="remark" width="10%;">备注</th>
													<th w_index="repertoryStatus" width="10%;">出入库状态</th>
													<th w_index="creatorUserName" width="10%;">创建人</th>
													<th w_index="creatorDate" width="10%;">创建时间</th>
												</tr>
											</table>
										</div>
									</div>
									<div class="row">
										<div class="well">
											<span id="LogMessage" style="border: 0px solid pink; font-size: 14px; color: red;">${LogMessage}</span>
										</div>
									</div>
								</div>
								<!--================ 库存部分  结束===== -->
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
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<SCRIPT type="text/javascript">
	$(function(){
		var LogMessage = '${LogMessage}';
		if(LogMessage!=''){
			kucun();
		}
	});

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
    	   return '<span class="btn btn-danger btn-xs">'+record.zlgm+'</span>';
    	}else{
    	   return '<span class="label label-primary label-xs">'+record.zlgm+'</span>';
    	}
    }

    function ifReturn(record, rowIndex, colIndex, options){
    	if(record.ifReturn=="是"){
     	   return '<span class="label label-primary label-xs">'+record.ifReturn+'</span>';
     	}else{
     	   return '<span class="btn btn-danger btn-xs">'+record.ifReturn+'</span>';
     	}
    }

    var gridObj;
      $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>device/chukuDevice',
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    var gridObj2;
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


        gridObj2 = $.fn.bsgrid.init('searchTable2',{
           url:'<%=basePath%>device/rukuDevice',
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    function operate(record, rowIndex, colIndex, options){
 	   return '<A class="btn btn-primary btn-xs" href="<%=basePath%>device/deviceInfodetail?deviceid='+record.SN+'">'+
       '<SPAN class="glyphicon glyphicon-edit">查看订单</SPAN></A>';
    }

    function qdsoperate(record, rowIndex, colIndex, options){
    	 return '<A class="btn btn-primary btn-xs" href="<%=basePath%>device/deviceInfodetail?deviceid='+record.SN+'">'+
         '<SPAN class="glyphicon glyphicon-edit">查看订单</SPAN></A>';
    }

	 document.getElementById("kucunbtn").style.backgroundColor="rgb(31, 181, 173)";

	 function devicechuku(){
         var logisticsJC = $("#KDGS").val();//快递公司
    	 var expressNO = $("#KDNO").val();//快递单号
    	 var repertoryStatus = $("input[name=repertoryStatus]:checked").val();
    	 var address = document.getElementById("address").value;//地址
    	 address = encodeURI(encodeURI(address));
    	 var remark = document.getElementById('remark').value;
    	 remark = encodeURI(encodeURI(remark));
    	 var logisticsCost = document.getElementById("expressPrice").value;//运费
    	 var recipientName = document.getElementById("recipientName").value;
    	 recipientName = encodeURI(encodeURI(recipientName));
  	   	 var selectRowCount = gridObj.getCheckedRowsRecords().length;


         if(selectRowCount==0){
    	   easy2go.toast('warn',"请选择你要出库的机身码号！");
    	   return false;
       	 }else{
//     	     if(logisticsJC==''){
//   				easy2go.toast('warn',"请输入快递公司");
//   				return false;
//   			 }
//  		     if(expressNO==''){
//  				easy2go.toast('warn',"请输入快递单号");
//  				return false;
//  			 }
//  		     if(address==''){
//  		    	 easy2go.toast('warn','请输入收货地址');
//  		    	 return false;
//  		     }
if(recipientName==''){
	easy2go.toast('warn','请输入领用人姓名');
	return false;
}
 		    var SN="";
 		    for(var i=0;i<selectRowCount;i++){
 		      if(i==0){
 		    	 SN+=gridObj.getCheckedRowsRecords()[i].SN;
 		      }else{
 		    	 SN+="|"+gridObj.getCheckedRowsRecords()[i].SN;
 		      }
 		    }
           var paraStr="?SN="+SN+"&logisticsJC="+logisticsJC+"&expressNO="+expressNO+"&address="+address+"&LogisticsCost="+logisticsCost+"&recipientName="+recipientName+"&remark="+remark+"&repertoryStatus="+repertoryStatus;
 		   $.ajax({
				type : "POST",
				url : "<%=basePath%>device/getOutDevice"+paraStr,
				dataType : "html",
				success : function(data) {
				    msg = data;
					data = parseInt(data);
					        if (data == 0) {
					        	easy2go.toast('info',"出库成功！");
					        	setTimeout("window.location='<%=basePath%>device/outboundDevice'",1200);
							} else {
								easy2go.toast('warn',"出库失败！");
			                }
						}
					});
 		 }
      }
	 //导入execl入库
	 function excelDevice(){
		 bootbox.dialog({
             title: "设备入库",
             message:'<form id="import_form" class="form-horizontal" action="<%=basePath%>device/excelInDevice" method="post" enctype="multipart/form-data">'+
            	 	'<div class="form-group">'+
					'<label class="col-sm-3 control-label">选择文件：</label>'+
					'<div class="col-sm-9">'+
					'<input type="file" id="file" name="file" id="file" data-popover-offset="0,8" required class="form-control">'+
					'</div>'+
					'</div>'+
		 			'</form>'+
		 			'<span  class="col-sm-12" style="color:red;"><span  class="col-sm-3" ></span><span  class="col-sm-9">1.批量导入数据只支持 excel 2003版本,机身码请输入后六位</span></span>',
             buttons: {
                 cancel: {
                     label: "取消",
                     className: "btn-default",
                     callback: function () {
                     }
                 },
                 success: {
                     label: "入库",
                     className: "btn-success edit-button-ok",
                     callback: function () {
                    	 $("#import_form").submit();
                     }
                 }
             }
         });
	 }
	 //选择机身码入库
	 function rukuDevice(){
		 var SN = $("#SN").val();
		 if(SN==''){
			 easy2go.toast('warn','请输入机身码');
			 return;
		 }else{
			  $.ajax({
					type : "POST",
					url : "<%=basePath%>device/getInDevice?SN="+SN,
					dataType : "html",
					success : function(data) {
					    msg = data;
						data = parseInt(data);
					        if (data == 0) {
					        	easy2go.toast('info',"入库成功！");
					        	setTimeout("window.location='<%=basePath%>device/outboundDevice'", 1200);
							}
							else {
								gridObj2.refreshPage();
								$("#LogMessage").html(msg);
							}
					}
			  });
		 }
	}

	 function serach(){
		 var file = $("#file").val();
			if (file != '') {
				$("#searchForm").submit();
			}
			else {
				gridObj.options.otherParames = $('#searchForm').serializeArray();
				gridObj.refreshPage();
			}
	 }
	</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>