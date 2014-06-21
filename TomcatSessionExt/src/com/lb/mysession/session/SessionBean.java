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
 * @author libin
 *
 */
public class SessionBean implements Serializable {
	private String id; 
	private String key; 
	
	private Map<String,ByteString > attributeMap;	
	
	public SessionBean(){
		this.attributeMap=new HashMap<String, ByteString>();
	}
	
	public SessionBean(String id) {
		this.id = id;
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
	
	 
	public List<ByteString> getAtttibuteList(){
		List<ByteString> list=new ArrayList<ByteString>();
		for(ByteString attribute : attributeMap.values()){
			list.add(attribute);
		}
		return list;
	}
	
 

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	 
    
}
