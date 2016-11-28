package ca.ljz.demo.resources;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.entities.User;
import ca.ljz.demo.exceptions.InvalidUserException;
import ca.ljz.demo.services.UserService;
import ca.ljz.demo.xml.Message;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
@DeclareRoles({ "admin", "user" })
public class UserResource {

	private static Logger logger = LoggerFactory.getLogger(UserResource.class);

	@EJB
	UserService us;

	@GET
	@RolesAllowed("admin")
//	@PermitAll
	public List<User> findAll() {
		logger.info("findAll");
		return us.findAllUsers();
	}

	@GET
	@Path("{id}")
	@RolesAllowed({ "admin", "user" })
	public Response findById(@PathParam("id") String id) {
		logger.info("findById");
		User user = us.findUserById(id);
		return Response.status(Status.OK).entity(user).build();
	}

	@PUT
	@PermitAll
	public Response registerUser(User user) {
		logger.info("createUser");
		try {
			String id = us.createUser(user);
			return Response.status(Status.CREATED).entity(id).build();
		} catch (InvalidUserException e) {
			Message msgs = new Message();
			msgs.setMessages(e.getMessages());

			return Response.status(Status.NOT_ACCEPTABLE).entity(msgs).build();
		}
	}

}