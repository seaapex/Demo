package ca.ljz.demo.ejbs;

import java.util.List;

import javax.ejb.Stateless;

import ca.ljz.demo.entities.Role;

/**
 * Session Bean implementation class RoleEJB
 */
@Stateless
public class RoleEJB extends BaseEJB<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1465577601010030274L;

	@Override
	protected Class<Role> getEntityType() {
		return Role.class;
	}

	@Override
	public List<Role> search(Role entity) {
		List<Role> groups = null;

		if (entity == null) {
			groups = em.createNamedQuery(Role.QUERY_ALL, getEntityType()).getResultList();
		} else {
		}
		
		return groups;
	}

}
