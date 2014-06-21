package com.lb.mysession.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.google.protobuf.ByteString;
import com.lb.session.ext.JavaSerializer;

public class ObjectUtil {
	
	
	public static void objectToDisk(Object obj,String filePath )
	  {
		ObjectOutputStream out = null;
		try{
			
			out  = new ObjectOutputStream(  
	                    new FileOutputStream(filePath)); 
			 
			 out.writeObject(obj); // ���л�һ����Ա����  
	            out.close(); 
	            
		}catch(Exception ex){
			
		}finally{
			if(out !=null){
			    try {
			    	out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static Object diskToObject( String filePath )
	  {
		ObjectInputStream in = null;
		try{
			
			  in = new ObjectInputStream(  
	                    new FileInputStream(filePath)); 
			 
			Object obj =  in.readObject(); // ���л�һ����Ա����  
			System.out.println(obj);
	         in.close();
	         return obj;
	            
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(in !=null){
			    try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		  return null;
	}
	
	
	/*public static byte[] objectToByte(Object obj )
			  {
		 
	 	try{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		bos.close();
		return bos.toByteArray();
	 	}catch(Exception ex){
	 		ex.printStackTrace();
	 	}
		return null;//Base64.encodeBase64String(bos.toByteArray()); 
	}
	public static Object byteToObject(byte[] b )
			throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object obj = ois.readObject();
		bis.close();
		ois.close(); 
		return obj;
	}*/
	public static ByteString ObjectToByteString(Object obj )
			  {
		 try{
			 
			 return ByteString.copyFrom(JavaSerializer.getInstance().serializeFrom(obj));
			//return ByteString.copyFrom(objectToByte(obj));
		 }catch(Exception  ex){  
			 ex.printStackTrace(); 
		 } 
		 return null;
	}
	
	public static Object ByteStringToObject(ByteString bs )
			throws Exception {
		 return JavaSerializer.getInstance().deserializeInto(bs.toByteArray());
		//return byteToObject(bs.toByteArray());
	}
	//ByteString.copyFrom(b2)
	
}
