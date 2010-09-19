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
	String serlvetClass = request.getParameter("class");
	String mappingUrl = request.getParameter("url");
	
	Class<HttpServlet> servletClass = (Class<HttpServlet>)Class.forName(serlvetClass);
	
	if(servletClass == null){
		out.println("传入要注销的servlet不存在！");
	}else if(servletClass.getSuperclass() != javax.servlet.http.HttpServlet.class){
		out.println("传入要注销的servlet类型不对！");
	}else{
		ServletFactory servletFactory  = (ServletFactory)application.getAttribute("servletFactory");
		
		servletFactory.destory(servletClass);
		
		out.println("注销成功 !");
	}
%>
</body>
</html>