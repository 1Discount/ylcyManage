<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>订单工作流-接单管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
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
			<SECTION class="wrapper" style="padding-bottom: 0;">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-heading">接单</DIV>
						<DIV class="panel-body">
							<DIV class="col-md-12">
								<DIV class="panel" style="background-repeat: repeat;">
									<DIV class="panel-body">
										<FORM id="acceptorder_form" class="form-horizontal" role="form" method="post" action="" autocomplete="off">
											<input type="hidden" name="aoID" value="${acceptOrder.aoID}">
											<table width="90%" cellspacing="2px">
												<tr style="height: 40px;">
													<td style="text-align: right;" width="100"><b>客户姓名：</b></td>
													<td><input class="form-control" type="text" id="customerName" required name="customerName" value="${acceptOrder.customerName}" /></td>
													<td style="text-align: right;"><b>电话：</b></td>
													<td><input class="form-control" type="text" id="customerPhone" required name="customerPhone" value="${acceptOrder.customerPhone}" /></td>
												</tr>
												<tr style="height: 62px;">
													<td style="text-align: right; vertical-align: top;" width="100"><b id="address">快递地址：</b></td>
													<td style="vertical-align: middle;"><input class="form-control" type="text" id="customerAddress" name="customerAddress" value="${acceptOrder.customerAddress}" /> <label class="checkbox-items">
															<input type="radio" name="pickUpWay" checked="checked" value="快递">
															快递
														</label> <label class="checkbox-items">
															<input type="radio" name="pickUpWay" value="自取">
															自取
														</label></td>
													<td style="text-align: right; vertical-align: top;"><b>旺旺号：</b></td>
													<td style="vertical-align: top;"><input class="form-control" type="text" id="wangwangNo" name="wangwangNo" value="${acceptOrder.wangwangNo}" /></td>
												</tr>
												<tr style="height: 40px;">
												
													
													<td style="text-align: right;"><b>设备交易类型：</b></td>
													<td><select class="form-control" id="deviceTransactionType" name="deviceTransactionType">
															<option value="">请选择</option>
															<option value="租用" <c:if test="${'租用' eq acceptOrder.deviceTransactionType}">selected</c:if>>租用</option>
															<option value="购买" <c:if test="${'购买' eq acceptOrder.deviceTransactionType}">selected</c:if>>购买</option>
													</select></td>
												</tr>
											
												<tr style="height: 40px;">
													<td style="text-align: right;" width="100"><b>网店订单编号：</b></td>
													<td><input class="form-control" type="text" id="orderNumber" name="orderNumber" value="${acceptOrder.orderNumber}" /></td>
													<td style="text-align: right;"><b>来源：</b></td>
													<td><select class="form-control" id="orderSource" name="orderSource">
															<option value="">请选择接单来源</option>
															<option value="淘宝A" <c:if test="${acceptOrder.orderSource=='淘宝A'}">selected</c:if>>淘宝A</option>
															<option value="淘宝B" <c:if test="${acceptOrder.orderSource=='淘宝B'}">selected</c:if>>淘宝B</option>
															<option value="天猫" <c:if test="${acceptOrder.orderSource=='天猫'}">selected</c:if>>天猫</option>
															<option value="天猫B" <c:if test="${acceptOrder.orderSource=='天猫B'}">selected</c:if>>天猫B</option>
															<option value="线下" <c:if test="${acceptOrder.orderSource=='线下'}">selected</c:if>>线下</option>
															<option value="武汉店" <c:if test="${acceptOrder.orderSource=='武汉店'}">selected</c:if>>武汉店</option>
															<option value="北京店 " <c:if test="${acceptOrder.orderSource=='北京店'}">selected</c:if>>北京店</option>
															<option value="成都店" <c:if test="${acceptOrder.orderSource=='成都店'}">selected</c:if>>成都店</option>
															<option value="其他" <c:if test="${acceptOrder.orderSource=='其他'}">selected</c:if>>其他</option>
													</select></td>
												</tr>
												<tr style="height: 40px;">
													<td style="text-align: right;" width="100"><b>使用开始时间：</b></td>
													<td><input type="text" id="startTime" name="startTime" required data-popover-offset="0,8" data-date-format="YYYY-MM-DD HH:mm:ss" value="${acceptOrder.startTime}" class="form_datetime form-control"></td>
													<td style="text-align: right;"><b>天数：</b></td>
													<td><input class="form-control" type="number" id="days" name="days" value="${acceptOrder.days}" /></td>
												</tr>
												<tr>
													<td style="text-align: right; vertical-align: top; padding-top: 10px;" width="100"><b>可使用国家：</b></td>
													<td colspan="3">
														<div class="checkbox">
															<c:set var="preivousContinent" value="" />
															<%-- !! 以下约定国家已按大洲的顺序排列 --%>
															<c:forEach items="${Countries}" var="country" varStatus="status">
																<c:if test="${preivousContinent ne country.continent}">
																	<div class="checkbox-group">
																		<strong>${country.continent}</strong>
																	</div>
																	<c:set var="preivousContinent" value="${country.continent}" />
																</c:if>
																<c:if test="${country.selected}">
																	<label class="checkbox-items">
																		<input type="checkbox" name="countryList" value="${country.countryName}-${country.countryCode}-${country.flowPrice }" checked>
																		${country.countryName}
																	</label>
																</c:if>
																<c:if test="${!country.selected}">
																	<label class="checkbox-items">
																		<input type="checkbox" name="countryList" value="${country.countryName}-${country.countryCode}-${country.flowPrice }">
																		${country.countryName}
																	</label>
																</c:if>
															</c:forEach>
														</div>
													</td>
												</tr>
												<tr style="height: 40px;">
													<td style="text-align: right;" width="100"><b>行程：</b></td>
													<td><input type="text" id="trip" name="trip" data-popover-offset="0,8" value="${acceptOrder.trip}" class="form-control"><a href="#">示例:中国（151102-151105，151108）|香港（151106-151107）</a></td>
													<td style="text-align: right;"><b>数量：</b></td>
													<td><input type="number" id="total" name="total" data-popover-offset="0,8" value="${acceptOrder.total==null?0:acceptOrder.total}" class="form-control"><a href="#">&nbsp;</a></td>
												</tr>
												<tr style="height: 40px;">
													<td style="text-align: right;" width="100"><b>金额：</b></td>
													<td><input type="text" id="price" name="price" data-popover-offset="0,8" value="${acceptOrder.price==null?0.0:acceptOrder.price}" class="form-control"></td>
													<td style="text-align: right;" width="100"><b>待发货时间：</b></td>
													<td><input type="text" id="shippmentDate" name="shippmentDate" required data-popover-offset="0,8" data-date-format="YYYY-MM-DD" value="${acceptOrder.shippmentDate==''?'':acceptOrder.shippmentDate}" class="form_datetime form-control"></td>
												
												</tr>
													
												<tr>
													<td style="text-align: right;" width="100"><b>备注：</b></td>
													<td colspan="3"><textarea id="remark" rows="3" name="remark" maxlength="499" data-popover-offset="0,8" class="form-control">${acceptOrder.remark}</textarea></td>
												</tr>
												<tr style="height: 45px;">
													<td style="text-align: right;" width="100"></td>
													<td colspan="3">
														<button type="submit" class="btn btn-primary">保存</button>
														<button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button>
													</td>
												</tr>
											</table>
										</FORM>
									</DIV>
								</DIV>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
			<SECTION class="wrapper" style="margin-top: 0; padding-top: 0;">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" id="leibie" method="get" action="#">
								<DIV class="form-group">
									<LABEL class="inline-label">状态：</LABEL>
									<select class="form-control" name="acceptOrderStatus" id="acceptOrderStatus" style="width: 150px;">
										<option value="">请选择</option>
										<option value="未下单">未下单</option>
										<option value="已下单">已下单</option>
										<option value="已打回">已打回</option>
									</select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">客户名：</LABEL>
									<input id="customerName" name="customerName" class="form-control" />
								</DIV>
								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
								</DIV>
							</FORM>
							<form id="testc"></form>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">接单列表</DIV>
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE id="searchTable">
									<TR>
										<TH w_index="customerName" width="6%"><b>客户姓名</b></TH>
										<TH w_index="customerPhone" width="6%"><b>手机号</b></TH>
										<TH w_index="customerAddress" width="8%" w_length="15"><b>地址</b></TH>
										<TH w_index="wangwangNo" width="6%"><b>旺旺号</b></TH>
										<TH w_index="orderNumber" width="6%"><b>网店订单编号</b></TH>
										<TH w_index="orderSource" width="6%"><b>来源</b></TH>
										<TH w_index="deviceTransactionType" width="6%"><b>设备交易类型</b></TH>
										<TH w_render="startTime" width="8%"><b>使用开始时间</b></TH>
										<TH w_index="countryName" width="8%"><b>可使用国家</b></TH>
										<th w_index="pickUpWay" width="5%;"><b>取货方式</b></th>
										<TH w_index="trip" width="6%"><b>行程</b></TH>
										<TH w_index="total" width="6%"><b>数量</b></TH>
										<TH w_index="price" width="6%"><b>金额</b></TH>
										<TH w_index="shippmentDate" width="6%"><b>待发货日期</b></TH>
										<TH w_render="acceptOrderStatus" width="6%"><b>状态</b></TH>
										<TH w_index="callBackReason" width="6%"><b>打回原因</b></TH>
										<TH w_index="remark" width="6%"><b>备注</b></TH>
										<TH w_render="operate" width="4%"><b>操作</b></TH>
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
	<script type="text/javascript">
        
          
    var gridObj;
    $(function(){
    	
       $("input[name='pickUpWay']").click(function(){
    	   if($(this).val()=="快递"){
    		   $("#address").html("快递地址：");
    	   }else{
               $("#address").html("自取地址：");
    	   }
       });
    	
       $(".form_datetime").datetimepicker({
           format: 'YYYY-MM-DD HH:00',
           pickDate: true,     
           pickTime: true,   
           showToday: true,    
           language:'zh-CN',   
           defaultDate: null, 
       });
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>orders/acceptorder/getpage',
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
       
       
       $("#acceptorder_form").validate_popover({
           rules:{
        	   'customerName': {
       	          required: false
       	        },
		       'customerPhone': {
		           required: false
		       },
               'startTime': {
                   required: false
               },
               'days': {
                   required: false
               },
               'countryList': {
                   required: false
               }
           },
           messages:{
              'serverCode': {
                  required: "请输入服务器编号.",
                  maxlength: "最多不超过5个字符."
               }
           },
           submitHandler: function(form){
        	   if($("#customerName").val()==""){
        		   easy2go.toast('warn', "请输入客户姓名");
                   return;
        	   }
               if($("#customerPhone").val()==""){
                   easy2go.toast('warn', "请输入手机号");
                   return;
               }
               if($("#price").val()==""){
                   easy2go.toast('warn', "请输入金额");
                   return;
               }
               if($("#orderSource").val()==""){
                   easy2go.toast('warn', "请选择订单来源");
                   return;
               }
               if($("#startTime").val()==""){
                   easy2go.toast('warn', "请选择开始时间");
                   return;
               }
               if($("#days").val()=="" || $("#days").val()=="0"){
                   easy2go.toast('warn', "请输入天数");
                   return;
               }
               if($("#total").val()==0){
           	    easy2go.toast('warn', "请选择设备数量");
                   return;
              }
               if($("#orderSource").val()==""){
             	    easy2go.toast('warn', "请选择订单来源");
                     return;
                }
                if($("#deviceTransactionType").val()==""){
              	    easy2go.toast('warn', "请选择设备交易类型");
                      return;
                 }
               var count = 0;
               var checkArry = document.getElementsByName("countryList");
               for (var i = 0; i < checkArry.length; i++) { 
                   if(checkArry[i].checked == true){
                       //选中的操作
                       count++; 
                   }
               }
               if( count == 0 ){
            	   easy2go.toast('warn', "请选择可使用国家");
            	   return;
               }
               $.ajax({
                   type:"POST",
                   url:"<%=basePath%>orders/acceptorder/save",
                   dataType:"html",
                   data:$('#acceptorder_form').serialize(),
                   success:function(data){
                       result = jQuery.parseJSON(data);
                       if (result.code == 0) { // 成功保存
                          easy2go.toast('info', result.msg);
                          window.location.href="<%=basePath%>orders/acceptorder/index";
                          
                       } else {
                           easy2go.toast('error', result.msg);
                       }
                   }
               });
           }
       });
   });
    
    
   function acceptOrderStatus(record, rowIndex, colIndex, options){
	   if(record.acceptOrderStatus=="已下单"){
		   return '<a class="btn btn-success btn-xs" onclick="return;"><span>已下单</span></a>';
	   }else if(record.acceptOrderStatus=="已打回"){
		   return '<a class="btn btn-warning btn-xs" onclick="return;"><span>已打回</span></a>';
	   }else if(record.acceptOrderStatus=="未下单"){
		   return '<a class="btn btn-danger btn-xs" onclick="return;"><span>未下单</span></a>';
       }
   }
   
   //创建时间
   function startTime(record, rowIndex, colIndex, options){

       if(record.startTime!=null && record.startTime!=""){
           
           var dateStr=record.startTime;
           
           return dateStr.slice(0,dateStr.indexOf("."));
           
       }else{
           
           return "";
       }
   }
 
   function operate(record, rowIndex, colIndex, options) {
	   if(record.acceptOrderStatus == '已下单'){
		   return '<A class="btn btn-primary btn-xs" href="#">'
           +'<SPAN class="glyphicon glyphicon-edit" onclick="op_cancel(\''+record.aoID+'\')">取消订单</SPAN></A>&nbsp;';
	   }else{
           return '<A class="btn btn-primary btn-xs" href="<%=path%>/orders/acceptorder/edit?id='+record.aoID+'">'
           +'<SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;'
           +'<A class="btn btn-danger btn-xs" href="#">'
           +'<SPAN class="glyphicon glyphicon-remove" onclick="op_delete(\''+record.aoID+'\')">删除</SPAN></A>';
	   }
   }
   function op_delete(id) {
       if(confirm("确认删除接单信息?")) {
           $.ajax({
               type:"POST",
               url:"<%=basePath%>orders/acceptorder/deletebyid?id=" + id,
				success : function(data) {
					easy2go.toast('info', data);
					gridObj.refreshPage();
				}
			});
		}
	}
   function op_cancel(id) {
       if(confirm("确认取消订单?")) {
           $.ajax({
               type:"POST",
               url:"<%=basePath%>orders/acceptorder/cancel?id=" + id,
                success : function(data) {
                	result = jQuery.parseJSON(data);
                	if (result.code == 0) { // 成功保存
                        easy2go.toast('info', result.msg);
                        gridObj.refreshPage();
                        
                    } else {
                        easy2go.toast('error', result.msg);
                    }
                }
            });
        }
    }
	</script>

	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="level2" />
	</jsp:include>
</body>
</html>