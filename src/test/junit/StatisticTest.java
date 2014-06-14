package test.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Route;
import model.User;
import rest.RouteRest;
import rest.StatisticRest;
import rest.UserRest;

import com.google.gson.Gson;

public class StatisticTest {
	static StatisticRest rest = new StatisticRest();
	static Gson gson = new Gson();

	@Test
	public void getStatistic() {
		assertNotEquals("Error", rest.getStatistic("junituser", "TESTPW"));
		assertEquals("Error", rest.getStatistic("userdernichtexistiert", "TESTPW"));

	}

}
