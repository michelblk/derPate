package de.db.derPate.model;

import org.eclipse.jdt.annotation.Nullable;

public abstract class User {
	@Nullable
	public abstract String getFirstName();
	@Nullable
	public abstract String getLastName();
}
