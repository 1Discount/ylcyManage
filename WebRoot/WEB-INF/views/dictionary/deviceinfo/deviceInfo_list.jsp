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
<title>全部设备-设备管理-EASY2GO ADMIN</title>
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
	height: 36px;
}
</style>
</head>
<body>
<div id="messdele">${messagedevicedele}</div>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" role="form" id="searchForm" method="get" action="" >
								<DIV class="form-group">
									<LABEL class="inline-label">状态：</LABEL>
									<select class="form-control" name="deviceStatus" id="deviceStatus">
									     <option>--所有状态--</option>
                                         <c:forEach var="dev" items="${types}">
                                             <option>${dev.label}</option>
                                         </c:forEach>
                                    </select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">出入库状态：</LABEL>
									<select class="form-control" name="repertoryStatus" id="repertoryStatus">
									     <option value="">--所有状态--</option>
									     <option value="出库">出库</option>
									     <option value="入库">入库</option>
                                    </select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">序列号：</LABEL>
									<INPUT class="form-control" name="SN" id="SN" type="text" placeholder="序列号">
								</DIV>


								<DIV class="form-group">
									<LABEL class="inline-label">渠道商：</LABEL>
									<select id="company" name="company" class="form-control" >
									<option value="">全部</option>
									<c:forEach items="${disAll}" var="item" varStatus="status">
										<option value="${item.company}">${item.company}</option>
									</c:forEach>
									</select>
								</DIV>
								<DIV class="form-group">
									<BUTTON class="btn btn-primary" id="devFind"
									onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);"
									type="button">搜索</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>

					<DIV class="panel">
						<DIV class="panel-heading">所有设备</DIV>
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE id="searchTable2">
									<TR>
										<TH w_render="device_num"  width="10%"><b>序列号</b></TH>
										<TH w_index="distributorName"  width="10%"><b>渠道商</b></TH>
										<TH w_index="deviceDealID" w_hidden="true" width="10%"><b>序列号</b></TH>
										<TH w_index="modelNumber" width="10%"><b>型号</b></TH>
										<TH w_index="frequencyRange"  width="10%"><b>制式和频段</b></TH>
										<TH w_render="device_Status"  width="10%"><b>状态</b></TH>
										<TH w_index="repertoryStatus" width="10%"><b>出入库状态</b></TH>
										<TH w_index=customerID w_hidden="true" width="10%"><b>客户ID</b></TH>
										<th w_render="userName" w_index="userName" width="10%"><b>创建人</b></th>
										<th w_index="creatorDate" width="10%"><b>创建时间</b></th>
										<TH w_render="operate" width="10%"><b>操作</b></TH>
									</TR>
								</TABLE>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<form id="testform"  method="get"></form>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script>
function messageload(){
	var mess = document.getElementById('messdele').innerHTML;
	if(mess!=""){
		mess=mess+"";
		if(mess.length>35){
			 easy2go.toast('error', mess);
	    	 document.getElementById('messdele').innerHTML="";
		}else{
	     easy2go.toast('info', mess);
    	 document.getElementById('messdele').innerHTML="";
		}
	 }
	}
window.onload=messageload;
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable2',{
           url:'<%=basePath%>device/deviceinfoPage',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
 //设备sn
    function device_num(record, rowIndex, colIndex, options){
     	return '<A style="color:#1FB5AD;" href="<%=basePath %>device/deviceInfodetail?deviceid='+record.SN+'">'+record.SN+'</A>';
     }
 //设备状态
    function device_Status(record, rowIndex, colIndex, options){
	   if(record.deviceStatus=="使用中"){
		   return '<span class="btn btn-danger btn-xs">'+record.deviceStatus+'</span>';
	   }else{
    	return '<span class="label label-primary label-xs">'+record.deviceStatus+'</span>';
	   }
    }

    function userName(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;"">'+record.creatorUserName+'</A>';
    }
    function zlgm(record, rowIndex, colIndex, options){
    	if(record.zlgm=="购买"){
    	   return '<span class="btn btn-danger btn-xs">'+record.zlgm+'</span>';
    	}else{
    	   return '<span class="label label-primary label-xs">'+record.zlgm+'</span>';
    	}
    }

    //操作
    function operate(record, rowIndex, colIndex, options) {
    	var str="";
        if(record.zlgm=="租赁"){
        	str='<A class="btn btn-warning btn-xs" onclick="rebackDevice('+rowIndex+')"  >'+
           		 '<SPAN class=" glyphicon glyphicon-circle-arrow-left">归还设备 </SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;';
        }/*  else{
        	str='<A class="btn btn-primary btn-xs" style="visibility:hidden;">'+
            	'<SPAN class="glyphicon glyphicon-edit">归还设备 </SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;';
        } */
        	 return '<A class="btn btn-primary btn-xs" href="<%=path %>/device/edit?xlid='+record.SN+'">'+
             '<SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;'+str;


		}

    $(document).keydown(function(event){
    	if(event.keyCode == 13){//绑定回车
    		$("#SN").blur();
    		gridObj.options.otherParames =$('#searchForm').serializeArray();
    		gridObj.page(1);
    		event.returnValue = false;
    	}
    	});



    function rebackDevice(index){
    	 var record= gridObj.getRecord(index);
    	 record = record.SN;
    	  $.ajax({
  			type : "POST",
  			url : "<%=basePath%>device/revertTo2?device_sn="+record,
  			dataType : "html",
  			success : function(data) {
  			    msg = data;
  				      if (data=="0")
  				       {
  				    	easy2go.toast('warn',"归还失败！");
  					   } else{
  						    easy2go.toast('info', data);
  							gridObj.refreshPage();
  					   }
  					}
  				});
    }
	</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>