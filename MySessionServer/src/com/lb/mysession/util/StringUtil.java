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

package com.lb.mysession.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author libin
 *
 */

public class StringUtil {
	public static String appendString(String str1, String str2) {
		StringBuilder sb = new StringBuilder(200);
		sb.append(str1);
		sb.append("-");
		sb.append(str2);
		return sb.toString();
	}

	public static String composeString(String... strings) {
		StringBuilder sb = new StringBuilder(200);
		for (String str : strings) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String object3String(Object obj) throws Exception {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(obj);
		objectOutputStream.flush();
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		// System.out.println(serStr);

		return serStr;// Base64.encodeBase64String(bos.toByteArray());
	}

	public static byte[] object4String(Object obj) throws Exception {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		bos.close();

		return bos.toByteArray();// Base64.encodeBase64String(bos.toByteArray());
	}

	public static Object string4Object(byte[] b) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object obj = ois.readObject();
		bis.close();
		ois.close();
		return obj;
	}

	public static Object string3Object(String str) throws Exception {
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		Object obj = objectInputStream.readObject();

		objectInputStream.close();
		byteArrayInputStream.close();
		return obj;
	}

	public static void main(String[] args) throws Exception {
		 
	}

}
