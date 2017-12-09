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
    <title>添加设备-设备管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
    
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
  </head>
 <body>
   
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
<!-- <SECTION id="main-content" style=""> -->
<!-- <SECTION class="wrapper"> -->
<!-- <DIV class="col-md-9"> -->
<!-- <DIV class="panel"> -->
<DIV class="panel-heading">添加设备</DIV>
<DIV class="panel-body">

<SECTION id="main-content">
<!--    <SECTION class="wrapper"> -->
<DIV class="col-md-9">
        <DIV class="panel" style="background-repeat:repeat;">
          <DIV class="panel-heading"><SMALL>添加设备设备默认状态为可用状态</SMALL></DIV>
           <DIV class="panel-body">
              <form id="device_form" role="form" method="POST"
								autocomplete="off"
								action=""
								class="form-horizontal">
                  <DIV class="form-group">
                      <LABEL class="col-md-3 control-label" for="device_sn">序列号：</LABEL>
                      <DIV class="col-md-6">
                         <INPUT id="device_sn" class="form-control" name="SN" type="text" required="" data-popover-offset="0,8">
                      </DIV>
                 <DIV class="col-md-3">
                      <P class="form-control-static">
                          <SPAN class="red">*</SPAN>
                      </P>
                 </DIV>
                </DIV>
                <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">种子卡编号：</LABEL>
                   <DIV class="col-md-6"> 
                       <input id="device_cardid" class="form-control"  name="CardID" data-popover-offset="0,8" placeholder="种子卡编号"/>
                   </DIV>
               </DIV>
                <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">型号：</LABEL>
                   <DIV class="col-md-6"> 
                       <input id="device_cardid" class="form-control"  name="modelNumber" data-popover-offset="0,8" placeholder="型号"/>
                   </DIV>
               </DIV>
                <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">制式和频段：</LABEL>
                   <DIV class="col-md-6"> 
                       <input id="device_cardid" class="form-control"  name="frequencyRange" data-popover-offset="0,8" placeholder="制式和频段"/>
                   </DIV>
               </DIV>
<!--                <DIV class="form-group"> -->
<!--                    <LABEL class="col-md-3 control-label" for="device_desc">支持的国家：</LABEL> -->
<!--                    <DIV class="col-md-6">  -->
<!--                        <input id="device_cardid" class="form-control"  name="supportCountry" data-popover-offset="0,8" placeholder="支持的国家"/> -->
<!--                    </DIV> -->
<!--                </DIV> -->
 
 											<DIV class="form-group">
												<LABEL class="col-md-3 control-label" for="device_desc">支持的国家：</LABEL>
												<DIV class="col-md-6">
                                                 <input onclick="selectAll()" type="checkbox"   name="controlAll" style="controlAll" id="controlAll"/>
                                                 <LABEL class="checkbox-items">全选/全不选(不选默认为支持所有国家)</LABEL><br/>
                                                 <c:forEach items="${countrys }" var="cs">
														<LABEL class="checkbox-items">
														<INPUT class="countryitem" name="userCountry" value="${cs.countryCode}" type="checkbox" >
														${cs.countryName}</LABEL>
													</c:forEach>
												</DIV>
											</DIV>
											<input name="supportCountry" id="usercountry" type="hidden"/>
											
               <DIV style="display: none;" class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">颜色：</LABEL>
                   <DIV class="col-md-6">
                            <select  class="form-control"  id="device_color0" name="deviceColour0">
                                   <option value="">请选择设备颜色</option>
                                       <c:forEach var="dic" items="${dictionary}">
                                          <option>${dic.label}</option>
                                       </c:forEach>
                            </select>
                   </DIV>
               </DIV>
                <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">渠道商名称：</LABEL>
                   <DIV class="col-md-6">
         <select  class="form-control" onchange="qdsid();" id="distributorName" name="distributorName">
             <option value="">请选择渠道商</option>
             <c:forEach var="dis" items="${distributor}">
             <option>${dis.company}</option>
             </c:forEach>
         </select>
         <select  class="form-control" id="distributorId" name="distributorId" style="width:0px;height:0px;border:0px;position:absolute;clip:rect(0 0 0 0)">
             <option value=""></option>
             <c:forEach var="dis" items="${distributor}">
             <option>${dis.distributorID}"</option>
             </c:forEach>
         </select>
                   </DIV>
               </DIV>               
               <input id="returnResult" value="${devicemessage}" type="hidden"/>
               <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">备注：</LABEL>
                   <DIV class="col-md-6">
                       <input id="device_remark" class="form-control"  name="remark" data-popover-offset="0,8"/>
                   </DIV>
               </DIV>
             <DIV class="form-group">
                <DIV class="col-md-9 col-md-offset-3">
                   <DIV class="btn-toolbar">
                        <BUTTON class="btn btn-primary" type="submit" onclick="return adddeviceinfo();">保存</BUTTON>
                        <BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
                   </DIV>
                </DIV>
             </DIV>
              </FORM>
          </DIV>
        </DIV>
     </DIV>
  </SECTION>
