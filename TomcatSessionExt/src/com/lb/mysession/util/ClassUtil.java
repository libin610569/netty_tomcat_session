package com.lb.mysession.util;

public class ClassUtil {
	
	public static void main(String[] args) {
		System.out.println(getAppPath(ClassUtil.class));
	}
	 public static String getAppPath(Class cls){   
	     //�?��用户传入的参数是否为�?  
	     if(cls==null)    
	       throw new java.lang.IllegalArgumentException("参数不能为空");   
	      ClassLoader loader=cls.getClassLoader();   
	      //获得类的全名，包括包�?   
	      String clsName=cls.getName()+".class";   
	      //获得传入参数�?��的包   
	      Package pack=cls.getPackage();   
	      String path="";   
	      //如果不是匿名包，将包名转化为路径   
	      if(pack!=null){   
	          String packName=pack.getName();   
	         //此处�?��判定是否是Java基础类库，防止用户传入JDK内置的类�?  
	         if(packName.startsWith("java.")||packName.startsWith("javax."))    
	            throw new java.lang.IllegalArgumentException("不要传�?系统类！");   
	          //在类的名称中，去掉包名的部分，获得类的文件名   
	          clsName=clsName.substring(packName.length()+1);   
	          //判定包名是否是简单包名，如果是，则直接将包名转换为路径，   
	          if(packName.indexOf(".")<0) path=packName+"/";   
	          else{//否则按照包名的组成部分，将包名转换为路径   
	              int start=0,end=0;   
	              end=packName.indexOf(".");   
	              while(end!=-1){   
	                  path=path+packName.substring(start,end)+"/";   
	                  start=end+1;   
	                  end=packName.indexOf(".",start);   
	              }   
	              path=path+packName.substring(start)+"/";   
	          }   
	      }   
	      //调用ClassLoader的getResource方法，传入包含路径信息的类文件名   
	      java.net.URL url =loader.getResource(path+clsName);   
	      //从URL对象中获取路径信�?  
	      String realPath=url.getPath();   
	      //去掉路径信息中的协议�?file:"   
	      int pos=realPath.indexOf("file:");   
	      if(pos>-1) realPath=realPath.substring(pos+5);   
	      //去掉路径信息�?��包含类文件信息的部分，得到类�?��的路�?  
	      pos=realPath.indexOf(path+clsName);   
	      realPath=realPath.substring(0,pos-1);   
	      //如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名   
	      if(realPath.endsWith("!"))   
	          realPath=realPath.substring(0,realPath.lastIndexOf("/"));   
	     try{   
	    	 realPath=java.net.URLDecoder.decode(realPath,"utf-8");   
	     }catch(Exception e){throw new RuntimeException(e);}   
	     return realPath.substring(1);   
	 }//getAppPath定义结束   
}
