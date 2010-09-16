package com.yong.rest;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yong.rest.annotation.AnnonationHelper;

/**
 * 控制转交请求给相应的Servlet
 */
public class RestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RestServlet() {
		super();
	}

	private static Map<String, HttpServlet> servletMap = null;

	public void init(ServletConfig config) throws ServletException {
		String scanPackage = config.getInitParameter("scanPackage");

		if (scanPackage == null || scanPackage.length() == 0) {
			throw new NullPointerException();
		}

		servletMap = AnnonationHelper.readServletClasses(scanPackage);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();

		String realUrl = url.replaceFirst(contextPath, "");

		HttpServlet servlet = getServletByUrl(realUrl);

		if (servlet != null) {
			servlet.service(request, response);
		} else {
			System.out.println("not found url pattern ...");
		}
	}

	private HttpServlet getServletByUrl(String oriUrl) {
		Set<String> urlSet = servletMap.keySet();
		
		if(urlSet.contains(oriUrl)){
			return servletMap.get(oriUrl);	
		}
		
		for (String url : servletMap.keySet()) {
			if (Pattern.matches(url, oriUrl)) {
				return servletMap.get(url);
			}
		}

		return null;
	}
	
	public void destroy() {
	}
}