<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<header class="header fixed-top clearfix"><!--logo start-->
  <div class="brand"><a href="<%=basePath %>admin/adminuserinfo/index" style="color:#fff" class="logo"><i style="font-size:24px" class="fa fa-gears"></i><span style="padding:0 10px;font-size:16px;color: #fff">ADMIN</span><span id="version" class="pull-right">beta 2.0</span></a>
    <div class="sidebar-toggle-box">
      <div class="fa fa-bars"></div>
    </div>
  </div><!--logo end-->
  <div id="top_menu" class="nav notify-row"><!--  notification start -->
    
      
  </div>
  <div class="top-nav clearfix"><!--search & user info start-->
    <ul class="nav pull-right top-menu"><!-- user login dropdown start-->
      <li class="dropdown"><span style="color: red;"></span><a data-toggle="dropdown" href="#" class="dropdown-toggle"><i style="font-size:24px;color: #bbb;padding:2px 8px;" class="fa fa-user"></i><span class="username">${sessionScope.User.userName}【${sessionScope.User.roleName}】</span><b class="caret"></b></a>
        <ul class="dropdown-menu extended logout">
          <li><a href="<%=basePath %>admin/adminuserinfo/touseredit?userID=${sessionScope.User.userID}&userName=${sessionScope.User.userName}&phone=${sessionScope.User.phone}"><i class=" fa fa-suitcase"></i>个人资料</a></li>
          <li><a href="<%=path %>/admin/adminuserinfo/loginout"><i class="fa fa-key"></i>退出</a></li>
        </ul>
      </li><!-- user login dropdown end -->
    </ul><!--search & user info end-->
  </div>
</header><!--header end-->