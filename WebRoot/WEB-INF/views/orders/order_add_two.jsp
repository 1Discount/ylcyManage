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
<title>新建订单第二步-订单管理-EASY2GO ADMINEASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/grid.paging.min.css">
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
				<DIV class="col-md-6">
					<DIV class="panel">
						<DIV class="panel-heading">新订单（第二步）：设备或数据服务</DIV>
						<DIV class="panel-body">
							<DIV role="tabpanel">
								<INPUT name="orderID" id="order_ID" value="${order.orderID }"
									type="hidden">
								<UL class="nav nav-tabs" role="tablist">
									<LI class="active" role="presentation"><A role="tab"
										aria-controls="custom"
										href="http://m.easy2go.cn/temporders/step2#device_tab"
										data-toggle="tab">设备</A></LI>
									<LI role="presentation"><A role="tab"
										aria-controls="plans"
										href="http://m.easy2go.cn/temporders/step2#service_tab"
										data-toggle="tab">自定数据服务</A></LI>
									<LI role="presentation"><A role="tab"
										aria-controls="plans"
										href="http://m.easy2go.cn/temporders/step2#plans_tab"
										data-toggle="tab">套餐数据服务</A></LI>
								</UL>
								<DIV class="tab-content">
									<DIV id="device_tab" class="tab-pane active" role="tabpanel">
										<FORM style="padding-top: 20px;" id="device_form"
											class="form-horizontal" role="form" action="">
											<INPUT name="deviceID" id="device_ID" value="" type="hidden"><INPUT
												name="SN" id="device_SN" value="" type="hidden">
											<DIV class="form-group">
												<LABEL class="control-label col-md-2">搜索设备：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="device_input" class="form-control"
														name="device_sn" maxLength="24" type="text"
														placeholder="序列号">
												</DIV>
												<DIV class="col-md-1">
													<BUTTON id="search" class="btn btn-primary" type="button">搜索</BUTTON>
												</DIV>
												<DIV class="col-md-1">
													<BUTTON id="showlist" class="btn btn-default"
														onclick="showtable();" type="button">列表</BUTTON>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<DIV class="col-md-8 col-md-offset-2">
													<DIV id="devices-list">
														<div class="table-responsive">
															<div id="devtable">
																<table id="searchTable">
																	<tr>
																		<th w_num="total_line" width="10%;">行号</th>
																		<th w_check="true" width="10%;"></th>
																		<th w_index="SN" width="80%;">序列号</th>
																	</tr>
																</table>
															</div>
														</div>
													</DIV>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<LABEL class="control-label col-md-2">交易类型：</LABEL><LABEL
													class="checkbox-inline"><INPUT name="deallType"
													value="租用" CHECKED="" type="radio">租用设备</LABEL><LABEL
													class="checkbox-inline"><INPUT name="deallType"
													value="购买" type="radio">购买设备</LABEL>
											</DIV>
											<DIV style="display: none;" class="form-group">
												<LABEL class="col-md-2 control-label">归还日期：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="device_returndate"
														class="form-control form_datetime" name="returnDate"
														type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
												</DIV>
												<DIV class="col-md-6">
													<P class="form-control-static"></P>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">金额：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="device_amount" class="form-control"
														name="dealAmount" value="500" type="number"
														placeholder="租用或者购买费用" min="0">
												</DIV>
											</DIV>
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">备注：</LABEL>
												<DIV class="col-md-6">
													<TEXTAREA id="device_desc" class="form-control" rows="2"
														name="remark" data-popover-offset="0,8"></TEXTAREA>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<DIV class="col-md-6 col-md-offset-2">
													<DIV class="btn-toolbar">
														<BUTTON id="create_device_deal" class="btn btn-primary"
															type="button">确认交易</BUTTON>
													</DIV>
												</DIV>
											</DIV>
										</FORM>
									</DIV>
									<DIV id="service_tab" class="tab-pane" role="tabpanel">
										<FORM style="padding-top: 20px;" id="service_form"
											class="form-horizontal" role="form" method="post" action=""
											autocomplete="off">
											<INPUT name="_csrf"
												value="OkhU5qmK-GjrbmgA8OfLBn_SW9V_jcTMsU-w" type="hidden">
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">可用国家：</LABEL>
												<DIV class="checkbox col-md-10">
													<c:forEach items="${countrys }" var="cs">
														<LABEL class="checkbox-items">
														<INPUT onclick="selectCountry()" class="countryitem" name="userCountry" value="${cs.countryName }-${cs.countryCode}-${cs.flowPrice }" type="checkbox" data-price="${cs.flowPrice }" >
														${cs.countryName}（${cs.flowPrice }/天）</LABEL>
													</c:forEach>
												</DIV>
											</DIV>
											
											
											
											<div class="form-group">
												<label class="col-md-2 control-label">时区：</label>
												<div class="col-md-10 selectCountry">
												<!-- 	<label for="deallType0" class="radio-inline">
													<input type="radio" name="deallType" id="deallType0" value="租用" checked="checked">中国</label>
													<label for="deallType0" class="radio-inline">
													<input type="radio" name="deallType" id="deallType0" value="租用" >中国</label>
													<label for="deallType0" class="radio-inline">
													<input type="radio" name="deallType" id="deallType0" value="租用" >中国</label> -->
												</div>
											</div>
											
											
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">北京开始时间：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="panlUser_Date" onchange="panlUserDatechange()"
														class="form-control form_datetime" name="panlUserDate"
														type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
												</DIV>
												<DIV class="col-md-6">
													<P class="form-control-static"></P>
												</DIV>
											</DIV>
											
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">当地开始时间：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="DDpanlUser_Date" onchange="DDpanlUserDatechange();" class="form-control DDpanlUserDate" name="DDpanlUserDate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
												</DIV>
												<DIV class="col-md-6">
													<P class="form-control-static"></P>
												</DIV>
											</DIV>
											
											
											 <DIV class="form-group">
												<LABEL class="col-md-2 control-label">计费模式：</LABEL>
												<DIV class="col-md-10">
													 <label for="timeraido" class="radio-inline"> <input type="radio" name="orderType" id="timeraido" value="1" checked="true"/>按时间不限流量&nbsp;</label>
													 <label for="flowvalueradio" class="radio-inline"> <input type="radio" name="orderType" id="flowvalueradio" value="2"/>按时间限流量&nbsp;</label>
													 <label for="daysraido" class="radio-inline"> <input type="radio" name="orderType" id="daysraido" value="3"/>按实际使用天数&nbsp;</label>
													 <label for="ondeviceday" class="radio-inline"> <input type="radio" name="orderType" id="ondeviceday" value="4"/>按开机时间连续</label>
												</DIV>
											</DIV>	
											
											<DIV class="form-group">
												<DIV class="form-group-raido hidden">
													<LABEL class="col-md-2 control-label">流量值(MB)：</LABEL>
													<DIV class="col-md-6">
													  <input type="text" id="flowtotalinput" name="flowTotal" value="0" class="form-control"/>
													</DIV>
												</DIV>		
											</DIV>	
											
									 		<DIV class="form-group">
											  <DIV class="form-group-endtime hidden">
													<LABEL class="col-md-2 control-label">过期时间：</LABEL>
													<DIV class="col-md-6">
	                                                    <INPUT id="panlUser_Date" class="form-control form_datetime" name="flowExpireDate"
															type="text" data-date-format="YYYY-MM-DD HH:mm:ss">												
													</DIV>
													<DIV class="col-md-6">
														<P class="form-control-static"></P>
													</DIV>
												</DIV>		
											</DIV>		
											
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">天数：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="deal_days" class="form-control" name="flowDays"
														value="7" type="number" min="1" max="99999">
												</DIV>
											</DIV>
											
											
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">金额：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="deal_amount" class="form-control"
														name="orderAmount" value="0" type="number" min="0">
												</DIV>
											</DIV>
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">行程：</LABEL>
												<DIV class="col-md-6"> 
													<INPUT id="deal_journey" onclick="showxc()"
														placeholder="请切换到中文输入状态，格式如下"
														class="form-control" name="journey" type="text">
														
												</DIV>
											</DIV>
											<div class="form-group" id="country" style="position: relative;left:145px; display: none;" >
												<select id="jourcountry"  name="" style="width: 137px; display: inline-block;" class="form-control" >
													<option value="">请选择国家</option>
												</select>
												<input style="width:130px; display: inline-block;" type="text"  id="begindate" class="form-control form_datetime" data-date-format="YYYY-MM-DD"/>到
												<input style="width:130px; display: inline-block;" type="text"  id="enddate" class="form-control form_datetime" data-date-format="YYYY-MM-DD"/>
												<span class="btn btn-primary" style="background:#999; border:none; font-size: 10px;  padding: 0px 5px; " onclick="addjourney();">加入行程</span>
												
											</div>
												<p style="margin-left: 120px; color:red;">　　*例如：中国（151102-151105，151108）|香港（151106-151107）</p>
											
											<DIV class="form-group">
											<div class="form-group-speedstr">
												<label for="deal_desc" class="col-md-2 control-label">限速策略:</label>
												<div class="col-md-6">
													<INPUT value="" class="form-control" name="speedStr" type="text" >例如：0-2000|50-150|150-24|200-16|250-8
												</div>
											</div>
											</div>
											
											<DIV class="form-group"></DIV>
											
											<DIV class="form-group-vpn">
												<LABEL for="deal_desc" class="col-md-2 control-label">VPN：</LABEL>
												<DIV class="col-md-6">
										           <input type="radio" name="ifVPN" id="novpn" value="0" checked="true"/>不支持&nbsp;&nbsp;&nbsp;
										           <input type="radio" name="ifVPN" id="bigvpn" value="1"/>大流量VPN&nbsp;&nbsp;&nbsp;
										           <input type="radio" name="ifVPN" id="littlevpn" value="2"/>小流量VPN&nbsp;&nbsp;&nbsp;
												</DIV>
											</DIV>	
											<DIV class="form-group"></DIV>
