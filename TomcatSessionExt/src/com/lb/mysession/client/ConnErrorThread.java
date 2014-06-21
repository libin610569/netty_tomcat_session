package com.lb.mysession.client;

import java.util.List;

import com.lb.mysession.util.ConfInit;
import com.lb.mysession.util.HashServer;
import com.lb.mysession.util.Node;
/**
 * 
 * @author libin
 * 连接失败 处理
 */
public class ConnErrorThread  extends Thread{
	private NioHttpSessionConnector httpSessionConnector;

	private Node node;
	ConnErrorThread(NioHttpSessionConnector httpSessionConnector,Node node){
		this.httpSessionConnector = httpSessionConnector;
		this.node = node;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		int count=0;
		while(count<3){ 
			try {
				Thread.sleep(1000*20);
				System.out.println("正在重新连接");
				httpSessionConnector.restInit();
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				httpSessionConnector.errorCount++;
			}
		
			count++;
		}
		if(!httpSessionConnector.isActive()){
			List<Node> list =   ConfInit.allNodes;
			list.remove(node);

			ModSessionNodeConnector.connectors.remove(node);
			System.out.println("移除节点");
	
			System.out.println("重新初始化");
			if(list.size()<=0){
				
			}
			HashServer.init(list);
		}

		ModSessionNodeConnector.connectorsError.remove(node);
	}
	
}
