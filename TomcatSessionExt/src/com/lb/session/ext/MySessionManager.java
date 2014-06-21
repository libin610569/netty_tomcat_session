package com.lb.session.ext;

import java.io.IOException; 
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Loader;
import org.apache.catalina.Session;
import org.apache.catalina.Valve;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.util.LifecycleSupport;

import com.lb.mysession.util.ConfInit;

public class MySessionManager extends ManagerBase implements Lifecycle {
	// 生命周期检测
	protected LifecycleSupport lifecycle = new LifecycleSupport(this);

	protected ThreadLocal<MySession> currentSession = new ThreadLocal<MySession>();
	protected ThreadLocal<String> currentSessionId = new ThreadLocal<String>();
	protected ThreadLocal<Boolean> currentSessionIsPersisted = new ThreadLocal<Boolean>();

	protected MySessionHandlerValve handlerValve;

	protected String serverlist;

	protected String webName;

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getServerlist() {
		return serverlist;
	}

	public void setServerlist(String serverlist) {
		ConfInit.initNodes(serverlist);
		this.serverlist = serverlist;
	}

	@Override
	public void load() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		System.out.println("load");
	}

	@Override
	public void unload() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("unload");

	}

	// 查询session
	@Override
	public Session findSession(String id) throws IOException {
		// TODO Auto-generated method stub
		// System.out.println("查询session"+id);

		MySession session = null;

		if (id == null) {
			session = null;
			currentSessionIsPersisted.set(false);
		} else if (id.equals(currentSessionId.get())) {
			session = currentSession.get();
		} else {
			// 从本地查询
			session = loadSessionFromSessionServer(id);
		}

		currentSession.set(session);
		currentSessionId.set(id);

		return session;
	}

	public MySession loadSessionFromSessionServer(String id) throws IOException {
		MySession session;

		Boolean error = true;

		try {
			error = false;

			session = (MySession) createEmptySession();
			session.setId(id);
			session.setNew(false);
			session.setMaxInactiveInterval(getMaxInactiveInterval() * 1000);
			session.access();
			session.setValid(true);
			session.resetDirtyTracking();
			return session;
		} catch (Exception e) {
			throw new IOException("Unable to deserialize into session", e);
		} finally {
		}
	}

	@Override
	public void add(Session session) {
		// TODO Auto-generated method stub
		try {
			save(session);
		} catch (IOException ex) {
			// log.warn("Unable to add to session manager store: " +
			// ex.getMessage());
			throw new RuntimeException(
					"Unable to add to session manager store.", ex);
		}
		System.out.println("添加ssession");
	}

	@Override
	public Session createEmptySession() {

		// System.out.println("创建createEmptySession");
		return new MySession(this);
	}

	@Override
	public Session createSession(String sessionId) {

		// System.out.println("创建createSession");
		MySession session = (MySession) createEmptySession();

		// Initialize the properties of the new session and return it
		session.setNew(true);
		session.setValid(true);
		session.setCreationTime(System.currentTimeMillis());
		session.setMaxInactiveInterval(getMaxInactiveInterval());

		String jvmRoute = getJvmRoute();

		Boolean error = true;

		try {

			// Ensure generation of a unique session identifier.
			// do {
			if (null == sessionId) {
				sessionId = generateSessionId();
			}

			if (jvmRoute != null) {
				sessionId += '.' + jvmRoute;
			}
			// } while (jedis.setnx(sessionId.getBytes(), NULL_SESSION) == 1L);
			// // 1 = key set; 0 = key already existed

			/*
			 * Even though the key is set in Redis, we are not going to flag the
			 * current thread as having had the session persisted since the
			 * session isn't actually serialized to Redis yet. This ensures that
			 * the save(session) at the end of the request will serialize the
			 * session into Redis with 'set' instead of 'setnx'.
			 */

			error = false;

			session.setId(sessionId);
			session.tellNew();

			currentSession.set(session);
			currentSessionId.set(sessionId);
			currentSessionIsPersisted.set(false);
		} finally {
		}
		return session;
	}

	// session 移除
	@Override
	public void remove(Session session) {
		remove(session, false);
	}

	// session 移除
	@Override
	public void remove(Session session, boolean update) {

		Boolean error = true;
		// System.out.println("Removing session ID : " + session.getId());
		// sessionMap.remove(session.getId());

		try {
			error = false;
		} finally {
		}
	}

	/**
	 * Add a lifecycle event listener to this component.
	 * 
	 * @param listener
	 *            The listener to add
	 */
	// session 超时线程监听
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		lifecycle.addLifecycleListener(listener);
	}

	/**
	 * Get the lifecycle listeners associated with this lifecycle. If this
	 * Lifecycle has no listeners registered, a zero-length array is returned.
	 */
	@Override
	public LifecycleListener[] findLifecycleListeners() {
		return lifecycle.findLifecycleListeners();
	}

	/**
	 * Remove a lifecycle event listener from this component.
	 * 
	 * @param listener
	 *            The listener to remove
	 */
	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		lifecycle.removeLifecycleListener(listener);
	}

	/**
	 * Start this component and implement the requirements of
	 * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
	 * 
	 * @exception LifecycleException
	 *                if this component detects a fatal error that prevents this
	 *                component from being used
	 */
	@Override
	protected synchronized void startInternal() throws LifecycleException {
		super.startInternal();
		System.out.println("startInternal");
		setState(LifecycleState.STARTING);

		Boolean attachedToValve = false;
		for (Valve valve : getContainer().getPipeline().getValves()) {
			if (valve instanceof MySessionHandlerValve) {
				this.handlerValve = (MySessionHandlerValve) valve;
				this.handlerValve.setMySessionManager(this);
				attachedToValve = true;
				break;
			}
		}

		if (!attachedToValve) {
			String error = "Unable to attach to session handling valve; sessions cannot be saved after the request without the valve starting properly.";
			throw new LifecycleException(error);
		}

		try {
			initializeSerializer();
		} catch (Exception e) {
			e.printStackTrace();
			// log.fatal("Unable to load serializer", e);
		}
		// log.info("Will expire sessions after " + getMaxInactiveInterval() +
		// " seconds");

		// initializeDatabaseConnection();

		setDistributable(true);
	}

	// 停止session 服务
	/**
	 * Stop this component and implement the requirements of
	 * {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
	 * 
	 * @exception LifecycleException
	 *                if this component detects a fatal error that prevents this
	 *                component from being used
	 */
	@Override
	protected synchronized void stopInternal() throws LifecycleException {

		System.out.println("stopInternal");
		setState(LifecycleState.STOPPING);

		try {
		} catch (Exception e) {
			// Do nothing.
		}

		// Require a new random number generator if we are restarted
		super.stopInternal();
	}

	public void save(Session session) throws IOException {
		// 持久到其他地方

		// System.out.println("添加到持久的");

		Boolean error = true;

		try {

			MySession mySession = (MySession) session;

			/*
			 * if (log.isTraceEnabled()) { log.trace("Session Contents [" +
			 * redisSession.getId() + "]:"); Enumeration en =
			 * redisSession.getAttributeNames(); while(en.hasMoreElements()) {
			 * log.trace("  " + en.nextElement()); } }
			 */

			// Boolean sessionIsDirty = mySession.isDirty();
			mySession.resetDirtyTracking();

			// Boolean isCurrentSessionPersisted =
			// this.currentSessionIsPersisted.get();
			/*
			 * if (sessionIsDirty || (isCurrentSessionPersisted == null ||
			 * !isCurrentSessionPersisted)) { jedis.set(binaryId,
			 * serializer.serializeFrom(redisSession)); }
			 */

			currentSessionIsPersisted.set(true);
			// sessionMap.put(mySession.getId(), mySession);

			// log.trace("Setting expire timeout on session [" +
			// redisSession.getId() + "] to " + getMaxInactiveInterval());
			// jedis.expire(binaryId, getMaxInactiveInterval());

			error = false;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	// request 请求结束后 删除本地内存
	public void afterRequest() {
		MySession mySession = currentSession.get();
		if (mySession != null) {
			currentSession.remove();
			currentSessionId.remove();
			currentSessionIsPersisted.remove();
			// sessionMap.remove(mySession.getId());
			// System.out.println("afterRequest");
			// log.trace("Session removed from ThreadLocal :" +
			// redisSession.getIdInternal());
		}
	}

	protected Serializer serializer;

	protected String serializationStrategyClass = "com.lb.session.ext.JavaSerializer";

	// 初始化 序列化
	private void initializeSerializer() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		// 初始化序列化；类
		// serializer = (Serializer)
		// Class.forName(serializationStrategyClass).newInstance();
		if (JavaSerializer.loader == null) {
			Loader loader = null;

			if (container != null) {
				loader = container.getLoader();
			}

			// System.out.println("loader-----------------------"+loader);

			ClassLoader classLoader = null;

			if (loader != null) {
				classLoader = loader.getClassLoader();
			}
			// System.out.println("classLoader-----------------------"+classLoader);
			String d = classLoader.toString();
			// classLoader.
			if (webName == null) {
				throw new RuntimeException();

			}
			if (d.indexOf(webName) != -1) {
				System.out.println("类加载-----------------------------------");
				JavaSerializer.loader = classLoader;
				;// (classLoader);
			}
		}
	}
}
