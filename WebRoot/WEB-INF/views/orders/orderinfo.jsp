
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
<title>发货-订单详情-订单管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<style type="text/css">
.panel .panel-body .table {
	margin-bottom: 0px;
}

.panel .panel-body .table tr td {
	padding: 8px;
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
		<SECTION id="main-content">
			<section class="wrapper">
				<div class="col-md-4">
					<div class="panel">
						<div class="panel-heading">订单详细</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<tr>
										<td style="width: 30%;"><label>ID：</label></td>
										<td>${order.orderID}</td>
									</tr>
									<tr>
										<td><label>客户名称：</label></td>
										<td><a href="#">${order.customerName }</a></td>
									</tr>
									<tr>
										<td><label>客户手机号：</label></td>
										<td>${order.customerInfo.phone }</td>
									</tr>
									<tr>
										<td><label>收货地址：</label></td>
										<td>${order.customerInfo.address }</td>
									</tr>
									<tr>
										<td><label>总额：</label></td>
										<td><span class="num">${order.orderAmount}</span></td>
									</tr>
									<tr>
										<td><label>订单状态：</label></td>
										<td>${order.orderStatus }</td>
									</tr>
									<tr>
										<td><label>订单来源：</label></td>
										<td>${order.orderSource }</td>
									</tr>
									
									

									<tr>
										<td><label>发货状态：</label></td>
										<td>${order.shipmentsStatus }</td>
									</tr>
									<tr>
										<td><label>物流信息：</label></td>
										<td>${order.logisticsInfo }</td>
									</tr>
									<tr>
										<td><label>备注：</label></td>
										<td>${order.remark }</td>
									</tr>
									<tr>
										<td><label>创建者：</label></td>
										<td>${order.creatorUserName }</td>
									</tr>
									<tr>
										<td><label>创建时间：</label></td>
										<td>${order.creatorDateString }</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">设备交易</h4>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable1" style="margin-bottom: 0px;">
									<tr>
										<th w_index="deviceDealID" width="10%;">交易ID</th>
										<th w_index="SN" width="10%;">设备SN</th>
										<th w_index="deallType" width="10%;">交易类型</th>
										<th w_index="dealAmount" width="10%;">金额</th>
										<th w_index="ifReturn" width="10%;">是否归还</th>
										<th w_index="returnDate" width="10%;">归还日期</th>
										<th w_index="creatorUserName" width="10%;">创建人</th>
										<th w_index="creatorDate" width="10%;">创建时间</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>


				<div class="col-md-12">
					<div class="panel">
						<div class="panel-body">
						<!-- 	<button type="button" class="btn btn-primary" onclick="sendsomething();">发货</button> -->
							<button type="button" class="btn btn-primary" onclick="history.go(-1)">返回</button>
							<%-- <div class="row"
								style="margin-top: 20px; margin-left: 0; margin-right: 0;">
								<div class="well">
									<span
										style="border: 0px solid pink; font-size: 14px; color: red;"
										id="logmessage">${LogMessage}</span>
								</div>
							</div> --%>
						</div>
					</div>
				</div>

						
			</section>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
   /*发货  */
  
   function sendsomething(){
	   var snString="";
	   bootbox.dialog({
           title: "发货信息",
           message:'<form id="edit_form" role="form" action="<%=basePath%>device/data/batchUpdateSN" method="post"  autocomplete="off" enctype="multipart/form-data" class="form-horizontal">'+
           '<div class="form-group"><input type="hidden" name="orderID" value="${order.orderID}" />'+
           '<label for="operatorName" class="col-md-3 control-label">物流公司:</label>'+
       '<div class="col-md-6">'+
       '<select class="form-control"'+
   		'name="logistics" id="logistics">'+
   		'<option value="">--请选择物流公司--</option>'+
   		'<c:forEach items="${dictionaries }" var="dict">'+
   		'	<option value="${dict.value}">${dict.label}</option>'+
   		'</c:forEach>'+
   '	</select>'+
      ' </div>'+
      ' <div class="col-sm-3">'+
        ' <p class="form-control-static"><span class="red"> </span>'+
        ' </p>'+
       '</div>'+
    ' </div>'+ '<div class="form-group">'+
    '<label for="operatorName" class="col-md-3 control-label">快递单号:</label>'+
    '<div class="col-md-6">'+
      '<input id="expressNO" type="text" name="expressNO" data-popover-offset="0,8"'+
     ' required maxlength="50" class="form-control">'+
   ' </div>'+
   ' <div class="col-sm-3">'+
     ' <p class="form-control-static"><span class="red">*自提自取免输入</span>'+
     ' </p>'+
    '</div>'+
 ' </div>'+
'<div class="form-group">'+
'<label class="col-sm-3 control-label">录入SN：</label>'+
'<div class="col-sm-9">'+
     ' <label for="ifboundsn0" class="radio-inline" onclick="inputSnType()">'+
     		 ' <input type="radio"  name="SNInputType" id="ifboundsn0" value="1" checked>手动输入</label> '+                 
     ' <label for="ifboundsn1" class="radio-inline"  onclick="inputSnType()">'+
    		  '<input type="radio"  name="SNInputType" id="ifboundsn1" value="2">导入excel表格<span style="color:red;">（请使用excel2003）</span></label>'+
'</div>'+
'</div>'+

'<div class="form-group" id="textInput">'+
'<label for="operatorName" class="col-md-3 control-label"></label>'+
'<div class="col-md-6">'+
'<input id="SN" type="text" name="SN" data-popover-offset="0,8"'+
' required maxlength="50"  placeholder="多个SN示例：1254/4525" class="form-control">'+
' </div>'+
' <div class="col-sm-3">'+
' <p class="form-control-static"><span class="red"> </span>'+
' </p>'+
'</div>'+
' </div>'+

'<div class="form-group" id="fileInput" style="display:none">'+
'<label class="col-md-3 control-label"></label>'+
'<div class="col-sm-6">'+
	'<input type="file" name="file" id="file" data-popover-offset="0,8" required class="form-control"  >'+
'</div>'+
'</div>'+
           '</form>',
           buttons: {
               cancel: {
                   label: "取消",
                   className: "btn-default",
                   callback: function () {}
               },
               success: {
                   label: "确认发货",
                   className: "btn-success edit-button-ok",
                   callback: function(){
                	   var logistics= $("#logistics option:selected").val();
                	   var expressNO=$("#expressNO").val();
                	   var selectRadioValue = $("input[name='SNInputType']:checked").val();
                	   var SN=$("#SN").val();
                	   var file=$("#file").val();
                	   if(logistics=='' ){
                		   easy2go.toast('warn','请选择物流公司');
            			   return fasle;
                	   }
                	   if(logistics!='zitiziqu'){
                    	   if(expressNO==''){
                    		   easy2go.toast('warn','请输入快递号');
                			   return fasle;
                    	   }
                	   }
                	   if(selectRadioValue==1){
                		   if(SN==''){
                			   easy2go.toast('warn','请输入设备序列号');
                			   return fasle;
                		   }else{
                			   var snArray = SN.split("/");
                			   for(var i=0;i<snArray.length;i++){
                				   if(i!=snArray.length-1){
                					   snString=snString+"17215021000"+snArray[i]+","; 
                				   }else{
                					   snString=snString+"17215021000"+snArray[i]; 
                				   }
                			   }
                		   }
                	   }else if(selectRadioValue==2){
                		   if(file==''){
                			   easy2go.toast('warn','请选择文件');
                			   return false;
                		   }
                	   }
                	   if(selectRadioValue==2){
                		   $("#edit_form").submit();
                	   }else if(selectRadioValue==1){
                		   // 注意在接口里处理时若为zitiziqu则不需要提供快递单号
                		   $('#logmessage').empty().append("");
                		   $.ajax({		   
                		  		 type:"POST",
                		  		 url:'<%=basePath%>orders/ordersinfo/saveSN?logistics='+logistics+'&expresssNO='+expressNO+'&SN='+snString+'&orderID=${order.orderID}&deviceDealCount=${order.deviceDealCount}',	
                		  		 dataType:'html',
                		  		 success:function(data){
                		  			 if(data=="-1"){
                		  				easy2go.toast('warn',"SN长度为15位");
                		  			 }
                		  			 else if(data=="0"){
                		  				easy2go.toast('warn',"SN个数大于设备个数");
                		  			}else if(data=="3"){
                		  				easy2go.toast('warn',"请输入SN后再发货");
                		  			}else if(data=="1"){
                		  				easy2go.toast('info',"发货成功, 正在跳转", false, null, {
                		  					onClose: function() { history.go(0); }
                		  				});	  				 
                		  			}else if(data=="4"){

                		  				easy2go.toast('warn',"sn个数不能小于设备个数");
                		  			}else if(data=="10"){
                	                    easy2go.toast('info',"有赞平台订单: 发货成功, 同时成功同步到有赞平台, 正在跳转", false, 4000, {
                	                        onClose: function() { history.go(0); }
                	                    });
                		  			}else if(data=="11"){
                	                    easy2go.toast('info',"有赞平台订单: 发货成功, 但此有赞购物车订单下仍有未发货的宝贝所以未同步物流状态到有赞, 正在跳转", false, 4000, {
                	                        onClose: function() { history.go(0); }
                	                    });
                	                }else if(data=="12"){
                	                    easy2go.toast('info',"有赞平台订单: 发货成功, 同时租用的设备订单增加了物流备注到有赞, 正在跳转", false, 4000, {
                	                        onClose: function() { history.go(0); }
                	                    });
                	                }else if(data.startWith("以下设备状态")){
                	                    $('#logmessage').empty().append("反馈信息:<br /><br />" + data);

                		  			}else{ // if(data=="-3"){ 直接返回错误信息到信息框
                		  				$('#logmessage').empty().append("反馈信息:<br /><br />" + data);
                						bootbox.alert("反馈信息:<br /><br />" + data,function(){
                							history.go(0);;
                					    });
                	                }
                		  		 }
                			   });
                	   }
                   }
               },
           }
	   });
   }
   
  
   function inputSnType(){
	   var selectRadioValue = $("input[name='SNInputType']:checked").val();
	 	if(selectRadioValue==1){
			$("#textInput").css({'display':'block'});
			$("#fileInput").css({'display':'none'});
		}else if(selectRadioValue==2){
			$("#textInput").css({'display':'none'});
			$("#fileInput").css({'display':'block'});
		} 
	} 
   var gridObjdev;
   $(function(){
	   gridObjdev = $.fn.bsgrid.init('searchTable1',{
          url:'<%=basePath%>orders/ordersinfo/orderdetails?order=${order}&ordersID=${order.orderID}',
          multiSort:true,
         /*  pageAll:true	 */
          pageSizeSelect: true,
          pageSize: 15,
          pageSizeForGrid:[15,20,30,50,100]
      });
  });
	</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="backend" />
	</jsp:include>
</body>
</html>