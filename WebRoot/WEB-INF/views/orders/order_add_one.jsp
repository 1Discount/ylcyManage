<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>新建订单第一步-订单管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
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
<DIV class="panel-heading">新订单（第一步）：客户</DIV>
<DIV class="panel-body">
<H4>搜索或添加客户</H4>
<FORM id="order_form" class="form" role="form" method="post" action="<%=basePath %>orders/ordersinfo/gotwo">
<DIV class="row">
<DIV class="col-md-4">
<DIV class="panel">
<DIV class="panel-body">
<DIV class="form-group">
<P class="form-control-static">搜索客户</P></DIV>
<DIV class="form-group"><INPUT id="client_rs_input" class="form-control" name="p" 
maxLength="24" type="text" placeholder="输入客户名称或者电话"></DIV>
<DIV class="form-group"><BUTTON id="search" class="btn btn-primary" type="button">搜索</BUTTON></DIV>
<DIV class="form-group">
<DIV id="client" class="col-md-12">

<!-- <DIV class="checkbox">

<a class="btn but-xs btn-success"><span class="glyphicon glyphicon-ok"></span></a>
<span></span>
<span>客户名称:汪博</span>
<span>手机号：234242424</span>
</DIV> -->

</DIV></DIV></DIV></DIV></DIV>
<DIV class="col-md-6">
<DIV class="panel">
<DIV class="panel-body">
<DIV class="form-group">
<P class="form-control-static">添加客户</P></DIV>
<DIV class="form-group"><LABEL for="client_name">称呼:<SPAN 
class="red">*</SPAN></LABEL><INPUT id="client_name" class="client form-control" 
name="customerName" maxLength="16" type="text" data-popover-offset="0,8"></DIV>

<DIV class="form-group"><LABEL for="client_email">Email:</LABEL><INPUT id="client_email" 
class="client form-control" name="email" type="text" placeholder="可不填" 
data-popover-offset="0,8"></DIV>

<DIV class="form-group">
    <LABEL class="" for="client_customerSource">客户来源：</LABEL>
<%--     <INPUT id="client_customerSource" class="form-control" name="customerSource" value="${cus.customerSource}" maxLength="20" type="text" data-popover-offset="0,8"> --%>
<!--     <select class="form-control" id="cusresource" name="customerSource" onchange="return myselect();"> -->
    <select class="form-control" id="cusresource" name="customerSource">
       <option value="">请选择客户来源</option>
       <c:forEach var="dic" items="${diclist}">
		<option value="${dic.label }">${dic.label }</option>
	</c:forEach>
    </select>
</DIV>
<!-- <DIV class="form-group"  id="qds" style="display:none;"> -->
<DIV class="form-group"  id="qds">
    <LABEL class="" for="client_distributorName">渠道商：</LABEL>
  
    <select class="form-control" id="client_distributorName" name="distributorID">
       <option value="">请选择渠道商</option>
       <c:forEach var="dic" items="${distributor}">
		<option value="${dic.distributorID},${dic.operatorName }">${dic.operatorName }</option>
	</c:forEach>
    </select>
</DIV>
<DIV style="display: none;" class="form-group">
    <LABEL class=" control-label" for="client_password">密码：(用户登录官网密码)<SPAN 
class="red">*</SPAN></LABEL>
  
    <INPUT id="client_password" class="form-control" name="password" value="88888888" maxLength="20" type="text" data-popover-offset="0,8">
    <input style="padding-top:4px;" id="ckbox" type='checkbox' name='VoteOption1' value='88888888' onclick="checkbox_pwd()"> &nbsp;&nbsp;使用随机密码
  
</DIV>

<DIV class="form-group"><LABEL for="client_phone">电话/手机:<SPAN 
class="red" id="tip_phone">*</SPAN></LABEL><INPUT id="client_phone" class="client form-control" 
name="phone"maxLength="20" type="text" data-popover-offset="0,8"></DIV>
<DIV class="form-group"><LABEL class="control-label" 
for="client_address">收货地址：<SPAN class="red">*</SPAN></LABEL><INPUT id="client_address" 
class="client form-control" name="address" maxLength="64" type="text" 
data-popover-offset="0,8"></DIV>
<DIV class="form-group"><LABEL class="control-label" 
for="client_desc">备注：</LABEL><TEXTAREA id="client_desc" class="client form-control" rows="3" name="remark" data-popover-offset="0,8" maxlength="255"></TEXTAREA></DIV></DIV></DIV></DIV></DIV>
<DIV class="row">
<HR>

