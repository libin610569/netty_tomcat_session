
package com.lb.mysession.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
 

/**
 * 
 * @author libin
 * 连接对象
 */

public class NioHttpSessionConnector {

	public final String host;
    public final int port;
    public   Channel ch =null;
    public int errorCount;
    
    public NioHttpSessionConnector(String host, int port) {
        this.host = host;
        this.port = port; 
    }
    public void restInit( ) throws Exception {
        run();
    }
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new SessionClientInitializer());

            ch = b.connect(host, port).sync().channel();
        }
        catch(Exception ex){
        	ex.printStackTrace();System.out.println("conn error");
        }
        finally {
         //   group.shutdownGracefully();
        }
    }
    public void writeMessage(Object message){
    	if(ch.isActive()){
        	ch.writeAndFlush(message);   
    	}else{

    	}
    }
    public boolean isActive(){
    	
    	if(ch !=null &&  ch.isActive()){
    		//System.out.println("可以发送");
    		return true;
    	}  
    	return false; 
    }
      
  
   public static void main(String[] args) {

	   try {
		   NioHttpSessionConnector d = new NioHttpSessionConnector("127.0.0.1",9091);
		   d.run();
		System.out.println(d.isActive());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
