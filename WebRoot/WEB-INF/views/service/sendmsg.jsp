<%@page import="java.util.Date"%>
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
	Date d=new Date();
	long nt=d.getTime();
	int Spagesize=5;
	if(request.getSession().getAttribute("nowpagesize1")==null){
		
	}else{
		Spagesize=Integer.parseInt(request.getSession().getAttribute("nowpagesize1").toString());
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>发送短信-流量运营中心</title>
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
<style type="text/css">
#searchTable tr {
	height: 40px;
}
</style>
</head>
<body>

	<section id="container" style="margin-top: 10px;">
		<%-- <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" /> --%>
<input type="hidden" id="pagenum" value="1" /><input type="hidden" id="pagesize" value="<%=Spagesize %>" />
		<SECTION id="main-content" style="width:100%;margin-left: 0px;">
			<SECTION class="wrapper" style="margin-top: 0px;">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body" style="margin: 0px; padding: 0px;">
							<!-- ======================================================================================== -->
							<!--  <SECTION id="main-content"> -->
							<SECTION class="wrapper">
								<DIV class="col-md-12">
									<DIV class="panel">
										<DIV class="panel-body"
											style="padding-top: 5px; padding-bottom: 5px;">

											<%--    <FORM class="form-inline"  method="get" action="<%=basePath %>customer/customerInfolist/getSearchCustomerinfolist"> --%>
											<FORM class="form-inline" id="searchForm" method="get"
												action="#">

												<DIV class="form-group">
													<LABEL>接收手机号：</LABEL> <INPUT class="form-control"
														name="phone" id="phone" type="text" placeholder="" value="${phone}" readonly="readonly">
												</DIV>
												<br />

												<div class="form-group" style="display: inline-block;">
													<label> 　提醒类型：</label> <select name="alertType"
														style="width: 180px;" class="form-control" id="alertType">
														<c:forEach items="${alertType}" var="aType"
															varStatus="status">
															<option value="${aType.value}">${aType.label}</option>
														</c:forEach>
														
															<option value="${aType.value}">${aType.label}</option>
													</select>
												</div>
												<div
													style="margin-top: 10px; display: inline-block; position: relative; top: 53px; opacity: 0;"
													class="battery">
													<label> 请选择电量：</label><input id="battery" name="battery"
														type="radio" 　 value="5" checked="checked">低于5<input
														id="battery" name="battery" type="radio" 　 value="10">低于10
													<input id="battery" name="battery" type="radio"
														　 value="20" checked="checked">低于20
												</div>
												<br /> <br />
												<div class="form-group" style="display: inline-block;">
													<label>设备号：</label> <input type="text"
														class="form-control" style="" id="editsms"  value="${SN}" readonly="readonly"/>

												</div>
												<div id="conectionCount" style="display: none; position: relative;">
													<label>设备连接数： </label><input type="text"
														name="conectionCount" style="width: 180px; position: absolute; top:-10px;z-index: 999;"
														id="conectionCount1" class="form-control" value="" />

												</div>
												<br /> <label 　style="border: 1px solid red;">
													短信预览：　 </label>
												<textarea cols="100" rows="5" id="SMStext"
													style="padding: 10px 5px; margin-top: 10px;"></textarea>
												<br /> <br />
												<DIV class="form-group">
													　　　　　　<BUTTON class="btn btn-primary" type="button"
														onclick="MSMsend()">立即发送</BUTTON>&nbsp;&nbsp;<span style="color: red;" id="msg"></span>
												</DIV>
											</FORM>
										</DIV>
									</DIV>

									<p>说明：1.每天最多给同一手机号发送10条短信</p>
								</DIV>
							</SECTION>
							<!-- ======================================================================================== -->
							
							<!-- ======================================================================================== -->
							<!--  <SECTION id="main-content"> -->
							<SECTION class="wrapper" style="margin-top: 0px;">
								<DIV class="col-md-12">
									<DIV class="panel">
										<DIV class="panel-body"
											style="padding-top: 5px; padding-bottom: 5px;">
                                             <p align="center" style="margin: 0 auto;font-size: 16px;"><strong>今天发送短信的记录</strong></p>
											<DIV class="panel">
											<DIV class="panel-body">
											<DIV class="table-responsive">
											<TABLE id="searchTable">
											  <TR  classs="extend_render_per_row">
											    <TH w_index="sn" width="5%"><b>设备机身码</b></TH>
											    <th w_index="phone" width="5%"><b>手机号</b></th>
											    <th w_render="smsType" width="5%"><b>短信类型</b></th>
											    <th w_index="datetime" width="5%"><b>发送时间</b></th>
											    <th w_index="adminName" width="5%"><b>操作人</b></th>
											     <th w_render="sendStatus" width="5%"><b>状态</b></th> 
											  </TR>
											</TABLE>
											   </DIV>
											<DIV>
											</DIV></DIV></DIV>
										</DIV>
									</DIV>
								</DIV>
							</SECTION>
							<!-- ======================================================================================== -->
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
	<script type="text/javascript">
	var editsms= $("#editsms").val();
	var conectionCount;
	var dl;
	var gridObj;
	$(function(){
	 var pagesize=parseInt($("#pagesize").val());
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>service/center/device/sendmsglist?sn=${SN}',
           autoLoad: false,
           pageSizeSelect: true,
           pageSize:pagesize,
           pageSizeForGrid:[5,10,20,30,50],
           isProcessLockScreen:false,
           autoLoad:false,
           otherParames:$('#searchForm').serializeArray(),
           additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
          	 if(parseSuccess){
          		 $("#snCount").html(options.totalRows);
          		 $("#pagenum").val(options.curPage);
          	     var a = $("#searchTable_pt_pageSize").val();
       		 	 $("#pagesize").val(a);
          	 }else{
          		
          	 }
           }
       });
       if($("#pagenum").val()!=""){
    	   gridObj.page($("#pagenum").val());
       }else{
    	   gridObj.page(1);
       }
		
		
		
		conectionCount=$("#conectionCount1").val();
		
		$(".battery input").click(function(){
			 editsms= $("#editsms").val();
			 var aa =$("input[name='battery']:checked").val();
			 if(aa=="20"){
				$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备电量低于20%，电量过低会导致网络不稳，为不影响正常使用，建议您充电后继续使用。祝您旅途愉快！温馨提示：充电状态下开机可能会导致开机异常，请您开机后再继续充电，或充电完毕后再开机。途狗漫游宝关机充电5-6小时即可充满，充满后可连续使用8-10小时（与接入设备数量及网络质量有关）。再次感谢您的配合与支持！");
				dl="20";
				editsms=editsms+"&dl="+dl+"&type=batterylow";
			}else if(aa=="10"){
				$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备电量低于10%，电量过低会导致网络不稳，为不影响正常使用，建议您充电后继续使用。祝您旅途愉快！温馨提示：充电状态下开机可能会导致开机异常，请您开机后再继续充电，或充电完毕后再开机。途狗漫游宝关机充电5-6小时即可充满，充满后可连续使用8-10小时（与接入设备数量及网络质量有关）。再次感谢您的配合与支持！");
				dl="10";
				editsms=editsms+"&dl="+dl+"&type=batterylow";
			}else{
				$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备电量低于5%，电量过低会导致网络不稳，为不影响正常使用，建议您充电后继续使用。祝您旅途愉快！温馨提示：充电状态下开机可能会导致开机异常，请您开机后再继续充电，或充电完毕后再开机。途狗漫游宝关机充电5-6小时即可充满，充满后可连续使用8-10小时（与接入设备数量及网络质量有关）。再次感谢您的配合与支持！");
				dl="5";
				editsms=editsms+"&dl="+dl+"&type=batterylow";
			}

		});
		$("#editsms").keyup(function(){

			aa();
		});
		$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备电量低于20%，电量过低会导致网络不稳，为不影响正常使用，建议您充电后继续使用。祝您旅途愉快！温馨提示：充电状态下开机可能会导致开机异常，请您开机后再继续充电，或充电完毕后再开机。途狗漫游宝关机充电5-6小时即可充满，充满后可连续使用8-10小时（与接入设备数量及网络质量有关）。再次感谢您的配合与支持！");
		$(".battery").css({'opacity':'1'});
	});
	$("#alertType").change(function(){
		$("#editsms").val("");
		$("#editsms").val("${SN}");
		aa();
	})
	$("#conectionCount1").change(function(){
		conectionCount=$("#conectionCount1").val();
		aa();
	});
	function aa(){
		$(".battery").css({'opacity':'0'});
		$("#conectionCount").css({'display':'none'});
		var value = $("#alertType  option:selected").val();
		editsms= $("#editsms").val();
		var templateId;//短信模板id
		var batterylow;//电量
		//$("#editsms").val("");
		//alert(value);
		// b = "服务号"+parseInt(Math.random()*100+10);
		if(value!="customAuto"){
			$("#SMStext").attr({'readonly':'readonly'});
		}else{
			$("#SMStext").removeAttr('readonly');
		}
		if(value=="batterylow"){
			templateId="25021";//电量过低提醒短信模板id
			$(".battery").css({'opacity':'1'});
			 var aa =$("input[name='battery']:checked").val();
			if(aa=="20"){
				batterylow="20";
				//$("#SMStext").val("【宜联畅游】您的途狗设备电量不足20%，请注意及时充电");
				//$("#editsms").val("电量不足20%");
				$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备电量低于20%，电量过低会导致网络不稳，为不影响正常使用，建议您充电后继续使用。祝您旅途愉快！温馨提示：充电状态下开机可能会导致开机异常，请您开机后再继续充电，或充电完毕后再开机。途狗漫游宝关机充电5-6小时即可充满，充满后可连续使用8-10小时（与接入设备数量及网络质量有关）。再次感谢您的配合与支持！");
				dl="20";
				editsms=editsms+"&dl="+dl+"&type=batterylow"+"&templateId="+templateId+"&batterylow="+batterylow;

			}else if(aa=="10"){
				$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备电量低于10%，电量过低会导致网络不稳，为不影响正常使用，建议您充电后继续使用。祝您旅途愉快！温馨提示：充电状态下开机可能会导致开机异常，请您开机后再继续充电，或充电完毕后再开机。途狗漫游宝关机充电5-6小时即可充满，充满后可连续使用8-10小时（与接入设备数量及网络质量有关）。再次感谢您的配合与支持！");
				dl="10";
				editsms=editsms+"&dl="+dl+"&type=batterylow"+"&templateId="+templateId+"&batterylow="+batterylow;
			}else{
				$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备电量低于5%，电量过低会导致网络不稳，为不影响正常使用，建议您充电后继续使用。祝您旅途愉快！温馨提示：充电状态下开机可能会导致开机异常，请您开机后再继续充电，或充电完毕后再开机。途狗漫游宝关机充电5-6小时即可充满，充满后可连续使用8-10小时（与接入设备数量及网络质量有关）。再次感谢您的配合与支持！");
				dl="5";
				editsms=editsms+"&dl="+dl+"&type=batterylow"+"&templateId="+templateId+"&batterylow="+batterylow;
			}
		}else if(value=="notificationrestart"){//开关机异常，通知重启
			templateId="25023";
			//$("#editsms").val("需要重新开机");
			$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备开关机异常，请您拔下设备电池，确认所有指示灯全灭后装上电池，长按电源键五秒直到电源灯亮WIFI灯闪，等待两分钟左右，待所有指示灯稳定后即可恢复上网。祝您旅途愉快！温馨提示：随设备寄出的“用户使用指南”有关于设备如何使用的详细描述，为避免再次不正确开关机而影响您正常上网，请您仔细阅读。再次感谢您的配合与支持！");
			editsms=editsms+"&type=notificationrestart"+"&templateId="+templateId;
			dl="";
		}else if(value=="tooManyDevicesConnected"){//设备连接太多

			templateId="25019";
			//$("#editsms").val("开机的地区和您所购买流量的地区不匹配");
			$("#conectionCount").css({'display':'inline-block'});
			$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"漫游宝已连接"+conectionCount+"台设备，连接设备越多，流量消耗越快，且每人获得的网络带宽越小（即网速越慢），为获得较好网速体验，建议您最多2人共享一台设备。祝您旅途愉快！ 温馨提示：途狗漫游宝可支持5台设备同时接入，但设备越多，网速越慢，请谨慎共享。再次感谢您的配合与支持！");
			editsms=editsms+"&type=tooManyDevicesConnected&conectionCount="+conectionCount+"&templateId="+templateId;
			dl="";
		}else if(value=="uploadAndDownloadTraffic"){//上传下载流量过大
			templateId="25020";
			//$("#editsms").val("没有可用的流量订单");
			$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备短时产生较大上传流量，请检查您的手机是否已关闭自动上传功能。自动上传会消耗您套餐内有限的高速流量，导致网速变慢。祝您旅途愉快！温馨提示：途狗漫游宝以连续的24小时为计费周期，24小时内前150MB为3G高速流量，超出后降速为2G网速，约为128Kbps。24小时后进入下一计费中期，网速将恢复3G网速。再次感谢您的配合与支持！");
			editsms=editsms+"&type=uploadAndDownloadTraffic"+"&templateId="+templateId;
			dl="";
		}else if(value=="regionalsignaldifference"){//地区信号差
			templateId="25022";
			//$("#editsms").val("所在地区信号不稳定");
			$("#SMStext").val("【途狗漫游宝】途狗后台监测到您的"+editsms+"设备本地网络信号异常，请您方便关机重启设备，长按电源键五秒直到电源灯亮WIFI灯闪，等待两分钟左右，待所有指示灯稳定后即可恢复上网。祝您旅途愉快！温馨提示：随设备寄出的“用户使用指南”有关于设备如何使用的详细描述，为避免再次不正确开关机而影响您正常上网，请您仔细阅读。再次感谢您的配合与支持！");

			editsms=editsms+"&type=regionalsignaldifference"+"&templateId="+templateId;
			//alert(editsms);
			dl="";
		}else if(value=="orderend"){//地区信号差
			templateId="28437";
			//$("#editsms").val("所在地区信号不稳定");
			$("#SMStext").val("【途狗漫游宝】途狗后台温馨提醒：由于您的尾号为"+editsms+"的设备WIFI订单已过期，运营商已中断该设备网络。如果还需要继续使用，麻烦联系微信公众号或者在线客服续租。祝您旅途愉快！");

			editsms=editsms+"&type=regionalsignaldifference"+"&templateId="+templateId;
			//alert(editsms);
			dl="";
		}else if(value=="customAuto"){//自定义
			templateId="28437";
			//$("#editsms").val("所在地区信号不稳定");
			$("#SMStext").val("【途狗漫游宝】途狗后台温馨提醒：您的"+editsms+"设备,再次感谢您的配合与支持！");
			editsms=editsms+"&type=regionalsignaldifference"+"&templateId="+templateId;
			//alert(editsms);
			dl="";
		}
	}
	function MSMsend(){
		aa();
		var value = $("#alertType  option:selected").val();
		var phone = $("#phone").val();
		var SMStext =$("#SMStext").val();
		
	    editsms =editsms.replace("%","%25");
		//alert(editsms)
		if(phone==""){
			  easy2go.toast('warn', "手机号不能为空");
			  return;
		}
		if(editsms==""){
			easy2go.toast('warn', "设备号不能为空");
			  return;
		}
		if(value=="batterylow") {
			 dl = $("input[name='battery']:checked").val()+"%25";
			//editsms=editsms+"&dl="+dl+"&type=batterylow";
		}
		if(value=="tooManyDevicesConnected"){
			var count = $("#conectionCount1").val();

			//editsms=editsms+"&conectionCount="+count+"&type=tooManyDevicesConnected";
		}
		if(confirm("确定发送短信吗？")){
			$.ajax({
				  type:"POST",
				  url:"<%=basePath%>devicelogs/startsendmsm?phone=" + phone
							+ "&SMStext=" + editsms,
					dataType : "html",
					success : function(data) {
						if (data == "1") {
							$("#msg").html("短信发送成功");
						} else {
							$("#msg").html("error!!短信发送失败");
						}
					}
				})
		 } 
	  }
	
	
	function smsType(record, rowIndex, colIndex, options){
		if(record.templateId=='25021'){
    		return "电量过低";
    	}else if(record.templateId=='25022'){
    		return "地区信号差";
    	}else if(record.templateId=='25019'){
    		return "设备连接太多";
    	}else if(record.templateId=='25023'){
    		return "通知重启";
    	}else if(record.templateId=='25020'){
    		return "上传下载流量过大";
    	}else if(record.templateId=='28437'){
    		return "订单已过期";
    	}else{
    		return "未知"; 
    	}
    }
  function sendStatus(record, rowIndex, colIndex, options){
    	if(record.status=='1'){
    		return "成功";
    	}
    	return "失败";
  }
	
	
	</script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<%-- <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" /> --%>
</body>
</html>