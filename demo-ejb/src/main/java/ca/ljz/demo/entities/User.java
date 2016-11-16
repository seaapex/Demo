package ca.ljz.demo.entities;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the nk_user database table.
 * 
 */
@Entity
@Table(name = "nk_user")
@NamedQueries({ @NamedQuery(name = User.QUERY_ALL, query = "SELECT u FROM User u"),
		@NamedQuery(name = User.QUERY_NAME, query = "SELECT u FROM User u WHERE u.username = :username") })
public class User extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8062894963248046669L;

	public static final String QUERY_ALL = "User.findAll";
	public static final String QUERY_NAME = "User.findByName";

	@Column(nullable = false, length = 50)
	private String email;

	@Column(length = 20)
	private String firstname;

	@Column
	private char gender;

	@Column(length = 20)
	private String lastname;

	@Column(name = "NAME", nullable = false, length = 30)
	private String username;

	@Column(nullable = false, length = 44)
	private String password;

	@Column(length = 15)
	private String phonenumber;

	// bi-directional many-to-one association to Note
	@OneToMany(mappedBy = "owner")
	private List<Note> ownedNotes;

	// bi-directional many-to-many association to Note
	@ManyToMany(mappedBy = "collaborators")
	private List<Note> collaboratedNotes;

	// uni-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(name = "nk_user_role", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", nullable = false) })
	private List<Role> roles;

	public User() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public char getGender() {
		return this.gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public List<Note> getOwnedNotes() {
		return this.ownedNotes;
	}

	public void setOwnedNotes(List<Note> ownedNotes) {
		this.ownedNotes = ownedNotes;
	}

	public Note addOwnedNote(Note ownedNote) {
		getOwnedNotes().add(ownedNote);
		ownedNote.setOwner(this);

		return ownedNote;
	}

	public Note removeOwnedNote(Note ownedNote) {
		getOwnedNotes().remove(ownedNote);
		ownedNote.setOwner(null);

		return ownedNote;
	}

	public List<Note> getCollaboratedNotes() {
		return this.collaboratedNotes;
	}

	public void setCollaboratedNotes(List<Note> collaboratedNotes) {
		this.collaboratedNotes = collaboratedNotes;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}