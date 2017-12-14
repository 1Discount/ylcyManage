<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>查看详情-国家管理-流量运营中心</title>
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
			    <div class="panel-heading">${Model.countryName}</div>
			    <div class="panel-body">
			      <c:if test="${empty Model}">
                    <p>${info}</p>
                    <div class="btn-toolbar">
                        <button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button>
                    </div>                  
                  </c:if>
                  <c:if test="${not empty Model}">
			      <div class="table-responsive">			      
			        <table class="table table-bordered">
			          <tr>
			            <td>ID:</td>
			            <td>${Model.countryID}</td>
			            <td>MCC:</td>
			            <td>${Model.countryCode}</td>
			          </tr>
			          <tr>
			            <td>数据服务价格（￥）：</td>
			            <td> <span class="num"><fmt:formatNumber type="currency" value="${Model.flowPrice}" currencyCode="CNY" /> 元/天</span>
			            </td>
			            <td>大洲:</td>
                        <td>${Model.continent}</td>
			          </tr>
			          <tr>
			            <td>创建者:</td>
			            <td>${Model.creatorUserName}</td>
			            <td>创建时间:</td>
			            <td>${Model.creatorDate}</td>
			          </tr>
			          <tr>
                        <td>该地区的流量套餐总数:</td>
                        <td><span class="label label-success label-xs">${Model.flowCount}</span></td>
                        <td>该地区的有效订单总数:</td>
                        <td><span class="label label-success label-xs">${Model.orderCount}</span></td>
                      </tr>
			        </table>
			      </div>
			      <div class="btn-toolbar"><a href="<%=basePath %>flowplan/countryinfo/edit/${Model.countryID}" class="btn btn-primary"> <span class="glyphicon glyphicon-edit"></span>编辑</a>
			      </div>
			      </c:if>
			    </div>
			  </div>
			  <div class="panel">
			    <div class="panel-heading">
				  <ul class="nav navbar-nav">
				      <li id="flowPlanMenu"><a href="javascript:void(0);">该国家下的套餐</a></li>
				      <li id="flowOrderMenu"><a href="javascript:void(0);">该国家下的交易订单</a></li>
				  </ul>
			    </div>
			    <div class="panel-body">			      
			      <div id="searchTableFlowPlanWrapper"><div class="table-responsive"><table id="searchTableFlowPlan">
	                <tr>
	                    <th w_index="flowPlanID" w_hidden="true" width="15%;">套餐ID</th>
	                    <th w_render="render_flowPlanName" width="15%;">套餐名</th>
	                    <th w_render="render_planPrice" width="10%;">价格(元)</th>
	                    <th w_index="countryList">国家</th>
	                    <th w_index="planType">套餐类型</th>
	                    <th w_render="render_flowSum" width="10%;">流量值(MB)</th> <!-- w_sort="userID,desc" -->                   
	                    <th w_render="render_planDays" width="5%;">天数</th>
	                    <th w_render="render_validPeriod" width="5%;">有效天数</th>
	                    <th w_render="render_flowAlert" width="10%;">流量预警值(MB)</th>
	                    <th w_render="render_speedLimit" width="5%;">限速值(KB)</th>
	                    <th w_index="creatorUserName" width="5%;">创建者</th>
	                    <th w_index="creatorDate" width="10%;">创建时间</th>
	                    <th w_render="operate" width="30%;">操作</th>
	                </tr>
	               </table></div>
	               <div class="btn-toolbar" style="margin-top:20px;"><a id="refreshFlowPlanBtn" href="javascript:void(0);" class="btn btn-primary"> <span class="glyphicon glyphicon-edit"></span>刷新</a>
                  </div>
	               </div>
	               <div id="searchTableFlowOrderWrapper"><div class="table-responsive"><table id="searchTableFlowOrder">
			        <tr>
			            <th w_render="orderidOP"  width="10%;">交易ID</th>
			            <th w_index="SN" width="10%;">机身码</th>
			            <th w_index="customerName"  width="10%;">客户</th>
			            <th w_index="userCountry"  width="10%;">国家</th>
			            <th w_index="orderAmount"  width="5%;">总金额</th>
			            <th w_index="flowDays"  width="3%;">天数</th>
			            <th w_index="ifActivate"  width="3%;">是否激活</th>
			            <th w_index="activateDate"  width="10%;">激活时间</th>
			            <th w_index="orderStatus"  width="5%;">订单状态</th>
			            <th w_index="creatorUserName"  width="6%;">创建人</th>
			            <th w_index="creatorDate"  width="10%;">创建时间</th>
			            <th w_index="modifyDate"  width="10%;">最后更新</th>
			            <th w_render="operate2" width="30%;">操作</th>
			        </tr>
				  </table></div>
                  <div class="btn-toolbar" style="margin-top:20px;"><a id="refreshFlowOrderBtn" href="javascript:void(0);" class="btn btn-primary"> <span class="glyphicon glyphicon-edit"></span>刷新</a>
                  </div>
				  </div>
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
   var gridObjFlowPlanInited = false;
   var gridObj2FlowOrderInited = false;
   
   var gridObj;
