package com.yong.test.servlet.anno;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yong.rest.annotation.RestSupport;

@RestSupport("/book/*/*")
public class BookChapterServlet extends HttpServlet {
	private static final long serialVersionUID = -235346477L;

	public void init() throws ServletException {
		super.init();

		System.out.println("init() is init now ...");
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("now is init the servlet now ...");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();

		out.write("show one chapter now ...");
		out.flush();
	}
}