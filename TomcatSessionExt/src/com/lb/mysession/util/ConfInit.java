package com.lb.mysession.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ConfInit { 
	public static int port =0;
	public static List<Node> allNodes ;
/*	static {
		try {
			allNodes = new ArrayList<>();
			 
			  String path2=ClassUtil.getAppPath(ConfInit.class)+"\\HttpSession.xml";
			  System.out.println("path2 --- "+path2);    
			  allNodes = NodeXmlUtil.getListNode(path2);
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("����properites�ļ�����");
		}
	}
	*/
	public static void initNodes(String nodes){
		//127.0.0.1:6379:123456,
		
		String arr []  =  nodes.split(",");
		allNodes   = new ArrayList<>();
		for(String str:arr){
			String arr2 []  =  str.split(":");
			
			System.out.println("arr2  ----------"+(arr2[0]+" "+Integer.parseInt(arr2[1])));
			Node node = new Node(arr2[0],Integer.parseInt(arr2[1])); 
			allNodes.add(node);
		}
	}
 
}
