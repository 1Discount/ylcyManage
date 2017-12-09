<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>首页- ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body class="skin-blue">
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />



		<section id="main-content">
			<section class="wrapper">
				<%-- 这里添加页面主要内容  block content --%>
				<div class="row">
					<div class="col-md-3">
						<section class="panel">
							<div class="panel-body">
								<div class="top-stats-panel">
									<h4 class="widget-h">流量订单</h4>
									<div class="bar-stats">
										<ul class="progress-stat-bar clearfix">
											<li data-percent="${flowmap['unactivateP']}%"><span class="progress-stat-percent pink"></span></li>
											<li data-percent="${flowmap['activateP']}%"><span class="progress-stat-percent"></span></li>
											<li data-percent="${flowmap['unfinishedP']}%"><span class="progress-stat-percent yellow-b"></span></li>
										</ul>
										<ul class="bar-legend">
											<li><span class="bar-legend-pointer pink"></span>未激活订单&nbsp;${flowmap['unactivate'] }</li>
											<li><span class="bar-legend-pointer green"></span>激活订单&nbsp;${flowmap['activate'] }</li>
											<li><span class="bar-legend-pointer yellow-b"></span>未完成订单&nbsp;${flowmap['unfinished'] }</li>
										</ul>
										<div class="daily-sales-info">
											<span class="sales-count">${flowmap['count'] }</span>
											<span class="sales-label">个总订单数</span>
											&nbsp;&nbsp;
											<span class="sales-count">${flowmap['efficient'] } </span>
											<span class="sales-label">个正常订单</span>
											<span class="sales-count">${flowmap['delcount'] } </span>
											<span class="sales-label">个删除订单</span>
											<br />
											<%-- <span class="sales-label">流量订单总额</span><span class="sales-count" style="color:red;">&nbsp;&nbsp;${flowmap['amount'] }元</span> &nbsp;&nbsp; --%>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
					<div class="col-md-3">
						<section class="panel">
							<div class="panel-body">
								<div class="top-stats-panel">
									<h4 class="widget-h">设备订单</h4>
									<div class="bar-stats">
										<ul class="progress-stat-bar clearfix">
											<li data-percent="${devmap['buycountP']}%"><span class="progress-stat-percent pink"></span></li>
											<li data-percent="${devmap['rentcountp']}%"><span class="progress-stat-percent"></span></li>
											<li data-percent="${devmap['unfinishedP']}%"><span class="progress-stat-percent yellow-b"></span></li>
										</ul>
										<ul class="bar-legend">
											<li><span class="bar-legend-pointer pink"></span>购买 &nbsp;${devmap['buycount']}</li>
											<li><span class="bar-legend-pointer green"></span>租用 &nbsp;${devmap['rentcount']}</li>
											<li><span class="bar-legend-pointer yellow-b"></span>未完成&nbsp;${devmap['unfinished']}</li>
										</ul>
										<div class="daily-sales-info">
											<span class="sales-count">${devmap['count']} </span>
											<span class="sales-label">个订单</span>
											&nbsp;&nbsp;
											<span class="sales-count">${devmap['efficient']} </span>
											<span class="sales-label">个正常订单</span>
											<span class="sales-count">${devmap['delcount']} </span>
											<span class="sales-label">个删除订单</span>
											<br />
											<%-- <span class="sales-label">设备订单总额</span>&nbsp;&nbsp;<span class="sales-count" style="color:red;">${devmap['amount']}元 </span> --%>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>

					<div class="col-md-3">
						<section class="panel">
							<div class="panel-body">
								<div class="top-stats-panel">
									<h4 class="widget-h">设备</h4>
									<div class="bar-stats">
										<ul class="progress-stat-bar clearfix">
											<li data-percent="${devicemap['rkDevicep']}%"><span class="progress-stat-percent pink"></span></li>
											<li data-percent="${devicemap['chDevicep']}%"><span class="progress-stat-percent"></span></li>
											<li data-percent="${devicemap['wxDevicep']}%"><span class="progress-stat-percent yellow-b"></span></li>
										</ul>
										<ul class="bar-legend">
											<li><span class="bar-legend-pointer pink"></span>可使用 ${devicemap['rkDevice']}</li>
											<li><span class="bar-legend-pointer green"></span>使用中 ${devicemap['chDevice']}</li>
											<li><span class="bar-legend-pointer yellow-b"></span>不可用 ${devicemap['wxDevice']}</li>
										</ul>
										<div class="daily-sales-info">
											<span class="sales-count">${devicemap['allDevice']} </span>
											<span class="sales-label">个设备</span>
											&nbsp;&nbsp;
											<span class="sales-count">${devicemap['kyDevice']} </span>
											<span class="sales-label">个可用</span>
											<br />
											<span class="sales-count">${devicemap['bkyDevice']} </span>
											<span class="sales-label">个不可用</span>
											&nbsp;&nbsp;
											<span class="sales-count">${devicemap['deleDevice']}</span>
											<span class="sales-label">个已删除</span>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
					<div class="col-md-3">
						<section class="panel">
							<div class="panel-body">
								<div class="top-stats-panel">
									<h4 class="widget-h">客户</h4>
									<div class="bar-stats">
										<ul class="progress-stat-bar clearfix">
											<li data-percent="${customermap['resourceWebp']}%"><span class="progress-stat-percent pink"></span></li>
											<li data-percent="${customermap['resourceLurup']}%"><span class="progress-stat-percent"></span></li>
											<li data-percent="${customermap['resourceAppp']}%"><span class="progress-stat-percent yellow-b"></span></li>
										</ul>
										<ul class="bar-legend">
											<li><span class="bar-legend-pointer pink"></span>官网 ${customermap['resourceWeb']}</li>
											<li><span class="bar-legend-pointer green"></span>后台 ${customermap['resourceLuru']}</li>
											<li><span class="bar-legend-pointer yellow-b"></span>app ${customermap['resourceApp']}</li>
										</ul>
										<div class="daily-sales-info">
											<span class="sales-count">${customermap['allCount']} </span>
											<span class="sales-label">个客户</span>
											&nbsp;&nbsp;
											<span class="sales-count">${customermap['deleteCount']} </span>
											<span class="sales-label">个已删除客户</span>
											&nbsp;&nbsp;
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>


				<div class="row">
					<div class="col-sm-12">
						<section class="panel">
							<header class="panel-heading">
								近一个月设备接入数(接入成功并且有过流量上传的),按日期统计(2015-09-06开始,不含测试单)
								<span style="color: red;">提示：鼠标移上节点会显示详细，点击节点可查看详细 </span>
								<span class="tools pull-right">
									<a href="javascript:;" class="fa fa-chevron-down"></a>
								</span>
								<div style="display: inline-block;">
									<form action="<%=basePath%>devicelogs/tubiaoexecleexp">
										<DIV class="" style="display: inline-block;">
											<LABEL style="display: inline; line-height: 34px;" class="">时间段：</LABEL>
											<INPUT style="display: inline-block; width: auto;" id="zxcreatorDatebegin" class="form-control form_datetime" name="begindate" type="text" data-date-format="YYYY-MM-DD">
											<!-- <INPUT type="hidden" value="zhexiantu" name="status"/> -->
										</DIV>
										<DIV class="form-group" style="display: inline-block;">
											<LABEL style="display: inline; line-height: 34px;" class="">到</LABEL>
											<INPUT style="display: inline-block; width: auto;" id="zxcreatorDateend" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD">
										</DIV>

										<DIV class="form-group" style="display: inline-block;">
											<BUTTON class="btn btn-primary" type="button" onclick="search('zhexiantu')">搜索</BUTTON>
											<input type="hidden" id="temp" value="" />
										</div>
										<DIV class="form-group" style="display: inline-block;">
											<BUTTON class="btn btn-primary" type="submit" id="zhexianEcexlexp">导出Excel</BUTTON>
										</div>
									</form>
								</div>
							</header>
							<div class="panel-body">
								<div id="device-in-chart">
									<div id="device-in-container" style="width: 100%; height: 400px; text-align: center; margin: 0 auto;"></div>
								</div>
								<%--ahming notes:之前实现使用tableName在url中传递,跳转后再切换日期选择当前日期与url显示不一致,所以
                        现在改由form post, 避免url中传参 --%>
								<form id="query-form" method="post" action="<%=basePath%>devicelogs/deveceonlinepost">
									<input type="hidden" name="tableName" value="" />
								</form>
							</div>
							<%--                    <div id="device-in-chart-tooltip" class="chart-tooltip"></div> 不能放里,放到body首层--%>
						</section>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-9">
						<section class="panel" style="width: 134%;">
							<header class="panel-heading" style="height: 50px; width: 100%;">

								近一个月内各国家设备接入数(接入成功并且有过流量上传的),按国家统计(2015-09-06开始,不含测试单)
								<div style="display: inline-block;">
									<form action="<%=basePath%>devicelogs/bingtuexeclexport">
										<DIV class="form-group" style="display: inline-block;">
											<LABEL class="inline-label">时间段：</LABEL>
											<INPUT id="order_creatorDatebegin" style="width: auto; display: inline-block;" class="form-control form_datetime" name="begindate" type="text" data-date-format="YYYY-MM-DD">
											<INPUT type="hidden" value="bingtu" name="status" />

										</DIV>

										<DIV class="form-group" style="display: inline-block;">
											<LABEL class="inline-label">到</LABEL>
											<INPUT id="order_creatorDateend" style="width: auto; display: inline-block;" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD">
										</DIV>

										<DIV class="form-group" style="display: inline-block;">
											<BUTTON class="btn btn-primary" type="button" onclick="search('bingtu')">搜索</BUTTON>
											<!-- <input type="hidden" id="temp" value=""/> -->
										</div>
										<DIV class="form-group" style="display: inline-block;">
											<BUTTON class="btn btn-primary" type="submit" id="bingtuEcexlexp">导出Excel</BUTTON>
										</div>
									</form>
								</div>
								<span class="tools pull-right">
									<a href="javascript:;" class="fa fa-chevron-down"></a>
								</span>
							</header>
							<div class="panel-body">
								<div id="pie-chart" class="pie-chart">
									<div id="pie-chartContainer" style="width: 80%; height: 400px; text-align: left; margin-bottom: 160px;"></div>
								</div>
							</div>

						</section>
					</div>
					<%--                <div class="col-sm-6">--%>
					<%--                    <section class="panel">--%>
					<%--                        <header class="panel-heading">--%>
					<%--                           Donut Chart--%>
					<%--                        <span class="tools pull-right">--%>
					<%--                            <a href="javascript:;" class="fa fa-chevron-down"></a>--%>
					<%--                            <a href="javascript:;" class="fa fa-cog"></a>--%>
					<%--                            <a href="javascript:;" class="fa fa-times"></a>--%>
					<%--                         </span>--%>
					<%--                        </header>--%>
					<%--                        <div class="panel-body">--%>
					<%--                            <div id="pie-chart-donut" class="pie-chart">--%>
					<%--                                <div id="pie-donutContainer" style="width: 100%;height:400px; text-align: left;">--%>
					<%--                                </div>--%>
					<%--                            </div>--%>
					<%--                        </div>--%>
					<%--                    </section>--%>
					<%--                </div>--%>
				</div>

				<%--          <div class="row">--%>
				<%--            <div class="col-md-12">--%>
				<%--              <div class="panel">--%>
				<%--                <div class="panel-heading">近期3天数据服务交易</div>--%>
				<%--                <div class="panel-body">--%>
				<%--                 <table id="searchTable">--%>
				<%--        <tr>--%>
				<%--          <!--  <th w_render="order"  width="10%;">交易ID</th> -->--%>
				<%--            <th w_index="SN" width="10%;">绑定的设备SN</th>--%>
				<%--            <th w_index="customerName"  width="10%;">客户</th>--%>
				<%--            <th w_index="userCountry"  width="10%;">国家</th>--%>
				<%--            <th w_index="orderAmount"  width="5%;">总金额</th>--%>
				<%--            <th w_index="flowDays"  width="3%;">天数</th>--%>
				<%--            <th w_index="ifActivate"  width="3%;">是否激活</th>--%>
				<%--            <th w_index="activateDate"  width="10%;">激活时间</th>--%>
				<%--            <th w_index="orderStatus"  width="5%;">订单状态</th>--%>
				<%--            <th w_index="creatorUserName"  width="6%;">创建人</th>--%>
				<%--            <th w_index="creatorDate"  width="10%;">创建时间</th>--%>
				<%--            <th w_index="modifyDate"  width="10%;">最后更新</th>--%>
				<%--        </tr>--%>
				<%--</table>--%>
				<%--                </div>--%>
				<%--              </div>--%>
				<%--            </div>--%>
				<%--            <div class="col-md-12">--%>
				<%--              <div class="panel">--%>
				<%--                <div class="panel-heading">近期3天设备交易</div>--%>
				<%--                <div class="panel-body">--%>
				<%--                 <table id="searchTable1">--%>
				<%--       <tr>--%>
				<%--            <!-- <th w_render="orderidOP"  width="10%;">交易ID</th> -->--%>
				<%--            <th w_index="SN" width="10%;">SN</th>--%>
				<%--            <th w_index="customerName"  width="10%;">客户</th>--%>
				<%--            <th w_index="deallType"  width="10%;">交易类型</th>--%>
				<%--            <th w_index="dealAmount"  width="10%;">总金额</th>--%>
				<%--            <th w_index="ifReturn"  width="10%;">是否归还</th>--%>
				<%--            <th w_index="returnDate"  width="10%;">归还日期</th>--%>
				<%--            <th w_index="creatorUserName"  width="10%;">创建人</th>--%>
				<%--            <th w_index="creatorDate"  width="10%;">创建时间</th>--%>
				<%--            <th w_index="modifyDate"  width="10%;">最后更新</th>--%>
				<%--        </tr>--%>
				<%--</table>--%>
				<%--                </div>--%>
				<%--              </div>--%>
				<%--            </div>--%>
				<%--          </div>--%>

			</section>
		</section>

	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript">

    $(function(){
    	   $("#order_creatorDatebegin").datetimepicker({
   	 		pickDate: true,                 //en/disables the date picker
   	 		pickTime: true,                 //en/disables the time picker
   	 		showToday: true,                 //shows the today indicator
   	 		language:'zh-CN',                  //sets language locale
   	 		defaultDate: moment().add(-29, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
   	 	});

   	     $("#order_creatorDateend").datetimepicker({
   	  		pickDate: true,                 //en/disables the date picker
   	  		pickTime: true,                 //en/disables the time picker
   	  		showToday: true,                 //shows the today indicator
   	  		language:'zh-CN',                  //sets language locale
   	  		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
   	   	});

   	     $("#zxcreatorDatebegin").datetimepicker({
	 		pickDate: true,                 //en/disables the date picker
	 		pickTime: true,                 //en/disables the time picker
	 		showToday: true,                 //shows the today indicator
	 		language:'zh-CN',                  //sets language locale
	 		defaultDate: moment().add(-29, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
	 	});

	     $("#zxcreatorDateend").datetimepicker({
	  		pickDate: true,                 //en/disables the date picker
	  		pickTime: true,                 //en/disables the time picker
	  		showToday: true,                 //shows the today indicator
	  		language:'zh-CN',                  //sets language locale
	  		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
	   	});
	});
     var menu={"dashboardMenu":true};
     activeMenus();
    easy2go.responseMsg.info = "${info}".trim();
    easy2go.responseMsg.warn = "".trim();
    easy2go.responseMsg.error = "".trim();
    setTimeout(function(){
        easy2go.showResponseMSG();
    },350);
    </script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<%--    <script type="text/javascript">--%>
	<%--      $(function(){--%>
	<%--    	  var gridObj;--%>
	<%--    	  var gridObj1;--%>
	<%--             gridObj = $.fn.bsgrid.init('searchTable',{--%>
	<%--                 url:'<%=basePath %>orders/flowdealorders/getpage?limitdays=-3',--%>
	<%--                 pageSizeSelect: true,--%>
	<%--                 pageSize: 8,--%>
	<%--                 multiSort:true,--%>
	<%--                 pageSizeForGrid:[10],--%>
	<%--                 displayPagingToolbarOnlyMultiPages:true--%>
	<%--                 --%>
	<%--             });--%>
	<%--             gridObj1 = $.fn.bsgrid.init('searchTable1',{--%>
	<%--                 url:'<%=basePath %>orders/devicedealorders/getpage?limitdays=-3',--%>
	<%--                 pageSizeSelect: true,--%>
	<%--                 pageSize: 8,--%>
	<%--                 pageSizeForGrid:[10],--%>
	<%--                 displayPagingToolbarOnlyMultiPages:true--%>
	<%--                 --%>
	<%--             });--%>
	<%--      });--%>
	<%--    </script>--%>


	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="<%=basePath%>static/js/flot-chart/excanvas.min.js"></script><![endif]-->
	<!-- jQuery Flot Chart-->
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.min.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.tooltip.min.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.resize.min.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.pie.resize.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.selection.min.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.stack.min.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.time.min.js"></script>
	<%-- 这是测试数据和OK的模板对应上面的注释掉的测试div --%>
	<%--<script src="<%=basePath %>static/js/flot-chart/flot.chart.init.js"></script>--%>

	<script>
	var Datebegin = "";
	var Dateend = "";
	var zxDatebegin="";
	var zxDateend="";
	var status = "";
	$(function(){
		send();
	});
	function search(sta){
	     status = sta;
		 Datebegin =$("#order_creatorDatebegin").val();
		 Dateend =$("#order_creatorDateend").val();
		 zxDatebegin =$("#zxcreatorDatebegin").val();
		 zxDateend =$("#zxcreatorDateend").val();
		 send();
	}
	function send() {
	$.ajax({
        type:"GET",
        url:"<%=basePath%>devicelogs/getDeviceInCount?dateBegin="+Datebegin+"&dateEnd="+Dateend+"&zxdateBegin="+zxDatebegin+"&zxdateEnd="+zxDateend+"&status="+status,
        dataType:"html",
       // data:$('#form').serialize(),
        success:function(data){
        	var result = jQuery.parseJSON(data);

        	// 折线图
        	/* var data_by_day = [
			    [new Date(2005,9,1).getTime(), 253],
			    [new Date(2005,9,2).getTime(), 465],
			    [new Date(2005,9,3).getTime(), 498],
			    [new Date(2005,9,4).getTime(), 383],
			    [new Date(2005,9,5).getTime(), 280],
			    [new Date(2005,9,6).getTime(), 108],
			    [new Date(2005,9,7).getTime(), 120],
			    [new Date(2005,9,12).getTime(), 474],
			    [new Date(2005,9,13).getTime(), 623],
			    [new Date(2005,9,23).getTime(), 479],
			    [new Date(2005,9,24).getTime(), 788],
			    [new Date(2005,9,25).getTime(), 836]
			]; */
			var data_by_day = [];
			var countByDay = result.countByDay;
			for(var i = 0; i < countByDay.length; i++) {
				//alert(countByDay[i].date + ' ' + countByDay[i].count);
				data_by_day.push([new Date(countByDay[i].date).getTime(), countByDay[i].count ]);
			}
			var data_by_day_all = [];
            var countByDayAll = result.countByDayAll;
            for(var i = 0; i < countByDayAll.length; i++) {
            	data_by_day_all.push([new Date(countByDayAll[i].date).getTime(), countByDayAll[i].count]);
            }
            $.plot($("#device-in-chart #device-in-container"), [{
                data: data_by_day,
                label: "登陆成功且有过流量上传数",
                points: {
                    show: true
                },
                lines: {
                    fill: true
                }
                ,yaxis: 2
            }, { // 第二条线, 按日期不管是否有上传流量
                data: data_by_day_all,
                label: "登陆成功数",

                points: {
                    show: true
                },
                lines: {
                    show: true
                }
                ,yaxis: 2
            }
            ],
                {
                    series: {
                        lines: {
                            show: true,
                            fill: false
                        },
                        points: {
                            show: true,
                            lineWidth: 2,
                            fill: true,
                            fillColor: "#ffffff",
                            symbol: "circle",
                            radius: 5
                        },
                        shadowSize: 0
                    },
                    grid: {
                        hoverable: true,
                        clickable: true,
                        tickColor: "#f9f9f9",
                        borderWidth: 1,
                        borderColor: "#eeeeee"
                    },
                    colors: ["#79D1CF", "#E67A77"],
                    tooltip: false, //true,
                    tooltipOpts: {
                        defaultTheme: false
                    },
                    xaxis: {
                        mode: "time",
                        timeformat:"%m-%d"

                    },
                    yaxes: [{
                        /* First y axis */
                    }, {

                        /* Second y axis */
                        position: "right" /* left or right */

                    }],
                    legend: {
                    	margin: 20,
                    	position: "nw"
                    }
                }
            );

            $("#device-in-container").bind("plothover", function(event, pos, item) {
             if (item) {
            	 var date = new Date(item.datapoint[0]);
                 $("#device-in-chart-tooltip").html(item.series.label + "<br />" + (date.getMonth() + 1) + "-" + date.getDate() + " " +  item.datapoint[1] + "台")
                     .css({ top: item.pageY + 8, left: item.pageX + 8 })
                     .fadeIn(200);
             } else {
                 $("#device-in-chart-tooltip").hide();
             }
            });
            $("#device-in-container").bind("plotclick", function(event, pos, item) {
                if (item) {
                	var date = new Date(item.datapoint[0]);
                	$('input[name="tableName"]').val(date.getFullYear() + '-'
                            + ('0' + (date.getMonth() + 1)).substr(('0' + (date.getMonth() + 1)).length-2) + '-'
                            + ('0' + date.getDate()).substr(('0' + date.getDate()).length-2));
                	$('#query-form').submit();
<%--                	window.location.href = '<%=basePath %>devicelogs/deveceonline?tableName='--%>
<%--                			+ date.getFullYear() + '-'--%>
<%--                			+ ('0' + (date.getMonth() + 1)).substr(('0' + (date.getMonth() + 1)).length-2) + '-' --%>
<%--                			+ ('0' + date.getDate()).substr(('0' + date.getDate()).length-2);                    --%>
                }
            });

            // 饼图
/*            var dataPie = [{
                label: "香港",
                data: 12
            }, {
                label: "台湾",
                data: 8
            }, {
                label: "澳门",
                data: 5
            }]; */
            var dataPie = [];
            var countByMCC = result.countByMCC;
            for(var j = 0; j < countByMCC.length; j++) {
            	dataPie.push({label:countByMCC[j].name, data:countByMCC[j].count});
            }
            var optionsPie = {
                series: {
                	pie: {
                        show: true,
                        radius: 1, //半径
                        label: {
                            show: true,
                            radius: 2/3,
                            formatter: function(label, series){
                            	var s=series.data.toString();
                                return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+Math.round(series.percent)+'%</div>'; //+ s.split(',')[1] + '台 '
                            },
                            threshold: 0.03 //这个值小于0.03，也就是3%时，label就会隐藏
                        }
                    }
                },
                legend: {
                    show: true,
                    noColumns:2,
                    labelFormatter:function (label, series) {
                    	var s=series.data.toString();
              		  return ' <a href="javascript:void();" title="' +
              		 series.label + '">' +
              		 label + ' ' + s.split(',')[1] + '台</a>';
              		}
                },
                grid: {
                    hoverable: true,
                    clickable: true
                },
                /*colors: ["#79D1CF", "#D9DD81", "#E67A77"],*/
                tooltip: true,
                tooltipOpts: {
                    content: "%s: %p.0%", // show percentages, rounding to 2 decimal places
                    defaultTheme: false
                }
            };
            $.plot($("#pie-chart #pie-chartContainer"), dataPie, optionsPie);
        }
    });


}

</script>



	<div id="device-in-chart-tooltip" class="chart-tooltip"></div>
</body>
</html>