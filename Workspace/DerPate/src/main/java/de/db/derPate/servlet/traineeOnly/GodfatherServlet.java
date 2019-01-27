package de.db.derPate.servlet.traineeOnly;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.db.derPate.Usermode;
import de.db.derPate.manager.LoginManager;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.Trainee;
import de.db.derPate.model.typeAdapter.DateTypeAdapter;
import de.db.derPate.persistence.GodfatherDao;
import de.db.derPate.servlet.FilterServlet;
import de.db.derPate.servlet.filter.LoginServletFilter;
import de.db.derPate.util.InputVerifyUtil;
import de.db.derPate.util.URIParameterEncryptionUtil;

@WebServlet("/godfather")
public class GodfatherServlet extends FilterServlet {
	protected Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(Date.class, new DateTypeAdapter()).create();

	public GodfatherServlet() {
		super(new LoginServletFilter(Usermode.TRAINEE));
	}

	@Override
	protected void onGet(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		Trainee trainee = (Trainee) LoginManager.getInstance().getUserBySession(this.session);
		if (trainee == null) {
			return;
		}

		String byId = req.getParameter("find");
		if (this.session != null && byId != null && InputVerifyUtil.isNotBlank(byId)) {

			if (!byId.equals("my") && trainee.getGodfather() == null) { // if user wants to list a specific godfather
																		// and hasn't selected one permanently, yet
				@SuppressWarnings("null") // suspress warning, that session might be null
				// decrypt the godfahter id
				String godfatherId = URIParameterEncryptionUtil.decrypt(byId, this.session, trainee);
				if (godfatherId != null && InputVerifyUtil.isInteger(godfatherId)) {
					int id = Integer.parseInt(godfatherId);

					Godfather godfather = GodfatherDao.getInstance().byId(id);
					resp.getWriter().print(this.gson.toJson(godfather));
					return;
				}
			} else {
				// look for my godfather, if "find" is "my" or user has already selected a
				// godfather permantently
				Godfather godfather = trainee.getGodfather();
				resp.getWriter().print(this.gson.toJson(godfather));
				return;
			}

		} else if (trainee.getGodfather() == null) {
			// TODO security: remove email, picktext, etc;
			List<Godfather> all = GodfatherDao.getInstance().list();
			resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());

			resp.getWriter().print(this.gson.toJson(all));
			return;
		}
		resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		super.onPost(req, resp);
	}
}
