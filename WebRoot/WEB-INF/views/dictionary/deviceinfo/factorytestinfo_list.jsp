<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>工厂测试记录-设备管理-EASY2GO ADMIN</title>
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
        <div class="panel-body">
        <form class="form-inline" id="searchForm" role="form" method="get" action="#">
        <div class="form-group">
          <label class="inline-label">机身码:</label>
          <input class="form-control" name="roam_SN" id="SN" type="text" placeholder="机身码" >
        </div>
        <div class="form-group">
          <label class="inline-label">结果:</label>
          <select class="form-control" id="result" name="result">
            <option value="">请选择</option>
            <option value="开始测试">开始测试</option>
            <option value="测试中">测试中</option>
            <option value="成功">成功</option>
            <option value="失败">失败</option>
          </select>
        </div><br/>
        <div class="form-group">
             <LABEL class="inline-label">创建时间：</LABEL>
             <input type="text" id="begainTime" name="begainTime" data-popover-offset="0,8" class="form_datetime form-control"/>-
             <input type="text" id="endTime" name="endTime" data-popover-offset="0,8" class="form_datetime form-control"/>
             <input type="hidden" name="distinct" id="distinct" value="no"/>
        </div>
        <div class="form-group">
        <button class="btn btn-primary"
        type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button>
        </div></form></div></div>

        <div class="panel">
        <div class="panel-heading">
        <h3 class="panel-title">工厂测试记录</h3></div>
        <div class="panel-body">
        <div class="table-responsive"><table id="searchTable">
		        <tr>
		        	<th colspan="1">上传</th>
                    <th colspan="1">结果</th>
                    <th colspan="3">其他</th>
			        <th colspan="1"></th>
			        <th colspan="10">漫游</th>
			        <th colspan="12">本地</th>
                    
                    
		        </tr>
                <tr>
                	<th w_index="upload_networkedTestResult" w_sort="upload_networkedTestResult,ase" >联网测试结果</th>
                	<!-- <th w_index="result" w_sort="result,ase">结果</th> -->
                    <th w_render="creatorDate" w_sort="creatorDate,desc">接入时间</th>
                    <th w_index="installCard" w_sort="result,ase">插卡</th>
                    <th w_index="gps" w_sort="result,ase">GPS</th>
                    <th w_index="power" w_sort="result,ase">电量</th>
                	
                    <th w_index="SSID" w_length="11" w_sort="SSID,ase">当前SSID</th>
                    <th w_index="roam_dataConnectionStatus" w_sort="roam_dataConnectionStatus,ase">数据连接状态</th>
                    <th w_index="roam_networkStrength" w_sort="roam_networkStrength,ase" >信号强度</th>
                    
                    <th w_index="roam_appVersionNum" w_sort="roam_appVersionNum,ase" >应用版本号</th>
                    <th w_index="roam_apkVersionNum" w_sort="roam_apkVersionNum,ase" >APK版本号</th>
                    
                    <th w_index="roam_SN" w_sort="roam_SN,ase">机身码</th>
                    <th w_index="roam_ICCID" w_length="11" w_sort="roam_ICCID,ase" >ICCID</th>
                    <th w_index="roam_calibrationMark" w_length="15" w_sort="roam_calibrationMark,ase" >校准标志位</th>
                    <th w_index="roam_networkType" w_sort="roam_networkType,ase" >网络类型</th>
                    <th w_index="roam_IMEI" w_sort="roam_IMEI,ase" >IMEI</th>
                    <th w_index="roam_SIMStatus" w_sort="roam_SIMStatus,ase" >SIM卡状态</th>
                    
                    
                    <th w_index="local_dataConnectionStatus" w_sort="local_dataConnectionStatus,ase">数据连接状态</th>
                    <th w_index="local_networkStrength" w_sort="local_networkStrength,ase" >信号强度</th>
                    <th w_index="local_appVersionNum" w_sort="local_appVersionNum,ase" >应用版本号</th>
                    <th w_index="local_apkVersionNum" w_sort="local_apkVersionNum,ase" >APK版本号</th>
                    <th w_index="local_SN"  w_sort="local_SN,ase">机身码</th>
                    <th w_index="local_ICCID" w_length="11" w_sort="local_ICCID,ase" >ICCID</th>
                    <th w_index="local_calibrationMark" w_length="15" w_sort="local_calibrationMark,ase" >校准标志位</th>
                    <th w_index="local_networkType" w_sort="local_networkType,ase" >网络类型</th>
                    <th w_index="local_IMEI" w_sort="local_IMEI,ase" >IMEI</th>
                    <th w_index="local_wifiPwd" w_sort="local_wifiPwd,ase" >WIFI PWD</th>
                    <th w_index="local_SIMStatus" w_sort="local_SIMStatus,ase" >SIM卡状态</th>
                    <th w_index="local_serialComTest" w_sort="local_serialComTest,ase" >串口通信测试</th>
                    
                    
                    
                </tr>
        </table></div>
        </div></div>
        <DIV class="panel">
            <div class="paomadeng">
                 <span style="font-size: 14px; font-weight: bolder;" onclick="return execl('no')" >
                 <a id="a" href="<%=basePath %>device/exportfactory?">
                 <img src="<%=basePath%>static/images/excel.jpg" style="float: left; margin-top: -5px;"  width="30" height="30" title="" /> 导出全部</a></span>
            </div>
        </DIV>
        <DIV class="panel">
            <div class="paomadeng">
                 <span style="font-size: 14px; font-weight: bolder;" onclick="return execl('yes')" >
                 <a id="b" href="<%=basePath %>device/exportfactory?">
                 <img src="<%=basePath%>static/images/excel.jpg" style="float: left; margin-top: -5px;"  width="30" height="30" title="" /> 导出非重复记录</a></span>
            </div>
        </DIV>
        </div>

        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
          var gridObj;
          $(function(){

              $(".form_datetime").datetimepicker({
                  format: 'YYYY-MM-DD',
                  pickDate: true,     //en/disables the date picker
                  pickTime: true,     //en/disables the time picker
                  showToday: true,    //shows the today indicator
                  language:'zh-CN',   //sets language locale
                  defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
              });


             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>device/factorypage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false
             });
         });


         function creatorDate(record, rowIndex, colIndex, options){
             return record.creatorDate.substring(0,record.creatorDate.length-5);
         }
         function execl(distinct){
        	 $("#distinct").val(distinct);
             var par = $('#searchForm').serialize();
             if(distinct=='yes'){
            	 $("#b").attr('href','<%=basePath %>device/exportfactory?'+par);
             }else{
            	 $("#a").attr('href','<%=basePath %>device/exportfactory?'+par);
             }
         }
         
         $(document).keydown(function(event){
             if(event.keyCode == 13){//绑定回车
                 $("#SN").blur();
                 $("#result").blur();
                 $("#begainTime").blur();
                 $("#endTime").blur();
                 gridObj.options.otherParames =$('#searchForm').serializeArray();
                 gridObj.page(1);
                 event.returnValue = false;
             }
        });
    </script>


<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>