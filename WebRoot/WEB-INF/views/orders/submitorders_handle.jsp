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
<title>提工单管理-流量运营中心</title>
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
                    <DIV class="panel">
                       <DIV class="panel-body">
                           <FORM class="form-inline" id="leibie" method="get" action="#">
                               <DIV class="form-group">
                                   <LABEL class="inline-label">问题类型：</LABEL> 
                                   <select id="problemType_select" name="problemType" class="form-control">
                                       <option value="">请选择</option>
                                       <c:forEach items="${typeList}" var="dic">
                                           <option value="${dic.label}">${dic.label}</option>
                                       </c:forEach>
                                   </select>
                               </DIV>
                               <DIV class="form-group">
                                   <LABEL class="inline-label">紧急程度：</LABEL> <select
                                       class="form-control" name="urgency"
                                       id="urgency_select" style="width: 150px;">
                                       <option value="">请选择</option>
                                       <option value="普通">普通</option>
                                       <option value="优先">优先</option>
                                       <option value="紧急">紧急</option>
                                   </select>
                               </DIV>
                               <!-- <DIV class="form-group">
                                   <LABEL class="inline-label">状态：</LABEL> <select
                                       class="form-control" name="orderStatus"
                                       id="status_select" style="width: 150px;">
                                       <option value="">请选择</option>
                                       <option value="待解决">待解决</option>
                                       <option value="已解决">已解决</option>
                                       <option value="已打回">已打回</option>
                                   </select>
                               </DIV> -->
                               <DIV class="form-group">
                                   <LABEL class="inline-label">提出人：</LABEL>
                                   <input id="introducerName_select" name="introducerName" class="form-control"/>
                               </DIV>
                               <div class="form-group">
                                    <LABEL class="inline-label">提交时间：</LABEL>
                                    <input type="text" id="begainTime" name="begainTime" data-popover-offset="0,8" class="form_datetime form-control"/>-
                                    <input type="text" id="endTime" name="endTime" data-popover-offset="0,8" class="form_datetime form-control"/>
                               </div>
                               <DIV class="form-group">
                                   <BUTTON class="btn btn-primary" type="button"
                                       onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
                               </DIV>
                           </FORM>
                           <form id="testc"></form>
                       </DIV>
                   </DIV>
                   <DIV class="panel">
                     <DIV class="panel-heading">工单列表</DIV>
                     <DIV class="panel-body">
                         <DIV class="table-responsive">
        
                             <TABLE id="searchTable">
                                 <TR>
                                  <TH w_render="creatorDate" width="6%"><b>提交时间</b></TH>
                                     <TH w_render="SN" width="8%"><b>机身码</b></TH>
                                     
                                     <TH w_render="urgency" width="4%"><b>紧急程度</b></TH>
                                     <TH w_index="problemType" width="6%"><b>类型</b></TH>
                                     <TH w_index="description" w_length="10" width="8%"><b>问题描述</b></TH>
                                     <TH w_index="introducerName" width="4%"><b>提交人</b></TH>
                                     
                                     <TH w_index="solveResultDesc" w_length="10" width="13%"><b>处理结果</b></TH>
                                     <TH w_index="correctiveMeasure" w_length="10" width="13%"><b>根本原因</b></TH>
                                     <TH w_index="preventiveMeasure" w_length="10" width="13%"><b>预防措施</b></TH>
                                     <TH w_index="solveRemark" w_length="10" width="13%"><b>点评</b></TH>
                                     <TH w_render="solveTime" width="6%"><b>处理时间</b></TH>
                                     <!-- <TH w_index="ordersRemark" w_length="20" width="8%"><b>工单备注</b></TH> -->
									 <!-- <TH w_index="designeeName" width="8%"><b>指派人</b></TH> -->
									 <!-- <TH w_render="orderStatus" width="6%"><b>状态</b></TH> -->
									 <!-- <TH w_index="handleName" width="8%"><b>处理人</b></TH> -->
									 <!-- <TH w_index="callBackReason" w_length="20" width="6%"><b>打回原因</b></TH> -->
									 <!-- <TH w_index="solveTime" width="6%"><b>解决时间</b></TH> -->
									 <!-- <TH w_index="solveResultDesc" w_length="20"  width="6%"><b>解决结果</b></TH> -->
									 <!-- <TH w_index="solveRemark" w_length="30" width="6%"><b>解决备注</b></TH> -->
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
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <script type="text/javascript">
        
          
    var gridObj;
    $(function(){
        
       $(".form_datetime").datetimepicker({
           format: 'YYYY-MM-DD HH:mm:ss',
           pickDate: true,     //en/disables the date picker
           pickTime: true,     //en/disables the time picker
           showToday: true,    //shows the today indicator
           language:'zh-CN',   //sets language locale
           defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
       });
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>submitorders/getpage1',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 20,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
       
   });
    
    
    //状态
   function orderStatus(record, rowIndex, colIndex, options){
       if(record.orderStatus=="已解决"){
           return '<a class="btn btn-success btn-xs" onclick="return;"><span>已解决</span></a>';
       }else if(record.orderStatus=="已打回"){
           return '<a class="btn btn-warning btn-xs" onclick="return;"><span>已打回</span></a>';
       }else if(record.orderStatus=="待解决"){
           return '<a class="btn btn-danger btn-xs" onclick="return;"><span>待解决</span></a>';
       }
   }
   
    //紧急程度
   function urgency(record, rowIndex, colIndex, options){
       if(record.urgency=="普通"){
           return '<a class="btn btn-success btn-xs" onclick="return;"><span>普通</span></a>';
       }else if(record.urgency=="优先"){
           return '<a class="btn btn-warning btn-xs" onclick="return;"><span>优先</span></a>';
       }else if(record.urgency=="紧急"){
           return '<a class="btn btn-danger btn-xs" onclick="return;"><span>紧急</span></a>';
       }
   }
    
   function SN(record, rowIndex, colIndex, options){
	   var time = record.creatorDate;
       time = time.substr(0,10);
   	   return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj='+time+'">'+record.SN+'</A>';
   }

   //要求解决截止时间
   function creatorDate(record, rowIndex, colIndex, options){

       /* if(record.creatorDate!=null && record.creatorDate!=""){
           
           var dateStr=record.creatorDate;
           
           String temp=dateStr.slice(0,dateStr.indexOf("."));
           return temp.subString(0,temp.length-3);
           
           
       }else{
           
           return "";
       } */
       return record.creatorDate;
   }
   
   //提交时间
   function creatorDate(record, rowIndex, colIndex, options){
       return record.creatorDate.substring(5,record.creatorDate.length-5);
   }
   
   //处理时间
   function solveTime(record, rowIndex, colIndex, options){

	   return record.solveTime.substring(5,record.solveTime.length-5);
   }
 
   function operate(record, rowIndex, colIndex, options) {
       return '<A class="btn btn-primary btn-xs" href="#">'
       +'<SPAN class="glyphicon glyphicon-edit" onclick="handle('+rowIndex+')">处理</SPAN></A>&nbsp;';
   }

   function handle(index) {
	   var record= gridObj.getRecord(index);
       bootbox.dialog({
           title : "处理工单",
           message : '<DIV class="">'
                   +'<DIV class="">'
                   +'<DIV class="panel-body">'
                   +'<FORM id="sub_form" class="form-horizontal" role="form"'
                   +'method="post" action="" autocomplete="off">'
                   +'<input type="hidden" name="submitOrdersID" value="'+record.submitOrdersID+'">'
                   +'<table width="90%" cellspacing="2px">'
                   +'<tr style="height: 40px;">'
                   +'<td style="text-align:right;" width="100"><b>处理结果：</b></td>'
                   +'<td><input type="text" id="solveResultDesc" name="solveResultDesc" value="'+record.solveResultDesc+'" data-popover-offset="0,8" class="form-control"></td>'
                   +'</tr>'
                   +'<tr style="height: 40px;">'
                   +'<td style="text-align:right;" width="100"><b>根本原因：</b></td>'
                   +'<td><input type="text" id="correctiveMeasure" name="correctiveMeasure" value="'+record.correctiveMeasure+'" data-popover-offset="0,8" class="form-control"></td>'
                   +'</tr>'
                   +'<tr style="height: 40px;">'
                   +'<td style="text-align:right;" width="100"><b>预防措施：</b></td>'
                   +'<td><input type="text" id="preventiveMeasure" name="preventiveMeasure" value="'+record.preventiveMeasure+'" data-popover-offset="0,8" class="form-control"></td>'
                   +'</tr>'
                   +'<tr style="height: 40px;">'
                   +'<td style="text-align:right;" width="100"><b>点评：</b></td>'
                   +'<td><input type="text" id="solveRemark" name="solveRemark" value="'+record.solveRemark+'" data-popover-offset="0,8" class="form-control"></td>'
                   +'</tr></table></FORM></DIV></DIV></DIV>',
           buttons : {
               cancel : {
                   label : "取消",
                   className : "btn-default",
                   callback : function() {
                       
                   }
               },
               success : {
                   label : "提交",
                   className : "btn-success byte-unit-picker-button-ok",
                   callback : function() {
                      $.ajax({
                          type:"POST",
                          url:"<%=basePath %>submitorders/save",
                           data : $('#sub_form').serialize(),
                           success : function(data) {
                               result = jQuery.parseJSON(data);
                               if (result.code == 0) { // 成功保存
                                   easy2go.toast('info',result.msg);
                                   gridObj.refreshPage();
                               } else {
                                   easy2go.toast('error',result.msg);
                               }
       
                           }
                       });
                   }
               }
           }
       });
   }
   
    </script>

    <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
        <jsp:param name="matchType" value="level2" />
    </jsp:include>
</body>
</html>