package ca.ljz.demo.entities;

import javax.persistence.*;

/**
 * The persistent class for the nk_role database table.
 * 
 */
@Entity
@Table(name = "nk_role")
@NamedQueries({ @NamedQuery(name = Role.QUERY_ALL, query = "SELECT r FROM Role r"),
		@NamedQuery(name = Role.QUERY_ROLE, query = "SELECT r FROM Role r WHERE r.role = :role") })
public class Role extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1881643330493736434L;

	public static final String QUERY_ALL = "Role.findAll";
	public static final String QUERY_ROLE = "Role.findByRole";

	@Column(name = "NAME", nullable = false, length = 30)
	private String role;

	public Role() {
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}