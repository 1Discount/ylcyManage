<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>编辑流量订单-订单管理-流量运营中心</title>
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
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div class="panel">
						<div class="panel-heading">编辑数据服务交易（${floworder.flowDealID
							}）</div>
						<div class="panel-body">
							<form id="deal_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
								<input type="hidden" name="flowDealID"
									value="${floworder.flowDealID }">
								<div class="form-group">
									<label for="deal_country" class="col-md-3 control-label">可用国家:</label>
									<div class="col-md-9">
										<c:forEach items="${countrys }" var="cs">
											<LABEL class="checkbox-items"> <c:if
													test="${fn:contains(floworder.userCountry,cs.countryName)}">
													<INPUT class="countryitem" name="userCountry"
														value="${cs.countryName }-${cs.countryCode}-${cs.flowPrice }"
														type="checkbox" checked="checked"
														data-price="${cs.flowPrice }">${cs.countryName }（${cs.flowPrice }/天）
</c:if> <c:if test="${!fn:contains(floworder.userCountry,cs.countryName)}">
													<INPUT class="countryitem" name="userCountry"
														value="${cs.countryName }-${cs.countryCode}-${cs.flowPrice }"
														type="checkbox" data-price="${cs.flowPrice }">${cs.countryName }（${cs.flowPrice }/天）
