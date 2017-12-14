<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>全部VPN-VPN管理-流量运营中心</title>
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
          <label class="inline-label">IP:</label>
          <input class="form-control" name="vpn" type="text" placeholder="IP" >
        </div>
        <div class="form-group">
          <label class="inline-label">机身码:</label>
          <input class="form-control" name="lastDeviceSN" type="text" placeholder="机身码" >
        </div>
		<div class="form-group">
			<label class="inline-label">代号：</label><input class="form-control" name="number" type="text" placeholder="代号" >
		</div>
		<div class="form-group">
            <label class="inline-label">状态：</label>
            <select class="form-control" id="availableNum" name="availableNum">
                <option value="">请选择</option>
                <option value="0">不可用</option>
                <option value="1">可使用</option>
                <option value="2">使用中</option>
            </select>
        </div>
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button></div></form></div></div>
		<div class="panel">
		<div class="panel-heading">
		<div class="btn-toolbar btn-header-right">
		<a class="btn btn-success btn-xs" href="<%=basePath %>vpn/vpninfo/newvpn"><span class="glyphicon glyphicon-plus"></span> 添加VPN</a></div>
		<h3 class="panel-title">全部VPN</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
                    <th w_index="number" width="3%;" >代号</th>
		            <th w_render="vpn" width="10%;" >VPN</th>
                    <th w_index="vpnc" width="5%;">VPN子账号</th>
		            <th w_render="availableNum" width="5%;">状态</th>
                    <th w_index="vpnPackageType" width="5%;">套餐类型</th>
                    <th w_render="vpnType" width="5%;" >VPN类型</th>
		            <th w_index="lastDeviceSN" width="15%;" >机身码</th>
 		            <th w_render="begainTime" width="8%;">开始时间</th>
 		            <th w_render="endTime" width="8%;">截止时间</th>
                    <th w_render="includeFlow" width="5%;">总账号剩余流量</th>
                    <th w_render="useFlow" width="8%;">已使用流量</th>
                    <th w_index="remark" width="10%;">备注</th>
		            <th w_render="operate" width="10%;">操作</th>
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
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>vpn/vpninfo/vpninfopage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false
             });
         });
         function includeFlow(record, rowIndex, colIndex, options) {
        	 return '<a title="' + record.includeFlow + '">' + prettyByteUnitSize(record.includeFlow,2,1,true) + '</a>';
         }
         function useFlow(record, rowIndex, colIndex, options) {
             return '<a title="' + record.useFlow + '">' + prettyByteUnitSize(record.useFlow,2,1,true) + '</a>';
         }
         function vpn(record, rowIndex, colIndex, options){
        	 return ''+record.vpn+'';
         }
         
         function begainTime(record, rowIndex, colIndex, options){
        	 return record.begainTime.substring(0,10);
         }
         
         function endTime(record, rowIndex, colIndex, options){
             return record.endTime.substring(0,10);
         }
         
         function availableNum(record, rowIndex, colIndex, options){
        	 if(record.availableNum==0){
                 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>不可用</span></a>';
        	 }else if(record.availableNum==1){
                 return '<a class="btn btn-success btn-xs" onclick="return;"><span>可使用</span></a>';
        	 }else if(record.availableNum==2){
        		 return '<a class="btn btn-info btn-xs" onclick="return;"><span>使用中</span></a>';
             }
         }
         function vpnType(record, rowIndex, colIndex, options){
        	 if(record.vpnType==2){
        		 return '小流量';
        	 }else if(record.vpnType==1){
        		 return '大流量';
        	 }
        	 
         }
         function operate(record, rowIndex, colIndex, options) {
<%--			//'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			return '<div class="btn-toolbar">'
				+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>vpn/vpninfo/view?vpnId='+record.vpnId+'"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
				+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>vpn/vpninfo/editvpn?vpnId='+record.vpnId+'"><span class="glyphicon glyphicon-edit">编辑</span></a>'
                + '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.vpnId + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
                + '</div>';
         }
         function op_delete(id) {
             if(confirm("确认删除VPN?")) {
                 $.ajax({
                     type:"POST",
                     url:"<%=basePath%>vpn/vpninfo/delete/" + id,
                     success:function(data){

                         easy2go.toast('info', data);
                         gridObj.refreshPage();
                     }

                 });
             }
          }
	</script>


<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>