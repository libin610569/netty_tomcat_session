package com.lb.mysession.util;

public class Node {
	public Node(){
		
	}
	public Node(String ip,int port){
		this.ip = ip;
		this.port = port;
		this.name=ip+":"+port;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private String name;
	private String ip;
	private int port;
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Node(String name){
		this.name= name;
	}
	@Override
	public String toString() { 
		return name;
	}
	
	      
    public boolean equals(Object obj) {  
        if (obj instanceof Node) {  
            return ((Node) obj).name.equals(this.name);  
        }  
          
        return false;  
    }  
      
    public int hashCode() {  
        return this.name.hashCode();  
    }  
}
