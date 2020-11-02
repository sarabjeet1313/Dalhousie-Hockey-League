package dpl.DplConstants;

public enum InitializeLeaguesConstants {
	
	LEAGUE_NAME("leagueName"),
	NULL_ERROR("Please enter League name. Null values are not accepted."),
	ERROR("Error"),
	VALID_MSG("Please enter valid League name."),
	CONFERENCES("conferences");
	
	private final String constantString;

	private InitializeLeaguesConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}

}