<DIV class="col-md-4 col-md-offset-4">
<DIV class="btn-toolbar"><BUTTON id="next" class="btn btn-primary"  disabled=""
type="submit">下一步</BUTTON><BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" 
type="button">返回</BUTTON></DIV></DIV></DIV></FORM></DIV></DIV></DIV></SECTION></SECTION></section>
<script src="<%=basePath %>static/js/app.min.js?20150209"></script>
<SCRIPT type="text/javascript">
if('${mes}'!=null && '${mes}'!=''){
	
	easy2go.toast('warn','${mes}');
}
var phoneFlag=false;
var scsum=0;
var prevent_form_db = false;
function enableClientForm(){
	//$('#order_form').attr('action','/temporders')
	$("#order_form").validate_popover({
		rules:{
			'customerName':{ required:true, minlength:2,maxlength:16 },
			'phone':{ required:true, minlength:5,maxlength:16 },
			'password':{ required:true, minlength:8,maxlength:16 }
		},
		messages:{
			'customerName':{
				required:"请输入称呼！",
				minlength:"至少输入2个字符！",
				maxlength:"最多不超过16个字符！"
			},
			'phone':{
				required:"请输入电话/手机！",
				minlength:"至少输入5个数字！",
				maxlength:"最多不超过16个数字！"
			},
			'password':{
				required:"请输入密码！",
				minlength:"至少输入8个字符!",
				maxlength:"最多不超过16个字符！"
			}
		}});
}

function searchClient(){
	var keyword = $('#client_rs_input').val().trim();
	if(keyword==''){
		easy2go.toast('warn','输入客户名称或者电话，然后点击搜索');
		return;
	}
	if(prevent_form_db)
		return;
	prevent_form_db = true;
	
	$.ajax({
		url:'<%=basePath %>orders/ordersinfo/getcustomer',
		data: {p: $('#client_rs_input').val()},
		dataType: 'json',
		type: "POST",
		success: function(data)	{
				if(data==null){
					
				}
		else if(data.length>0){
					//alert(data[0].customerName);
					$('#client_rs_input').val('');
					$('#client').html('');
					for(var i=0;i<data.length;i++){
						scsum++;
						var cdiv1='<DIV id="'+data[i].customerID+'" class="checkbox">';
						var btn='<a class="btn but-xs btn-success" href="<%=basePath %>orders/ordersinfo/gotwo?customerID='+data[i].customerID+'" ><span class="glyphicon glyphicon-ok"></span></a>';
						var span1='<span>&nbsp;&nbsp;<input type="hidden" id="customer_ID" name="customerID" value="'+data[i].customerID+'" /></span>';
						var span2='<span>姓名:'+data[i].customerName+'</span>&nbsp;&nbsp;';
						var span3='<span>手机号:'+data[i].phone+'</span>';
						var cdiv2='</DIV>';
						//alert(cdiv1+btn+span1+span2+span3+cdiv2);
						
						$('#client').append(cdiv1+btn+span1+span2+span3+cdiv2);
					}
					//$('#client').html(data.html);
					if(scsum==1){
						$('#next').prop('disabled',false);
					}
					scsum=0;
					//$('#order_form').attr('action','/temporders');
				}else{
					$('#client').html("<p class='form-control-static'><span class='red'>没有相关客户信息</span></p>");
					easy2go.toast('err',"查询失败!");
				}
			},
		error: function(xhr,textStatus,error){
			console.log(error);
			$('#next').prop('disabled',true);
			if(xhr.status==404)
				$('#client').html("<p class='form-control-static'><span class='red'>没有相关客户信息</span></p>");
			else{
				$('#client').html('');
				easy2go.toast('err',"出错了："+xhr.responseJSON.error);
			}
		}
	});
	window.setTimeout(function(){
		prevent_form_db=false;
	},600);
}

$('.client').change(function(){
	if($('#client_name').val().trim()!= '' &&  $('#client_address').val().trim()!='' && $('#client_phone').val().trim()!= '' && phoneFlag){
		$('#next').attr('disabled',false);
		enableClientForm();
	}
});

$('#search').click(searchClient);
$('#client_rs_input').keyup(function(event){
	if(event.which == 13 || event.keyCode == 13){
		searchClient();
	}
	return false;
});

$("#client_phone").blur(function(){
	if($("#client_phone").val().trim()==''){
		$("#tip_phone").html("*");
		return; 
	}
	/* var reg=/^(13[0-9]{9})|(15[0-9][0-9]{8})|(14[0-9][0-9]{8})|(18[0-9][0-9]{8})|(17[0-9][0-9]{8}|)$/;
	if(!reg.test($("#client_phone").val())){
		$("#tip_phone").html("*手机号不合法");
        phoneFlag=false;
        return;
	} */
	
	$.ajax({
		type : "POST",
		url : "<%=basePath%>customer/customerInfolist/customerInfockPhone",
		dataType : "html",
		data :{'phone':$("#client_phone").val()},
		success : function(data) {
		    msg = data;
			data = parseInt(data);
			      if (data >= 1) 
			       {
    			        $("#tip_phone").html("*此手机号码已被注册");
    			        phoneFlag=false;
					} else if (data == 0) {
						$("#tip_phone").html("*此手机号码可用");
						phoneFlag=true;
					}else {
					    
					}
				}
			});
	
});


function checkbox_pwd(){
	document.getElementById("client_password").value="88888888";
	if(!document.getElementById("ckbox").checked)
	{
    	document.getElementById("client_password").value="";
	}
}




</SCRIPT>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>