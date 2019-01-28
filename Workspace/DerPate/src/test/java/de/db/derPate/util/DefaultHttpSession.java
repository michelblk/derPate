package de.db.derPate.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.Constants;

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

		}

		@Override
		public void setAttribute(String name, Object value) {
			DefaultHttpSession.this.attributes.put(name, value);

		}

		@Override
		public void removeValue(String name) {

		}

		@Override
		public void removeAttribute(String name) {

		}

		@Override
		public void putValue(String name, Object value) {

		}

		@Override
		public boolean isNew() {

			return false;
		}

		@Override
		public void invalidate() {

		}

		@Override
		public String[] getValueNames() {

			return null;
		}

		@Override
		public Object getValue(String name) {

			return null;
		}

		@Override
		public HttpSessionContext getSessionContext() {

			return null;
		}

		@Override
		public ServletContext getServletContext() {

			return null;
		}

		@Override
		public int getMaxInactiveInterval() {

			return 0;
		}

		@Override
		public long getLastAccessedTime() {

			return 0;
		}

		@Override
		public String getId() {
			return DefaultHttpSession.this.sessionID;
		}

		@Override
		public long getCreationTime() {

			return 0;
		}

		@Override
		public Enumeration<String> getAttributeNames() {

			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return DefaultHttpSession.this.attributes.get(name);
		}
	};
}
