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

package com.lb.mysession.client;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger; 

import com.lb.mysession.handler.Command;
import com.lb.mysession.handler.DelCommand;
import com.lb.mysession.handler.GetCommand;
import com.lb.mysession.handler.RemoveCommand;
import com.lb.mysession.handler.SetCommand;
import com.lb.mysession.session.AttributeBean;
import com.lb.mysession.util.ConfInit;
import com.lb.mysession.util.HashServer;
import com.lb.mysession.util.IdWorkerFactory;
import com.lb.mysession.util.Node;


/** 
 * @author libin
 * 连接池选择类
 */
public class ModSessionNodeConnector implements SessionClient {
	private final static Logger logger=Logger.getLogger(ModSessionNodeConnector.class);
	public static  Map<String, Command> commandMap ; 
	private long OPERATE_TIME_OUT = 5000L;
	public static Map<Node,NioHttpSessionConnector>  connectors ;
	public static Map<Node,NioHttpSessionConnector>  connectorsError ;
//	public static	 List<Node> allNodes  ;
	 
	public ModSessionNodeConnector(){
		this.init(); 
	}
	public void init( ) {
		connectors = new ConcurrentHashMap<>();
		commandMap = new ConcurrentHashMap<>();
		connectorsError = new ConcurrentHashMap<>(); 
	    // allNodes = ConfInit.allNodes; 
		System.out.println("初始化连接  ------------------");
		HashServer.init( ConfInit.allNodes);
		for(int i=0;i< ConfInit.allNodes.size();i++){
			NioHttpSessionConnector d = new NioHttpSessionConnector( ConfInit.allNodes.get(i).getIp(),  ConfInit.allNodes.get(i).getPort());
			try {
				d.run();
			} catch (Exception e) { 
				e.printStackTrace();
			}
			connectors.put( ConfInit.allNodes.get(i), d);
		}
		 
	}


	public void shutdown() {
		/*for (int i = 0; i < connectors.length; i++) {
			connectors[i].shutdown();
		}*/
	}
	public Object getAttribute(String sessionId,String key) throws Exception {
		Command command = new GetCommand(sessionId,key);
		command.setToken(IdWorkerFactory.getInstance().nextId()); 
		// Command touchCommand=new TouchCommand(sessionId);
		return writeMessage(command);
	}

	public boolean delete(String sessionId) throws Exception {
		 Command command = new DelCommand(sessionId);
		writeMessage(command);
		if (Command.SUCCESS.equals(command.getStatus())) {
			return true;
		} else {
			return false;
		} 
	}
	

	@Override
	public boolean removeAttribute(String sessionId, String key)
			throws Exception {
		Command command = new RemoveCommand(sessionId,key);
		writeMessage(command);
		if (Command.SUCCESS.equals(command.getStatus())) {
			return true;
		} else {
			return false;
		} 
	}

	public boolean setAttribute(String sessionId, AttributeBean attribute) throws Exception {
	 	Command command = new SetCommand(attribute);

		command.setToken(IdWorkerFactory.getInstance().nextId()); 
		writeMessage(command);
		if (Command.SUCCESS.equals(command.getStatus())) {
			return true;
		} else {
			return false;
		} 
	}
	
	

	@Override
	public List<String> getAttributeNames(String sessionId) throws Exception {
	/*	Command command = new GetNamesCommand(sessionId);
		List<String> result =(List<String>)writeMessage(command, null);
		return result;*/
		return null;
	}

