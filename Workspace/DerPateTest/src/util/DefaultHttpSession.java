package util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class DefaultHttpSession {
	public final HashMap<String, Object> attributes = new HashMap<>();
	public final HttpSession SESSION = new HttpSession() {

		@Override
		public void setMaxInactiveInterval(int interval) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setAttribute(String name, Object value) {
			DefaultHttpSession.this.attributes.put(name, value);

		}

		@Override
		public void removeValue(String name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeAttribute(String name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void putValue(String name, Object value) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isNew() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void invalidate() {
			// TODO Auto-generated method stub

		}

		@Override
		public String[] getValueNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getValue(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpSessionContext getSessionContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getMaxInactiveInterval() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getLastAccessedTime() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getCreationTime() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return DefaultHttpSession.this.attributes.get(name);
		}
	};
}
