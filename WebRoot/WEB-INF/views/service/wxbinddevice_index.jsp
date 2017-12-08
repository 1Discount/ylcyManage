<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>全部-微信绑定设备关系管理-EASY2GO ADMIN</title>
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
      <section id="main-content">
        <section class="wrapper">
          <%-- 这里添加页面主要内容  block content --%>          
		<div class="col-md-12">
	
        <div class="panel">
<%--        <div class="panel-heading"><h3 class="panel-title">请先选取范围</h3></div>--%>
        <div class="panel-body">
            <form class="form-inline" id="searchForm" role="form" method="get" action="#">
            <div class="form-group">
              <label class="inline-label">设备序列号SN：</label>
              <input type="text" name="sn" style="width: 173px;" placeholder="SN" class="form-control" data-popover-offset="10,-105">
            </div>
            <div class="form-group"><label class="inline-label">选取绑定时间段： 从</label>
<%--            <label for="dateRangeType0" class="radio-inline">--%>
<%--                  <input type="radio" name="dateRangeType" id="dateRangeType0" value="all" checked>全部时间</label>--%>
<%--            <label for="dateRangeType1" class="radio-inline">--%>
<%--                  <input type="radio" name="dateRangeType" id="dateRangeType1" value="selected">选取特定时间: </label>--%>                  
            <input id="beginTime" class="form-control form_datetime" 
            name="beginTime" type="text" placeholder="开始时间"
            data-date-format="YYYY-MM-DD" data-popover-offset="10,-105"></div>
            
            <div class="form-group"><label class="inline-label">到</label>
            <input id="endTime" class="form-control form_datetime" 
            name="endTime" type="text" placeholder="结束时间"
            data-date-format="YYYY-MM-DD" data-popover-offset="10,-105"></div>
        
            <div class="form-group">
            <button class="btn btn-primary" 
            type="submit" onclick="validateForm()">搜索</button>
            </div>
            </form>
        </div>
        </div>
        
		<div class="panel">
		<div class="panel-heading">
		<h3 class="panel-title">全部微信绑定设备关系</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
		            <th w_index="bindID" w_hidden="true" width="10%;" >ID</th>
		        	<th w_index="openid" width="15%;">Open ID</th>
		            <th w_index="sn" width="5%;">设备SN</th>
                    <th w_index="bindDate" width="5%;" w_sort="bindDate,disorder">绑定时间</th>
		            <th w_index="unbindDate" width="10%;" >解绑时间</th>
 		            <th w_render="render_bindStatus" width="10%;" w_sort="bindStatus,disorder">绑定状态</th>
<%-- TODO: 目前暂去掉下面这个时间. 使用有赞平台后, 再无法直接获取用户最后的回复时间, 可以添加多一些互动网页, 
通过网页授权去获取类似的一个时间--%>
<%--                    <th w_index="userMsgDate" width="10%;" w_sort="userMsgDate,desc">客户最后回复时间</th>--%>
<%-- 不需要		        <th w_index="pushable" width="10%;">是否可回复消息<a href="#special-note">(注1,2)</a></sup></th>--%>
 		            <th w_index="remark" width="10%;">备注</th> 		            
		            <th w_render="operate" width="20%;">操作</th>
		        </tr>
		</table></div>
		</div></div>
		      <div id="special-note">备注：<br><br>
<p>1. "最后回复时间"指客户最后发消息到微信公众号的时间, 可根据此48小时去确定是否可直接回复微信的普通消息。</p>
<p>2. 微信上的消息分"普通消息"和"模板消息", 前者系关注的客户主动发送消息到公众号, 在48小时内公司微信号可被动回复任意条任意内容给客户。
而"模板消息"是根据模板填充参数, 可在一定条件下任意时间推送给关注的客户微信号. 目前日可发模板消息量为10万条.</p>
<p>3. 关于相关, 可参考微信开发文档的"发送消息"-<a target="_blank" href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html">"客服接口"</a>, 
<a target="_blank" href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html">"模板消息"</a></p>
<p>4. 表格以下列可组合排序: 绑定时间 绑定关系 客户最后回复时间(默认)</p>
<p>5. 开发相关: 因为微信后台没有提供获取模板消息的详情的API, 所以暂时只能够从公众号后台查看到模板ID和详情信息之后, 
由开发再添加到本页面. 开发请按照"div id=sendTemplateMsgForm"的相关内容对照添加. 大概系几步:<br />
　　(1) select name="template_id" 中添加 option 选项<br />
　　(2) 复制 div class="t-section" 整段对应修改, 注意 t-section 出现顺序与前面的 option 出现顺序保持一致.
一般只需要改各参数的 value(默认值) 和 input name="params" 各参数的 id , 还有从公众号后台复制模板内容样例过来.
</p>
<p>6. [20151118]. 使用有赞平台后, 再无法直接准确获取用户的最后回复时间. 那暂设定初绑定时的48小时之内, 可任意发送"普通消息".
以后考虑增加多一些网页互动, 通过增加更多的网页通过网页授权去获取一个较准确的用户互动时间.</p>
            </div>
		</div>

    