<DIV class="form-group"></DIV>											
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">备注：</LABEL>
												<DIV class="col-md-6">
													<TEXTAREA id="deal_desc" class="form-control" rows="2"
														name="remark" data-popover-offset="0,8"></TEXTAREA>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<DIV class="col-md-6 col-md-offset-2">
													<DIV class="btn-toolbar">
														<BUTTON id="create_service_deal" class="btn btn-primary"
															type="button">确认交易</BUTTON>
															</DIV>
												</DIV>
											</DIV>
										</FORM>
									</DIV>
									<DIV id="plans_tab" class="tab-pane" role="tabpanel">
										<FORM style="padding-top: 20px;" id="plan_form"
											class="form-horizontal" role="form" method="post"
											action="/temporders/plan" autocomplete="off">
											<INPUT name="_csrf"
												value="OkhU5qmK-GjrbmgA8OfLBn_SW9V_jcTMsU-w" type="hidden">
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">套餐:</LABEL>
												<DIV class="col-md-6">
													<SELECT id="plan_select" class="form-control"
														name="flowPlanID">
														<OPTION value="">请选择套餐</OPTION>
														<c:forEach items="${flows}" var="fl">
															<OPTION value="${fl.flowPlanID }">${fl.flowPlanName
																}（${fl.planPrice}/天）</OPTION>
														</c:forEach>
													</SELECT>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">SN：</LABEL>
												<DIV class="col-md-6">
													<INPUT id="panlUser_Date_plan"
														class="form-control" name="SN"
														type="text">
												</DIV>
												<DIV class="col-md-6">
													<P class="form-control-static"></P>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<LABEL class="col-md-2 control-label">备注:</LABEL>
												<DIV class="col-md-6">
													<TEXTAREA id="plan_desc" class="form-control" rows="2"
														name="remark" data-popover-offset="0,8"></TEXTAREA>
												</DIV>
											</DIV>
											<DIV class="form-group">
												<DIV class="col-md-6 col-md-offset-2">
													<DIV class="btn-toolbar">
														<BUTTON id="create_plan_deal" class="btn btn-primary"
															type="button">确认交易</BUTTON>
													</DIV>
												</DIV>
											</DIV>
											<p id="msg" style="color:red;"></p>
										</FORM>
									</DIV>
								</DIV>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
				<DIV class="col-md-6">
					<DIV class="panel">
						<DIV class="panel-heading">客户</DIV>
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE class="table table-hover">
									<TBODY>
										<TR>
											<TD>姓名：${cus.customerName}<input type="hidden"
												id="customer_ID" value="${cus.customerID}" /><input
												type="hidden" id="customer_Name" value="${cus.customerName}" /></TD>
											<TD>电话：${cus.phone}</TD>
										</TR>
										<TR>
											<TD colSpan="2">收货地址:${cus.address}</TD>
										</TR>
										<TR>
											<TD colSpan="2">备注：${cus.remark}</TD>
										</TR>
									</TBODY>
								</TABLE>
							</DIV>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">交易列表</H3>
						</DIV>
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE class="table table-hover">
									<THEAD>
										<TR>
											<TH>交易项目</TH>
											<TH>相关信息</TH>
											<TH>金额</TH>
											<TH></TH>
										</TR>
									</THEAD>
									<TBODY id="deal_list">
										<TR>
											<TD colSpan="3"></TD>
											<TD><font style="font-size: x-large;">总额:</font><SPAN
												style="font-size: x-large; color: red;" id="amount">0.00</SPAN></TD>
										</TR>
									</TBODY>
								</TABLE>
							</DIV>
						</DIV>
						<DIV class="panel-footer">
							<DIV class="btn-toolbar">
								<A id="saveorder" class="btn btn-primary" disabled=""
									onclick="saveorder()">保存订单</A>
								<BUTTON class="btn btn-default"
									onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
