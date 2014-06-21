package com.lb.mysession.util;

 
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author libin
 * 初始化配置
 */
public class ConfInit { 
	public static int port =0;
	static {
		try {
			String path=ClassUtil.getAppPath(ConfInit.class)+"\\conf.properties";
				
				
			InputStream is = new FileInputStream(path);
			Properties props = new Properties();
			props.load(is);
			port =Integer.parseInt(props.getProperty("port").trim());
			System.out.println(port); 
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("初始化失败"+e.getMessage());
		}
	}
 
}