<div id="dialogSendMsgForm" style="display:none;"><%--普通消息弹出框内容 class="hidden"--%>
    <%--警告,这里面的内容勿使用id去引用,请使用例如bootbox弹出窗的部件限制+class或其他去引用--%>
    <div class="row"><div class="col-md-12">
    <form class="form-horizontal sendMsgForm">
    <input type="hidden" name="touser" />
    <input type="hidden" name="msgtype" value="text" /><%--目前只需要text类型--%>
    <div class="form-group">
    <label class="col-md-12 control-label" style="text-align:left;">目前这里仅需要发送文本text类型消息, 请输入消息内容. 若要与"短信发送"中的内容相通, 则后续跟进:</label> 
    </div>
    <div class="form-group">
    <label class="col-md-12 control-label" style="text-align:left;">发送给: openid <span class="text-warning h4 openid-label"></span> 设备SN <span class="text-warning h4 sn-label"></span></label> 
    </div>
    <div class="form-group">
    <div class="col-sm-12">
    <textarea name="content" rows="5" maxlength="499" data-popover-offset="0,8" class="form-control msgContent"></textarea></div>
    </div>
    </form>
    </div></div>
</div>

<div id="dialogSendTemplateMsgForm" style="display:none;"><%--模板消息弹出框内容 class="hidden"--%>
    <%--警告,这里面的内容勿使用id去引用,请使用例如bootbox弹出窗的部件限制+class或其他去引用--%>
    <div class="row"><div class="col-md-12">
    <form class="form-horizontal sendTemplateMsgForm">
    <input type="hidden" name="touser" value="" />
    <div class="form-group">
    <label class="col-md-12 control-label" style="text-align:left;">模板消息需要按模板中的参数和样例填写各参数值. 后期开发人员添加时按照约定的步骤即可. 目前参数值暂不处理颜色, 都默认为UI主色</label> 
    </div>
    <div class="form-group">
    <label class="col-md-12 control-label" style="text-align:left;">发送给: openid <span class="text-warning h4 openid-label"></span> 设备SN <span class="text-warning h4 sn-label"></span></label> 
    </div>
    
            <div class="form-group">
              <label class="col-sm-3 control-label">模板名称：</label>
              <div class="col-sm-9">
                <select name="template_id" required class="form-control">
                <!-- 注意,务必保证下面t-section出现顺序对应的表单与这里option的顺序一致 -->
                <option value="l5GlNOdWcB9pAYB9YvbnT3zgXOlG2MmaJpfSKCb64a0">流量阀值预警通知</option>
                <option value="MFULnRADxpDrFurjzFA6Dq6ZQ5O700gKrA8_lcy9ks0">途狗低电量通知</option>
                <option value="NaAvMeYjOC6_JrHIkW2Czq-dL6O8KzOWZO1ga5ro44k">阀值预警通知(测试公众号)</option>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">URL网址：</label>
              <div class="col-sm-9">
                <input type="text" name="url" value="http://mp.weixin.qq.com/bizmall/mallshelf?id=&t=mall/list&biz=MzA4MjE3MDk5NQ==&shelf_id=8&showwxpaytitle=1#wechat_redirect" data-popover-offset="0,8" class="form-control" />
              </div>
            </div>
    </form><%--以上为公共部分表格--%>

            <div class="t-section"><!-- 模板1 阀值预警通知-->
            <form class="form-horizontal sendTemplateMsgFormSub">
                <div class="form-group">
	              <label class="col-sm-3 control-label">参数 first：</label>
	              <div class="col-sm-9"><%--注意下面的id仅用来传递参数名称值,不作他用--%>
	                <input type="text" name="params" id="first" value="请留意低流量预警" data-popover-offset="0,8" class="form-control" />
	              </div>
	            </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 system：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="system" value="Wifi流量" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 time：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="time" value="2015-10-27 15:30:20" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 account：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="account" value="4" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 remark：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="remark" value="请及时通过微信平台，途狗官网和手机应用充值。" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>

			    <div class="form-group">
			    <div class="col-sm-6"><!-- 模板内容 --><pre>
{{first.DATA}}

