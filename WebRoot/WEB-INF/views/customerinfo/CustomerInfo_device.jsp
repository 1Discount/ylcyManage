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
    <title>查看客户所有设备订单-客户管理-流量运营中心</title>
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
     
 <SECTION id="main-content" style="">
<SECTION class="wrapper">
  <DIV class="col-md-3">
    <DIV class="panel">
        <DIV class="panel-heading"><H3 class="panel-title">客户</H3></DIV>
        <DIV class="panel-body">
        <script type="text/javascript">
        function GetQueryString(name)//获取地址栏参数的值
        {
             var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
             var r = window.location.search.substr(1).match(reg);
             if(r!=null)return  unescape(r[2]); return null;
             
        }
        function datasrc(){
            //所有数据交易服务交易
            var alldata ="<%=path %>/customer/customerInfolist/ordersfloworder?uid="+GetQueryString("uid");
            //所有订单
            var allorders ="<%=path %>/customer/customerInfolist/OrdersInfo?uid="+GetQueryString("uid");
            //所有设备
            var device = "<%=path %>/customer/customerInfolist/DeviceInfo?uid="+GetQueryString("uid");
//             alert(alldata);
            document.getElementById('alldatas').href=alldata;
            document.getElementById('allorders').href=allorders;
//             document.getElementById('devicesrc').href=device;
            }
            window.onload=datasrc;
        </script>
        
  <UL class="nav nav-pills nav-stacked" role="tablist">
    <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/all">全部客户</A></LI>
    <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/insert">添加客户信息</A></LI>
    <LI role="presentation"><A id="allorders" href="">他/她的所有订单</A></LI>
    <LI role="presentation"><A id="alldatas" href="">他/她的所有数据服务交易</A></LI>
<!--     <LI role="presentation"><A href="#">他/她的所有设备</A></LI> -->
  </UL>
       </DIV>

    </DIV>
    <DIV class="panel">
       <DIV class="panel">
            <DIV class="panel-heading"><H3 class="panel-title">搜索</H3></DIV>
            <DIV class="panel-body">
              <FORM class="form" role="form" method="get" action="#">
                <DIV class="form-group"><INPUT class="form-control" name="q" type="text"></DIV>
                <DIV class="form-group">
                   <BUTTON class="btn btn-primary" type="submit">搜索</BUTTON>
                </DIV>
              </FORM>
           </DIV>
      </DIV>
    </DIV>
  </DIV>
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">查看 <strong><c:if test="${not empty devicedealorders}">${devicedealorders.get(0).customerName}</c:if><c:if test="${empty devicedealorders}">该客户</c:if></strong> 的所有设备订单</DIV>
<DIV class="panel-body">

<!-- ====================================================================== -->
 
  

<DIV class="col-md-12">
<%--<DIV class="panel">--%>
<%--<DIV class="panel-heading"></DIV>--%>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>交易ID</TH>
    <TH>设备</TH>
    <TH>金额</TH>
    <TH>类型</TH>
    <TH>归还日期</TH>
    <TH>创建时间</TH>
    <TH>操作</TH></TR></THEAD>
  <TBODY>
  <c:forEach  var="devicedealorders" items="${devicedealorders}">
  <TR>
    <!-- 交易ID --><TD><A href="<%=basePath %>orders/devicedealorders/info?deviceDealID=${devicedealorders.deviceDealID}">${devicedealorders.deviceDealID}</A></TD>
    <!-- 设备 --><TD><A href="<%=basePath %>device/deviceInfodetail?deviceid=${devicedealorders.SN}">${devicedealorders.SN}</A></TD>
    <!-- 金额 --><TD><SPAN class="num">${devicedealorders.dealAmount}</SPAN></TD>
    <!-- 类型 --><TD><A class="label label-primary">${devicedealorders.deallType}</A></TD>
    <!-- 归还时间 --><TD>${devicedealorders.returnDate}</TD>
    <!-- 创建时间 --><TD> <fmt:formatDate value="${devicedealorders.creatorDate}" pattern="yyyy/MM/dd HH:mm:ss"/> </TD>
    <TD>
<%--     <input type="hidden" id="ordersID" value="${.}"/> --%>
      <DIV class="btn-toolbar">
      <A class="btn btn-primary btn-xs" href="<%=basePath %>orders/devicedealorders/edit?deviceDealID=${devicedealorders.deviceDealID}"> 
          <SPAN class="glyphicon glyphicon-edit"></SPAN>编辑
      </A>
      <A class="btn btn-danger btn-xs"  onclick="deletebyid('+${orders.orderID}+')">
         <SPAN  class="glyphicon glyphicon-remove"></SPAN>删除
      </A>
      </DIV>
    </TD>
  </TR>
  </c:forEach>      
      
      
      
      </TBODY></TABLE></DIV></DIV></DIV></DIV> 
	  
 
<!-- ======================================================================================= -->

</DIV>
</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script>
      var gridObj;

      function deletebyid(index){
//  	 var record= gridObj.getRecord(index);
     	 var record= document.getElementById("ordersID").value;
     	 bootbox.confirm("确定删除吗?", function(result) {
     		 if(result){
     			 $.ajax({
             		 type:"GET",
             		 url:"<%=basePath %>orders/ordersinfo/deleteby/"+record,
             		 dataType:'html',
             		 success:function(data){
         				if(data=="1"){
         					easy2go.toast('info',"删除成功");
//              				gridObj.refreshPage();
         					window.location.reload();
         					}else if(data=="0"){
         					easy2go.toast('warn',"删除失败");
         				}else if(data=="-1"){
         					easy2go.toast('warn',"参数为空");
         				}else if(data=="-5"){
                            easy2go.toast('err',"请重新登录!");
                        }
             		 }
             	 });
     		 }  
     	 });
      }
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
      $(function(){
      });
    </script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>