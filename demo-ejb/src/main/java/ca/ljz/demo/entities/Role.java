package ca.ljz.demo.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the nk_role database table.
 * 
 */
@Entity
@Table(name = "nk_role")
@NamedQueries({ @NamedQuery(name = Role.QUERY_ALL, query = "SELECT r FROM Role r"),
		@NamedQuery(name = Role.QUERY_ROLE, query = "SELECT r FROM Role r WHERE r.rolename = :rolename") })
public class Role extends Base<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1881643330493736434L;

	public static final String QUERY_ALL = "Role.findAll";
	public static final String QUERY_ROLE = "Role.findByRole";

	@Id
	@Column(nullable = false, length = 30)
	private String rolename;

	public Role() {
	}

	@XmlTransient
	@Override
	public String getId() {
		return getRolename();
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}