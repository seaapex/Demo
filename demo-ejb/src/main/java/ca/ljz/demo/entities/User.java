package ca.ljz.demo.entities;

import javax.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.model.GroupModel;
import ca.ljz.demo.model.UserModel;
import java.util.List;

/**
 * The persistent class for the demo_user database table.
 * 
 */
@Entity
@Table(name = "demo_user")
@NamedQueries({ @NamedQuery(name = User.QUERY_ALL, query = "SELECT u FROM User u"),
		@NamedQuery(name = User.QUERY_NAME, query = "SELECT u FROM User u WHERE u.name = :name") })
public class User extends Base implements UserModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5903024118862091874L;

	private static final Logger logger = LoggerFactory.getLogger(User.class);

	public static final String QUERY_ALL = "User.findAll";
	public static final String QUERY_NAME = "User.findByName";

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
	@ManyToMany(targetEntity = Group.class/* , cascade = { CascadeType.ALL } */)
	@JoinTable(name = "demo_user_group", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "GROUP_ID", nullable = false) })
	private List<GroupModel> groups;

	public User() {
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

	@Override
	public String getPassword() {
		logger.info("getPassword");
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		logger.info("setPassword");
		this.password = password;
	}

	@Override
	public List<GroupModel> getGroups() {
		logger.info("getGroups");
		return this.groups;
	}

	@Override
	public void setGroups(List<GroupModel> groups) {
		logger.info("setGroups");
		this.groups = groups;
	}
}