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

package com.lb.mysession.server;

import java.util.HashMap;
import java.util.Map;

import com.lb.mysession.handler.CommandHandler;
import com.lb.mysession.handler.DeleteCommandHandler;
import com.lb.mysession.handler.GetCommandHandler;
import com.lb.mysession.handler.RemoveCommandHandler;
import com.lb.mysession.handler.SetCommandHandler;
/**
 * 
 * @author libin
 * 操作对应类选择
 */
public class CommandHandlerFactory {
	private static Map<String, CommandHandler> handlerMap = new HashMap<String, CommandHandler>();
	static {
		handlerMap.put(CommandHandler.COMMAND_SET, new SetCommandHandler());
		handlerMap.put(CommandHandler.COMMAND_GET, new GetCommandHandler());
		handlerMap.put(CommandHandler.COMMAND_DEL, new DeleteCommandHandler());
		handlerMap.put(CommandHandler.COMMAND_RMV, new RemoveCommandHandler());
	}
	
	public static CommandHandler geCommandHandler(String command){
		return handlerMap.get(command);
	}

}
