package ca.ljz.demo.ejbs;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.entities.Base;
import ca.ljz.demo.utils.UUIDUtils;

/**
 * 
 * @author Jianzhao Li
 *
 * @param <I>
 *            ID
 * @param <T>
 *            Entity Type
 */
@Stateless
public abstract class BaseEJB<I, T extends Base<I>> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1879756223965095751L;

	Logger logger = LoggerFactory.getLogger(BaseEJB.class);

	@PersistenceContext
	EntityManager em;

	@Resource
	SessionContext ctx;

	public T get(String id) {
		logger.info("get");
		T t = em.find(getEntityType(), UUIDUtils.uuidToByteArray(id));
		return t;
	}

	public T update(T entity) {
		logger.info("update");

		return em.merge(entity);
	}

	public I add(T entity) {
		logger.info("add");

		I id = entity.getId();

		em.persist(entity);

		return id;
	}

	public T delete(String id) {
		logger.info("delete");
		T entity = get(id);
		em.remove(entity);
		return entity;
	}

	public abstract List<T> search(T entity);

	protected abstract Class<T> getEntityType();

}
