package com.lb.session.ext;

import java.security.Principal;
import java.util.HashMap;

import org.apache.catalina.Manager;
import org.apache.catalina.session.StandardSession;

import com.lb.mysession.filter.HttpSessionService;

public class MySession extends StandardSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MySession(Manager manager) {
		super(manager);
		// TODO Auto-generated constructor stub
		resetDirtyTracking(); // 初始化session
	}

	protected HashMap<String, Object> changedAttributes;// 存放具体数据地方
	protected Boolean dirty;

	// protected static Boolean manualDirtyTrackingSupportEnabled = false;

	// protected static String manualDirtyTrackingAttributeKey = "__changed__";

	public Boolean isDirty() {
		return dirty || !changedAttributes.isEmpty();
	}

	public HashMap<String, Object> getChangedAttributes() {
		return changedAttributes;
	}

	public void resetDirtyTracking() {
		changedAttributes = new HashMap<String, Object>();
		dirty = false;
	}

	public void clearAttributes() {
		changedAttributes = null;
		dirty = false;
	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		Object obj = null;
		if (changedAttributes == null) {
			HttpSessionService service = HttpSessionService.getInstance();
			obj = service.getAttribute(this.id, name);
			changedAttributes.put(name, obj);
		} else {

			obj = changedAttributes.get(name);
			if (obj == null) {
				HttpSessionService service = HttpSessionService.getInstance();
				if (name.indexOf(".") != -1) {
					// System.out.println("服务器拦截" );
					return null;
				}
				// System.out.println(name);
				obj = service.getAttribute(this.id, name);
				/*
				 * if(obj==null){ // System.out.println("服务器查询"+obj
				 * +"  sesisonid"+this.id); }
				 */
				changedAttributes.put(name, obj);
			}
		}
		return obj;
	}

	// 设置session
	@Override
	public void setAttribute(String key, Object value) {

		/*
		 * if (manualDirtyTrackingSupportEnabled &&
		 * manualDirtyTrackingAttributeKey.equals(key)) { dirty = true; return;
		 * }
		 */

		/*
		 * Object oldValue = getAttribute(key); if ( (value != null || oldValue
		 * != null) && ( value == null && oldValue != null || oldValue == null
		 * && value != null || !value.getClass().isInstance(oldValue) ||
		 * !value.equals(oldValue) ) ) { changedAttributes.put(key, value); }
		 */

		if (value != null && key != null) {
			changedAttributes.put(key, value);
			// 发送给服务器
			HttpSessionService service = HttpSessionService.getInstance();
			service.setAttribute(this.id, key, value);
		}

		super.setAttribute(key, value);
	}

	// 删除session
	@Override
	public void removeAttribute(String name) {
		System.out.println("removeAttribute" + name);
		HttpSessionService service = HttpSessionService.getInstance();
		service.removeAttribute(this.id, name);

		changedAttributes.remove(name);

		dirty = true;
		super.removeAttribute(name);
	}

	// 设置session id
	@Override
	public void setId(String id) {
		// Specifically do not call super(): it's implementation does unexpected
		// things
		// like calling manager.remove(session.id) and manager.add(session).

		this.id = id;
	}

	@Override
	public void setPrincipal(Principal principal) {
		dirty = true;
		super.setPrincipal(principal);
	}
}
