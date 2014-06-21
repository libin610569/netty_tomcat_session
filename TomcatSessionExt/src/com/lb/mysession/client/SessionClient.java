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

package com.lb.mysession.client;

import java.util.List;

import com.lb.mysession.session.AttributeBean;
import com.lb.mysession.session.SessionBean;


/** 
 * @author libin
 *
 */
public interface SessionClient {
	//get attribute from session
	public Object getAttribute(String sessionId,String key)throws Exception;
	
	//get attribute name list from session
	public List<String> getAttributeNames(String sessionId)throws Exception;
	
	
	//save attribute to session
	public boolean setAttribute(String sessionId,AttributeBean obj)throws Exception;
	
	
	//remove attribute from session
	public boolean removeAttribute(String sessionId,String key)throws Exception;
	
	//delete session
	public boolean delete(String sessionId)throws Exception;
	
	public void shutdown();
}
