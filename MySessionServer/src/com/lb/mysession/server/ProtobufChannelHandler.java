package com.lb.mysession.server;



import com.google.protobuf.ByteString;
import com.lb.mysession.handler.CommandHandler;
import com.lb.mysession.model.User;
import com.lb.mysession.proto.MessageProto.Message;
import com.lb.mysession.proto.ResultProto.Result;
import com.lb.mysession.session.SessionConfig;
import com.lb.mysession.session.SessionContext;
import com.lb.mysession.session.SessionMap;
import com.lb.mysession.thread.DisruptorThread;
import com.lb.mysession.util.ObjectUtil;
import com.lb.mysession.util.StringUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProtobufChannelHandler extends SimpleChannelInboundHandler<Message> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message)
			throws Exception {
		
		
	    //  TestThread.put( message.getSessionId()+message.getToken()+message.getProtokey(),ctx, message);
/*
TicketEvent event = new TicketEvent();
event.setMessage(message);
event.setCtx(ctx);*/
		DisruptorThread.publishQueryEvent(message,ctx);
	    	
		 /*
		 Result.Builder resultBuilder=Result.newBuilder();
			resultBuilder.setProtokey(message.getProtokey());
			resultBuilder.setStatus("");
			resultBuilder.setToken(message.getToken());
			 
	    
			
	        resultBuilder.setKey(message.getKey());
			resultBuilder.setSessionId(message.getSessionId());

			//resultBuilder.setValue(ByteString.copyFrom(new byte[4000]));
			ctx.writeAndFlush(resultBuilder.build());*/
			/* 
		    if(message==null){
		    	Result.Builder resultBuilder=Result.newBuilder();
		    	resultBuilder.setStatus(CommandHandler.ERROR);
		    	ctx.writeAndFlush(resultBuilder.build()).sync();
		    	//e.getChannel().write(resultBuilder.build());
		    	return;
		    } 
		//   System.out.println("yaoz"+message.getProtokey()); 
		    CommandHandler handler=CommandHandlerFactory.geCommandHandler(message.getProtokey());
		  //  System.out.println(message.getProtokey());
		    if(handler==null){
		    	return;
		    }
		    SessionContext context=new SessionContext();
		    context.setSessionMap(SessionMap.getSessionMap());
		    context.setCtx( ctx);
		    context.setMessage(message);
		    context.setSessionTimeout(SessionConfig.sessionTimeout);
		    handler.doCommand(context);   */
		  //e.getMessage();
	        //Ϊ�˽�ʡ������,���������ж���request����response.
	        /*if(message instanceof MessageProto.Message) {
	    
	        	MessageProto.Message c = (MessageProto.Message)message ;
		            System.out.println(c.getProtokey());
		            byte[] bb = c.getValue().toByteArray();
		           System.out.println(bb.length);
		           
		           
		           if(c.getDataType() .equals("str")){
		        	   System.out.println(StringUtil.string4Object(bb).toString());
		           }else{
		            User u = (User) StringUtil.string4Object(bb);
		            System.out.println(u.getName());
		           }
		           
		 		  ResultProto.Result.Builder builder2 =
						  ResultProto.Result.newBuilder();
		 		 builder2.setDataType("str");
		 		builder2.setProtokey("hhhhh");
				    byte[]b=  StringUtil.object4String("�Ǻ�");
				    System.out.println(b.length);
				    ByteString bs=ByteString.copyFrom(b);
				    builder2.setValue(bs);
		 		 
				    

				    ResultProto.Result command = builder2.build();
				   System.out.println(command.toByteArray().length);
				   // Thread.sleep(1000*10);
				    System.out.println("xiechu"+command);
				   
				    byte[] bsd = command.toByteArray();
				    
				    ctx.writeAndFlush(command).sync();
				
					
		 		// ctx.write(builder2);
		 		 
	             //ctx.getChannel().write(builder2);
	        } else if(message instanceof SocketCommand.ResponseCommand) {
	            SocketCommand.ResponseCommand responseCommand = (SocketCommand.ResponseCommand)message ;
	           // log.info(responseCommand.getSuccess() + ", " + responseCommand.getMessage());
	        }*/
		
	}

	 

   
}