bootbox.setDefaults("locale","zh_CN");

var UnloadConfirm = {};
UnloadConfirm.MSG_UNLOAD = "订单尚未保存，建议保存后在离开此页!";
UnloadConfirm.set = function(a) {
    window.onbeforeunload = function(b) {
        b = b || window.event;
        b.returnValue = a;
        return a;
    };
};
UnloadConfirm.clear = function() {
    //fckDraft.delDraftById();
    window.onbeforeunload = function() {};
    
};
UnloadConfirm.set(UnloadConfirm.MSG_UNLOAD);

var gridObj;
$(function(){
	
	
	$(".form_datetime1").datetimepicker({
		pickDate: true,                 //en/disables the date picker
		pickTime: true,                 //en/disables the time picker
		showToday: true,                 //shows the today indicator
		language:'zh-CN',                  //sets language locale
		defaultDate: moment().add(1, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
	});
    $(".form_datetime").datetimepicker({
 		pickDate: true,                 //en/disables the date picker
 		pickTime: true,                 //en/disables the time picker
 		showToday: true,                 //shows the today indicator
 		language:'zh-CN',                  //sets language locale
 		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
 	});
    
    $(".DDpanlUserDate").datetimepicker({
 		pickDate: true,                 
 		pickTime: true,                
 		showToday: true,                
 		language:'zh-CN',                  
 		//defaultDate: moment().add(0, 'days'),                 
 	});
     
     $(".form_datetime").datetimepicker({
  		pickDate: true,                 //en/disables the date picker
  		pickTime: true,                 //en/disables the time picker
  		showToday: true,                 //shows the today indicator
  		language:'zh-CN',                  //sets language locale
  		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
  	});

	$('#deal_days').change(function(){
		computeTotal();
	});
 	$('.countryitem').click(function(){
 		 var country = $(this).val();
		var coun = $(this).val().split("-")[0];
//  		 if(coun=="中国"){
//  			$('.form-group-vpn').removeClass('hidden');//显示VPN选项
//  		 }
		if(this.checked==true){
			$("#jourcountry").append("<option value='"+coun+"'>"+coun+"</option>");
		}else{
			$("#jourcountry option[value='"+coun+"']").remove();
// 			 $('.form-group-vpn').addClass('hidden');//隐藏VPN选项
		} 
		computeTotal();
	});
	
  	
    gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>orders/ordersinfo/getdev',
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30],
           multiSort:true,
           isProcessLockScreen:false,
           pagingLittleToolbar:true,
           isplayPagingToolbarOnlyMultiPages:true
       });
});

