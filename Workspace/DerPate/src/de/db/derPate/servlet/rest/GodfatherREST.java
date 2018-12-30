package de.db.derPate.servlet.rest;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.db.derPate.Usermode;
import de.db.derPate.manager.LoginManager;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.Trainee;
import de.db.derPate.model.typeAdapter.DateTypeAdapter;
import de.db.derPate.persistence.GodfatherDao;
import de.db.derPate.servlet.filter.LoginServletFilter;

/**
 * REST interface for {@link Trainee}s to list informations about godfathers
 *
 * @author MichelBlank
 */
@Stateless
@Path("/godfather")
public class GodfatherREST extends FilterREST {
	/**
	 * Initialize security
	 */
	public GodfatherREST() {
		super(new LoginServletFilter(Usermode.TRAINEE));
	}

	/**
	 * List all Godfathers, that are available (with limited information)<br>
	 * HTTP method: GET
	 *
	 * @return a JSON list of godfathers
	 */
	@GET
	public String list() {
		// TODO security: remove email, picktext, etc; check if user has already
		// selected a godfather
		List<Godfather> all = GodfatherDao.getInstance().list();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
		return gson.toJson(all);
	}

	/**
	 * List informations regarding the wanted Godfather by id<br>
	 * HTTP method: GET
	 *
	 * @param godfatherid encrypted id of godfather
	 * @return a JSON representation of the godfather or <code>null</code>, if
	 *         godfather was not found
	 */
	@GET
	@Path("byid/{id}")
	public String specificGodfather(@PathParam("id") String godfatherid) {
		// TODO Security (user should not be able to guess id) -> encrypt id with a
		// session token
		return godfatherid; // TODO
	}

	/**
	 * List the Godfather, the user has committed to<br>
	 * HTTP method: GET
	 *
	 * @return a JSON representation of the godfather or <code>null</code>, if no
	 *         godfather was selected
	 */
	@GET
	@Path("my")
	public String myGodfather() {
		// TODO security: hiringDate should be replaced with educational year (e.g. 2),
		// birthday should be replaced with age
		Godfather godfather = null;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(Date.class, new DateTypeAdapter()).create();

		if (this.request != null) {
			HttpSession session = this.request.getSession();
			if (session != null) {
				// LoginServletFilter makes sure, that user is a trainee
				Trainee trainee = (Trainee) LoginManager.getInstance().getUserBySession(session);
				if (trainee != null) {
					godfather = trainee.getGodfather();
				}
			}
		}
		return gson.toJson(godfather);
	}
}
