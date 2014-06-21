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

package com.lb.mysession.handler;

import java.util.concurrent.CountDownLatch;







import com.google.protobuf.ByteString;
import com.lb.mysession.proto.MessageProto.Message;
import com.lb.mysession.session.AttributeBean;
import com.lb.mysession.session.SessionBean;

/** 
 * @author libin
 *
 */
public abstract class AbstractCommand implements Command{
	
	protected CountDownLatch latch;
	
	protected AttributeBean attribute;
	
	
	protected Object result;
	
	protected String status;

	protected long token;
	
	
	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}



	protected boolean decoded;
	
	public AbstractCommand(){
		
		latch=new CountDownLatch(1);
	}
	
	public void resetCountDownLatch(){
		latch=new CountDownLatch(1);
	}
	
	 
	public void setStatus(String status){
		this.status =status;
	}
	public void countDown(){
		if(latch!=null){
			latch.countDown();
		}
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	 
	public String getStatus(){
		return this.status;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	public Object getResult() {
	 
		return result;
	}
	


	public Object getSendObject(String protoKey){
		Message.Builder msgBuilder=Message.newBuilder();
		msgBuilder.setProtokey(protoKey);
		msgBuilder.setSessionId(attribute.getId());
		msgBuilder.setKey(attribute.getKey());	 
		/*if(attribute.getKey()!=null){
			msgBuilder.setKey(attribute.getKey());	
		}
		msgBuilder.setToken(attribute.getToken());
		msgBuilder.setUuid(getUuid());*/
		return msgBuilder.build();
	}
	 
	

	@Override
	public abstract Object getSendObject();
	
	
}
