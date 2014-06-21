package com.lb.mysession.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheckSessionThread extends Thread {
	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Map<String, SessionBean> map = SessionMap.getSessionMap().getMap();

			Iterator iter = map.entrySet().iterator();
			List<String> delList = new ArrayList<>();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				Object val = entry.getValue();

				SessionBean f = (SessionBean) val;// pre_map.get(it.next());

				if (isTimeout(f, SessionConfig.sessionTimeout)) {

					delList.add(key);
				}

			}

			for (String key : delList) {
				System.out.println("remove session");
				map.remove(key);
			}
		}

	}

	private boolean isTimeout(SessionBean session, long sessionTimeout) {
		long time = System.currentTimeMillis();
		if ((time - session.getLastAccessedTime()) > sessionTimeout) {
			return true;
		} else {
			return false;
		}
	}
}
