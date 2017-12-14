<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>流量订单详情-订单管理-流量运营中心</title>
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
<%--  <div class="panel">--%>
<%--    <div class="panel-body">--%>
<%--      <form role="form" action="/deals" method="get" class="form-inline">--%>
<%--        <div class="form-group">--%>
<%--          <label class="inline-label">ID：</label>--%>
<%--          <input type="text" name="id" placeholder="ID" class="form-control">--%>
<%--        </div>--%>
<%--        <div class="form-group">--%>
<%--          <button type="submit" class="btn btn-primary">搜索</button>--%>
<%--        </div>--%>
<%--      </form>--%>
<%--    </div>--%>
<%--  </div>--%>
  <div class="panel">
    <div class="panel-heading">数据服务交易详细</div>
    <div class="panel-body">
      <div class="table-responsive">
        <table class="table table-bordered table-hover">
          <tr>
            <td style="width:15%;">流量交易ID:</td>
            <td style="width:35%;">${Model.flowDealID}</td>
            <td style="width:15%;">订单：</td>
            <td style="width:35%;"><a href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID=${Model.orderID}">${Model.orderID}</a>
            </td>
          </tr>
          <tr>
            <td>客户名称：</td>
            <td><span class="label label-success label-xs">${Model.customerName}</span></td>
            <td>订单状态：</td>
            <td>
            <c:if test="${Model.orderStatus eq '已暂停' or Model.orderStatus eq '已过期'}">
            <span class="label label-danger label-xs">${Model.orderStatus}</span>
            </c:if>
            <c:if test="${Model.orderStatus ne '已暂停' and Model.orderStatus ne '已过期'}">
            <span class="label label-success label-xs">${Model.orderStatus}</span>
            </c:if>
            
            </td>
          </tr>
          <tr>
            <td>金额（￥）：</td>
            <td><span class="num">${Model.orderAmount}</span>
            </td>
            <td>创建者：</td>
            <td><a href="#">${Model.creatorUserName}</a>
            </td>
          </tr>
<%--          <tr>--%>
<%--            <td>使用国家：</td>--%>
<%--            <td>${Model.userCountry}</td>--%>
<%--          </tr>--%>
          <tr>
            <td>天数：</td>
            <td>${Model.flowDays}</td>
            <td>已使用天数：</td>
            <td>${Model.flowDays - Model.daysRemaining}</td>
          </tr>
          <tr>
            <td>剩余可用天数：</td>
            <td>${Model.daysRemaining}</td>
            <td>当前周期剩余分钟数：</td>
            <td>${Model.minsRemaining}</td>
          </tr>
          <tr>
            <td>预约使用日期：</td>
            <td>${Model.panlUserDate}</td>
            <td>到期时间：</td>
            <td>${Model.flowExpireDate}</td>
          </tr>
          <tr>
            <td>创建时间：</td>
            <td>${Model.creatorDateString}</td>
            <td>更新时间：</td>
            <td>${Model.modifyDateString}</td>
          </tr>
          <tr>
          <td colspan="4" ><b><em>设备情况:</em></b></td>
          </tr>
          <tr>
            <td>启用的设备：</td>
            <td><a href="<%=basePath %>device/deviceInfodetail?deviceid=${Model.SN}">${Model.SN}</a>
            </td>
            <td>当前计费周期开始时间：</td>
            <td>${Model.intradayDate}</td>
          </tr>
          <tr>
            <td>IMSI：</td>
            <td>${Model.IMSI} <a href="<%=basePath %>sim/siminfo/view?IMSI=${Model.IMSI}"><span class="label label-info label-xs">查看该SIM卡信息</span></a></td>
