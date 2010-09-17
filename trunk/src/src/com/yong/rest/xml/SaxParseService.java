package com.yong.rest.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParseService extends DefaultHandler {
	private Map<String, String> hashMap = null;
	private String preTag = null;// 作用是记录解析时的上一个节点名称
	private ServletUrl book;

	private static final String SERVLET_NODE = "servlet";
	private static final String CLASS_NODE = "class";
	private static final String URL_NODE = "url";

	public Map<String, String> getResult(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlStream, this);

		return getMap();
	}

	public Map<String, String> getMap() {
		return hashMap;
	}

	@Override
	public void startDocument() throws SAXException {
		hashMap = new HashMap<String, String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (SERVLET_NODE.equals(qName)) {
			book = new ServletUrl();
		}

		preTag = qName;// 将正在解析的节点名称赋给preTag
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (SERVLET_NODE.equals(qName)) {
			
			if(!book.getUrls().isEmpty()){			
				for(String url : book.getUrls()){
					hashMap.put(url, book.getPath());
				}
			}

			book = null;
		}

		preTag = null;
		/**
		 * 当解析结束时置为空。这里很重要，例如，当图中画3的位置结束后，会调用这个方法
		 * ，如果这里不把preTag置为null，根据startElement(....)方法，preTag的值还是book，当文档顺序读到图
		 * 中标记4的位置时，会执行characters(char[] ch, int start, int
		 * length)这个方法，而characters(....)方
		 * 法判断preTag!=null，会执行if判断的代码，这样就会把空值赋值给book，这不是我们想要的。
		 */
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if (CLASS_NODE.equals(preTag)) {
				book.setPath(content);
			} else if (URL_NODE.equals(preTag)) {
				book.setUrl(content);
			}
		}
	}
	
	public static void main(String [] args) throws Exception{
		SaxParseService sax = new SaxParseService();  
        InputStream input = SaxParseService.class.getClassLoader().getResourceAsStream("servlets.xml");  
        Map<String, String> hashMap = sax.getResult(input);
        
        if(hashMap == null){
        	System.out.println("xml is null");
        	return;
        }
        
        for(Map.Entry<String, String> entry : hashMap.entrySet()){
        	System.out.println(entry.getValue() + ":" + entry.getKey());
        }
	}
}

class ServletUrl {
	private String path;
	private List<String> urls;
	
	public ServletUrl(){
		urls = new ArrayList<String>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrl(String url) {
		this.urls.add(url);
	}
}