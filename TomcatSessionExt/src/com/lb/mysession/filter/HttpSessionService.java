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

package com.lb.mysession.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lb.mysession.client.ModSessionNodeConnector;
import com.lb.mysession.client.SessionClient;
import com.lb.mysession.handler.Command;
import com.lb.mysession.session.AttributeBean;
import com.lb.mysession.util.StringUtil;


/** 
 * @author libin
 *
 */
public class HttpSessionService {
	private final static Logger logger=Logger.getLogger(HttpSessionService.class);
	
	
	private static HttpSessionService instance;

	private SessionClient sessionClient;
	 
	
	public Map<String,Object> localAttributeMap=null;

	
	 private static synchronized void syncInit() {
	      if ( instance == null) { instance = new HttpSessionService(); 
			instance.localAttributeMap = new HashMap<String,Object>();
	        
	      }
	    }
	    public static HttpSessionService getInstance() {
	      if (instance == null) {
	        syncInit();
	      }
	      return instance;
	    }
	    /*
	public static HttpSessionService getInstance() {
		if (instance == null) {
			instance = new HttpSessionService(); 
			instance.localAttributeMap = new HashMap<String,Object>();
		}
		return instance;
	}*/

	public void deleteSession(String sessionId) {
		try {
			sessionClient.delete(sessionId);
			if(logger.isDebugEnabled()){
				//logger.debug(StringUtil.composeString("delete session:session ID:",sessionId));
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	public Object getAttribute(String sessionId, String name) {
		if (name == null || name.trim().length() == 0) {
			return null;
		}
		try {
			if(logger.isDebugEnabled()){
				//logger.debug(StringUtil.composeString("get attribute:session ID:",sessionId,";attribute name:",name));
			}
			return sessionClient.getAttribute(sessionId, name);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	public void removeAttribute(String sessionId, String name) {
		if (name == null || name.trim().length() == 0) {
			return;
		}
		try {
			sessionClient.removeAttribute(sessionId, name);
			if(logger.isDebugEnabled()){
				logger.debug(StringUtil.composeString("remove attribute:session ID:",sessionId,";attribute name:",name));
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}
	
	public List<String> getAttributeNames(String sessionId){
		try{
			if(logger.isDebugEnabled()){
				logger.debug(StringUtil.composeString("get attributeNames:session ID:",sessionId));
			}
			return sessionClient.getAttributeNames(sessionId);
		}catch(Exception e){
			logger.info(e.getMessage());
			return null;
		}
	}

	public void setAttribute(String sessionId, String key, Object obj) {
		 AttributeBean attribute=new AttributeBean(sessionId, key, obj);
		try{
			sessionClient.setAttribute(sessionId, attribute);
			if(logger.isDebugEnabled()){
				logger.debug(StringUtil.composeString("set attribute:session ID:",sessionId,";attribute name:",key));
			}
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		
	}
	
	public boolean isLocalAttribute(String name){
		return localAttributeMap.containsKey(name);
	}


	public void Shutdown() {
		sessionClient.shutdown();
	}

	private HttpSessionService() {
		
		/*	HttpSessionConfig config = new HttpSessionConfig();
			try {
				config.load();
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			this.localAttributeMap=config.getAttributeMap();
			if(this.localAttributeMap==null){
				localAttributeMap=new HashMap<String, Object>();
			}*/
			sessionClient = new ModSessionNodeConnector();
			
			/*SessionContext context=new SessionContext();
			context.setCommandMap(commandMap);
			List<String> nodes=config.getServerNodes();
			context.setNodes(nodes);
			context.setConnectionTimeout(config.getConnectionTimeout());
			sessionClient = new ModSessionClient(context);*/
		}
	

}
