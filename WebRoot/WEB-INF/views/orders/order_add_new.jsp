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
<title>订单管理-创建订单-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
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
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<header class="panel-heading tab-bg-dark-navy-blue">
							<ul class="nav nav-tabs nav-justified ">
								<li class="main-menu-tab active" id="youzanlistMenu"><a data-toggle="tab" href="#youzanlist">待下单列表(淘宝、天猫)</a></li>
								<li class="main-menu-tab" id="youzansyncMenu"><a data-toggle="tab" href="#youzansync">线上网店（官网、APP、微信、有赞）</a></li>
							</ul>
						</header>
					</DIV>
					<DIV class="panel">
						<div class="tab-content">
							<div id="youzanlist" class="tab-pane active">
								<DIV class="panel-body">
									<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
										<input type="hidden" value="1" id="pagenum" />
										<input type="hidden" id="pagesize" value="10" />

										<DIV class="form-group">
											<LABEL class="inline-label">客户：</LABEL>
											<INPUT class="form-control" id="order_customerName" name="customerName" type="text" placeholder="姓名">
										</DIV>

										<DIV class="form-group">
											<LABEL class="inline-label">使用国家：</LABEL>
											<INPUT class="form-control" id="countryList" name="countryList" type="text" placeholder="国家">
										</DIV>
										<DIV class="form-group">
											<LABEL class="inline-label">SN：</LABEL>
											<INPUT class="form-control" id="SN" name="SN" type="text" placeholder="">
										</DIV>


										<div class="form-group">
											<label class="inline-label">归还：</label>
											<select name="ifReturn" class="form-control">
												<option value="">全部</option>
												<option value="是">是</option>
												<option value="否">否</option>
											</select>
										</div>
										<div class="form-group">
											<label class="inline-label">取货方式：</label>
											<select name="pickUpWay" class="form-control">
												<option value="">全部</option>
												<option value="自取">自取</option>
												<option value="快递">快递</option>
											</select>
										</div>

										<div class="form-group">
											<label class="inline-label">交易类型：</label>
											<select name="deviceTransactionType" class="form-control">
												<option value="">全部</option>
												<option value="租用">租用</option>
												<option value="购买">购买</option>
											</select>
										</div>

										<div class="form-group">
											<label class="inline-label">订单状态：</label>
											<select name="acceptOrderStatus" class="form-control">
												<option value="">全部</option>
												<option value="已打回">已打回</option>
												<option value="已取消">已取消</option>
												<option value="已下单">已下单</option>
												<option value="未下单">未下单</option>
											</select>
										</div>


										<div class="form-group">     
											<label class="inline-label">接单来源1：</label>
											<select name="orderSource" class="form-control">
												<option value="">全部</option>
												<option value="淘宝A" <c:if test="${acceptOrder.orderSource=='淘宝A'}">selected</c:if>>淘宝A</option>
												<option value="淘宝B" <c:if test="${acceptOrder.orderSource=='淘宝B'}">selected</c:if>>淘宝B</option>
												<option value="天猫" <c:if test="${acceptOrder.orderSource=='天猫'}">selected</c:if>>天猫</option>
												<option value="线下" <c:if test="${acceptOrder.orderSource=='线下'}">selected</c:if>>线下</option>
												<option value="武汉店" <c:if test="${acceptOrder.orderSource=='武汉店'}">selected</c:if>>武汉店</option>
												<option value="北京店" <c:if test="${acceptOrder.orderSource=='北京店'}">selected</c:if>>北京店</option>
												<option value="成都店" <c:if test="${acceptOrder.orderSource=='成都店'}">selected</c:if>>成都店</option>
												<option value="其他" <c:if test="${acceptOrder.orderSource=='其他'}">selected</c:if>>其他</option>
											</select>
										</div>



										<DIV class="form-group">
											<LABEL class="inline-label">时间段：</LABEL>
											<INPUT id="order_creatorDatebegin" class="form-control form_datetime" name="begindate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
										</DIV>

										<DIV class="form-group">
											<LABEL class="inline-label">到</LABEL>
											<INPUT id="order_creatorDateend" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
										</DIV>



										<DIV class="form-group">
											<LABEL class="inline-label">发货日期：</LABEL>
											<INPUT id="shippmentDate" class="form-control form_datetime" data-popover-offset="0,8" name="shippmentDate" type="text" data-date-format="YYYY-MM-DD" value="">
										</DIV>

										<DIV class="form-group">
											<BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
											<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
										</DIV>



									</FORM>
								</DIV>
								<DIV class="panel">
									<div class="panel-heading">
										<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
											<a class="btn btn-danger  btn-xs" href="#" onclick="op_cancel()">
												<span class="glyphicon glyphicon-remove"></span>
												取消订单
											</a>
										</div>
										<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
											<a class="btn btn-danger  btn-xs" href="#" onclick="biaojiyixiadan()">
												<span class="glyphicon glyphicon-remove"></span>
												标记为已下单
											</a>
										</div>
										<!-- <div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
											<a class="btn btn-success btn-xs" href="#" onclick="return_Device()">
												<span class="glyphicon glyphicon-plus"></span>
												归还
											</a>
										</div> -->
										<h3 class="panel-title">接单列表</h3>
									</div>
									<div class="panel-body">
										<div class="table-responsive">
											<table id="searchTable">
												<TR>
													<th w_render="redio" width="2%;"></th>
													<TH w_index="customerName" width="5%"><b>客户姓名</b></TH>
													<TH w_index="SN" width="5%"  w_length="8"><b>SN</b></TH>
													<TH w_index="customerPhone" width="5%"><b>手机号</b></TH>
													<TH w_index="address" width="8%" w_length="10"><b>地址</b></TH>
													<TH w_index="wangwangNo" width="5%"><b>旺旺号</b></TH>
													<TH w_index="orderNumber" width="5%" w_length="10"><b>网店订单编号</b></TH>
													<TH w_index="orderSource" width="5%"><b>来源</b></TH>
													<!-- <TH w_index="SN" width="5%"><b>SN</b></TH> -->
													<TH w_index="startTime" width="8%"><b>使用开始时间</b></TH>
													<TH w_index="countryName" width="8%"><b>可使用国家</b></TH>
													<TH w_render="opifReturn" width="5%"><b>是否归还</b></TH>
													<TH w_index="trip" width="8%" w_length="10"><b>行程</b></TH>
													<th w_index="pickUpWay" width="5%;"><b>取货方式</b></th>
													<th w_index="deviceTransactionType" width="5%;"><b>交易类型</b></th>
													<TH w_index="total" width="5%"><b>数量</b></TH>
													<TH w_index="price" width="5%"><b>流量金额</b></TH>
													<TH w_index="shippmentDate" width="5%"><b>待发货日期</b></TH>
													<TH w_index="belowOrderPer" width="5%"><b>下单人</b></TH>
													<TH w_index="creatorUserName" width="5%"><b>接 单人</b></TH>
													<TH w_index="creatorDate" width="5%"><b>接单时间</b></TH>
													<!-- <TH w_index="belowOrderDate" width="5%"><b>下单时间</b></TH> -->
													<TH w_index="remark" width="5%"><b>备注</b></TH>
													<TH w_render="acceptOrderStatus" width="5%"><b>状态</b></TH>
													<TH w_render="operate" width="4%"><b>操作</b></TH>
												</TR>
											</table>
										</div>
									</DIV>
								</DIV>
							</div>




							<div id="youzansync" class="tab-pane">
								<DIV class="panel-body">
									<FORM class="form-inline" id="searchForm1" role="form" method="get" action="#">
										<input type="hidden" value="1" id="pagenum" />
										<input type="hidden" id="pagesize" value="10" />

										<DIV class="form-group">
											<LABEL class="inline-label">客户：</LABEL>
											<INPUT class="form-control" id="order_customerName" name="customerName" type="text" placeholder="姓名">
										</DIV>
										<DIV class="form-group">
											<LABEL class="inline-label">手机号：</LABEL>
											<INPUT class="form-control" id="customerPhone" name="customerPhone" type="text" placeholder="手机号">
										</DIV>
										<DIV class="form-group">
											<BUTTON class="btn btn-primary" type="button" onclick="gridObj1.options.otherParames =$('#searchForm1').serializeArray();gridObj1.page(1);">搜索</BUTTON>
											<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
										</DIV>
									</FORM>
								</DIV>
								<div class="table-responsive">
									<table id="searchTable1">
										<TR>
											<th w_render="redio" width="2%;"></th>
											<TH w_index="countryName1" w_hidden="true" width="8%"><b>使用国家</b></TH>
											<TH w_render="orderidOPtwo" w_hidden="false" width="8%"><b>订单ID</b></TH>
											<TH w_index="customerName" width="8%"><b>客户姓名</b></TH>
											<TH w_index="customerPhone" width="8%"><b>手机号</b></TH>
											<TH w_index="address" width="8%"><b>地址</b></TH>
											<TH w_index="orderSource" width="8%"><b>来源</b></TH>
											<TH w_index="startTime" width="8%"><b>使用开始时间</b></TH>
											<TH w_index="countryName" width="8%"><b>可使用国家</b></TH>
											<TH w_index="trip" width="8%"><b>行程</b></TH>
											<TH w_index="total" width="8%"><b>设备数量</b></TH>
											<th w_index="deallType" width="5%;"><b>交易类型</b></th>
											<TH w_index="yajingPrice" width="8%"><b>设备金额</b></TH>
											<TH w_index="flowPrice" width="8%"><b>流量金额</b></TH>
										</TR>
									</table>
								</div>
							</div>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
			<SECTION class="wrapper" style="margin-top: 0; padding-top: 0;">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-heading">创建订单</DIV>
						<DIV class="panel-body">
							<form id="flowdeal_form" role="form" method="POST" autocomplete="off" action="" class="form-horizontal">
								<input id="pickUpWay" name="pickUpWay" type="hidden" />
								<input id="aoID" name="aoID" type="hidden" />
								<input type="hidden" name="orderID" id="orderID" />

								<div class="form-group">
									<label for="client_name" class="col-xs-1 control-label">客户:</label>
									<div class="col-md-1">
										<input id="client_name" type="text" name="customerName" class="form-control">
									</div>
									<label for="client_phone" class="col-xs-1 control-label">手机号:</label>
									<div class="col-md-2">
										<input id="client_phone" type="text" name="phone" class="form-control">
									</div>
									<label for="client_address" class="col-xs-1 control-label">地址:</label>
									<div class="col-md-2">
										<input id="client_address" type="text" name="address" class="form-control">
									</div>
									<label for="devicedeal_sn" class="col-xs-1 control-label">SN:</label>
									<div class="col-md-2">
										<input id="devicedeal_sn" type="text" name="SN" placeholder="SN后六位，用/隔开" class="form-control">
									</div>
									<div class="col-md-3">
										<label for="" class="col-xs-1 control-label"></label>
										<LABEL class="checkbox-inline">
											<INPUT name="deallType" class="deallType" value="租用" type="radio">
											租用
										</LABEL>
										<LABEL class="checkbox-inline">
											<INPUT name="deallType" class="deallType" value="购买" type="radio" >
											购买
										</LABEL>
										<div style="display: inline-block; margin-left: 20px;" id="yajin">
											<label for="" class="control-label" style="float: none;"> 押金：</label>
											<input type="number" name="yajin" class="form-control" style="width: 120px; display: inline-block;">
										</div>
									</div>
								</div>
								<div class="form-group">

									<label for="devicedeal_sn" class="col-xs-1 control-label">国家:</label>
									<div class="col-md-4">
										
										<span id="userCountry1" style="display: block;">
											<select id="example" name="country" multiple="multiple" class="form-control">
												<c:forEach items="${Countries }" var="country">
											 		<c:choose>
											 			<c:when test="${country.countryName eq '摩洛哥'}">
											 				<option value="${country.countryName }-${country.countryCode }-${country.flowPrice}">${country.countryName }(${country.flowPrice}/天)--非洲</option>
											 			</c:when>
											 			<c:when test="${country.countryName eq '摩纳哥'}">
											 				<option value="${country.countryName }-${country.countryCode }-${country.flowPrice}">${country.countryName }(${country.flowPrice}/天)--欧洲</option>
											 			</c:when>
											 			<c:otherwise>
											 				<option value="${country.countryName }-${country.countryCode }-${country.flowPrice}">${country.countryName }(${country.flowPrice}/天)</option>
											 			</c:otherwise>
											 		</c:choose>
												</c:forEach>
											</select>
										</span>
										<input type="hidden" name="hfexample" id="hfexample" />
										<input type="hidden" name="pullcountry" id="pullcountry" />
									</div>
									<input type="hidden" id="orderSource" name="orderSource" value="122" class="form-control" />
									<label for="devicedeal_sn" class="col-xs-2 control-label">计费模式:</label>
									<div class="col-md-2">
										<label for="M1radio" class="radio-inline">
											<input class="Mraido" type="radio" name="orderType" id="M1radio" value="1" checked="true" />
											M1&nbsp;
										</label>
										<label for="M2radio" class="radio-inline">
											<input class="Mraido" type="radio" name="orderType" id="M2radio" value="2" />
											M2&nbsp;
										</label>
										<label for="M3radio" class="radio-inline">
											<input class="Mraido" type="radio" name="orderType" id="M3radio" value="3" />
											M3&nbsp;
										</label>
										<label for="M4radio" class="radio-inline">
											<input class="Mraido" type="radio" name="orderType" id="M4radio" value="4" />
											M4
										</label>
									</div>
									<label for="devicedeal_sn" class="col-xs-2 control-label">开始时间:</label>
									<div class="col-md-2">
										<INPUT id="panlUser_Date" class="form-control form_datetime" name="panlUserDate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
									</div>
									<label for="deal_days" class="col-xs-1 control-label">天数:</label>
									<div class="col-md-1">
										<INPUT id="deal_days" class="form-control" name="flowDays" value="" type="number" min="0" max="99999">
									</div>

								</div>
								<div class="form-group">

									<label for="flowExpireDate" class="col-xs-2 control-label">过期时间:</label>
									<div class="col-xs-3">
										<INPUT id="flowExpireDate" class="form-control form_datetime" name="flowExpireDate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss" readonly="readonly">
									</div>
									<LABEL class="col-xs-3 control-label">流量值(MB)：</LABEL>
									<DIV class="col-md-1">
										<input type="text" id="flowtotalinput" readonly="readonly" name="flowTotal" value="0" class="form-control" />
									</DIV>
									<label for="devicedeal_sn" class="col-xs-2 control-label">流量金额:</label>

									<div class="col-md-1">
										<INPUT id="deal_amount" class="form-control" name="orderAmount" value="0" type="number" min="0">
									</div>
									<LABEL for="deal_desc" class="col-xs-3 control-label">VPN：</LABEL>
									<DIV class="col-md-3">
										<input type="radio" name="ifVPN" id="novpn" value="0" checked="true" />
										不支持&nbsp;&nbsp;&nbsp;
										<input type="radio" name="ifVPN" id="bigvpn" value="1" />
										大流量VPN&nbsp;&nbsp;&nbsp;
										<input type="radio" name="ifVPN" id="littlevpn" value="2" />
										小流量VPN&nbsp;&nbsp;&nbsp;
									</DIV>
								</div>
								<div class="form-group">

									<label for="devicedeal_sn" class="col-xs-2 control-label">限速策略:</label>
									<div class="col-xs-3">
										<INPUT id="speedStr" class="form-control form_datetime" name="speedStr" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
										<a href="#">示例:0-2000|50-150|150-24|200-16|250-8</a>
									</div>

									<LABEL class="col-xs-3 control-label">行程：</LABEL>
									<DIV class="col-md-3">
										<input type="text" id="deal_journey" name="journey" value="" class="form-control" />
										<a href="#">示例:中国（151102-151105，151108）|香港（151106-151107）</a>
									</DIV>

									<LABEL class="col-xs-1 control-label">备注：</LABEL>
									<DIV class="col-md-3">
										<INPUT id="deal_desc" onclick="" placeholder="" class="form-control" name="remark" type="text">

									</DIV>
									<DIV class="col-xs-3">
										<BUTTON id="clare_service_deal" class="btn btn-" type="reset">清空</BUTTON>

										<BUTTON id="create_service_deal" onclick="addOrder()" class="btn btn-primary" type="button">创建订单</BUTTON>

									</DIV>
								</div>
							</form>


							<div class="form-group">
								<label class="col-md-1 control-label red">说明：</label>
								<div class="col-md-12">
									<span class="red">
										M1：有效期内包连续天（24小时），从下单开始计时，有效期等于可用天，每天限速不断网
										<br />
										M2：有效期内包流量，每天不限速不限流量，总流量用完即断网
										<br />
										M3：有效期内包不连续天（24小时），用一天计一天，有效期大于可用天，每天限速不断网
										<br />
										M4：有效期内包连续天（24小时），从接入开始计时，有效期大于可用天，每天限速不断网
										<br />
									</span>
								</div>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>

	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- dialog body -->
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Hello world!
				</div>
				<!-- dialog buttons -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-primary">取消</button>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min1.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.multiselect.js"></script>
	<SCRIPT type="text/javascript">
   $(function(){
	   $(".deallType").click(function(){
		   var dealType= $(this).val();
		   if(dealType=="租用"){
			   $("#yajin").css({'display':'inline-block'});
		   }else{
			   $("#yajin").css({'display':'none'});
		   }
	   });
   });
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
    	  var gridObj1;
          $(function(){
             $("#order_creatorDatebegin").datetimepicker({
         		pickDate: true,
         		pickTime: true,
         		showToday: true,
         		language:'zh-CN',
         		defaultDate: moment().add(-3, 'months'),
         	});

             $("#order_creatorDateend").datetimepicker({
          		pickDate: true,
          		pickTime: true,
          		showToday: true,
          		language:'zh-CN',
          		defaultDate: moment().add(0, 'days'),
          	});

              $("#shippmentDate").datetimepicker({
          		pickDate: true,
          		pickTime: true,
          		showToday: true,
          		language:'zh-CN',
          		defaultDate: null
          	});
             $("#panlUser_Date").datetimepicker({
           		pickDate: true,
           		pickTime: true,
           		showToday: true,
           		language:'zh-CN',
           	});
             $("#flowExpireDate").datetimepicker({
            		pickDate: true,
            		pickTime: true,
            		showToday: true,
            		language:'zh-CN',
            		defaultDate: null
            });
             $("#example").multiselect({
 	 	  	    header: false,
 	 	  	    height: 200,
 	 	  	    minWidth: 160,
 	 	  		selectedList: 10,//预设值最多显示10被选中项
 	 	  	    hide: ["explode", 500],
 	 	  	    noneSelectedText: '请选择使用国家',
 	 	  	    close: function(){
 	 	  	      var values= $("#example").val();
 	 	  		  $("#hfexample").val(values);

 	 	  		}
 	 	   });

             var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
            	 url:'<%=basePath%>orders/acceptorder/getpage?ifAll=1',
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 autoLoad:true,
                 otherParames:$('#searchForm').serializeArray(),
                 pageSizeForGrid:[10,20,50],
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 $("#pagenum").val(options.curPage);
                		 var a = $("#searchTable_pt_pageSize").val();
                		 $("#pagesize").val(a);
                	 }
                 },
                 event: {
	 	                selectRowEvent: function (record, rowIndex, trObj, options) {
	 	                	//这里是行选中事件
	 	                	trObj.children('td').eq(0).children('input').eq(0).prop('checked',true);
	 	                	if(record.acceptOrderStatus=="未下单"){
	 	                		$('#clare_service_deal').click();
	 	                		//填值.
		 	                	pullvalue(record);

	 	                		var dealType = record.deviceTransactionType;

	 	                		if(dealType=="购买"){
	 	                			$("input:radio[value='购买']").attr('checked','checked');
	 	                			$("#yajin").css({'display':'none'});
	 	                		}
	 	                		if(dealType=="租用"){
	 	                			$("input:radio[value='租用']").attr('checked','checked');
	 	                		    $("#yajin").css({'display':'inline-block'});
	 	                		}
	 	                	}
	 	                },
	 	                unselectRowEvent: function (record, rowIndex, trObj, options) {
	 	                	//这里是行取消选中事件
	 	                	trObj.children('td').eq(0).children('input').eq(0).removeAttr('checked');
	 	                	escpullvalue();
	 	                }
	 	          }

             });
             var buttonHtml = '<td style="text-align: left;">';
             buttonHtml += '<table><tr>';
             buttonHtml += '<td><input type="button" value="导出数据" onclick="exprotexcel()" /></td>';
             buttonHtml += '</tr></table>';
             buttonHtml += '</td>';
             $('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
             gridObj1 = $.fn.bsgrid.init('searchTable1',{
            	 url:'<%=basePath%>orders/ordersinfo/queryPage?ifAll=1',
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 autoLoad:true,
                 otherParames:$('#searchForm1').serializeArray(),
                 pageSizeForGrid:[10,20,50],
                 displayPagingToolbarOnlyMultiPages:true,
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 $("#pagenum").val(options.curPage);
                		 var a = $("#searchTable_pt_pageSize").val();
                		 $("#pagesize").val(a);
                	 }
                 },
                 event: {
	 	                selectRowEvent: function (record, rowIndex, trObj1, options) {
	 	                	//这里是行选中事件
	 	                	trObj1.children('td').eq(0).children('input').eq(0).prop('checked',true);

	 	                	//填值.
	 	                	pullvalue(record);
	 	                	$("input[name='yajin']").val(record.yajingPrice);
	 	                	$("input[name='orderAmount']").val(record.flowPrice);
	 	                	var dealType = record.deallType;
	 	                	if(dealType=="购买"){
 	                			$("input:radio[value='购买']").attr('checked',true);
 	                			$("#yajin").css({'display':'none'});
 	                		}
 	                		if(dealType=="租用"){
 	                			$("input:radio[value='租用']").attr('checked',true);
 	                		    $("#yajin").css({'display':'inline-block'});
 	                		}
	 	                },
	 	                unselectRowEvent: function (record, rowIndex, trObj1, options) {
	 	                	//这里是行取消选中事件
	 	                	trObj1.children('td').eq(0).children('input').eq(0).removeAttr('checked');
	 	                	escpullvalue();
	 	                }
	 	          }
             });

               if($("#pagenum").val()!=""){
	        	   gridObj.page($("#pagenum").val());
	           }else{
	        	   gridObj.page(1);
	           };

	           $(".Mraido").click(function(){
	        	    var item = $('input[name="orderType"]:checked ').val();
	        	   if(item==2){
	        		   $("#flowtotalinput").removeAttr("readonly");
	        		   $("#flowExpireDate").attr("readonly","readonly");
	        	   }else if(item==3 || item==4){
	        		   $("#flowExpireDate").removeAttr("readonly");
	        		   $("#flowtotalinput").attr("readonly","readonly");
	        	   }else if(item==1){
	        		   $("#flowExpireDate").attr("readonly","readonly");
	        		   $("#flowtotalinput").attr("readonly","readonly");
	        		   $("#flowExpireDate").val("");

	        	   }
	           });


         });
         //自定义列.
         function operate(record, rowIndex, colIndex, options) {
        	 if(record.acceptOrderStatus=="未下单"){
           		return '<A class="btn btn-primary btn-xs" onclick="toBackOrder('+rowIndex+')" ><SPAN class="glyphicon glyphicon-brightness-reduce">打回</SPAN></A>';
        	 }
         }
         function orderidOPtwo(record, rowIndex, colIndex, options) {
        	 return '<A  href="<%=basePath%>orders/ordersinfo/orderinfo?ordersID='+record.orderID+'"><SPAN class="" style="color: green;">详细</SPAN></A>';
         }
         function opifReturn(record, rowIndex, colIndex, options){

        	 if(record.ifReturn=="是"){
            		return '<A class="btn btn-primary btn-xs"  ><SPAN class="glyphicon glyphicon-brightness-reduce">是</SPAN></A>';
         	 }else if(record.ifReturn=="否"){
         		return '<A class="btn btn-warning btn-xs" ><SPAN class="glyphicon glyphicon-brightness-reduce">否</SPAN></A>';

         	 }else{
         		 return "";
         	 }
         }
         function redio(record, rowIndex, colIndex, options){
       	  return '<input type="radio" />';
         }

         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID='+record.flowDealID+'"><span class="" style="color: green;">详情</span></a>';
         }

         function acceptOrderStatus(record, rowIndex, colIndex, options){
        	 var status = record.acceptOrderStatus;
        	 if(status=="已下单"){
            	 return '<a class="btn btn-primary btn-xs"  title="" href="#"><span class="">已下单</span></a>';
        	 }else if(status=="未下单"){
            	 return '<a class="btn btn-warning btn-xs"  title="" href="#"><span class="" >未下单</span></a>';
        	 }else if(status=="已打回"){
            	 return '<a class="btn btn-warning btn-xs"  title="" href="#"><span class="" >已打回</span></a>';
        	 }else if(status=="已取消"){
            	 return '<a class="btn btn-warning btn-xs"  title="" href="#"><span class="" >已取消</span></a>';
        	 }
		 }
         function exprotexcel(){
        	 window.location.href="<%=basePath%>orders/acceptorder/exportexcel?"+$('#searchForm').serialize();
         }
         //打回接单
         function toBackOrder(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确定打回该接单吗?", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"POST",
                		 url:"<%=basePath%>orders/ordersinfo/BackAcceptOrder?acceptOrderID="+record.aoID,
                		 dataType:'html',
                		 success:function(data){
                            if(data=="0"){
            					easy2go.toast('warn',"打回失败");
            				}else if(data=="-1"){
            					easy2go.toast('warn',"参数为空");
            				}else if(data=="-5"){
	                            easy2go.toast('err',"请重新登录!");
	                        } else {
                                easy2go.toast('info',"打回成功");
                                gridObj.refreshPage();
	                        }
                		 }
                	 });
        		 }
        	 });
         }

         //刷新
        function re(){
        	$("#order_ID").val('');
        	$("#order_customerName").val('');
        	gridObj.options.otherParames =$('#searchForm').serializeArray();
        	gridObj.refreshPage();
        }
        var flowNum=1;
        //选中填值
        function pullvalue(data){
        	$("#orderID").val(data.orderID);
        	$("#client_name").val(data.customerName);
        	$("#client_phone").val(data.customerPhone);
        	$("#client_address").val(data.address);
            $("#example").multiselect({
 	 	  	    header: false,
 	 	  	    height: 200,
 	 	  	    minWidth: 160,
 	 	  		selectedList: 10,//预设值最多显示10被选中项
 	 	  	    hide: ["explode", 500],
 	 	  	    noneSelectedText: data.countryName,
 	 	  	    close: function(){
 	 	  	      var values= $("#example").val();
 	 	  	      if(values!=null){
 	 	  	    	$("#pullcountry").val("");
 	 	  	   		$("#hfexample").val(values);
 	 	  	      }else{
 	 	  	    	$("#pullcountry").val(data.countryList);
 	 	  	   		$("#hfexample").val("");
 	 	  	      }
 	 	  		}
 	 	   });
        	$("#panlUser_Date").val(data.startTime);
        	$("#deal_days").val(data.days);
        	$("#deal_amount").val(data.price);
        	$("#flowExpireDate").val();
        	$("#deal_journey").val(data.trip);
        	$("#orderSource").val(data.orderSource);
        	$("#deal_desc").val(data.remark);
        	$("#aoID").val(data.aoID);
        	$("#pickUpWay").val(data.pickUpWay);
        	//清空选择的值
        	$("#hfexample").val("");
        	$("#pullcountry").val(data.countryList);
        	//流量个数
        	flowNum=data.total;
        	if(flowNum==1){
        		$("#devicedeal_sn").attr("placeholder","请输入"+flowNum+"个SN后6位");
        	}else if(flowNum>1){
        		$("#devicedeal_sn").attr("placeholder","请输入"+flowNum+"个SN后6位,用/隔开");
        	}
        }
        //取消填值
        function escpullvalue(data){
        	$("#orderID").val("");
        	$("#client_name").val("");
        	$("#client_phone").val("");
        	$("#client_address").val("");
            $("#example").multiselect({
 	 	  	    header: false,
 	 	  	    height: 200,
 	 	  	    minWidth: 160,
 	 	  		selectedList: 10,
 	 	  	    hide: ["explode", 500],
 	 	  	    noneSelectedText: "",
 	 	  	    close: function(){}
 	 	   });
        	$("#panlUser_Date").val("");
        	$("#deal_days").val("");
        	$("#deal_amount").val("");
        	$("#flowExpireDate").val("");
        	$("#deal_journey").val("");
        	$("#orderSource").val("");
        	$("#deal_desc").val("");
        	$("#aoID").val("");
        	$("#pickUpWay").val("");
        	$("#hfexample").val("");
        	$("#pullcountry").val("");
        	$("#userCountry").val("");
        	$("#devicedeal_sn").attr("placeholder","");
        }


        var temp=1;
      	$(function(){
      		$("#youzanlistMenu").click(function(){
      			temp=1;
      			$("#userCountry1").css({'display':'block'});
      			$("#userCountry").attr({'type':'hidden'});
      		});
			$("#youzansyncMenu").click(function(){
      			temp=2;
      			$("#userCountry1").css({'display':'none'});
      			$("#userCountry").attr({'type':'text'});
      		});
      	});

      function addOrder(){
    	  //文本校验
    	  if($("#client_name").val()==''){
    		  easy2go.toast('warn',"请输入客户名称");
    		  return;
    	  }
    	  if($("#client_phone").val()==''){
    		  easy2go.toast('warn',"请输入客户手机号");
    		  return;
    	  }
    	  if($("#client_address").val()==''){
    		  easy2go.toast('warn',"请输入客户地址");
    		  return;
    	  }
    	  if($("#devicedeal_sn").val()==''){
    		  easy2go.toast('warn',"请输入SN");
    		  return;
    	  }

    	if($("#panlUser_Date").val()==''){
		  easy2go.toast('warn',"请输入开始时间");
		  return;
	    }
    	if($("#deal_days").val()==''){
		  easy2go.toast('warn',"请输入使用天数");
		  return;
	  	}



      	if(temp==1){

      		if($("#hfexample").val()=='' && $("#pullcountry").val()==''){
          		easy2go.toast('warn',"请选择使用国家");
      		  return;
          	}
      		 //开始提交订单
          	$.ajax({
       		 		type:"POST",
       		 		url:"<%=basePath%>orders/ordersinfo/addOrder?flowNum="+ flowNum,
    				data : $('#flowdeal_form').serialize(),
    				dataType : 'json',
    				success : function(data) {
    					if (data.code == 3) {
    						easy2go.toast('info', data.msg);
    						gridObj.refreshPage();
    					} else if (data.code == 4) {
    						easy2go.toast('info', data.msg);
    						gridObj.refreshPage();
    					} else if (data.code == 2) {
    						easy2go.toast('warn', data.msg);
    					} else if (data.code == -3) {
    						easy2go.toast('warn', data.msg);

    					} else if (data.code == -5) {
    						easy2go.toast('warn', data.msg);

    					} else if (data.code == -4) {
    						easy2go.toast('warn', data.msg + "SN如下:" + data.sn);

    					}
    				},
    				error:function(data){
    					alert(data.status);
    				}
    		});
      	}
      	else if(temp==2){
      		if($("#userCountry").val()==''){
        		  easy2go.toast('warn',"使用国家不能为空");
        		  return;
        	  	}
      		$.ajax({
   		 		type:"POST",
   		 		url:"<%=basePath%>orders/ordersinfo/bindSNupdate?flowNum=" + flowNum,
				data : $('#flowdeal_form').serialize(),
				dataType : 'json',
				success : function(data) {
					//-1表示SN不存在。-2表示SN使用中,0表示SN或漫游卡不支持国家
					if (data.code == '00') {
						easy2go.toast('info', '订单创建成功！！！');
						gridObj1.refreshPage();
					}
					else if (data.code == '01') {
						easy2go.toast('error', '订单创建失败！！！');
					}
					else if (data.code == '-1') {
						easy2go.toast('warn', data.Msg + '不存在！！！');
					}
					else if (data.code == '-2') {
						easy2go.toast('warn', data.Msg + '使用中！！！');
					}
					else if (data.code == '0') {
						easy2go.toast('warn', data.Msg + 'SN或漫游卡不支持国家！！！');
					}
				}
			});
		}
	}

      function biaojiyixiadan(){
    	  if(gridObj.getSelectedRowIndex()==-1){
	   		  easy2go.toast('warn', "请选择一条接单信息");
	   		  return ;
	   	  }
    		var id =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'aoID');
	       	var acceptOrderStatus =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'acceptOrderStatus');
	       	if(acceptOrderStatus!='未下单'){
	       		easy2go.toast('warn','只有未下单的订单才能被标记为已下单');
	       		return;
	       	}
	       	////////////
	       	bootbox.dialog({
	             title: "请输入设备号",
	             message:'<div><input class="form-control" id="snlist" type="text" placeholder="请输入SN后六位, 多个SN用 / 隔开" /></div>',
	             buttons: {
	                 cancel: {
	                     label: "取消",
	                     className: "btn-default",
	                     callback: function () {}
	                 },
	                 success: {
	                     label: "开始分发",
	                     className: "btn-success edit-button-ok",
	                     callback: function () {
	                    	 var SN = $("#snlist").val();
	                    	 
	                    	 $.ajax({
	                             type:"POST",
	                             url:"<%=basePath%>orders/acceptorder/biaojiyixiadan?SN="+SN+"&id=" + id,
	         					success : function(data) {
	         						result = jQuery.parseJSON(data);
	         						if (result.code == 0) { // 成功保存
	         							easy2go.toast('info', result.msg);
	         							gridObj.refreshPage();
	         						}else {
	         							easy2go.toast('error', result.msg);
	         						}
	         					}
	         				});
	                     }
	                 }
	             }
	         });
	       	
	       	///////////////
	       	
	      <%--   if(confirm("确认将该订单标记为已下单?")) {
	             $.ajax({
                    type:"POST",
                    url:"<%=basePath%>orders/acceptorder/biaojiyixiadan?id=" + id,
					success : function(data) {
						result = jQuery.parseJSON(data);
						if (result.code == 0) { // 成功保存
							easy2go.toast('info', result.msg);
							gridObj.refreshPage();
						}else {
							easy2go.toast('error', result.msg);
						}
					}
				});
			 } --%>
      }
      //标记为已下单
      function op_cancel() {
	   	  if(gridObj.getSelectedRowIndex()==-1){
	   		  easy2go.toast('warn', "请选择一条接单信息");
	   		  return ;
	   	  }
	       	var id =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'aoID');
	       	var acceptOrderStatus =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'acceptOrderStatus');
	       	if(acceptOrderStatus!='已下单'){
	       		easy2go.toast('warn','只有已下单的订单才能取消');
	       		return;
	       	}
          if(confirm("确认取消订单?")) {
              $.ajax({
                  type:"POST",
                  url:"<%=basePath%>orders/acceptorder/cancel?id=" + id,
				  success : function(data) {
					result = jQuery.parseJSON(data);
					if (result.code == 0) { // 成功保存
						easy2go.toast('info', result.msg);
						gridObj.refreshPage();
					}
					else {
						easy2go.toast('error', result.msg);
					}
				  }
			});
		  }
	  }

     //取消订单
      function return_Device() {
	   	  if(gridObj.getSelectedRowIndex()==-1){
	   		  easy2go.toast('warn', "请选择一条接单信息");
	   		  return ;
	   	  }
	       	var id =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'aoID');

          if(confirm("确认取消订单?")) {
              $.ajax({
                    type:"POST",
                    url:"<%=basePath%>orders/acceptorder/cancel?id=" + id,
					success : function(data) {
						result = jQuery.parseJSON(data);
						if (result.code == 0) { // 成功保存
							easy2go.toast('info', result.msg);
							gridObj.refreshPage();
						}
						else {
							easy2go.toast('error', result.msg);
						}
					}
				});
			}
		}
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>