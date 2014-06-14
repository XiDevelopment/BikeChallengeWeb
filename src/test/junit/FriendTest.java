package test.junit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;

import rest.FriendRest;
import rest.UserRest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendTest {
	static FriendRest rest = new FriendRest();
	static UserRest restUser = new UserRest();
	static Gson gson = new Gson();
	
	@BeforeClass
	public static void init() {

	}
	
	@Test
	public void a_getFriends() {
		assertNotEquals("Error", rest.getFriends("junituser", "TESTPW"));
		assertEquals("Error", rest.getFriends("junituser", "wrongpw"));
	}
	
	@Test
	public void b_request() {
		assertEquals("OK", rest.request("junituser", "TESTPW", "junituser2"));
		assertEquals("Error", rest.request("junituser", "TESTPW", "userdernichtexistiert"));
		assertEquals("Error", rest.request("userdernichtexistiert", "TESTPW", "junituser2"));
	}
	
	@Test
	public void c_pending() {
		assertNotEquals("Error", rest.pending("junituser", "TESTPW"));
		assertNotEquals("Error", rest.pending("junituser2", "TESTPW"));
		assertEquals("Error", rest.pending("userdernichtexistiert", "TESTPW"));
		assertEquals("Error", rest.pending("junituser", "wrongpw"));
	}
	
	@Test
	public void d_accept() {
		rest = new FriendRest();
		assertEquals("OK", rest.accept("junituser2", "TESTPW", "junituser"));
		assertEquals("Error", rest.accept("junituser", "TESTPW", "junituser2"));
	}
	
	@Test
	public void e_refuse() {
		assertEquals("OK", rest.refuse("junituser", "TESTPW", "junituser2"));
		assertEquals("OK", rest.refuse("junituser2", "TESTPW", "junituser"));
	}
}
