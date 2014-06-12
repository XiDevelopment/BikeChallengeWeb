package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Route;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;

import persistence.HibernateUtil;
import persistence.Querys;
import persistence.Validation;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author Jakob
 * 
 *         RESTful interface for routes.
 */
@Path("/route/{username}")
public class RouteRest {

	private Session session = HibernateUtil.getSessionFactory().openSession();
	private Query deleteRouteQuery = session
			.createQuery(Querys.DELETE_ROUTE_QUERY);
	private Query getUserQuery = session.createQuery(Querys.GET_USER_QUERY);
	private Query getUserRoutesQuery = session
			.createQuery(Querys.GET_ROUTES_QUERY);
	private Query getUserRouteQuery = session
			.createQuery(Querys.GET_ROUTE_QUERY);
	private Gson gson = new Gson();

	/**
	 * Method for getting all routes of a user
	 * 
	 * @param userName
	 *            user which want his routes
	 * @return a list of all routes from the user
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllRoutes(@PathParam("username") String userName,
			@QueryParam("pw") String password) {
		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();

		if (user == null)
			return "Error";

		if (!Validation.checkPassword(userName, password, session))
			return "Error";

		return gson.toJson(getUserRoutesQuery.setParameter("userId",
				user.getId()).list());
	}

	/**
	 * Method for getting a single route of a user
	 * 
	 * @param userName
	 *            user who wants to get a route
	 * @param routeId
	 *            the routeId of the route you want
	 * @return the desired route
	 */
	@GET
	@Path("/{routeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRoute(@PathParam("username") String userName,
			@QueryParam("pw") String password,
			@PathParam("routeId") String routeId) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";

		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();

		if (user != null) {
			try {
				Route route = (Route) getUserRouteQuery
						.setParameter("userId", user.getId())
						.setParameter("routeId", Integer.valueOf(routeId))
						.uniqueResult();
				if (route != null) {
					return gson.toJson(route);
				}
			} catch (NumberFormatException e) {
				return "Error";
			}
		}

		return "Error";
	}

	/**
	 * Method for posting a new route to the server
	 * 
	 * @param userName
	 *            user who wants to post a new route
	 * @return status object
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String postRoute(@PathParam("username") String userName,
			@QueryParam("pw") String password, String routeJson) {

		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();

		if (user == null) {
			return "Error";
		}

		if (!Validation.checkPassword(userName, password, session))
			return "Error";

		session.beginTransaction();
		try {
			Route route = gson.fromJson(routeJson, Route.class);
			
			
			
			if (user.getScore() != null) {
				user.setScore(user.getScore() + ((int) route.getDistance() / 200));
			} else {
				user.setScore((int) route.getDistance() / 200);
			}
			session.save(route);			
			session.save(user);			
			
		} catch (JsonSyntaxException e) {
			session.getTransaction().rollback();
			return "Error";
		}
		session.getTransaction().commit();
		return "OK";
	}

	/**
	 * Method for deleting a route from the server
	 * 
	 * @param userName
	 *            user who wants to delete a route
	 * @param routeId
	 *            the route which wants to be deleted
	 * @return status object
	 */
	@DELETE
	@Path("/{routeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteRoute(@PathParam("username") String userName,
			@QueryParam("pw") String password,
			@PathParam("routeId") String routeId) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";

		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();

		if (user != null) {
			session.beginTransaction();
			deleteRouteQuery.setParameter("routeId", Integer.valueOf(routeId))
					.setParameter("userId", user.getId()).executeUpdate();
			session.getTransaction().commit();

			return "OK";

		}

		return "Error";
	}
}