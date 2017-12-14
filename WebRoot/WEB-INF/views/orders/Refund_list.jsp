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
<title>退款管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html" %>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
#searchTable2 tr {
	height: 36px;
}
</style>
</head>
<body>
<div id="messdele">${messagedevicedele}</div>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<!-- ============================================================ -->
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
<%-- 							<FORM class="form-inline" role="form" id="searchForm" method="get" action="<%=path%>/device/deviceinfolikePage" > --%>
							<FORM class="form-inline" role="form" id="searchForm" method="get" action="" >
								<DIV class="form-group">
									<LABEL class="inline-label">状态：</LABEL>
									<select class="form-control" name="refundStatus" id="refundStatus">
									     <option>--所有状态--</option>
									     <option>等待退款</option>
									     <option>退款成功</option>
<%--                                          <c:forEach var="dev" items="${types}"> --%>
<%--                                              <option>${dev.label}</option> --%>
<%--                                          </c:forEach> --%>
                                    </select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">订单号：</LABEL> 
									<INPUT class="form-control" name="orderID" id="orderID" type="text" placeholder="订单号">
								</DIV>
								<DIV class="form-group">
									<BUTTON class="btn btn-primary"
									onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);" 
									type="button">搜索</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>

					<DIV class="panel">
						<DIV class="panel-heading">退款管理</DIV>
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE id="searchTable2">
									<TR>
									    <TH w_render="render_orderID" width="5%;">相关订单</TH>
										<TH w_index="orderID"  width="10%"><b>订单号</b></TH>
										<TH w_index="customerName"  width="5%"><b>客户姓名</b></TH>
										<TH w_index="refundMoney"  width="5%"><b>退款金额</b></TH>
										<TH w_index="refundMoneyTrue"  width="5%"><b>实退金额</b></TH>
										<TH w_index="remark"  width="15%"><b>备注信息</b></TH>
										<TH w_index="refundStatus" width="7%"><b>状态</b></TH>
<!-- 										<TH w_index="refundMoneyTime" w_index="userName" width="5%"><b>申请退款时间</b></TH> -->
										<TH w_render="operate" width="10%"><b>操作</b></TH>
									</TR>
								</TABLE>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
				<!-- ============================================================ -->
			</SECTION>
		</SECTION>
	</section>
	<form id="testform"  method="get"></form>
	
	        <form name=alipayment action="<%=basePath %>refund/alipayapituikuan" method=post target="_blank" id="alipayment">
					<input size="30" type="hidden" name="WIDseller_email" value="cseasy2go@163.com" /> <!-- 家支付宝帐户：-->
					<input size="30" type="hidden" name="WIDrefund_date" id="WIDrefund_date" value="<% java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat();sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");out.println(sdf2.format(new java.util.Date()));%>"/><!-- 退款当天日期：-->
					<input size="30" type="hidden" name="WIDbatch_no" id="WIDbatch_no"/><!--批次号：-->
					<input size="30" type="hidden" name="WIDbatch_num" value="1"/><!--退款笔数：-->
					<input size="30" type="hidden" name="WIDdetail_data" id="WIDdetail_data"/><!--退款详细数据：-->
		    </form>
		
		
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    
	<script>
function messageload(){
	var mess = document.getElementById('messdele').innerHTML;
	if(mess!=""){
		mess=mess+"";
		if(mess.length>35){
			 easy2go.toast('error', mess);
	    	 document.getElementById('messdele').innerHTML="";
		}else{
	     easy2go.toast('info', mess);
    	 document.getElementById('messdele').innerHTML="";
		}
	 }
	}
window.onload=messageload;
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable2',{
           url:'<%=basePath%>refund/listPage',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
 
 //设备状态
    function device_Status(record, rowIndex, colIndex, options){
	   if(record.deviceStatus=="使用中"){
		   return '<span class="btn btn-danger btn-xs">'+record.deviceStatus+'</span>';
	   }else{
    	return '<span class="label label-primary label-xs">'+record.deviceStatus+'</span>';
	   }
    }
    function render_orderID(record, rowIndex, colIndex, options) {
        return '<a title="相关订单详情" href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID=' + record.orderID + '"><span>查看订单详情</span></a>';
     }
    function userName(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;"">'+record.creatorUserName+'</A>';
    }
    function zlgm(record, rowIndex, colIndex, options){
    	if(record.zlgm=="购买"){
    	   return '<span class="btn btn-danger btn-xs">'+record.zlgm+'</span>';
    	}else{
    	   return '<span class="label label-primary label-xs">'+record.zlgm+'</span>';
    	}
    }

    //操作
    function operate(record, rowIndex, colIndex, options) {
        return '<a class="btn btn-primary btn-xs" href="javascript:void(0);">'+
        '<span class="" onclick="tuikuan('+rowIndex+');">确认退款</span></a>';
             
	}

    
    function tuikuan(index){
   	 var record= gridObj.getRecord(index);
       	var nowDate = document.getElementById('WIDrefund_date').value;//当前时间前八位作为批次号开头（接口要求不能修改）
       	var WIDbatch_no = nowDate.substr(0,10).replace('-','');
       	var Num=0;
       	for(var i=0;i<9;i++) { Num+=Math.floor(Math.random()*10000000); } // 生成八位随机数 
       	document.getElementById("WIDbatch_no").value=WIDbatch_no.replace('-','')+"17011201"+Num;//生成退款批次号
   bootbox.dialog({
      message:'请输入退款金额: <input id="refundMoney" value="'+record.refundMoney+'" class="form-control" style="width:200px;margin-bottom:10px;"></input><br/>'+
      '请输入备注信息: <input id="remarks" value="协商退款" class="form-control" style="width:500px;"></input>',
      title: "退款",
      buttons: {
         main: {
       label: "确认退款",
       className: "btn-primary",
       callback: function() {
       	     //首先写入备注信息
       	     
       	     $.ajax({
   				async: false, 
   				type : "POST",
   				url : "<%=basePath %>refund/updateRefundRemarks?remarks="+encodeURI(encodeURI(document.getElementById('remarks').value))+"&aliPayTradeNo="+record.aliPayTradeNo+"&WIDbatch_no="+document.getElementById('WIDbatch_no').value,
   				dataType : "html",
   				success : function(data) 
   				{
   				    msg = data;
   				    if(data=="1"){//提交退款申请
                         var refundMoney = document.getElementById("refundMoney").value;
                         var WIDdetail_data=record.aliPayTradeNo+"^"+refundMoney+"^xieshangtuikuan";
                         document.getElementById("WIDdetail_data").value = WIDdetail_data;
                         document.getElementById('alipayment').submit();//提交申请退款操作
   				    }
   				    else{
   				    	easy2go.toast('info', msg);
   				    }
   				 }
   				});
           }
         }
      }
   });
       	
   }
    
    
    $(document).keydown(function(event){ 
    	if(event.keyCode == 13){//绑定回车 
    		$("#orderID").blur();
    		gridObj.options.otherParames =$('#searchForm').serializeArray();
    		gridObj.page(1);
    		event.returnValue = false;
    	}
    	}); 
    
    
    
   
	</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>