</c:if>
											</LABEL>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label for="country_code" class="col-md-3 control-label">预约使用时间:</label>
									<div class="col-md-6">
										<INPUT id="forder_panlUserDate" onchange="timefor()"
											class="form-control form_datetime" name="panlUserDate" type="text"
											data-date-format="YYYY-MM-DD HH:mm:ss">
									</div>
								</div>
								
																			
								<div class="form-group">
									<label for="country_code" class="col-md-3 control-label">可用天数:</label>
									<div class="col-md-6">
										<input id="deal_days" onchange="timefor()" type="number" name="flowDays"
											min="1" max="365" value="${floworder.flowDays }"
											data-popover-offset="0,8" required class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label for="country_code" class="col-md-3 control-label">到期时间:</label>
									<div class="col-md-6">
										<input readonly="readonly" id="deal_timeout" type="text" name="flowExpireDate"
											 value="${floworder.flowExpireDate }"
											data-popover-offset="0,8" required class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label for="deal_amount" class="col-md-3 control-label">金额:</label>
									<div class="col-md-6">
										<input id="deal_amount" type="number" name="orderAmount"
											value="${floworder.orderAmount }" min="1"
											data-popover-offset="0,8" required class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label for="deal_desc" class="col-md-3 control-label">备注:</label>
									<div class="col-md-6">
										<textarea id="deal_desc" rows="3" name="remark"
											data-popover-offset="0,8" class="form-control">${floworder.remark }</textarea>
									</div>
								</div>
								
								<DIV class="form-group">
								<DIV class="form-group-vpn">
									<LABEL for="deal_desc" class="col-md-3 control-label">是否需要VPN：</LABEL>
									<DIV class="col-md-6">
									<c:if test="${floworder.ifVPN eq '0'}">
										<input type="radio" name="iifVPN" id="novpn" onclick="setvpnvalue();"　value="0" checked="true"/>不支持&nbsp;&nbsp;&nbsp;
										<input type="radio" name="iifVPN" id="bigvpn"onclick="setvpnvalue();" value="1"/>大流量VPN&nbsp;&nbsp;&nbsp;
										<input type="radio" name="iifVPN" id="littlevpn" onclick="setvpnvalue();"value="2"/>小流量VPN&nbsp;&nbsp;&nbsp;
									</c:if>
									<c:if test="${floworder.ifVPN eq '1'}">
										<input type="radio" name="iifVPN" id="novpn" onclick="setvpnvalue();"value="0"/>不支持&nbsp;&nbsp;&nbsp;
										<input type="radio" name="iifVPN" id="bigvpn" onclick="setvpnvalue();"value="1" checked="true"/>大流量VPN&nbsp;&nbsp;&nbsp;
										<input type="radio" name="iifVPN" id="littlevpn"onclick="setvpnvalue();" value="2"/>小流量VPN&nbsp;&nbsp;&nbsp;
									</c:if>
									<c:if test="${floworder.ifVPN eq '2'}">
										<input type="radio" name="iifVPN" id="novpn" onclick="setvpnvalue();"value="0"/>不支持&nbsp;&nbsp;&nbsp;
										<input type="radio" name="iifVPN" id="bigvpn" onclick="setvpnvalue();"value="1"/>大流量VPN&nbsp;&nbsp;&nbsp;
										<input type="radio" name="iifVPN" id="littlevpn" onclick="setvpnvalue();"value="2" checked="true"/>小流量VPN&nbsp;&nbsp;&nbsp;
									</c:if>									
									</DIV>
									<input type="hidden" id="isvpn" name="ifVPN" value="${floworder.ifVPN}"/>
								</DIV>	
								</DIV>	
						<script type="text/javascript">
						 function setvpnvalue(){
                                var vpn = $("input[name='iifVPN']:checked").val();
                                if(vpn=="on"){vpn="0";}
                                document.getElementById("isvpn").value=vpn;
						 }
						</script>
                               <DIV class="form-group"></DIV>
											
											
								<DIV class="form-group">
									 <LABEL class="col-md-3 control-label">计费模式：</LABEL>
									 <DIV class="col-md-6">
									   <c:if test="${floworder.orderType eq '按时间'}">
										    	  <input type="radio"  disabled id="timeraido" value="1"  checked ="true"/>按时间不限流量&nbsp;
												  <input type="radio" disabled id="flowvalueradio" value="2"/>按时间流量&nbsp;
												  <input type="radio" disabled id="daysraido" value="3"/>按天数&nbsp;
												  <input type="radio" disabled id="ondeviceday" value="4"/>按开机时间连续
									     </c:if>	
									     <c:if test="${floworder.orderType eq '按天数'}">
										    	  <input type="radio"  disabled id="timeraido" value="1"  checked ="true"/>按时间不限流量&nbsp;
												  <input type="radio" disabled id="flowvalueradio" value="2"/>按时间流量&nbsp;
												  <input type="radio" disabled id="daysraido" value="3"/>按天数&nbsp;
												  <input type="radio" disabled id="ondeviceday" value="4"/>按开机时间连续
									     </c:if>										     
									     <c:if test="${floworder.orderType eq '1'}">
										    	  <input type="radio"  disabled id="timeraido" value="1"  checked ="true"/>按时间不限流量&nbsp;
												  <input type="radio" disabled id="flowvalueradio" value="2"/>按时间流量&nbsp;
												  <input type="radio" disabled id="daysraido" value="3"/>按天数&nbsp;
												  <input type="radio" disabled id="ondeviceday" value="4"/>按开机时间连续
									     </c:if>									     
									 <c:if test="${floworder.orderType eq '2'}">
									        <input type="radio" disabled  id="timeraido" value="1" />按时间不限流量&nbsp;
										    <input type="radio" disabled id="flowvalueradio" value="2" checked ="true"/>按时间流量&nbsp;
										    <input type="radio" disabled id="daysraido" value="3"/>按天数&nbsp;
										    <input type="radio" disabled id="ondeviceday" value="4"/>按开机时间连续
									 </c:if>
									    <c:if test="${floworder.orderType eq '3'}">
										     	  <input type="radio" disabled id="timeraido" value="1"/>按时间不限流量&nbsp;
												  <input type="radio" disabled id="flowvalueradio" value="2"/>按时间流量&nbsp;
												  <input type="radio" disabled id="daysraido" value="3" checked ="true"/>按天数&nbsp;
												  <input type="radio" disabled id="ondeviceday" value="4"/>按开机时间连续
									    </c:if>
									    <c:if test="${floworder.orderType eq '4'}">
										     	  <input type="radio" disabled id="timeraido" value="1"/>按时间不限流量&nbsp;
												  <input type="radio" disabled id="flowvalueradio" value="2"/>按时间流量&nbsp;
												  <input type="radio" disabled id="daysraido" value="3"/>按天数&nbsp;
												  <input type="radio" disabled id="ondeviceday" value="4" checked ="true"/>按开机时间连续
									    </c:if>									    
									 </DIV>
								</DIV>	<input name="orderType"  type="hidden" value="${floworder.orderType}"/>
								
								
							   <DIV class="form-group">
							 <c:if test="${floworder.orderType eq '2'}">
							   <DIV class="form-group-raido-flwtotal">
									 <LABEL for="deal_desc" class="col-md-3 control-label">流量值(MB)：</LABEL>
										 <DIV class="col-md-6">
											<input type="text" name="flowTotal" value="${floworder.flowTotal }" class="form-control"/>
										 </DIV>
							   </DIV>	
							   	
							 </c:if>
							   </DIV>	
							    <DIV class="form-group"></DIV>
											
