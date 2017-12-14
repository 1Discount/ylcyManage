<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>设备流量使用情况分析-经营分析报表-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
    <section id="container">
        <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
        <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
        <SECTION id="main-content">
            <SECTION class="wrapper">
                <DIV class="col-md-12">
                    <DIV class="panel">
                       <DIV class="panel-body">
                           <FORM class="form-inline" id="leibie" method="get" action="#">
                           <input type="hidden" id="pagesize" value="10" />
                           <input type="hidden" value="1" id="pagenum" />
                               <div class="form-group">
                                    <LABEL class="inline-label">日期：</LABEL>
                                    <input type="text" id="beginTime" name="beginTime" value="${beginTime}" data-popover-offset="0,8" class="form_datetime form-control"/>-
                                    <input type="text" id="endTime" name="endTime" value="${endTime}" data-popover-offset="0,8" class="form_datetime form-control"/>
                               </div>
                               <div class="form-group">
                                    <LABEL class="inline-label">机身码：</LABEL>
                                    <input type="text" id="SN" name="SN" data-popover-offset="0,8" class="form-control"/>
                                    <input type="hidden" id="MCC" name="MCC" value="${MCC}"/>
                               </div>
                               <div class="form-group">
                                    <LABEL class="inline-label">不纳入统计流量阀值(MB)：</LABEL>
                                    <input type="text" id="flowCount" name="flowCount" data-popover-offset="0,8" class="form-control"/>
                               </div>
                               <DIV class="form-group">
                                   <BUTTON class="btn btn-primary" type="button"
                                       onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
                               </DIV>
                           </FORM>
                           <form id="testc"></form>
                       </DIV>
                   </DIV>
                    <DIV class="panel">
                        <DIV class="panel-heading">
                            <H3 class="panel-title">设备流量使用情况</H3>
                        </DIV>
                        <DIV class="panel-body">
                            <table id="searchTable">
                                <tr>
                                    <th w_index="countryName" width="14%;">国家</th>
                                    <th w_render="SN" width="14%;">机身码</th>
                                    <th w_render="avgFlow" width="14%;">日流量均值</th>
                                    <th w_render="maxFlow" width="14%;">日流量峰值</th>
                                    <th w_render="minFlow" width="14%;">日流量谷值</th>
                                    <th w_render="middleFlow" width="14%;">日流量中位数</th>
                                    <th w_render="sumFlow" width="16%;">日流量总值</th>
                                </tr>
                            </table>
                        </DIV>
                    </DIV>
                    <DIV class="panel">
                        <div class="paomadeng">
                             <span style="font-size: 14px; font-weight: bolder;" onclick="return execl()" >
                             <a id="a" href="<%=basePath %>deviceflow/exportdeviceflowstatistics?">
                             <img src="<%=basePath%>static/images/excel.jpg" style="float: left; margin-top: -5px;"  width="30" height="30" title="" /> 导出全部</a></span>
                        </div>
                    </DIV>
                </DIV>
            </SECTION>
        </SECTION>
    </section>
    <script src="<%=basePath%>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <SCRIPT type="text/javascript">
    var gridObj;
    $(function(){
        $("#beginTime").datetimepicker({
            pickDate : true, //en/disables the date picker
            pickTime : false, //en/disables the time picker
            showToday : true, //shows the today indicator
            language : 'zh-CN', //sets language locale
            defaultDate : moment().add(-30, 'days'),
        });

        $("#endTime").datetimepicker({
            pickDate : true, //en/disables the date picker
            pickTime : false, //en/disables the time picker
            showToday : true, //shows the today indicator
            language : 'zh-CN', //sets language locale
            defaultDate : moment().add(0, 'days'),
        });
        var pagesize=parseInt($("#pagesize").val());

        gridObj = $.fn.bsgrid.init('searchTable',{
            url:'<%=basePath%>deviceflow/deviceflowstatisticspage',
            pageSizeSelect: true,
            pageSize:pagesize,
            autoLoad:false,
            otherParames:$('#leibie').serializeArray(),
            pageSizeForGrid:[15,30,50,100],
            displayPagingToolbarOnlyMultiPages:true,
            additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                 if(parseSuccess){
                     $("#pagenum").val(options.curPage);
                     $("#pagesize").val( $("#searchTable_pt_pageSize").val());
                 }
             },
        });
        
        if($("#pagenum").val()!=""){
           gridObj.page($("#pagenum").val());
        }else{
           gridObj.page(1);
        };
        
    });
    
    function sfjs(){
    	var fwdklb = $(".fwdklb").attr("data-val");
    	if(fwdklb=="0")
    	{
    		
    	}
    	else if(fwdklb=="1")
    	{
    		
    	}
        $("#result1").attr("style","display:none;");
        $("#title").attr("style","display:none;");
        $("#result2").attr("style","display:block;");
    }

    function avgFlow(record, rowIndex, colIndex, options) {
        return '<a title="' + record.avgFlow + '">' + prettyByteUnitSize(record.avgFlow,2,1,true) + '</a>';
    }

    function maxFlow(record, rowIndex, colIndex, options) {
        return '<a title="' + record.maxFlow + '">' + prettyByteUnitSize(record.maxFlow,2,1,true) + '</a>';
    }

    function minFlow(record, rowIndex, colIndex, options) {
        return '<a title="' + record.minFlow + '">' + prettyByteUnitSize(record.minFlow,2,1,true) + '</a>';
    }

    function middleFlow(record, rowIndex, colIndex, options) {
        return '<a title="' + record.middleFlow + '">' + prettyByteUnitSize(record.middleFlow,2,1,true) + '</a>';
    }

    function sumFlow(record, rowIndex, colIndex, options) {
        return '<a title="' + record.sumFlow + '">' + prettyByteUnitSize(record.sumFlow,2,1,true) + '</a>';
    }

    function SN(record, rowIndex, colIndex, options) {
    	var str = "beginTime="+$("#beginTime").val()+"&endTime="+$("#endTime").val()+"&SN="+record.SN;
    	return '<a href="<%=basePath %>devicelogs/flowBySnAndDate?'+str+'" target="_blank">' + record.SN + '</a>';
    }
    
   $(document).keydown(function(event){
       if(event.keyCode == 13){//绑定回车
           $("#beginTime").blur();
           $("#endTime").blur();
           gridObj.options.otherParames =$('#leibie').serializeArray();
           gridObj.page(1);
           event.returnValue = false;
       }
   });
   
   function execl(){
       var par = $('#leibie').serialize();
       $("#a").attr('href','<%=basePath %>deviceflow/exportdeviceflowstatistics?'+par);
   }
   $("#leibie").validate_popover({
       rules : {
           'beginTime' : {
               required : true
           },
           'endTime' : {
               required : true
           },
       },
       messages : {
           'beginTime' : {
               required : "请选择开始时间"
           },
           'endTime' : {
               required : "请选择结束时间"
           },
       },
       submitHandler : function(form) {
           gridObj.options.otherParames = $('#leibie').serializeArray();
           gridObj.refreshPage();
       }
   });

</SCRIPT>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>