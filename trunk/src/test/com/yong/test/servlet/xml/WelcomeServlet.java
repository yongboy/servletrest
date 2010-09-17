package com.yong.test.servlet.xml;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = -253465767899L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.write("you are welcome here !<br/><br/>USER GET METHOD <br/><br/><br/><br/>POWER BY XML!");
		out.flush();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.write("you are welcome here !<br/><br/>USER POST METHOD <br/><br/><br/><br/>POWER BY XML!");
		out.flush();
	}
}