<!-- 								<DIV class="form-group">			 -->
<!-- 							   <DIV class="form-group-raido hidden"> -->
<!-- 									 <LABEL for="deal_desc" class="col-md-3 control-label">流量值(MB)：</LABEL> -->
<!-- 										 <DIV class="col-md-6"> -->
<%-- 											<input type="text" name="flowTotal" value="${floworder.flowTotal }" class="form-control"/> --%>
<!-- 										 </DIV> -->
<!-- 							   </DIV>	 -->
<!-- 							   </DIV>	 -->
							   
							   				 <DIV class="form-group"></DIV>							
								<div class="form-group">
									<label for="deal_desc" class="col-md-3 control-label">行程:</label>
									<div class="col-md-6">
										<INPUT id="deal_journey" value="${floworder.journey }" class="form-control" name="journey" type="text" >e:香港（151016-151018，151020）|台湾（151021-151024）
									</div>
								</div>
								<div class="form-group">
								<c:if test="${floworder.orderType ne '2'}">
								   <div class="form-group-xiansu">
									<label for="deal_desc" class="col-md-3 control-label">限速策略:</label>
									<div class="col-md-6">
										<INPUT id="deal_journey" value="${floworder.speedStr }" class="form-control" name="speedStr" type="text" >例如：0-2000|50-150|150-24|200-16|250-8
									</div>
								   </div>
								</c:if>
									
								</div>
								<p style="margin-left: 130px; color:red;">*行程格式：中国（151102-151105，151108）|香港（151106-151107）</p>
							<!-- 	<p style="margin-left: 130px; color:red;">　　*注意：所有有输入全部在中文状态下输入</p> -->
											
								<div class="form-group">
									<div class="col-md-6 col-md-offset-3">
										<div class="btn-toolbar">
											<button type="button"  onclick="addsub();"
												class="btn btn-primary">保存</button>
											<button type="button" onclick="javascript:history.go(-1);"
												class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</SECTION>
		</SECTION>
	</section>

	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
   
   /* function operate1(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       if(record.remark==""){
    	   return "";
       }
       return '<A class="btn btn-primary btn-xs"  href="#">'+record.remark+'</A>'
   } */
   
   $(function(){
	   $("#forder_panlUserDate").datetimepicker({
     		pickDate: true,                 //en/disables the date picker
     		pickTime: true,                 //en/disables the time picker
     		showToday: true,                 //shows the today indicator
     		language:'zh-CN',                  //sets language locale
     		defaultDate:'${floworder.panlUserDate }',                 //sets a default date, accepts js dates, strings and moment objects
     	}); 
   });
   
   function addsub(){
// 	   $("#ifVPN").val("");//清空原来的vpn
//        $("#ifVPN").val($("input[name='iifVPN']:checked").val());
// 	   alert($("input[name='iifVPN']:checked").val());
	   var vpn = $("input[name='iifVPN']:checked").val();
// 	   document.getElementById("ifVPN").value=$("input[name='iifVPN']:checked").val();
	   		//香港（151022，151030）|意大利（151023，151024）|瑞士（151024-151027）|德国（151027-151029）	
	  	    //检查行程格式是否正确
	  	    //var reg=/^\D{2,8}\（(\d{6}|\d{6}\-\d{6})\，(\d{6}|\d{6}\-\d{6})\）$/;
	  	    var journey1 = $("#deal_journey").val();
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
	  	 	
			$.ajax({
				type : "POST",
				url : "<%=basePath%>orders/flowdealorders/editsave?days=${floworder.flowDays }&redays=${floworder.daysRemaining}",
				dataType : "html",
				data : $('#deal_form').serialize(),
				success : function(data) {
                    msg = data;
					data = parseInt(data);
					if (data == 1) {
						bootbox.alert("流量订单编辑成功",function(){
							window.location="<%=basePath%>orders/flowdealorders/list";
												});
							} else if (data == 0) {
								easy2go.toast('warn', "保存失败");
							} else if (data == -1) {
								easy2go.toast('warn', "参数为空");
							}else if(data == -2){
								easy2go.toast('warn', "行程格式错误");
							}else {
								easy2go.toast('error', msg);
							}
						}
					});
		}
   
    function timefor(){
    	var t=$("#forder_panlUserDate").val();
    	var d=$("#deal_days").val();
    	t = t.replace(/-/g,"/");
    	var date1=new Date(t);
    	var l=date1.getTime();
    	//alert(new Number(d));
    	var date2=new Date(l+(new Number(d))*24*3600*1000);
    	//alert(1);
    	
    	$("#deal_timeout").val(ChangeTimeToString(date2));
    }
    
    function ChangeTimeToString(DateIn)
    {
        var Year=0;
        var Month=0;
        var Day=0;
        var Hour = 0;
        var Minute = 0;
        var CurrentDate="";

        //初始化时间
        Year      = DateIn.getYear()+1900;
        Month     = DateIn.getMonth()+1;
        Day       = DateIn.getDate();
        Hour      = DateIn.getHours();
        Minute    = DateIn.getMinutes();

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
        return CurrentDate;
    }
        
