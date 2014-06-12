package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Route;
import model.Statistic;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gson.Gson;

import persistence.HibernateUtil;
import persistence.Querys;
import persistence.Validation;

/**
 * 
 * @author Jakob
 * 
 */
@Path("/statistic/{username}")
public class StatisticRest {
	private Session session = HibernateUtil.getSessionFactory().openSession();
	private Query getUserQuery = session.createQuery(Querys.GET_USER_QUERY);
	private Query getUserRoutesQuery = session
			.createQuery(Querys.GET_ROUTES_QUERY);
	private Query getGlobalDistance = session
			.createQuery(Querys.GET_GLOBAL_DISTANCE_QUERY);
	private Query getGlobalTime = session
			.createSQLQuery(Querys.GET_GLOBAL_TIME_QUERY);
	private Gson gson = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStatistic(@PathParam("username") String userName, @QueryParam("pw") String password) {
		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();
		if (user != null) {
			List<Route> routes = getUserRoutesQuery.setParameter("userId",
					user.getId()).list();
			
			
			double globalDistance = 0.0;
			long globalTime = 0;
			
			Object globalDistanceObj = getGlobalDistance.uniqueResult();
			Object globalTimeObj = getGlobalTime.uniqueResult();
			
			if (globalDistanceObj != null) {
				globalDistance = (Double) getGlobalDistance.uniqueResult();
			}
			
			if (globalTimeObj != null) {
				globalTime = ((java.math.BigDecimal) getGlobalTime
						.uniqueResult()).longValue();
			}
			
			return gson.toJson(new Statistic(routes, user.getScore(),
					globalDistance, globalTime));
		}
		return "Error";
	}
}
