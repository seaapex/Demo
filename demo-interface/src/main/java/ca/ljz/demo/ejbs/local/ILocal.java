package ca.ljz.demo.ejbs.local;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import ca.ljz.demo.model.BaseModel;

@Local
public interface ILocal<T extends BaseModel> extends Serializable {
	public T get(String id);

	public T update(T entity);

	public String add(T entity);

	public T delete(String id);

	public List<T> search(T entity);

}
