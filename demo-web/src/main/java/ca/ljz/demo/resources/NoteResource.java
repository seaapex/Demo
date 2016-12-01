package ca.ljz.demo.resources;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import ca.ljz.demo.entities.Note;
import ca.ljz.demo.services.NoteService;

@Path("note")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class NoteResource {

	private static Logger logger = Logger.getLogger(NoteResource.class.getName());

	@EJB
	NoteService ns;

	@Inject
	@AuthContext
	CallerManager ac;

	@GET
	@Auth("user")
	public Response findAllNotes() {
		logger.log(Level.INFO, "findAllNotes");

		logger.log(Level.WARNING, ac.getCallerPrincipal());

		GenericEntity<List<Note>> list = new GenericEntity<List<Note>>(
				ns.findAllNotesByUsername(ac.getCallerPrincipal())) {
		};

		return Response.status(Status.OK).entity(list).build();
	}

	@GET
	@Auth("user")
	@Path("trushed")
	public Response findAllTrushedNotes() {
		logger.log(Level.INFO, "findAllNotes");

		logger.log(Level.INFO, ac.getCallerPrincipal());

		GenericEntity<List<Note>> list = new GenericEntity<List<Note>>(
				ns.findAllTrushedNotesByUsername(ac.getCallerPrincipal())) {
		};

		return Response.status(Status.OK).entity(list).build();
	}

	@GET
	@Auth("user")
	@Path("id/{noteid}")
	public Response findNote(@PathParam("noteid") String noteid) {
		logger.log(Level.INFO, "findAllNotes");

		logger.log(Level.INFO, ac.getCallerPrincipal());

		String username = ac.getCallerPrincipal();
		Note note = ns.findNoteById(username, Integer.parseInt(noteid));

		if (note != null)
			return Response.status(Status.OK).entity(note).build();
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Auth("user")
	@Path("create")
	public Response createNote() {

		logger.log(Level.INFO, "createNote");
		String username = ac.getCallerPrincipal();
		int id = ns.createNote(username);

		logger.log(Level.INFO, "created Note id: " + id);

		Note note = ns.findNoteById(username, id);

		return Response.status(Status.CREATED).entity(note).build();
	}

	@PUT
	@Auth("user")
	public Response saveOwnedNote(Note note) {

		logger.log(Level.INFO, "saveOwnedNote");
		String username = ac.getCallerPrincipal();
		if (ns.editOwnedNote(username, note.getId(), note.getContent()))
			return Response.status(Status.OK).build();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@PUT
	@Auth("user")
	@Path("collaborate")
	public Response saveCollaboratedNote(Note note) {

		logger.log(Level.INFO, "saveCollaboratedNote");
		String username = ac.getCallerPrincipal();
		if (ns.editCollaboratedNote(username, note.getId(), note.getContent()))
			return Response.status(Status.OK).build();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@PUT
	@Auth("user")
	@Path("collaborate/{noteid}/{collaborator}")
	public Response addCollaborator(@PathParam("noteid") String noteid,
			@PathParam("collaborator") String collaborator) {

		logger.log(Level.INFO, "addCollaborator");
		String owner = ac.getCallerPrincipal();
		if (ns.addCollaborator(owner, collaborator, Integer.parseInt(noteid)))
			return Response.status(Status.OK).build();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@DELETE
	@Auth("user")
	@Path("collaborate/{noteid}/{collaborator}")
	public Response removeCollaborator(@PathParam("noteid") String noteid,
			@PathParam("collaborator") String collaborator) {

		logger.log(Level.INFO, "removeCollaborator");
		String owner = ac.getCallerPrincipal();
		if (ns.removeCollaborator(owner, collaborator, Integer.parseInt(noteid)))
			return Response.status(Status.OK).build();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@DELETE
	@Auth("user")
	@Path("delete/{noteid}")
	public Response deleteNote(@PathParam("noteid") String noteid) {

		logger.log(Level.INFO, "deleteNote");
		String owner = ac.getCallerPrincipal();
		if (ns.deleteNote(owner, Integer.parseInt(noteid)))
			return Response.status(Status.OK).build();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@PUT
	@Auth("user")
	@Path("restore/{noteid}")
	public Response restoreNote(@PathParam("noteid") String noteid) {

		logger.log(Level.INFO, "deleteNote");
		String owner = ac.getCallerPrincipal();
		if (ns.restoreNote(owner, Integer.parseInt(noteid)))
			return Response.status(Status.OK).build();
		return Response.status(Status.UNAUTHORIZED).build();
	}

}