package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Route;
import model.Statistic;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gson.Gson;

import persistence.HibernateUtil;

/**
 * 
 * @author Jakob
 * 
 */
@Path("/statistic/{username}")
public class StatisticRest {
	private Session session = HibernateUtil.getSessionFactory().openSession();
	private Query getUserQuery = session.createQuery("FROM User U where U.name = :userName");
	private Query getUserRouteQuery = session.createQuery("FROM Route U where U.userId = :userId");
	private Gson gson = new Gson();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getFriends(@PathParam("username") String userName) {
		User user = (User) getUserQuery.setParameter("userName", userName).uniqueResult();
		List<Route> routes = getUserRouteQuery.setParameter("userId", user.getId()).list();
		return gson.toJson(new Statistic(routes));
	}
}
