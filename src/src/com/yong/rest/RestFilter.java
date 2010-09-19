package com.yong.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.yong.rest.annotation.AnnotationServletFactoryImpl;
import com.yong.rest.xml.XmlServletFactoryImpl;

/**
 * 控制转交请求给相应的Servlet
 * 
 * @author yong
 * @date 2010-9-19
 * @version 1.0
 */
public class RestFilter implements Filter {
	private static ServletFactory servletFactory = null;

	/**
	 * 初始化servlet工厂
	 */
	public void init(FilterConfig config) throws ServletException {
		String scanPackage = config.getInitParameter("scanPackage");

		if (scanPackage == null || scanPackage.length() == 0) {
			throw new NullPointerException();
		}

		if(scanPackage.toLowerCase().endsWith(".xml")){
			servletFactory = new XmlServletFactoryImpl(scanPackage);
		}else{
			servletFactory = new AnnotationServletFactoryImpl(scanPackage);
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		String realUrl = request.getRequestURI().replaceFirst(
				request.getContextPath(), "");

		HttpServlet servlet = servletFactory.getServletByUrl(realUrl);

		if (servlet != null) {
			servlet.service(req, res);

			return;
		}

		chain.doFilter(req, res);
	}

	public void destroy() {
		if (servletFactory != null) {
			servletFactory.clear();
		}
	}
}