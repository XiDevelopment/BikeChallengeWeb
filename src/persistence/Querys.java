package persistence;

public class Querys {
	public static String GET_USER_QUERY = "FROM User WHERE name = :userName";
	public static String GET_FRIENDS_QUERY = "FROM IsFriend WHERE "
			+ "(id.userId1 = :userId OR id.userId2 = :userId) AND status = 'accepted'";
	public static String GET_PENDING_QUERY = "FROM IsFriend f " +
			"WHERE (id.userId2 = :userId) AND status = 'pending'";

	public static String GET_ROUTES_QUERY = "FROM Route R WHERE R.userId = :userId";
	public static String GET_ROUTE_QUERY = "FROM Route WHERE userId = :userId " +
			"AND id = :routeId";
	public static String DELETE_ROUTE_QUERY = "DELETE Route WHERE id = :routeId " +
			"AND userId = :userId";

	public static String GET_GLOBAL_DISTANCE_QUERY = "SELECT sum(distance) " +
			"FROM Route";
	public static String GET_GLOBAL_TIME_QUERY = 
			"SELECT SUM(TIMESTAMPDIFF(SECOND, starttime, stoptime)) * 1000 " +
			"FROM Route";
	
	public static String GET_USER_PW = "SELECT password FROM User WHERE name = :userName";
}
