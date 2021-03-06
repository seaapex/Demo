package ca.ljz.demo.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.xml.adapters.PasswordAdapter;

import java.util.List;

/**
 * The persistent class for the demo_user database table.
 * 
 */
@Entity
@Table(name = "demo_user")
@NamedQueries({ @NamedQuery(name = User.QUERY_ALL, query = "SELECT u FROM User u"),
		@NamedQuery(name = User.QUERY_NAME, query = "SELECT u FROM User u WHERE u.name = :name") })
@XmlRootElement
@XmlType(propOrder = { "uuid", "name", "password", "creatTime", "editTime", "groups" })
public class User extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5903024118862091874L;

	public static final String QUERY_ALL = "User.findAll";
	public static final String QUERY_NAME = "User.findByName";

	transient Logger logger = LoggerFactory.getLogger(User.class);

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 44)
	private String password;

	/*
	 * // bi-directional many-to-many association to Group
	 * 
	 * @ManyToMany(mappedBy = "users") private List<Group> groups;
	 */

	/*
	 * To map the relationship on the user side (current solution) will allow
	 * client to store group association when creating user. Otherwise, after
	 * user creation, client needs to update group(s) to assign user in to the
	 * group(s)
	 */
	@ManyToMany
	@JoinTable(name = "demo_user_group", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "GROUP_ID", nullable = false) })
	private List<Group> groups;

	public User() {
	}

	public String getName() {
		logger.info("getName");
		return this.name;
	}

	public void setName(String name) {
		logger.info("setName");
		this.name = name;
	}

	@XmlTransient
	public String getPassword() {
		logger.info("getPassword");
		return this.password;
	}

	@XmlElement
	@XmlJavaTypeAdapter(PasswordAdapter.class)
	public void setPassword(String password) {
		logger.info("setPassword");
		this.password = password;
	}

	public List<Group> getGroups() {
		logger.info("getGroups");
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		logger.info("setGroups");
		this.groups = groups;
	}
}