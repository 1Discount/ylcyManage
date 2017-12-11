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
    <title>添加推送-推送消息管理-EASY2GO ADMIN</title>
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
<DIV class="panel-heading">添加推送消息</DIV>
<DIV class="panel-body">
<FORM id="order_form" class="form-horizontal" role="form"  method="post" autocomplete="off">
<DIV class="form-group">
     <LABEL class="col-md-3 control-label" for="client_name">标题：</LABEL>
  <DIV class="col-md-6">
    <INPUT id="client_title" class="client form-control"   name="title" value="${pushMessage.title}" maxlength="24" type="text" required data-popover-offset="0,8">
  </DIV>
  <DIV class="col-md-3">
    <P class="form-control-static">
         <SPAN class="red" style="color:red;" id="customer_default">*</SPAN>
    </P>
   </DIV>
</DIV>

<DIV class="form-group">
  <LABEL class="col-md-3 control-label" for="client_phone">消息内容：</LABEL>
  <DIV class="col-md-6">
     <textarea id="client_content" class="client form-control"  name="content" value="${pushMessage.content}" required data-popover-offset="0,8" rows="10" cols="40"></textarea>
  </DIV>
  <DIV class="col-md-3">
     <P class="form-control-static">
        <SPAN class="phone_default"   style="color:red;" id="phone_default">*</SPAN>
     </P>
  </DIV>
</DIV>

<DIV class="form-group">
    <LABEL class="col-md-3 control-label" for="client_email">推送平台：</LABEL>
  <DIV class="col-md-6">
    <input type="checkbox" name="deviceTypes" value="android">安卓android&nbsp;&nbsp;
    <input type="checkbox" name="deviceTypes" value="ios">苹果IOS
  </DIV>
  <DIV class="col-md-3">
    <P class="form-control-static" style="color:red;" id="phone_default">*</P>
  </DIV>
</DIV>

<DIV class="form-group">
  <LABEL class="col-md-3 control-label" for="client_phone">推送设备机身码：</LABEL>
  <DIV class="col-md-6">
     <textarea id="client_sn" class="client form-control"  name="sn" value="${pushMessage.sn}" required data-popover-offset="0,8" rows="8" cols="30"></textarea>
  </DIV>
  <DIV class="col-md-3">
     <P class="form-control-static">
        <SPAN class="phone_default"   style="color:red;" id="phone_default">*</SPAN>
        <SPAN class="phone_default">多个设备号之间请使用","分割</SPAN>
     </P>
  </DIV>
</DIV>

<%-- <DIV class="form-group">
  <LABEL class="col-md-3 control-label" for="client_phone">推送设备机身码：</LABEL>
  <DIV class="col-md-6">
     <textarea id="client_sn" class="client form-control"  name="sn" value="${pushMessage.sn}" required data-popover-offset="0,8" rows="8" cols="30"></textarea>
  </DIV>
  <DIV class="col-md-3">
     <P class="form-control-static">
        <SPAN class="phone_default"   style="color:red;" id="phone_default">*</SPAN>
        <SPAN class="phone_default">多个设备号之间请使用","分割</SPAN>
     </P>
  </DIV>
</DIV> --%>

<DIV class="form-group">
   <LABEL class="col-md-3 control-label" for="client_desc">备注：</LABEL>
   <DIV class="col-md-6">
     <textarea id="client_desc" class="client form-control"  name="remark" value="${pushMessage.remark}" required data-popover-offset="0,8" rows="5" cols="30"></textarea>
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
<form id="devicedeal_form" role="form" method="POST"
								autocomplete="off"
								action=""
								class="form-horizontal">
  <input type="hidden" value="" id="phones" name="phone" />
</form>

</DIV>
</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script type="text/javascript">var redirect = "<%=request.getHeader("Referer") %>";</script>
    <script type="text/javascript">
    
    $('.client').keyup(function(){
    	if($('#client_title').val().trim()!='' &&  $('#client_content').val().trim()!='' && $('#client_').val().trim()!='' || $("input[type='checkbox']:checked").length<=0){
    		$('#next').attr('disabled',false);
    		enableClientForm();
    	}
    });
	function enableClientForm(){
		//$('#order_form').attr('action','/temporders')
		$("#order_form").validate_popover({
		rules:{
			'title':{ required:true, minlength:5,maxlength:99 },
			'content':{ required:true, minlength:10,maxlength:999 },
			'sn':{ required:true},
			'deviceTypes':{required:true}
		},
		messages:{
			'title':{
				required:"请输入标题！",
				minlength:"至少输入5个字符！",
				maxlength:"最多不超过100个字符！"
			},
			'content':{
				required:"请输入要推送的内容！",
				minlength:"至少输入10个字符！",
				maxlength:"最多不超过1000个字符！"
			},
			'sn':{
				required:"请输入设备sn",
			},
			'deviceTypes':{
				required:"请选中推送平台",
			}
		}});
	}

        function sendurl(){
    		$("#next").attr("disabled",true);
        	  if($("#client_title").val().trim()=='' ||  $("#client_content").val().trim()=='' || $("#client_sn").val().trim()=='' || $("input[type='checkbox']:checked").length<=0){
        		  easy2go.toast('warn',"请输入必填项");
        		  return;
        	  }
  	        $.ajax({
  	            type:"POST",
  	            url:"<%=basePath %>pushMessage/addPushMessage",
  	            dataType:"json",
  	            data:$('#order_form').serialize(),
  	            success:function(data){
  	                  if (data=="1") { // 成功保存
   	                     easy2go.toast('info', "添加推送消息成功！");
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