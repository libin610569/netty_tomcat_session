package com.lb.mysession.util;

import java.util.List;

public class HashServer {
	
	
	public   static KetamaNodeLocator locator ;
	public static void init(List<Node> allNodes){
		locator = new KetamaNodeLocator(allNodes, HashAlgorithm.KETAMA_HASH, 2000);
	}
	public static Node getHashServer(String jsessionid){
		return locator.getPrimary(jsessionid);
	}
}
 