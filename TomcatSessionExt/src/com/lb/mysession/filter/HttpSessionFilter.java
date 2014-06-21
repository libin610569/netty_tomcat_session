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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lb.mysession.util.MD5;



/** 
 * @author libin
 *
 */
public class HttpSessionFilter implements Filter {
	private String sessionKey = "JMSSESIONID";

	private String cookieDomain = "";

	private String cookiePath = "/TianciXSZ/";
	
	private int maxAge=-1;
	
	private String authorizedKey="authorized";
	
	private int authorizedLength=16;
	
 
	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//get cookie from response
		String sessionId = getCurrentSessionId(request, response);

		
		if (sessionId == null || sessionId.trim().length() == 0) {
			sessionId = buildSessionId();
			Cookie mycookies = new Cookie(sessionKey, sessionId);
			mycookies.setMaxAge(maxAge);
			if (this.cookieDomain != null && this.cookieDomain.length() > 0) {
				mycookies.setDomain(this.cookieDomain);
			}
			mycookies.setPath(this.cookiePath);
			response.addCookie(mycookies);
			//将请求交给 HttpRequestWrapper
			chain.doFilter(new HttpRequestWrapper(sessionId, request),
					servletResponse);
		}else{
			//需要验证session
			/*if(!checkSessionId(sessionId)){
				response.getWriter().print("this is a invalid sessoin id");
				return;
			}*/
			//when user do logout action, remove cookie from response.
			HttpServletRequest req=new HttpRequestWrapper(sessionId, request);
			checkLogoutAction(req, response,sessionId);
			chain.doFilter(req,servletResponse);
		}

	}
	
	public void init(FilterConfig config) throws ServletException {
		String key=config.getInitParameter("sessionKey");
		if(key!=null && key.trim().length()>0){
			this.sessionKey =key;	
		}
		this.cookieDomain = config.getInitParameter("cookieDomain");
		if (this.cookieDomain == null) {
			this.cookieDomain = "";
		}
		this.cookiePath = config.getInitParameter("cookiePath");
		if (this.cookiePath == null || this.cookiePath.trim().length() == 0) {
			this.cookiePath = "/";
		}
		
		String authorized=config.getInitParameter("authorizedKey");
		if(authorized!=null && authorized.trim().length()>0){
			this.authorizedKey=authorized;
		}
		if(authorizedKey!=null && authorizedKey.trim().length()>0){
		//	authorizedKey=Utils.get16MD5String(authorizedKey);	
		}
		
		//load logout action
		String logout=config.getInitParameter("logout");
		if(logout!=null && logout.trim().length()>0){
			String[] actions=logout.split(",");
			for(int i=0,n=actions.length;i<n;i++){
			}
		}
		
		
	}
	
	private String getCurrentSessionId(HttpServletRequest request,HttpServletResponse response){
		Cookie cookies[] = request.getCookies();
		Cookie mycookie = null;
		String sessionId = "";
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				mycookie = cookies[i];
				if (sessionKey.equals(mycookie.getName())) {
					sessionId = mycookie.getValue();
					//break;
				}
			}
		}
		return sessionId;
	}
	
	private String buildSessionId(){
		String sessionId = java.util.UUID.randomUUID().toString().toUpperCase();
		
		sessionId = MD5.getMD5ofStr(sessionId);
		
		/*if (authorizedKey!=null && authorizedKey.length()>0){
			StringBuilder sb=new StringBuilder(60);
			sb.append(authorizedKey);
			sb.append("-");
			sb.append(sessionId);
			sessionId=sb.toString();
		}*/
		
		return sessionId;
	}
	
	private boolean checkSessionId(String sessionId){
		if (authorizedKey!=null && authorizedKey.length()>0){
			if(sessionId.trim().length()<authorizedLength){
				return false;
			}
			String key=sessionId.substring(0, authorizedLength);
			if(authorizedKey.equals(key)){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	private void checkLogoutAction(HttpServletRequest request,HttpServletResponse response,String sessionId){
		String context = request.getContextPath();
		String uri = request.getRequestURI();
		if(context!=null && context.length()>0){
			uri=uri.substring(context.length(),uri.length());
		}
		//remove cookie
		/*if(logoutMap.containsKey(uri)){
			String sid=buildSessionId();
			Cookie[] cookies=request.getCookies();
			for(int i=0,n=cookies.length;i<n;i++){
				Cookie mycookie = new Cookie(sessionKey, null);
				mycookie.setMaxAge(0);
				mycookie.setPath(cookiePath);
				mycookie.setDomain(cookieDomain);
				response.addCookie(mycookie);	
			}
			
			
			//delete session info from sessionbox
			HttpSession session=request.getSession();
			if(session!=null){
				if(session instanceof HttpSessionWrapper){
					((HttpSessionWrapper)session).deleteSession();
				}
			}
			
		}*/
		
	}
	
	

}
