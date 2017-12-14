<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>VPN预警-VPN管理-流量运营中心</title>
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
            <label class="inline-label">IP：</label>
            <input class="form-control" name="IP" type="text" placeholder="IP" >
        </div>
        <div class="form-group">
            <label class="inline-label">状态：</label>
            <select class="form-control" id="warningStatus" name="warningStatus">
                <option value="">请选择</option>
                <option value="正常">正常</option>
                <option value="异常">异常</option>
            </select>
        </div>
        <div class="form-group">
        <button class="btn btn-primary" 
        type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button></div></form></div></div>
        <div class="panel">
        <div class="panel-heading">
        <div class="btn-toolbar btn-header-right">
        <a class="btn btn-success btn-xs" href="#" onclick="op_edit(null)"><span class="glyphicon glyphicon-plus"></span> 添加VPN预警</a></div>
        <h3 class="panel-title">VPN预警</h3></div>
        <div class="panel-body">
        <div class="table-responsive"><table id="searchTable">
                <tr>
                    
                    <th w_index="IP" width="18%" >IP</th>
                    <th w_render="warningStatus" width="18%">状态</th>
                    <th w_render="ifMsgAlter" width="18%">是否短信提醒</th>
                    <th w_index=remark width="18%">备注</th>
                    <th w_render="creatorDate" width="18%">创建时间</th>
                    <th w_render="operate" width="10%">操作</th>
                </tr>
        </table></div>
        </div></div>
        </div>
          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
          var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>vpn/vpninfo/vpnwarningpage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false
             });
         });
          
         function warningStatus(record, rowIndex, colIndex, options){
        	 if(record.warningStatus == "正常"){
        		 return '<a class="btn btn-success btn-xs" onclick="return;"><span>正常</span></a>';
        	 }
        	 if(record.warningStatus == "异常"){
                 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>异常</span></a>';
             }
         }
         
         function operate(record, rowIndex, colIndex, options) {
            return '<div class="btn-toolbar">'
                + '<a class="btn btn-primary btn-xs" href="#" onclick="op_ping(' + rowIndex + ')"><span class="glyphicon glyphicon-edit">手动ping</span></a>'
                + '<a class="btn btn-primary btn-xs" href="#" onclick="op_edit(' + rowIndex + ')"><span class="glyphicon glyphicon-edit">编辑</span></a>'
                + '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.vpnWarningID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
                + '</div>';
         }
         function op_delete(id) {
            if(confirm("确认删除VPN预警?")) {
                $.ajax({
                    type:"POST",
                    url:"<%=basePath%>vpn/vpninfo/deletewarning?id=" + id,
                    success:function(data){

                        easy2go.toast('info', data);
                        gridObj.refreshPage();
                    }

                });
            }
         }
         function ifMsgAlter(record, rowIndex, colIndex, options){
        	 
        	 if(record.ifMsgAlter==true){
        		 return "是";
        	 }else if(record.ifMsgAlter==false){
        		 return "否";
        	 }else{
        		 return "";
        	 }
         }
         
         function creatorDate(record, rowIndex, colIndex, options){
      
              if(record.creatorDate!=null && record.creatorDate!=""){
                  
                  var dateStr=record.creatorDate;
                  
                  return dateStr.slice(0,dateStr.indexOf("."));
                  
              }else{
                  
                  return "";
              }
         }
         
         
         function op_ping(index){
            var record = gridObj.getRecord(index);
            $.ajax({
                type:"POST",
                 url:"<%=basePath %>vpn/vpninfo/ping",
                 data : {"IP":record.IP,"warningStatus":record.warningStatus},
                 success : function(data) {
                     result = jQuery.parseJSON(data);
                     if (result.code == 0) { 
                         easy2go.toast('info',result.msg);
                         gridObj.refreshPage();
                     } else {
                         easy2go.toast('error',result.msg);
                         gridObj.refreshPage();
                     }
                 }
            });
         }
         
         function op_edit(index) {
             var vpnWarningID = "";
             var IP = "";
             var remark = "";
             var warningStatus = '<option value="">请选择</option><option value="正常">正常</option><option value="异常">异常</option>';
             var ifMsgAlter = '<input type="radio" name="ifMsgAlter" value="true"/>是&nbsp;&nbsp;<input type="radio" name="ifMsgAlter" value="false"/>否';
             if(index!=null){
                 var record = gridObj.getRecord(index);
                 vpnWarningID = record.vpnWarningID;
                 IP = record.IP;
                 remark = record.remark;
                 warningStatus = '<option value="">请选择</option>';
                 warningStatus = warningStatus + (record.warningStatus=="正常"?'<option value="正常" selected="selected">正常</option><option value="异常">异常</option>':'<option value="正常">正常</option><option value="异常" selected="selected">异常</option>');
                 ifMsgAlter = record.ifMsgAlter==true?'<input type="radio" checked="checked" name="ifMsgAlter" value="true"/>是&nbsp;&nbsp;<input type="radio" name="ifMsgAlter" value="false"/>否':'<input type="radio" name="ifMsgAlter" value="true"/>是&nbsp;&nbsp;<input type="radio" name="ifMsgAlter" checked="checked" value="false"/>否';
             }
             bootbox.dialog({
                 title : "编辑VPN预警",
                 message :'<DIV class="">'
                         +'<DIV class="">'
                         +'<DIV class="panel-body">'
                         +'<FORM id="warning_form" class="form-horizontal" role="form"'
                         +'method="post" action="" autocomplete="off">'
                         +'<input type="hidden" name="vpnWarningID" value="' + vpnWarningID + '">'
                         +'<table width="90%" cellspacing="2px">'
                         +'<tr style="height: 40px;">'
                         +'<td style="text-align:right;" width="100"><b>IP：</b></td>'
                         +'<td><input type="text" id="IP" name="IP" value="' + IP + '" data-popover-offset="0,8" class="form-control"></td>'
                         +'</tr>'
                         +'<tr style="height: 40px;">'
                         +'<td style="text-align:right;" width="100"><b>状态：</b></td>'
                         +'<td><select class="form-control" id="warningStatus" name="warningStatus">'
                         +warningStatus
                         +'</select></td></tr>'
                         +'<tr style="height: 40px;">'
                         +'<td style="text-align:right;" width="100"><b>是否短信预警：</b></td>'
                         +'<td>'
                         +ifMsgAlter
                         +'</td></tr>'
                         +'<tr style="height: 40px;">'
                         +'<td style="text-align:right;" width="100"><b>备注：</b></td>'
                         +'<td><input type="text" id="remark" name="remark" value="' + remark + '" data-popover-offset="0,8" class="form-control"></td>'
                         +'</tr></table></FORM></DIV></DIV></DIV>',
                 buttons : {
                     cancel : {
                         label : "取消",
                         className : "btn-default",
                         callback : function() {
                             
                         }
                     },
                     success : {
                         label : "提交",
                         className : "btn-success byte-unit-picker-button-ok",
                         callback : function() {
                            $.ajax({
                                type:"POST",
                                url:"<%=basePath %>vpn/vpninfo/savewarning",
                                 data : $('#warning_form').serialize(),
                                 success : function(data) {
                                     result = jQuery.parseJSON(data);
                                     if (result.code == 0) { // 成功保存
                                         easy2go.toast('info',result.msg);
                                         gridObj.refreshPage();
                                     } else {
                                         easy2go.toast('error',result.msg);
                                     }
             
                                 }
                             });
                         }
                     }
                 }
             });
         }
         
         window.setInterval(function(){
             gridObj.refreshPage();
         },60000);
    </script>


<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>