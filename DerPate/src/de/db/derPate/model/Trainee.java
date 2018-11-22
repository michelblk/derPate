package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Trainee extends User {
	@NonNull
	private String loginToken;
	@Nullable
	private Godfather godfather = null;
	
	public Trainee(@NonNull String loginToken) {
		this.loginToken = loginToken;
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
