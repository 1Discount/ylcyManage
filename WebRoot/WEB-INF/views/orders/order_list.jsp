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
<title><c:if test="${not IsOtherOrderStatus}">全部有效订单-订单管理-EASY2GO ADMIN</c:if><c:if test="${IsOtherOrderStatus}">全部其他订单-订单管理-EASY2GO ADMIN</c:if></title>
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

							<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
								<input type="hidden" value="1" id="pagenum"/><input type="hidden" id="pagesize" value="20" />

								<DIV style="display: none;" class="form-group">
									<LABEL class="inline-label">订单ID：</LABEL><INPUT
										class="form-control" id="order_ID" name="orderID" type="text"
										placeholder="订单ID">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">客户名称：</LABEL><INPUT style="width: 137px;"
										class="form-control" id="order_customerName"
										name="customerName" type="text" placeholder="客户名称">
								</DIV>
								
                                <DIV class="form-group">
                                    <LABEL class="inline-label">时间段：</LABEL> <INPUT
                                        id="order_creatorDatebegin" class="form-control form_datetime"
                                        name="begindate" type="text"
                                        data-date-format="YYYY-MM-DD HH:mm:ss">
                                </DIV>

                                <DIV class="form-group">
                                    <LABEL class="inline-label">到</LABEL> <INPUT
                                        id="order_creatorDateend" class="form-control form_datetime"
                                        name="enddate" type="text"
                                        data-date-format="YYYY-MM-DD HH:mm:ss">
                                </DIV>
								
						        <div class="form-group">
						          <label class="inline-label">订单状态：</label>
						          <select name="orderStatus" style="width: 137px;" class="form-control">

                                    <option value="已付款">全部有效订单</option>
                                      <c:forEach items="${OrderStatusDict}" var="item" varStatus="status"><option value="${item.label}">${item.label}</option></c:forEach>

						          </select>
						        </div>
                                <div class="form-group">
                                  <label class="inline-label">订单来源：</label>
                                  <select name="orderSource" style="width: 90px;" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${OrderSourceDict}" var="item" varStatus="status">
                                    <c:if test="${item.label ne '淘宝' and item.label ne '其他'}">
                                    <option value="${item.label}">${item.label}</option>
                                    </c:if>
                                    </c:forEach>
                                     <option value="渠道商">渠道商</option>
                                  </select>
                                </div>
                                
                                <div class="form-group">
                                  <label class="inline-label"> 渠道商：</label>
                                  <select name="company" style="width: 90px;" class="form-control">
                                    <option value="">全部</option>
                                    <c:forEach items="${dis}" var="item" varStatus="status">
                                    <option value="${item.company}">${item.company}</option>
                                    </c:forEach>
                                  </select>
                                </div>

								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button"
										onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
								</DIV>
								
								<div class="form-group"><label class="inline-label" style="margin-left:30px;font-size:14px;">默认显示全部有效订单 <a href="#note1"><em>说明(注1)</em></a></label></div>
							</FORM>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">所有订单</H3>
						</DIV>
						<span style="color: green;"></span>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									 <th w_render="orderidOP" width="10%;">订单ID</th>
									<th w_index="customerName" width="10%;">客户</th>
									<th w_index="deviceDealCount" width="10%;">设备交易数</th>
									<th w_index="flowDealCount" width="10%;">流量交易数</th>
									<th w_render="render_orderAmount" width="10%;">总金额</th>
									<th w_render="render_orderSource" width="10%;">来源</th>
									<th w_render="render_orderStatus" width="10%;">订单状态</th>
									<th w_index="shipmentsStatus" width="10%;">发货状态</th>
									<th w_index="creatorUserName" width="10%;">创建人</th>
									<th w_index="creatorDate" width="10%;">创建时间</th>
									<th w_index="modifyDate" width="10%;">最后更新</th>
									<th w_render="operate" width="30%;">操作</th> 
								</tr>
							</table>
						</DIV>
						<div style="padding:15px;"><p>备注:</p><p id="note1">1. 默认显示全部有效订单, 即后台, 官网, APP中全部"已付款"状态的订单, 也即未删除未取消未退订的订单.</p></div>
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
	<script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
