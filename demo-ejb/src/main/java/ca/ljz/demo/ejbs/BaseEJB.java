package ca.ljz.demo.ejbs;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ca.ljz.demo.entities.Base;
import ca.ljz.demo.exceptions.ValidationException;

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

	private static Logger logger = Logger.getLogger(BaseEJB.class.getName());

	@PersistenceContext
	EntityManager em;

	@Inject
	Validator validator;

	public T get(I id) {
		T t = em.find(getEntityType(), id);
		return t;
	}

	public T update(T entity) {
		validate(entity);

		return em.merge(entity);
	}

	public I add(T entity) {

		logger.log(Level.INFO, "add");
		validate(entity);

		em.persist(entity);
		em.flush();

		logger.log(Level.INFO, "enity id: " + entity.getId());

		return entity.getId();
	}

	public T delete(I id) {
		T entity = get(id);
		em.remove(entity);
		return entity;
	}

	public abstract List<T> findAll();

	protected abstract Class<T> getEntityType();

	private void validate(T entity) {
		Set<ConstraintViolation<T>> violations = validator.validate(entity);
		if (violations.size() > 0) {
			Iterator<ConstraintViolation<T>> violationIterator = violations.iterator();
			String[] violationMsgs = new String[violations.size()];
			for (int i = 0; violationIterator.hasNext(); i++) {
				violationMsgs[i] = violationIterator.next().getMessage();
			}

			ValidationException ve = new ValidationException(violationMsgs);
			logger.log(Level.INFO, "ValidationException Message:" + ve.getMessages());
			logger.log(Level.INFO, "ValidationException ID:" + ve);
			throw ve;
		}
	}

}
