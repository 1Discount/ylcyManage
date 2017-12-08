<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>订单详情-订单管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html" %>
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
									<td style="width:30%;"><label>ID：</label></td>
									<td colspan="3">${order.orderID}</td>
								</tr>
								<tr>
									<td><label>客户名称：</label></td>
									<td colspan="3"><a href="#">${order.customerName }</a></td>
								</tr>
								<tr>
									<td><label>总额：</label></td>
									<td colspan="3"><span class="num"><fmt:formatNumber type="currency" value="${order.orderAmount}" currencyCode="CNY" /></span></td>
								</tr>
                                <tr>
                                    <td style="width:30%;"><label>订单状态：</label></td>
                                    <td style="width:20%;">${order.orderStatus }</td>
                                    <td style="width:30%;"><label>订单来源：</label></td>
                                    <td style="width:20%;">${order.orderSource }</td>
                                </tr>
                                <tr>
                                    <td><label>支付宝帐号：</label></td>
                                    <td colspan="3">${order.aliPayID }</td>
                                </tr>
								<tr>
									<td><label>收货地址：</label></td>
									<td colspan="3">${order.address }</td>
								</tr>
								<c:if test="${order.deviceDealCount gt 0}">
								<tr>
									<td><label>物流信息：</label></td>
									<td colspan="3">${order.logisticsInfo }</td>
								</tr>
								</c:if>
								<tr>
									<td><label>客户手机号：</label></td>
									<td colspan="3">${order.customerInfo.phone }</td>
								</tr>
								<tr>
									<td><label>备注：</label></td>
									<td colspan="3">${order.remark }</td>
								</tr>
								<tr>
									<td><label>创建者：</label></td>
									<td colspan="3">${order.creatorUserName }</td>
								</tr>
								<tr>
									<td><label>创建时间：</label></td>
									<td colspan="3">${order.creatorDateString }</td>
								</tr>
								<tr>
									<td><label>更新时间：</label></td>
									<td colspan="3">${order.modifyDateString }</td>
								</tr>
							</table>
							</div>
							<div class="section">
								<div class="btn-toolbar">
									<a href="javascript:;" onclick="javascript:history.go(-1);"
										class="btn btn-primary">返回</a>
								</div>
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
								<table id="searchTable1">
        <tr>
            <th w_index="deviceDealID"  width="10%;">交易ID</th>
            <th w_index="SN" width="10%;">设备SN</th>
            <th w_index="deallType"  width="10%;">交易类型</th>
            <th w_index="dealAmount"  width="10%;">金额</th>
            <th w_index="ifReturn"  width="10%;">是否归还</th>
            <th w_index="returnDate"  width="10%;">归还日期</th>
            <th w_index="creatorUserName"  width="10%;">创建人</th>
            <th w_index="creatorDate"  width="10%;">创建时间</th>
           <th w_render="operatedev" width="10%;">操作</th>
        </tr>
</table>
							</div>
						</div>
					</div>
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">数据服务交易</h4>
							<small>交易状态为：已使用完；过期；有效；使用中(已激活)。</small>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable2">
        <tr>
            <th w_index="flowDealID"  width="10%;">交易ID</th>
            
            <th w_index="userCountry" width="10%;">国家</th>
            <th w_index="SN" width="10%;">SN</th>
            <th w_index="flowDays" width="5%;">天数</th>
            <th w_index="orderAmount"  width="10%;">金额</th>
             <th w_index="orderStatus"  width="10%;">状态</th>
             <th w_index="ifActivate"  width="5%;">是否激活</th>
            <th w_index="activateDate"  width="10%;">激活日期</th>
            <th w_index="creatorUserName"  width="10%;">创建人</th>
            <th w_index="creatorDate"  width="10%;">创建时间</th>
            <th w_render="operateflow" width="10%;">操作</th>
        </tr>
