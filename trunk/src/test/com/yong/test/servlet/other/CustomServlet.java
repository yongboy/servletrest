package com.yong.test.servlet.other;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomServlet extends HttpServlet {
	private static final long serialVersionUID = 6940350914907295577L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();

		out.write("这是自定义Servlet，后期通过入口直接注入到Servlet工厂中去。<br/>即动态注册Servlet，动态删除Servlet等。");

		out.flush();
		out.close();
	}
}