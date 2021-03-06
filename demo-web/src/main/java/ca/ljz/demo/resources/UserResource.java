package ca.ljz.demo.resources;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
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

import ca.ljz.demo.ejbs.UserEJB;
import ca.ljz.demo.entities.User;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
@DeclareRoles({ "admin", "user" })
public class UserResource {

	Logger logger = LoggerFactory.getLogger(UserResource.class);

	@EJB
	UserEJB userEJB;

	@Resource
	SessionContext sc;

	@GET
	@RolesAllowed("admin")
	public Response findAll() {
		logger.info("findAll");
		List<User> users = userEJB.search(null);
		return Response.status(Status.OK).entity(users).build();
	}

	@GET
	@Path("{id}")
	@RolesAllowed({ "admin", "user" })
	public Response findById(@PathParam("id") String id) {
		logger.info("findById");
		User user = userEJB.get(id);
		return Response.status(Status.OK).entity(user).build();
	}

	@PUT
	@PermitAll
	public Response createUser(User user) {
		logger.info("createUser");
		String id = userEJB.add(user);
		return Response.status(Status.OK).entity(id).build();
	}

}