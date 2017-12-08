<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<title>编辑设备-设备管理-EASY2GO ADMIN</title>
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
	height: 40px;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<!-- ============================================================ -->

		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-9">
					<DIV class="panel">
						<DIV class="panel-heading">编辑设备信息</DIV>
						<DIV class="panel-body">
							<FORM id="device_form" class="form-horizontal" role="form" method="post" action="<%=path %>/device/updateToDevice" autocomplete="off">
								<INPUT name="_csrf" value="2w5HyXLN-PpEHUAtXe26G2sWBVeB1rG1G-kw"type="hidden">
								<INPUT name="_method" value="put" type="hidden">
								<DIV class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">序列号：</LABEL>
									<DIV class="col-md-6">
										<INPUT id="device_sn" disabled="true" value="${device.SN}" class="form-control" name="deviceSn1" type="text" required=""data-popover-offset="0,8">
										<INPUT id="device_sn" type="hidden" value="${device.SN}" class="form-control" name="SN" type="text" required=""data-popover-offset="0,8">
										<input type="hidden" value="${device.sysStatus}"/>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">漫游卡代号：</LABEL>
									<DIV class="col-md-6">
										<INPUT id="device_sn"  value="${device.cardID}" class="form-control" name="CardID" type="text" data-popover-offset="0,8">
									</DIV>
								</DIV>
                <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">型号：</LABEL>
                   <DIV class="col-md-6">
                       <input id="device_cardid" class="form-control" value="${device.modelNumber}" name="modelNumber" data-popover-offset="0,8" placeholder="型号"/>
                   </DIV>
               </DIV>
                <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">制式和频段：</LABEL>
                   <DIV class="col-md-6">
                       <input id="device_cardid" class="form-control" value="${device.frequencyRange}"  name="frequencyRange"  placeholder="制式和频段"/>
                   </DIV>
               </DIV>
               <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">支持的国家：</LABEL>
                   <DIV class="col-md-6">
                       <input id="device_cardid" class="form-control" value="${device.supportCountry}"  placeholder="支持的国家"/>
                   </DIV>
               </DIV>


			<DIV class="form-group">
				<LABEL class="col-md-3 control-label" for="device_desc">支持的国家：</LABEL>
				<DIV class="col-md-6">
                        <input onclick="selectAll()" type="checkbox"   name="controlAll"  id="controlAll"/>
                        <LABEL class="checkbox-items">全选/全不选(不选默认为支持所有国家)</LABEL><br/>

					<c:forEach items="${countrys}" var="cs">
						<LABEL class="checkbox-items">
						        <c:if test="${cs.selected}">
						           <INPUT class="countryitem" name="userCountry" value="${cs.countryCode}" type="checkbox" checked="checked">${cs.countryName}
						        </c:if>
						      	<c:if test="${!cs.selected}">
						        	<INPUT class="countryitem" name="userCountry" value="${cs.countryCode}" type="checkbox">${cs.countryName}
						        </c:if>
						</LABEL>
				  </c:forEach>
				</DIV>
			</DIV> <input name="supportCountry" id="usercountry" type="hidden"/>


								<DIV style="display: none;" class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">颜色：</LABEL>
									<DIV class="col-md-6">
										<INPUT id="device_sn" disabled="true" value="${device.deviceColour}" class="form-control" name="deviceSn" type="text" required=""data-popover-offset="0,8">
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-sm-3 control-label">状态：</LABEL>
									<DIV class="col-sm-6">

                                            <select class="form-control"  selected="selected" name="deviceStatus">
                                               <option id="1">不可用</option>
                                               <option id="2">可使用</option>
                                               <option id="3">使用中</option>
                                            </select>
                                            <input value="${device.deviceStatus}" type="hidden" id="device_ck"/>
                                            <script>
                                             if(document.getElementById('device_ck').value=="不可用")
                                                 document.getElementById("1").selected="selected";
                                             if(document.getElementById('device_ck').value=="可使用")
                                                  document.getElementById("2").selected="selected";
                                              if(document.getElementById('device_ck').value=="使用中")
                                                    document.getElementById("3").selected="selected";
                                            </script>
									</DIV>
									<DIV class="col-sm-3">
										<P class="form-control-static">
											<SPAN class="red" style="color:red;">*</SPAN>
										</P>
									</DIV>
								</DIV>

								<DIV class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">出入库状态：</LABEL>
									<DIV class="col-md-6">
										<select class="form-control" name="repertoryStatus">
										  <c:if test="${device.repertoryStatus eq '入库'}">
										    <option selected="selected">入库</option>
										  </c:if>
										  <c:if test="${device.repertoryStatus ne '入库'}">
										    <option>入库</option>
										  </c:if>
										  <c:if test="${device.repertoryStatus eq '出库'}">
										    <option selected="selected">出库</option>
										  </c:if>
										  <c:if test="${device.repertoryStatus ne '出库'}">
										    <option>出库</option>
										  </c:if>
										</select>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-3 control-label" for="device_desc">备注：</LABEL>
									<DIV class="col-md-6">
										<TEXTAREA id="device_desc" class="form-control" rows="3"
											name="remark">${device.remark}
										</TEXTAREA>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<DIV class="col-md-9 col-md-offset-3">
										<DIV class="btn-toolbar">
											<BUTTON class="btn btn-primary" type="submit" onclick="ckusercountry();">保存</BUTTON>
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

		<!-- ============================================================ -->

	</section>
	<script>
	   function ckusercountry(){

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
	   }

	   function selectAll(){
		   var checklist = document.getElementsByName ("userCountry");
		     if(document.getElementById("controlAll").checked)
		     {
		       for(var i=0;i<checklist.length;i++){
		          checklist[i].checked = 1;
		       }
		     }else{
		       for(var j=0;j<checklist.length;j++){
		         checklist[j].checked = 0;
		         }
		     }
		  }

	</script>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="level1" />
</jsp:include>
</body>
</html>