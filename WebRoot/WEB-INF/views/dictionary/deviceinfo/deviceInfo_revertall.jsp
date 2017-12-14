<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>批量归还设备-设备管理-流量运营中心</title>
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

<SECTION id="main-content" style="">
<SECTION class="wrapper">
<DIV class="col-md-12">

     <DIV class="panel">
         <DIV class="panel-heading"><b>归还设备</b> <SMALL>归还租用的设备，并且设备状态更改为可用状态。可以在此输入框直接指定设备机身码去尝试归还，或者从下方的客户押金申请记录去操作。它们的结果等同。</SMALL></DIV>


     <!-- 批量归还 开始 -->
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
<!--      归还成功！ -->
     </div>
     <div style="border:1px solid #e2e2e4;margin-top:10px;margin-bottom:10px;">${excelreverdevmsg}</div>
     <div style="padding-left:15px;border:1px solid #e2e2e4;height:120px;background-color:#e2e2e4;font-size:11px;color:red;">
       <p>
           <p>注意：</p>
           <p style="margin-left:43px;">1.批量归还设备需要按照指定的excel格式，机身码列输入设备号的后六位即可。</p>
           <p style="margin-left:43px;">2.批量归还设备与出入库无关，归还设备后即可下单，然后去出入库管理界面，进行入库可改变设备库存数量  </p>
           <p style="margin-left:43px;">3.批量归还设备excel最新格式为：id &nbsp;&nbsp;&nbsp;  机身码(后六位即可) 分id和机身码两列即可  （从2016-5-12起调整为两列，去掉是否入库列）</p>
       </p>
     </div>
    </div><!-- Excel 批量归还 结束 -->

   </DIV>

        <div class="panel">
        <div class="panel-heading">客户押金申请记录</div>
        <div class="panel-body">
        <form class="form-inline" id="searchForm" role="form" method="get" action="#">
        <div class="form-group">
            <label class="inline-label">SN：</label><input class="form-control" name="SN" type="text" placeholder="SN" ></div>
        <div class="form-group">
            <label class="inline-label">客户姓名：</label><input class="form-control" name="customerName" type="text" placeholder="姓名" ></div>
        <div class="form-group">
          <label class="inline-label">申请记录状态：</label>
          <select name="status" style="width: 170px;" class="form-control">
            <option value="">全部</option>
    <c:forEach items="${DepositStatusDict}" var="item" varStatus="status"><option value="${item.label}">${item.label}</option></c:forEach>
          </select>
        </div>
        <div class="form-group">
        <button class="btn btn-primary"
        type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button></div></form></div></div>
        <div class="panel">
        <div class="panel-body">
        <div class="table-responsive"><table id="searchTable">
                <tr>
                    <th w_index="recordID" w_hidden="true">ID</th>
                    <th w_render="render_orderID" width="5%;">相关订单</th><%-- w_sort="serverCode,asc" --%>
                    <th w_index="SN" width="5%;" >机身码</th>
                    <th w_render="render_customerName" width="10%;" >客户</th>
                    <th w_index="phone" width="10%;" >电话</th>
                    <th w_render="render_dealAmount" width="10%;" >押金总额</th>
                    <th w_index="status" width="10%;" >申请状态</th>
                    <th w_index="expressName" width="10%;" >快递名</th>
                    <th w_render="render_expressNum" width="10%;">快递单号</th>
                    <th w_index="creatorDate" width="10%;">申请时间</th>
                    <th w_index="remark" width="10%;">备注/反馈</th>
                    <th w_render="operate">操作(红色表示不可操作)</th>
                </tr>
        </table></div>
        </div>
        <div style="padding-left:10px; padding-bottom:20px;"><p><strong>说明：</strong></p>
        <p>1. 表格中的操作之后，操作结果可见刷新页面之后更新的“操作结果提示”处的文字。见上面</p>
        </div>
        </div>

</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    $("#import_form").validate_popover({
        rules:{
            'file':{ required:true },
        },
        messages:{
            'file':{required:"请选择要导入的excel文件！"},
        },
        submitHandler: function(form){
            form.submit();
        }
    });


        function revertodata(){
        	var devicesn = document.getElementById('device_sn').value.trim();
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
				url : "<%=basePath %>device/revertTo?device_sn="+document.getElementById('device_sn').value,
				dataType : "html",
				success : function(data) {
				    msg = data;
	                            document.getElementById("backmessage").innerHTML=msg;
							    easy2go.toast('info', msg);
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
                 url:'<%=basePath %>device/deposit/getPageRecord',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false
             });
         });

          function render_orderID(record, rowIndex, colIndex, options) {
              return '<a title="相关订单详情" href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID=' + record.orderID + '"><span>查看</span></a>';
           }
          function render_customerName(record, rowIndex, colIndex, options) {
              return '<a title="客户详情" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid=' + record.customerID + '"><span>' + record.customerName + '</span></a>';
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
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath %>device/revertTo?from=deposit&device_sn=' + record.SN +'"><span class="glyphicon glyphicon-edit">归还设备</span></a>'
                 +'<a class="btn btn-danger btn-xs" title="归还设备成功后才能退押金" href="javascript:void(0);"><span class="glyphicon glyphicon-remove">退还押金</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath %>device/deposit/edit?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>'
                 + '</div>';
        	 } else if(record.status == '等待处理' && record.ifReturn == '是') {
        		 return '<div class="btn-toolbar">'
                 +'<a class="btn btn-success btn-xs" title="已归还设备成功, 可以处理退押金了" href="javascript:void(0);"><span class="glyphicon glyphicon-edit">成功归还</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath %>device/deposit/withdraw?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-share">退还押金</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath %>device/deposit/edit?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>'
                 + '</div>';
        	 } else if(record.status == '退款成功') {
                 return '<div class="btn-toolbar">'
                 +'<a class="btn btn-success btn-xs" title="押金退款成功" href="javascript:void(0);"><span class="glyphicon glyphicon-edit">已退押金</span></a>'
                 +'<a class="btn btn-primary btn-xs" href="<%=basePath %>device/deposit/edit?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>'
                 + '</div>';
        	 } else if(record.status == '申请异常') {<%-- 申请异常时设备应该未归还 目前同第一个 等待处理+未归还 一样的操作 --%>
	             return '<div class="btn-toolbar">'
	             +'<a class="btn btn-primary btn-xs" href="<%=basePath %>device/revertTo?from=deposit&device_sn=' + record.SN +'"><span class="glyphicon glyphicon-edit">归还设备</span></a>'
	             +'<a class="btn btn-danger btn-xs" title="处理好异常后再操作归还和处理押金等" href="javascript:void(0);"><span class="glyphicon glyphicon-remove">异常反馈</span></a>'
	             +'<a class="btn btn-primary btn-xs" href="<%=basePath %>device/deposit/edit?recordID=' + record.recordID +'"><span class="glyphicon glyphicon-edit">详情及反馈</span></a>'
	             + '</div>';
             }
         }

    </script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
  </body>
</html>