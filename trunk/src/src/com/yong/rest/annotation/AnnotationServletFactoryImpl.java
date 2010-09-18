package com.yong.rest.annotation;

import java.util.Map;

import javax.servlet.http.HttpServlet;

import com.yong.rest.ServletFactory;
import com.yong.rest.exception.ScanPackageIllegalException;

/**
 * 读取注解，填充ServletFactory工程数据源
 *
 * @author y.nie
 * @date 2010-9-16
 * @version 1.0
 */
public class AnnotationServletFactoryImpl extends ServletFactory {

	public AnnotationServletFactoryImpl(String scanPackage) {
		super(scanPackage);

		if (scanPackage == null || scanPackage.length() == 0) {
			throw new ScanPackageIllegalException("要扫描的包路径不能为空");
		}

		if(scanPackage.indexOf(',') == -1){
			this.init(scanPackage);
		}else{
			String [] packages = scanPackage.split(",");
			for(String pack : packages){
				if(pack == null || pack.length() == 0){
					continue;
				}

				this.init(pack);
			}
		}
	}

	protected void init(String scanPackage) {
		Map<String, HttpServlet> resultMap = AnnonationHelper
				.readServletClasses(scanPackage);

		super.initMap(resultMap);
	}
}