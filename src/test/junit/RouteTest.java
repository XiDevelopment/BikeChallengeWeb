package test.junit;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import rest.RouteRest;
import rest.UserRest;

import model.Route;
import model.User;
import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RouteTest {
	RouteRest rest = new RouteRest();
	static UserRest restUser = new UserRest();
	static Route route = new Route();
	static User user;
	static Gson gson = new Gson();
	static int routeId = 0;
		
	@BeforeClass
	public static void init() {
		route.setId(9);
		route.setDistance(399.0f);
		route.setStartTime(new Date());
		route.setStopTime(new Date());
		
		String userJson = restUser.login("junituser", "TESTPW");
		user = gson.fromJson(userJson, User.class);
		
		route.setUserId(user.getId());
		
	}
	
	@Test
	public void a_postRoute() {
		assertEquals("OK", rest.postRoute("junituser", "TESTPW", gson.toJson(route)));
		assertEquals("Error", rest.postRoute("junituser", "TESTPW", "KEINJSON"));
		assertEquals("Error", rest.postRoute("userDerNichtExistiert", "TESTPW", gson.toJson(route)));
	}
	
	@Test
	public void b_getAllRoutes() {
		String routeJson = rest.getAllRoutes("junituser", "TESTPW");
		assertNotEquals("Error", routeJson);
		List<Route> list = gson.fromJson(routeJson, new TypeToken<List<Route>>(){}.getType());
		if (list.size() != 0)
			routeId = list.get(0).getId();
		assertEquals("Error", rest.getAllRoutes("userDerNichtExistiert", "TESTPW"));
	}
	
	@Test 
	public void c_getRoute() {
		assertNotEquals("Error", rest.getRoute("junituser", "TESTPW", String.valueOf(routeId)));
		assertEquals("Error", rest.getRoute("junituser", "TESTPW", "999999"));
		assertEquals("Error", rest.getRoute("junituser", "wrongpw", "999999"));
		assertEquals("Error", rest.getRoute("userDerNichtExisiter", "TESTPW", "13"));
		assertEquals("Error", rest.getRoute("userDerNichtExisiter", "TESTPW", "9999"));
		assertEquals("Error", rest.getRoute("junituser", "TESTPW", "asdf"));
	}
	

}
