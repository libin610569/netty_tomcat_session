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

import com.lb.mysession.session.AttributeBean;



/** 
 * @author libin
 *
 */
public class DelCommand extends AbstractCommand implements Command {
	
	public DelCommand(String sessionId){
		super();
		this.attribute=new AttributeBean(sessionId, null, null);
	}
	
	@Override
	public Object getSendObject() {		
		return getSendObject(COMMAND_DEL);
	}

	@Override
	public AttributeBean getAttribute() {
		// TODO Auto-generated method stub
		return this.attribute;
	}
	
}