系统名称：{{system.DATA}}
截止时间：{{time.DATA}}
报警次数：{{account.DATA}}
{{remark.DATA}}
			    </pre></div>
			    <div class="col-sm-6"><!-- 模板样例 --><pre>
请注意！

系统名称：BSS系统免费资源模块
截止时间：2013-11-21 17:30
报警次数：36次
请确认报障原因。
                </pre></div>
			    </div>
			</form>
		    </div>
		    
            <div class="t-section"><!-- 模板2 设备低电量提示-->
            <form class="form-horizontal sendTemplateMsgFormSub">
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 first：</label>
                  <div class="col-sm-9"><%--注意下面的id仅用来传递参数名称值,不作他用--%>
                    <input type="text" name="params" id="first" value="您绑定的途狗设备电量过低" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 keyword1：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="keyword1" value="172150210001348" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 keyword2：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="keyword2" value="10%" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 keyword3：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="keyword3" value="2015-10-27 15:30:20" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 remark：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="remark" value="请尽快充电，保障设备正常运行。" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>

                <div class="form-group">
                <div class="col-sm-6"><!-- 模板内容 --><pre>
{{first.DATA}}
设备名称：{{keyword1.DATA}}
当前电量：{{keyword2.DATA}}
时间：{{keyword3.DATA}}
{{remark.DATA}}
                </pre></div>
                <div class="col-sm-6"><!-- 模板样例 --><pre>
您绑定的安全设备电量过低
设备名称：小明
当前电量：10%
时间：2014年7月21日 18:36
请尽快充电，保障设备正常运行。
                </pre></div>
                </div>
            </form>
            </div> 
		    
            <div class="t-section"><!-- 模板16 阀值预警通知(测试公众号)-->
            <form class="form-horizontal sendTemplateMsgFormSub">
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 first：</label>
                  <div class="col-sm-9"><%--注意下面的id仅用来传递参数名称值,不作他用--%>
                    <input type="text" name="params" id="first" value="请留意低流量预警" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 system：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="system" value="Wifi流量" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 time：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="time" value="2015-10-27 15:30:20" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 account：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="account" value="4" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">参数 remark：</label>
                  <div class="col-sm-9">
                    <input type="text" name="params" id="remark" value="请及时通过微信平台，途狗官网和手机应用充值！" data-popover-offset="0,8" class="form-control" />
                  </div>
                </div>

                <div class="form-group">
                <div class="col-sm-6"><!-- 模板内容 --><pre>
{{first.DATA}}

系统名称：{{system.DATA}}
截止时间：{{time.DATA}}
报警次数：{{account.DATA}}
{{remark.DATA}}
                </pre></div>
                <div class="col-sm-6"><!-- 模板样例 --><pre>
请注意！

系统名称：BSS系统免费资源模块
截止时间：2013-11-21 17:30
报警次数：36次
请确认报障原因。
                </pre></div>
                </div>
            </form>
            </div>
		    
<%--    </form> 发送模板消息需要细分表格--%>
    </div></div>
