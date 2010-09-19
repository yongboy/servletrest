<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>动态操作Servlet</title>
</head>
<body>
<div>
未注册前，请访问 :<br/>
<a href="mycustom" target="_blank">/mycustom</a>
<br/>
现在开始注册:<br/>
对应的Servlet为com.yong.test.servlet.other.CustomServlet,要注册的URL为/mycustom
<form action="register.jsp" method="post" target="_blank">
	Servlet Class: <input type="text" name="class" value="com.yong.test.servlet.other.CustomServlet" /><br/>
	Mapping URL: <input type="text" name="url" value="/mycustom"/><br/>
	<input type="submit" value="注册" />
</form>
<br/>
现在开始注册:<br/>
对应的Servlet为com.yong.test.servlet.other.CustomServlet,要注册的URL为/mycustom
<form action="register.jsp" method="post" target="_blank">
	Servlet Class: <input type="text" name="class" value="com.yong.test.servlet.other.CustomServlet" /><br/>
	Mapping URL: <input type="text" name="url" value="/mycustom2"/><br/>
	<input type="submit" value="注册" />
</form>
</div>
<div>
<br/>
根据Servlet Class注销刚才注册的URL和Servlet<br/>
<form action="deleteClass.jsp" method="post" target="_blank">
		Servlet Class: <input type="text" name="class" value="com.yong.test.servlet.other.CustomServlet" /><br/>
		备注: 会删除当前servlet所有对应的URL
	<input type="submit" value="注销" />
</form>


<br/>
根据URL注销刚才注册的URL和Servlet<br/>
<form action="deleteUrl.jsp" method="post" target="_blank">
	Mapping URL: <input type="text" name="url" value="/mycustom"/> <br/>
	备注：仅仅可以删除当前URL，即url对应的servlet所对应的其它URL不能够被删除
	<input type="submit" value="注销" />
</form>
<br/>
</div>
</body>
</html>