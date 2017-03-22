package com.zandero.server.entities;

/**
 * Dummy user entity
 */
public class User {

	private final String username;

	private final String password;

	private final String fullName;

	private final UserRole role;

	public User(String username, String password, String fullName, UserRole role) {

		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
	}

	public String getUsername() {

		return username;
	}

	public String getPassword() {

		return password;
	}

	public String getFullName() {

		return fullName;
	}

	public boolean isInRole(String allowedRole) {

		UserRole check = UserRole.parse(allowedRole);
		return role.isAllowed(check);
	}

	public UserRole getRole() {

		return role;
	}
}
