package com.yong.test.servlet.anno;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servlet.rest.RestSupport;

@RestSupport("/person/*")
public class PersonServlet extends HttpServlet {
	private static final long serialVersionUID = -153456L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.write("Person pages now !");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		super.doPost(req, resp);
	}
}