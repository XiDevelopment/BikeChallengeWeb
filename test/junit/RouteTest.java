package junit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import rest.RouteRest;

import model.Route;
import junit.framework.TestCase;

public class RouteTest {
	RouteRest rest = new RouteRest();
	static Route route = new Route();
	Gson gson = new Gson();
		
	@BeforeClass
	public static void init() {
		route.setId(9);
		route.setDistance(10.0f);
		route.setStarttime(new Date());
		route.setStoptime(new Date());
	}
	
	@Test
	public void addRoute() {
		assertEquals("OK", rest.postRoute("rick", gson.toJson(route)));
	}
	
	@Test
	public void getAllRoutes() {
		assertNotEquals("Error", rest.getAllRoutes("rick"));
	}
	
	@Test 
	public void getRoute() {
		assertNotEquals("Error", rest.getRoute("rick", "13"));
		assertEquals("Error", rest.getRoute("rick", "9999"));
	}
	

}
