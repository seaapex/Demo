package ca.ljz.demo.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.ljz.demo.entities.Note;
import ca.ljz.demo.entities.User;
import ca.ljz.demo.exceptions.ValidationException;
import ca.ljz.demo.ejbs.NoteEJB;
import ca.ljz.demo.ejbs.UserEJB;

@Stateless
public class NoteService {
	@EJB
	UserEJB userEJB;

	@EJB
	NoteEJB noteEJB;

	public List<Note> findAllNotesByUsername(String username) {
		User user = userEJB.get(username);
		List<Note> owned = user.getOwnedNotes();
		List<Note> collaborated = user.getCollaboratedNotes();

		List<Note> all = new ArrayList<>();

		for (Note note : owned)
			if (!note.getIsTrushed())
				all.add(note);

		for (Note note : collaborated)
			if (!note.getIsTrushed())
				all.add(note);

		return all;
	}

	public List<Note> findAllTrushedNotesByUsername(String username) {
		User user = userEJB.get(username);
		List<Note> owned = user.getOwnedNotes();
		List<Note> collaborated = user.getCollaboratedNotes();

		List<Note> all = new ArrayList<>();

		for (Note note : owned)
			if (note.getIsTrushed())
				all.add(note);

		for (Note note : collaborated)
			if (note.getIsTrushed())
				all.add(note);

		return all;
	}

	public Note findNoteById(String username, int noteid) {
		User user = userEJB.get(username);
		List<Note> owned = user.getOwnedNotes();
		List<Note> collaborated = user.getCollaboratedNotes();

		for (Note note : owned)
			if (note.getId().intValue() == noteid)
				return note;

		for (Note note : collaborated)
			if (note.getId().intValue() == noteid)
				return note;

		return null;
	}

	public int createNote(String username) throws ValidationException {
		User owner = userEJB.get(username);
		Note note = new Note();
		note.setOwner(owner);
		return noteEJB.add(note);
	}

	public boolean editOwnedNote(String ownerUsername, int noteid, String noteContent) {
		Note note = noteEJB.get(noteid);

		if (note.getOwner().getUsername().equals(ownerUsername)) {
			note.setContent(noteContent);
			noteEJB.update(note);
			return true;
		}
		return false;
	}

	public boolean editCollaboratedNote(String collaboratorUsername, int noteid, String noteContent) {
		Note note = noteEJB.get(noteid);

		for (User collaborator : note.getCollaborators()) {
			if (collaborator.getUsername().equals(collaboratorUsername)) {
				note.setContent(noteContent);
				noteEJB.update(note);
				return true;
			}
		}

		return false;
	}

	public boolean addCollaborator(String ownerUsername, String collaboratorUsername, int noteid) {
		Note note = noteEJB.get(noteid);

		if (note.getOwner().getUsername().equals(ownerUsername)) {

			// owners add themselves as collaborator
			if (ownerUsername.equals(collaboratorUsername))
				return true;

			User collaborator = userEJB.get(collaboratorUsername);

			if (!note.getCollaborators().contains(collaborator))
				note.getCollaborators().add(collaborator);

			if (!collaborator.getCollaboratedNotes().contains(note))
				collaborator.getCollaboratedNotes().add(note);

			noteEJB.update(note);
			userEJB.update(collaborator);
			return true;
		}

		return false;
	}

	public boolean removeCollaborator(String ownerUsername, String collaboratorUsername, int noteid) {
		// odd case: owners remove themselves as collaborator
		if (ownerUsername.equals(collaboratorUsername))
			return false;

		Note note = noteEJB.get(noteid);

		if (note.getOwner().getUsername().equals(ownerUsername)) {

			User collaborator = userEJB.get(collaboratorUsername);

			note.getCollaborators().remove(collaborator);
			collaborator.getCollaboratedNotes().remove(note);

			noteEJB.update(note);
			userEJB.update(collaborator);
			return true;
		}

		return false;
	}

	public boolean deleteNote(String ownerUsername, int noteid) {
		Note note = noteEJB.get(noteid);

		if (note.getOwner().getUsername().equals(ownerUsername)) {

			note.setIsTrushed(true);
			noteEJB.update(note);

			return true;
		}

		return false;
	}

	public boolean restoreNote(String ownerUsername, int noteid) {
		Note note = noteEJB.get(noteid);

		if (note.getOwner().getUsername().equals(ownerUsername)) {

			note.setIsTrushed(false);
			noteEJB.update(note);

			return true;
		}

		return false;
	}
}
