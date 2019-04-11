package de.db.derpate.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.Constants;

@SuppressWarnings({ "deprecation", "javadoc" })
public class DefaultHttpSession {
	private static final int RANDOM_SESSION_ID_LENGTH = 20;

	/**
	 * generate session id (used for {@link URIParamterEncryptionUtilTest})
	 */
	final String sessionID;

	public DefaultHttpSession() {
		byte[] randomArray = new byte[RANDOM_SESSION_ID_LENGTH];
		new Random().nextBytes(randomArray);
		this.sessionID = new String(randomArray, Constants.CHARSET);
	}

	@NonNull
	public final HashMap<String, Object> attributes = new HashMap<>();
	@NonNull
	public final HttpSession SESSION = new HttpSession() {

		@Override
		public void setMaxInactiveInterval(int interval) {
			// not implemented
		}

		@Override
		public void setAttribute(String name, Object value) {
			DefaultHttpSession.this.attributes.put(name, value);

		}

		@Override
		public void removeValue(String name) {
			// not implemented
		}

		@Override
		public void removeAttribute(String name) {
			// not implemented
		}

		@Override
		public void putValue(String name, Object value) {
			// not implemented
		}

		@Override
		public boolean isNew() {
			// not implemented
			return false;
		}

		@Override
		public void invalidate() {
			// not implemented
		}

		@Override
		public String[] getValueNames() {
			// not implemented
			return null;
		}

		@Override
		public Object getValue(String name) {
			// not implemented
			return null;
		}

		@Override
		public HttpSessionContext getSessionContext() {
			// not implemented
			return null;
		}

		@Override
		public ServletContext getServletContext() {
			// not implemented
			return null;
		}

		@Override
		public int getMaxInactiveInterval() {
			// not implemented
			return 0;
		}

		@Override
		public long getLastAccessedTime() {
			// not implemented
			return 0;
		}

		@Override
		public String getId() {
			return DefaultHttpSession.this.sessionID;
		}

		@Override
		public long getCreationTime() {
			// not implemented
			return 0;
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			// not implemented
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return DefaultHttpSession.this.attributes.get(name);
		}
	};
}
