package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.IsFriend;
import model.IsFriendId;
import model.User;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;

import com.google.gson.Gson;

import persistence.HibernateUtil;
import persistence.Querys;
import persistence.Validation;

@Path("/friend/{username}")
public class FriendRest {
	private Session session = HibernateUtil.getSessionFactory().openSession();
	private Query getUserQuery = session
			.createQuery(Querys.GET_USER_QUERY);
	private Query getFriendsQuery = session.createQuery(Querys.GET_FRIENDS_QUERY);
	private Query getPendingQuery = session
			.createQuery(Querys.GET_PENDING_QUERY);
	private Gson gson = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getFriends(@PathParam("username") String userName, @QueryParam("pw") String password) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";
		
		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();
		
		if (user == null)
			return "Error";
		
		List<IsFriend> friendList = getFriendsQuery.setParameter("userId",
				user.getId()).list();
		List<Integer> idList = new ArrayList<Integer>();
		for (IsFriend f : friendList) {
			if (f.getId().getUserId1() != user.getId()) {
				idList.add(f.getId().getUserId1());
			}
			if (f.getId().getUserId2() != user.getId()) {
				idList.add(f.getId().getUserId2());
			}
		}

		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.in("id", idList));
		
		try {
			List<User> users = crit.list();

			for (User u : users) {
				u.setPassword(null);
				u.setEmail(null);
				u.setId(null);
			}

			return gson.toJson(users);
			// is thrown when user has no friends
		} catch (SQLGrammarException e) {
			return gson.toJson(new ArrayList<User>());
			//return "Error";
		}
	}

	@POST
	@Path("/request/{userToAdd}")
	@Produces(MediaType.APPLICATION_JSON)
	public String request(@PathParam("username") String userName, @QueryParam("pw") String password,
			@PathParam("userToAdd") String userToAdd) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";
		
		User requestUser = (User) getUserQuery.setParameter("userName",
				userName).uniqueResult();
		User requestedUser = (User) getUserQuery.setParameter("userName",
				userToAdd).uniqueResult();
		
		if (requestUser == null || requestedUser == null) 
			return "Error";

		session.beginTransaction();

		IsFriend request = new IsFriend(new IsFriendId(requestUser.getId(),
				requestedUser.getId()), "pending");

		try {
			session.save(request);
			session.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			return "Error";
		}

		return "OK";
	}

	@POST
	@Path("/request/{requestUser}/accept")
	@Produces(MediaType.APPLICATION_JSON)
	public String accept(@PathParam("username") String userName, @QueryParam("pw") String password,
			@PathParam("requestUser") String requestUserName) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";
		
		User requestUser = (User) getUserQuery.setParameter("userName",
				requestUserName).uniqueResult();
		User requestedUser = (User) getUserQuery.setParameter("userName",
				userName).uniqueResult();

		session.beginTransaction();

		IsFriend request = (IsFriend) session.get(IsFriend.class,
				new IsFriendId(requestUser.getId(), requestedUser.getId()));
		request.setStatus("accepted");
		session.update(request);
		
		session.getTransaction().commit();
		
		return "OK";
	}

	@POST
	@Path("/request/{requestUser}/refuse")
	@Produces(MediaType.APPLICATION_JSON)
	public String refuse(@PathParam("username") String userName, @QueryParam("pw") String password,
			@PathParam("requestUser") String requestUserName) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";
		
		User requestUser = (User) getUserQuery.setParameter("userName",
				requestUserName).uniqueResult();
		User requestedUser = (User) getUserQuery.setParameter("userName",
				userName).uniqueResult();
		
		if (requestUser == null || requestedUser == null) 
			return "Error";

		session.beginTransaction();

		IsFriend request = (IsFriend) session.get(IsFriend.class,
				new IsFriendId(requestUser.getId(), requestedUser.getId()));
		IsFriend request2 = (IsFriend) session.get(IsFriend.class,
				new IsFriendId(requestedUser.getId(), requestUser.getId()));
		if (request != null) {
			session.delete(request);
		} else if (request2 != null) {
			session.delete(request2);
		}

		session.getTransaction().commit();
		
		return "OK";
	}

	@GET
	@Path("/pending")
	@Produces(MediaType.APPLICATION_JSON)
	public String pending(@PathParam("username") String userName, @QueryParam("pw") String password) {
		if (!Validation.checkPassword(userName, password, session))
			return "Error";
		
		User user = (User) getUserQuery.setParameter("userName", userName)
				.uniqueResult();
		
		if (user == null)
			return "Error";
		
		List<IsFriend> pendingList = new ArrayList<IsFriend>();
		List<User> result = new ArrayList<User>();
		if (user != null) {
			pendingList = getPendingQuery.setParameter("userId", user.getId())
					.list();
		}
		
		List<Integer> idList = new ArrayList<Integer>();
		for (IsFriend f : pendingList) {
			idList.add(f.getId().getUserId1());
		}

		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.in("id", idList));
		
		try {
			List<User> users = crit.list();

			for (User u : users) {
				u.setPassword(null);
				u.setEmail(null);
				u.setId(null);
			}

			return gson.toJson(users);
			// is thrown when user has no friends
		} catch (SQLGrammarException e) {
			return gson.toJson(new ArrayList<User>());
			//return "Error";
		}
	}
}