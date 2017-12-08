<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>SIM卡充值管理-EASY2GO ADMIN</title>
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
      <section id="main-content">
        <section class="wrapper">
          <%-- 这里添加页面主要内容  block content --%>
    <div class="col-lg-3">
        <div class="panel">
          <div class="panel-heading">
            <h3 class="panel-title">添加SIM卡充值</h3>
          </div>
          <div class="panel-body">
            <form id="edit_form" role="form" action="" method="post"
          autocomplete="off" class="form-horizontal">
            <input type="hidden" name="SIMCategory" value="${Model.SIMCategory}">
            <input type="hidden" name="SIMinfoID" value="${Model.SIMinfoID}">
            <input type="hidden" name="IMSI" value="${Model.IMSI}">
            <input type="hidden" name="MCC" value="${Model.MCC}">
            <input type="hidden" name="countryName" value="${Model_MCCCountryName}">
            <input type="hidden" name="operatorName" value="${Model.trademark}">
            <div class="form-group">
              <label for="rechargeAmount" class="col-md-3 control-label">充值金额</label>
              <div class="col-md-9">
                <input id="rechargeAmount" type="text" name="rechargeAmount" data-popover-offset="0,8"
                required class="form-control">
                <p class="help-block"><span class="red">* 单位: 元/人民币</span></p>
              </div>
            </div>
            <div class="form-group">
              <label for="simserver_status" class="col-md-3 control-label">充值后有效期</label>
              <div class="col-md-9">
                <div class="inputgroup">
                  <input type="text" name="rechargedValidDate" data-popover-offset="0,8" required
                  class="form_datetime form-control">
                  <p class="help-block"><span class="red">*</span></p>
                </div>
              </div>

            </div>
            <div class="form-group">
              <label for="remark" class="col-md-3 control-label">备注</label>
              <div class="col-md-9">
                <textarea id="remark" rows="5" name="remark" maxlength="99" data-popover-offset="0,8"
                class="form-control"></textarea>
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-9 col-md-offset-3">
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
             
    <div class="col-lg-9">
      <div class="panel">
        <div class="panel-heading">
          <h4 class="panel-title">该SIM卡信息</h4>
        </div>
        <div class="panel-body">
            <c:if test="${empty Model}">
                <p>${info}</p>
                <div class="btn-toolbar">
                    <button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">取消</button>
                </div>                  
            </c:if>
            <c:if test="${not empty Model}">
          <div class="table-responsive">
            <table class="table table-bordered table-hover">
              <tr>
                <td width="10%">ICCID：</td>
                <td width="40%"><a href="<%=basePath %>sim/siminfo/view/${Model.SIMinfoID}"><span class="label label-success label-xs">${Model.ICCID}</span></a>
                </td>
                <td width="10%">SIM卡使用类型：</td>
                <td width="40%">${Model.SIMCategory}</td>
              </tr>
              <tr>
                <td width="10%">IMSI：</td>
                <td width="40%"><span class="label label-success label-xs">${Model.IMSI}</span>
                </td>
                <td>最近设备SN：</td>
                <td><a href="#">${Model.lastDeviceSN}</a></td>
              </tr>
              <tr>
                <td>国家：</td>
                <td><a href="#">${Model_MCCCountryName}</a>
                </td>
                <td>运营商：</td>
                <td><a href="#">${Model.trademark}</a>
                </td>
              </tr>
              <tr>
                <td width="20%">套餐名称：</td>
                <td width="30%">${Model.planType}</td>
                <td width="20%">到期日期：</td>
                <td width="30%">${Model.planEndDate}</td>
              </tr>
              <tr>
                <td>套餐流量：</td>
                <td><span class="label label-success label-xs">${Model.planData} KB</span>
                </td>
                <td>剩余流量：</td>
                <td><span class="label label-success label-xs">${Model.planRemainData} KB</span>
                </td>
              </tr>
              <tr>
                <td>卡内余额：</td>
                <td><span class="label label-success label-xs"><fmt:formatNumber type="currency" value="${Model.cardBalance}" currencyCode="CNY" />元</span>
                </td>
                <td>卡状态：</td>
                <td><span class='label label-success label-xs'>${Model.cardStatus}</span></td>
              </tr>
            </table>
          </div>
          
          </c:if>
        </div>
      </div>
      
      <div class="panel">
        <div class="panel-heading">
        
        <h3 class="panel-title">该SIM卡充值记录</h3></div>
        <div class="panel-body">
        <div class="table-responsive"><table id="searchTable">
                <tr>
            <th w_index="IMSI">IMSI</th>
            <th w_render="render_SIMinfoID">SIM卡ID</th>
            <th w_render="render_rechargeAmount" width="5%;">充值金额</th>
            <th w_index="rechargedValidDate">充值后有效期</th>
            <th w_index="creatorUserName">创建者</th>
            <th w_index="creatorDate">充值日期</th>
            <th w_render="operate" width="15%;">操作</th>
                </tr>
        </table></div>
        </div></div>
    </div>


        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    accounting.settings = { // 参数的默认设置:
        currency: {
            symbol : "￥",   //默认的货币符号 '$'
            format: "%s%v", // 输出控制: %s = 符号, %v = 值或数字 (can be object: see below)
            decimal : ".",  // 小数点分隔符
            thousand: ",",  // 千位分隔符
            precision : 2   // 小数位数
        },
        number: {
            precision : 0,  // 精度，默认的精度值为0
            thousand: ",",
            decimal : "."
        }
    };
    </script>
    <script type="text/javascript">
          var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/simrechargebill/billsbysimid/${Model.SIMinfoID}',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         
         function render_SIMinfoID(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/siminfo/view/' + record.SIMinfoID + '" title="点击查看该卡详情"><span>' + record.SIMinfoID.substring(0,8) + '</span></a>';
         }
        function render_rechargeAmount(record, rowIndex, colIndex, options) {
           return accounting.formatMoney(record.rechargeAmount) + '元';
        }
         function operate(record, rowIndex, colIndex, options) {
<%--            //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
            return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/edit/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '</div>';
// + '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/view/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'

         }
    </script>
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
	                    // TODO: 适宜跳转哪个页面? -> 目前暂时刷新本页
	                    //需要确认 window.location.href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=${Model.SIMinfoID}&SIMCategory=${Model.SIMCategory}";
	                    window.location.reload(true);
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