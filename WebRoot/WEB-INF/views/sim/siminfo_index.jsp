<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title><c:if test="${IsIndexView}">全部SIM卡-本地SIM卡管理-流量运营中心</c:if> <c:if test="${IsTrashView}">本地SIM卡管理-全部已删除SIM卡-流量运营中心</c:if></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<section id="main-content">
			<section class="wrapper">
				<div class="col-md-12">
					<c:if test="${IsIndexView}">
						<div class="row">
							<div class="col-md-3">
								<div class="mini-stat clearfix">
									<a href="<%=basePath%>sim/siminfo/statistics">
										<span class="mini-stat-icon pink">
											<i class="fa fa-bar-chart-o"></i>
										</span>
										<div class="mini-stat-info">
											<span style="font-size: 20px;">本地SIM卡状态统计</span>
										</div>
									</a>
								</div>
							</div>

							<div class="col-md-3">
								<div class="mini-stat clearfix">
									<a href="<%=basePath%>sim/siminfo/index/alert">
										<span class="mini-stat-icon orange">
											<i class="fa fa-th-list"></i>
										</span>
										<div class="mini-stat-info">
											<span style="font-size: 20px;">本地SIM卡充值预警列表</span>
										</div>
									</a>
								</div>
							</div>
						</div>
					</c:if>
					<div class="panel">
						<div class="panel-body">
							<form class="form-inline" id="searchForm" role="form" method="get" action="#">
								<input type="hidden" id="pagenum" value="1" />
								<input type="hidden" id="pagesize" value="50" />
								<div class="form-group">
									<label class="inline-label">IMSI：</label>
									<input type="text" name="IMSI" style="width: 173px;" placeholder="IMSI" class="form-control" id="IMSI">
								</div>
								
								<div class="form-group">
									<label class="inline-label">机身码：</label>
									<input type="text" name="lastDeviceSN" style="width: 173px;" placeholder="设备机身码" class="form-control">
								</div>
								
								<div class="form-group">
									<label class="inline-label">代号：</label>
									<input type="text" name="simAlias" placeholder="SIM卡代号" style="width: 173px;" class="form-control">
								</div>
								
								<div class="form-group">
									<label class="inline-label">渠道商：</label>
									<select name="serverCode" style="width: 135px;" class="form-control">
										<option value="">全部</option>
										<option value="0" selected="selected">宜联畅游</option>
										<c:forEach items="${distributors}" var="distributor" varStatus="status">
											<c:choose>
												<c:when test="${empty  distributor.serverCode}"></c:when>
												<c:otherwise>
													<option value="${distributor.serverCode}">${distributor.company}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								
								<div class="form-group">
									<label class="inline-label">服务器：</label>
									<select name="serverIP" style="width: 135px;" class="form-control">
										<option value="">全部服务器</option>
										<c:forEach items="${SIMServers}" var="server" varStatus="status">
											<option value="${server.IP}">${server.IP}</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="form-group">
									<label class="inline-label">国&nbsp;家：</label>
									<select name="MCC" style="width: 130px;" id="MCC" class="form-control">
										<option value="">全部国家</option>
										<c:forEach items="${Countries}" var="country" varStatus="status">
											<option value="${country.countryCode}">${country.countryName}</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="form-group">
									<label class="inline-label">状&nbsp;态：</label>
									<select name="cardStatus" style="width: 130px;" class="form-control" id="cardStatus">
										<option value="">全部状态</option>
										<c:forEach items="${CardStatusDict}" var="item" varStatus="status">
											<option value="${item.label}">${item.label}</option>
										</c:forEach>
									</select>
								</div>

								<div class="form-group">
									<label class="inline-label">过期时间：</label>
									<INPUT id="SIMEndDate" class="form-control form_datetime" name="SIMEndDate" type="text" data-date-format="YYYY-MM-DD" placeholder="过期时间">
								</div>

                                <div class="form-group">
                                    <label class="inline-label">创建时间：</label>
                                    <INPUT id="begindate" class="form-control form_datetime" name="begindate" type="text" data-date-format="YYYY-MM-DD" placeholder="开始时间">-
                                    <INPUT id="enddate" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD" placeholder="结束时间">
                                </div>

								<div class="form-group">
									<button class="btn btn-primary" type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button>
									<button type="reset" class="btn btn-default">重置</button>
								</div>
							</form>
						</div>
					</div>

				</div>

				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">
							<c:if test="${IsIndexView}">
								<div class="btn-toolbar btn-header-right">
									<a class="btn btn-success btn-xs" href="<%=basePath%>sim/siminfo/new">
										<span class="glyphicon glyphicon-plus"></span>
										添加SIM卡
									</a>
								</div>
								<div style="margin-right: 30px;" class="btn-toolbar btn-header-right">
									<a class="btn btn-success btn-xs" onclick="updateStatus()" href="javascript:;">
										<span class="glyphicon glyphicon-plus"></span>
										批量操作
									</a>
								</div>
							</c:if>
							<h3 class="panel-title">
								<c:if test="${IsIndexView}">全部本地SIM卡    <span style="color: red;">（不可用的卡请标注为调试不可用，停用表示备用的卡） </span>
								</c:if>
								<c:if test="${IsTrashView}">全部已删除本地SIM卡</c:if>
							</h3>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable">
									<tr>
										<th w_check="true" width="3%;"></th>
										<th w_index="SIMinfoID" w_hidden="true">SIM卡ID</th>
										<th w_render="render_IMSI">IMSI号</th>
										<th w_index="simAlias">代号</th>
										<th w_index="slotNumber">卡位</th>
										<th w_index="countryName">可用国家</th>
										<th w_render="ifr">漫游</th>
										<th w_render="render_cardStatus" width="5%;">状态</th>
										<th w_index="IPAndPort" width="8%;">服务器</th>
										<th w_index="lastDeviceSN" w_sort="lastDeviceSN,unorder">最近设备机身码</th>
										<th w_render="render_planData" width="5%;">包含流量</th>
										<th w_render="render_planRemainData" width="5%;">剩余流量</th>
										<th w_render="render_historyUsedFlow" width="5%;">累计使用流量</th>
										<th w_render="render_speedType">高/低速</th>
										<th w_render="ifBoundSN">是否绑定</th>
										<th w_index="SIMActivateDate" width="5%;">卡开启日期</th>
										<th w_index="SIMEndDate" width="5%;">到期日期</th>
                                        <th w_render="creatorDate" width="5%;">创建时间</th>
										<th w_render="operate">操作</th>
									</tr>
								</table>
							</div>
							<c:if test="${IsIndexView}">
								<DIV class="panel-body">
									<a onclick="excelExport()" id="exprot" href="#">
										<img src="<%=basePath%>static/images/excel.jpg" width="30" height="30" />导出EXCEL请点我<span style="color: red;"> </span>
									</a>
								</DIV>
							</c:if>
						</div>
					</div>
					<div id="special-note">
						备注：
						<br>
						<br>
						<p>1. “包含流量”和“剩余流量”的精确数据表字段数值，以KB为单位，可以在表格相应位置上鼠标悬停显示。</p>
						<p>
							2. 点击“标记”可以添加/修改标记，
							<a class="btn btn-info btn-xs" onclick="javascript:void(0);">
								<span>标记</span>
							</a>
							表示带有标记， 鼠标悬停时会显示。
							<a class="btn btn-primary btn-xs" onclick="javascript:void(0);">
								<span>标记</span>
							</a>
							表示目前未有任何标记。
						</p>
					</div>
				</div>
			</section>
		</section>
	</section>
	
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/tooltips.js"></script>
	
	<script type="text/javascript">
	$(function(){
		var countryName = "${countryName}";
		if(countryName!=""){
			var count=$("#MCC option").length;
		  	for(var i=0;i<count;i++)  
		    {   
			  if($("#MCC ").get(0).options[i].text == countryName)  
		      {  
	              $("#MCC ").get(0).options[i].selected = true;  
	              break;  
		      }  
		    }
			$("#MCC option[text='"+countryName+"']").attr("selected", true);
		}
		
		var cardZT = "${cardZT}";
		if(cardZT!=""){
			var count=$("#cardStatus option").length;
			  for(var i=0;i<count;i++)  
			     {   
				  if($("#cardStatus ").get(0).options[i].text == cardZT)  
			        {  
			            $("#cardStatus ").get(0).options[i].selected = true;  
			            break;  
			        }  
			    }
	
			$("#cardStatus option[text='"+countryName+"']").attr("selected", true);
		}
		
		var MCC ="${MCC}";
		if(MCC!=""){
			var count=$("#MCC option").length;
			
			  for(var i=0;i<count;i++)  
			     {  
				  if($("#MCC ").get(0).options[i].value == MCC)  
			        {  
			            $("#MCC ").get(0).options[i].selected = true;  
			            break;  
			        }  
			    }
			$("#MCC option[text='"+MCC+"']").attr("selected", true);
		}
	});
    bootbox.setDefaults("locale","zh_CN");;

    accounting.settings = {
        currency: {
            symbol : "￥",   
            format: "%s%v",
            decimal : ".",  
            thousand: ",", 
            precision : 2   
        },
        number: {
            precision : 0,
            thousand: ",",
            decimal : "."
        }
    };
    
	 function render_ICCID(record, rowIndex, colIndex, options) {
	    return '<a href="<%=basePath%>sim/siminfo/view/' + record.SIMinfoID + '"><span>' + record.ICCID + '</span></a>';
	 }
	
	 function render_IMSI(record, rowIndex, colIndex, options) {
		var imsiString = '<a href="<%=basePath%>sim/siminfo/view/' + record.SIMinfoID + '"><span>' + record.IMSI + '</span></a>';
		if(record.notes != null && record.notes.length > 0) {
			imsiString += '<a href="javascript:void(0);" class="ahover-tips notes-tips-' + rowIndex + '" tooltips="' + escape(record.notes) + '">';
		} else {
			imsiString += '<a href="javascript:void(0);" class="hidden ahover-tips notes-tips-' + rowIndex + '" tooltips="' + escape(record.notes) + '">';
		}
        return imsiString;
     }
	
	 function render_cardStatus(record, rowIndex, colIndex, options) {
	    if (record.cardStatus == "可用") {
	        return '<a class="btn btn-success btn-xs" onclick="return;"><span>&nbsp;可用&nbsp;&nbsp;</span></a>';
	    } else if (record.cardStatus == "使用中") {
	        return '<a class="btn btn-info btn-xs" onclick="return;"><span>使用中</span></a>';
	    } else if(record.cardStatus == "不可用"){
	        return '<a class="btn btn-danger btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
	    }else if(record.cardStatus == "调试不可用"){
	        return '<a class="btn btn-danger btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
	    } else if(record.cardStatus == "停用"){
	    	return '<a class="btn btn-warning btn-xs" onclick="return;"><span>' + record.cardStatus + '(备用)</span></a>';
	    }else if(record.cardStatus == "下架"){
	    	return '<a class="btn btn-default btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
	    }else if(record.cardStatus == "库存"){
	    	return '<a class="btn btn-stock btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
	    }
	 }
	 
	 function render_speedLimit(record, rowIndex, colIndex, options) {
	    if (record.speedLimit == 0) {
	        return '<a class="btn btn-success btn-xs" onclick="return;"><span>不限速</span></a>';
	    } else {
	        return '<a class="btn btn-danger btn-xs" onclick="return;"><span>限速' + record.speedLimit + 'KB</span></a>';
	    }
	 }
	 
    function render_speedType(record, rowIndex, colIndex, options) {
    	if(record.speedType==0) {
       		return '<a class="btn btn-info btn-xs">高速</a>';
    	} else {
         	return '<a class="btn btn-danger btn-xs">低速</a>';
    	}
    }
    
    
    function render_planData(record, rowIndex, colIndex, options) {
        return '<a title="' + record.planData + '">' + prettyByteUnitSize(record.planData,2,1,true) + '</a>';
    }
    
    function render_planRemainData(record, rowIndex, colIndex, options) {
        if(record.planRemainData < 204800) {
	        return '<a class="btn btn-danger btn-xs" title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
	    } else {
	        return '<a title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
	    }
     }
    
    
    function render_historyUsedFlow(record, rowIndex, colIndex, options) {
    	return '<a title="' + record.historyUsedFlow + '">' + prettyByteUnitSize(record.historyUsedFlow,2,1,true) + '</a>';
    }
    
    
   function ifr(record, rowIndex, colIndex, options) {
    	if(record.ifRoam==1){
    		return '是';
    	}else{
    		return '否';
    	}
    }
	 
   function ifBoundSN(record, rowIndex, colIndex, options){
		if(record.ifBoundSN==1){
    		return '是';
    	}else{
    		return '否';
    	}
   }
	
   var resultCode = 0; 
   function showNotesDialog(simId,rowIndex){
	   var notesRefId = '.notes-'+ rowIndex;
	   var notesTipsRefId = '.notes-tips-' + rowIndex; 
	   var currentVal = unescape($(notesRefId).val());
       bootbox.dialog({
            title: "请输入/修改标记内容",
            message: '<div class="row">  ' +
                '<div class="col-md-12"> ' +
                '<form class="form-horizontal" id="sim-notes-form" mothod="post"> ' +
                '<input name="SIMinfoID" type="hidden" value="' + simId + '"> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-3 control-label" for="name">标记:</label> ' +
                '<div class="col-md-8">' +
                '<textarea id="notes" rows="3" name="notes" maxlength="254" data-popover-offset="0,8" class="form-control">' + 
                    currentVal + '</textarea>' +
                '</div> ' +
                '</form></div></div>',
            buttons: {
                cancel: {
                    label: "取消",
                    className: "btn-default",
                    callback: function () {
                    }
                },
                success: {
                    label: "设定",
                    className: "btn-success edit-button-ok",
                    callback: function () {
                    	if($('#notes').val() != currentVal){
                    		
	                        $.ajax({
	                            type:"POST",
	                            url:"<%=basePath%>sim/siminfo/updatenotes",
	                            dataType:"html",
	                            data:$('#sim-notes-form').serialize(),
	                            success:function(data){
	                            	result = jQuery.parseJSON(data);
	                            	resultCode = result.code;
	                            	if (result.code == 0) {                            		
	                                    easy2go.toast('info', result.msg);
	                                    $(notesRefId).val(escape($('#notes').val()));
	                                    $(notesTipsRefId).attr('tooltips', escape($('#notes').val()));
	                                    $(notesTipsRefId).each(function(){$(this).hoverTipsEx({isEscape:true,isSmallWidth:true});});
                                  	    if($('#notes').val().length!=0){ 
	                                    	$(notesTipsRefId).removeClass('hidden');
	                                        $('.edit-notes-' + rowIndex).removeClass('btn-primary').addClass('btn-info');
	                                    }
	                                    $('#notes').val('');

	                                } else {
	                                    easy2go.toast('error', result.msg);	                                    
	                                }
	                            }
	                        });                        
                    	} else {
                    		easy2go.toast('info', '内容无更改');
                    	}
                    }
                }
            }
       });
   }
