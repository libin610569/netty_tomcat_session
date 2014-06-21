/*
 * Copyright 2011 David Ding.
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */


package com.lb.mysession.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.ByteString;


/** 
 * @author David Ding
 *
 */
public class SessionBean implements Serializable {
	private String id;
	private String userId;
	private long creationTime;
	private long lastAccessedTime;
	private Map<String,ByteString > attributeMap;	
	
	public SessionBean(){
		this.attributeMap=new HashMap<String, ByteString>();
	}
	
	public SessionBean(String id) {
		this.id = id;
		this.creationTime=System.currentTimeMillis();
		this.lastAccessedTime = System.currentTimeMillis();
		this.attributeMap=new HashMap<String, ByteString>();
	}
	
	
	/*
	public SessionBean(String id,long creationTime,long lastAccessedTime,List<byte[]> attributeList){
		this.id = id;
		this.creationTime=creationTime;
		this.lastAccessedTime = lastAccessedTime;
		this.attributeMap=new HashMap<String, byte[]>();
		for(byte[] attribute : attributeList){
			attributeMap.put(attribute.getKey(), attribute);
		}
	}*/
	
	public void updateLastAccessTime(){
		this.lastAccessedTime=System.currentTimeMillis();
	}
	
	public List<ByteString> getAtttibuteList(){
		List<ByteString> list=new ArrayList<ByteString>();
		for(ByteString attribute : attributeMap.values()){
			list.add(attribute);
		}
		return list;
	}
	
	public void setAttribute(String key,ByteString attribute){
		attributeMap.put(key, attribute);
		lastAccessedTime=System.currentTimeMillis();
	}
	
	public ByteString getAttribute(String key){
		lastAccessedTime=System.currentTimeMillis();
		return attributeMap.get(key);
	}
	
 
	public  ByteString removeAttribute(String key){
		lastAccessedTime=System.currentTimeMillis();
		return attributeMap.remove(key);
	}
	
	 
	
	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}
	public void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

}
