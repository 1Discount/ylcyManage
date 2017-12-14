<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>查看种子卡-种子卡管理-流量运营中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
  </head>
  <body>
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
      <section id="main-content">
        <section class="wrapper">
          <%-- 这里添加页面主要内容  block content --%>

      <div class="col-md-12">

	  <div class="panel">
	    <div class="panel-heading">
          <div class="btn-toolbar btn-header-right"><a href="<%=basePath %>sim/roamingsiminfo/edit/${Model.SIMinfoID}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-edit"></span>编辑</a>
<%--          <a href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=${Model.SIMinfoID}&SIMCategory=${Model.SIMCategory}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-plus"></span>充值</a></div>--%>
          </div>
	      <h4 class="panel-title">漫游SIM卡</h4>
	    </div>
	    <div class="panel-body">
	      <div class="table-responsive">
	        <table class="table table-bordered table-hover">
	          <tr>
	            <td width="15%">ICCID：</td>
	            <td width="35%"><span class="label label-success label-xs">${Model.ICCID}</span></td>
	            <td width="15%">SIM卡使用类型：</td>
	            <td width="35%">${Model.SIMCategory}</td>
	          </tr>
              <tr>
                <td width="15%">IMSI：</td>
                <td width="35%"><span class="label label-success label-xs">${Model.IMSI}</span></td>
                <td width="15%">最近设备机身码：</td>
                <td width="35%"><a href="<%=basePath %>device/deviceInfodetail?deviceid=${Model.lastDeviceSN}">${Model.lastDeviceSN}</a></td>
              </tr>
<%--	          <tr>--%>
<%--                <td>MCC：</td>--%>
<%--                <td><a href="<%=basePath %>flowplan/countryinfo/view/mcc/${Model.MCC}">${Model.MCC}</a></td>--%>
<%--	            <td>MNC：</td>--%>
<%--	            <td>${Model.MNC}</td>--%>
<%--	          </tr>--%>
              <tr>
<%--                <td>发行国家：</td>--%>
<%--                <td><a href="<%=basePath %>flowplan/countryinfo/view/mcc/${Model.MCC}">${Model_MCCCountryName}</a></td>   --%>
                <td>代号：</td>
                <td>${Model.simAlias}</td>
              </tr>
	          <tr>
	            <td>状态：</td>
                <td><c:if test="${Model.cardStatus eq '损失' or Model.cardStatus eq '丢失'}"><span class='label label-danger label-xs'>${Model.cardStatus}</span></c:if>
                <c:if test="${Model.cardStatus ne '损失' and Model.cardStatus ne '丢失'}"><span class='label label-success label-xs'>${Model.cardStatus}</span></c:if></td>
                <td>购卡时间：</td>
                <td>${Model.SIMActivateDate}</td>
	          </tr>

	          <tr>
	            <td>创建者：</td>
                <td><a href="#">${Model.creatorUserName}</a></td>
	            <td>创建时间：</td>
	            <td>${Model.creatorDate}</td>
	          </tr>
	          <tr>
	            <td>更新时间：</td>
                <td><span>${Model.modifyDate}</span></td>
	            <td>备注：</td>
	            <td>${Model.remark}</td>
	          </tr>
	        </table>
	      </div>
	      <h4>更多sim卡信息</h4>
          <div class="table-responsive">
            <table class="table table-bordered table-hover">
              <tr>
                <td width="15%">套餐名称：</td>
                <td width="35%">${Model.planType}</td>
                <td width="15%">运营商：</td>
                <td width="35%">${Model.trademark}</td>
              </tr>
              <tr>
                <td width="15%">卡手机号码：</td>
                <td width="35%">${Model.phone}</td>
                <td>SIM卡结算方式：</td>
                <td>${Model.simBillMethod}</td>
              </tr>
              <tr>
                <td>PIN：</td>
                <td>${Model.PIN}</td>
                <td>PUK：</td>
                <td>${Model.PUK}</td>
              </tr>
              <tr>
              <td>卡内余额：</td>
              <td>${Model.cardBalance}</td>
<%--                <td>套餐流量：</td>--%>
<%--                <td><a title="${Model.planData}KB"><span id="planData_pretty" class="label label-success label-xs">${Model.planData}KB</span></a></td>--%>
<%--                <td>剩余流量:</td>--%>
<%--                <td><a title="${Model.planRemainData}KB"><span id="planRemainData_pretty" class="label label-success label-xs">${Model.planRemainData}KB</span></a></td>--%>
              </tr>
              </table>
          </div>
	      <div class="btn-toolbar"><a href="<%=basePath %>sim/roamingsiminfo/edit/${Model.SIMinfoID}" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span>编辑</a>
<%--	      <a href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=${Model.SIMinfoID}&SIMCategory=${Model.SIMCategory}" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>充值</a>--%>
          </div>
	    </div>
	  </div>

     </div>

        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<script type="text/javascript">
    $(function(){
        $("#planData_pretty").empty().append(prettyByteUnitSize("${Model.planData}",2,1,true));
        $("#planRemainData_pretty").empty().append(prettyByteUnitSize("${Model.planRemainData}",2,1,true));
    });
</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>