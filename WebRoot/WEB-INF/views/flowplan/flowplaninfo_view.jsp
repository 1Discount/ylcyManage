<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>套餐详情-套餐管理-流量运营中心</title>
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
                      <td>名称：</td>
                      <td><b>${Model.flowPlanName}</b></td>
                      <td>价格：</td>
                      <td><b><fmt:formatNumber type="currency" value="${Model.planPrice}" currencyCode="CNY" /></b>
                      </td>
                    </tr>
                    <tr>
                      <td>套餐类型：</td>
                      <td><span class="label label-success label-xs">${Model.planType}</span></td>
                      <td>国家：</td>
                      <td>${Model.countryList}
                      </td>
                    </tr>
                    <tr>
                      <td>天数：</td>
                      <td><span class="label label-success label-xs"><c:if test="${Model.planType eq '流量'}">不限天数</c:if><c:if test="${Model.planType eq '天数'}">${Model.planDays} 天</c:if></span></td>
                      <td>流量(MB)：</td>
                      <td><c:if test="${Model.planType eq '天数'}">不限流量</c:if><c:if test="${Model.planType eq '流量'}"><a title="${Model.flowSum}MB"><span id="flowSum_pretty_byte_unit" class="label label-success label-xs">${Model.flowSum}MB</span></c:if></a></td>
                    </tr>
                    <tr>
                      <td>默认限速值：</td>
                      <td>${Model.speedLimit} KB/s</td>
                      <td>流量预警值(MB):</td>
                      <td>${Model.flowAlert} MB</td>
                    </tr>
                    <tr>
                      <td>创建者：</td>
                      <td>${Model.creatorUserName}</td>
                      <td>创建时间：</td>
                      <td>${Model.creatorDate}</td>
                    </tr>
                    <tr>
                      <td>上次更新者：</td>
                      <td>${Model.modifyUserID}</td>
                      <td>更新时间：</td>
                      <td><span>${Model.modifyDate}</span></td>
                    </tr>
                    <tr>
                      <td>备注：</td>
                      <td colspan="3">${Model.note}</td>
                    </tr>
                  </table>
                </div>
                <div class="btn-toolbar"><a href="<%=basePath %>flowplan/flowplaninfo/edit/${Model.flowPlanID}" class="btn btn-primary"> <span class="glyphicon glyphicon-edit"></span>编辑</a></div>
              </c:if>
              </div>
            </div>
            <c:if test="${not empty Model}">
            <div class="panel">
              <div class="panel-heading">
                <h4 class="panel-title"> 相关交易</h4>
              </div>
              <div class="panel-body">                
<div class="table-responsive"><table id="searchTable">
        <tr>
           <!--  <th w_render="orderidOP"  width="10%;">交易ID</th> -->
            <th w_render="render_SN" width="10%;">设备机身码</th>
            <th w_render="render_customerName"  width="10%;">客户</th>
            <th w_index="userCountry"  width="10%;">国家</th>
            <th w_render="render_orderAmount"  width="5%;">总金额</th>
            <th w_render="render_flowDays"  width="3%;">天数</th>
            <th w_index="ifActivate"  width="3%;">是否激活</th>
            <th w_index="activateDate"  width="10%;">激活时间</th>
            <th w_index="orderStatus"  width="5%;">订单状态</th>
            <th w_index="creatorUserName"  width="6%;">创建人</th>
            <th w_index="creatorDate"  width="10%;">创建时间</th>
            <th w_index="modifyDate"  width="10%;">最后更新</th>
<%-- 在此处不需要操作,且涉及权限问题           <th w_render="operate" width="30%;">操作</th> --%>
        </tr>
</table></div>
              </div>
            </div>
            </c:if>
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
    
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>orders/flowdealorders/getpage?flowPlanID=${Model.flowPlanID}',
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true,
           displayPagingToolbarOnlyMultiPages:true
           
       });
       
       $('#flowSum_pretty_byte_unit').empty().append(prettyByteUnitSize("${Model.flowSum}",2,2,true));
   });
   
    //设备sn
    function render_SN(record, rowIndex, colIndex, options){
        return '<a href="<%=basePath %>device/deviceInfodetail?deviceid='+record.SN+'">'+record.SN+'</a>';
     }
	function render_customerName(record, rowIndex, colIndex, options){
        return '<a href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</a>';
	}
    function render_orderAmount(record, rowIndex, colIndex, options) {
       return accounting.formatMoney(record.orderAmount);
    }
    function render_flowDays(record, rowIndex, colIndex, options) {
       return record.flowDays + '天';
    }
 
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