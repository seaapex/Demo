package ca.ljz.demo.ejbs;

import java.util.List;

import javax.ejb.Stateless;

import ca.ljz.demo.ejbs.local.UserLocal;
import ca.ljz.demo.entities.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
public class UserEJB extends BaseEJB<User> implements UserLocal<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4089028826859008176L;

	@Override
	protected Class<User> getEntityType() {
		logger.info("getEntityType");
		return User.class;
	}

	@Override
	public List<User> search(User entity) {
		logger.info("search");
		List<User> users = null;

		if (entity == null) {
			users = em.createNamedQuery(User.QUERY_ALL, getEntityType()).getResultList();
		} else {
		}

		return users;
	}

}
