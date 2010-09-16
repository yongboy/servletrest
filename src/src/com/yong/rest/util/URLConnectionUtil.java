package com.yong.rest.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class URLConnectionUtil {
	private static final String SERVLET_POST = "POST";
	private static final String SERVLET_GET = "GET";
	private static final String SERVLET_DELETE = "DELETE";
	private static final String SERVLET_PUT = "PUT";

	public static void doGet(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		String paramStr = prepareParam(paramMap);
		if (paramStr.trim().length() > 0) {
			urlStr += "?" + paramStr;
		}
		
		System.out.println(urlStr);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(SERVLET_GET);
//		conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");

		conn.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		String result = "";
		while ((line = br.readLine()) != null) {
			result += "\n" + line;
		}
		System.out.println(result);
		br.close();
	}

	public static void doPost(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		String paramStr = prepareParam(paramMap);

		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod(SERVLET_POST);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		OutputStream os = conn.getOutputStream();
		os.write(paramStr.toString().getBytes("utf-8"));
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		String result = "";
		while ((line = br.readLine()) != null) {
			result += "\n" + line;
		}
		System.out.println(result);
		br.close();

	}

	public static void doPut(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		String paramStr = prepareParam(paramMap);
		
		if (paramStr.trim().length() > 0) {
			urlStr += "?" + paramStr;
		}
		
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(SERVLET_PUT);
//		conn.setDoInput(true);
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		out.write(paramStr.toString());
		out.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;
		String result = "";
		while ((line = br.readLine()) != null) {
			result += "\n" + line;
		}
		System.out.println(result);
		br.close();

	}

	public static void doPut2(String urlStr, Map<String, Object> paramMap)
	throws Exception {
		String paramStr = prepareParam(paramMap);
		if (paramStr.trim().length() > 0) {
			urlStr += "?" + paramStr;
		}
		
		System.out.println(urlStr);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(SERVLET_PUT);
		
		if (conn.getResponseCode() == 200) {
			System.out.println("成功");
		} else {
			System.out.println(conn.getResponseCode());
		}
	}
	
	public static void doDelete(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		String paramStr = prepareParam(paramMap);
		if (paramStr.trim().length() > 0) {
			urlStr += "?" + paramStr;
		}
		
		System.out.println(urlStr);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(SERVLET_DELETE);

		if (conn.getResponseCode() == 200) {
			System.out.println("成功");
		} else {
			System.out.println(conn.getResponseCode());
		}
	}

	private static String prepareParam(Map<String, Object> paramMap) {
		StringBuffer sb = new StringBuffer();
		if (paramMap.isEmpty()) {
			return "";
		} else {
			for (String key : paramMap.keySet()) {
				String value = (String) paramMap.get(key);
				if (sb.length() < 1) {
					sb.append(key).append("=").append(value);
				} else {
					sb.append("&").append(key).append("=").append(value);
				}
			}
			return sb.toString();
		}
	}

	public static void main(String[] args) throws Exception {
		String urlStr = "http://localhost/HiRest/demo/";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "John");
		map.put("password", "88888");
		
		URLConnectionUtil.doPut(urlStr, map);
	}
}