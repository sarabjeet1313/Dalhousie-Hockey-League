package dal.asd.dpl.Util;

public enum InitializeLeaguesUtil {
	
	LEAGUE_NAME("leagueName"),
	NULL_ERROR("Please enter League name. Null values are not accepted."),
	ERROR("Error"),
	VALID_MSG("Please enter valid League name."),
	CONFERENCES("conferences");
	
	private final String constantString;

	private InitializeLeaguesUtil(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}

}
