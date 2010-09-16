package com.yong.test.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yong.rest.annotation.RestSupport;

@RestSupport("/demo/")
public class DemoHomeServlet extends HttpServlet {
	private static final long serialVersionUID = -153456L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		System.out.println("GET : " + request.getParameter("name"));

		out.write("You had GET data SUCCESSFULLY!<br/> Thank You !");
		out.flush();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST : " + request.getParameter("name"));
		PrintWriter out = response.getWriter();

		out.write("You had POST data SUCCESSFULLY!<br/> Thank You !");
		out.flush();
	}

	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PUT : " + request.getParameter("name"));
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		System.out.println("now is the inputstream content : ");
		while((line = reader.readLine()) != null){
			System.out.println(line);
		}
		System.out.println("input stream is end ...");
		reader.close();
		
		
		PrintWriter out = response.getWriter();

		out.write("You had PUT data SUCCESSFULLY!<br/> Thank You !");
		out.flush();
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DELETE : " + request.getParameter("name"));
		PrintWriter out = response.getWriter();
		out.write("You had DELETE data SUCCESSFULLY!<br/> Thank You !");

		out.flush();
	}
}