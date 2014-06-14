package test.test;

import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientResponse;

import com.google.gson.Gson;

import model.Route;
import model.User;

public class RestTest {
	public static void main(String[] args) {
		/*
		User user = new User("hi", "hipw", "hi@hi.hi");
		Route route = new Route(10.5f, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 10000), String.valueOf(1));
		Gson gson = new Gson();
		String json = gson.toJson(user);
		String routeJson = gson.toJson(route);
		Client client = ClientBuilder.newClient();
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		Entity<String> routeE = Entity.entity(routeJson, MediaType.APPLICATION_JSON);
		WebTarget target = client.target("http://localhost:8080/BikeChallengeWeb/route").path("TEST");
		//Response response = target.request().post(entity);
		//Response response2 = target.request().post(routeE);
		//Response response3 = target.request().post(routeE);
		Response response3 = target.request().get();
		String routeListJson = response3.readEntity(String.class);
		Route[] routeList = gson.fromJson(routeListJson, Route[].class);
		
		for (Route r : routeList) {
			System.out.println(r.getId());
		}
		
		System.out.println("LOL");
		//System.out.println(response3.readEntity(String.class));
		System.out.println("LOL");
		*/
	}
}
