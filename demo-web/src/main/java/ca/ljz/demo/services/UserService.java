package ca.ljz.demo.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ca.ljz.demo.entities.Role;
import ca.ljz.demo.entities.User;
import ca.ljz.demo.exceptions.ValidationException;
import ca.ljz.demo.ejbs.RoleEJB;
import ca.ljz.demo.ejbs.UserEJB;

@Stateless
public class UserService {

	@EJB
	UserEJB userEJB;

	@EJB
	RoleEJB roleEJB;

	public List<User> findAllUsers() {
		return userEJB.findAll();
	}

	public User findUserByUsername(String username) {
		return userEJB.get(username);
	}

	public List<User> searchUserByUsernameOrEmail(String input) {
		User user = new User();
		user.setUsername(input);
		List<User> containUsernameUsers = userEJB.findAll();

		List<User> users = new ArrayList<>();
		for (User u : containUsernameUsers) {
			if (u.getEmail().contains(input) || u.getUsername().contains(input)) {
				users.add(u);
				if (users.size() > 5)
					break;
			}
		}

		return users;
	}

	public String createUser(String username, String password, String email, String firstname, String lastname,
			String phonenumber, char gender) throws ValidationException {

		User user = new User();
		user.setUsername(username.trim());

		user.setPassword(password);
		user.setFirstname(firstname.trim());
		user.setLastname(lastname.trim());
		user.setEmail(email.trim().toLowerCase());
		user.setPhonenumber(phonenumber.trim());
		user.setGender(gender);

		List<Role> roles = new ArrayList<>(1);
		roles.add(roleEJB.get("user"));
		user.setRoles(roles);

		return userEJB.add(user);
	}

	public void editUser(String username, String password, String email, String firstname, String lastname,
			String phonenumber, char gender) throws ValidationException {

		User user = userEJB.get(username);

		user.setPassword(password == null || password.trim().isEmpty() ? user.getPassword() : password);
		user.setFirstname(firstname == null || firstname.trim().isEmpty() ? user.getFirstname() : firstname.trim());
		user.setLastname(lastname == null || lastname.trim().isEmpty() ? user.getLastname() : lastname.trim());
		user.setEmail(email == null || email.trim().isEmpty() ? user.getEmail() : email.trim().toLowerCase());
		user.setPhonenumber(
				phonenumber == null || phonenumber.trim().isEmpty() ? user.getPhonenumber() : phonenumber.trim());
		user.setGender(gender);

		userEJB.update(user);
	}

	public void deleteUserByUsername(String username) {
		User user = userEJB.get(username);

		user.getRoles().clear();
		userEJB.update(user);

		userEJB.delete(username);
	}

	public void promoteUser(String username) {
		User user = userEJB.get(username);
		if (!user.getRoles().contains(roleEJB.get("admin")))
			user.getRoles().add(roleEJB.get("admin"));
		userEJB.update(user);
	}

	public void demoteUser(String username) {
		User user = userEJB.get(username);
		user.getRoles().remove(roleEJB.get("admin"));
		userEJB.update(user);
	}
}
