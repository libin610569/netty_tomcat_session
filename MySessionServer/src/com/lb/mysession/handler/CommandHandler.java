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

import com.lb.mysession.session.SessionContext;




/** 
 * @author libin
 * 操作类型定义
 */

public interface CommandHandler {
	public final static String COMMAND_GET="get";  //get attribute or value from session
	public final static String COMMAND_GETS="gets";;  //get attribute list or value list
	public final static String COMMAND_IMP="imp";    //import session
	public final static String COMMAND_SET="set";   // set attribute or value to session
	public final static String COMMAND_DEL="del";    // delete session object
	public final static String COMMAND_RMV="rmv";    // remove attribute or value from session
	public final static String COMMAND_TOUCH="touch"; //update lastAceessTime for session
	
	public final static String COMMAND_GETRESULT="getresult";
	public final static String COMMAND_GETSRESULT="getsresult";
	public final static String COMMAND_IMPRESULT="impresult";
	public final static String COMMAND_DELRESULT="delresult"; 
	public final static String COMMAND_SETRESULT="setresult";
	public final static String COMMAND_RMVRESULT="rmvresult";
	public final static String COMMAND_TOUCHRESULT="touchresult";
	
	
	public final static String SUCCESS="1";
	public final static String UNSUCCESS="0";
	public final static String SESSIONNULL="2";
	public final static String ATTRIBUTENULL="3";
	public final static String ERROR="-1";
	
	public void doCommand(SessionContext context);

}
