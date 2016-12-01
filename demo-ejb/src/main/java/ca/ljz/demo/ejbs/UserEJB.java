package ca.ljz.demo.ejbs;

import java.util.List;

import javax.ejb.Stateless;

import ca.ljz.demo.entities.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
public class UserEJB extends BaseEJB<String, User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4089028826859008176L;

	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		return em.createNamedQuery(User.QUERY_ALL).getResultList();
	}

}
