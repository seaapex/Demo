package ca.ljz.demo.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ljz.demo.annotations.Auth;
import ca.ljz.demo.annotations.AuthContext;
import ca.ljz.demo.contexts.CallerManager;
import ca.ljz.demo.entities.User;
import ca.ljz.demo.exceptions.ValidationException;
import ca.ljz.demo.services.UserService;
import ca.ljz.demo.unwrappers.AbstractExceptionUnwrapper;
import ca.ljz.demo.xml.Message;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class UserResource {

	private static Logger logger = Logger.getLogger(UserResource.class.getName());

	@EJB
	UserService us;

	@Inject
	@AuthContext
	CallerManager ac;

	@GET
	@Auth("admin")
	public Response findAll() {
		logger.log(Level.INFO, "findAll");

		logger.log(Level.INFO, ac.getCallerPrincipal());

		logger.log(Level.INFO, us.findAllUsers().size() + " users are returned");
		GenericEntity<List<User>> list = new GenericEntity<List<User>>(us.findAllUsers()) {
		};

		return Response.status(Status.OK).entity(list).build();
	}

	@GET
	@Path("{username}")
	@Auth("admin")
	public Response findByUsername(@PathParam("username") String username) {
		User user = us.findUserByUsername(username);
		return Response.status(Status.OK).entity(user).build();
	}

	@GET
	@Path("{input}/search")
	@Auth({ "admin", "user" })
	public Response searchUser(@PathParam("input") String input) {
		List<User> usernames = new ArrayList<>();
		for (User user : us.searchUserByUsernameOrEmail(input)) {
			User u = new User();
			u.setUsername(user.getUsername());
			u.setGender(user.getGender());
			usernames.add(u);
		}
		GenericEntity<List<User>> list = new GenericEntity<List<User>>(usernames) {
		};

		return Response.status(Status.OK).entity(list).build();
	}

	@POST
	public Response registerUser(User user) {
		logger.log(Level.INFO, "registerUser");
		try {
			us.createUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstname(),
					user.getLastname(), user.getPhonenumber(), user.getGender());
			return Response.status(Status.CREATED).build();
		} catch (Throwable e) {
			logger.log(Level.INFO, "handling exception");

			ValidationException ex = new AbstractExceptionUnwrapper<ValidationException>() {
			}.unwrap(e);

			if (ex != null) {
				Message msgs = new Message();
				msgs.setMessages(ex.getMessages());
				return Response.status(Status.NOT_ACCEPTABLE).entity(msgs).build();
			}

			logger.log(Level.INFO, "unhandled exception");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("{username}/promote")
	@Auth("admin")
	public Response promoteUser(@PathParam("username") String username) {
		logger.log(Level.INFO, "promoteUser");
		us.promoteUser(username);
		return Response.status(Status.OK).build();
	}

	@PUT
	@Path("{username}/demote")
	@Auth("admin")
	public Response demoteUser(@PathParam("username") String username) {
		logger.log(Level.INFO, "demoteUser");

		if (username.equals(ac.getCallerPrincipal()))
			return Response.status(Status.NOT_ACCEPTABLE).build();

		us.demoteUser(username);
		return Response.status(Status.OK).build();
	}

	/**
	 * For admins to edit user. the target is the path param username
	 * 
	 * @param username
	 * @param user
	 * @return
	 */
	@PUT
	@Path("{username}")
	@Auth("admin")
	public Response editUser(@PathParam("username") String username, User user) {
		logger.log(Level.INFO, "editUser");
		try {
			us.editUser(username, user.getPassword(), user.getEmail(), user.getFirstname(), user.getLastname(),
					user.getPhonenumber(), user.getGender());
			return Response.status(Status.ACCEPTED).build();
		} catch (Throwable e) {
			logger.log(Level.INFO, "handling exception");

			ValidationException ex = new AbstractExceptionUnwrapper<ValidationException>() {
			}.unwrap(e);

			if (ex != null) {
				Message msgs = new Message();
				msgs.setMessages(ex.getMessages());
				return Response.status(Status.NOT_ACCEPTABLE).entity(msgs).build();
			}

			logger.log(Level.INFO, "unhandled exception");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * for users to update their own profile
	 * 
	 * @param user
	 * @return
	 */
	@PUT
	@Auth({ "admin", "user" })
	public Response updateProfile(User user) {
		logger.log(Level.INFO, "updateProfile");
		try {
			us.editUser(ac.getCallerPrincipal(), user.getPassword(), user.getEmail(), user.getFirstname(),
					user.getLastname(), user.getPhonenumber(), user.getGender());
			return Response.status(Status.ACCEPTED).build();
		} catch (Throwable e) {
			logger.log(Level.INFO, "handling exception");

			ValidationException ex = new AbstractExceptionUnwrapper<ValidationException>() {
			}.unwrap(e);

			if (ex != null) {
				Message msgs = new Message();
				msgs.setMessages(ex.getMessages());
				return Response.status(Status.NOT_ACCEPTABLE).entity(msgs).build();
			}

			logger.log(Level.INFO, "unhandled exception");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Auth("admin")
	@Path("{username}")
	public Response deleteUser(@PathParam("username") String username) {
		logger.log(Level.INFO, "deleteUser");
		us.deleteUserByUsername(username);
		return Response.status(Status.OK).build();

	}
}