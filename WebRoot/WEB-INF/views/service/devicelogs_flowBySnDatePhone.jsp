<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>设备流量时间段统计 - EASY2GO ADMIN</title>
    <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=320px, user-scalable=no, initial-scale=1, maximum-scale=1"> 
  <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
  
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
    <style>
    body{margin:0px;padding:0px;}
tr{width:100%;border:1px solid gray;}
td{border:1px solid gray;}
      
    </style>
  </head>
  <body>
        <div><h3 class="panel-title">请先选取范围</h3></div>
        <div class="panel-body">
	        <form class="form-inline" id="searchForm" role="form" method="get" action="<%=basePath %>devicelogs/flowbydevicelogsTest">
	        <div class="form-group">
	          <label class="inline-label">设备序列号SN：</label>
              <input type="text" name="SN" value="" style="width: 150px;" placeholder="输入15位完整SN" class="form-control" data-popover-offset="10,-105">
	        </div>
	        <label class="inline-label">选取时间段：</label>从
               <select name="beginTime">
                   <option>2015-01</option><option>2015-02</option><option>2015-03</option><option>2015-04</option><option>2015-05</option><option>2015-06</option><option>2015-07</option>
                   <option>2015-08</option><option>2015-09</option><option>2015-10</option><option>2015-11</option><option>2015-12</option>
               </select>
                 <label class="inline-label">到</label>
                  <select name="endTime">
                          <option>2015-01</option><option>2015-02</option><option>2015-03</option><option>2015-04</option><option>2015-05</option>
                          <option>2015-06</option><option>2015-07</option><option>2015-08</option><option>2015-09</option><option>2015-10</option><option>2015-11</option><option>2015-12</option>
                  </select>
	        <div class="form-group"> <button class="btn btn-primary"  type="submit"  >查看</button> </div>
	        </form>
        </div>
        
        <div class="panel-body">
            <div class="table-responsive">
            （只查询一月，开始和结束时间相同）
				<table id="searchTable"  >
				        <tr style="text-align:center;">
				            <td style="width:25%;"><b>日期</b></td>
				            <td style="width:20%;"><b>SN</b></td>
				            <td style="width:15%;"><b>是否接入过</b></td>
				            <td style="width:15%;"><b>是否接入成功</b></td>
				            <td style="width:20%;"><b>使用流量</b></td>
				        </tr>
				        <c:forEach items="${resultDeviceLogsall}" varStatus="status">        
				        <tr style="text-align:center;">
				           <td>${resultDeviceLogsall[status.index].logsDate}</td>
				           <td>${resultDeviceLogsall[status.index].SN}</td>
				           <td>${resultDeviceLogsall[status.index].ifLogin}</td>
				           <td>${resultDeviceLogsall[status.index].ifLoginSuccess}</td>
				           <td style="text-align:right;">${resultDeviceLogsall[status.index].monthUsedFlow}</td>
				        </tr>
				        </c:forEach>
				</table>
            </div>
        </div>

    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
 
    
    $("#beginTime").datetimepicker({
       pickDate: true,                 //en/disables the date picker
       pickTime: false,                 //en/disables the time picker
       showToday: true,                 //shows the today indicator
       language:'zh-CN',                  //sets language locale
       //defaultDate: moment().add(-1, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
       defaultDate: moment().add(-2, 'months'),
   });
    
    $("#endTime").datetimepicker({
       pickDate: true,                 //en/disables the date picker
       pickTime: false,                 //en/disables the time picker
       showToday: true,                 //shows the today indicator
       language:'zh-CN',                  //sets language locale
       defaultDate: moment().add(0, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
   });
   
    </script>

  <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>