package ca.ljz.demo.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "user")
public interface IUser extends IModel {
	public String getName();

	@XmlElement
	public void setName(String name);

	@XmlTransient
	public String getPassword();

	@XmlElement
	public void setPassword(String password);

	public List<IGroup> getGroups();

	@XmlElement
	public void setGroups(List<IGroup> groups);
}
