package ca.ljz.demo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.ljz.demo.model.GroupModel;
import ca.ljz.demo.model.UserModel;
import ca.ljz.demo.xml.adapters.PasswordAdapter;

import java.util.List;

/**
 * The persistent class for the demo_user database table.
 * 
 */
@XmlRootElement
@XmlType(propOrder = { "uuid", "name", "password", "creatTime", "editTime", "groups" })
public class UserXML extends BaseXML implements UserModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5903024118862091874L;

	public static final String QUERY_ALL = "User.findAll";
	public static final String QUERY_NAME = "User.findByName";

	private String name;

	private String password;

	private List<GroupModel> groups;

	public UserXML() {
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
	public String getPassword() {
		logger.info("getPassword");
		return this.password;
	}

	@XmlElement
	@XmlJavaTypeAdapter(PasswordAdapter.class)
	@Override
	public void setPassword(String password) {
		logger.info("setPassword");
		this.password = password;
	}

	@Override
	@XmlElement(type = GroupXML.class)
	public List<GroupModel> getGroups() {
		logger.info("getGroups");
		return this.groups;
	}

	@Override
	// Instead of setting GroupModel to causes problem, jaxb will use GroupXML
	@XmlElement(type = GroupXML.class)
	public void setGroups(List<GroupModel> groups) {
		logger.info("setGroups");
		this.groups = groups;
	}
}