	/*private Object writeMessage(Command firstCommand, Command secCommand) {
		if (connectors == null || connectors.length == 0) {
			return null;
		}
		HttpSessionConnector connector = null;
		Object result = null;
		String key=null;
		String uuid=null;
		boolean bsuccess = false;
		String status=null;
		Command command=null;
		for (int i = 0, n = connectors.length; i < n; i++) {
			try {
				command=firstCommand;
				if(bsuccess && secCommand!=null){
					command=secCommand;
				}
				uuid=UUIDHexGenerator.generate();
				command.setUuid(uuid);//需要发送出去的
				key=StringUtil.appendString(command.getAttribute().getSessionId(), uuid);
				commandMap.put(key, command);	//存放get指令
				command.resetCountDownLatch();
				connector = connectors[i];
				if(connector.checkConnected()){
					connector.writeMessage(command.getSendObject());	
				}else{
					continue;
				}
				if (bsuccess) {
					continue;
				}
				try {
					//latchWait(command, OPERATE_TIME_OUT);
					if(!command.getLatch().await(OPERATE_TIME_OUT, TimeUnit.MILLISECONDS)){
						StringBuilder sb=new StringBuilder();
						sb.append("Timed out(");
						sb.append(OPERATE_TIME_OUT);
						sb.append(") waiting for operation");
						logger.info(sb.toString());
						continue;
					}
				} catch (InterruptedException ex) {
					logger.info(ex.getMessage());
					continue;
				}
				command.decode();
				status=command.getStatus();
				result =command.getResult();
				if (status != null && Command.SUCCESS.equals(status)) {
					bsuccess = true;
				}
			} finally {
				commandMap.remove(key);
			}

		}
		return result;
	}*/

	public Object writeMessage(Command command ) {
		/*if (connectors == null || connectors.length == 0) {
			return null;
		}*/
		 
		 //NioHttpSessionConnector
		Node node = HashServer.getHashServer(command.getAttribute().getId()); 
	//	System.out.println("节点名字"+node.getIp()+""+node.getPort());
		NioHttpSessionConnector nioHttpSessionConnector = connectors.get(node);
		if(!nioHttpSessionConnector.isActive()){
			System.out.println("无法连接服务器"+connectorsError.size()); 
			if(connectorsError.get(node) ==null){
				System.out.println("加载到错误列表");
				connectorsError.put(node, nioHttpSessionConnector);  
				new ConnErrorThread(nioHttpSessionConnector,node).start();
			}
			return null; 
		}
		Object result = null;
		String key=null;
		boolean bsuccess = false;
		String status=null;
	 	 
			try {
				//System.out.println(command.getAttribute().getKey());
				//System.out.println(command.getAttribute().getId());
				
				 key= command.getAttribute().getId() +command.getToken();
				commandMap.put(key, command);	//存放get指令
				command.resetCountDownLatch();
				if(nioHttpSessionConnector !=null){//connector.checkConnected()
					nioHttpSessionConnector.writeMessage(command.getSendObject());	
				}else{
					return null; 
				}
				try {
					if(command instanceof GetCommand){ 
						if(!command.getLatch().await(5000, TimeUnit.MILLISECONDS)){
							/*StringBuilder sb=new StringBuilder();
							sb.append("Timed out(");
							sb.append(5000);
							sb.append(") waiting for operation");*/
						}
					}else if(command instanceof SetCommand){ 
						if(!command.getLatch().await(5000, TimeUnit.MILLISECONDS)){
							/*StringBuilder sb=new StringBuilder();
							sb.append("Timed out(");
							sb.append(5000);
							sb.append(") waiting for operation");*/
						}
					}
					//latchWait(command, OPERATE_TIME_OUT);
					
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				//
				
				
				status=command.getStatus();
				result =command.getResult();
				if(result == null){

					//  System.out.println(result);
				}
				if (status != null && Command.SUCCESS.equals(status)) {
					bsuccess = true;
				}
			} finally {
			//	System.out.println(commandMap);
				//System.out.println("remvowe"+key+"  "+command.getToken());
				 commandMap.remove(key);
				
			}
		
		//释放
		status = null; 
		command =null;
		if(bsuccess){
			return result;
		}
		return null;
	}
	private void latchWait(final Command cmd, final long timeout)
			throws InterruptedException, TimeoutException {
		if (!cmd.getLatch().await(timeout, TimeUnit.MILLISECONDS)) {
			throw new TimeoutException("Timed out(" + timeout
					+ ") waiting for operation");
		}
	}
}
