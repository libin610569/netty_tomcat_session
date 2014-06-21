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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;


/** 
 * @author libin
 *
 */
public class HttpSessionWrapper implements HttpSession {
	private String sessionId;
	private HttpSession session;

	public HttpSessionWrapper(String sessionId, HttpSession session) {
		this.sessionId = sessionId;
		this.session = session;
	}

	public Object getAttribute(String name) {
		HttpSessionService service=HttpSessionService.getInstance();
		if(service.isLocalAttribute(name)){
			if(session!=null){
				return session.getAttribute(name);
			}else{
				return null;
			}
		}else{
			return service.getAttribute(sessionId, name);
		}
	}

	public Enumeration getAttributeNames() {
		Vector v=new Vector();
		List<String> names=HttpSessionService.getInstance().getAttributeNames(sessionId);
		if(names!=null){
			for(String name: names){
				v.add(name);
			}	
		}
		if(session!=null){
			Enumeration e= session.getAttributeNames();
			while(e.hasMoreElements()){
				v.add(e.nextElement());
			}	
		}
		return v.elements();
	}

	public long getCreationTime() {
		return session.getCreationTime();
	}

	public String getId() {
		return this.sessionId;
	}

	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	public HttpSessionContext getSessionContext() {
		return session.getSessionContext();
	}

	public Object getValue(String name) {
		HttpSessionService service=HttpSessionService.getInstance();
		if(service.isLocalAttribute(name)){
			if(session!=null){
				return session.getValue(name); 
			}else{
				return null;
			}
		}else{
			return service.getAttribute(sessionId, name);
		}
	}

	public String[] getValueNames() {
		List<String> list=new ArrayList<String>();
		List<String> names=HttpSessionService.getInstance().getAttributeNames(sessionId);
		if(names!=null){
			for(String name:names){
				list.add(name);
			}
		}
		if(session!=null){
			String[] strArray=session.getValueNames();
			if(strArray!=null){
				for(int i=0,n=strArray.length;i<n;i++){
					list.add(strArray[i]);
				}
			}	
		}
		return (String[])list.toArray();
	}

	public void invalidate() {
		session.invalidate();
	}

	public boolean isNew() {
		return session.isNew();
	}

	public void putValue(String name, Object value) {
		HttpSessionService service=HttpSessionService.getInstance();
		if(service.isLocalAttribute(name)){
			if(session!=null){
				session.putValue(name, value);	
			}
		}else{
			service.setAttribute(sessionId, name, value);
		}
	}

	public void removeAttribute(String name) {
		HttpSessionService service=HttpSessionService.getInstance();
		if(service.isLocalAttribute(name)){
			if(session!=null){
				session.removeAttribute(name);	
			}
		}else{
			service.removeAttribute(sessionId, name);
		}
	}

	public void removeValue(String name) {
		HttpSessionService service=HttpSessionService.getInstance();
		if(service.isLocalAttribute(name)){
			if(session!=null){
				session.removeValue(name);	
			}
		}else{
			service.removeAttribute(sessionId, name);
		}
	}

	public void setAttribute(String name, Object value) {		
		HttpSessionService service=HttpSessionService.getInstance();
		if(service.isLocalAttribute(name)){
			if(session!=null){
				session.setAttribute(name, value);	
			}
		}else{
			service.setAttribute(sessionId, name, value);
		}	
	}

	public void setMaxInactiveInterval(int arg0) {
		session.setMaxInactiveInterval(arg0);
	}
	
	public void deleteSession(){
		HttpSessionService.getInstance().deleteSession(this.sessionId);
	}
}
