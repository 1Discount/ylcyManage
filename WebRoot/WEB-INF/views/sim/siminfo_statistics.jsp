<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>SIM卡状态统计-本地SIM卡管理-流量运营中心</title>
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
            <div class="panel-heading"><h3 class="panel-title">全部SIM卡状态统计</h3></div>
            <div class="panel-body">
	            <div class="table-responsive"><table id="searchTableSTotalCount">
		            <th w_index="count" width="10%;">SIM卡总数</th>
		            <th w_index="alert" width="10%;">充值预警卡数</th>
		            <th w_index="status_available" width="10%;">可用卡数</th>
		            <th w_index="status_using" width="10%;">使用中卡数</th>
		            <th w_index="status_unavailable" width="10%;">不可用卡数</th>
                    <th w_index="status_stop" width="10%;">停用卡数</th>
	            </table>
	            </div>
            </div>
          </div>
          </div> 
            
            <div class="col-md-12">
                <section class="panel">
                    <header class="panel-heading tab-bg-dark-navy-blue">
                        <ul class="nav nav-tabs nav-justified ">
                            <li class="active main-menu-tab">
                                <a data-toggle="tab" href="#country">按国家和地区</a>
                            </li>
                            <li class="main-menu-tab">
                                <a data-toggle="tab" href="#operator" onclick="loadByOperator();">按运营商</a>
                            </li>
                        </ul>
                    </header>
                    <div class="panel-body">
                        <div class="tab-content tasi-tab">
                            <div id="country" class="tab-pane active">
                                <div class="row">
                                    <div class="col-md-12">
            <div class="panel">
<%--            <div class="panel-heading"><h3 class="panel-title">本地SIM卡状态 - 全部国家</h3></div>--%>
            <div class="panel-body">
	        <div class="table-responsive"><table id="searchTableStatusByCountry">
	            <tr><th w_index="countryID" w_hidden="true">国家ID</th>
	            <th w_index="countryCode" w_hidden="true">MCC</th>
	            <th w_index="countryName" width="10%;">国家</th>
	            <th w_index="continent">大洲</th>
	            <th w_index="simsTotalCount">SIM卡总数<sup><a href="#special-note-country">(注1)</a></sup></th>
	            <th w_render="render_alertSimsCount">充值预警卡数</th>
	            <th w_index="avalableSimsCount">可用卡数</th>
	            <th w_index="usingSimsCount">使用中卡数</th>
	            <th w_index="unavalableSimsCount">不可用卡数</th>
                <th w_index="deadSimsCount">停用卡数</th>
	            <th w_render="render_cardStatus_avalableSimsCountPercent">可用卡百分比</th>
	            <th w_render="render_cardStatus_usingSimsCountPercent">使用中卡百分比</th>
	            <th w_render="render_cardStatus_unavalableSimsCountPercent">不可用卡百分比</th>
	            <th w_render="render_cardStatus_deadSimsCountPercent">停用卡百分比</th>
	            </tr>
	        </table></div>
            </div>
            </div>
            <div id="special-note-country">备注：<br><br>
<p>1. 按<strong><span class="text-success">“国家和地区”</span></strong>统计, 系按“可用国家”来统计的，所以若某张卡在多个国家或地区可用，则在各个国家或地区均会被统计，这样，这列的统计数字相加，并不一定与上面的“SIM卡总数”一致。其他全部列类似！</p>
<p>2. 按<strong><span class="text-success">“运营商”</span></strong>统计, 则不会这样。</p>
            </div>
                                    </div>
                                </div>
                            </div>
                            <div id="operator" class="tab-pane ">
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
                <th w_index="deadSimsCount">停用卡数</th>
                <th w_render="render_cardStatus_avalableSimsCountPercent">可用卡百分比</th>
                <th w_render="render_cardStatus_usingSimsCountPercent">使用中卡百分比</th>
                <th w_render="render_cardStatus_unavalableSimsCountPercent">不可用卡百分比</th>
                <th w_render="render_cardStatus_deadSimsCountPercent">停用卡百分比</th>
                </tr>
            </table></div>
            </div>
            </div>
            <div id="special-note-operator">备注：<br><br>
<p>1. 目前，按“运营商”查看未显示那些未指定运营商/运营商字段空白的记录。</p>
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
    var gridObjAllCount;
    $(function(){
    	gridObjAllCount = $.fn.bsgrid.init('searchTableSTotalCount',{
            url:'<%=basePath %>sim/siminfo/statistics/count',
            // autoLoad: false,
            pageAll:true,
        });
    });
    
    var gridObjStatusByCountry;
     $(function(){
             gridObjStatusByCountry = $.fn.bsgrid.init('searchTableStatusByCountry',{
                 url:'<%=basePath %>sim/siminfo/statistics/getpagebycountry',
                 // autoLoad: false,
                 pageAll:true,
             });
         });
    var gridObjStatusByOperator;
    var hasByOperatorLoaded = false;
     $(function(){
             gridObjStatusByOperator = $.fn.bsgrid.init('searchTableStatusByOperator',{
                 url:'<%=basePath %>sim/siminfo/statistics/getpagebyoperator',
                 autoLoad: false, // 到点击tab菜单后再加载
                 pageAll:true,
             });
         });
     function render_alertSimsCount(record, rowIndex, colIndex, options) {
        if (record.alertSimsCount == 0) {
            return 0;
        } else {
            //return '<span class="label label-danger label-xs">' + record.alertSimsCount + '</span>';
          return '<a class="btn btn-danger btn-xs" onclick="return;"><span>' + record.alertSimsCount + '</span></a>';
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
     function render_cardStatus_deadSimsCountPercent(record, rowIndex, colIndex, options) {
         return Math.round(record.deadSimsCountPercent * 10000)/100 + '%';
      }
     function loadByOperator() {
    	 if(!hasByOperatorLoaded){
    		  gridObjStatusByOperator.refreshPage();
    		  hasByOperatorLoaded = true;
    	 }
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
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
  </body>
</html>