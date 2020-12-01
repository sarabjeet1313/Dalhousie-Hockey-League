package dpl.LeagueManagement.TeamManagement;

public enum TeamManagementConstants {
	
	EMPTY("Empty"),
	TEAM_A("AllStarTeamA"),
	TEAM_B("AllStarTeamB"),
	RETIREMENT_INFO("Finding Retired Players"),
	SHOULD_PLAYER_INJURE("Found the player who should injure"),	
	SHOULD_PLAYER_RETIRE("Found the player who should retire"),
	RETIREMENT_EXCEPTION("Exception occured in league"),
	TEST_JSON("test.json");
	
	private final String constantString;

	TeamManagementConstants(String constantString) {
		this.constantString = constantString;
	}

	public String toString() {
		return constantString;
	}

}
