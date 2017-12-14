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
<title>归还设备-设备管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<SECTION id="main-content" style="">
			<SECTION class="wrapper">
				<DIV class="col-md-12">

					<DIV class="panel">
						<DIV class="panel-heading">
							<b>归还设备</b> <SMALL>归还租用的设备，并且设备状态更改为可用状态。可以在此输入框直接指定设备机身码去尝试归还，或者从下方的客户押金申请记录去操作。它们的结果等同。</SMALL>
						</DIV>
						<DIV class="panel-body">
							<!--                <FORM id="device_form" role="form" method="POST" autocomplete="off" action="" class="form-horizontal"> -->
							<INPUT name="_csrf" value="1yfwZ17s-lN0AGrFNa_UiWO4yqg2gYKf8PFU" type="hidden">
							<INPUT name="_method" value="put" type="hidden">

							<DIV class="form-group" style="height: 20px;">
								<DIV class="col-md-9 col-md-offset-3">
									<div>
										<strong>直接指定机身码归还:</strong>
									</div>
								</DIV>
							</DIV>
							<DIV class="form-group" style="height: 60px;">
								<!--                       <div style="border:0px solid gray;width:90%;color:red;text-align:right;"> -->
								<%--                            ${message}  --%>
								<!--                       </div> -->
								<LABEL class="col-md-3 control-label" for="device_sn">设备机身码：</LABEL>
								<DIV class="col-md-6">
									<INPUT id="device_sn" class="form-control" name="device_sn" type="text" required="" style="width: 320px;">
									<span style="font-size: 12px; color: red;">* 输入后六位即可</span>
									<br>
<!-- 									是否入库： -->
<!-- 									<select style="width: 170px;" class="form-control" name="repertoryStatus" id="repertoryStatus"> -->
<!-- 									  <option selected="selected">是</option> -->
<!-- 									  <option>否</option> -->
<!-- 									</select> -->

								</DIV>
								<DIV class="col-md-3">
									<P class="form-control-static">
										<!--                           <SPAN class="red">*</SPAN> -->
									</P>
								</DIV>
							</DIV>
							<DIV class="form-group" style="margin-top: 20px;">
								<DIV class="col-md-9 col-md-offset-3">
									<DIV class="btn-toolbar">
										<BUTTON class="btn btn-primary" onclick="return revertodata()">归还</BUTTON>
										<BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
									</DIV>
								</DIV>
							</DIV>
							<div>
								<a href="<%=basePath%>revertDevice/revertall">
									<img src="<%=basePath%>static/images/ico_excel_n_2.jpg" style="width: 20px; height: 20px;" /> <b>使用Excel批量导入归还请点击这里</b>
								</a>
							</div>
							<!--              </FORM> -->
						</DIV>

						<div id="backmessage" style="margin-bottom: 20px; padding-left: 15px; border: 1px solid #e2e2e4; font-size: 14px;">
							<!--      归还成功！ -->
						</div>

						<div style="padding-left: 15px; border: 1px solid #e2e2e4; height: 120px; background-color: #e2e2e4; font-size: 11px; color: gray;">
							<p>
							<p>注意：</p>
							<p style="margin-left: 43px;">
								1.可输入单个设备号(
								<span style="color: red;">后六位即可</span>
								)，归还设备
							</p>
							<p style="margin-left: 43px;">
								2.多个设备归还 输入设备号后四位用"
								<span style="color: red;">/</span>
								"隔开 ；例如：001203/001204/001205
							</p>
                           <p style="margin-left: 43px;">
								<span style="color: red;">3.归还设备与库存无关，这里这是解除下订单限制条件，正确的入库步骤为：归还设备——>入库(设备入库) 经过这两个步骤说明设备已经改为库存设备</span>
							</p>
							</p>
						</div>
						<%--      <!-- 批量归还 开始 -->
    <div style="padding-top:20px;padding-left:15px;border:1px solid #e2e2e4;height:130px;font-size:11px;color:gray;margin-top:10px;">
          <form id="import_form" class="form-horizontal" action="<%=basePath %>revertDevice/readSN" method="post" enctype="multipart/form-data">
            <div class="form-group">
              <label class="col-sm-3 control-label">EXCEL批量归还设备：</label>
              <div class="col-sm-9">
                <input type="file" name="file" id="file" data-popover-offset="0,8" required class="form-control" style="width:500px;">
              </div>
            </div>
           <div class="form-group">
              <div class="col-sm-6 col-sm-offset-3">
                <div class="btn-toolbar">
                  <button type="submit" class="btn btn-primary">归还</button>
                  <button type="button" onclick="javascript:history.go(-1);"class="btn btn-default">返回</button>
                </div>
              </div>
            </div>
          </form>
          <div>${excelreverdevmsg}</div>
    </div><!-- Excel 批量归还 结束 --> --%>

					</DIV>

					<div class="panel">
						<div class="panel-heading">客户押金申请记录</div>
						<div class="panel-body">
							<form class="form-inline" id="searchForm" role="form" method="get" action="#">
								<div class="form-group">
									<label class="inline-label">机身码：</label>
									<input class="form-control" name="SN" type="text" placeholder="SN">
								</div>
								<div class="form-group">
									<label class="inline-label">客户姓名：</label>
									<input class="form-control" name="customerName" type="text" placeholder="姓名">
								</div>
								<div class="form-group">
									<label class="inline-label">申请记录状态：</label>
									<select name="status" style="width: 170px;" class="form-control">
										<option value="">全部</option>
										<c:forEach items="${DepositStatusDict}" var="item" varStatus="status">
											<option value="${item.label}">${item.label}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<button class="btn btn-primary" type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button>
								</div>
							</form>
						</div>
					</div>
					<div class="panel">
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable">
									<tr>
										<th w_index="recordID" w_hidden="true">ID</th>
										<th w_render="render_orderID" width="5%;">相关订单</th>
										<%-- w_sort="serverCode,asc" --%>
										<th w_index="SN" width="5%;">机身码</th>
										<th w_render="render_customerName" width="10%;">客户</th>
										<th w_index="phone" width="10%;">电话</th>
										<th w_render="render_dealAmount" width="10%;">押金总额</th>
										<th w_index="refundMoney" width="10%;">实退金额</th>
										<th w_index="aliPayTradeNo" width="10%">支付宝交易号</th>
										<th w_index="status" width="10%;">申请状态</th>
										<th w_index="expressName" width="10%;">快递名</th>
										<th w_render="render_expressNum" width="10%;">快递单号</th>
										<th w_index="creatorDate" width="10%;">申请时间</th>
										<th w_index="remark" width="10%;">备注/反馈</th>
										<th w_render="operate">操作(红色表示不可操作)</th>
									</tr>
								</table>
							</div>
						</div>
						<div style="padding-left: 10px; padding-bottom: 20px;">
							<p>
								<strong>说明：</strong>
							</p>
							<p>1. 表格中的操作之后，操作结果可见刷新页面之后更新的“操作结果提示”处的文字。见上面</p>
						</div>
					</div>
				</DIV>

				<form name=alipayment action="<%=basePath%>refund/alipayapi" method=post target="_blank" id="alipayment">
					<input size="30" type="hidden" name="WIDseller_email" value="cseasy2go@163.com" />
					<!-- 家支付宝帐户：-->
					<input size="30" type="hidden" name="WIDrefund_date" id="WIDrefund_date" value="<%java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat();sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");out.println(sdf2.format(new java.util.Date()));%>" />
					<!-- 退款当天日期：-->
					<input size="30" type="hidden" name="WIDbatch_no" id="WIDbatch_no" /><!-- 201603011701120129861643 -->
					<!--批次号：-->
					<input size="30" type="hidden" name="WIDbatch_num" value="1" />
					<!--退款笔数：-->
					<input size="30" type="hidden" name="WIDdetail_data" id="WIDdetail_data" /><!-- 201603011701120129861643^0.01^tuiyajing -->
					<!--退款详细数据：-->
				</form>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script type="text/javascript">