<%--            <td>ICCID：</td>--%>
<%--            <td>${Model.ICCID}</td> 流量订单表没有这字段改为通过IMSI查询SIM卡--%>
            <td colspan="2"></td>
          </tr>
        </table>
      </div>
      <div class="btn-toolbar"><a href="<%=basePath %>orders/flowdealorders/edit?flowDealID=${Model.flowDealID}" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span>编辑</a>
      </div>
    </div>
  </div>
  <div class="panel">
    <div class="panel-heading">
      <h3 class="panel-title">可用国家列表<%--<span class="badge">2</span> --%></h3>
    </div>
    <div class="panel-body">
      <div class="section" style="margin-bottom:10px;">
        <div class="btn-toolbar"><a class="btn btn-primary" id="show-hide-country-panel">增加可用国家</a><a class="btn btn-info" id="btn-add-country">确定</a><span class="text-muted"></span>
           <div id="country-panel" class="checkbox" style="margin-left:10px;"><c:set var="preivousContinent" value="" /><c:set var="contryListCheckboxGroupIndex" value="0" /><%-- !! 以下约定国家已按大洲的顺序排列 --%>
           <c:forEach items="${AllCountryList}" var="country" varStatus="status"><c:if test="${preivousContinent ne country.continent}">    <div class="checkbox-group"><strong>${country.continent}</strong>
           &nbsp;&nbsp;</div>
           <c:set var="preivousContinent" value="${country.continent}" /><c:set var="contryListCheckboxGroupIndex" value="${contryListCheckboxGroupIndex + 1}" /></c:if><label class="checkbox-items"><input type="checkbox" name="countryList" class="countryListCheckboxGroup${contryListCheckboxGroupIndex}" value="${country.countryCode}"><span>${country.countryName}</span></label>
           </c:forEach>
           </div>
        </div>
      </div>
      <div class="table-responsive">
        <table class="table table-bordered table-hover" id="country-table">
          <thead>
            <tr>
              <th>名称</th>
              <th>MCC</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
   
          <c:forEach items="${Countries}" var="country" varStatus="status">
            <tr id="${country.countryCode}" class="selected-country">
              <td><a href="<%=basePath %>flowplan/countryinfo/view/${country.countryCode}">${country.countryName}</a>
              </td>
              <td>${country.countryCode}</td>
              <td>
                <div class="btn-toolbar"><a onclick="delete_country(${country.countryCode},'${country.countryName}');"
                  class="btn btn-danger btn-xs"> <span class="glyphicon glyphicon-remove"></span>删除</a>
                </div>
              </td>
            </tr>
          </c:forEach>
            
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<script type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    function delete_country(mcc, name){             
       bootbox.confirm("确定删除这个国家吗? " + name, function(result) {
           if(result){
               $.ajax({
                   type:"POST",
                   url:"<%=basePath %>orders/flowdealorders/updatecountry?op=delete&mcc=" + mcc + "&flowDealID=${Model.flowDealID}",
                   dataType:'html',
                   success:function(data){
                      if(data=="1"){
                          easy2go.toast('info',"删除成功");
                          $("input[name='oldCountryList'][value='" +$("#"+mcc).attr("id") + "']").prop("checked", false).prop('disabled',false).attr("name", "countryList");
                          $("#"+mcc).remove();                          
                      }else if(data=="-1"){
                          easy2go.toast('warn',"参数为空");
                      }else if(data=="-2"){
                          easy2go.toast('warn',"此订单已失效");
                      //}else if(data=="-3"){
                      //    easy2go.toast('warn',"删除出错, 请重试");
                      }else if(data=="-5"){
                          easy2go.toast('err',"请重新登录!");
                      } else { 
                          easy2go.toast('info',"删除出错, 请重试");
                      }
                   }
               });
           }  
       });
   }
    
    // 设置目前的已在订单中的国家不可选
    $(function(){
    	// 把那些国家的 name 由 countryList 改为 oldCountryList
    	$("tr.selected-country").each(function(){
    		$("input[name='countryList'][value='" + $(this).attr("id") + "']").prop("checked", true).prop('disabled',true).attr("name", "oldCountryList");

    	});    	
        $("#country-panel").hide();
        $("#btn-add-country").hide();
    });
    
    var shown = false;
    $("#show-hide-country-panel").click(function(){
    	if(shown) {
    		$(this).empty().append("增加可用国家");
    		$("#country-panel").hide();
    		$("#btn-add-country").hide();
    		shown = false;
    	} else {
    		$(this).empty().append("　　收起　　");
    		$("#country-panel").show();
    		$("#btn-add-country").show();
    		shown = true;
    	}

    });
    
    // 确认增加
    $("#btn-add-country").click(function(){
    	var mcc = "";
    	var name = "";
    	$("input[name='countryList']:checked").each(function(){
    		//alert($(this).next().text());
    		mcc = mcc + $(this).val() + ',';
    		name = name + $(this).next().text() + ' ';
    	});
    	if(mcc.length == 0) {
    		$("#btn-add-country").next().empty().append("&nbsp;&nbsp;请选择国家...");
    		return;
    	} else {
    		$("#btn-add-country").next().empty();
    		mcc.substring(0, mcc.length - 1);
    	}
    	bootbox.confirm("确定增加下列国家: " + name, function(result) {
            if(result){
                $.ajax({
                    type:"POST",
                    url:"<%=basePath %>orders/flowdealorders/updatecountry?op=add&mcc=" + mcc + "&flowDealID=${Model.flowDealID}",
                    dataType:'html',
                    success:function(data){
                       if(data=="1"){
                           easy2go.toast('info',"增加成功");
                           // 更新 checked 状态等
                           $("input[name='countryList']:checked").prop('disabled',true).attr("name", "oldCountryList");
                           $("#country-table tbody").empty();
                           // 刷新国家列表表格
                           $("input[name='oldCountryList']:checked").each(function(){
                               //alert($(this).next().text());
                               mcc = $(this).val();
                               name = $(this).next().text();
                               $("#country-table tbody").append("<tr id=\"" + mcc + "\" class=\"selected-country\"><td><a href=\"<%=basePath %>flowplan/countryinfo/view/" + mcc + "\">" + name + "</a></td><td>"  + mcc + "</td><td><div class=\"btn-toolbar\"><a onclick=\"delete_country(" + mcc + ",'" + name + "');\" class=\"btn btn-danger btn-xs\"> <span class=\"glyphicon glyphicon-remove\"></span>删除</a></div></td></tr>");
                           });
                           
                       }else if(data=="-1"){
                           easy2go.toast('warn',"参数为空");
                       }else if(data=="-2"){
                           easy2go.toast('warn',"此订单已失效");
                       //}else if(data=="-3"){
                       //    easy2go.toast('warn',"删除出错, 请重试");
                       }else if(data=="-5"){
                           easy2go.toast('err',"请重新登录!");
                       } else { 
                           easy2go.toast('info',"删除出错, 请重试");
                       }
                    }
                });
            }  
        });
    });
</script>
    <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
        <jsp:param name="matchType" value="backend" />
    </jsp:include>
  </body>
</html>