package ca.ljz.demo.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import ca.ljz.demo.entities.User;
import ca.ljz.demo.exceptions.InvalidUserException;
import ca.ljz.demo.ejbs.UserEJB;

@Stateless
public class UserService {
	@EJB
	UserEJB userEJB;

	@Inject
	Validator validator;

	public List<User> findAllUsers() {
		return userEJB.search(null);
	}

	public User findUserById(String id) {
		return userEJB.get(id);
	}

	public User findUserByUsername(String username) {
		User user = new User();
		user.setUsername(username);
		List<User> containUsernameUsers = userEJB.search(user);

		for (User u : containUsernameUsers) {
			if (u.getUsername().equals(username))
				return u;
		}

		return null;
	}

	public String createUser(User user) throws InvalidUserException {
		String[] violationMsgs = validateUser(user);
		if (violationMsgs != null)
			throw new InvalidUserException(violationMsgs);
		return userEJB.add(user);
	}

	public void editUser(User user) throws InvalidUserException {
		String[] violationMsgs = validateUser(user);
		if (violationMsgs != null)
			throw new InvalidUserException(violationMsgs);
		userEJB.update(user);
	}

	public void deleteUserById(String id) {
		userEJB.delete(id);
	}

	private String[] validateUser(User user) {
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		if (violations.size() > 0) {
			Iterator<ConstraintViolation<User>> violationIterator = violations.iterator();
			String[] violationMsgs = new String[violations.size()];
			for (int i = 0; violationIterator.hasNext(); i++) {
				violationMsgs[i] = violationIterator.next().getMessage();
			}
			return violationMsgs;
		}
		return null;
	}
}
