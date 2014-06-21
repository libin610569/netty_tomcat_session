package com.lb.session.ext;

import org.apache.catalina.util.CustomObjectInputStream;

import com.lb.mysession.filter.HttpSessionService;

import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.HashMap;


public class JavaSerializer  {
  public static JavaSerializer javaSerializer;
  private static synchronized void syncInit() {
      if ( javaSerializer == null) { 
    	  javaSerializer = new JavaSerializer(); 
      }
    }
  
  public static JavaSerializer getInstance(){
	  
	  if(javaSerializer == null){
		  syncInit();//javaSerializer = new JavaSerializer();
	  }
	  return javaSerializer;
  }
	
  public static  ClassLoader loader;

 /* public   void setClassLoader(ClassLoader loader) {
     loader = loader;
  }*/

  public byte[] serializeFrom(Object obj) throws IOException {

  // MySession redisSession = (MySession) session;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));
  //  oos.writeLong(redisSession.getCreationTime());
  //  obj.writeObjectData(oos);
    oos.writeObject(obj);
    oos.flush();
    byte[] b = bos.toByteArray();
    
    oos.close();bos.close();
    return b ;
  }

  public Object deserializeInto(byte[] data ) throws IOException, ClassNotFoundException {
 

    BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
 //   System.out.println("loader--------------------"+loader);
    ObjectInputStream ois = new CustomObjectInputStream(bis, loader);
    Object obj = ois.readObject();
    ois.close(); 
    bis.close(); 

    return obj;
  }
}
