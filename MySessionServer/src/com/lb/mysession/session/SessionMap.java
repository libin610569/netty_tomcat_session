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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lb.mysession.util.ObjectUtil;



/** 
 * @author David Ding
 *
 */
public class SessionMap {
	private   Map<String, SessionBean> map;
	private static SessionMap sessionMap ;
	public static  SessionMap getSessionMap(){
		if(sessionMap == null){
			initSessionMap();
		}
		return sessionMap  ;
	}
	public static synchronized void initSessionMap  (){
		if(sessionMap == null){
			sessionMap = new SessionMap();
		} 
	}
	
	public static synchronized void initSessionMapByDisk  (Map<String, SessionBean> map){
		if(sessionMap == null){
			sessionMap = new SessionMap();
		}
		
		Iterator iter = map.entrySet().iterator(); 
		while (iter.hasNext()) { 
			String key = iter.next().toString();
			SessionBean sessionBean =  map.get(key);

			sessionMap.map.put(key, sessionBean) ;
		}
	}
	public SessionMap(){
		this.map = new ConcurrentHashMap<String, SessionBean>();
	}
	
	public void putSession(SessionBean session){
		map.put(session.getId(), session);
	}
	
	public SessionBean getSession(String id){
		return map.get(id);
	}
	
	public SessionBean removeSession(String id){
		return map.remove(id);
	}
	
	public int getSessionCount(){
		return map.size();
	}
	
	/*public int clearOldSession(long interval){
		long currentTime=System.currentTimeMillis();
		long lastAccessTime=0;
		int count=0;
		Iterator<SessionBean> it=map.values().iterator();
		SessionBean session=null;
		while(it.hasNext()){
			session=it.next();
			lastAccessTime=session.getLastAccessedTime();
			if(currentTime-lastAccessTime>interval){
				it.remove();
				count++;
			}
		}
		return count;
	}
	*/
	public Map<String,SessionBean> getMap(){
		return this.map;
	}

}
