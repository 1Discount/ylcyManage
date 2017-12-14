<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 格式化时间 -->
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>SIM卡记录-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
    content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
    href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
    href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
/*         #searchTable tr{height:40px;} */
</style>
</head>
<body>

    <section id="container">
        <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
        <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

        <SECTION id="main-content">
            <SECTION class="wrapper" style="">
            
                <div class="row">
                    <div class="col-sm-12">
                        <section class="panel">
                            <header class="panel-heading">
                                                                                                时间段内某个国家的SIM卡使用记录
                                <span style="color: red;">提示：鼠标移上节点会显示详细，点击节点可查看详细 </span>
                                <span class="tools pull-right">
                                    <a href="javascript:;" class="fa fa-chevron-down"></a>
                                </span>
                                <div style="display: inline-block;">
                                    <form action="#" id="form">
                                        <DIV class="" style="display: inline-block;">
                                            <LABEL style="display: inline; line-height: 34px;" class="">时间段：</LABEL>
                                            <INPUT style="display: inline-block; width: auto;" id="zxcreatorDatebegin" class="form-control form_datetime" name="dateBegin" type="text" data-date-format="YYYY-MM-DD">
                                        </DIV>
                                        <DIV class="form-group" style="display: inline-block;">
                                            <LABEL style="display: inline; line-height: 34px;" class="">到</LABEL>
                                            <INPUT style="display: inline-block; width: auto;" id="zxcreatorDateend" class="form-control form_datetime" name="dateEnd" type="text" data-date-format="YYYY-MM-DD">
                                        </DIV>
                                        <div class="form-group" style="display: inline-block;">
								          <label style="display: inline; line-height: 34px;">国&nbsp;家：</label>
								          <select name="country" style="display: inline-block; width: auto;" class="form-control">
								            <c:forEach items="${Countries}" var="country" varStatus="status">
								                <option value="${country.countryCode}">${country.countryName}</option>
								            </c:forEach>
								          </select>
								        </div>
                                        <DIV class="form-group" style="display: inline-block;">
                                            <BUTTON class="btn btn-primary" type="button" onclick="search('zhexiantu')">搜索</BUTTON>
                                            <input type="hidden" id="temp" value="" />
                                        </div>
                                    </form>
                                </div>
                            </header>
                            <div class="panel-body">
                                <div id="device-in-chart">
                                    <div id="device-in-container" style="width: 100%; height: 400px; text-align: center; margin: 0 auto;"></div>
                                </div>
                                <form id="query-form" method="post" action="#">
                                    <input type="hidden" name="tableName" value="" />
                                </form>
                            </div>
                        </section>
                    </div>
                </div>
                
            </SECTION>
        </SECTION>
    </section>
    <script src="<%=basePath%>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    
    <script type="text/javascript">
        
          
    var gridObj;
    $(function(){
        
       $("#zxcreatorDatebegin").datetimepicker({
           format: 'YYYY-MM-DD',
           pickDate: true,     //en/disables the date picker
           pickTime: true,     //en/disables the time picker
           showToday: true,    //shows the today indicator
           language:'zh-CN',   //sets language locale
           defaultDate: moment().add(-29, 'days') // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
       });
       $("#zxcreatorDateend").datetimepicker({
           format: 'YYYY-MM-DD',
           pickDate: true,     //en/disables the date picker
           pickTime: true,     //en/disables the time picker
           showToday: true,    //shows the today indicator
           language:'zh-CN',   //sets language locale
           defaultDate: moment().add(0, 'days') // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
       });
       
    });   
    
    $(function(){   
        
        send();
        
    });
    
    </script>
    
    <script src="<%=basePath%>static/js/flot-chart/jquery.flot.min.js"></script>
    <script src="<%=basePath%>static/js/flot-chart/jquery.flot.tooltip.min.js"></script>
    <script src="<%=basePath%>static/js/flot-chart/jquery.flot.resize.min.js"></script>
    <script src="<%=basePath%>static/js/flot-chart/jquery.flot.pie.resize.js"></script>
    <script src="<%=basePath%>static/js/flot-chart/jquery.flot.selection.min.js"></script>
    <script src="<%=basePath%>static/js/flot-chart/jquery.flot.stack.min.js"></script>
    <script src="<%=basePath%>static/js/flot-chart/jquery.flot.time.min.js"></script>
    <script>
    function search(){
    	send();
    }
    
    function send(){
 	   $.ajax({
 	        type:"GET",
 	        url:"<%=basePath%>sim/siminfo/getSIMRecordCount",
 	        dataType:"html",
 	        data:$('#form').serializeArray(),
 	        success:function(data){
 	            var result = jQuery.parseJSON(data);
 	        
 	            var use_record_count = [];
 	            var useRecordCount = result.useRecordCount;
 	            for(var i = 0; i < useRecordCount.length; i++) {
 	            	use_record_count.push([new Date(useRecordCount[i].date).getTime(), useRecordCount[i].count ]);
 	            }
 	            
                var record_count = [];
                var recordCount = result.recordCount;
                for(var i = 0; i < recordCount.length; i++) {
                	record_count.push([new Date(recordCount[i].date).getTime(), recordCount[i].count ]);
                }
 	            
 	            $.plot($("#device-in-chart #device-in-container"), [{
 	                data: use_record_count,
 	                label: "使用过的卡总数",
 	                points: {
 	                    show: true
 	                },
 	                lines: {
 	                    fill: true
 	                }
 	                ,yaxis: 2
 	            }, { // 第二条线, 按日期不管是否有上传流量
 	                data: record_count,
 	                label: "可用卡总数",

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
 	                    }, {
 	                        
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
 	                 $("#device-in-chart-tooltip").html(item.series.label + "<br />" + (date.getMonth() + 1) + "-" + date.getDate() + " " +  item.datapoint[1] + "张")
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
 	                }
 	            });
 	        }
 	   });
    }
    
    

    </script>

    <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
        <jsp:param name="matchType" value="exactly" />
    </jsp:include>
    <div id="device-in-chart-tooltip" class="chart-tooltip"></div>
</body>
</html>