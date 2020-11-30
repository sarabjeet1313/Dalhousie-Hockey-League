package dpl.Database;

public enum DatabaseConstants {
	
	DB_USER_NAME("db_user_name"),
	DB_PASSWORD("db_password"),
	PROP_FILE("app.properties"),
	FORWARD("forward"),
	DEFENSE("defense"),
	GOALIE("goalie");

	private final String constantString;

	DatabaseConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}

}
