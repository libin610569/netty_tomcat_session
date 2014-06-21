package com.lb.mysession.thread;

import io.netty.channel.ChannelHandlerContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.lb.mysession.handler.CommandHandler;
import com.lb.mysession.model.MessageObj;
import com.lb.mysession.proto.MessageProto.Message;
import com.lb.mysession.proto.ResultProto.Result;
import com.lb.mysession.server.CommandHandlerFactory;
import com.lb.mysession.session.SessionConfig;
import com.lb.mysession.session.SessionContext;
import com.lb.mysession.session.SessionMap;

public class TestThread2  extends Thread implements BarrierHolder {
	private Object barrier = new Object();
	
	 public static  Map<String,ChannelHandlerContext> map = new ConcurrentHashMap();
	 public static  Map<String,Message> mapMessage = new ConcurrentHashMap();
	//public static  OptimisticQueue<MessageObj> queue = new OptimisticQueue<MessageObj>(20);
	public static BlockingQueue<MessageObj> queue = new ArrayBlockingQueue<MessageObj>(1024);
	
	public static void put(String key,ChannelHandlerContext ctx,Message message){
		//MessageObj messageObj = new MessageObj();
		//messageObj.setCtx(ctx);
		//messageObj.setMessage(message); 
		//queue.offer(messageObj);
		 map.put( key, ctx);	mapMessage.put( key, message);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		while(true){
			try{
		 	Thread.sleep(1);
			 
			  Set<String> set= map.keySet();
			
			 
			 Iterator<String>  ite=  set.iterator() ;
			 
			 while(ite.hasNext()){
				 String key =  ite.next();
				 ChannelHandlerContext ctx = map.remove(key);
				 Message message =  mapMessage.remove(key);
				 if(message==null){
				    	Result.Builder resultBuilder=Result.newBuilder();
				    	resultBuilder.setStatus(CommandHandler.ERROR);
				    	ctx.writeAndFlush(resultBuilder.build()).sync();
				    	//e.getChannel().write(resultBuilder.build());
				    	continue;
				    } 
				 
				    CommandHandler handler=CommandHandlerFactory.geCommandHandler(message.getProtokey());
				    if(handler==null){
				    	continue;
				    }
				    SessionContext context=new SessionContext();
				    context.setSessionMap(SessionMap.getSessionMap());
				    context.setCtx( ctx);
				    context.setMessage(message);
				    context.setSessionTimeout(SessionConfig.sessionTimeout);
				    handler.doCommand(context);
			 } 
			
			}catch(Exception ex){
				
			}
		}
	}
	@Override
	public Object getBarrier() {
		// TODO Auto-generated method stub
		return barrier;
	} 
	
}