</div>
              
        </section>
      </section>
    </section>
    
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    
    $("#beginTime").datetimepicker({
        pickDate: true,                 //en/disables the date picker
        pickTime: false,                 //en/disables the time picker
        showToday: true,                 //shows the today indicator
        language:'zh-CN',                  //sets language locale
        //defaultDate: moment().add(-1, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
        defaultDate: moment().add(-7, 'days'),
    });
     
     $("#endTime").datetimepicker({
        pickDate: true,                 //en/disables the date picker
        pickTime: false,                 //en/disables the time picker
        showToday: true,                 //shows the today indicator
        language:'zh-CN',                  //sets language locale
        defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
    });
    
     $("#searchForm").validate_popover({
         rules:{
             //'SN':{ required:true },
             'beginTime':{ required:true },
             'endTime':{ required:true },
         },
         messages:{
             //'SN':{required:"请输入SN"},
             'beginTime':{required:"请选择开始时间"},
             'endTime':{required:"请选择结束时间"},
         },
         submitHandler: function(form){
             gridObj.options.otherParames =$('#searchForm').serializeArray();
             gridObj.refreshPage();
             $("#b a").attr("href","javascript:void(0);"); // 暂这样处理，没结果时不需要导出
         }       
     });
     
     function validteFrom() {
    	 return false;
    	 $('input').each(function(){$(this).val(jQuery.trim($(this).val()));});
     }
    
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>wx/bindDevice/datapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 15,
                 pageSizeForGrid:[15,30,50,100],
                 multiSort:true
             });
         });
          
          function render_bindStatus(record, rowIndex, colIndex, options) {
              if (record.bindStatus == "绑定") {
                  return '<a class="btn btn-success btn-xs" onclick="return;"><span>绑定</span></a>';
              } else if (record.bindStatus == "解绑") {
                  return '<a class="btn btn-danger btn-xs" onclick="return;"><span>解绑</span></a>';
              } else {
            	  return record.bindStatus;
              }
           }

         function operate(record, rowIndex, colIndex, options) {
            <%-- //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			if (record.bindStatus == "绑定") {
				var result = '<div class="btn-toolbar">'
				    +   '<a class="btn btn-primary btn-xs" onclick="op_unbind(' + rowIndex + ')" href="javascript:void(0);"><span class="glyphicon glyphicon-remove">解绑</span></a>'
				    +   '<a class="btn btn-primary btn-xs" onclick="op_sendTemplate(' + rowIndex + ')" href="javascript:void(0);"><span class="glyphicon glyphicon-share-alt">模板消息</span></a>'
				if(record.pushable == "是"){
					result += '<a class="btn btn-primary btn-xs" onclick="op_sendNormal(' + rowIndex + ')" href="javascript:void(0);"><span class="glyphicon glyphicon-share-alt">普通消息</span></a>';
				}			
				result += '</div>';    
			    return result;
			} else {
				return '已解绑';
			}
         }
         
         function op_unbind(index) {alert($('input[name="sn"]').val());
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确认解绑此微信号与设备吗? <br />openid = " + record.openid + "<br />设备SN = " + record.sn, function(result) {
                 if(result){
                     $.ajax({
                         type:"POST",
                         url:"<%=basePath %>wx/bindDevice/unbind/" + record.bindID,
                         dataType:'html',
                         success:function(data){
                            if(data=="0"){
                                easy2go.toast('success',"微信绑定关系解绑成功!");
                                gridObj.refreshPage();
                            }else if(data=="-1"){
                                easy2go.toast('err',"请提供绑定id!");
                            }else if(data=="-2"){
                                easy2go.toast('err',"微信绑定关系解绑出错! 请重试");
                            }else if(data=="-5"){
                                easy2go.toast('err',"请重新登录!");
                            }else {
                                easy2go.toast('err',"解绑出错, 请重试");
                            }
                         },
                         error:function() {
                        	 easy2go.toast('err',"网络出错, 请重试");
                         }
                     });
                 }
             });
        	
          }

         // select 切换模板表单显示 --> 要整体放到bootbox生成之后!
