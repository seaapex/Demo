package ca.ljz.demo.ejbs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.ljz.demo.entities.Note;
import ca.ljz.demo.entities.User;

/**
 * Session Bean implementation class NoteEJB
 */
@Stateless
public class NoteEJB extends BaseEJB<Integer, Note> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8476159698976290856L;

	@EJB
	UserEJB userEJB;

	@Override
	protected Class<Note> getEntityType() {
		return Note.class;
	}

	@Override
	public List<Note> findAll() {
		return null;
	}

	@Override
	public Integer add(Note note) {

		note.setOwner(userEJB.get(note.getOwner().getId()));
		note.getOwner().getOwnedNotes().add(note);

		List<User> collaborators = new ArrayList<>();
		note.getCollaborators().forEach(collaborator -> collaborators.add(userEJB.get(collaborator.getId())));
		note.setCollaborators(collaborators);

		note.getCollaborators().forEach(collaborator -> collaborator.getCollaboratedNotes().add(note));

		return super.add(note);
	}

	@Override
	public Note delete(Integer id) {
		// TODO Auto-generated method stub
		return super.delete(id);
	}

	// public Note addOwnedNote(Note ownedNote) {
	// getOwnedNotes().add(ownedNote);
	// ownedNote.setOwner(this);
	//
	// return ownedNote;
	// }
	//
	// public Note removeOwnedNote(Note ownedNote) {
	// getOwnedNotes().remove(ownedNote);
	// ownedNote.setOwner(null);
	//
	// return ownedNote;
	// }

}