var prevent_form_db = false;
var dorder=0; //设备交易数
var forder=0; //流量交易数
var dorderid="";//设备订单ID集合
var forderid="";//流量订单ID集合
var devSNarray="";//设备SN列表
var devSN="";//最后面添加的设备SN
//搜索设备
function searchDevice(){
var keyword = $('#device_input').val().trim();
if(keyword==''){
	easy2go.toast('warn',"请输入关键字，然后点击搜索");
	return;
}

if(prevent_form_db)
	return;
prevent_form_db = true;

$.ajax({
	url:'<%=basePath%>orders/ordersinfo/getbysn',
	data: {SN: $('#device_input').val()},
	dataType: 'json',
	type: "POST",
	success: function(data)	{
		if(data.length>0){
			if(data[0].error==0){
				easy2go.toast('info',"没有相关可用设备信息");
			}else if(data[0].error==-1){
				easy2go.toast('err',"参数为空");
			}else if(data[0].error==2){
				easy2go.toast('err',"该设备状态为使用中");
			}
			else{
				$('#device_input').val('');
				var cdiv1='<DIV id="sdev" class="checkbox">';
				var btn='<a class="btn but-xs btn-success"><span class="glyphicon glyphicon-ok"></span></a>';
				var span1='<span>&nbsp;&nbsp;</span>';
				var span2='<span>SN:'+data[0].SN+'</span>&nbsp;&nbsp;';
				var cdiv2='</DIV>';
				$("#devtable").hide();
				$("#device_ID").val(data[0].deviceID);
				$("#device_SN").val(data[0].SN);
				$('#devices-list').prepend(cdiv1+btn+span1+span2+cdiv2);
			}
			
		}else{
			easy2go.toast('err',"查询失败");
		}
	},
	error: function(xhr,textStatus,error){
		//console.log(error);
		//console.log(xhr);
		if(xhr.status==404)
			$('#device').html("<p class='form-control-static'><span class='red'>没有相关设备信息</span></p>");
		else{
			$('#device').html('');
			easy2go.toast('err',"出错了："+xhr.responseJSON.error);
		}
	}
});
window.setTimeout(function(){
	prevent_form_db=false;
},600);
}

$('#search').click(searchDevice);
$('#device_input').keyup(function(event){
if(event.which == 13 || event.keyCode == 13){
	searchDevice();
}
});

//确定自定义套餐交易.
$('#create_service_deal').click(function(){
	var journey1=$("#deal_journey").val();
	 if(!journey1==""){
	    	var check= $("input[name='userCountry']:checked").length;
		    var aa =journey1.split("\|").length-1;
	        if(aa!=check-1){    
	        	easy2go.toast('warn', "行程格式不对,请检查使用国家和行程国家的数量是否一致");
	            return;
	        } 
	        var reg=/^\D{2,8}\（.*\）$/;
	        var arr = journey1.split("\|");
	        var temp=0;
	        $.each(arr, function(i,val){  
	            var journey = val;
	           //如果等 于2说明出现了一次
	            if(journey.split("）").length!=2 ||journey.split("（").length!=2){
	            	easy2go.toast('warn', "行程格式不对,请检查是否多或少了括号");
	            	temp=temp+1;
		        	return false;
	            } 
	            if(!reg.test(journey)){
	            	easy2go.toast('warn', "行程格式不对");
	            	temp=temp+1;
		        	return false;
		        } 
	        });  
	         if(temp!=0){
	        	return;
	        } 
	 }
	
	 if($("#deal_days").val()<1){
		 easy2go.toast('warn', "流量天数不能小于1天");
         return;
	 }
	
	
	
	
	var oid=$("#order_ID").val();
	var cid=$("#customer_ID").val();
	var cname=$("#customer_Name").val();
	cname=encodeURI(encodeURI(cname));
$.ajax({
	url:'<%=basePath%>orders/ordersinfo/floworder?orderID='+oid+'&customerID='+cid+'&customerName='+cname,
	data: $('#service_form').serialize(),
	dataType: 'json',
	type: "POST",
	success: function(data){
		if(data.error==0){
			easy2go.toast('err', "添加流量订单失败");
		}else if(data.error==-1){
			easy2go.toast('err', "出错了：参数为空。");
		}else if(data.error==-2){
			easy2go.toast('warr', "请选择国家");
		}else if(data.error==-5){
            easy2go.toast('err', "请重新登录");
		}
		else if(data.flowDealID){
			gridObj.refreshPage();
			//clean
			$('input:radio').prop('checked',false);
			$('#deal_desc').val('');
			$('input:checkbox').prop('checked',false);
	
			var tr='<tr data-temporder="'+data.flowDealID+'">';
			var td1;
				td1='<td>自定义流量服务</td>';
			var td2='<td>'+data.flowDays+'天('+data.userCountry+')</td>';
			var td3='<td>'+data.orderAmount+'</td>';
			var td4='<td><button class="del_deal btn btn-xs btn-danger" data-sn="" data-id="'+data.flowDealID+'" onclick="del(this,0)" data-url="<%=basePath%>orders/ordersinfo/delfloworder?flowDealID='+data.flowDealID+'&orderAmount='+data.orderAmount+'" >删除</button></td>';
			var tr1='</tr>';
			$('#deal_list').prepend(tr+td1+td2+td3+td4+tr1);
			easy2go.toast('info', "流量订单成功生成");
			//insert html 
			forder++;
			$('#amount').html(jine(data.orderAmount,"+"));
			$('#saveorder').attr('disabled',false);
			if(forderid==''){
				forderid+=data.flowDealID;
			}else{
				forderid+=","+data.flowDealID;
			}
			
		}else{
			easy2go.toast('err',"出错了：服务器返回数据异常。");
		}
	},
	error: function(xhr, textStatus, error){
		if(xhr.status == 404){
			//$('#device').html("<p class='form-control-static'><span class='red'>没有相关设备信息</span></p>");
		}else{
			$('#device').html('');
			easy2go.toast('err', "出错了："+xhr.responseJSON.error);
		}
	}
});
});