//         $(function(){
//             $('.t-section').hide(); // 初始化重置显示首个
//             $('.t-section').eq(1).show();            
//             // 对select的操作要放到bootbox生成之后 
//         });
       
         // 使用 $('#dialogSendMsgForm').html() 去获取对话框内容,但可能那样同一页面出现两套同样的代码所以造成里面设定的标签#id失效
         // 所以目前要引用里面的元素, 暂都使用class去处理, 并可使用类似 .modal-body 的去作范围限制
         function op_sendNormal(index) {
        	 var record= gridObj.getRecord(index);
        	 bootbox.dialog({
                 title: "发送普通消息",
                 message: $('#dialogSendMsgForm').html(),
                 buttons: {
                     cancel: {
                         label: "取消",
                         className: "btn-default send-msg-btn-cancel",
                         callback: function () {
                         }
                     },
                     success: {
                         label: "确定发送",
                         className: "btn-success send-msg-btn-ok",
                         callback: function () {
                             //alert($(".modal-body textarea.msgContent").val());
                        	  $.ajax({
                        		  type:"POST",
                        		  url:"<%=basePath %>/wx/bindDevice/sendTextMsg", // http://www.easy2go.cn
                        		  dataType: "html",
                        		  data:$('.modal-body form.sendMsgForm').serialize(),
                        		  success: function(data){
                        			  result = jQuery.parseJSON(data);
                        			  if(result.error=="00"){
                                          easy2go.toast('success',"发送消息成功!");
                                          //gridObj.refreshPage();
                                          $('button.send-msg-btn-cancel').click();
                                      }else{
                                    	  easy2go.toast('err',result.msg);
                                      }
                        		  }
                        		  
                        	  });
                        	  return false; // 到ajax success再判断是否关闭
                         }
                     }
                 }
             });
             
<%--             $("form.sendMsgForm").validate_popover({--%>
<%--                 rules:{--%>
<%--                     'content':{ required:true },--%>
<%--                 },--%>
<%--                 messages:{--%>
<%--                     'content':{number:"请输入消息内容"},--%>
<%--                 }--%>
<%--             });--%>
             $("button.send-msg-btn-ok").prop("disabled", true); // 初始时禁止
             $("textarea.msgContent").keyup(function(){
                 if($(this).val().length > 0) {
                     $("button.send-msg-btn-ok").prop("disabled", false);
                 } else {
                     $("button.send-msg-btn-ok").prop("disabled", true);
                 }
             });
             $('input[name="touser"]').val(record.openid);
             $('span.openid-label').empty().append(record.openid);
             $('span.sn-label').empty().append(record.sn);
         }
         function dump_obj(myObject) {  
        	  var s = "";  
        	  for (var property in myObject) {  
        	   s = s + "\n "+property +": " + myObject[property] ;  
        	  }  
        	  alert(s);  
        	}
         function op_sendTemplate(index) {
             var record= gridObj.getRecord(index);
             bootbox.dialog({
                 title: "发送模板消息",
                 message: $('#dialogSendTemplateMsgForm').html(),
                 buttons: {
                     cancel: {
                         label: "取消",
                         className: "btn-default send-t-msg-btn-cancel",
                         callback: function () {
                         }
                     },
                     success: {
                         label: "确定发送",
                         className: "btn-success send-t-msg-btn-ok",
                         callback: function () {
                             //alert($(".modal-body textarea.msgContent").val());
                             //alert($('.modal-body select[name="template_id"]').find('option:selected').get(0).index);
                             var subFormIndex = $('.modal-body select[name="template_id"]').find('option:selected').get(0).index;
                             
                             // params 的值前面还要带上各自参数的名称如 first=请留意低流量预警 只能手动拼,直接使用serialize()不行
                             var children = $('.modal-body form.sendTemplateMsgFormSub').eq(subFormIndex).find('input');
                             var paramsCount = children.size();
                             var paramsString = '';
                             for(var i=0;i<paramsCount;i++){
                            	 if(i<paramsCount-1){//serialize之后要去除前面的params=
                            		 paramsString += 'params=' + children.eq(i).attr('id') + '=' + children.eq(i).serialize().substring(7) + '&';
                            	 } else {
                            		 paramsString += 'params=' + children.eq(i).attr('id') + '=' + children.eq(i).serialize().substring(7);
                            	 }                            	 
                             }
                             //alert(paramsString);
                             //alert($('.modal-body form.sendTemplateMsgForm').serialize());
                             //alert($('.modal-body form.sendTemplateMsgFormSub').eq(subFormIndex).serialize());
                              $.ajax({
                                  type:"POST",
                                  //url:"http://www.easy2go.cn/api/v3/wx/sendTemplateMsg",//http://www.easy2go.cn
                                  url: "<%=basePath %>/wx/bindDevice/sendTemplateMsg",
                                  dataType: "html",
                                  data:$('.modal-body form.sendTemplateMsgForm').serialize() +'&'+paramsString,
                                  success: function(data){
                                      result = jQuery.parseJSON(data);
                                      if(result.error=="00"){
                                          easy2go.toast('success',"发送模板消息成功!");
                                          //gridObj.refreshPage();
                                          $('button.send-t-msg-btn-cancel').click();
                                      }else{
                                          easy2go.toast('err',result.msg);
                                      }
                                  }
                                  
                              });
                              return false; // 到ajax success再判断是否关闭
                         }
                     }
                 }
             });
             
<%--             $("button.send-t-msg-btn-ok").prop("disabled", true); // 初始时禁止--%>
<%--             $("textarea.msgContent").keyup(function(){--%>
<%--                 if($(this).val().length > 0) {--%>
<%--                     $("button.send-msg-btn-ok").prop("disabled", false);--%>
<%--                 } else {--%>
<%--                     $("button.send-msg-btn-ok").prop("disabled", true);--%>
<%--                 }--%>
<%--             });--%>
             $('input[name="touser"]').val(record.openid);
             $('span.openid-label').empty().append(record.openid);
             $('span.sn-label').empty().append(record.sn);
             $('.modal-body .t-section').hide(); // 注意.modal-body限制
             $('.modal-body .t-section').eq(0).show();
             $('select[name="template_id"]').bind('change',function(){
            	 //dump_obj($(this).get(0));alert($(this).get(0).options.length);           	    
                 //alert($(this).get(0).selectedIndex);                 
                 $('.modal-body .t-section').hide(); // 注意.modal-body限制
                 $('.modal-body .t-section').eq($(this).get(0).selectedIndex).show();
             });
         }

	</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>