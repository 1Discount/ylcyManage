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
    <title>批量导入本地SIM卡-SIM卡管理-流量运营中心</title>
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

<section id="main-content" style="">
<section class="wrapper">
<div class="col-md-12">
<div class="panel">
<div class="panel-heading">批量导入本地SIM卡</div>
<div class="panel-body">


<div class="panel">
  <div class="panel-heading"><SMALL>使用Excel文档批量导入SIM卡信息</SMALL></div>
   <div class="panel-body">
    <form id="import_form" class="form-horizontal" action="" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label class="col-md-3 control-label">SIM使用类型：</label>
                <div class="col-md-6">
                    <select name="SIMCategory" required class="form-control" readonly="readonly" disabled>
                      <option value="0" selected="selected">本地卡</option>
                      <option value="1">种子卡</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">批量导入：</label>
              <div class="col-sm-8">
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
    <span id="result" style="border:0px solid pink; font-size:14px; color:red;">${LogMessage}</span>
    </div>
    </div>
  </div>
</div>

<div>
注意：<br><br>

<div class="well red">
<p>导入要求、规则更改历史： (2015-08-27) ICCID 可能不唯一，所以改用 IMSI 号作必要字段。并且：（1）一般情况是这样，先插入一系列SIM卡后服务器
会读取出 IMSI 和 ICCID 号，然后该系列卡需要从这里批量导入必要的 SIM 卡信息之后，才能正常使用。（2） 少数情况，可到SIM卡编辑手动添加SIM卡
或者，插入 SIM 卡后，从全部SIM卡列表查询到该卡，再进行手动编辑。（3）这样，批量导入模板需要以 IMSI 号作必要字段，即必须提供 IMSI 才能正常
导入。<br />（4） <strong>注意！！！</strong>因为“更新“系直接覆盖的，所以为避免存在错误覆盖使用中的SIM卡的情况，请保证明确更新的数据放在单独的
模板，并且新增数据和更新数据分开也系一个好推荐。</p>
</div>

<p>1. 批量导入数据只支持 excel 2003版本 ，具体格式请按照 2中的地址进行下载</p>
<p>2. excel下载地址：<a href="http://yun.baidu.com/s/1gdpm7vH" target="_bank">http://yun.baidu.com/s/1gdpm7vH</a> 或者访问公司FTP>培训资料>批量导入模板 </p>
<p>3. 请不要修改excel的格式，在对应的列加上需要导入的数据即可</p>
<p>4. "无效数据"指缺少了必要的 <del>"ICCID"</del> <strong class="red">"IMSI"</strong> 列值或格式不对(目前IMSI为18位). 还要注意"序号"列必须正确提供, 否则该记录会被完全忽略.</p>
<p>5. "插入失败"的原因可能有: 与数据库原记录重复(指 <del>ICCID或</del> IMSI ), 数据库内部出错等，“更新失败”的原因可能有数据库内部出错、字段的格式可能不符等，如IMEI系15位数字，若输入包含非数字的字符，则失败.</p>
<p>6. 因关键字段缺失被强制设置'SIM卡状态'为'不可用', 即表示此记录已添加到数据库, 但若要正常使用必须去手动编辑, 补上必要字段参数.</p>
<p>7. 上面出错的记录都已列举出其序号, 请保存好此日志信息, 及时处理后再尝试导入.</p>
<p>8. 这些数量之间的关系: 无效数据条数 + 成功新插入条数 + 插入失败条数 + 成功更新条数 + 失败更新条数 = 原始记录总数 </p>
</div>

<!-- ============================================================ -->
</div>

</div>
</div>
</section>
</section>
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

var timeID;
function updateProgress(){
    $('#result').append('. ');
    timeID = setTimeout(function() {
        updateProgress();
    }, 1000);
}

    $("#import_form").validate_popover({
        rules:{
            'file':{ required:true },
        },
        messages:{
            'file':{required:"请选择要导入的excel文件！"},
        },
        submitHandler: function(form){
        	// 创建动态进度条
            $('#result').empty().append('操作正在进行，请等候. . . ');
            timeID = setTimeout(function() {
                updateProgress();
            }, 1000);

        	var action = "<%=basePath %>sim/siminfo/startImport?simcategory=" + $('select[name="SIMCategory"]').val();
        	form.action = action;
            form.submit();
        }
    });

    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
<%--      $(function(){--%>
<%--      });--%>
    </script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>