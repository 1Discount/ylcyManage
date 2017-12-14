<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>编辑用户-用户管理-流量运营中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
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
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">
<H2 class="panel-title">修改用户信息</H2></DIV>
<DIV class="panel-body">
<FORM id="user-form" class="form-horizontal" role="form" method="post" 
action=""><INPUT name="userID" value="${userinfo.userID}" type="hidden">
<DIV class="form-group"><LABEL class="form-label col-md-3 control-label" for="text">姓名:</LABEL>
<DIV class="col-md-5"><INPUT id="user_name" class="form-control" name="userName" required="required"  value="${userinfo.userName}"
maxLength="32" type="text"></DIV></DIV>
<DIV class="form-group"><LABEL class="form-label col-md-3 control-label"  for="text">手机号:</LABEL>
<DIV class="col-md-5"><INPUT id="user_phone" class="form-control" name="phone" value="${userinfo.phone}"
maxLength="32" type="text"></DIV></DIV>
<DIV class="form-group">
<DIV class="col-md-offset-3 col-md-5"><div class="btn-toolbar"><BUTTON class="btn btn-primary" type="submit">保存</BUTTON><BUTTON class="btn btn-primary" onclick="showpwd()" id="ifshowpwd" type="button">重置密码</BUTTON><button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button></DIV></DIV></DIV></FORM>

<form id="pw_form" style="display:none;" action="#" method="post" role="form" class="form-horizontal"><input type="hidden" name="userID" value="${userinfo.userID}"><input type="hidden" name="_method" value="put">
<div class="form-group">
<label class="col-md-3 control-label">新密码：</label><div class="col-md-5">
<input id="user_new_password" type="password" name="user_new_password" class="form-control"></div></div>
<div class="form-group"><label class="col-md-3 control-label">密码确认：</label><div class="col-md-5"><input id="password" type="password" name="password" class="form-control"></div></div>
<div class="form-group"><div class="col-md-offset-4 col-md-3"><button type="submit" class="btn btn-primary">修改</button><button type="button" onclick="hidepwd()" class="btn btn-default">取消</button></div></div></form>

</DIV></DIV></DIV></SECTION></SECTION></SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script>
    //var curMenu = "${MenuPath}".substring(1).replace(/\//g, "\\\/").replace(/\?/g, "\\?").replace(/\=/g, "\\\=");
    //var menuGroup = $("#" + curMenu).parent().siblings().filter(":first").attr("id");
    var curMenu="<%=request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping")%>".substring(1);
    var parentPathBase = curMenu.match(/^\w+\/\w+/);
    var menuGroup = $("li[id*=\"" + parentPathBase[0].replace(/\//g, "\\\/") + "\"]").filter(":first").parent().siblings().filter(":first").attr("id");
    curMenu = curMenu.replace(/\//g, "\\\/").replace(/\?/g, "\\?").replace(/\=/g, "\\\=");
    var menu = {};
    menu[menuGroup] = true;
    menu[curMenu] = true;
    activeMenus();
    </script>
    <SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    
    /* function GetQueryString(name)//获取地址栏参数的值
    {
         var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
         var r = window.location.search.substr(1).match(reg);
         if(r!=null)return  unescape(r[2]); return null;
         
    }
    
    // 常见弱密码
    jQuery.validator.addMethod("notweak", function(value, element) {  
        var weak = /^((1234567890)|(0987654321)|(1234554321)|(1234512345)|(0{10,20})|(0{10,20})|(1{10,20})|(2{10,20})|(3{10,20})|(4{10,20})|(5{10,20})|(6{10,20})|(7{10,20})|(8{10,20})|(9{10,20}))?$/;  
        return !(this.optional(element) || (weak.test(value)));
    }, "请使用更复杂的密码");
    // 手机号码验证  
    jQuery.validator.addMethod("notmobile", function(value, element) {  
        var length = value.length;  
        var mobile =  /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        return !(this.optional(element) || (length == 11 && mobile.test(value)));  
    }, "请勿直接使用手机号码,可尝试添加前后缀或更复杂的密码");
    // 电话号码验证
    jQuery.validator.addMethod("notphone", function(value, element) {
    	// var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
        var tel = /^(0[0-9]{2,3}\-{0,1})?([2-9][0-9]{6,7})+(\-{0,1}[0-9]{1,4})?$/;
        return !(this.optional(element) || (tel.test(value)));  
    }, "请勿直接使用电话号码,可尝试添加前后缀或更复杂的密码"); */

    $(function(){
    	$("#pw_form").validate_popover({
    		rules:{
    			'user_new_password':{ required:true, minlength:5,maxlength:16 },
    			'old_password':{ required:true },
    			'user_new_password':{ required:true, minlength:10,maxlength:32,
    				notweak:true,
    				notmobile:true,
    				//notphone:true // 不好控制,先不要考虑公司的实际电话
    				},    			
    			'password':{ required:true, equalTo:'#user_new_password'},
    		},
    		messages:{
    			
    			/* 'old_password':{ required:"请输入目前的密码！" },
    			'user_new_password':{required:"请输入新密码！",
    				minlength:"至少10个字符", // 5
    				maxlength:"少于32个字符"}, // 32 */
    			'user_new_password':{required:"请输入新密码！",
    				minlength:"至少5个字符",
    				maxlength:"少于16个字符"},
    			'password':{required:"请再次输入相同的密码！", equalTo:'两次输入的密码不一致！'},
            },
		    submitHandler: function(form){
		        $.ajax({
		            type:"POST",
		            url:"<%=basePath %>distributor/adminuserinfo/pwdedit",
		            dataType:"html",
		            data:$('#pw_form').serialize(),
		            success:function(data){
		                if(data=="修改密码成功"){
		                	
		                    bootbox.alert("密码修改成功，现在去重新登录",function(){
		                        window.location="<%=basePath %>login.jsp";
		                    });

		                }else{
		                    easy2go.toast('info',data);
		                }
		            }
		        });
		    }
            });
    	
	//异步提交发邮件表单.
	$("#user-form").submit(function(){
		
		$.ajax({
			type:"POST",
			url:"<%=basePath %>distributor/adminuserinfo/useredit",
			dataType:"html",
			data:$('#user-form').serialize(),
			success:function(data){
				easy2go.toast('info',data);
			}
		});
		return false;
	});
	
});
    
    function showpwd(){
  	  $("#pw_form").show();
  	  $("#ifshowpwd").hide();
  	  
  	}
    function hidepwd(){
    	  $("#pw_form").hide();
    	  $("#ifshowpwd").show();
    	  
    	}
    </SCRIPT>
<%--<c:if test="${empty IsWeakPwd}">--%>
<%--    <script type="text/javascript">--%>
<%--    var isweak = false;--%>
<%--    </script>--%>
<%--</c:if>--%>
<%--<c:if test="${not empty IsWeakPwd}">--%>
    <script type="text/javascript">
    var isweak = GetQueryString("IsWeakPwd") == '1';
    $(function(){
    	if(isweak){
	    	showpwd();
	    	$('#weakPwdTips').show();
    	}
    });
    </script>
<%--</c:if>--%>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>