//     $(function(){
    function initSearchTableFlowPlan() {
       gridObj = $.fn.bsgrid.init('searchTableFlowPlan',{
           url:'<%=basePath %>flowplan/flowplaninfo/datapage/mcc/${Model.countryCode}',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
     }
//         });    
    
   function operate(record, rowIndex, colIndex, options) {
      return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>flowplan/flowplaninfo/view/' + record.flowPlanID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
+ '</div>';
   }
   function render_flowPlanName(record, rowIndex, colIndex, options) {
       return '<a href="<%=basePath %>flowplan/flowplaninfo/view/' + record.flowPlanID + '"><span>' + record.flowPlanName + '</span></a>';
    }
    function render_planPrice(record, rowIndex, colIndex, options) {
       return accounting.formatMoney(record.planPrice); //'￥' + record.planPrice;
    }
    function render_flowSum(record, rowIndex, colIndex, options) {
       if (record.planType == "流量") {
        return '<a class="btn btn-success btn-xs" onclick="return;"><span>' + record.flowSum + 'MB' + '</span></a>';
       } else {
        return '0'; //record.flowSum + 'MB';
       }       
    }
    function render_flowAlert(record, rowIndex, colIndex, options) {
       return record.flowAlert + 'MB';
    }
    function render_speedLimit(record, rowIndex, colIndex, options) {
       return record.speedLimit + 'KB/s';
    }
    function render_planDays(record, rowIndex, colIndex, options) {       
       if (record.planType == "天数") {
        return '<a class="btn btn-success btn-xs" onclick="return;"><span>' + record.planDays + '天' + '</span></a>';
       } else {
        return '0'; // record.planDays + '天';
       }    
    }
    function render_validPeriod(record, rowIndex, colIndex, options) {
       return record.validPeriod + '天';
    }
    </script>
<script type="text/javascript">
    var gridObj2;
//     $(function(){
    function initSearchTableFlowOrder() {
        gridObj2 = $.fn.bsgrid.init('searchTableFlowOrder',{
            url:'<%=basePath %>orders/flowdealorders/getpage/mcc/${Model.countryCode}',
            pageSizeSelect: true,
            pageSize: 10,
            pageSizeForGrid:[10,20,30,50,100],
            multiSort:true
            
        });
      }
//    });
     //自定义列
    function operate2(record, rowIndex, colIndex, options) {
        //'<a href="#" onclick="alert(\'ID=' + gridObj2.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
         return '<A class="btn btn-primary btn-xs"   href="<%=basePath %>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>';
    }    
    function orderidOP(record, rowIndex, colIndex, options) {
        return '<A  href="<%=basePath %>orders/flowdealorders/info?flowDealID='+record.flowDealID+'">'+record.flowDealID+'</A>';
    }     
   function re(){
       $("#order_ID").val('');
       $("#order_customerName").val('');
       gridObj2.options.otherParames =$('#searchForm').serializeArray();
       gridObj2.refreshPage();
   }
    </script>
<script type="text/javascript">
    function activeFlowPlanPanel(){
        $("#flowPlanMenu").attr("class", "active");
        $("#flowOrderMenu").removeAttr("class");
        $("#searchTableFlowPlan_pt_outTab").remove(); // fix 多次显示问题
        $("#searchTableFlowOrderWrapper").hide();
        $("#searchTableFlowPlanWrapper").show();
        
        if(!gridObjFlowPlanInited) { // 按需刷新, 添加手动刷新按钮, 避免每次都请求刷新, 减少数据库请求
            initSearchTableFlowPlan();
            gridObjFlowPlanInited = true;
        }
    }
    function activeFlowOrderPanel(){
        $("#flowOrderMenu").attr("class", "active");
        $("#flowPlanMenu").removeAttr("class");
        $("#searchTableFlowOrder_pt_outTab").remove(); // fix 多次显示问题
        $("#searchTableFlowPlanWrapper").hide();
        $("#searchTableFlowOrderWrapper").show();
        
        if(!gridObj2FlowOrderInited) { // 按需刷新, 添加手动刷新按钮, 避免每次都请求刷新, 减少数据库请求
            initSearchTableFlowOrder();
            gridObj2FlowOrderInited = true;
        }
    }
    $("#flowPlanMenu").click(function(){
        activeFlowPlanPanel();
    });
    $("#flowOrderMenu").click(function(){
        activeFlowOrderPanel();
    });
    
    activeFlowPlanPanel(); // default
    
$(function(){
	$('#refreshFlowPlanBtn').click(function(){
		gridObj.refreshPage();
	});
    $('#refreshFlowOrderBtn').click(function(){
        gridObj2.refreshPage();
    });
	
});
</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>