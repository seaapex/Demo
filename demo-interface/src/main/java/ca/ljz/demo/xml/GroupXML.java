package ca.ljz.demo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import ca.ljz.demo.model.GroupModel;
import ca.ljz.demo.model.UserModel;

import java.util.List;

@XmlRootElement
@XmlType(propOrder = { "uuid", "name", "creatTime", "editTime" })
@XmlSeeAlso(UserXML.class)
public class GroupXML extends BaseXML implements GroupModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1536961265567927480L;

	public static final String QUERY_ALL = "Group.findAll";
	public static final String QUERY_NAME = "Group.findByName";

	private String name;

	private List<UserModel> users;

	public GroupXML() {
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
	@XmlTransient
	public List<UserModel> getUsers() {
		logger.info("getUsers");
		return this.users;
	}

	@Override
	@XmlElement(type = UserXML.class)
	public void setUsers(List<UserModel> users) {
		logger.info("setUsers");
		this.users = users;
	}

}