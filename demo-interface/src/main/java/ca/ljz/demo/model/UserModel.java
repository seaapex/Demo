package ca.ljz.demo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import ca.ljz.demo.xml.UserXML;

@XmlRootElement(name = "user")
@XmlSeeAlso(UserXML.class)
@XmlType(propOrder = { "uuid", "name", "password", "creatTime", "editTime", "groups" })
public interface UserModel extends BaseModel {
	
	@XmlElement
	public String getName();

	@XmlElement
	public void setName(String name);

	@XmlTransient
	public String getPassword();

	@XmlElement
	public void setPassword(String password);

	@XmlElement
	public List<GroupModel> getGroups();

	@XmlElement
	public void setGroups(List<GroupModel> groups);
}