//确定设备交易.
$('#create_device_deal').click(function(){
	var oid=$("#order_ID").val();
	var cid=$("#customer_ID").val();
	var cname=$("#customer_Name").val();
	cname=encodeURI(encodeURI(cname));
	var r=gridObj.getCheckedRowsIndexs();
	if(r!=null && r!=''){
		var row= gridObj.getRecord(r);
		$("#device_ID").val(row.deviceID);
		$("#device_SN").val(row.SN);
	}else{
		if($("#device_ID").val()==''){
			easy2go.toast('info', "请选择设备");
			return;
		}
	}
	//验证设备SN是否可用
	
	/* if($("#device_ID").val()==''){
		var r=gridObj.getCheckedRowsIndexs();
		if(r!=null && r!=''){
			var row= gridObj.getRecord(r);
			$("#device_ID").val(row.deviceID);
			$("#device_SN").val(row.SN);
			
		}else{
			easy2go.toast('info', "请选择设备");
			return;
		}
	} */
	//判断是否包含设备sn
	if(devSNarray.indexOf($("#device_SN").val())>-1){
		easy2go.toast('info', "该设备SN已有未保存订单,不能重复添加");
		return;
	}
$.ajax({
	url:'<%=basePath%>orders/ordersinfo/devorder?orderID='+oid+'&customerID='+cid+'&customerName='+cname,
	data: $('#device_form').serialize(),
	dataType: 'json',
	type: "POST",
	success: function(data){
		///alert(data.Msg);
		if(data.error==0){
			easy2go.toast('err', "添加设备订单失败");
			
		}else if(data.error==-1){
			easy2go.toast('err', "出错了：参数为空");
        }else if(data.error==-5){
            easy2go.toast('err', "请重新登录");
		}
        
        
        else if(data.deviceDealID){
			//clean
			gridObj.refreshPage();
			
			//$('input:radio').prop('checked',false);
			$("#device_ID").val('');
			$("#device_SN").val('');
			$('#device_input').val('');
			$('#device_desc').val('');
			//insert html
			var tr='<tr data-temporder="'+data.deviceDealID+'">';
			var td1;
			if(data.deallType=='租用'){
				td1='<td>租用设备</td>';
			}else if(data.deallType=='购买'){
				td1='<td>购买设备</td>';
			}
			var td2='<td>'+data.SN+'</td>';
			var td3='<td>'+data.dealAmount+'</td>';
			var td4='<td><button class="del_deal btn btn-xs btn-danger" data-id="'+data.deviceDealID+'"  data-sn="'+data.SN+'" onclick="del(this,1)" data-url="<%=basePath%>orders/ordersinfo/deldevorder?deviceDealID='+data.deviceDealID+'&dealAmount='+data.dealAmount+'" >删除</button></td>';
			var tr1='</tr>';
			$('#deal_list').prepend(tr+td1+td2+td3+td4+tr1);
			easy2go.toast('info', "设备订单成功生成");
			dorder++;
			devSN=data.SN;
			if(devSNarray==""){
				devSNarray+=data.SN;
			}else{
				devSNarray+=","+data.SN;
			}
			//计算金额.
			$('#amount').html(jine(data.dealAmount,"+"));
			$('#saveorder').attr('disabled',false);
			if(dorderid==''){
				dorderid+=data.deviceDealID;
			}else{
				dorderid+=","+data.deviceDealID;
			}
		}else{
			easy2go.toast('err', "出错了：服务器返回数据异常。");
		}
	},
	error: function(xhr, textStatus, error){
		if(xhr.status == 404){
			$('#device').html("<p class='form-control-static'><span class='red'>没有相关设备信息</span></p>");
		}else{
			$('#device').html('');

			easy2go.toast('err', "出错了："+xhr.responseJSON.error);
		}
		console.log(xhr);
	}
});
});



