package ca.ljz.demo.ejbs;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import ca.ljz.demo.entities.IGroup;
import ca.ljz.demo.entities.IModel;
import ca.ljz.demo.entities.IUser;

@Local
public interface ILocal<T extends IModel<U, G>, U extends IUser<U, G>, G extends IGroup<U, G>> extends Serializable {
	public T get(String id);

	public T update(T entity);

	public String add(T entity);

	public T delete(String id);

	public List<T> search(T entity);

}
