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
</div>
<div>
<br/>
注销刚才注册的URL和Servlet<br/>
<form action="delete.jsp" method="post" target="_blank">
		Servlet Class: <input type="text" name="class" value="com.yong.test.servlet.other.CustomServlet" /><br/>
	Mapping URL: <input type="text" name="url" value="/mycustom"/> <br/>
	备注：<br/>注意URL要填写刚才注册的URL，这里为了测试URL是否已经可以访问，不是必要参数<br/>
	删除可以根据URL或者servlet<br/>
	<input type="submit" value="注销" />
</form>
</div>
</body>
</html>