function tuikuan(index){
	 var record= gridObj.getRecord(index);
    	var nowDate = document.getElementById('WIDrefund_date').value;//当前时间前八位作为批次号开头（接口要求不能修改）
    	var WIDbatch_no = nowDate.substr(0,10).replace('-','');
    	var Num=0;
    	for(var i=0;i<9;i++) { Num+=Math.floor(Math.random()*10000000); } // 生成八位随机数
    	document.getElementById("WIDbatch_no").value=WIDbatch_no.replace('-','')+"17011201"+Num;//生成退款批次号

bootbox.dialog({
   message:'请输入退还金额: <input id="refundMoney" value="'+record.dealAmount+'" class="form-control" style="width:200px;margin-bottom:10px;"></input><br/>'+
   '请输入备注信息: <input id="remarks" value="协商退款" class="form-control" style="width:500px;"></input>',
   title: "退还押金",
   buttons: {
      main: {
    label: "确认退款",
    className: "btn-primary",
    callback: function() {
    	     //首先写入备注信息

    	     $.ajax({
				async: false,
				type : "POST",
				url : "<%=basePath%>device/deposit/updateRefundRemarks?remarks="+encodeURI(encodeURI(document.getElementById('remarks').value))+"&aliPayTradeNo="+record.aliPayTradeNo+"&WIDbatch_no="+document.getElementById('WIDbatch_no').value,
				dataType : "html",
				success : function(data) {
				    msg = data;
				    if(data=="1"){//提交退款申请
                      var refundMoney = document.getElementById("refundMoney").value;//退款金额

                      var WIDdetail_data=record.aliPayTradeNo+"^"+refundMoney+"^tuiyajing";
                      document.getElementById("WIDdetail_data").value = WIDdetail_data;

                      document.getElementById('alipayment').submit();//提交申请退款操作
				    }
				    else{
				    	easy2go.toast('info', msg);
				    }
				}});
        }
      }
   }
});

}


        function revertodata(){
        	var devicesn = document.getElementById('device_sn').value.trim();
//         	var repertoryStatus = document.getElementById('repertoryStatus').value;
//         	repertoryStatus = encodeURI(encodeURI(repertoryStatus));
        	if(devicesn==""){

        		easy2go.toast('error', "请填写设备号！");
        		return false;
        	}else if(devicesn.length<4){
        		easy2go.toast('error',"请输入设备后四位数字！");
        		document.getElementById('device_sn').focus();
        		return false;
        	}
        	else{
			$.ajax({
				async: false,
				type : "POST",
<%-- 				url : "<%=basePath%>device/revertTo?device_sn="+document.getElementById('device_sn').value+"&repertoryStatus="+repertoryStatus, --%>
				url : "<%=basePath%>device/revertTo?device_sn="+document.getElementById('device_sn').value,
				dataType : "html",
				success : function(data) {
				    msg = data;
// 					data = parseInt(data);
// 					 if (data == 1) {
// 								easy2go.toast('warn', "修改设备信息表状态失败！");
// 							} else if (data == 2) {
// 								easy2go.toast('warn', "修改设备订单表 设备状态为 已归还失败！");
// 							} else if (data == 3) {
// 								easy2go.toast('warn', "修改流量交易订单失败！");
// 							}else if (data == 4) {
// 						        easy2go.toast('info', "归还成功！");
// 							} else if (data == 5) {
// 								easy2go.toast('warn', "设备归还失败!");
// 							} else if (data == 6) {
// 								easy2go.toast('warn', "此设备当前还没有交易记录,不存在归还操作 !");
// 							}
// 							else if (data == 7) {
// 								easy2go.toast('warn', "没有搜索到此设备记录，此设备信息或已被他人删除！");
// 							}
// 							else {
	                            document.getElementById("backmessage").innerHTML=msg;
							    easy2go.toast('info', msg);
// 							}
						}
					});
        	}


        }
