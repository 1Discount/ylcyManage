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
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" id="searchForm" role="form"
								method="get" action="#">
									<input type="hidden" value="1" id="pagenum" />
									<input type="hidden" id="pagesize" value="15" />

								<DIV style="display: none;" class="form-group">
									<LABEL class="inline-label">交易ID：</LABEL><INPUT
										class="form-control" id="order_ID" name="flowDealID"
										type="text" placeholder="交易ID">
								</DIV>
                                <DIV class="form-group">
                                    <LABEL class="inline-label">机身码：</LABEL><INPUT
                                        class="form-control" id="SN" name="SN" type="text"
                                        placeholder="设备机身码">
                                </DIV>
									<div class="form-group">
									<label class="inline-label">订单来源：</label><!-- <input
										class="form-control" name="orderSource" type="text"/> -->
										<select id="orderSource" name="orderSource" class="form-control">
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
								<DIV class="form-group"><LABEL class="inline-label">渠道商：</LABEL>
									
									<select id="distributorName" name="distributorName" class="form-control form_datetime">
											<option value="">全部</option>
										<c:forEach items="${distributors}" var="dis">
											<option value="${dis.company }">${dis.company}</option>
										</c:forEach>
									</select></DIV>
                                <DIV class="form-group">
                                    <LABEL class="inline-label">是否已退款：</LABEL> 
                                    <select name="refundStatus" class="form-control">
                                        <option value="">全部</option>
                                        <option value="是">是</option>
                                        <option value="否">否</option>
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
									<LABEL class="inline-label">客户：</LABEL><INPUT
										class="form-control" id="order_customerName"
										name="customerName" type="text" placeholder="姓名">
								</DIV>
                                <div class="form-group">
                                    <label class="inline-label">国 家：</label><input
                                        class="form-control" name="userCountry"  id="userCountry" type="text"/>
                                </div>
								<DIV class="form-group">
									<LABEL class="inline-label">时间段：</LABEL> <INPUT
										id="order_creatorDatebegin" class="form-control form_datetime"
										name="begindateForQuery" type="text"
										data-date-format="YYYY-MM-DD HH:mm:ss">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL> <INPUT
										id="order_creatorDateend" class="form-control form_datetime"
										name="enddate" type="text"
										data-date-format="YYYY-MM-DD HH:mm:ss">
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
									<th w_index="SN" width="10%;">设备机身码</th>
									<th w_index="customerName" width="10%;">客户</th>
									<th w_index="userCountry" width="10%;">国家</th>
									<th w_index="orderSource" width="10%;">订单来源</th>
									<th w_index="orderAmount" width="5%;">总金额</th>
									<th w_index="flowDays" width="3%;">天数</th>
									<th w_index="ifActivate" width="3%;">是否激活</th>
									<th w_index="panlUserDate" width="10%;">预约开始时间</th>
									<th w_index="flowExpireDate" width="10%;">到期时间</th>
									<th w_render="op_lastUpdateDate" width="10%;">上次接入时间</th>
									<th w_index="orderStatus" width="5%;">订单状态</th>
									<th w_index="creatorUserName" width="6%;">创建人</th>
									<th w_index="creatorDate" width="10%;">创建时间</th>
									<th w_render="operate" width="30%;">操作</th>
								</tr>
							</table>
							
							
						</DIV>
					</DIV>
				<DIV class="panel">
				<div class="paomadeng">
								<span style="font-size: 14px; font-weight: bolder;" onclick="return execl();" >
								<a id="a" href="<%=basePath %>orders/flowdealorders/flowexportexecl?"
									><img src="<%=basePath%>static/images/excel.jpg"
										style="float: left; margin-top: -5px;"  width="30" height="30"
										title="" /> 导出EXCEL请点我</a></span>
							</div> 
							
						
							
						
				</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>

	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- dialog body -->
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Hello world!
				</div>
				<!-- dialog buttons -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-primary">取消</button>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
    $(function(){
    	var SN = "${SN}";
    	if(SN!=""){
    		  $("#SN").val(SN);
    	}
    });
    function execl(){	
    		var par = $('#searchForm').serialize();
			$("#a").attr('href','<%=basePath %>orders/flowdealorders/flowexportexecl?'+par);
    }
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
          $(function(){
             $("#order_creatorDatebegin").datetimepicker({
         		pickDate: true,                 
         		pickTime: true,                 
         		showToday: true,                 
         		language:'zh-CN',                 
         		defaultDate: moment().add(-3, 'months'),                
         	});
             
             $("#order_creatorDateend").datetimepicker({
          		pickDate: true,                 
          		pickTime: true,               
          		showToday: true,                
          		language:'zh-CN',                 
          		defaultDate: moment().add(0, 'days'),                 
          	});
             
             
             
             var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>orders/flowdealorders/getpage',
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 autoLoad:false,
                 otherParames:$('#searchForm').serializeArray(),
                 pageSizeForGrid:[15,30,50,100],
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
         
         
         //自定义列.
         function operate(record, rowIndex, colIndex, options) {
              if(record.ifActivate=='否' || record.ifActivate==''){
            	  return '<A class="btn btn-primary btn-xs" onclick="toactivate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-brightness-reduce">激活</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-danger  btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.orderStatus=='使用中' && record.refundStatus!='已退款'){
            	  return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-stop">暂停服务</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;';
              }else if(record.orderStatus=='已暂停' && record.refundStatus!='已退款'){
            	  return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-play">启动服务</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-danger  btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.orderStatus=='可使用' && record.refundStatus!='已退款' && record.SN!=''){
            	  return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-stop">暂停服务</SPAN></A>&nbsp;&nbsp;<a class="btn btn-primary btn-xs"  onclick="updateSN('+rowIndex+')"><SPAN class="glyphicon glyphicon-edit">更换设备</SPAN></a>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-danger  btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.orderStatus=='不可用' &&  record.ifActivate=='是' && record.refundStatus!='已退款'){
            	  return '<A class="btn btn-danger btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.refundStatus=='已退款'){
            	  return '<A class="btn btn-primary btn-xs" href="<%=basePath%>orders/flowdealorders/refundView?flowDealID='+record.flowDealID+'" ><SPAN class="glyphicon glyphicon-brightness-reduce">查看退款详情</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-danger btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.lastUpdateDate==""&&record.orderStatus=='已过期'){
            	  return '<A class="btn btn-danger btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';  
              }
         }
         
         function op_lastUpdateDate(record, rowIndex, colIndex, options){
        	  return '<A  href="<%=basePath%>service/center/device/history?SN='+record.SN+'&lastUpdateDate='+record.lastUpdateDate+'" >'+record.lastUpdateDate+'</A>';
         }
         

         
         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID='+record.flowDealID+'"><span class="" style="color: green;">详情</span></a>';
         }
          
         //根据ID删除 
         function deletebyid(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确定删除吗?", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"POST",
                		 url:"<%=basePath%>orders/ordersinfo/delfloworder?flowDealID="+record.flowDealID+"&orderAmount="+record.orderAmount,
                		 dataType:'html',
                		 success:function(data){ 
                            if(data=="0"){
            					easy2go.toast('warn',"删除失败");
            				}else if(data=="-1"){
            					easy2go.toast('warn',"参数为空");
            				}else if(data=="-5"){
	                            easy2go.toast('err',"请重新登录!");
	                        } else { 
                                easy2go.toast('info',"删除成功");
                                gridObj.refreshPage();
	                        }
                		 }
                	 });
        		 }  
        	 });
         }
         
         
         //刷新
        function re(){
        	$("#order_ID").val('');
        	$("#order_customerName").val('');
        	gridObj.options.otherParames =$('#searchForm').serializeArray();
        	gridObj.refreshPage();
        }
        
       //激活，暂停,启动
       function  activate(index){
    	   var record= gridObj.getRecord(index);
    	   var urlpath="";
    	   var OP="";
      	 if(record.orderStatus=='可使用' || record.orderStatus=='使用中'){
      		OP='暂停服务';
      		urlpath='<%=basePath%>orders/flowdealorders/pause?flowOrderID='+record.flowDealID;
      	 }else if(record.orderStatus=='已暂停'){
      		OP='启动服务';
      		urlpath='<%=basePath%>orders/flowdealorders/launch?flowOrderID='+record.flowDealID;
      	 }
      	 bootbox.confirm("确定要"+OP+"吗?", function(result) {
      		 if(result){
      			 $.ajax({
              		 type:"POST",
              		 url:urlpath,
              		 dataType:'html',
              		 success:function(data){
                        if(data=="0"){
          					easy2go.toast('warn',OP+"失败");
          				}else if(data=="-1"){
          					easy2go.toast('warn',"参数为空");
          				}else if(data=="-5"){
	                        easy2go.toast('err',"请重新登录!");
	                    } else if(data=="1"){
                            easy2go.toast('info',OP+"成功");                              
                            gridObj.refreshPage();
	                    }
              		 }
              	 });
      		 }  
      	 });
       }
       
       //跳转到订单激活界面
       function  toactivate(index){
    	   var record= gridObj.getRecord(index);
    	   window.location="<%=basePath%>orders/ordersinfo/activate?orderID="+record.customerID+"&flowOrderID="+record.flowDealID;
       } 
       function updateSN(index){
    	   	 var record= gridObj.getRecord(index);
    	   	 bootbox.confirm("确定更换吗?", function(result) {
	   	   		 if(result){
	   						 window.location.href="<%=basePath%>orders/flowdealorders/updateSN?flowDealID="+record.flowDealID+"&oldSN="+record.SN;
	   				}
	   		});
    	}
       
       $(document).keydown(function(event){
           if(event.keyCode == 13){//绑定回车
               $("#SN").blur();
               $("#order_customerName").blur();
               $("#userCountry").blur();
               $("#order_creatorDatebegin").blur();
               $("#order_creatorDateend").blur();
               gridObj.options.otherParames =$('#searchForm').serializeArray();
               gridObj.page(1);
               event.returnValue = false;
           }
       });
</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="level1" />
	</jsp:include>
</body>
</html>