//     $('#daysraido').click(function(){
//         $('.form-group-raido').addClass('hidden');
//     });
//     $('#timeraido').click(function(){
//         $('.form-group-raido').addClass('hidden');
//     });
//     $('#flowvalueradio').click(function(){
//         $('.form-group-raido').removeClass('hidden');
//     });
	    $('#daysraido').click(function(){
	        $('.form-group-raido').addClass('hidden');
	        $('.form-group-endtime').removeClass('hidden');
	        $('.form-group-speedstr').removeClass('hidden');
	        $('.form-group-xiansu').removeClass('hidden');

	    });
	    $('#timeraido').click(function(){
	        $('.form-group-raido').addClass('hidden');
	        $('.form-group-endtime').addClass('hidden');
	        $('.form-group-speedstr').removeClass('hidden');
	        $('.form-group-xiansu').removeClass('hidden');
	    });
	    $('#flowvalueradio').click(function(){
	        $('.form-group-raido').removeClass('hidden');
	        $('.form-group-endtime').addClass('hidden');
	        $('.form-group-speedstr').addClass('hidden');
	        $('.form-group-xiansu').addClass('hidden');
	        document.getElementById('form-group-raido-flwtotal').style.display="none";
	        
	    });
    $('#ondeviceday').click(function(){
        $('.form-group-raido').addClass('hidden');
        $('.form-group-xiansu').removeClass('hidden');

    });
    
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="backend" />
</jsp:include>
</body>
</html>