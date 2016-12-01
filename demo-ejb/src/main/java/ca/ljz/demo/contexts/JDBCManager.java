package ca.ljz.demo.contexts;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ca.ljz.demo.annotations.JDBCContext;
import ca.ljz.demo.annotations.Property;

@Singleton
@JDBCContext
public class JDBCManager {

	private static DataSource dS = null;

	@Inject
	@Property("dataSource")
	private String dataSource;

	@PostConstruct
	private void init(){
		try {
			InitialContext ic = new InitialContext();
			dS = (DataSource) ic.lookup(dataSource);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() throws SQLException {
		return dS.getConnection();
	}
}
