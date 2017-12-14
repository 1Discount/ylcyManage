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
<title>提工单管理-流量运营中心</title>
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
                <DIV class="col-md-12">
                    <DIV class="panel">
                       <DIV class="panel-body">
                           <FORM class="form-inline" id="leibie" method="get" action="#">
                               <div class="form-group">
                                    <LABEL class="inline-label">日期：</LABEL>
                                    <input type="text" id="creatorDate" name="creatorDate" data-popover-offset="0,8" class="form_datetime form-control"/>
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
                     <DIV class="panel-heading">SIM卡使用情况</DIV>
                     <DIV class="panel-body">
                         <DIV class="table-responsive">
        
                             <TABLE id="searchTable">
                                 <TR>
                                     <TH w_index="country" width="17%"><b>国家</b></TH>
                                     <TH w_render="useFlow" width="17%"><b>累计使用流量</b></TH>
                                     <TH w_index="useCount" width="17%"><b>使用SIM卡总数</b></TH>
                                     <TH w_index="SIMCount" width="17%"><b>可用SIM卡总数</b></TH>
                                     <TH w_render="useRate" width="17%"><b>SIM卡使用率</b></TH>
                                     <TH w_index="creatorDate" width="15%"><b>日期</b></TH>
                                 </TR>
                             </TABLE>
                         </DIV>
                         <DIV></DIV>
                     </DIV>
                 </DIV>
                </DIV>
            </SECTION>
        </SECTION>
    </section>
    
    
    <script src="<%=basePath%>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <script type="text/javascript">
        
          
    var gridObj;
    $(function(){
        
        $("#creatorDate").datetimepicker({
            format: 'YYYY-MM-DD',
            pickDate: true,     //en/disables the date picker
            pickTime: true,     //en/disables the time picker
            showToday: true,    //shows the today indicator
            language:'zh-CN',   //sets language locale
            defaultDate: moment().add(-1, 'days') // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
        });
        
    });
    
    $(function(){
       
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>sim/siminfo/getSIMRecordCountByDate',
           otherParames:$('#leibie').serializeArray(),
           pageSizeSelect: true,
           pageSize: 20,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
       
   });

    function useFlow(record, rowIndex, colIndex, options) {
        return '<a title="' + record.useFlow + '" href="<%=basePath%>sim/siminfo/simrecorddetails?country='+record.country+'&creatorDate='+record.creatorDate+'">' + prettyByteUnitSize(record.useFlow,2,1,true) + '</a>';
    }
    
    function useRate(record, rowIndex, colIndex, options){
    	return (Math.round(record.useCount/record.SIMCount*10000)/100) + '%';
    }
    

    </script>

    <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
        <jsp:param name="matchType" value="exactly" />
    </jsp:include>
</body>
</html>