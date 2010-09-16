package com.yong.rest;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.yong.rest.annotation.AnnonationHelper;

/**
 * 控制转交请求给相应的Servlet
 */
public class CopyOfRestFilter implements Filter {
	private static Map<String, HttpServlet> servletMap = null;

	public void init(FilterConfig config) throws ServletException {
		String scanPackage = config.getInitParameter("scanPackage");

		if (scanPackage == null || scanPackage.length() == 0) {
			throw new NullPointerException();
		}

		servletMap = AnnonationHelper.readServletClasses(scanPackage);
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		String realUrl = request.getRequestURI().replaceFirst(
				request.getContextPath(), "");

		HttpServlet servlet = getServletByUrl(realUrl);

		if (servlet != null) {
			servlet.service(req, res);

			return;
		}

		chain.doFilter(req, res);
	}

	public void destroy() {
		if(servletMap != null){
			servletMap.clear();
			servletMap = null;
		}
	}

	private HttpServlet getServletByUrl(String oriUrl) {
		Set<String> urlSet = servletMap.keySet();

		if (urlSet.contains(oriUrl)) {
			return servletMap.get(oriUrl);
		}

		Iterator<String> itern = urlSet.iterator();

		while (itern.hasNext()) {
			String url = itern.next();
			if (Pattern.matches(url, oriUrl)) {
				return servletMap.get(url);
			}
		}

		return null;
	}
}