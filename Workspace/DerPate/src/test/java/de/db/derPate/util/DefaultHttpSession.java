package de.db.derPate.util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class DefaultHttpSession {
	public final HashMap<String, Object> attributes = new HashMap<>();
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

			return null;
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