<c:if test="${not IsOtherOrderStatus}"><script type="text/javascript">
var queryParam = ''; // 空白时默认显示有效订单
</script>
</c:if><c:if test="${IsOtherOrderStatus}"><script type="text/javascript">
var queryParam = '?otherStatusFlag=0'; // 空白时默认显示有效订单
</script></c:if>
	<SCRIPT type="text/javascript">
	accounting.settings = { // 参数的默认设置:
	        currency: {
	            symbol : "￥",   //默认的货币符号 '$'
	            format: "%s%v", // 输出控制: %s = 符号, %v = 值或数字 (can be object: see below)
	            decimal : ".",  // 小数点分隔符
	            thousand: ",",  // 千位分隔符
	            precision : 2   // 小数位数
	        },
	        number: {
	            precision : 0,  // 精度，默认的精度值为0
	            thousand: ",",
	            decimal : "."
	        }
	    };
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
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
                 url:'<%=basePath%>orders/ordersinfo/getpage' + queryParam,
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 pageSizeForGrid:[5,30,50,100],

                 displayPagingToolbarOnlyMultiPages:true,
                 multiSort:true,
                 autoLoad:false,
                 otherParames:$('#searchForm').serializeArray(),
                 rowHoverColor:true,
                 autoLoad:false,
                 //otherParames:$('#searchForm').serializeArray(),
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
	           }
         });
          
          //自定义列
         function operate(record, rowIndex, colIndex, options) {
             //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
             /*  var temp='&nbsp;&nbsp;<a class="btn btn-primary btn-xs" onclick="workflow(\''+record.orderID+'\')">查看订单流程</a>';  */
             var temp="";
             if(record.orderSource=="微信" && record.orderStatus=="未付款"){
                 var opString = '<a class="btn btn-primary btn-xs" onclick="upweixing('+rowIndex+')"><span class="glyphicon glyphicon-edit">确认</span></a>'
                      + '&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-primary btn-xs" href="<%=basePath%>orders/ordersinfo/edit?orderID='+record.orderID+'"><span class="glyphicon glyphicon-edit">编辑</span></a>'
            		  + '&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger  btn-xs" onclick="deletebyid('+rowIndex+')"><span class="glyphicon glyphicon-remove">删除</span></a>';
                 if(record.orderStatus == '退订中'){<%-- 确认退订 按钮放前面--%>
            	    opString = '<a class="btn btn-primary btn-xs" onclick="cancelPaidOrderbyid('+rowIndex+')"><span class="glyphicon glyphicon-return">确认退订</span></a>&nbsp;&nbsp;'
            	    + opString;
                 }
                 return opString;
             }else{
            	 var opString = '<a class="btn btn-primary btn-xs" href="<%=basePath%>orders/ordersinfo/edit?orderID='+record.orderID+'"><span class="glyphicon glyphicon-edit">编辑</span></a>'
       		     + '&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-danger  btn-xs" onclick="deletebyid('+rowIndex+')"><span class="glyphicon glyphicon-remove">删除</span></a>';
                 if(record.orderStatus == '退订中'){<%-- 确认退订 按钮放前面--%>
       	         opString = '<a class="btn btn-primary btn-xs" onclick="cancelPaidOrderbyid('+rowIndex+')"><span class="glyphicon glyphicon-return">确认退订</span></a>&nbsp;&nbsp;'
       	         + opString;
                 }
                 return opString+temp;
             }
         } 
         function workflow(orderID){
        	 window.location.href="<%=basePath%>workflow/gooflow?orderID="+orderID;
         }
         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<A  href="<%=basePath%>orders/ordersinfo/orderinfo?ordersID='+record.orderID+'"><SPAN class="" style="color: green;">详细</SPAN></A>';
         }
         function render_orderAmount(record, rowIndex, colIndex, options) {
             return accounting.formatMoney(record.orderAmount);
          }
         function render_orderSource(record, rowIndex, colIndex, options) {
        	 if(record.orderSource == null || record.orderSource == ''){
        		 record.orderSource = '后台';
        	 }
             return record.orderSource;
          }
         function render_orderStatus(record, rowIndex, colIndex, options) {
             if(record.orderStatus == '' || record.orderStatus == '0'){
                 record.orderStatus = '已付款';
             }
             return record.orderStatus;
          }
         function upweixing(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("该订单来源于微信是否确认生效？", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"GET",
                		 url:"<%=basePath%>orders/ordersinfo/updatewxStatus/" + record.orderID,
						dataType : 'html',
						success : function(data) {
							if (data == "1") {
								easy2go.toast('info', "确认成功！");
								gridObj.refreshPage();
							} else if (data == "0") {
								easy2go.toast('warn', "确认失败！");
							}else{
								 bootbox.confirm("设备"+data+"有未结束的有效订单,是否继续？", function(result) {
								  if(result){
									   
								      $.ajax({
					                         type:"GET",
					                         url:"<%=basePath%>orders/ordersinfo/checkwxordersecond/"
					                                + record.orderID+"|"+data,
					                        dataType : 'html',
					                        success : function(data2) {
					                            if (data2 == "1") {
					                                easy2go.toast('info', "确认成功！");
					                                gridObj.refreshPage();
					                            } else if(data2 =="0"){
					                            	easy2go.toast('info','确认失败！');
					                            }else{
					                            	easy2go.toast('warn',data2);
					                            }
					                        }
					                    });
									  
								  }
								 });
							}
						}
					});
				} 
			});
		}
         function cancelPaidOrderbyid(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确认退订此订单吗? 退订后则订单状态更改为'已退订', 款项将按来源相应处理, 返还给客户(详细见说明)", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"GET",
                		 url:"<%=basePath%>orders/ordersinfo/cancelPaidOrder/" + record.orderID,
						dataType : 'html',
						success : function(data) {
							if (data == "1") {
								easy2go.toast('info', "退订成功");
								gridObj.refreshPage();
							} else if (data == "0") {
								easy2go.toast('warn', "退订失败");
							} else if (data == "-1") {
								easy2go.toast('warn', "参数为空");
							} else if (data == "-2") {
                                easy2go.toast('warn', "查无此订单");
							} else if (data == "-3") {
                                easy2go.toast('warn', "此订单不可退订, 只有官网或APP的订单状态为'退订中'的订单才可退订");
 							} else if (data == "-4") {
								easy2go.toast('error', "重要! 此订单已退订, 但后台未成功返还此客户在线账户金额, 请跟进处理!", null, 0);
							} else if (data == "-5") {
								easy2go.toast('err', "请重新登录!");
							}
						}
					});
				} 
			});
		}
         
         function deletebyid(index){
             var record= gridObj.getRecord(index);
             bootbox.confirm("确定删除吗?", function(result) {
                 if(result){
                     $.ajax({
                         type:"GET",
                         url:"<%=basePath%>orders/ordersinfo/deleteby/"
                                + record.orderID,
                        dataType : 'html',
                        success : function(data) {
                            if (data == "1") {
                                easy2go.toast('info', "删除成功");
                                gridObj.refreshPage();
                            } else if (data == "0") {
                                easy2go.toast('warn', "删除失败");
                            } else if (data == "-1") {
                                easy2go.toast('warn', "参数为空");
                            } else if (data == "-5") {
                                easy2go.toast('err', "请重新登录!");
                            }
                        }
                    });
                } 
            });
        }
		function re() {
			$("#order_ID").val('');
			$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
		}
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>