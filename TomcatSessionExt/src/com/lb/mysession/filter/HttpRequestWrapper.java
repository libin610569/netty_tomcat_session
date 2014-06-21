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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;


/** 
 * @author libin
 *
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {
	String sessionId = "";

	public HttpRequestWrapper(String sessionId, HttpServletRequest request) {
		super(request);
		this.sessionId = sessionId;
	}

	public HttpSession getSession(boolean create) {
		return new HttpSessionWrapper(this.sessionId, super.getSession(create));
	}

	public HttpSession getSession() {
		return new HttpSessionWrapper(this.sessionId, super.getSession());
	}
}
