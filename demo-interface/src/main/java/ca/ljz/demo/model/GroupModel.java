package ca.ljz.demo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import ca.ljz.demo.xml.GroupXML;

@XmlRootElement(name = "group")
@XmlSeeAlso(GroupXML.class)
@XmlType(propOrder = { "uuid", "name", "creatTime", "editTime" })
public interface GroupModel extends BaseModel {

	@XmlElement
	public String getName();

	@XmlElement
	public void setName(String name);

	@XmlTransient
	public List<UserModel> getUsers();

	@XmlElement
	public void setUsers(List<UserModel> users);
}
