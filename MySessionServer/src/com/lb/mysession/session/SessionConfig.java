package com.lb.mysession.session;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.lb.mysession.util.ClassUtil;

/**
 * 
 * @author libin
 * session配置
 */
public class SessionConfig {
	public static  long sessionTimeout=1000*60*30L;//10分钟
	public static  String diskFilePath= "d:\\server\\session";
	public static  int port=9091;

	static{
		  String path=ClassUtil.getAppPath(SessionConfig.class)+"\\conf.properties";
	      System.out.println(path);
			try {
				
				Properties pro=new Properties();
				InputStream in  = new FileInputStream(path);
				pro.load(in);
				
				port = Integer.parseInt(pro.getProperty("port").trim());
				System.out.println(port);
				diskFilePath =pro.getProperty("diskFilePath");
				System.out.println(diskFilePath);
				sessionTimeout=1000*60*Long.parseLong(pro.getProperty("sessionTimeout").trim());
				System.out.println(sessionTimeout); 
				
				in.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
	}
}
