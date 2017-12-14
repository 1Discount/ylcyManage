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
    <title>优惠劵管理-流量运营中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
    <style type="text/css">
/*         #searchTable tr{height:40px;} */
    </style>
    <script>
    function getSmbo(SelObj){  
    	var val=SelObj.value;  
    	if(val!=-1){      
    		var smboObj=document.getElementById("couponValue");  
    		/*上来清空小版块的选项*/             
    		for(var i=smboObj.options.length;i>=0;i--){ 
    			smboObj.remove(i);             
    			}             
    		/*添加一个待选项*/       
    		smboObj.options.add(new Option("请选择优惠折扣","-1")); 
    		if(val=="满减券"){               
    			smboObj.options.add(new Option("满128减5元","128-5"));   
    			smboObj.options.add(new Option("满188减10","188-10"));   
    			smboObj.options.add(new Option("满288减20","288-20"));  
                smboObj.options.add(new Option("满488减50","488-50"));   
                }else if(val=="现金券"){
                	smboObj.options.add(new Option("5","5")); 
                	smboObj.options.add(new Option("10","10"));  
                	smboObj.options.add(new Option("20","20")); 
                	smboObj.options.add(new Option("40","40")); 
                	smboObj.options.add(new Option("100","100")); 
                	}
    		}   
    	}
    </script>
  </head>
 <body>
   
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
     
 <SECTION id="main-content">
<SECTION class="wrapper">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading">新增优惠劵</DIV>
<DIV class="panel-body">
<!-- ======================================================================================== -->
<DIV class="col-md-12">
        <DIV class="panel" style="background-repeat:repeat;">
           <DIV class="panel-body">
               <FORM id="coupon_form" class="form-horizontal" role="form" method="post" action="" autocomplete="off">
               <input class="form-control" id="testdictID" name="testdictID" value="${dictionary.dictID}" type="hidden"/>
                   <table style="width:90%;cellspacing:10px;cellpadding:5px;"> 
                       <tr style="height:40px;">
                          <td style="text-align:right;"><b>优惠活动标题：</b></td>
                          <td><input class="form-control" type="text" id="couponTitle" name="couponTitle" value="${dictionary.value}"/></td>
                          <td style="text-align:right;"><b>优惠劵个数：</b></td>
                          <td><input class="form-control" type="text" id="couponCount" name="couponCount" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();" value="${dictionary.label}"/></td>
                       </tr>
                       <tr style="height:40px;">
                          <td style="text-align:right;"><b>优惠类型：</b></td>
                          <td>
<!--                              <select class="form-control" type="text" id="couponDiscountStatus" name="couponDiscountStatus"> -->
<!--                                 <option>满减券</option> -->
<!--                                 <option>现金券</option> -->
<!--                              </select> -->

<select name="couponDiscountStatus" id="couponDiscountStatus" class="form-control"  onchange="getSmbo(this);" style="margin-left:10px;">    
  <option value="-1">请选择优惠类型</option>       
  <option value="满减券">满减券</option>    
  <option value="现金券">现金券</option>   
</select>

                          </td>
                          <td style="text-align:right;"><b>优惠折扣：</b></td>
                          <td>