</SECTION>
<input type="hidden" id="cksn" value="${addmesg}"/>
<!-- ============================================================ -->
</DIV>

<!-- </DIV> -->
<!-- </DIV> -->
<!-- </SECTION> -->
<!-- </SECTION> -->
<!--     </section> -->
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <script>
    function adddeviceinfo(){
    	
    	
    	var str=document.getElementsByName("userCountry");
    	var objarray=str.length;
    	var userCountry="";
    	for (i=0;i<objarray;i++)
    	{ 
    	  if(str[i].checked == true)
    	  {
    		  userCountry+=str[i].value+"|";
    	  }
    	} 
    	userCountry=userCountry.substr(0,userCountry.length-1);
        document.getElementById("usercountry").value=userCountry;  
    	
    	
    	 var devicename = document.getElementById('device_sn').value.trim();
    	if(devicename == ""){
    	  	easy2go.toast('info',"设备序列号不能为空！");
    	  	document.getElementById('device_sn').focus();
    	    return false;
    	}else if(isNaN(devicename)){
    	    easy2go.toast('info','请输入数字！');
    	    document.getElementById('device_sn').focus();
    	    return false;
    	}else if(devicename.length<15){
    		easy2go.toast('info','设备号有误，正确的设备号为15位数字，请检查！');
    		document.getElementById('device_sn').focus();
    		return false;
    	}else if(devicename.length>15){
    		easy2go.toast('info','设备号有误，正确的设备号为15位数字，请检查！');
    		document.getElementById('device_sn').focus();
    		return false;
    	} else if(devicename.length==15){
    		$.ajax({
       			type : "GET",
       			url : "<%=basePath%>device/checksn?sid="+document.getElementById('device_sn').value.trim(),
       			success : function(data) {
       			    msg = data;
       				data = parseInt(data);
       				if (data == 1) {
       							alert("设备已存在！不可重复添加");
       							document.getElementById('cksn').value="1";
       							document.getElementById('device_sn').focus();
       					    	easy2go.toast('warn', "设备已存在！不可重复添加");
       							return false;
       						} else if (data == 0) {
       							$.ajax({
       				       			type : "POST",
       				       			url : "<%=basePath%>device/add",
       				       			dataType : "html",
       				       			data : $('#device_form').serialize(),
       				       			success : function(data) {
       				       			    msg = data;
       				       				data = parseInt(data);
       				       				if (data == 1) {
//        				       					    alert("新增设备成功!");
       				       						window.location="<%=basePath%>device/list";
       				       						} else if (data == 0) {
       				       							easy2go.toast('warn', "新增失败");
       				       						} else if (data == -1) {
       				       							easy2go.toast('warn', "参数为空");
       				       						} else {
       				       						    easy2go.toast('error', msg);
       				       						}
       				       					}
       				       				});
       							alert("新增设备成功!");
                                  document.getElementById('cksn').value="0";
       							return true;
       						} 
       					}
       				});
    	}
<%--     	else if(document.getElementById('cksn').value=="0"){
       		$.ajax({
       			type : "POST",
       			url : "<%=basePath%>device/add",
       			dataType : "html",
       			data : $('#device_form').serialize(),
       			success : function(data) {
       			    msg = data;
       				data = parseInt(data);
       				if (data == 1) {
       					    alert("新增设备成功!");
       						window.location="<%=basePath%>device/list";
       						} else if (data == 0) {
       							easy2go.toast('warn', "新增失败");
       						} else if (data == -1) {
       							easy2go.toast('warn', "参数为空");
       						} else {
       						    easy2go.toast('error', msg);
       						}
       					}
       				});
    	} --%>
    	
    	
//    	   function senddata(){

//        }

   		
    }
    
  
    function qdsid(){
    	var test = document.getElementById('distributorName').selectedIndex;
    	document.getElementById('distributorId').selectedIndex=test;
    }
 
    
function selectAll(){
 var checklist = document.getElementsByName ("userCountry");
   if(document.getElementById("controlAll").checked)
   {
   for(var i=0;i<checklist.length;i++)
   {
      checklist[i].checked = 1;
   } 
 }else{
  for(var j=0;j<checklist.length;j++)
  {
     checklist[j].checked = 0;
  }
 }
}
    
//     }
    </script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
  </body>
</html>