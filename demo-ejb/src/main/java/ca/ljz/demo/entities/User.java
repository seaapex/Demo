package ca.ljz.demo.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ljz.demo.xml.adapters.PasswordAdapter;

import java.util.List;

/**
 * The persistent class for the nk_user database table.
 * 
 */
@Entity
@Table(name = "nk_user")
@NamedQueries({ @NamedQuery(name = User.QUERY_ALL, query = "SELECT u FROM User u"),
		@NamedQuery(name = User.QUERY_NAME, query = "SELECT u FROM User u WHERE u.username = :username") })
@XmlRootElement
public class User extends Base<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8062894963248046669L;

	public static final String QUERY_ALL = "User.findAll";
	public static final String QUERY_NAME = "User.findByName";

	@Id
	@Column(nullable = false, length = 30)
	private String username;

	@Column(nullable = false, length = 44)
	private String password;

	@Column(nullable = false, length = 50)
	private String email;

	@Column(length = 20)
	private String firstname;

	@Column(length = 20)
	private String lastname;

	@Column(length = 15)
	private String phonenumber;

	@Column
	private char gender;

	// bi-directional many-to-one association to Note
	@OneToMany(mappedBy = "owner")
	private List<Note> ownedNotes;

	// bi-directional many-to-many association to Note
	@ManyToMany(mappedBy = "collaborators")
	private List<Note> collaboratedNotes;

	// uni-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(name = "nk_user_role", joinColumns = {
			@JoinColumn(name = "USERNAME", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ROLENAME", nullable = false) })
	private List<Role> roles;

	public User() {
	}

	@XmlTransient
	@Override
	public String getId() {
		return getUsername();
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

	@XmlTransient
	public String getPassword() {
		return this.password;
	}

	@XmlJavaTypeAdapter(PasswordAdapter.class)
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@XmlTransient
	public List<Note> getOwnedNotes() {
		return this.ownedNotes;
	}

	public void setOwnedNotes(List<Note> ownedNotes) {
		this.ownedNotes = ownedNotes;
	}

	@XmlTransient
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