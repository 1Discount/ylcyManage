<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" +

			request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>流量订单服务端口跳转</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="csrf_token">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
	<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

	<SECTION id="main-content"> <SECTION class="wrapper">
	<DIV class="col-md-12">
		<DIV class="panel">
			<DIV class="panel-body">
				<form class="form-inline" id="searchForm" role="form" method="get"
					action="#">
					<div class="form-group">
						<label class="inline-label">国 家：</label><input
							class="form-control" name="countryName" id="countryName"
							type="text" />
					</div>
					<div class="form-group">
						<label class="inline-label">日&nbsp;期：</label> <INPUT
							id="order_creatorDatebegin" class="form-control form_datetime"
							name="tableName" type="text" data-date-format="YYYY-MM-DD">
					</div>
					<div class="form-group">
						<button class="btn btn-primary" type="button" id="search"
							onclick="stopsubmit();$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button>
						<i
							style="float: right; margin-left: 150px; font-weight: 900; color: #333; font-size: 15px; background: #fff;"
							class="btn  "><i style="font-size: 18px;">*温馨提示：</i>
							设备接入数表示当天登陆成功并且有流量上传过的用户</i>
					</div>
				</form>
			</DIV>
			<DIV class="panel-heading">
				<H3 class="panel-title">接入设备统计</H3>
			</DIV>
			<DIV class="panel-body">
				<table id="searchTable">
					<tr classs="extend_render_per_row">
						<th w_index="MCC" width="10%;">国家</th>
						<th w_index="SNCount" width="10%;">接入设备数</th>
						<th w_index="SN" w_length="9999999999999" width="10%;">设备机身码</th>
						<th w_render="operate" width="2%;">操作</th>
					</tr>
				</table>
			</DIV>
			<DIV class="panel-body"
				style="float: right; margin-right: 45px; border: 1px solid #ddd; width: 280px; height: 700px;">
				<DIV class="panel-heading" style="margin-top: -15px;">

					<H3 class="panel-title">导出数据(不填国家则导出各国)</H3>
				</DIV>
				<br />
				<form class="form-inline" id="searchForm" role="form" method="get"
					action="#">

					<div class="form-group">
						<label class="inline-label">国 家：</label><input
							class="form-control" name="cName" id="cName" type="text" />
					</div><br /> <br /> 
					<div class="form-group">
						<label class="inline-label">开始：</label> <input id="beginTime"
							class="form-control form_datetime" name="beginTime" type="text"
							placeholder="开始时间" data-date-format="YYYY-MM-DD"
							data-popover-offset="10,-105">
					</div><br /> <br /> 

					<div class="form-group">
						<label class="inline-label">结束：</label> <input id="endTime"
							class="form-control form_datetime" name="endTime" type="text"
							placeholder="结束时间" data-date-format="YYYY-MM-DD"
							data-popover-offset="10,-105">
					</div>
					<br /> <br /> <br /> 
						<div class="paomadeng">
								<span style="font-size: 14px; font-weight: bolder;"><a id="a"
									href="<%=basePath%>devicelogs/exportexecl?" ><img
										src="<%=basePath%>static/images/excel.jpg"
										style="float: left; margin-top: -5px;"  width="30" height="30"
										title="" /> <marquee style=" margin-top: -25px;">导出EXCEL请点我</marquee></a></span>
							</div>
				</form>
			</DIV>


			<div class="row" style="display: none;">
				<div class="col-sm-12" >
					<section class="panel"> <header class="panel-heading">
					近一个月设备接入数(接入成功并且有过流量上传的),按日期统计(2015-09-06开始,不含测试单) <span
						style="color: red;">提示：鼠标移上节点会显示详细，点击节点可查看详细</span> <span
						class="tools pull-right"> <a href="javascript:;"
						class="fa fa-chevron-down"></a>
					</span> </header>
					<div class="panel-body">
						<div id="device-in-chart">
							<div id="device-in-container"
								style="width: 100%; height: 400px; text-align: center; margin: 0 auto;">
							</div>
						</div>
					</div>
					<%--                    <div id="device-in-chart-tooltip" class="chart-tooltip"></div> 不能放里,放到body首层--%>
					</section>
				</div>
			</div>

			<div class="row">
				<div class="col-sm-9" style=" border: 1px solid #ddd; padding-left: 0; margin-left: 30px;">
					<section class="panel"> <header class="panel-heading">
					当天各国家设备接入数(接入成功并且有过流量上传的),按国家统计(不含测试单) <span
						class="tools pull-right"> <a href="javascript:;"
						class="fa fa-chevron-down"></a>
					</span> </header>
					<div class="panel-body">
						<div id="pie-chart" class="pie-chart">
							<div id="pie-chartContainer"
								style="width: 100%; height: 600px; text-align: left;"></div>
						</div>
					</div>
					</section>
				</div>
			</div>
		</DIV>
	</DIV>
	</SECTION> </SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/jquery.zclip.min.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.min.js"></script>
	<script
		src="<%=basePath%>static/js/flot-chart/jquery.flot.tooltip.min.js"></script>
	<script
		src="<%=basePath%>static/js/flot-chart/jquery.flot.resize.min.js"></script>
	<script
		src="<%=basePath%>static/js/flot-chart/jquery.flot.pie.resize.js"></script>
	<script
		src="<%=basePath%>static/js/flot-chart/jquery.flot.selection.min.js"></script>
	<script
		src="<%=basePath%>static/js/flot-chart/jquery.flot.stack.min.js"></script>
	<script src="<%=basePath%>static/js/flot-chart/jquery.flot.time.min.js"></script>

	<script>
