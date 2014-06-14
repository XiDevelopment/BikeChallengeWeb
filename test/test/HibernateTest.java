package test.test;

import java.util.Date;
import java.util.List;

import model.Route;
import model.User;

import org.hibernate.Query;
import org.hibernate.Session;

import persistence.HibernateUtil;

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

		Query getGlobalTime = session
				.createSQLQuery("SELECT SUM(TIMESTAMPDIFF(SECOND, starttime, stoptime)) * 1000 FROM Route");
		java.math.BigDecimal test = (java.math.BigDecimal) getGlobalTime.uniqueResult();
		Long test2 = test.longValue();
		System.out.println(test2);

		HibernateUtil.close();
	}

}
