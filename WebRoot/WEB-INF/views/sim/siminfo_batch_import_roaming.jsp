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
    <title>批量导入漫游SIM卡-SIM卡管理-流量运营中心</title>
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
<div class="panel-heading">批量导入漫游SIM卡</div>
<div class="panel-body">


<div class="panel">
  <div class="panel-heading"><SMALL>使用Excel文档批量导入SIM卡信息</SMALL></div>
   <div class="panel-body">
    <form id="import_form" class="form-horizontal" action="" method="post" enctype="multipart/form-data">

            <div class="form-group">
              <label class="col-sm-3 control-label">更新方式：</label>
              <div class="col-sm-6">
                  <label for="importAllCols" class="radio-inline">
                  <input type="radio" name="importType" id="importAllCols" value="0" data-popover-offset="0,235">更新全部字段</label>
                  <label for="importIccidPhone" class="radio-inline">
                  <input type="radio" name="importType" id="importIccidPhone" value="1">仅更新ICCID和PHONE</label>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 control-label">SIM使用类型：</label>
                <div class="col-md-6">
                    <select name="SIMCategory" required class="form-control" readonly="readonly" disabled>
                      <option value="0">本地卡</option>
                      <option value="1" selected="selected">种子卡</option>
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
              <div class="col-sm-3">
              <div class="btn-toolbar"><button type="button" onclick="javascript:clearAllRoamingSim();"class="btn btn-danger">导入前清除种子卡</button></div>
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
<p>导入要求、规则更改历史： 种子卡与本地卡有很大的不同, 所以分开为不同的模板。(1) 种子卡根据 ICCID 来添加记录;
(2) 若需要支持更新种子卡, 需要开发人员处理, 方法见后;
(3) <del>目前种子卡导入只有"新增", 不涉及"更新", 若模板中带有已经导入的数据, 则该行数据会提示为"插入失败". 所以,
为了减少重复插入这种情况影响不好分辨哪些插入成功或失败, 请保证每次添加的数据在单独一个xls档, 或者不理会,
但务必最后仔细检查提示为"插入失败"的数据行.</del> 若后期需要考虑更新, 请联系开发打开一个开关, 或扩展为可配置.
<strong>最近更新：按需求现在已经打开了支持更新。若该 ICCID 已存在，则该条记录为更新，否则为新增</strong>
<br /><br />注意：“导入前清除种子卡”这个功能系何广超根据种子卡管理实际情况提出的需求，在导入前手动执行将清除全部种子卡信息！请只有在清楚问题情况下再继续清。原则上，
应该不要使用此功能，因为平时可能已经单个编辑了若干种子卡的状态，若导入覆盖就丢失那些编辑了。目前也还未有导出功能。请相关人员逐渐完善种子卡的文档管理，避免问题出现</p>
</div>

<p>1. 批量导入数据只支持 excel 2003版本 ，具体格式请按照 2中的地址进行下载</p>
<p>2. excel下载地址：<a href="http://yun.baidu.com/s/1gdpm7vH" target="_bank">http://yun.baidu.com/s/1gdpm7vH</a> 或者访问公司FTP>培训资料>批量导入模板 </p>
<p>3. 请不要修改excel的格式，在对应的列加上需要导入的数据即可</p>
<p>4. "无效数据"指缺少了必要的 <strong class="red">"ICCID"</strong> 列值或格式不对(长度不是19位). 还要注意"序号"列必须正确提供, 否则该记录会被完全忽略.</p>
<p>5. "插入失败"的原因可能有: 与数据库原记录重复(指 ICCID ), 数据库内部出错等.</p>
<p>6. 因关键字段缺失被强制设置'SIM卡状态'为'不可用', 即表示此记录已添加到数据库, 但若要正常使用必须去手动编辑, 补上必要字段参数.</p>
<p>7. 上面出错的记录都已列举出其序号, 请保存好此日志信息, 及时处理后再尝试导入.</p>
<p>8. 这些数量之间的关系: 无效数据条数 + 成功新插入条数 + 插入失败条数 + 成功更新条数 + 失败更新条数 = 原始记录总数 </p>
<p>9. <strong>重要！：</strong>现在发现导入容易失败的原因系日期列的格式，所以 Excel 档在编辑最后有必要检查并统一设置日期列的
“单元格格式”，见下面示意图：<div style="border:1px solid #eeeeee"><img src="<%=basePath %>static/images/date-format-error-notice.png" alt="单元格格式示意截图"></img></div></p>

</div>

<!-- ============================================================ -->
</div>

</div>
</div>
</section>
</section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script>
    bootbox.setDefaults("locale","zh_CN");

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
            'importType':{ required:true },
        },
        messages:{
            'file':{required:"请选择要导入的excel文件！"},
            'importType':{ required:"请确认更新的列范围" },
        },
        submitHandler: function(form){
        	// 创建动态进度条
        	$('#result').empty().append('操作正在进行，请等候. . . ');
            timeID = setTimeout(function() {
                updateProgress();
            }, 1000);

        	var action = "<%=basePath %>sim/roamingsiminfo/startImportRoamingSim?simcategory=" + $('select[name="SIMCategory"]').val()
        	    + "&importType=" + $("input[name='importType']:checked").val();
        	form.action = action;
            form.submit();
        }
    });

    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    function clearAllRoamingSim() {
    	bootbox.confirm("这个功能系何广超根据种子卡管理实际情况提出的需求，在导入前手动执行将清除全部种子卡信息！请只有在清楚问题情况下再继续清除，并且做好旧备份！确定继续?", function(result) {
            if(result){
            	bootbox.confirm("继续操作将清除目前系统止全部种子卡信息！请确保旧的 Excel 种子卡表已备份！请进行二次确认！否则取消返回", function(result) {
            		if(result){
            			$.ajax({
                            type:"POST",
                            url:"<%=basePath %>sim/roamingsiminfo/clearAll",
                            dataType:"html",
                            //data:$('#edit_form').serialize(),
                            success:function(data){
                                result = jQuery.parseJSON(data);
                                if (result.code == 0) { // 成功保存
                                    easy2go.toast('info', result.msg);
                                } else {
                                    easy2go.toast('error', result.msg);
                                }
                            }
                        });
            		}
            	});
            }
    	});
    }
    </script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
  </body>
</html>