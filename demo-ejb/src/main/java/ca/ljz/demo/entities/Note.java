package ca.ljz.demo.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

/**
 * The persistent class for the nk_note database table.
 * 
 */
@Entity
@Table(name = "nk_note")
@NamedQuery(name = "Note.findAll", query = "SELECT n FROM Note n")
@XmlRootElement
public class Note extends Base<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4890835542854229507L;

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noteid;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(name = "TRUSHED", nullable = false)
	private boolean isTrushed;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "USERNAME", nullable = false)
	private User owner;

	// bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(name = "nk_user_note", joinColumns = {
			@JoinColumn(name = "NOTEID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "USERNAME", nullable = false) })
	private List<User> collaborators;

	public Note() {
	}

	@XmlTransient
	public int getNoteid() {
		return this.noteid;
	}

	public void setNoteid(int noteid) {
		this.noteid = noteid;
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

	@XmlTransient
	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@XmlTransient
	public List<User> getCollaborators() {
		return this.collaborators;
	}

	public void setCollaborators(List<User> collaborators) {
		this.collaborators = collaborators;
	}

	@Override
	public Integer getId() {
		return this.noteid;
	}

}