</table>
							</div>
						</div>
					</div>
					<c:if test="${order.deviceDealCount gt 0}">
					<div class="panel"><%-- 物流信息 --%>
                        <div class="panel-heading">
                            <h4 class="panel-title">物流查询</h4>
                        </div>
                        <div class="panel-body">
                            <p id="logistics-desc">没有物流信息</p>
                            <div class="well" id="logistics-content" style="margin-bottom:0;"></div>
                        </div>
                    </div>
                    </c:if>
				</div>
			</section>
		</SECTION>
	</section>

	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	   <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
   
   /* function operate1(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       if(record.remark==""){
    	   return "";
       }
       return '<A class="btn btn-primary btn-xs"  href="#">'+record.remark+'</A>'
   } */
   
   var gridObjdev;
   var gridObjflow;
   $(function(){
	   gridObjdev = $.fn.bsgrid.init('searchTable1',{
          url:'<%=basePath %>orders/ordersinfo/getdevbyoid?ordersID=${order.orderID}',
          multiSort:true,
          pageAll:true
      });
	   
	   gridObjflow = $.fn.bsgrid.init('searchTable2',{
	          url:'<%=basePath %>orders/ordersinfo/getflowbyoid?ordersID=${order.orderID}',
	          multiSort:true,
	          pageAll:true
	      });
  });
   
   function operatedev(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
        if(record.orderStatus=='已过期'){
        	return '';
        }else{
            return '<A class="btn btn-primary btn-xs"   href="<%=basePath %>orders/devicedealorders/edit?deviceDealID='+record.deviceDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;<A class="btn btn-primary btn-xs"  onclick="deletebyid1('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';

        }
   }
   
   function operateflow(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       if(record.orderStatus=='已过期'){
    	   return '';
       }else{
           return '<A class="btn btn-primary btn-xs"   href="<%=basePath %>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;<A class="btn btn-primary btn-xs"  onclick="deletebyid2('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';

       }
   }
   
   function addsub(){
	   
	   if($("#menu_name").val()==""||  $("#menu_path").val()=="" || $("#slect_mg").val()==""){
		   return false;
	   }
			$.ajax({
				type : "POST",
				url : "<%=basePath%>orders/ordersinfo/update",
				dataType : "html",
				data : $('#order_form').serialize(),
				success : function(data) {
                    msg = data;
					data = parseInt(data);
					if (data == 1) {
						bootbox.alert("订单编辑成功",function(){
							window.location="<%=basePath%>orders/ordersinfo/list";
												});
							} else if (data == 0) {
								easy2go.toast('warn', "添加失败");
							} else if (data == -1) {
								easy2go.toast('warn', "参数为空");
							} else {
                                easy2go.toast('error', msg);
                            }
						}
					});
		}
   
   
   //根据ID删除设备订单
   function deletebyid1(index){
  	 var record= gridObjdev.getRecord(index);
  	 bootbox.confirm("确定删除吗?", function(result) {
  		 if(result){
  			 $.ajax({
          		 type:"POST",
          		 url:"<%=basePath %>orders/ordersinfo/deldevorder?deviceDealID="+record.deviceDealID+"&dealAmount="+record.dealAmount,
          		 dataType:'html',
          		 success:function(data){
                    if(data=="0"){
      					easy2go.toast('warn',"删除失败");
      				}else if(data=="-1"){
      					easy2go.toast('warn',"参数为空");
      				}else if(data=="-5"){
                        easy2go.toast('err',"请重新登录!");
      				} else { // 这种else为成功应该需要改善
                        easy2go.toast('info',"删除成功");
                        gridObjdev.refreshPage();
      				}
          		 }
          	 });
  		 }  
  	 });
   }
   //根据ID删除流量订单
   function deletebyid2(index){
  	 var record= gridObjflow.getRecord(index);
  	 bootbox.confirm("确定删除吗?", function(result) {
  		 if(result){
  			 $.ajax({
          		 type:"POST",
          		 url:"<%=basePath %>orders/ordersinfo/delfloworder?flowDealID="+record.flowDealID+"&orderAmount="+record.orderAmount,
          		 dataType:'html',
          		 success:function(data){
                    if(data=="0"){
      					easy2go.toast('warn',"删除失败");
      				}else if(data=="-1"){
      					easy2go.toast('warn',"参数为空");
      				}else if(data=="-5"){
                        easy2go.toast('err',"请重新登录!");
                    } else { // 这种else为成功应该需要改善
                        easy2go.toast('info',"删除成功");
                        gridObjdev.refreshPage();
                    }
          		 }
          	 });
  		 }  
  	 });
   }
  	 
  	 $(function(){
  		 var id = "${order.orderID}";
  		 var expressName = "${ExpressName}";
  		var expressNum = "${ExpressNum}";
  		 var tid;
  		 if(id.indexOf('YZ') == 0){
  			 if(expressName == '' || expressNum == ''){
  				 $('#logistics-desc').empty().append('目前还没有物流信息');
  				 return;
  			 }
  			$('#logistics-desc').empty().append('${order.logisticsInfo} '
                  + '<a onclick="getLogisticsTraceList();" class="btn btn-primary btn-sm">查询</a>');
  		 } else {
  			if(expressName == '' || expressNum == ''){
  				$('#logistics-desc').empty().append('没有物流信息');
  			} else {
  			   $('#logistics-desc').empty().append('目前只支持有赞平台的来源订单通过其接口查询物流的流转信息');
  			}
  		 }

  	 });

   function getLogisticsTraceList() {
       var id = "${order.orderID}";
       var expressName = "${ExpressName}";
       var expressNum = "${ExpressNum}";
       var ids = id.substring(2); // 去掉前面的YZ
       var tid = ids.split('-')[0]; // tid-oid // E20151116170153068522015-XXXXX
       
       $.ajax({
           type:"GET",
           url:"<%=basePath %>orders/youzan/logistics?name="+expressName+"&num="+expressNum+"&tid="+tid,
           dataType:'html',
           success:function(data){
        	   result = jQuery.parseJSON(data);
        	   //$('#logistics-content').removeClass('hidden');
              if(result.code=="0"){
            	  if(null == result.data || result.data == ''){
            		  $('#logistics-content').empty().append("查询到无记录");
            	  } else {
            		    $('#logistics-content').empty().append(result.data);
            	  }
              }else if(result.code=="-1"){
            	  $('#logistics-content').empty().append(result.msg);
              }else if(result.code=="-5"){
            	  $('#logistics-content').empty().append(result.msg);
              }else if(result.code=="-2"){
                  $('#logistics-content').empty().append(result.data); 
              } else {
                  $('#logistics-content').empty().append("查询出错, 请重试");                 
              }
           }
       });
       
    }
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>