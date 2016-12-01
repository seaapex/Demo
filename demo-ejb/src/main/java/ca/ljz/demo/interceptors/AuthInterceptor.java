package ca.ljz.demo.interceptors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ca.ljz.demo.annotations.Auth;
import ca.ljz.demo.annotations.AuthContext;
import ca.ljz.demo.annotations.JDBCContext;
import ca.ljz.demo.annotations.Property;
import ca.ljz.demo.contexts.CallerManager;
import ca.ljz.demo.contexts.JDBCManager;

@Interceptor
@Auth
@Priority(Interceptor.Priority.APPLICATION)
public class AuthInterceptor {

	private static Logger logger = Logger.getLogger(AuthInterceptor.class.getName());

	@Inject
	@AuthContext
	private CallerManager ac;

	@Inject
	@Property("auth.role")
	private String roleSql;

	@Inject
	@Property("auth.password")
	private String passwordSql;

	@Inject
	@JDBCContext
	private JDBCManager jm;

	@AroundInvoke
	public Object AuthorizeUser(InvocationContext context) throws Exception {
		logger.log(Level.INFO, "AuthorizeUser");

		try (Connection c = jm.getConnection()) {

			logger.log(Level.INFO, ac.getCallerPrincipal());
			logger.log(Level.INFO, ac.getCallerPassword());

			logger.log(Level.INFO, context.getMethod().getAnnotation(Auth.class).value()[0]);
			logger.log(Level.INFO, "Role Sql: " + roleSql);
			logger.log(Level.INFO, "Password Sql: " + passwordSql);

			PreparedStatement passwordQuery = c.prepareStatement(passwordSql);
			passwordQuery.setString(1, ac.getCallerPrincipal());
			ResultSet passwordResult = passwordQuery.executeQuery();
			if (passwordResult.next() && passwordResult.getString(1).equals(ac.getCallerPassword())) {
				logger.log(Level.INFO, "User is authendicated: " + ac.getCallerPrincipal());

				if ("ALL".equalsIgnoreCase(context.getMethod().getAnnotation(Auth.class).value()[0]))
					return context.proceed();

				PreparedStatement roleQuery = c.prepareStatement(roleSql);
				roleQuery.setString(1, ac.getCallerPrincipal());
				ResultSet roleSet = roleQuery.executeQuery();
				while (roleSet.next()) {
					String role = roleSet.getString(1);
					for (String authRole : context.getMethod().getAnnotation(Auth.class).value()) {
						if (role.equalsIgnoreCase(authRole)) {
							logger.log(Level.INFO, "User is authorized: " + ac.getCallerPrincipal());
							return context.proceed();
						}
					}
				}
			}
		} catch (Exception e) {
			// any exception would cause unauthorized
		}

		return Response.status(Status.UNAUTHORIZED).build();
	}

}
