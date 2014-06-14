package test.junit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;

import rest.UserRest;

import model.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
	static UserRest rest = new UserRest();
	static User user = new User();
	static User user2 = new User();
	Gson gson = new Gson();

		
	@BeforeClass
	public static void init() {
		rest.deleteUser("junituser");
		user.setName("junituser");
		user.setPassword("TESTPW");
		user.setScore(0);
		user.setAvatar(0);
		user.setEmail("test@test.test");
		
		rest.deleteUser("junituser2");
		user2.setName("junituser2");
		user2.setPassword("TESTPW");
		user2.setScore(0);
		user2.setAvatar(0);
		user2.setEmail("test@test.test");
	}
	
	@Test
	public void a_register() {
		assertEquals("OK", rest.register("junituser", gson.toJson(user)));
		assertEquals("OK", rest.register("junituser2", gson.toJson(user2)));
		assertEquals("Error", rest.register("junituser", gson.toJson(user)));
	}
	
	@Test
	public void b_login() {
		String userJson = rest.login("junituser", "TESTPW");
		User user = gson.fromJson(userJson, User.class);
		assertEquals("junituser", user.getName());
		assertEquals("Error", rest.login("ichexistierenicht", "pw"));
	}
	
	@Test
	public void c_setAvatar() {
		assertEquals("OK", rest.setAvatar("junituser", "TESTPW", "5"));
		assertEquals("Error", rest.setAvatar("junituser", "wrongpw", "1"));
		assertEquals("Error", rest.setAvatar("userdernichtexistiert", "TESTPW", "1"));
		assertEquals("Error", rest.setAvatar("junituser", "TESTPW", "NaN"));
	}
	
}
