package ca.ljz.demo.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

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
public class Group extends Base implements IGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1536961265567927480L;

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
	@ManyToMany(targetEntity = User.class, mappedBy = "groups")
	private List<IUser> users;

	public Group() {
	}

	@Override
	public String getName() {
		logger.info("getName");
		return this.name;
	}

	@Override
	public void setName(String name) {
		logger.info("setName");
		this.name = name;
	}

	@XmlTransient
	@Override
	public List<IUser> getUsers() {
		logger.info("getUsers");
		return this.users;
	}

	@Override
	public void setUsers(List<IUser> users) {
		logger.info("setUsers");
		this.users = users;
	}

}