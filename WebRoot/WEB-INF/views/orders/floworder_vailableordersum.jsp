<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" +

			request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>流量订单服务端口跳转</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-

scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
	<style type="text/css">
		a.ahover-tips{ background: url(/ylcyManage/static/images/question2.png) no-repeat; margin-left: -50px;}
	</style>
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
			<DIV class="panel-body">
				<form class="form-inline" id="searchForm" role="form" method="get"
					action="#">
					<div class="form-group">
						<label class="inline-label">国 家：</label><input
							class="form-control" name="countryName" type="text"/>
					</div>
					<DIV class="form-group">
									<LABEL class="inline-label">　　　　时间段：</LABEL> <INPUT id="order_creatorDatebegin" value="" class="form-control form_datetime" name="endtime" type="text" data-date-format="YYYY-MM-DD">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL> <INPUT id="order_creatorDateend" class="form-control form_datetime" value="" name="begintime" type="text" data-date-format="YYYY-MM-DD">
								</DIV>
					
					<button class="btn btn-primary" type=	"button"
							onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button>
							
					<!-- <div class="form-group" style="margin-left: 150px; margin-top: -15px;">
						<i style="float:left; font-weight:900; color:#333; font-size:14px; background: #fff; " class="btn  "><i style="font-size: 16px;">*温馨提示：</i> 1. 表格中红色的数据代表<span style="">　(可使用　+　使用中　&lt;　用户订单数) </span>　即需要准备SIM卡</i>
							<br/><i style="float:left;  line-height:15px; margin-left:90px; font-weight:900; color:#333; font-size:14px; background: #fff; " class="btn  ">2. 当单元格内容超过列宽时，鼠标悬停此单元格上面将会显示详细信息</i>
					</div> -->
				</form>	
				
			</DIV>
			<DIV class="panel-body">
				<table id="searchTable">
					<tr classs="extend_render_per_row">
						 <th w_index="countrName"  width="10%;">国家</th>
					 	 <th w_index="flowdealCount" sum="12" id="flowCount" width="10%;">用户订单数</th> 
						 <th maxLength="" w_index="SN" w_length="9999999999999"  id="SN" width="10%;">订单SN</th> 
					</tr>
					   <tfoot>
					        <tr>
					         
          						  <td class="aggLabel">合计</td>
          						  <td  w_agg="sum,flowdealCount" class="aggLabel"></td>
          						  <td class="aggLabel">----</td>
          						  
					        </tr>
					    </tfoot>
				</table>
			</DIV>
		</DIV>
	</DIV>
	</SECTION></SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/jquery.zclip.min.js"></script>
	<SCRIPT type="text/javascript">
	  //自定义列
     function operate(record, rowIndex, colIndex, options) {
		  var SN=record.SN;
		 // alert(SN);
		   var j=(record.SN).split("/").length;
		    for(var i=0;i<j-1;i++){
				SN=SN.replace("/","w");
		   	}   
         return '<a href="#" id="'+rowIndex+'" onmousemove="copy('+rowIndex+',\''+SN+'\')" class="glyphicon  btn-primary btn-xs">复制SN</a>';
	  }
	 $.bsgrid.forcePushPropertyInObject($.fn.bsgrid.defaults.extend.renderPerRowMethods, 'extend_render_per_row', function(record, rowIndex, trObj, options){
		/*  var c=parseInt(record.availableing)+parseInt(record.available);
		 var b=parseInt(record.flowdealcount); */
 		var c=parseInt(record.useing)+parseInt(record.available);
		 
		 var b=parseInt(record.flowdealCount);
		 if(c<b){
			 //trObj.find('td').css('background-color', 'pink');
			 trObj.children('td').css('background-color', 'pink');
			// trObj.children('td').last().css('background-color', 'none');
		 }
       		
   });
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
    	  $("#order_creatorDatebegin").datetimepicker({
				pickDate : true, //en/disables the date picker
				pickTime : true, //en/disables the time picker
				showToday : true, //shows the today indicator
				language : 'zh-CN', //sets language locale
				defaultDate : moment(), //sets a default date, accepts js dates, strings and moment objects
			});
			$("#order_creatorDateend").datetimepicker({
				pickDate : true, //en/disables the date picker
				pickTime : true, //en/disables the time picker
				showToday : true, //shows the today indicator
				language : 'zh-CN', //sets language locale
				defaultDate : moment().add(1, 'days'), //sets a default date, accepts js dates, strings and moment objects
			});
          $(function(){
        
             gridObj = $.fn.bsgrid.init('searchTable',{
                url:'<%=basePath%>flowplan/countryinfo/vailableorder',
				pageSizeSelect : true,
				pageAll:true,
				additionalAfterRenderGrid:function(){
					$(".aggLabel").css({'background':'#ccc','font-size':'16px','font-weight':'200'});
				},
				 extend: {
		                settings:{
		                    supportColumnMove: true //if support extend column move, default false
		                }
		            }
			});

			
		});
          function copy(t,sn){
      		//alert(t+":::"+sn);
      			 // alert(SN);
      			   var j=sn.split("w").length;
      			    for(var i=0;i<j-1;i++){	
      					sn=sn.replace("w","/");
      			   	}   
      		 $("#"+t).zclip({
      			path:'<%=basePath%>static/js/ZeroClipboard.swf',
      	 		copy:sn+'',
      	 		afterCopy: function(){
      	 			//$("<span id='msg'/>").insertAfter($('#copy_input')).text('复制成功').fadeOut(2000);
      	 			easy2go.toast('info', "复制成功!");
      	 		}
      	 	});
      	} 
	</script>
	    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>