<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>全部流量订单-订单管理-EASY2GO ADMIN</title>
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
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper" style="padding-bottom: 0;">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" id="searchForm" role="form"
								method="get" action="#">
								<input type="hidden" value="1" id="pagenum" /> <input
									type="hidden" id="pagesize" value="10" />

								<DIV style="display: none;" class="form-group">
									<LABEL class="inline-label">交易ID：</LABEL><INPUT
										class="form-control" id="order_ID" name="flowDealID"
										type="text" placeholder="交易ID">
								</DIV>
								<div class="form-group">
									<label class="inline-label">国 家：</label><input
										class="form-control" name="userCountry" id="userCountry"
										type="text" />
								</div>
								<div class="form-group">
									<label class="inline-label">订单来源：</label>
									<!-- <input
                                        class="form-control" name="orderSource" type="text"/> -->
									<select id="orderSource" name="orderSource"
										class="form-control">
										<option value="">全部</option>
										<option value="微信">微信</option>
										<option value="官网">官网</option>
										<option value="有赞">有赞</option>
										<option value="APP">APP</option>
										<option value="后台">后台</option>
										<option value="渠道商">渠道商</option>
										<option value="天猫">天猫</option>
										<option value="淘宝A">淘宝A</option>
										<option value="淘宝B">淘宝B</option>
										<option value="其他">其他</option>
										<option value="线下">线下</option>
									</select>
								</div>
								<DIV class="form-group">
									<LABEL class="inline-label">渠道商：</LABEL> <select
										id="distributorName" name="distributorName"
										class="form-control form_datetime">
										<option value="">全部</option>
										<c:forEach items="${distributors}" var="dis">
											<option value="${dis.company }">${dis.company}</option>
										</c:forEach>
									</select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">订单状态：</LABEL> <select id="slect_mg"
										name="orderStatus" class="form-control">
										<option value="">所有</option>
										<c:forEach var="dic" items="${diclist}">
											<option value="${dic.label }">${dic.label }</option>
										</c:forEach>
									</select>
								</DIV>
                                <DIV class="form-group">
                                    <LABEL class="inline-label">是否已退款：</LABEL> 
                                    <select name="refundStatus" class="form-control">
                                        <option value="">全部</option>
                                        <option value="是">是</option>
                                        <option value="否">否</option>
                                    </select>
                                </DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">客户：</LABEL><INPUT
										class="form-control" id="order_customerName"
										name="customerName" type="text" placeholder="姓名">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">SN：</LABEL><INPUT
										class="form-control" id="SN" name="SN" type="text"
										placeholder="设备SN">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL> <INPUT id="order_creatorDatebegin" class="form-control form_datetime"  
									name="begindateForQuery" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL> <INPUT id="order_creatorDateend" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>

								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button"
										onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">所有流量订单</H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									<th w_render="orderidOP" width="10%;">交易ID</th>
									<th w_index="SN" width="10%;">设备SN</th>
									<th w_index="customerName" width="10%;">客户</th>
									<th w_index="userCountry" width="10%;">国家</th>
									<th w_index="orderSource" width="10%;">订单来源</th>
									<th w_index="orderAmount" width="5%;">总金额</th>
									<th w_index="flowDays" width="3%;">天数</th>
									<th w_index="ifActivate" width="3%;">是否激活</th>
									<th w_render="panlUserDate" width="10%;">预约开始时间</th>
									<th w_render="flowExpireDate" width="10%;">到期时间</th>
									<th w_render="lastUpdateDate" width="10%;">上次接入时间</th>
									<th w_index="orderStatus" width="5%;">订单状态</th>
									<th w_index="creatorUserName" width="6%;">创建人</th>
									<th w_render="creatorDate" width="10%;">创建时间</th>
									<th w_render="operate" width="30%;">操作</th>
								</tr>
							</table>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
		<SECTION class="aftersale_content" id="main-content"
			style="display: none;">
			<SECTION class="wrapper" style="margin-top: 0; padding-top: 0;">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">售后服务记录</H3>
						</DIV>
						<DIV class="panel-body" id="aftersale">
							<form class="form-inline" action="#" id="searchForm1"
								method="get" role="form">
								<input id="fdID" name="fdID" type="hidden" />
							</form>
							<table id="searchTable1">
								<tr>
									<th w_index="problemType" width="15%;">问题类型</th>
									<th w_index="problemLevel" width="15%;">问题级别</th>
									<th w_index="handleResult" width="15%;">处理结果</th>
									<th w_index="refundAmount" width="15%;">退款金额</th>
                                    <th w_render="problemTime" width="10%;">问题出现时间</th>
									<th w_index="handleDescription" width="15%">处理描述</th>
									<th w_index="remark" width="15%;">备注</th>
									<th w_render="operate1" width="30%;">操作</th>
								</tr>
							</table>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min1.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	
	
	
	
	<SCRIPT type="text/javascript">
    $(function(){
        var SN = "${SN}";
        if(SN!=""){
            $("#SN").val(SN);
        }
    });

    var gridObj1;
    $(function(){
       gridObj1 = $.fn.bsgrid.init('searchTable1',{
           url:'<%=basePath%>orders/aftersale/getpage',
           pageSizeSelect: true,
           pageSize: 10,
           autoLoad:false,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true,
       });
       var buttonHtml = '<td style="text-align: left;">';
       buttonHtml += '<table><tr>';
       buttonHtml += '<td><input type="button" value="导出售后记录" onclick="exprotexcel()" /></td>';
       buttonHtml += '</tr></table>';
       buttonHtml += '</td>';
       $('#' + gridObj1.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
    });
    bootbox.setDefaults("locale","zh_CN");
    var gridObj;
    var ifClick = false;
    $(function(){
       $("#order_creatorDatebegin").datetimepicker({
          pickDate: true,                 //en/disables the date picker
          pickTime: true,                 //en/disables the time picker
          showToday: true,                 //shows the today indicator
          language:'zh-CN',                  //sets language locale
          defaultDate: moment().add(-3, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
      });
       
      $("#order_creatorDateend").datetimepicker({
          pickDate: true,                 //en/disables the date picker
          pickTime: true,                 //en/disables the time picker
          showToday: true,                 //shows the today indicator
          language:'zh-CN',                  //sets language locale
          defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
      });
      
       
      var pagesize=parseInt($("#pagesize").val());
      
      gridObj = $.fn.bsgrid.init('searchTable',{
          url:'<%=basePath%>orders/flowdealorders/getpage',
          pageSizeSelect: true,
          pageSize:pagesize,
          event:{
              selectRowEvent:function(record, rowIndex, trObj, options){
            	  if(ifClick){
            		  return;
            	  }
                  $("#fdID").val(record.flowDealID);
            	  gridObj1.options.otherParames=$('#searchForm1').serializeArray();
                  $(".aftersale_content").attr("style","display:inherit;");
                  gridObj1.refreshPage();
              },
              unselectRowEvent:function(record, rowIndex, trObj, options){
            	  $(".aftersale_content").attr("style","display:none;");
              }
          },
          autoLoad:false,
          otherParames:$('#searchForm').serializeArray(),
          pageSizeForGrid:[10,30,50,100],
          displayPagingToolbarOnlyMultiPages:true,
          additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
              if(parseSuccess){
                  $("#pagenum").val(options.curPage);
                  var a = $("#searchTable_pt_pageSize").val();
                  $("#pagesize").val(a);
              }
          } 
      });
      
	          
      if($("#pagenum").val()!=""){
          gridObj.page($("#pagenum").val());
      }else{
          gridObj.page(1);
      };
   });
         
   function exprotexcel(){
	   bootbox.dialog({
           title: "请输入导出条件",
           message:'<form id="exproFrom" role="form" action="" method="post"  class="form-horizontal">'+
            ' <div class="form-group">'+
              ' <label for="company" class="col-md-3 control-label">开始时间:</label>'+
               '<div class="col-md-6">'+
                '<INPUT id="beginDate" class="form-control form_datetime" name="beginDate" type="text" data-date-format="YYYY-MM-DD">'+
               '</div>'+
            ' </div>'+
            ' <div class="form-group">'+
            ' <label for="company" class="col-md-3 control-label">结束时间:</label>'+
             '<div class="col-md-6">'+
              ' <INPUT id="endDate" class="form-control form_datetime" name="endDate" type="text" data-date-format="YYYY-MM-DD">'+
             '</div>'+
          ' </div>'+
          ' </form>',
           buttons: {
               cancel: {
                   label: "取消",
                   className: "btn-default",
                   callback: function(){}
               },
               success: {
                   label: "确定",
                   className: "btn-success edit-button-ok",
                   callback: function(){
                	   window.location.href="<%=basePath%>orders/aftersale/exproexcel?"+$('#exproFrom').serialize();
                   }
               }
           }
	   });
	   
	   $("#beginDate").datetimepicker({
	          pickDate: true,                 //en/disables the date picker
	          pickTime: true,                 //en/disables the time picker
	          showToday: true,                 //shows the today indicator
	          language:'zh-CN',                  //sets language locale
	          defaultDate: moment().add(-3, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
	      });
	       
	      $("#endDate").datetimepicker({
	          pickDate: true,                 //en/disables the date picker
	          pickTime: true,                 //en/disables the time picker
	          showToday: true,                 //shows the today indicator
	          language:'zh-CN',                  //sets language locale
	          defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
	      });
               
   }
   
   //自定义列.
   function operate(record, rowIndex, colIndex, options) {
        return '<a class="btn btn-primary btn-xs" href="#" onclick="addAfterSale(\'' + record.flowDealID + '\',\'' + record.SN + '\',\'' + record.customerName + '\')"><SPAN class="glyphicon glyphicon-edit">添加售后记录</span></a>';
   }
   
   //自定义列.
   function operate1(record, rowIndex, colIndex, options) {
        return '<a class="btn btn-danger btn-xs" href="#" onclick="delete_afterSale(\''+record.asiID+'\')"><SPAN class="glyphicon glyphicon-remove">删除</span></a>';
   }
         
   function orderidOP(record, rowIndex, colIndex, options) {
       return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID=' + record.flowDealID + '"><span class="" style="color: green;">详情</span></a>';
   }
   
   //问题出现时间
   function problemTime(record, rowIndex, colIndex, options){
	   
	   if(record.problemTime!=null && record.problemTime!=""){
		   
		   var dateStr=record.problemTime;
	       
	       return dateStr.slice(0,dateStr.indexOf("."));
	       
	   }else{
		   
		   return "";
	   }
   }
   
   //预约开始时间
   function panlUserDate(record, rowIndex, colIndex, options){

       if(record.panlUserDate!=null && record.panlUserDate!=""){
           
           var dateStr=record.panlUserDate;
           
           return dateStr.slice(0,dateStr.indexOf("."));
           
       }else{
           
           return "";
       }
   }
   
   //到期时间
   function flowExpireDate(record, rowIndex, colIndex, options){

       if(record.flowExpireDate!=null && record.flowExpireDate!=""){
           
           var dateStr=record.flowExpireDate;
           
           return dateStr.slice(0,dateStr.indexOf("."));
           
       }else{
           
           return "";
       }
   }
   
   //上次接入时间
   function lastUpdateDate(record, rowIndex, colIndex, options){

       if(record.lastUpdateDate!=null && record.lastUpdateDate!=""){
           
           var dateStr=record.lastUpdateDate;
           
           return dateStr.slice(0,dateStr.indexOf("."));
           
       }else{
           
           return "";
       }
   }
   
   //创建时间
   function creatorDate(record, rowIndex, colIndex, options){

       if(record.creatorDate!=null && record.creatorDate!=""){
           
           var dateStr=record.creatorDate;
           
           return dateStr.slice(0,dateStr.indexOf("."));
           
       }else{
           
           return "";
       }
   }

	//刷新
	function re() {
		$("#order_ID").val('');
		$("#order_customerName").val('');
		gridObj.options.otherParames = $('#searchForm').serializeArray();
		gridObj.refreshPage();
	}
	
	function onChange(){
		if($("#handleResult").val() == "退款"){
            $("#refundAmount").attr("disabled",false);
        }else{
        	$("#refundAmount").attr("disabled","disabled");
        }
	}
	
	function bindDatePicker(){
	      $("#problemTime").datetimepicker({
	          pickDate: true,                 //en/disables the date picker
	          pickTime: true,                 //en/disables the time picker
	          showToday: true,                 //shows the today indicator
	          language:'zh-CN',                  //sets language locale
	          defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
	      });
	}

	function addAfterSale(flowDealID, SN, customerName) {
		var dicAfterSale = '${dicAfterSale}';
		dicAfterSale = jQuery.parseJSON(dicAfterSale);
		var str = "<option value=\"\">请选择</option>";
	    for(var i = 0 ; i < dicAfterSale.length ; i++){
	    	str += "<option value=\""+dicAfterSale[i].label+"\">"+dicAfterSale[i].label+"</option>";
	    }
	    
		ifClick = true;
		bootbox.dialog({
			title : "添加售后记录",
			message : '<DIV class="">'
			        +'<DIV class="">'
			        +'<DIV class="panel-body">'
			        +'<FORM id="aftersale_form" class="form-horizontal" role="form"'
			        +'method="post" action="" autocomplete="off">'
			        +'<input type="hidden" name="flowDealID" value="'+flowDealID+'">'
			        +'<table width="90%" cellspacing="2px">'
			        +'<tr style="height: 40px;">'
			        +'<td style="text-align:right;" width="100"><b>设&nbsp;备&nbsp;SN：</b></td>'
			        +'<td><b>'+SN+'</b></td>'
			        +'<td style="text-align:right;" width="80"><b>客户姓名：</b></td>'
			        +'<td><b>'+customerName+'</b></td>'
			        +'</tr>'
			        +'<tr style="height: 40px;">'
			        +'<td style="text-align:right;" width="100"><b>问题类型：</b></td>'
			        +'<td><select class="form-control" id="problemType" name="problemType">'
			        +str
			        +'</select></td>'
			        +'<td style="text-align:right;" width="80"><b>问题级别：</b></td>'
			        +'<td><select class="form-control" id="problemLevel" name="problemLevel">'
			        +'<option value="1">1</option>'
                    +'<option value="2">2</option>'
                    +'<option value="3">3</option>'
			        +'</select></td>'
			        +'</tr>'
			        +'<tr style="height: 40px;">'
                    +'<td style="text-align:right;" width="80"><b>处理结果：</b></td>'
                    +'<td><select class="form-control" id="handleResult" name="handleResult" onchange="onChange()">'
                    +'<option value="">请选择</option><option value="已解决">已解决</option>'
                    +'<option value="未解决">未解决</option><option value="退款">退款</option><option value="退货">退货</option>'
                    +'</select></td>'
			        +'<td style="text-align:right;" width="100"><b>退款金额：</b></td>'
			        +'<td><input type="text" id="refundAmount" disabled="disabled" name="refundAmount" class="form-control"/></td>'
			        +'</tr>'
                    +'<tr style="height: 40px;">'
                    +'<td style="text-align:right;" width="80"><b>问题出现时间：</b></td>'
                    +'<td colspan="3"><INPUT id="problemTime" class="form-control form_datetime" name="problemTime" type="text" data-date-format="YYYY-MM-DD HH:mm:ss"></td>'
                    +'</tr>'
                    +'<tr style="height: 40px;">'
                    +'<td style="text-align:right;" width="100"><b>处理描述：</b></td>'
                    +'<td colspan="3"><textarea id="handleDescription" rows="3" name="handleDescription" maxlength="499" data-popover-offset="0,8" class="form-control"></textarea></td></tr>'
                    +'<tr><td style="text-align:right;" width="100"><b>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</b></td>'
                    +'<td colspan="3"><textarea id="remark" rows="3" name="remark" maxlength="499" data-popover-offset="0,8" class="form-control"></textarea></td>'
                    +'</tr></table></FORM></DIV></DIV></DIV>'
                    +'<script type="text/javascript">bindDatePicker()<\/script>',
			buttons : {
				cancel : {
					label : "取消",
					className : "btn-default",
					callback : function() {
						ifClick=false;
					}
				},
				success : {
					label : "添加",
					className : "btn-success byte-unit-picker-button-ok",
					callback : function() {
						ifClick=false;
						if($("#handleResult").val() == ""){
				            alert("请选择处理结果");
				            return;
				        }
					   $.ajax({
			               type:"POST",
			               url:"<%=basePath%>orders/aftersale/save",
							data : $('#aftersale_form')
									.serialize(),
							success : function(data) {
								result = jQuery
										.parseJSON(data);
								if (result.code == 0) { // 成功保存
									easy2go.toast('info',
											result.msg);
									gridObj.refreshPage();
								} else {
									easy2go.toast('error',
											result.msg);
								}
		
							}
						});
						}
					}
				}
			});
		}
	
	//删除售后记录
	function delete_afterSale(asiID){
		if(confirm("确认删除该售后记录?")) {
			$.ajax({
				type:"POST",
	            url:"<%=basePath%>orders/aftersale/deleteinfo?asiID="+asiID,
	            success : function(data) {
	                result = jQuery.parseJSON(data);
			        if (result.code == 0) { // 成功保存
			            easy2go.toast('info',result.msg);
		                gridObj1.options.otherParames=$('#searchForm1').serializeArray();
			            gridObj1.refreshPage();
			        } else {
			            easy2go.toast('error',result.msg);
			        }
			    }
			});
		}
	}
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>