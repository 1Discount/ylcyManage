<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>SIM卡状态统计柱状图-本地SIM卡管理-EASY2GO ADMIN</title>
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
	        <div class="panel-heading"><h3 class="panel-title">全部国家 - 漫游SIM卡状态</h3></div>
	        <div class="panel-body">

<c:set var="preivousContinent" value="" />
<%-- !! 以下约定国家已按大洲的顺序排列 --%>
<c:forEach items="${SimStatusStatByCountry}" var="country" varStatus="status">
    <c:if test="${preivousContinent ne country.continent}">
        <div style="clear:both;"><h4>${country.continent}</h4></div>
        <c:set var="preivousContinent" value="${country.continent}" />
    </c:if>
    <div class="col-md-3">
        <c:if test="${country.alertSimsCount gt 0}">
        <section class="panel orange">
        </c:if>
        <c:if test="${country.alertSimsCount eq 0}">
        <section class="panel">
        </c:if>
            <div class="panel-body">
                <div class="top-stats-panel">
                    <h4 class="widget-h">${country.countryName}</h4>
                    <div class="bar-stats">
                        <ul class="progress-stat-bar clearfix">
                            <li data-percent="${country.avalableSimsCountPercent}%"><span class="progress-stat-percent pink"></span></li>
                            <li data-percent="${country.usingSimsCountPercent}%"><span class="progress-stat-percent"></span></li>
                            <li data-percent="${country.unavalableSimsCountPercent}%"><span class="progress-stat-percent yellow-b"></span></li>
                        </ul>
                        <ul class="bar-legend">
                            <li><span class="bar-legend-pointer pink"></span>可用卡 ${country.avalableSimsCount}</li>
                            <li><span class="bar-legend-pointer green"></span>使用中卡 ${country.usingSimsCount}</li>
                            <li><span class="bar-legend-pointer yellow-b"></span>不可用卡 ${country.unavalableSimsCount}</li>
                        </ul>
                        <div class="daily-sales-info">
<%--                            <span class="sales-count">0 </span> <span class="sales-label">个在跑订单</span>&nbsp;&nbsp;
                            <a herf="#"><span class="sales-count">0 </span> <span class="sales-label">个预约订单</span></a> --%>
                            <span class="sales-label">SIM卡总数: </span> <span class="sales-count">${country.simsTotalCount} </span> 
                            <br /><span class="sales-label">充值预警数: </span> <span class="sales-count" style="color:red;">${country.alertSimsCount} </span>&nbsp;&nbsp;
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</c:forEach>
	        
	        </div>
	        </div>
          
          </div>          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script>
/*===Vertical Bar===*/
$(function () {
    "use strict";
    jQuery('.progress-stat-bar li').each(function () {
        jQuery(this).find('.progress-stat-percent').animate({
            height: jQuery(this).attr('data-percent')
        }, 1000);
    });
});
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>