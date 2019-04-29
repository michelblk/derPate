package de.db.derpate.servlet.adminOnly;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.Usermode;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;

@WebServlet("/adminGodfatherAdd")
public class GodfatherAddServlet extends FilterServlet {
	/**
	 * Default constructor initializing the {@link FilterServlet}
	 */
	public GodfatherAddServlet() {
		super(new LoginServletFilter(Usermode.ADMIN), new CSRFServletFilter(CSRFForm.ADMIN_ADD_GODFATHER));
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
	}
}