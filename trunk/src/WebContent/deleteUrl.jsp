<%@page import="com.servlet.rest.ServletFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注销Servlet</title>
</head>
<body>
<%
	String mappingUrl = request.getParameter("url");
	
	if(mappingUrl == null){
		out.println("传入要注销的mappingUrl 不存在！");
	}else{
		ServletFactory servletFactory  = (ServletFactory)application.getAttribute("servletFactory");
		
		servletFactory.destory(mappingUrl);
		
		out.println("注销成功，访问吧:<br/><a href='" + (mappingUrl.startsWith("/") ? mappingUrl.substring(1) : mappingUrl) + "'>" + mappingUrl + "</a>");
	}
%>
</body>
</html>