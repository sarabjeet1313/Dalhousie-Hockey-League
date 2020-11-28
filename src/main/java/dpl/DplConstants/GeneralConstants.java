package dpl.DplConstants;

public enum GeneralConstants {
	
	DRIVER_NAME("com.mysql.cj.jdbc.Driver"), 
	DB_URL("db_url"),
	DB_USER_NAME("db_user_name"),
	DB_PASSWORD("db_password"),
	PROP_FILE("app.properties"),
	FORWARD("forward"),
	DEFENSE("defense"),
	GOALIE("goalie"),
	LOG_FILE("log.txt");

	private final String constantString;

	GeneralConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}
}
