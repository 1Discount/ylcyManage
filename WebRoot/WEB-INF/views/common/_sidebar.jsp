
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<aside>
	<div id="sidebar" class="nav-collapse">
		<!-- sidebar menu start-->
		<div class="leftside-navigation">
			<ul id="nav-accordion" class="sidebar-menu">
			 <c:if test="${sessionScope.User.email ne '362331359@qq.com'}">
			 <li><a id="dashboardMenu" href="<%=basePath%>admin/adminuserinfo/index"><i class="fa fa-dashboard"></i>
					<span>主页概览</span></a>
				</li>
			 </c:if>
			 <li class="sub-menu"><a id="DeviceJumpInfo" href="javascript:;"><i class="fa fa-desktop"></i><span>设备跳转管理</span></a>
          	<ul class="sub">
            	<li id="allDeviceJumpInfo"><a href="<%=path %>/deviceJumpInfo/index">全部跳转记录</a></li>
          	</ul>
        	</li>
			<li class="sub-menu"><a id="TDMManage" href="javascript:;"><i class="fa fa-desktop"></i><span>TDM管理</span></a>
          	<ul class="sub">
            	<li id="allTDM"><a href="<%=path %>/tdm/index">全部TDM</a></li>
            	<li id="addTDM"><a href="<%=path %>/tdm/toAdd">添加TDM</a></li>
            	<li id="deletedTDM"><a href="<%=path %>/tdm/deletedTDMList">已删除TDM</a></li>
          	</ul>
        	</li>
				<!--  动态加载菜单的部分 -->
				<c:forEach items="${sessionScope.mgList}" var="mg">
					<li class="sub-menu">
						<a id="${mg.menuGroupID}" href="javascript:;"><i class="${mg.menuIcon }"></i><span>${mg.menuGroupName}</span></a>
						<ul class="sub">
							<c:forEach items="${mg.menuInfos}" var="m">
								<li id="${m.menuPath}"><a href="<%=basePath %>${m.menuPath}">${m.menuName}</a></li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
				<%-- <li class="sub-menu"><a id="customerService" href="javascript:;"><i class="fa fa-desktop"></i><span>本地菜单</span></a>
          <ul class="sub">
            <li id="onlineDevice"><a href="<%=path %>/orders/devicedealorders/statistics">设备订单统计</a></li>
            <li id="onlineDevice"><a href="<%=path %>/orders/flowdealorders/statistics">流量订单统计</a></li>
            <li id="dateDevice"><a href="<%=path %>/serviceCenter/dateDevice">当日可用流量交易</a></li>
          </ul>
        </li> --%>
		<%-- <li class="sub-menu"><a id="customerService" href="javascript:;"><i class="fa fa-desktop"></i><span>客服中心</span></a>
          <ul class="sub">
            <li id="onlineDevice"><a href="<%=path %>/serviceCenter/onlineDevice">实时在线设备</a></li>
            <li id="hisOnlineDevice"><a href="<%=path %>/serviceCenter/hisOnlineDevice">历史在线设备</a></li>
            <li id="dateDevice"><a href="<%=path %>/serviceCenter/dateDevice">当日可用流量交易</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="dealMenu" href="javascript:;"><i class="fa fa-th-list"></i><span>数据交易</span></a>
          <ul class="sub">
            <li id="allDealsMenu"><a href="<%=basePath %>orders/flowdealorders/list">数据交易列表</a></li>
            <li id="deletedDealsMenu"><a href="<%=path %>/deals?deleted=1">已删交易</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="deviceDealMenu" href="javascript:;"><i class="fa fa-cube"></i><span>设备交易</span></a>
          <ul class="sub">
            <li id="allDeviceDealsMenu"><a href="<%=basePath %>orders/devicedealorders/list">设备交易列表</a></li>
            <li id="deletedDeviceDealsMenu"><a href="<%=path %>/deviceDeals?deleted=1">已删交易</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="orderMenu" href="javascript:;"><i class="fa fa-gavel"></i><span>订单管理</span></a>
          <ul class="sub">
            <li id="allOrdersMenu"><a href="<%=basePath %>orders/ordersinfo/list">全部订单</a></li>
            <li id="allOrdersMenu"><a href="<%=basePath %>orders/ordersinfo/goone">新建订单</a></li>
             <li id="allOrdersMenu"><a href="<%=basePath %>orders/ordersinfo/count">订单统计</a></li>
            <li id="deletedOrdersMenu"><a href="<%=path %>/orders/deleted">已删除订单</a></li>
          </ul>
        </li>
         <li class="sub-menu"><a id="clientMenu" href="javascript:;"><i class="fa fa-users"></i><span>客户管理</span></a>
          <ul class="sub">
            <li id="allClientsMenu"><a href="<%=path %>/customer/customerInfolist/all">全部客户</a></li>
            <li id="newClientMenu"><a href="<%=path %>/customer/customerInfolist/insert">添加客户</a></li>
          </ul>
        </li>
         <li class="sub-menu"><a id="deviceMenu" href="javascript:;"><i class=" fa fa-laptop"></i><span>设备管理</span></a>
          <ul class="sub">
            <li id="allDevicesMenu"><a href="<%=path %>/device/list">全部设备</a></li>
            <li id="importDeviceMenu"><a href="<%=path %>/device/insert">添加设备</a></li>
            <li id="importDeviceMenu"><a href="<%=path %>/device/data/insert">批量添加设备</a></li>
            <li id="revertDeviceMenu"><a href="<%=path %>/device/revert">归还设备</a></li>
            <li id="deletedDeviceMenu"><a href="<%=path %>/device/deviceinfoPagedelete">已删设备</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="planMenu" href="javascript:;"><i class="fa fa-connectdevelop"></i><span>套餐管理</span></a>
          <ul class="sub">
            <li id="allPlansMenu"><a href="<%=path %>/flowplan/flowplaninfo/index">全部套餐</a></li>
            <li id="newPlanMenu"><a href="<%=path %>/flowplan/flowplaninfo/new">添加套餐</a></li>
            <li id="trashPlanMenu"><a href="<%=path %>/flowplan/flowplaninfo/trash">已删除套餐</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="simMenu" href="javascript:;"><i class="fa fa-credit-card"></i><span>SIM卡管理</span></a>
          <ul class="sub">
            <li id="allSimsMenu"><a href="<%=path %>/sim/siminfo/index">全部SIM卡</a></li>
            <li id="newSimMenu"><a href="<%=path %>/sim/siminfo/new">添加SIM卡</a></li>
            <li id="trashSimsMenu"><a href="<%=path %>/sim/siminfo/trash">已删除SIM卡</a></li>
            <li id="allSimsRechargeBillMenu"><a href="<%=path %>/sim/simrechargebill/index">全部SIM卡充值记录</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="serverMenu" href="javascript:;"><i class=" fa fa-server"></i><span>SIM服务器</span></a>
          <ul class="sub">
            <li id="allServersMenu"><a href="<%=path %>/sim/simserver/index">全部服务器</a></li>
            <li id="newServerMenu"><a href="<%=path %>/sim/simserver/new">添加服务器</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="countryMenu" href="javascript:;"><i class="fa fa-flag-o"></i><span>国家管理</span></a>
          <ul class="sub">
            <li id="allCountriesMenu"><a href="<%=path %>/flowplan/countryinfo/index">全部国家</a></li>
            <li id="newCountryMenu"><a href="<%=path %>/flowplan/countryinfo/trash">已删除国家</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="userMenu" href="javascript:;"><i class="fa fa-user"></i><span>帐号管理</span></a>
          <ul class="sub">
            <li id="allUsersMenu"><a href="<%=basePath %>admin/adminuserinfo/userlist">全部帐号</a></li>
            <li id="inviteUserMenu"><a href="<%=basePath %>admin/invitation/toinvita">邀请注册</a></li>
            <li id="deletedUsersMenu"><a href="<%=path %>/users?deleted=1">已删帐号</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="roleMenu" href="javascript:;"><i class="fa fa-shield"></i><span>权限管理</span></a>
          <ul class="sub">
            <li id="allRolesMenu"><a href="<%=basePath %>admin/roleinfo/rolelist">全部角色</a></li>
            <li id="newRoleMenu"><a href="<%=basePath %>admin/roleinfo/add">添加角色</a></li>
            <li id="allMenuGroup"><a href="<%=basePath %>admin/menuGroupinfo/info">菜单组维护</a></li>
            <li id="allPermissionsMenu"><a href="<%=basePath %>admin/menuinfo/info">权限菜单维护</a></li>
          </ul>
        </li>
        <li class="sub-menu"><a id="systemMenu" href="javascript:;"><i class="fa fa-cog"></i><span>系统管理</span></a>
          <ul class="sub">
            <li id="sysSettingsMenu"><a href="<%=path %>/settings">系统设置</a></li>
            <li id="syslogsMenu"><a href="<%=path %>/syslogs">系统日志</a></li>
          </ul>
        </li> --%>
			</ul>
		</div>
	</div>
	<!-- sidebar menu end-->
</aside>
<!--sidebar end-->

