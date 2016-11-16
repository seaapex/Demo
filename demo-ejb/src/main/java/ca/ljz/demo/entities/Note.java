package ca.ljz.demo.entities;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the nk_note database table.
 * 
 */
@Entity
@Table(name="nk_note")
@NamedQuery(name="Note.findAll", query="SELECT n FROM Note n")
public class Note extends Base  {
	private static final long serialVersionUID = 1L;

	@Lob
	@Column(nullable=false)
	private String content;

	@Column(name="TRUSHED", nullable=false)
	private boolean isTrushed;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="OWNER_ID", nullable=false)
	private User owner;

	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(
		name="nk_user_note"
		, joinColumns={
			@JoinColumn(name="NOTE_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		)
	private List<User> collaborators;

	public Note() {
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getIsTrushed() {
		return this.isTrushed;
	}

	public void setIsTrushed(boolean isTrushed) {
		this.isTrushed = isTrushed;
	}

	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getCollaborators() {
		return this.collaborators;
	}

	public void setCollaborators(List<User> collaborators) {
		this.collaborators = collaborators;
	}

}