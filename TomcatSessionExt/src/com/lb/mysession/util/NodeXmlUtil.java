package com.lb.mysession.util;
import java.io.File;    
import java.util.ArrayList;
import java.util.Iterator;    
    
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/*
import org.dom4j.Attribute;    
import org.dom4j.Document;    
import org.dom4j.DocumentException;    
import org.dom4j.DocumentHelper;    
import org.dom4j.Element;    
import org.dom4j.io.SAXReader; */
public class NodeXmlUtil {
	public static void main(String[] args) {    
	    
        try {    
            File file = new File("d:\\HttpSession.xml");    
            getListNode("d:\\HttpSession.xml");// 解析XML文档    
    
            //String xmlStr = getStringFromXML(doc);// 将XML文档转换为String    
            //System.out.println("xmlStr:" + xmlStr);    
    
            //String str = "<?xml version='1.0' encoding='UTF-8'?><AirPortLine><line lid='MU2480' num='3'><id>1</id><station><sid>1</sid><sname>武汉</sname></station><station><sid>2</sid><sname>石家庄</sname></station><station><sid>3</sid><sname>北京</sname></station></line><line lid='MU2482' num='4'><id>2</id><station><sid>1</sid><sname>重庆</sname></station><station><sid>2</sid><sname>武汉</sname></station><station><sid>3</sid><sname>上海</sname></station><station><sid>4</sid><sname>东京</sname></station></line><line lid='MU2483' num='2'><id>3</id><station><sid>1</sid><sname>上海</sname></station><station><sid>2</sid><sname>纽约</sname></station></line></AirPortLine>";    
            //Document document = getXMLDocFromString(str);// 将String转换为XML文档 
        } catch (Exception e) {    
        	e.printStackTrace();
            System.out.println("Can't read the file");    
        }    
    }   
	public  static List<Node> getListNode(String filePath) throws Exception { 
		  
        List<Node> list = new ArrayList<>();
        Document d=	XmlManager.parse(filePath);
		Element e = XmlManager.getChildElement(d.getDocumentElement(), "ServerNodes")  ;
		NodeList list2 = e.getElementsByTagName( "ServerNode" );
		
		for(int i=0;i<list2.getLength();i++){
			  Element child = ( Element )list2.item(i);
			  String arr[]  =child.getTextContent().split(":");
			  Node node = new Node(arr[0],Integer.parseInt(arr[1]));
			  list.add(node);
		} 
        return list;
	 }
    
}
