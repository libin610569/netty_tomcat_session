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

import com.google.protobuf.ByteString;
import com.lb.mysession.proto.MessageProto.Message;
import com.lb.mysession.proto.ResultProto.Result;
import com.lb.mysession.session.SessionBean;
import com.lb.mysession.session.SessionContext;
import com.lb.mysession.session.SessionMap;
import com.lb.mysession.util.ObjectUtil;



/** 
 * @author libin
 * 根据key获取
 */


public class GetCommandHandler extends AbstractCommandHandler {

	@Override
	public void doCommand(SessionContext context){
		
		Message message=context.getMessage();
		String attributeKey=message.getKey();
		String sessionId=message.getSessionId();
		SessionMap smap=context.getSessionMap();
		SessionBean session=smap.getSession(sessionId);
		if(session==null){
			String msg="session is null";

			
			writeResult(context,COMMAND_GETRESULT,SESSIONNULL, msg);
			return;
		}
	 
		//check session timeout
		if(isTimeout(session, context.getSessionTimeout())){
			smap.removeSession(sessionId);
			String msg="sessiontimeout is null";
			System.out.println(msg);
			
			writeResult(context,COMMAND_GETRESULT,SESSIONNULL, msg);
			return;
		}
		
		ByteString attribute=session.getAttribute(attributeKey);
		if(attribute==null){
			String msg="attribute is null  key==="+attributeKey;
			System.out.println(msg); 
			writeResult(context,COMMAND_GETRESULT, ATTRIBUTENULL,msg);
			return;
		}
		writeResult(context, SUCCESS, attribute);
	}
	
	private boolean isTimeout(SessionBean session,long sessionTimeout){
		long time=System.currentTimeMillis();
		if((time-session.getLastAccessedTime())>sessionTimeout){
			return true;
		}else{
			return false;
		}
	}
	
	private void writeResult(SessionContext context,String status,ByteString byteString){
		Result.Builder resultBuilder=Result.newBuilder();
		resultBuilder.setProtokey(COMMAND_GETRESULT);
		resultBuilder.setStatus(status);
		resultBuilder.setToken(context.getMessage().getToken());
		
		if(SUCCESS.equals(status)){
			resultBuilder.setDataType(context.getMessage().getDataType());
			resultBuilder.setKey(context.getMessage().getKey());
			resultBuilder.setValue(byteString);
		}else{
			System.out.println("error");
			resultBuilder.setValue (null);	
		}
		if(byteString == null){

			System.out.println("error2");
		}
		resultBuilder.setSessionId(context.getMessage().getSessionId());
		context.getCtx().writeAndFlush(resultBuilder.build());
	}
}