<%--                             <input class="form-control" type="text" id="couponValue" name="couponValue" value="${dictionary.sort}"/>（格式：条件-折扣，例如：满100减20，100-20） --%>
                               <select class="form-control" id="couponValue" name="couponValue">    
                                   <option value="-1">请选择优惠折扣</option>
                               </select>
                          </td>
                       </tr>
                      <tr  style="height:40px;">
                          <td style="text-align:right;"><b>开始时间：</b></td>
                          <td>
                             <INPUT style="width:250px;" class="form-control form_datetime" id="couponBeginTime" name="couponBeginTime" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
                          </td>
                          <td style="text-align:right;"><b>结束时间：</b></td>
                          <td>
                             <INPUT style="width:250px;" class="form-control form_datetime" id="couponEndTime" name="couponEndTime" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
                          </td>
                       </tr>
                       <tr style="height:40px;">
                          <td style="text-align:right;"><b>备注：</b></td>
                          <td colspan="3">
                              <input class="form-control" type="text" id="remark" name="remark" value="${dictionary.sort}"/>
                          </td>
                       </tr>
                     <tr style="height:40px;">
                          <td style="text-align:right;"><b>是否公开此优惠券：</b></td>
                          <td>
                              <select class="form-control" id="ispublic" name="ispublic" style="width:200px;">    
                                   <option value="1">是</option>
                                   <option value="0">否</option>
                               </select>
                               <input type="hidden" id="couponimg" name="couponimg" />
                          </td>
                          <td style="text-align:right;"><b>优惠种类：</b></td>
                         <td>
                               <select class="form-control" id="coupontype" name="coupontype" style="width:200px;">    
                                   <option value="设备">设备</option>
                                   <option value="流量">流量</option>
                               </select>
                          </td>
                       </tr>
                       <tr>
                         <td colspan="4" style="text-align:center">
                         </td>
                       </tr>
                   </table>
              </FORM>
              <div style="width:100%;text-align:center;padding-top:10px;">
                   <BUTTON style="margin-right:50px;" class="btn btn-primary" onclick="return ck_send()" type="submit">保存</BUTTON>
                   <BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
              </div>
          </DIV>
        </DIV>
     </DIV>
     

<!--  <SECTION id="main-content"> -->
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
   <FORM class="form-inline" id="banben"  method="get" action="#">
         <DIV class="form-group">
             <LABEL class="inline-label">批次号搜索：</LABEL>
             <select class="form-control" name="couponVesion" id="couponVesion" style="width:250px;" >
             <option></option>
               <c:forEach var="cvl" items="${CouponVersionList}">
                  <option>${cvl.couponVesion}</option>
               </c:forEach>
             </select>
         </DIV>
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#banben').serializeArray();gridObj.page(1);">搜索</BUTTON>
         </DIV>
   </FORM>
   <form id="testc"></form>
</DIV>
</DIV>
<DIV class="panel">
<DIV class="panel-heading">[ 优惠劵列表 ]</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
 
 <TABLE id="searchTable">
  <TR>
    <TH w_index="couponVesion" width="8%"><b>批次号</b></TH>
    <TH w_render="ispublic" width="8%"><b>是否公开</b></TH>
    <TH w_index="couponTitle" width="10%"><b>优惠活动标题</b></TH>
    <TH w_index="couponCount" width="5%"><b>优惠劵数量</b></TH>
<!-- <TH w_index="couponCount" width="5%"><b>已领取数量</b></TH> -->
    <TH w_index="couponDiscountStatus" width="7%"><b>类型(减总额/打折)</b></TH>
    <TH w_index="coupontype" width="7%"><b>种类(设备/流量)</b></TH>
    <TH w_index="couponValue" width="5%"><b>优惠劵折扣</b></TH>
    <TH w_index="couponBeginTime" width="7%"><b>活动开始时间</b></TH>
    <TH w_index="couponEndTime" width="7%"><b>活动结束时间</b></TH>
    <TH w_index="createUserName"  width="3%" ><b>创建人</b></TH>
    <TH w_index="createDate" width="7%"><b>创建时间</b></TH>
    <TH w_index="remark"  width="10%" ><b>备注</b></TH>
    <TH w_render="operate" width="10%"><b>操作</b></TH>
  </TR>
</TABLE>
   </DIV>
<DIV>
</DIV></DIV></DIV></DIV>
</SECTION>
<form id="testform"  method="get">
</form>
<!-- ======================================================================================== -->
</DIV>

