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

import io.netty.channel.ChannelHandlerContext;

import com.lb.mysession.proto.MessageProto.Message;



/** 
 * @author David Ding
 *
 */
public class SessionContext {
	private SessionMap sessionMap;
	 private ChannelHandlerContext ctx;
	 
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	private Message message;
	private long sessionTimeout;
	
	public SessionMap getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(SessionMap sessionMap) {
		this.sessionMap = sessionMap;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public long getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(long sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	
	

}
