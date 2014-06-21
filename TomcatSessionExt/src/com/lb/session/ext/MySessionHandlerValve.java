package com.lb.session.ext;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

//管理全局类
public class MySessionHandlerValve extends ValveBase {
	// private final Log log = LogFactory.getLog(RedisSessionManager.class);
	private MySessionManager manager;

	public void setMySessionManager(MySessionManager manager) {
		this.manager = manager;
	}

	// 检测session
	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		try {
			getNext().invoke(request, response);
		} finally {
			final Session session = request.getSessionInternal(false);
			storeOrRemoveSession(session);
			manager.afterRequest();// 请求结束后
		}
	}

	// 检测session 是否存活方法
	private void storeOrRemoveSession(Session session) {
		// System.out.println("storeOrRemoveSession");
		try {
			if (session != null) {
				if (session.isValid()) {
					// Sys("Request with session completed, saving session " +
					// session.getId());
					if (session.getSession() != null) {
						// log.trace("HTTP Session present, saving " +
						// session.getId());
						manager.save(session);// 保存session
					} else {
						// log.trace("No HTTP Session present, Not saving " +
						// session.getId());
					}
				} else {
					// log.trace("HTTP Session has been invalidated, removing :"
					// + session.getId());
					manager.remove(session);// 删除session
				}
			}
		} catch (Exception e) {
			// Do nothing.
		}
	}
}