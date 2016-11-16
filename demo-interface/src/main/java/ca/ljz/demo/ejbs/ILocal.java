package ca.ljz.demo.ejbs;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import ca.ljz.demo.entities.IModel;

@Local
public interface ILocal<T extends IModel> extends Serializable {
	public T get(String id);

	public T update(T entity);

	public String add(T entity);

	public T delete(String id);

	public List<T> search(T entity);

}
