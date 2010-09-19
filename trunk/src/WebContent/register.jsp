<%@page import="com.yong.rest.ServletFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册Servlet</title>
</head>
<body>
<%
	String serlvetClass = request.getParameter("class");
	String mappingUrl = request.getParameter("url");
	
	Class servletClass = Class.forName(serlvetClass);
	
	if(servletClass == null){
		out.println("传入要注册的servlet不存在！");
	}else if(servletClass.getSuperclass() != javax.servlet.http.HttpServlet.class){
		out.println("传入要注册的servlet类型不对！");
	}else{
		ServletFactory servletFactory = (ServletFactory)application.getAttribute("servletFactory");
		
		servletFactory.add(mappingUrl, (HttpServlet)servletClass.newInstance());
		out.println("注册成功，访问吧:<br/><a href='" + (mappingUrl.startsWith("/") ? mappingUrl.substring(1) : mappingUrl) + "'>" + mappingUrl + "</a>");
	}
%>
</body>
</html>