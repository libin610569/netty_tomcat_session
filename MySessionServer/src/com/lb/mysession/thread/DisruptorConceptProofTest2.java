package com.lb.mysession.thread;


import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;










import com.lb.mysession.handler.CommandHandler;
import com.lb.mysession.proto.MessageProto.Message;
import com.lb.mysession.proto.ResultProto.Result;
import com.lb.mysession.server.CommandHandlerFactory;
import com.lb.mysession.session.SessionConfig;
import com.lb.mysession.session.SessionContext;
import com.lb.mysession.session.SessionMap;
import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.MultiThreadedClaimStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SingleThreadedClaimStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorConceptProofTest2 {
    private static RingBuffer<TicketEvent> _ringBuffer;
    private static Disruptor<TicketEvent> _disruptor;
    // Ĭ�ϵ�������Ϣ����Ӧ��Ϣ���еĴ�С��2��13�η�
    private static int RING_SIZE = 2 << 13;
    private static final ExecutorService EXECUTOR = 
	Executors.newCachedThreadPool();
/*    final EventHandler<TicketEvent> _eventProcessor = new EventHandler<TicketEvent>() {
		public void onEvent(final TicketEvent event, final long sequence,
				final boolean endOfBatch) throws Exception {
			System.out.println("[processor] ===="
					+ Thread.currentThread().getName() + "===>"
					+ sequence+"  event.getType() "+event.getType()+" "+event.getValue());
		}
	};*/
    
    public static void main(String[] args) {
    	
    	/*for(int i=0;i<100;i++){
    		TicketEvent event = new TicketEvent();
    		event.setValue(i);
    		publishQueryEvent(event);
    	}*/
    	
	}
    
    // ����Ϣ���з���һ����ѯ�����¼�
    // 写入消息
    public static void publishQueryEvent(TicketEvent args) {
		long sequence = _ringBuffer.next();
		TicketEvent event = _ringBuffer.get(sequence);
	//	System.out.println("event "+event);
	//	System.out.println("args "+args);
		
	//	event = args;
		//System.out.println("args2 "+event);
		args.copyTo(event);
		event.setSequence(sequence);
	
		_ringBuffer.publish(sequence); 
    }
    
    public static void startDisruptor() {      
    	/*_disruptor = 
    	    new Disruptor<TicketEvent>
    	    (
    	     TicketPoolService.QueryFactory,
    	     EXECUTOR,
    	     new SingleThreadedClaimStrategy(RING_SIZE),
    	     new BlockingWaitStrategy()
    	     );
 */
    //	new  Disruptor(TicketPoolService.QueryFactory,RING_SIZE);
    	   
    	_disruptor.handleEventsWith(_eventProcessor);
    /*	_disruptor
    	     .handleEventsWith(_journalist, _replicator)
    	     .then(_eventProcessor); */
    		_ringBuffer = _disruptor.start();
        }
    
    

    //日志
    static final EventHandler<TicketEvent> _journalist = 
	  new EventHandler<TicketEvent>() {
	  public void onEvent(final TicketEvent event, 
			      final long sequence,
			      final boolean endOfBatch) throws Exception {
		  
		  
		  
	  }
    };
    
    //备份
    static final EventHandler<TicketEvent> _replicator = new EventHandler<TicketEvent>() {
	  public void onEvent(final TicketEvent event, final long sequence,
			      final boolean endOfBatch) throws Exception {
	  }
    };

   static final EventHandler<TicketEvent> _eventProcessor = 
	new EventHandler<TicketEvent>() {
	public void onEvent(final TicketEvent event,
			    final long sequence,
			    final boolean endOfBatch) throws Exception {
		
		 ChannelHandlerContext ctx =  event.getCtx();
		 Message message =  event.getMessage();
			
		 
		 if (message == null) {
				Result.Builder resultBuilder = Result.newBuilder();
				resultBuilder.setStatus(CommandHandler.ERROR);
				ctx.writeAndFlush(resultBuilder.build()).sync();
				 return ;
			}
			CommandHandler handler = CommandHandlerFactory
					.geCommandHandler(message.getProtokey());
			if (handler == null) {
				 return ;
			}
			SessionContext context = new SessionContext();
			context.setSessionMap(SessionMap.getSessionMap());
			context.setCtx(ctx);
			context.setMessage(message);
			context.setSessionTimeout(SessionConfig.sessionTimeout);
			handler.doCommand(context);
			
		  
		  /*
	     ChannelFuture future = event.channel.write(result);
	     
	     
	     future.addListener(ChannelFutureListener.CLOSE);*/ 
	}
   };
}
