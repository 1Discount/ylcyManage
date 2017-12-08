<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>添加客户-客户管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
    <style type="text/css">
       .col-md-6{width:30%;}
    </style>
  </head>
 <body>

    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

 <SECTION id="main-content">
<SECTION class="wrapper">
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">添加客户信息</DIV>
<DIV class="panel-body">

<!-- ====================================================================== -->
<!-- <FORM id="client_form" class="form-horizontal" role="form" method="post" action="/clients" autocomplete="off"> -->

<FORM id="order_form" class="form-horizontal" role="form"  method="post" autocomplete="off">
<!-- <INPUT name="_csrf" value="cUpsmXh2-WBHV_jGrENK_XXtTJyHVvNqXINU" type="hidden"> -->

<DIV class="form-group">
     <LABEL class="col-md-3 control-label" for="client_name">称呼：</LABEL>
  <DIV class="col-md-6">
    <INPUT id="client_name" class="client form-control"   name="customerName" value="${cus.customerName}" maxlength="24" type="text" required data-popover-offset="0,8">
  </DIV>
  <DIV class="col-md-3">
    <P class="form-control-static">
         <SPAN class="red" style="color:red;" id="customer_default">*</SPAN>
    </P>
   </DIV>
</DIV>


<DIV class="form-group">
  <LABEL class="col-md-3 control-label" for="client_phone">手机：</LABEL>
  <DIV class="col-md-6">
<%--      <INPUT id="client_phone" class="form-control" onblur="nullck_ph();" name="phone" value="${cus.phone}" maxlength="20" type="text" required="" data-popover-offset="0,8"> --%>
     <INPUT id="client_phone" class="client form-control"  name="phone" value="${cus.phone}" maxlength="20" type="text" required data-popover-offset="0,8" onblur="return sendpdata();">
  </DIV>
  <DIV class="col-md-3">
     <P class="form-control-static">
        <SPAN class="phone_default"   style="color:red;" id="phone_default">*</SPAN>
     </P>
  </DIV>
</DIV>

    <INPUT id="client_password" type="hidden" placeholder="用户登录官网密码" class="form-control" name="password" value="88888888" required minlength="5" maxlength="20" type="text" data-popover-offset="0,8">

<DIV class="form-group">
    <LABEL class="col-md-3 control-label" for="client_email">Email：</LABEL>
  <DIV class="col-md-6">
    <INPUT id="client_email" class="form-control" name="email" value="${cus.email}" maxlength="20" type="text" data-popover-offset="0,8">
  </DIV>
  <DIV class="col-md-3">
    <P class="form-control-static">可不填</P>
  </DIV>
</DIV>
<DIV class="form-group">
    <LABEL class="col-md-3 control-label" for="client_customerSource">客户类型：</LABEL>
  <DIV class="col-md-6">
         <select class="form-control" id="customerType" name="customerType" data-popover-offset="0,8">
<!--              <option>请选择客户类型</option> -->
             <option>重要客户</option>
             <option selected="selected">普通客户</option>
             <option>合作伙伴</option>
         </select>
  </DIV>
</DIV>
<DIV class="form-group">
    <LABEL class="col-md-3 control-label" for="client_customerSource">客户来源：</LABEL>
  <DIV class="col-md-6">
         <select class="form-control" id="cusresource" name="customerSource" data-popover-offset="0,8">
           <c:forEach var="dic" items="${dictionary}">
             <c:if test="${dic.label eq '后台'}"><option  selected="selected">${dic.label}</option></c:if>
             <c:if test="${dic.label ne '后台'}"><option>${dic.label}</option></c:if>
           </c:forEach>
         </select>
  </DIV>
</DIV>
<!-- <DIV class="form-group"  id="qds" style="display:none;"> -->
<DIV class="form-group"  id="qds">
    <LABEL class="col-md-3 control-label" for="client_distributorName">渠道商：</LABEL>
  <DIV class="col-md-6">
         <select  class="form-control" onchange="" id="distributorName" name="distributorId">
             <option value="">请选择渠道商</option>
             <c:forEach var="dis" items="${distributor}">
             <option value="${dis.distributorID}">${dis.operatorName}</option>
             </c:forEach>
         </select>
   </DIV>
  <DIV class="col-md-3">
    <P class="form-control-static"> </P>
  </DIV>
