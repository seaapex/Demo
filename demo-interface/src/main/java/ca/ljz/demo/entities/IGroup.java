package ca.ljz.demo.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "group")
public interface IGroup extends IModel {
	public String getName();

	@XmlElement
	public void setName(String name);

	@XmlTransient
	public List<IUser> getUsers();

	@XmlElement
	public void setUsers(List<IUser> users);
}
