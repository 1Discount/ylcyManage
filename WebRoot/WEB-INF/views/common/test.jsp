
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<aside>
  <div id="sidebar" class="nav-collapse"><!-- sidebar menu start-->            
    <div class="leftside-navigation">
      <ul id="nav-accordion" class="sidebar-menu">
        <c:forEach items="${mgList}" var="mg">
        <li class="sub-menu">
        <a id="${mg.menuGroupID}" href="javascript:;"><i class="fa fa-desktop"></i><span>${mg.menuGroupName}</span></a>
        <ul class="sub">
        <c:forEach items="${mg.menuInfos}" var="m">
        	<li id="${m.menuInfoID}"><a href="#">${m.menuName}</a></li>
        </c:forEach>
        </ul>
        </li>
        </c:forEach>
       
      </ul>
    </div>
  </div><!-- sidebar menu end-->
</aside><!--sidebar end-->

