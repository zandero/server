package com.zandero.server.entities.json;

import com.zandero.rest.annotations.NotNullAndIgnoreUnknowns;
import com.zandero.server.entities.User;
import com.zandero.server.entities.UserRole;

/**
 * JSON representation
 */
@NotNullAndIgnoreUnknowns
public class UserJSON {

	public String name;

	public String userName;

	public UserRole role;

	private UserJSON() {}

	public UserJSON(User user) {

		name = user.getFullName();
		userName = user.getUsername();
		role = user.getRole();
	}
}