</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
        <script type="text/javascript">
        
 
     function ck_send(){
    	 
    	 var couponTitle = document.getElementById('couponTitle').value;
    	 var couponCount = document.getElementById('couponCount').value;
    	 var couponDiscountStatus = document.getElementById('couponDiscountStatus').value;
    	 var couponValue = document.getElementById('couponValue').value;
    	 var couponBeginTime = document.getElementById('couponBeginTime').value;
    	 var couponEndTime = document.getElementById('couponEndTime').value;
    	 document.getElementById('couponimg').value="static/images/"+document.getElementById('couponValue').value+".jpg";
    	 var couponimg = document.getElementById('couponimg').value;
    	 if(couponTitle==""){
    		 easy2go.toast('info',"请填写活动标题后提交！");
    		 document.getElementById('couponTitle').focus();
    		 return false;
    	 }else if(couponCount==""){
    		 easy2go.toast('info','请填写优惠劵个数！');
    		 document.getElementById('couponCount').focus();
    		 return false;
    	 }else if(couponDiscountStatus =="-1"){
    		 easy2go.toast('info','请填写优惠类型！');
    		 document.getElementById('couponDiscountStatus').focus();
    		 return false;
    	 }
    	 else if(couponValue =="-1"){
    		 easy2go.toast('info','请填写优惠折扣！');
    		 document.getElementById('couponValue').focus();
    		 return false;
    	 }else if(couponBeginTime==""){
    		 easy2go.taost('info','请填写开始时间！');
    		 document.getElementById('couponBeginTime').focus();
    		 return false;
    	 }else if(couponEndTime==""){
    		 easy2go.taost('info','请填写结束时间！');
    		 document.getElementById('couponEndTime').focus();
    		 return false;
    	 }
    	 else{
    	    $.ajax({
    	    	async: false, 
  	            type:"POST",
  	            url:"<%=basePath %>coupon/save",
  	            dataType:"json",
  	            data:$('#coupon_form').serialize(),
    			success : function(data) {
    			    msg = data;
    	                if(data=="1"){
    	                	gridObj.refreshPage();
    	                	easy2go.toast('info',"新增优惠劵成功！");
    	                }else if(data=="0"){
    	                	easy2go.toast('info','新增失败！');
    	                } else{
    	                	easy2go.taost('info',data);
    	                }
    			 }
    		   }); 
    	 }
    	 
    
    }

    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>coupon/getlist',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
 
    function deleteCK(){
    	if(confirm("确定要删除数据吗？"))
        {
    	return true;	
    	}return false;
    }
 
   function operate(record, rowIndex, colIndex, options) {
           return  '<A class="btn btn-primary btn-xs"href="javascript:void(0);">'+
                        '<SPAN class="glyphicon glyphicon-remove" onclick="deleteCount('+rowIndex+');">删除</SPAN>'+
                   '</A>';
   }
   function ispublic(record, rowIndex, colIndex, options) {
	  if(record.ispublic=="1"){
		  return "是";
	  }else{
		  return "否";
	  }
   }
   
   function deleteCount(index){
	   var record= gridObj.getRecord(index);
bootbox.confirm("确定删除吗?", function(result) {
	   $.ajax({
			async: false, 
			type : "POST",
			url : "<%=basePath %>coupon/deletecoupon?couponVesion="+record.couponVesion,
			dataType : "html",
			success : function(data) {
			    msg = data;
			    if(data=="1"){
			    	easy2go.toast('info', "删除成功！");
			    	gridObj.refreshPage();
			    }else if(data=="0"){
			    	easy2go.toast('info', "删除失败！");
			    }
			    else{
			    	easy2go.toast('info', msg);
			    }
			}});   
});
   }
   
   
   $("#couponBeginTime").datetimepicker({
		pickDate: true,                 //en/disables the date picker
		pickTime: true,                 //en/disables the time picker
		showToday: true,                 //shows the today indicator
		language:'zh-CN',                  //sets language locale
		defaultDate: moment().add(-3, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
	});
    
    $("#couponEndTime").datetimepicker({
 		pickDate: true,                 //en/disables the date picker
 		pickTime: true,                 //en/disables the time picker
 		showToday: true,                 //shows the today indicator
 		language:'zh-CN',                  //sets language locale
 		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
 	});
    
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="level1" />
</jsp:include>
  </body>
</html>