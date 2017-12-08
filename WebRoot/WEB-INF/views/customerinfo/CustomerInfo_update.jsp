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
    <title>编辑客户-客户管理-EASY2GO ADMIN</title>
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

<!-- ======================================================================================== -->
  <SECTION id="main-content">
    <SECTION class="wrapper">
<!--      <DIV class="col-md-3"> -->
<!--        <DIV class="panel"> -->
<!--           <DIV class="panel-body"> -->
  
<!--  <FORM class="form-inline" role="form" id="sendSearch" method="get" action=""> -->
<!--       <DIV class="form-group"> -->
<!--          <LABEL class="inline-label">用户名称：</LABEL> -->
<!--          <INPUT class="form-control" name="customerName" type="text" placeholder="用户名称" id="customerdata"> -->
<!--       </DIV> -->
      
<!--       <DIV class="form-group"> -->
<!--          <BUTTON class="btn btn-primary" type="submit" onclick="return searchCustomer();">搜索</BUTTON> -->
<!--       </DIV> -->
<!--   </FORM> -->
<!--   <script type="text/javascript"> -->
<!-- //       function searchCustomer(){ -->
<!-- //     	  var resultdata =  document.getElementById('customerdata').value; -->
<!-- //     	  if(resultdata==""){ -->
<!-- //     	      var menu; -->
<!-- //     	      easy2go.responseMsg.info = "搜索信息不能为空!".trim(); -->
<!-- //     	      easy2go.responseMsg.warn = "".trim(); -->
<!-- //     	      easy2go.responseMsg.error = "".trim(); -->
<!-- //     	      activeMenus(); -->
<!-- //     	      setTimeout(function(){ -->
<!-- //     	      	easy2go.showResponseMSG();	 -->
<!-- //     	      },350); -->
<!-- //     	      return false; -->
<!-- //     	  }else{ -->
<%--         	  var resEnd ="<%=basePath %>customer/customerInfolist/SearchCustomerInfo?cusdata="+resultdata; --%>
<!-- //         	  document.getElementById("sendSearch").action=resEnd;	   -->
<!-- //     	  }return true; -->
<!-- //       } -->
<!--   </script> -->
<!--          </DIV> -->
<!--        </DIV> -->
<!--      </DIV> -->
     
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">编辑客户信息</DIV>
<DIV class="panel-body">
  <input type="hidden" id="uname" value="${sessionScope.User.userName}"/>
 <FORM  action="" method="post" autocomplete="off" id="client_form" class="form-horizontal" role="form">
  
   <INPUT name="_csrf" value="PoChPFtn-rZ2lhI67fexQ2ndBrS60U5bFM-s" type="hidden">
   <INPUT name="_method" value="put" type="hidden"> 
   
   <DIV class="form-group">
      <LABEL class="col-md-3 control-label" for="client_name">称呼：</LABEL>
      <DIV class="col-md-6">
          <INPUT id="client_name" class="form-control" name="customerName" value="${cus.customerName}" type="text" required data-popover-offset="0,8">
          <input style="display:none;" type="text" name="customerID" value="${cus.customerID}">
          <input type="hidden" id="customername" value="${cus.customerName}"/>
     </DIV>
   </DIV>
   
   <DIV class="form-group">
     <LABEL class="col-md-3 control-label" for="client_phone">手机：</LABEL>
     <DIV class="col-md-6">
        <INPUT id="client_phone" class="form-control" name="phone" value="${cus.phone}" type="text" required data-popover-offset="0,8">
        <INPUT id="phone" value="${cus.phone}" type="hidden">
     </DIV>
   </DIV>
   <DIV class="form-group">
    <LABEL class="col-md-3 control-label" for="client_customerSource">客户类型：</LABEL>
  <DIV class="col-md-6">
         <select class="form-control" id="customerType" name="customerType" data-popover-offset="0,8">
         <c:if test="${cus.customerType ne null }">
            <c:if test="${cus.customerType eq '重要客户' }">
            	<option value="重要客户">重要客户</option>
            	<option value="普通客户">普通客户</option>
            	<option value="合作伙伴">合作伙伴</option>
            </c:if>  
             <c:if test="${cus.customerType eq '普通客户' }">
            	<option value="普通客户">普通客户</option>
            	<option value="合作伙伴">合作伙伴</option>
            	<option value="重要客户">重要客户</option>
            </c:if>  
             <c:if test="${cus.customerType eq '合作伙伴' }">
            	<option value="合作伙伴">合作伙伴</option>
            	<option value="重要客户">重要客户</option>
            	<option value="普通客户">普通客户</option>
            </c:if>
          
         </c:if>
              <c:if test="${empty  cus.customerType}">
            	<option value="合作伙伴">合作伙伴</option>
            	<option value="重要客户">重要客户</option>
            	<option value="重要客户">普通客户</option>
            </c:if>
         </select>
  </DIV>