//添加套餐订单.
$('#create_plan_deal').click(function(){
	var oid=$("#order_ID").val();
	var cid=$("#customer_ID").val();
	var cname=$("#customer_Name").val();
	cname=encodeURI(encodeURI(cname));
$.ajax({
	url:'<%=basePath%>orders/ordersinfo/flowplanorder?orderID='+oid+'&customerID='+cid+'&customerName='+cname,
	data: $('#plan_form').serialize(),
	dataType: 'json',
	type: "POST",
	success: function(data){
		if(data.error==0){
			easy2go.toast('err', "添加流量订单失败");
			$("#msg").html(data.Msg);
		}else if(data.error==-1){
			easy2go.toast('err', "出错了：参数为空。");
		}else if(data.error==-2){
			easy2go.toast('warn', "请选择套餐");
		}else if(data.error==-5){
            easy2go.toast('err', "请重新登录");
		}else if(data.error==6){
			gridObj.refreshPage();
			var resultData = data.data;
			 for(var i=0;i<resultData.length;i++){
				 var flowDays =resultData[i]["flowDays"];
				 var flowDealID =resultData[i]["flowDealID"];
				 var userCountry =resultData[i]["userCountry"];
				 var orderAmount =resultData[i]["orderAmount"];
				 
				 $('input:radio').prop('checked',false);
				 $('#device_input').val('');
				 $('#device_desc').val('');
				 $('#plan_select').val('');
				 
				 var tr='<tr data-temporder="'+flowDealID+'">';
					var td1;
						td1='<td>套餐流量服务</td>';
					var td2='<td>'+flowDays+'天('+userCountry+')</td>';
					var td3='<td>'+orderAmount+'</td>';
					var td4='<td><button class="del_deal btn btn-xs btn-danger" data-sn="" data-id="'+flowDealID+'" onclick="del(this,2)" data-url="<%=basePath%>orders/ordersinfo/delfloworder?flowDealID='+flowDealID+'&orderAmount='+orderAmount+'" >删除</button></td>';
					var tr1='</tr>';
					$('#deal_list').prepend(tr+td1+td2+td3+td4+tr1);
					easy2go.toast('info', "流量订单成功生成1");
					//insert html 
					forder++;
					$('#amount').html(jine(orderAmount,"+"));
					$('#saveorder').attr('disabled',false);
					if(forderid==''){
						forderid+=flowDealID;
					}else{
						forderid+=","+flowDealID;
					}
			 }
		}
		
		
		
		else{
			easy2go.toast('err', "出错了：服务器返回数据异常。");
		}
	},
	error: function(xhr, textStatus, error){
		if(xhr.status == 404){
			$('#device').html("<p class='form-control-static'><span class='red'>没有相关设备信息</span></p>");
		}else{
			$('#device').html('');
			easy2go.toast('err', "出错了："+xhr.responseJSON.error);
		}
	}
});
});

//删除交易
function del(dataP,temp){
	var urlpath=$(dataP).attr("data-url");
	var id=$(dataP).attr("data-id");
	var sn=$(dataP).attr("data-sn");
	$.ajax({
		url:urlpath,
		dataType: 'html',
		type: "GET",
		success:function(data){
			if(data=="0"){
				easy2go.toast('info', "删除失败");
			}else if(data=="-1"){
				easy2go.toast('info', "参数为空");
			}else if(data=="-5"){
                easy2go.toast('err',"请重新登录!");
			}else{
				$('#amount').html(jine(data.split("|")[1],"-"));
				easy2go.toast('info', "删除成功");
				$(dataP).parent().parent().remove();
				//如果删除成功，将数量-1
				if(temp=="0"){
					forder=forder-1>0?forder-1:0;
					cleanid(1,id,sn);
				}else if(temp=="1"){
					dorder=dorder-1>0?dorder-1:0;
					cleanid(0,id,sn);
					
				}else if(temp=="2"){
					forder=forder-1>0?forder-1:0;
					cleanid(1,id,sn);
				}
				//$(this).parent().parent().remove();
				if($('.del_deal').length==0)
					$('#saveorder').attr('disabled',true);
			}
			
		},
		error: function(xhr, textStatus, error){
			easy2go.toast('err', "出错了："+xhr.responseJSON.error);
		}
	});
}

$('.device').click(function(){
	$('input',this).prop('checked', true);
});
function computeTotal(){
	
var total =0;
	try{
		var days = parseInt($('#deal_days').val());
		var maxprice=0;
		$('.countryitem').each(function(){
			//console.log($(this).attr('data-price'));
			if(this.checked)
				if(parseFloat($(this).attr('data-price'))>maxprice)
					maxprice=parseFloat($(this).attr('data-price'));
		});
		total = days * maxprice;
		$('#deal_amount').val(total.toFixed(2));
	}catch(e){
		if(console){
			console.log(" compute price bug");
			console.error(e);
		}
	}
}



function showtable(){
	$("#devtable").show();
	$("#sdev").hide();
}

//计算金额.
function jine(dealAmount,arithmetic){
	var j1=$('#amount').html();
	if(arithmetic=="+"){
	  return	(parseFloat(j1)+parseFloat(dealAmount)).toFixed(2);
	}else if(arithmetic=="-"){
	  return	(parseFloat(j1)-parseFloat(dealAmount)).toFixed(2);
	}
}

