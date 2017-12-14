<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>反馈押金申请记录-设备管理-流量运营中心</title>
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
    
    <c:if test="${WithdrawView}">
    <div class="panel">
        <div class="panel-heading">
          <h4>退押金</h4>
        </div>
        <div class="panel-body">
          <form id="deposit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
            <input type="hidden" name="recordID" value="${Model.recordID}">
            
            <div class="form-group">
              <label for="company" class="col-md-3 control-label">说明:</label>
              <div class="col-md-6" style="margin-top:8px;">
              请根据实际情况退还押金到客户的支付宝帐户。若有需要扣费用的情况，请作好与客户的沟通处理，在下方输入退部分押金的金额。<br />
              <span class="red"><strong>目前支付宝接口未支持对个人的订单退款，所以目前只是一个退押金记录。即由客服与客户沟通并实际从支付宝手动退款后，再在这里做记录!</strong></span>              
              </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">退款金额：</label>
                <div class="col-md-6">
                    <input type="text" name="dealAmount" value="${Model.dealAmount}" data-popover-offset="0,8" required maxlength="32" class="form-control">
                </div>
                <div class="col-md-3">
                    <p class="form-control-static">
                        <span class="red">* 若部分退款请仔细确认</span>
                    </p>
                </div>
            </div>
            <div class="form-group">
              <div class="col-md-6 col-md-offset-3">
                <div class="btn-toolbar">
                  <button type="submit" class="btn btn-primary">退款</button>
                  <button type="button" onclick="javascript:history.go(-1);"
                  class="btn btn-default">返回</button>
                  <button type="reset" class="btn btn-default">重置</button>
                </div>
              </div>
            </div>
          </form>
        </div>
    </div>
    </c:if>
    
      <div class="panel">
        <div class="panel-heading">
          <h4>修改反馈信息</h4>
        </div>
        <div class="panel-body">
          <form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
			<input type="hidden" name="recordID" value="${Model.recordID}">
			
            <div class="form-group">
              <label for="company" class="col-md-3 control-label">退押金申请记录:</label>
              <div class="col-md-6">
                <table class="table table-bordered"><tbody><tr><td width="30%">设备机身码:</td><td>${Model.SN}</td></tr>
<tr><td>客户名称:</td><td>${Model.customerName}</td></tr>
<tr><td>电话:</td><td><span class="">${Model.phone}</span></td></tr>
<tr><td>押金总额:</td><td><span class="label label-success label-xs">${Model.dealAmount}</span></td></tr>
<tr><td>状态:</td><td><span class="label label-success label-xs">${Model.status}</span></td></tr>
<tr><td>物流:</td><td><span class="">${Model.expressName}</span></td></tr>
<tr><td>快递单号:</td><td><span class="">${Model.expressNum}</span></td></tr>
<tr><td>来自客户的评价/备注:</td><td><span class="">${Model.comment}</span></td></tr>
</tbody></table>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="remark" class="col-md-3 control-label">给客户的反馈信息/备注:</label>
              <div class="col-md-6">
                <textarea id="remark" rows="6" name="remark" maxlength="499" data-popover-offset="0,8"
                class="form-control">${Model.remark}</textarea>
              </div>
              <div class="col-sm-3"><p class="form-control-static"><span class="red">* 如设备已损坏, 部件已遗失等异常情况需要反馈给客户, 先在此记录. 必要时再考虑其他方式去与客户及时沟通</span></p></div>
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
   $("#edit_form").validate_popover({
      rules: {
        'remark': {
          required: true,
          maxlength: 499
        }
      },
      messages: {
//         'company': {
//           required: "请输入单位名称.",
//           maxlength: "最多不超过50个字符."
//         },
//         'operatorName': {
//           required: "请输入联系人姓名.",
//           maxlength: "最多不超过50个字符."
//         },
        'remark': {
            required: "请输入反馈信息",
          maxlength: "最多不超过500字"
        }

      },
        submitHandler: function(form){
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath %>device/deposit/save",
	            dataType:"html",
	            data:$('#edit_form').serialize(),
	            success:function(data){
	                result = jQuery.parseJSON(data);
	                if (result.code == 0) { // 成功保存
	                    easy2go.toast('info', result.msg);
	                    var redirect = "<%=request.getHeader("Referer") %>";
	                	if(redirect != null){
	                		window.location.href=redirect;
	                	} else {
	                    	window.location.href="<%=basePath %>device/revert";
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