<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>编辑运营商-数据字典管理-流量运营中心</title>
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
      <div class="panel">
        <div class="panel-heading">
          <h4 class="form-title">更新运营商信息</h4>
        </div>
        <div class="panel-body">
          <form id="edit_form" role="form" action="" method="post"
          autocomplete="off" class="form-horizontal">
            <input type="hidden" name="operatorID" value="${Model.operatorID}">
            <div class="form-group">
              <label for="operatorName" class="col-md-3 control-label">名称:</label>
              <div class="col-md-6">
                <input id="operatorName" type="text" name="operatorName" maxlength="20" value="${Model.operatorName}" data-popover-offset="0,8"
                required class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">*</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="select_country" class="col-sm-3 control-label">国家：</label>
              <div class="col-sm-6">
                <select id="select_country" name="countryID" required class="form-control">
                <c:forEach items="${Countries}" var="country" varStatus="status"><c:if test="${country.countryID eq Model.countryID}"><option id="${country.countryCode}" value="${country.countryID}" selected>${country.countryName}</option></c:if><c:if test="${country.countryID ne Model.countryID}"><option id="${country.countryCode}" value="${country.countryID}">${country.countryName}</option></c:if></c:forEach>
                </select>
                <input type="hidden" id="countryName" name="countryName"><!-- TODO: 根据前面select值设置 -->
                <input type="hidden" id="MCC" name="MCC">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* 运营商所在的国家</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="operatorSite" class="col-md-3 control-label">网址:</label>
              <div class="col-md-6">
                <input id="operatorSite" type="text" name="operatorSite" maxlength="100" value="${Model.operatorSite}" data-popover-offset="0,8"
                class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="remark" class="col-md-3 control-label">备注:</label>
              <div class="col-md-6">
                <textarea id="remark" rows="3" name="remark" maxlength="99" data-popover-offset="0,8"
                class="form-control">${Model.remark}</textarea>
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
//     $("#edit_form").validate_popover({
//       rules: {
//         'serverCode': {
//           required: true,
//           minlength: 3,
//           maxlength: 32
//         },
//         'IP': {
//           required: true,
//           minlength: 7,
//           maxlength: 15
//         },
//         'remark': {
//           required: false,
//           maxlength: 120
//         },
//       },
//       messages: {
//         'serverCode': {
//           required: "请输入服务器编号.",
//           minlength: "至少输入3个字符",
//           maxlength: "最多不超过70个字符."
//         },
//         'IP': {
//           required: "请输入服务器IP地址.",
//           minlength: "至少输入7个字符",
//           maxlength: "最多不超过15个字符."
//         },
//         'remark': {
//           maxlength: "最多不超过120字"
//         }
//       }
//     });
	$("#edit_form").submit(function() {
		$("#MCC").val($("#select_country").find("option:selected").attr("id"));
	    $("#countryName").val($("#select_country").find("option:selected").text());
		$.ajax({
			type:"POST",
			url:"<%=basePath %>sim/operatorinfo/save",
			dataType:"html",
			data:$('#edit_form').serialize(),
			success:function(data){
				result = jQuery.parseJSON(data);
				if (result.code == 0) { // 成功保存
					easy2go.toast('info', result.msg);
					window.location.href="<%=basePath %>sim/operatorinfo/index";					
				} else {
					easy2go.toast('error', result.msg);
				}
			}
		});
		return false;
	});
	});</script>
	
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>