</DIV>
<DIV class="form-group">
     <LABEL class="col-md-3 control-label" for="client_address">收货地址：</LABEL>
     <DIV class="col-md-6">
        <INPUT id="client_address" required class="client form-control" name="address" value="${cus.address}" maxlength="100" type="text" data-popover-offset="0,8">
     </DIV>
     <DIV class="col-md-3">
  <SPAN class="red" style="color:red;">*</SPAN>
    <P class="form-control-static"> </P>
  </DIV>
</DIV>

<DIV class="form-group">
   <LABEL class="col-md-3 control-label" for="client_desc">备注：</LABEL>
   <DIV class="col-md-6">
     <input id="client_desc" class="form-control" rows="3" name="remark" value="${cus.remark}" data-popover-offset="0,8" maxlength="99"/>
   </DIV>
</DIV>

 <DIV class="form-group">
  <DIV class="col-md-6 col-md-offset-3">
    <DIV class="btn-toolbar">
          <BUTTON class="btn btn-primary" id="next" disabled="" type="button" onclick="sendurl()">保存</BUTTON>
          <BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
    </DIV>
  </DIV>
</DIV>
</FORM>
<!-- ======================================================================================= -->
<!-- <iframe src="about:blank" name="blankFrame" id="blankFrame" style="display: none;"> -->
<!-- </iframe> -->
<form id="devicedeal_form" role="form" method="POST"
								autocomplete="off"
								action=""
								class="form-horizontal">
<!-- <form method="POST" action="" target="blankFrame" id="testform" type="hidden"> -->
  <input type="hidden" value="" id="phones" name="phone" />
</form>

</DIV>
</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script type="text/javascript">
    var phoneflage=false;
    function sendpdata(){
    	if(document.getElementById('client_phone').value.trim()==""){
    		return;
    	}
    	document.getElementById('phones').value=document.getElementById('client_phone').value.trim();
    	var reg=/^(13[0-9]{9})|(15[0-9][0-9]{8})|(14[0-9][0-9]{8})|(18[0-9][0-9]{8})|(17[0-9][0-9]{8})$/;
    	if(!reg.test($("#client_phone").val())){
//     		alert("*手机号不合法");
    		document.getElementById('phone_default').innerHTML="* &nbsp;请输入正确的手机号码！";
    		//document.getElementById('client_phone').focus();
            return false;
    	}else if(document.getElementById('phones').value.trim()!=""){
    	searchphone();
    	}
    }

     function searchphone(){
		$.ajax({
			type : "POST",
			url : "<%=basePath%>customer/customerInfolist/customerInfockPhone",
			dataType : "html",
			data : $('#devicedeal_form').serialize(),
			success : function(data) {
			    msg = data;
				data = parseInt(data);
				      if (data >= 1)
				       {
	    			        $('.phone_default').empty().append("<span style='color:red;'>此号码已被注册！</span>");
	    			        document.getElementById('client_phone').focus();
	    			        return false;
						} else if (data == 0) {
							$('.phone_default').empty().append("<span style='color:green;'>此号码可使用！</span>");
							phoneflage=true;
							if($('#client_name').val().trim()!='' &&  $('#client_address').val().trim()!='' && $('#client_phone').val().trim()!='' && phoneflage){
					    		$('#next').attr('disabled',false);
					    		//enableClientForm();
					    	}
							return true;
						}else {
						    $('.phone_default').empty().append(msg);
						}
					}
				});
	}

    function qdsid(){
    	var test = document.getElementById('distributorName').selectedIndex;
    	document.getElementById('distributorId').selectedIndex=test;
    }
  <%--   function searchphone()
    {
    	document.getElementById('phones').value=document.getElementById('client_phone').value;
    	if(document.getElementById('phones').value!="")
    	{
    		var sendurl="<%=basePath %>customer/customerInfolist/customerInfockPhone?cusid="+document.getElementById('phones').value;
    		document.getElementById('testform').action=sendurl;
    	    document.getElementById('testform').submit();
    	}

    } --%>

    $('.client').keyup(function(){
    	if($('#client_name').val().trim()!='' &&  $('#client_address').val().trim()!='' && $('#client_phone').val().trim()!='' && phoneflage){
    		$('#next').attr('disabled',false);
    		enableClientForm();
    	}
    });

    function checkbox_pwd(){
        var Num="";
        for(var i=0;i<8;i++){Num+=Math.floor(Math.random()*10);}
    	//alert(Num);
    	document.getElementById("client_password").value=Num;
    	if(!document.getElementById("ckbox").checked)
    	{
        	document.getElementById("client_password").value="";
    	}
    }

    </script>
