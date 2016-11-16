package ca.ljz.demo.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public interface IGroup<U extends IUser<U, G>, G extends IGroup<U, G>> extends IModel<U, G> {
	public String getName();

	public void setName(String name);

	@XmlTransient
	public List<U> getUsers();

	public void setUsers(List<U> users);
}
