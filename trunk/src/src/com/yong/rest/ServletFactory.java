package com.yong.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public abstract class ServletFactory {
	private static Map<String, HttpServlet> servletMap = null;

	private static Multimap<HttpServlet,String> multimap = null;

	public ServletFactory(String path){
		servletMap = new HashMap<String, HttpServlet>();

		multimap = ArrayListMultimap.create();
	}

	/**
	 * 交由子类实现
	 *
	 * @param path
	 */
	protected abstract void init(String path);

	protected void initMap(Map<String, HttpServlet> resultMap){
		if (resultMap == null) {
			return;
		}

		servletMap.putAll(resultMap);

		switchMaps(resultMap);
	}

	private void switchMaps(Map<String, HttpServlet> resultMap){
		if(resultMap == null || resultMap.isEmpty()){
			return;
		}

		for(Map.Entry<String, HttpServlet> entry : resultMap.entrySet()){
			multimap.put(entry.getValue(), entry.getKey());
		}
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
	public Collection<String> getUrlByServlet(HttpServlet servletInstance) {
		if (servletInstance == null) {
			return null;
		}

		return multimap.get(servletInstance);
	}

	/**
	 * 传入servlet实例，获取参数数组
	 *
	 * @param servletInstance
	 * @return
	 */
	public String[] getUrlParametersByServlet(HttpServlet servletInstance,
			String oriUrl) {
		Collection<String> urls = getUrlByServlet(servletInstance);

		if (urls == null || urls.isEmpty()) {
			return null;
		}

		// 使用正则表达式提取 参数

		for(String url : urls){
			String [] paramters =  analyticsParameters(url, oriUrl);

			if(paramters != null && paramters.length > 0){
				return paramters;
			}
		}

		return null;
	}

	/**
	 * 获取url中出现参数
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
	 * 动态添加servlet和url映射关系
	 * @param url
	 * @param servletInstance
	 */
	public synchronized void add(String url, HttpServlet servletInstance) {
		if (url == null || url.length() == 0) {
			return;
		}

		if (servletInstance == null) {
			return;
		}

		synchronized (ServletFactory.class) {
			servletMap.put(url, servletInstance);
		}
	}

	/**
	 * 动态删除servlet和url映射关系
	 * @param servletInstance
	 */
	public synchronized void remove(HttpServlet servletInstance) {
		if (servletInstance == null) {
			return;
		}

		Collection<String> urls = getUrlByServlet(servletInstance);

		for(String url : urls){
			multimap.remove(servletInstance, url);
		}
	}

	/**
	 * 动态清空所有servlet和url映射关系等
	 */
	public synchronized void clear(){
		servletMap.clear();

		multimap.clear();
	}
}