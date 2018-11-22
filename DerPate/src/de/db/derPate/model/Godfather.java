package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Godfather extends User {
	@Nullable
	private String username;
	@Nullable
	private String password;
	
	public Godfather(@NonNull String username, @NonNull String password) {
		this.username = username;
		this.password = password;
	}
	
	@Nullable
	public String getFirstName() {
		return null;
	}
	
	@Nullable
	public String getLastName() {
		return null;
	}
}
