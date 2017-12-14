<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>添加菜单组-权限管理-流量运营中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
  </head>
  <body>
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
      <SECTION id="main-content">
      <SECTION class="wrapper">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading">
<H3 class="panel-title">添加菜单组</H3></DIV>
<DIV class="panel-body">
<FORM id="menugroup_form" class="form-horizontal" role="form" method="post" 
action=""><input  type="hidden" id="menuGroup_ID" name="menuGroupID" value="" />
<DIV class="form-group"><LABEL class="col-md-2 control-label" >菜单组名称:</LABEL>
<DIV class="col-md-6"><INPUT id="menugroup_name" class="form-control" name="menuGroupName" 
type="text" required="" data-popover-offset="0,8"></DIV>
<DIV class="col-md-4">
<P class="form-control-static"> </P></DIV></DIV>
<DIV class="form-group"><LABEL class="col-md-2 control-label" for="permission_path">备注:</LABEL>
<DIV class="col-md-6"><INPUT id="menugroup_remark" class="form-control" name="remark" 
type="text"  data-popover-offset="0,8"></DIV>
<DIV class="col-md-4">
<P class="form-control-static"></P></DIV></DIV>
<DIV  class="form-group"><LABEL class="col-md-2 control-label" for="permission_methods">图标:</LABEL>
<DIV class="col-md-6">
<select id="slect_ico" name="menuIcon" required="" class="form-control" onchange="os();" >
<option value="">选择图标</option>
<option value="fa fa-cube">
正方形
</option>
<option value="fa fa-gavel">小锤子</option>
<option value="fa fa-users">群体</option>
<option value="fa fa-laptop">电脑</option>
<option value="fa fa-credit-card">卡片</option>
<option value="fa fa-server">服务器</option>
<option value="fa fa-flag-o">红旗</option>
<option value="fa fa-user">人像</option>
<option value="fa fa-shield">权限</option>
<option value="fa fa-cog">设置</option>
<option value="fa fa-wifi">wifi</option>
<option value="fa fa-trash">清除</option>

<option value="fa fa-eye">眼睛</option>

</select>

</DIV><i id="tez" class="fa fa-cube"></i>
<DIV class="col-md-4">
<P class="form-control-static"></P></DIV></DIV>
<DIV class="form-group"><LABEL class="col-md-2 control-label" for="permission_group_no">序号:</LABEL>
<DIV class="col-md-6"><INPUT id="menugroup_no" class="form-control" name="sortCode" 
value="0" type="number" required="" data-popover-offset="0,8" max="99" 
min="0"></DIV>
<DIV class="col-md-4">
<P class="form-control-static"></P></DIV></DIV>
<DIV class="form-group">
<DIV class="col-md-7 col-md-offset-2">
<DIV class="btn-toolbar"><BUTTON class="btn btn-primary" 
type="submit"  onclick="addsub()" >保存</BUTTON><BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" 
type="button">返回</BUTTON></DIV></DIV></DIV></FORM>
</DIV></DIV>
<DIV class="panel">
   <DIV class="panel-body">
       <FORM class="form-inline" id="leibie" method="get" action="#">
           <DIV class="form-group">
               <LABEL class="inline-label">菜单组名称：</LABEL>
               <input id="menuGroupName_select" name="menuGroupName" class="form-control"/>
           </DIV>
           <DIV class="form-group">
               <BUTTON class="btn btn-primary" type="button"
                   onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
            </DIV>
        </FORM>
        <form id="testc"></form>
    </DIV>
</DIV>
<DIV class="panel">
<DIV class="panel-heading">
<H3 class="panel-title">菜单组信息</H3></DIV>
<DIV class="panel-body">
<table id="searchTable">
        <tr>
        	<th w_index="MenuGroupID" w_hidden="true" width="10%;">11</th>
            <th w_index="menuGroupName" width="10%;">菜单组名称</th>
            <th w_index="remark" width="10%;">说明</th>
            <th w_index="sortCode" width="10%;">序号</th>
            <th w_render="operate" width="30%;">操作</th>
        </tr>
</table>
</DIV></DIV>
</DIV></SECTION></SECTION></section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <SCRIPT type="text/javascript">
    var gridObj;
    $(function(){
    	/* $("#menugroup_form").submit(function(){
    		
    	}); */
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>distributor/menuGroupinfo/getpage',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30],
           multiSort:true
       });
   });
    
   function operate(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       return '<A class="btn btn-primary btn-xs" onclick="editinit('+rowIndex+')"  href="#"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;<A class="btn btn-primary btn-xs" onclick="deleteinfo('+rowIndex+')" href="#"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>'
   }
   
   /* function operate1(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       if(record.remark==""){
    	   return "";
       }
       return '<A class="btn btn-primary btn-xs"  href="#">'+record.remark+'</A>'
   } */
   
   function addsub(){
	   if($("#menugroup_name").val()=='' || $("#slect_ico").val()=='' || $("#menugroup_no").val()==''){
		   return false;
	   }
	  	
	   var urlpath="<%=basePath %>distributor/menuGroupinfo/insertinfo";
	   if($("#menuGroup_ID").val()!=null && $("#menuGroup_ID").val()!=''){
		   urlpath="<%=basePath %>distributor/menuGroupinfo/editinfo";
	   }
	   $.ajax({
			type:"POST",
			url:urlpath,
			dataType:"html",
			data:$('#menugroup_form').serialize(),
			success:function(data){
                msg = data;
				data=parseInt(data);
				if(data==1){
					easy2go.toast('info',"添加成功");
					gridObj.refreshPage();
				}else if(data==0){
					easy2go.toast('info',"添加失败");
				}else if(data==2){
					easy2go.toast('info',"修改失败");
				}else if(data==3){
					easy2go.toast('info',"修改成功");
					gridObj.refreshPage();
				} else {
                     easy2go.toast('error', msg);
                 }
				
				$("#menuGroup_ID").val("");
				$("#menugroup_name").val("");
				$("#menugroup_remark").val("");
				$("#menugroup_no").val();
			}
		});
   }
   
   function editinit(index){
	   var record= gridObj.getRecord(index);
	    $("#menuGroup_ID").val(record.menuGroupID);
	   	$("#menugroup_name").val(record.menuGroupName);
		$("#menugroup_remark").val(record.remark);
		$("#menugroup_no").val(record.sortCode);
   }
   
   //删除.
   function deleteinfo(index){
	   var record= gridObj.getRecord(index);
	   $.ajax({
			type:"POST",
			url:"<%=basePath %>distributor/menuGroupinfo/deleteinfo",
			dataType:"html",
			data:{menuGroupID:record.menuGroupID},
			success:function(data){
				if(data=="1"){
					easy2go.toast('info',"删除成功");
					gridObj.refreshPage();
				}else{
					easy2go.toast('info',"删除失败");
				}
			}
	   });
   }
   
   function os(){
	   var cs=$("#slect_ico").val();
	   $("#tez").attr("class",cs);
   }
   
   $(document).keydown(function(event){
       if(event.keyCode == 13){//绑定回车
           $("#menuGroupName_select").blur();
           gridObj.options.otherParames =$('#leibie').serializeArray();
           gridObj.page(1);
           event.returnValue = false;
       }
   });
</SCRIPT>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>