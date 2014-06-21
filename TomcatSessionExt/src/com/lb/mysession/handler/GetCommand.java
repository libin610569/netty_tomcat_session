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

import java.util.Date;






import com.lb.mysession.proto.MessageProto.Message;
import com.lb.mysession.session.AttributeBean;
import com.lb.mysession.session.SessionBean;
import com.lb.mysession.util.ObjectUtil;





/** 
 * @author libin
 *
 */
public class GetCommand extends AbstractCommand{
	
	public GetCommand(String sessionId,String key){
		super();
		attribute=new AttributeBean(sessionId,key);
	}

	@Override
	public Object getSendObject() {
		Message.Builder msgBuilder=Message.newBuilder();
		msgBuilder.setProtokey(COMMAND_GET);
		msgBuilder.setSessionId(attribute.getId()); 
		msgBuilder.setKey(attribute.getKey());
		msgBuilder.setToken(getToken());
		/*if (DataType.STRING.equals(type)){
			value=(String)attribute.getValue();	
		}else if (DataType.DATE.equals(type)){
			Date date=(Date)attribute.getValue();
			value=String.valueOf(date.getTime());
			
		}else if(DataType.OBJECT.equals(type)){
			try {
				value = StringUtil.object2String(attribute.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			value=String.valueOf(attribute.getValue());
		}	*/
		return msgBuilder.build();
		
	}

 
	@Override
	public AttributeBean getAttribute() {
		// TODO Auto-generated method stub
		return attribute;
	}
 
}
