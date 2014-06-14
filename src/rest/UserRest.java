package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.User;

import org.hibernate.PropertyValueException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gson.Gson;

import persistence.HibernateUtil;
import persistence.Querys;
import persistence.Validation;

/**
 * @author Jakob
 * 
 *         RESTful interface for routes.
 */
@Path("/user/{username}")
public class UserRest {

	private Session session = HibernateUtil.getSessionFactory().openSession();
	private Query getUserQuery = session.createQuery(Querys.GET_USER_QUERY);
	private Gson gson = new Gson();

	/**
	 * Method for getting user information
	 * 
	 * @param userName
	 *            user who wants his informations
	 * @return user object
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@PathParam("username") String userName,
			@QueryParam("pw") String password) {
		try {
			User user = (User) getUserQuery.setParameter("userName", userName)
					.uniqueResult();

			if (user == null)
				return "Error";
		} catch (PropertyValueException e) {
			return "Error";
		}

		if (!Validation.checkPassword(userName, password, session))
			return "Error";

		List<User> userList = getUserQuery.setParameter("userName", userName)
				.list();
		if (userList != null && userList.size() == 1) {
			userList.get(0).setPassword(null);
			userList.get(0).setEmail(null);

			if (userList.get(0).getScore() == null) {
				userList.get(0).setScore(0);
			}

			return gson.toJson(userList.get(0));
		}

		return "Error";
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@PathParam("username") String userName,
			String newUser) {
		
		if (getUserQuery.setParameter("userName", userName).list().size() == 0) {
			session.beginTransaction();
			session.save(gson.fromJson(newUser, User.class));
			session.getTransaction().commit();
			return "OK";
		}
		return "Error";
	}

	@POST
	@Path("/{avatar}")
	@Produces(MediaType.APPLICATION_JSON)
	public String setAvatar(@PathParam("username") String userName,
			@QueryParam("pw") String password,
			@PathParam("avatar") String avatar) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";
		
		User user = (User) getUserQuery.setParameter("userName", userName).uniqueResult();
		
		if (user == null)
			return "Error";
		
		try {
			user.setAvatar(Integer.valueOf(avatar));
			user.setPassword(password);
		} catch (NumberFormatException e) {
			return "Error";
		}
		
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		
		return "OK";
	}

	public void deleteUser(String userName) {
		session.createQuery("delete User where name = :userName")
				.setParameter("userName", userName).executeUpdate();
	}
}
