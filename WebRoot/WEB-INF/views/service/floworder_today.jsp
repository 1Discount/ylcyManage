<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.*" %>
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
<title>今天在线流量订单-客服监控中心-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
	
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/jquery-ui.css" />

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
					<section class="panel">
						<header class="panel-heading tab-bg-dark-navy-blue">
							<ul class="nav nav-tabs nav-justified ">
								<li class="active main-menu-tab" id="notuserMenu"><a data-toggle="tab" href="#notuser">当天未使用</a></li>
								<li class="main-menu-tab" id="onlineMenu"><a data-toggle="tab" href="#online">当天使用中</a></li>
								<li class="main-menu-tab" id="availableMenu"><a data-toggle="tab" href="#available">当天可用</a></li>
							</ul>
						</header>
						<div class="panel-body">
							<div class="tab-content tasi-tab">
							<!-- 第一个选项卡 -->
								<div id="online" class="tab-pane">
									<div class="row">
										<div class="col-md-12">
										<!--中间form表单  -->
											<div class="panel">
												<div class="panel-body">
												<!-- -------------- -->
													<div class="row">
														<div class="alert alert-info clearfix">
															<span class="alert-icon"><i class="fa fa-bell-o"></i></span>
															<div class="notification-info">
																<ul class="clearfix notification-meta">
																	<li class="pull-left notification-sender">今日有接入过的订单(不包含测试单)</li>
																	<li class="pull-right notification-time">接入订单总计: <span
																		id="onlineTotalCount">0</span> 个
																	</li>
																</ul>
																<p>这里是当日成功接入过的订单，可以查看到详细的订单信息</p>
															</div>
														</div>
													</div>
													<!-- -------------- -->
													<form class="form-inline" id="searchFormOnline" role="form"
														method="get" action="#">
														<div class="form-group">
															<label class="inline-label">设备序列号：</label><input
																class="form-control" name="SN" type="text"
																placeholder="">
														</div>
														<div class="form-group">
															<label class="inline-label">客户名称：</label>
															<input class="form-control" name="customerName" type="text" placeholder="">
														</div>
														<div class="form-group">
															<button class="btn btn-primary" type="button" onclick="$('input, textarea').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObjOnline.options.otherParames = $('#searchFormOnline').serializeArray();gridObjOnline.page(1);">搜索</button>
															<button class="btn btn-default" onclick="refreshOnline();" type="button">重置刷新</button>
														</div>
													</form>
												<!-- -------------- -->
												</div>
											</div>
											
											
											<!-- 表格 -->
											<div class="panel">
												<div class="panel-body">
													<div class="table-responsive">
														<table id="searchTableOnline">
															<tr>
																<th w_render="render_flowOrderName" width="10%;">订单名称</th>
																<th w_render="render_customerName" width="10%;">客户</th>
																<th w_render="render_orderID" width="10%;">关联订单</th>
																<th w_index="orderStatus" width="5%;">订单状态</th>
																<th w_index="panlUserDate" width="10%;">预约使用时间</th>
																<th w_index="flowExpireDate" width="10%;">到期时间</th>
																<th w_index="beginDate" width="10%;">实际开始使用时间</th>
																<th w_render="render_SN" width="5%;">设备序列号</th>
																<th w_index="IMSI" width="5%;">IMSI</th>
																<th w_render="flowUsed_look" width="5%;">已使用流量</th>
															</tr>
														</table>
													</div>
												</div>
											</div>
											<!-- 下面的备注 -->
											<!-- <div id="special-note">
												备注：<br>
												<br>
												<p>1. “当天使用中”的订单系指状态为“使用中”，且最近有数据更新的订单。</p>
												<p>2.
													“当天可用”的订单系指“预约使用时间”系今天的，状态可能系可使用，或已暂停，或使用中（但今天没有数据更新）的订单。</p>
											</div> -->
											
										</div>
									</div>
								</div>
								
								<!-- 第二个选项卡 -->
							    <div id="offline" class="tab-pane ">
										<div class="row">
											<div class="col-md-12">
	
												<div class="panel">
													<div class="panel-body">
														<div class="row">
															<div class="alert alert-success ">
																<span class="alert-icon"><i class="fa fa-comments-o"></i></span>
																<div class="notification-info">
																	<ul class="clearfix notification-meta">
																		<li class="pull-left notification-sender">订单已激活,但设备下线了的流量订单</li>
																		<li class="pull-right notification-time">下线订单总计: <span id="offlineTotalCount">0</span> 个</li>
																	</ul>
																	<p>若客户电话反映出现问题, 可能在这里找到信息.</p>
																</div>
															</div>
														</div>
														<form class="form-inline" id="searchFormOffline" role="form" method="get" action="#">
															<div class="form-group">
																<label class="inline-label">设备序列号：</label>
																<input class="form-control" name="SN" type="text" placeholder="">
															</div>
															<div class="form-group">
																<label class="inline-label">客户名称：</label>
																<input class="form-control" name="customerName" type="text" placeholder="">
															</div>
															<div class="form-group">
																<button class="btn btn-primary" type="button" onclick="$('input, textarea').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObjOffline.options.otherParames = $('#searchFormOffline').serializeArray();gridObjOffline.refreshPage();">搜索</button>
																<button class="btn btn-default" onclick="refreshOffline();" type="button">重置刷新</button>
															</div>
														</form>
													</div>
												</div>
												<div class="panel">
													<div class="panel-body">
														<div class="table-responsive">
															<table id="searchTableOffline">
																<tr>
																	<th w_render="render_flowOrderName" width="10%;">订单名称</th>
																	<th w_render="render_customerName" width="10%;">客户</th>
																	<th w_render="render_orderID" width="10%;">关联订单</th>
													
																	<th w_index="orderStatus" width="5%;">订单状态</th>
																	<th w_index="panlUserDate" width="10%;">预约使用时间</th>
																<th w_index="flowExpireDate" width="10%;">到期时间</th>
																<th w_index="beginDate" width="10%;">实际开始使用时间</th>
																	<th w_render="render_SN" width="5%;">设备序列号</th>
																	<th w_index="IMSI" width="5%;">IMSI</th>
																	<th w_render="render_flowStatus" width="5%;">流量状态</th>
																	<th w_index="flowUsed" width="5%;">已使用流量</th>
																	<th w_render="operateOffline">操作</th>
																</tr>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									
									
									
									
								<!-- 第三个选项卡 -->
									 
								<div id="available" class="tab-pane ">
									<div class="row">
										<div class="col-md-12">
											<div class="panel">
												<div class="panel-body">
													<div class="row">
														<div class="alert alert-warning ">
															<span class="alert-icon"><i class="fa fa-bell-o"></i></span>
															<div class="notification-info">
																<ul class="clearfix notification-meta">
																	<li class="pull-left notification-sender">今日用户可以开机接入的订单(不包含测试单)</li>
																	<li class="pull-right notification-time">可用订单总计: <span id="availableTotalCount">0</span> 个</li>
																</ul>
																<p>当日可用流量订单包含当日使用过的流量订单和当日未使用流量订单</p>
															</div>
														</div>
													</div>
													<form class="form-inline" id="searchFormAvailable"
														role="form" method="get" action="#">
														<div class="form-group">
															<label class="inline-label">设备序列号：</label>
															<input class="form-control" name="SN" type="text" placeholder="">
														</div>
														<div class="form-group">
															<label class="inline-label">客户名称：</label>
															<input class="form-control" name="customerName" type="text" placeholder="">
														</div>
														<div class="form-group">
															<label class="inline-label">国&nbsp;家：</label> 
															<select name="userCountry" style="width: 179px;" class="form-control">
																<option value="">全部国家</option>
																<c:forEach items="${Countries}" var="country" varStatus="status">
																	<option value="${country.countryCode}">${country.countryName}</option>
																</c:forEach>
															</select>
														</div>
														<div class="form-group">
															<button class="btn btn-primary" type="button" onclick="$('input, textarea').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObjAvailable.options.otherParames = $('#searchFormAvailable').serializeArray();gridObjAvailable.refreshPage();">搜索</button>
															<button class="btn btn-default" onclick="refreshAvailable();" type="button">重置刷新</button>
														</div>
													</form>
												</div>
											</div>

											<div class="panel">
												<%--            <div class="panel-heading"><h3 class="panel-title">当天可用设备</h3></div>--%>
												<div class="panel-body">
													<div class="table-responsive">
														<table id="searchTableAvailable">
															<tr>
																<th w_render="render_flowOrderName" width="10%;">订单名称</th>
																<th w_render="render_customerName" width="10%;">客户</th>
																<th w_render="render_orderID" width="10%;">关联订单</th>
																<th w_index="orderStatus" width="5%;">订单状态</th>
																<th w_index="panlUserDate" width="10%;">预约使用时间</th>
																<th w_index="flowExpireDate" width="10%;">到期时间</th>
																<th w_index="beginDate" width="10%;">实际开始使用时间</th>
																<th w_render="render_SN" width="5%;">设备序列号</th>
																<th w_render="render_deviceDealID" width="5%;">相关设备交易</th>
																<th w_index="IMSI" width="5%;">IMSI</th>
																<th w_render="operateAvailable">操作</th>
															</tr>
														</table>
													</div>
												</div>
											</div>
											<!-- <div id="special-note">
												备注：<br>
												<br>
												<p>1. “当天使用中”的订单系指状态为“使用中”，且最近有数据更新的订单。</p>
												<p>2. “当天可用”的订单系指“预约使用时间”系今天的，状态可能系可使用，或已暂停，或使用中（但今天没有数据更新）的订单。</p>
											</div> -->
										</div>
									</div>
								</div>
								
								<!-- 第四个选项卡 -->
								<div id="notuser" class="tab-pane active">
									<div class="row">
										<div class="col-md-12">
											<div class="panel">
												<div class="panel-body">
													<div class="row">
														<div class="alert alert-success ">
															<span class="alert-icon"><i class="fa fa-comments-o"></i></span>
															<div class="notification-info">
																<ul class="clearfix notification-meta">
																	<li class="pull-left notification-sender">今日应当接入但未接入过的订单（不包含测试单）</li>
																	<li class="pull-right notification-time">未使用订单总计: <span id="notUserTotalCount">0</span> 个</li>
																</ul>
																<p>这里是显示当日应当计入的，但是 没有接入过或者接入过但未稳定过的订单</p>
															</div>
														</div>
													</div>
													<form class="form-inline" id="searchFormnotuser" role="form" method="get" action="#">
														<div class="form-group">
															<label class="inline-label">设备序列号：</label>
															<input class="form-control" name="SN" type="text" placeholder="">
														</div>
														<div class="form-group">
															<label class="inline-label">客户名称：</label>
															<input class="form-control" name="customerName" type="text" placeholder="">
														</div>
														 <DIV class="form-group"><LABEL class="inline-label">时间：</LABEL>
															<INPUT id="order_creatorDateend" class="form-control form_datetime" 
															name="enddate" type="text" 
															data-date-format="YYYY-MM-DD"></DIV>
														 <div class="form-group">
															<label class="inline-label" style="float:left; height:40px; line-height: 40px; ">国家：</label>
															<p style="float:left; position: relative;top:7px;">
																<select id="example" name="example" multiple="multiple" style="width:400px;"  class="form-control" >
																<c:forEach items="${Countries }" var="country">
																	<option value="${country.countryCode }">${country.countryName }</option>
																</c:forEach>
																
																</select>
																<input type="hidden" id="hfexample" />
															</p>
														</div> 
														<div class="form-group">
															<button class="btn btn-primary" type="button" onclick="$('input, textarea').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObjnotuser.options.otherParames = $('#searchFormnotuser').serializeArray();gridObjnotuser.page(1);">搜索</button>
															<button class="btn btn-default" onclick="refreshnotuser();" type="button">重置刷新</button>
														</div>
													</form>
												</div>
											</div>
											<div class="panel">
												<div class="panel-body">
													<div class="table-responsive">
														<table id="searchTablenotuser">
															<tr>
																<th w_render="render_flowOrderName" width="10%;">订单名称</th>
																<th w_render="render_customerName" width="10%;">客户</th>
																<th w_render="render_orderID" width="10%;">关联订单</th>
																<th w_index="userCountry" width="10%;">使用国家</th>
																
																<th w_index="orderStatus" width="5%;">订单状态</th>
																<th w_index="panlUserDate" width="10%;">预约使用时间</th>
																<th w_index="flowExpireDate" width="10%;">到期时间</th>
																<th w_index="beginDate" width="10%;">实际开始使用时间</th>
																<th w_render="render_SN" width="5%;">设备序列号</th>
																<th w_index="IMSI" width="5%;">IMSI</th>
																<th w_render="render_flowStatus" width="5%;">流量状态</th> 
																<th w_index="flowUsed" width="5%;">已使用流量</th>
																<!-- <th w_render="operateOffline">操作</th> -->
															</tr>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!--  -->
	<%-- 						<h1>
	 <!--刷新后，multiselect 还可以显示之前的选中项-->
	 类型：<select id="Category" name="Category" multiple="multiple" style="width: 100px">
                                      <option value="0" <% if(("searchCategoryID").IndexOf(",0,")>-1) { %> selected="selected" <%} %> >请选择类型</option>
                                      <option value="1" <% if(("searchCategoryID").IndexOf(",1,")>-1) { %> selected="selected" <%} %> >1</option>
                                      <option value="2" <% if(("searchCategoryID").IndexOf(",2,")>-1) { %> selected="selected" <%} %> >2</option>
                                     </select>
                     <asp:HiddenField ID="hfCategory" runat="server" /> 
	</h1> --%>
								
								
								
							</div>
						</div>
					</section>
				</div>
			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.multiselect.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
 		<script>
	$(function(){
	 	  $("#example").multiselect({
	 	  	    header: false,
	 	  	    height: 200,
	 	  	    minWidth: 200,
	 	  		selectedList: 10,//预设值最多显示10被选中项
	 	  	    hide: ["explode", 500],
	 	  	    noneSelectedText: '国家',
	 	  	    close: function(){
	 	  	      var values= $("#example").val();
	 	  		  $("#hfexample").val(values);
	 	  		}
	 	   });
}); 
</script>
	
	<script>
    bootbox.setDefaults("locale","zh_CN");
    var gridObjnotuser;
    $(function(){
    	/*var countryName=$("#example").val();
    	 var dateTime=$("#order_creatorDateend").val();
    	 alert(countryName+":::"+dateTime); */
    	gridObjnotuser = $.fn.bsgrid.init('searchTablenotuser',{
              url:'<%=basePath%>service/center/pageFlowOrder?range=today&status=notuser',
              //pageAll:true,
              //autoLoad: false,
              pageSizeSelect: true,
              pageSize: 20,
              pageSizeForGrid:[10,20,30,50,100],
              multiSort:true,
              additionalAfterRenderGrid : function(parseSuccess, gridData, options) {
                  if (parseSuccess) {
                      $("#notUserTotalCount").empty().append(options.totalRows);
                  }
              },
              additionalBeforeRenderGrid : function(parseSuccess, gridData, options) {
                      $("#notUserTotalCount").empty().append("0");
              }
         });
    	 $("#order_creatorDateend").datetimepicker({
       		pickDate: true,                 //en/disables the date picker
       		pickTime: true,                 //en/disables the time picker
       		showToday: true,                 //shows the today indicator
       		language:'zh-CN',                  //sets language locale
       		defaultDate: moment().add(-0, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
       	});
    	 
     });

    var gridObjOnline;
     $(function(){
         gridObjOnline = $.fn.bsgrid.init('searchTableOnline',{
        	 url:'<%=basePath%>service/center/pageFlowOrder?range=today&status=online',
             // autoLoad: false,
             //pageAll:true,
             pageSizeSelect: true,
             pageSize: 20,
             pageSizeForGrid:[10,20,30,50,100],
             multiSort:true,
             additionalAfterRenderGrid : function(parseSuccess, gridData, options) {
            	 if (parseSuccess) {
                     $("#onlineTotalCount").empty().append(options.totalRows);
                 }
             }
         });
  
         });
	<%--var gridObjOffline;
    $(function(){
         gridObjOffline = $.fn.bsgrid.init('searchTableOffline',{
             url:'<%=basePath%>service/center/pageFlowOrder?range=today&status=offline',
             autoLoad: false,
             //pageAll:true,
             pageSizeSelect: true,
             pageSize: 20,
             pageSizeForGrid:[10,20,30,50,100],
             multiSort:true,
             additionalAfterRenderGrid : function(parseSuccess, gridData, options) {
                 if (parseSuccess) {
                     $("#offlineTotalCount").empty().append(options.totalRows);
                 }
             }
         });
    }); --%>
    var gridObjAvailable;
    $(function(){
    	   gridObjAvailable = $.fn.bsgrid.init('searchTableAvailable',{
              url:'<%=basePath%>service/center/pageFlowOrder?range=today&status=available',
              autoLoad: false,
              //pageAll:true,
              pageSizeSelect: true,
              pageSize: 20,
              pageSizeForGrid:[10,20,30,50,100],
              multiSort:true,
              additionalAfterRenderGrid : function(parseSuccess, gridData, options) {
                  if (parseSuccess) {
                      $("#availableTotalCount").empty().append(options.totalRows);
                  }
              }
            });
     });
    
 

    //自定义列
    function render_flowOrderName(record, rowIndex, colIndex, options) {
    	var appendString;
    	if (null != record.flowPlanID && record.flowPlanID.length > 0) {
    		// 若不是自定义套餐, 最好显示原套餐名称
    		appendString = "";
    	} else {
    		appendString = ""; // ' <a class="btn btn-success btn-xs" onclick="return;"><span>自定义</span></a>';
    	}
        return '<a title="查看订单详情" href="<%=basePath%>orders/flowdealorders/info?flowDealID=' + record.flowDealID + '"><span>' + record.userCountry + record.flowDays + '日套餐' + '</span>' + appendString + '</a>';
     }
    function render_SN(record, rowIndex, colIndex, options) {
        return '<a title="查看设备详情" href="<%=basePath%>device/deviceInfodetail?deviceid=' + record.SN + '"><span>' + record.SN + '</span></a>';
    }
    function render_customerName(record, rowIndex, colIndex, options) {
        return '<a title="查看用户详情" href="<%=basePath%>customer/customerInfolist/customerInfoDetail?cusid=' + record.customerID + '"><span>' + record.customerName + '</span></a>';
     }
    function render_flowStatus(record, rowIndex, colIndex, options) {
    	return "正常";
    }
    function render_orderID(record, rowIndex, colIndex, options) {
        return '<a href="<%=basePath%>orders/ordersinfo/orderinfo?ordersID=' + record.orderID + '"><span>查看</span></a>';
    }
    function render_deviceDealID(record, rowIndex, colIndex, options) {
    	if (record.deviceDealID != null && record.deviceDealID.length > 0) {
            return '<a href="<%=basePath%>orders/devicedealorders/info?deviceDealID=' + record.deviceDealID + '"><span>查看</span></a>';
        } else {
            return '';
        }
    }
    function render_limitSpeed(record, rowIndex, colIndex, options) {
        if (record.limitSpeed == 0) {
            return '<a class="btn btn-success btn-xs" onclick="return;"><span>不限速</span></a>';
        } else {
            return '<a class="btn btn-danger btn-xs" onclick="return;"><span>限速' + record.limitSpeed + 'KB</span></a>';
        }
     }
    
    // 注意链接中把表格对象传递过去, 共用 activate() 函数. 这样做的原因系未知 operateOnline 的参数是否必须保持一致.
    function operateOnline(record, rowIndex, colIndex, options) {
        //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
        return '<a title="查看订单详情" class="btn btn-primary btn-xs" href="<%=basePath%>orders/flowdealorders/info?flowDealID=' + record.flowDealID + '" ><span class="glyphicon glyphicon-info-sign">查看</span></a>';
    }
    // 注意链接中把表格对象传递过去, 共用 activate() 函数.
    function operateOffline(record, rowIndex, colIndex, options) {
        //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
        var part = '';
         if(record.ifActivate=='否' || record.ifActivate==''){
        	 part = '<a class="btn btn-primary btn-xs" onclick="activate('+rowIndex+', gridObjOffline)" ><span class="glyphicon glyphicon-brightness-reduce">激活</span></a>';
         }else if(record.orderStatus=='使用中'){
        	 part =''
             + '<a class="btn btn-primary btn-xs" onclick="activate('+rowIndex+', gridObjOffline)" ><span class="glyphicon glyphicon-stop">暂停</span></a>';
         }else if(record.orderStatus=='已暂停'){
        	 part = ''
             + '<a class="btn btn-primary btn-xs" onclick="activate('+rowIndex+', gridObjOffline)" ><span class="glyphicon glyphicon-play">启动</span></a>';
         }         
         return part + ' <a title="查看订单详情" class="btn btn-primary btn-xs" href="<%=basePath%>orders/flowdealorders/info?flowDealID=' + record.flowDealID + '" ><span class="glyphicon glyphicon-info-sign">查看</span></a>';
    }
    // 注意链接中把表格对象传递过去, 共用 activate() 函数.
    function operateAvailable(record, rowIndex, colIndex, options) {
        //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
        var part = '';
         if(record.ifActivate=='否' || record.ifActivate==''){
        	 part = '<a class="btn btn-primary btn-xs" onclick="activate('+rowIndex+', gridObjAvailable)" ><span class="glyphicon glyphicon-brightness-reduce">激活</span></a>';
         }else if(record.orderStatus=='可使用' || record.orderStatus=='使用中'){
        	 part = ''
             + '<a class="btn btn-primary btn-xs" onclick="activate('+rowIndex+', gridObjAvailable)" ><span class="glyphicon glyphicon-stop">暂停</span></a>';
         }else if(record.orderStatus=='已暂停'){
        	 part = ''
             + '<a class="btn btn-primary btn-xs" onclick="activate('+rowIndex+', gridObjAvailable)" ><span class="glyphicon glyphicon-play">启动</span></a>';
         }
         return part; // + ' <a title="查看订单详情" class="btn btn-primary btn-xs" href="<%=basePath%>orders/flowdealorders/info?flowDealID=' + record.flowDealID + '" ><span class="glyphicon glyphicon-info-sign">查看</span></a>';
    }
    
    function flowUsed_look(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;" href="<%=basePath%>service/center/devicelogs?sbid='+record.SN+'&sj=">查看</A>';
    }

     function refreshOnline(){
         $("#searchFormOnline input[name='SN']").val('');
         $("#searchFormOnline input[name='customerName']").val('');
         //$("#searchFormOnline select option").eq(0).attr("selected","true");
         gridObjOnline.options.otherParames =$('#searchFormOnline').serializeArray();
         gridObjOnline.refreshPage();
      }     
      function refreshOffline(){
         $("#searchFormOffline input[name='SN']").val('');
         $("#searchFormOffline input[name='customerName']").val('');
         //$("#searchFormOffline select option").eq(0).attr("selected","true");
         gridObjOffline.options.otherParames =$('#searchFormOffline').serializeArray();
         gridObjOffline.refreshPage();
      }
     function refreshAvailable(){
         $("#searchFormAvailable input[name='SN']").val('');
         $("#searchFormAvailable input[name='customerName']").val('');
         $("#searchFormAvailable select option").eq(0).attr("selected","true");
         gridObjAvailable.options.otherParames =$('#searchFormAvailable').serializeArray();
         gridObjAvailable.refreshPage();
      }
     function refreshnotuser(){
         $("#searchFormnotuser input[name='SN']").val('');
         $("#searchFormnotuser input[name='customerName']").val('');
         $("#searchFormnotuser select option").eq(0).attr("selected","true");
         $(".ui-widget").children().eq(1).text("");
         $("#example").val("");
         $("input[type='checkbox']").attr("checked",false);
         gridObjnotuser.options.otherParames =$('#searchFormnotuser').serializeArray();
         gridObjnotuser.refreshPage();
      } 
     // 未知 bsgrid 怎样判断是否已load过, 所以添加全局变量控制, 只初次点击菜单会刷新, 其他时间要手动刷新
     // 除非另有考虑
     var bHasInitOnlineGrid = true;
     /* var bHasInitOfflineGrid = false; */
     var bHasInitAvailableGrid = false;
     var bHasInitnotuserGrid = false;
     $("#notuserMenu").click(function(){
    	 if(!bHasInitnotuserGrid) {
    		 gridObjnotuser.refreshPage();
    		 bHasInitnotuserGrid = true;
        }
     });
     $("#onlineMenu").click(function(){
    	 if(!bHasInitOnlineGrid) {
    		  gridObjOnline.refreshPage();
    		  bHasInitOnlineGrid = true;
    	 }
     });
 /*$("#offlineMenu").click(function(){
    	 if(!bHasInitOfflineGrid) {
    		 gridObjOffline.refreshPage();
    		 bHasInitOfflineGrid = true;
        }
     }); */
     $("#availableMenu").click(function(){
    	 if(!bHasInitAvailableGrid) {
    		 gridObjAvailable.refreshPage();
    		 bHasInitAvailableGrid = true;
        }
     });  
 
     
     //激活，暂停,启动
     function  activate(index, gridObj){
         var record= gridObj.getRecord(index);
         var urlpath="";
         var OP="";
       if(record.ifActivate=='否' || record.ifActivate==''){
           OP='激活';
          urlpath='<%=basePath%>orders/flowdealorders/activate?flowOrderID='+record.flowDealID;
       }else if(record.orderStatus=='可使用' || record.orderStatus=='使用中'){
          OP='暂停服务';
          urlpath='<%=basePath%>orders/flowdealorders/pause?flowOrderID='+record.flowDealID;
       }else if(record.orderStatus=='已暂停'){
          OP='启动服务';
          urlpath='<%=basePath%>orders/flowdealorders/launch?flowOrderID='+record.flowDealID;
       }
       bootbox.confirm("确定要"+OP+"吗?", function(result) {
           if(result){
               $.ajax({
                   type:"POST",
                   url:urlpath,
                   dataType:'html',
                   success:function(data){
                      if(data=="0"){
                          easy2go.toast('warn',OP+"失败");
                      }else if(data=="-1"){
                          easy2go.toast('warn',"参数为空");
                      }else if(data=="-5"){
                          easy2go.toast('err',"请重新登录!");
                      } else { // 这种else为成功应该需要改善
                          easy2go.toast('info',OP+"成功");                              
                          gridObj.refreshPage();
                      }
                   }
               });
           }  
       });
     }
     
     function  limitSpeed(index, gridObj){
    	 // TODO: 使用弹出窗口输入表单或其他方法提供限速的大小 8K 16K 32K 64K 等等
    	 //OP='限速';
         //urlpath='<%=basePath%>orders/flowdealorders/limitspeed?flowOrderID='+record.flowDealID+'&limitSpeed={size}';
		}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>