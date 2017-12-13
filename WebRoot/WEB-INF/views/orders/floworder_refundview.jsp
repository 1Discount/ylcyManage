<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>VPN详情-SIM服务器维护-EASY2GO ADMIN</title>
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
          <div class="col-md-12">
            <div class="panel">
              <div class="panel-heading">退款详情</div>
              <div class="panel-body"> 
              <c:if test="${empty Model}">
                <p>${info}</p>
                <div class="btn-toolbar">
                    <button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">取消</button>
                </div>                  
              </c:if>
              <c:if test="${not empty Model}">
                <div class="table-responsive">
                  <table class="table table-bordered">
                    <tr>
                      <td>流量订单ID：</td>
                      <td><b>${Model.flowDealID}</b>
                      </td>
                      <td>处理人：</td>
                      <td><b>${Model.creatorUserName}</b></td>
                    </tr>
                    <tr>
                      <td>问题类型：</td>
                      <td>${Model.problemType}</td>
                      <td>问题级别：</td>
                      <td><b>${Model.problemLevel}</b>
                      </td>
                    </tr>
                    <tr>
                      <td>处理描述：</td>
                      <td><b>${Model.handleDescription}</b>
                      </td>
                      <td>处理结果：</td>
                      <td><b>${Model.handleResult}</b></td>
                    </tr>
                    <tr>
                      <td>退款金额：</td>
                      <td><b>${Model.refundAmount}</b></td>
                      <td>问题发生时间：</td>
                      <td>${Model.problemTime}</td>
                    </tr>
                    <tr>
                      <td>备注：</td>
                      <td colspan="3">${Model.remark}</td>
                    </tr>
                  </table>
                </div>
                <div class="btn-toolbar">
                <button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button>
                </div>
              </c:if>
              </div>
            </div>
          </div>
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
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
    
 
 <%-- 在此处不需要操作,且涉及权限问题  
    //自定义列.
   function operate(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
        if(record.ifActivate=='否' || record.ifActivate==''){
            return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-brightness-reduce">激活</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath %>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
        }else if(record.orderStatus=='可使用' || record.orderStatus=='使用中'){
            return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-stop">暂停服务</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath %>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
        }else if(record.orderStatus=='已暂停'){
            return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-play">启动服务</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath %>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
        }      
   }
   
   function orderidOP(record, rowIndex, colIndex, options) {
       return '<A  href="<%=basePath %>orders/flowdealorders/info?flowDealID='+record.flowDealID+'">'+record.flowDealID+'</A>';
   }
--%>

</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>