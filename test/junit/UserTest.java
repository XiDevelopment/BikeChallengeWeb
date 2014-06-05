package junit;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import rest.UserRest;

import model.User;

public class UserTest {
	static UserRest rest = new UserRest();
	static User user = new User();
	Gson gson = new Gson();

		
	@BeforeClass
	public static void init() {
		user.setName("junituser");
		user.setPassword("TESTPW");
		user.setScore(0);
		user.setEmail("test@test.test");
	}
	
	@AfterClass 
	public static void after() {
		rest.deleteUser("junituser");
	}
	
	@Test
	public void register() {
		assertEquals("OK", rest.register("junituser", gson.toJson(user)));
		user.setName("rick");
		assertEquals("Error", rest.register("rick", gson.toJson(user)));
	}
	
	@Test
	public void login() {
		String userJson = rest.getUser("rick");
		User user = gson.fromJson(userJson, User.class);
		assertEquals("rick", user.getName());
		assertEquals("Error", rest.getUser("ichexistierenicht"));
	}
	
}
