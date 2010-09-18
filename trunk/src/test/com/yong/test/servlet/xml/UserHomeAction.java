package com.yong.test.servlet.xml;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.PrintWriter;

public class UserHomeAction extends HttpServlet {
	private static final long serialVersionUID = 983408504860486L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();

		out.write("welcome to user home action ...");

		out.flush();
		out.close();
	}
}
