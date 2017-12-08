<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>流量订单服务端口跳转</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<style type="text/css">
.bigtable {
	border: 1px solid #ccc;

}

a:-webkit-any-link {
    color: -webkit-link;
    text-decoration: underline;
    cursor: auto;
}
.bigtable tr td input {
	border: 1px solid #FFF;
	text-align: center;
	width: 85px;
	height: 30px;
	margin-left: 5px;
	

}
.bigspan{width:100px; border:1px solid red; height: 100px;}
.bigtable tr td {
	border: 1px solid #ccc;
	height: 100px;
	width: 100px;
}

.bigtable .rows td {
	border: 1px solid #ccc;
	color: #333;
	height: 45px;
	width: 100px;
	text-align: center;
	background: url(../images/skins/default/bg.gif) repeat-x #f4f4f4;
	
	
}
.bigtable tr td.column {
	border: 1px solid #ccc;
	color: #333;
	height: 80px;
	width: 180px;
	text-align: center;
	background: url(../images/skins/default/bg.gif) repeat-x #f4f4f4;
}
.bigtable tr td .simlocation{position:absolute;  left:5px; top:35px;  background:yellow; width:85px;  height:30px;  display:none; line-height:30px; font-size: 12px; text-align: center;}
.bigtable tr td .subscribeTag{ position:absolute;  left:75px; top:5px; border-radius:50% ; background: yellow; width:15px; height:15px; display:none; line-height:15px; font-size: 12px; text-align: center; }
.bigtable tr td .speedType{ position:absolute;  left:5px; top:5px; border-radius:50% ; background: yellow; width:15px; height:15px; display:none; line-height:15px; font-size: 12px; text-align: center;}
.panel.panel-body ul {margin-left: -30px;}
.panel.panel-body ul li{list-style: none; display: inline-block; width:100px; height: 30px; line-height: 30px;  text-align: center;}
.panel.panel-body ul li:hover{cursor:pointer;}
.panel.panel-body ul li.bj{ background: #ccc;}
</style>
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
<div id="D_display" style="">
		<div style="background: #666; width: 100%; height: 100%; position:fixed; z-index: 1; opacity: 0.2;">

		</div>
		<div style=" border: 1px solid #999; width: 240px; background: #FFF; color: #000; height: 35px; padding: 0px 15px; line-height: 35px; text-align: center; opacity: 1; position: absolute; left: 50%; top: 50%; z-index: 888;">
			<img src="../../static/images/spinner.gif" style="float: left; margin-top: 7px; width: 20px; height: 20px;" />正在加载数据...
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
	<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
	<SECTION id="main-content"> <SECTION class="wrapper">
	<DIV class="col-md-12">
		<DIV class="panel">
			<div class="panel-body">
				<form id="searchForm" class="form-inline" action="/ylcyManage/sim/simserver/getpageinfo" method="get" role="form">
					<div class="form-group">
						<label class="inline-label">服务器：</label>
						 <select class="form-control" style="width: 179px;" name="serverIP"  id="severIP">
						 	
						 	<c:if test="${serverIP!=''}">
						 		<option  value="${serverIP }">${serverIP }</option>
						 		<c:forEach items="${servers }" var="server" >
							 		<c:if test="${server.IP!=serverIP }">
										<option  value="${server.IP }">${server.IP }</option>
									</c:if>
				  				</c:forEach> 
						 	</c:if>
			 				<c:if test="${serverIP==''}">
				 			    <c:forEach items="${servers }" var="server" >
									<option  value="${server.IP }">${server.IP }</option>
					  			</c:forEach> 
						 	</c:if>
						</select>
					</div>
					<div class="form-group">
						<button class="btn btn-primary" id="button" onclick="sendd()" type="button">搜索</button>
					</div>
					<div class="form-group" style="margin-left: 150px;">
						*红色字体代表剩余流量小于50M；　　背景颜色  　 红:不可用；　 绿:可用；　蓝:使用中；　黄:停用；　白：下架；
						　　高:高速；　　预：预约；　　
					</div>
				</form>
				 
			</div>
		</div>
		<DIV class="panel panel-body">
		<div class="oneTable">
		<ul class="simbank">
			<li class="bj2 li1">SIMBank1-4</li>
			<li class="li2">SIMBank5-8</li>
			<li class="li3">SIMBank9-12</li>
			<li class="li4">SIMBank13-16</li>
		</ul>
			<table class="bigtable">
				<tr class="rows">
					<td></td>
					<td class="td1"  colspan="4">${a}</td>	
					<td class="td2" colspan="4">${b}</td>	
					<td class="td3" colspan="4">${c}</td>	
					<td class="td4" colspan="4">${d}</td>	
				</tr>
				<% 
					for(int i=1;i<9;i++){
				%>
					<tr><%-- //<%=i+16%> --%>
						<td  class="column" style="width:40px;"><%=i%></td>
						<td id="${a }-<%=i%>"    style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${a }-<%=i+8%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${a }-<%=i+16%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${a }-<%=i+24%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${b }-<%=i%>"    style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${b }-<%=i+8%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${b }-<%=i+16%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${b }-<%=i+24%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${c }-<%=i%>"    style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${c }-<%=i+8%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${c }-<%=i+16%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${c }-<%=i+24%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${d }-<%=i%>"    style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${d }-<%=i+8%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${d }-<%=i+16%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${d }-<%=i+24%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
					</tr>
				<%} %>
			</table>
			</div>
			<br />
		 <div class="twoTable">
			<ul class="simbank">
				<li class="bj2 li1">SIMBank1-16</li>
				<li class="li2">SIMBank17-32</li>
				<li class="li3">SIMBank33-48</li>
				<li class="li4">SIMBank49-64</li>
			</ul>
			<table class="bigtable">
				<tr class="rows">
					<td></td>
					<td class="td1" >${a}</td>	
					<td class="td2" >${b}</td>	
					<td class="td3" >${c}</td>	
					<td class="td4" >${d}</td>	
					<td class="td1" >${e}</td>	
					<td class="td2" >${f}</td>	
					<td class="td3" >${g}</td>	
					<td class="td4" >${h}</td>	
					<td class="td1" >${i}</td>	
					<td class="td2" >${j}</td>	
					<td class="td3" >${k}</td>	
					<td class="td4" >${l}</td>	
					<td class="td1" >${m}</td>	
					<td class="td2" >${n}</td>	
					<td class="td3" >${o}</td>	
					<td class="td4" >${p}</td>	
				</tr>
				<% 
					for(int i=1;i<6;i++){
				%>
					<tr>
						<td  class="column" style="width:40px;"><%=i%></td>
						<td id="${a }-<%=i%>"    style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${b }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${c }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${d }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${e }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${f }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${g }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${h }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${i }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${j }-<%=i%>"  style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${k }-<%=i%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${l }-<%=i%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${m }-<%=i%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${n }-<%=i%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${o }-<%=i%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
						<td id="${p }-<%=i%>" style="position: relative;"><span class="speedType"></span><span class="subscribeTag"></span><span class="simlocation"></span><input type="text" readonly="readonly" value="" class="simAlias"/><input type="text" readonly="readonly" value="" class="countryName"/><input type="text" readonly="readonly" value="" class="flow"/><input type="hidden" readonly="" value="" class="imsi"/></td>
					</tr>
				<%} %>
			</table>
			</div>
		</DIV>
	</DIV>
	</SECTION></SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript">
	/* 	$(function() {
			
			$("#batchsubmit").click(function() {
				 if ($("#file").val() == "") {
					$("#D_display").css("display", "none");
				} else {
					$("#D_display").css("display", "block");
				} 
			});

		}) */
		$(function() {
	/* 	 var inputText="";
			var temp = false;
			$("input").dblclick(
				function() {
					temp=true;
					inputText = $(this).val();
					$(this).css({'border' : '1px solid #3c763d','background-color':'none' ,'font-size':'20px'});
					$(this).removeAttr("readOnly");
			});  */
			var serverIP = $("#severIP").find("option:selected").val();
			//alert(serverIP=="192.168.0.160" || serverIP=="192.168.0.162");
			if(serverIP=="192.168.1000.160" || serverIP=="192.168.1000.162"){
				$(".oneTable").css({'display':'none'});
			}else{
				$(".twoTable").css({'display':'none'});//simlocaltion.jsp
			}
			/* li单击事件 */
			$(".simbank li").click(function(){
				var SIMBankNO =$(this).text();
				var serverIP=$("#severIP").find("option:selected").text();
				window.location.href="<%=basePath%>sim/simserver/simLocation?SIMBankNO="+SIMBankNO+"&serverIP="+serverIP;
			});
			$("td").click(function(){
				var id = $(this).attr("id");
				var imsi = $(this).children(".imsi").val();
				if(id!=null && imsi!=''){
					window.location.href="<%=basePath%>/sim/siminfo/siminfoviewserver?imsi="+imsi;
				}
			});
			var countryName;
			$("td").mouseover(function(){
			    countryName = $(this).children(".countryName").val();
				var id = $(this).attr("id");
				var imsi = $(this).children(".imsi").val();
				if(id!=null && imsi!=''){
					$(this).children(".countryName").val(id);
				}
			});
			$("td").mouseout(function(){
					$(this).children(".countryName").val(countryName);
			});
			$("")
		
			/*   $("input").blur(function(){
				 if(temp){
					 $(this).css({'border' : 'none','font-size':'14px'});
					 $(this).attr('readOnly','readonly');
					 var newinputtext = $(this).val();
					 if(newinputtext==""){
						 $(this).css({'background-color' : 'none','border' : 'none' });
						 temp=false;
						 return;
					 }
		      			 if(newinputtext!=inputText){
						
						 alert("开始修改");
						 // 修改完后根据需要改变单元格的颜色 
						 $("input").each(function(index){ //取得整个页面的input值 
								if($(this).val()==""){
									 $(this).css({'background-color' : 'none','border' : 'none' });
								}else if($(this).val()!=""){
									 $(this).css({'background-color' : '#a48ad4','border' : 'none' });
								}
						 }); 
						temp=false;
					 }else{
						 temp=false;
						 $(this).css({'background-color' : '#a48ad4','border' : 'none' });
						 
					 }
				 } 
			});  */
			/*  $("input").each(function(index){ //取得整个页面的input值 
				if($(this).val()=="111"){
					 $(this).css({'background-color' : '#a48ad4','border' : 'none' });
				}
			 });  */ 
			lise();
	        send();
		});
		/* 控制li背景颜色 */
		function lise(){
			var a =  ${a };
			$("ul li").removeClass("bj");
		 	if(a==1){
				$(".li1").addClass("bj");
			}else if(a==5 ||a==17){
				$(".li2").addClass("bj");
			}else if(a==9 ||a==33){
				$(".li3").addClass("bj");
			}else if(a==13 ||a==49){
				$(".li4").addClass("bj");
			}
		 }
		function send(){
			//$("#D_display").css("display", "none");
			var serverIP=$("#severIP").find("option:selected").text();
			var SIMBankNO = $(".bj").text();
			//alert(SIMBankNO);
        	$.ajax({
        		type:"GET",
        		url:"<%=basePath%>sim/simserver/getpageinfo?SIMBankNO="+SIMBankNO+"&serverIP="+serverIP+"",
				dataType : 'json',
				success : function(data) {
					$("#D_display").css("display", "none");
					
					 for(var i=0;i<data.length;i++){
						 var simAlias =data[i]["simAlias"];
						 var countryList =data[i]["countryName"];
						 var cardStatus = data[i]["cardStatus"];					
						 var speedType = data[i]["speedType"];
						 var IMSI = data[i]["IMSI"];
						 var value = data[i]["slotNumber"];
						 var planRemainData = data[i]["planRemainData"];//剩余流量
						 var planflow;
						 if(planRemainData>=1048576){
							 planflow=(planRemainData/1048576).toFixed(2)+"G";
						 }else if(planRemainData>=1024){
							 planflow=(planRemainData/1024).toFixed(2)+"M";
						 }else{
							 planflow=planRemainData+"kb";
						 }
						 var subscribeTag = data[i]["subscribeTag"];
						 var curtd = $("td[id='"+value+"']");
						 var id = $(curtd).attr("id");//undefined
						 if(id!=undefined){
				        	var aa = $(this).attr("id");
				        	$(curtd).children('.simAlias').val(simAlias);
			        		$(curtd).children('.countryName').val(countryList); 
			        		$(curtd).children('.imsi').val(IMSI);
			        		$(curtd).children('.simlocation').html(aa);
			        		
			        		if(planRemainData<51200){
			        			 $(curtd).children('input').css({'color':'red',});
			        			 $(curtd).children('.flow').val(planflow);
			        		}else{
			        			 $(curtd).children('.flow').val(planflow);
			        			 $(curtd).children('input').css({'color':'#333',});
			        		}
			        		if(cardStatus=="不可用"){
								$(curtd).children('input').css({'background-color' : '#fa8564','border' : 'none',});
			        		}else if(cardStatus=="可用"){
								$(curtd).children('input').css({'background-color' : '#95b75d','border' : 'none',});
			        		}else if(cardStatus=="使用中"){
								$(curtd).children('input').css({'background-color' : '#57c8f1','border' : 'none',});
			        		}else if(cardStatus=="停用"){
								$(curtd).children('input').css({'background-color' : '#f3c022','border' : 'none',});
			        		}else if(cardStatus=="下架"){
								$(curtd).children('input').css({'background-color' : '','border' : 'none',});
			        		}
			        		 if(speedType=="0"){
			        			 $(curtd).children('.speedType').css({'display':'block',});
			        			 $(curtd).children('.speedType').html("高");
			        		}
			        		if(subscribeTag=="1"){
			        			$(curtd).children('.subscribeTag').css({'display':'block',});
			        			$(curtd).children('.subscribeTag').html("预");
			        		};
				        	
						 }
						       /*   $("td").each( function () {
						        	var aa = $(this).attr("id");
						        	if(value==aa && aa!=null){
						        		$(this).children('.simAlias').val(simAlias);
						        		$(this).children('.countryName').val(countryList); 
						        		$(this).children('.imsi').val(IMSI);
						        		$(this).children('.simlocation').html(aa);
						        		
						        		if(planRemainData<51200){
						        			 $(this).children('input').css({'color':'red',});
						        			 $(this).children('.flow').val(planflow);
						        		}else{
						        			 $(this).children('.flow').val(planflow);
						        		}
						        		if(cardStatus=="不可用"){
											$(this).children('input').css({'background-color' : '#fa8564','border' : 'none',});
						        		}else if(cardStatus=="可用"){
											$(this).children('input').css({'background-color' : '#95b75d','border' : 'none',});
						        		}else if(cardStatus=="使用中"){
											$(this).children('input').css({'background-color' : '#57c8f1','border' : 'none',});
						        		}else if(cardStatus=="停用"){
											$(this).children('input').css({'background-color' : '#f3c022','border' : 'none',});
						        		}else if(cardStatus=="下架"){
											$(this).children('input').css({'background-color' : '','border' : 'none',});
						        		}
						        		 if(speedType=="0"){
						        			 $(this).children('.speedType').css({'display':'block',});
						        			 $(this).children('.speedType').html("高");
						        		}
						        		if(subscribeTag=="1"){
						        			$(this).children('.subscribeTag').css({'display':'block',});
						        			$(this).children('.subscribeTag').html("预");
						        		};
						        	};
						        	
						        });  */
						};
				}
			});
        }
		function sendd(){
			var serverIP=$("#severIP").find("option:selected").text();
			window.location.href="<%=basePath%>sim/simserver/simLocation?SIMBankNO=SIMBank1-4&serverIP="+serverIP;
		};
	</script>
	    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>