</script>

	<script type="text/javascript">
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

          var gridObj; <%-- 此表请勿打开multiSort --%>
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>device/deposit/getPageRecord',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false
             });
         });

          function render_orderID(record, rowIndex, colIndex, options) {
              return '<a title="相关订单详情" href="<%=basePath%>orders/ordersinfo/orderinfo?ordersID=' + record.orderID + '"><span>查看</span></a>';
           }
          function render_customerName(record, rowIndex, colIndex, options) {
              return '<a title="客户详情" href="<%=basePath%>customer/customerInfolist/customerInfoDetail?cusid=' + record.customerID + '"><span>' + record.customerName + '</span></a>';
           }
          function render_expressNum(record, rowIndex, colIndex, options) {
              return '<a title="物流详情" href="javascript:void(0);"><span>' + record.expressNum + '</span></a>';
           }
          function render_dealAmount(record, rowIndex, colIndex, options) {
              return accounting.formatMoney(record.dealAmount);;
           }
         function operate(record, rowIndex, colIndex, options) {
        	 if(record.status == '等待处理' && (record.ifReturn == null || record.ifReturn.length == 0 || record.ifReturn == '否' )){
                 return '<div class="btn-toolbar">'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath%>device/revertTo?from=deposit&device_sn=' + record.SN +'"><span class="glyphicon glyphicon-edit">归还设备</span></a>'
                 +'<a class="btn btn-danger btn-xs" title="归还设备成功后才能退押金" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" onclick="tuikuan('+rowIndex+');">退还押金</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath%>device/deposit/edit?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>'
                 + '</div>';
        	 } else if(record.status == '等待处理' && record.ifReturn == '是') {
        		 return '<div class="btn-toolbar">'
                 +'<a class="btn btn-success btn-xs" title="已归还设备成功, 可以处理退押金了" href="javascript:void(0);"><span class="glyphicon glyphicon-edit">成功归还</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath%>device/deposit/withdraw?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-share" onclick="tuikuan('+rowIndex+');">退还押金</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath%>device/deposit/edit?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>'
                 + '</div>';
        	 } else if(record.status == '退款成功') {
                 return '<div class="btn-toolbar">'
                 +'<a class="btn btn-success btn-xs" title="押金退款成功" href="javascript:void(0);"><span class="glyphicon glyphicon-edit">已退押金</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath%>device/deposit/edit?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>'
                 + '</div>';
        	 } else if(record.status == '申请异常') {<%-- 申请异常时设备应该未归还 目前同第一个 等待处理+未归还 一样的操作 --%>
	             return '<div class="btn-toolbar">'
	             +'<a class="btn btn-primary btn-xs" href="<%=basePath%>device/revertTo?from=deposit&device_sn=' + record.SN +'"><span class="glyphicon glyphicon-edit">归还设备</span></a>'
	             +'<a class="btn btn-danger btn-xs" title="处理好异常后再操作归还和处理押金等" href="javascript:void(0);"><span class="glyphicon glyphicon-remove">异常反馈</span></a>'
	             +'<a class="btn btn-primary btn-xs" href="<%=basePath%>device/deposit/edit?recordID=' + record.recordID + '"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>' + '</div>';
			}
		}
	</script>

	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>