</DIV>
   <DIV class="form-group">
     <LABEL class="col-md-3 control-label" for="client_phone">Email：</LABEL>
     <DIV class="col-md-6">
        <INPUT id="client_phone" class="form-control" name="email" value="${cus.email}" type="text" data-popover-offset="0,8">
        <INPUT id="phone" value="${cus.email}" type="hidden">
     </DIV>
   </DIV>

   <DIV class="form-group">
     <LABEL class="col-md-3 control-label" for="client_address0">收货地址：</LABEL>
     <DIV class="col-md-6">
       <INPUT id="client_address0" class="form-control" name="address" value="${cus.address} " type="text" data-popover-offset="0,8"/>
       <INPUT id="address" value="${cus.address} " type="hidden"/>
     </DIV>
  </DIV>
  
 <DIV class="form-group">
     <LABEL class="col-md-3 control-label" for="client_address0">是否是VIP用户：</LABEL>
     <DIV class="col-md-6">
<!--        <select class="form-control" id="isVIP" name="isVIP"> -->
<!--           <option>否</option> -->
<!--           <option>是</option> -->
<!--        </select> -->
       
       <select class="form-control" id="isVIP" name="isVIP" data-popover-offset="0,8">
         <c:if test="${cus.isVIP ne null }">
            <c:if test="${cus.isVIP eq '否' }">
            	<option value="否">否</option>
            	<option value="是">是</option>
            </c:if>  
             <c:if test="${cus.isVIP eq '是' }">
            	<option value="是">是</option>
            	<option value="否">否</option>
            </c:if>
         </c:if>
         <c:if test="${empty  cus.isVIP}">
            	<option value="是">是</option>
            	<option value="否">否</option>
          </c:if>
         </select>
         
         
     </DIV>
  </DIV>
  
  <DIV class="form-group">
    <LABEL class="col-md-3 control-label" for="client_desc">备注：</LABEL>
    <DIV class="col-md-6">
      <textarea rows="3" cols="20" id="client_desc" class="form-control"  name="remark">${cus.remark}</textarea>
      <input id="remark" value="${cus.remark}" type="hidden" />
     </DIV>
  </DIV>
  
<DIV class="form-group">
<DIV class="col-md-6 col-md-offset-3">
<DIV class="btn-toolbar">
  <BUTTON class="btn btn-primary" type="submit">保存</BUTTON>
  <BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">取消</BUTTON>
</DIV>
</DIV>
</DIV>
</FORM>
</DIV></DIV></DIV></SECTION></SECTION>
<!-- ======================================================================================== -->
 
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
<script type="text/javascript">
    var redirect = "<%=request.getHeader("Referer") %>"; <%-- "<%=basePath %>customer/customerInfolist/all"; --%>
</script>
    <script type="text/javascript">
/*     $(function(){
    	alert("1");
     	 var cusType = "${cus.customerType}";
     	    //$("#customerType").find("option[text='${cus.customerType}']").attr("selected",true);
     	   $("#customerType option[text='${cus.customerType}']").attr("selected", true);
     	   // alert( $("#customerType").val());
     	   alert("1");
    }); */
    $("#client_form").validate_popover({
        rules: {
          'customerName': {
            required: true,
            maxlength: 50
          },
//            'password': {
//             required: true,
//             minlength: 6,
//             maxlength: 50
//           },
          'email': {
            required: false,
            email: true,
            maxlength: 20
          },
//           'customerSource': {
//               required: true,
//           },
//           'distributorName': {
//               required: false,
//               maxlength: 50
//           },
          'address': {
            required: false,
            maxlength: 100
          },
          'remark': {
            required: false,
            maxlength: 99
          }
        },
        messages: {
// 这里不添加, 使用默认提示字符串        	
//           'customerName': {
//             required: "请输入单位名称.",
//             maxlength: "最多不超过50个字符."
//           },
          'remark': {
            maxlength: "最多不超过100字"
          }

        },
          submitHandler: function(form){
  	        $.ajax({
  	            type:"POST",
  	            url:"<%=basePath %>customer/customerInfolist/updateToCustomerInfo",
  	            dataType:"html",
  	            data:$('#client_form').serialize(),
  	            success:function(data){
  	                result = jQuery.parseJSON(data);
  	                if (result.code == 0) { // 成功保存
  	                    easy2go.toast('info', result.msg);
	                   if(redirect==null) {
	                     window.location.href="<%=basePath %>customer/customerInfolist/all";
	                   } else {
	                     window.location.href=redirect;
	                   }
  	                } else {
  	                    easy2go.toast('error', result.msg);
  	                }
  	            }
  	        });
          }
      });
    
//     function datack()
//     {
    
//        var uname = document.getElementById('uname').value;
//        if(uname==""){
//     	   alert("请输入用户名称");
//     	   return false;
//        }else if(uname!=""){
// //     	   alert(uname);
//     	   return true;
//        }
       
//        var name = document.getElementById('customerName').value;
//        var phone = document.getElementById('phone').value;
//        var address = document.getElementById('address').value;
//        var remark = document.getElementById('remark').value;
       
//        var client_name = document.getElementById('client_name').value;
//        var client_phone = document.getElementById('client_phone').value;
//        var client_address0 = document.getElementById('client_address0').value;
//        var client_desc = document.getElementById('client_desc').value;
       
//        alert(name+phone+'==='+address+'==='+remark+'==='+client_name+'==='+client_phone+'==='+client_address0+'==='+client_desc);
//        return false;
// //        if(name.equals(client_name)){
// //     	   return true;
// //        }else{
// //       	   alert('您没有修改任何数据！');
// //     	   return false; 
// //        }
       
//     }       
       
    </script>	
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
 
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>