function  saveorder(){	
	$.ajax({
		url:'<%=basePath%>orders/ordersinfo/saveorder',
		data:{orderID:$("#order_ID").val(),orderAmount:$('#amount').html(),deviceDealCount:dorder,flowDealCount:forder,floworderid:forderid,devorderid:dorderid,SN:devSN},
		dataType: 'html',
		type: "POST",
		success: function(data){
			if(data=="-2"){
				easy2go.toast('info', "没有相关交易");
			}else if(data=="-1"){
				easy2go.toast('err', "参数为空");
			}else if(data=="-5"){
                easy2go.toast('err',"请重新登录!");
			}else if(data=="0"){
				easy2go.toast('err', "保存失败");
			}else if(data=="1"){
				window.onbeforeunload = function() {};
				bootbox.alert("订单保存成功",function(){
					window.location="<%=basePath%>orders/ordersinfo/list";
												});
							}
						}
					});

		}

		//清除设备订单ID
		function cleanid(temp, id, sn) {
			//清除设备订单ID和sn
			if (temp == 0) {
				if (dorderid.indexOf(id) == -1) {
					return;
				}
				if (dorderid.indexOf(",") > -1) {
					if (dorderid.indexOf(id) == 0) {
						var tm = dorderid.replace(id + ",", "");
						dorderid = tm;
					} else {
						var tm = dorderid.replace("," + id, "");
						dorderid = tm;
					}

				} else {
					dorderid = "";
				}
				if (devSNarray.indexOf(",") > -1) {
					if (devSNarray.indexOf(sn) == 0) {
						var tm = devSNarray = devSNarray.replace(sn + ",", "");
						devSNarray = tm;
					} else {
						var tm = devSNarray = devSNarray.replace("," + sn, "");
						devSNarray = tm;
					}
				} else {
					devSNarray = "";
				}

				//清除流量订单ID
			} else if (temp == 1) {
				if (dorderid.indexOf(id) == -1) {
					return;
				}
				if (forderid.indexOf(",") > -1) {
					if (forderid.indexOf(",") > -1) {
						var tm = forderid = forderid.replace(id + ",", "");
						forderid = tm;
					} else {
						var tm = forderid = forderid.replace("," + id, "");
						forderid = tm;
					}
				} else {
					forderid = "";
				}	

			}
		}
	 	var content = "";
	 	
	 	
		function addjourney(){
			content = $("#deal_journey").val();
			var journey = $("#deal_journey").val();
			var country = $("#country  option:selected").text();
			var beginDate = $("#begindate").val().replaceAll("-","").substring(2,$("#begindate").val().length);
			var endDate = $("#enddate").val().replaceAll("-","").substring(2,$("#begindate").val().length);
			if(beginDate>endDate){
				easy2go.toast('err', "开始时间不能大于结束日期");
				return;
			}
			if(country=="请选择国家"){
				easy2go.toast('err', "国家不能 为空");
				return;
			}
			if(journey.indexOf(country)!=-1){
				var  arr = journey.split("|");
				for(var i=0;i<arr.length;i++){
					var cx = arr[i];//中国（151120-151123）
					if(cx.indexOf(country)!=-1){
						if(cx.indexOf(beginDate)!=-1 || cx.indexOf(endDate)!=-1){
							//alert("请检查时间是否重复");
							easy2go.toast('err', "请检查时间是否重复");
							return;
						}
						var left=cx.indexOf("（");
						var right=cx.indexOf("）");
						var repContent = country+"（"+cx.substring(left+1,right);
						var XCtime =repContent.substring(repContent.indexOf("（")+1,repContent.length) ;
						//alert(XCtime);//151023，151024
					    var XCarr = XCtime.split("，");
						for(var i=0;i<XCarr.length;i++){
							if(XCarr[i].split("-").length==1){
								if(beginDate<=XCarr[i] && XCarr[i]<=endDate){
									//alert(beginDate+":::"+ XCarr[i]+":::"+endDate);
									easy2go.toast('err', "请检查时间是否重复");
									return;
								}
							}else{
								for(var j=0;j<XCarr[i].split("-").length;j++){
									if(beginDate<=XCarr[i].split("-")[j] && XCarr[i].split("-")[j]<=endDate){
										easy2go.toast('err', "请检查时间是否重复");
										return;
									}
								}
							}
							
							
						}
					}
				}
				if(beginDate!=endDate){
					alert(content);
					alert(repContent);
					content=content.replace(repContent, repContent+"，"+beginDate+"-"+endDate);
				}else{
					content=content.replace(repContent, repContent+"，"+beginDate);
				}
			}else{
				if(beginDate!=endDate){
					content =content+"|"+country+"（"+beginDate+"-"+endDate+"）";
				}else{
					content =content+"|"+country+"（"+beginDate+"）";
				}
			}
			$("#deal_journey").val(content.substring(1,content.length));
		}
	 function showxc(){
		$("#country").css({'display':'none'});
	 }  
	 
	    $('#daysraido').click(function(){
	        $('.form-group-raido').addClass('hidden');
	        $('.form-group-endtime').removeClass('hidden');
	        $('.form-group-speedstr').removeClass('hidden');
	    });
	    $('#timeraido').click(function(){
	        $('.form-group-raido').addClass('hidden');
	        $('.form-group-endtime').addClass('hidden');
	        $('.form-group-speedstr').removeClass('hidden');
	    });
	    $('#flowvalueradio').click(function(){
	        $('.form-group-raido').removeClass('hidden');
	        $('.form-group-endtime').addClass('hidden');
	        $('.form-group-speedstr').addClass('hidden');
	    });
	    $('#ondeviceday').click(function(){
	        $('.form-group-raido').addClass('hidden');
	        $('.form-group-endtime').removeClass('hidden');
	        $('.form-group-speedstr').removeClass('hidden');
	    });
	    
		/* 选 择国家事件 */
		function selectCountry(){
			var html = "";
			var index=1;
			 $("input[name='userCountry']:checked").each(function () {
				    var countryName = this.value.split("-")[0];
				    var mcc = this.value.split("-")[1];
				    html=html+'<label for="country1'+index+'" class="radio-inline"><input type="radio" onclick="countryed()" name="country1" id="country1'+index+'" value="'+mcc+'">'+countryName+'</label>';
				   index++;
			 });
			 $(".selectCountry").html(html);
		}
		/* 算出当地时间 */
		var SCJSON={"334":-14,"310":-13,"302":-13,"724":-11,"272":-8,"620":-8,"274":-8,"604":-8,"268":-8,"234":-8,"208":-7,"222":-7,"295":-7,"214":-7,"213":-7,"240":-7,"225":-7,"216":-7,"278":-7,"260":-7,"270":-7,"230":-7,"206":-7,"238":-7,"292":-7,"284":-7,"219":-7,"247":-7,"231":-7,"280":-7,"226":-7,"276":-7,"204":-7,"293":-7,"242":-7,"228":-7,"262":-7,"232":-7,"655":-6,"246":-6,"248":-6,"202":-6,"602":-6,"244":-6,"286":-6,"427":-5,"250":-5,"424":-4,"617":-4,"472":-3,"404":-2.5,"414":-1.5,"457":-1,"520":-1,"456":-1,"452":-1,"460":0,"454":0,"455":0,"466":0,"502":0,"515":0,"525":0,"450":1,"440":1,"510":1,"505":3,"530":4,"542":4};
	
		//选中使用国家时
		function countryed(){
			var mcc = $("input[name='country1']:checked").val();
			var aa = timefor($("#panlUser_Date").val(),SCJSON[mcc]);
			$("#DDpanlUser_Date").val(aa);
		}
		
		//北京开始时间更改时
		function panlUserDatechange(){
			var mcc = $("input[name='country1']:checked").val();
			if( mcc ==undefined){
				mcc=460;
			}
			var aa = timefor($("#panlUser_Date").val(),SCJSON[mcc]);
			$("#DDpanlUser_Date").val(aa);
		}
		
		//当地开始时间更改时
		function DDpanlUserDatechange(){
			var mcc = $("input[name='country1']:checked").val();
			if( mcc ==undefined){
				mcc=460;
			}
			var t= $("#DDpanlUser_Date").val();
			var d=SCJSON[mcc];
			
			t = t.replace(/-/g,"/");
	    	var date1=new Date(t);
	    	var l=date1.getTime();
	    	var date2=new Date(l-(new Number(d))*60*60*1000);
	    	var aa = ChangeTimeToString(date2);
			$("#panlUser_Date").val(aa);
		}
		
		
		 	function timefor(t,d){
		    	t = t.replace(/-/g,"/");
		    	var date1=new Date(t);
		    	var l=date1.getTime();
		    	var date2=new Date(l+(new Number(d))*60*60*1000);
		    	return ChangeTimeToString(date2);
		    }
		    
		    function ChangeTimeToString(DateIn)
		    {
		        var Year=0;
		        var Month=0;
		        var Day=0;
		        var Hour = 0;
		        var Minute = 0;
		        var Seconds=0;
		        var CurrentDate="";
		        //初始化时间
		        Year      = DateIn.getYear()+1900;
		        Month     = DateIn.getMonth()+1;
		        Day       = DateIn.getDate();
		        Hour      = DateIn.getHours();
		        Minute    = DateIn.getMinutes();
		        Seconds   = DateIn.getSeconds();
		        DateIn.get
		        CurrentDate = Year + "-";
		        if (Month >= 10 )
		        {
		            CurrentDate = CurrentDate + Month + "-";
		        }
		        else
		        {
		            CurrentDate = CurrentDate + "0" + Month + "-";
		        }
		        if (Day >= 10 )
		        { 	
		            CurrentDate = CurrentDate + Day ;
		        }
		        else
		        {
		            CurrentDate = CurrentDate + "0" + Day ;
		        }
		        if(Hour >=10)
		        {
		            CurrentDate = CurrentDate + " " + Hour ;
		        }
		        else
		        {
		            CurrentDate = CurrentDate + " 0" + Hour ;
		        }
		        if(Minute >=10)
		        {
		            CurrentDate = CurrentDate + ":" + Minute ;
		        }
		        else
		        {
		            CurrentDate = CurrentDate + ":0" + Minute ;
		        }  
		        if(Seconds >=10)
		        {
		            CurrentDate = CurrentDate + ":" + Seconds ;
		        }
		        else
		        {
		            CurrentDate = CurrentDate + ":0" + Seconds ;
		        } 
		        return CurrentDate;
		    }
	</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>