</script>
<c:if test="${IsIndexView}">
	<script type="text/javascript">
    	  var gridObj;
          $(function(){
              $("#SIMEndDate").datetimepicker({
           		pickDate: true,                 
           		pickTime: true,               
           		showToday: true,                
           		language:'zh-CN',                                
           	});
              $("#begindate").datetimepicker({
                  pickDate: true,                 
                  pickTime: true,               
                  showToday: true,                
                  language:'zh-CN',                                
              });
              $("#enddate").datetimepicker({
                  pickDate: true,                 
                  pickTime: true,               
                  showToday: true,                
                  language:'zh-CN',                                
              });

        	  var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>sim/siminfo/datapage',
                 pageSizeSelect: true,
                 otherParames:$('#searchForm').serializeArray(),
                 pageSize: pagesize,
                 pageSizeForGrid:[15,30,50,100],
                 multiSort:true,
                 rowHoverColor:true,
                 autoLoad:false,
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 $("#pagenum").val(options.curPage);
                		 var a = $("#searchTable_pt_pageSize").val();
                		 $("#pagesize").val(a);
                		 $(".ahover-tips").each(function(){$(this).hoverTipsEx({isEscape:true,isSmallWidth:true});});
                	 }
                 }
             });
	           if($("#pagenum").val()!=""){
	        	   gridObj.page($("#pagenum").val());
	           }else{
	        	   gridObj.page(1);
	           }
	           
         });
     
          
        function excelExport(){
        	  var l=gridObj.getCheckedRowsRecords().length;
        	  var imsistr='"';
          	  for(var i=0;i<l;i++){
          	    if(i==0){
          	    	imsistr+=gridObj.getCheckedRowsRecords()[i].IMSI;
          	    }else{
          	    	imsistr+=","+gridObj.getCheckedRowsRecords()[i].IMSI;
          	    }
          	  }
             $("#exprot").attr('href','<%=basePath%>sim/siminfo/excelimportSIM?IMSI='+imsistr+'"');
        }

        //创建时间
        function creatorDate(record, rowIndex, colIndex, options){

            if(record.creatorDate!=null && record.creatorDate!=""){
                
                var dateStr=record.creatorDate;
                
                return dateStr.substring(0,10);
                
            }else{
                
                return "";
            }
        }
          

         function operate(record, rowIndex, colIndex, options) {
			var result = '<div class="btn-toolbar">';
			if(record.notes != null && record.notes.length > 0) {
			result += '<a class="btn btn-info btn-xs edit-notes-' + rowIndex + '" onclick="showNotesDialog(\'' + record.SIMinfoID + '\',' + rowIndex + ');"><span>标记</span></a>'
			  + '<input type="hidden" class="notes-' + rowIndex + '" value="' + escape(record.notes) + '" />';
			} else {
            result += '<a class="btn btn-primary btn-xs edit-notes-' + rowIndex + '" onclick="showNotesDialog(\'' + record.SIMinfoID + '\',' + rowIndex + ');"><span>标记</span></a>'
              + '<input type="hidden" class="notes-' + rowIndex + '" value="' + escape(record.notes) + '" />';
			}
			if(record.cardStatus == "使用中") {
				result += '<a class="btn btn-primary btn-xs" onclick="op_confirm_modify(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-edit">编辑</span></a>';
			} else {
				result += '<a class="btn btn-primary btn-xs" href="<%=basePath%>sim/siminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>' 
                + '<a class="btn btn-danger btn-xs" onclick="op_delete(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>';
			}
			result += '<a class="btn btn-primary btn-xs" href="<%=basePath%>sim/simrechargebill/new?SIMinfoID=' + record.SIMinfoID + '&SIMCategory=' + record.SIMCategory + '"><span class="glyphicon glyphicon-plus">充值</span></a></div>';
			
          return result;
         }   
         
         
         function op_delete(id) {
            if(confirm("确认删除SIM卡?")) {
                $.ajax({
                    type:"POST",
                    url:"<%=basePath%>sim/siminfo/delete/" + id,
                    success:function(data){
                        easy2go.toast('info', data);
                        gridObj.refreshPage();
                    }

                });
            }
         }
         
         
         function op_confirm_modify(id) {
        	 if(confirm("该SIM卡现在使用中，是否确认编辑?")) {
        		 window.location.href = '<%=basePath%>sim/siminfo/edit/' + id;
        	 }
         }
         
       //批量操作
         function updateStatus(){
	      	  var l=gridObj.getCheckedRowsRecords().length;
	      	  if(l==0){
	      		easy2go.toast('warn',"请选择要操作的记录!");
	      		return;
	      	  }
      	  	  var imsistr="";
	      	  for(var i=0;i<l;i++){
	      	     if(i==0){
	      	    	 imsistr+=gridObj.getCheckedRowsRecords()[i].IMSI;
	      	     }else{
	      	    	 imsistr+=","+gridObj.getCheckedRowsRecords()[i].IMSI;
	      	     }
	      	  }
	      	   bootbox.dialog({
		             title: "批量编辑SIM卡",
		             message: '<div class="row">  ' +
		                 '<div class="col-md-12"> ' +
		                 '<form class="form-horizontal" id="sim-update-form" mothod="post"> ' +
		                 '<div class="form-group"> ' +
		                 '<label class="col-md-3 control-label" for="name">SIM状态:</label> ' +
		                 '<div class="col-md-8">' +
		                 '<label for="" class="radio-inline"><input type="radio" name="cardStatus" id="" value="0" >使用中</label>'+
		                 '<label for="" class="radio-inline"><input type="radio" name="cardStatus" id="" value="1" >可用</label>'+
		                 '<label for="" class="radio-inline"><input type="radio" name="cardStatus" id="" value="2" >调试不可用</label>'+
		                 '<label for="" class="radio-inline"><input type="radio" name="cardStatus" id="" value="3" >停用</label>'+
		                 '<label for="" class="radio-inline"><input type="radio" name="cardStatus" id="" value="4" >下架</label>'+
		                 '</div> ' +
		                 '</form></div></div>',
		             buttons: {
		                 cancel: {
		                     label: "取消",
		                     className: "btn-default",
		                     callback: function () {}},
		                 success: {
		                     label: "确认修改",
		                     className: "btn-success edit-button-ok",
		                     callback: function () {
		                     	var se=$("#sim-update-form").serialize();
		                     	if(se!=''){
		                     		var cardStatus=urltovalue(0,se);
		                     		$.ajax({
		                     			type:"POST",
		                     			url:"<%=basePath%>sim/siminfo/updateCardStatus",
		                     			data:{cardStatus:cardStatus,IMSI:imsistr},
		                     			dataType:"html",
		                     			success:function(data){
		                     				if(data=='1'){
		                     					gridObj.refreshPage();
		                     				}else if(data=='0'){
		                     					alert("修改失败!");
		                     				}else if(data=="-1"){
		                     					alert("参数为空!");
		                     				}
		                     			}
		                     		});
		                     	}else{
		                     		alert("请选择卡状态!");
		                     		return false;
		                     	}
		                     }
		                 }
		             }
	           });
         }
         function urltovalue(index,urlstring){
        	 if(urlstring.indexOf("&")>-1){
        		 var c= urlstring.split("=");
             	 return c[1];
        	 }else{
        		 var d=urlstring.split("&");
             	 var c= d[index].split("=");
             	 return c[1];
        	 }
         }
	</script>
	</c:if>
	
	<c:if test="${IsTrashView}">
		<script type="text/javascript">
    	  var gridObj;
          $(function(){
        	 var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>sim/siminfo/trashdatapage',
                 autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: pagesize,
                 pageSizeForGrid:[15,30,50,100],
                 multiSort:true,
                 rowHoverColor:true,
                 otherParames:$('#searchForm').serializeArray(),
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 $("#pagenum").val(options.curPage);
                		 var a = $("#searchTable_pt_pageSize").val();
                		 $("#pagesize").val(a);
                	 }
                     $(".ahover-tips").each(function(){$(this).hoverTipsEx({isEscape:true,isSmallWidth:true});});
                 }
             });
             if($("#pagenum").val()!=""){
          	   gridObj.page($("#pagenum").val());
             }else{
          	   gridObj.page(1);
             }
         });

          //创建时间
          function creatorDate(record, rowIndex, colIndex, options){

              if(record.creatorDate!=null && record.creatorDate!=""){
                  
                  var dateStr=record.creatorDate;
                  
                  return dateStr.substring(0,10);
                  
              }else{
                  
                  return "";
              }
          }
         
         function operate(record, rowIndex, colIndex, options) {
			return '<div class="btn-toolbar"><a class="btn btn-primary btn-xs" href="<%=basePath%>sim/siminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
				 + '<a class="btn btn-danger btn-xs" href="#" onclick="op_restore(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">恢复</span></a></div>';
         } 
         
         function op_restore(id) {
            if(confirm("确认恢复这张SIM卡?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath%>sim/siminfo/restore/" + id,
					success : function(data) {
						easy2go.toast('info', data);
						gridObj.refreshPage();
					}
				});
			}
		 }
		</script>
	</c:if>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>