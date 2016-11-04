package ca.ljz.demo.resources;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;
import org.junit.Test;

public class UserTest {

	@Test
	public void selfUserRegister() {
		// Create a new Client object
		Client client = ClientBuilder.newClient();

		// prepare a json string to create user, and not specify the group. by
		// doing this, this particular user do not have a group, and will be
		// resisted to access many functions
		String json = "{\"name\":\"user5\",\"password\":\"demo\"}";

		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		System.out.println(entity.toString());

		// Set the url, which can be a String or wrapped object
		WebTarget target = client.target("http://localhost:8080/demo-web/rest/user");

		// Build a builder
		Builder builder = target.request();

		final Response response = builder.put(entity);
		System.out.println(response.readEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void userRegister() {
		// Create a new Client object
		Client client = ClientBuilder.newClient();

		// Set an authentication (if needed)
		client = client.register(new BasicAuthentication("demo", "demo"));

		// prepare a json string to create user, and specify that this user is
		// in "user" group. in the real world, the group id and name may be
		// retrieved separately
		String json = "{\"name\":\"user6\",\"password\":\"demo\",\"groups\":[{\"uuid\":\"35323334-3536-3738-3930-313233343536\",\"name\":\"user\"}]}";

		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		System.out.println(entity.toString());

		// Set the url, which can be a String or wrapped object
		WebTarget target = client.target("http://localhost:8080/demo-web/rest/user");

		// Build a builder
		Builder builder = target.request();

		final Response response = builder.put(entity);
		System.out.println(response.readEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void listAllUsers() {
		// Create a new Client object
		Client client = ClientBuilder.newClient();

		// Set an authentication (if needed)
		client = client.register(new BasicAuthentication("demo", "demo"));

		// Set the url, which can be a String or wrapped object
		WebTarget target = client.target("http://localhost:8080/demo-web/rest/user");

		// Build a builder
		Builder builder = target.request();

		final Response response = builder.get();
		System.out.println(response.readEntity(String.class));
		assertEquals(200, response.getStatus());
	}

	@Test
	public void listAllUsersNotPermited() {
		// Create a new Client object
		Client client = ClientBuilder.newClient();

		// Set an authentication (if needed)
		client = client.register(new BasicAuthentication("user1", "demo"));

		// Set the url, which can be a String or wrapped object
		WebTarget target = client.target("http://localhost:8080/demo-web/rest/user");

		// Build a builder
		Builder builder = target.request();

		final Response response = builder.get();
		System.out.println(response.readEntity(String.class));
		assertEquals(500, response.getStatus());
	}

	@Test
	public void findUser() {
		// Create a new Client object
		Client client = ClientBuilder.newClient();

		// Set an authentication (if needed)
		client = client.register(new BasicAuthentication("demo", "demo"));

		// Set the url, which can be a String or wrapped object
		WebTarget target = client
				.target("http://localhost:8080/demo-web/rest/user/31323334-3536-3738-3930-313233343536");

		// Build a builder
		Builder builder = target.request();

		final Response response = builder.get();
		System.out.println(response.readEntity(String.class));
		assertEquals(200, response.getStatus());
	}
}
