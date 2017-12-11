<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>编辑SIM卡充值记录-EASY2GO ADMIN</title>
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
          <h4 class="form-title">更新SIM卡充值记录</h4>
        </div>
        <div class="panel-body">
          <form id="edit_form" role="form" action="" method="post"
          autocomplete="off" class="form-horizontal">            
            <input type="hidden" name="rechargeBillID" value="${Model.rechargeBillID}">
            <input type="hidden" name="SIMinfoID" value="${Model.SIMinfoID}">
<%--            <input type="hidden" name="MCC" value="${Model.MCC}">
            <input type="hidden" id="countryName" name="${Model.countryName}">
            <input type="hidden" name="operatorName" value="${Model.operatorName}"> --%>
            <div class="form-group">
              <label for="IMSI" class="col-md-3 control-label">IMSI:</label>
              <div class="col-md-6">
                <input readonly="readonly" id="IMSI" type="text" name="IMSI" value="${Model.IMSI}" data-popover-offset="0,8"
                class="form-control">
                <table class="table table-bordered"><tbody><tr><td width="30%">SIM卡使用类型:</td><td>${Model.SIMCategory}</td></tr>
<tr><td>SIM卡ID:</td><td>${Sim.SIMinfoID}</td></tr>
<tr><td width="30%">国家:</td><td><span class="label label-success label-xs">${Model_MCCCountryName}</span></td></tr>
<tr><td>运营商:</td><td><span class="label label-success label-xs">${Sim.trademark}</span></td></tr>
<tr><td>最近设备机身码:</td><td><span class="label label-success label-xs">${Sim.lastDeviceSN}</span></td></tr></tbody></table>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* 如果想修改此项, 应该新建记录或者新建后删除本记录 </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="rechargeAmount" class="col-md-3 control-label">充值金额:</label>
              <div class="col-md-6">
                <input id="rechargeAmount" type="text" name="rechargeAmount" value="${Model.rechargeAmount}" data-popover-offset="0,8"
                required class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">充值后有效期：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <input type="text" name="rechargedValidDate" value="${Model.rechargedValidDate}" data-popover-offset="0,8" required
                  class="form_datetime form-control">
                </div>
              </div>
              <div class="col-md-3">
                <p class="form-control-static"><span class="red">* </span>
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
    <script>$(function () {
    $(".form_datetime").datetimepicker({
        format: 'YYYY-MM-DD HH:mm:ss',
        pickDate: true,     //en/disables the date picker
        pickTime: true,     //en/disables the time picker
        showToday: true,    //shows the today indicator
        language:'zh-CN',   //sets language locale
        defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
    });
});</script>
    <script type="text/javascript">$(function(){    
    $("#edit_form").validate_popover({
      rules: {
        'remark': {
          required: false,
          maxlength: 99
        },
        'rechargeAmount':{required:true,number:true},
      },
      messages: {
        'remark': {
          maxlength: "最多不超过100字"
        },
        'rechargeAmount':{required:"必填字段",number:"请输入数字"},
      },
        submitHandler: function(form){
            $.ajax({
            type:"POST",
            url:"<%=basePath %>sim/simrechargebill/save",
            dataType:"html",
            data:$('#edit_form').serialize(),
            success:function(data){
                result = jQuery.parseJSON(data);
                if (result.code == 0) { // 成功保存
                    easy2go.toast('info', result.msg);
                    var redirect = "<%=request.getHeader("Referer") %>";
	                   if(redirect==null) {
	                     //不再需要从返回结果查询result.simid
	                     window.location.href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=${Model.SIMinfoID}&SIMCategory=${Model.SIMCategory}"
	                   } else {
	                     window.location.href=redirect;
	                   } 
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