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
    <title>批量导入设备-设备管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
  </head>
   <script>
function getFullPath(obj)
{
    if(obj)
    {
        //ie
        if (window.navigator.userAgent.indexOf("MSIE")>=1)
        {
            obj.select();
            return document.selection.createRange().text;
        }
        //firefox
        else if(window.navigator.userAgent.indexOf("Firefox")>=1)
        {
            if(obj.files)
            {
                try
                {
                     netscape.security.PrivilegeManager.enablePrivilege( 'UniversalFileRead' )
                 }
                 catch (err) {
                     //need to set signed.applets.codebase_principal_support to true
                 }
                 return obj.value;
            }
            return obj.value;
        }
        return obj.value;
    }
}
 </script>
 <body>

    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

<SECTION id="main-content" style="">
<SECTION class="wrapper">
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">批量导入设备</DIV>
<DIV class="panel-body">


        <DIV class="panel">
          <DIV class="panel-heading"><SMALL>使用Excel文档批量导入设备</SMALL></DIV>
           <DIV class="panel-body">
<%--              <span style="border:0px solid pink;font-size:14px;color:red;padding-left:50%;">${excelmessage}</span>--%>
            <form id="import_form" class="form-horizontal" action="<%=basePath %>device/data/startInsert" method="post" enctype="multipart/form-data">
<%--                 <LABEL class="col-md-3 control-label">批量导入：</LABEL>--%>
<%--                 <input class="form-control" id="file" style="width:400px;" type="file" name="file"/><br/>--%>
<%--               <div> style="margin-left:45%" --%>
<%--                  <BUTTON type="submit" class="btn btn-primary" onclick="return trim_ck();">  提    交 </BUTTON>--%>
<%--                  <BUTTON  class="btn btn-default" onclick="javascript:history.go(-1);" type="button">  返    回 </BUTTON>--%>
<%--               </div>--%>

            <div class="form-group">
              <label class="col-sm-3 control-label">批量导入：</label>
              <div class="col-sm-9">
                <input type="file" name="file" id="file" data-popover-offset="0,8" required class="form-control">
              </div>
            </div>
           <div class="form-group">
              <div class="col-sm-6 col-sm-offset-3">
                <div class="btn-toolbar">
                  <button type="submit" class="btn btn-primary">提交</button>
                  <button type="button" onclick="javascript:history.go(-1);"class="btn btn-default">返回</button>
                </div>
              </div>
            </div>

            </form>

    <div class="row">
    <div class="well">
    <span style="border:0px solid pink; font-size:14px; color:red;">${LogMessage}</span>
    </div>
    </div>

          </DIV>
        </DIV>

<div>
注意：<br><br>
<p>1.批量导入数据只支持 excel 2003版本，具体格式请按照 2中的地址进行下载</p>
<p>2.excel下载地址：<a href="http://yun.baidu.com/share/link?shareid=199962598&uk=2519258044" target="_bank">http://yun.baidu.com/share/link?shareid=199962598&uk=2519258044</a></p>
<p>3.请不要修改excel的格式，在对应的列加上需要导入的数据即可. excel档的文件名可以使用中文</p>
<p>4. "无效数据"指缺少了必要的"设备SN"列值. 还要注意"序号"列必须正确提供, 否则该记录会被完全忽略</p>
<p>5. "插入失败"的原因可能有: 与数据库原记录重复(如设备SN), 数据库内部出错等</p>
<p>6. 上面出错的记录都已列举出其序号, 请保存好此日志信息, 及时处理后再尝试导入</p>
<p>7. 如果对已存在的设备进行导入 ，会覆盖掉之前的数据，例如:excel中填写SN和设备颜色，只会根据SN来修改设备颜色其他字段不变</p>
<p>8. 这些数量之间的关系: 无效数据条数 + 成功导入条数 + 插入失败条数  + 覆盖条数= 原始记录总数 </p>
</div>

<!-- ============================================================ -->
</DIV>

</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script>
<%--    function trim_ck(){--%>
<%--    	var files = document.getElementById("file").value;--%>
<%--    	if(files==""){--%>
<%--    		easy2go.toast('info',"请选择要导入的excel文件！");--%>
<%--    		return false;--%>
<%--    	}--%>
<%--    	--%>
<%--    	return true;--%>
<%--    }--%>

    $("#import_form").validate_popover({
        rules:{
            'file':{ required:true },
        },
        messages:{
            'file':{required:"请选择要导入的excel文件！"},
        },
        submitHandler: function(form){
            form.submit();
        }
    });

    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
<%--      $(function(){--%>
<%--      });--%>
    </script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
  </body>
</html>