package ca.ljz.demo.ejbs;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.entities.Base;
import ca.ljz.demo.entities.User;
import ca.ljz.demo.utils.UUIDUtils;

@Stateless
public abstract class BaseEJB<T extends Base> implements Serializable {

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
		entity.setEditor(getCaller());

		return em.merge(entity);
	}

	public String add(T entity) {
		logger.info("add");
		User caller = getCaller();

		if (caller == null) {
			if (entity instanceof User) {
				logger.info("Self User Registration");
				caller = (User) entity;
			} else {
				logger.debug("Unauthorized creation of an entity");
				throw new RuntimeException("Unauthorized creation of an entity");
			}
		}
		entity.setCreator(caller);
		entity.setEditor(caller);

		String id = entity.getUUID();

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

	private User getCaller() {
		logger.info("getCaller");
		User caller = null;
		try {
			String name = ctx.getCallerPrincipal().getName();
			TypedQuery<User> tq = em.createNamedQuery(User.QUERY_NAME, User.class);

			tq.setParameter("name", name);
			caller = tq.getSingleResult();
		} catch (Exception e) {
			logger.info("no caller");
		}
		return caller;
	}

}
