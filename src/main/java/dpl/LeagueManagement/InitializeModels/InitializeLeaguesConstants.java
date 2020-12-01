package dpl.LeagueManagement.InitializeModels;

public enum InitializeLeaguesConstants {
	
	LEAGUE_NAME("leagueName"),
	NULL_ERROR("Please enter League name. Null values are not accepted."),
	ERROR("Error"),
	VALID_MSG("Please enter valid League name."),
	CONFERENCES("conferences"),
	TEST_LEAGUE("Test");
	
	private final String constantString;

	InitializeLeaguesConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}
}
