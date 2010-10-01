package com.servlet.rest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

/**
 * Servlet和URL处理工厂
 * 
 * @author yong
 * @date 2010-9-19
 * @version 1.0
 */
public abstract class ServletFactory {
	private static HashBidiMap<String, HttpServlet> servletMap = null;

	protected ServletFactory(String path) {
		servletMap = new HashBidiMap<String, HttpServlet>();
	}

	/**
	 * 交由子类实现
	 * 
	 * @param path
	 */
	protected abstract void init(String path);

	protected void initMap(Map<String, HttpServlet> resultMap) {
		if (resultMap == null) {
			return;
		}

		servletMap.putAll(resultMap);
	}

	/**
	 * 传入url地址，获取对应的servlet实例
	 * 
	 * @param oriUrl
	 * @return
	 */
	public HttpServlet getServletByUrl(String oriUrl) {
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

	/**
	 * 传入servlet实例，获取对应的url
	 * 
	 * @param servletInstance
	 * @return
	 */
	public String getUrlByServlet(HttpServlet servletInstance) {
		if (servletInstance == null) {
			return null;
		}

		return servletMap.reverse().get(servletInstance);
	}

	/**
	 * 传入servlet实例，获取参数数组
	 * 
	 * @param servletInstance
	 * @return
	 */
	public String[] getUrlParametersByServlet(HttpServlet servletInstance,
			String oriUrl) {
		String url = getUrlByServlet(servletInstance);

		if (url == null || url.length() == 0) {
			return null;
		}

		// 使用正则表达式提取 参数

		String[] paramters = analyticsParameters(url, oriUrl);

		if (paramters != null && paramters.length > 0) {
			return paramters;
		}

		return null;
	}

	/**
	 * 获取url中出现参数
	 * 
	 * @param regUrl
	 * @param oriUrl
	 * @return
	 */
	private static String[] analyticsParameters(String regUrl, String oriUrl) {
		Pattern pattern = Pattern.compile(regUrl);

		Matcher matcher = pattern.matcher(oriUrl);

		String[] paramters = null;

		if (matcher.matches()) {
			int count = matcher.groupCount();
			paramters = new String[count];

			for (int i = 1; i <= count; i++) {
				paramters[i - 1] = matcher.group(i);
			}
		}

		return paramters;
	}

	/**
	 * 动态添加servlet和url映射关系，开放此接口，将为带来隐含的问题，比如需要浪费精力去检查当前实例是否已经存在
	 * 
	 * @param url
	 * @param servletInstance
	 */
	private synchronized void register(String url, HttpServlet servletInstance) {
		if (url == null || url.length() == 0) {
			return;
		}
		
		if (servletInstance == null) {
			return;
		}
		
		servletMap.put(url, servletInstance);
	}
	
	/**
	 * 动态添加servlet和url映射关系
	 * 
	 * @param url
	 * @param servletInstance
	 */
	public void register(String url, Class<HttpServlet> servletClass) {
		if (url == null || url.length() == 0) {
			return;
		}

		if (servletClass == null
				|| servletClass.getSuperclass() != HttpServlet.class) {
			return;
		}

		try {
			register(url, servletClass.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加servlet和多个URL，注意每一个URL都对应一个单独的servlet实例
	 * 
	 * @param urls
	 * @param servletInstance
	 */
	public void register(String[] urls, Class<HttpServlet> servletClass) {
		if (urls == null || urls.length == 0) {
			return;
		}

		if (servletClass == null
				|| servletClass.getSuperclass() != HttpServlet.class) {
			return;
		}

		for (String url : urls) {
			register(url, servletClass);
		}
	}

	/**
	 * 动态删除servlet和url映射关系,请请注意，这里是删除一个HttpServlet实例
	 * 
	 * @param servletInstance
	 */
	public synchronized void destory(HttpServlet servletInstance) {
		servletMap.removeValue(servletInstance);
	}
	
	/**
	 * 这个是删除当前servlet所有对应关系
	 * @param servletClass
	 */
	public synchronized void destory(Class<HttpServlet> servletClass) {
		servletMap.removeValueByClassPath(servletClass.getName());
	}

	/**
	 * 动态删除servlet和url映射关系
	 * 
	 * @param servletInstance
	 */
	public synchronized void destory(String url) {
		servletMap.remove(url);
	}

	/**
	 * 动态清空所有servlet和url映射关系等
	 */
	public synchronized void clear() {
		servletMap.clear();
	}
}