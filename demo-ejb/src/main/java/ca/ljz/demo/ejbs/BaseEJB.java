package ca.ljz.demo.ejbs;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.ejbs.interceptors.DataTransferInterceptor;
import ca.ljz.demo.ejbs.local.ILocal;
import ca.ljz.demo.entities.User;
import ca.ljz.demo.model.BaseModel;
import ca.ljz.demo.model.UserModel;
import ca.ljz.demo.utils.UUIDUtils;

@Stateless
@Interceptors({ DataTransferInterceptor.class })
public abstract class BaseEJB<T extends BaseModel> implements ILocal<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1879756223965095751L;

	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	@PersistenceContext
	EntityManager em;

	@Resource
	SessionContext ctx;

	@Override
	public T get(String id) {
		logger.info("get");
		T t = em.find(getEntityType(), UUIDUtils.uuidToByteArray(id));
		return t;
	}

	@Override
	public T update(T entity) {
		logger.info("update");
		entity.setEditor(getCaller());

		return em.merge(entity);
	}

	@Override
	public String add(T entity) {
		logger.info("add");
		UserModel caller = getCaller();

		if (caller == null) {
			if (entity instanceof UserModel) {
				logger.info("Self User Registration");
				caller = (UserModel) entity;
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

	@Override
	public T delete(String id) {
		logger.info("delete");
		T entity = get(id);
		em.remove(entity);
		return entity;
	}

	protected abstract Class<T> getEntityType();

	private UserModel getCaller() {
		logger.info("getCaller");
		UserModel caller = null;
		try {
			String name = ctx.getCallerPrincipal().getName();
			TypedQuery<User> tq = em.createNamedQuery(User.QUERY_NAME, User.class);

			tq.setParameter("name", name);
			caller = tq.getSingleResult();
			logger.debug("getCaller - caller is: " + caller.getName());
		} catch (Exception e) {
			logger.info("no caller");
		}
		return caller;
	}

}
