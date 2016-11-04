package ca.ljz.demo.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The persistent class for the demo_group database table.
 * 
 */
@Entity
@Table(name = "demo_group")
@NamedQueries({ @NamedQuery(name = Group.QUERY_ALL, query = "SELECT g FROM Group g"),
		@NamedQuery(name = Group.QUERY_NAME, query = "SELECT g FROM Group g WHERE g.name = :name") })
@XmlRootElement
@XmlType(propOrder = { "uuid", "name", "creatTime", "editTime" })
public class Group extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1536961265567927480L;

	transient Logger logger = LoggerFactory.getLogger(Group.class);

	public static final String QUERY_ALL = "Group.findAll";
	public static final String QUERY_NAME = "Group.findByName";

	@Column(nullable = false, length = 20)
	private String name;

	/*
	 * // bi-directional many-to-many association to User
	 * 
	 * @ManyToMany
	 * 
	 * @JoinTable(name = "demo_user_group", joinColumns = {
	 * 
	 * @JoinColumn(name = "GROUP_ID", nullable = false) }, inverseJoinColumns =
	 * {
	 * 
	 * @JoinColumn(name = "USER_ID", nullable = false) }) private List<User>
	 * users;
	 */

	/*
	 * To map the relationship on the user side (current solution) will allow
	 * client to store group association when creating user. Otherwise, after
	 * user creation, client needs to update group(s) to assign user in to the
	 * group(s)
	 */
	@ManyToMany(mappedBy = "groups")
	private List<User> users;

	public Group() {
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
	public List<User> getUsers() {
		logger.info("getUsers");
		return this.users;
	}

	public void setUsers(List<User> users) {
		logger.info("setUsers");
		this.users = users;
	}

}