$(function(){
	$("#a").click(function(){
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var cName = $("#cName").val();
		var href = $(this).attr('href');
		
		if($("#cName").val()==""){
			$(this).attr('href','<%=basePath%>devicelogs/exportexecl?beginTime='+beginTime+'&endTime='+endTime+'&cName='+cName);
		}else{
			$(this).attr('href','<%=basePath%>devicelogs/exportexeclone?beginTime='+beginTime+'&endTime='+endTime+'&cName='+cName);
		}
	});
	loadb();
});
function loadb() {
	var d = $("#order_creatorDatebegin").val();
	var countryName = $("#countryName").val();
	$.ajax({
        type:"GET",
        url:"<%=basePath%>devicelogs/getDeviceInCount1?tableName="+d+"&countryName="+countryName,
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
				data_by_day.push([new Date(countByDay[i].date).getTime(), countByDay[i].count]);
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
                	window.location.href = '<%=basePath%>devicelogs/deveceonline?tableName='
                			+ date.getFullYear() + '-'
                			+ ('0' + (date.getMonth() + 1)).substr(('0' + (date.getMonth() + 1)).length-2) + '-' 
                			+ ('0' + date.getDate()).substr(('0' + date.getDate()).length-2);                    
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
                                return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>' + Math.round(series.percent)+'%</div>'; //+ s.split(',')[1] + '台 '  
                            },  
                            threshold: 0.03 //这个值小于0.03，也就是3%时，label就会隐藏
                        }
                    }
                },
                legend: {
                    show: true,
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


	<script type="text/javascript">
	  //自定义列
    function operate(record, rowIndex, colIndex, options) {
		  var SN=record.SN;
         return '<a href="#" id="'+rowIndex+'" onmousemove="copy('+rowIndex+',\''+SN+'\')" class="glyphicon btn btn-primary btn-xs">复制机身码</a>';
	  }
	 
	var tableName = '${tableName}'; // 形如2015-09-22格式
	var isTableNameEmpty = false;
    if(tableName.length == 0){ // 当指定日期时使用指定日期为默认初始化日期
        tableName = moment();
        isTableNameEmpty = true;
    }
 	  var gridObj;
 	  var aa;
      $(function(){
         gridObj = $.fn.bsgrid.init('searchTable',{
             url:'<%=basePath%>/devicelogs/getonline',
                autoLoad:false,
				pageSizeSelect : true,
				pageAll:true,
			    extend: {
	                settings:{
	                    supportColumnMove: true // if support extend column move, default false
	                }
	            }
			});
			$("#order_creatorDatebegin").datetimepicker({
				pickDate : true, //en/disables the date picker
				pickTime : true, //en/disables the time picker
				showToday : true, //shows the today indicator
				language : 'zh-CN', //sets language locale
				//defaultDate : moment().add(0, 'days'), //moment(), //sets a default date, accepts js dates, strings and moment objects
                defaultDate :  moment().add(0, 'days'),
			});
			$("#order_creatorDateend").datetimepicker({
				pickDate : true, //en/disables the date picker
				pickTime : true, //en/disables the time picker
				showToday : true, //shows the today indicator
				language : 'zh-CN', //sets language locale
				defaultDate : moment().add(0, 'days'), //sets a default date, accepts js dates, strings and moment objects
			});
			$("#order_creatorDatebegin").click(function(){
				 aa = $("#order_creatorDatebegin").val();
			});
		    // 不 autoLoad 后， 手动初次刷新
			$("#order_creatorDatebegin").blur();
            $("#search").click();
            //event.returnValue = false;<%--此处没event--%>
            $("#beginTime").datetimepicker({
                pickDate: true,                 //en/disables the date picker
                pickTime: false,                 //en/disables the time picker
                showToday: true,                 //shows the today indicator
                language:'zh-CN',                  //sets language locale
                //defaultDate: moment().add(-1, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
                defaultDate: moment().add(-10, 'days'),
            });
             
             $("#endTime").datetimepicker({
                pickDate: true,                 //en/disables the date picker
                pickTime: false,                 //en/disables the time picker
                showToday: true,                 //shows the today indicator
                language:'zh-CN',                  //sets language locale
                defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
            });

		});
      $(document).keydown(function(event){ 
      	if(event.keyCode == 13){//绑定回车 
      		$("#order_creatorDatebegin").blur();
      		$("#search").click();
      		event.returnValue = false;
      	} 
      }); 
    
      function formatDate(date, format) {   
    	    if (!date) return;   
    	    if (!format) format = "yyyy-MM-dd";   
    	    switch(typeof date) {   
    	        case "string":   
    	            date = new Date(date.replace(/-/, "/"));   
    	            break;   
    	        case "number":   
    	            date = new Date(date);   
    	            break;   
    	    }    
    	    if (!date instanceof Date) return;   
    	    var dict = {   
    	        "yyyy": date.getFullYear(),   
    	        "M": date.getMonth() + 1,   
    	        "d": date.getDate(),   
    	        "H": date.getHours(),   
    	        "m": date.getMinutes(),   
    	        "s": date.getSeconds(),   
    	        "MM": ("" + (date.getMonth() + 101)).substr(1),   
    	        "dd": ("" + (date.getDate() + 100)).substr(1),   
    	        "HH": ("" + (date.getHours() + 100)).substr(1),   
    	        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
    	        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    	    };       
    	    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
    	        return dict[arguments[0]];   
    	    });                   
    	}   
     function stopsubmit(){
		var date = $("#order_creatorDatebegin").val().replace("-","");
			date = date.replace("-","");
		var mydate = new Date();
		var mydate =formatDate(mydate.toLocaleDateString(), "yyyy/MM/dd");
			mydate = mydate.replace("/","").replace("/","");
		if(date>mydate){
			easy2go.toast('warn', "日期不能大于当天");
			$("#order_creatorDatebegin").val(aa);	
			return mySubmit(false);  
		}else{
			loadb();
		}
    }
     

	function copy(t,sn){
		//alert(t+":::"+sn);
			 // alert(SN);
			/*    var j=sn.split("w").length;
			    for(var i=0;i<j-1;i++){
					sn=sn.replace("w","/");
			   	}   */ 
		 $("#"+t).zclip({
			path:'<%=basePath%>static/js/ZeroClipboard.swf',
	 		copy:sn+'',
	 		afterCopy: function(){
	 			//$("<span id='msg'/>").insertAfter($('#copy_input')).text('复制成功').fadeOut(2000);
	 			easy2go.toast('info', "复制成功!");
	 		}
	 	});
	} 
	 


 
	</script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>