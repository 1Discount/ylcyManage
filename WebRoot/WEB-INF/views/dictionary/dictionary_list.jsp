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
    <title>数据字典列表-数据字典管理-EASY2GO ADMIN</title>
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
  </head>
 <body>
   
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
     
 <SECTION id="main-content">
<SECTION class="wrapper">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading">数据字典</DIV>
<DIV class="panel-body">
<!-- ======================================================================================== -->
<DIV class="col-md-12">
        <DIV class="panel" style="background-repeat:repeat;">
           <DIV class="panel-body">
               <FORM id="device_form" class="form-horizontal" role="form" method="post" action="" autocomplete="off">
               <input class="form-control" id="testdictID" name="testdictID" value="${dictionary.dictID}" type="hidden"/>
                   <table width="90%" cellspacing="2px"> 
                       <tr style="height:40px;">
                          <td style="text-align:right;"><b>属性值：</b></td>
                          <td><input class="form-control" type="text" id="values" name="values" value="${dictionary.value}"/></td>
                          <td style="text-align:right;"><b>属性名称：</b></td>
                          <td><input class="form-control" type="text" id="label" name="label" value="${dictionary.label}"/></td>
                       </tr>
                       <tr>
                          <td style="text-align:right;"><b>类别：</b></td>
                          <td><input class="form-control" type="text" id="description" name="description" value="${dictionary.description}"/></td>
                          <td style="text-align:right;"><b>排序：</b></td>
                          <td><input class="form-control" type="text" id="sort" name="sort" value="${dictionary.sort}"/></td>
                       </tr>
                       <tr style="height:40px;">
                          <td style="text-align:right;"><b>备注：</b></td>
                          <td colspan="3">
                             <input class="form-control" type="text" id="remark" name="remark" value="${dictionary.remark}"/>
                          </td>
                       </tr>
                       <tr>
                         <td colspan="4" style="text-align:center">
                        <BUTTON style="margin-right:50px;" class="btn btn-primary" onclick="return ck_send()" type="submit">保存</BUTTON>
                        <BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
                         </td>
                       </tr>
                   </table>
              </FORM>
          </DIV>
        </DIV>
     </DIV>
     

<!--  <SECTION id="main-content"> -->
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
   <FORM class="form-inline" id="leibie"  method="get" action="#">
         <DIV class="form-group">
             <LABEL class="inline-label">类别：</LABEL>
             <select class="form-control" name="description" id="descriptionselect" style="width:150px;" >
               <c:forEach var="dic" items="${dic}">
                  <option>${dic.description}</option>
               </c:forEach>
             </select>
         </DIV>
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
         </DIV>
   </FORM>
   <form id="testc"></form>
</DIV>
</DIV>
<DIV class="panel">
<DIV class="panel-heading">字典列表</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
 
 <TABLE id="searchTable">
  <TR>
    <TH w_index="value" width="7%"><b>属性值</b></TH>
    <TH w_index="label" width="7%"><b>属性名称</b></TH>
    <TH w_index="description" width="7%"><b>类别</b></TH>
    <TH w_index="remark"  width="7%" ><b>备注</b></TH>
    <TH w_index="sort"  width="7%" ><b>排序</b></TH>
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
        <script type="text/javascript">
        
//         function jump(){
//         	var num2=document.getElementById('descriptionselect').value;
//         	if(num2!=""){
//         		return false;
//         	}
<%--               var sendurl="<%=basePath %>dictionary/getdescription"; --%>
//               document.getElementById('testform').action=sendurl;
//               document.getElementById('testform').submit();
//         }window.onload=jump;
          
     function ck_send(){
    	var va=document.getElementById("values").value;
    	var label = document.getElementById("label").value;
    	var description = document.getElementById("description").value;
    	if(va=="" || label=="" || description==""){
   		 easy2go.toast('info',"属性值、属性名、类别为必填项请填写完成点击保存！");
    		return false;
    	}else{
    		
    		var cshu = document.getElementById("testdictID").value;
        	if(cshu=="")
        	{
        	   	document.getElementById('device_form').action="<%=basePath %>dictionary/save";
        	}
        	else if(cshu!="")
        	{
        		var result="<%=basePath %>dictionary/updateToDic?dicid="+cshu;
         	   	document.getElementById('device_form').action=result;
        	}
    	}
    
    }

    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>dictionary/all',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 50,
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
           return '<A class="btn btn-primary btn-xs" href="<%=path %>/dictionary/update?dictID='+record.dictID+'">'+
                 '<SPAN class="glyphicon glyphicon-edit">编辑</SPAN>'+
             '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
             '<A class="btn btn-primary btn-xs" href="<%=basePath %>dictionary/deletedic?uid='+record.dictID+'">'+
                 '<SPAN class="glyphicon glyphicon-remove" onclick="return deleteCK()">删除</SPAN>'+
             '</A>';
   }
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="level1" />
</jsp:include>
  </body>
</html>