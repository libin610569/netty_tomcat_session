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

import com.lb.mysession.proto.MessageProto.Message;
import com.lb.mysession.session.SessionBean;
import com.lb.mysession.session.SessionContext;
import com.lb.mysession.session.SessionMap;


/** 
 * @author libin
 * 删除key
 */

public class RemoveCommandHandler extends AbstractCommandHandler {

	@Override
	public void doCommand(SessionContext context) {
		Message message=context.getMessage();
		String sessionId=message.getSessionId();
		SessionMap smap=context.getSessionMap();
		SessionBean session=smap.getSession(sessionId);
		if(session==null){
			String msg="session is null";
			writeResult(context, COMMAND_RMVRESULT, SESSIONNULL, msg);
		}
		
		String attributeKey=message.getKey();
		session.removeAttribute(attributeKey);
		
		writeResult(context, COMMAND_RMVRESULT, SUCCESS, "");
	}
}
