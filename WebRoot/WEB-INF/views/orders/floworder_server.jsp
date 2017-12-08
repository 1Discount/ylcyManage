<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>流量订单服务端口跳转</title>
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
	<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
	<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

	<SECTION id="main-content"> <SECTION class="wrapper">
	<DIV class="col-md-12">
		<DIV class="panel">
			<DIV class="panel-heading">
				<b>订单跳转</b>
			</DIV>
			<DIV class="panel-body">
				<lable style=" display: inline-block; font-weight: 700;  float: left;font-size:14px; margin-bottom: 5px;height:34px; line-height:34px; max-width: 100%;	">平台：</lable>
				<select class="form-control" style="width: 179px; float: left;" name="logistics" id="logistics">
					<option value="">--请选择平台--</option>
					<c:forEach items="${dictionaries }" var="dict">
						<option value="${dict.value }">${dict.label}</option>

					</c:forEach>


				</select>
				<lable style=" display: inline-block; float: left; font-weight: 700;font-size:14px;line-height:34px; max-width: 100%; margin-bottom: 5px;max-width: 100%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SN：</lable>
				<!-- SN -->
				<input class="form-control" style="width: 200px; float: left;" type="text" id="SN">
				<br />
				<br />
				<br />
				<button type="button" class="btn btn-primary" onclick="jump();">确定跳转</button>
			</DIV>
		</DIV>

		<DIV class="panel">
			<DIV class="panel-heading">
				<b>生成测试订单</b>
			</DIV>
			<DIV class="panel-body">
				<FORM id="add_test_floworder" action="<%=basePath%>orders/flowdealorders/addTestOrderexcel" enctype="multipart/form-data" method="post">
					<DIV class="form-group">
						<LABEL for="client_phone">
							录入SN:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</LABEL>
						<label class="radio-inline" onclick="$('#sn_list').css({'display':'block'});$('#file').css({'display':'none'});">
							<input type="radio" name="type" checked="checked" value="手动录入">
							<span>手动录入</span>
						</label>
						
						<label class="radio-inline" onclick="$('#file').css({'display':'block'});$('#sn_list').css({'display':'none'});">
							<input type="radio" name="type" value="导入excel">
							<span>导入excel&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="color:red;">1.手动输入sn时，请输入sn后六位,多个用/隔开,例如001234/130029 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.excel导入时请使用excel2003,并输入长度为15位的SN</a></span>
						</label>
						<br />
						<br />
					 	<INPUT id="sn_list" class="client form-control" name="SNlist" maxLength="1000" type="text" data-popover-offset="0,8" style="margin-left: 4%" placeholder="请输入SN后6位,多个用/隔开,例如001234/130029"> 
						<input type="file" id="file" name="file" id="file" data-popover-offset="0,8" required class="form-control" style="border:none;padding:0px; width:100%; display: none; margin-left: 4%;">
						<br />
					</DIV>
					<DIV class="form-group">
						<LABEL for="client_phone" class="col-xs-1 control-label">
							天数:
							
						</LABEL><div class="col-md-3">
						<INPUT id="flow_days" class="client form-control" name="days" maxLength="20" type="text"  value="1">
						</div>
					</DIV>
					
					<br />
					<br />
					<br />
					<DIV class="form-group">
						<LABEL for="client_phone">
							是否自动录入设备:
							<SPAN class="red" id="tip_phone"></SPAN>
						</LABEL>
						<label class="radio-inline">
							<input type="radio" name="ifAddDevcie"  value="是">
							<span>是</span>
						</label>
						<label class="radio-inline">
							<input type="radio" name="ifAddDevcie" checked="checked" value="否">
							<span>否</span>
						</label>
					</DIV>

					<DIV class="form-group">
						<LABEL for="client_phone" >
							是否工厂测试:
							<SPAN class="red" id="tip_phone"></SPAN>
						</LABEL>
						<label class="radio-inline">
							<input type="radio" name="ifLimitSpeed" checked="checked" value="1">
							<span>是</span>
						</label>
						<label class="radio-inline">
							<input type="radio" name="ifLimitSpeed"  value="0">
							<span>否</span>
						</label>
					</DIV>
					
					<DIV class="btn-toolbar">
						<BUTTON id="addOrder" class="btn btn-primary" type="button" onclick="addTestOrder()">生成测试单</BUTTON>
						<BUTTON class="btn btn-default" type="button" onclick="clareTestOrder()">清除全部测试单</BUTTON>
					</DIV>
				</FORM>
			</DIV>
		</DIV>
		<DIV class="panel">
            <DIV class="panel-body">
                <FORM class="form-inline" id="leibie" method="get" action="#">
                    <DIV class="form-group">
                        <LABEL class="inline-label">SN：</LABEL> 
                        <input type="text" id="sn" name="SN" class="form-control"/>
                    </DIV>
                    <div class="form-group">
                         <LABEL class="inline-label">开始时间：</LABEL>
                         <input type="text" id="begindateForQuery" name="begindateForQuery" data-popover-offset="0,8" class="form_datetime form-control"/>-
                         <input type="text" id="enddate" name="enddate" data-popover-offset="0,8" class="form_datetime form-control"/>
                    </div>
                    <DIV class="form-group">
                        <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
                        <BUTTON class="btn btn-primary" type="button" onclick="deleteCheck()">删除选中</BUTTON>
                        <BUTTON class="btn btn-primary" type="button" onclick="deleteBySearch()">删除条件下所有</BUTTON>
                    </DIV>
                </FORM>
                <form id="testc"></form>
            </DIV>
        </DIV>
		<DIV class="panel">
            <DIV class="panel-heading">
                <b>测试订单列表</b>
            </DIV>
            <DIV class="panel-body">
                <TABLE id="searchTable">
                   <TR>
                       <TH w_render="check" width="5%"><b><input type="checkbox" id="checkAll"></b></TH>
                       <TH w_index="SN" width="19"><b>SN</b></TH>
                       <TH w_render="panlUserDate" width="19%"><b>预约使用时间</b></TH>
                       <TH w_render="flowExpireDate" width="19%"><b>流量到期时间</b></TH>
                       <TH w_index="orderStatus" width="19%"><b>订单状态</b></TH>
                       <TH w_index="creatorDate" width="19%"><b>创建时间</b></TH>
                   </TR>
               </TABLE>
            </DIV>
        </DIV>
	</DIV>
	</SECTION></SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript">
	    var gridObj;
		$(function(){
			if('${count}'!=''){
				if('${count}'=='0'){
					easy2go.toast("warn",'${count}'+'条订单生成成功');
				}else{
					easy2go.toast("info",'${count}'+'条订单生成成功');
				}
			}
			$(".form_datetime").datetimepicker({
	           format: 'YYYY-MM-DD HH:mm:ss',
	           pickDate: true,     //en/disables the date picker
	           pickTime: true,     //en/disables the time picker
	           showToday: true,    //shows the today indicator
	           language:'zh-CN',   //sets language locale
	           defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
	        });
			gridObj = $.fn.bsgrid.init('searchTable',{
	            url:'<%=basePath%>orders/flowdealorders/pageTestOrder',
	            // autoLoad: false,
	            pageSizeSelect: true,
	            pageSize: 8,
	            pageSizeForGrid:[8,20,30,50,100],
	            multiSort:true
	        });
	        
	        $("#checkAll").click(function(){
	            var selectAll = document.getElementById("checkAll");
	            if(selectAll.checked){
	                 $("input[name=flowDealID]").prop("checked",true);
	            }else{
	                 $("input[name=flowDealID]").prop("checked",false);
	            }
	        });
		});
		
		function jump(){
			var sn = $("#SN").val();
			var value = $("#logistics").val();
			  $.ajax({
		  		 type:"POST",
		  		 url:'<%=basePath%>/orders/flowdealorders/selectFlowdeal?SN='+sn+'&value='+value+'',		  		
		  		 dataType:'json',
		  		 success:function(data){
		  			if(data=="0"){
		  				easy2go.toast('warn',"没有该设备订单");
		  			}else if(data=="1"){
		  				easy2go.toast('warn',"跳转成功");
		  			}else if(data=="-1"){
		  				easy2go.toast('warn',"请选择平台");
		  			}else if(data=="-2"){
		  				easy2go.toast('warn',"请输入SN");
		  			}
		  		 }
			   }); 
		}
		
		function addTestOrder(){
			var type=  $("input[name='type']:checked").val();
			if(type=="手动录入"){
				if($("#sn_list").val()==''){
					easy2go.toast('warn',"请输入SN");
					return;
				}
				$.ajax({
			  		 type:"POST",
			  		 url:'<%=basePath%>orders/flowdealorders/addTestOrder',
			  		 data:$("#add_test_floworder").serialize(),
			  		 dataType:'html',
			  		 success:function(data){
			  			 data=parseInt(data);
			  			if(data<0){
			  				easy2go.toast('warn',"生成失败!");
			  			}else if(data>=0){
			  				easy2go.toast('warn',data+"条测试单生成成功!");
			  			}else if(data==-1){
			  				easy2go.toast('warn',"SN为空");
			  			}
			  		 }
				   });
			}else if(type=="导入excel"){
				$("#add_test_floworder").submit();
			}
		}
		
		function check(record, rowIndex, colIndex, options){
			return "<input type='checkbox' name='flowDealID' value='"+record.flowDealID+"'>";
		}
		
		//删除选中的测试单
		function deleteCheck()
		{
			var l=gridObj.getCheckedRowsRecords().length;
	       if(l==0)
	       {
	         easy2go.toast('warn',"请选择你要删除的记录");
	         return;
	       }
	       //获取到选中的订单ID
	       var flowDealID="";
	       for(var i=0;i<l;i++)
	       {
	         if(i==0)
	         {
	        	 flowDealID+=gridObj.getCheckedRowsRecords()[i].flowDealID;
	         }
	         else
	         {
	        	 flowDealID+=","+gridObj.getCheckedRowsRecords()[i].flowDealID;
	         }
	       }

	       $.ajax({
	            type:"POST",
	            url:"<%=basePath %>orders/flowdealorders/deleteCheckTestOrder?flowDealID="+flowDealID,
	            success:function(data){
                    result = jQuery.parseJSON(data);
	                if (result.code == 0) { // 成功删除
	                   easy2go.toast('info', result.msg);
                       gridObj.refreshPage();
	                } else {
	                   easy2go.toast('error', result.msg);
	                }
	            }
	       });
		}
		
		//根据查询条件删除测试单
		function deleteBySearch()
		{
			$.ajax({
                type:"POST",
                url:"<%=basePath %>orders/flowdealorders/deleteBySearchTestOrder",
                data:$('#leibie').serializeArray(),
                success:function(data){
                	result = jQuery.parseJSON(data);
                    if (result.code == 0) { // 成功删除
                       easy2go.toast('info', result.msg);
                       gridObj.refreshPage();
                    } else {
                       easy2go.toast('error', result.msg);
                    }
                }
           });
		}
		
		//清除测试单
		function clareTestOrder(){
			$.ajax({
		  		 type:"POST",
		  		 url:'<%=basePath%>/orders/flowdealorders/claerTestOrder',
				 dataType : 'html',
				 success : function(data) {
					data = parseInt(data);
					easy2go.toast('info', data + "条数据已清理!");
				 }
			});
		}
		
	   function panlUserDate(record, rowIndex, colIndex, options){

           if(record.panlUserDate!=null && record.panlUserDate!=""){
               
               var dateStr=record.panlUserDate;
               
               var temp=dateStr.substring(0,dateStr.indexOf("."));
               return temp;
               
               
           }else{
               
               return "";
           }
       }
        
       function flowExpireDate(record, rowIndex, colIndex, options){

           if(record.flowExpireDate!=null && record.flowExpireDate!=""){
               
               var dateStr=record.flowExpireDate;
               
               var temp=dateStr.substring(0,dateStr.indexOf("."));
               return temp;
               
               
           }else{
               
               return "";
           }
       }
       
       $(document).keydown(function(event){
           if(event.keyCode == 13){//绑定回车
               $("#sn").blur();
               $("#begindateForQuery").blur();
               $("#enddate").blur();
               gridObj.options.otherParames =$('#leibie').serializeArray();
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