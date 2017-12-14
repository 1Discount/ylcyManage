<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>添加服务器-SIM服务器管理-流量运营中心</title>
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
      <section id="main-content">
        <section class="wrapper">
          <%-- 这里添加页面主要内容  block content --%>
    <div class="col-md-12">
<!--       <div class="panel"> -->
<!--         <div class="panel-heading"> -->
<!--           <h3 class="panel-title">Search</h3> -->
<!--         </div> -->
<!--         <div class="panel-body"> -->
<!--           <form role="form" action="/simservers" method="get" class="form-inline"> -->
<!--             <div class="form-group"> -->
<!--               <input type="text" name="code" placeholder="编号" class="form-control"> -->
<!--             </div> -->
<!--             <div class="form-group"> -->
<!--               <button type="submit" class="btn btn-primary">Search</button> -->
<!--             </div> -->
<!--           </form> -->
<!--         </div> -->
<!--       </div> -->
      <div class="panel">
        <div class="panel-heading">
          <h4 class="form-title">添加SIM服务器</h4>
        </div>
        <div class="panel-body">
          <form id="edit_form" role="form" action="" method="post"
          autocomplete="off" class="form-horizontal">
            <input type="hidden" name="_csrf" value="QLaKdKJy-zJ2Cd9BWFfWknPv6_QqFwXns5rg">
            <div class="form-group">
              <label for="sim_server_code" class="col-md-3 control-label">编号:</label>
              <div class="col-md-6">
                <input id="sim_server_code" type="text" name="serverCode" data-popover-offset="0,8"
                required class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">*</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="sim_server_ip" class="col-md-3 control-label">IP地址:</label>
              <div class="col-md-6">
                <input id="sim_server_ip" type="text" name="IP" data-popover-offset="0,8"
                required class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="simserver_status" class="col-md-3 control-label">状态:</label>
              <div class="col-md-6">
                <select id="simserver_status" name="serverStatus" required class="form-control">
    <c:forEach items="${ServerStatusDict}" var="item" varStatus="status"><option value="${item.label}">${item.label}</option></c:forEach>                                   
                </select>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">*</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="sim_server_desc" class="col-md-3 control-label">备注:</label>
              <div class="col-md-6">
                <textarea id="sim_server_desc" rows="3" name="remark" maxlength="99" data-popover-offset="0,8"
                class="form-control"></textarea>
              </div>
              <div class="col-sm-3"></div>
            </div>
            <div class="form-group">
              <div class="col-md-6 col-md-offset-3">
                <div class="btn-toolbar">
                  <button type="submit" class="btn btn-primary">保存</button>
                  <button type="button" onclick="javascript:history.go(-1);"
                  class="btn btn-default">返回</button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>

        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">$(function(){
    // IP地址验证
    jQuery.validator.addMethod("ip", function(value, element) {  
        if(value.trim()==''){
        	return fasle;
        }else{
        	return true;
        }
    }, "请输入IP获取域名"); 
    $("#edit_form").validate_popover({
      rules: {
        'serverCode': {
          required: true,
          maxlength: 5
        },
        'IP': {
          required: true,
//           minlength: 7,
//           maxlength: 15,
          ip: true
        },
        'remark': {
          required: false,
          maxlength: 99
        }
      },
      messages: {
        'serverCode': {
          required: "请输入服务器编号.",
          maxlength: "最多不超过5个字符."
        },
        'IP': {
          required: "请输入服务器IP地址.",
//           minlength: "至少输入7个字符",
//           maxlength: "最多不超过15个字符."
        },
        'remark': {
          maxlength: "最多不超过100字"
        }

      },
        submitHandler: function(form){
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath %>sim/simserver/save",
	            dataType:"html",
	            data:$('#edit_form').serialize(),
	            success:function(data){
	                result = jQuery.parseJSON(data);
	                if (result.code == 0) { // 成功保存
	                    easy2go.toast('info', result.msg);
	                    window.location.href="<%=basePath %>sim/simserver/index";                   
	                } else {
	                    easy2go.toast('error', result.msg);
	                }
	            }
	        });
        }
    });

	});</script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>