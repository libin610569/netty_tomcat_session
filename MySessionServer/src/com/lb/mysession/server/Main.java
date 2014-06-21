package com.lb.mysession.server;

import com.lb.mysession.session.CheckSessionThread;
import com.lb.mysession.session.DiskSessionThread;
import com.lb.mysession.session.SessionConfig;
import com.lb.mysession.thread.DisruptorThread;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Main {
	int port = 9091;
	 public void run() throws Exception {
	        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap();
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class)
	             .childHandler(new ProtobufServerInitializer());

	            Channel ch = b.bind(port).sync().channel();
	            System.out.println("Web socket server started at port " + port + '.');
	            System.out.println("Open your browser and navigate to http://localhost:" + port + '/');

	            ch.closeFuture().sync();
	            
	        } finally {
	            bossGroup.shutdownGracefully();
	            workerGroup.shutdownGracefully();
	        }
	    }
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.port = SessionConfig.port;

	   //  System.out.println("ddxs "+(5<<13));
		/*DiskSessionThread d = new DiskSessionThread();
	     d.init();
	     d.start(); */
	     new CheckSessionThread().start();
	  //  new Thread( new ProThread()).start();
	    // DisruptorConceptProofTest2 d2 = new DisruptorConceptProofTest2();
	     DisruptorThread.start();
	 //    new TestThread().start();
	     main.run(); 
	}
}
