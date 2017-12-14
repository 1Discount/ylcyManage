<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>添加渠道商-渠道商管理-流量运营中心</title>
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
          <h4 class="form-title">添加渠道商</h4>
        </div>
        <div class="panel-body">
          <form id="edit_form" role="form" action="" method="post"
          autocomplete="off" class="form-horizontal">
<%--            <input type="hidden" name="_csrf" value="QLaKdKJy-zJ2Cd9BWFfWknPv6_QqFwXns5rg"> --%>
            <div class="form-group">
              <label for="company" class="col-md-3 control-label">单位名称:</label>
              <div class="col-md-6">
                <input id="company" type="text" name="company" data-popover-offset="0,8"
                required maxlength="50" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* 经营单位, 如公司名等</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="serverCode" class="col-md-3 control-label">sim服务器编号:</label>
              <div class="col-md-6">
                <input id="serverCode" type="text" name="serverCode" data-popover-offset="0,8"
                 maxlength="5" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">sim服务器的编号。填写此编号后，会将对应的sim卡服务器分配给此渠道商；此渠道的订单会使用对应的sim服务器上的卡；默认为空，使用本公司的卡</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="operatorName" class="col-md-3 control-label">联系人姓名:</label>
              <div class="col-md-6">
                <input id="operatorName" type="text" name="operatorName" data-popover-offset="0,8"
                required maxlength="50" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="operatorTel" class="col-md-3 control-label">联系电话:</label>
              <div class="col-md-6">
                <input id="operatorTel" type="text" name="operatorTel" data-popover-offset="0,8"
                required maxlength="50" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* </span>
                </p>
              </div>
            </div>
            
            <div class="form-group">
              <label for="operatorTel" class="col-md-3 control-label">备用联系电话:</label>
              <div class="col-md-6">
                <input id="standbyOperator" type="text" name="standbyOperator" data-popover-offset="0,8"
                required maxlength="50" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* </span>
                </p>
              </div>
            </div>
            
            <div class="form-group">
              <label for="operatorEmail" class="col-md-3 control-label">联系邮箱email:</label>
              <div class="col-md-6">
                <input id="operatorEmail" type="text" name="operatorEmail" data-popover-offset="0,8"
                maxlength="50" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="address" class="col-md-3 control-label">地址:</label>
              <div class="col-md-6">
                <input id="address" type="text" name="address" data-popover-offset="0,8"
                maxlength="100" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="type" class="col-md-3 control-label">渠道商类型:</label>
              <div class="col-md-6">
                <select id="type" name="type" required class="form-control">
                
                <option value="设备">设备</option>
		                </select>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">*</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="paymentMethod" class="col-md-3 control-label">结算方式:</label>
              <div class="col-md-6">
                <select id="paymentMethod" name="paymentMethod" required class="form-control" onchange="paymentMethodChange()">
                <option value="预付费">预付费</option>
                <option value="后付费">后付费</option>
	                </select>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">*</span>
                </p>
              </div>
            </div>
            
              
           <div class="form-group">
              <label for="address" class="col-md-3 control-label">帐户:</label>
              <div class="col-md-6">
                <input id="score" type="text" name="score" value="0" data-popover-offset="0,8"
                maxlength="100" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> 如果选择后付费,该值请填写为0</span>
                </p>
              </div>
            </div>
            
            
            <div class="form-group">
              <label for="address" class="col-md-3 control-label">帐户余额:</label>
              <div class="col-md-6">
                <input id="balance" type="text" name=balance value="0" data-popover-offset="0,8"
                maxlength="100" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">如果选择后付费,该值请填写为0 </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label for="remark" class="col-md-3 control-label">备注:</label>
              <div class="col-md-6">
                <textarea id="remark" rows="3" name="remark" maxlength="99" data-popover-offset="0,8"
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
    <script type="text/javascript">
    function paymentMethodChange(){
		var temp = $("#paymentMethod").val();
		if(temp=="后付费"){
			$("#score").val("0");
			$("#score").attr('readonly',true);
			$("#balance").val("0");
			$("#balance").attr('readonly',true);
		}else{
			$("#score").val("0");
			$("#score").attr('readonly',false);
			$("#balance").val("0");
			$("#balance").attr('readonly',false);
		}
		
	}
    $(function(){
    // IP地址验证
    jQuery.validator.addMethod("ip", function(value, element) {  
        var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;  
        return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));  
    }, "IP地址格式错误, 形如 192.168.0.160"); 
    $("#edit_form").validate_popover({
      rules: {
        'company': {
          required: true,
          maxlength: 50,
          remote: {
                type: "post",
                url: "<%=basePath%>customer/distributor/checkCompany",
                data: {
                    company: function() {
                        return $("#company").val();
                    }
                },
                dataType: "html",
                dataFilter: function(data, type) {
                    if (data == "true"){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        },
        'operatorName': {
          required: true,
          maxlength: 50
        },
        'operatorTel': {
          required: true,
          maxlength: 50,
          remote: {
              type: "post",
              url: "<%=basePath %>customer/distributor/checkPhone",
              data: {
            	  operatorTel: function() {
                      return $("#operatorTel").val();
                  }
              },
              dataType: "html",
              dataFilter: function(data, type) {
                  if (data == "true")
                      return true;
                  else
                      return false;
              }
          }
        },
        'operatorEmail': {
          required: false,
          email: true,
          maxlength: 50,
          remote: {
              type: "post",
              url: "<%=basePath %>customer/distributor/checkEmail",
              data: {
            	  operatorEmail: function() {
                      return $("#operatorEmail").val();
                  }
              },
              dataType: "html",
              dataFilter: function(data, type) {
                  if (data == "true")
                      return true;
                  else
                      return false;
              }
          }
        },
        'address': {
          required: false,
          maxlength: 100
        },
        'remark': {
          required: false,
          maxlength: 99
        }
        
      },
      'standbyOperator': {
          required: true,
          phone:true,
        },
      messages: {
         'company': {
            required: "请输入单位名称.",
            maxlength: "最多不超过50个字符.",
            remote:"此单位名称已使用"
         },
//         'operatorName': {
//           required: "请输入联系人姓名.",
//           maxlength: "最多不超过50个字符."
//         },
		'operatorEmail': {
          remote: "邮箱已注册",
        },
        'operatorTel': {
            remote: "该手机号已注册",
          },
        'remark': {
          maxlength: "最多不超过100字"
        },
        'standbyOperator': {
            maxlength: "备用联系人不能为空",
            phone:"请输入正确的电话格式",
         }
      },
        submitHandler: function(form){
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath %>customer/distributor/save",
	            dataType:"html",
	            data:$('#edit_form').serialize(),
	            success:function(data){
	                result = jQuery.parseJSON(data);
	                if (result.code == 0) { // 成功保存
	                    easy2go.toast('info', result.msg);
	                    window.location.href="<%=basePath %>customer/distributor/index";                   
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