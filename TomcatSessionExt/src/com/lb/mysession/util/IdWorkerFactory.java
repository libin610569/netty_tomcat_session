package com.lb.mysession.util;

import java.util.HashMap;
import java.util.Map;


public class IdWorkerFactory {

	 private static IdWorker instance = null;
	    
	    private IdWorkerFactory() {
	      //Load configuration informa tion from DB or file
	      //Set values for properties 
	    }
	  
	    private static synchronized void syncInit(Integer node_id) {
	      if ( instance == null) {
	        instance = new IdWorker(node_id);
	        
	      }
	    }
	    public static IdWorker getInstance()throws IllegalArgumentException {
	      if (instance == null) {
	        
	    	  Object obj =  3;// SystemConfig.getInstance().getObject("node_id");
	    	  if(obj!=null){
	    		  syncInit(Integer.parseInt(obj.toString()));
	    	  }else{
	    		  throw new IllegalArgumentException(String.format("node id is null "));
	    	  }
	    	 
	        
	      }
	      return instance;
	    }
	   
	   public static void main(String[] args) throws Exception {
		 // SystemConfig.getInstance().put("node_id", 1);
		  try{
			  long d = System.currentTimeMillis();
			  
			  for(int i=0;i<1000000;i++){
		  IdWorkerFactory.getInstance().nextId();
			  }
			  long d2 = System.currentTimeMillis();
			  System.out.println(d2-d);
		  }catch(IllegalArgumentException e) {
			  // 如果null 就要初始化 node_id
			  //e.printStackTrace();
		  }
	   }

}
