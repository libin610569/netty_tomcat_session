package com.lb.mysession.session;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lb.mysession.util.DateUtil;
import com.lb.mysession.util.ObjectUtil;

public class DiskSessionThread extends Thread {
	@Override
	public void run() {
		super.run();

		while (true) {
			try {
				Thread.sleep(1000L * 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String filePath = SessionConfig.diskFilePath + "\\"
					+ DateUtil.getDiskDate() + ".session";
			ObjectUtil.objectToDisk(SessionMap.getSessionMap().getMap(),
					filePath);

		}

	}

	public static void init() {
		File f = new File(SessionConfig.diskFilePath);

		File[] files = f.listFiles();
		long tmp_time = 0l;
		Map<Long, File> map = new HashMap<>();
		for (File tmp : files) {
			long time = tmp.lastModified();// 返回文件最后修改时间，是以个long型毫秒数

			if (time > tmp_time) {
				tmp_time = time;
			}
			map.put(time, tmp);

		}

		File fff = map.get(tmp_time);
		if (fff != null) {
			System.out.println(fff.getPath());
			Map<String, SessionBean> mapObj = (Map<String, SessionBean>) ObjectUtil
					.diskToObject(fff.getPath());
			System.out.println("mapobj= " + map.size());

			if (mapObj != null) {
				SessionMap.initSessionMapByDisk(mapObj);

			}
			System.out.println(fff.getPath());
		}
	}

	public static void main(String[] args) {
 
		init();
	}
}
