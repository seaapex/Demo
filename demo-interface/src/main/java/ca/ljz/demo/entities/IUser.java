package ca.ljz.demo.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public interface IUser<U extends IUser<U, G>, G extends IGroup<U, G>> extends IModel<U, G> {
	public String getName();

	public void setName(String name);

	@XmlTransient
	public String getPassword();

	@XmlElement
	public void setPassword(String password);

	public List<G> getGroups();

	public void setGroups(List<G> groups);
}