<script type="text/javascript">
    var redirect = "<%=request.getHeader("Referer") %>"; <%-- "<%=basePath %>customer/customerInfolist/all"; --%>
</script>
    <script type="text/javascript">
//     $("#client_form").validate_popover({
//     	searchphone();

<%-- $.ajax({
			type : "POST",
			url : "<%=basePath%>customer/customerInfolist/customerInfockPhone",
			dataType : "html",
			data : $('#devicedeal_form').serialize(),
			success : function(data) {
			    msg = data;
				data = parseInt(data);
				      if (data >= 1)
				       {
	    			        $('.phone_default').empty().append("<span style='color:red;'>此号码已被注册！</span>");
	    			        document.getElementById('client_phone').focus();
	    			        return false;
						} else if (data == 0) {
							$('.phone_default').empty().append("<span style='color:green;'>此号码可使用！</span>");
							return true;
						}else {
						    $('.phone_default').empty().append(msg);
						}
					}
				}); --%>

        /* rules: {
          'customerName': {
            required: true,
            maxlength: 50
          },
          'password': {
            required: true,
            minlength: 6,
            maxlength: 50
          },
          'email': {
            required: false,
            email: true,
            maxlength: 20
          },
          'customerSource': {
              required: false,
          },
          'distributorName': {
              required: false,
              maxlength: 50
          },
          'address': {
            required: true,
            maxlength: 100
          },
          'remark': {
            required: false,
            maxlength: 99
          }
        },
        messages: {
// 这里不添加, 使用默认提示字符串
          'customerName': {
            required: "请输入单位或个人名称！",
            maxlength: "最多不超过50个字符."
          },
          'phone': {
              required: "请输入手机号码！",
              maxlength: "最多不超过50个字符."
            },
            'password': {
                required: "请输入密码！",
                maxlength: "最多不超过50个字符."
              },
          'remark': {
            maxlength: "最多不超过100字"
          }

<<<<<<< Updated upstream
        }
         */

				function enableClientForm(){
					//$('#order_form').attr('action','/temporders')
					$("#order_form").validate_popover({
						rules:{
							'customerName':{ required:true, minlength:2,maxlength:16 },
							'phone':{ required:true, minlength:5,maxlength:16 },
							'address':{ required:true}
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
							'address':{
								required:"请输入收货地址！",
							}
						}});
				}

        function sendurl(){
    		$("#next").attr("disabled",true);

        	  if($("#client_name").val().trim()=='' ||  $("#client_phone").val().trim()=='' || $("#client_address").val().trim()=='' || !phoneflage){

        		  //easy2go.toast('warn',"请输入必填项");
        		  return;
        	  }
        	 var customerType = $("#customerType option:selected").text();
        	 if(customerType=="请选择客户类型"){
        		 customerType="";
        	 }
  	        $.ajax({
  	            type:"POST",
  	            url:"<%=basePath %>customer/customerInfolist/insertinto",
  	            dataType:"json",
  	            data:$('#order_form').serialize(),
  	            success:function(data){
  	            	//data = parseInt(data);
  	                  if (data=="1") { // 成功保存
   	                     easy2go.toast('info', "添加客户成功！");
<%-- 	                     window.location.href="<%=basePath %>customer/customerInfolist/all"; --%>
  	                  }else {
  	                    easy2go.toast('err', data);}
  	            }
  	        });
        }


//           }
//       });

    </script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
  </body>
</html>