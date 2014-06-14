package test.test;

import java.util.Date;
import java.util.List;

import model.Route;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;

import persistence.HibernateUtil;
import persistence.Querys;

public class HibernateTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		/*
		 * Route route = new Route(100.5f, new Date(System.currentTimeMillis()),
		 * new Date(System.currentTimeMillis() + 10000), 9); route.setId(14);
		 * session.update(route);
		 * 
		 * session.getTransaction().commit();
		 * 
		 * Query query = session.createQuery("from Route");
		 * 
		 * @SuppressWarnings("unchecked") List<Route> list = query.list();
		 * 
		 * for (Route u : list) { System.out.println(u.getId() + " " +
		 * u.getDistance()); }
		 */

		
		Query getGlobalDistance = session
				.createQuery(Querys.GET_GLOBAL_DISTANCE_QUERY);
		Query getGlobalTime = session
				.createSQLQuery(Querys.GET_GLOBAL_TIME_QUERY);
		
		long globalTime = ((java.math.BigDecimal) getGlobalTime
				.uniqueResult()).longValue();
		System.out.println(globalTime);
		
		Object test = getGlobalDistance.uniqueResult();
		
		
		//double test = (Double) getGlobalDistance.uniqueResult();
		System.out.println(test);

		HibernateUtil.close();
	}

}
