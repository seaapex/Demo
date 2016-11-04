package ca.ljz.demo.ejbs;

import java.util.List;

import javax.ejb.Stateless;

import ca.ljz.demo.entities.Group;

/**
 * Session Bean implementation class GroupEJB
 */
@Stateless
public class GroupEJB extends BaseEJB<Group> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1465577601010030274L;

	@Override
	protected Class<Group> getEntityType() {
		return Group.class;
	}

	@Override
	public List<Group> search(Group entity) {
		List<Group> groups = null;

		if (entity == null) {
			groups = em.createNamedQuery(Group.QUERY_ALL, getEntityType()).getResultList();
		} else {
		}
		
		return groups;
	}

}
