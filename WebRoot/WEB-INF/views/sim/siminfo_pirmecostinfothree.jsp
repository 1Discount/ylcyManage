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
<title>SIM卡成本详情页面-EASY2GO ADMIN</title>
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
<style type="text/css">
/*         #searchTable tr{height:40px;} */
</style>
</head>
<body>

    <section id="container">
        <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
        <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

        <SECTION id="main-content">
            <SECTION class="wrapper" style="">
                <DIV class="col-md-12">
                    <DIV class="panel hidden">
                       <DIV class="panel-body ">
                           <FORM class="form-inline " id="leibie" method="get" action="#">
                           
                           	   <div class="form-group ">
                                    <LABEL class="inline-label">国家：</LABEL>
                                    <input type="text" id="MCC" name="MCC" value="${MCC }" class="form_datetime form-control"/>
                               </div>
                           
                               <div class="form-group">
                                    <LABEL class="inline-label">时间段：</LABEL>
                                    <input type="text" id="begindate" name="begindate"  data-date-format="YYYY-MM-DD" class="form_datetime form-control"/>
                               </div>
                               
                               <div class="form-group">
                                    <LABEL class="inline-label">-</LABEL>
                                    <input type="text" id="enddate" name="enddate"  data-date-format="YYYY-MM-DD" class="form_datetime form-control"/>
                               </div>
                               
                               <div class="form-group">
                                    <LABEL class="inline-label">套餐类型</LABEL>
                                    <input type="text" id="planType" name="planTypePar"  value="${planTypePar}" class="form-control"/>
                               </div>
                               
                               <DIV class="form-group">
                                   <BUTTON class="btn btn-primary" type="button"
                                       onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
                               </DIV>
                               
                           </FORM>
                       </DIV>
                   </DIV>
                   <DIV class="panel">
                     <DIV class="panel-heading">详情页面</DIV>
                     <DIV class="panel-body">
                         <DIV class="table-responsive">
                             <TABLE id="searchTable">
                                 <TR>
                                     <TH w_index="IMSI" width="8%"><b>IMSI</b></TH>
                                     <TH w_index="planType" width="7%"><b>套餐类型</b></TH>
                                     <TH w_render="userDays" width="3%"><b>使用天数</b></TH>
                                     <TH w_index="countryName" width="5%"><b>使用国家</b></TH>
                                     <TH w_index="assignedSN" width="35%"  w_length="999999999999999999999999"><b>使用机身码</b></TH>
                                     <TH w_index="totalData" width="5%"><b>总流量</b></TH>
                                     <TH w_index="totalFlow" width="5%"><b>使用流量</b></TH>
                                     <TH w_index="totalPrice" width="5%"><b>价格</b></TH>
                                     <TH w_index="primeCost" width="5%"><b>成本(元)</b></TH>
                                     
                                 </TR>
                             <!--     <tfoot>
							        <tr>
							       <td class="aggLabel">Count:</td>
							            <td w_agg="count" class="aggValue"></td>
							            <td class="aggLabel">Count(XH*ID):</td>
							            <td w_agg="custom,countXhMultId" class="aggValue"></td>
							            <td class="aggLabel">Max(NUM):</td>
							            <td w_agg="max,NUM" class="aggValue"></td>
							            <td class="aggLabel">Sum(NUM):</td>
							            <td w_agg="sum,NUM" class="aggValue"></td>
							        </tr>
							    </tfoot> -->
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
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <script type="text/javascript">
	    var gridObj;
	    $(function(){
	         $("#begindate").datetimepicker({
	      		pickDate: true,                 
	      		pickTime: true,                 
	      		showToday: true,                 
	      		language:'zh-CN',                 
	      		defaultDate: '${begindate}',                
	      	});
	         
	         $("#enddate").datetimepicker({
	       		pickDate: true,                 
	       		pickTime: true,                 
	       		showToday: true,                 
	       		language:'zh-CN',                 
	       		defaultDate: '${enddate}',              
	       	});
	    });
	    
	    $(function(){
	       gridObj = $.fn.bsgrid.init('searchTable',{
	           url:'<%=basePath%>sim/siminfo/getPageSiminfo_pirmeCostInfoThree',
	           otherParames:$('#leibie').serializeArray(),
	           pageSizeSelect: true,
	           pageSize: 20,
	           lineWrap:true,
	           //pageAll:true,
	           pageSizeForGrid:[10,20,30,50,100],
	           multiSort:true,
	           additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                   if(parseSuccess){}
	           },
			   extend: {
	                settings:{
	                    supportColumnMove: true 
	                }
		       }
	       });
	   });
	    
	 $.bsgrid.forcePushPropertyInObject($.fn.bsgrid.defaults.extend.renderPerRowMethods, 'extend_render_per_row', function(record, rowIndex, trObj, options){
			 trObj.children('td:nth-child(5)').css({'text-align':'left'});
	 });
	 
	
	 
	 function userDays(record, rowIndex, colIndex, options){
		 
		return '<A  href="<%=basePath%>sim/siminfo/tosiminfo_pirmecostbyimsiuserdays?IMSI='+record.IMSI+'&'+$('#leibie').serialize()+'">'+record.userDays+'</A>';
	 }
    </script>

    <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
        <jsp:param name="matchType" value="level2" />
    </jsp:include>
</body>
</html>