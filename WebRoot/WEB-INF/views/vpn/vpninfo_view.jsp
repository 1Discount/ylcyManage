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
              <div class="panel-heading"> 套餐</div>
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
                      <td>ID：</td>
                      <td><b>${Model.vpnId}</b>
                      </td>
                      <td>套餐类型：</td>
                      <td><span class="label label-success label-xs">${Model.vpnPackageType}</span></td>
                    </tr>
                    <tr>
                      <td>VPN：</td>
                      <td><b>${Model.vpn}</b>
                      </td>
                      <td>VPN类型：</td>
                      <td>
                        <c:if test="${Model.vpnType=='1'}"><span class="label label-success label-xs">小流量</span></c:if>
                        <c:if test="${Model.vpnType=='0'}"><span class="label label-success label-xs">大流量</span></c:if>
                      </td>
                    </tr>
                    <tr>
                      <td>VPN子账号：</td>
                      <td><b>${Model.vpnc}</b></td>
                      <td>状态：</td>
                      <td>
	                      <c:if test="${Model.availableNum=='0'}">不可用</c:if>
	                      <c:if test="${Model.availableNum=='1'}">可使用</c:if>
                          <c:if test="${Model.availableNum=='2'}">使用中</c:if>
                      </td>
                    </tr>
                    <tr>
                      <td>机身码列表：</td>
                      <td>${Model.lastDeviceSN}</td>
                      <td>代号：</td>
                      <td>${Model.number}</td>
                    </tr>
                    <tr>
                      <td>开始时间:</td>
                      <td>${Model.begainTime}</td>
                      <td>截止时间:</td>
                      <td>${Model.endTime}</td>
                    </tr>
                    <tr>
                      <td>包含流量：</td>
                      <td>${Model.includeFlow} KB</td>
                      <td>已使用流量：</td>
                      <td>${Model.useFlow==null?0:Model.useFlow} KB</td>
                    </tr>
                    <tr>
                      <td>上次分配时间：</td>
                      <td>${Model.lastTimeAllocation}</td>
                      <td>创建人姓名：</td>
                      <td>${Model.creatorUserName}</td>
                    </tr>
                    <tr>
                      <td>创建人ID：</td>
                      <td>${Model.creatorUserId}</td>
                      <td>创建时间：</td>
                      <td>${Model.creatorDate}</td>
                    </tr>
                    <tr>
                      <td>更新人ID：</td>
                      <td>${Model.modifyUserId}</td>
                      <td>更新时间：</td>
                      <td>${Model.modifyDate}</td>
                    </tr>
                    <tr>
                      <td>备注：</td>
                      <td colspan="3">${Model.remark}</td>
                    </tr>
                  </table>
                </div>
                <div class="btn-toolbar">
                <a href="<%=basePath %>vpn/vpninfo/editvpn?vpnId=${Model.vpnId}" class="btn btn-primary"> <span class="glyphicon glyphicon-edit"></span>编辑</a>
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
       return '<A  href="<%=basePath %>orders/flowdealorders/info?flowDealID='+record.flowDealID+'"><SPAN class="" style="color: green;">'+record.flowDealID+'</SPAN></A>';
   }
--%>

</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>