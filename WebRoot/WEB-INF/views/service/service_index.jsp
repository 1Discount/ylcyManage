<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>首页-客服监控中心-流量运营中心</title>
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
                <section class="panel">
                    <header class="panel-heading tab-bg-dark-navy-blue">
                        <ul class="nav nav-tabs nav-justified ">
                            <li class="active main-menu-tab">
                                <a data-toggle="tab" href="#overview">概况</a>
                            </li>
                            <li class="main-menu-tab">
                                <a data-toggle="tab" href="#device">设备</a>
                            </li>
                            <li class="main-menu-tab">
                                <a data-toggle="tab" href="#flow">流量</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                        <div class="tab-content tasi-tab">
                            <div id="overview" class="tab-pane active">
                                <div class="row">
                                    <div class="col-md-12">
            <div class="panel">
            <div class="panel-heading"><h3 class="panel-title">本地SIM卡状态 - 全部国家</h3></div>
            <div class="panel-body">
	        <div class="table-responsive"><table id="searchTableStatusByCountry">
	            <tr><th w_index="countryID" w_hidden="true">国家ID</th>
	            <th w_index="countryCode" w_hidden="true">MCC</th>
	            <th w_index="countryName" width="10%;">国家</th>
	            <th w_index="continent">大洲</th>
	            <th w_index="simsTotalCount">SIM卡总数</th>
	            <th w_render="render_alertSimsCount">充值预警卡数</th>
	            <th w_index="avalableSimsCount">可用卡数</th>
	            <th w_index="usingSimsCount">使用中卡数</th>
	            <th w_index="unavalableSimsCount">不可用卡数</th>
	            <th w_render="render_cardStatus_avalableSimsCountPercent">可用卡百分比</th>
	            <th w_render="render_cardStatus_usingSimsCountPercent">使用中卡百分比</th>
	            <th w_render="render_cardStatus_unavalableSimsCountPercent">不可用卡百分比</th></tr>
	        </table></div>
            </div>
            </div>
                                    </div>
                                </div>
                            </div>
                            <div id="device" class="tab-pane ">
                                <div class="row">
                                    <div class="col-md-12">
            <div class="panel">
            <div class="panel-heading"><h3 class="panel-title">本地SIM卡状态 - 全部运营商</h3></div>
            <div class="panel-body">
            <div class="table-responsive"><table id="searchTableStatusByOperator">
                <tr><th w_index="operatorID" w_hidden="true">运营商ID</th>
                <th w_index="MCC" w_hidden="true">MCC</th>
                <th w_index="countryName" width="10%;">国家</th>
                <th w_index="operatorName">运营商</th>
                <th w_index="simsTotalCount">SIM卡总数</th>
                <th w_render="render_alertSimsCount">充值预警卡数</th>
                <th w_index="avalableSimsCount">可用卡数</th>
                <th w_index="usingSimsCount">使用中卡数</th>
                <th w_index="unavalableSimsCount">不可用卡数</th>
                <th w_render="render_cardStatus_avalableSimsCountPercent">可用卡百分比</th>
                <th w_render="render_cardStatus_usingSimsCountPercent">使用中卡百分比</th>
                <th w_render="render_cardStatus_unavalableSimsCountPercent">不可用卡百分比</th></tr>
            </table></div>
            </div>
            </div>
                                    </div>
                                </div>
                            </div>                            
                            <div id="flow" class="tab-pane ">
                                <div class="row">
                                    <div class="col-md-12">
            <div class="panel">
<%--            <div class="panel-heading"><h3 class="panel-title">本地SIM卡状态 - 全部运营商</h3></div>--%>
            <div class="panel-body">
            <div class="table-responsive"><table id="searchTableFlow">
                <tr><th w_index="operatorID" w_hidden="true">运营商ID</th>
                <th w_index="MCC" w_hidden="true">MCC</th>
                <th w_index="countryName" width="10%;">国家</th>
                <th w_index="operatorName">运营商</th>
                <th w_index="simsTotalCount">SIM卡总数</th>
                <th w_render="render_alertSimsCount">充值预警卡数</th>
                <th w_index="avalableSimsCount">可用卡数</th>
                <th w_index="usingSimsCount">使用中卡数</th>
                <th w_index="unavalableSimsCount">不可用卡数</th>
                <th w_render="render_cardStatus_avalableSimsCountPercent">可用卡百分比</th>
                <th w_render="render_cardStatus_usingSimsCountPercent">使用中卡百分比</th>
                <th w_render="render_cardStatus_unavalableSimsCountPercent">不可用卡百分比</th></tr>
            </table></div>
            </div>
            </div>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                </section>
            </div>
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script>
    var gridObjStatusByCountry;
     $(function(){
             gridObjStatusByCountry = $.fn.bsgrid.init('searchTableStatusByCountry',{
                 url:'<%=basePath %>sim/siminfo/statistics/getpagebycountry',
                 // autoLoad: false,
                 pageAll:true,
             });
         });
    var gridObjStatusByOperator;
     $(function(){
             gridObjStatusByOperator = $.fn.bsgrid.init('searchTableStatusByOperator',{
                 url:'<%=basePath %>sim/siminfo/statistics/getpagebyoperator',
                 // autoLoad: false,
                 pageAll:true,
             });
         });
     function render_alertSimsCount(record, rowIndex, colIndex, options) {
        if (record.alertSimsCount == 0) {
            //return '<span class="label label-success label-xs">' + record.alertSimsCount + '</span>';
            return 0;
        } else {
            return '<span class="label label-danger label-xs">' + record.alertSimsCount + '</span>';
        }
     }
     function render_cardStatus_avalableSimsCountPercent(record, rowIndex, colIndex, options) {
        return Math.round(record.avalableSimsCountPercent * 10000)/100 + '%';
     }
     function render_cardStatus_usingSimsCountPercent(record, rowIndex, colIndex, options) {
        return Math.round(record.usingSimsCountPercent * 10000)/100 + '%';
     }
     function render_cardStatus_unavalableSimsCountPercent(record, rowIndex, colIndex, options) {
        return Math.round(record.unavalableSimsCountPercent * 10000)/100 + '%';
     }
    
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
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>