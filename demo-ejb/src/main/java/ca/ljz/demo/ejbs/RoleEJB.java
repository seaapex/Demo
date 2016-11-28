package ca.ljz.demo.ejbs;

import java.util.List;

import javax.ejb.Stateless;

import ca.ljz.demo.entities.Role;

/**
 * Session Bean implementation class RoleEJB
 */
@Stateless
public class RoleEJB extends BaseEJB<String, Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1465577601010030274L;

	@Override
	protected Class<Role> getEntityType() {
		return Role.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> search(Role entity) {
		List<Role> roles = null;

		if (entity == null) {
			roles = em.createNamedQuery(Role.QUERY_ALL).getResultList();
		} else {
		}

		return roles;
	}

}
