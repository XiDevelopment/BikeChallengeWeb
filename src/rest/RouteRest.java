package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Route;
import model.User;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import persistence.HibernateUtil;

import com.google.gson.Gson;

/**
 * @author Jakob
 * 
 *         RESTful interface for routes.
 */
@Path("/route/{username}")
public class RouteRest {

	private Session session = HibernateUtil.getSessionFactory().openSession();
	private Query deleterRouteQuery = session
			.createQuery("DELETE Route where id = :routeId and userId = :userId");
	private Query getUserQuery = session
			.createQuery("FROM User U where U.name = :userName");
	private Query getUserRoutesQuery = session
			.createQuery("FROM Route R where R.userId = :userId");
	private Query getUserRouteQuery = session
			.createQuery("FROM Route R where R.userId = :userId and R.id = :routeId");
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
	public String getAllRoutes(@PathParam("username") String userName) {
		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();

		if (user != null) {
			return gson.toJson(getUserRoutesQuery.setParameter("userId",
					user.getId()).list());
		}

		return "Error";
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
			@PathParam("routeId") String routeId) {
		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();

		if (user != null) {
			Route route = (Route) getUserRouteQuery
					.setParameter("userId", user.getId())
					.setParameter("routeId", Integer.valueOf(routeId))
					.uniqueResult();
			if (route != null) {
				return gson.toJson(route);
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
			String routeJson) {
		session.beginTransaction();
		session.save(gson.fromJson(routeJson, Route.class));
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
			@PathParam("routeId") String routeId) {

		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();

		if (user != null) {
			session.beginTransaction();
			deleterRouteQuery.setParameter("routeId", Integer.valueOf(routeId))
					.setParameter("userId", user.getId()).executeUpdate();
			session.getTransaction().commit();
			
			return "OK";

		}
		
		return "Error";
	}

}