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


import org.apache.log4j.Logger;

import com.google.protobuf.ByteString;
import com.lb.mysession.proto.ResultProto.Result;
import com.lb.mysession.session.SessionContext;
import com.lb.mysession.util.ObjectUtil;


/** 
 * @author libin
 * 操作 抽象类 
 */


public abstract class AbstractCommandHandler implements CommandHandler {
	private final static Logger logger=Logger.getLogger(AbstractCommandHandler.class);
	
	
	protected void writeResult(SessionContext context,String command,String status,Object obj){
		Result.Builder resultBuilder=Result.newBuilder();
		resultBuilder.setProtokey(command);
		resultBuilder.setStatus(status);
		resultBuilder.setToken(context.getMessage().getToken());
		 
        if(obj instanceof ByteString) {
    		resultBuilder.setValue((ByteString)obj);
        }else if(obj instanceof String){

    		resultBuilder.setValue(ObjectUtil.ObjectToByteString(obj));
        }
      
        resultBuilder.setKey(context.getMessage().getKey());
		resultBuilder.setSessionId(context.getMessage().getSessionId());

		context.getCtx().writeAndFlush(resultBuilder.build());
	}
	

	@Override
	public abstract void doCommand(SessionContext context);

}
