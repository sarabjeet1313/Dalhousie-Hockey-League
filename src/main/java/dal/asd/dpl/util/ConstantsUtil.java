package dal.asd.dpl.util;

public enum ConstantsUtil {
	DRIVER_NAME("com.mysql.cj.jdbc.Driver"), 
	DB_URL("db_url"),
	DB_USER_NAME("db_user_name"),
	DB_PASSWORD("db_password"),
	PROP_FILE("app.properties"),
	FORWARD("forward"),
	DEFENSE("defense"),
	GOALIE("goalie");

	private final String constantString;

	private ConstantsUtil(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}

}
